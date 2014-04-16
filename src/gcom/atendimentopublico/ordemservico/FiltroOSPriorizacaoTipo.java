package gcom.atendimentopublico.ordemservico;

import gcom.util.filtro.Filtro;

import java.io.Serializable;

/**
 * O filtro serve para armazenar os crit�rios de busca de um tipo de priorizacao
 * 
 * @author Th�lio Ara�jo
 */

public class FiltroOSPriorizacaoTipo extends Filtro implements Serializable {
	
	private static final long serialVersionUID = 1L;

    public static final String ID_PRIORIZACAOTIPO = "id";

    public static final String DESCRICAO_PRIORIZACAOTIPO = "descricaoPriorizacao";

    public FiltroOSPriorizacaoTipo() {
    }

    public FiltroOSPriorizacaoTipo(String campoOrderBy) {
        this.campoOrderBy = campoOrderBy;
    }
}
