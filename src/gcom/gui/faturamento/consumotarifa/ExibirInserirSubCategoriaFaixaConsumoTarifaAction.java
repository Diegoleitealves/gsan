package gcom.gui.faturamento.consumotarifa;

import gcom.faturamento.consumotarifa.ConsumoTarifaFaixa;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * < <Descri��o da Classe>>
 * 
 * @author Administrador
 */
public class ExibirInserirSubCategoriaFaixaConsumoTarifaAction extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping
				.findForward("inserirSubCategoriaFaixaConsumoTarifa");

		InserirSubCategoriaFaixaConsumoTarifaActionForm inserirSubCategoriaFaixaConsumoTarifaActionForm = (InserirSubCategoriaFaixaConsumoTarifaActionForm) actionForm;

		//Fachada fachada = Fachada.getInstancia();

			
		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		if ((httpServletRequest.getParameter("parametro2") != null)
				&& (!httpServletRequest.getParameter("parametro2").equals(""))) {
			String consumoMinimo = (String) httpServletRequest
					.getParameter("parametro2");
			sessao.setAttribute("consumoMinimo", consumoMinimo);
		}

		if ((httpServletRequest.getParameter("parametro1") != null)
				&& (!httpServletRequest.getParameter("parametro1").equals(""))) {
			String categoriaSelected = (String) httpServletRequest
					.getParameter("parametro1");
			sessao.setAttribute("categoriaSelected", categoriaSelected);
		}

		if ((httpServletRequest.getParameter("parametro3") != null)
				&& (!httpServletRequest.getParameter("parametro3").equals(""))) {
			String valorMinimo = (String) httpServletRequest
					.getParameter("parametro3");
			sessao.setAttribute("valorMinimo", valorMinimo);
		}
		
		if ((httpServletRequest.getParameter("parametro4") != null)
				&& (!httpServletRequest.getParameter("parametro4").equals(""))) {
			String subCategoriaSelected = (String) httpServletRequest
					.getParameter("parametro4");
			sessao.setAttribute("subCategoriaSelected", subCategoriaSelected);
		}

		if ((httpServletRequest.getParameter("limpaFaixa") != null)
				&& (httpServletRequest.getParameter("limpaFaixa").equals("1"))) {
			sessao
					.removeAttribute("InserirSubCategoriaFaixaConsumoTarifaActionForm");
		}
		
		
		Collection colecaoFaixa = (Collection) sessao.getAttribute("colecaoFaixa");
		
		
		if ((colecaoFaixa != null) && (!colecaoFaixa.isEmpty())){
			Iterator colecaoFaixaIt = colecaoFaixa.iterator();
			boolean i = false;
			while (colecaoFaixaIt.hasNext()) {
				ConsumoTarifaFaixa consumoTarifaFaixa = (ConsumoTarifaFaixa) colecaoFaixaIt.next();
				if (consumoTarifaFaixa.getNumeroConsumoFaixaFim().toString().equals("999999")){
					i = true;
				}
				
			}
			if (i){
				throw new ActionServletException(
					"atencao.faixa_limite_superior_existe");
			}
		}
		
		if (httpServletRequest.getParameter("limpaForm") != null){
			inserirSubCategoriaFaixaConsumoTarifaActionForm.setLimiteSuperiorFaixa("");
			inserirSubCategoriaFaixaConsumoTarifaActionForm.setValorM3Faixa("");
		}
		
		
		return retorno;
	}

}
