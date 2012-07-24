package br.edu.client.grids;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.DSDataFormat;

/**
 * Classe que carrega os dados no grid de Caronas
 * @author 
 */
public class CarregaDadosGridCaronas extends DataSource {

    private static CarregaDadosGridCaronas instance = null;

    public static CarregaDadosGridCaronas getInstance() {
        if (instance == null) {
            instance = new CarregaDadosGridCaronas("carregaCaronas");
        }
        return instance;
    }

    private CarregaDadosGridCaronas(String id) {
        setID(id);
        setDataFormat(DSDataFormat.JSON);

        setDataURL("arquivoJSON/caronas.json");

        DataSourceTextField dstfOrigem = new DataSourceTextField("origem",
                "Origem");
        DataSourceTextField dstfDestino = new DataSourceTextField("destino",
                "Destino");
        DataSourceTextField dstfData = new DataSourceTextField("data",
                "Data de Saída");
        DataSourceTextField dstfHora = new DataSourceTextField("hora",
                "Horário de saída");
        DataSourceTextField dstfIdCarona = new DataSourceTextField("id",
                "Identificador");
        DataSourceTextField dstfIdVagas = new DataSourceTextField("vagas",
                "Vagas");
        DataSourceTextField dstfIdMunicipal = new DataSourceTextField("municipal",
                "Carona Municipal");
        DataSourceTextField dstfIdDono = new DataSourceTextField("dono_carona",
                "Dono");
        setFields(dstfIdCarona, dstfIdDono, dstfOrigem, dstfDestino, dstfData,
                dstfHora, dstfIdVagas, dstfIdMunicipal);

        setClientOnly(Boolean.TRUE);
    }
}
