package gcom.gui.atendimentopublico;

import gcom.arrecadacao.pagamento.Pagamento;
import gcom.atendimentopublico.registroatendimento.AtendimentoMotivoEncerramento;
import gcom.atendimentopublico.registroatendimento.AtendimentoRelacaoTipo;
import gcom.atendimentopublico.registroatendimento.RegistroAtendimento;
import gcom.atendimentopublico.registroatendimento.RegistroAtendimentoUnidade;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.bean.ContaValoresHelper;
import gcom.fachada.Fachada;
import gcom.faturamento.bean.CreditosHelper;
import gcom.faturamento.conta.Conta;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.Util;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Vivianne Sousa
 * @created 18/03/2011
 */
public class TransferirDevolucaoValoresPagosDuplicidadeAction extends GcomAction {
	/**
	 * [UC1131] Efetuar Devolu��o de Valores Pagos em Duplicidade
	 * 
	 * @author Vivianne Sousa
	 * @date 18/03/2011
	 * 
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		ActionForward retorno = actionMapping.findForward("telaSucesso");
		FiltrarRegistroAtendimentoDevolucaoValoresActionForm form = (FiltrarRegistroAtendimentoDevolucaoValoresActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);
		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");
		
		Collection colecaoContaASerRetificada = (Collection) sessao.getAttribute("colecaoContaASerRetificada");
		Collection colecaoCreditoASerTransferido = (Collection) sessao.getAttribute("colecaoCreditoASerTransferido");
		Collection colecaoCreditoARealizar = (Collection) sessao.getAttribute("colecaoCreditoARealizar");
		Collection colecaoPagamento = (Collection) sessao.getAttribute("colecaoPagamentosSelecionados");
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		
		String confirmado = httpServletRequest.getParameter("confirmado");
		if (confirmado == null || (!confirmado.trim().equalsIgnoreCase("ok") && !confirmado.trim().equalsIgnoreCase("cancelar"))) {
			
			RegistroAtendimento ra = new RegistroAtendimento();
			ra.setId(new Integer(form.getIdRAConsulta()));
			Integer idImovel = new Integer(form.getIdImovelSelecionado());
			
			Collection colecaoContas = fachada.transferirDevolucaoValoresPagosDuplicidade(
					colecaoContaASerRetificada,	colecaoCreditoARealizar, colecaoCreditoASerTransferido,
					colecaoPagamento, sistemaParametro, usuarioLogado, ra, idImovel);
			
			sessao.setAttribute("idsContaEP",colecaoContas);
			
//			if(colecaoContaASerRetificada != null && !colecaoContaASerRetificada.isEmpty()){
//				
//				Iterator iterContaASerRetificada = colecaoContaASerRetificada.iterator();
//				
//				while (iterContaASerRetificada.hasNext()) {
//					ContaValoresHelper helper = (ContaValoresHelper) iterContaASerRetificada.next();
//					Conta conta = helper.getConta();
//					Collection colecaoCreditoRealizadoInserir = new ArrayList();
//					
//					Iterator iterCreditoASerTransferido = colecaoCreditoASerTransferido.iterator();
//					while (iterCreditoASerTransferido.hasNext()) {
//						CreditosHelper creditosHelper = (CreditosHelper) iterCreditoASerTransferido.next();
//						if(creditosHelper.getIdContaCreditorealizado().equals(conta.getId())){
//							colecaoCreditoRealizadoInserir.add(creditosHelper);
//						}
//					}
//					retificarConta(conta, sistemaParametro,usuarioLogado, colecaoCreditoRealizadoInserir,sessao);
//				}
//
//			}
//			
//			RegistroAtendimento ra = new RegistroAtendimento();
//			ra.setId(new Integer(form.getIdRAConsulta()));
//			
//			Integer idImovel = new Integer(form.getIdImovelSelecionado());
//			
//			if(colecaoCreditoARealizar != null && !colecaoCreditoARealizar.isEmpty()){
//				
//				Iterator iterCreditoARealizar = colecaoCreditoARealizar.iterator();
//				while (iterCreditoARealizar.hasNext()) {
//					CreditosHelper helper = (CreditosHelper) iterCreditoARealizar.next();
//					
//					inserirCreditoARealizar(idImovel, helper, usuarioLogado,sistemaParametro,ra); 
//				}
//				
//			}
//
//			atualizarPagamento(colecaoPagamento,sistemaParametro,ra.getId());
			
			httpServletRequest.setAttribute("caminhoActionConclusao","/gsan/transferirDevolucaoValoresPagosDuplicidadeAction.do");

			httpServletRequest.setAttribute("cancelamento", "TRUE");
			httpServletRequest.setAttribute("nomeBotao1", "Sim");
			httpServletRequest.setAttribute("nomeBotao2", "N�o");
			sessao.setAttribute("confirmacao" , "sim");
			
			// Monta a p�gina de confirma��o para perguntar se o
			// usu�rio quer encerrar o RA
			return montarPaginaConfirmacao("atencao.enecrramento.ra.confirma",
				httpServletRequest, actionMapping, null);
			
		}else if(confirmado.equals("ok")){
			//Se entrou aqui, � porque o usuario clicou em Sim, para o encerramento do Registro de Atendimento, 
			//dessa forma, esse c�digo, trata todas as quest�es pertinentes
			//ao encerramento do registro e por fim, encerra o RA
			
			//Pega o registro de atendimento 
			RegistroAtendimento ra = new RegistroAtendimento();
			ra.setId(new Integer(form.getIdRAConsulta()));
			
			AtendimentoMotivoEncerramento atendimentoMotivoEncerramento = new AtendimentoMotivoEncerramento();
			atendimentoMotivoEncerramento.setId(AtendimentoMotivoEncerramento.CONCLUSAO_SERVICO);
			atendimentoMotivoEncerramento.setIndicadorExecucao(AtendimentoMotivoEncerramento.INDICADOR_EXECUCAO_SIM);
			
			ra.setAtendimentoMotivoEncerramento(atendimentoMotivoEncerramento);
			ra.setCodigoSituacao(RegistroAtendimento.SITUACAO_ENCERRADO);
			
			ra.setDataEncerramento(new Date());
			
			ra.setParecerEncerramento(obterParecerEncerramento(colecaoPagamento,
					colecaoCreditoASerTransferido,colecaoCreditoARealizar,colecaoContaASerRetificada));
			
			ra.setUltimaAlteracao(new Date());
			
			//cria o objeto para a inser��o do registro de atendimento unidade
			RegistroAtendimentoUnidade registroAtendimentoUnidade = new RegistroAtendimentoUnidade();
			registroAtendimentoUnidade.setRegistroAtendimento(ra);
			
			if (usuarioLogado.getUnidadeOrganizacional() != null && 
					!usuarioLogado.getUnidadeOrganizacional().equals("")) {
				
				registroAtendimentoUnidade.setUnidadeOrganizacional(
						usuarioLogado.getUnidadeOrganizacional());
			}
			registroAtendimentoUnidade.setUsuario(usuarioLogado);
			registroAtendimentoUnidade.setUltimaAlteracao(new Date());
			
			AtendimentoRelacaoTipo atendimentoRelacaoTipo = new AtendimentoRelacaoTipo();
			atendimentoRelacaoTipo.setId(AtendimentoRelacaoTipo.ENCERRAR);
			
			registroAtendimentoUnidade.setAtendimentoRelacaoTipo(atendimentoRelacaoTipo);
			
			//booleano false para n�o gerar um novo RA
			Boolean confirmadoGeracaoNovoRA = false;
			
			//encerra o RA		
			fachada.encerrarRegistroAtendimento(
						ra,
						registroAtendimentoUnidade, 
						usuarioLogado, 
						null, 
						null, 
						null, 
						null, 
						confirmadoGeracaoNovoRA,null,false);					
			
		}
		
		String msgSucesso = "";
		if(colecaoContaASerRetificada != null && !colecaoContaASerRetificada.isEmpty()){
			
			if(colecaoCreditoARealizar != null && !colecaoCreditoARealizar.isEmpty()){
				msgSucesso = "Pagamento(s) duplicado(s) transferido(s)  e cr�dito a realizar gerado com sucesso";
			}else{
				msgSucesso = "Pagamento(s) duplicado(s) transferido(s)  com sucesso";
			}
		}else if(colecaoCreditoARealizar != null && !colecaoCreditoARealizar.isEmpty()){
			msgSucesso = "Cr�dito a realizar gerado com sucesso";
		}
		
		
		montarPaginaSucesso(httpServletRequest, msgSucesso,
				  "Imprimir Contas Retificadas" ,"gerarRelatorio2ViaContaAction.do?cobrarTaxaEmissaoConta=N",
				  "exibirFiltrarRegistroAtendimentoDevolucaoValoresAction.do?menu=s", "Fazer outra transfer�ncia de pagamento.");

		return retorno;
	}

	//[SB0005] � Formatar dados para Retifica��o da Conta. 
//	private void retificarConta(Conta conta, SistemaParametro sistemaParametro,
//			Usuario usuarioLogado,Collection colecaoCreditoRealizadoInserir,HttpSession sessao){
//		
//		Collection colecaoCategoriaOUSubcategoria = null;
//	
//		if (sistemaParametro.getIndicadorTarifaCategoria().equals(SistemaParametro.INDICADOR_TARIFA_SUBCATEGORIA)) {
//
//			colecaoCategoriaOUSubcategoria = Fachada.getInstancia()
//					.obterQuantidadeEconomiasContaCategoriaPorSubcategoria(conta);
//
//		} else {
//
//			colecaoCategoriaOUSubcategoria = Fachada.getInstancia()
//					.obterQuantidadeEconomiasContaCategoria(conta);
//
//		}
//		
//		Collection colecaoCreditoRealizado = Fachada.getInstancia().obterCreditosRealizadosConta(conta);
//		colecaoCreditoRealizado = adicionarCreditoRealizado(colecaoCreditoRealizado ,colecaoCreditoRealizadoInserir) ;
//		
//		Collection colecaoDebitoCobrado = Fachada.getInstancia().obterDebitosCobradosConta(conta);
//
//		Conta contaParaRetificacao =  Fachada.getInstancia().pesquisarContaRetificacao(conta.getId());
//		
//		//[UC0120] - Calcular Valores de �gua e/ou Esgoto
//		Collection<CalcularValoresAguaEsgotoHelper> valoresConta = 
//			Fachada.getInstancia().calcularValoresConta(
//			Util.formatarAnoMesParaMesAno(conta.getReferencia()), 
//			contaParaRetificacao.getImovel().getId().toString(), 
//			contaParaRetificacao.getLigacaoAguaSituacao().getId(), 
//			contaParaRetificacao.getLigacaoEsgotoSituacao().getId(), 
//			colecaoCategoriaOUSubcategoria, 
//			contaParaRetificacao.getConsumoAgua().toString(), 
//			contaParaRetificacao.getConsumoEsgoto().toString(), 
//			contaParaRetificacao.getPercentualEsgoto().toString(), 
//			contaParaRetificacao.getConsumoTarifa().getId(), usuarioLogado);
//		
//		//[UC0150] - Retificar Conta
//		
//		ContaMotivoRetificacao contaMotivoRetificacao = new ContaMotivoRetificacao();
//		contaMotivoRetificacao.setId(ContaMotivoRetificacao.DEVOLUCAO_CREDITADA_EM_CONTA);
//		
//		Integer idConta = Fachada.getInstancia().retificarConta(
//				contaParaRetificacao.getReferencia(), 
//				contaParaRetificacao, 
//				contaParaRetificacao.getImovel(), 
//				colecaoDebitoCobrado,
//				colecaoCreditoRealizado, 
//				contaParaRetificacao.getLigacaoAguaSituacao(), 
//				contaParaRetificacao.getLigacaoEsgotoSituacao(),
//				colecaoCategoriaOUSubcategoria, 
//				contaParaRetificacao.getConsumoAgua().toString(), 
//				contaParaRetificacao.getConsumoEsgoto().toString(),
//				contaParaRetificacao.getPercentualEsgoto().toString(), 
//				contaParaRetificacao.getDataVencimentoConta(), 
//				valoresConta, 
//				contaMotivoRetificacao, 
//				null, 
//				usuarioLogado, 
//				contaParaRetificacao.getConsumoTarifa().getId().toString(),
//				false,null,null,false, null,null,null,null,null);
//		
//		Collection colecaoContas = null;
//		if(sessao.getAttribute("idsContaEP") != null){
//			colecaoContas = (Collection)sessao.getAttribute("idsContaEP");
//		}else{
//			colecaoContas = new ArrayList();
//		}
//		colecaoContas.add(idConta);
//		sessao.setAttribute("idsContaEP",colecaoContas);
//	}
//
//	private void inserirCreditoARealizar(Integer idImovel, CreditosHelper helper, Usuario usuarioLogado,
//			SistemaParametro sistemaParametro, RegistroAtendimento ra) {
//
//		CreditoARealizar creditoARealizar = new CreditoARealizar();
//		
//		Imovel imovel  =  Fachada.getInstancia().pesquisarImovelDigitado(idImovel);
//		creditoARealizar.setImovel(imovel);
//		
//		creditoARealizar.setValorCredito(helper.getValorCredito());
//		
//		creditoARealizar.setAnoMesReferenciaCredito(helper.getReferenciaCredito());
//		
//		creditoARealizar.setRegistroAtendimento(ra);
//		
//		// Cr�dito Tipo
//		creditoARealizar.setCreditoTipo(helper.getTipoCreditoID());
//		
//		// Cr�dito Origem
//		creditoARealizar.setCreditoOrigem(helper.getOrigemCreditoID());
//		
//		LancamentoItemContabil lict = new LancamentoItemContabil();
//		lict.setId(LancamentoItemContabil.OUTROS_SERVICOS_AGUA);
//		creditoARealizar.setLancamentoItemContabil(lict);
//		
//		creditoARealizar.setUsuario(usuarioLogado);
//		
//		
//		// [FS0011] - Validar Refer�ncia do Cr�dito
////		FiltroCreditoARealizar filtroCreditoARealizar = new FiltroCreditoARealizar();
////		filtroCreditoARealizar.adicionarParametro(new ParametroSimples(
////				FiltroCreditoARealizar.ANO_MES_REFERENCIA_CREDITO,creditoARealizar.getAnoMesReferenciaCredito()));
////		filtroCreditoARealizar.adicionarParametro(new ParametroSimples(
////				FiltroCreditoARealizar.ID_CREDITO_ORIGEM, creditoARealizar.getCreditoOrigem().getId()));
////		filtroCreditoARealizar.adicionarParametro(new ParametroSimples(
////				FiltroCreditoARealizar.ID_CREDITO_TIPO, creditoARealizar.getCreditoTipo().getId()));
////		filtroCreditoARealizar.adicionarParametro(new ParametroSimples(
////				FiltroCreditoARealizar.IMOVEL_ID, creditoARealizar.getImovel().getId()));
////		
////		Collection colecaoCreditosBase = Fachada.getInstancia().pesquisar(
////				filtroCreditoARealizar, CreditoARealizar.class.getName());
////		
////		if (colecaoCreditosBase != null && !colecaoCreditosBase.isEmpty()) {
////			throw new ControladorException("atencao.referencia.credito_a_realizar.ja.existente");
////		}
//		
//		// Data de Gera��o do Cr�dito
//		creditoARealizar.setGeracaoCredito(new Date());
//		
//		Date dataAtual = new Date();
//		Integer anoMesReferenciaAtual = Util.recuperaAnoMesDaData( dataAtual );
//		
//		creditoARealizar.setAnoMesReferenciaContabil( 
//				
//				( anoMesReferenciaAtual > sistemaParametro.getAnoMesFaturamento() ? anoMesReferenciaAtual : sistemaParametro.getAnoMesFaturamento() )
//				
//			);				
//		
//		creditoARealizar.setAnoMesCobrancaCredito(sistemaParametro.getAnoMesArrecadacao());
//		
//		// Valor Residual Mes Anterior
//		creditoARealizar.setValorResidualMesAnterior(new BigDecimal(0));
//		
//		// Prestacao Credito
//		creditoARealizar.setNumeroPrestacaoCredito(new Short("1"));
//		
//		// Prestacao Realizada
//		creditoARealizar.setNumeroPrestacaoRealizada(new Short((short) 0));
//		
//		// Imovel
//		creditoARealizar.setImovel(imovel);
//		creditoARealizar.setLocalidade(imovel.getLocalidade());
//		creditoARealizar.setCodigoSetorComercial(imovel.getSetorComercial().getCodigo());
//		creditoARealizar.setNumeroLote(imovel.getLote());
//		creditoARealizar.setNumeroSubLote(imovel.getSubLote());
//		creditoARealizar.setQuadra(imovel.getQuadra());
//		creditoARealizar.setNumeroQuadra(new Integer(imovel.getQuadra().getNumeroQuadra()));
//		
//		// Ordem de Servico
//		creditoARealizar.setOrdemServico(null);
//		
//		// Debito Credito Situacao Atual
//		DebitoCreditoSituacao debitoCreditoSituacaoAtual = new DebitoCreditoSituacao();
//		debitoCreditoSituacaoAtual.setId(DebitoCreditoSituacao.NORMAL);
//		creditoARealizar.setDebitoCreditoSituacaoAtual(debitoCreditoSituacaoAtual);
//		
//		// Debito Credito Situacao Anterior
//		creditoARealizar.setDebitoCreditoSituacaoAnterior(null);
//		
//		// Data de Ultima Alteracao
//		creditoARealizar.setUltimaAlteracao(new Date());
//		
//		//GERANDO O CREDITO A REALIZAR
//		Fachada.getInstancia().gerarCreditoARealizar(creditoARealizar, imovel, usuarioLogado);
//	}
//	
//	private void atualizarPagamento(Collection colecaoPagamento,SistemaParametro sistemaParametro, Integer idRa){
//		
//		Integer refereciaArrecadacaoSP = sistemaParametro.getAnoMesArrecadacao();
//		PagamentoSituacao pagamentoSituacao = new PagamentoSituacao();
//		pagamentoSituacao.setId(PagamentoSituacao.DUPLICIDADE_EXCESSO_DEVOLVIDO);
//		Iterator iterPagamento = colecaoPagamento.iterator();
//		
//		while (iterPagamento.hasNext()) {
//			Pagamento pagamento = (Pagamento) iterPagamento.next();
//			
//			if(refereciaArrecadacaoSP.equals(pagamento.getAnoMesReferenciaArrecadacao())){
//				pagamento.setPagamentoSituacaoAnterior(pagamento.getPagamentoSituacaoAtual());
//			}
//			
//			pagamento.setPagamentoSituacaoAtual(pagamentoSituacao);
//			pagamento.setUltimaAlteracao(new Date());
//			
//			Fachada.getInstancia().atualizar(pagamento);
//			
//			Fachada.getInstancia().atualizarRegistroAtendimentoPagamentoDuplicidade(idRa,pagamento.getId());
//		}
//		
//	}
//	
	private String obterParecerEncerramento(Collection colecaoPagamento,
			Collection colecaoCreditoASerTransferido,Collection colecaoCreditoARealizar,Collection colecaoContaASerRetificada){
		
		BigDecimal valorCreditoRealizado = ConstantesSistema.VALOR_ZERO;		
		BigDecimal valorCreditoARealizar = ConstantesSistema.VALOR_ZERO;	
		String referenciaPagamentos = "";
//		String referenciaCreditosRealizado = "";
		String referenciaContas = "";
		

		Iterator iterPagamento = colecaoPagamento.iterator();
		while (iterPagamento.hasNext()) {
			Pagamento pagamento = (Pagamento) iterPagamento.next();
			String referencia = "";
			if(pagamento.getContaGeral().getConta() != null){
				
				referencia = Util.formatarAnoMesParaMesAno(pagamento.getContaGeral().getConta().getReferencia());
				
			}else if(pagamento.getContaGeral().getContaHistorico() != null){
				referencia = Util.formatarAnoMesParaMesAno(pagamento.getContaGeral().getContaHistorico().getAnoMesReferenciaConta());
			}
			
			String[] referencias = referenciaPagamentos.split(",");
			boolean existeNaColecao = false;
			for(int x=0; x<referencias.length; x++){

				if (referencia.equals(referencias[x])){
					existeNaColecao = true;
				}
			}
			if(!existeNaColecao){
				referenciaPagamentos = referenciaPagamentos + referencia + ",";
			}
		
		}
		
		Iterator iterCreditoASerTransferido = colecaoCreditoASerTransferido.iterator();
		while (iterCreditoASerTransferido.hasNext()) {
			CreditosHelper helper = (CreditosHelper) iterCreditoASerTransferido.next();
			
			valorCreditoRealizado = valorCreditoRealizado.add(helper.getValorCredito());
//			referenciaCreditosRealizado = referenciaCreditosRealizado + Util.formatarAnoMesParaMesAno(helper.getReferenciaCredito()) + ",";
		}
		
		Iterator iterCreditoARealizar = colecaoCreditoARealizar.iterator();
		while (iterCreditoARealizar.hasNext()) {
			CreditosHelper helper = (CreditosHelper) iterCreditoARealizar.next();
			
			valorCreditoARealizar = valorCreditoARealizar.add(helper.getValorCredito());
		}
		
		Iterator iterContas = colecaoContaASerRetificada.iterator();
		while (iterContas.hasNext()) {
			ContaValoresHelper helper = (ContaValoresHelper) iterContas.next();
			Conta conta = helper.getConta();
			
			referenciaContas = referenciaContas + Util.formatarAnoMesParaMesAno(conta.getReferencia()) + ",";
			
		}
		
		String msgParecer = "";
		if(!referenciaPagamentos.equals("") && !valorCreditoRealizado.equals(ConstantesSistema.VALOR_ZERO)){
			
			referenciaPagamentos = Util.removerUltimosCaracteres(referenciaPagamentos, 1);
			referenciaContas = Util.removerUltimosCaracteres(referenciaContas, 1);
			
			msgParecer = "Devolvido o valor de " + Util.formatarMoedaReal(valorCreditoRealizado)  + " referente ao(s) pagamento(s) " +
			"em duplicidade da(s) fatura(s) " + referenciaPagamentos   + " com cr�dito(s) " +
					"lan�ado(s) na(s) fatura(s) " +  referenciaContas ;
			
			if(!valorCreditoARealizar.equals(ConstantesSistema.VALOR_ZERO)){
				msgParecer = msgParecer + " e saldo restante de " + Util.formatarMoedaReal(valorCreditoARealizar) + " lan�ado como cr�dito a realizar";
			}
			
		}else if(!referenciaPagamentos.equals("") && !valorCreditoARealizar.equals(ConstantesSistema.VALOR_ZERO)){

			referenciaPagamentos = Util.removerUltimosCaracteres(referenciaPagamentos, 1);
			msgParecer = "Devolvido o valor de " + Util.formatarMoedaReal(valorCreditoARealizar)  + " referente ao(s) pagamento(s) " +
			"em duplicidade da(s) fatura(s) " + referenciaPagamentos   + " lan�ado como cr�dito a realizar " ;
		}
		
		if(msgParecer.length() > 1000){
			msgParecer = msgParecer.substring(0, 999);
		}

		return msgParecer;
	}
//	
//	
//
//	private Collection adicionarCreditoRealizado(Collection colecaoCreditoRealizado ,
//			Collection colecaoCreditoRealizadoInserir) {
//		
//		if(colecaoCreditoRealizado == null || colecaoCreditoRealizado.isEmpty()){
//			colecaoCreditoRealizado = new ArrayList();
//		}
//		
//		if(colecaoCreditoRealizadoInserir != null && !colecaoCreditoRealizadoInserir.isEmpty()){
//
//			Iterator iterCreditosHelper = colecaoCreditoRealizadoInserir.iterator();
//			
//			while (iterCreditosHelper.hasNext()) {
//				CreditosHelper helper = (CreditosHelper) iterCreditosHelper.next();
//				
//				CreditoRealizado creditoRealizado = new CreditoRealizado();
//				creditoRealizado.setCreditoTipo(helper.getTipoCreditoID());
//				creditoRealizado.setCreditoOrigem(helper.getOrigemCreditoID());
//				creditoRealizado.setValorCredito(helper.getValorCredito());
//				creditoRealizado.setAnoMesCobrancaCredito(helper.getReferenciaCredito());
//				creditoRealizado.setAnoMesReferenciaCredito(helper.getReferenciaCredito());
//				creditoRealizado.setNumeroPrestacao(new Short("0"));
//				creditoRealizado.setNumeroPrestacaoCredito(new Short("0"));
//				colecaoCreditoRealizado.add(creditoRealizado);
//			}
//			
//		}
//		return colecaoCreditoRealizado;
//	}
//	
}
