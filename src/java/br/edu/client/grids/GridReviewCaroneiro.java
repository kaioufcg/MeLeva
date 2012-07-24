package br.edu.client.grids;

import br.edu.client.RPCServico;
import br.edu.client.RPCServicoAsync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.info.Info;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.data.ResultSet;
import com.smartgwt.client.data.events.DataChangedEvent;
import com.smartgwt.client.data.events.DataChangedHandler;
import com.smartgwt.client.types.ListGridComponent;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

/**
 * Classe que gera um grid dos dados de Resposta de Sugestões.
 *
 * @author
 */
public class GridReviewCaroneiro implements IsWidget {

    private static final RPCServicoAsync servico = GWT.create(RPCServico.class);
    private final String usuarioLogado;
    private ListGrid grid;
    private Label totalsLabel;
    private final String sessao;

    /**
     * Construtor do grid;
     *
     * @param usuarioLogado - Login do usuário.
     * @param sessao
     */
    public GridReviewCaroneiro(String usuarioLogado, String sessao) {
        this.usuarioLogado = usuarioLogado;
        grid = new ListGrid();
        this.sessao = sessao;

    }

    @Override
    public Widget asWidget() {
        //controles do grid
        ToolStrip gridEditControls = new ToolStrip();
        gridEditControls.setWidth100();
        gridEditControls.setHeight(24);

        totalsLabel = new Label();
        totalsLabel.setPadding(5);

        LayoutSpacer spacer = new LayoutSpacer();
        spacer.setWidth("*");

        gridEditControls.setMembers(totalsLabel, spacer);

        //configurando o grid
        grid.setHeight(390);
        grid.setWidth(1135);
        grid.setDataSource(CarregaDadosGridReview.getInstance());
        grid.setAutoFetchData(true);
        grid.setShowFilterEditor(true);
        grid.setEmptyMessage("Ainda não existem caronas que você confirmou.");

        //barra de propriedades
        ResultSet dataProperties = new ResultSet();
        dataProperties.addDataChangedHandler(new DataChangedHandler() {

            @Override
            public void onDataChanged(DataChangedEvent event) {
                RecordList data = grid.getDataAsRecordList();
                if (data != null
                        && data instanceof ResultSet && ((ResultSet) data).lengthIsKnown()
                        && data.getLength() > 0) {
                    if (data.getLength() == 1) {
                        totalsLabel.setContents(data.getLength()
                                + " carona.");
                    } else {
                        totalsLabel.setContents(data.getLength()
                                + " caronas.");
                    }
                } else {
                    totalsLabel.setContents(" ");
                }
            }
        });
        grid.setDataProperties(dataProperties);
        grid.setGridComponents(new Object[]{
                    ListGridComponent.HEADER,
                    ListGridComponent.FILTER_EDITOR,
                    ListGridComponent.BODY,
                    gridEditControls
                });

        //definindo os fildes
        ListGridField idField = new ListGridField("id", "Identificador da Confirmação");
        ListGridField donoField = new ListGridField("dono_carona", "Dono");
        ListGridField idCaronaField = new ListGridField("id_carona", "Identificador da Carona");
        //ListGridField userField = new ListGridField("usuario_pede", "Caroneiro");
        ListGridField idUserField = new ListGridField("id_usuario_pede", "Identificador do Caroneiro");
        ListGridField userField = new ListGridField("login_caroneiro", "Caroneiro");

        grid.setFields(idField, donoField, idCaronaField, userField, idUserField);

        grid.filterData(new Criteria("login_caroneiro", usuarioLogado));
        userField.setCanFilter(false);

        //botoes
        // Add a drop box with the list types
        final ListBox dropBox = new ListBox(false);
        String[] listTypes = new String[]{"segura e tranquila", "não funcionou"};
        for (int i = 0; i < listTypes.length; i++) {
            dropBox.addItem(listTypes[i]);
        }
        dropBox.ensureDebugId("cwListBox-dropBox2");

        Button printButton = new Button();
        printButton.setText("Imprimir");
        printButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                Canvas.showPrintPreview(grid);
            }
        });

        Button revisarButton = new Button();
        revisarButton.setText("Revisar");
        revisarButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                final ListGridRecord record = grid.getSelectedRecord();
                if (record == null) {
                    return;
                }

                final Object idCarona = record.getAttributeAsObject("id_carona");
                
                servico.reviewCarona(sessao, idCarona.toString(), dropBox.getItemText(dropBox.getSelectedIndex()), new AsyncCallback<String>() {

                    @Override
                    public void onFailure(Throwable caught) {
                        AlertMessageBox d = new AlertMessageBox("Erro", "Desculpe mas ocorreu o seguinte erro: " + caught.getMessage());
                        d.show();
                    }

                    @Override
                    public void onSuccess(String result) {
                       Info.display("A carona foi revisada com sucesso.", "Obrigado por usar o sistema Me Leva.");
                    }
                });
            }
        });

        Window janela = new Window();
        janela.addButton(revisarButton);
        janela.addButton(dropBox);
        janela.addButton(printButton);
        janela.setModal(Boolean.TRUE);
        janela.setWidth(1152);
        janela.setHeight(470);
        janela.add(grid);
        janela.focus();
        janela.show();
        return janela;
    }
}
