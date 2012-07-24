package br.edu.ufcg.projetomelevamavem.logicaSistema;

import br.edu.ufcg.projetomelevamavem.logicaCarona.AbstractCarona;
import br.edu.ufcg.projetomelevamavem.logicaCarona.Carona;
import br.edu.ufcg.projetomelevamavem.logicaNegociacao.NegociacaoDePonto;
import br.edu.ufcg.projetomelevamavem.logicaUsuario.Usuario;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Map;

/**
 * Classe para verificação de dados do sistema.
 * 
 * @author Grupo Me Leva - Projeto SI1 - UFCG 2012.1
 * @version 1.0
 * 
 */
public class Verificador implements Serializable {
    private static final long serialVersionUID = 0000000010L;

	/**
	 * Construtor do verificador.
	 */
	public Verificador() {
	}

	/**
	 * Método que verifica o Login.
	 * 
	 * @param login
	 *            - Login
	 * @throws MeLevaException
	 *             - Exceções para o caso dos dados inseridos sejam inválidos.
	 */
	public void verificaLogin(String login) throws MeLevaException {
		if (login == null || login.trim().equals("")) {
			throw new MeLevaException(MSGDeExcecao.MSG_ERRO_LOGIN_INVALIDO);
		}

	}

	/**
	 * Método que verifica a senha.
	 * 
	 * @param senha
	 *            - Senha.
	 * @throws MeLevaException
	 *             - Exceções para o caso dos dados inseridos sejam inválidos.
	 */
	public void verificaSenha(String senha) throws MeLevaException {
		if (senha == null || senha.equals("")) {
			throw new MeLevaException(MSGDeExcecao.MSG_ERRO_SENHA_INVALIDO);
		}
	}

	/**
	 * Método que verifica o nome.
	 * 
	 * @param nome
	 *            - Nome.
	 * @throws MeLevaException
	 *             - Exceções para o caso dos dados inseridos sejam inválidos.
	 */
	public void verificaNome(String nome) throws MeLevaException {
		if (nome == null || nome.trim().equals("")
				|| !(nome.replaceAll(" ", "").matches("^[a-zA-Z0-9.]*$"))) {
			throw new MeLevaException(MSGDeExcecao.MSG_ERRO_NOME_INVALIDO);
		}

	}

	/**
	 * Método que verifica o endereço.
	 * 
	 * @param endereco
	 *            - Endereço.
	 * @throws MeLevaException
	 *             - Exceções para o caso dos dados inseridos sejam inválidos.
	 */
	public void verificaEndereco(String endereco) throws MeLevaException {
		if (endereco == null || endereco.trim().equals("")) {
			throw new MeLevaException(MSGDeExcecao.MSG_ERRO_ENDERECO_INVALIDO);
		}

	}

	/**
	 * Método que verifica o e-mail.
	 * 
	 * @param email
	 *            - E-mail
	 * @throws MeLevaException
	 *             - Exceções para o caso dos dados inseridos sejam inválidos.
	 */
	public void verificaEmail(String email) throws MeLevaException {
		if (email == null || email.trim().equals("")
				|| !email.matches("[\\w_.]+@\\w+[..](com|com[.-.]br)")) {
			throw new MeLevaException(MSGDeExcecao.MSG_ERRO_EMAIL_INVALIDO);
		}

	}

	/**
	 * Métod que verifica o atributo.
	 * 
	 * @param atributo
	 *            - Atributo.
	 * @throws MeLevaException
	 *             - Exceções para o caso dos dados inseridos sejam inválidos.
	 */
	public void verificaAtributo(String atributo) throws MeLevaException {
		if (atributo == null || atributo.trim().equals("")) {
			throw new MeLevaException(MSGDeExcecao.MSG_ERRO_ATRIBUTO_INVALIDO);
		}

	}

	/**
	 * Método que verifica se o login existe.
	 * 
	 * @param login
	 *            - Login.
	 * @throws MeLevaException
	 *             - Exceções para o caso dos dados inseridos sejam inválidos.
	 */
	public void verificaLoginExistente(String login) throws MeLevaException {
		Iterator<Usuario> it = ControleDeUsuarios.getInstance()
				.getUsuariosCadastradosPorLogin().iterator();
		while (it.hasNext()) {
			Usuario usuario = (Usuario) it.next();
			if (usuario.getLogin().equals(login)) {
				throw new MeLevaException(MSGDeExcecao.MSG_ERRO_LOGIN_EM_USO);
			}

		}
	}

