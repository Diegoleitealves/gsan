package gcom.relatorio;

import gcom.relatorio.RelatorioBean;

/**
 * Bean respons�vel de pegar os parametros que ser�o exibidos na parte de detail
 * do relat�rio.
 * 
 * @author Rafael Pinto
 * @created 27/08/2007
 */
public class RelatorioPadraoBatchBean implements RelatorioBean {
		
	private String observacao;

	public RelatorioPadraoBatchBean(String observacao) {
		this.observacao = observacao;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
	
	
}
