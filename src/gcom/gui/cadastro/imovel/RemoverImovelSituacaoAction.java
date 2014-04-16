package gcom.gui.cadastro.imovel;

import gcom.cadastro.imovel.ImovelSituacao;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.gui.ManutencaoRegistroActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Descri��o da classe
 * 
 * @author R�mulo Aur�lio
 * @date 13/06/2006
 */
public class RemoverImovelSituacaoAction extends GcomAction {
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

		fachada.remover(idsRegistrosRemocao, ImovelSituacao.class.getName(),
				null, null);

		montarPaginaSucesso(httpServletRequest, idsRegistrosRemocao.length
				+ " Situa��o(�es) de Im�vel(is) exclu�da(s) com sucesso.",
				"Consultar outra Situa��o de Im�vel",
				"exibirFiltrarImovelSituacaoAction.do?menu=sim");

		return retorno;

	}
}
