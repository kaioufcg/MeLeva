package br.edu.ufcg.projetomelevamavem.testes;

import br.edu.ufcg.projetomelevamavem.controller.MeLevaController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class Teste09 {

	private MeLevaController meleva;

	@Before
	public void testeJU() {
		meleva = MeLevaController.getInstance();
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
			// duas por que eu aceitei a diminuo o numero de vagas
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
			meleva.reviewCarona("2", "10", "segura e tranquila");
		} catch (Exception e3) {
			System.out.println(e3.getMessage() + "aki aki");
		}
		try {
			Assert.assertEquals("Perfil de Mark: \n"
					+ "Nome: Mark Zuckerberg\n" + "Email: mark@facebook.com\n"
					+ "Endereço: Palo Alto, California",
					meleva.visualizarPerfil("1", "mark"));
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}
		try {
			Assert.assertEquals("Mark Zuckerberg",
					meleva.getAtributoPerfil("mark", "nome"));
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}
		try {
			Assert.assertEquals("Palo Alto, California",
					meleva.getAtributoPerfil("mark", "endereco"));
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}
		try {
			Assert.assertEquals("mark@facebook.com",
					meleva.getAtributoPerfil("mark", "email"));
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}
		try {
			Assert.assertEquals("[]", meleva.getAtributoPerfil("mark",
					"historico de vagas em caronas"));
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}
		try {
			Assert.assertEquals("[10, 20]",
					meleva.getAtributoPerfil("mark", "historico de caronas"));
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}
		try {
			Assert.assertEquals("1", meleva.getAtributoPerfil("mark",
					"caronas seguras e tranquilas"));
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}
		try {
			Assert.assertEquals("0", meleva.getAtributoPerfil("mark",
					"caronas que não funcionaram"));
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}
		try {
			Assert.assertEquals("0", meleva.getAtributoPerfil("mark",
					"faltas em vagas de caronas"));
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}
		try {
			Assert.assertEquals("0", meleva.getAtributoPerfil("mark",
					"presenças em vagas de caronas"));
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}

		// Segunda carona que mark ofereceu
		try {
			meleva.reviewCarona("2", "20", "não funcionou");
			// Assert.fail();
		} catch (Exception e3) {
			System.out.println(e3.getMessage());
		}
		try {
			Assert.assertEquals("Perfil de Mark: \n"
					+ "Nome: Mark Zuckerberg\n" + "Email: mark@facebook.com\n"
					+ "Endereço: Palo Alto, California",
					meleva.visualizarPerfil("1", "mark"));
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}
		try {
			Assert.assertEquals("Mark Zuckerberg",
					meleva.getAtributoPerfil("mark", "nome"));
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}
		try {
			Assert.assertEquals("Palo Alto, California",
					meleva.getAtributoPerfil("mark", "endereco"));
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}
		try {
			Assert.assertEquals("mark@facebook.com",
					meleva.getAtributoPerfil("mark", "email"));
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}
		try {
			Assert.assertEquals("[]", meleva.getAtributoPerfil("mark",
					"historico de vagas em caronas"));
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}
		try {
			Assert.assertEquals("[10, 20]",
					meleva.getAtributoPerfil("mark", "historico de caronas"));
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}
		try {
			Assert.assertEquals("1", meleva.getAtributoPerfil("mark",
					"caronas seguras e tranquilas"));
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}
		try {
			Assert.assertEquals("1", meleva.getAtributoPerfil("mark",
					"caronas que não funcionaram"));
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}
		try {
			Assert.assertEquals("0", meleva.getAtributoPerfil("mark",
					"faltas em vagas de caronas"));
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}
		try {
			Assert.assertEquals("0", meleva.getAtributoPerfil("mark",
					"presenças em vagas de caronas"));
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}

		// possiveis erros
		try {

			meleva.reviewCarona("2", "20", "bacana");
			Assert.fail();
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
			meleva.reviewCarona("3", "20", "não funcionou");
			Assert.fail();
		} catch (Exception e) {
			Assert.assertEquals("Usuário não possui vaga na carona.", e.getMessage());
		}

		meleva.encerrarSistema();
		//meleva.quit();
	}
}
