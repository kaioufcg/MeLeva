package br.edu.server.logicaSistema;

import br.edu.server.logicaCarona.AbstractCarona;
import br.edu.server.logicaCarona.Carona;
import br.edu.server.logicaCarona.CaronaMunicipal;
import br.edu.server.logicaNegociacao.Negociacao;
import br.edu.server.logicaNegociacao.NegociacaoDePonto;
import br.edu.server.logicaUsuario.Usuario;
import com.google.gwt.user.client.rpc.IsSerializable;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.*;
import java.security.AccessControlException;
import java.util.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Classe que controla a persistência de dados do sistema Me Leva.
 *
 * @author Grupo Me Leva - Projeto SI1 - UFCG 2012.1
 *
 */
public class ControleDePersistenciaDeDados implements Serializable, IsSerializable {

    /**
     *
     */
    private static final long serialVersionUID = -6861104397771356681L;
    private volatile static ControleDePersistenciaDeDados unicaInstancia;
    private XStream xstream;
    private final String USERS_FILE = "web/arquivoXML/usuarios.xml";
    private final String NEGOCIACOES_FILE = "web/arquivoXML/negociacoes.xml";
    private final String INTERESSES_FILE = "web/arquivoXML/interesses.xml";
    private final String JSON_CARONA_FILE = "web/arquivoJSON/caronas.json";
    private final String JSON_PEDIDOS_FILE = "web/arquivoJSON/pedidosCaronas.json";
    private final String JSON_SUGESTAO_FILE = "web/arquivoJSON/sugestoes.json";
    private final String JSON_RESPOSTA_SUGESTAO_FILE = "web/arquivoJSON/respostaSugestoes.json";
    private final String JSON_INTERESSES_FILE = "web/arquivoJSON/interessesCaronas.json";
    private final String JSON_CONFIRMADAS_FILE  = "web/arquivoJSON/caronasConfirmadas.json";

    /**
     * Construtor
     */
    private ControleDePersistenciaDeDados() {
        xstream = new XStream(new DomDriver());
        xstream.alias("Usuario", Usuario.class);
        xstream.alias("Carona", Carona.class);
        xstream.alias("AbstractCarona", AbstractCarona.class);
        xstream.alias("CaronaMunicipal", CaronaMunicipal.class);
        xstream.alias("Interesse", Interesse.class);
        xstream.alias("Lista", LinkedList.class);
        xstream.alias("TreeMap", TreeMap.class);
        xstream.alias("Map", Map.class);
        xstream.alias("NegociacaoDePonto", NegociacaoDePonto.class);
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
            e2.getMessage();
        } catch (IOException e) {
            e.getMessage();
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
            e2.getMessage();
        } catch (IOException e) {
            e.getMessage();
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
            e2.getMessage();
        } catch (IOException e) {
            e.getMessage();
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
        File arquivo1 = new File(NEGOCIACOES_FILE);
        File arquivo2 = new File(USERS_FILE);
        File arquivo3 = new File(INTERESSES_FILE);
        File arquivo4 = new File(JSON_CARONA_FILE);
        File arquivo5 = new File(JSON_PEDIDOS_FILE);
        File arquivo6 = new File(JSON_SUGESTAO_FILE);
        File arquivo7 = new File(JSON_RESPOSTA_SUGESTAO_FILE);
        File arquivo8 = new File(JSON_INTERESSES_FILE);
        File arquivo9 = new File(JSON_CONFIRMADAS_FILE);


        try {
            arquivo1.delete();
            arquivo2.delete();
            arquivo3.delete();
            arquivo4.delete();
            arquivo5.delete();
            arquivo6.delete();
            arquivo7.delete();
            arquivo8.delete();
            arquivo9.delete();

        } catch (AccessControlException e) {
            e.getMessage();
        }

    }

