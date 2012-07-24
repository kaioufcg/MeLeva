package br.edu.teste;

import br.edu.client.utilitarios.MeLevaException;
import br.edu.server.logicaSistema.Verificador;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestaData {

    private Verificador data;

    @Before
    public void Testa() {
        data = new Verificador();
    }

    @Test
    public void test() {
        // dd/mm/aaaa
        try {
            data.verificaData("10/02/2231", "10:00");
            //Assert.fail();
        } catch (MeLevaException e) {
            Assert.assertEquals("Data inválida", e.getMessage());
        }
        try {
            data.verificaData("05/04/2012", "05:00");
            Assert.fail();
        } catch (MeLevaException e) {

            Assert.assertEquals("Data inválida", e.getMessage());
        }
        try {
            data.verificaData("05/04/2012", "04:00");
            Assert.fail();
        } catch (MeLevaException e) {

            Assert.assertEquals("Data inválida", e.getMessage());
        }
        try {
            data.verificaData("05/04/2012", "02:00");
            Assert.fail();
        } catch (MeLevaException e) {

            Assert.assertEquals("Data inválida", e.getMessage());
        }
        try {
            data.verificaData("05/04/2012", "01:00");
            Assert.fail();
        } catch (MeLevaException e) {

            Assert.assertEquals("Data inválida", e.getMessage());
        }
        try {
            data.verificaData("05/04/2012", "05:30");
            Assert.fail();
        } catch (MeLevaException e) {

            Assert.assertEquals("Data inválida", e.getMessage());
        }
        try {
            data.verificaData("03/04/2012", "05:00");
            Assert.fail();
        } catch (MeLevaException e) {

            Assert.assertEquals("Data inválida", e.getMessage());
        }
        try {
            data.verificaData("05/04/2011", "05:00");
            Assert.fail();
        } catch (MeLevaException e) {

            Assert.assertEquals("Data inválida", e.getMessage());
        }

        try {
            data.verificaData("10/03/1111", null);
            Assert.fail();
        } catch (MeLevaException e1) {

            Assert.assertEquals("Data inválida", e1.getMessage());
        }
        try {
            data.verificaData("10/01/2012", null);
            Assert.fail();
        } catch (MeLevaException e) {

            Assert.assertEquals("Data inválida", e.getMessage());
        }
        try {
            data.verificaData("30/10/3000", "8:00");
            //Assert.fail();
        } catch (MeLevaException e) {

            Assert.assertEquals("Data inválida", e.getMessage());
        }
        try {
            data.verificaData("31/05/2020", "8:00");
            //Assert.fail();
        } catch (MeLevaException e) {

            Assert.assertEquals("Data inválida", e.getMessage());
        }
        try {
            data.verificaData("01/12/3131", "8:00");
            //Assert.fail();
        } catch (MeLevaException e) {

            Assert.assertEquals("Data inválida", e.getMessage());
        }
        try {
            data.verificaData("29/02/2012", "10:00");
            Assert.fail();
        } catch (MeLevaException e) {

            Assert.assertEquals("Data inválida", e.getMessage());
        }
        try {
            data.verificaData("29/02/2013", null);
            Assert.fail();
        } catch (MeLevaException e) {

            Assert.assertEquals("Data inválida", e.getMessage());
        }
        try {
            data.verificaData("31/02/2012", null);
            Assert.fail();
        } catch (MeLevaException e) {

            Assert.assertEquals("Data inválida", e.getMessage());
        }
        try {
            data.verificaData("31/02/2011", null);
            Assert.fail();
        } catch (MeLevaException e) {

            Assert.assertEquals("Data inválida", e.getMessage());
        }
        try {
            data.verificaData("31/00/2012", null);
            Assert.fail();
        } catch (MeLevaException e) {

            Assert.assertEquals("Data inválida", e.getMessage());
        }
        try {
            data.verificaData("00/00/2012", null);
            Assert.fail();
        } catch (MeLevaException e) {

            Assert.assertEquals("Data inválida", e.getMessage());
        }
        try {
            data.verificaData("1/2/2012", null);
            Assert.fail();
        } catch (MeLevaException e) {

            Assert.assertEquals("Data inválida", e.getMessage());
        }
        try {
            data.verificaData("31/02/12", null);
            Assert.fail();
        } catch (MeLevaException e) {

            Assert.assertEquals("Data inválida", e.getMessage());
        }
        try {
            data.verificaData("31/04/2012", null);
            Assert.fail();
        } catch (MeLevaException e) {

            Assert.assertEquals("Data inválida", e.getMessage());
        }
        try {
            data.verificaData("31/06/2012", null);
            Assert.fail();
        } catch (MeLevaException e) {
            Assert.assertEquals("Data inválida", e.getMessage());
        }
        try {
            data.verificaData("31/08/2012", "8:00");
            //Assert.fail();
        } catch (MeLevaException e) {

            Assert.assertEquals("Data inválida", e.getMessage());
        }
        try {
            data.verificaData("31/09/2012", null);
            Assert.fail();
        } catch (MeLevaException e) {

            Assert.assertEquals("Data inválida", e.getMessage());
        }
        try {
            data.verificaData("31/02/2012", null);
            Assert.fail();
        } catch (MeLevaException e) {

            Assert.assertEquals("Data inválida", e.getMessage());
        }
        try {
            data.verificaData("31/11/2012", null);
            Assert.fail();
        } catch (MeLevaException e) {

            Assert.assertEquals("Data inválida", e.getMessage());
        }
        try {
            data.verificaData("3177/02/2012", null);
            Assert.fail();
        } catch (MeLevaException e) {

            Assert.assertEquals("Data inválida", e.getMessage());
        }
        try {
            data.verificaData("31/02000/2012", null);
            Assert.fail();
        } catch (MeLevaException e) {

            Assert.assertEquals("Data inválida", e.getMessage());
        }
        try {
            data.verificaData("31/0200/2012", null);
            Assert.fail();
        } catch (MeLevaException e) {

            Assert.assertEquals("Data inválida", e.getMessage());
        }
        try {
            data.verificaData("Data inválida", "Data inválida");
            Assert.fail();
        } catch (MeLevaException e) {

            Assert.assertEquals("Data inválida", e.getMessage());
        }
        try {
            data.verificaData(null, "Data inválida");
            Assert.fail();
        } catch (MeLevaException e) {

            Assert.assertEquals("Data inválida", e.getMessage());
        }
        try {
            data.verificaData("dd/mm/aaaa", null);
            Assert.fail();
        } catch (MeLevaException e) {

            Assert.assertEquals("Data inválida", e.getMessage());
        }
        try {
            data.verificaData("31*02*2012", null);
            Assert.fail();
        } catch (MeLevaException e) {

            Assert.assertEquals("Data inválida", e.getMessage());
        }
        try {
            data.verificaData("31 02 2012", null);
            Assert.fail();
        } catch (MeLevaException e) {

            Assert.assertEquals("Data inválida", e.getMessage());
        }
        try {
            data.verificaData("31022012", "Data inválida");
            Assert.fail();
        } catch (MeLevaException e) {

            Assert.assertEquals("Data inválida", e.getMessage());
        }
        try {
            data.verificaData("20", "Data inválida");
            Assert.fail();
        } catch (MeLevaException e) {

            Assert.assertEquals("Data inválida", e.getMessage());
        }
    }
}
