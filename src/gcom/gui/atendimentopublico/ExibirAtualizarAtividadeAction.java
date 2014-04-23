package gcom.gui.atendimentopublico;

import gcom.atendimentopublico.ordemservico.Atividade;
import gcom.atendimentopublico.ordemservico.FiltroAtividade;
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
 * @author Vinicius Medeiros
 * @date 29/04/2008
 */
public class ExibirAtualizarAtividadeAction extends GcomAction {

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
				.findForward("atividadeAtualizar");

		AtualizarAtividadeActionForm atualizarAtividadeActionForm = (AtualizarAtividadeActionForm) actionForm;

		// Cria uma inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		// Mudar quando houver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		String id = httpServletRequest.getParameter("idRegistroAtualizacao");

		// Verifica se veio do Manter ou do Filtrar
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

		Atividade atividade = new Atividade();

		if (id != null && !id.trim().equals("") && Integer.parseInt(id) > 0) {

			FiltroAtividade filtroAtividade = new FiltroAtividade();
			filtroAtividade.adicionarParametro(new ParametroSimples(
					FiltroAtividade.ID, id));
			Collection colecaoAtividade = fachada.pesquisar(
					filtroAtividade, Atividade.class.getName());
			if (colecaoAtividade != null
					&& !colecaoAtividade.isEmpty()) {
				atividade = (Atividade) Util
						.retonarObjetoDeColecao(colecaoAtividade);
			}

			if (id == null || id.trim().equals("")) {

				atualizarAtividadeActionForm.setId(atividade
						.getId().toString());

				atualizarAtividadeActionForm
						.setDescricao(atividade.getDescricao());

				atualizarAtividadeActionForm
						.setDescricaoAbreviada(atividade
								.getDescricaoAbreviada());

			}

			atualizarAtividadeActionForm.setId(id);

			atualizarAtividadeActionForm.setDescricao(atividade
					.getDescricao());

			atualizarAtividadeActionForm
					.setDescricaoAbreviada(atividade
							.getDescricaoAbreviada());

			atualizarAtividadeActionForm.setIndicadorUso(""
					+ atividade.getIndicadorUso());

			atualizarAtividadeActionForm
					.setIndicadorAtividadeUnica(""
							+ atividade
									.getIndicadorAtividadeUnica());

			sessao.setAttribute("atualizarAtividade", atividade);

			if (sessao.getAttribute("colecaoAtividade") != null) {
				sessao.setAttribute("caminhoRetornoVoltar",
						"/gsan/filtrarAtividadeAction.do");
			} else {
				sessao.setAttribute("caminhoRetornoVoltar",
						"/gsan/exibirFiltrarAtividadeAction.do");
			}

		}

		return retorno;
	}
}
