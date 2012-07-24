package br.edu.client;

import br.edu.client.utilitarios.MeLevaException;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 *
 * @author Família Buscapé
 */
@RemoteServiceRelativePath("meleva")
public interface RPCServico extends RemoteService {

    public String criarUsuario(String login, String senha, String nome,
            String endereco, String email) throws MeLevaException;

    public String enviaEmailSenhaEsquecida(String destinatario) throws MeLevaException;

    public void iniciaSistema() throws MeLevaException;

    public String pedirCarona(String iDSessao, String iDCarona) throws MeLevaException;

    public String getDonoCarona(String iDCarona) throws MeLevaException;

    public String getIdUsuarioLogin(String login) throws MeLevaException;

    public String getSenhaUsuarioLogin(String login) throws MeLevaException;

    public String encerraLogin(String login) throws MeLevaException;

    public String abrirSessao(String login, String senha) throws MeLevaException;

    public String ofereceCarona(String idSessao, String origem, String destino,
            String data, String hora, String vagas) throws MeLevaException;

    public void zerarSistema();

    public String localizarCarona(String idSessao, String origem, String destino)
            throws MeLevaException;

    public String localizarCaronaMunicipal(String idSessao, String cidade,
            String origem, String destino) throws MeLevaException;

    public String localizarCaronaMunicipal(String idSessao, String cidade)
            throws MeLevaException;

    public String cadastrarCaronaMunicipal(String idSessao, String origem,
            String destino, String cidade, String data, String hora,
            String vagas) throws MeLevaException;

    public String cadastrarInteresse(String iDSessao, String origem,
            String destino, String data, String horaInicio, String horaFim)
            throws MeLevaException;

    public String encerrarSessao(String login) throws MeLevaException;

    public void encerrarSistema();

    public void reiniciarSistema();

    public void quit();

    public String getAtributoCarona(String idCarona, String atributo)
            throws MeLevaException;

    public String getAtributoUsuario(String login, String atributo)
            throws MeLevaException;

    public String getAtributoPerfil(String login, String atributo)
            throws MeLevaException;

    public String getAtributoSolicitacao(String idSolicitacao, String atributo)
            throws MeLevaException;

    public String getTrajeto(String idCarona) throws MeLevaException;

    public String getCarona(String idCarona) throws MeLevaException;

    public String getCaronaUsuario(String iDSessao, int index)
            throws MeLevaException;

    public String getTodasCaronasUsuario(String iDSessao)
            throws MeLevaException;

    public String getPontosSugeridos(String iDSessao, String iDCarona)
            throws MeLevaException;

    public String getPontosEncontro(String iDSessao, String iDCarona)
            throws MeLevaException;

    public String sugerirPontoEncontro(String idSessao, String idCarona,
            String pontos) throws MeLevaException;

    public String getSolicitacoesConfirmadas(String iDSessao, String idCarona)
            throws MeLevaException;

    public String getSolicitacoesPendentes(String iDSessao, String idCarona)
            throws MeLevaException;

    public String solicitarVaga(String iDSessao, String iDCarona)
            throws MeLevaException;

    public String solicitarVagaPontoEncontro(String iDSessao, String iDCarona,
            String ponto) throws MeLevaException;

    public String responderSugestaoPontoEncontro(String iDSessao, String idCarona,
            String idSugestao, String pontos) throws MeLevaException;

    public String aceitarSolicitacaoPontoEncontro(String idSessao,
            String idSolicitacao) throws MeLevaException;

    public String aceitarSolicitacao(String idSessao, String idSolicitacao)
            throws MeLevaException;

    public String desistirRequisicao(String idSessao, String idCarona,
            String idSugestao) throws MeLevaException;

    public String rejeitarSolicitacao(String idSessao, String idSolicitacao)
            throws MeLevaException;

    public String visualizarPerfil(String iDSessao, String login)
            throws MeLevaException;

    public String reviewCarona(String iDSessao, String iDCarona, String review)
            throws MeLevaException;

    public String reviewVagaEmCarona(String iDSessao, String idCarona,
            String loginCaroneiro, String review) throws MeLevaException;

    public String enviarEmail(String iDSessao, String destino, String message)
            throws MeLevaException;

    public String verificarMensagensPerfil(String iDSessao)
            throws MeLevaException;
}
