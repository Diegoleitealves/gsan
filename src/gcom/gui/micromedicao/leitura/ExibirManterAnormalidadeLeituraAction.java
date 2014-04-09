package gcom.gui.micromedicao.leitura;

import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.leitura.FiltroLeituraAnormalidade;
import gcom.micromedicao.leitura.LeituraAnormalidade;

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
 * /**
 * <p>
 * <b>[UC0191]</b> Manter Anormalidade de Leitura
 * </p>
 * <p>
 * Esta funcionalidade permite atualizar uma Anormalidade de Leitura
 * </p>
 * 
 * @author Thiago Ten�rio
 * @since 31/10/2006
 */
public class ExibirManterAnormalidadeLeituraAction extends GcomAction {

	/**
	 * 
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		ActionForward retorno = actionMapping.findForward("exibirManterAnormalidadeLeitura");

		HttpSession sessao = httpServletRequest.getSession(false);

		/*
		 * Recupera o filtro passado pelo FiltrarResolucaoDiretoriaAction para
		 * ser efetuada pesquisa
		 */
		FiltroLeituraAnormalidade filtroLeituraAnormalidade = (FiltroLeituraAnormalidade) sessao.getAttribute("filtroLeituraAnormalidade");

		/*
		 * Aciona o controle de pagina��o para que sejam pesquisados apenas os
		 * registros que aparecem na p�gina
		 */
		Collection colecaoAnormalidadeLeitura = new ArrayList();
		if (filtroLeituraAnormalidade != null && !filtroLeituraAnormalidade.equals("")) {
			Map resultado = controlarPaginacao(httpServletRequest, retorno, filtroLeituraAnormalidade, LeituraAnormalidade.class.getName());
			colecaoAnormalidadeLeitura = (Collection) resultado.get("colecaoRetorno");
			retorno = (ActionForward) resultado.get("destinoActionForward");
		}
		/*
		 * Verifica se a cole��o retornada pela pesquisa � nula, em caso
		 * afirmativo comunica ao usu�rio que n�o existe nenhuma resolu��o de
		 * diretoria cadastrado para a pesquisa efetuada e em caso negativo e se
		 * atender a algumas condi��es seta o retorno para o
		 * ExibirAtualizarResolucaoDiretoriaAction, se n�o atender manda a
		 * cole��o pelo request para ser recuperado e exibido pelo jsp.
		 */
		if (colecaoAnormalidadeLeitura != null && !colecaoAnormalidadeLeitura.isEmpty()) {

			/*
			 * Verifica se a cole��o cont�m apenas um objeto, se est� retornando
			 * da pagina��o (devido ao esquema de pagina��o de 10 em 10 faz uma
			 * nova busca), evitando, assim, que caso haja 11 elementos no
			 * retorno da pesquisa e o usu�rio selecione o link para ir para a
			 * segunda p�gina ele n�o v� para tela de atualizar.
			 */
			if (colecaoAnormalidadeLeitura.size() == 1
					&& (httpServletRequest.getParameter("page.offset") == null || httpServletRequest.getParameter("page.offset").equals("1"))) {
				/*
				 * Verifica se o usu�rio marcou o checkbox de atualizar no jsp
				 * resolucao_diretoria_filtrar. Caso todas as condi��es sejam
				 * verdadeiras seta o retorno para o
				 * ExibirAtualizarResolucaoDiretoriaAction e em caso negativo
				 * manda a cole��o pelo request.
				 */
				if (httpServletRequest.getParameter("indicadorAtualizar") != null) {
					retorno = actionMapping.findForward("exibirAtualizarAnormalidadeLeitura");
					LeituraAnormalidade leituraAnormalidade = (LeituraAnormalidade) colecaoAnormalidadeLeitura.iterator().next();
					sessao.setAttribute("leituraAnormalidade", leituraAnormalidade);
				} else {
					httpServletRequest.setAttribute("colecaoAnormalidadeLeitura", colecaoAnormalidadeLeitura);
				}
			} else {
				httpServletRequest.setAttribute("colecaoAnormalidadeLeitura", colecaoAnormalidadeLeitura);
			}
		} else {
			// Caso a pesquisa n�o retorne nenhum objeto comunica ao usu�rio;
			throw new ActionServletException("atencao.pesquisa.nenhumresultado");
		}

		return retorno;

	}

}
