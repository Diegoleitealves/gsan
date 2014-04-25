package gcom.gui.atendimentopublico.registroatendimento;

import gcom.atendimentopublico.registroatendimento.FiltroSolicitacaoTipoGrupo;
import gcom.atendimentopublico.registroatendimento.SolicitacaoTipoGrupo;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action respons�vel pela pre-exibi��o da pagina de inserir bairro
 * 
 * @author S�vio Luiz
 * @created 25 de Julho de 2006
 */
public class ExibirInserirTipoSolicitacaoEspecificacaoAction extends GcomAction {
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
				.findForward("inserirTipoSolicitacaoEspecificacao");
		InserirTipoSolicitacaoEspecificacaoActionForm inserirTipoSolicitacaoEspecificacaoActionForm = (InserirTipoSolicitacaoEspecificacaoActionForm) actionForm;
		
		if (httpServletRequest.getParameter("menu") != null && !httpServletRequest.getParameter("menu").trim().equals("")) {
			inserirTipoSolicitacaoEspecificacaoActionForm
					.setIndicadorUsoSistema("" + ConstantesSistema.NAO);
		}
		
		// limpa os campos da sess�o
		if (httpServletRequest.getParameter("limpaSessao") != null
				&& !httpServletRequest.getParameter("limpaSessao").equals("")) {
			inserirTipoSolicitacaoEspecificacaoActionForm.setDescricao("");
			inserirTipoSolicitacaoEspecificacaoActionForm
					.setIndicadorFaltaAgua("");
			inserirTipoSolicitacaoEspecificacaoActionForm
					.setIndicadorTarifaSocial("");
			inserirTipoSolicitacaoEspecificacaoActionForm
					.setIdgrupoTipoSolicitacao("");
			inserirTipoSolicitacaoEspecificacaoActionForm
					.setIndicadorUsoSistema("" + ConstantesSistema.NAO);
			sessao.removeAttribute("colecaoSolicitacaoTipoEspecificacao");
		}

		Fachada fachada = Fachada.getInstancia();
		
		
		// Verificar as permiss�o especial para alterar o indicador de uso do sistema 
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		
		boolean temPermissaoAlterarIndicadorUsoSistemaTipoSolicitacao = fachada
				.verificarPermissaoAlterarIndicadorUsoSistemaTipoSolicitacao(usuario);
		if (temPermissaoAlterarIndicadorUsoSistemaTipoSolicitacao) {
			httpServletRequest.setAttribute("temPermissaoAlterarIndicadorUsoSistemaTipoSolicitacao",
				temPermissaoAlterarIndicadorUsoSistemaTipoSolicitacao);
		}
		
		if (sessao.getAttribute("") == null
				|| sessao.getAttribute("").equals("")) {

			FiltroSolicitacaoTipoGrupo filtroSolicitacaoTipoGrupo = new FiltroSolicitacaoTipoGrupo();
			filtroSolicitacaoTipoGrupo.adicionarParametro(new ParametroSimples(
					FiltroSolicitacaoTipoGrupo.INDICADOR_USO,
					ConstantesSistema.INDICADOR_USO_ATIVO));
			Collection colecaoSolicitacaoTipoGrupo = fachada.pesquisar(
					filtroSolicitacaoTipoGrupo, SolicitacaoTipoGrupo.class
							.getName());
			sessao.setAttribute("colecaoSolicitacaoTipoGrupo",
					colecaoSolicitacaoTipoGrupo);

		}
		// remove o parametro de retorno
		sessao.removeAttribute("retornarTela");

		return retorno;
	}
}
