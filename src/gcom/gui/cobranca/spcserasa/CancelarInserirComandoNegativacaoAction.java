package gcom.gui.cobranca.spcserasa;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import gcom.gui.GcomAction;

/**
 * Esta classe tem por finalidade cancelar todas as atividades referentes ao processo de inser��o de um 
 * Comando Negativa��o voltando para o menu principal do sistema
 *
 * @author Ana Maria
 * @date 17/12/2007
 */
public class CancelarInserirComandoNegativacaoAction extends GcomAction {
	
	
	public ActionForward execute(ActionMapping actionMapping,
            ActionForm actionForm, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {

        ActionForward retorno = actionMapping.findForward("telaPrincipal");

        HttpSession sessao = httpServletRequest.getSession(false);

        sessao.removeAttribute("statusWizard"); 

        return retorno;
    }

}
