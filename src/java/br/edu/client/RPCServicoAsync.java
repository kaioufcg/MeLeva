package br.edu.client;

import br.edu.client.utilitarios.MeLevaException;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface RPCServicoAsync {

    public void criarUsuario(String login, String senha, String nome,
            String endereco, String email, AsyncCallback<String> asyncCallback) throws MeLevaException;

    public void enviaEmailSenhaEsquecida(String destinatario, AsyncCallback<String> asyncCallback) throws MeLevaException;

    public void iniciaSistema(AsyncCallback<String> asyncCallback) throws MeLevaException;

    public void pedirCarona(String iDSessao, String iDCarona, AsyncCallback<String> asyncCallback) throws MeLevaException;

    public void getDonoCarona(String iDCarona, AsyncCallback<String> asyncCallback) throws MeLevaException;

    public void getIdUsuarioLogin(String login, AsyncCallback<String> asyncCallback) throws MeLevaException;

    public void getSenhaUsuarioLogin(String login, AsyncCallback<String> asyncCallback) throws MeLevaException;

    public void encerraLogin(String login, AsyncCallback<String> asyncCallback) throws MeLevaException;

    public void abrirSessao(String login, String senha, AsyncCallback<String> asyncCallback) throws MeLevaException;

    public void ofereceCarona(String idSessao, String origem, String destino,
            String data, String hora, String vagas, AsyncCallback<String> asyncCallback) throws MeLevaException;

    public void zerarSistema(AsyncCallback<String> asyncCallback);

    public void localizarCarona(String idSessao, String origem, String destino, AsyncCallback<String> asyncCallback)
            throws MeLevaException;

    public void localizarCaronaMunicipal(String idSessao, String cidade,
            String origem, String destino, AsyncCallback<String> asyncCallback) throws MeLevaException;

    public void localizarCaronaMunicipal(String idSessao, String cidade, AsyncCallback<String> asyncCallback)
            throws MeLevaException;

    public void cadastrarCaronaMunicipal(String idSessao, String origem,
            String destino, String cidade, String data, String hora,
            String vagas, AsyncCallback<String> asyncCallback) throws MeLevaException;

    public void cadastrarInteresse(String iDSessao, String origem,
            String destino, String data, String horaInicio, String horaFim, AsyncCallback<String> asyncCallback)
            throws MeLevaException;

    public void encerrarSessao(String login, AsyncCallback<String> asyncCallback) throws MeLevaException;

    public void encerrarSistema(AsyncCallback<String> asyncCallback);

    public void reiniciarSistema(AsyncCallback<String> asyncCallback);

    public void quit(AsyncCallback<String> asyncCallback);

    public void getAtributoCarona(String idCarona, String atributo, AsyncCallback<String> asyncCallback)
            throws MeLevaException;

    public void getAtributoUsuario(String login, String atributo, AsyncCallback<String> asyncCallback)
            throws MeLevaException;

    public void getAtributoPerfil(String login, String atributo, AsyncCallback<String> asyncCallback)
            throws MeLevaException;

    public void getAtributoSolicitacao(String idSolicitacao, String atributo, AsyncCallback<String> asyncCallback)
            throws MeLevaException;

    public void getTrajeto(String idCarona, AsyncCallback<String> asyncCallback) throws MeLevaException;

    public void getCarona(String idCarona, AsyncCallback<String> asyncCallback) throws MeLevaException;

    public void getCaronaUsuario(String iDSessao, int index, AsyncCallback<String> asyncCallback)
            throws MeLevaException;

    public void getTodasCaronasUsuario(String iDSessao, AsyncCallback<String> asyncCallback)
            throws MeLevaException;

    public void getPontosSugeridos(String iDSessao, String iDCarona, AsyncCallback<String> asyncCallback)
            throws MeLevaException;

    public void getPontosEncontro(String iDSessao, String iDCarona, AsyncCallback<String> asyncCallback)
            throws MeLevaException;

    public void sugerirPontoEncontro(String idSessao, String idCarona,
            String pontos, AsyncCallback<String> asyncCallback) throws MeLevaException;

    public void getSolicitacoesConfirmadas(String iDSessao, String idCarona, AsyncCallback<String> asyncCallback)
            throws MeLevaException;

    public void getSolicitacoesPendentes(String iDSessao, String idCarona, AsyncCallback<String> asyncCallback)
            throws MeLevaException;

   public void solicitarVaga(String iDSessao, String iDCarona, AsyncCallback<String> asyncCallback)
            throws MeLevaException;

    public void solicitarVagaPontoEncontro(String iDSessao, String iDCarona,
            String ponto, AsyncCallback<String> asyncCallback) throws MeLevaException;

    public void responderSugestaoPontoEncontro(String iDSessao, String idCarona,
            String idSugestao, String pontos, AsyncCallback<String> asyncCallback) throws MeLevaException;

    public void aceitarSolicitacaoPontoEncontro(String idSessao,
            String idSolicitacao, AsyncCallback<String> asyncCallback) throws MeLevaException;

    public void aceitarSolicitacao(String idSessao, String idSolicitacao, AsyncCallback<String> asyncCallback)
            throws MeLevaException;

    public void desistirRequisicao(String idSessao, String idCarona,
            String idSugestao, AsyncCallback<String> asyncCallback) throws MeLevaException;

    public void rejeitarSolicitacao(String idSessao, String idSolicitacao, AsyncCallback<String> asyncCallback)
            throws MeLevaException;

    public void visualizarPerfil(String iDSessao, String login, AsyncCallback<String> asyncCallback)
            throws MeLevaException;

    public void reviewCarona(String iDSessao, String iDCarona, String review, AsyncCallback<String> asyncCallback)
            throws MeLevaException;

    public void reviewVagaEmCarona(String iDSessao, String idCarona,
            String loginCaroneiro, String review, AsyncCallback<String> asyncCallback) throws MeLevaException;

    public void enviarEmail(String iDSessao, String destino, String message, AsyncCallback<String> asyncCallback)
            throws MeLevaException;

    public void verificarMensagensPerfil(String iDSessao, AsyncCallback<String> asyncCallback)
            throws MeLevaException;
}
