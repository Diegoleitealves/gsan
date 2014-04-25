package gcom.gui.atendimentopublico.ligacaoagua;

import gcom.atendimentopublico.ligacaoagua.FiltroLigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 
 * @author Vinicius Medeiros
 * @date 16/05/2008
 */
public class ExibirAtualizarLigacaoAguaSituacaoAction extends GcomAction {

	/**
	 * M�todo responsavel por responder a requisicao
	 * 
	 * @param actionMapping
	 *            Descri��o do par�metro
	 * @param actionForm
	 *            Descri��o do par�metro
	 * @param httpServletRequest
	 *            Descri��o do par�metro
	 * @param httpServletResponse
	 *            Descri��o do par�metro
	 * @return Descri��o do retorno
	 */
	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		// Seta o caminho de retorno
		ActionForward retorno = actionMapping
				.findForward("ligacaoAguaSituacaoAtualizar");

		AtualizarLigacaoAguaSituacaoActionForm atualizarLigacaoAguaSituacaoActionForm = (AtualizarLigacaoAguaSituacaoActionForm) actionForm;

		// Obt�m a inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		// Mudar isso quando houver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		String id = httpServletRequest.getParameter("idRegistroAtualizacao");

		// Verifica se veio do Filtrar ou do Manter
		if (httpServletRequest.getParameter("manter") != null) {
			sessao.setAttribute("manter", true);
		} else if (httpServletRequest.getParameter("filtrar") != null) {
			sessao.removeAttribute("manter");
		}

		if (id == null) {
			if (httpServletRequest.getAttribute("idRegistroAtualizacao") == null) {
				id = (String) sessao.getAttribute("idRegistroAtualizacao");
			} else {
				id = (String) httpServletRequest.getAttribute(
						"idRegistroAtualizacao").toString();
			}
		} else {
			sessao.setAttribute("i", true);
		}

		LigacaoAguaSituacao ligacaoAguaSituacao = new LigacaoAguaSituacao();


		if (id != null && !id.trim().equals("") && Integer.parseInt(id) > 0) {

			FiltroLigacaoAguaSituacao filtroLigacaoAguaSituacao = new FiltroLigacaoAguaSituacao();
			filtroLigacaoAguaSituacao.adicionarParametro(new ParametroSimples(
					FiltroLigacaoAguaSituacao.ID, id));
			Collection colecaoLigacaoAguaSituacao = fachada.pesquisar(
					filtroLigacaoAguaSituacao, LigacaoAguaSituacao.class.getName());
			if (colecaoLigacaoAguaSituacao != null
					&& !colecaoLigacaoAguaSituacao.isEmpty()) {
				ligacaoAguaSituacao = (LigacaoAguaSituacao) Util
						.retonarObjetoDeColecao(colecaoLigacaoAguaSituacao);
			}

			if (id == null || id.trim().equals("")) {

				atualizarLigacaoAguaSituacaoActionForm.setId(
						ligacaoAguaSituacao.getId().toString());

				atualizarLigacaoAguaSituacaoActionForm.setDescricao(
						ligacaoAguaSituacao.getDescricao());

				atualizarLigacaoAguaSituacaoActionForm.setDescricaoAbreviada(
						ligacaoAguaSituacao.getDescricaoAbreviado());
			}

			atualizarLigacaoAguaSituacaoActionForm.setId(id);

			atualizarLigacaoAguaSituacaoActionForm.setDescricao(
					ligacaoAguaSituacao.getDescricao());

			atualizarLigacaoAguaSituacaoActionForm.setDescricaoAbreviada(
					ligacaoAguaSituacao.getDescricaoAbreviado());
			
			atualizarLigacaoAguaSituacaoActionForm.setConsumoMinimoFaturamento(
					ligacaoAguaSituacao.getConsumoMinimoFaturamento().toString());

			atualizarLigacaoAguaSituacaoActionForm.setIndicadorUso(
					""+ ligacaoAguaSituacao.getIndicadorUso());
			
			atualizarLigacaoAguaSituacaoActionForm.setIndicadorAbastecimento(
					""+ ligacaoAguaSituacao.getIndicadorAbastecimento());

			atualizarLigacaoAguaSituacaoActionForm.setIndicadorExistenciaRede(
					""+ ligacaoAguaSituacao.getIndicadorExistenciaRede());
			
			atualizarLigacaoAguaSituacaoActionForm.setIndicadorFaturamentoSituacao(
					""+ ligacaoAguaSituacao.getIndicadorFaturamentoSituacao());
			
			atualizarLigacaoAguaSituacaoActionForm.setIndicadorExistenciaLigacao(
					""+ ligacaoAguaSituacao.getIndicadorExistenciaLigacao());

			sessao.setAttribute("atualizarLigacaoAguaSituacao", ligacaoAguaSituacao);

			if (sessao.getAttribute("colecaoLigacaoAguaSituacao") != null) {
				sessao.setAttribute("caminhoRetornoVoltar",
						"/gsan/filtrarLigacaoAguaSituacaoAction.do");
			} else {
				sessao.setAttribute("caminhoRetornoVoltar",
						"/gsan/exibirFiltrarLigacaoAguaSituacaoAction.do");
			}

		}

		return retorno;
	}
}
