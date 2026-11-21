package br.edu.ifpb.api_achados_perdidos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ArquivoInvalidoException.class)
    public ResponseEntity<Map<String, String>> arquivoInvalido(ArquivoInvalidoException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("erro", ex.getMessage()));
    }

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<Map<String, String>> naoEncontrado(RecursoNaoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("erro", ex.getMessage()));
    }

    @ExceptionHandler(ServicoExternoException.class)
    public ResponseEntity<Map<String, String>> servicoExterno(ServicoExternoException ex) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                .body(Map.of("erro", ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> validacao(MethodArgumentNotValidException ex) {
        Map<String, String> erros = new HashMap<>();
        ex.getBindingResult()
                .getFieldErrors()
                .forEach(e -> erros.put(e.getField(), e.getDefaultMessage()));
        return ResponseEntity.badRequest()
                .body(erros);
    }
}
