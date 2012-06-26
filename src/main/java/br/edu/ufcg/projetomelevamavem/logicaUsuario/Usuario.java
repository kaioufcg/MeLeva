package br.edu.ufcg.projetomelevamavem.logicaUsuario;

import br.edu.ufcg.projetomelevamavem.logicaCarona.AbstractCarona;
import br.edu.ufcg.projetomelevamavem.logicaCarona.Carona;
import br.edu.ufcg.projetomelevamavem.logicaCarona.CaronaMunicipal;
import br.edu.ufcg.projetomelevamavem.logicaSistema.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Classe para representação de um usuário do sistema Me Leva.
 *
 * @author Grupo Me Leva - Projeto SI1 - UFCG 2012.1
 *
 */
public class Usuario implements Serializable {

    private static final long serialVersionUID = 00000000017L;
    private String login;
    private String senha;
    private String nome;
    private String endereco;
    private String email;
    private final Integer ID_USUARIO;
    // perfil
    private int caronasQueFuncionaram = 0;
    private int caronasQueNaoFuncionaram = 0;
    private int faltaDeVagas = 0;
    private int presencaEmVagas = 0;
    private List<AbstractCarona> historicoCaronas;
    private List<AbstractCarona> historicoPedidoDeVagasEmCaronas;
    // mensagens
    private List<String> mensagens;
    private ControleDeInteresse controleInteressados;
    private static Integer contadorId = 0;
    private Verificador verificador;

    /**
     * Enum dos atributos de um usuário.
     *
     * @author Grupo Me Leva - Projeto SI1 - UFCG 2012.1
     *
     */
    enum AtributosDeUsuario {

        NOME("nome"), ENDERECO("endereco"), HISTORICODECARONAS(
        "historico de caronas"), EMAIL("email"), HISTORICOVAGASCARONAS(
        "historico de vagas em caronas"), CARONASSEGURAS(
        "caronas seguras e tranquilas"), CARONASNAOFUNCIONOU(
        "caronas que não funcionaram"), FALTAEMVAGASDECARONAS(
        "faltas em vagas de caronas"), PRESENCAEMVAGASDECARONA(
        "presenças em vagas de caronas");
        private String atributo;

        /**
         * Construtor do enum.
         *
         * @param atributo - Atributo de usuário.
         */
        AtributosDeUsuario(String atributo) {
            this.atributo = atributo;
        }

        /**
         * Método que acessa o atributo do usuário.
         *
         * @return Atributo de um usuário.
         */
        public String getAtributo() {
            return atributo;
        }
    }

    /**
     * Enum dos atributos de revisão de carona.
     *
     * @author Grupo Me Leva - Projeto SI1 - UFCG 2012.1
     *
     */
    enum AtributosDeReview {

        NAO_FALTOU("não faltou"), FALTOU("faltou"), CARONA_FUNCIONOU(
        "segura e tranquila"), CARONA_NAO_FUNCIONOU("não funcionou");
        private String atributo;

        /**
         * Construtor do enum.
         *
         * @param atributo - Atributo de revisão de carona.
         */
        private AtributosDeReview(String atributo) {
            this.atributo = atributo;
        }

        /**
         * Método que acessa o atributo de revisão de carona.
         *
         * @return Atributo de um atributo de revisão de carona.
         */
        public String getAtributo() {
            return atributo;
        }
    }

