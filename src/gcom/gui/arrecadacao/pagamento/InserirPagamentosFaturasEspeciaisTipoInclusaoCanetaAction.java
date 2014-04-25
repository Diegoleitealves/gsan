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
 * [UC0971] Inserir Pagamentos para Faturas Especiais
 * 
 * @author 	Vivianne Sousa
 * @created	21/12/2009
 */
public class InserirPagamentosFaturasEspeciaisTipoInclusaoCanetaAction extends GcomAction {

    public ActionForward execute(ActionMapping actionMapping,
            					ActionForm actionForm, 
            					HttpServletRequest httpServletRequest,
            					HttpServletResponse httpServletResponse) {

    	//Seta o retorno para nulo pois o retrono vai ser definido pelo wizard 
    	ActionForward retorno = null;
		
		return retorno;
	}
}
