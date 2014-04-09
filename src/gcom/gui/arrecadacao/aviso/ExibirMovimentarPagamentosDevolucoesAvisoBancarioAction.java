package gcom.gui.arrecadacao.aviso;

import gcom.arrecadacao.FiltroDevolucao;
import gcom.arrecadacao.aviso.bean.MovimentarPagamentosDevolucoesHelper;
import gcom.arrecadacao.aviso.bean.PagamentosDevolucoesHelper;
import gcom.arrecadacao.aviso.bean.ValoresArrecadacaoDevolucaoAvisoBancarioHelper;
import gcom.arrecadacao.pagamento.FiltroPagamento;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.Util;

import java.math.BigDecimal;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC0611] Movimentar Pagamentos/ Devolu��es entre Avisos Banc�rios
 * 
 * @author Ana Maria
 * 
 * @date 11/06/2007
 */

public class ExibirMovimentarPagamentosDevolucoesAvisoBancarioAction extends
		GcomAction {

	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		ActionForward retorno = actionMapping
				.findForward("exibirMovimentarPagamentosDevolucoesAvisoBancario");

		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();

		MovimentarPagamentosDevolucoesAvisoBancarioActionForm form = (MovimentarPagamentosDevolucoesAvisoBancarioActionForm) actionForm;

		if(httpServletRequest.getParameter("removerPagamentos") == null && httpServletRequest.getParameter("removerDevolucoes") == null){
			PagamentosDevolucoesHelper pagamentoHelper = null;
			PagamentosDevolucoesHelper devolucaoHelper = null;
	
			if (sessao.getAttribute("filtroPagamento") != null) {
				FiltroPagamento filtroPagamento = null;
				filtroPagamento = (FiltroPagamento) sessao.getAttribute("filtroPagamento");
	
				pagamentoHelper = fachada.filtrarPagamentos(filtroPagamento);
			}
	
			if (sessao.getAttribute("filtroDevolucao") != null) {
				FiltroDevolucao filtroDevolucao = null;
				filtroDevolucao = (FiltroDevolucao) sessao.getAttribute("filtroDevolucao");
	
				devolucaoHelper = fachada.filtrarDevolucoes(filtroDevolucao);
			}
	
			if (pagamentoHelper == null && devolucaoHelper == null) {
				throw new ActionServletException(
						"atencao.aviso_bancario_origem_sem_pagamentos_devolucoes");
			}
	
			//Aviso Banc�rio Origem
			if (sessao.getAttribute("avisoBancarioO") != null) {
				Integer idAvisoBancarioO = (Integer) sessao.getAttribute("avisoBancarioO");
				ValoresArrecadacaoDevolucaoAvisoBancarioHelper helper = fachada.pesquisarValoresAvisoBancario(idAvisoBancarioO);
	
				if (helper != null && !helper.equals("")) {
					//Situa��o Antes
					form.setArrecadacaoInformadoAntesOrigem(Util
							.formatarMoedaReal(helper.getValorArrecadacaoInformado()));
					form.setArrecadacaoCalculadoAntesOrigem(Util
							.formatarMoedaReal(helper.getValorArrecadacaoCalculado()));
					form.setDevolucaoInformadoAntesOrigem(Util
							.formatarMoedaReal(helper.getValorDevolucaoInformado()));
					form.setDevolucaoCalculadoAntesOrigem(Util
							.formatarMoedaReal(helper.getValorDevolucaoCalculado()));
					
					//Situa��o Depois
					if (pagamentoHelper != null) {
						form.setArrecadacaoInformadoDepoisOrigem(Util
							.formatarMoedaReal(helper.getValorArrecadacaoInformado().subtract(
													Util.formatarMoedaRealparaBigDecimal(pagamentoHelper.getValorTotalPagamentos()))));

						form.setArrecadacaoCalculadoDepoisOrigem(Util
							.formatarMoedaReal(helper.getValorArrecadacaoCalculado().subtract(
													Util.formatarMoedaRealparaBigDecimal(pagamentoHelper.getValorTotalPagamentos()))));
					} else {
						form.setArrecadacaoInformadoDepoisOrigem(Util
							.formatarMoedaReal(helper.getValorArrecadacaoInformado()));
						form.setArrecadacaoCalculadoDepoisOrigem(Util
							.formatarMoedaReal(helper.getValorArrecadacaoCalculado()));
					}
					if (devolucaoHelper != null) {
						form.setDevolucaoInformadoDepoisOrigem(Util
							.formatarMoedaReal(helper.getValorDevolucaoInformado().subtract(
													Util.formatarMoedaRealparaBigDecimal(devolucaoHelper.getValorTotalDevolucoes()))));

						form.setDevolucaoCalculadoDepoisOrigem(Util
							.formatarMoedaReal(helper.getValorDevolucaoCalculado().subtract(
													Util.formatarMoedaRealparaBigDecimal(devolucaoHelper.getValorTotalDevolucoes()))));
					} else {
						form.setDevolucaoInformadoDepoisOrigem(Util
							.formatarMoedaReal(helper.getValorDevolucaoInformado()));
						form.setDevolucaoCalculadoDepoisOrigem(Util
							.formatarMoedaReal(helper.getValorDevolucaoCalculado()));
					}
				}
			}
	
			//Aviso Banc�rio Destino
			if (sessao.getAttribute("avisoBancarioD") != null) {
				Integer idAvisoBancarioD = (Integer) sessao.getAttribute("avisoBancarioD");
				ValoresArrecadacaoDevolucaoAvisoBancarioHelper helper = fachada.pesquisarValoresAvisoBancario(idAvisoBancarioD);
	
				if (helper != null && !helper.equals("")) {
					//Situa��o Antes
					form.setArrecadacaoInformadoAntesDestino(Util
						    .formatarMoedaReal(helper.getValorArrecadacaoInformado()));
					form.setArrecadacaoCalculadoAntesDestino(Util
						    .formatarMoedaReal(helper.getValorArrecadacaoCalculado()));
					form.setDevolucaoInformadoAntesDestino(Util
							.formatarMoedaReal(helper.getValorDevolucaoInformado()));
					form.setDevolucaoCalculadoAntesDestino(Util
							.formatarMoedaReal(helper.getValorDevolucaoCalculado()));
					
					//Situa��o Depois
					if (pagamentoHelper != null) {
						form.setArrecadacaoInformadoDepoisDestino(Util
					        .formatarMoedaReal(helper.getValorArrecadacaoInformado().add(
												Util.formatarMoedaRealparaBigDecimal(pagamentoHelper.getValorTotalPagamentos()))));

						form.setArrecadacaoCalculadoDepoisDestino(Util
							.formatarMoedaReal(helper.getValorArrecadacaoCalculado().add(
												Util.formatarMoedaRealparaBigDecimal(pagamentoHelper.getValorTotalPagamentos()))));
					} else {
						form.setArrecadacaoInformadoDepoisDestino(Util
							.formatarMoedaReal(helper.getValorArrecadacaoInformado()));
						form.setArrecadacaoCalculadoDepoisDestino(Util
							.formatarMoedaReal(helper.getValorArrecadacaoCalculado()));
					}
					if (devolucaoHelper != null) {
						form.setDevolucaoInformadoDepoisDestino(Util
							.formatarMoedaReal(helper.getValorDevolucaoInformado().add(
												Util.formatarMoedaRealparaBigDecimal(devolucaoHelper.getValorTotalDevolucoes()))));

						form.setDevolucaoCalculadoDepoisDestino(Util.formatarMoedaReal(helper.getValorDevolucaoCalculado().add(
												Util.formatarMoedaRealparaBigDecimal(devolucaoHelper.getValorTotalDevolucoes()))));
					} else {
						form.setDevolucaoInformadoDepoisDestino(Util.formatarMoedaReal(helper.getValorDevolucaoInformado()));
						form.setDevolucaoCalculadoDepoisDestino(Util.formatarMoedaReal(helper.getValorDevolucaoCalculado()));
					}
				}
			}
	
			sessao.setAttribute("pagamentoHelper", pagamentoHelper);
			sessao.setAttribute("devolucaoHelper", devolucaoHelper);
		}else if(httpServletRequest.getParameter("removerPagamentos") != null){
			if(sessao.getAttribute("pagamentoHelper") != null){
				/*Collection<Integer> idsPagamentosRemovidos = null;
				if(sessao.getAttribute("idsPagamentosRemovidos") == null){
					idsPagamentosRemovidos = new ArrayList();
				}else{
					idsPagamentosRemovidos = (Collection<Integer>)sessao.getAttribute("idsPagamentosRemovidos");
				}*/
				PagamentosDevolucoesHelper helper = (PagamentosDevolucoesHelper)sessao.getAttribute("pagamentoHelper");
				Collection<MovimentarPagamentosDevolucoesHelper> colecaoPagamentos = helper.getColecaoMovimentarPagamentos();
				String[] ids = form.getIdRegistrosRemocaoPagamento();
				if (ids != null && ids.length != 0) {
					for (int i = 0; i < ids.length; i++) {
						String[] idsPagamentos = ids[i].split(";");
						Integer idPagamento = Integer.parseInt(idsPagamentos[0]);		
						//idsPagamentosRemovidos.add(idPagamento);
						//sessao.setAttribute("idsPagamentosRemovidos", idsPagamentosRemovidos);
						MovimentarPagamentosDevolucoesHelper movimentarPagamento = new MovimentarPagamentosDevolucoesHelper();
						movimentarPagamento.setId(idPagamento);
						colecaoPagamentos.remove(movimentarPagamento);
						Integer qtdAtual = helper.getQtdPagamentos();
						helper.setQtdPagamentos(qtdAtual - 1);
						BigDecimal valorRemocao = Util.formatarMoedaRealparaBigDecimal(idsPagamentos[1]);
						BigDecimal valorAtual = Util.formatarMoedaRealparaBigDecimal(helper.getValorTotalPagamentos()).subtract(valorRemocao);
						helper.setValorTotalPagamentos(Util.formatarMoedaReal(valorAtual));
						form.setArrecadacaoInformadoDepoisOrigem(Util.formatarMoedaReal(Util.formatarMoedaRealparaBigDecimal(form.getArrecadacaoInformadoDepoisOrigem()).add(valorRemocao)));
						form.setArrecadacaoInformadoDepoisDestino(Util.formatarMoedaReal(Util.formatarMoedaRealparaBigDecimal(form.getArrecadacaoInformadoDepoisDestino()).subtract(valorRemocao)));						
						form.setArrecadacaoCalculadoDepoisOrigem(Util.formatarMoedaReal(Util.formatarMoedaRealparaBigDecimal(form.getArrecadacaoCalculadoDepoisOrigem()).add(valorRemocao)));
						form.setArrecadacaoCalculadoDepoisDestino(Util.formatarMoedaReal(Util.formatarMoedaRealparaBigDecimal(form.getArrecadacaoCalculadoDepoisDestino()).subtract(valorRemocao)));
					}
				}
			  if(colecaoPagamentos != null && !colecaoPagamentos.isEmpty()){
				  helper.setColecaoMovimentarPagamentos(colecaoPagamentos);	  
			  }else{
				  helper = null; 
			  }
			  sessao.setAttribute("pagamentoHelper", helper);
			}			
		}else if(httpServletRequest.getParameter("removerDevolucoes") != null){
			if(sessao.getAttribute("devolucaoHelper") != null){
				/*Collection<Integer> idsDevolucaoRemovidos = null;
				if(sessao.getAttribute("idsDevolucaoRemovidos")== null){
					idsDevolucaoRemovidos = new ArrayList();
				}else{
				    idsDevolucaoRemovidos = (Collection<Integer>)sessao.getAttribute("idsDevolucaoRemovidos");
				}*/
				PagamentosDevolucoesHelper helper = (PagamentosDevolucoesHelper)sessao.getAttribute("devolucaoHelper");
				Collection<MovimentarPagamentosDevolucoesHelper> colecaoDevolucao = helper.getColecaoMovimentarDevolucoes();
				String[] ids = form.getIdRegistrosRemocaoDevolucao();
				if (ids != null && ids.length != 0) {
					for (int i = 0; i < ids.length; i++) {
						String[] idsDevolucao = ids[i].split(";");
						Integer idDevolucao = Integer.parseInt(idsDevolucao[0]);	
						//idsDevolucaoRemovidos.add(idDevolucao);
						//sessao.setAttribute("idsDevolucaoRemovidos", idsDevolucaoRemovidos);
						MovimentarPagamentosDevolucoesHelper movimentarDevolucao = new MovimentarPagamentosDevolucoesHelper();
						movimentarDevolucao.setId(idDevolucao);
						colecaoDevolucao.remove(movimentarDevolucao);
						Integer qtdAtual = helper.getQtdDevolucoes();
						helper.setQtdDevolucoes(qtdAtual - 1);						
						BigDecimal valorRemocao = Util.formatarMoedaRealparaBigDecimal(idsDevolucao[1]);
						BigDecimal valorAtual = Util.formatarMoedaRealparaBigDecimal(helper.getValorTotalDevolucoes()).subtract(valorRemocao);
						helper.setValorTotalDevolucoes(Util.formatarMoedaReal(valorAtual));						
						form.setDevolucaoInformadoDepoisOrigem(Util.formatarMoedaReal(Util.formatarMoedaRealparaBigDecimal(form.getDevolucaoInformadoDepoisOrigem()).add(valorRemocao)));
						form.setDevolucaoInformadoDepoisDestino(Util.formatarMoedaReal(Util.formatarMoedaRealparaBigDecimal(form.getDevolucaoInformadoDepoisDestino()).subtract(valorRemocao)));						
						form.setDevolucaoCalculadoDepoisOrigem(Util.formatarMoedaReal(Util.formatarMoedaRealparaBigDecimal(form.getDevolucaoCalculadoDepoisOrigem()).add(valorRemocao)));
						form.setDevolucaoCalculadoDepoisDestino(Util.formatarMoedaReal(Util.formatarMoedaRealparaBigDecimal(form.getDevolucaoCalculadoDepoisDestino()).subtract(valorRemocao)));
					}
				}
			  if(colecaoDevolucao != null && !colecaoDevolucao.isEmpty()){
				  helper.setColecaoMovimentarPagamentos(colecaoDevolucao);	  
			  }else{
				  helper = null; 
			  }
			  sessao.setAttribute("devolucaoHelper", helper);
			}			
		}
		
		return retorno;
	}

}
