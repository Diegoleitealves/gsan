package gcom.gui.micromedicao.hidrometro;

import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.hidrometro.FiltroHidrometroCapacidade;
import gcom.micromedicao.hidrometro.HidrometroCapacidade;

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
 * @since 18/06/2007
 */
public class ExibirManterCapacidadeHidrometroAction extends GcomAction {

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
				.findForward("exibirManterCapacidadeHidrometro");

		// Obt�m a inst�ncia da fachada
		// Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		// Recupera o filtro passado pelo FiltrarResolucaoDiretoriaAction para
		// ser efetuada pesquisa
		FiltroHidrometroCapacidade filtroHidrometroCapacidade = (FiltroHidrometroCapacidade) sessao
				.getAttribute("filtroHidrometroCapacidade");

		// Aciona o controle de pagina��o para que sejam pesquisados apenas
		// os registros que aparecem na p�gina
		Collection colecaoCapacidadeHidrometro = new ArrayList();
		if(filtroHidrometroCapacidade != null && !filtroHidrometroCapacidade.equals("")){
			Map resultado = controlarPaginacao(httpServletRequest, retorno,
					filtroHidrometroCapacidade, HidrometroCapacidade.class.getName());
			colecaoCapacidadeHidrometro = (Collection) resultado
				.get("colecaoRetorno");
			retorno = (ActionForward) resultado.get("destinoActionForward");
		}
		// Verifica se a cole��o retornada pela pesquisa � nula, em caso
		// afirmativo comunica ao usu�rio que n�o existe nenhuma resolu��o de
		// diretoria cadastrado para a pesquisa efetuada e em caso negativo e se
		// atender a algumas condi��es seta o retorno para o
		// ExibirAtualizarResolucaoDiretoriaAction, se n�o atender manda a
		// cole��o pelo request para ser recuperado e exibido pelo jsp.
		if (colecaoCapacidadeHidrometro != null
				&& !colecaoCapacidadeHidrometro.isEmpty()) {

			// Verifica se a cole��o cont�m apenas um objeto, se est� retornando
			// da pagina��o (devido ao esquema de pagina��o de 10 em 10 faz uma
			// nova busca), evitando, assim, que caso haja 11 elementos no
			// retorno da pesquisa e o usu�rio selecione o link para ir para a
			// segunda p�gina ele n�o v� para tela de atualizar.
			if (colecaoCapacidadeHidrometro.size() == 1
					&& (httpServletRequest.getParameter("page.offset") == null || httpServletRequest
							.getParameter("page.offset").equals("1"))) {
				// Verifica se o usu�rio marcou o checkbox de atualizar no jsp
				// resolucao_diretoria_filtrar. Caso todas as condi��es sejam
				// verdadeiras seta o retorno para o
				// ExibirAtualizarResolucaoDiretoriaAction e em caso negativo
				// manda a cole��o pelo request.
				if (httpServletRequest.getAttribute("atualizar") != null) {
					retorno = actionMapping
							.findForward("exibirAtualizarCapacidadeHidrometro");
					HidrometroCapacidade hidrometroCapacidade = (HidrometroCapacidade) colecaoCapacidadeHidrometro
							.iterator().next();
					sessao.setAttribute("hidrometroCapacidade",
							hidrometroCapacidade);
				} else {
					httpServletRequest.setAttribute(
							"colecaoCapacidadeHidrometro",
							colecaoCapacidadeHidrometro);
				}
			} else {
				httpServletRequest.setAttribute("colecaoCapacidadeHidrometro",
						colecaoCapacidadeHidrometro);
			}
		} else {
			// Caso a pesquisa n�o retorne nenhum objeto comunica ao usu�rio;
			throw new ActionServletException("atencao.pesquisa.nenhumresultado");
		}

		return retorno;

	}

}
