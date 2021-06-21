package br.com.diego.brewer.controller.handler;

import br.com.diego.brewer.service.impl.exception.NomeEstiloJaCadastradoException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdviceExceptionHandler {

    @ExceptionHandler(NomeEstiloJaCadastradoException.class)
    public ResponseEntity<String> handleNomeEstiloJaCadastradoException(NomeEstiloJaCadastradoException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
