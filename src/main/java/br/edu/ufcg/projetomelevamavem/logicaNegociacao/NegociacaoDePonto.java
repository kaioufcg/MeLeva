package br.edu.ufcg.projetomelevamavem.logicaNegociacao;

import br.edu.ufcg.projetomelevamavem.logicaCarona.AbstractCarona;
import br.edu.ufcg.projetomelevamavem.logicaUsuario.Usuario;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Classe que representa a negociação entre usuário e a carona.
 *
 * @author Grupo Me Leva - Projeto SI1 - UFCG 2012.1
 *
 */
public class NegociacaoDePonto implements Serializable {

    private static final long serialVersionUID = 00000000016L;
    private static int contador;
    private final AbstractCarona carona;
    private final Usuario usuario;
    private String pontos;
    private int iDSugestao;

    /**
     * Construtor da negociação.
     *
     * @param carona - Carona.
     * @param usuario - Usuário.
     * @param pontos - Pontos de encontro.
     */
    public NegociacaoDePonto(AbstractCarona carona, Usuario usuario,
            String pontos) {
        this.carona = carona;
        this.usuario = usuario;
        this.pontos = pontos;

        iDSugestao = ++contador * 100;
    }

    /**
     * Método que acessa a carona da negociação.
     *
     * @return Carona.
     */
    public AbstractCarona getCarona() {
        return carona;
    }

    /**
     * Método que acessa o usuário da negociação.
     *
     * @return Usuário.
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Método que acessa os pontos de encontro da negociação.
     *
     * @return Pontos de encontro.
     */
    public String getPontos() {
        return pontos;
    }

    /**
     * Método que acessa o indetificador da sugestão.
     *
     * @return Identificador da sugestão.
     */
    public int getIdSugestao() {
        return iDSugestao;
    }

    /**
     * Lista com os pontos de encontro sugeridos.
     *
     * @return Pontos sugeridos.
     */
    public List<String> getPontosDeEncontro() {
        List<String> pontosSugeridos = new LinkedList<String>();
        String[] lista = pontos.split(";");
        for (String ponto : lista) {
            pontosSugeridos.add(ponto.trim());
        }

        return pontosSugeridos;
    }

    /**
     * Método que modifica e zera o contador de indetificador das negociações.
     */
    protected static void zeraCont() {
        contador = 0;
    }
}