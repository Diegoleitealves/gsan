package gcom.gui.util.tabelaauxiliar.unidade;

import java.util.Collection;

import gcom.atendimentopublico.ordemservico.FiltroMaterialUnidade;
import gcom.atendimentopublico.ordemservico.MaterialUnidade;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Descri��o da classe
 * 
 * @author R�mulo Aur�lio
 * @date 02/08/2006
 */
public class ExibirPesquisarTabelaAuxiliarUnidadeAction extends GcomAction {
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
				.findForward("pesquisarTabelaAuxiliarUnidade");

		Fachada fachada = Fachada.getInstancia();

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
		DadosTelaTabelaAuxiliarUnidade dados = DadosTelaTabelaAuxiliarUnidade
				.obterDadosTelaTabelaAuxiliarAbreviada(tela);

		FiltroMaterialUnidade filtroMaterialUnidade = new FiltroMaterialUnidade();

		// Verifica se os dados foram informados da tabela existem e joga numa
		// colecao

		Collection<MaterialUnidade> colecaoUnidadeMaterial = fachada.pesquisar(
				filtroMaterialUnidade, MaterialUnidade.class.getName());

		if (colecaoUnidadeMaterial == null || colecaoUnidadeMaterial.isEmpty()) {
			throw new ActionServletException(
					"atencao.entidade_sem_dados_para_selecao", null,
					"Tabela Material Unidade");
		}

		httpServletRequest.setAttribute("colecaoUnidadeMaterial",
				colecaoUnidadeMaterial);

		sessao.setAttribute("dados", dados);

		return retorno;

	}
}
