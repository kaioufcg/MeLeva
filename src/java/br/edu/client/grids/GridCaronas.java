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
 * Classe que gera um grid dos dados de Caronas oferecidas pelos usuarios.
 *
 * @author
 */
public class GridCaronas implements IsWidget {

    private static final RPCServicoAsync servico = GWT.create(RPCServico.class);
    private ListGrid grid;
    private Label totalsLabel;
    private final String sessao;
    private final String loginUser;
    //private String nome;

    /**
     * Construtor
     *
     * @param sessao - Sessao do usuário.
     * @param loginUser - login usuario
     */
    public GridCaronas(String sessao, String loginUser) {
        this.sessao = sessao;
        grid = new ListGrid();
        this.loginUser = loginUser;
    }

    @Override
    public Widget asWidget() {
        //controles
        ToolStrip gridEditControls = new ToolStrip();
        gridEditControls.setWidth100();
        gridEditControls.setHeight(24);

        totalsLabel = new Label();
        totalsLabel.setPadding(5);

        LayoutSpacer spacer = new LayoutSpacer();
        spacer.setWidth("*");

        gridEditControls.setMembers(totalsLabel, spacer);


        //configuracao grid
        grid.setHeight(380);
        grid.setWidth(1135);
        grid.setDataSource(CarregaDadosGridCaronas.getInstance());

        grid.setShowFilterEditor(true);
        //grid.setAutoDraw(Boolean.TRUE);
        //grid.setAutoFetchAsFilter(Boolean.FALSE);
        grid.setAutoFetchData(Boolean.TRUE);
        //grid.setResizeFieldsInRealTime(Boolean.TRUE);
        grid.setAutoFetchAsFilter(Boolean.TRUE);
        //grid.setCanSelectAll(true); 
        grid.setEmptyMessage("Ainda não existem caronas cadastradas.");
        //grid.setCanEdit(true);  
        //grid.setEditEvent(ListGridEditEvent.NONE);  

        //fildes
        ListGridField destinoField = new ListGridField("destino", "Destino");
        ListGridField origemField = new ListGridField("origem", "Origem");
        ListGridField dataField = new ListGridField("data", "Data");
        ListGridField horaField = new ListGridField("hora", "Hora");
        ListGridField idField = new ListGridField("id", "Identificador");
        ListGridField vagasField = new ListGridField("vagas", "Vagas");
        ListGridField muniField = new ListGridField("municipal", "Carona Municipal");
        ListGridField donoField = new ListGridField("dono_carona", "Dono da Carona");

        grid.setFields(idField, donoField, origemField, destinoField, dataField, horaField, vagasField, muniField);
        //grid.setShowAllRecords(true);  
//        servico.getAtributoUsuario(loginUser, "nome", new AsyncCallback<String>() {
//
//            @Override
//            public void onFailure(Throwable caught) {
//                AlertMessageBox d = new AlertMessageBox("Erro", "Desculpe mas ocorreu o seguinte erro: " + caught.getMessage());
//                d.show();
//            }
//
//            @Override
//            public void onSuccess(String result) {
//                nome = result;
//            }
//        });

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
                    } else if (data.getLength() == 0) {
                        totalsLabel.setContents("Não existem caronas.");
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

        //grid.setCriteria(new Criteria("id", nome));
        grid.setGridComponents(new Object[]{
                    ListGridComponent.HEADER,
                    ListGridComponent.FILTER_EDITOR,
                    ListGridComponent.BODY,
                    gridEditControls
                });
        //grid.setShowAllRecords(true);

       // grid.getInstantScrollTrackRedraw();
        //grid.getResizeFieldsInRealTime();

        //construindo janela
        Window janela = new Window();
        janela.setModal(Boolean.TRUE);
        //janela.focus();
        janela.setWidth(1152);
        janela.setHeight(470);
        janela.add(grid);

        //botoes
        Button printButton = new Button();
        printButton.setText("Imprimir");
        printButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                Canvas.showPrintPreview(grid);
            }
        });
//
//        Button recarregarButton = new Button();
//        recarregarButton.setText("Recarregar");
//        recarregarButton.addClickHandler(new ClickHandler() {
//
//            @Override
//            public void onClick(ClickEvent event) {
//                //removendo as noociacoes que sao do proprio usuario
//                grid.refreshFields();
//                
//                grid.redraw();
//                
//                grid.selectAllRecords();
//                
//                for (ListGridRecord record : grid.getSelectedRecords()) {
//                    if (record.getAttributeAsObject("dono_carona").toString().equals(nome)) {
//                        grid.deselectAllRecords();
//                        grid.selectRecord(record);
//                        grid.updateShadow();
//                        grid.selectAllRecords();
//                    }
//                    //grid.updateData(record);
//                }
//
//
////                for (ListGridRecord record : grid.getSelectedRecords()) {
////                    //AlertMessageBox d = new AlertMessageBox(nome, record.getAttributeAsObject("dono_carona").toString());
////                    //d.show();
////                    Info.display(nome, record.getAttributeAsObject("dono_carona").toString());
////                    
////                    System.out.println(nome + " Result");
////                    System.out.println(record.getAttributeAsObject("dono_carona").toString());
////
////                    if (record.getAttributeAsObject("dono_carona").toString().equals(nome)) {
////                        grid.removeData(record);
////                    }
////                    //grid.refreshFields();
////                }
//
//                grid.deselectAllRecords();
//                //grid.redraw();
//                //grid.setAutoFetchData(true);
//            }
//        });

        Button pedirButton = new Button();
        pedirButton.setText("Solicitar Carona");
        pedirButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                ListGridRecord record = grid.getSelectedRecord();
                if (record == null) {
                    return;
                }
                //grid.startEditing(grid.getDataAsRecordList().indexOf(record), 0, false);  
                //grid.getDragData();
                final Object idCarona = record.getAttributeAsObject("id");
                Info.display("Carona selecionada", idCarona.toString());

                servico.pedirCarona(sessao, idCarona.toString(), new AsyncCallback<String>() {

                    @Override
                    public void onFailure(Throwable caught) {
                        AlertMessageBox d = new AlertMessageBox("Erro", "Desculpe, ocorreu o seguinte erro: " + caught.getMessage());
                        d.show();
                    }

                    @Override
                    public void onSuccess(String result) {
                        Info.display("Sua solicitação foi enviada com sucesso.", "Obrigado por usar o sistema Me Leva.");
                    }
                });


            }
        });
        janela.addButton(pedirButton);
        //janela.addButton(recarregarButton);
        janela.addButton(printButton);
        janela.focus();
        janela.show();
        return janela;
    }
}
