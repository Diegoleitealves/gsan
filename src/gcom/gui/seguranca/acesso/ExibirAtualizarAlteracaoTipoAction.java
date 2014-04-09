package gcom.gui.seguranca.acesso;


import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.seguranca.transacao.AlteracaoTipo;
import gcom.seguranca.transacao.FiltroAlteracaoTipo;
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
 * @date 14/05/2008
 */
public class ExibirAtualizarAlteracaoTipoAction extends GcomAction {

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
				.findForward("AlteracaoTipoAtualizar");

		AtualizarAlteracaoTipoActionForm atualizarAlteracaoTipoActionForm = (AtualizarAlteracaoTipoActionForm) actionForm;

		// Obt�m a inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		// Mudar isso quando estiver implementado o esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		String id = httpServletRequest.getParameter("idRegistroAtualizacao");

		// Verifica se a solicita��o est� vindo do Filtrar ou do Manter
		if (httpServletRequest.getParameter("manter") != null) {
			
			sessao.setAttribute("manter", true);
		
		} else if (httpServletRequest.getParameter("filtrar") != null) {
		
			sessao.removeAttribute("manter");
		
		}

		if (id == null) {
		
			if (httpServletRequest.getAttribute("idRegistroAtualizacao") == null) {
			
				id = (String) sessao.getAttribute("idRegistroAtualizacao");
			
			} else {
			
				id = (String) httpServletRequest.getAttribute("idRegistroAtualizacao").toString();
			
			}
		
		} else {
		
			sessao.setAttribute("i", true);
		
		}

		AlteracaoTipo alteracaoTipo = new AlteracaoTipo();

		if (id != null && !id.trim().equals("") && Integer.parseInt(id) > 0) {

			FiltroAlteracaoTipo filtroAlteracaoTipo = new FiltroAlteracaoTipo();
			filtroAlteracaoTipo.adicionarParametro(new ParametroSimples(FiltroAlteracaoTipo.ID, id));
			Collection colecaoAlteracaoTipo = fachada.pesquisar(filtroAlteracaoTipo, AlteracaoTipo.class.getName());
			
			if (colecaoAlteracaoTipo != null && !colecaoAlteracaoTipo.isEmpty()) {
				
				alteracaoTipo = (AlteracaoTipo) Util.retonarObjetoDeColecao(colecaoAlteracaoTipo);
			
			}

			if (id == null || id.trim().equals("")) {

				atualizarAlteracaoTipoActionForm.setId(alteracaoTipo.getId().toString());
				atualizarAlteracaoTipoActionForm.setDescricao(alteracaoTipo.getDescricao());
				atualizarAlteracaoTipoActionForm.setDescricaoAbreviada(alteracaoTipo.getDescricaoAbreviada());

			}

			atualizarAlteracaoTipoActionForm.setId(id);
			atualizarAlteracaoTipoActionForm.setDescricao(alteracaoTipo.getDescricao());
			atualizarAlteracaoTipoActionForm.setDescricaoAbreviada(alteracaoTipo.getDescricaoAbreviada());

			sessao.setAttribute("atualizarAlteracaoTipo", alteracaoTipo);

			if (sessao.getAttribute("colecaoAlteracaoTipo") != null) {

				sessao.setAttribute("caminhoRetornoVoltar","/gsan/filtrarAlteracaoTipoAction.do");
			
			} else {
			
				sessao.setAttribute("caminhoRetornoVoltar","/gsan/exibirFiltrarAlteracaoTipoAction.do");
			
			}

		}

		return retorno;
	}
}