    /**
     * Constrotor de um usuário do sistema Me Leva.
     *
     * @param login - Login do usuário.
     * @param senha - Senha do usuário.
     * @param nome - Nome do usuário.
     * @param endereco - Endereço do usuário.
     * @param email - E-mail do usuário.
     * @throws MeLevaException - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public Usuario(String login, String senha, String nome, String endereco,
            String email) throws MeLevaException {
        verificador = new Verificador();
        // Verifica Entradas
        verificador.verificaLogin(login);
        verificador.verificaSenha(senha);
        verificador.verificaNome(nome);
        verificador.verificaEndereco(endereco);
        verificador.verificaEmail(email);

        this.login = login;
        this.senha = senha;
        this.nome = nome;
        this.endereco = endereco;
        this.email = email;

        // caronasOfertadas = new LinkedHashMap<String, Carona>();

        // numero id
        ID_USUARIO = ++contadorId;
        // para perfil
        historicoCaronas = new LinkedList<AbstractCarona>();
        historicoPedidoDeVagasEmCaronas = new LinkedList<AbstractCarona>();

        mensagens = new LinkedList<String>();

        controleInteressados = ControleDeInteresse.getInstance();

    }

    /**
     * Método que cadastra carona.
     *
     * @param origem - String que representa a origem de onde parte a carona.
     * @param destino - String que representa o destino da carona.
     * @param data - Data que parte a carona.
     * @param hora - Horário que parte a carona.
     * @param vagas - Quantidade de vagas na carona.
     * @return Identificador de uma carona.
     * @throws MeLevaException - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public int cadastraCarona(String origem, String destino, String data,
            String hora, Integer vagas) throws MeLevaException {
        Carona carona = new Carona(origem, destino, data, hora, vagas);
        controleInteressados.alertaInteressados(this, carona);
        this.addHistoricoCaronas(carona);
        return Integer.valueOf(carona.getId());
    }

    /**
     * Método que cadastra carona municipal.
     *
     * @param origem - String que representa a origem de onde parte a carona.
     * @param destino - String que representa o destino da carona.
     * @param cidade - Cidade municipal.
     * @param data - Data que parte a carona.
     * @param hora - Horário que parte a carona.
     * @param vagas - Quantidade de vagas na carona.
     * @return Identificador de uma carona municipal.
     * @throws MeLevaException - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public int cadastraCarona(String origem, String destino, String cidade,
            String data, String hora, Integer vagas) throws MeLevaException {
        AbstractCarona carona = new CaronaMunicipal(origem, destino, cidade,
                data, hora, vagas);
        this.addHistoricoCaronas(carona);
        return Integer.valueOf(carona.getId());
    }

    /**
     * Método localiza uma carona cadastrada.
     *
     * @param origem - String que representa a origem de onde parte a carona.
     * @param destino - String que representa o destino da carona.
     * @return Lista de caronas da origem para o destino.
     */
    public List<String> localizaCarona(String origem, String destino) {

        List<String> caronasLocalizadas = new LinkedList<String>();
        Iterator<AbstractCarona> iteradorDeCaronas = this.getCaronas();

        while (iteradorDeCaronas.hasNext()) {
            AbstractCarona carona = iteradorDeCaronas.next();
            // retorno todos com o mesmo destino e origem
            if (carona.getOrigem().equals(origem)
                    && carona.getDestino().equals(destino)) {
                caronasLocalizadas.add(carona.getId());
                // se a destino eh vzio
                // retorno todos com o mesmo origem
            } else if (carona.getOrigem().equals(origem) && destino.equals("")) {
                caronasLocalizadas.add(carona.getId());
                // se a origem eh vzio
                // retorno todos com o mesmo destino
            } else if (carona.getDestino().equals(destino) && origem.equals("")) {
                caronasLocalizadas.add(carona.getId());
                // Se a origem e o destino forem vazias
                // retorna todas as caronas possiveis cadastradas
            } else if (origem.equals("") && destino.equals("")) {
                caronasLocalizadas.add(carona.getId());
            }
        }
        return caronasLocalizadas;
    }

