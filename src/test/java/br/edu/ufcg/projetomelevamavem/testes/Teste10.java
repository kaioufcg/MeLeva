package br.edu.ufcg.projetomelevamavem.testes;

import br.edu.ufcg.projetomelevamavem.controller.MeLevaController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class Teste10 {

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
			Assert.assertEquals(20, meleva.cadastrarCarona("1", "Souza",
					"João Pessoa", "08/07/2012", "14:00", "2"));

		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}

		try {
			Assert.assertEquals(30, meleva.cadastrarCarona("1",
					"Campina Grande", "Patos", "25/07/2012", "8:00", "1"));

		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}

		try {
			Assert.assertEquals(40, meleva.cadastrarCaronaMunicipal("1",
					"Açude Velho", "Shopping Boulevard", "Campina Grande",
					"04/07/2012", "20:00", "2"));

		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}

		try {
			Assert.assertEquals(50, meleva.cadastrarCaronaMunicipal("1",
					"Praça do Cajú", "Villa São Paulo", "João Pessoa",
					"04/07/2012", "20:00", "2"));

		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}

		try {
			Assert.assertEquals(60, meleva.cadastrarCaronaMunicipal("1",
					"Manaíra Shopping", "Parque Solon de Lucena",
					"João Pessoa", "04/07/2012", "14:00", "2"));

		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}

		//
		try {
			Assert.assertEquals("false",
					meleva.getAtributoCarona("10", "ehMunicipal"));

		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}
		try {
			Assert.assertEquals("false",
					meleva.getAtributoCarona("20", "ehMunicipal"));

		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}
		try {
			Assert.assertEquals("false",
					meleva.getAtributoCarona("30", "ehMunicipal"));

		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}
		try {
			Assert.assertEquals("true",
					meleva.getAtributoCarona("40", "ehMunicipal"));

		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}
		try {
			Assert.assertEquals("true",
					meleva.getAtributoCarona("50", "ehMunicipal"));

		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}
		try {
			Assert.assertEquals("true",
					meleva.getAtributoCarona("60", "ehMunicipal"));

		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}

		// Iniciar sessão com outro usuário.
		try {
			Assert.assertEquals("2", meleva.abrirSessao("bill", "bilz@o"));

		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}

		try {
			Assert.assertEquals("{10}", meleva.localizarCarona("2",
					"Campina Grande", "João Pessoa"));
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}

		try {
			Assert.assertEquals("{40}", meleva.localizarCaronaMunicipal("2",
					"Campina Grande", "Açude Velho", "Shopping Boulevard"));
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		try {
			Assert.assertEquals("{50,60}",
					meleva.localizarCaronaMunicipal("2", "João Pessoa"));
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		// Testa possiveis erros no sistema.
		try {
			Assert.assertEquals("{50,60}", meleva.localizarCaronaMunicipal("2",null, 
					"Açude Velho", "Shopping Boulevard"));
			Assert.fail();
		} catch (Exception e1) {
			Assert.assertEquals("Cidade inexistente", e1.getMessage());
		}

		try {
			Assert.assertEquals("{50,60}", meleva.localizarCaronaMunicipal("2",
					"", "Açude Velho", "Shopping Boulevard"));
			Assert.fail();
		} catch (Exception e1) {
			Assert.assertEquals("Cidade inexistente", e1.getMessage());
		}

		meleva.encerrarSistema();
		//meleva.quit();
	}
}
