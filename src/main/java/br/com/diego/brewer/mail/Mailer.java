package br.com.diego.brewer.mail;

import br.com.diego.brewer.model.Cerveja;
import br.com.diego.brewer.model.ItemVenda;
import br.com.diego.brewer.model.Venda;
import br.com.diego.brewer.storage.FotoStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Component
public class Mailer {

    private static final Logger logger = LoggerFactory.getLogger(Mailer.class);

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private FotoStorage fotoStorage;

    @Async
    public void enviar(Venda venda) {

        Context context = new Context(new Locale("pt", "BR"));
        context.setVariable("venda", venda);
        context.setVariable("logo", "logo");

        Map<String, String> fotos = new HashMap<>();
        boolean adicionarMockCerveja = false;
        for(ItemVenda item : venda.getItens()) {
            Cerveja cerveja = item.getCerveja();

            if(!ObjectUtils.isEmpty(cerveja.getFoto())) {
                String cid = "foto-" + cerveja.getCodigo();
                context.setVariable(cid, cid);
                fotos.put(cid, cerveja.getFoto() + "|" + cerveja.getContentType());
            } else {
                adicionarMockCerveja = true;
                context.setVariable("mockCerveja", "mockCerveja");
            }
        }
        try {
            String email = templateEngine.process("mail/ResumoVenda", context);
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setFrom("ederctba@hotmail.com");
            helper.setTo(venda.getCliente().getEmail());
            helper.setSubject(String.format("Brewer - Venda nº %d", venda.getCodigo()));
            helper.setText(email, true);

            helper.addInline("logo", new ClassPathResource("static/layout/images/logo-gray.png"));

            if(adicionarMockCerveja) {
                helper.addInline("mockCerveja", new ClassPathResource("static/layout/images/cerveja-mock.png"));
            }


            for (String cid : fotos.keySet()) {
                String[] fotoContentType = fotos.get(cid).split("\\|");
                String foto = fotoContentType[0];
                String contentType = fotoContentType[1];
                byte[] arrayFoto = fotoStorage.recuperarFoto("thumbnail." + foto);
                helper.addInline(cid, new ByteArrayResource(arrayFoto), contentType);
            }

            mailSender.send(mimeMessage);
        } catch (Exception e) {
            logger.error("Erro ao enviar e-mail.");
            e.printStackTrace();
        }

    }

}
