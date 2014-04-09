package gcom.gui;

import gcom.util.filtro.Filtro;

import java.util.Collection;

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
public abstract class ControladorConsultaGcomAction extends ControladorGcomAction {

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

		ControladorConsultaGcomActionForm form = (ControladorConsultaGcomActionForm) actionForm; 

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

		ControladorConsultaGcomActionForm form = (ControladorConsultaGcomActionForm) actionForm; 

		Filtro filtro = (Filtro) this.gerarFiltro(form);

		Collection objects = this.pesquisarObjetoSistema(filtro);

		form.setCollObjeto(objects);
		
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
	 * M�todo que gera o filtro a partir do form
	 * 
	 * @param actionForm
	 * @return
	 */
	public abstract Filtro gerarFiltro(ControladorConsultaGcomActionForm actionForm) ;

	/**
	 * M�todo que consulta o Objeto no sistema com o array de chaves necessaria
	 * 
	 * @param chavesPrimarias
	 * @return
	 * @throws Exception
	 */	
	public abstract Collection pesquisarObjetoSistema(Filtro filtro ) throws Exception;

	/**
	 * M�todo que carrega a colecao para a apresenta��o dos dados.
	 * 
	 * @param actionForm
	 */
	public abstract void carregarColecao(ControladorConsultaGcomActionForm actionForm);
}
