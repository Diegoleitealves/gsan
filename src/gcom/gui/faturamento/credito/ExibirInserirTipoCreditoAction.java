package gcom.gui.faturamento.credito;

import gcom.fachada.Fachada;
import gcom.financeiro.lancamento.FiltroLancamentoItemContabil;
import gcom.financeiro.lancamento.LancamentoItemContabil;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Permite Inserir uma Agencia Bancaria
 * 
 * @author Thiago Ten�rio
 * @date 07/02/2007
 */
public class ExibirInserirTipoCreditoAction extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		// Seta o retorno
		ActionForward retorno = actionMapping
				.findForward("exibirInserirTipoCredito");

		// Obt�m a inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		// Obt�m a sess�o
		HttpSession sessao = httpServletRequest.getSession(false);

		InserirTipoCreditoActionForm inserirTipoCreditoActionForm = (InserirTipoCreditoActionForm) actionForm;

		String limparForm = (String) httpServletRequest
				.getParameter("limparCampos");

		Collection colecaoPesquisa = null;

		FiltroLancamentoItemContabil filtroLancamentoItemContabil = new FiltroLancamentoItemContabil();

		filtroLancamentoItemContabil
				.setCampoOrderBy(FiltroLancamentoItemContabil.DESCRICAO);

		filtroLancamentoItemContabil.adicionarParametro(new ParametroSimples(
				FiltroLancamentoItemContabil.INDICADOR_USO,
				ConstantesSistema.INDICADOR_USO_ATIVO));

		// Retorna banco
		colecaoPesquisa = fachada.pesquisar(filtroLancamentoItemContabil,
				LancamentoItemContabil.class.getName());

		if (colecaoPesquisa == null || colecaoPesquisa.isEmpty()) {
			// Nenhum registro na tabela localidade_porte foi encontrado
			throw new ActionServletException(
					"atencao.pesquisa.nenhum_registro_tabela", null,
					"Tipo do Lan�amento do Item Cont�bi");
		} else {
			sessao.setAttribute("colecaoPorte", colecaoPesquisa);
		}

		// Constr�i filtro para pesquisa do banco
		filtroLancamentoItemContabil
				.setCampoOrderBy(FiltroLancamentoItemContabil.ID);
		filtroLancamentoItemContabil.adicionarParametro(new ParametroSimples(
				FiltroLancamentoItemContabil.ID,
				ConstantesSistema.INDICADOR_USO_ATIVO));

		sessao.setAttribute("colecaoTipoLancamentoContabil", fachada.pesquisar(
				filtroLancamentoItemContabil, LancamentoItemContabil.class
						.getName(), "Tipo do Lan�amento do Item Cont�bil"));

		httpServletRequest.setAttribute("colecaoTipoLancamentoContabil",
				colecaoPesquisa);

		if ((limparForm == null || limparForm.trim().equalsIgnoreCase(""))
				|| (httpServletRequest.getParameter("desfazer") != null && httpServletRequest
						.getParameter("desfazer").equalsIgnoreCase("S"))) {
			// -------------- bt DESFAZER ---------------

			// Limpando o formulario
			inserirTipoCreditoActionForm.setDescricao("");
			inserirTipoCreditoActionForm.setAbreviatura("");
			inserirTipoCreditoActionForm.setTipoLancamentoContabil("");
			inserirTipoCreditoActionForm.setIndicadorgeracaoAutomaica("");
			inserirTipoCreditoActionForm.setValorLimiteCredito("");

		}

		// devolve o mapeamento de retorno
		return retorno;
	}

}