    /**
     * Método que localiza uma carona municipal especificada.
     *
     * @param origem - Origem da carona.
     * @param destino - Destino da carona.
     * @param cidade - Cidade da carona.
     * @return Lista de caronas municipais especificadas.
     */
    public List<String> localizaCaronaMunicipal(String origem, String destino,
            String cidade) {
        List<String> caronasLocalizadas = new LinkedList<String>();
        Iterator<AbstractCarona> iteradorDeCaronas = this.getCaronas();

        while (iteradorDeCaronas.hasNext()) {
            AbstractCarona carona = iteradorDeCaronas.next();
            // retorno todos com o mesmo destino e origem e cidade
            if (carona.getOrigem().equals(origem)
                    && carona.getDestino().equals(destino)
                    && ((CaronaMunicipal) carona).getCidade().equals(cidade)) {
                caronasLocalizadas.add(carona.getId());
                // retorno todos com o mesmo origem independene da cidade
            } else if (carona.getOrigem().equals(origem) && destino.equals("")
                    && ((CaronaMunicipal) carona).getCidade().equals("")) {
                caronasLocalizadas.add(carona.getId());
                // retorno todos com o mesmo destino
            } else if (carona.getDestino().equals(destino) && origem.equals("")
                    && ((CaronaMunicipal) carona).getCidade().equals("")) {
                caronasLocalizadas.add(carona.getId());
                // Se a origem e o destino forem vazias
                // retorna todas as caronas possiveis cadastradas na cidade
            } else if (origem.equals("") && destino.equals("")
                    && ((CaronaMunicipal) carona).getCidade().equals(cidade)) {
                caronasLocalizadas.add(carona.getId());
            }
        }
        return caronasLocalizadas;
    }

    /**
     * Método que localiza uma carona municipal especificada.
     *
     * @param cidade - Cidade da carona.
     * @return Lista de caronas municipais especificadas.
     */
    public List<String> localizaCaronaMunicipal(String cidade) {
        List<String> caronasLocalizadas = new LinkedList<String>();
        Iterator<AbstractCarona> iteradorDeCaronas = this.getHistoricoCaronas().iterator();

        while (iteradorDeCaronas.hasNext()) {
            AbstractCarona carona = iteradorDeCaronas.next();
            if (carona instanceof CaronaMunicipal) {
                if (((CaronaMunicipal) carona).getCidade().equals(cidade)) {
                    caronasLocalizadas.add(carona.getId());
                }
            }

        }
        return caronasLocalizadas;
    }

    /**
     * Método que adiciona no histórico de caronas.
     *
     * @param carona - carona a ser adicionada no histórico.
     */
    public void addHistoricoCaronas(AbstractCarona carona) {
        historicoCaronas.add(carona);
    }

    /**
     * Método que adiciona nas viagens confirmadas.
     *
     * @param carona - Carona a ser adicionada.
     */
    public void addViagemConfirmada(AbstractCarona carona) {
        historicoPedidoDeVagasEmCaronas.add(carona);
    }

    /**
     * Revisão de uma carona que foi confirmada sua vaga na viajem.
     *
     * @param review - Revisão de uma carona.
     * @throws MeLevaException - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public void reviewVagaEmCarona(String review) throws MeLevaException {
        if (review.trim().equalsIgnoreCase(
                AtributosDeReview.NAO_FALTOU.getAtributo())) {
            presencaEmVagas++;
        } else if (review.trim().equalsIgnoreCase(
                AtributosDeReview.FALTOU.getAtributo())) {
            this.faltaDeVagas++;
        } else {
            throw new MeLevaException(MSGDeExcecao.MSG_ERRO_REVIEW_INVALIDA);
        }

    }

    /**
     * Método que modifica as revisões das caronas que viajaram ou não viajaram.
     *
     * @param review - Revisão da carona.
     * @throws MeLevaException - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public void reviewCarona(String review) throws MeLevaException {
        if (review.trim().equalsIgnoreCase(
                AtributosDeReview.CARONA_FUNCIONOU.getAtributo())) {
            caronasQueFuncionaram++;
        } else if (review.trim().equalsIgnoreCase(
                AtributosDeReview.CARONA_NAO_FUNCIONOU.getAtributo())) {
            caronasQueNaoFuncionaram++;
        } else {
            throw new MeLevaException(MSGDeExcecao.MSG_ERRO_REVIEW_INVALIDA);
        }

    }

    /**
     * Método que acessa a carona pelo identificador.
     *
     * @param idCarona - Identificador da carona.
     * @return Carona.
     */
    public AbstractCarona getCarona(String idCarona) {
        Iterator<AbstractCarona> caronasIt = this.getCaronas();
        AbstractCarona carona = null;
        while (caronasIt.hasNext() && carona == null) {
            AbstractCarona caronaDoUsuario = caronasIt.next();
            if (String.valueOf(caronaDoUsuario.getId()).equals(idCarona)) {
                carona = caronaDoUsuario;
            }
        }
        return carona;
    }

