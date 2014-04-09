package gcom.gui.arrecadacao.pagamento;

import gcom.gui.GcomAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * Action respons�vel pela inser��o de pagamentos via leitura �ptica de c�digos de barra
 *
 * @author Pedro Alexandre
 * @date 16/02/2006
 */
public class InserirPagamentosTipoInclusaoCanetaAction extends GcomAction {

    /**
     * Inserir pagamentos no sistema
     *
     * [UC0265] Inserir Pagamentos
     *
     * @author Pedro Alexandre 
     * @date 16/02/2006
     *
     * @param actionMapping
     * @param actionForm
     * @param httpServletRequest
     * @param httpServletResponse
     * @return
     */
    public ActionForward execute(ActionMapping actionMapping,
            					ActionForm actionForm, 
            					HttpServletRequest httpServletRequest,
            					HttpServletResponse httpServletResponse) {

    	//Seta o retorno para nulo pois o retrono vai ser definido pelo wizard 
    	ActionForward retorno = null;
		
		return retorno;
	}
}
