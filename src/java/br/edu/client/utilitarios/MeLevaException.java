package br.edu.client.utilitarios;

import java.io.Serializable;

/**
 * Exceção do sistema Me Leva.
 * 
 * @author Grupo Me Leva - Projeto SI1 - UFCG 2012.1
 * 
 */
public class MeLevaException extends IllegalArgumentException implements
        Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1380712028566030759L;
    private String msg;

    /**
     * Contrutor da Exceção.
     */
    public MeLevaException() {
    }

    /**
     * Contrutor da Exceção.
     * 
     * @param msg
     *            - Mensagem da exceção.
     */
    public MeLevaException(String msg) {
        super(msg);
        this.msg = msg;

    }

    public String getMsg() {
        return msg;
    }
}
