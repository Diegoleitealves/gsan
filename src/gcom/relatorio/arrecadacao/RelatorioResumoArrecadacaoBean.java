package gcom.relatorio.arrecadacao;

import gcom.relatorio.RelatorioBean;

import java.math.BigDecimal;

public class RelatorioResumoArrecadacaoBean implements RelatorioBean {

	private BigDecimal[] valorItemArrecadacao;

	private Integer lancamentoTipo;

	private Integer lancamentoTipoSuperior;

	private Short indicadorImpressao;

	private String descricaoItemContabil;

	private String descricaoTipoLancamento;

	private Short indicadorTotal;

	private boolean tipoLancamentoSemItem = false;

	@SuppressWarnings("unused")
	private BigDecimal somaValoresItemArrecadacao;
	
	private Integer recebimentoTipo;
	
	private String descricaoTipoRecebimento;
	
	private String descricaoItemLancamento;
	
	private String descricaoGerencia;
	
	private String gerencia ;
	
	private String descricaoLocalidade ;
	
	private String descricaoMunicipio;
	
	private String codigoCentroCusto;
	
	private String localidade ;

	private String municipio;
	
	private String descLancamentoTipoSuperior;
	
	private String descricaoUnidadeNegocio ;
	
	private String unidadeNegocio ;
	
	public String getDescricaoLocalidade() {
		return descricaoLocalidade;
	}

	public void setDescricaoLocalidade(String descricaoLocalidade) {
		this.descricaoLocalidade = descricaoLocalidade;
	}


	public String getDescricaoItemLancamento() {
		return descricaoItemLancamento;
	}

	public void setDescricaoItemLancamento(String descricaoItemLancamento) {
		this.descricaoItemLancamento = descricaoItemLancamento;
	}

	public RelatorioResumoArrecadacaoBean(BigDecimal[] valorItemArrecadacao,
			String descricaoTipoRecebimento, String descricaoTipoLancamento,
			String descricaoItemLancamento,String descricaoItemContabil,
			Short indicadorImpressao,Short indicadorTotal,
			Integer lancamentoTipo, Integer lancamentoTipoSuperior,
			boolean tipoLancamentoSemItem,
			String descricaoGerencia, String gerencia,
			String descricaoLocalidade, String localidade,
			String descricaoMunicipio, String municipio,
			String descLancamentoTipoSuperior,
			String descricaoUnidadeNegocio,
			String unidadeNegocio,
			String codigoCentroCusto) {
		this.valorItemArrecadacao = valorItemArrecadacao;
		this.lancamentoTipo = lancamentoTipo;
		this.lancamentoTipoSuperior = lancamentoTipoSuperior;
		this.indicadorImpressao = indicadorImpressao;
		this.descricaoItemContabil = descricaoItemContabil;
		this.indicadorTotal = indicadorTotal;
		this.tipoLancamentoSemItem = tipoLancamentoSemItem;
		this.descricaoTipoLancamento = descricaoTipoLancamento;
		this.descricaoTipoRecebimento = descricaoTipoRecebimento;
		this.descricaoItemLancamento = descricaoItemLancamento;
		this.descricaoGerencia = descricaoGerencia;
		this.gerencia = gerencia;
		this.descricaoLocalidade = descricaoLocalidade;
		this.localidade =localidade;
		this.descricaoMunicipio = descricaoMunicipio;
		this.municipio = municipio;
		this.descLancamentoTipoSuperior = descLancamentoTipoSuperior;
		this.descricaoUnidadeNegocio = descricaoUnidadeNegocio;
		this.unidadeNegocio = unidadeNegocio;
		this.codigoCentroCusto = codigoCentroCusto;
	
	}

	public String getDescricaoItemContabil() {
		return descricaoItemContabil;
	}

	public void setDescricaoItemContabil(String descricaoItemContabil) {
		this.descricaoItemContabil = descricaoItemContabil;
	}

	public Short getIndicadorImpressao() {
		return indicadorImpressao;
	}

	public void setIndicadorImpressao(Short indicadorImpressao) {
		this.indicadorImpressao = indicadorImpressao;
	}

	public Short getIndicadorTotal() {
		return indicadorTotal;
	}

	public void setIndicadorTotal(Short indicadorTotal) {
		this.indicadorTotal = indicadorTotal;
	}

	public Integer getLancamentoTipo() {
		return lancamentoTipo;
	}

	public void setLancamentoTipo(Integer lancamentoTipo) {
		this.lancamentoTipo = lancamentoTipo;
	}

	public Integer getLancamentoTipoSuperior() {
		return lancamentoTipoSuperior;
	}

	public void setLancamentoTipoSuperior(Integer lancamentoTipoSuperior) {
		this.lancamentoTipoSuperior = lancamentoTipoSuperior;
	}

