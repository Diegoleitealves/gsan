package gcom.gui.arrecadacao.banco;

import gcom.arrecadacao.banco.ContaBancaria;
import gcom.arrecadacao.banco.FiltroContaBancaria;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 
 * Permite exibir uma lista com as resolu��es de diretoria retornadas do
 * FiltrarManterTipoRetornoOrdemServicoReferidaAction ou ir para o
 * ExibirManterTipoRetornoOrdemServicoReferidaAction
 * 
 * @author Thiago Ten�rio
 * @since 31/10/2006
 */
public class ExibirManterContaBancariaAction extends GcomAction {

	/**
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

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping
				.findForward("exibirManterContaBancaria");

		// Obt�m a inst�ncia da fachada
		// Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);
		
		sessao.removeAttribute("contaBancariaAtualizar");

		// Recupera o filtro passado pelo FiltrarResolucaoDiretoriaAction para
		// ser efetuada pesquisa
		FiltroContaBancaria filtroContaBancaria = (FiltroContaBancaria) sessao
				.getAttribute("filtroContaBancaria");
		filtroContaBancaria.adicionarCaminhoParaCarregamentoEntidade("agencia.banco");
		

		// Aciona o controle de pagina��o para que sejam pesquisados apenas
		// os registros que aparecem na p�gina
		Collection colecaoContaBancaria = new ArrayList();
		if(filtroContaBancaria != null && !filtroContaBancaria.equals("")){
			Map resultado = controlarPaginacao(httpServletRequest, retorno,
				filtroContaBancaria, ContaBancaria.class.getName());
			colecaoContaBancaria = (Collection) resultado
				.get("colecaoRetorno");
			retorno = (ActionForward) resultado.get("destinoActionForward");
		}
		// Verifica se a cole��o retornada pela pesquisa � nula, em caso
		// afirmativo comunica ao usu�rio que n�o existe nenhuma resolu��o de
		// diretoria cadastrado para a pesquisa efetuada e em caso negativo e se
		// atender a algumas condi��es seta o retorno para o
		// ExibirAtualizarResolucaoDiretoriaAction, se n�o atender manda a
		// cole��o pelo request para ser recuperado e exibido pelo jsp.
		if (colecaoContaBancaria != null
				&& !colecaoContaBancaria.isEmpty()) {

			// Verifica se a cole��o cont�m apenas um objeto, se est� retornando
			// da pagina��o (devido ao esquema de pagina��o de 10 em 10 faz uma
			// nova busca), evitando, assim, que caso haja 11 elementos no
			// retorno da pesquisa e o usu�rio selecione o link para ir para a
			// segunda p�gina ele n�o v� para tela de atualizar.
			if (colecaoContaBancaria.size() == 1
					&& (httpServletRequest.getParameter("page.offset") == null || httpServletRequest
							.getParameter("page.offset").equals("1"))) {
				// Verifica se o usu�rio marcou o checkbox de atualizar no jsp
				// resolucao_diretoria_filtrar. Caso todas as condi��es sejam
				// verdadeiras seta o retorno para o
				// ExibirAtualizarResolucaoDiretoriaAction e em caso negativo
				// manda a cole��o pelo request.
				if (sessao.getAttribute("atualizar") != null) {
					retorno = actionMapping
							.findForward("exibirAtualizarContaBancaria");
					ContaBancaria contaBancaria = (ContaBancaria) colecaoContaBancaria
							.iterator().next();
					sessao.setAttribute("contaBancaria",
							contaBancaria);
				} else {
					httpServletRequest.setAttribute(
							"colecaoContaBancaria",
							colecaoContaBancaria);
				}
			} else {
				httpServletRequest.setAttribute("colecaoContaBancaria",
						colecaoContaBancaria);
			}
		} else {
			// Caso a pesquisa n�o retorne nenhum objeto comunica ao usu�rio;
			throw new ActionServletException("atencao.pesquisa.nenhumresultado");
		}

		return retorno;

	}

}
