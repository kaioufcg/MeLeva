package br.edu.ufcg.projetomelevamavem.logicaNegociacao;

import br.edu.ufcg.projetomelevamavem.logicaCarona.AbstractCarona;
import br.edu.ufcg.projetomelevamavem.logicaSistema.ControleDeUsuarios;
import br.edu.ufcg.projetomelevamavem.logicaSistema.MSGDeExcecao;
import br.edu.ufcg.projetomelevamavem.logicaSistema.MeLevaException;
import br.edu.ufcg.projetomelevamavem.logicaSistema.Verificador;
import br.edu.ufcg.projetomelevamavem.logicaUsuario.Usuario;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Classe controladora das negociações entre o usuário e o caroneiro.
 *
 * @author Grupo Me Leva - Projeto SI1 - UFCG 2012.1
 *
 */
public class Negociacao implements Serializable {

    private static final long serialVersionUID = 00000000015L;
    private Verificador verificar;
    private Map<Integer, NegociacaoDePonto> sugestoesPontoDeEncontro;
    private Map<Integer, NegociacaoDePonto> respostasSugestaoPontoEncontro;
    private List<NegociacaoDePonto> solicitacoesDeViagem;
    private Map<String, NegociacaoDePonto> solicitacoesPendentes,
            solicitacoesConfirmadas;

    /**
     * Enum dos atributos de uma negociação.
     *
     * @author Grupo Me Leva - Projeto SI1 - UFCG 2012.1
     *
     */
    enum AtributosDeSolicitacao {

        ORIGEM("origem"), DESTINO("destino"), DONO_DA_CARONA("Dono da carona"), DONO_DA_SOLICITACAO(
        "Dono da solicitacao"), PONTO_DE_DESTINO("Ponto de Encontro");
        private String atributo;

        /**
         * Construtor do enum.
         *
         * @param atributo - Atributo da negociação.
         */
        AtributosDeSolicitacao(String atributo) {
            this.atributo = atributo;
        }

        /**
         * Método que acessa o atributo da negociação.
         *
         * @return Atributo da negociação.
         */
        public String getAtributo() {
            return atributo;
        }
    }

    /**
     * Contrutor da negociação entre o usuário e o caroneiro.
     */
    public Negociacao() {
        verificar = new Verificador();
        sugestoesPontoDeEncontro = new HashMap<Integer, NegociacaoDePonto>();
        respostasSugestaoPontoEncontro = new HashMap<Integer, NegociacaoDePonto>();

        solicitacoesDeViagem = new LinkedList<NegociacaoDePonto>();
        solicitacoesPendentes = new HashMap<String, NegociacaoDePonto>();
        solicitacoesConfirmadas = new HashMap<String, NegociacaoDePonto>();

    }

    /**
     * Método para zerar as negociações.
     */
    public void clear() {
        sugestoesPontoDeEncontro.clear();
        respostasSugestaoPontoEncontro.clear();
        solicitacoesDeViagem.clear();
        solicitacoesPendentes.clear();
        solicitacoesConfirmadas.clear();
        NegociacaoDePonto.zeraCont();

    }

