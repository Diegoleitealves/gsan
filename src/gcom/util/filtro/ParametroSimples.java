package gcom.util.filtro;

/**
 * Representa um par�metro simples de busca de um filtro
 * 
 * @author rodrigo
 */
public class ParametroSimples extends FiltroParametro {
	private static final long serialVersionUID = 1L;
    protected Object valor;

    /**
     * Construtor da classe ParametroSimples
     * 
     * @param nomeAtributo
     *            Nome do atributo de que ser� feita a filtragem
     * @param valor
     *            Valor do filtro
     */
    public ParametroSimples(String nomeAtributo, Object valor) {
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
    public ParametroSimples(String nomeAtributo, Object valor, String conector) {
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
    public ParametroSimples(String nomeAtributo, Object valor, String conector,
            int numeroParametrosIsoladosConector) {
        super(nomeAtributo, conector, numeroParametrosIsoladosConector);
        this.valor = valor;
    }

    /**
     * Retorna o valor de valor
     * 
     * @return O valor de valor
     */
    public Object getValor() {
        return valor;
    }

    /**
     * Seta o valor de valor
     * 
     * @param valor
     *            O novo valor de valor
     */
    public void setValor(Object valor) {
        this.valor = valor;
    }

}
