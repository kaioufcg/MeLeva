package br.edu.ufcg.projetomelevamavem.logicaCarona;

import br.edu.ufcg.projetomelevamavem.logicaSistema.MeLevaException;
import java.io.Serializable;

/**
 * Classe para representação de uma carona Normal.
 *
 * @author Grupo Me Leva - Projeto SI1 - UFCG 2012.1
 *
 */
public class Carona extends AbstractCarona implements Serializable {

    private static final long serialVersionUID = 00000000012L;

    /**
     * Construtor da carona.
     *
     * @param origem - String que representa a origem de onde parte a carona.
     * @param destino - String que representa o destino da carona.
     * @param data - Data que parte a carona.
     * @param hora - Horário que parte a carona.
     * @param vagas - Quantidade de vagas na carona.
     * @throws MeLevaException - Exceções para o caso dos dados inseridos sejam
     * inválidos.
     */
    public Carona(String origem, String destino, String data, String hora,
            Integer vagas) throws MeLevaException {
        super(origem, destino, data, hora, vagas);
    }

    /*
     * (non-Javadoc) @see meLeva2.AbstractCarona#getMunicipal()
     */
    @Override
    public boolean getMunicipal() {
        return false;
    }
}
