package br.edu.client.grids;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.DSDataFormat;
/**
 * Classe que carrega os dados no grid de Resposta de Sugestoes.
 *
 * @author
 */
public class CarregaDadosGridRespostaSugestoes extends DataSource {

    private static CarregaDadosGridRespostaSugestoes instance = null;

    public static CarregaDadosGridRespostaSugestoes getInstance() {
        if (instance == null) {
            instance = new CarregaDadosGridRespostaSugestoes("respostaSugestoes");
        }
        return instance;
    }

    private CarregaDadosGridRespostaSugestoes(String id) {
        setID(id);
        setDataFormat(DSDataFormat.JSON);

        setDataURL("arquivoJSON/respostaSugestoes.json");

        DataSourceTextField dstfId = new DataSourceTextField("id",
                "Negociação");
        DataSourceTextField dstfDono = new DataSourceTextField("dono_carona",
                "Dono da Carona");
        DataSourceTextField dstfUser = new DataSourceTextField("usuario_pede",
                "Usuário");
        DataSourceTextField dstfCarona = new DataSourceTextField("carona",
                "Carona");
        DataSourceTextField dstfResp = new DataSourceTextField("resposta",
                "Pontos");

        setFields(dstfId, dstfDono, dstfCarona,
                dstfUser, dstfResp);

        setClientOnly(Boolean.TRUE);
    }
}
