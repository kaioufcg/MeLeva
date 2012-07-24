package br.edu.client.grids;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.DSDataFormat;

/**
 * Classe que carrega os dados no grid de Caronas do Usuario para revisar.
 *
 * @author
 */
public class CarregaDadosGridReview extends DataSource {

    private static CarregaDadosGridReview instance = null;

    public static CarregaDadosGridReview getInstance() {
        if (instance == null) {
            instance = new CarregaDadosGridReview("confirmadas");
        }
        return instance;
    }

    private CarregaDadosGridReview(String id) {
        setID(id);
        setDataFormat(DSDataFormat.JSON);

        setDataURL("arquivoJSON/caronasConfirmadas.json");

        DataSourceTextField dstfId = new DataSourceTextField("id",
                "Identificador da Viajem");
        DataSourceTextField dstfOrigem = new DataSourceTextField("dono_carona",
                "Dono");
        DataSourceTextField dstfDestino = new DataSourceTextField("id_carona",
                "Id Carona");
        DataSourceTextField dstfData = new DataSourceTextField("usuario_pede",
                "Usuario");
        DataSourceTextField dstfH_I = new DataSourceTextField("id_usuario_pede",
                "Id Usuario");
        DataSourceTextField dstflogin = new DataSourceTextField("login_caroneiro",
                "Login Caroneiro");

        DataSourceTextField dstfUser = new DataSourceTextField("pontos",
                "Pontos");

        setFields(dstfId, dstfUser, dstfOrigem, dstfDestino, dstfData, dstflogin, dstfH_I, dstfUser);

        setClientOnly(Boolean.TRUE);
    }
}
