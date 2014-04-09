package gcom.gui.atendimentopublico.registroatendimento;

import org.apache.struts.validator.ValidatorActionForm;


/**
 * Esta classe tem por finalidade gerar o formul�rio que receber� os par�metros
 * para cria��o da rela��o num�rica
 * 
 * @author Raphael Rossiter
 * @date 06/11/2006
 */
public class GerarNumeracaoManualRegistroAtendimentoActionForm extends ValidatorActionForm {
	private static final long serialVersionUID = 1L;
	private String ultimoValorGerado;
	
	private String quantidade;
	
	private String idUnidadeOrganizacional;
	
	private String descricaoUnidadeOrganizacional;

	public String getDescricaoUnidadeOrganizacional() {
		return descricaoUnidadeOrganizacional;
	}

	public void setDescricaoUnidadeOrganizacional(
			String descricaoUnidadeOrganizacional) {
		this.descricaoUnidadeOrganizacional = descricaoUnidadeOrganizacional;
	}

	public String getIdUnidadeOrganizacional() {
		return idUnidadeOrganizacional;
	}

	public void setIdUnidadeOrganizacional(String idUnidadeOrganizacional) {
		this.idUnidadeOrganizacional = idUnidadeOrganizacional;
	}

	public String getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(String quantidade) {
		this.quantidade = quantidade;
	}

	public String getUltimoValorGerado() {
		return ultimoValorGerado;
	}

	public void setUltimoValorGerado(String ultimoValorGerado) {
		this.ultimoValorGerado = ultimoValorGerado;
	}

}
