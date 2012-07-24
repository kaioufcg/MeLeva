package br.edu.client.views;

import br.edu.client.RPCServico;
import br.edu.client.RPCServicoAsync;
import com.google.gwt.core.client.GWT;
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
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.*;
import java.util.Date;

public class CadastraCarona implements IsWidget {
    private final int C = 16, F = 21;
    private static final RPCServicoAsync servico = GWT.create(RPCServico.class);
    private final String sessao;

    public CadastraCarona(String sessao) {
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
        final TimeItem horaField = new TimeItem("horaItem", "Hora");
        horaField.setUseMask(true);
        horaField.setDefaultValue(Boolean.TRUE);

        // vagas
        final SpinnerItem vagas = new SpinnerItem();
        vagas.setName("Vagas");
        vagas.setDefaultValue(1);
        vagas.setMin(1);

        // titulo
        HeaderItem titulo = new HeaderItem();
        titulo.setDefaultValue("Cadastro de Carona");

        // aparece eh municipal
        final TextItem cidadeField = new TextItem("cidade", "Cidade");
        cidadeField.setRequired(true);
        cidadeField.setVisible(false);

        final CheckboxItem ehMunicipal = new CheckboxItem();
        ehMunicipal.setName("ehMunicipal");
        ehMunicipal.setTitle("Carona Municipal");
        ehMunicipal.setRedrawOnChange(true);
        ehMunicipal.setWidth(50);
        ehMunicipal.setValue(false);

        cidadeField.setShowIfCondition(new FormItemIfFunction() {

            @Override
            public boolean execute(FormItem item, Object value, DynamicForm form) {
                return (Boolean) form.getValue("ehMunicipal");
            }
        });

        // mensagem ajuda
        formulario.setFields(titulo, origemField, destinoField,
                dataField, horaField, vagas, ehMunicipal, cidadeField);
        Button cadastra = new Button();
        cadastra.setText("Cadastrar");
        //printButton.setIcon("[SKIN]/actions/remove.png");
        //printButton.setPrompt("Imprimir lista de caronas.");
        cadastra.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(com.google.gwt.event.dom.client.ClickEvent event) {
                if (ehMunicipal.getValueAsBoolean()) {
                    servico.cadastrarCaronaMunicipal(sessao, origemField.getValueAsString(), destinoField.getValueAsString(), cidadeField.getValueAsString(), getData(dataField.getValueAsDate()), horaField.getValueAsString().substring(C, F), vagas.getValueAsString(), new AsyncCallback<String>() {

                        @Override
                        public void onFailure(Throwable caught) {
                            AlertMessageBox d = new AlertMessageBox("Erro", "Desculpe, ocorreu o seguinte erro: " + caught.getMessage());
                            d.show();
                            janela.hide();
                        }

                        @Override
                        public void onSuccess(String result) {
                            Info.display("Sua carona foi cadastrada com sucesso.", "Obrigado por usar o sistema Me Leva.");

                        }
                    });
                } else {
                    servico.ofereceCarona(sessao, origemField.getValueAsString(), destinoField.getValueAsString(), getData(dataField.getValueAsDate()), horaField.getValueAsString().substring(C, F), vagas.getValueAsString(), new AsyncCallback<String>() {

                        @Override
                        public void onFailure(Throwable caught) {
                            AlertMessageBox d = new AlertMessageBox("Erro", "Desculpe, ocorreu o seguinte erro: " + caught.getMessage());
                            d.show();
                            janela.hide();
                        }

                        @Override
                        public void onSuccess(String result) {
                            //salvar no xml
                            Info.display("Sua carona foi cadastrada com sucesso.", "Obrigado por usar o sistema Me Leva.");
                            janela.hide();
                        }
                    });
                }

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
