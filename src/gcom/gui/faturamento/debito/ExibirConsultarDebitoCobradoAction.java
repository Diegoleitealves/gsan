package gcom.gui.faturamento.debito;

import gcom.fachada.Fachada;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.conta.ContaHistorico;
import gcom.faturamento.conta.FiltroContaHistorico;
import gcom.faturamento.debito.DebitoCobrado;
import gcom.faturamento.debito.DebitoCobradoHistorico;
import gcom.faturamento.debito.FiltroDebitoCobrado;
import gcom.faturamento.debito.FiltroDebitoCobradoHistorico;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.relatorio.cadastro.imovel.RelatorioDebitoCobradoContaHelper;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * action respons�vel pela exibi��o da tela de consultar d�bito cobrado
 * 
 * @author pedro alexandre
 * @created 04 de Janeiro de 2006
 */
public class ExibirConsultarDebitoCobradoAction extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		// cria a vari�vel de retorno e seta o mapeamento para a tela de
		// consultar d�bito cobrado
		ActionForward retorno = actionMapping
				.findForward("exibirConsultarDebitoCobrado");

		// cria uma inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		// cria uma inst�ncia da sess�o
		HttpSession sessao = httpServletRequest.getSession(false);

		String tipoConsulta = httpServletRequest.getParameter("tipoConsulta");
		
		RelatorioDebitoCobradoContaHelper relatorioDebitosDeUmaConta = new RelatorioDebitoCobradoContaHelper();

		if (tipoConsulta.equalsIgnoreCase("conta")) {

			// recupera o c�digo da conta do request
			String idConta = httpServletRequest.getParameter("contaID");

			// se o c�digo n�o for nulo
			if (idConta != null && !idConta.equalsIgnoreCase("")) {
				// remove o objeto conta da sess�o
				sessao.removeAttribute("conta");

				// remove o objeto conta da sess�o
				sessao.removeAttribute("contaHistorico");

				// remove a cole��o de categorias
				sessao.removeAttribute("colecaoContaCategoria");

				// remove a cole��o de impostos deduzidos
				sessao.removeAttribute("colecaoContaImpostosDeduzidos");

				// remove a cole��o de d�bitos cobrados
				sessao.removeAttribute("colecaoContaDebitosCobrados");
			}

			/*
			 * Pesquisando a conta a partir do id recebido
			 * =====================================================================
			 */

			// cria o objeto conta
			Conta conta = null;

			// se o c�digo da conta n�o for nulo
			if (idConta != null && !idConta.equalsIgnoreCase("")) {

				// cria o filtro da conta
				/*
				 * FiltroConta filtroConta = new FiltroConta();
				 * 
				 * //carrega o im�vel
				 * filtroConta.adicionarCaminhoParaCarregamentoEntidade("imovel");
				 * 
				 * //carrega a situa��o da conta
				 * filtroConta.adicionarCaminhoParaCarregamentoEntidade("debitoCreditoSituacaoAtual");
				 * 
				 * //carrega a situa��o da liga��o de �gua
				 * filtroConta.adicionarCaminhoParaCarregamentoEntidade("ligacaoAguaSituacao");
				 * 
				 * //carrega a a situa��o de esgoto
				 * filtroConta.adicionarCaminhoParaCarregamentoEntidade("ligacaoEsgotoSituacao");
				 * 
				 * //seta o c�digo da conta no filtro
				 * filtroConta.adicionarParametro(new
				 * ParametroSimples(FiltroConta.ID, idConta));
				 * 
				 * //pesquisa a conta na base de dados Collection colecaoConta =
				 * fachada.pesquisar(filtroConta, Conta.class.getName());
				 */

				Collection colecaoConta = fachada.consultarConta(new Integer(
						idConta));
				// se n�o encontrou nenhuma conta com o c�digo informado
				if (colecaoConta == null || colecaoConta.isEmpty()) {
					throw new ActionServletException(
							"atencao.pesquisa.conta.inexistente");
				}
				// recupera o objeto conta da cole��o
				conta = (Conta) Util.retonarObjetoDeColecao(colecaoConta);
				
				relatorioDebitosDeUmaConta.setIdImovel(conta.getImovel().getId()+"");
				relatorioDebitosDeUmaConta.setMesAnoConta(conta.getFormatarAnoMesParaMesAno());
				relatorioDebitosDeUmaConta.setSituacaoConta(conta.getDebitoCreditoSituacaoAtual().getDescricaoDebitoCreditoSituacao());
				relatorioDebitosDeUmaConta.setSituacaoLigacaoAgua(conta.getLigacaoAguaSituacao().getDescricao());
				relatorioDebitosDeUmaConta.setSituacaoLigacaoEsgoto(conta.getLigacaoEsgotoSituacao().getDescricao());
								// seta o objeto conta na sess�o
				sessao.setAttribute("conta", conta);
			}
			// se j� existe uma conta na sess�o
			else if (sessao.getAttribute("conta") != null) {
				// recupera a conta da sess�o
				conta = (Conta) sessao.getAttribute("conta");
				
				if(relatorioDebitosDeUmaConta.getIdImovel() == null){
					relatorioDebitosDeUmaConta.setIdImovel(conta.getImovel().getId()+"");
				}
			} else {
				// levanta o erro de conta inexistente
				throw new ActionServletException(
						"atencao.pesquisa.conta.inexistente");
			}
			// ====================================================================

			// cria a cole��o de d�bitos cobrados
			Collection colecaoContaDebitosCobrados;

			/*
			 * D�bitos Cobrados (Carregar cole��o)
			 * ======================================================================
			 */

			// cria o filtro de d�bito cobrado
			FiltroDebitoCobrado filtroDebitoCobrado = new FiltroDebitoCobrado();

			// carrega o tipo de d�bito
			filtroDebitoCobrado
					.adicionarCaminhoParaCarregamentoEntidade("debitoTipo");

			// seta o c�digo da conta no filtro
			filtroDebitoCobrado.adicionarParametro(new ParametroSimples(
					FiltroDebitoCobrado.CONTA_ID, conta.getId()));

			// pesquisa a cole��o de d�bitos cobrados
			colecaoContaDebitosCobrados = fachada.pesquisar(
					filtroDebitoCobrado, DebitoCobrado.class.getName());
			
			

			// seta a cole��o de d�bitos cobrados na sess�o
			sessao.setAttribute("colecaoContaDebitosCobrados",
					colecaoContaDebitosCobrados);
			relatorioDebitosDeUmaConta.setColecaoDebitosCobrados(colecaoContaDebitosCobrados);
			sessao.removeAttribute("contaHistorico");
			sessao.removeAttribute("colecaoContaDebitosCobradosHistorico");

		} else if (tipoConsulta.equalsIgnoreCase("contaHistorico")) {

			String idContaHistorico = httpServletRequest
					.getParameter("contaID");

			// se o c�digo n�o for nulo
			if (idContaHistorico != null
					&& !idContaHistorico.equalsIgnoreCase("")) {

				// remove o objeto conta da sess�o
				sessao.removeAttribute("conta");

				// remove o objeto conta hist�rico da sess�o
				sessao.removeAttribute("contaHistorico");

				// remove a cole��o de categorias
				sessao.removeAttribute("colecaoContaCategoria");

				// remove a cole��o de impostos deduzidos
				sessao.removeAttribute("colecaoContaImpostosDeduzidos");

				// remove a cole��o de d�bitos cobrados
				sessao.removeAttribute("colecaoContaDebitosCobrados");
			}

			/*
			 * Pesquisando a conta hist�rico a partir do id recebido
			 * =====================================================================
			 */

			// cria o objeto conta hist�rico
			ContaHistorico contaHistorico = null;

			// se o c�digo da conta hist�rico n�o for nulo
			if (idContaHistorico != null
					&& !idContaHistorico.equalsIgnoreCase("")) {

				// cria o filtro da conta hist�rico
				FiltroContaHistorico filtroContaHistorico = new FiltroContaHistorico();

				// carrega o im�vel
				filtroContaHistorico
						.adicionarCaminhoParaCarregamentoEntidade("imovel");

				// carrega a situa��o da conta
				filtroContaHistorico
						.adicionarCaminhoParaCarregamentoEntidade("debitoCreditoSituacaoAtual");

				// carrega a situa��o da liga��o de �gua
				filtroContaHistorico
						.adicionarCaminhoParaCarregamentoEntidade("ligacaoAguaSituacao");

				// carrega a situa��o de esgoto
				filtroContaHistorico
						.adicionarCaminhoParaCarregamentoEntidade("ligacaoEsgotoSituacao");

				// seta o c�digo da conta hist�rico no filtro
				filtroContaHistorico.adicionarParametro(new ParametroSimples(
						FiltroContaHistorico.ID, idContaHistorico));

				// pesquisa a conta hist�rico na base de dados
				Collection colecaoContaHistorico = fachada.pesquisar(
						filtroContaHistorico, ContaHistorico.class.getName());

				// se n�o encontrou nenhuma conta hist�rico com o c�digo
				// informado
				if (colecaoContaHistorico == null
						|| colecaoContaHistorico.isEmpty()) {
					throw new ActionServletException(
							"atencao.pesquisa.conta.inexistente");
				}

				// recupera o objeto conta hist�rico da cole��o
				contaHistorico = (ContaHistorico) Util
						.retonarObjetoDeColecao(colecaoContaHistorico);

				// seta o objeto conta historico na sess�o
				sessao.setAttribute("contaHistorico", contaHistorico);
				relatorioDebitosDeUmaConta.setIdImovel(contaHistorico.getImovel().getId()+"");
				relatorioDebitosDeUmaConta.setMesAnoConta(contaHistorico.getFormatarAnoMesParaMesAno());
				relatorioDebitosDeUmaConta.setSituacaoConta(contaHistorico.getDebitoCreditoSituacaoAtual().getDescricaoDebitoCreditoSituacao()+"");
				relatorioDebitosDeUmaConta.setSituacaoLigacaoAgua(contaHistorico.getLigacaoAguaSituacao().getDescricao());
				relatorioDebitosDeUmaConta.setSituacaoLigacaoEsgoto(contaHistorico.getLigacaoEsgotoSituacao().getDescricao());				
				sessao.removeAttribute("conta");
				sessao.removeAttribute("colecaoContaDebitosCobrados");

			}
			// se j� existe uma conta na sess�o
			else if (sessao.getAttribute("contaHistorico") != null) {
				// recupera a conta da sess�o
				contaHistorico = (ContaHistorico) sessao
						.getAttribute("contaHistorico");
			} else {
				// levanta o erro de conta inexistente
				throw new ActionServletException(
						"atencao.pesquisa.conta.inexistente");
			}
			// ====================================================================

			// cria a cole��o de d�bitos cobrados hist�rico
			Collection colecaoContaDebitosCobradosHistorico;

			/*
			 * D�bitos Cobrados (Carregar cole��o)
			 * ======================================================================
			 */

			// cria o filtro de d�bito cobrado
			FiltroDebitoCobradoHistorico filtroDebitoCobradoHistorico = new FiltroDebitoCobradoHistorico();

			// carrega o tipo de d�bito
			filtroDebitoCobradoHistorico
					.adicionarCaminhoParaCarregamentoEntidade("debitoTipo");

			// seta o c�digo da conta hist�rico no filtro
			filtroDebitoCobradoHistorico
					.adicionarParametro(new ParametroSimples(
							FiltroDebitoCobradoHistorico.CONTA_HISTORICO_ID,
							contaHistorico.getId()));

			// pesquisa a cole��o de d�bitos cobrados
			colecaoContaDebitosCobradosHistorico = fachada.pesquisar(
					filtroDebitoCobradoHistorico, DebitoCobradoHistorico.class
							.getName());

			// seta a cole��o de d�bitos cobrados hist�rico na sess�o
			sessao.setAttribute("colecaoContaDebitosCobradosHistorico",
					colecaoContaDebitosCobradosHistorico);
			
			relatorioDebitosDeUmaConta.setColecaoDebitosCobradosHistorico(colecaoContaDebitosCobradosHistorico);

		}
		
		sessao.setAttribute("relatorioHelper",relatorioDebitosDeUmaConta);
		
		// envia uma flag que ser� verificado no cliente_resultado_pesquisa.jsp
		// para saber se ir� usar o enviar dados ou o enviar dados parametros
		if (httpServletRequest.getParameter("caminhoRetornoTelaConsultaDebitos") != null) {
			sessao.setAttribute("caminhoRetornoTelaConsultaDebitos",
					httpServletRequest
							.getParameter("caminhoRetornoTelaConsultaDebitos"));
		}
		// retorna o mapeamento contido na vari�vel retorno
		return retorno;
	}
}
