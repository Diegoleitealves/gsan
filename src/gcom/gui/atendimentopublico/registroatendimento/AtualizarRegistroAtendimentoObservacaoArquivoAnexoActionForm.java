package gcom.gui.atendimentopublico.registroatendimento;

import org.apache.struts.validator.ValidatorForm;

/**
 * Esta classe tem por finalidade gerar o formul�rio que receber� os par�metros
 * para realiza��o da atualiza��o de uma observa��o de um arquivo anexo ao R.A
 * 
 * @author Raphael Rossiter
 * @date 06/08/2009
 */
public class AtualizarRegistroAtendimentoObservacaoArquivoAnexoActionForm extends ValidatorForm{

	private static final long serialVersionUID = 1L;
	
	private String idRegistroAtendimentoAnexo;
	
	private String observacaoAnexoAtualizacao;

	public String getObservacaoAnexoAtualizacao() {
		return observacaoAnexoAtualizacao;
	}

	public void setObservacaoAnexoAtualizacao(String observacaoAnexoAtualizacao) {
		this.observacaoAnexoAtualizacao = observacaoAnexoAtualizacao;
	}

	public String getIdRegistroAtendimentoAnexo() {
		return idRegistroAtendimentoAnexo;
	}

	public void setIdRegistroAtendimentoAnexo(String idRegistroAtendimentoAnexo) {
		this.idRegistroAtendimentoAnexo = idRegistroAtendimentoAnexo;
	}
}
