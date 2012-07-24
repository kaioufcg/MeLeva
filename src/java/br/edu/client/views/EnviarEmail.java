package br.edu.client.views;

import br.edu.client.RPCServico;
import br.edu.client.RPCServicoAsync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer;
import com.sencha.gxt.widget.core.client.info.Info;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;

/**
 *
 * @author
 */
public class EnviarEmail implements IsWidget {

    private static final RPCServicoAsync servico = GWT.create(RPCServico.class);
    private final String sessao;
    private TextItem subjectItem;
    private TextAreaItem messageItem;

    public EnviarEmail(String sessao) {
        this.sessao = sessao;
    }

    @Override
    public Widget asWidget() {
        final Window janela = new Window();

        DynamicForm form = new DynamicForm();
        form.setGroupTitle("Email Me Leva");
        form.setIsGroup(true);
        form.setWidth(300);
        form.setHeight(180);
        //form.setNumCols(2);
        //form.setColWidths(60, "*");
        //form.setBorder("1px solid blue");  
        form.setPadding(5);
        //form.setCanDragResize(true);
        //form.setResizeFrom("R");

        subjectItem = new TextItem();
        subjectItem.setTitle("E-mail Destinatario");
        subjectItem.setWidth("*");

        messageItem = new TextAreaItem();
        messageItem.setShowTitle(false);
        messageItem.setLength(5000);
        //messageItem.setWidth(280);
        messageItem.setColSpan(2);
        messageItem.setWidth("*");
        messageItem.setHeight("*");

        form.setFields(subjectItem, messageItem);

        Button enviar = new Button();
        enviar.setText("Enviar");
        enviar.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                servico.enviarEmail(sessao, subjectItem.getValueAsString().trim(), messageItem.getValueAsString(), new AsyncCallback<String>() {

                    @Override
                    public void onFailure(Throwable caught) {
                        AlertMessageBox d = new AlertMessageBox("Erro", "Desculpe, ocorreu o seguinte erro: " + caught.getMessage());
                        d.show();


                        janela.hide();
                    }

                    @Override
                    public void onSuccess(String result) {
                        Info.display("Seu email foi enviado com sucesso.", "Obrigado por usar o sistema Me Leva.");


                        janela.hide();
                    }
                });
            }
        });

        Button cancel = new Button();
        cancel.setText("Cancelar");
        cancel.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                janela.hide();
            }
        });

        form.draw();
        janela.setModal(Boolean.TRUE);

        janela.addButton(enviar);
        janela.addButton(cancel);
        janela.setButtonAlign(BoxLayoutContainer.BoxLayoutPack.START);
        janela.setWidth(1152);
        janela.setHeight(470);
        janela.add(form);
        janela.setPosition(188, 50);
        janela.focus();
        janela.show();
        return janela;
    }
}
