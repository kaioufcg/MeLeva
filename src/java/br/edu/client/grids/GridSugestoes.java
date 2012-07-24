package br.edu.client.grids;

import br.edu.client.RPCServico;
import br.edu.client.RPCServicoAsync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.TextBox;
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
 * Classe que gera um grid dos dados de Sugestões.
 *
 * @author
 */
public class GridSugestoes implements IsWidget {

    private static final RPCServicoAsync servico = GWT.create(RPCServico.class);
    private ListGrid grid;
    private Label totalsLabel;
    private final String sessao;
    private final String nomeUsuario;
    private TextBox pontos;

    /**
     * Construtor do grid;
     *
     * @param sessao - Identificador da sessão do usuário.
     * @param nomeUsuario - Nome do usuário.
     */
    public GridSugestoes(String sessao, String nomeUsuario) {
        this.sessao = sessao;
        this.nomeUsuario = nomeUsuario;
        grid = new ListGrid();
    }

    @Override
    public Widget asWidget() {
        //controles no grid
        ToolStrip gridEditControls = new ToolStrip();
        gridEditControls.setWidth100();
        gridEditControls.setHeight(24);

        totalsLabel = new Label();
        totalsLabel.setPadding(5);

        LayoutSpacer spacer = new LayoutSpacer();
        spacer.setWidth("*");

        gridEditControls.setMembers(totalsLabel, spacer);

        //configuracao
        grid.setHeight(390);
        grid.setWidth(1135);
        //setDados
        grid.setDataSource(CarregaDadosGridSugestoes.getInstance());
        //
        grid.setAutoFetchData(Boolean.TRUE);
        grid.setShowFilterEditor(true);
        grid.setEmptyMessage("Ainda não existem sugestões para você.");

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
                                + " sugestão.");
                    } else if (data.getLength() == 0) {
                        totalsLabel.setContents("Não existem sugestões");
                    } else {
                        totalsLabel.setContents(data.getLength()
                                + " sugestões.");
                    }
                } else {
                    totalsLabel.setContents(" ");
                }
            }
        });
        grid.setDataProperties(dataProperties);
        //componentes do grid
        grid.setGridComponents(new Object[]{
                    ListGridComponent.HEADER,
                    ListGridComponent.FILTER_EDITOR,
                    ListGridComponent.BODY,
                    gridEditControls
                });
        //fides do grid
        ListGridField donoField = new ListGridField("dono_carona", "Dono da Carona");
        ListGridField idField = new ListGridField("id", "Identificador da Solicitação");
        ListGridField userField = new ListGridField("usuario_pede", "Usuario que Solicitou");
        ListGridField caronaField = new ListGridField("id_carona", "Identificador da Carona");

        grid.setFields(idField, donoField, caronaField, userField);
        
//        servico.getAtributoUsuario(nomeUsuario, "nome", new AsyncCallback<String>() {
//
//            @Override
//            public void onFailure(Throwable caught) {
//                AlertMessageBox d = new AlertMessageBox("Erro", "Desculpe mas ocorreu o seguinte erro: " + caught.getMessage());
//                d.show();
//            }
//
//            @Override
//            public void onSuccess(String result) {
//                //removendo as noociacoes que sao do proprio usuario
//                grid.selectAllRecords();
//                for (ListGridRecord record : grid.getSelectedRecords()) {
//                    if (record.getAttributeAsObject("usuario_pede").toString().equals(result)) {
//                        grid.removeData(record);
//                    }
//                }
//                grid.deselectAllRecords();
//                //grid.setAutoFetchData(true);
//            }
//        });



        //definindo configuracoes da janela
        Window janela = new Window();
        janela.setModal(Boolean.TRUE);
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
        //caixa de texto
        pontos = new TextBox();
        pontos.setTitle("Pontos");
        pontos.setText("Coloque sua sugestão aqui");
        Button sugerirButton = new Button();
        sugerirButton.setText("Sugerir");
        sugerirButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                final ListGridRecord record = grid.getSelectedRecord();
                if (record == null) {
                    return;
                }

                final Object idCarona = record.getAttributeAsObject("carona");

                Info.display("Pontos Sugeridos", pontos.getText());
                servico.sugerirPontoEncontro(sessao, idCarona.toString(), pontos.getText(), new AsyncCallback<String>() {

                    @Override
                    public void onFailure(Throwable caught) {
                        AlertMessageBox d = new AlertMessageBox("Erro", "Desculpe mas ocorreu o seguinte erro: " + caught.getMessage());
                        d.show();
                    }

                    @Override
                    public void onSuccess(String result) {
                        Info.display("A sugestão foi enviada com sucesso.", "Obrigado por usar o sistema Me Leva.");
                    }
                });
            }
        });

//        Button rejeitarButton = new Button();
//        rejeitarButton.setText("Rejeitar");
//        rejeitarButton.addClickHandler(new ClickHandler() {
//
//            @Override
//            public void onClick(ClickEvent event) {
//                final ListGridRecord record = grid.getSelectedRecord();
//                if (record == null) {
//                    return;
//                }
//                final Object idSolicitacao = record.getAttributeAsObject("id");
//                Info.display("Carona selecionada", idSolicitacao.toString());
//                servico.rejeitarSolicitacao(sessao, idSolicitacao.toString(), new AsyncCallback<String>() {
//
//                    @Override
//                    public void onFailure(Throwable caught) {
//                        AlertMessageBox d = new AlertMessageBox("Erro", "Desculpe mas ocorreu o seguinte erro: " + caught.getMessage());
//                        d.show();
//                    }
//
//                    @Override
//                    public void onSuccess(String result) {
//                        Info.display("A solicitação foi rejeitada com sucesso.", "Obrigado por usar o sistema Me Leva.");
//                        grid.removeData(record);
//                    }
//                });
//            }
//        });
//
//        Button aceitarButton = new Button();
//        aceitarButton.setText("Aceitar");
//        aceitarButton.addClickHandler(new ClickHandler() {
//
//            @Override
//            public void onClick(ClickEvent event) {
//                final ListGridRecord record = grid.getSelectedRecord();
//                if (record == null) {
//                    return;
//                }
//                final Object idSolicitacao = record.getAttributeAsObject("id");
//                Info.display("Carona selecionada", idSolicitacao.toString());
//
//                servico.aceitarSolicitacao(sessao, idSolicitacao.toString(), new AsyncCallback<String>() {
//
//                    @Override
//                    public void onFailure(Throwable caught) {
//                        AlertMessageBox d = new AlertMessageBox("Erro", "Desculpe mas ocorreu o seguinte erro: " + caught.getMessage());
//                        d.show();
//                    }
//
//                    @Override
//                    public void onSuccess(String result) {
//                        Info.display("A solicitação foi enviada com sucesso.", "Obrigado por usar o sistema Me Leva.");
//                        Info.display("A sogrid.removeData(record);
//                    }
//                });
//
//
//            }
//        });

        janela.addButton(sugerirButton);
        janela.addButton(pontos);
        janela.addButton(printButton);
        janela.focus();
        janela.show();
        return janela;
    }
}