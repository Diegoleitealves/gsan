package gcom.gui.arrecadacao;

import gcom.arrecadacao.ArrecadacaoForma;
import gcom.arrecadacao.FiltroArrecadacaoForma;
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
 * @author Vin�cius Medeiros
 * 
 */
public class ExibirManterArrecadacaoFormaAction extends GcomAction {
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

		// Seta o caminho de retorno
		ActionForward retorno = actionMapping
				.findForward("exibirManterArrecadacaoForma");

		Collection colecaoArrecadacaoForma = new ArrayList();

		// Mudar isso quando houver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		FiltroArrecadacaoForma filtroArrecadacaoForma = (FiltroArrecadacaoForma) sessao
			.getAttribute("filtroArrecadacaoForma");
		
		if(filtroArrecadacaoForma != null && !filtroArrecadacaoForma.equals("")){
			Map resultado = controlarPaginacao(httpServletRequest, retorno,
					filtroArrecadacaoForma, ArrecadacaoForma.class.getName());
			colecaoArrecadacaoForma = (Collection) resultado
				.get("colecaoRetorno");
			retorno = (ActionForward) resultado.get("destinoActionForward");
		}		
		
		if (colecaoArrecadacaoForma != null
				&& !colecaoArrecadacaoForma.isEmpty()) {
			if (colecaoArrecadacaoForma.size() == 1
					&& (httpServletRequest.getParameter("page.offset") == null || httpServletRequest
							.getParameter("page.offset").equals("1"))) {
				if (httpServletRequest.getParameter("indicadorAtualizar") != null) {
					retorno = actionMapping
							.findForward("exibirAtualizarArrecadacaoForma");
					ArrecadacaoForma arrecadacaoForma = (ArrecadacaoForma) colecaoArrecadacaoForma
							.iterator().next();
					sessao.setAttribute("arrecadacaoForma",
							arrecadacaoForma);
				} else {
					httpServletRequest.setAttribute(
							"colecaoArrecadacaoForma",
							colecaoArrecadacaoForma);
				}
			} else {
				httpServletRequest.setAttribute("colecaoArrecadacaoForma",
						colecaoArrecadacaoForma);
			}
		} else {
			throw new ActionServletException("atencao.pesquisa.nenhumresultado");
		}

		return retorno;
}}
