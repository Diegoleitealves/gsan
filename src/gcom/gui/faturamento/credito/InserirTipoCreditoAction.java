package gcom.gui.faturamento.credito;

import gcom.fachada.Fachada;
import gcom.faturamento.credito.CreditoTipo;
import gcom.faturamento.credito.FiltroCreditoTipo;
import gcom.financeiro.lancamento.FiltroLancamentoItemContabil;
import gcom.financeiro.lancamento.LancamentoItemContabil;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class InserirTipoCreditoAction extends GcomAction {

	/**
	 * Este caso de uso permite inserir um Tipo de Credito
	 * 
	 * [UC0515] Inserir Tipo de Credito
	 * 
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 * @author Thiago Ten�rio
	 */

	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Obt�m a sess�o
		HttpSession sessao = httpServletRequest.getSession(false);

		// Usuario logado no sistema
		Usuario usuarioLogado = (Usuario)sessao.getAttribute(Usuario.USUARIO_LOGADO);

		// Obt�m a inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		InserirTipoCreditoActionForm inserirTipoCreditoActionForm = (InserirTipoCreditoActionForm) actionForm;

		String descricao = inserirTipoCreditoActionForm.getDescricao();
		String abreviatura = inserirTipoCreditoActionForm.getAbreviatura();
		String tipoLancamentoContabil = inserirTipoCreditoActionForm
				.getTipoLancamentoContabil();
		String indicadorgeracaoAutomaica = inserirTipoCreditoActionForm
				.getIndicadorgeracaoAutomaica();
		String valorLimiteCredito = inserirTipoCreditoActionForm
				.getValorLimiteCredito();
		
		CreditoTipo tipoCreditoInserir = new CreditoTipo();
		Collection colecaoPesquisa = null;

		sessao.removeAttribute("tipoPesquisaRetorno");

		if (Util.validarNumeroMaiorQueZERO(inserirTipoCreditoActionForm
				.getTipoLancamentoContabil())) {
			// Constr�i o filtro para pesquisa do Tipo do Lan�amento do Item Cont�bil
			FiltroLancamentoItemContabil filtroLancamentoItemContabil = new FiltroLancamentoItemContabil();
			filtroLancamentoItemContabil
					.adicionarParametro(new ParametroSimples(
							FiltroCreditoTipo.LANCAMENTO_ITEM_CONTABIL,
							inserirTipoCreditoActionForm
									.getTipoLancamentoContabil()));
		}

		// Descri��o do Tipo de Cr�dito � obrigat�rio.
		if (descricao == null || descricao.equalsIgnoreCase("")) {
			throw new ActionServletException("atencao.required", null,
					"Descri��o do Tipo de Cr�dito");
		}

		// Descri��o do Tipo de Cr�dito Abreviada
		if (abreviatura != null && !abreviatura.equalsIgnoreCase("")) {
			tipoCreditoInserir.setDescricaoAbreviada(abreviatura);
		}

		// Valor Limite do Cr�dito � obrigat�rio.
		if (valorLimiteCredito == null
				|| valorLimiteCredito.equalsIgnoreCase("")) {
			throw new ActionServletException("atencao.required", null,
					"Valor Limite do Cr�dito");
		}

		// indicador de Gera��o Autom�tica do Cr�dito � obrigat�rio.
		if (indicadorgeracaoAutomaica == null
				|| indicadorgeracaoAutomaica.equalsIgnoreCase("")) {
			throw new ActionServletException("atencao.required", null,
					"indicador de Gera��o Autom�tica do Cr�dito");
		}

		tipoCreditoInserir.setDescricao(descricao);
		tipoCreditoInserir.setDescricaoAbreviada(abreviatura);
		tipoCreditoInserir.setIndicadorGeracaoAutomatica(new Short(
				indicadorgeracaoAutomaica));
		valorLimiteCredito = valorLimiteCredito.replace(".","");
		valorLimiteCredito = valorLimiteCredito.replace(",",".");
		tipoCreditoInserir.setValorLimite(new BigDecimal(valorLimiteCredito));

		// Tipo do Lan�amento do Item Cont�bil
		LancamentoItemContabil lancamentoItemContabil = new LancamentoItemContabil();
		if (tipoLancamentoContabil != null
				&& !tipoLancamentoContabil.equalsIgnoreCase(String
						.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))) {

			FiltroLancamentoItemContabil filtroLancamentoItemContabil = new FiltroLancamentoItemContabil();

			filtroLancamentoItemContabil
					.adicionarParametro(new ParametroSimples(
							FiltroLancamentoItemContabil.ID,
							tipoLancamentoContabil));

			filtroLancamentoItemContabil
					.adicionarParametro(new ParametroSimples(
							FiltroLancamentoItemContabil.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			// Retorna Tipo do Lan�amento do Item Cont�bil
			colecaoPesquisa = fachada.pesquisar(filtroLancamentoItemContabil,
					LancamentoItemContabil.class.getName());

			if (colecaoPesquisa == null || colecaoPesquisa.isEmpty()) {

				throw new ActionServletException(
						"atencao.pesquisa_elo_nao_inexistente");
			} else {
				lancamentoItemContabil = (LancamentoItemContabil) Util
						.retonarObjetoDeColecao(colecaoPesquisa);
				if (lancamentoItemContabil.getId().intValue() != lancamentoItemContabil
						.getId().intValue()) {

					throw new ActionServletException(
							"atencao.localidade_nao_e_elo");
				} else {
					tipoCreditoInserir
							.setLancamentoItemContabil(lancamentoItemContabil);
				}
			}
		}

		CreditoTipo creditoTipo = new CreditoTipo();
		creditoTipo.setLancamentoItemContabil(lancamentoItemContabil);

		// Ultima altera��o
		tipoCreditoInserir.setUltimaAlteracao(new Date());

		// Pesquisa se a descri��o informada j� foi cadastrada
		FiltroCreditoTipo filtroCreditoTipo = new FiltroCreditoTipo();
		filtroCreditoTipo.adicionarParametro(new ParametroSimples(
				FiltroCreditoTipo.DESCRICAO, descricao));
		Collection colCreditoTipo = fachada.pesquisar(filtroCreditoTipo, CreditoTipo.class
				.getName());
		if (colCreditoTipo != null && !colCreditoTipo.isEmpty()) {
			throw new ActionServletException(
					"atencao.credito_tipo.descricao_ja_existente", null,
					descricao);
		}
		//*****************************************************
		
		// Pesquisa se a descri��o abreviada informada j� foi cadastrada
		filtroCreditoTipo = new FiltroCreditoTipo();
		filtroCreditoTipo.adicionarParametro(new ParametroSimples(
				FiltroCreditoTipo.DESCRICAO_ABREVIADA, abreviatura));
		colCreditoTipo = fachada.pesquisar(filtroCreditoTipo, CreditoTipo.class
				.getName());
		if (colCreditoTipo != null && !colCreditoTipo.isEmpty()) {
			throw new ActionServletException(
					"atencao.credito_tipo.abreviatura_ja_existente", null,
					abreviatura);
		}
		//	*****************************************************
		
		Integer idTipoCredito = null;
		
		tipoCreditoInserir.setIndicadorUso( 1 );

		idTipoCredito = fachada.inserirTipoCredito(tipoCreditoInserir, usuarioLogado);
		montarPaginaSucesso(httpServletRequest, "Tipo de Cr�dito de c�digo  "
				+ tipoCreditoInserir.getId() + " inserido com sucesso.",
				"Inserir outro Tipo de Cr�dito",
				"exibirInserirTipoCreditoAction.do?menu=sim",
				"exibirAtualizarTipoCreditoAction.do?idRegistroAtualizacao="
						+ idTipoCredito, "Atualizar Tipo de Cr�dito Inserido");

		// }

		// devolve o mapeamento de retorno
		return retorno;
	}

}
