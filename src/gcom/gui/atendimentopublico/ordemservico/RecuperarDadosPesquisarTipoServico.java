package gcom.gui.atendimentopublico.ordemservico;

import gcom.gui.GcomAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action auxiliar respons�vel por n�o perder as informa��es preenchidas no
 * *.jsp na hora de adicionar solicita��es especifica��es
 * 
 * @author S�vio Luiz
 * @created 28 de Julho de 2006
 */
public class RecuperarDadosPesquisarTipoServico extends
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
		PesquisarTipoServicoActionForm pesquisarTipoServicoActionForm = (PesquisarTipoServicoActionForm) actionForm;
		
		sessao.setAttribute("PesquisarTipoServicoActionForm", pesquisarTipoServicoActionForm);

		
		// Tipo de D�bito
		if (httpServletRequest.getParameter("caminhoRetornoTelaPesquisaTipoDebito") != null) {
			retorno = actionMapping
			.findForward("exibirPesquisarTipoDebito");
			sessao.setAttribute("caminhoRetornoTelaPesquisaTipoDebito", httpServletRequest
					.getParameter("caminhoRetornoTelaPesquisaTipoDebito"));
		}
		// Tipo Perfil Servi�o
		else if (httpServletRequest.getParameter("caminhoRetornoTelaPesquisaTipoPerfil") != null) {
			retorno = actionMapping
			.findForward("exibirPesquisarPerfilServico");
			sessao.setAttribute("caminhoRetornoTelaPesquisaTipoPerfil", httpServletRequest
					.getParameter("caminhoRetornoTelaPesquisaTipoPerfil"));
		}
		// Tipo Servi�o Refer�ncia 
		else if (httpServletRequest.getParameter("caminhoRetornoTelaPesquisaTipoReferencia") != null) {
			retorno = actionMapping
			.findForward("exibirPesquisarServicoReferencia");
			sessao.setAttribute("caminhoRetornoTelaPesquisaTipoReferencia", httpServletRequest
					.getParameter("caminhoRetornoTelaPesquisaTipoReferencia"));
		}
		// Tipo Atividades Servi�o
		else if (httpServletRequest.getParameter("caminhoRetornoTelaPesquisaAtividade") != null) {
			retorno = actionMapping
			.findForward("exibirPesquisarServicoAtividade");
			sessao.setAttribute("caminhoRetornoTelaPesquisaAtividade", httpServletRequest
					.getParameter("caminhoRetornoTelaPesquisaAtividade"));
		}
		// Tipo Mateial Servi�o
		else if (httpServletRequest.getParameter("caminhoRetornoTelaPesquisaMaterial") != null) {
			retorno = actionMapping
			.findForward("exibirPesquisarServicoMaterial");
			sessao.setAttribute("caminhoRetornoTelaPesquisaMaterial", httpServletRequest
					.getParameter("caminhoRetornoTelaPesquisaMaterial"));
		}
		return retorno;
	}
}
