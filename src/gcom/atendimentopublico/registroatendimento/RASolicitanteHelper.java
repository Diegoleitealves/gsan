package gcom.atendimentopublico.registroatendimento;

import java.util.Collection;

public class RASolicitanteHelper {

	private Integer idCliente; 
	private String pontoReferenciaSolicitante;
	private String nomeSolicitante; 
	private boolean novoSolicitante;
	private Integer idUnidadeSolicitante; 
	private Integer idServicoTipo; 
	@SuppressWarnings("rawtypes")
	private Collection colecaoFone; 
	@SuppressWarnings("rawtypes")
	private Collection colecaoEnderecoSolicitante;
	private String habilitarCampoSatisfacaoEmail; 
	private String enviarEmailSatisfacao; 
	private String enderecoEmail;
	
	public Integer getIdCliente() {
		return idCliente;
	}
	
	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}
	
	public RASolicitanteHelper idClente(Integer idCliente) {
		setIdCliente(idCliente);
		return this;
	}
	
	public String getPontoReferenciaSolicitante() {
		return pontoReferenciaSolicitante;
	}
	
	public void setPontoReferenciaSolicitante(String pontoReferenciaSolicitante) {
		this.pontoReferenciaSolicitante = pontoReferenciaSolicitante;
	}
	
	public RASolicitanteHelper pontoReferenciaSolicitante(String pontoReferenciaSolicitante) {
		setPontoReferenciaSolicitante(pontoReferenciaSolicitante);
		return this;
	}
	
	public String getNomeSolicitante() {
		return nomeSolicitante;
	}
	
	public void setNomeSolicitante(String nomeSolicitante) {
		this.nomeSolicitante = nomeSolicitante;
	}
	
	public RASolicitanteHelper  nomeSolicitante(String nomeSolicitante) {
		setNomeSolicitante(nomeSolicitante);
		return this;
	}
	
	public boolean isNovoSolicitante() {
		return novoSolicitante;
	}
	
	public void setNovoSolicitante(boolean novoSolicitante) {
		this.novoSolicitante = novoSolicitante;
	}
	
	public RASolicitanteHelper novoSolicitante(boolean novoSolicitante) {
		setNovoSolicitante(novoSolicitante);
		return this;
	}
	
	public Integer getIdUnidadeSolicitante() {
		return idUnidadeSolicitante;
	}
	
	public void setIdUnidadeSolicitante(Integer idUnidadeSolicitante) {
		this.idUnidadeSolicitante = idUnidadeSolicitante;
	}
	
	public RASolicitanteHelper idUnidadeSolicitante(Integer idUnidadeSolicitante) {
		setIdUnidadeSolicitante(idUnidadeSolicitante);
		return this;
	}
	
	public Integer getIdServicoTipo() {
		return idServicoTipo;
	}
	
	public void setIdServicoTipo(Integer idServicoTipo) {
		this.idServicoTipo = idServicoTipo;
	}
	
	public RASolicitanteHelper idServicoTipo(Integer idServicoTipo) {
		setIdServicoTipo(idServicoTipo);
		return this;
	}
	
	@SuppressWarnings("rawtypes")
	public Collection getColecaoFone() {
		return colecaoFone;
	}

	@SuppressWarnings("rawtypes")

	public void setColecaoFone(Collection colecaoFone) {
		this.colecaoFone = colecaoFone;
	}

	@SuppressWarnings("rawtypes")

	public RASolicitanteHelper colecaoFone(Collection colecaoFone) {
		setColecaoFone(colecaoFone);
		return this;
	}

	@SuppressWarnings("rawtypes")
	public Collection getColecaoEnderecoSolicitante() {
		return colecaoEnderecoSolicitante;
	}

	@SuppressWarnings("rawtypes")
	public void setColecaoEnderecoSolicitante(Collection colecaoEnderecoSolicitante) {
		this.colecaoEnderecoSolicitante = colecaoEnderecoSolicitante;
	}

	@SuppressWarnings("rawtypes")
	public RASolicitanteHelper colecaoEnderecoSolicitante(Collection colecaoEnderecoSolicitante) {
		setColecaoEnderecoSolicitante(colecaoEnderecoSolicitante);
		return this;
	}
	
	public String getHabilitarCampoSatisfacaoEmail() {
		return habilitarCampoSatisfacaoEmail;
	}
	
	public void setHabilitarCampoSatisfacaoEmail(String habilitarCampoSatisfacaoEmail) {
		this.habilitarCampoSatisfacaoEmail = habilitarCampoSatisfacaoEmail;
	}
	
	public RASolicitanteHelper habilitarCampoSatisfacaoEmail(String habilitarCampoSatisfacaoEmail) {
		setHabilitarCampoSatisfacaoEmail(habilitarCampoSatisfacaoEmail);
		return this;
	}
	
	public String getEnviarEmailSatisfacao() {
		return enviarEmailSatisfacao;
	}
	
	public void setEnviarEmailSatisfacao(String enviarEmailSatisfacao) {
		this.enviarEmailSatisfacao = enviarEmailSatisfacao;
	}
	
	public RASolicitanteHelper enviarEmailSatisfacao(String enviarEmailSatisfacao) {
		setEnviarEmailSatisfacao(enviarEmailSatisfacao);
		return this;
	}
	
	public String getEnderecoEmail() {
		return enderecoEmail;
	}
	
	public void setEnderecoEmail(String enderecoEmail) {
		this.enderecoEmail = enderecoEmail;
	}
	
	public RASolicitanteHelper enderecoEmail(String enderecoEmail) {
		setEnderecoEmail(enderecoEmail);
		return this;
	}
	
}
