package gcom.cobranca.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Este caso de uso permite gerar o resumo das a��es de cobran�a com a 
 * atividade emitir j� realizada e a atividade encerrar ainda n�o realizada 
 * e realizar a atividade encerrar das a��es que estejam comandadas.
 *
 * [UC0478] Gerar Resumo das A��es de Cobran�a do Cronograma
 *
 * [SB0004] - Atualizar Item do Documento de Cobran�a
 * 
 * Acumula a quantidade e o valor do item, na situia�o de d�bito correspondente
 * Armazena a data da situa��o do d�bito do imte do documento de cobran�a refrente a situa��o do d�bito do item do documento de cobran�a
 *
 * Objeto composto de:
 * Id Situa��o do D�bito
 * Quantidade de Ocorrencia das Situa��es de D�bito
 * Valor do Item Cobrado
 * Data situa��o do D�bito
 * 
 * @author Rafael Santos
 * @since 18/10/2006
 */
public class GerarResumoAcoesCobrancaCronogramaHelper implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Quantidade de Ocorrencia da Situa��oi de D�bito 
	 */
	private int quantidadeOcorrenciaSituacaoDebito;
	
	/**
	 * Valor do Item Pago
	 */
	private BigDecimal valorItemCobrado;

	/**
	 * Id da Situa��oi do D�bito
	 */
	private int idSituacaoDebito;
	
	/**
	 * Data da Situa��o do D�bito
	 */
	private Date dataSituacaoDebito;

	/**
	 * @return Retorna o campo dataSituacaoDebito.
	 */
	public Date getDataSituacaoDebito() {
		return dataSituacaoDebito;
	}

	/**
	 * @param dataSituacaoDebito O dataSituacaoDebito a ser setado.
	 */
	public void setDataSituacaoDebito(Date dataSituacaoDebito) {
		this.dataSituacaoDebito = dataSituacaoDebito;
	}

	/**
	 * @return Retorna o campo idSituacaoDebito.
	 */
	public int getIdSituacaoDebito() {
		return idSituacaoDebito;
	}

	/**
	 * @param idSituacaoDebito O idSituacaoDebito a ser setado.
	 */
	public void setIdSituacaoDebito(int idSituacaoDebito) {
		this.idSituacaoDebito = idSituacaoDebito;
	}

	/**
	 * @return Retorna o campo quantidadeOcorrenciaSituacaoDebito.
	 */
	public int getQuantidadeOcorrenciaSituacaoDebito() {
		return quantidadeOcorrenciaSituacaoDebito;
	}

	/**
	 * @param quantidadeOcorrenciaSituacaoDebito O quantidadeOcorrenciaSituacaoDebito a ser setado.
	 */
	public void setQuantidadeOcorrenciaSituacaoDebito(
			int quantidadeOcorrenciaSituacaoDebito) {
		this.quantidadeOcorrenciaSituacaoDebito = quantidadeOcorrenciaSituacaoDebito;
	}

	/**
	 * @return Retorna o campo valorItemCobrado.
	 */
	public BigDecimal getValorItemCobrado() {
		return valorItemCobrado;
	}

	/**
	 * @param valorItemCobrado O valorItemCobrado a ser setado.
	 */
	public void setValorItemCobrado(BigDecimal valorItemCobrado) {
		this.valorItemCobrado = valorItemCobrado;
	}

	/**
	 * Construtor de GerarResumoAcoesCobrancaCronogramaHelper 
	 * 
	 * @param quantidadeOcorrenciaSituacaoDebito
	 * @param valorItemCobrado
	 * @param idSituacaoDebito
	 * @param dataSituacaoDebito
	 */
	public GerarResumoAcoesCobrancaCronogramaHelper(int quantidadeOcorrenciaSituacaoDebito, BigDecimal valorItemCobrado, int idSituacaoDebito, Date dataSituacaoDebito) {
		super();
		this.quantidadeOcorrenciaSituacaoDebito = quantidadeOcorrenciaSituacaoDebito;
		this.valorItemCobrado = valorItemCobrado;
		this.idSituacaoDebito = idSituacaoDebito;
		this.dataSituacaoDebito = dataSituacaoDebito;
	}
	
	/**
	 * Construtor de GerarResumoAcoesCobrancaCronogramaHelper 
	 * 
	 * @param quantidadeOcorrenciaSituacaoDebito
	 * @param valorItemCobrado
	 * @param idSituacaoDebito
	 * @param dataSituacaoDebito
	 */
	public GerarResumoAcoesCobrancaCronogramaHelper() {
		super();
	}	
}
