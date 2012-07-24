package br.edu.server.logicaSistema;

import br.edu.server.logicaUsuario.Usuario;
import java.io.Serializable;

/**
 * Classe que representa os interesses por caronas.
 *
 * @author Grupo Me Leva - Projeto SI1 - UFCG 2012.1
 *
 */
public class Interesse implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -4836767115800217982L;
    private final int MIL = 1000;
    private final Usuario user;
    private final String origem;
    private final String destino;
    private final String data;
    private String horaInicio = "00:00";
    private final String horaFim;
    private static int contador;
    private final int ID;

    /**
     * Construtor do interesse.
     *
     * @param user - Usuário de interesse.
     * @param origem - Origem de interesse.
     * @param destino - Destino de Interesse.
     * @param data - Data de Interesse.
     * @param horaInicio - Horario inicial de interesse.
     * @param horaFim - Horario final de Ineresse.
     */
    public Interesse(Usuario user, String origem, String destino, String data,
            String horaInicio, String horaFim) {

        this.user = user;
        this.origem = origem;
        this.destino = destino;
        this.data = data;
        if (!horaInicio.equals("")) {
            this.horaInicio = horaInicio;
        }
        this.horaFim = horaFim;
        contador++;
        ID = contador * MIL;
    }

    /**
     * Método acessador do Identificador.
     *
     * @return Identificador.
     */
    public int getId() {
        return ID;
    }

    /**
     * Método acessador do usuário.
     *
     * @return Usuário.
     */
    public Usuario getUser() {
        return user;
    }

    /**
     * Método acessador da origem.
     *
     * @return Origem.
     */
    public String getOrigem() {
        return origem;
    }

    /**
     * Método acessador do destino.
     *
     * @return Destino.
     */
    public String getDestino() {
        return destino;
    }

    /**
     * Método acessador da data.
     *
     * @return Data.
     */
    public String getData() {
        return data;
    }

    /**
     * Método acessador do horario inicial.
     *
     * @return Hora inicial.
     */
    public String getHoraInicio() {
        return horaInicio;
    }

    /**
     * Método acessador do horario final.
     *
     * @return Hora final.
     */
    public String getHoraFim() {
        return horaFim;
    }

    /**
     * Métod que zera o contador de indertificador.
     */
    protected static void zeraCont() {
        contador = 0;
    }

    static void iniciaContador(int size) {
        contador = size;
    }
}
