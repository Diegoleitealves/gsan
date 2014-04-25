package gcom.gui.relatorio.atendimentopublico.ordemservico;

import org.apache.struts.action.ActionForm;

/**
 * Description of the Class
 * 
 * @author Hugo Leonardo
 * @created 24/01/2011
 */
public class GerarRelatorioReligacaoClientesInadiplentesForm extends ActionForm {
	
	private static final long serialVersionUID = 1L;
	
	private String gerenciaRegionalID;
	
	private String unidadeNegocioID;
	
	private String localidadeID;

	private String nomeLocalidade;
	
	private String setorComercialCD;

	private String setorComercialID;

	private String nomeSetorComercial;
	
	private String clienteID;
	
	private String nomeCliente;
	
	private String usuarioID;
	
	private String nomeUsuario;
	
	private String dataInicioEncerramento;
	
	private String dataFimEncerramento;
	
	private String dataInicioRecorrencia;
	
	private String dataFimRecorrencia;
	
	private String escolhaRelatorio;

	
	public String getClienteID() {
		return clienteID;
	}

	public void setClienteID(String clienteID) {
		this.clienteID = clienteID;
	}

	public String getDataFimEncerramento() {
		return dataFimEncerramento;
	}

	public void setDataFimEncerramento(String dataFimEncerramento) {
		this.dataFimEncerramento = dataFimEncerramento;
	}

	public String getDataFimRecorrencia() {
		return dataFimRecorrencia;
	}

	public void setDataFimRecorrencia(String dataFimRecorrencia) {
		this.dataFimRecorrencia = dataFimRecorrencia;
	}

	public String getDataInicioEncerramento() {
		return dataInicioEncerramento;
	}

	public void setDataInicioEncerramento(String dataInicioEncerramento) {
		this.dataInicioEncerramento = dataInicioEncerramento;
	}

	public String getDataInicioRecorrencia() {
		return dataInicioRecorrencia;
	}

	public void setDataInicioRecorrencia(String dataInicioRecorrencia) {
		this.dataInicioRecorrencia = dataInicioRecorrencia;
	}

	public String getEscolhaRelatorio() {
		return escolhaRelatorio;
	}

	public void setEscolhaRelatorio(String escolhaRelatorio) {
		this.escolhaRelatorio = escolhaRelatorio;
	}

	public String getGerenciaRegionalID() {
		return gerenciaRegionalID;
	}

	public void setGerenciaRegionalID(String gerenciaRegionalID) {
		this.gerenciaRegionalID = gerenciaRegionalID;
	}

	public String getLocalidadeID() {
		return localidadeID;
	}

	public void setLocalidadeID(String localidadeID) {
		this.localidadeID = localidadeID;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public String getNomeLocalidade() {
		return nomeLocalidade;
	}

	public void setNomeLocalidade(String nomeLocalidade) {
		this.nomeLocalidade = nomeLocalidade;
	}

	public String getNomeSetorComercial() {
		return nomeSetorComercial;
	}

	public void setNomeSetorComercial(String nomeSetorComercial) {
		this.nomeSetorComercial = nomeSetorComercial;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public String getSetorComercialCD() {
		return setorComercialCD;
	}

	public void setSetorComercialCD(String setorComercialCD) {
		this.setorComercialCD = setorComercialCD;
	}

	public String getSetorComercialID() {
		return setorComercialID;
	}

	public void setSetorComercialID(String setorComercialID) {
		this.setorComercialID = setorComercialID;
	}

	public String getUnidadeNegocioID() {
		return unidadeNegocioID;
	}

	public void setUnidadeNegocioID(String unidadeNegocioID) {
		this.unidadeNegocioID = unidadeNegocioID;
	}

	public String getUsuarioID() {
		return usuarioID;
	}

	public void setUsuarioID(String usuarioID) {
		this.usuarioID = usuarioID;
	}

}