    /**
     * Método que adiciona resposta das sugestões de pontos de encontro.
     *
     * @param iDSessao - Identificador de sessão do usuário.
     * @param idCarona - Identificador da carona.
     * @param pontos - Pontos de encontro.
     * @return Identificador da resposta da sugestão.
     * @throws MeLevaException - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public int addRespostaPontoDeEncontro(String iDSessao, String idCarona,
            String pontos) throws MeLevaException {
        verificar.verificaIdSessao(iDSessao);
        verificar.verificaIdCarona(idCarona);
        verificar.verificaPonto(pontos);

        Usuario usuario = ControleDeUsuarios.getInstance().getUsuarioId(
                iDSessao);
        AbstractCarona carona = ControleDeUsuarios.getInstance().getCarona(
                idCarona);

        NegociacaoDePonto sugestaoResposta = new NegociacaoDePonto(carona,
                usuario, pontos);

        respostasSugestaoPontoEncontro.put(sugestaoResposta.getIdSugestao(),
                sugestaoResposta);
        return sugestaoResposta.getIdSugestao();

    }

    /**
     * Método que adiciona uma solicitação para viajar.
     *
     * @param iDSessao - Identificador da sessão do usuário.
     * @param iDCarona - Identificador da carona.
     * @param ponto - Ponto de encontro.
     * @return Identificador da solicitação.
     * @throws MeLevaException - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public int addSolicitacaoParaViajar(String iDSessao, String iDCarona,
            String ponto) throws MeLevaException {

        // dando pau aki pois nao é o mesmo controle
        ControleDeUsuarios controle = ControleDeUsuarios.getInstance();
        Usuario usuarioQuePede = controle.getUsuarioId(iDSessao);
        AbstractCarona caronaRecebe = ControleDeUsuarios.getInstance().getCarona(iDCarona);
        if (usuarioQuePede == null) {
            throw new MeLevaException(MSGDeExcecao.MSG_ERRO_USUARIO_INEXISTENTE);
        }
        if (caronaRecebe == null) {
            throw new MeLevaException(MSGDeExcecao.MSG_ERRO_CARONA_INEXISTENTE);
        }
        NegociacaoDePonto solicitacao = new NegociacaoDePonto(caronaRecebe,
                usuarioQuePede, ponto);

        solicitacoesDeViagem.add(solicitacao);
        solicitacoesPendentes.put(iDCarona, solicitacao);

        // mudou aki
        return solicitacao.getIdSugestao();
    }

    /**
     * Método que adiciona sugestões de pontos de encontro.
     *
     * @param iDSessao - Identificador de sessão do usuário.
     * @param idCarona - Identificador da carona.
     * @param pontos - Pontos de encontro.
     * @return Identificador da sugestão.
     * @throws MeLevaException - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public int addSugestaoPontoDeEncontro(String iDSessao, String idCarona,
            String pontos) throws MeLevaException {

        verificar.verificaIdSessao(iDSessao);
        verificar.verificaIdCarona(idCarona);
        verificar.verificaPonto(pontos);

        Usuario usuario = ControleDeUsuarios.getInstance().getUsuarioId(
                iDSessao);
        AbstractCarona carona = ControleDeUsuarios.getInstance().getCarona(
                idCarona);

        if (usuario == null) {
            throw new MeLevaException(MSGDeExcecao.MSG_ERRO_USUARIO_INEXISTENTE);
        }
        if (carona == null) {
            throw new MeLevaException(MSGDeExcecao.MSG_ERRO_CARONA_INEXISTENTE);
        }
        NegociacaoDePonto sugestao = new NegociacaoDePonto(carona, usuario,
                pontos);

        verificar.verificaContemNegociacao(carona, usuario,
                sugestoesPontoDeEncontro);
        // if (naoEstaEmPontosDeEmbarque(sugestao)) {
        // throw new MeLevaException(MSGDeExcecao.MSG_ERRO_PONTO_INVALIDO);
        // }

        sugestoesPontoDeEncontro.put(sugestao.getIdSugestao(), sugestao);
        return sugestao.getIdSugestao();
    }

    /**
     * Método que faz o usuário a ceitar a solicitação do ponto de encontro.
     *
     * @param iDSessao - Identificador da sessão do usuário.
     * @param idSolicitacao - Identificador da Solicitação do ponto de encontro.
     * @throws MeLevaException - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public void aceitarSolicitacaoPontoEncontro(String iDSessao,
            String idSolicitacao) throws MeLevaException {
        verificar.verificaIdSolicitacao(idSolicitacao);
        verificar.verificaIdSessao(iDSessao);

        NegociacaoDePonto solicitacao = this.getSolicitacaoPendentePorId(idSolicitacao);
        if (solicitacao == null) {
            throw new MeLevaException(
                    MSGDeExcecao.MSG_ERRO_SOLICITACAO_INEXISTENTE);
        }
        // faco isso pois naum posso remover do solicitacoes pendentes ainda
        if (solicitacoesConfirmadas.containsValue(solicitacao)) {
            throw new MeLevaException(
                    MSGDeExcecao.MSG_ERRO_SOLICITACAO_INEXISTENTE);
        }

        ControleDeUsuarios rep = ControleDeUsuarios.getInstance();
        Usuario usuarioDonoDaSolicitacao = rep.getUsuarioId(solicitacao.getUsuario().getIdUsuario());
        AbstractCarona carona = rep.getCarona(solicitacao.getCarona().getId());

        usuarioDonoDaSolicitacao.addViagemConfirmada(carona);

        if (rep.getDono(carona.getId()).getIdUsuario().equals(iDSessao)) {
            carona.preencheVagas();
            solicitacoesPendentes.remove(carona.getId());
            solicitacoesConfirmadas.put(iDSessao, solicitacao);
        } else {
            throw new MeLevaException(
                    MSGDeExcecao.MSG_ERRO_SOLICITACAO_INEXISTENTE);
        }

    }

    /**
     * Método que remove a solicitação.
     *
     * @param idSessao - Identificador da sessão do usuário.
     * @param idSolicitacao - Identificador da Solicitação.
     * @param negociacoes - Negociação do ponto.
     * @throws MeLevaException - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public void rejeitaSolicitacao(String idSessao, String idSolicitacao,
            Negociacao negociacoes) throws MeLevaException {
        NegociacaoDePonto solicitacao = negociacoes.getSolicitacaoPendentePorId(idSolicitacao);
        if (solicitacao == null) {
            throw new MeLevaException(
                    MSGDeExcecao.MSG_ERRO_SOLICITACAO_INEXISTENTE);
        }

        getSolicitacoesPendentes().values().remove(solicitacao);

    }

    /**
     * Método para usuário desistir da requisição d uma vaga na carona.
     *
     * @param iDSessao - Identificador de sessão do usuário.
     * @param iDCarona - Identificador da carona.
     * @param iDSugestao - Identificador da sugestão.
     * @throws MeLevaException - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public void desistirRequisicao(String iDSessao, String iDCarona,
            String iDSugestao) throws MeLevaException {
        Iterator<NegociacaoDePonto> sugestaoIt = solicitacoesConfirmadas.values().iterator();

        NegociacaoDePonto sugestaRemover = null, nextSugestao = null;
        while (sugestaoIt.hasNext()) {
            nextSugestao = sugestaoIt.next();
            if (String.valueOf(nextSugestao.getCarona().getId()).equals(
                    nextSugestao.getCarona().getId())
                    && String.valueOf(nextSugestao.getUsuario().getIdUsuario()).equals(nextSugestao.getUsuario().getIdUsuario())) {
                sugestaRemover = nextSugestao;
                break;
            }
        }

        solicitacoesConfirmadas.values().remove(sugestaRemover);

    }

    /**
     * Método que acessa o atributo da solicitação.
     *
     * @param idSolicitacao - Identificador da solicitação.
     * @param atributo - Atributo a ser retornado.
     * @return String do atributo da solicitação.
     * @throws MeLevaException - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public String getAtributoSolicitacao(String idSolicitacao, String atributo)
            throws MeLevaException {
        verificar.verificaAtributo(atributo);
        verificar.verificaIdSolicitacao(idSolicitacao);

        // solicitacao para retornar o atributo da mesma
        NegociacaoDePonto solicitacao = getSolicitacaoPorId(idSolicitacao);

        // carona que tem a solicitacao
        AbstractCarona carona = ControleDeUsuarios.getInstance().getCarona(
                solicitacao.getCarona().getId());

        // dono da carona
        Usuario usuarioDonoCarona = ControleDeUsuarios.getInstance().getDono(
                solicitacao.getCarona().getId());

        // dono da solicitacao
        Usuario usuarioDonoSolicitacao = getUsuarioDaSolicitacao(solicitacao);

        String resp = null;

        if (AtributosDeSolicitacao.ORIGEM.getAtributo().equalsIgnoreCase(
                atributo)) {
            resp = carona.getOrigem();
        } else if (AtributosDeSolicitacao.DESTINO.getAtributo().equalsIgnoreCase(atributo)) {
            resp = carona.getDestino();
        } else if (AtributosDeSolicitacao.DONO_DA_CARONA.getAtributo().equalsIgnoreCase(atributo)) {
            resp = usuarioDonoCarona.getNome();
        } else if (AtributosDeSolicitacao.DONO_DA_SOLICITACAO.getAtributo().equalsIgnoreCase(atributo)) {
            resp = usuarioDonoSolicitacao.getNome();
        } else if (AtributosDeSolicitacao.PONTO_DE_DESTINO.getAtributo().equalsIgnoreCase(atributo)) {
            resp = solicitacao.getPontosDeEncontro().toString().replace("[", "").replace("]", "");
        } else {
            throw new MeLevaException(
                    MSGDeExcecao.MSG_ERRO_ATRIBUTO_INEXISTENTE);

        }

        return resp;
    }

    /**
     * Método para acessar os pontos de encontro da carona.
     *
     * @param idCarona - Identificador da carona.
     * @return Pontos de encontro.
     * @throws MeLevaException - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public String getPontosDeEmbarque(String idCarona) throws MeLevaException {
        Iterator<NegociacaoDePonto> sugestaoIt = solicitacoesConfirmadas.values().iterator();

        NegociacaoDePonto sugesta = null, nextSugestao = null;
        while (sugestaoIt.hasNext()) {
            nextSugestao = sugestaoIt.next();
            if (String.valueOf(nextSugestao.getCarona().getId()).equals(
                    nextSugestao.getCarona().getId())) {
                if (idCarona.equals(nextSugestao.getCarona().getId())) {
                    sugesta = nextSugestao;
                }

                break;
            }
        }
        return sugesta.getPontos();
    }

    /**
     * Método que acessa as solicitação específica do usuário.
     *
     * @param iDSessao - Identificador da sessão do usuário.
     * @param iDCarona - Identificador da carona.
     * @return Lista de pontos de encontro.
     * @throws MeLevaException - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public List<String> getPontosSugeridos(String iDSessao, String iDCarona)
            throws MeLevaException {
        NegociacaoDePonto pontosSugeridos = null;
        Iterator<NegociacaoDePonto> iterador = sugestoesPontoDeEncontro.values().iterator();
        while (iterador.hasNext()) {
            NegociacaoDePonto negociacao = iterador.next();
            if (negociacao.getCarona().getId().equals(iDCarona)) {
                pontosSugeridos = negociacao;
                break;
            }
        }
        if (pontosSugeridos == null) {
            throw new MeLevaException(MSGDeExcecao.MSG_ERRO_CARONA_INEXISTENTE);
        }
        return pontosSugeridos.getPontosDeEncontro();

    }

    /**
     * Método que acessa os pontos de encontro entre usuário e carona.
     *
     * @param iDSessao - Identificador da sessão do usuário.
     * @param iDCarona - Identificador da carona.
     * @return Lista de pontos sugerios entre os negociantes.
     * @throws MeLevaException - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public List<String> getPontosEncontro(String iDSessao, String iDCarona)
            throws MeLevaException {
        return getPontosSugeridos(iDSessao, iDCarona);
    }

    /**
     * Método que acessa o usuário da solicitação.
     *
     * @param solicitacao - Solicitação do usuário.
     * @return Usuário que solicitou.
     */
    public Usuario getUsuarioDaSolicitacao(NegociacaoDePonto solicitacao) {

        Iterator<Usuario> iteradorUsuarios = ControleDeUsuarios.getInstance().getUsuarios().iterator();
        Usuario usuarioDonoSolicitacao = null;
        while (iteradorUsuarios.hasNext()) {
            Usuario usuario = iteradorUsuarios.next();
            if (usuario.getIdUsuario().equals(
                    solicitacao.getUsuario().getIdUsuario())) {
                usuarioDonoSolicitacao = usuario;
                break;
            }
        }

        return usuarioDonoSolicitacao;
    }

