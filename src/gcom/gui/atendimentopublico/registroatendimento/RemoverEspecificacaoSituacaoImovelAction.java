package gcom.gui.atendimentopublico.registroatendimento;

import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.gui.ManutencaoRegistroActionForm;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Descri��o da classe
 * 
 * @author Rafael Francisco Pinto
 * @date 08/11/2006
 */
public class RemoverEspecificacaoSituacaoImovelAction extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping,ActionForm actionForm, 
		HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		ManutencaoRegistroActionForm manutencaoRegistroActionForm = (ManutencaoRegistroActionForm) actionForm;

		String[] idsRegistrosRemocao = manutencaoRegistroActionForm.getIdRegistrosRemocao();

		// mensagem de erro quando o usu�rio tenta excluir sem ter selecionado
		// nenhum registro
		if (idsRegistrosRemocao == null || idsRegistrosRemocao.length == 0) {
			throw new ActionServletException("atencao.registros.nao_selecionados");
		}
		
		Date ultimaAlteracao = (Date) this.getSessao(httpServletRequest).getAttribute("ultimaAlteracao");
		
		this.getFachada().removerEspecificacaoSituacaoImovel(idsRegistrosRemocao,
			this.getUsuarioLogado(httpServletRequest),ultimaAlteracao);

		this.getSessao(httpServletRequest).removeAttribute("indicadorAtualizar");

		montarPaginaSucesso(httpServletRequest, idsRegistrosRemocao.length
				+ " Especifica��o(�es) removida(s) com sucesso.",
				"Realizar outra Manuten��o da Especifica��o da Situa��o do Im�vel",
				"exibirFiltrarEspecificacaoSituacaoImovelAction.do?menu=sim");

		return retorno;

	}

}
