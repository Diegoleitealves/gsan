package gcom.faturamento.conta;

import gcom.util.filtro.Filtro;

/**
 * < <Descri��o da Classe>>
 * 
 * @author Administrador
 */
public class FiltroMotivoCancelamentoConta extends Filtro {
	private static final long serialVersionUID = 1L;
	/**
     * Description of the Field
     */
    public final static String ID = "id";

    /**
     * Description of the Field
     */
    public final static String DESCRICAO_MOTIVO_CANCELAMENTO_CONTA = "descricaoMotivoCancelamentoConta";
    
    /**
     * Description of the Field
     */
    public final static String INDICADOR_USO = "indicadorUso";

    /**
     * Construtor da classe FiltroOcupacaoTipo
     */
    public FiltroMotivoCancelamentoConta() {
    }

    /**
     * Construtor da classe FiltroCategoria
     * 
     * @param campoOrderBy
     *            Descri��o do par�metro
     */
    public FiltroMotivoCancelamentoConta(String campoOrderBy) {
        this.campoOrderBy = campoOrderBy;
    }

}
