package gcom.gui.atendimentopublico.ordemservico;

import gcom.gui.GcomAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Descri��o da classe
 * 
 * @author R�mulo Aur�lio
 * @date 10/08/2006
 */
public class ExibirInserirPrioridadeTipoServicoAction extends GcomAction {
	/**
	 * Este caso de uso permite a inclus�o de um tipo de servi�o de refer�ncia.
	 * 
	 * [UC0436] Inserir Tipo de Servi�o de Refer�ncia
	 * 
	 * 
	 * @author R�mulo Aur�lio
	 * @date 10/08/2006
	 * 
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		ActionForward retorno = actionMapping
				.findForward("inserirPrioridadeTipoServico");

		// HttpSession sessao = httpServletRequest.getSession(false);

		// InserirPrioridadeTipoServicoActionForm
		// inserirPrioridadeTipoServicoActionForm =
		// (InserirPrioridadeTipoServicoActionForm) actionForm;

		return retorno;
	}

}
