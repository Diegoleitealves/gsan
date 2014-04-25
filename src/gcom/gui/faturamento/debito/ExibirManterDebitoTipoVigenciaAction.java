package gcom.gui.faturamento.debito;

import gcom.faturamento.debito.DebitoTipoVigencia;
import gcom.faturamento.debito.FiltroDebitoTipoVigencia;
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
 * @author Josenildo Neves - Hugo Leonardo	
 * @date 18/02/2010 - 16/04/2010
 */
public class ExibirManterDebitoTipoVigenciaAction extends GcomAction {

	/**
	 * [UC0986] Manter tipo de d�bito com vig�ncia
	 * 
	 * Este caso de uso permite alterar ou excluir um debito tipo vigencia
	 * 
	 * @author Josenildo Neves
	 * @date 18/02/2010
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
				.findForward("exibirManterDebitoTipoVigenciaAction");

		// Obt�m a inst�ncia da fachada
		// Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		// Limpa o atributo se o usu�rio voltou para o manter
		if (sessao.getAttribute("colecaoDebitoTipoVigenciaTela") != null) {
			sessao.removeAttribute("colecaoDebitoTipoVigenciaTela");
		}

		// Recupera o filtro passado pelo FiltrarValorCobrancaServicoAction para
		// ser efetuada pesquisa

		FiltroDebitoTipoVigencia filtroDebitoTipoVigencia = (FiltroDebitoTipoVigencia)sessao.getAttribute("filtroDebitoTipoVigencia");
		
		// Aciona o controle de pagina��o para que sejam pesquisados apenas
		// os registros que aparecem na p�gina

		Map resultado = controlarPaginacao(httpServletRequest, retorno,
				filtroDebitoTipoVigencia, DebitoTipoVigencia.class.getName());
		
		Collection colecaoDebitoTipoVigencia = (Collection) resultado
				.get("colecaoRetorno");
		
		retorno = (ActionForward) resultado.get("destinoActionForward");

		// Verifica se a cole��o retornada pela pesquisa � nula, em caso
		// afirmativo comunica ao usu�rio que n�o existe nenhum Debito Tipo Vigencia
		// cadastrada
		// para a pesquisa efetuada e em caso negativo e se
		// atender a algumas condi��es seta o retorno para o
		// ExibirAtualizarDebitoTipoVigenciaAction, se n�o atender manda a
		// cole��o pelo request para ser recuperado e exibido pelo jsp.

		if (colecaoDebitoTipoVigencia != null
				&& !colecaoDebitoTipoVigencia.isEmpty()) {

			// Verifica se a cole��o cont�m apenas um objeto, se est� retornando
			// da pagina��o (devido ao esquema de pagina��o de 10 em 10 faz uma
			// nova busca), evitando, assim, que caso haja 11 elementos no
			// retorno da pesquisa e o usu�rio selecione o link para ir para a
			// segunda p�gina ele n�o v� para tela de atualizar.
			
			if (colecaoDebitoTipoVigencia.size() == 1
					&& (httpServletRequest.getParameter("page.offset") == null || httpServletRequest
							.getParameter("page.offset").equals("1"))) {

				// Verifica se o usu�rio marcou o checkbox de atualizar no jsp
				// valor_cobranca_servico_filtrar. Caso todas as condi��es sejam
				// verdadeiras seta o retorno para o
				// ExibirAtualizarDebitoTipoVigenciaAction e em caso negativo
				// manda a cole��o pelo request.

				if (httpServletRequest.getParameter("atualizar") != null) {
					retorno = actionMapping
							.findForward("atualizarDebitoTipoVigencia");
					DebitoTipoVigencia debitoTipoVigencia = (DebitoTipoVigencia) colecaoDebitoTipoVigencia
							.iterator().next();
					sessao.setAttribute("objetoDebitoTipoVigencia",
							debitoTipoVigencia);

				} else {
					
					sessao.removeAttribute("objetoDebitoTipoVigencia");
					
					httpServletRequest.setAttribute(
							"colecaoDebitoTipoVigencia",
							colecaoDebitoTipoVigencia);
				}
			} else {
				httpServletRequest.setAttribute("colecaoDebitoTipoVigencia",
						colecaoDebitoTipoVigencia);
			}
		} else {
			// Nenhuma funcionalidade cadastrada
			throw new ActionServletException("atencao.pesquisa.nenhumresultado");
		}

		return retorno;
	}
}
