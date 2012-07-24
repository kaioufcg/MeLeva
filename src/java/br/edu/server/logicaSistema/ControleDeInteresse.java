package br.edu.server.logicaSistema;

import br.edu.client.utilitarios.MeLevaException;
import br.edu.server.logicaCarona.Carona;
import br.edu.server.logicaUsuario.Usuario;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Classe que controla os interesses de usuário em uma carona.
 *
 * @author Grupo Me Leva - Projeto SI1 - UFCG 2012.1
 *
 */
public class ControleDeInteresse implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -1900898757096561744L;
    private volatile static ControleDeInteresse unicaInstancia;
    private Verificador verificador;
    private Map<Integer, Interesse> interessesCadastrados;

    /**
     * Construtor do controlador de interesses.
     */
    private ControleDeInteresse() {
        verificador = new Verificador();
        interessesCadastrados = new TreeMap<Integer, Interesse>();
    }

    /**
     * Método que retorna a instancia do controle de interesse.
     *
     * @return Instância unica.
     */
    public static ControleDeInteresse getInstance() {
        if (unicaInstancia == null) {
            synchronized (ControleDeUsuarios.class) {
                if (unicaInstancia == null) {
                    unicaInstancia = new ControleDeInteresse();
                }
            }
        }
        return unicaInstancia;

    }

    /**
     * Método que adiciona o Interesse.
     *
     * @param user - Usuário.
     * @param origem - Origem de interesse.
     * @param destino - Destino de interesse.
     * @param data - Data de Interesse.
     * @param horaInicio - Hora inicial para o interesse.
     * @param horaFim - Horario final do interesse.
     * @return Identificador de interesse.
     * @throws MeLevaException - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public int addInteresse(Usuario user, String origem, String destino,
            String data, String horaInicio, String horaFim)
            throws MeLevaException {
        if (data == null || !data.equals("")) {
            verificador.verificaData(data);
        }

        verificador.verificaOrigem(origem);
        verificador.verificaDestino(destino);
        Interesse interesse = new Interesse(user, origem, destino, data,
                horaInicio, horaFim);
        getInteressesCadastrados().put(interesse.getId(), interesse);
        return interesse.getId();

    }

    /**
     * Método que avisa os interessados na carona.
     *
     * @param usuarioDono - dono da carona
     * @param carona - carona oferecida pelo usuario
     * @throws MeLevaException - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public void alertaInteressados(Usuario usuarioDono, Carona carona)
            throws MeLevaException {
        Iterator<Interesse> listaInteressados = getInteressesCadastrados().values().iterator();

        while (listaInteressados.hasNext()) {
            Interesse interesse = (Interesse) listaInteressados.next();
            // completo
            if (interesse.getOrigem().equals(carona.getOrigem())
                    && interesse.getDestino().equals(carona.getDestino())
                    && interesse.getData().equals("")) {
                // Se a data eh hoje
                if (verificador.verificaEhHoje(carona.getData())) {
                    interesse.getUser().avisaInteressado(usuarioDono, carona);
                }
                interesse.getUser().avisaInteressado(usuarioDono, carona);
            } else if (interesse.getOrigem().equals(carona.getOrigem())
                    && interesse.getDestino().equals(carona.getDestino())
                    && interesse.getData().equals(carona.getData())) {
                if (verificador.verificaEstaNaHora(interesse.getHoraInicio(),
                        carona.getHora(), interesse.getHoraFim())) {
                    interesse.getUser().avisaInteressado(usuarioDono, carona);

                }
            }

        }
    }

    /**
     * Método que zera as informações contidas no controle.
     */
    public void clear() {
        getInteressesCadastrados().clear();
        Interesse.zeraCont();
    }

    /**
     * Método que acessa os interesses cadastrados.
     *
     * @return Mapa de Interesses.
     */
    public Map<Integer, Interesse> getInteressesCadastrados() {
        return interessesCadastrados;
    }

    /**
     * Método que modifica os interesses cadastrados.
     *
     * @param interessesCadastrados - Mapa de interesses cadastrados.
     */
    public void setInteressesCadastrados(
            Map<Integer, Interesse> interessesCadastrados) {
        this.interessesCadastrados = interessesCadastrados;
    }

    public void iniciarContadores() {
        Interesse.iniciaContador(interessesCadastrados.size());

    }
}
