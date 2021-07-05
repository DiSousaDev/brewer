package br.com.diego.brewer.model.validation.validator;

import br.com.diego.brewer.model.validation.AtributoConfirmacao;
import org.apache.commons.beanutils.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AtributoConfirmacaoValidator implements ConstraintValidator<AtributoConfirmacao, Object> {

    private String atributo;
    private String atributoConfirmacao;

    @Override
    public void initialize(AtributoConfirmacao constraintAnnotation){
        this.atributo = constraintAnnotation.atributo();
        this.atributoConfirmacao = constraintAnnotation.atributoConfirmacao();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context){

        boolean valido = false;

        try {
            Object valorAtributo = BeanUtils.getProperty(object, this.atributo);
            Object valorAtributoConfirmacao = BeanUtils.getProperty(object, this.atributoConfirmacao);
            valido = ambosSaoNull(valorAtributo, valorAtributoConfirmacao) || ambosSaoIguais(valorAtributo, valorAtributoConfirmacao);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao recuperar valores dos atributos", e);
        }

        if(!valido) {
            context.disableDefaultConstraintViolation();
            String mensagem = context.getDefaultConstraintMessageTemplate();
            ConstraintValidatorContext.ConstraintViolationBuilder violationBuilder = context.buildConstraintViolationWithTemplate(mensagem);
            violationBuilder.addPropertyNode(atributoConfirmacao).addConstraintViolation();
        }

        return valido;

    }

    private boolean ambosSaoIguais(Object valorAtributo, Object valorAtributoConfirmacao){
        return valorAtributo != null && valorAtributo.equals(valorAtributoConfirmacao);
    }

    private boolean ambosSaoNull(Object valorAtributo, Object valorAtributoConfirmacao){
        return valorAtributo == null && valorAtributoConfirmacao == null;
    }
}