    /**
     * Método que acessa o numero de caronas que funcionaram.
     *
     * @return Caronas que funcionaram.
     */
    public int getCaronasQueFuncionaram() {
        return caronasQueFuncionaram;
    }

    /**
     * Método que acessa o numero de caronas que não funcionaram.
     *
     * @return Quantidade de caronas que não funcionaram.
     */
    public int getCaronasQueNaoFuncionaram() {
        return caronasQueNaoFuncionaram;
    }

    /**
     * Método que acessa as caronas ofertadas.
     *
     * @return Lista de caronas ofertadas.
     */
    public List<AbstractCarona> getCaronasOfertadas() {
        return historicoCaronas;
    }

    /**
     * Método que acessa as caronas perdidas.
     *
     * @return Lista de caronas de vagas pedidas a outros usuários.
     */
    public List<AbstractCarona> getCaronasPedidas() {
        return historicoPedidoDeVagasEmCaronas;
    }

    /**
     * Método de acesso do login.
     *
     * @return Login do usuário.
     */
    public String getLogin() {
        return login;
    }

    /**
     * Método que acessa a senha do usuário.
     *
     * @return Senha do usuário.
     */
    public String getSenha() {
        return senha;
    }

    /**
     * Método que acessa o nome do usuário.
     *
     * @return Nome do usuário.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Método que acessa o endereço do usuário.
     *
     * @return Endereço do usuário.
     */
    public String getEndereco() {
        return endereco;
    }

    /**
     * Método que acessa o e-mail do usuário.
     *
     * @return E-mail do usuário.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Método que acessa o Identificador do usuário.
     *
     * @return Identificador do usuário.
     */
    public String getIdUsuario() {
        return String.valueOf(ID_USUARIO);
    }

    /**
     * Método que acessa o atributo de usuário.
     *
     * @param atributo - Atributo de retorno do usuário.
     * @return String do atributo do usuário.
     * @throws MeLevaException - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public String getAtributoUsuario(String atributo) throws MeLevaException {
        verificador.verificaAtributo(atributo);
        String resp;

        if (atributo.equalsIgnoreCase(AtributosDeUsuario.NOME.getAtributo())) {
            resp = getNome();
        } else if (atributo.equalsIgnoreCase(AtributosDeUsuario.ENDERECO.getAtributo())) {
            resp = getEndereco();
        } else if (atributo.equalsIgnoreCase(AtributosDeUsuario.EMAIL.getAtributo())) {
            resp = getEmail();
        } else if (atributo.equalsIgnoreCase(AtributosDeUsuario.CARONASNAOFUNCIONOU.getAtributo())) {
            resp = String.valueOf(getCaronasQueNaoFuncionaram());
        } else if (atributo.equalsIgnoreCase(AtributosDeUsuario.CARONASSEGURAS.getAtributo())) {
            resp = String.valueOf(getCaronasQueFuncionaram());
        } else if (atributo.equalsIgnoreCase(AtributosDeUsuario.FALTAEMVAGASDECARONAS.getAtributo())) {
            resp = String.valueOf(getFaltaDeVagas());
        } else if (atributo.equalsIgnoreCase(AtributosDeUsuario.HISTORICODECARONAS.getAtributo())) {
            resp = historicoCaronas();

        } else if (atributo.equalsIgnoreCase(AtributosDeUsuario.HISTORICOVAGASCARONAS.getAtributo())) {
            resp = historicoVagasCaronas();

        } else if (atributo.equalsIgnoreCase(AtributosDeUsuario.PRESENCAEMVAGASDECARONA.getAtributo())) {
            resp = String.valueOf(getPresencaEmVagas());
        } else {
            throw new MeLevaException(
                    MSGDeExcecao.MSG_ERRO_ATRIBUTO_INEXISTENTE);
        }

        return resp;
    }

    /**
     * Método que acessa o histórico de vagas em caronas.
     *
     * @return Lista do histórico de vagas em caronas.
     */
    public List<AbstractCarona> getHistoricoPedidoDeVagasEmCaronas() {
        return historicoPedidoDeVagasEmCaronas;
    }

