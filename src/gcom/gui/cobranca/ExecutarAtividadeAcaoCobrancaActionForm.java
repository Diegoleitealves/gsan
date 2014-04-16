package gcom.gui.cobranca;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ExecutarAtividadeAcaoCobrancaActionForm extends ActionForm {
	private static final long serialVersionUID = 1L;
	private String[] idsAtividadesCobrancaCronograma;

	private String[] idsAtividadesCobrancaEventuais;

	public String[] getIdsAtividadesCobrancaCronograma() {
		return idsAtividadesCobrancaCronograma;
	}

	public void setIdsAtividadesCobrancaCronograma(
			String[] idsAtividadesCobrancaCronograma) {
		this.idsAtividadesCobrancaCronograma = idsAtividadesCobrancaCronograma;
	}

	public String[] getIdsAtividadesCobrancaEventuais() {
		return idsAtividadesCobrancaEventuais;
	}

	public void setIdsAtividadesCobrancaEventuais(
			String[] idsAtividadesCobrancaEventuais) {
		this.idsAtividadesCobrancaEventuais = idsAtividadesCobrancaEventuais;
	}

	/**
     * < <Descri��o do m�todo>>
     * 
     * @param actionMapping
     *            Descri��o do par�metro
     * @param httpServletRequest
     *            Descri��o do par�metro
     */
    public void reset(ActionMapping actionMapping,
            HttpServletRequest httpServletRequest) {
    	idsAtividadesCobrancaCronograma = null;
    	idsAtividadesCobrancaEventuais = null;
    }
}