	/**
	 * Método que verifica se o e-mail existe.
	 * 
	 * @param email
	 *            - E-mail.
	 * @throws MeLevaException
	 *             - Exceções para o caso dos dados inseridos sejam inválidos.
	 */
	public void verificaEmailExistente(String email) throws MeLevaException {
		Iterator<Usuario> iterador = ControleDeUsuarios.getInstance()
				.getUsuariosCadastradosPorLogin().iterator();
		while (iterador.hasNext()) {
			Usuario user = iterador.next();
			if (user.getEmail().equals(email)) {
				throw new MeLevaException(MSGDeExcecao.MSG_ERRO_EMAIL_EM_USO);
			}
		}
	}

	/**
	 * Método que verifica se o usuário é nulo.
	 * 
	 * @param usuario
	 *            - Usuário.
	 * @throws MeLevaException
	 *             - Exceções para o caso dos dados inseridos sejam inválidos.
	 */
	public void verificaUsuarioInexistente(Usuario usuario)
			throws MeLevaException {
		if (usuario == null)
			throw new MeLevaException(MSGDeExcecao.MSG_ERRO_USUARIO_INEXISTENTE);
	}

	/**
	 * Método que verifica a origem.
	 * 
	 * @param origem
	 *            - Origem
	 * @throws MeLevaException
	 *             - Exceções para o caso dos dados inseridos sejam inválidos.
	 */
	public void verificaOrigem(String origem) throws MeLevaException {
		if (origem == null || origem.trim().contains("-")
				|| origem.trim().contains("()") || origem.trim().contains("!")
				|| origem.trim().contains("!?") || origem.trim().contains(".")
				|| origem.trim().replace(" ", "").matches("\\d+")) {
			throw new MeLevaException(MSGDeExcecao.MSG_ERRO_ORIGEM_INVALIDO);
		}

	}

	/**
	 * Método que verifica o destino.
	 * 
	 * @param destino
	 *            - Destino.
	 * @throws MeLevaException
	 *             - Exceções para o caso dos dados inseridos sejam inválidos.
	 */
	public void verificaDestino(String destino) throws MeLevaException {
		if (destino == null || destino.trim().contains("-")
				|| destino.trim().contains("()")
				|| destino.trim().contains("!")
				|| destino.trim().contains("!?")
				|| destino.trim().contains(".")
				|| destino.trim().replace(" ", "").matches("\\d+")) {
			throw new MeLevaException(MSGDeExcecao.MSG_ERRO_DESTINO_INVALIDO);
		}
	}

	/**
	 * Método que verifica o Identificador.
	 * 
	 * @param iDSessao
	 *            - Identificador.
	 * @throws MeLevaException
	 */
	public void verificaIdSessao(String iDSessao) throws MeLevaException {
		if (iDSessao == null || iDSessao.equals("")) {
			throw new MeLevaException(MSGDeExcecao.MSG_ERRO_SESSAO_INVALIDO);
		}

	}

	/**
	 * Método que verifica se a sessão existe.
	 * 
	 * @param sessoesAbertas
	 *            - Sessões abertas no sistema.
	 * @param idSessao
	 *            - Identificador de sessão.
	 * @throws MeLevaException
	 *             - - Exceções para o caso dos dados inseridos sejam inválidos.
	 */
	public void verificaSessaoInexistente(Map<String, Usuario> sessoesAbertas,
			String idSessao) throws MeLevaException {
		if (!sessoesAbertas.containsKey(idSessao))
			throw new MeLevaException(MSGDeExcecao.MSG_ERRO_SESSAO_INEXISTENTE);

	}

