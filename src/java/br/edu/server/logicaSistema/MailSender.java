package br.edu.server.logicaSistema;

import java.io.Serializable;
import java.util.Date;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Classe para envio de um email pelo sistema Me Leva.
 *
 * @author Grupo Me Leva - Projeto SI1 - UFCG 2012.1
 *
 */
public class MailSender implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 7023723421561866574L;
    private Properties p;
    private Authenticator autenticador = null;

    /**
     * Construtor das propriedades para envio de mensagem.
     *
     * @param servidor - Servidor para envio de mensagem.
     * @param porta - Porta do servidor.
     */
    public MailSender(String servidor, String porta) {
        p = new Properties();
        p.put("mail.smtp.host", servidor);
        p.put("mail.smtp.port", porta);
        p.put("mail.mime.charset", "ISO-8859-1");
        p.put("mail.smtp.socketFactory.port", porta);
        p.put("mail.smtp.socketFactory.fallback", "false");
        // exclusivamente para debug
        // p.put("mail.debug", "true");
        // p.put("mail.smtp.debug", "true");
    }

    /**
     * Método que cria o objeto autenticador, atribuindo valores necessarios ao
     * Properties. Deve ser chamado sempre que a autenticação for necessária.
     *
     * @param usuario - final para a Inner class.
     * @param senha - final para a Inner class.
     * @param ssl -> se houver necessidade, deve ser setada para true.
     */
    public void autenticarUsuario(final String usuario, final String senha,
            boolean ssl) {
        // adiciona a propriedade que exige autenticacao
        p.put("mail.smtp.auth", "true");
        // verifica se a autenticacao deve ser feita via ssl
        if (ssl) {
            // atributos que ativam o ssl e tsl
            p.put("mail.smtp.socketFactory.class",
                    "javax.net.ssl.SSLSocketFactory");
            p.put("mail.smtp.starttls.enable", "true");
        }

        // gera o objeto autenticador
        autenticador = new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(usuario, senha);
            }
        };
    }

    /**
     * Método que efetivamente envia o email. Caso haja a necessidade de
     * autenticacao, o objeto autenticador ja vem criado atraves do metodo
     * autenticar usuario, caso contrario o objeto referencia null.
     *
     * @param de - Remetente.
     * @param para - Destinatário.
     * @param assunto - Assunto da mensagem.
     * @param corpo - Corpo da Mensagem.
     * @param html -> se esse parametro for true, o texto a ser inserido no
     * corpo do email sera interpretado como se estivesse no formato HTML.
     */
    public void enviarEmail(String de, String para, String assunto,
            String corpo, boolean html) {
        Session session = Session.getInstance(p, autenticador);
        // cria o bojeto mensagem, passando o session com as conf.
        MimeMessage msg = new MimeMessage(session);
        try {
            // coloca os parametros no email
            msg.setFrom(new InternetAddress(de));
            msg.setRecipient(javax.mail.Message.RecipientType.TO,
                    new InternetAddress(para));
            msg.setSentDate(new Date());
            msg.setSubject(assunto);

            // verifica se o corpo do email sera HTML ou texto puro
            if (html) {
                msg.setContent(corpo, "text/html");
            } else {
                msg.setText(corpo);
            }

            // envia o email
            Transport.send(msg);

        } catch (AddressException e) {
            e.getMessage();
        } catch (MessagingException e) {
            e.getMessage();
        }
    }
}