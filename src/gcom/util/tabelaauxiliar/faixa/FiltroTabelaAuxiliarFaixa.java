package gcom.util.tabelaauxiliar.faixa;

import gcom.util.filtro.Filtro;

import java.io.Serializable;

/**
 * < <Descri��o da Classe>>
 * 
 * @author Administrador
 */
public class FiltroTabelaAuxiliarFaixa extends Filtro implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
     * Construtor da classe FiltroTabelaAuxiliar
     */
    public FiltroTabelaAuxiliarFaixa() {
    }

    /**
     * Construtor da classe FiltroTabelaAuxiliar
     * 
     * @param campoOrderBy
     *            Descri��o do par�metro
     */
    public FiltroTabelaAuxiliarFaixa(String campoOrderBy) {
        this.campoOrderBy = campoOrderBy;
    }

    /**
     * Description of the Field
     */
    public final static String ID = "id";

    /**
     * Description of the Field
     */
    public final static String FAIXAINICIAL = "faixaInicial";

    /**
     * Description of the Field
     */
    public final static String FAIXAFINAL = "faixaFinal";

}
