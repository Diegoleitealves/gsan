package gcom.faturamento.conta;

import gcom.util.filtro.Filtro;

/**
 * < <Descri��o da Classe>>
 * 
 * @author Administrador
 */
public class FiltroContaImpressao extends Filtro {

	private static final long serialVersionUID = 1L;
    public FiltroContaImpressao() {
    }

    public final static String ID = "id";
    
    public final static String CONTA_TIPO = "contaTipo";
    
    public final static String CONTA_GERAL = "contaGeral";
    
    /**
     * Construtor da classe FiltroCategoria
     * 
     * @param campoOrderBy
     *            Descri��o do par�metro
     */
    public FiltroContaImpressao(String campoOrderBy) {
        this.campoOrderBy = campoOrderBy;
    }

}
