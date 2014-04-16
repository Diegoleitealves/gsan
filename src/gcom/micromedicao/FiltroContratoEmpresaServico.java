package gcom.micromedicao;

import gcom.util.filtro.Filtro;
import java.io.Serializable;

/**
 * FiltroContratoEmpresaServico 
 *
 * @author Hugo Leonardo
 * @date 23/07/2010
 */
public class FiltroContratoEmpresaServico extends Filtro implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
     * Constructor for the FiltroUnidadeOrganizacional object
     */
    public FiltroContratoEmpresaServico() {
    }

    /**
     * Constructor for the FiltroCliente object
     * 
     * @param campoOrderBy
     *            Description of the Parameter
     */
    public FiltroContratoEmpresaServico(String campoOrderBy) {
        this.campoOrderBy = campoOrderBy;
    }

    /**
     * Id do Contrato Empresa Servico
     */
    public final static String ID = "id";
    
    /**
     * Id da Empresa
     */
    public final static String EMPRESA = "empresa";

    /**
     * Descri��o do numero do Contrato
     */
    public final static String DESCRICAO = "descricaoContrato";
    
    /**
     * Descri��o do numero do Contrato
     */
    public final static String DATA_FIM_CONTRATO = "dataFimContrato";
    
    /**
     * Descri��o do numero do Contrato
     */
    public final static String ITEM_SERVICO_CONTRATOS = "itemServicoContratos";

}
