package gcom.atendimentopublico.ordemservico;

import gcom.util.filtro.Filtro;

import java.io.Serializable;

/**
 * Classe que representa o filtro da Data de Fiscalizacao das OS Seletivas
 * 
 * @author Ivan S�rgio
 * @data 09/04/2008
 * 
 */
public class FiltroDataFiscalizacaoOSSeletiva extends Filtro implements Serializable {
	
	private static final long serialVersionUID = 1L;
    /**
     * Construtor da classe 
     */
    public FiltroDataFiscalizacaoOSSeletiva() {
    }

    /**
     * Construtor da classe 
     * 
     * @param campoOrderBy
     *            Descri��o do par�metro
     */
    public FiltroDataFiscalizacaoOSSeletiva(String campoOrderBy) {
        this.campoOrderBy = campoOrderBy;
    }

    /**
     * Description of the Field
     */
    public final static String ID = "id";

    /**
     * Description of the Field
     */
    public final static String DATA_FISCALIZACAO_OS_2 = "dataFiscalizacaoOrdemServico2";

    /**
     * Description of the Field
     */
    public final static String DATA_FISCALIZACAO_OS_3 = "dataFiscalizacaoOrdemServico3";
    
}