	public BigDecimal[] getValorItemArrecadacao() {
		return valorItemArrecadacao;
	}

	public void setValorItemArrecadacao(BigDecimal[] valorItemArrecadacao) {
		this.valorItemArrecadacao = valorItemArrecadacao;
	}

	public BigDecimal getValorItemArrecadacao1() {
		BigDecimal valorItemArrecadacao0 = valorItemArrecadacao[0];
		
		if (valorItemArrecadacao0 == null) {
			valorItemArrecadacao0 = new BigDecimal(0);
		}
		
		return valorItemArrecadacao0;
	}

	public BigDecimal getValorItemArrecadacao2() {
		BigDecimal valorItemArrecadacao1 = valorItemArrecadacao[1];
		
		if (valorItemArrecadacao1 == null) {
			valorItemArrecadacao1 = new BigDecimal(0);
		}
		
		return valorItemArrecadacao1;
	}

	public BigDecimal getValorItemArrecadacao3() {
		BigDecimal valorItemArrecadacao2 = valorItemArrecadacao[2];
		
		if (valorItemArrecadacao2 == null) {
			valorItemArrecadacao2 = new BigDecimal(0);
		}
		
		return valorItemArrecadacao2;
	}

	public BigDecimal getValorItemArrecadacao4() {
		return this.getValorItemArrecadacao1().add(getValorItemArrecadacao2())
		.add(getValorItemArrecadacao3());
	}

	public BigDecimal getValorItemArrecadacao5() {
		BigDecimal valorItemArrecadacao4 = valorItemArrecadacao[4];
		
		if (valorItemArrecadacao4 == null) {
			valorItemArrecadacao4 = new BigDecimal(0);
		}
		
		return valorItemArrecadacao4;
	}

	public boolean isTipoLancamentoSemItem() {
		return tipoLancamentoSemItem;
	}

	public void setTipoLancamentoSemItem(boolean tipoLancamentoSemItem) {
		this.tipoLancamentoSemItem = tipoLancamentoSemItem;
	}

	public BigDecimal getSomaValoresItemArrecadacao() {
		return this.getValorItemArrecadacao4()
				.add(getValorItemArrecadacao5());

	}

	public void setSomaValoresItemArrecadacao(
			BigDecimal somaValoresItemArrecadacao) {
		this.somaValoresItemArrecadacao = somaValoresItemArrecadacao;
	}

	public String getDescricaoTipoLancamento() {
		return descricaoTipoLancamento;
	}

	public void setDescricaoTipoLancamento(String descricaoTipoLancamento) {
		this.descricaoTipoLancamento = descricaoTipoLancamento;
	}

	public String getDescricaoTipoRecebimento() {
		return descricaoTipoRecebimento;
	}

	public void setDescricaoTipoRecebimento(String descricaoTipoRecebimento) {
		this.descricaoTipoRecebimento = descricaoTipoRecebimento;
	}

	public Integer getRecebimentoTipo() {
		return recebimentoTipo;
	}

	public void setRecebimentoTipo(Integer recebimentoTipo) {
		this.recebimentoTipo = recebimentoTipo;
	}

	public String getDescricaoGerencia() {
		return descricaoGerencia;
	}

	public void setDescricaoGerencia(String descricaoGerencia) {
		this.descricaoGerencia = descricaoGerencia;
	}

	public String getGerencia() {
		return gerencia;
	}

	public void setGerencia(String gerencia) {
		this.gerencia = gerencia;
	}

	public String getLocalidade() {
		return localidade;
	}

	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}

	public String getDescLancamentoTipoSuperior() {
		return descLancamentoTipoSuperior;
	}

	public void setDescLancamentoTipoSuperior(String descLancamentoTipoSuperior) {
		this.descLancamentoTipoSuperior = descLancamentoTipoSuperior;
	}

	public String getDescricaoUnidadeNegocio() {
		return descricaoUnidadeNegocio;
	}

	public void setDescricaoUnidadeNegocio(String descricaoUnidadeNegocio) {
		this.descricaoUnidadeNegocio = descricaoUnidadeNegocio;
	}

	public String getUnidadeNegocio() {
		return unidadeNegocio;
	}

	public void setUnidadeNegocio(String unidadeNegocio) {
		this.unidadeNegocio = unidadeNegocio;
	}

	public String getCodigoCentroCusto() {
		return codigoCentroCusto;
	}

	public void setCodigoCentroCusto(String codigoCentroCusto) {
		this.codigoCentroCusto = codigoCentroCusto;
	}

	public String getDescricaoMunicipio() {
		return descricaoMunicipio;
	}

	public void setDescricaoMunicipio(String descricaoMunicipio) {
		this.descricaoMunicipio = descricaoMunicipio;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
}
