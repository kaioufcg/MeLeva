package br.edu.ufcg.projetomelevamavem.logicaSistema;

import java.io.Serializable;

/**
 * Exceção do sistema Me Leva.
 *
 * @author Grupo Me Leva - Projeto SI1 - UFCG 2012.1
 *
 */
public class MeLevaException extends Exception implements Serializable {

    private String msg;

    public MeLevaException() {
    }

    /**
     * Contrutor da Exceção.
     *
     * @param msg - Mensagem da exceção.
     */
    public MeLevaException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public String getSymbol() {
        return this.msg;
    }
}
