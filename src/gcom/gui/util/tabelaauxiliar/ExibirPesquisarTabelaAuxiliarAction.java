package gcom.gui.util.tabelaauxiliar;

import gcom.gui.GcomAction;

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
public class ExibirPesquisarTabelaAuxiliarAction extends GcomAction {
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
				.findForward("pesquisarTabelaAuxiliar");

		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		// Pega o parametro passado no request
		String tela = httpServletRequest.getParameter("tela");

		// Pega o par�metro que indica se a pesquisa � do tipo que fecha o popup
		// de pesquisa e retorna os dados (indicado pela aus�ncia de par�metro)
		// ou do tipo que � acionado a partir de um popup
		String tipoPesquisa = httpServletRequest.getParameter("tipoPesquisa");
		
		String caminhoRetorno = httpServletRequest
				.getParameter("caminhoRetorno");

		// Repassa o tipo da pesquisa para a p�gina
		httpServletRequest.setAttribute("tipoPesquisa", tipoPesquisa);

		// Repassa o caminho do retorno para a p�gina
		httpServletRequest.setAttribute("caminhoRetorno", caminhoRetorno);

		// Busca na classe DadosTelaTabelaAuxiliarAbreviada pela configura��o da
		// tela requisitada
		DadosTelaTabelaAuxiliar dados = DadosTelaTabelaAuxiliar.obterDadosTelaTabelaAuxiliar(tela);

		sessao.setAttribute("dados", dados);

		return retorno;

	}
}
