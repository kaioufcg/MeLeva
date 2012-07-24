package br.edu.ufcg.projetomelevamavem.controller;

import br.edu.ufcg.projetomelevamavem.logicaCarona.AbstractCarona;
import br.edu.ufcg.projetomelevamavem.logicaCarona.Carona;
import br.edu.ufcg.projetomelevamavem.logicaNegociacao.Negociacao;
import br.edu.ufcg.projetomelevamavem.logicaSistema.*;
import br.edu.ufcg.projetomelevamavem.logicaUsuario.Usuario;
import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

/**
 * Classe que controla o sistema de caronas Me Leva
 *
 * @author Grupo Me Leva - Projeto SI1 - UFCG 2012.1
 *
 */
public class MeLevaController implements Serializable {

    private static final long serialVersionUID = 00000000014L;
    private volatile static MeLevaController unicaInstancia;
    // Contrala os usuarios cadastrados
    private ControleDeUsuarios controleUsuarios;
    // Contrala os interesses em caronas dos usuarios cadastrados
    private ControleDeInteresse controleInteresse;
    // Negociacoes
    private Negociacao negociacoes;
    private ControleDePersistenciaDeDados gerenciaDados;
    // Objeto para verificar se as entradas estão corretas
    private Verificador verificador;

    /**
     * Construtor da carona.
     */
    private MeLevaController() {
        controleUsuarios = ControleDeUsuarios.getInstance();
        negociacoes = new Negociacao();
        verificador = new Verificador();
        gerenciaDados = ControleDePersistenciaDeDados.getInstance();
        controleInteresse = ControleDeInteresse.getInstance();
    }

    public static MeLevaController getInstance() {
        if (unicaInstancia == null) {
            synchronized (ControleDeUsuarios.class) {
                if (unicaInstancia == null) {
                    unicaInstancia = new MeLevaController();
                }
            }
        }
        return unicaInstancia;

    }

    /**
     * Método que zera os dados do sistema.
     */
    public void zerarSistema() {

        controleUsuarios.clear();
        negociacoes.clear();
        gerenciaDados.clear();
        controleInteresse.clear();
    }

