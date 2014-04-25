package gcom.relatorio.faturamento;

import gcom.relatorio.RelatorioBean;

import java.math.BigDecimal;

/**
 *[UC0958] Gerar Relat�rio Juras, Multas e D�bitos Cancelados
 *
 *Esta classe foi necess�rio para a correta exibi��o dos totalizadores no relat�rio.
 *O que acontece � que foi pedido para o relat�rio ser ordenado por alguns campos
 *e que fossem dados os totais de outros campos.
 *Sendo assim, n�o � poss�vel criar um grupo no relat�rio e 
 *as variav�is para calcular esses totalizadores.
 *
 * @author Marlon Patrick
 * @since 22/10/2009
 */
public class RelatorioJurosMultasDebitosCanceladosBeanHelper implements RelatorioBean,Comparable<RelatorioJurosMultasDebitosCanceladosBeanHelper> {

	private String tipoDebito;
	
	private BigDecimal totalCancelado;
	
	private Integer quantidadeRegistros;

	public String getTipoDebito() {
		return tipoDebito;
	}

	public void setTipoDebito(String tipoDebito) {
		this.tipoDebito = tipoDebito;
	}

	public BigDecimal getTotalCancelado() {
		return totalCancelado;
	}

	public void setTotalCancelado(BigDecimal valorDebito) {
		this.totalCancelado = valorDebito;
	}

	public Integer getQuantidadeRegistros() {
		return quantidadeRegistros;
	}

	public void setQuantidadeRegistros(Integer quantidadeRegistros) {
		this.quantidadeRegistros = quantidadeRegistros;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((tipoDebito == null) ? 0 : tipoDebito.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RelatorioJurosMultasDebitosCanceladosBeanHelper other = (RelatorioJurosMultasDebitosCanceladosBeanHelper) obj;
		if (tipoDebito == null) {
			if (other.tipoDebito != null)
				return false;
		} else if (!tipoDebito.equals(other.tipoDebito))
			return false;
		return true;
	}	

	public int compareTo(RelatorioJurosMultasDebitosCanceladosBeanHelper o) {
		return this.getTipoDebito().compareTo(o.getTipoDebito());
	}
}
