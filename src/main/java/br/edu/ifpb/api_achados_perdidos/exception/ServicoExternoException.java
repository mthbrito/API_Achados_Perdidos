package br.edu.ifpb.api_achados_perdidos.exception;

public class ServicoExternoException extends RuntimeException {
    public ServicoExternoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}