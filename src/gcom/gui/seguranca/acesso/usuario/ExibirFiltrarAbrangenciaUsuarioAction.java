package gcom.gui.seguranca.acesso.usuario;

import gcom.gui.GcomAction;

/**
 * Permite filtrar Abrang�ncia de usuario [UC0219] Filtrar Resolu��o de
 * Diretoria
 * 
 * @author Rafael Corr�a
 * @since 31/03/2006
 */
public class ExibirFiltrarAbrangenciaUsuarioAction extends GcomAction {

	/**
	 * 
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
/**	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		FiltrarResolucaoDiretoriaActionForm filtrarResolucaoDiretoriaActionForm = (FiltrarResolucaoDiretoriaActionForm) actionForm;

		filtrarResolucaoDiretoriaActionForm.setAtualizar("1");
		
		HttpSession sessao = httpServletRequest.getSession(false);
		
		if (httpServletRequest.getParameter("paginacao") != null) {
		
			filtrarResolucaoDiretoriaActionForm.setNumero((String) sessao.getAttribute("numero"));
			filtrarResolucaoDiretoriaActionForm.setAssunto((String) sessao.getAttribute("assunto"));
			filtrarResolucaoDiretoriaActionForm.setDataInicio((String) sessao.getAttribute("dataInicio"));
			filtrarResolucaoDiretoriaActionForm.setDataFim((String) sessao.getAttribute("dataFim"));
		
		}

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping
				.findForward("exibirFiltrarResolucaoDiretoria");

		return retorno;

	}
**/
}
