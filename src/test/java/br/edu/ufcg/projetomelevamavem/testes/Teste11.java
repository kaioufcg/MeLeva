package br.edu.ufcg.projetomelevamavem.testes;

import br.edu.ufcg.projetomelevamavem.controller.MeLevaController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class Teste11 {
	// - Interesse em destino e tempo: o usuário diz que está interessado
	// em caronas entre uma origem e destino em um determinado período.
	// Caso seja publicada uma carona que satisfaça os requisitos,
	// O usuário é avisado por uma mensagem no seu perfil.

	// usuario é o observador
	// carona é o subject que vai falar

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
			meleva.criarUsuario("zezyt0", "z3z1t0", "Jose de zito",
					"Rua belarmina pereira 452, João Pessoa",
					"zezyto@gmail.com");

		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			meleva.criarUsuario("manelito", "w4n3l1t0", "Manel da Silva",
					"Rua adamastor pitaco 24, João Pessoa",
					"manel@yahoo.com.br");

		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			meleva.criarUsuario("jucaPeroba", "juqinha", "Juca Peroba",
					"Rua 13 de maio, Caruaru", "jucaPeroba@gmail.com");

		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			meleva.criarUsuario("mariano0ab", "mariozinho", "Mariano Silva",
					"Rua 27 de maio, Campina Grande",
					"marianoAdvogado@gmail.com");

		} catch (Exception e1) {
			System.out.println(e1.getLocalizedMessage());
		}

		try {
			meleva.criarUsuario("caba", "Marcin", "Marcio Sarvai",
					"Rua 21 de maio, Campina Grande", "marcioSarvai@gmail.com");

		} catch (Exception e1) {
			e1.printStackTrace();
		}

		// Iniciar sessão.
		try {
			Assert.assertEquals("1", meleva.abrirSessao("zezyt0", "z3z1t0"));

		} catch (Exception e1) {
			e1.printStackTrace();
		}

		try {
			Assert.assertEquals("Jose de zito",
					meleva.getAtributoUsuario("zezyt0", "nome"));

		} catch (Exception e1) {
			e1.printStackTrace();
		}

		try {
			Assert.assertEquals("Rua belarmina pereira 452, João Pessoa",
					meleva.getAtributoUsuario("zezyt0", "endereco"));

		} catch (Exception e1) {
			e1.printStackTrace();
		}
		//
		try {
			Assert.assertEquals("2", meleva.abrirSessao("manelito", "w4n3l1t0"));

		} catch (Exception e1) {
			e1.printStackTrace();
		}

		try {
			Assert.assertEquals("Manel da Silva",
					meleva.getAtributoUsuario("manelito", "nome"));

		} catch (Exception e1) {
			e1.printStackTrace();
		}

		try {
			Assert.assertEquals("Rua adamastor pitaco 24, João Pessoa",
					meleva.getAtributoUsuario("manelito", "endereco"));

		} catch (Exception e1) {
			e1.printStackTrace();
		}
		//
		try {
			Assert.assertEquals("3",
					meleva.abrirSessao("jucaPeroba", "juqinha"));

		} catch (Exception e1) {
			e1.printStackTrace();
		}

		try {
			Assert.assertEquals("4",
					meleva.abrirSessao("mariano0ab", "mariozinho"));

		} catch (Exception e1) {
			e1.printStackTrace();
		}

		try {
			Assert.assertEquals("5", meleva.abrirSessao("caba", "Marcin"));

		} catch (Exception e1) {
			e1.printStackTrace();
		}

		// Mostrar interesse.
		try {
			Assert.assertEquals(1000, meleva.cadastrarInteresse("1",
					"João Pessoa", "Campina Grande", "23/06/2012", "06:00",
					"16:00"));

		} catch (Exception e1) {
			e1.printStackTrace();
		}

		try {
			Assert.assertEquals(2000, meleva.cadastrarInteresse("2",
					"Campina Grande", "João Pessoa", "25/06/2012", "11:00",
					"18:00"));

		} catch (Exception e1) {
			e1.printStackTrace();
		}

		try {
			Assert.assertEquals(3000, meleva.cadastrarInteresse("4",
					"Campina Grande", "João Pessoa", "23/06/2012", "", "18:00"));

		} catch (Exception e1) {
			e1.printStackTrace();
		}

		try {
			Assert.assertEquals(4000, meleva.cadastrarInteresse("5",
					"Campina Grande", "João Pessoa", "", "", "18:00"));

		} catch (Exception e1) {
			e1.printStackTrace();
		}

		// Para os horários o sistema poderá deixar o usuário livre, ou seja,
		// ele poderá não colocar horaInicio(pegará todos a partir das 0h do dia
		// especificado
		// ou horaFim(pegará todos até às 11:59 do dia especificado) que a
		// consulta será realizada. data="" retornará todas as caronas que tem
		// marcadas da data atual em diante.
		try {
			Assert.assertEquals("5000", meleva.cadastrarInteresse("1", "-",
					"João Pessoa", "23/06/2012", "06:00", "16:00"));
			Assert.fail();
		} catch (Exception e1) {
			Assert.assertEquals("Origem inválida", e1.getMessage());
		}

		try {
			Assert.assertEquals("5000", meleva.cadastrarInteresse("1", "!",
					"João Pessoa", "23/06/2012", "06:00", "16:00"));
			Assert.fail();
		} catch (Exception e1) {
			Assert.assertEquals("Origem inválida", e1.getMessage());
		}

		try {
			Assert.assertEquals("5000", meleva.cadastrarInteresse("1",
					"Campina Grande", "!", "23/06/2012", "06:00", "16:00"));
			Assert.fail();
		} catch (Exception e1) {
			Assert.assertEquals("Destino inválido", e1.getMessage());
		}

		try {
			Assert.assertEquals("5000", meleva.cadastrarInteresse("1",
					"Campina Grande", "-", "23/06/2012", "06:00", "16:00"));
			Assert.fail();
		} catch (Exception e1) {
			Assert.assertEquals("Destino inválido", e1.getMessage());
		}

		try {
			Assert.assertEquals("5000", meleva.cadastrarInteresse("1",
					"Campina Grande", "-", "", "06:00", "16:00"));
			Assert.fail();
		} catch (Exception e1) {
			Assert.assertEquals("Destino inválido", e1.getMessage());
		}

		try {
			Assert.assertEquals("5000", meleva.cadastrarInteresse("1",
					"Campina Grande", "-", null, "06:00", "16:00"));
			Assert.fail();
		} catch (Exception e1) {
			Assert.assertEquals("Data inválida", e1.getMessage());
		}

		// Cadastrar caronas.
		try {
			Assert.assertEquals(10, meleva
					.cadastrarCarona("3", "Campina Grande", "João Pessoa",
							"23/06/2012", "16:00", "3"));

		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			Assert.assertEquals(20, meleva.cadastrarCarona("3", "João Pessoa",
					"Campina Grande", "25/06/2012", "14:00", "4"));

		} catch (Exception e1) {
			e1.printStackTrace();
		}

		try {
			Assert.assertEquals(30, meleva.cadastrarCarona("4", "João Pessoa",
					"Campina Grande", "25/06/2012", "15:00", "1"));

		} catch (Exception e1) {
			e1.printStackTrace();
		}

		// Verificar perfil.
		try {
			Assert.assertEquals("[]", meleva.verificarMensagensPerfil("1"));

		} catch (Exception e1) {
			e1.printStackTrace();
		}

		try {
			Assert.assertEquals("[]", meleva.verificarMensagensPerfil("2"));

		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			Assert.assertEquals(
					"[Carona cadastrada no dia 23/06/2012, às 16:00 de acordo com os seus interesses registrados. Entrar em contato com jucaPeroba@gmail.com]",
					meleva.verificarMensagensPerfil("4"));

		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			Assert.assertEquals(
					"[Carona cadastrada no dia 23/06/2012, às 16:00 de acordo com os seus interesses registrados. Entrar em contato com jucaPeroba@gmail.com]",
					meleva.verificarMensagensPerfil("5"));

		} catch (Exception e1) {
			e1.printStackTrace();
		}

		meleva.encerrarSistema();
		//meleva.quit();
	}
}
