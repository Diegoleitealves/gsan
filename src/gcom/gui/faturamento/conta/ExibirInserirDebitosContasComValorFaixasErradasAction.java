package gcom.gui.faturamento.conta;

import gcom.gui.GcomAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Inserir D�bitos para as contas impressas via Impress�o Simult�nea de Contas que sairam com o valor da conta errada (Alguns grupos com tarifa proporcional
 *  que n�o estava levando em considera��o a quantidade de economias)
 *
 * @author S�vio Luiz
 * @date 12/01/2011
 */
public class ExibirInserirDebitosContasComValorFaixasErradasAction extends GcomAction {

	 public ActionForward execute(ActionMapping actionMapping,
	            ActionForm actionForm, HttpServletRequest httpServletRequest,
	            HttpServletResponse httpServletResponse) {

	    	ActionForward retorno = actionMapping.findForward("exibirInserirDebitosContas");       


	       	        
	        
	        return retorno;
	 }
	 
}
