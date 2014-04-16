package gcom.gui.util.tabelaauxiliar.unidade;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.gui.util.tabelaauxiliar.DadosTelaTabelaAuxiliar;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ComparacaoTexto;
import gcom.util.filtro.ParametroSimples;
import gcom.util.tabelaauxiliar.unidade.FiltroTabelaAuxiliarUnidade;
import gcom.util.tabelaauxiliar.unidade.TabelaAuxiliarUnidade;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

/**
 * Descri��o da classe
 * 
 * @author COMPESA
 * @date 02/08/2006
 */
public class PesquisarTabelaAuxiliarUnidadeAction extends GcomAction {
	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		DynaValidatorForm pesquisarActionForm = (DynaValidatorForm) actionForm;

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping
				.findForward("retornoPesquisaTabelaAuxiliarUnidade");

		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		// Pega o parametro passado no request
		DadosTelaTabelaAuxiliar dados = (DadosTelaTabelaAuxiliarUnidade) sessao
				.getAttribute("dados");

		// Recupera os par�metros do form
		String id = (String) pesquisarActionForm.get("id");
		String descricao = (String) pesquisarActionForm.get("descricao");
		String descricaoAbreviada = (String) pesquisarActionForm
				.get("descricaoAbreviada");
		String unidadeMaterial = (String) pesquisarActionForm
				.get("unidadeMaterial");

		// cria o filtro para Tabela Auxiliar abreviada
		FiltroTabelaAuxiliarUnidade filtroTabelaAuxiliarUnidade = new FiltroTabelaAuxiliarUnidade();
		filtroTabelaAuxiliarUnidade.adicionarCaminhoParaCarregamentoEntidade("materialUnidade");

		boolean peloMenosUmParametroInformado = false;

		// Insere os par�metros informados no filtro
		if (id != null && !id.trim().equalsIgnoreCase("")) {
			peloMenosUmParametroInformado = true;
			filtroTabelaAuxiliarUnidade
					.adicionarParametro(new ParametroSimples(
							FiltroTabelaAuxiliarUnidade.ID, id));
		}
		if (descricao != null && !descricao.trim().equalsIgnoreCase("")) {
			peloMenosUmParametroInformado = true;
			filtroTabelaAuxiliarUnidade.adicionarParametro(new ComparacaoTexto(
					FiltroTabelaAuxiliarUnidade.DESCRICAO, descricao));
		}

		if (descricaoAbreviada != null
				&& !descricaoAbreviada.trim().equalsIgnoreCase("")) {
			peloMenosUmParametroInformado = true;
			filtroTabelaAuxiliarUnidade.adicionarParametro(new ComparacaoTexto(
					FiltroTabelaAuxiliarUnidade.DESCRICAOABREVIADA,
					descricaoAbreviada));
		}

		if (unidadeMaterial != null
				&& !unidadeMaterial.trim().equals(
						"" + ConstantesSistema.NUMERO_NAO_INFORMADO)) {
			peloMenosUmParametroInformado = true;
			filtroTabelaAuxiliarUnidade
					.adicionarParametro(new ParametroSimples(
							FiltroTabelaAuxiliarUnidade.MATERIAL_UNIDADE_ID,
							unidadeMaterial));

		}

		// Erro caso o usu�rio mandou filtrar sem nenhum par�metro
		if (!peloMenosUmParametroInformado) {
			throw new ActionServletException(
					"atencao.filtro.nenhum_parametro_informado");
		}

		Collection colecaoTabelaAuxiliarUnidade = null;

		// Obt�m a inst�ncia da Fachada
		Fachada fachada = Fachada.getInstancia();

		TabelaAuxiliarUnidade tabelaAuxiliarUnidade = (TabelaAuxiliarUnidade) dados
				.getTabelaAuxiliar();

		// Faz a busca das empresas
		colecaoTabelaAuxiliarUnidade = fachada.pesquisar(
				filtroTabelaAuxiliarUnidade, tabelaAuxiliarUnidade.getClass()
						.getName());

		if (colecaoTabelaAuxiliarUnidade == null
				|| colecaoTabelaAuxiliarUnidade.isEmpty()) {
			// Nenhum municipio cadastrado
			throw new ActionServletException(
					"atencao.pesquisa.nenhumresultado", null, dados.getTitulo());
		} else if (colecaoTabelaAuxiliarUnidade.size() > ConstantesSistema.NUMERO_MAXIMO_REGISTROS_PESQUISA) {
			// Muitos registros encontrados
			throw new ActionServletException(
					"atencao.pesquisa.muitosregistros");
		} else {
			if (colecaoTabelaAuxiliarUnidade.size() == ConstantesSistema.NUMERO_MAXIMO_REGISTROS_PESQUISA) {
				httpServletRequest.setAttribute("limitePesquisa", "");
			}

			// Coloca a cole��o na sess�o
			sessao.setAttribute("colecaoTabelaAuxiliarUnidade",
					colecaoTabelaAuxiliarUnidade);

		}

		// Repassa o tipo da pesquisa para a tela de resultado
		httpServletRequest.setAttribute("tipoPesquisa", httpServletRequest
				.getParameter("tipoPesquisa"));

		// Repassa o caminho do resultado para a tela de resultado
		httpServletRequest.setAttribute("caminhoRetorno", httpServletRequest
				.getParameter("caminhoRetorno"));

		// Devolve o mapeamento de retorno
		return retorno;

	}
}
