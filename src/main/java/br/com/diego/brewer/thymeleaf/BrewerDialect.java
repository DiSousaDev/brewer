package br.com.diego.brewer.thymeleaf;

import br.com.diego.brewer.thymeleaf.processor.ClassForErrorAttributeTagProcessor;
import br.com.diego.brewer.thymeleaf.processor.ClassForMessageElementTagProcessor;
import br.com.diego.brewer.thymeleaf.processor.ClassForOrderElementTagProcessor;
import br.com.diego.brewer.thymeleaf.processor.ClassForPaginationElementTagProcessor;

import org.springframework.stereotype.Component;
import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;

import java.util.HashSet;
import java.util.Set;

@Component
public class BrewerDialect extends AbstractProcessorDialect {

    public BrewerDialect() {
        super("Algaworks Brewer", "brewer", StandardDialect.PROCESSOR_PRECEDENCE);
    }

    @Override
    public Set<IProcessor> getProcessors(String dialectPrefix){
        final Set<IProcessor> processadores = new HashSet<>();
        processadores.add(new ClassForErrorAttributeTagProcessor(dialectPrefix));
        processadores.add(new ClassForMessageElementTagProcessor(dialectPrefix));
        processadores.add(new ClassForOrderElementTagProcessor(dialectPrefix));
        processadores.add(new ClassForPaginationElementTagProcessor(dialectPrefix));
        return processadores;
    }

}