    public JSONArray salvaCaronasEmJson(List<Usuario> usuariosCadastradosPorLogin, ControleDeUsuarios controle) {
        JSONArray array = new JSONArray();
        for (Iterator<Usuario> it = usuariosCadastradosPorLogin.iterator(); it.hasNext();) {
            Usuario usuario = it.next();
            for (AbstractCarona carona : usuario.getHistoricoCaronas()) {
                try {
                    JSONObject objeto = new JSONObject();
                    objeto.put("id", carona.getId());
                    objeto.put("origem", carona.getOrigem());
                    objeto.put("destino", carona.getDestino());
                    objeto.put("data", carona.getData());
                    objeto.put("hora", carona.getHora());
                    objeto.put("vagas", carona.getVagas());
                    objeto.put("municipal", carona.getMunicipal());
                    objeto.put("dono_carona", controle.getDono(carona.getId()).getNome());
                    array.put(objeto);
                } catch (JSONException ex) {
                    ex.getMessage();
                }
            }

        }
        PrintWriter outputStream = null;
        try {
            outputStream = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(JSON_CARONA_FILE, false),
                    "UTF-8")));
            outputStream.write(array.toString());

        } catch (FileNotFoundException e2) {
            e2.getMessage();

        } catch (IOException e) {
            e.getMessage();
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }

        return array;
    }

    public JSONArray salvaPedidosCaronaEmJson(Map<String, NegociacaoDePonto> solicitacoesPendentes, ControleDeUsuarios controle) {
        JSONArray array = new JSONArray();
        for (Iterator<NegociacaoDePonto> it = solicitacoesPendentes.values().iterator(); it.hasNext();) {
            try {
                NegociacaoDePonto negociacao = it.next();

                JSONObject objeto = new JSONObject();
                objeto.put("id", String.valueOf(negociacao.getIdSugestao()));
                objeto.put("dono_carona", controle.getDono(negociacao.getCarona().getId()).getNome());
                objeto.put("id_carona", negociacao.getCarona().getId());
                objeto.put("usuario_pede", negociacao.getUsuario().getNome());
                //objeto.put("id_usuario_pede", negociacao.getUsuario().getIdUsuario());
                objeto.put("pontos", negociacao.getPontosDeEncontro().toString());
                array.put(objeto);
            } catch (JSONException ex) {
                ex.getMessage();
            }

        }
        PrintWriter outputStream = null;
        try {
            outputStream = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(JSON_PEDIDOS_FILE, false),
                    "UTF-8")));
            outputStream.write(array.toString());

        } catch (FileNotFoundException e2) {
            e2.getMessage();

        } catch (IOException e) {
            e.getMessage();
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }

        return array;
    }
    
    public JSONArray salvaCaronaConfirmadasEmJson(Map<String, NegociacaoDePonto> solicitacoesConfirmadas, ControleDeUsuarios controle) {
        JSONArray array = new JSONArray();
        for (Iterator<NegociacaoDePonto> it = solicitacoesConfirmadas.values().iterator(); it.hasNext();) {
            try {
                NegociacaoDePonto negociacao = it.next();

                JSONObject objeto = new JSONObject();
                objeto.put("id", String.valueOf(negociacao.getIdSugestao()));
                objeto.put("dono_carona", controle.getDono(negociacao.getCarona().getId()).getLogin());
                objeto.put("id_carona", negociacao.getCarona().getId());
                objeto.put("usuario_pede", negociacao.getUsuario().getNome());
                objeto.put("login_caroneiro", negociacao.getUsuario().getLogin());
                objeto.put("id_usuario_pede", negociacao.getUsuario().getIdUsuario());
                objeto.put("pontos", negociacao.getPontosDeEncontro().toString());
                array.put(objeto);
            } catch (JSONException ex) {
                ex.getMessage();
            }

        }
        PrintWriter outputStream = null;
        try {
            outputStream = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(JSON_CONFIRMADAS_FILE, false),
                    "UTF-8")));
            outputStream.write(array.toString());

        } catch (FileNotFoundException e2) {
            e2.getMessage();

        } catch (IOException e) {
            e.getMessage();
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }

        return array;
    }

    public JSONArray salvaSugestoesEmJson(Map<Integer, NegociacaoDePonto> sugestoesPontoDeEncontro, ControleDeUsuarios controle) {
        JSONArray array = new JSONArray();

        for (Iterator<NegociacaoDePonto> it = sugestoesPontoDeEncontro.values().iterator(); it.hasNext();) {
            try {
                NegociacaoDePonto negociacao = it.next();
                JSONObject objeto = new JSONObject();
                objeto.put("id", String.valueOf(negociacao.getIdSugestao()));
                objeto.put("dono_carona", controle.getDono(negociacao.getCarona().getId()).getNome());
                objeto.put("id_carona", negociacao.getCarona().getId().toString());
                objeto.put("usuario_pede", negociacao.getUsuario().getNome());
                objeto.put("pontos", negociacao.getPontosDeEncontro().toString());
                array.put(objeto);
            } catch (JSONException ex) {
                ex.getMessage();
            }
        }
        PrintWriter outputStream = null;
        try {
            outputStream = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(JSON_SUGESTAO_FILE, false),
                    "UTF-8")));
            outputStream.write(array.toString());

        } catch (FileNotFoundException e2) {
            e2.getMessage();

        } catch (IOException e) {
            e.getMessage();
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }

        return array;
    }

    public JSONArray salvaRespostaSugestoesEmJson(Map<Integer, NegociacaoDePonto> respostasPontoDeEncontro, ControleDeUsuarios controle) {
        JSONArray array = new JSONArray();

        for (Iterator<NegociacaoDePonto> it = respostasPontoDeEncontro.values().iterator(); it.hasNext();) {
            try {
                NegociacaoDePonto negociacao = it.next();
                JSONObject objeto = new JSONObject();
                objeto.put("id", String.valueOf(negociacao.getIdSugestao()));
                objeto.put("dono_carona", controle.getDono(negociacao.getCarona().getId()).getNome());
                objeto.put("carona", negociacao.getCarona().getId().toString());
                objeto.put("usuario_pede", negociacao.getUsuario().getLogin());
                objeto.put("resposta", negociacao.getPontosDeEncontro().toString());
                array.put(objeto);
            } catch (JSONException ex) {
                ex.getMessage();
            }
        }
        PrintWriter outputStream = null;
        try {
            outputStream = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(JSON_RESPOSTA_SUGESTAO_FILE, false),
                    "UTF-8")));
            outputStream.write(array.toString());

        } catch (FileNotFoundException e2) {
            e2.getMessage();

        } catch (IOException e) {
            e.getMessage();
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }

        return array;
    }

    
}