    /**
     * Método que acessa a negociação do ponto de encontro.
     *
     * @param idSolicitacao - Identificador de solicitação.
     * @return Negociação do ponto de encontro.
     * @throws MeLevaException - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public NegociacaoDePonto getSolicitacaoPorId(String idSolicitacao)
            throws MeLevaException {
        verificar.verificaIdSolicitacao(idSolicitacao);
        Iterator<NegociacaoDePonto> solicitacoesIt = solicitacoesDeViagem.iterator();
        NegociacaoDePonto solicitacao = null;
        while (solicitacoesIt.hasNext()) {
            NegociacaoDePonto nextSolicitacao = solicitacoesIt.next();
            // mudo getIsolici para sugest
            if (idSolicitacao.equals(String.valueOf(nextSolicitacao.getIdSugestao()))) {
                solicitacao = nextSolicitacao;
                break;
            }
            if (idSolicitacao.equals(String.valueOf(nextSolicitacao.getIdSugestao()))) {
                solicitacao = nextSolicitacao;
                break;
            }
        }

        return solicitacao;
    }

    /**
     * Método que acessa a negociação de pontos pendentes pelo identificador.
     *
     * @param idSolicitacao - Identificador da solicitação.
     * @return negociação de ponto de encontro.
     */
    public NegociacaoDePonto getSolicitacaoPendentePorId(String idSolicitacao) {

        Iterator<NegociacaoDePonto> solicitacoesIt = getSolicitacoesPendentes().values().iterator();
        NegociacaoDePonto solicitacao = null;
        while (solicitacoesIt.hasNext()) {
            NegociacaoDePonto nextSolicitacao = solicitacoesIt.next();
            // mudou aki
            if (idSolicitacao.equals(String.valueOf(nextSolicitacao.getIdSugestao()))) {
                solicitacao = nextSolicitacao;
                break;
            }
        }
        return solicitacao;
    }

