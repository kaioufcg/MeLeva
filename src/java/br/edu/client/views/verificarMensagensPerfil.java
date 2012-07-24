/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.client.views;

import br.edu.client.RPCServico;
import br.edu.client.RPCServicoAsync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 *
 * @author Família Buscapé
 */
public class verificarMensagensPerfil implements IsWidget {

    private Window winModal;
    private static final RPCServicoAsync servico = GWT.create(RPCServico.class);
    //private ListBox cbItem;
    private final String sessao;
    private final String login;
    private TextArea l;

    public verificarMensagensPerfil(String sessao, String login) {
        this.sessao = sessao;

        this.login = login;

    }

    @Override
    public Widget asWidget() {
        VLayout vLayout = new VLayout();
        vLayout.setMembersMargin(10);

        l = new TextArea();
        servico.verificarMensagensPerfil(sessao, new AsyncCallback<String>() {

            @Override
            public void onFailure(Throwable caught) {
                AlertMessageBox msg = new AlertMessageBox("Erro", "Desculpe, o seguinte erro ocorreu: " + caught.getMessage());
                msg.show();
            }

            @Override
            public void onSuccess(String result) {
                l.setText(result);
            }
        });


        winModal = new Window();
        winModal.setWidth(1152);
        winModal.setHeight(470);
        winModal.setPagePosition(188, 50);
        winModal.setModal(true);
        winModal.setBlinkModal(true);
        winModal.setHeadingText("Mensagens de Caronas do Seu Interesse");

        //vLayout.addMember(cbItem);
        vLayout.addMember(l);
        //vLayout.addMember(b);
        //form.addChild(l);
        winModal.add(vLayout);
        //winModal.addButton(b);
        winModal.setButtonAlign(BoxLayoutContainer.BoxLayoutPack.START);

        winModal.show();
        return winModal;
    }
}