    /**
     * Método que acessa o histórico de caronas.
     *
     * @return Lista do histórico de caronas.
     */
    public List<AbstractCarona> getHistoricoCaronas() {
        return historicoCaronas;
    }

    /**
     * Método que acessa as faltas de caronas que recebram confirmação de vaga.
     *
     * @return Falta de caronas na viajem.
     */
    public int getFaltaDeVagas() {
        return faltaDeVagas;
    }

    /**
     * Método que acessa a quantidade de presença de vagas em caronas.
     *
     * @return Quantidade de caronas que viajaram.
     */
    public int getPresencaEmVagas() {
        return presencaEmVagas;
    }

    /**
     * Método acessador de mensagens de interesses por caronas.
     *
     * @return Lista de interesses.
     */
    public List<String> getMensagens() {
        return mensagens;
    }

    /**
     * Método que envia uum email pelo sistema Me Leva.
     *
     * @param usuario - Usuário remetente.
     * @param destino - Usuário destinatário.
     * @param message - Mensagem para o destino.
     * @return Boolean verificador de envio ocorreu corretamente.
     * @throws MeLevaException - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public boolean enviarEmail(Usuario usuario, String destino, String message)
            throws MeLevaException {

        verificador.verificaMensagem(message);

        final String FROM = "melevaproject@gmail.com";
        final String KEY = "projetoSi12012";

        MailSender mail = new MailSender("smtp.gmail.com", "465");
        mail.autenticarUsuario(FROM, KEY, true);
        mail.enviarEmail(FROM, destino, "<<Me Leva Correspondência>>", message
                + "\nEnviado por: " + usuario.getEmail(), false);

        return true;
    }

    /**
     * Método que avisa aos interessados pela carona específica.
     *
     * @param usuario - Usuário que infroma a carona.
     * @param carona - Carona.
     */
    public void avisaInteressado(Usuario usuario, Carona carona) {
        mensagens.add("Carona cadastrada no dia "
                + carona.getData()
                + ", às "
                + carona.getHora()
                + " de acordo com os seus interesses registrados. Entrar em contato com "
                + usuario.getEmail());

    }

    /**
     * Método que acesse a lista de histórico de caronas oferecidas.
     *
     * @return String do histórico de caronas oferecidas.
     */
    private String historicoCaronas() {
        List<String> result = new LinkedList<String>();
        if (!historicoCaronas.isEmpty()) {
            Iterator<AbstractCarona> iterador = historicoCaronas.iterator();
            while (iterador.hasNext()) {
                AbstractCarona carona = iterador.next();
                result.add(carona.getId());
            }
        }
        return Arrays.toString(result.toArray()).replace("{", "[").replace("}", "]");
    }

    /**
     * Método para acessar as caronas oferecidas pelo usuário com iterador.
     *
     * @return Iterador de carona oferecidas.
     */
    private Iterator<AbstractCarona> getCaronas() {
        return historicoCaronas.iterator();
    }

    /**
     * Método que acessa a lista de histórico vagas em caronas.
     *
     * @return String da lista do historico de vagas em caronas.
     */
    private String historicoVagasCaronas() {
        List<String> result = new LinkedList<String>();
        if (!historicoPedidoDeVagasEmCaronas.isEmpty()) {
            Iterator<AbstractCarona> iterador = historicoPedidoDeVagasEmCaronas.iterator();
            while (iterador.hasNext()) {
                AbstractCarona carona = iterador.next();
                result.add(carona.getId());
            }
        }
        return Arrays.toString(result.toArray()).replace("{", "[").replace(" ", "").replace("}", "]");
    }

    /**
     * Método que zera os identificadores.
     */
    public static void zeraCont() {
        contadorId = 0;
    }
}
