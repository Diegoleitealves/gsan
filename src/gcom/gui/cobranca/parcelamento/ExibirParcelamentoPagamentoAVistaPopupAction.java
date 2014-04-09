package gcom.gui.cobranca.parcelamento;

import gcom.gui.GcomAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * action respons�vel pela exibi��o da tela de consultar d�bito cobrado
 * 
 * @author Fernanda Paiva
 * @created 30 de Mar�o de 2006
 */
public class ExibirParcelamentoPagamentoAVistaPopupAction extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		// cria a vari�vel de retorno e seta o mapeamento para a tela de
		// consultar d�bito cobrado
		ActionForward retorno = actionMapping
				.findForward("exibirParcelamentoPagamentoAVistaPopupAction");

		// retorna o mapeamento contido na vari�vel retorno
		return retorno;
	}
}
