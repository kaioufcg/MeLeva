package br.edu.client.views;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.maps.client.InfoWindowContent;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.Maps;
import com.google.gwt.maps.client.control.LargeMapControl3D;
import com.google.gwt.maps.client.control.MapTypeControl;
import com.google.gwt.maps.client.geocode.Geocoder;
import com.google.gwt.maps.client.geocode.LatLngCallback;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widget.client.TextButton;
import com.sencha.gxt.widget.core.client.Window;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;

/*
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class MapaGoogle implements IsWidget {

    private LatLng cawkerCity;
    private Widget resultado;
    private Geocoder geocoder;
    private TextBox local;
    

    private Window buildUi() {
        // Open a map centered on Cawker City, KS USA
        cawkerCity = LatLng.newInstance(-7.4873562, -36.6642387);

        final MapWidget map = new MapWidget(cawkerCity, 2);

        map.setSize("1150px", "450px");
        // Add some controls for the zoom level
        //map.addControl(new LargeMapControl());

        // Add a marker
        map.addOverlay(new Marker(cawkerCity));

        // Add an info window to highlight a point of interest
        map.getInfoWindow().open(map.getCenter(),
                new InfoWindowContent("Sistema Me Leva foi criado na pricesa do cariri paraibano."));

        final DockLayoutPanel dock = new DockLayoutPanel(Unit.PX);
        dock.addNorth(map, 500);


        // Add some type controls for the different map types
        map.addControl(new MapTypeControl());

        map.addControl(new LargeMapControl3D());


        //for the gecoding codes add this to your button...

        geocoder = new Geocoder();

        local = new TextBox();
        local.setTitle("Local");

        TextButton butao = new TextButton();
        butao.setTitle("Buscar");
        butao.setText("Buscar");
        butao.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                geocoder.getLatLng(local.getText(), new LatLngCallback() {

                    @Override
                    public void onFailure() {
                        SC.confirm("Erro", "Desculpe mas sua busca não deu certo.", new BooleanCallback() {

                            @Override
                            public void execute(Boolean value) {
                            }
                        });
                    }

                    @Override
                    public void onSuccess(LatLng point) {

                        cawkerCity = LatLng.newInstance(point.getLatitude(), point.getLongitude());

                        map.setCenter(cawkerCity);
                        Marker marker = new Marker(point);

                        map.addOverlay(marker);

                    }
                });
            }
        });

        map.setZoomLevel(5);
        Window janela = new Window();
        janela.setModal(Boolean.TRUE);
        janela.focus();
        janela.setWidth(1150);
        janela.setHeight(450);
        janela.add(map);
        //janela.center();
        janela.addButton(butao);
        //butao.setFocus(true);
        janela.addButton(local);
        janela.setPosition(188, 50);
               
        janela.show();        
        
        return janela;
    }

    @Override
    public Widget asWidget() {
        /*
         * Asynchronously loads the Maps API.
         *
         * The first parameter should be a valid Maps API Key to deploy this
         * application on a public server, but a blank key will work for an
         * application served from localhost.
         */
        Maps.loadMapsApi("", "2", false, new Runnable() {

            @Override
            public void run() {
                resultado = buildUi();
            }
        });

        return resultado;
    }
}
