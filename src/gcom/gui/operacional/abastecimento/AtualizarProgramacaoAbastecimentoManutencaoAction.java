package gcom.gui.operacional.abastecimento;

import gcom.gui.GcomAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC0414] - Informar Programa��o de Abastecimento e Manuten��o
 * 
 * [SB0006] - Atualizar Programa��o de Abastecimento na Base de Dados
 * [SB0006] - Atualizar Programa��o de Manuten��o na Base de Dados
 *
 * @author Rafael Pinto
 * 
 * @date 15/11/2006
 */

public class AtualizarProgramacaoAbastecimentoManutencaoAction extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		InformarProgramacaoAbastecimentoManutencaoActionForm form = 
			(InformarProgramacaoAbastecimentoManutencaoActionForm) actionForm;

		this.getFachada().atualizarProgramacaoAbastecimentoManutencao(
				form.getAbastecimentoProgramacao(),
				form.getAbastecimentoProgramacaoRemovidas(),
				form.getManutencaoProgramacao(),
				form.getManutencaoProgramacaoRemovidas(),
				this.getUsuarioLogado(httpServletRequest));

		montarPaginaSucesso(httpServletRequest, "Programa��o de Abastecimento e Manuten��o para "
				+ form.getNomeAreaBairro() + " em "+form.getMesAnoReferencia()+" informada com sucesso",
				"Informar Nova Programa��o de Abastecimento e Manuten��o",
				"exibirFiltrarProgramacaoAbastecimentoManutencaoAction.do?menu=sim");

		return retorno;
	}

}
