package gcom.gui.cobranca;

import gcom.arrecadacao.pagamento.GuiaPagamento;
import gcom.cadastro.geografico.Municipio;
import gcom.cobranca.bean.ContaValoresHelper;
import gcom.cobranca.bean.GuiaPagamentoValoresHelper;
import gcom.cobranca.bean.ObterDebitoImovelOuClienteHelper;
import gcom.fachada.Fachada;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.credito.CreditoARealizar;
import gcom.faturamento.debito.DebitoACobrar;
import gcom.gui.GcomAction;
import gcom.relatorio.cobranca.RelatorioEmitirDeclaracaoTransferenciaDebito;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.Util;

import java.math.BigDecimal;
import java.util.ArrayList;
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
 * Esta classe tem por finalidade realizar a transfer�ncia de d�bitos e cr�ditos entre
 * os im�veis de origem e destino 
 * 
 * @author Raphael Rossiter
 * @date 12/06/2007
 */
public class TransferirDebitoCreditoAction extends GcomAction {
	
	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		ActionForward retorno = actionMapping.findForward("telaSucesso");

		TransferenciaDebitoCreditoDadosImovelActionForm form = 
			(TransferenciaDebitoCreditoDadosImovelActionForm) actionForm;

		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();
		
		Integer idRa = new Integer(form.getIdRegistroAtendimento());
		Integer idImovelDestino = new Integer(form.getIdImovelDestino());
		
		// Valida��o dos dados informados referentes aos im�veis de origem e destino
		fachada.validarTransferenciaDebitoCreditoDadosImoveis(idRa, idImovelDestino);
		
		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");
		
		String idsContas = httpServletRequest.getParameter("conta");
		String idsDebitos = httpServletRequest.getParameter("debito");
		String idsCreditos = httpServletRequest.getParameter("credito");
		String idsGuias = httpServletRequest.getParameter("guiaPagamento");
		
		//[SB0001] - Apresentar D�bitos/Cr�ditos do Im�vel de Origem
		ObterDebitoImovelOuClienteHelper obterDebitoImovelOuClienteHelper = 
		fachada.apresentarDebitoCreditoImovelOrigem(new Integer(form.getIdImovelOrigem()));
		
		Collection<Conta> colecaoContas = this.obterContasSelecionadas(idsContas, 
		obterDebitoImovelOuClienteHelper.getColecaoContasValoresImovel());
		
		Collection<DebitoACobrar> colecaoDebitos = this.obterDebitosSelecionados(idsDebitos, 
		obterDebitoImovelOuClienteHelper.getColecaoDebitoACobrar());
		
		Collection<CreditoARealizar> colecaoCreditos = this.obterCreditosSelecionadas(idsCreditos, 
		obterDebitoImovelOuClienteHelper.getColecaoCreditoARealizar());
		
		Collection<GuiaPagamento> colecaoGuiaPagamentos = this.obterGuiasSelecionadas(idsGuias, 
		obterDebitoImovelOuClienteHelper.getColecaoGuiasPagamentoValores());
		
		String idEmitir = httpServletRequest.getParameter("indicadorEmissao");
		
		fachada.transferirDebitosCreditos(idImovelDestino, colecaoContas, colecaoDebitos, colecaoCreditos, 
				colecaoGuiaPagamentos, usuarioLogado, idRa, idsContas);
		
		if(idEmitir.equals("true")){		

			BigDecimal novoValor = BigDecimal.ZERO;		

			novoValor = novoValor
			.add(this.obterValorContasSelecionadas(idsContas, 
					obterDebitoImovelOuClienteHelper.getColecaoContasValoresImovel()))
			.add(this.obterValorDebitosSelecionados(idsDebitos, 
					obterDebitoImovelOuClienteHelper.getColecaoDebitoACobrar()))
			.add(this.obterValorCreditosSelecionadas(idsCreditos, 
					obterDebitoImovelOuClienteHelper.getColecaoCreditoARealizar()))
			.add(this.obterValorGuiasSelecionadas(idsGuias, 
					obterDebitoImovelOuClienteHelper.getColecaoGuiasPagamentoValores()));		

			this.gerarRelatorio(novoValor,form,httpServletRequest);
			
			
			montarPaginaSucesso(httpServletRequest, "Transfer�ncia realizada com sucesso.", 
					"Transfer�ncia de D�bitos/Cr�ditos", 
					"exibirTransferenciaDebitoCreditoDadosImovelAction.do?menu=sim",
					"gerarRelatorioEmitirDeclaracaoTransferenciaDebitoCredito.do",
					"Emitir Declara��o de Transfer�ncia de D�bitos/Cr�ditos");
		}else{
			montarPaginaSucesso(httpServletRequest, "Transfer�ncia realizada com sucesso.", 
					"Transfer�ncia de D�bitos/Cr�ditos", 
					"exibirTransferenciaDebitoCreditoDadosImovelAction.do?menu=sim");			
		}
		
