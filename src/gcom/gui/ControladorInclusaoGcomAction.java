package gcom.gui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Respons�vel por executar a a��o de altera�ao
 * 
 * @author thiago toscano
 * @date 21/12/2005
 */
public abstract class ControladorInclusaoGcomAction extends ControladorGcomAction {

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
	public final ActionForward exibir(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws Exception {

		ControladorInclusaoGcomActionForm form = (ControladorInclusaoGcomActionForm) actionForm; 

		this.carregarColecao(form);

		form.setAcao(ControladorGcomAction.PARAMETRO_ACAO_PROCESSAR);
		
		ActionForward forward = this.exibirAuxiliar(actionMapping, actionForm, request, response);
		if (forward != null) {
			return forward;
		} else {
			return actionMapping.findForward(ControladorGcomAction.FORWARD_EXIBIR);	
		}
	}

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
	public final ActionForward processar(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws Exception{

		ControladorInclusaoGcomActionForm form = (ControladorInclusaoGcomActionForm) actionForm; 

		Object obj = gerarObject(form, request);

		this.inserirObjeto(obj, request, form);
		
		ActionForward forward = this.processarAuxiliar(actionMapping, actionForm, request, response);
		
		if (forward != null) {
			return forward;
		} else {
			return actionMapping.findForward(ControladorGcomAction.FORWARD_PROCESSAR);
		}
	}

	/**
	 * M�todo que auxiliar ao m�todo exibir 
	 *
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward exibirAuxiliar(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws Exception {
		return null;
	}

	/**
	 * M�todo que auxiliar ao m�todo processar 
	 *
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward processarAuxiliar(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws Exception {
		return null;
	}

	/**
	 * M�todo que inseri o objeto
	 * 
	 * @param obj
	 * @throws Exception
	 */
	public abstract void inserirObjeto(Object obj, HttpServletRequest request, ControladorGcomActionForm actionForm) throws Exception;
	
	/**
	 * M�todo que gera o objeto para a manipulacao no sistema 
	 *  
	 * @param actionForm
	 * @return
	 */
	public abstract Object gerarObject(ControladorGcomActionForm actionForm, HttpServletRequest request);
	
	/**
	 * M�todo que carrega a colecao para a apresenta��o dos dados.
	 * 
	 * @param actionForm
	 */
	public abstract void carregarColecao(ControladorGcomActionForm actionForm);
}
