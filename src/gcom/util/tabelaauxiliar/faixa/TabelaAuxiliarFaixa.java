package gcom.util.tabelaauxiliar.faixa;

import gcom.util.tabelaauxiliar.TabelaAuxiliarAbstrata;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * < <Descri��o da Classe>>
 * 
 * @author Administrador
 */
public abstract class TabelaAuxiliarFaixa extends TabelaAuxiliarAbstrata {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    private String faixaInicial;

    private String faixaFinal;

    /**
     * full constructor
     * 
     * @param id
     *            Descri��o do par�metro
     * @param faixaInical
     *            Descri��o do par�metro
     * @param faixaFinal
     *            Descri��o do par�metro
     */
    public TabelaAuxiliarFaixa(Integer id, Date ultimaAlteracao,
            String faixaInical, String faixaFinal) {
        super.setId(id);
        super.setUltimaAlteracao(ultimaAlteracao);
        this.faixaInicial = faixaInical;
        this.faixaFinal = faixaFinal;
    }

    /**
     * default constructor
     */
    public TabelaAuxiliarFaixa() {
    }

    /**
     * Retorna o valor de faixaFinal
     * 
     * @return O valor de faixaFinal
     */
    public String getFaixaFinal() {
        return faixaFinal;
    }

    /**
     * Seta o valor de faixaFinal
     * 
     * @param faixaFinal
     *            O novo valor de faixaFinal
     */
    public void setFaixaFinal(String faixaFinal) {
        this.faixaFinal = faixaFinal;
    }

    /**
     * Seta o valor de faixaInicial
     * 
     * @param faixaInicial
     *            O novo valor de faixaInicial
     */
    public void setFaixaInicial(String faixaInicial) {
        this.faixaInicial = faixaInicial;
    }

    /**
     * Retorna o valor de faixaInicial
     * 
     * @return O valor de faixaInicial
     */
    public String getFaixaInicial() {
        return faixaInicial;
    }

    /**
     * < <Descri��o do m�todo>>
     * 
     * @return Descri��o do retorno
     */
    public String toString() {
        return new ToStringBuilder(this).append("id", getId()).append(
                "faixaInicial", getFaixaInicial()).append("faixaFinal",
                getFaixaFinal()).toString();
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
        if (!(other instanceof TabelaAuxiliarFaixa)) {
            return false;
        }
        TabelaAuxiliarFaixa castOther = (TabelaAuxiliarFaixa) other;

        return new EqualsBuilder().append(this.getId(), castOther.getId())
                .append(this.getFaixaInicial(), castOther.getFaixaInicial())
                .isEquals();
    }

    /**
     * < <Descri��o do m�todo>>
     * 
     * @return Descri��o do retorno
     */
    public int hashCode() {
        return new HashCodeBuilder().append(getId()).append(getFaixaInicial())
                .append(getFaixaFinal()).toHashCode();
    }

}