    /**
     * Método que cria um usuário no sistema.
     *
     * @param login - Login do usuário.
     * @param senha - Senha do usuário.
     * @param nome - Nome do usuário.
     * @param endereco - Endereço do usuário.
     * @param email - Email do usuário.
     * @return String do identificador do usuario.
     * @throws Exception - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public String criarUsuario(String login, String senha, String nome,
            String endereco, String email) throws MeLevaException {

        verificador.verificaLogin(login);
        verificador.verificaLoginExistente(login);

        verificador.verificaSenha(senha);

        verificador.verificaNome(nome);

        verificador.verificaEndereco(endereco);

        verificador.verificaEmail(email);
        verificador.verificaEmailExistente(email);

        Usuario usuario = new Usuario(login, senha, nome, endereco, email);
        controleUsuarios.addUsuario(usuario);
        return usuario.getIdUsuario();

    }

    /**
     * Método que cadastra as caronas ofertadas pelos usuários.
     *
     * @param idSessao - Identificador da sessão do usuário.
     * @param origem - Origem da saída da carona.
     * @param destino - Destino da carona ofertada.
     * @param data - Data de saída.
     * @param hora - Horário de saída.
     * @param vagas - Numero de vagas ofertadas.
     * @return Identificador da carona ofertada.
     * @throws MeLevaException - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public int cadastrarCarona(String idSessao, String origem, String destino,
            String data, String hora, String vagas) throws MeLevaException {
        verificador.verificaIdSessao(idSessao);

        verificador.verificaSessaoInexistente(
                controleUsuarios.getSessoesAbertas(), idSessao);

        verificador.verificaOrigem(origem);

        verificador.verificaDestino(destino);

        verificador.verificaOrigemDestinoVazio(origem, destino);

        verificador.verificaData(data, hora);

        verificador.verificaVagas(vagas);

        Usuario usuario = controleUsuarios.getSessoesAbertas().get(idSessao);

        verificador.verificaUsuarioInexistente(usuario);

        return usuario.cadastraCarona(origem, destino, data, hora,
                Integer.valueOf(vagas));

    }

    /**
     * Método que localiza a carona.
     *
     * @param idSessao - Identificador da sessão usada.
     * @param origem - Origem da carona.
     * @param destino - Destino da carona.
     * @return String da lista de caronas de origem e destino especificados.
     * @throws MeLevaException - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public String localizarCarona(String idSessao, String origem, String destino)
            throws MeLevaException {
        verificador.verificaIdSessao(idSessao);
        verificador.verificaOrigem(origem);
        verificador.verificaDestino(destino);
        List<String> axi = new LinkedList<String>();
        Iterator<Usuario> userIt = controleUsuarios.getSessoesAbertas().values().iterator();

        while (userIt.hasNext()) {
            Usuario usuario = (Usuario) userIt.next();
            for (String carona : usuario.localizaCarona(origem, destino)) {
                axi.add(carona);
            }

        }

        return axi.toString().replace("[", "{").replace("]", "}").replace(" ", "");
    }

    /**
     * Método que localiza a carona Municipal.
     *
     * @param idSessao - Identificador do usuário.
     * @param cidade - Cidade da carona municipal.
     * @param origem - Origem da carona municipal.
     * @param destino - Destino da carona municipal.
     * @return String da carona de origem e destino especificados.
     * @throws MeLevaException - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public String localizarCaronaMunicipal(String idSessao, String cidade,
            String origem, String destino) throws MeLevaException {

        verificador.verificaIdSessao(idSessao);
        verificador.verificaCidade(cidade);
        verificador.verificaOrigem(origem);
        verificador.verificaDestino(destino);

        AbstractSet<String> axi = new TreeSet<String>();
        Iterator<Usuario> userIt = controleUsuarios.getSessoesAbertas().values().iterator();

        while (userIt.hasNext()) {
            Usuario usuario = (Usuario) userIt.next();
            for (String carona : usuario.localizaCaronaMunicipal(origem,
                    destino, cidade)) {
                axi.add(carona);
            }

        }

        return axi.toString().replace("[", "{").replace("]", "}").replace(" ", "");
    }

    /**
     * Método que localiza a carona Municipal.
     *
     * @param idSessao - Identificador do usuário.
     * @param cidade - Cidade da carona municipal.
     * @return String da carona de origem e destino especificados.
     * @throws MeLevaException - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public String localizarCaronaMunicipal(String idSessao, String cidade)
            throws MeLevaException {
        verificador.verificaIdSessao(idSessao);

        AbstractSet<String> axi = new TreeSet<String>();
        Iterator<Usuario> userIt = controleUsuarios.getSessoesAbertas().values().iterator();

        while (userIt.hasNext()) {
            Usuario usuario = (Usuario) userIt.next();
            for (String carona : usuario.localizaCaronaMunicipal(cidade)) {
                axi.add(carona);
            }

        }

        return axi.toString().replace("[", "{").replace("]", "}").replace(" ", "");
    }

    /**
     * Método que cadastra as caronas municipais ofertadas pelos usuários.
     *
     * @param idSessao - Identificador da sessão do usuário.
     * @param origem - Origem da saída da carona.
     * @param destino - Destino da carona ofertada.
     * @param cidade - Cidade.
     * @param data - Data de saída.
     * @param hora - Horário de saída.
     * @param vagas - Numero de vagas ofertadas.
     * @return Identificador da carona ofertada.
     * @throws Exception - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public int cadastrarCaronaMunicipal(String idSessao, String origem,
            String destino, String cidade, String data, String hora,
            String vagas) throws Exception {
        verificador.verificaIdSessao(idSessao);

        verificador.verificaSessaoInexistente(
                controleUsuarios.getSessoesAbertas(), idSessao);

        verificador.verificaOrigem(origem);

        verificador.verificaDestino(destino);

        verificador.verificaOrigemDestinoVazio(origem, destino);

        verificador.verificaData(data, hora);

        verificador.verificaVagas(vagas);

        Usuario usuario = controleUsuarios.getSessoesAbertas().get(idSessao);

        verificador.verificaUsuarioInexistente(usuario);

        return usuario.cadastraCarona(origem, destino, cidade, data, hora,
                Integer.valueOf(vagas));

    }

    /**
     * Método que cadastra os interesses de um usuario nas caronas ofertadas
     * pelos usuários.
     *
     * @param iDSessao - Identificador da sessão do usuário.
     * @param origem - Origem de interesse de uma carona ofertada.
     * @param destino - Destino de interesse de uma carona ofertada.
     * @param data - Data de interesse de uma carona ofertada.
     * @param horaInicio - Horário inicial de espera por um carona.
     * @param horaFim - Horário final de espera por um carona.
     * @return
     * @throws MeLevaException - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public int cadastrarInteresse(String iDSessao, String origem,
            String destino, String data, String horaInicio, String horaFim)
            throws MeLevaException {
        Usuario user = controleUsuarios.getUsuarioId(iDSessao);

        return controleInteresse.addInteresse(user, origem, destino, data,
                horaInicio, horaFim);
    }

    /**
     * Método que adiciona o usuário para utilizar os recursos do sistema.
     *
     * @param login - Login do usuário cadastrado.
     * @param senha - Senha do Usuário
     * @return String do identificador da sessão aberta pelo usuário.
     * @throws Exception - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public String abrirSessao(String login, String senha) throws Exception {

        verificador.verificaSenha(senha);

        return controleUsuarios.putSessoesAbertas(login, senha);
    }

    /**
     * Método que encerra a sessao do Usuario logado no sistema.
     *
     * @param login - Login do Usuario.
     * @return Boolean de verificação de remoção confirmada.
     * @throws MeLevaException - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public boolean encerrarSessao(String login) throws MeLevaException {

        return controleUsuarios.removeSessao(login);

    }

    /**
     * Método que ancerra o sistema.
     */
    public void encerrarSistema() {
        controleUsuarios.getSessoesAbertas().clear();
        gerenciaDados.salvaUsuarios(controleUsuarios.getUsuariosCadastradosPorLogin());
        gerenciaDados.salvaNegociacoes(negociacoes);
        gerenciaDados.salvaInteresses(controleInteresse.getInteressesCadastrados());
        controleUsuarios.clear();
        negociacoes.clear();
        controleInteresse.clear();
    }

