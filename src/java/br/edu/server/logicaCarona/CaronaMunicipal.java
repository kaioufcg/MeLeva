package br.edu.server.logicaCarona;

import br.edu.client.utilitarios.MeLevaException;
import java.io.Serializable;

/**
 * Classe para representação de uma carona municipal.
 * 
 * @author Grupo Me Leva - Projeto SI1 - UFCG 2012.1
 * 
 */
public class CaronaMunicipal extends AbstractCarona implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6534911836523351080L;
	private String cidade;

	/**
	 * Construtor da carona municipal.
	 * 
	 * @param origem
	 *            - String que representa a origem de onde parte a carona.
	 * @param destino
	 *            - String que representa o destino da carona.
	 * @param cidade
	 *            - Cidade.
	 * @param data
	 *            - Data que parte a carona.
	 * @param hora
	 *            - Horário que parte a carona.
	 * @param vagas
	 *            - Quantidade de vagas na carona.
	 * @throws MeLevaException
	 *             - Exceções para o caso dos dados inseridos sejam inválidos.
	 */
	public CaronaMunicipal(String origem, String destino, String cidade,
			String data, String hora, Integer vagas) throws MeLevaException {
		super(origem, destino, data, hora, vagas);
		this.cidade = cidade;

	}

	/**
	 * Método que acessa a cidade.
	 * 
	 * @return String da Cidade Municipal.
	 */
	public String getCidade() {
		return cidade;
	}

	/* (non-Javadoc)
	 * @see meLeva2.AbstractCarona#getMunicipal()
	 */
	@Override
	public boolean getMunicipal() {
		return true;
	}

}
