package br.edu.ufcg.projetomelevamavem.testes;

import br.edu.ufcg.projetomelevamavem.controller.MeLevaController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;




public class Teste12 {
	MeLevaController meleva;

	@Before
	public void testeJU() {
		meleva = MeLevaController.getInstance();
	}

	@Test
	public void testCriaUsuario() {
		// Limpar os dados do sistema.
		meleva.zerarSistema();

		// Criar usuário.

		try {
			meleva.criarUsuario("mark", "m@rk", "Mark Zuckerberg",
					"Palo Alto, California", "mark@facebook.com");
		} catch (Exception e1) {

			e1.printStackTrace();
		}

		try {
			meleva.criarUsuario("bill", "bilz@o", "Bill Clinton",
					"Hollywood, California", "bill@gmail.com");
		} catch (Exception e1) {

			e1.printStackTrace();
		}

		// Iniciar sessão.

		try {
			Assert.assertEquals("1", meleva.abrirSessao("mark", "m@rk"));
		} catch (Exception e1) {

			e1.printStackTrace();
		}

		// Cadastrar caronas.
		try {
			Assert.assertEquals(10, meleva
					.cadastrarCarona("1", "Campina Grande", "João Pessoa",
							"02/07/2012", "12:00", "1"));
		} catch (Exception e1) {

			e1.printStackTrace();
		}
		try {
			Assert.assertEquals(20, meleva
					.cadastrarCarona("1", "Campina Grande", "João Pessoa",
							"04/07/2012", "16:00", "2"));
		} catch (Exception e1) {

			e1.printStackTrace();
		}
		// Iniciar sessão com outro usuário.
		try {
			Assert.assertEquals("2", meleva.abrirSessao("bill", "bilz@o"));
		} catch (Exception e1) {

			e1.printStackTrace();
		}
		// Requisitar vaga na carona.
		try {
			Assert.assertEquals(100, meleva.solicitarVaga("2", "10"));
		} catch (Exception e1) {

			e1.printStackTrace();
		}
		try {
			Assert.assertEquals("Campina Grande",
					meleva.getAtributoSolicitacao("100", "origem"));
		} catch (Exception e1) {

			e1.printStackTrace();
		}
		try {
			Assert.assertEquals("João Pessoa",
					meleva.getAtributoSolicitacao("100", "destino"));
		} catch (Exception e1) {

			e1.printStackTrace();
		}
		try {
			Assert.assertEquals("Mark Zuckerberg",
					meleva.getAtributoSolicitacao("100", "Dono da carona"));
		} catch (Exception e1) {

			e1.printStackTrace();
		}
		try {
			Assert.assertEquals("Bill Clinton",
					meleva.getAtributoSolicitacao("100", "Dono da solicitacao"));
		} catch (Exception e1) {

			e1.printStackTrace();
		}

		// Enviar email
		try {
			Assert.assertTrue(meleva.enviarEmail("1", "bill@gmail.com",
					"A solicitação foi recebida"));
		} catch (Exception e1) {

			e1.printStackTrace();
		}

		// Aceitar requisição
		try {
			meleva.aceitarSolicitacaoPontoEncontro("1", "100");
		} catch (Exception e1) {

			e1.printStackTrace();
		}

		try {
			Assert.assertEquals("0", meleva.getAtributoCarona("10", "vagas"));
		} catch (Exception e1) {

			e1.printStackTrace();
		}

		// Enviar email
		try {
			Assert.assertTrue(meleva.enviarEmail("1", "bill@gmail.com",
					"A carona foi confirmada"));
		} catch (Exception e1) {

			e1.printStackTrace();
		}

		// Requisitar vaga na carona.
		try {
			Assert.assertEquals(200, meleva.solicitarVaga("2", "20"));
		} catch (Exception e1) {

			e1.printStackTrace();
		}
		try {
			Assert.assertEquals("Campina Grande",
					meleva.getAtributoSolicitacao("200", "origem"));
		} catch (Exception e1) {

			e1.printStackTrace();
		}
		try {
			Assert.assertEquals("João Pessoa",
					meleva.getAtributoSolicitacao("200", "destino"));
		} catch (Exception e1) {

			e1.printStackTrace();
		}
		try {
			Assert.assertEquals("Mark Zuckerberg",
					meleva.getAtributoSolicitacao("200", "Dono da carona"));
		} catch (Exception e1) {

			e1.printStackTrace();
		}
		try {
			Assert.assertEquals("Bill Clinton",
					meleva.getAtributoSolicitacao("200", "Dono da solicitacao"));
		} catch (Exception e1) {

			e1.printStackTrace();
		}

		// Enviar email
		try {
			Assert.assertTrue(meleva.enviarEmail("1", "bill@gmail.com",
					"A solicitação foi recebida"));
		} catch (Exception e1) {

			e1.printStackTrace();
		}

		// Rejeitar requisição
		try {
			meleva.rejeitarSolicitacao("2", "200");
		} catch (Exception e1) {

			e1.printStackTrace();
		}
		try {
			Assert.assertEquals("2", meleva.getAtributoCarona("20", "vagas"));
		} catch (Exception e1) {

			e1.printStackTrace();
		}

		// Enviar email
		try {
			Assert.assertTrue(meleva.enviarEmail("1", "bill@gmail.com",
					"A carona foi rejeitada por falta de vaga"));
		} catch (Exception e1) {

			e1.printStackTrace();
		}

		meleva.encerrarSistema();
		//meleva.quit();

	}
}
