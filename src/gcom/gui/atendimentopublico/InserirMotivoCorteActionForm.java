package gcom.gui.atendimentopublico;

import org.apache.struts.validator.ValidatorActionForm;

/**
 * < <Descri��o da Classe>>
 * 
 * @author Vin�cius Medeiros
 * 
 */
public class InserirMotivoCorteActionForm extends ValidatorActionForm {
	private static final long serialVersionUID = 1L;

	String descricao;

	String IndicadorUso;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getIndicadorUso() {
		return IndicadorUso;
	}

	public void setIndicadorUso(String indicadorUso) {
		IndicadorUso = indicadorUso;
	}
}
