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
public abstract class ControladorAlteracaoGcomAction extends ControladorGcomAction {

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

		ControladorAlteracaoGcomActionForm form = (ControladorAlteracaoGcomActionForm) actionForm; 

		String[] chavesPrimarias = form.getChavePrimaria().split(ControladorGcomAction.PARAMETRO_SEPARADO_CHAVE_PRIMARIA);
		
		Object obj = this.consultarObjetoSistema(chavesPrimarias, request, form);
		
		this.montarFormulario(obj, form);
		
		ActionForward forward = this.exibirAuxiliar(actionMapping, actionForm, request, response);

		this.carregarColecao(form);

		form.setAcao(ControladorGcomAction.PARAMETRO_ACAO_PROCESSAR);

		if (forward != null) {
			return forward;
		}
		return actionMapping.findForward(ControladorGcomAction.FORWARD_EXIBIR);
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

		ControladorAlteracaoGcomActionForm form = (ControladorAlteracaoGcomActionForm) actionForm; 

		Object obj = gerarObject(form,request);

		this.atualizarObjeto(obj, request, form);
		
		ActionForward forward = this.processarAuxiliar(actionMapping, actionForm, request, response);
		
		if (forward != null) {
			return forward;
		}
		return actionMapping.findForward(ControladorGcomAction.FORWARD_PROCESSAR);
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
	 * M�todo que consulta o Objeto no sistema com o array de chaves necessaria
	 * 
	 * @param chavesPrimarias
	 * @return
	 * @throws Exception
	 */	
	public abstract Object consultarObjetoSistema(String[] chavesPrimarias, HttpServletRequest request, ControladorAlteracaoGcomActionForm actionForm) throws Exception;

	/**
	 * M�todo que atualiza o objeto no sistema
	 * 
	 * @param obj
	 * @throws Exception
	 */
	public abstract void atualizarObjeto(Object obj, HttpServletRequest request, ControladorAlteracaoGcomActionForm actionForm) throws Exception;

	/**
	 * M�todo respons�vel por preencher o formulario de apresenta��o a partir do objeto selecionado  
	 * 
	 * @param obj
	 * @param actionForm
	 */
	public abstract void montarFormulario(Object obj, ControladorAlteracaoGcomActionForm actionForm) ;
	
	/**
	 * M�todo que gera o objeto para a manipulacao no sistema 
	 *  
	 * @param actionForm
	 * @return
	 */
	public abstract Object gerarObject(ControladorAlteracaoGcomActionForm actionForm, HttpServletRequest request);
	
	/**
	 * M�todo que carrega a colecao para a apresenta��o dos dados.
	 * 
	 * @param actionForm
	 */
	public abstract void carregarColecao(ControladorAlteracaoGcomActionForm actionForm);
}