    /**
     * Método que reinicia o sistema.
     *
     * @throws Exception - Exceções para erros de I/O.
     */
    public void reiniciarSistema() throws Exception {
        controleUsuarios.setUsuariosCadastradosPorLogin(gerenciaDados.leUsuarios());
        negociacoes = gerenciaDados.leNegociacoes();
        controleInteresse.setInteressesCadastrados(gerenciaDados.leInteresses());

    }

    /**
     * Método para sair do sistema.
     */
    public void quit() {
        System.exit(0);
    }

    /**
     * Método que acessa o atributo de uma carona.
     *
     * @param idCarona - Identificador da carona.
     * @param atributo - Atributo a ser retornado.
     * @return String do atributo da carona a ser retornado.
     * @throws MeLevaException - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    /**
     * @param idCarona
     * @param atributo
     * @return
     * @throws MeLevaException
     */
    public String getAtributoCarona(String idCarona, String atributo)
            throws MeLevaException {

        verificador.verificaIdCarona(idCarona);

        AbstractCarona carona = controleUsuarios.getCarona(idCarona);

        if (carona == null) {
            throw new MeLevaException(MSGDeExcecao.MSG_ERRO_ITEM_INEXISTENTE);
        }

        return carona.getAtributo(atributo, negociacoes);
    }

