package br.edu.ufcg.projetomelevamavem.testes;

import br.edu.ufcg.projetomelevamavem.controller.MeLevaController;
import br.edu.ufcg.projetomelevamavem.logicaSistema.MeLevaException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class Teste04 {

	private MeLevaController meleva;

	@Before
	public void testeJU04() {
		meleva = MeLevaController.getInstance();
	}

	@Test
	public void test() {
		// User Story 04 - Solicitação de vagas com marcação de ponto de
		// encontro

		/*
		 * US04 - Solicitação de vagas. Permitir a solicitação de uma vaga em
		 * uma carona disponível. Este processo envolve os seguintes passos: 1.
		 * Requisitar vaga. Um usuário pode solicitar uma vaga em qualquer
		 * carona disponível no sistema. 2. Usuário sugerir ponto de encontro 3.
		 * Aprovar vaga ou segerir novo lugar. O usuário que cadastrou a carona
		 * recebe a solicitação e pode aprová-la ou sugerir um outro ponto de
		 * encontro. Ao aprová-la a quantidade de vagas disponíveis na carona é
		 * atualizada. 4. Se for sugerido um novo ponto de encontro pelo usuário
		 * que cadastrou a carona o solicitante pode aprovar a carona ou
		 * recusar.
		 */

		// Limpar os dados do sistema.
		meleva.zerarSistema();

		// Criar usuário.
		try {
			meleva.criarUsuario("mark", "m@rk", "Mark Zuckerberg",
					"Palo Alto, California", "mark@facebook.com");
		} catch (Exception e1) {
			System.err.println(e1.getMessage());
		}

		// Iniciar sessão.
		try {
			Assert.assertEquals("1", meleva.abrirSessao("mark", "m@rk"));
		} catch (Exception e1) {
			System.err.println(e1.getMessage());
		}

		// Cadastrar caronas.
		try {
			Assert.assertEquals(10, meleva.cadastrarCarona("1", "Cajazeiras",
					"Patos", "20/07/2012", "14:00", "4"));
		} catch (Exception e1) {
			System.err.println(e1.getMessage());
		}
		try {
			Assert.assertEquals(20, meleva.cadastrarCarona("1",
					"São Francisco", "Palo Alto", "12/09/2012", "21:00", "2"));
		} catch (Exception e1) {
			System.err.println(e1.getMessage());
		}
		try {
			Assert.assertEquals(30, meleva
					.cadastrarCarona("1", "Campina Grande", "João Pessoa",
							"01/07/2012", "12:00", "1"));
		} catch (Exception e1) {
			System.err.println(e1.getMessage());
		}
		try {
			Assert.assertEquals(40, meleva
					.cadastrarCarona("1", "Campina Grande", "João Pessoa",
							"02/07/2012", "12:00", "3"));
		} catch (Exception e1) {
			System.err.println(e1.getMessage());
		}
		try {
			Assert.assertEquals(50, meleva
					.cadastrarCarona("1", "Campina Grande", "João Pessoa",
							"04/07/2012", "16:00", "2"));
		} catch (Exception e1) {
			System.err.println(e1.getMessage());
		}
		try {
			Assert.assertEquals(60, meleva.cadastrarCarona("1", "Leeds",
					"Londres", "10/02/2013", "10:00", "3"));
		} catch (Exception e1) {
			System.err.println(e1.getMessage());
		}

		// Encerrar a sessao de mark.
		try {
			Assert.assertEquals(true, meleva.encerrarSessao("mark"));
		} catch (MeLevaException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}

		// Iniciar sessão com outro usuário.
		try {
			meleva.criarUsuario("bill", "billz@o", "William Henry Gates III",
					"Medina, Washington", "billzin@gmail.com");
		} catch (Exception e1) {
			System.err.println(e1.getMessage());
		}
		try {
			Assert.assertEquals("2", meleva.abrirSessao("bill", "billz@o"));
		} catch (Exception e1) {
			System.err.println(e1.getMessage());
		}

		// Sugerir ponto de encontro para uma carona

		try {
			Assert.assertEquals(100, meleva.sugerirPontoEncontro("2", "40",
					"Acude Velho; Hiper Bompreco"));
		} catch (Exception e1) {
			System.err.println(e1.getMessage());
		}
		try {
			Assert.assertEquals(true, meleva.encerrarSessao("bill"));
		} catch (MeLevaException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}

		// Resposta a requisicao

		try {
			Assert.assertEquals("1", meleva.abrirSessao("mark", "m@rk"));
		} catch (Exception e1) {
			System.err.println(e1.getMessage());
		}
		try {
			meleva.responderSugestaoPontoEncontro("1", "40", "100",
					"Acude Velho;Parque da Crianca");
		} catch (Exception e1) {
			System.err.println(e1.getMessage());
		}
		try {
			Assert.assertEquals(true, meleva.encerrarSessao("mark"));
		} catch (MeLevaException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}

		// Requisitar vaga na carona.

		try {
			Assert.assertEquals("2", meleva.abrirSessao("bill", "billz@o"));
		} catch (Exception e1) {
			System.err.println(e1.getMessage());
		}
		try {
			// 1000
			Assert.assertEquals(300,
					meleva.solicitarVagaPontoEncontro("2", "40", "Acude Velho"));
		} catch (Exception e1) {
			System.err.println(e1.getMessage());
		}
		try {
			// aki null
			Assert.assertEquals("Campina Grande",
					meleva.getAtributoSolicitacao("300", "origem"));
		} catch (Exception e1) {
			System.err.println(e1.getMessage());
		}
		try {
			Assert.assertEquals("João Pessoa",
					meleva.getAtributoSolicitacao("300", "destino"));
		} catch (Exception e1) {
			System.err.println(e1.getMessage());
		}
		try {
			Assert.assertEquals("Mark Zuckerberg",
					meleva.getAtributoSolicitacao("300", "Dono da carona"));
		} catch (Exception e1) {
			System.err.println(e1.getMessage());
		}
		try {
			Assert.assertEquals("William Henry Gates III",
					meleva.getAtributoSolicitacao("300", "Dono da solicitacao"));
		} catch (Exception e1) {
			System.err.println(e1.getMessage());
		}
		try {
			Assert.assertEquals("Acude Velho",
					meleva.getAtributoSolicitacao("300", "Ponto de Encontro"));
		} catch (Exception e1) {
			System.err.println("aki" + e1.getMessage());
		}

		try {
			meleva.encerrarSessao("bill");
		} catch (MeLevaException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}

		// Aceitar requisição
		try {
			Assert.assertEquals("1", meleva.abrirSessao("mark", "m@rk"));
		} catch (Exception e1) {
			System.err.println(e1.getMessage());
		}
		try {
			meleva.aceitarSolicitacaoPontoEncontro("1", "300");
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			System.out.println(e2.getMessage());
		}
		try {
			// 2
			Assert.assertEquals("2", meleva.getAtributoCarona("40", "vagas"));
		} catch (Exception e1) {
			System.err.println(e1.getMessage());
		}
		try {
			Assert.assertEquals("Acude Velho",
					meleva.getAtributoCarona("40", "Ponto de Encontro"));
		} catch (Exception e1) {
			System.err.println(e1.getMessage());
		}

		// Tentar aceitar novamente a requisição

		try {
			Assert.assertEquals("2", meleva.abrirSessao("bill", "billz@o"));
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		try {
			meleva.aceitarSolicitacao("2", "1000");
			Assert.fail();
		} catch (Exception e) {
			Assert.assertEquals("Solicitação inexistente", e.getMessage());
		}

		try {
			Assert.assertEquals("2", meleva.getAtributoCarona("40", "vagas"));
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		// Sugerir ponto de encontro para uma carona

		try {
			// //////////////////////////Eu posso dar outra sugestao se naum for
			// 300
			Assert.assertEquals(400, meleva.sugerirPontoEncontro("2", "50",
					"Acude Velho; Hiper Bompreco"));
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		// Resposta a requisicao
		try {
			Assert.assertEquals("1", meleva.abrirSessao("mark", "m@rk"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		try {
			Assert.assertEquals(500, meleva.responderSugestaoPontoEncontro("1",
					"40", "400", "Acude Velho;Parque da Crianca"));
		} catch (Exception e) {
			System.err.println(e.getMessage());

		}

		// Requisitar vaga na carona.
		try {
			meleva.desistirRequisicao("2", "40", "100");
		} catch (MeLevaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			Assert.assertEquals(500, meleva.sugerirPontoEncontro("2", "40",
					"Acude Velho; Hiper Bompreco"));
			Assert.fail();
		} catch (Exception e1) {
			Assert.assertEquals("Ponto Inválido", e1.getMessage());
		}
		try {
			Assert.assertEquals(600,
					meleva.responderSugestaoPontoEncontro("1", "40", "100", ""));
			Assert.fail();
		} catch (Exception e1) {
			Assert.assertEquals("Ponto Inválido", e1.getMessage());
		}

		try {
			meleva.encerrarSessao("mark");
		} catch (MeLevaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			meleva.encerrarSessao("bill");
		} catch (MeLevaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		meleva.encerrarSistema();
		//meleva.quit();

	}

}
