package br.edu.client.views;

import br.edu.client.RPCServico;
import br.edu.client.RPCServicoAsync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer;
import com.sencha.gxt.widget.core.client.info.Info;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.HeaderItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.TimeItem;
import java.util.Date;

public class CadastraInteresse implements IsWidget {

    private final int C = 16, F = 21;
    private static final RPCServicoAsync servico = GWT.create(RPCServico.class);
    private final String sessao;

    public CadastraInteresse(String sessao) {
        this.sessao = sessao;
    }

    private String getData(Date data) {
        DateTimeFormat fmt = DateTimeFormat.getFormat("dd/MM/yyyy");
        return fmt.format(data);
    }

    @Override
    public Widget asWidget() {
        final Window janela = new Window();

        // formulario
        final DynamicForm formulario = new DynamicForm();

        // origem e destino
        final TextItem origemField = new TextItem("origem", "Origem");
        final TextItem destinoField = new TextItem("destino", "Destino");

        // data
        final DateItem dataField = new DateItem();
        dataField.setName("dataItem");
        dataField.setTitle("Data");
        dataField.setRequired(true);

        // hora
        final TimeItem horaInicialField = new TimeItem("horaItem", "Hora Inicial");
        horaInicialField.setUseMask(true);
        horaInicialField.setDefaultValue(Boolean.TRUE);

        // hora final
        final TimeItem horaFinalField = new TimeItem("horaItem", "Hora Final");
        horaFinalField.setUseMask(true);
        horaFinalField.setDefaultValue(Boolean.TRUE);

        // titulo
        HeaderItem titulo = new HeaderItem();
        titulo.setDefaultValue("Cadastro de Interesse");

        // mensagem ajuda
        formulario.setFields(titulo, origemField, destinoField,
                dataField, horaInicialField, horaFinalField);
        Button cadastra = new Button();
        cadastra.setText("Cadastrar");
        //printButton.setIcon("[SKIN]/actions/remove.png");
        //printButton.setPrompt("Imprimir lista de caronas.");
        cadastra.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {

                servico.cadastrarInteresse(sessao, origemField.getValueAsString(), destinoField.getValueAsString(), getData(dataField.getValueAsDate()), horaInicialField.getValueAsString().substring(C, F), horaFinalField.getValueAsString().substring(C, F), new AsyncCallback<String>() {

                    @Override
                    public void onFailure(Throwable caught) {
                        AlertMessageBox d = new AlertMessageBox("Erro", "Desculpe, ocorreu o seguinte erro: " + caught.getMessage());
                        d.show();
                        janela.hide();
                    }

                    @Override
                    public void onSuccess(String result) {
                        Info.display("Seu interesse foi cadastrado com sucesso.", "Obrigado por usar o sistema Me Leva.");
                        janela.hide();
                    }
                });
            }
        });

        formulario.draw();
        janela.setModal(Boolean.TRUE);

        janela.addButton(cadastra);
        janela.setButtonAlign(BoxLayoutContainer.BoxLayoutPack.START);
        janela.setWidth(1152);
        janela.setHeight(470);
        janela.add(formulario);
        janela.setPosition(188, 50);
        janela.focus();
        janela.show();
        return janela;
    }
}
