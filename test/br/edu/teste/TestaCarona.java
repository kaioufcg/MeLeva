package br.edu.teste;



import br.edu.server.logicaCarona.Carona;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestaCarona {

    private Carona carona1;
    private Carona carona2;
    private Carona carona3;

    @Before
    public void Testa() throws Exception {
        // Cria caronas validas
        carona1 = new Carona("Campina grande", "Joao Pessoa", "19/11/2021",
                "13:00", 5);
        carona2 = new Carona("Recife", "Natal", "23/11/2022", "22:00", 3);
        carona3 = new Carona("Rio Grande do sul", "Conxixola", "23/11/2023",
                "19:00", 1);
    }

    @Test
    public void construtorTest() throws Exception {

        // TESTA QUANDO ORIGEM FOR INVÁLIDA

        // Origem = null, esperado: "Origem inválida".
        try {
            new Carona(null, "Joao Pessoa", "23/10/2012", "12:00", 5);
            Assert.fail("Teste de Origem = null falhou!");
        } catch (Exception e) {
            Assert.assertEquals("Origem inválida", e.getMessage());
        }

        // Origem = vazio, esperado: "Origem inválida".
        try {
            new Carona("", "Joao Pessoa", "19/10/2012", "12:00", 5);
            Assert.fail("Teste de Origem = vazio falhou!");
        } catch (Exception e) {
            Assert.assertEquals("Origem inválida", e.getMessage());
        }

        // Origem = Só espaços vazios, esperado: "Origem inválida".
        try {
            new Carona("            ", "Campina Grande", "19/10/2012", "12:00",
                    5);
            Assert.fail("Teste de Origem = Só espaços vazios falhou!");
        } catch (Exception e) {
            Assert.assertEquals("Origem inválida", e.getMessage());
        }

        // Origem = 2 5 7, esperado: "Origem inválida".
        try {
            new Carona(" 2 5 7", "Campina Grande", "23/05/2012", "12:00", 5);
            // Assert.fail("Teste de Origem = 2 5 7 falhou!");
        } catch (Exception e) {
            Assert.assertEquals("Origem inválida", e.getMessage());
        }

        // Origem = ()*/, esperado: "Origem inválida".
        try {
            new Carona("()*/", "Campina Grande", "23/05/2012", "12:00", 5);
            // Assert.fail("Teste de Origem = 2 5 7 falhou!");
        } catch (Exception e) {
            Assert.assertEquals("Origem inválida", e.getMessage());
        }

        // TESTA QUANDO DESTINO FOR INVÁLIDO

        // Destino = null, esperado: "Destino inválida".
        try {
            new Carona("Joao Pessoa", null, "19/10/2012", "12:00", 5);
            Assert.fail("Teste de Destino = null falhou!");
        } catch (Exception e) {
            Assert.assertEquals("Destino inválido", e.getMessage());
        }

        // Destino = vazio, esperado: "Destino inválida".
        try {
            new Carona("Joao Pessoa", "", "19/10/2012", "12:00", 5);
            Assert.fail("Teste de Destino = vazio falhou!");
        } catch (Exception e) {
            Assert.assertEquals("Destino inválido", e.getMessage());
        }

        // Destino = Só espaços vazios, esperado: "Destino inválida".
        try {
            new Carona("Joao Pessoa", "            ", "19/10/2012", "12:00", 5);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals("Destino inválido", e.getMessage());
        }

        // Destino = 2 5 7, esperado: "Destino inválida".
        try {
            new Carona("Joao Pessoa", "  2 5 7", "23/05/2012", "12:00", 5);
            // Assert.fail("Teste de Destino = 2 5 7 falhou!");
        } catch (Exception e) {
            Assert.assertEquals("Destino inválido", e.getMessage());
        }

        // Destino = ()*/, esperado: "Destino inválida".
        try {
            new Carona("Joao Pessoa", "()*/", "23/05/2012", "12:00", 5);
            // Assert.fail("Teste de Destino = 2 5 7 falhou!");
        } catch (Exception e) {
            Assert.assertEquals("Destino inválido", e.getMessage());
        }

        // TESTA QUANDO DATA FOR INVÁLIDO

        // Data = null, esperado: "Data inválida".
        try {
            new Carona("Joao Pessoa", "Campina grande", null, "12:00", 5);
            Assert.fail("Teste de data = null falhou!");
        } catch (Exception e) {
            Assert.assertEquals("Data inválida", e.getMessage());
        }

        // Data = vazio, esperado: "Data inválida".
        try {
            new Carona("Joao Pessoa", "Campina grande", "", "12:00", 5);
            Assert.fail("Teste de Data = vazio falhou!");
        } catch (Exception e) {
            Assert.assertEquals("Data inválida", e.getMessage());
        }

        // Data = Só espaços vazios, esperado: "Data inválida".
        try {
            new Carona("Joao Pessoa", "Campina grande", "        ", "12:00", 5);
            Assert.fail("Teste de Data = Só espaços vazios falhou!");
        } catch (Exception e) {
            Assert.assertEquals("Data inválida", e.getMessage());
        }

        // Data = 2 5 7, esperado: "Data inválida".
        try {
            new Carona("Joao Pessoa", "Campina grande", "2 5 7", "12:00", 5);
            Assert.fail("Teste de Data = 2 5 7 falhou!");
        } catch (Exception e) {
            Assert.assertEquals("Data inválida", e.getMessage());
        }

        // Data = ()*/, esperado: "Data inválida".
        try {
            new Carona("Joao Pessoa", "Campina grande", "()*/", "12:00", 5);
            Assert.fail("Teste de Data = ()*/ falhou!");
        } catch (Exception e) {
            Assert.assertEquals("Data inválida", e.getMessage());
        }

        // Data = 19/122010, esperado: "Data inválida".
        try {
            new Carona("Joao Pessoa", "Campina grande", "19/122010", "12:00", 5);
            Assert.fail("Teste de Data = 19/122010 falhou! - linha 155");
        } catch (Exception e) {
            Assert.assertEquals("Data inválida", e.getMessage());
        }

        // Data = 19122010, esperado: "Data inválida".
        try {
            new Carona("Joao Pessoa", "Campina grande", "19122010", "12:00", 5);
            Assert.fail("Teste de Data = 19122010 falhou!");
        } catch (Exception e) {
            Assert.assertEquals("Data inválida", e.getMessage());
        }

        // TESTA HORA INVÁLIDA

        // Hora = null, esperado: "Hora inválida".
        try {
            new Carona("Joao Pessoa", "Campina grande", "19/12/2015", null, 5);
            Assert.fail("Teste de Hora = null falhou!");
        } catch (Exception e) {
            Assert.assertEquals("Hora inválida", e.getMessage());
        }

        // Hora = vazio, esperado: "Hora inválida".
        try {
            new Carona("Joao Pessoa", "Campina grande", "19/12/2015", "", 5);
            Assert.fail("Teste de Hora = vazio falhou!");
        } catch (Exception e) {
            Assert.assertEquals("Hora inválida", e.getMessage());
        }

        // Hora = Só espaços vazios, esperado: "Hora inválida".
        try {
            new Carona("Joao Pessoa", "Campina grande", "19/12/2015",
                    "        ", 5);
            Assert.fail("Teste de Hora = Só espaços vazios falhou!");
        } catch (Exception e) {
            Assert.assertEquals("Hora inválida", e.getMessage());
        }

        // Hora = 191:00 : "Hora inválida".
        try {
            new Carona("Joao Pessoa", "Campina grande", "19/12/2015", "191:00",
                    5);
            Assert.fail("Teste de Hora = 191:00 falhou!");
        } catch (Exception e) {
            Assert.assertEquals("Hora inválida", e.getMessage());
        }

        // Hora = 25:00 : "Hora inválida".
        try {
            new Carona("Joao Pessoa", "Campina grande", "19/12/2015", "25:00",
                    5);
            Assert.fail("Teste de Hora = 25:00 falhou!");
        } catch (Exception e) {
            Assert.assertEquals("Hora inválida", e.getMessage());
        }

        // Hora = 01:121 : "Hora inválida".
        try {
            new Carona("Joao Pessoa", "Campina grande", "19/12/2015", "01:121",
                    5);
            Assert.fail("Teste de Hora = 01:121 falhou!");
        } catch (Exception e) {
            Assert.assertEquals("Hora inválida", e.getMessage());
        }

        // Hora = 01:60 : "Hora inválida".
        try {
            new Carona("Joao Pessoa", "Campina grande", "19/12/2015", "01:60",
                    5);
            Assert.fail("Teste de Hora = 01:60 falhou!");
        } catch (Exception e) {
            Assert.assertEquals("Hora inválida", e.getMessage());
        }

        // Hora = 01:NILL : "Hora inválida".
        try {
            new Carona("Joao Pessoa", "Campina grande", "19/12/2015",
                    "01:NILL", 5);
            Assert.fail("Teste de Hora = 01:NILL falhou!");
        } catch (Exception e) {
            Assert.assertEquals("Hora inválida", e.getMessage());
        }

        // Hora = 0100 : "Hora inválida".
        try {
            new Carona("Joao Pessoa", "Campina grande", "19/12/2015", "0100", 5);
            Assert.fail("Teste de Hora = 0100 falhou!");
        } catch (Exception e) {
            Assert.assertEquals("Hora inválida", e.getMessage());
        }

        // TESTE DE NUMERO DE VAGAS

        // Vaga = null : "Vaga inválida".
        try {
            new Carona("Joao Pessoa", "Campina grande", "19/12/2015", "01:00",
                    null);
            Assert.fail("Teste de Vaga = null falhou!");
        } catch (Exception e) {
            Assert.assertEquals("Vaga inválida", e.getMessage());
        }

        // Vaga = 0 : "Vaga inválida".
        try {
            new Carona("Joao Pessoa", "Campina grande", "19/12/2015", "01:00",
                    0);
            Assert.fail("Teste de Vaga = 0 falhou!");
        } catch (Exception e) {
            Assert.assertEquals("Vaga inválida", e.getMessage());
        }

        // Vaga = -1 : "Vaga inválida".
        try {
            new Carona("Joao Pessoa", "Campina grande", "19/12/2015", "01:00",
                    -1);
            Assert.fail("Teste de Vaga = Só espaços vazio falhou!");
        } catch (Exception e) {
            Assert.assertEquals("Vaga inválida", e.getMessage());
        }

        // TESTA GET'S
        // getOrigem()
        Assert.assertEquals("Campina grande", carona1.getOrigem());
        Assert.assertEquals("Recife", carona2.getOrigem());
        Assert.assertEquals("Rio Grande do sul", carona3.getOrigem());

        // getDestino()
        Assert.assertEquals("Joao Pessoa", carona1.getDestino());
        Assert.assertEquals("Natal", carona2.getDestino());
        Assert.assertEquals("Conxixola", carona3.getDestino());

        // getData()
        Assert.assertEquals("19/11/2021", carona1.getData());
        Assert.assertEquals("23/11/2022", carona2.getData());
        Assert.assertEquals("23/11/2023", carona3.getData());

        // getHora()
        Assert.assertEquals("13:00", carona1.getHora());
        Assert.assertEquals("22:00", carona2.getHora());
        Assert.assertEquals("19:00", carona3.getHora());

        // cria carona para comparar com carona3
        // new Carona("Rio Grande do sul", "Coxixola",
        // "19/11/2023", "19:00", 1);

        // getTrajeto()
        Assert.assertEquals("Campina grande - Joao Pessoa",
                carona1.getTrajeto());
        Assert.assertEquals("Recife - Natal", carona2.getTrajeto());
        Assert.assertEquals("Rio Grande do sul - Conxixola",
                carona3.getTrajeto());

        // toCarona()
        Assert.assertEquals(
                "Campina grande para Joao Pessoa, no dia 19/11/2021, as 13:00",
                carona1.getCarona());
        Assert.assertEquals("Recife para Natal, no dia 23/11/2022, as 22:00",
                carona2.getCarona());
        Assert.assertEquals(
                "Rio Grande do sul para Conxixola, no dia 23/11/2023, as 19:00",
                carona3.getCarona());

    }
}
