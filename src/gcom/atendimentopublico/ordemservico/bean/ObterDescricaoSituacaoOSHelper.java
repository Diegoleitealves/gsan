package gcom.atendimentopublico.ordemservico.bean;

/**
 * [UC0420] Obter Descri��o da Situa��o da OS
 * 
 * Esta classe tem por finalidade obter a descri��o da situa��o de uma 
 * ordem de servi�o. 
 * 
 * @author Leonardo Regis
 * @date 11/08/2006
 */
public class ObterDescricaoSituacaoOSHelper {

	private String descricaoSituacao;
	
	private String descricaoAbreviadaSituacao;
	
	private String letraIdentificadoraSituacaoOS;
	
	public String getLetraIdentificadoraSituacaoOS() {
		return letraIdentificadoraSituacaoOS;
	}

	public void setLetraIdentificadoraSituacaoOS(
			String letraIdentificadoraSituacaoOS) {
		this.letraIdentificadoraSituacaoOS = letraIdentificadoraSituacaoOS;
	}

	public ObterDescricaoSituacaoOSHelper(){}

	public String getDescricaoAbreviadaSituacao() {
		return descricaoAbreviadaSituacao;
	}

	public void setDescricaoAbreviadaSituacao(String descricaoAbreviadaSituacao) {
		this.descricaoAbreviadaSituacao = descricaoAbreviadaSituacao;
	}

	public String getDescricaoSituacao() {
		return descricaoSituacao;
	}

	public void setDescricaoSituacao(String descricaoSituacao) {
		this.descricaoSituacao = descricaoSituacao;
	}
}
