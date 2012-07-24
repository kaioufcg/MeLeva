package br.edu.ufcg.projetomelevamavem.testes;

import br.edu.ufcg.projetomelevamavem.controller.MeLevaController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class Teste02 {

	private MeLevaController meleva;

	@Before
	public void testeJU02() {
		meleva = MeLevaController.getInstance();
	}

	@Test
	public void test() {
		// User Story 02 - Cadastro de Caronas

		meleva.zerarSistema();

		// entradas válidas
		try {
			meleva.criarUsuario("mark", "m@rk", "Mark Zuckerberg",
					"Palo Alto, California", "mark@facebook.com");
		} catch (Exception e) {
			e.getMessage();
		}
		try {
			Assert.assertEquals("1", meleva.abrirSessao("mark", "m@rk"));
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.getMessage();
		}

		// Localizar carona, sem carona cadastrada.
		try {
			Assert.assertEquals("{}", meleva.localizarCarona("1",
					"Campina Grande", "João Pessoa"));
		} catch (Exception e) {
			e.getMessage();
		}
		try {
			Assert.assertEquals("{}",
					meleva.localizarCarona("1", "São Francisco", "Palo Alto"));
		} catch (Exception e) {
			e.getMessage();
		}
		try {
			Assert.assertEquals("{}",
					meleva.localizarCarona("1", "Rio de Janeiro", "São Paulo"));
		} catch (Exception e) {
			e.getMessage();
		}

		// o método cadastrar carona retorna id
		try {
			Assert.assertEquals("1", meleva.abrirSessao("mark", "m@rk"));
		} catch (Exception e1) {
			e1.getMessage();
		}
		try {
			Assert.assertEquals(10, meleva
					.cadastrarCarona("1", "Campina Grande", "João Pessoa",
							"23/06/2012", "16:00", "3"));
		} catch (Exception e1) {
			e1.getMessage();
		}
		try {
			Assert.assertEquals("Campina Grande",
					meleva.getAtributoCarona("10", "origem"));
		} catch (Exception e) {
			e.getMessage();
		}
		try {
			Assert.assertEquals("João Pessoa",
					meleva.getAtributoCarona("10", "destino"));
		} catch (Exception e) {
			e.getMessage();
		}
		try {
			Assert.assertEquals("Campina Grande - João Pessoa",
					meleva.getTrajeto("10"));
		} catch (Exception e) {
			e.getMessage();
		}
		// ////////////////////////////////////////////////
		try {
			Assert.assertEquals(20, meleva.cadastrarCarona("1",
					"Rio de Janeiro", "São Paulo", "30/06/2012", "08:00", "2"));
		} catch (Exception e1) {
			e1.getMessage();
		}
		try {
			Assert.assertEquals("30/06/2012",
					meleva.getAtributoCarona("20", "data"));
		} catch (Exception e) {
			e.getMessage();
		}
		try {
			Assert.assertEquals("2", meleva.getAtributoCarona("20", "vagas"));
		} catch (Exception e) {
			e.getMessage();
		}
		// //////////////////////////////////////////////////////////
		try {
			Assert.assertEquals(30, meleva.cadastrarCarona("1", "João Pessoa",
					"Campina Grande", "25/11/2026", "06:59", "4"));
		} catch (Exception e1) {
			e1.getMessage();
		}
		try {
			Assert.assertEquals(
					"João Pessoa para Campina Grande, no dia 25/11/2026, as 06:59",
					meleva.getCarona("30"));
		} catch (Exception e) {
			e.getMessage();
		}
		// ///////////////////////////////////////////////////////////
		try {
			Assert.assertEquals(40, meleva.cadastrarCarona("1", "João Pessoa",
					"Lagoa Seca", "25/11/2016", "05:00", "4"));
		} catch (Exception e1) {
			e1.getMessage();
		}
		try {
			Assert.assertEquals(
					"João Pessoa para Lagoa Seca, no dia 25/11/2016, as 05:00",
					meleva.getCarona("40"));
		} catch (Exception e) {
			e.getMessage();
		}

		// ///////////////////////////////////////////////////////////////////////
		try {
			Assert.assertEquals(50, meleva.cadastrarCarona("1", "João Pessoa",
					"Lagoa Seca", "25/11/2017", "05:00", "4"));
		} catch (Exception e1) {
			e1.getMessage();
		}
		try {
			Assert.assertEquals(
					"João Pessoa para Lagoa Seca, no dia 25/11/2017, as 05:00",
					meleva.getCarona("50"));
		} catch (Exception e) {
			e.getMessage();
		}

		// Localizar carona.
		try {
			Assert.assertEquals("{}",
					meleva.localizarCarona("1", "São Francisco", "Palo Alto"));
		} catch (Exception e) {
			e.getMessage();
		}
		try {
			Assert.assertEquals("{20}",
					meleva.localizarCarona("1", "Rio de Janeiro", "São Paulo"));
		} catch (Exception e) {
			e.getMessage();
		}
		try {
			Assert.assertEquals("{30}", meleva.localizarCarona("1",
					"João Pessoa", "Campina Grande"));
		} catch (Exception e) {
			e.getMessage();
		}

		// Todas as caronas que irá acontecer e que tem como origem João pessoa.
		try {
			Assert.assertEquals("{30,40,50}",
					meleva.localizarCarona("1", "João Pessoa", ""));
		} catch (Exception e) {
			e.getMessage();
		}

		// Todas as caronas que irá acontecer e que tem como destino São Paulo.
		try {
			Assert.assertEquals("{20}",
					meleva.localizarCarona("1", "", "São Paulo"));
		} catch (Exception e) {
			e.getMessage();
		}

		// Todas as caronas que irá acontecer.
		try {
			Assert.assertEquals("{10,20,30,40,50}",
					meleva.localizarCarona("1", "", ""));
		} catch (Exception e) {
			e.getMessage();
		}

		// Cadastro de carona com entradas inválidas, Nenhuma carona aqui deve
		// ser cadastrada.
		try {
			meleva.cadastrarCarona(null, "Campina Grande", "João Pessoa",
					"23/06/2012", "16:00", "3");
			Assert.fail();
		} catch (Exception e1) {
			Assert.assertEquals("Sessão inválida", e1.getMessage());
		}
		try {
			meleva.cadastrarCarona("", "Patos", "São Paulo", "31/05/2012",
					"08:00", "2");
			Assert.fail();
		} catch (Exception e1) {
			Assert.assertEquals("Sessão inválida", e1.getMessage());
		}
		try {
			meleva.cadastrarCarona("teste", "João Pessoa", "Campina Grande",
					"25/11/2026", "06:59", "4");
			Assert.fail();
		} catch (Exception e1) {
			Assert.assertEquals("Sessão inexistente", e1.getMessage());
		}
		// Segundo bloco

		try {
			meleva.cadastrarCarona("1", null, "João Pessoa", "23/06/2012",
					"16:00", "3");
			Assert.fail();
		} catch (Exception e1) {
			Assert.assertEquals("Origem inválida", e1.getMessage());
		}

		try {
			meleva.cadastrarCarona("1", "", "São Paulo", "31/05/2012", "08:00",
					"2");
			Assert.fail();
		} catch (Exception e1) {
			Assert.assertEquals("Origem inválida", e1.getMessage());
		}

		// terceiro bloco
		try {
			meleva.cadastrarCarona("1", "Campina Grande", null, "30/02/2012",
					"16:00", "3");
			Assert.fail();
		} catch (Exception e1) {
			Assert.assertEquals("Destino inválido", e1.getMessage());
		}

		try {
			meleva.cadastrarCarona("1", "Patos", "", "31/05/2012", "08:00", "2");
			Assert.fail();
		} catch (Exception e1) {
			Assert.assertEquals("Destino inválido", e1.getMessage());
		}

		// Quarto bloco

		try {
			meleva.cadastrarCarona("1", "Campina Grande", "João Pessoa", null,
					"16:00", "3");
			Assert.fail();
		} catch (Exception e1) {
			Assert.assertEquals("Data inválida", e1.getMessage());
		}

		try {
			meleva.cadastrarCarona("1", "Patos", "João Pessoa", "", "8:00", "2");
			Assert.fail();
		} catch (Exception e1) {
			Assert.assertEquals("Data inválida", e1.getMessage());
		}

		try {
			meleva.cadastrarCarona("1", "Campina Grande", "João Pessoa",
					"30/02/2012", "16:00", "3");
			Assert.fail();
		} catch (Exception e1) {
			Assert.assertEquals("Data inválida", e1.getMessage());
		}

		try {
			meleva.cadastrarCarona("1", "Campina Grande", "João Pessoa",
					"31/04/2012", "16:00", "3");
			Assert.fail();
		} catch (Exception e1) {
			Assert.assertEquals("Data inválida", e1.getMessage());
		}

		try {
			meleva.cadastrarCarona("1", "Campina Grande", "João Pessoa",
					"32/12/2012", "16:00", "3");
			Assert.fail();
		} catch (Exception e1) {
			Assert.assertEquals("Data inválida", e1.getMessage());
		}

		try {
			meleva.cadastrarCarona("1", "Campina Grande", "João Pessoa",
					"25/12/2011", "16:00", "3");
			Assert.fail();
		} catch (Exception e1) {
			Assert.assertEquals("Data inválida", e1.getMessage());
		}
		// Quinto bloco

		try {
			meleva.cadastrarCarona("1", "Campina Grande", "João Pessoa",
					"23/06/2012", null, "3");
			Assert.fail();
		} catch (Exception e1) {
			Assert.assertEquals("Hora inválida", e1.getMessage());
		}

		try {
			meleva.cadastrarCarona("1", "Patos", "João Pessoa", "30/06/2012",
					"", "2");
			Assert.fail();
		} catch (Exception e1) {
			Assert.assertEquals("Hora inválida", e1.getMessage());
		}

		try {
			meleva.cadastrarCarona("1", "Patos", "João Pessoa", "30/06/2012",
					"seis", "2");
			Assert.fail();
		} catch (Exception e1) {
			Assert.assertEquals("Hora inválida", e1.getMessage());
		}
		// Sexto bloco
		try {
			meleva.cadastrarCarona("1", "Campina Grande", "João Pessoa",
					"23/06/2012", "16:00", null);
			Assert.fail();
		} catch (Exception e1) {
			Assert.assertEquals("Vaga inválida", e1.getMessage());
		}
		try {
			meleva.cadastrarCarona("1", "Patos", "João Pessoa", "30/06/2012",
					"08:00", "duas");
			Assert.fail();
		} catch (Exception e1) {
			Assert.assertEquals("Vaga inválida", e1.getMessage());
		}
		// Setimo Bloco

		try {
			meleva.getAtributoCarona(null, "origem");
			Assert.fail();
		} catch (Exception e1) {
			Assert.assertEquals("Identificador do carona é inválido",
					e1.getMessage());
		}

		try {
			meleva.getAtributoCarona("", "origem");
			Assert.fail();
		} catch (Exception e1) {
			Assert.assertEquals("Identificador do carona é inválido",
					e1.getMessage());
		}

		try {
			meleva.getAtributoCarona("xpto", "destino");
			Assert.fail();
		} catch (Exception e1) {
			Assert.assertEquals("Item inexistente", e1.getMessage());
		}

		try {
			meleva.getAtributoCarona("30", null);
			Assert.fail();
		} catch (Exception e1) {
			Assert.assertEquals("Atributo inválido", e1.getMessage());
		}

		try {
			meleva.getAtributoCarona("20", "");
			Assert.fail();
		} catch (Exception e1) {
			Assert.assertEquals("Atributo inválido", e1.getMessage());
		}

		try {
			meleva.getAtributoCarona("10", "xpto");
			Assert.fail();
		} catch (Exception e1) {
			Assert.assertEquals("Atributo inexistente", e1.getMessage());
		}

		try {
			meleva.getCarona("");
			Assert.fail();
		} catch (Exception e1) {
			Assert.assertEquals("Carona Inexistente", e1.getMessage());
		}

		try {
			meleva.getCarona(null);
			Assert.fail();
		} catch (Exception e1) {
			Assert.assertEquals("Carona Inválida", e1.getMessage());
		}

		try {
			meleva.getCarona("xpto");
			Assert.fail();
		} catch (Exception e1) {
			Assert.assertEquals("Carona Inexistente", e1.getMessage());
		}

		try {
			meleva.getTrajeto("xpto");
			Assert.fail();
		} catch (Exception e1) {
			Assert.assertEquals("Trajeto Inexistente", e1.getMessage());
		}
		try {
			meleva.getTrajeto("");
			Assert.fail();
		} catch (Exception e1) {
			Assert.assertEquals("Trajeto Inexistente", e1.getMessage());
		}
		try {
			meleva.getTrajeto(null);
			Assert.fail();
		} catch (Exception e1) {
			Assert.assertEquals("Trajeto Inválida", e1.getMessage());
		}

		// Localizar carona com entrada inválida
		try {
			meleva.localizarCarona("1", "-", "João Pessoa");
			Assert.fail();
		} catch (Exception e1) {
			Assert.assertEquals("Origem inválida", e1.getMessage());
		}

		try {
			meleva.localizarCarona("1", "()", "João Pessoa");
			Assert.fail();
		} catch (Exception e1) {
			Assert.assertEquals("Origem inválida", e1.getMessage());
		}

		try {
			meleva.localizarCarona("1", "!", "João Pessoa");
			Assert.fail();
		} catch (Exception e1) {
			Assert.assertEquals("Origem inválida", e1.getMessage());
		}

		try {
			meleva.localizarCarona("1", "!?", "João Pessoa");
			Assert.fail();
		} catch (Exception e1) {
			Assert.assertEquals("Origem inválida", e1.getMessage());
		}

		try {
			meleva.localizarCarona("1", "Campina Grande", ".");
			Assert.fail();
		} catch (Exception e1) {
			Assert.assertEquals("Destino inválido", e1.getMessage());
		}
		try {
			meleva.localizarCarona("1", "Campina Grande", "()");
			Assert.fail();
		} catch (Exception e1) {
			Assert.assertEquals("Destino inválido", e1.getMessage());
		}

		try {
			meleva.localizarCarona("1", "Campina Grande", "!?");
			Assert.fail();
		} catch (Exception e1) {
			Assert.assertEquals("Destino inválido", e1.getMessage());
		}

	}
}