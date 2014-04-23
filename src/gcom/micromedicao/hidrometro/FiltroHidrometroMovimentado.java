package gcom.micromedicao.hidrometro;

import gcom.util.filtro.Filtro;

import java.io.Serializable;

/**
 * < <Descri��o da Classe>>
 * 
 * @author Fernanda Paiva
 */
public class FiltroHidrometroMovimentado extends Filtro implements
        Serializable {
	private static final long serialVersionUID = 1L;
    /**
     * Construtor da classe FiltroHidrometroMovimentado
     */
    public FiltroHidrometroMovimentado() {
    }

    /**
     * Construtor da classe FiltroHidrometroMovimentado
     * 
     * @param campoOrderBy
     *            Descri��o do par�metro
     */
    public FiltroHidrometroMovimentado(String campoOrderBy) {
        this.campoOrderBy = campoOrderBy;
    }

	/**
	 * Description of the Field
	 */
	public final static String HIDROMETRO_ID = "hidrometro.id";

	/**
	 * Description of the Field
	 */
	public final static String HIDROMETRO_MOVIMENTACAO_ID = "hidrometroMovimentacao.id";

   
}
