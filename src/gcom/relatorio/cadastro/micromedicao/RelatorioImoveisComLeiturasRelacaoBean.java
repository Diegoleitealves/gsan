package gcom.relatorio.cadastro.micromedicao;

import gcom.relatorio.RelatorioBean;

/**
 * <b>[UC1180] Relat�rio Im�veis com Leituristas</b>:
 *
 * <ul>
 * 		<li> 
 * 			<b>[SB0001] Gerar Relat�rio do Tipo 1</b>: Quantitativo de im�veis com leituras atrav�s da WEB
 * 		</li>
 * 		<li> 
 * 			<b>[SB0002] Gerar Relat�rio do Tipo 2</b>: Quantitativo de im�veis sem leituras atrav�s da ISC e WEB
 * 		</li>
 * 		<li> 
 * 			<b>[SB0003] Gerar Relat�rio do Tipo 3</b>: Quantitativo de im�veis que est�o na rota mas n�o foram recebidos atrav�s da ISC</p>
 * 		</li>
 * 		<li> 
 * 			<b>[SB0004] Gerar Relat�rio do Tipo 4</b>: Rela��o de im�veis com leituras n�o recebidas atrav�s da ISC</b>
 * 		</li>
 * 		<li> 
 * 			<b>[SB0005] Gerar Relat�rio do Tipo 5</b>: Rela��o de im�veis n�o medidos que n�o est�o na rota de ISC</b>
 * 		</li>
 * 		<li> 
 * 			<b>[SB0006] Gerar Relat�rio do Tipo 6</b>: Rela��o de im�veis medidos que n�o est�o na rota de ISC</b>
 * 		</li>
 * </ul>
 * 
 * @author Magno Gouveia
 * @date 03/06/2011
 */
public class RelatorioImoveisComLeiturasRelacaoBean implements RelatorioBean {

	private static final long serialVersionUID = 1L;

	private String mesAnoReferencia;

	private Integer grupoFaturamento;

	private String empresa;

	private String localidade;

	private String setorComercial;

	private Integer rota;

	private String leiturista;

	private String imovel;

	public RelatorioImoveisComLeiturasRelacaoBean(String mesAnoReferencia,
			Integer grupoFaturamento, String empresa, String localidade,
			String setorComercial, Integer rota, String leiturista,
			String imovel) {
		this.mesAnoReferencia = mesAnoReferencia;
		this.grupoFaturamento = grupoFaturamento;
		this.empresa = empresa;
		this.localidade = localidade;
		this.setorComercial = setorComercial;
		this.rota = rota;
		this.leiturista = leiturista;
		this.imovel = imovel;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public Integer getGrupoFaturamento() {
		return grupoFaturamento;
	}

	public void setGrupoFaturamento(Integer grupoFaturamento) {
		this.grupoFaturamento = grupoFaturamento;
	}

	public String getLeiturista() {
		return leiturista;
	}

	public void setLeiturista(String leiturista) {
		this.leiturista = leiturista;
	}

	public String getLocalidade() {
		return localidade;
	}

	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}

	public String getMesAnoReferencia() {
		return mesAnoReferencia;
	}

	public void setMesAnoReferencia(String mesAnoReferencia) {
		this.mesAnoReferencia = mesAnoReferencia;
	}

	public String getImovel() {
		return imovel;
	}

	public void setImovel(String imovel) {
		this.imovel = imovel;
	}

	public Integer getRota() {
		return rota;
	}

	public void setRota(Integer rota) {
		this.rota = rota;
	}

	public String getSetorComercial() {
		return setorComercial;
	}

	public void setSetorComercial(String setorComercial) {
		this.setorComercial = setorComercial;
	}
}
