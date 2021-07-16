package br.com.diego.brewer.mail;

import br.com.diego.brewer.model.Venda;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.internet.MimeMessage;

@Service
public class Mailer {

    private static Logger logger = LoggerFactory.getLogger(Mailer.class);

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Async
    public void enviar(Venda venda) {

        Context context = new Context();
        context.setVariable("venda", venda);

        try {
            String email = templateEngine.process("mail/ResumoVenda", context);
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setFrom("ederctba@hotmail.com");
            helper.setTo(venda.getCliente().getEmail());
            helper.setSubject("Brewer - Venda Realizada");
            helper.setText(email, true);
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            logger.error("Erro ao enviar e-mail.");
        }

    }

}
