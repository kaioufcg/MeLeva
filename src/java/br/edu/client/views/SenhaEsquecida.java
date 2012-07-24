package br.edu.client.views;

import br.edu.client.RPCServico;
import br.edu.client.RPCServicoAsync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.box.PromptMessageBox;
import com.sencha.gxt.widget.core.client.event.HideEvent;
import com.sencha.gxt.widget.core.client.event.HideEvent.HideHandler;
import com.sencha.gxt.widget.core.client.info.Info;

public class SenhaEsquecida {

    public static final RPCServicoAsync servico = GWT.create(RPCServico.class);

    public SenhaEsquecida() {
    }

    public void carregar() {
        final PromptMessageBox box = new PromptMessageBox("Recuperação de senha", "Por favor, entre com seu email:");

        final HideHandler hideHandler = new HideHandler() {

            @Override
            public void onHide(HideEvent event) {
                Dialog btn = (Dialog) event.getSource();
                //String msg = Format.substitute("The '{0}' button was pressed", btn.getHideButton().getText());
                if (btn.getHideButton().getText().equals("OK")) {

                    servico.enviaEmailSenhaEsquecida(box.getValue(), new AsyncCallback<String>() {

                        @Override
                        public void onSuccess(String result) {
                            Info.display("Seu email foi enviado " + box.getValue(), "Obrigado por usar o sistema Me Leva.");

                        }

                        @Override
                        public void onFailure(Throwable caught) {
                            Info.display("Seu email não foi enviado " + box.getValue(), caught.getMessage());

                        }
                    });

                }
                //Info.display("MessageBox", msg);
            }
        };
        box.addHideHandler(hideHandler);
        box.show();

    }
}