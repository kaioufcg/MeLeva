package br.edu.client.views;

import br.edu.client.RPCServico;
import br.edu.client.RPCServicoAsync;
import br.edu.client.grids.*;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.core.client.util.Padding;
import com.sencha.gxt.core.client.util.ToggleGroup;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.button.ToggleButton;
import com.sencha.gxt.widget.core.client.container.AccordionLayoutContainer;
import com.sencha.gxt.widget.core.client.container.AccordionLayoutContainer.AccordionLayoutAppearance;
import com.sencha.gxt.widget.core.client.container.AccordionLayoutContainer.ExpandMode;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutData;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer.VBoxLayoutAlign;
import com.sencha.gxt.widget.core.client.event.BeforeSelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItemIcon;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;

public class Logado implements IsWidget {

    private static final RPCServicoAsync servico = GWT.create(RPCServico.class);
    private static final String HELPTEXT = "<p style=\"padding:10px;color:#556677;font-size:11px;\">Por favor, selecione uma das opções a esquerda.</p><br><br><b>Ajuda 1</b> - Problema com botão cadastramento.<br>Se o botão de cadastramento não aparecer, por favor marque a caixa Carona Municipal, depois desmarque se essa for sua opção."
            + "<br><br><b>Severity 2</b> - Major problem<br>An important function of the system "
            + "is not available in production, and the user's operations are restricted."
            + "<br><br><b>Severity 3</b> - Minor problem<br>Inability to use a function of the "
            + "system occurs, but it does not seriously affect the user's operations.";
    private ContentPanel lccenter;
    private ToggleGroup toggleGroup;
    private ContentPanel cp;
    private final String usuarioLogado;
    private String iDusuario = null;
    private String sessao;

    public Logado(String usuarioLogado, String senha) {
        this.usuarioLogado = usuarioLogado;
        servico.abrirSessao(usuarioLogado, senha, new AsyncCallback<String>() {

            @Override
            public void onFailure(Throwable caught) {
                AlertMessageBox d = new AlertMessageBox("Erro", "Desculpe, o seguinte erro ocorreu: " + caught.getMessage());
                d.show();
            }

            @Override
            public void onSuccess(String result) {
                sessao = result;
            }
        });



    }

    @Override
    public Widget asWidget() {
        servico.getIdUsuarioLogin(usuarioLogado, new AsyncCallback<String>() {

            @Override
            public void onFailure(Throwable caught) {
                AlertMessageBox d = new AlertMessageBox("Erro", "Desculpe, o seguinte erro ocorreu: " + caught.getMessage());
                d.show();
            }

            @Override
            public void onSuccess(String result) {
                iDusuario = result;
            }
        });
        final DynamicForm formulario = new DynamicForm();
        formulario.setHeight(450);
        formulario.setWidth(450);

        FormItemIcon icon = new FormItemIcon();
        icon.setSrc("[SKIN]/actions/help.png");

        final StaticTextItem ajuda = new StaticTextItem();
        ajuda.setTitle("Ajuda");
        ajuda.setIcons(icon);
        ajuda.addIconClickHandler(new IconClickHandler() {

            @Override
            public void onIconClick(IconClickEvent event) {
                SC.say("Bem Vindo ao Sistema Me Leva", HELPTEXT);
            }
        });


        final ScrollPanel con = new ScrollPanel();

        con.getElement().getStyle().setMargin(10, Unit.PX);

        ContentPanel panel = new ContentPanel();

        panel.setHeadingText("Você esta Logado como " + usuarioLogado);
        panel.setPixelSize(600, 500);
        panel.setWidth("100%");


        BorderLayoutContainer border = new BorderLayoutContainer();
        panel.setWidget(border);

        VBoxLayoutContainer lcwest = new VBoxLayoutContainer();
        lcwest.setPadding(new Padding(5));
        lcwest.setVBoxLayoutAlign(VBoxLayoutAlign.STRETCH);

        BorderLayoutData west = new BorderLayoutData(150);
        west.setMargins(new Margins(5));

        border.setWestWidget(lcwest, west);

        lccenter = new ContentPanel();
        lccenter.setHeaderVisible(Boolean.FALSE);

        formulario.setFields(ajuda);

        lccenter.add(formulario);

        MarginData center = new MarginData(new Margins(5));

        border.setCenterWidget(lccenter, center);

        BoxLayoutData vBoxData = new BoxLayoutData(new Margins(5, 5, 5, 5));
        vBoxData.setFlex(1);

        lcwest.add(barra(), vBoxData);
        TextButton butao = new TextButton("Buscar Local para carona");
        butao.addBeforeSelectHandler(new BeforeSelectEvent.BeforeSelectHandler() {

            @Override
            public void onBeforeSelect(BeforeSelectEvent event) {
                addToCenter(new MapaGoogle().asWidget());
            }
        });
        lcwest.add(butao);

        TextButton butaoPerfil = new TextButton("Vizualizar Perfil");
        butaoPerfil.addBeforeSelectHandler(new BeforeSelectEvent.BeforeSelectHandler() {

            @Override
            public void onBeforeSelect(BeforeSelectEvent event) {
                addToCenter(new VizualizadorPerfil(sessao, usuarioLogado).asWidget());
            }
        });
        lcwest.add(butaoPerfil);

        lcwest.add(
                createToggleButton("Sair", new ValueChangeHandler<Boolean>() {

            @Override
            public void onValueChange(ValueChangeEvent<Boolean> event) {
                if (event.getValue()) {

                    servico.encerraLogin(usuarioLogado, new AsyncCallback<String>() {

                        @Override
                        public void onFailure(Throwable caught) {
                            AlertMessageBox d = new AlertMessageBox("Erro", "Desculpe, o seguinte erro ocorreu: " + caught.getMessage());

                            d.show();
                        }

                        @Override
                        public void onSuccess(String result) {
                            if (result.equalsIgnoreCase(Boolean.FALSE.toString())) {
                                AlertMessageBox d = new AlertMessageBox("Erro", "Desculpe, o seguinte erro ocorreu: Você não esta logado.");
                                d.show();
                            } else {

                                con.removeFromParent();
                            }

                        }
                    });

                }
            }
        }), vBoxData);

        con.add(panel);

        return con;
    }

