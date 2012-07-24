package br.edu.server.logicaSistema;

import br.edu.client.utilitarios.MSGDeExcecao;
import br.edu.client.utilitarios.MeLevaException;
import br.edu.server.logicaCarona.AbstractCarona;
import br.edu.server.logicaCarona.Carona;
import br.edu.server.logicaUsuario.Usuario;
import java.io.Serializable;
import java.util.*;

/**
 * Classe que controla os usuário do sistema Me Leva.
 *
 * @author Grupo Me Leva - Projeto SI1 - UFCG 2012.1
 *
 */
public class ControleDeUsuarios implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -6599094864598347544L;
    private volatile static ControleDeUsuarios unicaInstancia;
    private List<Usuario> usuariosCadastradosPorLogin;
    private Map<String, Usuario> sessoesAbertas;
    private Verificador verificador;

    /**
     * Construtor do controlador.
     */
    private ControleDeUsuarios() {
        usuariosCadastradosPorLogin = new LinkedList<Usuario>();
        sessoesAbertas = new TreeMap<String, Usuario>();
        verificador = new Verificador();
    }

    /**
     * Método que acessa a instância unica do controle.
     *
     * @return Instância unica.
     */
    public static ControleDeUsuarios getInstance() {
        if (unicaInstancia == null) {
            synchronized (ControleDeUsuarios.class) {
                if (unicaInstancia == null) {
                    unicaInstancia = new ControleDeUsuarios();
                }
            }
        }
        return unicaInstancia;

    }

    /**
     * Método que adiciona usuário na lista de cadastrados.
     *
     * @param usuario - Usuario a ser cadastrado.
     * @throws MeLevaException - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public void addUsuario(Usuario usuario) throws MeLevaException {
        verificador.verificaUsuarioInexistente(usuario);

        getUsuariosCadastradosPorLogin().add(usuario);
    }

    /**
     * Método que adiciona as sessões abertas do sistema.
     *
     * @param login - Login do usuário a ser adicionado.
     * @param senha - Senha do usuário a ser adicionado.
     * @return Identificador da sessão.
     * @throws MeLevaException - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public String putSessoesAbertas(String login, String senha)
            throws MeLevaException {
        verificador.verificaSenha(senha);

        Usuario usuarioLogar = getUsuarioLogin(login);
        String id = null;

        if (usuarioLogar.getSenha().equals(senha)) {
            id = usuarioLogar.getIdUsuario();
            getSessoesAbertas().put(id, usuarioLogar);
        } else {
            throw new MeLevaException(MSGDeExcecao.MSG_ERRO_LOGIN_INVALIDO);
        }

        return id;

    }

    /**
     * Método que remove a sessão da lista de sessões abertas.
     *
     * @param login - Login do usuário a ser removido.
     * @return Boolean verificador para confirmação da remoção.
     * @throws MeLevaException - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public boolean removeSessao(String login) throws MeLevaException {
        verificador.verificaLogin(login);
        verificador.verificaUsuarioInexistente(getUsuarioLogin(login));
        getSessoesAbertas().remove(login);
        return true;
    }

    /**
     * Método para simples visualização do perfil.
     *
     * @param iDSessao - Identificador da sessão do usuário.
     * @param login - Login do usuário.
     * @return String do perfil do usuário.
     * @throws MeLevaException - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public String visualizarPerfil(String iDSessao, String login)
            throws MeLevaException {

        Usuario usuario = getUsuarioId(iDSessao);
        if (usuario == null || !usuario.getLogin().equals(login)) {
            throw new MeLevaException(MSGDeExcecao.MSG_ERRO_LOGIN_INVALIDO);
        }
                    
        return "Perfil de " + usuario.getLogin().substring(0, 1).toUpperCase() + usuario.getLogin().substring(1);
    }

    /**
     * Método que modifaca os cadastros de usuários por login.
     *
     * @param usuariosCadastradosPorLogin - Lista de usuários.
     */
    public void setUsuariosCadastradosPorLogin(
            List<Usuario> usuariosCadastradosPorLogin) {
        this.usuariosCadastradosPorLogin = usuariosCadastradosPorLogin;
        
    }

    /**
     * Método que zera as informações do controle.
     */
    public void clear() {
        getUsuariosCadastradosPorLogin().clear();
        getSessoesAbertas().clear();
        Usuario.zeraCont();
        Carona.zeraCont();
    }

    /**
     * Método que acessa a carona pelo identificador.
     *
     * @param idCarona - Identificador da carona.
     * @return Carona.
     * @throws MeLevaException - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public AbstractCarona getCarona(String idCarona) throws MeLevaException {
        verificador.verificaIdCarona(idCarona);

        Iterator<Usuario> iteradorUsuarios = this.getUsuarios().iterator();
        AbstractCarona carona = null;
        while (iteradorUsuarios.hasNext() && carona == null) {
            // para cada usuario verifico se ele � o dono da carona
            Usuario proximoUser = iteradorUsuarios.next();
            carona = proximoUser.getCarona(idCarona);
        }
        return carona;
    }

    /**
     * Método que acessa a coleção de usuários cadastrados por login.
     *
     * @return Coleção de usuários cadastrados por login.
     */
    public Collection<Usuario> getUsuarios() {
        return getUsuariosCadastradosPorLogin();
    }

    /**
     * Método que acessa o usuário pelo ligon.
     *
     * @param login - Login do usuário a ser acessado.
     * @return Usuário buscado por login.
     * @throws MeLevaException - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public Usuario getUsuarioLogin(String login) throws MeLevaException {

        verificador.verificaLogin(login);

        Iterator<Usuario> usuarioIt = getUsuariosCadastradosPorLogin().iterator();
        Usuario usuarioTemp = null;
        while (usuarioIt.hasNext()) {
            Usuario usuario = (Usuario) usuarioIt.next();
            if (usuario.getLogin().equals(login)) {
                usuarioTemp = usuario;
                break;
            }

        }

        verificador.verificaUsuarioInexistente(usuarioTemp);

        return usuarioTemp;
    }

    /**
     * Método que acessa o usuário pelo identificador.
     *
     * @param iDSessao - Identificador da sessão do usuário.
     * @return Usuário buscado.
     * @throws MeLevaException - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public Usuario getUsuarioId(String iDSessao) throws MeLevaException {
        verificador.verificaIdSessao(iDSessao);
        verificador.verificaSessaoInexistente(getSessoesAbertas(), iDSessao);

        Iterator<Usuario> iteradorUsuario = getUsuariosCadastradosPorLogin().iterator();
        Usuario user = null;

        while (iteradorUsuario.hasNext()) {
            Usuario nextUser = iteradorUsuario.next();
            if (nextUser.getIdUsuario().equals(iDSessao)) {
                user = nextUser;
                break;
            }
        }
        return user;
    }

    /**
     * Método que acessa a lista de usuários cadastrados por login.
     *
     * @return Lista de usuários.
     */
    public List<Usuario> getUsuariosCadastradosPorLogin() {
        return usuariosCadastradosPorLogin;
    }

    /**
     * Método que acessa o dono da carona.
     *
     * @param idCarona - Identificador da carona.
     * @return Usuário dono da carona.
     */
    public Usuario getDono(String idCarona) {
        Usuario user = null;

        Iterator<Usuario> iteradorUsuario = getUsuariosCadastradosPorLogin().iterator();
        while (iteradorUsuario.hasNext()) {
            Usuario nextUser = iteradorUsuario.next();
            if (nextUser.getCarona(idCarona) != null) {
                user = nextUser;
            }
        }
        return user;
    }

    /**
     * Método que acessa os atributos do perfil.
     *
     * @param login - Login do usuário.
     * @param atributo - Atributo do perfil.
     * @return String do atributo buscado.
     * @throws MeLevaException - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public String getAtributoPerfil(String login, String atributo)
            throws MeLevaException {
        Usuario usuario = getUsuarioLogin(login);
        return usuario.getAtributoUsuario(atributo);
    }

    /**
     * Método que acessa as sessões abertas pelo sistema.
     *
     * @return Map das sessões abertas.
     */
    public Map<String, Usuario> getSessoesAbertas() {
        return sessoesAbertas;
    }

    public void iniciarContadores() {
        Usuario.iniciazeraCont(getUsuariosCadastradosPorLogin().size());
        int contador = 0;
        for (Iterator<Usuario> it = usuariosCadastradosPorLogin.iterator(); it.hasNext();) {
            Usuario usuario = it.next();
            contador += usuario.getCaronasOfertadas().size(); 
        }
        AbstractCarona.iniciaCont(contador);
    }
}
