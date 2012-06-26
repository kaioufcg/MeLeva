package br.edu.ufcg.projetomelevamavem.testes;

import br.edu.ufcg.projetomelevamavem.controller.MeLevaController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class Teste08 {

	private MeLevaController meleva;

	@Before
	public void testeJU() {
		meleva = MeLevaController.getInstance();
		// Limpar os dados do sistema.
		meleva.zerarSistema();
	}

	@Test
	public void test() {
		// Limpar os dados do sistema.
		meleva.zerarSistema();
		// Criar usuário.

		try {
			meleva.criarUsuario("mark", "m@rk", "Mark Zuckerberg",
					"Palo Alto, California", "mark@facebook.com");

		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}
		try {
			meleva.criarUsuario("bill", "bilz@o", "William Henry Gates III",
					"Medina, Washington", "billzin@gmail.com");

		} catch (Exception e1) {
			System.out.println(e1.getLocalizedMessage());
		}

		try {
			meleva.criarUsuario("vader", "d4rth", "Anakin Skywalker",
					"Death Star I", "darthvader@empire.com");

		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}
		// Iniciar sessão.
		try {
			Assert.assertEquals("1", meleva.abrirSessao("mark", "m@rk"));

		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}

		// Cadastrar caronas.

		try {
			Assert.assertEquals(10, meleva
					.cadastrarCarona("1", "Campina Grande", "João Pessoa",
							"02/07/2012", "12:00", "3"));

		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}
		try {
			Assert.assertEquals(20, meleva
					.cadastrarCarona("1", "Campina Grande", "João Pessoa",
							"04/07/2012", "16:00", "2"));

		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}

		// Iniciar sessão com outro usuário.
		try {
			Assert.assertEquals("2", meleva.abrirSessao("bill", "bilz@o"));

		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}

		// Requisitar vaga na carona.
		try {
			Assert.assertEquals(100, meleva.solicitarVaga("2", "10"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		try {
			Assert.assertEquals("Campina Grande",
					meleva.getAtributoSolicitacao("100", "origem"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		try {
			Assert.assertEquals("João Pessoa",
					meleva.getAtributoSolicitacao("100", "destino"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		try {
			Assert.assertEquals("Mark Zuckerberg",
					meleva.getAtributoSolicitacao("100", "Dono da carona"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		try {
			Assert.assertEquals("William Henry Gates III",
					meleva.getAtributoSolicitacao("100", "Dono da solicitacao"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		// Aceitar requisição
		try {
			meleva.aceitarSolicitacao("1", "100");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		try {
			//duas por que eu aceitei a diminuo o numero de vagas
			Assert.assertEquals("2", meleva.getAtributoCarona("10", "vagas"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		// Requisitar vaga na carona.
		try {
			Assert.assertEquals(200, meleva.solicitarVaga("2", "20"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		try {
			Assert.assertEquals("Campina Grande",
					meleva.getAtributoSolicitacao("200", "origem"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		try {
			Assert.assertEquals("João Pessoa",
					meleva.getAtributoSolicitacao("200", "destino"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		try {
			Assert.assertEquals("Mark Zuckerberg",
					meleva.getAtributoSolicitacao("200", "Dono da carona"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		try {
			Assert.assertEquals("William Henry Gates III",
					meleva.getAtributoSolicitacao("200", "Dono da solicitacao"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		// Aceitar requisição
		try {
			meleva.aceitarSolicitacao("1", "200");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		try {
			Assert.assertEquals("1", meleva.getAtributoCarona("20", "vagas"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		// Review de carona. Depois da viagem
		try {
			meleva.reviewVagaEmCarona("1", "20", "bill", "faltou");
		} catch (Exception e3) {
			System.out.println(e3.getMessage() + "aki aki");
		}
		try {
			Assert.assertEquals("Perfil de Bill: \n"
					+ "Nome: William Henry Gates III\n"
					+ "Email: billzin@gmail.com\n"
					+ "Endereço: Medina, Washington",
					meleva.visualizarPerfil("2", "bill"));
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}
		try {
			Assert.assertEquals("William Henry Gates III",
					meleva.getAtributoPerfil("bill", "nome"));
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}
		try {
			Assert.assertEquals("Medina, Washington",
					meleva.getAtributoPerfil("bill", "endereco"));
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}
		try {
			Assert.assertEquals("billzin@gmail.com",
					meleva.getAtributoPerfil("bill", "email"));
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}
		try {
			Assert.assertEquals("[10,20]", meleva.getAtributoPerfil("bill",
					"historico de vagas em caronas"));
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}
		try {
			Assert.assertEquals("[]",
					meleva.getAtributoPerfil("bill", "historico de caronas"));
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}
		try {
			Assert.assertEquals("0", meleva.getAtributoPerfil("bill",
					"caronas seguras e tranquilas"));
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}
		try {
			Assert.assertEquals("0", meleva.getAtributoPerfil("bill",
					"caronas que não funcionaram"));
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}
		try {
			Assert.assertEquals("1", meleva.getAtributoPerfil("bill",
					"faltas em vagas de caronas"));
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}
		try {
			Assert.assertEquals("0", meleva.getAtributoPerfil("bill",
					"presenças em vagas de caronas"));
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}

		// Segunda carona que mark ofereceu
		try {
			meleva.reviewVagaEmCarona("1", "20", "bill", "não faltou");
			//Assert.fail();
		} catch (Exception e3) {
			System.out.println(e3.getMessage());
		}
		try {
			Assert.assertEquals("Perfil de Bill: \n"
					+ "Nome: William Henry Gates III\n"
					+ "Email: billzin@gmail.com\n"
					+ "Endereço: Medina, Washington",
					meleva.visualizarPerfil("2", "bill"));
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}
		try {
			Assert.assertEquals("William Henry Gates III",
					meleva.getAtributoPerfil("bill", "nome"));
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}
		try {
			Assert.assertEquals("Medina, Washington",
					meleva.getAtributoPerfil("bill", "endereco"));
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}
		try {
			Assert.assertEquals("billzin@gmail.com",
					meleva.getAtributoPerfil("bill", "email"));
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}
		try {
			Assert.assertEquals("[10,20]", meleva.getAtributoPerfil("bill",
					"historico de vagas em caronas"));
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}
		try {
			Assert.assertEquals("[]",
					meleva.getAtributoPerfil("bill", "historico de caronas"));
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}
		try {
			Assert.assertEquals("0", meleva.getAtributoPerfil("bill",
					"caronas seguras e tranquilas"));
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}
		try {
			Assert.assertEquals("0", meleva.getAtributoPerfil("bill",
					"caronas que não funcionaram"));
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}
		try {
			Assert.assertEquals("1", meleva.getAtributoPerfil("bill",
					"faltas em vagas de caronas"));
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}
		try {
			Assert.assertEquals("1", meleva.getAtributoPerfil("bill",
					"presenças em vagas de caronas"));
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}

		// possiveis erros
		try {

			meleva.reviewVagaEmCarona("1", "20", "bill", "não dou mais carona");
		} catch (Exception e2) {
			Assert.assertEquals("Opção inválida.", e2.getMessage());
		}

		// Iniciar sessão com outro usuário.
		try {
			Assert.assertEquals("3", meleva.abrirSessao("vader", "d4rth"));
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}

		try {
			meleva.reviewVagaEmCarona("1", "20", "vader",
					"não funcionou");
			Assert.fail();
		} catch (Exception e) {
			Assert.assertEquals("Usuário não possui vaga na carona.",
					e.getMessage());
		}

		meleva.encerrarSistema();
		//meleva.quit();
	}
}
