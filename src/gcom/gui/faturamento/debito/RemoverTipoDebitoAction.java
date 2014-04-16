package gcom.gui.faturamento.debito;

import gcom.fachada.Fachada;
import gcom.faturamento.debito.DebitoTipo;
import gcom.gui.GcomAction;
import gcom.gui.ManutencaoRegistroActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author R�mulo Aur�lio
 * @Date 26/06/2007
 */
public class RemoverTipoDebitoAction extends GcomAction {
	
	
	
	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Obt�m a inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		ManutencaoRegistroActionForm manutencaoRegistroActionForm = (ManutencaoRegistroActionForm) actionForm;

		String[] idsRegistrosRemocao = manutencaoRegistroActionForm
				.getIdRegistrosRemocao();

		fachada.remover(idsRegistrosRemocao, DebitoTipo.class.getName(),
				null, null);

		montarPaginaSucesso(httpServletRequest, idsRegistrosRemocao.length
				+ " Tipo(s) de Debito(s) exclu�da(s) com sucesso.",
				"Manter outra Tipo de D�bito",
				"exibirFiltrarTipoDebitoAction.do?menu=sim");

		return retorno;

	}
}
