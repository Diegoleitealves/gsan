package gcom.util.filtro;

import java.util.Collection;

/**
 * 
 * @since 23/08/2010
 * @author Hugo Leonardo
 */
public class ParametroSimplesNotIn extends FiltroParametro {
	private static final long serialVersionUID = 1L;
    protected Collection<? extends Object> valor;

    /**
     * Construtor da classe ParametroSimples
     * 
     * @param nomeAtributo
     *            Nome do atributo de que ser� feita a filtragem
     * @param valor
     *            Valor do filtro
     */
    public ParametroSimplesNotIn(String nomeAtributo, Collection<? extends Object> valor) {
        super(nomeAtributo);
        this.valor = valor;
    }

    /**
     * Construtor da classe ParametroSimples
     * 
     * @param nomeAtributo
     *            Nome do atributo de que ser� feita a filtragem
     * @param valor
     *            Valor do filtro
     * @param conector
     *            Conector da query
     */
    public ParametroSimplesNotIn(String nomeAtributo, Collection<? extends Object> valor, String conector) {
        super(nomeAtributo, conector);
        this.valor = valor;
    }

    /**
     * Construtor da classe ParametroSimples
     * 
     * @param nomeAtributo
     *            Descri��o do par�metro
     * @param valor
     *            Descri��o do par�metro
     * @param conector
     *            Descri��o do par�metro
     * @param numeroParametrosIsoladosConector
     *            Descri��o do par�metro
     */
    public ParametroSimplesNotIn(String nomeAtributo, Collection<? extends Object> valor, String conector,
            int numeroParametrosIsoladosConector) {
        super(nomeAtributo, conector, numeroParametrosIsoladosConector);
        this.valor = valor;
    }

    /**
     * Retorna o valor de valor
     * 
     * @return O valor de valor
     */
    public Collection<? extends Object> getValor() {
        return valor;
    }

    /**
     * Seta o valor de valor
     * 
     * @param valor
     *            O novo valor de valor
     */
    public void setValor(Collection<? extends Object> valor) {
        this.valor = valor;
    }

}
