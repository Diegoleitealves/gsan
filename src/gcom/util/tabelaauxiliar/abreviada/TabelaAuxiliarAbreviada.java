package gcom.util.tabelaauxiliar.abreviada;

import gcom.util.tabelaauxiliar.TabelaAuxiliar;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * < <Descri��o da Classe>>
 * 
 * @author Administrador
 */
public abstract class TabelaAuxiliarAbreviada extends TabelaAuxiliar {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String descricaoAbreviada;

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
    public TabelaAuxiliarAbreviada(Integer id, String descricao,
            String descricaoAbreviada, Short indicadorUso, Date ultimaAlteracao) {
        super(id, descricao, indicadorUso, ultimaAlteracao);
        this.descricaoAbreviada = descricaoAbreviada;
    }

    /**
     * default constructor
     */
    public TabelaAuxiliarAbreviada() {
    }

    /**
     * Retorna o valor de descricaoAbreviada
     * 
     * @return O valor de descricaoAbreviada
     */
    public String getDescricaoAbreviada() {
        return descricaoAbreviada;
    }

    /**
     * Seta o valor de descricaoAbreviada
     * 
     * @param descricaoAbreviada
     *            O novo valor de descricaoAbreviada
     */
    public void setDescricaoAbreviada(String descricaoAbreviada) {
        this.descricaoAbreviada = descricaoAbreviada;
    }

    /**
     * < <Descri��o do m�todo>>
     * 
     * @return Descri��o do retorno
     */
    public String toString() {
        return new ToStringBuilder(this).append("id", getId()).append(
                "descricao", getDescricao()).append("descricaoAbreviada",
                getDescricaoAbreviada()).toString();
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
        if (!(other instanceof TabelaAuxiliarAbreviada)) {
            return false;
        }
        TabelaAuxiliarAbreviada castOther = (TabelaAuxiliarAbreviada) other;

        return new EqualsBuilder().append(this.getId(), castOther.getId())
                .append(this.getDescricao(), castOther.getDescricao()).append(
                        this.getDescricaoAbreviada(),
                        castOther.getDescricaoAbreviada()).isEquals();
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
