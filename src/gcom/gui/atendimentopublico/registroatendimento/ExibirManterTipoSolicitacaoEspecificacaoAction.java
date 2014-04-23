package gcom.gui.atendimentopublico.registroatendimento;

import gcom.atendimentopublico.registroatendimento.FiltroSolicitacaoTipo;
import gcom.atendimentopublico.registroatendimento.SolicitacaoTipo;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;

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
 * @author R�mulo Aur�lio
 * @date 07/11/2006
 */
public class ExibirManterTipoSolicitacaoEspecificacaoAction extends GcomAction {

	/**
	 * < <Descri��o do m�todo>>
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

		ActionForward retorno = actionMapping
				.findForward("exibirManterTipoSolicitacaoEspecificacaoAction");

		// Obt�m a inst�ncia da fachada
		// Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		// Limpa o atributo se o usu�rio voltou para o manter
		if (sessao.getAttribute("colecaoTipoSolicitacaoTela") != null) {
			sessao.removeAttribute("colecaoTipoSolicitacaoTela");
		}

		// Recupera o filtro passado pelo
		// FiltrarTipoSolicitacaoEspecificacaoAction para
		// ser efetuada pesquisa
		FiltroSolicitacaoTipo filtroSolicitacaoTipo = (FiltroSolicitacaoTipo) sessao
				.getAttribute("filtroSolicitacaoTipo");

		// Collection colecaoSolicitacaoTipoEspecificacaoAdicionar =
		// (Collection) sessao
		// .getAttribute("colecaoSolicitacaoTipoEspecificacao");

		// Aciona o controle de pagina��o para que sejam pesquisados apenas
		// os registros que aparecem na p�gina
		Map resultado = controlarPaginacao(httpServletRequest, retorno,
				filtroSolicitacaoTipo, SolicitacaoTipo.class.getName());
		Collection colecaoTipoSolicitacao = (Collection) resultado
				.get("colecaoRetorno");
		retorno = (ActionForward) resultado.get("destinoActionForward");
		
		
		sessao.removeAttribute("colecaoSolicitacaoTipoEspecificacao");

		// Verifica se a cole��o retornada pela pesquisa � nula, em caso
		// afirmativo comunica ao usu�rio que n�o existe nenhuma fuuncionalidade
		// cadastrada
		// para a pesquisa efetuada e em caso negativo e se
		// atender a algumas condi��es seta o retorno para o
		// ExibirAtualizarTipoSolicitacaoEspecificacaoAction, se n�o atender
		// manda a
		// cole��o pelo request para ser recuperado e exibido pelo jsp.

		if (colecaoTipoSolicitacao != null && !colecaoTipoSolicitacao.isEmpty()) {

			// Verifica se a cole��o cont�m apenas um objeto, se est� retornando
			// da pagina��o (devido ao esquema de pagina��o de 10 em 10 faz uma
			// nova busca), evitando, assim, que caso haja 11 elementos no
			// retorno da pesquisa e o usu�rio selecione o link para ir para a
			// segunda p�gina ele n�o v� para tela de atualizar.

			if (colecaoTipoSolicitacao.size() == 1
					&& (httpServletRequest.getParameter("page.offset") == null || httpServletRequest
							.getParameter("page.offset").equals("1"))) {

				// Verifica se o usu�rio marcou o checkbox de atualizar no jsp
				// tipo_solicitacao_filtrar. Caso todas as condi��es sejam
				// verdadeiras seta o retorno para o
				// ExibirAtualizarTipoSolicitacaoEspecificacaoAction e em caso
				// negativo
				// manda a cole��o pelo request.

				if (httpServletRequest.getParameter("atualizar") != null) {
					retorno = actionMapping
							.findForward("atualizarTipoSolicitacaoEspecificacao");
					SolicitacaoTipo solicitacaoTipo = (SolicitacaoTipo) colecaoTipoSolicitacao
							.iterator().next();
					sessao.setAttribute("objetoSolicitacaoTipo",
							solicitacaoTipo);

				} else {
					httpServletRequest.setAttribute("colecaoTipoSolicitacao",
							colecaoTipoSolicitacao);
				}
			} else {
				httpServletRequest.setAttribute("colecaoTipoSolicitacao",
						colecaoTipoSolicitacao);
			}
		} else {
			// Nenhuma funcionalidade cadastrada
			throw new ActionServletException("atencao.pesquisa.nenhumresultado");
		}

		return retorno;
	}
}
