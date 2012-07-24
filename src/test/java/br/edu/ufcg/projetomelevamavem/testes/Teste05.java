package br.edu.ufcg.projetomelevamavem.testes;

import br.edu.ufcg.projetomelevamavem.controller.MeLevaController;
import br.edu.ufcg.projetomelevamavem.logicaSistema.MeLevaException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class Teste05 {
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
			Assert.assertEquals("1", meleva.abrirSessao("mark", "m@rk"));
		} catch (Exception e1) {

			e1.printStackTrace();
		}

		// cadastra
		try {
			Assert.assertEquals(10, meleva.cadastrarCarona("1", "Cajazeiras",
					"Patos", "20/07/2012", "14:00", "4"));
		} catch (Exception e1) {

			e1.printStackTrace();
		}
		try {
			Assert.assertEquals(20, meleva.cadastrarCarona("1",
					"São Francisco", "Palo Alto", "12/09/2012", "21:00", "2"));
		} catch (Exception e1) {

			e1.printStackTrace();
		}
		try {
			Assert.assertEquals(30, meleva
					.cadastrarCarona("1", "Campina Grande", "João Pessoa",
							"01/07/2012", "12:00", "1"));
		} catch (Exception e1) {

			e1.printStackTrace();
		}
		try {
			Assert.assertEquals(40, meleva
					.cadastrarCarona("1", "Campina Grande", "João Pessoa",
							"02/07/2012", "12:00", "3"));
		} catch (Exception e1) {

			e1.printStackTrace();
		}
		try {
			Assert.assertEquals(50, meleva
					.cadastrarCarona("1", "Campina Grande", "João Pessoa",
							"04/07/2012", "16:00", "2"));
		} catch (Exception e1) {

			e1.printStackTrace();
		}
		try {
			Assert.assertEquals(60, meleva.cadastrarCarona("1", "Leeds",
					"Londres", "10/02/2013", "10:00", "3"));
		} catch (Exception e1) {

			e1.printStackTrace();
		}
		// Encerrar a sessao de mark.
		try {
			Assert.assertEquals(true, meleva.encerrarSessao("mark"));
		} catch (MeLevaException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		// Iniciar sessão com outro usuário.
		try {
			meleva.criarUsuario("bill", "billz@o", "William Henry Gates III",
					"Medina, Washington", "billzin@gmail.com");
		} catch (Exception e1) {

			e1.printStackTrace();
		}
		try {
			Assert.assertEquals("2", meleva.abrirSessao("bill", "billz@o"));
		} catch (Exception e1) {

			e1.printStackTrace();
		}

		// Requisitar vaga na carona.
		try {
			Assert.assertEquals(100, meleva.solicitarVaga("2", "40"));
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
			Assert.assertEquals("William Henry Gates III",
					meleva.getAtributoSolicitacao("100", "Dono da solicitacao"));
		} catch (Exception e1) {

			e1.printStackTrace();
		}

		// Iniciar sessão.
		try {
			Assert.assertEquals("1", meleva.abrirSessao("mark", "m@rk"));
		} catch (Exception e1) {

			e1.printStackTrace();
		}

		// Aceitar requisição
		try {
			meleva.aceitarSolicitacao("1", "100");
		} catch (Exception e1) {

			e1.printStackTrace();
		}
		try {
			Assert.assertEquals("2", meleva.getAtributoCarona("40", "vagas"));
		} catch (Exception e1) {

			e1.printStackTrace();
		}

		// Tentar aceitar novamente a requisição
		try {
			meleva.aceitarSolicitacao("1", "100");

		} catch (Exception e) {
			Assert.assertEquals("Solicitação inexistente", e.getMessage());
		}
		try {
			Assert.assertEquals("2", meleva.getAtributoCarona("40", "vagas"));
		} catch (Exception e1) {

			e1.printStackTrace();
		}

		// Requisitar vaga na carona.
		// por que na carona 5 ja começa com solicitacao 2 se é a primeira vez
		// que ele recebe
		try {
			Assert.assertEquals(200, meleva.solicitarVaga("2", "50"));
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
			Assert.assertEquals("William Henry Gates III",
					meleva.getAtributoSolicitacao("200", "Dono da solicitacao"));
		} catch (Exception e1) {

			e1.printStackTrace();
		}

		// Rejeitar requisição
		try {
			meleva.rejeitarSolicitacao("1", "200");
		} catch (Exception e1) {

			e1.printStackTrace();
		}
		try {
			Assert.assertEquals("2", meleva.getAtributoCarona("50", "vagas"));
		} catch (Exception e1) {

			e1.printStackTrace();
		}

		// Tentar rejeitar novamente a requisição
		// o problema é que essa solicatacao pedida ao pode ser tanto do carona
		// 5ID como do
		try {
			meleva.rejeitarSolicitacao("1", "20");
			Assert.fail();
		} catch (Exception e) {

			Assert.assertEquals("Solicitação inexistente", e.getMessage());
		}

		try {
			Assert.assertEquals("2", meleva.getAtributoCarona("50", "vagas"));
		} catch (Exception e) {

			e.printStackTrace();
		}
		
		try {
			meleva.visualizarPerfil("1", "mark");
		} catch (MeLevaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Assert.assertEquals("Mark Zuckerberg",meleva.getAtributoPerfil("mark", "nome"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Assert.assertEquals("Palo Alto, California",meleva.getAtributoPerfil("mark", "endereco"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Assert.assertEquals("mark@facebook.com",meleva.getAtributoPerfil("mark", "email"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Assert.assertEquals("[10, 20, 30, 40, 50, 60]",meleva.getAtributoPerfil("mark", "historico de caronas"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Assert.assertEquals("[]",meleva.getAtributoPerfil("mark", "historico de vagas em caronas"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Assert.assertEquals("[40]",meleva.getAtributoPerfil("bill", "historico de vagas em caronas"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Assert.assertEquals("0",meleva.getAtributoPerfil("mark", "caronas seguras e tranquilas"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Assert.assertEquals("0",meleva.getAtributoPerfil("mark", "caronas que não funcionaram"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Assert.assertEquals("0",meleva.getAtributoPerfil("mark", "faltas em vagas de caronas"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Assert.assertEquals("0",meleva.getAtributoPerfil("mark", "presenças em vagas de caronas"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
