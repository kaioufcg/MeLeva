package br.edu.client.grids;

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
 * Classe que gera um grid dos dados de Caronas oferecidas pelo usuario, que
 * foram solicitados vagas.
 *
 * @author
 */
public class GridPedidosCaronas implements IsWidget {

    private static final RPCServicoAsync servico = GWT.create(RPCServico.class);
    private ListGrid grid;
    private Label totalsLabel;
    private final String sessao;
    private final String nomeUsuario;

    /**
     * Construtor
     * @param sessao - sessao do usuário.
     * @param nomeUsuario - login do usário
     */
    public GridPedidosCaronas(String sessao, String nomeUsuario) {
        this.sessao = sessao;
        this.nomeUsuario = nomeUsuario;
        grid = new ListGrid();
    }

    @Override
    public Widget asWidget() {
        //controles do grid
        ToolStrip gridEditControls = new ToolStrip();
        gridEditControls.setWidth100();
        gridEditControls.setHeight(24);

        totalsLabel = new Label();
        totalsLabel.setPadding(10);

        LayoutSpacer spacer = new LayoutSpacer();
        spacer.setWidth("*");

        gridEditControls.setMembers(totalsLabel, spacer);

        //configuracao do grid
        grid.setHeight(390);
        grid.setWidth(1135);
        grid.setDataSource(CarregaDadosGridPedidosCaronas.getInstance());
        grid.setAutoFetchData(true);
        grid.setShowFilterEditor(true);
        grid.setEmptyMessage("Ainda não existem pedidos nas suas caronas.");

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
                                + " solicitação.");
                    } else if (data.getLength() == 0) {
                        totalsLabel.setContents("Não existem solicitações.");
                    } else {
                        totalsLabel.setContents(data.getLength()
                                + " solicitações.");
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
        //fildes
        ListGridField donoField = new ListGridField("dono_carona", "Dono da Carona");
        ListGridField idField = new ListGridField("id", "Identificador da Solicitação");
        ListGridField caronaField = new ListGridField("id_carona", "Identificador da Carona");
        ListGridField userField = new ListGridField("usuario_pede", "Usuario que Solicitou");
        ListGridField pontosField = new ListGridField("pontos", "Pontos de encontro");
        grid.setFields(idField, donoField, caronaField, userField, pontosField);

        //filtar para aparecer apenas os pedidos do usario
        grid.filterData(new Criteria("dono_carona", nomeUsuario));
        donoField.setCanFilter(false);
        //grid.setAutoFetchData(true); 

        Window janela = new Window();
        janela.setModal(Boolean.TRUE);
        janela.setWidth(1152);
        janela.setHeight(470);
        janela.add(grid);
        Button printButton = new Button();
        printButton.setText("Imprimir");
        printButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                Canvas.showPrintPreview(grid);
            }
        });

        Button recarregarButton = new Button();
        recarregarButton.setText("Rejeitar");
        recarregarButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                final ListGridRecord record = grid.getSelectedRecord();
                if (record == null) {
                    return;
                }
                final Object idSolicitacao = record.getAttributeAsObject("id");
                Info.display("Carona selecionada", idSolicitacao.toString());
                servico.rejeitarSolicitacao(sessao, idSolicitacao.toString(), new AsyncCallback<String>() {

                    @Override
                    public void onFailure(Throwable caught) {
                        AlertMessageBox d = new AlertMessageBox("Erro", "Desculpe mas ocorreu o seguinte erro: " + caught.getMessage());
                        d.show();
                    }

                    @Override
                    public void onSuccess(String result) {
                        Info.display("A solicitação foi rejeitada com sucesso.", "Obrigado por usar o sistema Me Leva.");
                        grid.removeData(record);
                    }
                });
            }
        });

        Button pedirButton = new Button();
        pedirButton.setText("Aceitar");
        pedirButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                final ListGridRecord record = grid.getSelectedRecord();
                if (record == null) {
                    return;
                }
                final Object idSolicitacao = record.getAttributeAsObject("id");
                Info.display("Carona selecionada", idSolicitacao.toString());

                servico.aceitarSolicitacao(sessao, idSolicitacao.toString(), new AsyncCallback<String>() {

                    @Override
                    public void onFailure(Throwable caught) {
                        AlertMessageBox d = new AlertMessageBox("Erro", "Desculpe mas ocorreu o seguinte erro: " + caught.getMessage());
                        d.show();
                    }

                    @Override
                    public void onSuccess(String result) {
                        Info.display("A solicitação foi enviada com sucesso.", "Obrigado por usar o sistema Me Leva.");
                        grid.removeData(record);
                    }
                });


            }
        });
        janela.addButton(pedirButton);
        janela.addButton(recarregarButton);
        janela.addButton(printButton);
        janela.focus();
        janela.show();
        return janela;
    }
}