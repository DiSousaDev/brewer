package br.com.diego.brewer.thymeleaf.processor;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.spring5.util.FieldUtils;
import org.thymeleaf.templatemode.TemplateMode;

public class ClassForErrorAttributeProcessor extends AbstractAttributeTagProcessor {

    private static final String NOME_ATRIBUTO = "classforerror";
    private static final int PRECEDENCIA = 1000;

    public ClassForErrorAttributeProcessor(String dialectPrefix){
        super(TemplateMode.HTML, dialectPrefix, null, false, NOME_ATRIBUTO, true, PRECEDENCIA, true);
    }

    @Override
    protected void doProcess(ITemplateContext context, IProcessableElementTag tag, AttributeName attributeName,
            String attributeValue, IElementTagStructureHandler structureHandler){

        boolean temErro = FieldUtils.hasErrors(context, attributeValue);

        if(temErro) {
            String classesExistentes = tag.getAttributeValue("class");
            structureHandler.setAttribute("class", classesExistentes + " has-error");
        }

    }
}