    public void onModuleLoad() {
        RootPanel.get().add(asWidget());
    }

    private void addToCenter(Widget c) {
        lccenter.clear();
        lccenter.add(c);
        lccenter.forceLayout();
    }

    private ToggleButton createToggleButton(String name,
            ValueChangeHandler<Boolean> handler) {
        ToggleButton button = new ToggleButton(name);
        toggleGroup = new ToggleGroup();
        toggleGroup.add(button);
        button.addValueChangeHandler(handler);
        button.setAllowDepress(false);
        return button;
    }

    private ContentPanel barra() {
        ContentPanel panel = null;

        if (panel == null) {
            panel = new ContentPanel();
            panel.setHeadingText("Opções");
            panel.setBodyBorder(false);
            panel.setPixelSize(200, 325);
            panel.addStyleName("margin-10");
            // panel.getHeader().setIcon(ExampleImages.INSTANCE.accordion());

            AccordionLayoutContainer con = new AccordionLayoutContainer();
            con.setExpandMode(ExpandMode.SINGLE_FILL);
            panel.add(con);

            AccordionLayoutAppearance appearance = GWT.<AccordionLayoutAppearance>create(AccordionLayoutAppearance.class);

            //Caronas
            cp = new ContentPanel(appearance);
            cp.setAnimCollapse(Boolean.FALSE);
            cp.setHeadingText("Caronas");
            FlexTable table = new FlexTable();
            table.setHeight("100px");
            table.setWidth("100px");
            TextButton butao1 = new TextButton("Cadastrar Carona", new SelectEvent.SelectHandler() {

                @Override
                public void onSelect(SelectEvent event) {
                    addToCenter(new CadastraCarona(sessao).asWidget());
                }
            });
            TextButton butao2 = new TextButton("Pedir Carona", new SelectEvent.SelectHandler() {

                @Override
                public void onSelect(SelectEvent event) {
                    addToCenter(new GridCaronas(sessao, usuarioLogado).asWidget());
                }
            });

            TextButton resposta = new TextButton("Aceitar/Rejeitar Vagas", new SelectEvent.SelectHandler() {

                @Override
                public void onSelect(SelectEvent event) {
                    addToCenter(new GridPedidosCaronas(sessao, usuarioLogado).asWidget());
                }
            });

            table.setWidget(0, 0, butao1);
            table.setWidget(1, 0, butao2);
            table.setWidget(2, 0, resposta);
            //table.setWidget(3, 0, butao4);

            //table.add(butao2);
            cp.setWidget(table);
//            cp.add(createToggleButton("Pedir Carona",
//                    new ValueChangeHandler<Boolean>() {
//
//                        @Override
//                        public void onValueChange(
//                                ValueChangeEvent<Boolean> event) {
//                            if (event.getValue()) {
//                                addToCenter(new GridXml().asWidget());
//                            }
//                        }
//                    }), new BoxLayoutData(new Margins(1, 1, 1, 1)));
            con.add(cp);
            con.setActiveWidget(cp);

            //Sugestoes
            ContentPanel cp2 = new ContentPanel(appearance);
            cp2.setAnimCollapse(false);
            cp2.setBodyStyleName("pad-text");
            cp2.setHeadingText("Sugestões");
            FlexTable tableSugestoes = new FlexTable();
            tableSugestoes.setHeight("100px");
            tableSugestoes.setWidth("100px");
            TextButton sugerir = new TextButton("Sugerir Ponto", new SelectEvent.SelectHandler() {

                @Override
                public void onSelect(SelectEvent event) {
                    addToCenter(new GridSugestoes(sessao, usuarioLogado).asWidget());
                }
            });
            TextButton responderSugestao = new TextButton("Respostas de Sugestão", new SelectEvent.SelectHandler() {

                @Override
                public void onSelect(SelectEvent event) {
                    addToCenter(new GridRespostaSugestao(usuarioLogado).asWidget());
                }
            });
            tableSugestoes.setWidget(0, 0, sugerir);
            tableSugestoes.setWidget(1, 0, responderSugestao);
            //table.add(butao2);
            cp2.setWidget(tableSugestoes);
            con.add(cp2);
            con.setActiveWidget(cp2);

            //Negociacoes
            ContentPanel cp3 = new ContentPanel(appearance);
            cp3.setAnimCollapse(false);
            cp3.setBodyStyleName("pad-text");
            cp3.setHeadingText("Interesses");

            FlexTable tableBotoesInteresse = new FlexTable();
            tableBotoesInteresse.setHeight("100px");
            tableBotoesInteresse.setWidth("100px");
            TextButton butao01 = new TextButton("Cadastrar Interesse", new SelectEvent.SelectHandler() {

                @Override
                public void onSelect(SelectEvent event) {
                    addToCenter(new CadastraInteresse(sessao).asWidget());
                }
            });
            TextButton butao02 = new TextButton("Respostas de Interesse", new SelectEvent.SelectHandler() {

                @Override
                public void onSelect(SelectEvent event) {
                    addToCenter(new verificarMensagensPerfil(sessao, usuarioLogado).asWidget());
                }
            });

            TextButton butao03 = new TextButton("Me Leva Email", new SelectEvent.SelectHandler() {

                @Override
                public void onSelect(SelectEvent event) {
                    addToCenter(new EnviarEmail(sessao).asWidget());
                }
            });

            tableBotoesInteresse.setWidget(0, 0, butao01);
            tableBotoesInteresse.setWidget(1, 0, butao02);
            tableBotoesInteresse.setWidget(2, 0, butao03);

            cp3.setWidget(tableBotoesInteresse);
            con.add(cp3);
            con.setActiveWidget(cp3);

            //configuracoes
            ContentPanel cp4 = new ContentPanel(appearance);
            cp4.setAnimCollapse(false);
            cp4.setBodyStyleName("pad-text");
            cp4.setHeadingText("Configurações");
            FlexTable tableBotoesReview = new FlexTable();
            tableBotoesReview.setHeight("100px");
            tableBotoesReview.setWidth("100px");
            TextButton butao001 = new TextButton("Caroneiro Faltou?", new SelectEvent.SelectHandler() {

                @Override
                public void onSelect(SelectEvent event) {
                    addToCenter(new GridReview(usuarioLogado, sessao).asWidget());
                }
            });
            TextButton butao002 = new TextButton("Viagem Tranquila?", new SelectEvent.SelectHandler() {

                @Override
                public void onSelect(SelectEvent event) {
                    addToCenter(new GridReviewCaroneiro(usuarioLogado, sessao).asWidget());
                }
            });

            tableBotoesReview.setWidget(0, 0, butao001);
            tableBotoesReview.setWidget(1, 0, butao002);

            cp4.setWidget(tableBotoesReview);
            con.add(cp4);

        }
        return panel;

    }
}
