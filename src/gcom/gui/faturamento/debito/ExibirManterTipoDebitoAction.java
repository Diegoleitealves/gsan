package gcom.gui.faturamento.debito;

import gcom.faturamento.debito.DebitoTipo;
import gcom.faturamento.debito.FiltroDebitoTipo;
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
 * @date 13/03/2007
 */
public class ExibirManterTipoDebitoAction extends GcomAction {
	/**
	 * Este caso de uso permite alterar e remover um Tipo de D�bito
	 * 
	 * [UC0530] Manter Tipo de D�bito
	 * 
	 * 
	 * @author R�mulo Aur�lio
	 * @date 13/03/2007
	 * 
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */

	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		ActionForward retorno = actionMapping
				.findForward("exibirManterTipoDebitoAction");

		// Obt�m a inst�ncia da fachada
		// Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		// Limpa o atributo se o usu�rio voltou para o manter
		if (sessao.getAttribute("colecaoTipoDebitoTela") != null) {
			sessao.removeAttribute("colecaoTipoDebitoTela");
		}

		// Recupera o filtro passado pelo FiltrarFuncionalidadeAction para
		// ser efetuada pesquisa
		FiltroDebitoTipo filtroDebitoTipo = (FiltroDebitoTipo) sessao
				.getAttribute("filtroDebitoTipo");

		// Aciona o controle de pagina��o para que sejam pesquisados apenas
		// os registros que aparecem na p�gina
		Map resultado = controlarPaginacao(httpServletRequest, retorno,
				filtroDebitoTipo, DebitoTipo.class.getName());
		Collection colecaoDebitoTipo = (Collection) resultado
				.get("colecaoRetorno");
		retorno = (ActionForward) resultado.get("destinoActionForward");

		// Verifica se a cole��o retornada pela pesquisa � nula, em caso
		// afirmativo comunica ao usu�rio que n�o existe nenhuma fuuncionalidade
		// cadastrada
		// para a pesquisa efetuada e em caso negativo e se
		// atender a algumas condi��es seta o retorno para o
		// ExibirAtualizarFuncionalidadeAction, se n�o atender manda a
		// cole��o pelo request para ser recuperado e exibido pelo jsp.

		if (colecaoDebitoTipo != null && !colecaoDebitoTipo.isEmpty()) {

			// Verifica se a cole��o cont�m apenas um objeto, se est� retornando
			// da pagina��o (devido ao esquema de pagina��o de 10 em 10 faz uma
			// nova busca), evitando, assim, que caso haja 11 elementos no
			// retorno da pesquisa e o usu�rio selecione o link para ir para a
			// segunda p�gina ele n�o v� para tela de atualizar.

			if (colecaoDebitoTipo.size() == 1
					&& (httpServletRequest.getParameter("page.offset") == null || httpServletRequest
							.getParameter("page.offset").equals("1"))) {

				// Verifica se o usu�rio marcou o checkbox de atualizar no jsp
				// funcionalidade_filtrar. Caso todas as condi��es sejam
				// verdadeiras seta o retorno para o
				// ExibirAtualizarFuncionalidadeAction e em caso negativo
				// manda a cole��o pelo request.

				if (httpServletRequest.getParameter("atualizar") != null) {
					retorno = actionMapping
							.findForward("atualizarTipoDebitoAction");
					DebitoTipo debitoTipo = (DebitoTipo) colecaoDebitoTipo
							.iterator().next();
					sessao.setAttribute("objetoDebitoTipo", debitoTipo);

				} else {
					httpServletRequest.setAttribute("colecaoDebitoTipo",
							colecaoDebitoTipo);
				}
			} else {
				httpServletRequest.setAttribute("colecaoDebitoTipo",
						colecaoDebitoTipo);
			}
		} else {
			// Nenhuma funcionalidade cadastrada
			throw new ActionServletException("atencao.pesquisa.nenhumresultado");
		}

		return retorno;
	}

}