		return retorno;
	}
	
	private void gerarRelatorio (BigDecimal novoValor,
		TransferenciaDebitoCreditoDadosImovelActionForm form,HttpServletRequest httpServletRequest){
		
		String indicadorTipoEmissao = form.getIndicadorTipoEmissao();		
		String clienteUsuarioDestino = 
			this.getFachada().consultarClienteUsuarioImovel(Integer.parseInt(form.getIdImovelDestino()));
		String clienteUsuarioOrigem = this.getFachada().consultarClienteUsuarioImovel(Integer.parseInt(form.getIdImovelOrigem()));		
		String valorNovaConta = Util.formatarBigDecimalParaStringComVirgula(novoValor);				
		String diaMesAno = Util.retornaDataPorExtenso(new Date());
		Municipio municipio = this.getFachada().pesquisarMunicipioImovel(Integer.parseInt(form.getIdImovelDestino()));
		
		// cria uma inst�ncia da classe do relat�rio
		RelatorioEmitirDeclaracaoTransferenciaDebito relatorioEmitirDeclaracaoTransferenciaDebito = 
			new RelatorioEmitirDeclaracaoTransferenciaDebito(this.getUsuarioLogado(httpServletRequest));
			
		relatorioEmitirDeclaracaoTransferenciaDebito.addParametro("clienteUsuarioDestino", clienteUsuarioDestino);
		relatorioEmitirDeclaracaoTransferenciaDebito.addParametro("clienteUsuarioOrigem", clienteUsuarioOrigem);
		relatorioEmitirDeclaracaoTransferenciaDebito.addParametro("valorNovaConta", valorNovaConta);			
		relatorioEmitirDeclaracaoTransferenciaDebito.addParametro("indicadorTipoEmissao", indicadorTipoEmissao);
		relatorioEmitirDeclaracaoTransferenciaDebito.addParametro("diaMesAno", diaMesAno);
		relatorioEmitirDeclaracaoTransferenciaDebito.addParametro("municipio", municipio.getNome());
		this.getSessao(httpServletRequest).setAttribute("declaracaoTransferencia", relatorioEmitirDeclaracaoTransferenciaDebito);
	}
	
	private Collection<Conta> obterContasSelecionadas(String idsContas, Collection colecaoContasBase){
		
		Collection<Conta> colecaoContas = null;
		
		if (idsContas != null && !idsContas.equals("")){
			
			colecaoContas = new ArrayList();
			
			Iterator itColecaoContas = colecaoContasBase.iterator();
			ContaValoresHelper contaValoresHelper = null;
			
			String[] idsContasArray = idsContas.split(",");
			
			while (itColecaoContas.hasNext()){
				
				contaValoresHelper = (ContaValoresHelper) itColecaoContas.next();
				
				for(int x=0; x<idsContasArray.length; x++){
					
					if (contaValoresHelper.getConta().getId().equals(new Integer(idsContasArray[x]))){
						colecaoContas.add(contaValoresHelper.getConta());
					}
				}
			}
		}
		
		return colecaoContas;
	}
	
	private Collection<DebitoACobrar> obterDebitosSelecionados(String idsDebitos, Collection colecaoDebitosBase){
		
		Collection<DebitoACobrar> colecaoDebitos = null;
		
		if (idsDebitos != null && !idsDebitos.equals("")){
			
			colecaoDebitos = new ArrayList();
			
			Iterator itColecaoDebitos = colecaoDebitosBase.iterator();
			DebitoACobrar debitoACobrar = null;
			
			String[] idsDebitosArray = idsDebitos.split(",");
			
			while (itColecaoDebitos.hasNext()){
				
				debitoACobrar = (DebitoACobrar) itColecaoDebitos.next();
				
				for(int x=0; x<idsDebitosArray.length; x++){
					
					if (debitoACobrar.getId().equals(new Integer(idsDebitosArray[x]))){
						colecaoDebitos.add(debitoACobrar);
					}
				}
			}
		}
		
		return colecaoDebitos;
	}
	
	private Collection<CreditoARealizar> obterCreditosSelecionadas(String idsCreditos, Collection colecaoCreditosBase){
		
		Collection<CreditoARealizar> colecaoCreditos = null;
		
		if (idsCreditos != null && !idsCreditos.equals("")){
			
			colecaoCreditos = new ArrayList();
			
			Iterator itColecaoCreditos = colecaoCreditosBase.iterator();
			CreditoARealizar creditoARealizar = null;
			
			String[] idsCreditosArray = idsCreditos.split(",");
			
			while (itColecaoCreditos.hasNext()){
				
				creditoARealizar = (CreditoARealizar) itColecaoCreditos.next();
				
				for(int x=0; x<idsCreditosArray.length; x++){
					
					if (creditoARealizar.getId().equals(new Integer(idsCreditosArray[x]))){
						colecaoCreditos.add(creditoARealizar);
					}
				}
			}
		}
		
		return colecaoCreditos;
	}
	
	private Collection<GuiaPagamento> obterGuiasSelecionadas(String idsGuias, Collection colecaoGuiasBase){
		
		Collection<GuiaPagamento> colecaoGuias = null;
		
		if (idsGuias != null && !idsGuias.equals("")){
			
			colecaoGuias = new ArrayList();
			
			Iterator itColecaoGuias = colecaoGuiasBase.iterator();
			GuiaPagamentoValoresHelper guiaPagamentoValoresHelper = null;
			
			String[] idsGuiasArray = idsGuias.split(",");
			
			while (itColecaoGuias.hasNext()){
				
				guiaPagamentoValoresHelper = (GuiaPagamentoValoresHelper) itColecaoGuias.next();
				
				for(int x=0; x<idsGuiasArray.length; x++){
					
					if (guiaPagamentoValoresHelper.getGuiaPagamento().getId().equals(new Integer(idsGuiasArray[x]))){
						colecaoGuias.add(guiaPagamentoValoresHelper.getGuiaPagamento());
					}
				}
			}
		}
		
		return colecaoGuias;
	}
	
	private BigDecimal obterValorContasSelecionadas(String idsContas, Collection colecaoContasBase){
		
		BigDecimal retorno = BigDecimal.ZERO; 
		
		if (idsContas != null && !idsContas.equals("")){
			
			Iterator itColecaoContas = colecaoContasBase.iterator();
			ContaValoresHelper contaValoresHelper = null;
			
			String[] idsContasArray = idsContas.split(",");
			
			while (itColecaoContas.hasNext()){
				
				contaValoresHelper = (ContaValoresHelper) itColecaoContas.next();
				
				for(int x=0; x<idsContasArray.length; x++){
					
					if (contaValoresHelper.getConta().getId().equals(new Integer(idsContasArray[x]))){
						retorno = retorno.add(contaValoresHelper.getValorTotalConta().add(contaValoresHelper.getValorTotalContaValoresParcelamento()));
					}
				}
			}
		}		
		return retorno;
	}
	
    private BigDecimal obterValorDebitosSelecionados(String idsDebitos, Collection colecaoDebitosBase){
		
    	BigDecimal retorno = BigDecimal.ZERO;
		
		if (idsDebitos != null && !idsDebitos.equals("")){
			
			Iterator itColecaoDebitos = colecaoDebitosBase.iterator();
			DebitoACobrar debitoACobrar = null;
			
			String[] idsDebitosArray = idsDebitos.split(",");
			
			while (itColecaoDebitos.hasNext()){
				
				debitoACobrar = (DebitoACobrar) itColecaoDebitos.next();
				
				for(int x=0; x<idsDebitosArray.length; x++){
					
					if (debitoACobrar.getId().equals(new Integer(idsDebitosArray[x]))){
						retorno = retorno.add(debitoACobrar.getValorTotalComBonus());
					}
				}
			}
		}
		
		return retorno;
	}
	
    private BigDecimal obterValorCreditosSelecionadas(String idsCreditos, Collection colecaoCreditosBase){
		
		BigDecimal retorno = BigDecimal.ZERO;
		
		if (idsCreditos != null && !idsCreditos.equals("")){
			
			Iterator itColecaoCreditos = colecaoCreditosBase.iterator();
			CreditoARealizar creditoARealizar = null;
			
			String[] idsCreditosArray = idsCreditos.split(",");
			
			while (itColecaoCreditos.hasNext()){
				
				creditoARealizar = (CreditoARealizar) itColecaoCreditos.next();
				
				for(int x=0; x<idsCreditosArray.length; x++){
					
					if (creditoARealizar.getId().equals(new Integer(idsCreditosArray[x]))){
						retorno = retorno.add(creditoARealizar.getValorTotalComBonus());
					}
				}
			}
		}
		
		return retorno;
	}
	
    private BigDecimal obterValorGuiasSelecionadas(String idsGuias, Collection colecaoGuiasBase){
		
    	BigDecimal retorno = BigDecimal.ZERO;
		
		if (idsGuias != null && !idsGuias.equals("")){
			
			Iterator itColecaoGuias = colecaoGuiasBase.iterator();
			GuiaPagamentoValoresHelper guiaPagamentoValoresHelper = null;
			
			String[] idsGuiasArray = idsGuias.split(",");
			
			while (itColecaoGuias.hasNext()){
				
				guiaPagamentoValoresHelper = (GuiaPagamentoValoresHelper) itColecaoGuias.next();
				
				for(int x=0; x<idsGuiasArray.length; x++){
					
					if (guiaPagamentoValoresHelper.getGuiaPagamento().getId().equals(new Integer(idsGuiasArray[x]))){
						retorno = retorno.add(guiaPagamentoValoresHelper.getGuiaPagamento().getValorDebito());
					}
				}
			}
		}
		
		return retorno;
	}
			
}
