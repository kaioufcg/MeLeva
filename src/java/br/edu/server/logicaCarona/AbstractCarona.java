package br.edu.server.logicaCarona;

import br.edu.client.utilitarios.MSGDeExcecao;
import br.edu.client.utilitarios.MeLevaException;
import br.edu.server.logicaNegociacao.Negociacao;
import br.edu.server.logicaSistema.Verificador;
import java.io.Serializable;

/**
 * Classe abstrata para representação de uma carona.
 *
 * @author Grupo Me Leva - Projeto SI1 - UFCG 2012.1
 *
 */
public abstract class AbstractCarona implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 6572699459890024959L;
    private String origem;
    private String destino;
    private String data;
    private String hora;
    private int vagas;
    private int id;
    private static int contador;
    private Verificador verificador;

    /**
     * Enum dos atributos de uma carona.
     *
     * @author Grupo Me Leva - Projeto SI1 - UFCG 2012.1
     *
     */
    public enum AtributosDeCarona {

        ORIGEM("origem"), DESTINO("destino"), DATA("data"), HORA("hora"), VAGA(
        "vagas"), PONTO_DE_ENCONTRO("Ponto de Encontro"), EH_MUNICIPAL(
        "ehMunicipal");
        private String atributo;

        /**
         * Construtor
         *
         * @param atributo - Atributo.
         */
        AtributosDeCarona(String atributo) {
            this.atributo = atributo;
        }

        /**
         * Método que acessa o atributo da carona.
         *
         * @return Atributo da carona.
         */
        public String getAtributo() {
            return atributo;
        }
    }

    /**
     * Construtor da carona.
     *
     * @param origem - String que representa a origem de onde parte a carona.
     * @param destino - String que representa o destino da carona.
     * @param data - Data que parte a carona.
     * @param hora - Horário que parte a carona.
     * @param vagas - Quantidade de vagas na carona.
     * @throws MeLevaException - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public AbstractCarona(String origem, String destino, String data,
            String hora, Integer vagas) throws MeLevaException {

        verificador = new Verificador();
        verificador.verificaOrigem(origem);
        verificador.verificaDestino(destino);
        verificador.verificaOrigemDestinoVazio(origem, destino);
        verificador.verificaData(data, hora);
        verificador.verificaVagas(String.valueOf(vagas));

        this.origem = origem;
        this.destino = destino;
        this.data = data;
        this.hora = hora;
        this.vagas = vagas;
        id = ++contador * 10;
    }

    /**
     * Método que acessa o atributo da carona.
     *
     * @param atributo - Nome do atributo a ser avaliado.
     * @param negociacoes - Negociacao da carona ofertada.
     * @return String do atributo da carona ofertada.
     * @throws MeLevaException - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public String getAtributo(String atributo, Negociacao negociacoes)
            throws MeLevaException {
        if (atributo == null || atributo.equals("")) {
            throw new MeLevaException(MSGDeExcecao.MSG_ERRO_ATRIBUTO_INVALIDO);
        }

        String resp = null;

        if (AtributosDeCarona.ORIGEM.getAtributo().equalsIgnoreCase(atributo)) {
            resp = this.getOrigem();
        } else if (AtributosDeCarona.DATA.getAtributo().equalsIgnoreCase(
                atributo)) {
            resp = this.getData();
        } else if (AtributosDeCarona.DESTINO.getAtributo().equalsIgnoreCase(
                atributo)) {
            resp = this.getDestino();
        } else if (AtributosDeCarona.VAGA.getAtributo().equalsIgnoreCase(
                atributo)) {
            resp = String.valueOf(this.getVagas());
        } else if (AtributosDeCarona.HORA.getAtributo().equalsIgnoreCase(
                atributo)) {
            resp = String.valueOf(this.getHora());
        } else if (AtributosDeCarona.PONTO_DE_ENCONTRO.getAtributo().equalsIgnoreCase(atributo)) {
            resp = negociacoes.getPontosDeEmbarque(this.getId());
        } else if (AtributosDeCarona.EH_MUNICIPAL.getAtributo().equalsIgnoreCase(atributo)) {
            resp = String.valueOf(this.getMunicipal());
        } else {
            throw new MeLevaException(
                    MSGDeExcecao.MSG_ERRO_ATRIBUTO_INEXISTENTE);
        }
        return resp;
    }

    /**
     * Método que acessa uma string informando o trajeto da carona.
     *
     * @return String do trajeto da carona.
     */
    public String getTrajeto() {
        return this.getOrigem() + " - " + this.getDestino();
    }

    /**
     * Método que acessa uma string representando as informações da carona.
     *
     * @return String representando as informações da carona.
     */
    public String getCarona() {
        return this.getOrigem() + " para " + this.getDestino() + ", no dia "
                + this.getData() + ", as " + this.getHora();
    }

    /**
     * Método que acessa se uma carona é municipal.
     *
     * @return Boolean se é uma carona municipal.
     */
    public abstract boolean getMunicipal();

    /**
     * Método que modifica o número de vagas na carona se ela for preenchida.
     */
    public void preencheVagas() {
        vagas--;
    }

    /**
     * Método que acessa a origem da carona.
     *
     * @return String origem da carona.
     */
    public String getOrigem() {
        return origem;
    }

    /**
     * Método que acessa o destino da carona.
     *
     * @return String destino da carona
     */
    public String getDestino() {
        return destino;
    }

    /**
     * Método que modifica a origem da carona.
     *
     * @param origem
     */
    public void setOrigem(String origem) {
        this.origem = origem;
    }

    /**
     * Método que acessa a data da partida da carona.
     *
     * @return String data da partida da carona.
     */
    public String getData() {
        return data;
    }

    /**
     * Método que acessa a hora da partida da carona.
     *
     * @return String hora da partida da carona.
     */
    public String getHora() {
        return hora;
    }

    /**
     * Método que acessa as vagas da carona.
     *
     * @return Quantidade de vagas na carona.
     */
    public Integer getVagas() {
        return vagas;
    }

    /**
     * Método que acessa o numero identificador da carona.
     *
     * @return String do identificador da carona.
     */
    public String getId() {
        return String.valueOf(id);
    }

    public static void zeraCont() {
        contador = 0;
    }

    public static void iniciaCont(int caronasContadas) {
        contador = caronasContadas;
    }
}
