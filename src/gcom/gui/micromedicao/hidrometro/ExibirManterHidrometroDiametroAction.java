package gcom.gui.micromedicao.hidrometro;

import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.hidrometro.FiltroHidrometroDiametro;
import gcom.micromedicao.hidrometro.HidrometroDiametro;

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
public class ExibirManterHidrometroDiametroAction extends GcomAction {
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

		// Seta o caminho do retorno
		ActionForward retorno = actionMapping
				.findForward("exibirManterHidrometroDiametro");

		Collection colecaoHidrometroDiametro = new ArrayList();

		// Mudar isso quando for implementado o esquema de seguran�a
		HttpSession session = httpServletRequest.getSession(false);

		FiltroHidrometroDiametro filtroHidrometroDiametro = (FiltroHidrometroDiametro) session
				.getAttribute("filtroHidrometroDiametro");

		filtroHidrometroDiametro.setCampoOrderBy(FiltroHidrometroDiametro.ID,
				FiltroHidrometroDiametro.DESCRICAO,
				FiltroHidrometroDiametro.DESCRICAO_ABREVIADA);

		if (filtroHidrometroDiametro != null && !filtroHidrometroDiametro.equals("")) {
			
			Map resultado = controlarPaginacao(httpServletRequest, retorno,
					filtroHidrometroDiametro, HidrometroDiametro.class.getName());
			colecaoHidrometroDiametro = (Collection) resultado.get("colecaoRetorno");
			retorno = (ActionForward) resultado.get("destinoActionForward");
		
		}

		if (colecaoHidrometroDiametro != null && !colecaoHidrometroDiametro.isEmpty()) {
		
			if (colecaoHidrometroDiametro.size() == 1
					&& (httpServletRequest.getParameter("page.offset") == null 
							|| httpServletRequest.getParameter("page.offset").equals("1"))) {
			
				if (httpServletRequest.getParameter("indicadorAtualizar") != null) {
					// Caso o indicador ATUALIZAR esteja marcado, altera o caminho do retorno
					retorno = actionMapping
							.findForward("exibirAtualizarHidrometroDiametro");
					
					HidrometroDiametro hidrometroDiametro = (HidrometroDiametro) colecaoHidrometroDiametro
							.iterator().next();
					
					session.setAttribute("hidrometroDiametro", hidrometroDiametro);
				
				} else {
				
					httpServletRequest.setAttribute("colecaoHidrometroDiametro",colecaoHidrometroDiametro);
				
				}
			
			} else {
			
				httpServletRequest.setAttribute("colecaoHidrometroDiametro",colecaoHidrometroDiametro);
			
			}
		
		} else {
			// Caso n�o haja nenhum resultado da pesquisa
			throw new ActionServletException("atencao.pesquisa.nenhumresultado");
		
		}

		return retorno;
	}
}
