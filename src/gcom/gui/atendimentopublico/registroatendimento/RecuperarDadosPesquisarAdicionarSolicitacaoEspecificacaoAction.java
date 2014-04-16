package gcom.gui.atendimentopublico.registroatendimento;

import gcom.gui.GcomAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action auxiliar respons�vel por n�o perder as informa��es preenchidas no
 * solicitacao_especificacao_adicionar_popup.jsp na hora de adicionar
 * solicita��es especifica��es
 * 
 * @author S�vio Luiz
 * @created 28 de Julho de 2006
 */
public class RecuperarDadosPesquisarAdicionarSolicitacaoEspecificacaoAction extends
		GcomAction {
	/**
	 * Description of the Method
	 * 
	 * @param actionMapping
	 *            Description of the Parameter
	 * @param actionForm
	 *            Description of the Parameter
	 * @param httpServletRequest
	 *            Description of the Parameter
	 * @param httpServletResponse
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		// Set no mapeamento de retorno
		ActionForward retorno = null;
		//AdicionarSolicitacaoEspecificacaoActionForm adicionarSolicitacaoEspecificacaoActionForm = (AdicionarSolicitacaoEspecificacaoActionForm) actionForm;

		// recupera o caminho de retorno passado como parametro no jsp
		// que
		// chama
		// essa funcionalidade
		if (httpServletRequest
				.getParameter("caminhoRetornoTelaPesquisaUnidadeOrganizacional") != null) {
			retorno = actionMapping
					.findForward("exibirPesquisarUnidadeOrganizacional");
			sessao
					.setAttribute(
							"caminhoRetornoTelaPesquisaUnidadeOrganizacional",
							httpServletRequest
									.getParameter("caminhoRetornoTelaPesquisaUnidadeOrganizacional"));
		} else {
			sessao
					.removeAttribute("caminhoRetornoTelaPesquisaUnidadeOrganizacional");
		}
		// recupera o caminho de retorno passado como parametro no jsp
		// que
		// chama
		// essa funcionalidade
		if (httpServletRequest
				.getParameter("caminhoRetornoTelaPesquisaTipoServico") != null) {
			retorno = actionMapping.findForward("exibirPesquisarServicoTipo");
			sessao
					.setAttribute(
							"caminhoRetornoTelaPesquisaTipoServico",
							httpServletRequest
									.getParameter("caminhoRetornoTelaPesquisaTipoServico"));
		} else {
			sessao.removeAttribute("caminhoRetornoTelaPesquisaTipoServico");
		}
		
		/*
		 * Colocado por Raphael Rossiter em 25/02/2008
		 * Recupera o caminho de retorno passado como parametro no jsp que chama essa funcionalidade
		 */
		if (httpServletRequest.getParameter("caminhoRetornoTelaPesquisaTipoDebito") != null) {
			
			retorno = actionMapping.findForward("exibirPesquisarDebitoTipo");
			
			sessao.setAttribute("caminhoRetornoTelaPesquisaTipoDebito",
			httpServletRequest.getParameter("caminhoRetornoTelaPesquisaTipoDebito"));
		
		} else {
			sessao.removeAttribute("caminhoRetornoTelaPesquisaTipoDebito");
		}

		return retorno;
	}
}
