package gcom.gui.util.tabelaauxiliar.abreviada;

import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

/**
 * < <Descri��o da Classe>>
 * 
 * @author Administrador
 */
public class ExibirPesquisarTabelaAuxiliarAbreviadaAction extends GcomAction {
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
				.findForward("pesquisarTabelaAuxiliarAbreviada");

		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		// Pega o parametro passado no request
		String tela = httpServletRequest.getParameter("tela");
		
		/*
		 * Colocado por Raphael Rossiter em 28/05/2007
		 */
		if (tela != null && !tela.equals("")){
			DynaValidatorForm pesquisarActionForm = (DynaValidatorForm) actionForm;
			pesquisarActionForm.set("tipoPesquisaDescricao", ConstantesSistema.TIPO_PESQUISA_INICIAL.toString());
		}

		// Pega o par�metro que indica se a pesquisa � do tipo que fecha o popup
		// de pesquisa e retorna os dados (indicado pela aus�ncia de par�metro)
		// ou do tipo que � acionado a partir de um popup
		String tipoPesquisa = httpServletRequest.getParameter("tipoPesquisa");

		String caminhoRetorno = httpServletRequest
				.getParameter("caminhoRetorno");
		
		String caminhoRetornoTelaPesquisa = httpServletRequest
			.getParameter("caminhoRetornoTelaPesquisa");

		// Repassa o tipo da pesquisa para a p�gina
		httpServletRequest.setAttribute("tipoPesquisa", tipoPesquisa);

		// Repassa o caminho do retorno para a p�gina
		httpServletRequest.setAttribute("caminhoRetorno", caminhoRetorno);
		
		// Repassa o caminho do retorno para a p�gina
		sessao.setAttribute("caminhoRetornoTelaPesquisa", caminhoRetornoTelaPesquisa);
		
		// Busca na classe DadosTelaTabelaAuxiliarAbreviada pela configura��o da
		// tela requisitada
		DadosTelaTabelaAuxiliarAbreviada dados = DadosTelaTabelaAuxiliarAbreviada
				.obterDadosTelaTabelaAuxiliar(tela);

		sessao.setAttribute("dados", dados);

		return retorno;

	}
}
