package gcom.gui;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Respons�vel por controlar o wizard
 * 
 * @author thiago toscano
 * @date 21/12/2005
 */
public abstract class ControladorWizardGcomAction extends WizardAction {

	public static final String ACAO = "acao";

	public static final String ACAO_EXIBIR = "exibir";

	public static final String ACAO_PROCESSAR = "processar";

	/**
	 * Reemplementa��o do M�todo para validar a passagem do parametro acao
	 * 
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, ServletRequest request, ServletResponse response) throws Exception {
		String acao = request.getParameter(ACAO);
		ActionForward retorno = null;
		if (acao == null || (!acao.equals(ACAO_EXIBIR) && !acao.equals(ACAO_PROCESSAR))) {
			retorno = super.execute(actionMapping, actionForm, request, response);
		} else {
			retorno = super.execute(actionMapping, actionForm, request, response);
		}
		return retorno;
	}

	/**
	 * M�todo que responde pela a��o de exibi��o
	 * 
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public abstract ActionForward exibir(ActionMapping actionMapping, ActionForm actionForm, ServletRequest request, ServletResponse response) throws Exception;
	
	/**
	 * M�todo que responde pela a��o de processamento 
	 * 
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public abstract ActionForward processar(ActionMapping actionMapping, ActionForm actionForm, ServletRequest request, ServletResponse response) throws Exception;

	
}
