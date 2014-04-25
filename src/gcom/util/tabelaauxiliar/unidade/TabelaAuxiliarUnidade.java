package gcom.util.tabelaauxiliar.unidade;

import gcom.atendimentopublico.ordemservico.MaterialUnidade;
import gcom.util.tabelaauxiliar.abreviada.TabelaAuxiliarAbreviada;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * < <Descri��o da Classe>>
 * 
 * @author R�mulo Aur�lio
 */
public abstract class TabelaAuxiliarUnidade extends TabelaAuxiliarAbreviada {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private MaterialUnidade materialUnidade;

	/**
	 * full constructor
	 * 
	 * @param id
	 *            Descri��o do par�metro
	 * @param descricao
	 *            Descri��o do par�metro
	 * @param descricaoAbreviada
	 *            Descri��o do par�metro
	 */
	

	public TabelaAuxiliarUnidade(Integer id, String descricao,
			String descricaoAbreviada, Short indicadorUso,MaterialUnidade materialUnidade,Date ultimaAlteracao) {
		super(id, descricao, descricaoAbreviada, indicadorUso, ultimaAlteracao);
		this.materialUnidade = materialUnidade;
	}

	/**
	 * default constructor
	 */
	public TabelaAuxiliarUnidade() {
	}

	/**
	 * @return Retorna o campo unidade.
	 */
	public MaterialUnidade getMaterialUnidade() {
		return materialUnidade;
	}

	/**
	 * @param unidade
	 *            O unidade a ser setado.
	 */
	public void setMaterialUnidade(MaterialUnidade materialUnidade) {
		this.materialUnidade = materialUnidade;
	}

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @return Descri��o do retorno
	 */
	public String toString() {
		return new ToStringBuilder(this).append("id", getId()).append(
				"descricao", getDescricao()).append("descricaoAbreviada",
				getDescricaoAbreviada()).append("MaterialUnidade", getMaterialUnidade())
				.toString();
	}

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param other
	 *            Descri��o do par�metro
	 * @return Descri��o do retorno
	 */
	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if (!(other instanceof TabelaAuxiliarUnidade)) {
			return false;
		}
		TabelaAuxiliarUnidade castOther = (TabelaAuxiliarUnidade) other;

		return new EqualsBuilder().append(this.getId(), castOther.getId())
				.append(this.getDescricao(), castOther.getDescricao()).append(
						this.getDescricaoAbreviada(),
						castOther.getDescricaoAbreviada()).append(
						this.getMaterialUnidade(), castOther.getMaterialUnidade()).isEquals();
	}

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @return Descri��o do retorno
	 */
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).append(getDescricao())
				.append(getDescricaoAbreviada()).toHashCode();
	}

}