	/**
	 * Métodos que verifica a data e hora.
	 * 
	 * @param data
	 *            - Data.
	 * @param hora
	 *            - Hora.
	 * @throws MeLevaException
	 *             - Exceções para o caso dos dados inseridos sejam inválidos.
	 */
	public void verificaData(String data, String hora) throws MeLevaException {
		if (data == null || data.equals("")
				|| !data.matches("[0-9]{2}/[0-9]{2}/[0-9]{4}")) {
			throw new MeLevaException(MSGDeExcecao.MSG_ERRO_DATA_INVALIDO);
		}

		SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
		Calendar axi = Calendar.getInstance();
		GregorianCalendar calendarioPC = new GregorianCalendar();

		try {
			axi.setTime(formatoData.parse(data));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		String retorno = formatoData.format(axi.getTime());

		// facil para ver se a data é valida ex: 31/02/... não é valido
		// portanto o Calendar transforma para uma data valida que não é a que
		// eu passei
		if (!retorno.equals(data)) {
			throw new MeLevaException(MSGDeExcecao.MSG_ERRO_DATA_INVALIDO);
		}

		if (axi.getTime().before(calendarioPC.getTime())) {
			throw new MeLevaException(MSGDeExcecao.MSG_ERRO_DATA_INVALIDO);
		}

		// verificando a hora
		if (hora == null || hora.equals("")
				|| !hora.matches("[0-9]{1,2}:[0-9]{2}")) {
			throw new MeLevaException(MSGDeExcecao.MSG_ERRO_HORA_INVALIDO);
		}

		String[] horaSoNumeros = hora.trim().split(":");

		Integer hour = Integer.parseInt(horaSoNumeros[0]);
		Integer minut = Integer.parseInt(horaSoNumeros[1]);

		if (!(hour >= 0 && hour <= 23)) {
			throw new MeLevaException(MSGDeExcecao.MSG_ERRO_HORA_INVALIDO);
		}

		if (!(minut >= 0 && minut <= 59)) {
			throw new MeLevaException(MSGDeExcecao.MSG_ERRO_HORA_INVALIDO);
		}

		axi.set(Calendar.HOUR, hour);
		axi.set(Calendar.MINUTE, minut);

		if (axi.getTime().before(calendarioPC.getTime())) {
			throw new MeLevaException(MSGDeExcecao.MSG_ERRO_DATA_INVALIDO);
		}

	}

	/**
	 * Método que verifica vagas.
	 * 
	 * @param vagas
	 *            - Vagas.
	 * @throws MeLevaException
	 *             - Exceções para o caso dos dados inseridos sejam inválidos.
	 */
	public void verificaVagas(String vagas) throws MeLevaException {
		if (vagas == null || vagas.equals("") || !vagas.matches("^[1-9]*$")) {
			throw new MeLevaException(MSGDeExcecao.MSG_ERRO_VAGA_INVALIDO);
		}

	}

	/**
	 * Método que verifica carona.
	 * 
	 * @param carona
	 *            - Carona.
	 * @throws MeLevaException
	 *             - Exceções para o caso dos dados inseridos sejam inválidos.
	 */
	public void verificaCarona(Carona carona) throws MeLevaException {
		if (carona == null || carona.equals("")) {
			throw new MeLevaException(MSGDeExcecao.MSG_ERRO_CARONA_INVALIDO);
		}

	}

	/**
	 * Método que verifica o Identificador de uma carona.
	 * 
	 * @param idCarona
	 *            - Identificador da carona.
	 * @throws MeLevaException
	 *             - Exceções para o caso dos dados inseridos sejam inválidos.
	 */
	public void verificaIdCarona(String idCarona) throws MeLevaException {
		if (idCarona == null || idCarona.equals("")) {
			throw new MeLevaException(MSGDeExcecao.MSG_ERRO_IDCARONA_INVALIDO);
		}

	}

	/**
	 * Método verifica o identificador da carona.
	 * 
	 * @param idCarona
	 *            - Identificador.
	 * @throws MeLevaException
	 *             - Exceções para o caso dos dados inseridos sejam inválidos.
	 */
	public void verificaGetCarona(String idCarona) throws MeLevaException {
		if (idCarona == null) {
			throw new MeLevaException(MSGDeExcecao.MSG_ERRO_CARONA_INVALIDO);
		} else if (idCarona.equals("")) {
			throw new MeLevaException(MSGDeExcecao.MSG_ERRO_CARONA_INEXISTENTE);
		}

	}

	/**
	 * Método que verifica o trajeto da carona.
	 * 
	 * @param idCarona
	 *            - Identificador da carona.
	 * @throws MeLevaException
	 *             - Exceções para o caso dos dados inseridos sejam inválidos.
	 */
	public void verificaTrajetoCarona(String idCarona) throws MeLevaException {
		if (idCarona == null) {
			throw new MeLevaException(MSGDeExcecao.MSG_ERRO_TRAJETO_INVALIDO);
		} else if (idCarona.equals("")) {
			throw new MeLevaException(MSGDeExcecao.MSG_ERRO_TRAJETO_INEXISTENTE);
		}

	}

	/**
	 * Método que verifica a origem e destino.
	 * 
	 * @param origem
	 *            - Origem.
	 * @param destino
	 *            - Destino.
	 * @throws MeLevaException
	 *             - Exceções para o caso dos dados inseridos sejam inválidos.
	 */
	public void verificaOrigemDestinoVazio(String origem, String destino)
			throws MeLevaException {
		if (destino.trim().equals("")) {
			throw new MeLevaException(MSGDeExcecao.MSG_ERRO_DESTINO_INVALIDO);
		}
		if (origem.trim().equals("")) {
			throw new MeLevaException(MSGDeExcecao.MSG_ERRO_ORIGEM_INVALIDO);
		}

	}

	/**
	 * Método que verifica o ponto de encontro.
	 * 
	 * @param pontos
	 *            - ponto de encontro.
	 * @throws MeLevaException
	 *             - Exceções para o caso dos dados inseridos sejam inválidos.
	 */
	public void verificaPonto(String pontos) throws MeLevaException {
		if (pontos == null || pontos.trim().equals("")) {
			throw new MeLevaException(MSGDeExcecao.MSG_ERRO_PONTO_INVALIDO);
		}

	}

	/**
	 * Método que verifica o identifacador de solicitação.
	 * 
	 * @param idSolicitacao
	 *            - Identificador da solicitação.
	 * @throws MeLevaException
	 *             - Exceções para o caso dos dados inseridos sejam inválidos.
	 */
	public void verificaIdSolicitacao(String idSolicitacao)
			throws MeLevaException {
		if (idSolicitacao == null || idSolicitacao.trim().equals("")) {
			throw new MeLevaException(
					MSGDeExcecao.MSG_ERRO_ID_SOLICITACAO_INVALIDO);
		}

	}

	/**
	 * Método que verifica se a negociacao é dos respectivos usuarios.
	 * 
	 * @param carona
	 *            - Carona.
	 * @param usuario
	 *            - Usuario.
	 * @param sugestoesPontoDeEncontro
	 *            - Sugestoes de ponto de encontro.
	 * @throws MeLevaException
	 *             - Exceções para o caso dos dados inseridos sejam inválidos.
	 */
	public void verificaContemNegociacao(AbstractCarona carona,
			Usuario usuario,
			Map<Integer, NegociacaoDePonto> sugestoesPontoDeEncontro)
			throws MeLevaException {
		Iterator<NegociacaoDePonto> sugestaoIt = sugestoesPontoDeEncontro
				.values().iterator();

		NegociacaoDePonto nextSugestao = null;
		while (sugestaoIt.hasNext()) {
			nextSugestao = sugestaoIt.next();
			if (nextSugestao.getCarona().equals(carona)
					&& nextSugestao.getUsuario().equals(usuario)) {
				throw new MeLevaException(MSGDeExcecao.MSG_ERRO_PONTO_INVALIDO);
			}
		}

	}

	/**
	 * Método que varifica a carona oferecida.
	 * 
	 * @param userReview
	 *            - Usuario.
	 * @param idCarona
	 *            - Identificador de carona.
	 * @throws MeLevaException
	 *             - Exceções para o caso dos dados inseridos sejam inválidos.
	 */
	public void verificaCaronaOferecida(Usuario userReview, String idCarona)
			throws MeLevaException {
		Iterator<AbstractCarona> caronaOfertada = userReview
				.getCaronasOfertadas().iterator();
		Carona caronaOK = null;
		while (caronaOfertada.hasNext()) {
			Carona carona = (Carona) caronaOfertada.next();
			if (carona.getId().equals(idCarona)) {
				caronaOK = carona;
			}

		}
		if (caronaOK == null) {
			throw new MeLevaException(MSGDeExcecao.MSG_ERRO_CARONA_INEXISTENTE);
		}
	}

	/**
	 * Método que verifica a carona pedida.
	 * 
	 * @param user
	 *            - Usuario.
	 * @param iDCarona
	 *            - Identificador de carona.
	 * @throws MeLevaException
	 *             - Exceções para o caso dos dados inseridos sejam inválidos.
	 */
	public void verificaCaronaPedida(Usuario user, String iDCarona)
			throws MeLevaException {
		Iterator<AbstractCarona> caronaPedida = user.getCaronasPedidas()
				.iterator();
		Carona caronaOK = null;
		while (caronaPedida.hasNext()) {
			Carona carona = (Carona) caronaPedida.next();
			if (carona.getId().equals(iDCarona)) {
				caronaOK = carona;
			}

		}
		if (caronaOK == null) {
			throw new MeLevaException(
					MSGDeExcecao.MSG_ERRO_USUARIO_NAO_POSSUI_VAGA_NA_CARONA);
		}

	}

	/**
	 * Método que verifica a cidade.
	 * 
	 * @param cidade
	 *            - Cidade.
	 * @throws MeLevaException
	 *             - Exceções para o caso dos dados inseridos sejam inválidos.
	 */
	public void verificaCidade(String cidade) throws MeLevaException {
		if (cidade == null || cidade.trim().equals("")) {
			throw new MeLevaException(MSGDeExcecao.MSG_ERRO_CIDADE_INEXISTENTE);
		}

	}

	/**
	 * Método verifica a data.
	 * 
	 * @param data
	 *            - Data.
	 * @throws MeLevaException
	 *             - Exceções para o caso dos dados inseridos sejam inválidos.
	 */
	public void verificaData(String data) throws MeLevaException {
		if (data == null) {
			throw new MeLevaException(MSGDeExcecao.MSG_ERRO_DATA_INVALIDO);
		}

		SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
		Calendar axi = Calendar.getInstance();
		GregorianCalendar calendarioPC = new GregorianCalendar();

		try {
			axi.setTime(formatoData.parse(data));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		String retorno = formatoData.format(axi.getTime());

		// facil para ver se a data é valida ex: 31/02/... não é valido
		// portanto o Calendar transforma para uma data valida que não é a que
		// eu passei
		if (!retorno.equals(data)) {
			throw new MeLevaException(MSGDeExcecao.MSG_ERRO_DATA_INVALIDO);
		}

		if (axi.getTime().before(calendarioPC.getTime())) {
			throw new MeLevaException(MSGDeExcecao.MSG_ERRO_DATA_INVALIDO);
		}

	}

	/**
	 * Método que verifica se esta na hora, entre o inicio e o fim.
	 * 
	 * @param horaInicio
	 *            - Hora inicial.
	 * @param horaCarona
	 *            - Hora da carona oferecida.
	 * @param horaFim
	 *            - Hora do final da espera.
	 * @return Boolean de verificação se esta na hora.
	 */
	public boolean verificaEstaNaHora(String horaInicio, String horaCarona,
			String horaFim) {
		SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");
		Calendar horaI = Calendar.getInstance();
		Calendar horaF = Calendar.getInstance();
		Calendar horaC = Calendar.getInstance();
		try {
			horaI.setTime(formatoHora.parse(horaInicio));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		try {
			horaC.setTime(formatoHora.parse(horaCarona));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		try {
			horaF.setTime(formatoHora.parse(horaFim));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return (horaI.compareTo(horaC) == -1 || horaI.compareTo(horaC) == 0)
				&& (horaC.compareTo(horaF) == -1 || horaC.compareTo(horaF) == 0);

	}

	/**
	 * Verifica se a data é hoje.
	 * 
	 * @param data
	 *            - Data.
	 * @return Boolean identificador para data de hoje.
	 * @throws MeLevaException
	 *             - Exceções para o caso dos dados inseridos sejam inválidos.
	 */
	public boolean verificaEhHoje(String data) throws MeLevaException {
		verificaData(data);
		SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
		Calendar axi = Calendar.getInstance();
		GregorianCalendar calendarioPC = new GregorianCalendar();
		calendarioPC.set(Calendar.HOUR, 0);
		calendarioPC.set(Calendar.MINUTE, 0);
		calendarioPC.set(Calendar.SECOND, 0);
		calendarioPC.set(Calendar.MILLISECOND, 0);

		try {
			axi.setTime(formatoData.parse(data));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return axi.getTime().equals(calendarioPC.getTime());
	}

	/**
	 * Método que verifica a mensagem enviada.
	 * 
	 * @param message
	 *            - Mensagem.
	 * @throws MeLevaException
	 *             - Exceções para o caso dos dados inseridos sejam inválidos.
	 */
	public void verificaMensagem(String message) throws MeLevaException {
		if (message == null) {
			throw new MeLevaException(MSGDeExcecao.MSG_ERRO_MENSSAGEM_INVALIDA);
		}

	}

}
