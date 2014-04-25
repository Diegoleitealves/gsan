package gcom.gui.atendimentopublico.registroatendimento;

import gcom.gui.GcomAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Descri��o da classe Action auxiliar respons�vel por n�o perder as informa��es
 * preenchidas no solicitacao_tipo_filtrar.jsp na hora de adicionar solicita��es
 * especifica��es
 * 
 * @author R�mulo Aur�lio
 * @date 06/11/2006
 */
public class RecuperarDadosFiltrarTipoSolicitacaoEspecificacaoAction extends
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
		ActionForward retorno = actionMapping
				.findForward("exibirAdicionarSolicitacaoEspecificacao");
		//FiltrarTipoSolicitacaoEspecificacaoActionForm filtrarTipoSolicitacaoEspecificacaoActionForm = (FiltrarTipoSolicitacaoEspecificacaoActionForm) actionForm;

		// recupera o caminho de retorno passado como parametro no jsp
		// que
		// chama
		// essa funcionalidade
		if (httpServletRequest.getParameter("retornarTela") != null) {
			sessao.setAttribute("retornarTela", httpServletRequest
					.getParameter("retornarTela"));
		}

		sessao.setAttribute("filtrar", true);

		return retorno;
	}
}
