package gcom.gui.seguranca.acesso.usuario;

import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.FiltroSolicitacaoAcesso;
import gcom.seguranca.acesso.usuario.SolicitacaoAcesso;
import java.util.Collection;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Descri��o da classe
 * 
 * @author Hugo Leonardo	
 * @date 16/11/2010
 */
public class ExibirManterSolicitacaoAcessoAction extends GcomAction {

	/**
	 * [UC1093] Manter Solicita��o Acesso.
	 * 
	 * Este caso de uso permite alterar uma Solicita��o de Acesso
	 * 
	 */
	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		ActionForward retorno = actionMapping
				.findForward("exibirManterSolicitacaoAcessoAction");
		
		HttpSession sessao = httpServletRequest.getSession(false);
		
		String objeto = (String) sessao.getAttribute("objeto");
		
		if(objeto != null && objeto.equals("autorizar")){
			retorno = actionMapping.findForward("exibirManterAutorizarSolicitacaoAcessoAction");
		}
		
		if(objeto != null && objeto.equals("cadastrar")){
			retorno = actionMapping.findForward("exibirManterCadastrarSolicitacaoAcessoAction");
		}
		
		if(objeto != null && objeto.equals("relatorio")){
			retorno = actionMapping.findForward("exibirGerarRelatorioSolicitacaoAcessoAction");
		}

		// Limpa o atributo se o usu�rio voltou para o manter
		if (sessao.getAttribute("colecaoSolicitacaoAcesso") != null) {
			sessao.removeAttribute("colecaoSolicitacaoAcesso");
		}

		FiltroSolicitacaoAcesso filtroSolicitacaoAcesso = 
			(FiltroSolicitacaoAcesso)sessao.getAttribute("filtroSolicitacaoAcesso");
		
		// Aciona o controle de pagina��o para que sejam pesquisados apenas
		// os registros que aparecem na p�gina
		Map resultado = controlarPaginacao(httpServletRequest, retorno,
				filtroSolicitacaoAcesso, SolicitacaoAcesso.class.getName());
		
		Collection colecaoSolicitacaoAcesso = (Collection) resultado
				.get("colecaoRetorno");
		
		retorno = (ActionForward) resultado.get("destinoActionForward");
		
		String situacao = (String) httpServletRequest.getAttribute("situacoes");

		// Verifica se a cole��o retornada pela pesquisa � nula, em caso
		// afirmativo comunica ao usu�rio que n�o existe nenhuma Solicita��o de Acesso
		// cadastrada para a pesquisa efetuada e em caso negativo e se
		// atender a algumas condi��es seta o retorno para o
		// ExibirAtualizarSolicitacaoAcessoAction, se n�o atender manda a
		// cole��o pelo request para ser recuperado e exibido pelo jsp.

		if (colecaoSolicitacaoAcesso != null
				&& !colecaoSolicitacaoAcesso.isEmpty()) {

			// Verifica se a cole��o cont�m apenas um objeto, se est� retornando
			// da pagina��o (devido ao esquema de pagina��o de 10 em 10 faz uma
			// nova busca), evitando, assim, que caso haja 11 elementos no
			// retorno da pesquisa e o usu�rio selecione o link para ir para a
			// segunda p�gina ele n�o v� para tela de atualizar.
			
			if (colecaoSolicitacaoAcesso.size() == 1
					&& (httpServletRequest.getParameter("page.offset") == null || httpServletRequest
							.getParameter("page.offset").equals("1"))) {

				// Verifica se o usu�rio marcou o checkbox de atualizar no jsp
				// solicitacao_acesso_filtrar. Caso todas as condi��es sejam
				// verdadeiras seta o retorno para o
				// ExibirAtualizarSolicitacaoAcessoAction e em caso negativo
				// manda a cole��o pelo request.

				if (httpServletRequest.getParameter("atualizar") != null && situacao.equals("1")) {
					
					retorno = actionMapping.findForward("atualizarSolicitacaoAcesso");
					
					SolicitacaoAcesso solicitacaoAcesso = 
						(SolicitacaoAcesso) colecaoSolicitacaoAcesso.iterator().next();
					
					sessao.setAttribute("objetoSolicitacaoAcesso", solicitacaoAcesso);

				} else {
					
					sessao.removeAttribute("objetoSolicitacaoAcesso");
					
					httpServletRequest.setAttribute(
							"colecaoSolicitacaoAcesso",	colecaoSolicitacaoAcesso);
				}
			} else {
				httpServletRequest.setAttribute("colecaoSolicitacaoAcesso", colecaoSolicitacaoAcesso);
			}
		} else {
			// Nenhuma funcionalidade cadastrada
			throw new ActionServletException("atencao.pesquisa.nenhumresultado");
		}
		
		httpServletRequest.setAttribute("situacao", situacao);

		return retorno;
	}
}
