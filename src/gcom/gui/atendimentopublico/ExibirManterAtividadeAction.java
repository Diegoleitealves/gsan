package gcom.gui.atendimentopublico;

import gcom.atendimentopublico.ordemservico.Atividade;
import gcom.atendimentopublico.ordemservico.FiltroAtividade;
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
public class ExibirManterAtividadeAction extends GcomAction {
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
		ActionForward retorno = actionMapping.findForward("exibirManterAtividade");

		Collection colecaoAtividade = new ArrayList();

		// Mudar isso quando for implementado o esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		FiltroAtividade filtroAtividade = (FiltroAtividade) sessao.getAttribute("filtroAtividade");

		filtroAtividade.setCampoOrderBy(FiltroAtividade.ID,
										FiltroAtividade.DESCRICAO,
										FiltroAtividade.DESCRICAOABREVIADA,
										FiltroAtividade.INDICADORATIVIDADEUNICA);


		if (filtroAtividade != null && !filtroAtividade.equals("")) {
		
			Map resultado = controlarPaginacao(
					httpServletRequest, retorno,filtroAtividade, Atividade.class.getName());
			colecaoAtividade = (Collection) resultado.get("colecaoRetorno");
			retorno = (ActionForward) resultado.get("destinoActionForward");
		}

		if (colecaoAtividade != null && !colecaoAtividade.isEmpty()) {
			
			if (colecaoAtividade.size() == 1
					&& (httpServletRequest.getParameter("page.offset") == null 
							|| httpServletRequest.getParameter("page.offset").equals("1"))) {
				
				// Verifica se o indicador ATUALIZAR est� marcado
				if (httpServletRequest.getParameter("indicadorAtualizar") != null) {
					
					retorno = actionMapping.findForward("exibirAtualizarAtividade");
					Atividade atividade = (Atividade) colecaoAtividade.iterator().next();
					sessao.setAttribute("atividade", atividade);
				
				} else {
				
					httpServletRequest.setAttribute("colecaoAtividade",colecaoAtividade);
				}
			} else {
				
				httpServletRequest.setAttribute("colecaoAtividade",colecaoAtividade);
			}
		} else {
			
			throw new ActionServletException("atencao.pesquisa.nenhumresultado");
		
		}

		return retorno;
	}
}
