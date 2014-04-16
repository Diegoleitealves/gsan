package gcom.seguranca.acesso.usuario;

import gcom.util.filtro.Filtro;

import java.io.Serializable;

/**
 * 
 * @author S�vio Luiz
 */
public class FiltroPemissaoEspecial extends Filtro implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * Constructor
	 */
	public FiltroPemissaoEspecial() {
	}

	/**
	 * Constructor
	 * 
	 * @param campoOrderBy
	 *            Description of the Parameter
	 */
	public FiltroPemissaoEspecial(String campoOrderBy) {
		this.campoOrderBy = campoOrderBy;
	}

	/**
	 * C�digo
	 */
	public final static String ID = "id";
	
	/**
	 * C�digo
	 */
	public final static String INDICADOR_USO = "indicadorUso";
	
	public final static String DESCRICAO = "descricao";


}
