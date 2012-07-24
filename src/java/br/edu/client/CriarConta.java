package br.edu.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.AbstractHtmlLayoutContainer.HtmlData;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.event.BeforeSelectEvent;
import com.sencha.gxt.widget.core.client.event.BeforeSelectEvent.BeforeSelectHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.info.Info;

public class CriarConta implements IsWidget {

    private static final RPCServicoAsync greetingService = GWT.create(RPCServico.class);
    private static final int COLUMN_FORM_WIDTH = 370;
    private static final int POS_X = 520;
    private static final int POS_Y = 120;
    private VerticalPanel vp;

    @Override
    public Widget asWidget() {
        if (vp == null) {
            vp = new VerticalPanel();
            vp.setSpacing(10);
            createColumnForm();
        }
        return vp;
    }

    public void load() {
        RootPanel.get().add(asWidget());
    }

    private void createColumnForm() {
        final FramedPanel panel = new FramedPanel();
        panel.setHeadingText("Formulario de Cadastramento");
        panel.setPosition(POS_X, POS_Y);
        panel.setWidth(COLUMN_FORM_WIDTH);
        panel.focus();

        final HtmlLayoutContainer con = new HtmlLayoutContainer(
                "Você recebera uma email informando sobre o seu registro.");
        panel.setWidget(con);

        final TextField log = new TextField();
        con.add(new FieldLabel(log, "Login"), new HtmlData(".ln"));

        final TextField name = new TextField();
        con.add(new FieldLabel(name, "Nome Completo"), new HtmlData(".Nn"));

        final TextField email = new TextField();
        FieldLabel mail = new FieldLabel(email, "Email");
        con.add(mail, new HtmlData(".email"));

        final TextField end = new TextField();
        FieldLabel endereco = new FieldLabel(end, "Endereço");
        con.add(endereco, new HtmlData(".end"));

        final TextField password = new TextField();
        FieldLabel senha = new FieldLabel(password, "Senha");
        con.add(senha, new HtmlData(".pw"));

        final TextField password2 = new TextField();
        FieldLabel senha2 = new FieldLabel(password2, "Repita a Senha");
        con.add(senha2, new HtmlData(".pw"));

        // botoes
        final TextButton botaoS = new TextButton("Submeter");
        botaoS.addBeforeSelectHandler(new BeforeSelectHandler() {

            @Override
            public void onBeforeSelect(BeforeSelectEvent event) {
                if (!password.getValue().equals(password2.getValue())) {
                    AlertMessageBox d = new AlertMessageBox("Erro", "Descupe mas ocorreu o seguinte erro: Senhas não são iguais.");
                    d.show();
                } else {
                    greetingService.criarUsuario(log.getValue(), password.getValue(), name.getValue(), end.getValue(), email.getValue(), new AsyncCallback<String>() {

                        @Override
                        public void onFailure(Throwable caught) {
                            AlertMessageBox d = new AlertMessageBox("Erro", "Descupe mas ocorreu o seguinte erro: " + caught.getMessage());
                            d.show();
                            panel.removeFromParent();

                        }

                        @Override
                        public void onSuccess(String result) {
                            Info.display("Obrigado " + name.getValue() + " por usar o sistema Me Leva.", "Você foi cadastrado com sucesso. Cheque sua caixa de mensagens.");
                            panel.removeFromParent();
                        }
                    });
                }

            }
        });
        final TextButton botaoC = new TextButton("Cancelar");
        botaoC.addBeforeSelectHandler(new BeforeSelectHandler() {

            @Override
            public void onBeforeSelect(BeforeSelectEvent event) {
                panel.removeFromParent();

            }
        });
        panel.addButton(botaoS);
        panel.addButton(botaoC);
        vp.add(panel);
    }
}
