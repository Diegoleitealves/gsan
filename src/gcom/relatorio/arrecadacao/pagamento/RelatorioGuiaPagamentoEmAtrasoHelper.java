package gcom.relatorio.arrecadacao.pagamento;

import java.io.Serializable;


/**
 * Classe que ir� auxiliar no formato de entrada da pesquisa 
 * do relatorio de certidao negativa 
 *
 * @author Bruno Barros
 * @date 29/01/2008
 */
public class RelatorioGuiaPagamentoEmAtrasoHelper implements Serializable, Cloneable{
	
	private static final long serialVersionUID = 1L;
	
	private String financiamentoTipoId;
	
	private String financiamentoTipoDescricao;

	private String vencimentoInicial;

	private String vencimentoFinal;

	private String referenciaInicialContabil;

	private String referenciaFinalContabil;
	
	private String clienteId;
	
	private String clienteNome;
	
	private String clienteCpf;
	
	private String clienteEndereco;
	
	private String emissao;
	
	private String pagamento;
	
	private String valor;
	
	private String referencia;
	
	private String parcelas;
	
	private String parcelasTotal;
	
	private String vencimento;
	
	private String Imovel;
	
	
	private String debitoTipoDescricao;

	public String getVencimento() {
		return vencimento;
	}

	public void setVencimento(String vencimento) {
		this.vencimento = vencimento;
	}

	public String getClienteCpf() {
		return clienteCpf;
	}

	public void setClienteCpf(String clienteCpf) {
		this.clienteCpf = clienteCpf;
	}

	public String getClienteEndereco() {
		return clienteEndereco;
	}

	public void setClienteEndereco(String clienteEndereco) {
		this.clienteEndereco = clienteEndereco;
	}

	public String getClienteId() {
		return clienteId;
	}

	public void setClienteId(String clienteId) {
		this.clienteId = clienteId;
	}

	public String getClienteNome() {
		return clienteNome;
	}

	public void setClienteNome(String clienteNome) {
		this.clienteNome = clienteNome;
	}

	public String getEmissao() {
		return emissao;
	}

	public void setEmissao(String emissao) {
		this.emissao = emissao;
	}

	public String getFinanciamentoTipoDescricao() {
		return financiamentoTipoDescricao;
	}

	public void setFinanciamentoTipoDescricao(String financiamentoTipoDescricao) {
		this.financiamentoTipoDescricao = financiamentoTipoDescricao;
	}

	public String getFinanciamentoTipoId() {
		return financiamentoTipoId;
	}

	public void setFinanciamentoTipoId(String financiamentoTipoId) {
		this.financiamentoTipoId = financiamentoTipoId;
	}

	public String getPagamento() {
		return pagamento;
	}

	public void setPagamento(String pagamento) {
		this.pagamento = pagamento;
	}

	public String getParcelas() {
		return parcelas;
	}

	public void setParcelas(String parcelas) {
		this.parcelas = parcelas;
	}

	public String getParcelasTotal() {
		return parcelasTotal;
	}

	public void setParcelasTotal(String parcelasTotal) {
		this.parcelasTotal = parcelasTotal;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getReferenciaFinalContabil() {
		return referenciaFinalContabil;
	}

	public void setReferenciaFinalContabil(String referenciaFinalContabil) {
		this.referenciaFinalContabil = referenciaFinalContabil;
	}

	public String getReferenciaInicialContabil() {
		return referenciaInicialContabil;
	}

	public void setReferenciaInicialContabil(String referenciaInicialContabil) {
		this.referenciaInicialContabil = referenciaInicialContabil;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getVencimentoFinal() {
		return vencimentoFinal;
	}

	public void setVencimentoFinal(String vencimentoFinal) {
		this.vencimentoFinal = vencimentoFinal;
	}

	public String getVencimentoInicial() {
		return vencimentoInicial;
	}

	public void setVencimentoInicial(String vencimentoInicial) {
		this.vencimentoInicial = vencimentoInicial;
	}

	public String getImovel() {
		return Imovel;
	}

	public void setImovel(String imovel) {
		Imovel = imovel;
	}

	public String getDebitoTipoDescricao() {
		return debitoTipoDescricao;
	}

	public void setDebitoTipoDescricao(String debitoTipoDescricao) {
		this.debitoTipoDescricao = debitoTipoDescricao;
	}

}
