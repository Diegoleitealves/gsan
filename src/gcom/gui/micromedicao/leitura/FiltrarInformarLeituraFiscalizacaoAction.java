package gcom.gui.micromedicao.leitura;

import gcom.gui.GcomAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author R�mulo Aur�lio
 *
 */
public class FiltrarInformarLeituraFiscalizacaoAction extends GcomAction {
	/**
	 * Este caso de uso permite informar ou corrigir leitura de fiscaliza��o
	 * 
	 * [UC0100] Informar Leitura de Fiscaliza��o
	 * 
	 * 
	 * @author R�mulo Aur�lio
	 * @date 16/05/2007
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
				.findForward("exibirInformarLeituraFiscalizacao");

		FiltrarInformarLeituraFiscalizacaoActionForm form = (FiltrarInformarLeituraFiscalizacaoActionForm) actionForm;

		httpServletRequest.setAttribute("matricula", form.getMatricula());

		httpServletRequest.setAttribute("idMedicaoTipo", form.getMedicaoTipo());
		httpServletRequest.setAttribute("mesAnoReferencia", form
				.getMesAnoReferencia());

		return retorno;

	}

}
