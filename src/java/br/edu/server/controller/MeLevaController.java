package br.edu.server.controller;

import br.edu.client.utilitarios.MSGDeExcecao;
import br.edu.client.utilitarios.MeLevaException;
import br.edu.server.logicaCarona.AbstractCarona;
import br.edu.server.logicaCarona.Carona;
import br.edu.server.logicaNegociacao.Negociacao;
import br.edu.server.logicaNegociacao.NegociacaoDePonto;
import br.edu.server.logicaSistema.*;
import br.edu.server.logicaUsuario.Usuario;
import java.io.Serializable;
import java.util.*;

/**
 * Classe que controla o sistema de caronas Me Leva
 *
 * @author Grupo Me Leva - Projeto SI1 - UFCG 2012.1
 *
 */
public class MeLevaController implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -2760886711812584493L;
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
     * @throws MeLevaException - Exceções para o caso dos dados inseridos sejam
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
        gerenciaDados.salvaUsuarios(controleUsuarios.getUsuariosCadastradosPorLogin());

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
        int id = usuario.cadastraCarona(origem, destino, data, hora,
                Integer.valueOf(vagas));
        gerenciaDados.salvaUsuarios(controleUsuarios.getUsuariosCadastradosPorLogin());
        gerenciaDados.salvaCaronasEmJson(controleUsuarios.getUsuariosCadastradosPorLogin(), controleUsuarios);
         
        //gerenciaDados.salvaPedidosCaronaEmJson(negociacoes.getSolicitacoesPendentes(), controleUsuarios);
        //dados.setNewRecords(origem, destino, data, hora, vagas, hora);
        //gerenciaDados.salvaDadosGridUsuario(usuario.getNome(), getSolicitacoesPendentes(idSessao));
        //gerenciaDados.carregaDadosGrid(controleUsuarios.getUsuariosCadastradosPorLogin());
        return id;

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
     * @throws MeLevaException - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public int cadastrarCaronaMunicipal(String idSessao, String origem,
            String destino, String cidade, String data, String hora,
            String vagas) throws MeLevaException {
        verificador.verificaIdSessao(idSessao);

        verificador.verificaSessaoInexistente(
                controleUsuarios.getSessoesAbertas(), idSessao);

        verificador.verificaOrigem(origem);

        verificador.verificaDestino(destino);

        verificador.verificaOrigemDestinoVazio(origem, destino);

        verificador.verificaCidade(cidade);

        verificador.verificaData(data, hora);

        verificador.verificaVagas(vagas);

        Usuario usuario = controleUsuarios.getSessoesAbertas().get(idSessao);

        verificador.verificaUsuarioInexistente(usuario);

        int id = usuario.cadastraCarona(origem, destino, cidade, data, hora,
                Integer.valueOf(vagas));
        gerenciaDados.salvaUsuarios(controleUsuarios.getUsuariosCadastradosPorLogin());
        gerenciaDados.salvaCaronasEmJson(controleUsuarios.getUsuariosCadastradosPorLogin(), controleUsuarios);
        //gerenciaDados.salvaPedidosCaronaEmJson(negociacoes.getSolicitacoesPendentes(), controleUsuarios);
        //dados.setNewRecords(origem, destino, data, hora, vagas, hora);
        //gerenciaDados.salvaCaronasUsuario(usuario.getNome(), getSolicitacoesPendentes(idSessao));
        //gerenciaDados.salvaDadosGridUsuario(usuario.getNome(), getSolicitacoesPendentes(idSessao));

        return id;
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
        int interesse = controleInteresse.addInteresse(user, origem, destino, data,
                    horaInicio, horaFim);
       
        return interesse;
    }

    /**
     * Método que adiciona o usuário para utilizar os recursos do sistema.
     *
     * @param login - Login do usuário cadastrado.
     * @param senha - Senha do Usuário
     * @return String do identificador da sessão aberta pelo usuário.
     * @throws MeLevaException - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public String abrirSessao(String login, String senha) throws MeLevaException {

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
        gerenciaDados.salvaUsuarios(controleUsuarios.getUsuariosCadastradosPorLogin());
        gerenciaDados.salvaNegociacoes(negociacoes);
        gerenciaDados.salvaInteresses(controleInteresse.getInteressesCadastrados());
        gerenciaDados.salvaCaronasEmJson(controleUsuarios.getUsuariosCadastradosPorLogin(), controleUsuarios);
        //gerenciaDados.salvaPedidosCaronaEmJson(negociacoes.getSolicitacoesPendentes(), controleUsuarios);
        //gerenciaDados.salvaSugestoesEmJson(negociacoes.getSugestoesPontoDeEncontro(), controleUsuarios);

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
        //gerenciaDados.carregaDadosGrid(controleUsuarios.getUsuariosCadastradosPorLogin());
        controleUsuarios.clear();
        negociacoes.clear();
        controleInteresse.clear();
    }

    /**
     * Método que reinicia o sistema.
     */
    public void reiniciarSistema() {
        controleUsuarios.setUsuariosCadastradosPorLogin(gerenciaDados.leUsuarios());
        negociacoes = gerenciaDados.leNegociacoes();
        controleInteresse.setInteressesCadastrados(gerenciaDados.leInteresses());
        controleUsuarios.iniciarContadores();
        negociacoes.iniciarContadores();
        controleInteresse.iniciarContadores();
        //gerenciaDados.carregaDadosGrid(controleUsuarios.getUsuariosCadastradosPorLogin());
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
     * @throws MeLevaException - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public String getAtributoUsuario(String login, String atributo)
            throws MeLevaException {

        Usuario usuario = controleUsuarios.getUsuarioLogin(login);

        return usuario.getAtributoUsuario(atributo);
    }

    /**
     * Método que acessa o atributo do perfil do usuário.
     *
     * @param login - Login do usuário.
     * @param atributo - Atributo do perfil do usuário.
     * @return String que representa o atributo do perfil do usuário.
     * @throws MeLevaException - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public String getAtributoPerfil(String login, String atributo)
            throws MeLevaException {

        return controleUsuarios.getAtributoPerfil(login, atributo);

    }

    /**
     * Método de acesso aos atributos da solicitação.
     *
     * @param idSolicitacao - Identificador da Solicitação.
     * @param atributo - Atributo a ser retornado.
     * @return String que representa o atributo da solicitacao.
     * @throws MeLevaException - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public String getAtributoSolicitacao(String idSolicitacao, String atributo)
            throws MeLevaException {
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
     * @throws MeLevaException - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public int sugerirPontoEncontro(String idSessao, String idCarona,
            String pontos) throws MeLevaException {
        int retorno = negociacoes.addSugestaoPontoDeEncontro(idSessao, idCarona,
                pontos);
        gerenciaDados.salvaSugestoesEmJson(negociacoes.getSugestoesPontoDeEncontro(), controleUsuarios);
        return retorno;
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
     * Método para acessar as Solicitações pendentes do usuário.
     *
     * @param iDSessao - Identificador da Sessão do usuário.
     * @return Lista de pendencias do usuário.
     * @throws MeLevaException - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public Map<String, NegociacaoDePonto> getSolicitacoesPendentes(String iDSessao)
            throws MeLevaException {
        return negociacoes.getSolicitacoesPendentes();
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
        int retorno = negociacoes.addSolicitacaoParaViajar(iDSessao, iDCarona, "");
        gerenciaDados.salvaPedidosCaronaEmJson(negociacoes.getSolicitacoesPendentes(), controleUsuarios);
        return retorno;
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
        int retorno = negociacoes.addSolicitacaoParaViajar(iDSessao, iDCarona, ponto);
        gerenciaDados.salvaPedidosCaronaEmJson(negociacoes.getSolicitacoesPendentes(), controleUsuarios);
        return retorno;
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
        int retorno = negociacoes.addRespostaPontoDeEncontro(iDSessao, idCarona,
                pontos);
        gerenciaDados.salvaRespostaSugestoesEmJson(negociacoes.getRespostasSugestaoPontoEncontro(), controleUsuarios);
        return retorno;
    }

    /**
     * Método para o caroneiro seder uma vaga ao solicitante na viagem naquele
     * ponto.
     *
     * @param idSessao - Identificador da sessão do usuário.
     * @param idSolicitacao -Identificador da solicitação.
     * @return True se a solicitacao foi aceita com sucesso.
     * @throws MeLevaException - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public boolean aceitarSolicitacaoPontoEncontro(String idSessao,
            String idSolicitacao) throws MeLevaException {
        negociacoes.aceitarSolicitacaoPontoEncontro(idSessao, idSolicitacao);
        gerenciaDados.salvaPedidosCaronaEmJson(negociacoes.getSolicitacoesPendentes(), controleUsuarios);
        gerenciaDados.salvaCaronaConfirmadasEmJson(negociacoes.getSolicitacoesConfirmadas(), controleUsuarios);
        //gerenciaDados.salvaSolicitacaoEmJson(negociacoes.getSolicitacoesConfirmadas(), controleUsuarios);
        return true;
    }

    /**
     * Método para o caroneiro seder uma vaga ao solicitante na viagem.
     *
     * @param idSessao - Identificador da sessão do usuário.
     * @param idSolicitacao -Identificador da solicitação.
     * @return True se a solicitacao foi aceita com sucesso.
     * @throws MeLevaException - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public boolean aceitarSolicitacao(String idSessao, String idSolicitacao)
            throws MeLevaException {
        aceitarSolicitacaoPontoEncontro(idSessao, idSolicitacao);
        gerenciaDados.salvaPedidosCaronaEmJson(negociacoes.getSolicitacoesPendentes(), controleUsuarios);
        gerenciaDados.salvaCaronaConfirmadasEmJson(negociacoes.getSolicitacoesConfirmadas(), controleUsuarios);
        return true;
    }

    /**
     * Método para o carona da vaga desista de embarcar.
     *
     * @param idSessao - Identificador da sessão do usuário.
     * @param idCarona - Identificador da Carona.
     * @param idSugestao - Identificador da Sugestão.
     * @return True se a desistencia foi feita com sucesso.
     * @throws MeLevaException - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public boolean desistirRequisicao(String idSessao, String idCarona,
            String idSugestao) throws MeLevaException {
        negociacoes.desistirRequisicao(idSessao, idCarona, idSugestao);
        gerenciaDados.salvaPedidosCaronaEmJson(negociacoes.getSolicitacoesPendentes(), controleUsuarios);
        return true;
    }

    /**
     * Método para o solicitante da vaga desista da solicitação.
     *
     * @param idSessao - Identificador da sessão do usuário.
     * @param idSolicitacao - Identificador da Solicitação.
     * @return True se a rejeição foi feita com sucesso.
     * @throws MeLevaException - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public boolean rejeitarSolicitacao(String idSessao, String idSolicitacao)
            throws MeLevaException {
        negociacoes.rejeitaSolicitacao(idSessao, idSolicitacao, negociacoes);
        gerenciaDados.salvaPedidosCaronaEmJson(negociacoes.getSolicitacoesPendentes(), controleUsuarios);
        return true;

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
     * @return True se a revisão foi concluida com sucesso.
     * @throws MeLevaException - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public boolean reviewCarona(String iDSessao, String iDCarona, String review)
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

        return true;

    }

    /**
     * Método para revisar a situação da vaga do carona na viagem.
     *
     * @param iDSessao - Identificador da sessão do usuário.
     * @param idCarona - Idenficador da carona.
     * @param loginCaroneiro - login do Caroneiro.
     * @param review - Situação da vaga na viagem.
     * @return True se a revisão foi concluida com sucesso.
     * @throws MeLevaException - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public boolean reviewVagaEmCarona(String iDSessao, String idCarona,
            String loginCaroneiro, String review) throws MeLevaException {

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

        return true;

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
     * Método para usuário enviar e-mails para um determinado usuário de
     * destino.
     *
     * @param destinatario - Destino a ser enviado o e-mail.
     * @return Boolean de verificação se o envio foi correto.
     * @throws MeLevaException - Exceções para o caso dos dados inseridos sejam
     * inválidos. inválidos.
     */
    public String enviaEmailSenhaEsquecida(String destinatario)
            throws MeLevaException {
        verificador.verificaDestinatario(destinatario);
        Usuario user = getUsuarioEmail(destinatario);
        //verificador.verificaEmailNome(destinatario, nomeCompleto);
        final String FROM = "melevaproject@gmail.com";
        final String KEY = "projetoSi12012";

        MailSender mail = new MailSender("smtp.gmail.com", "465");
        mail.autenticarUsuario(FROM, KEY, true);
        mail.enviarEmail(FROM, destinatario, "<<Me Leva Correspondência>>", "Sua senha é: "
                + user.getSenha() + "\nEnviado por: " + FROM + "\n\nNão responda esse email, esse email foi enviado para verificação requerida por você.", false);

        return "Email enviado com sucesso.";

    }

    private Usuario getUsuarioEmail(String email) {
        List<Usuario> usuarios = ControleDeUsuarios.getInstance().getUsuariosCadastradosPorLogin();
        Iterator<Usuario> it = usuarios.iterator();
        Usuario usuarioConfirmado = null;
        while (it.hasNext()) {
            Usuario user = it.next();
            if (user.getEmail().trim().equals(email.trim())) {
                usuarioConfirmado = user;
                break;
            }

        }
        if (usuarioConfirmado == null) {
            throw new MeLevaException(MSGDeExcecao.MSG_ERRO_EMAIL_INVALIDO + "OPS");
        }

        return usuarioConfirmado;
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

    /**
     * Método para acessar o dono da carona.
     *
     * @param iDCarona - Identificador da carona do usuário.
     * @return Identificador do usuario dono da carona.
     * @throws MeLevaException - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public String getDonoCarona(String iDCarona) throws MeLevaException {
        Usuario userDono = controleUsuarios.getDono(iDCarona);
        if (userDono == null) {
            throw new MeLevaException(MSGDeExcecao.MSG_ERRO_CARONA_INEXISTENTE);
        }
        return userDono.getIdUsuario();
    }

    public ControleDeUsuarios getControleUsuarios() {
        return controleUsuarios;
    }
}
