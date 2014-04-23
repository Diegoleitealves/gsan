package gcom.gui.faturamento;

import gcom.faturamento.FaturamentoSituacaoTipo;
import gcom.faturamento.FiltroFaturamentoSituacaoTipo;
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
 * @author Arthur Carvalho
 * @date 21/08/08
 */
public class ExibirManterFaturamentoSituacaoTipoAction extends GcomAction {
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
				.findForward("exibirManterFaturamentoSituacaoTipo");

		Collection colecaoFaturamentoSituacaoTipo = new ArrayList();

		HttpSession sessao = httpServletRequest.getSession(false);

		FiltroFaturamentoSituacaoTipo filtroFaturamentoSituacaoTipo = (FiltroFaturamentoSituacaoTipo) sessao
				.getAttribute("filtroFaturamentoSituacaoTipo");

		filtroFaturamentoSituacaoTipo.setCampoOrderBy(FiltroFaturamentoSituacaoTipo.ID);	
		
		if (filtroFaturamentoSituacaoTipo!= null && !filtroFaturamentoSituacaoTipo.equals("")) {
			Map resultado = 
				controlarPaginacao(httpServletRequest, retorno,filtroFaturamentoSituacaoTipo, FaturamentoSituacaoTipo.class.getName());
			
			colecaoFaturamentoSituacaoTipo = (Collection) resultado.get("colecaoRetorno");
			
			retorno = (ActionForward) resultado.get("destinoActionForward");
		}

		if (colecaoFaturamentoSituacaoTipo != null
				&& !colecaoFaturamentoSituacaoTipo.isEmpty()) {
			if (colecaoFaturamentoSituacaoTipo.size() == 1
					&& (httpServletRequest.getParameter("page.offset") == null || httpServletRequest
							.getParameter("page.offset").equals("1"))) {
				if (httpServletRequest.getParameter("indicadorAtualizar") != null) {
					retorno = actionMapping
							.findForward("exibirAtualizarFaturamentoSituacaoTipo");
					FaturamentoSituacaoTipo faturamentoSituacaoTipo = (FaturamentoSituacaoTipo) colecaoFaturamentoSituacaoTipo
							.iterator().next();
					sessao.setAttribute("faturamentoSituacaoTipo", faturamentoSituacaoTipo);
				} else {
					httpServletRequest.setAttribute("colecaoFaturamentoSituacaoTipo",
							colecaoFaturamentoSituacaoTipo);
				}
			} else {
				httpServletRequest.setAttribute("colecaoFaturamentoSituacaoTipo",
						colecaoFaturamentoSituacaoTipo);
			}
		} else {
			throw new ActionServletException("atencao.pesquisa.nenhumresultado");
		}

		return retorno;
	}
}
