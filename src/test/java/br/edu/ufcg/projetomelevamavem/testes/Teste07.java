package br.edu.ufcg.projetomelevamavem.testes;

import br.edu.ufcg.projetomelevamavem.controller.MeLevaController;
import br.edu.ufcg.projetomelevamavem.logicaSistema.MeLevaException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class Teste07 {

    private MeLevaController meleva;
    private int contador;

    @Before
    public void testeJU() {
        meleva = MeLevaController.getInstance();
    }

    @Test
    public void testCriaUsuario() throws MeLevaException {
        // Limpar os dados do sistema.
        meleva.zerarSistema();

        // Criar usuário.
        try {
            Assert.assertEquals("1", meleva.criarUsuario("mark", "m@rk",
                    "Mark Zuckerberg", "Palo Alto, California",
                    "mark@facebook.com"));
            this.contador++;
        } catch (Exception e1) {
            System.out.println("AKI" + this.contador + " " + e1.getMessage());
        }
        try {
            Assert.assertEquals("2", meleva.criarUsuario("steve", "5t3v3",
                    "Steve Paul Jobs", "Palo Alto, California",
                    "jobs@apple.com"));
            this.contador++;
        } catch (Exception e1) {
            System.out.println("AKI" + this.contador + " " + e1.getMessage());
        }

        // Iniciar sessão.
        try {
            Assert.assertEquals("2", meleva.abrirSessao("steve", "5t3v3"));
            this.contador++;
        } catch (Exception e1) {
            System.out.println("AKI" + this.contador + " " + e1.getMessage());
        }

        try {
            Assert.assertEquals("Steve Paul Jobs",
                    meleva.getAtributoUsuario("steve", "nome"));
            this.contador++;
        } catch (Exception e1) {
            System.out.println("AKI" + this.contador + " " + e1.getMessage());
        }
        try {
            Assert.assertEquals("Palo Alto, California",
                    meleva.getAtributoUsuario("steve", "endereco"));
            this.contador++;
        } catch (Exception e1) {
            System.out.println("AKI" + this.contador + " " + e1.getMessage());
        }

        try {
            Assert.assertEquals("1", meleva.abrirSessao("mark", "m@rk"));
            this.contador++;
        } catch (Exception e1) {
            System.out.println("AKI" + this.contador + " " + e1.getMessage());
        }
        try {
            Assert.assertEquals("Mark Zuckerberg",
                    meleva.getAtributoUsuario("mark", "nome"));
            this.contador++;
        } catch (Exception e1) {
            System.out.println("AKI" + this.contador + " " + e1.getMessage());
        }
        try {
            Assert.assertEquals("Palo Alto, California",
                    meleva.getAtributoUsuario("mark", "endereco"));
            this.contador++;
        } catch (Exception e1) {
            System.out.println("AKI" + this.contador + " " + e1.getMessage());
        }

        // cadastra
        try {
            Assert.assertEquals(10, meleva.cadastrarCarona("1", "Cajazeiras",
                    "Patos", "20/07/2012", "14:00", "4"));
            this.contador++;
        } catch (Exception e1) {
            System.out.println("AKI" + this.contador + " " + e1.getMessage());
        }
        try {
            Assert.assertEquals(20, meleva.cadastrarCarona("1",
                    "São Francisco", "Palo Alto", "12/09/2012", "21:00", "2"));
            this.contador++;
        } catch (Exception e1) {
            System.out.println("AKI" + this.contador + " " + e1.getMessage());
        }
        try {
            Assert.assertEquals(30, meleva.cadastrarCarona("1", "Campina Grande", "João Pessoa",
                    "01/07/2012", "12:00", "1"));
            this.contador++;
        } catch (Exception e1) {
            System.out.println("AKI" + this.contador + " " + e1.getMessage());
        }
        try {
            Assert.assertEquals(40, meleva.cadastrarCarona("2", "Campina Grande", "João Pessoa",
                    "02/07/2012", "12:00", "3"));
            this.contador++;
        } catch (Exception e1) {
            System.out.println("AKI" + this.contador + " " + e1.getMessage());
        }
        try {
            Assert.assertEquals(50, meleva.cadastrarCarona("2", "Campina Grande", "João Pessoa",
                    "04/07/2012", "16:00", "2"));
            this.contador++;
        } catch (Exception e1) {
            System.out.println("AKI" + this.contador + " " + e1.getMessage());
        }
        try {
            Assert.assertEquals(60, meleva.cadastrarCarona("2", "Leeds",
                    "Londres", "10/02/2013", "10:00", "3"));
            this.contador++;
        } catch (Exception e1) {
            System.out.println("AKI" + this.contador + " " + e1.getMessage());
        }
        // Encerrar a sessao de mark.
        Assert.assertEquals(true, meleva.encerrarSessao("mark"));
        Assert.assertEquals(true, meleva.encerrarSessao("steve"));

        // Sugerir ponto de encontro para uma carona
        try {
            Assert.assertEquals("2", meleva.abrirSessao("steve", "5t3v3"));
        } catch (Exception e1) {
            System.out.println("AKI" + this.contador++ + " " + e1.getMessage());
        }
        try {
            Assert.assertEquals(100, meleva.sugerirPontoEncontro("2", "20",
                    "[Acude Velho;Hiper Bompreco]"));
        } catch (Exception e1) {
            System.out.println("AKI" + this.contador++ + " " + e1.getMessage());
        }

        // Requisitar vaga na carona.
        try {
            Assert.assertEquals(200, meleva.solicitarVaga("2", "20"));
        } catch (Exception e1) {
            System.out.println("AKI" + this.contador++ + " " + e1.getMessage());
        }
        try {
            Assert.assertEquals("São Francisco",
                    meleva.getAtributoSolicitacao("200", "origem"));
        } catch (Exception e1) {

            e1.printStackTrace();
        }
        try {
            Assert.assertEquals("Palo Alto",
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
            Assert.assertEquals("Steve Paul Jobs",
                    meleva.getAtributoSolicitacao("200", "Dono da solicitacao"));
        } catch (Exception e1) {

            e1.printStackTrace();
        }

        // Aceitar requisição
        try {
            Assert.assertEquals("1", meleva.abrirSessao("mark", "m@rk"));
        } catch (Exception e1) {

            e1.printStackTrace();
        }
        try {
            meleva.aceitarSolicitacao("1", "200");
        } catch (Exception e) {

            e.printStackTrace();
        }
        try {
            Assert.assertEquals("3", meleva.getAtributoCarona("40", "vagas"));
        } catch (Exception e1) {

            e1.printStackTrace();
        }

        // Encerrar a sessao.
        Assert.assertEquals(true, meleva.encerrarSessao("mark"));
        Assert.assertEquals(true, meleva.encerrarSessao("steve"));

        // Finaliza o sistema
        meleva.encerrarSistema();

        // Abre o sistema novamente
        try {
            meleva.reiniciarSistema();
        } catch (Exception e4) {
            e4.printStackTrace();
        }

        // Comeca a recuperar as informacoes que foram persistidas anteriormente

        try {
            Assert.assertEquals("1", meleva.abrirSessao("mark", "m@rk"));
        } catch (Exception e1) {
            System.out.println("AKI");
        }

        try {
            Assert.assertEquals("2", meleva.abrirSessao("steve", "5t3v3"));
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        // Recupera Informacoes Usuario
        try {
            Assert.assertEquals("Steve Paul Jobs",
                    meleva.getAtributoUsuario("steve", "nome"));
        } catch (Exception e) {

            e.printStackTrace();
        }
        try {
            Assert.assertEquals("Palo Alto, California",
                    meleva.getAtributoUsuario("steve", "endereco"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Assert.assertEquals("Mark Zuckerberg",
                    meleva.getAtributoUsuario("mark", "nome"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Assert.assertEquals("Palo Alto, California",
                    meleva.getAtributoUsuario("mark", "endereco"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Recupera Informacoes das Caronas
        try {
            Assert.assertEquals("10", meleva.getCaronaUsuario("1", 1));
        } catch (MeLevaException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            Assert.assertEquals("Cajazeiras",
                    meleva.getAtributoCarona("10", "origem"));
        } catch (MeLevaException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Assert.assertEquals("Patos", meleva.getAtributoCarona("10", "destino"));
        // /////////
        Assert.assertEquals("20", meleva.getCaronaUsuario("1", 2));
        Assert.assertEquals("São Francisco",
                meleva.getAtributoCarona("20", "origem"));
        Assert.assertEquals("Palo Alto",
                meleva.getAtributoCarona("20", "destino"));
        // /////////
        Assert.assertEquals("30", meleva.getCaronaUsuario("1", 3));
        Assert.assertEquals("Campina Grande",
                meleva.getAtributoCarona("30", "origem"));
        Assert.assertEquals("João Pessoa",
                meleva.getAtributoCarona("30", "destino"));
        // /////////
        Assert.assertEquals("40", meleva.getCaronaUsuario("2", 1));
        Assert.assertEquals("Campina Grande",
                meleva.getAtributoCarona("40", "origem"));
        Assert.assertEquals("João Pessoa",
                meleva.getAtributoCarona("40", "destino"));
        // /////////
        Assert.assertEquals("50", meleva.getCaronaUsuario("2", 2));
        Assert.assertEquals("Campina Grande",
                meleva.getAtributoCarona("50", "origem"));
        Assert.assertEquals("João Pessoa",
                meleva.getAtributoCarona("50", "destino"));
        // /////////
        Assert.assertEquals("60", meleva.getCaronaUsuario("2", 3));
        Assert.assertEquals("Leeds", meleva.getAtributoCarona("60", "origem"));
        Assert.assertEquals("Londres",
                meleva.getAtributoCarona("60", "destino"));

        // Recupera todas as caronas cadastradas
        Assert.assertEquals("{10,20,30}", meleva.getTodasCaronasUsuario("1"));
        Assert.assertEquals("{40,50,60}", meleva.getTodasCaronasUsuario("2"));

        // Recupera solicitacoes confirmadas
        Assert.assertEquals("{200}",
                meleva.getSolicitacoesConfirmadas("1", "20"));
        Assert.assertEquals("{}", meleva.getSolicitacoesPendentes("1", "20"));
        Assert.assertEquals("[[Acude Velho;Hiper Bompreco]]",
                meleva.getPontosSugeridos("1", "20"));

        // Recupera pontos sugeridos

        Assert.assertEquals("[[Acude Velho;Hiper Bompreco]]",
                meleva.getPontosEncontro("1", "20"));

        // Finaliza o sistema
        meleva.encerrarSistema();
        //meleva.quit();

        // //////////////////////////////////////////////////////////////////////////////
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
            meleva.criarUsuario("bill", "bilz@o", "William Henry Gates III",
                    "Medina, Washington", "billzin@gmail.com");

        } catch (Exception e1) {
            System.out.println(e1.getLocalizedMessage());
        }

        try {
            meleva.criarUsuario("vader", "d4rth", "Anakin Skywalker",
                    "Death Star I", "darthvader@empire.com");

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
            Assert.assertEquals(10, meleva.cadastrarCarona("1", "Campina Grande", "João Pessoa",
                    "02/07/2012", "12:00", "3"));

        } catch (Exception e1) {
            e1.printStackTrace();
        }
        try {
            Assert.assertEquals(20, meleva.cadastrarCarona("1", "Campina Grande", "João Pessoa",
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Assert.assertEquals("Campina Grande",
                    meleva.getAtributoSolicitacao("100", "origem"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Assert.assertEquals("João Pessoa",
                    meleva.getAtributoSolicitacao("100", "destino"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Assert.assertEquals("Mark Zuckerberg",
                    meleva.getAtributoSolicitacao("100", "Dono da carona"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Assert.assertEquals("William Henry Gates III",
                    meleva.getAtributoSolicitacao("100", "Dono da solicitacao"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Aceitar requisição
        try {
            meleva.aceitarSolicitacao("1", "100");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            // duas por que eu aceitei a diminuo o numero de vagas
            Assert.assertEquals("2", meleva.getAtributoCarona("10", "vagas"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Requisitar vaga na carona.
        try {
            Assert.assertEquals(200, meleva.solicitarVaga("2", "20"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Assert.assertEquals("Campina Grande",
                    meleva.getAtributoSolicitacao("200", "origem"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Assert.assertEquals("João Pessoa",
                    meleva.getAtributoSolicitacao("200", "destino"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Assert.assertEquals("Mark Zuckerberg",
                    meleva.getAtributoSolicitacao("200", "Dono da carona"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Assert.assertEquals("William Henry Gates III",
                    meleva.getAtributoSolicitacao("200", "Dono da solicitacao"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Aceitar requisição
        try {
            meleva.aceitarSolicitacao("1", "200");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Assert.assertEquals("1", meleva.getAtributoCarona("20", "vagas"));
        } catch (Exception e) {
            e.printStackTrace();
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
            e1.printStackTrace();
        }
        try {
            Assert.assertEquals("William Henry Gates III",
                    meleva.getAtributoPerfil("bill", "nome"));
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        try {
            Assert.assertEquals("Medina, Washington",
                    meleva.getAtributoPerfil("bill", "endereco"));
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        try {
            Assert.assertEquals("billzin@gmail.com",
                    meleva.getAtributoPerfil("bill", "email"));
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        try {
            Assert.assertEquals("[10,20]", meleva.getAtributoPerfil("bill",
                    "historico de vagas em caronas"));
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        try {
            Assert.assertEquals("[]",
                    meleva.getAtributoPerfil("bill", "historico de caronas"));
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        try {
            Assert.assertEquals("0", meleva.getAtributoPerfil("bill",
                    "caronas seguras e tranquilas"));
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        try {
            Assert.assertEquals("0", meleva.getAtributoPerfil("bill",
                    "caronas que não funcionaram"));
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        try {
            Assert.assertEquals("1", meleva.getAtributoPerfil("bill",
                    "faltas em vagas de caronas"));
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        try {
            Assert.assertEquals("0", meleva.getAtributoPerfil("bill",
                    "presenças em vagas de caronas"));
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        // Segunda carona que mark ofereceu
        try {
            meleva.reviewVagaEmCarona("1", "20", "bill", "não faltou");
            // Assert.fail();
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
            e1.printStackTrace();
        }
        try {
            Assert.assertEquals("William Henry Gates III",
                    meleva.getAtributoPerfil("bill", "nome"));
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        try {
            Assert.assertEquals("Medina, Washington",
                    meleva.getAtributoPerfil("bill", "endereco"));
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        try {
            Assert.assertEquals("billzin@gmail.com",
                    meleva.getAtributoPerfil("bill", "email"));
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        try {
            Assert.assertEquals("[10,20]", meleva.getAtributoPerfil("bill",
                    "historico de vagas em caronas"));
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        try {
            Assert.assertEquals("[]",
                    meleva.getAtributoPerfil("bill", "historico de caronas"));
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        try {
            Assert.assertEquals("0", meleva.getAtributoPerfil("bill",
                    "caronas seguras e tranquilas"));
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        try {
            Assert.assertEquals("0", meleva.getAtributoPerfil("bill",
                    "caronas que não funcionaram"));
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        try {
            Assert.assertEquals("1", meleva.getAtributoPerfil("bill",
                    "faltas em vagas de caronas"));
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        try {
            Assert.assertEquals("1", meleva.getAtributoPerfil("bill",
                    "presenças em vagas de caronas"));
        } catch (Exception e1) {
            e1.printStackTrace();
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
            e1.printStackTrace();
        }

        try {
            meleva.reviewVagaEmCarona("1", "20", "vader", "não funcionou");
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals("Usuário não possui vaga na carona.",
                    e.getMessage());
        }

        meleva.encerrarSistema();
        //meleva.quit();

    }
}
