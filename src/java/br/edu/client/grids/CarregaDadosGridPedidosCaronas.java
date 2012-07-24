package br.edu.client.grids;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.DSDataFormat;

/**
 * Classe que carrega os dados no grid de Pedidos feitos por usuários as caronas
 * oferecidas.
 *
 * @author
 */
public class CarregaDadosGridPedidosCaronas extends DataSource {

    private static CarregaDadosGridPedidosCaronas instance = null;

    public static CarregaDadosGridPedidosCaronas getInstance() {
        if (instance == null) {
            instance = new CarregaDadosGridPedidosCaronas("pedidosCaronas");
        }
        return instance;
    }

    private CarregaDadosGridPedidosCaronas(String id) {
        setID(id);
        setDataFormat(DSDataFormat.JSON);

        setDataURL("arquivoJSON/pedidosCaronas.json");

        DataSourceTextField dstfId = new DataSourceTextField("id",
                "Negociação");
        DataSourceTextField dstfDono = new DataSourceTextField("dono_carona",
                "Dono da Carona");
        DataSourceTextField dstfCarona = new DataSourceTextField("id_carona",
                "Carona");
        DataSourceTextField dstfUser = new DataSourceTextField("usuario_pede",
                "Usuário");
        DataSourceTextField dstfPonto = new DataSourceTextField("pontos",
                "Pontos");

        setFields(dstfId, dstfDono, dstfCarona, dstfUser,
                dstfPonto);

        setClientOnly(Boolean.TRUE);
    }
}
