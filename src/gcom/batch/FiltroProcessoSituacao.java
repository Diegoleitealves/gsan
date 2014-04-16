package gcom.batch;

import gcom.util.filtro.Filtro;

import java.io.Serializable;

/**
 * O filtro serve para armazenar os crit�rios de busca de um processo situa��o
 * 
 * @author Rodrigo Silveira
 * @created 22/07/2006
 */

public class FiltroProcessoSituacao extends Filtro implements Serializable {
	
	private static final long serialVersionUID = 1L;
	public FiltroProcessoSituacao() {
		

	}

	public FiltroProcessoSituacao(String campoOrderBy) {
		this.campoOrderBy = campoOrderBy;
	}
	
	public final static String ID = "id";
	

}
