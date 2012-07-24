package br.edu.server;

import br.edu.client.RPCServico;
import br.edu.client.utilitarios.MeLevaException;
import br.edu.server.controller.MeLevaController;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class RPCServicoImpl extends RemoteServiceServlet implements RPCServico {

    private static final long serialVersionUID = 1L;

    /**
     * Escape an html string. Escaping data received from the client helps to
     * prevent cross-site script vulnerabilities.
     *
     * @param html the html string to escape
     * @return the escaped string
     */
    private String escapeHtml(String html) {
        if (html == null) {
            return null;
        }
        return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
    }

    @Override
    public String criarUsuario(String login, String senha, String nome, String endereco, String email) throws MeLevaException {
        return escapeHtml(MeLevaController.getInstance().criarUsuario(login, senha, nome, endereco, email));
    }

    @Override
    public String enviaEmailSenhaEsquecida(String destinatario) throws MeLevaException {
        return escapeHtml(MeLevaController.getInstance().enviaEmailSenhaEsquecida(destinatario));
    }

    @Override
    public void iniciaSistema() throws MeLevaException {
        MeLevaController.getInstance().reiniciarSistema();
    }

    @Override
    public String pedirCarona(String iDSessao, String iDCarona) throws MeLevaException {
        return escapeHtml(String.valueOf(MeLevaController.getInstance().solicitarVaga(iDSessao, iDCarona)));
    }

    @Override
    public String getDonoCarona(String iDCarona) throws MeLevaException {
        return escapeHtml(String.valueOf(MeLevaController.getInstance().getDonoCarona(iDCarona)));
    }

    @Override
    public String getIdUsuarioLogin(String login) throws MeLevaException {
        return escapeHtml(String.valueOf(MeLevaController.getInstance().getControleUsuarios().getUsuarioLogin(login).getIdUsuario()));
    }

    @Override
    public String getSenhaUsuarioLogin(String login) throws MeLevaException {
        return escapeHtml(String.valueOf(MeLevaController.getInstance().getControleUsuarios().getUsuarioLogin(login).getSenha()));
    }

    @Override
    public String encerraLogin(String login) throws MeLevaException {
        return escapeHtml(String.valueOf(MeLevaController.getInstance().encerrarSessao(login)));
    }

    @Override
    public String abrirSessao(String login, String senha) throws MeLevaException {
        return escapeHtml(MeLevaController.getInstance().abrirSessao(login, senha));
    }

    @Override
    public String ofereceCarona(String idSessao, String origem, String destino, String data, String hora, String vagas) throws MeLevaException {
        return escapeHtml(String.valueOf(MeLevaController.getInstance().cadastrarCarona(idSessao, origem, destino, data, hora, vagas)));
    }

    @Override
    public void zerarSistema() {
        MeLevaController.getInstance().zerarSistema();

    }

    @Override
    public String localizarCarona(String idSessao, String origem, String destino) throws MeLevaException {
        return escapeHtml(String.valueOf(MeLevaController.getInstance().localizarCarona(idSessao, origem, destino)));

    }

    @Override
    public String localizarCaronaMunicipal(String idSessao, String cidade, String origem, String destino) throws MeLevaException {
        return escapeHtml(String.valueOf(MeLevaController.getInstance().localizarCaronaMunicipal(idSessao, cidade, origem, destino)));
    }

    @Override
    public String localizarCaronaMunicipal(String idSessao, String cidade) throws MeLevaException {
        return escapeHtml(String.valueOf(MeLevaController.getInstance().localizarCaronaMunicipal(idSessao, cidade)));
    }

    @Override
    public String cadastrarCaronaMunicipal(String idSessao, String origem, String destino, String cidade, String data, String hora, String vagas) throws MeLevaException {
        return escapeHtml(String.valueOf(MeLevaController.getInstance().cadastrarCaronaMunicipal(idSessao, origem, destino, cidade, data, hora, vagas)));
    }

    @Override
    public String cadastrarInteresse(String iDSessao, String origem, String destino, String data, String horaInicio, String horaFim) throws MeLevaException {
        return escapeHtml(String.valueOf(MeLevaController.getInstance().cadastrarInteresse(iDSessao, origem, destino, data, horaInicio, horaFim)));
    }

    @Override
    public String encerrarSessao(String login) throws MeLevaException {
        return escapeHtml(String.valueOf(MeLevaController.getInstance().encerrarSessao(login)));
    }

    @Override
    public void encerrarSistema() {
        MeLevaController.getInstance().encerrarSistema();
    }

    @Override
    public void reiniciarSistema() {
        MeLevaController.getInstance().reiniciarSistema();
    }

    @Override
    public void quit() {
        MeLevaController.getInstance().quit();
    }

    @Override
    public String getAtributoCarona(String idCarona, String atributo) throws MeLevaException {
        return escapeHtml(String.valueOf(MeLevaController.getInstance().getAtributoCarona(idCarona, atributo)));
    }

    @Override
    public String getAtributoUsuario(String login, String atributo) throws MeLevaException {
        return escapeHtml(String.valueOf(MeLevaController.getInstance().getAtributoUsuario(login, atributo)));
    }

    @Override
    public String getAtributoPerfil(String login, String atributo) throws MeLevaException {
        return escapeHtml(String.valueOf(MeLevaController.getInstance().getAtributoPerfil(login, atributo)));
    }

    @Override
    public String getAtributoSolicitacao(String idSolicitacao, String atributo) throws MeLevaException {
        return escapeHtml(String.valueOf(MeLevaController.getInstance().getAtributoSolicitacao(idSolicitacao, atributo)));
    }

    @Override
    public String getTrajeto(String idCarona) throws MeLevaException {
        return escapeHtml(String.valueOf(MeLevaController.getInstance().getTrajeto(idCarona)));
    }

    @Override
    public String getCarona(String idCarona) throws MeLevaException {
        return escapeHtml(String.valueOf(MeLevaController.getInstance().getCarona(idCarona)));
    }

    @Override
    public String getCaronaUsuario(String iDSessao, int index) throws MeLevaException {
        return escapeHtml(String.valueOf(MeLevaController.getInstance().getCaronaUsuario(iDSessao, index)));
    }

    @Override
    public String getTodasCaronasUsuario(String iDSessao) throws MeLevaException {
        return escapeHtml(String.valueOf(MeLevaController.getInstance().getTodasCaronasUsuario(iDSessao)));
    }

    @Override
    public String getPontosSugeridos(String iDSessao, String iDCarona) throws MeLevaException {
        return escapeHtml(String.valueOf(MeLevaController.getInstance().getPontosSugeridos(iDSessao, iDCarona)));
    }

    @Override
    public String getPontosEncontro(String iDSessao, String iDCarona) throws MeLevaException {
        return escapeHtml(String.valueOf(MeLevaController.getInstance().getPontosEncontro(iDSessao, iDCarona)));
    }

    @Override
    public String sugerirPontoEncontro(String idSessao, String idCarona, String pontos) throws MeLevaException {
        return escapeHtml(String.valueOf(MeLevaController.getInstance().sugerirPontoEncontro(idSessao, idCarona, pontos)));
    }

    @Override
    public String getSolicitacoesConfirmadas(String iDSessao, String idCarona) throws MeLevaException {
        return escapeHtml(String.valueOf(MeLevaController.getInstance().getSolicitacoesConfirmadas(iDSessao, idCarona)));
    }

    @Override
    public String getSolicitacoesPendentes(String iDSessao, String idCarona) throws MeLevaException {
        return escapeHtml(String.valueOf(MeLevaController.getInstance().getSolicitacoesPendentes(iDSessao, idCarona)));
    }

    @Override
    public String solicitarVaga(String iDSessao, String iDCarona) throws MeLevaException {
        return escapeHtml(String.valueOf(MeLevaController.getInstance().solicitarVaga(iDSessao, iDCarona)));
    }

    @Override
    public String solicitarVagaPontoEncontro(String iDSessao, String iDCarona, String ponto) throws MeLevaException {
        return escapeHtml(String.valueOf(MeLevaController.getInstance().solicitarVagaPontoEncontro(iDSessao, iDCarona, ponto)));
    }

    @Override
    public String responderSugestaoPontoEncontro(String iDSessao, String idCarona, String idSugestao, String pontos) throws MeLevaException {
        return escapeHtml(String.valueOf(MeLevaController.getInstance().responderSugestaoPontoEncontro(iDSessao, idCarona, idSugestao, pontos)));
    }

    @Override
    public String aceitarSolicitacaoPontoEncontro(String idSessao, String idSolicitacao) throws MeLevaException {
        return escapeHtml(String.valueOf(MeLevaController.getInstance().aceitarSolicitacaoPontoEncontro(idSessao, idSolicitacao)));
    }

    @Override
    public String aceitarSolicitacao(String idSessao, String idSolicitacao) throws MeLevaException {
        return escapeHtml(String.valueOf(MeLevaController.getInstance().aceitarSolicitacao(idSessao, idSolicitacao)));
    }

    @Override
    public String desistirRequisicao(String idSessao, String idCarona, String idSugestao) throws MeLevaException {
        return escapeHtml(String.valueOf(MeLevaController.getInstance().desistirRequisicao(idSessao, idCarona, idSugestao)));
    }

    @Override
    public String rejeitarSolicitacao(String idSessao, String idSolicitacao) throws MeLevaException {
        return escapeHtml(String.valueOf(MeLevaController.getInstance().rejeitarSolicitacao(idSessao, idSolicitacao)));

    }

    @Override
    public String visualizarPerfil(String iDSessao, String login) throws MeLevaException {
        return escapeHtml(String.valueOf(MeLevaController.getInstance().visualizarPerfil(iDSessao, login)));
    }

    @Override
    public String reviewCarona(String iDSessao, String iDCarona, String review) throws MeLevaException {
        return escapeHtml(String.valueOf(MeLevaController.getInstance().reviewCarona(iDSessao, iDCarona, review)));

    }

    @Override
    public String reviewVagaEmCarona(String iDSessao, String idCarona, String loginCaroneiro, String review) throws MeLevaException {
        return escapeHtml(String.valueOf(MeLevaController.getInstance().reviewVagaEmCarona(iDSessao, idCarona, loginCaroneiro, review)));
    }

    @Override
    public String enviarEmail(String iDSessao, String destino, String message) throws MeLevaException {
        return escapeHtml(String.valueOf(MeLevaController.getInstance().enviarEmail(iDSessao, destino, message)));
    }

    @Override
    public String verificarMensagensPerfil(String iDSessao) throws MeLevaException {
        return escapeHtml(String.valueOf(MeLevaController.getInstance().verificarMensagensPerfil(iDSessao)));
    }
}