    /**
     * Método que acessa o atributo usuário.
     *
     * @param login - Login do usuário.
     * @param atributo - Atributo do usuário.
     * @return String que representa o atributo do usuário.
     * @throws Exception - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public String getAtributoUsuario(String login, String atributo)
            throws Exception {

        Usuario usuario = controleUsuarios.getUsuarioLogin(login);

        return usuario.getAtributoUsuario(atributo);
    }

    /**
     * Método que acessa o atributo do perfil do usuário.
     *
     * @param login - Login do usuário.
     * @param atributo - Atributo do perfil do usuário.
     * @return String que representa o atributo do perfil do usuário.
     * @throws Exception - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public String getAtributoPerfil(String login, String atributo)
            throws Exception {

        return controleUsuarios.getAtributoPerfil(login, atributo);

    }

    /**
     * Método de acesso aos atributos da solicitação.
     *
     * @param idSolicitacao - Identificador da Solicitação.
     * @param atributo - Atributo a ser retornado.
     * @return String que representa o atributo da solicitacao.
     * @throws Exception - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public String getAtributoSolicitacao(String idSolicitacao, String atributo)
            throws Exception {
        return negociacoes.getAtributoSolicitacao(idSolicitacao, atributo);
    }

    /**
     * Método que acessa o atributo do perfil do usuário.
     *
     * @param idCarona - Identificador da carona.
     * @return String representando o trajeto da carona.
     * @throws MeLevaException - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public String getTrajeto(String idCarona) throws MeLevaException {

        verificador.verificaTrajetoCarona(idCarona);

        AbstractCarona carona = controleUsuarios.getCarona(idCarona);
        if (carona == null) {
            throw new MeLevaException(MSGDeExcecao.MSG_ERRO_TRAJETO_INEXISTENTE);
        }
        return carona.getTrajeto();
    }

    /**
     * Método que acessa uma string representando as informações da carona.
     *
     * @param idCarona - Identificador da carona.
     * @return String representando as infromações da carona.
     * @throws MeLevaException - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public String getCarona(String idCarona) throws MeLevaException {
        verificador.verificaGetCarona(idCarona);

        AbstractCarona carona = controleUsuarios.getCarona(idCarona);
        if (carona == null) {
            throw new MeLevaException(MSGDeExcecao.MSG_ERRO_CARONA_INEXISTENTE);
        }
        return carona.getCarona();
    }

    /**
     * Método para acessar a carona do usuário.
     *
     * @param iDSessao - Identificador da sessão do usuário.
     * @param index - Indice da carona oferecida pelo usuario.
     * @return Identificador da carona do usuário.
     * @throws MeLevaException - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public String getCaronaUsuario(String iDSessao, int index)
            throws MeLevaException {
        return controleUsuarios.getUsuarioId(iDSessao).getHistoricoCaronas().get(index - 1).getId();
    }

    /**
     * Método que acessa todas as caronas oferecidas pelo usuário.
     *
     * @param iDSessao -Identificador da sessão do usuário.
     * @return Lista com todas as caronas do usuário.
     * @throws MeLevaException - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public String getTodasCaronasUsuario(String iDSessao)
            throws MeLevaException {
        Iterator<AbstractCarona> caronas = controleUsuarios.getUsuarioId(iDSessao).getHistoricoCaronas().iterator();
        LinkedList<String> result = new LinkedList<String>();
        while (caronas.hasNext()) {
            Carona carona = (Carona) caronas.next();
            result.add(carona.getId());
        }

        return Arrays.toString(result.toArray()).replace("[", "{").replace("]", "}").replace(" ", "");
    }

    /**
     * Método que acessa os pontos sugeridos pelo usuário.
     *
     * @param iDSessao - Identificador da essão do usuário.
     * @param iDCarona - Identificador da carona.
     * @return Lista das sugestões dos usuários.
     * @throws MeLevaException - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public String getPontosSugeridos(String iDSessao, String iDCarona)
            throws MeLevaException {
        return addArray(negociacoes.getPontosSugeridos(iDSessao, iDCarona));
    }

    /**
     * Método que acessa os pontos de encontro na negociacao entre o usuário e a
     * carona.
     *
     * @param iDSessao - Identificador da sessão do usuário.
     * @param iDCarona - Identificador da carona.
     * @return Pontos de encontro.
     * @throws MeLevaException - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public String getPontosEncontro(String iDSessao, String iDCarona)
            throws MeLevaException {
        return addArray(negociacoes.getPontosEncontro(iDSessao, iDCarona));
    }

    /**
     * Método para usuário sugerir um ponto de encontro com o caroneiro.
     *
     * @param idSessao - Identificador da sessão do usuário.
     * @param idCarona - Identificador da carona.
     * @param pontos - Pontos de encontro sugeridos.
     * @return Identificador da sugestão.
     * @throws Exception - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public int sugerirPontoEncontro(String idSessao, String idCarona,
            String pontos) throws Exception {

        return negociacoes.addSugestaoPontoDeEncontro(idSessao, idCarona,
                pontos);
    }

    /**
     * Método para acessar as Solicitações que foram confirmadas pelo usuário
     * dono das caronas.
     *
     * @param iDSessao - Identificador da Sessão do usuário.
     * @param idCarona - Identificador da carona.
     * @return Lista da confirmação confirmadas pelo usuário.
     * @throws MeLevaException - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public String getSolicitacoesConfirmadas(String iDSessao, String idCarona)
            throws MeLevaException {
        List<Integer> listaSolicitacoesConfirmadas = new LinkedList<Integer>();
        if (!negociacoes.getSolicitacoesConfirmadas().isEmpty()) {
            listaSolicitacoesConfirmadas.add(negociacoes.getSolicitacoesConfirmadas().get(iDSessao).getIdSugestao());
        }
        return Arrays.toString(listaSolicitacoesConfirmadas.toArray()).replace("[", "{").replace("]", "}").replace(" ", "");
    }

    /**
     * Método para acessar as Solicitações pendentes do usuário dono das
     * caronas.
     *
     * @param iDSessao - Identificador da Sessão do usuário.
     * @param idCarona - Identificador da carona.
     * @return Lista de pendencias do usuário.
     * @throws MeLevaException - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public String getSolicitacoesPendentes(String iDSessao, String idCarona)
            throws MeLevaException {
        List<Integer> listaSolicitacoesPendentes = new LinkedList<Integer>();
        if (!negociacoes.getSolicitacoesPendentes().isEmpty()) {
            listaSolicitacoesPendentes.add(negociacoes.getSolicitacoesPendentes().get(idCarona).getIdSugestao());
        }
        return Arrays.toString(listaSolicitacoesPendentes.toArray()).replace("[", "{").replace("]", "}").replace(" ", "");
    }

    /**
     * Método para usuário pedir uma vaga com o caroneiro.
     *
     * @param iDSessao - Identificador da sessão do usuário.
     * @param iDCarona - Identificador da carona.
     * @return Identificador da solicitação da vaga.
     * @throws MeLevaException - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public int solicitarVaga(String iDSessao, String iDCarona)
            throws MeLevaException {
        return negociacoes.addSolicitacaoParaViajar(iDSessao, iDCarona, "");
    }

    /**
     * Método para usuário pedir uma vaga no ponto de encontro com o caroneiro.
     *
     * @param iDSessao - Identificador da sessão do usuário.
     * @param iDCarona - Identificador da carona.
     * @param ponto - Pontos de encontro sugeridos.
     * @return Identificador da solicitação da vaga.
     * @throws MeLevaException - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public int solicitarVagaPontoEncontro(String iDSessao, String iDCarona,
            String ponto) throws MeLevaException {
        return negociacoes.addSolicitacaoParaViajar(iDSessao, iDCarona, ponto);
    }

    /**
     * Método para o caroneiro responder a sugestão de um ponto de encontro com
     * o carona.
     *
     * @param iDSessao - Identificador da sessão do usuário.
     * @param idCarona - Identificador da carona.
     * @param idSugestao - Identificador da Sugestão.
     * @param pontos - Pontos de encontro sugeridos.
     * @return Identificador da resposta da sugestão.
     * @throws MeLevaException - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public int responderSugestaoPontoEncontro(String iDSessao, String idCarona,
            String idSugestao, String pontos) throws MeLevaException {

        return negociacoes.addRespostaPontoDeEncontro(iDSessao, idCarona,
                pontos);
    }

    /**
     * Método para o caroneiro seder uma vaga ao solicitante na viagem naquele
     * ponto.
     *
     * @param idSessao - Identificador da sessão do usuário.
     * @param idSolicitacao -Identificador da solicitação.
     * @throws MeLevaException - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public void aceitarSolicitacaoPontoEncontro(String idSessao,
            String idSolicitacao) throws MeLevaException {
        negociacoes.aceitarSolicitacaoPontoEncontro(idSessao, idSolicitacao);
    }

    /**
     * Método para o caroneiro seder uma vaga ao solicitante na viagem.
     *
     * @param idSessao - Identificador da sessão do usuário.
     * @param idSolicitacao -Identificador da solicitação.
     * @throws MeLevaException - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public void aceitarSolicitacao(String idSessao, String idSolicitacao)
            throws Exception {
        aceitarSolicitacaoPontoEncontro(idSessao, idSolicitacao);
    }

    /**
     * Método para o carona da vaga desista de embarcar.
     *
     * @param idSessao - Identificador da sessão do usuário.
     * @param idCarona - Identificador da Carona.
     * @param idSugestao - Identificador da Sugestão.
     * @throws MeLevaException - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public void desistirRequisicao(String idSessao, String idCarona,
            String idSugestao) throws MeLevaException {
        negociacoes.desistirRequisicao(idSessao, idCarona, idSugestao);
    }

    /**
     * Método para o solicitante da vaga desista da solicitação.
     *
     * @param idSessao - Identificador da sessão do usuário.
     * @param idSolicitacao - Identificador da Solicitação.
     * @throws MeLevaException - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public void rejeitarSolicitacao(String idSessao, String idSolicitacao)
            throws MeLevaException {
        negociacoes.rejeitaSolicitacao(idSessao, idSolicitacao, negociacoes);

    }

    /**
     * Método para vizualização do perfil do usuário.
     *
     * @param iDSessao - Identificador da Sessão do Usuário.
     * @param login - Login do usuário.
     * @return String representando o perfil do usuário.
     * @throws MeLevaException - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public String visualizarPerfil(String iDSessao, String login)
            throws MeLevaException {
        return controleUsuarios.visualizarPerfil(iDSessao, login);
    }

    /**
     * Método para revisar a situação do caroneiro que cedeu vaga na viagem.
     *
     * @param iDSessao - Identificdor da sessão do usuário.
     * @param iDCarona - Indetificador da carona.
     * @param review - Situação do caroneiro que cedeu vagas na viagem.
     * @throws MeLevaException - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public void reviewCarona(String iDSessao, String iDCarona, String review)
            throws MeLevaException {

        Usuario user = controleUsuarios.getUsuarioId(iDSessao);
        verificador.verificaUsuarioInexistente(user);
        verificador.verificaCaronaPedida(user, iDCarona);

        Usuario userDono = controleUsuarios.getDono(iDCarona);

        if (userDono == null) {
            throw new MeLevaException(
                    MSGDeExcecao.MSG_ERRO_USUARIO_NAO_DEU_CARONA);
        }

        userDono.reviewCarona(review);

    }

    /**
     * Método para revisar a situação da vaga do carona na viagem.
     *
     * @param iDSessao - Identificador da sessão do usuário.
     * @param idCarona - Idenficador da carona.
     * @param loginCaroneiro - login do Caroneiro.
     * @param review - Situação da vaga na viagem.
     * @throws Exception - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public void reviewVagaEmCarona(String iDSessao, String idCarona,
            String loginCaroneiro, String review) throws Exception {

        Usuario user = controleUsuarios.getUsuarioId(iDSessao);
        verificador.verificaUsuarioInexistente(user);
        verificador.verificaCaronaOferecida(user, idCarona);
        Usuario userReview = controleUsuarios.getUsuarioLogin(loginCaroneiro);

        Iterator<AbstractCarona> caronaPedidaPeloCaroneiro = userReview.getCaronasPedidas().iterator();
        boolean caronaFoiPedida = false;
        while (caronaPedidaPeloCaroneiro.hasNext()) {
            Carona carona = (Carona) caronaPedidaPeloCaroneiro.next();
            if (String.valueOf(carona.getId()).equals(idCarona)) {
                caronaFoiPedida = true;
                break;
            }

        }
        if (!caronaFoiPedida) {
            throw new MeLevaException(
                    MSGDeExcecao.MSG_ERRO_USUARIO_NAO_POSSUI_VAGA_NA_CARONA);
        }

        userReview.reviewVagaEmCarona(review);

    }

    /**
     * Método auxiliar para transformação da lista em string.
     *
     * @param pontosSugeridos - Pontos sugeridos pelo usuário.
     * @return String da lista de pontos.
     */
    private String addArray(List<String> pontosSugeridos) {
        String string = "[";

        Iterator<String> iterador = pontosSugeridos.iterator();
        while (iterador.hasNext()) {
            string += iterador.next() + ";";
        }
        string = string.substring(0, string.length() - 1) + "]";
        return string;
    }

    /**
     * Método para usuário enviar e-mails para um determinado usuário de
     * destino.
     *
     * @param iDSessao - Identificador da sessão do usuário.
     * @param destino - Destino a ser enviado o e-mail.
     * @param message - Menssagem a ser enviada ou destinatario.
     * @return Boolean de verificação se o envio foi correto.
     * @throws MeLevaException - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public boolean enviarEmail(String iDSessao, String destino, String message)
            throws MeLevaException {

        Usuario usuario = controleUsuarios.getUsuarioId(iDSessao);

        return usuario.enviarEmail(usuario, destino, message);
    }

    /**
     * Método para visualizar as mensagens do Perfil.
     *
     * @param iDSessao - Identificador da sessão do usuário.
     * @return Lista de Mensagens.
     * @throws MeLevaException - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public String verificarMensagensPerfil(String iDSessao)
            throws MeLevaException {
        Usuario usuario = controleUsuarios.getUsuarioId(iDSessao);
        return usuario.getMensagens().toString();
    }
}
