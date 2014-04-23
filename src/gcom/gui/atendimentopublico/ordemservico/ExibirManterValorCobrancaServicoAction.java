package gcom.gui.atendimentopublico.ordemservico;

import gcom.atendimentopublico.ordemservico.FiltroServicoCobrancaValor;
import gcom.atendimentopublico.ordemservico.ServicoCobrancaValor;
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
 * @author R�mulo Aur�lio de Melo
 * @date 30/10/2006
 */
public class ExibirManterValorCobrancaServicoAction extends GcomAction {

	/**
	 * [UC0393] Manter Valor de Cobran�a do Servi�o
	 * 
	 * Este caso de uso permite alterar ou excluir um valor de cobran�a de
	 * servi�o
	 * 
	 * @author R�mulo Aur�lio  de Melo
	 * @date 30/10/2006
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
				.findForward("exibirManterValorCobrancaServicoAction");

		// Obt�m a inst�ncia da fachada
		// Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		// Limpa o atributo se o usu�rio voltou para o manter
		if (sessao.getAttribute("colecaoServicoCobrancaValorTela") != null) {
			sessao.removeAttribute("colecaoServicoCobrancaValorTela");
		}

		// Recupera o filtro passado pelo FiltrarValorCobrancaServicoAction para
		// ser efetuada pesquisa

		FiltroServicoCobrancaValor filtroServicoCobrancaValor = (FiltroServicoCobrancaValor) sessao
				.getAttribute("filtroServicoCobrancaValor");

		// Aciona o controle de pagina��o para que sejam pesquisados apenas
		// os registros que aparecem na p�gina

		Map resultado = controlarPaginacao(httpServletRequest, retorno,
				filtroServicoCobrancaValor, ServicoCobrancaValor.class
						.getName());
		Collection colecaoServicoCobrancaValor = (Collection) resultado
				.get("colecaoRetorno");
		retorno = (ActionForward) resultado.get("destinoActionForward");

		// Verifica se a cole��o retornada pela pesquisa � nula, em caso
		// afirmativo comunica ao usu�rio que n�o existe nenhum valor de
		// cobranca do servico
		// cadastrada
		// para a pesquisa efetuada e em caso negativo e se
		// atender a algumas condi��es seta o retorno para o
		// ExibirAtualizarServicoCobrancaValorAction, se n�o atender manda a
		// cole��o pelo request para ser recuperado e exibido pelo jsp.

		if (colecaoServicoCobrancaValor != null
				&& !colecaoServicoCobrancaValor.isEmpty()) {

			// Verifica se a cole��o cont�m apenas um objeto, se est� retornando
			// da pagina��o (devido ao esquema de pagina��o de 10 em 10 faz uma
			// nova busca), evitando, assim, que caso haja 11 elementos no
			// retorno da pesquisa e o usu�rio selecione o link para ir para a
			// segunda p�gina ele n�o v� para tela de atualizar.
			
			//ServicoCobrancaValor servicoCobrancaValor1 = (ServicoCobrancaValor) colecaoServicoCobrancaValor
			//.iterator().next();
			
			//servicoCobrancaValor1.getServicoTipo().getDescricao();

			if (colecaoServicoCobrancaValor.size() == 1
					&& (httpServletRequest.getParameter("page.offset") == null || httpServletRequest
							.getParameter("page.offset").equals("1"))) {

				// Verifica se o usu�rio marcou o checkbox de atualizar no jsp
				// valor_cobranca_servico_filtrar. Caso todas as condi��es sejam
				// verdadeiras seta o retorno para o
				// ExibirAtualizarServicoCobrancaValorAction e em caso negativo
				// manda a cole��o pelo request.

				if (httpServletRequest.getParameter("atualizar") != null) {
					retorno = actionMapping
							.findForward("atualizarValorCobrancaServico");
					ServicoCobrancaValor servicoCobrancaValor = (ServicoCobrancaValor) colecaoServicoCobrancaValor
							.iterator().next();
					sessao.setAttribute("objetoservicoCobrancaValor",
							servicoCobrancaValor);
					httpServletRequest.setAttribute("manter", "sim");

				} else {
					
					sessao.removeAttribute("objetoservicoCobrancaValor");				
					sessao.removeAttribute("idRegistroAtualizar");
					
					httpServletRequest.setAttribute(
							"colecaoServicoCobrancaValor",
							colecaoServicoCobrancaValor);
				}
			} else {
				
				sessao.removeAttribute("objetoservicoCobrancaValor");
				sessao.removeAttribute("idRegistroAtualizar");
				
				httpServletRequest.setAttribute("colecaoServicoCobrancaValor",
						colecaoServicoCobrancaValor);
			}
		} else {
			// Nenhuma funcionalidade cadastrada
			throw new ActionServletException("atencao.pesquisa.nenhumresultado");
		}

		return retorno;
	}
}
