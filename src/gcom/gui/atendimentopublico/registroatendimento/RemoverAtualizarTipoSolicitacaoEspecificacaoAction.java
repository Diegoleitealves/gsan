package gcom.gui.atendimentopublico.registroatendimento;

import gcom.atendimentopublico.registroatendimento.SolicitacaoTipo;
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
 * @date 07/12/2006
 */
public class RemoverAtualizarTipoSolicitacaoEspecificacaoAction extends GcomAction {
	
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

		fachada.remover(idsRegistrosRemocao, SolicitacaoTipo.class.getName(),
				null, null);

		montarPaginaSucesso(httpServletRequest, idsRegistrosRemocao.length
				+ "Tipo(s) de Solicita��o com Especifica��es exclu�da(s) com sucesso.",
				"Manter outro Tipo de Solicita��o com Especifica��es",
				"exibirFiltrarTipoSolicitacaoEspecificacaoAction.do?menu=sim");

		return retorno;

	}
}
