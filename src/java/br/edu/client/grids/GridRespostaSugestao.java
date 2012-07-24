package br.edu.client.grids;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.Window;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.data.ResultSet;
import com.smartgwt.client.data.events.DataChangedEvent;
import com.smartgwt.client.data.events.DataChangedHandler;
import com.smartgwt.client.types.ListGridComponent;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

/**
 * Classe que gera um grid dos dados de Resposta de Sugestões.
 *
 * @author
 */
public class GridRespostaSugestao implements IsWidget {

    private final String usuarioLogado;
    private ListGrid grid;
    private Label totalsLabel;

    /**
     * Construtor do grid;
     * 
     * @param usuarioLogado - Login do usuário.
     */
    public GridRespostaSugestao(String usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
        grid = new ListGrid();
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
        grid.setHeight(430);
        grid.setWidth(1135);
        grid.setDataSource(CarregaDadosGridRespostaSugestoes.getInstance());
        grid.setAutoFetchData(true);
        grid.setShowFilterEditor(true);
        grid.setEmptyMessage("Ainda não existem resposta de sugestões para você.");

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
                                + " resposta.");
                    } else if (data.getLength() == 0) {
                        totalsLabel.setContents("Não existem respostas");
                    } else {
                        totalsLabel.setContents(data.getLength()
                                + " respostas.");
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
        ListGridField donoField = new ListGridField("dono_carona", "Dono da Carona");
        ListGridField idField = new ListGridField("id", "Identificador da Solicitação");
        ListGridField caronaField = new ListGridField("carona", "Identificador da Carona");
        ListGridField userField = new ListGridField("usuario_pede", "Usuario que Sugeriu");
        ListGridField pontosField = new ListGridField("resposta", "Respostas de Ponto");
        
        grid.setFields(idField, donoField, caronaField, userField, pontosField);

        grid.filterData(new Criteria("usuario_pede", usuarioLogado));
        userField.setCanFilter(false);
        
        Window janela = new Window();
        janela.setModal(Boolean.TRUE);
        janela.setWidth(1152);
        janela.setHeight(470);
        janela.add(grid);
        janela.focus();
        janela.show();
        return janela;
    }
}
