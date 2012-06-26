package br.edu.ufcg.projetomelevamavem.logicaSistema;

import br.edu.ufcg.projetomelevamavem.logicaCarona.Carona;
import br.edu.ufcg.projetomelevamavem.logicaNegociacao.Negociacao;
import br.edu.ufcg.projetomelevamavem.logicaUsuario.Usuario;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.security.AccessControlException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.Serializable;

/**
 * Classe que controla a persistência de dados do sistema Me Leva.
 *
 * @author Grupo Me Leva - Projeto SI1 - UFCG 2012.1
 *
 */
public class ControleDePersistenciaDeDados implements Serializable {

    private static final long serialVersionUID = 00000000002L;
    private volatile static ControleDePersistenciaDeDados unicaInstancia;
    private XStream xstream;
    private final String USERS_FILE = "arquivosXML/usuarios.xml";
    private final String NEGOCIACOES_FILE = "arquivosXML/negociacoes.xml";
    private final String INTERESSES_FILE = "arquivosXML/interesses.xml";

    /**
     * Construtor
     */
    private ControleDePersistenciaDeDados() {
        xstream = new XStream(new DomDriver());
        xstream.alias("Usuario", Usuario.class);
        xstream.alias("Carona", Carona.class);
        xstream.alias("Interesse", Interesse.class);
        xstream.alias("Lista", LinkedList.class);
        xstream.alias("Map", TreeMap.class);
    }

    /**
     * Método que acessa a instância da Persistência dos dados.
     *
     * @return Instância unica.
     */
    public static ControleDePersistenciaDeDados getInstance() {
        if (unicaInstancia == null) {
            synchronized (ControleDePersistenciaDeDados.class) {
                if (unicaInstancia == null) {
                    unicaInstancia = new ControleDePersistenciaDeDados();
                }
            }
        }
        return unicaInstancia;

    }

    /**
     * Método que salva os interesses em um xml.
     *
     * @param controleInteresse - Mapa de interessados.
     */
    public synchronized void salvaInteresses(
            Map<Integer, Interesse> controleInteresse) {
        String xml = xstream.toXML(controleInteresse);

        PrintWriter outputStream = null;
        try {
            outputStream = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(
                    INTERESSES_FILE, false), "UTF-8")));
            outputStream.write(xml);

        } catch (FileNotFoundException e2) {
            e2.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }

    }

    /**
     * Método que acessa os interesses do sistema.
     *
     * @return Mapa de interesses.
     */
    public synchronized Map<Integer, Interesse> leInteresses() {
        File file = new File(INTERESSES_FILE);
        @SuppressWarnings("unchecked")
        Map<Integer, Interesse> interesses = (TreeMap<Integer, Interesse>) xstream.fromXML(file);
        return interesses;

    }

    /**
     * Método que salva os usuários em um xml.
     *
     * @param usuarios - Lista de usuários.
     */
    public synchronized void salvaUsuarios(List<Usuario> usuarios) {
        String xml = xstream.toXML(usuarios);

        PrintWriter outputStream = null;
        try {
            outputStream = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(USERS_FILE,
                    false), "UTF-8")));
            outputStream.write(xml);

        } catch (FileNotFoundException e2) {
            e2.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }

    }

    /**
     * Método que acessa os dados gravados de usuários.
     *
     * @return Lista de Usuários.
     */
    public synchronized List<Usuario> leUsuarios() {
        File file = new File(USERS_FILE);
        @SuppressWarnings("unchecked")
        List<Usuario> mapaUsuarios = (LinkedList<Usuario>) xstream.fromXML(file);
        return mapaUsuarios;

    }

    /**
     * Método que salva as nogociações em um xml.
     *
     * @param negociacoes - Negociação.
     */
    public synchronized void salvaNegociacoes(Negociacao negociacoes) {
        String xml = xstream.toXML(negociacoes);
        PrintWriter outputStream = null;
        try {
            outputStream = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(
                    NEGOCIACOES_FILE, false), "UTF-8")));
            outputStream.write(xml);

        } catch (FileNotFoundException e2) {
            e2.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }

    }

    /**
     * Método que acessa as negociações do sistema.
     *
     * @return Negociações do sistema.
     */
    public synchronized Negociacao leNegociacoes() {
        File file = new File(NEGOCIACOES_FILE);
        Negociacao controlador = (Negociacao) xstream.fromXML(file);
        return controlador;
    }

    /**
     * Método que zera os arquivos.
     */
    public synchronized void clear() {
        // ageitar

        File arquivo1 = new File(NEGOCIACOES_FILE);
        File arquivo2 = new File(USERS_FILE);

        try {
            arquivo1.delete();
            arquivo2.delete();

        } catch (AccessControlException e) {
            e.printStackTrace();
        }

        // String xml = xstream.toXML("");

        /*
         * PrintWriter outputStream = null; try { outputStream = new
         * PrintWriter(new BufferedWriter( new OutputStreamWriter(new
         * FileOutputStream(USERS_FILE, false), "UTF-8")));
         * outputStream.write("");
         *
         * } catch (FileNotFoundException e2) { e2.printStackTrace();
         *
         * } catch (IOException e) { e.printStackTrace(); } finally { if
         * (outputStream != null) { outputStream.close(); } }
         *
         * try { outputStream = new PrintWriter(new BufferedWriter( new
         * OutputStreamWriter(new FileOutputStream( NEGOCIACOES_FILE, false),
         * "UTF-8"))); outputStream.write("");
         *
         * } catch (FileNotFoundException e2) { e2.printStackTrace();
         *
         * } catch (IOException e) { e.printStackTrace(); } finally { if
         * (outputStream != null) { outputStream.close(); } }
         */

    }
}
