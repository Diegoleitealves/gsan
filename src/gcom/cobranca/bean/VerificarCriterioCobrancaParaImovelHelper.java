package gcom.cobranca.bean;

import gcom.faturamento.debito.DebitoACobrar;

import java.math.BigDecimal;
import java.util.Collection;

/**
 * Objeto utilizado no retorno do met�do: do [SB0004] Verificar Crit�rio de
 * Cobran�a para Im�vel do [UC 0251]
 * 
 * @author Pedro Alexandre
 * @since 09/02/2006
 */
public class VerificarCriterioCobrancaParaImovelHelper {

	/**
	 * Quantidade de itens cobrados nos documentos
	 */
	private Integer quantidadeItensEmDebito;

	/**
	 * Valor dos d�bitos do im�vel
	 */
	private BigDecimal valorDebitoImovel;

	/**
	 * Flag que indica se o im�vel satifaz o crit�rio de cobran�a
	 */
	private boolean flagCriterioCobrancaImovel;

	/**
	 * Cole��o de valores de conta do  d�bito do im�vel
	 */
	private Collection<ContaValoresHelper> colecaoContasValores;
	
	/**
	 * Cole��o de d�bito a cobrar do  d�bito do im�vel
	 */
	private Collection<DebitoACobrar> colecaoDebitoACobrar;
	
	/**
	 * Cole��o de valores de guia de pagamento do  d�bito do im�vel
	 */
	private Collection<GuiaPagamentoValoresHelper> colecaoGuiasPagamentoValores;
	
	private int motivoRejeicao;
	
	public static int SITUACAO_LIGACAO_AGUA_INVALIDA = 1;
	
	public static int SITUACAO_LIGACAO_ESGOTO_INVALIDA = 1;
	
	public static int SITUACAO_ESPECIAL_COBRANCA = 3;
	
	public static int SITUACAO_COBRANCA = 4;
	
	public static int SITUACAO_COBRANCA_DIFERENTE_SELECIONADAS = 5;
	
	public static int PERFIL_SEM_CRITERIO = 6;
	
	public static int IMOVEL_SEM_DEBITO = 7;
	
	public static int NAO_CONSIDERAR_CONTA_MES = 8;
	
	public static int NAO_CONSIDERAR_CONTA_ANTIGA = 9;
	
	public static int APENAS_CONTA_ANTIGA_CONTA_MES = 10;
	
	public int getMotivoRejeicao() {
		return motivoRejeicao;
	}

	public void setMotivoRejeicao(int motivoRejeicao) {
		this.motivoRejeicao = motivoRejeicao;
	}

	public VerificarCriterioCobrancaParaImovelHelper() {}

	public boolean isFlagCriterioCobrancaImovel() {
		return flagCriterioCobrancaImovel;
	}

	public void setFlagCriterioCobrancaImovel(boolean flagCriterioCobrancaImovel) {
		this.flagCriterioCobrancaImovel = flagCriterioCobrancaImovel;
	}

	public Integer getQuantidadeItensEmDebito() {
		return quantidadeItensEmDebito;
	}

	public void setQuantidadeItensEmDebito(Integer quantidadeItensEmDebito) {
		this.quantidadeItensEmDebito = quantidadeItensEmDebito;
	}

	public BigDecimal getValorDebitoImovel() {
		return valorDebitoImovel;
	}

	public void setValorDebitoImovel(BigDecimal valorDebitoImovel) {
		this.valorDebitoImovel = valorDebitoImovel;
	}

	public Collection<ContaValoresHelper> getColecaoContasValores() {
		return colecaoContasValores;
	}

	public void setColecaoContasValores(
			Collection<ContaValoresHelper> colecaoContasValores) {
		this.colecaoContasValores = colecaoContasValores;
	}

	public Collection<DebitoACobrar> getColecaoDebitoACobrar() {
		return colecaoDebitoACobrar;
	}

	public void setColecaoDebitoACobrar(
			Collection<DebitoACobrar> colecaoDebitoACobrar) {
		this.colecaoDebitoACobrar = colecaoDebitoACobrar;
	}

	public Collection<GuiaPagamentoValoresHelper> getColecaoGuiasPagamentoValores() {
		return colecaoGuiasPagamentoValores;
	}

	public void setColecaoGuiasPagamentoValores(
			Collection<GuiaPagamentoValoresHelper> colecaoGuiasPagamentoValores) {
		this.colecaoGuiasPagamentoValores = colecaoGuiasPagamentoValores;
	}

}
