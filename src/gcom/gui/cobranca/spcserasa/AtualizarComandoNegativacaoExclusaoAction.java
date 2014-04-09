package gcom.gui.cobranca.spcserasa;

import gcom.gui.GcomAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Esta classe tem por finalidade validar as informa��es da 5� aba do processo de inser��o
 * de um Comando de Negativa��o
 *
 * @author Vivianne Sousa	
 * @date 07/07/2010
 */
public class AtualizarComandoNegativacaoExclusaoAction extends GcomAction {
	
	
	public ActionForward execute(ActionMapping actionMapping,
            ActionForm actionForm, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {

        ActionForward retorno = actionMapping.findForward("");
        
        return retorno;
	}

}
