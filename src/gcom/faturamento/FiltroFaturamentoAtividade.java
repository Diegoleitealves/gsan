package gcom.faturamento;

import gcom.util.filtro.Filtro;

/**
 * < <Descri��o da Classe>>
 * 
 * @author Administrador
 */
public class FiltroFaturamentoAtividade extends Filtro {
	private static final long serialVersionUID = 1L;
    /**
     * Construtor da classe FiltroFaturamentoAtividade
     */
    public FiltroFaturamentoAtividade() {
    }

    /**
     * Construtor da classe FiltroFaturamentoAtividade
     * 
     * @param campoOrderBy
     *            Descri��o do par�metro
     */
    public FiltroFaturamentoAtividade(String campoOrderBy) {
        this.campoOrderBy = campoOrderBy;
    }

    /**
     * Description of the Field
     */
    public final static String ID = "id";

    /**
     * Description of the Field
     */
    public final static String DESCRICAO = "descricao";

    /**
     * Description of the Field
     */
    public final static String INDICADOR_POSSIBILIDADE_COMANDO_ATIVIDADE = "indicadorPossibilidadeComandoAtividade";

    /**
     * Description of the Field
     */
    public final static String INDICADOR_USO = "indicadorUso";
    
    public final static String ORDEM_REALIZACAO = "ordemRealizacao";

}
