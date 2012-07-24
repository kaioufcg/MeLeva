package br.edu.client;

import br.edu.client.views.Logado;
import br.edu.client.views.SenhaEsquecida;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.widget.client.TextButton;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.box.AutoProgressMessageBox;

/**
 * Entry point classes define
 * <code>onModuleLoad()</code>.
 */
public class MeLevaEntryPoint implements EntryPoint {

    private static final RPCServicoAsync servico = GWT.create(RPCServico.class);
    private AutoProgressMessageBox box;
    private RootPanel rootPanel;

    @Override
    public void onModuleLoad() {
        box = new AutoProgressMessageBox("Por favor espere", "Carregando sistema...");
        box.setProgressText("Iniciando...");
        box.auto();
        box.show();

        Timer t = new Timer() {

            //float i;
            @Override
            public void run() {
                //box.updateProgress(i / 100, "{0}% Completo");
                //i += 10;
                //ageitar contadores
                servico.iniciaSistema(new AsyncCallback<String>() {

                    @Override
                    public void onFailure(Throwable caught) {
                        AlertMessageBox d = new AlertMessageBox("Desculpe", "Erro ao carregar sistema." + caught.getMessage());
                        //d.addHideHandler(hideHandler);
                        d.show();
                        //cancel();

                    }

                    @Override
                    public void onSuccess(String result) {
                        //AlertMessageBox d = new AlertMessageBox("Alerta", "OK");
                        //d.addHideHandler(hideHandler);
                        //d.show();
                    }
                });
                //if (i > 100) {
                //  cancel();

                //}
                box.hide();
                cancel();
            }
        };
        t.scheduleRepeating(3000);

        //painel principal
        rootPanel = RootPanel.get();

        Image image_5 = new Image("imagens/imagemPojeto.png");
        rootPanel.add(image_5, 0, 0);
        image_5.setSize("100%", "570px");

        final Label lblLogin = new Label("Login:");
        rootPanel.add(lblLogin, 633, 249);

        Label lblSenha = new Label("Senha:");
        rootPanel.add(lblSenha, 627, 285);

        // botao criar conta
        final TextButton txtbtnCriarContaAgora = new TextButton(
                "Criar Conta Agora");
        txtbtnCriarContaAgora.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                CriarConta conta = new CriarConta();
                conta.load();
            }
        });
        rootPanel.add(txtbtnCriarContaAgora, 329, 360);
        
        Hyperlink htmlEsqueceuASenha = new Hyperlink("Esqueceu a senha?", true, "");
        //htmlEsqueceuASenha.setStyleName("serverResponseLabelError");
        htmlEsqueceuASenha.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                SenhaEsquecida senhaEsquecida = new SenhaEsquecida();
                senhaEsquecida.carregar();

            }
        });
        
        final TextBox textBox = new TextBox();
        //rootPanel.add(textBox, 676, 200);


        final PasswordTextBox passwordTextBox = new PasswordTextBox();
        
        passwordTextBox.setSize("161px", "16px");

        // botao logar
        final TextButton txtbtnLogar = new TextButton("Logar");
        txtbtnLogar.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                servico.getSenhaUsuarioLogin(textBox.getText(), new AsyncCallback<String>() {

                    @Override
                    public void onFailure(Throwable caught) {
                        AlertMessageBox d = new AlertMessageBox("Alerta", caught.getMessage());
                        d.show();
                    }

                    @Override
                    public void onSuccess(String result) {
                        if (passwordTextBox.getText().equals(result)) {
                            Logado log = new Logado(textBox.getText().trim(), result);
                            rootPanel.add(log.asWidget());
                        } else {
                            AlertMessageBox d = new AlertMessageBox("Alerta", "Senha incorreta.");
                            d.show();
                        }
                    }
                });

            }
        });
        rootPanel.add(txtbtnLogar, 676, 315);
        rootPanel.add(textBox, 676, 250);
        rootPanel.add(passwordTextBox, 678, 285);
        rootPanel.add(htmlEsqueceuASenha, 676, 342);
    }
}