    /**
     * Método que acessa as solicições confrimadas pelos usuários.
     *
     * @return Mapa de solicitações confrimadas de ponto de encontro.
     */
    public Map<String, NegociacaoDePonto> getSolicitacoesConfirmadas() {
        return solicitacoesConfirmadas;
    }

    /**
     * Método que acessa as solicitação pendentes dos usuários.
     *
     * @return Mapa de solicitações pendentes de ponto de encontro.
     */
    public Map<String, NegociacaoDePonto> getSolicitacoesPendentes() {
        return solicitacoesPendentes;
    }

    /**
     * Método que acessa as solicitações confirmadas pelo Identificador.
     *
     * @param idSolicitacao - Identificador da solicitação.
     * @return Negociação de ponto de encontro.
     */
    public NegociacaoDePonto getSolicitacoesConfirmadasPoId(String idSolicitacao) {
        Iterator<NegociacaoDePonto> solicitacoesIt = getSolicitacoesConfirmadas().values().iterator();
        NegociacaoDePonto solicitacao = null;
        while (solicitacoesIt.hasNext()) {
            NegociacaoDePonto nextSolicitacao = solicitacoesIt.next();
            // mudou aki
            if (idSolicitacao.equals(String.valueOf(nextSolicitacao.getIdSugestao()))) {
                solicitacao = nextSolicitacao;
                break;
            }
        }
        return solicitacao;
    }
}
