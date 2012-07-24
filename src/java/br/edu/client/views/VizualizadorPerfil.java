package br.edu.client.views;

import br.edu.client.RPCServico;
import br.edu.client.RPCServicoAsync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.ListBox;
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
public class VizualizadorPerfil implements IsWidget {

    private Window winModal;
    private static final RPCServicoAsync servico = GWT.create(RPCServico.class);
    private ListBox cbItem;
    private final String login;
    private TextArea l;

    public VizualizadorPerfil(String sessao, String login) {

        this.login = login;

    }

    @Override
    public Widget asWidget() {
        VLayout vLayout = new VLayout();
        vLayout.setMembersMargin(10);



        l = new TextArea();
        l.setText("O atributo será vizualizado aqui.");

        winModal = new Window();
        winModal.setWidth(1152);
        winModal.setHeight(470);
        winModal.setPagePosition(188, 50);
        winModal.setModal(true);
        winModal.setBlinkModal(true);
        winModal.setHeadingText("Perfil");


//        servico.visualizarPerfil(sessao, login, new AsyncCallback<String>() {
//
//            @Override
//            public void onFailure(Throwable caught) {
//                AlertMessageBox msg = new AlertMessageBox("Erro", "Desculpe mas ocorreu o seguinte erro: " + caught.getMessage());
//                msg.show();
//            }
//
//            @Override
//            public void onSuccess(String result) {
//                texto1 += result + "/n";
//            }
//        });
        cbItem = new ListBox();
        cbItem.setTitle("Atributos");
        String[] s = new String[]{"nome", "endereco", "historico de caronas", "email",
            "historico de vagas em caronas",
            "caronas seguras e tranquilas",
            "caronas que não funcionaram",
            "faltas em vagas de caronas",
            "presenças em vagas de caronas"};

        for (String string : s) {
            cbItem.addItem(string);
        }
//        cbItem.setValueMap("nome", "endereco", "historico de caronas", "email",
//                "historico de vagas em caronas",
//                "caronas seguras e tranquilas",
//                "caronas que não funcionaram",
//                "faltas em vagas de caronas",
//                "presenças em vagas de caronas");
//


        cbItem.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                servico.getAtributoPerfil(login, cbItem.getValue(cbItem.getSelectedIndex()), new AsyncCallback<String>() {

                    @Override
                    public void onFailure(Throwable caught) {
                        AlertMessageBox msg = new AlertMessageBox("Erro", "Desculpe, o seguinte erro ocorreu: " + caught.getMessage());
                        msg.show();
                    }

                    @Override
                    public void onSuccess(String result) {
                        l.setText(result);
                        //cbItem.setHint("<nobr>" + result + "</nobr>");
                    }
                });
            }
        });

        vLayout.addMember(cbItem);
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
