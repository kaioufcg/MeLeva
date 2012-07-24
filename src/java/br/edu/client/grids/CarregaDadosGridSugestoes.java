package br.edu.client.grids;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.DSDataFormat;

/**
 * Classe que carrega os dados no grid de Sugestoes
 *
 * @author
 */
public class CarregaDadosGridSugestoes extends DataSource {

    private static CarregaDadosGridSugestoes instance = null;

    public static CarregaDadosGridSugestoes getInstance() {
        if (instance == null) {
            instance = new CarregaDadosGridSugestoes("sugestoes");
        }
        return instance;
    }

    private CarregaDadosGridSugestoes(String id) {
        setID(id);
        setDataFormat(DSDataFormat.JSON);

        setDataURL("arquivoJSON/sugestoes.json");

        DataSourceTextField dstfId = new DataSourceTextField("id",
                "Negociação");
        DataSourceTextField dstfDono = new DataSourceTextField("dono_carona",
                "Dono da Carona");
        DataSourceTextField dstfUsu = new DataSourceTextField("usuario_pede",
                "Usuário");
        DataSourceTextField dstfCarona = new DataSourceTextField("id_carona",
                "Identificador Carona");
        
        setFields(dstfId, dstfDono, dstfCarona, dstfUsu);

        setClientOnly(Boolean.TRUE);
    }
}
