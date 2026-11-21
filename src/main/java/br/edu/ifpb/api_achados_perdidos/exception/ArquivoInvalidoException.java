package br.edu.ifpb.api_achados_perdidos.exception;

public class ArquivoInvalidoException extends RuntimeException {
    public ArquivoInvalidoException(String mensagem) {
        super(mensagem);
    }
}