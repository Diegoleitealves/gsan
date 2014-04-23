package gcom.util.filtro;

/**
 * Representa um filtro que serve como par�metro simples de busca para outro um
 * filtro
 * 
 * @author rodrigo
 */
public class SubFiltro extends FiltroParametro {
	private static final long serialVersionUID = 1L;
    private Filtro filtro;

    /**
     * Construtor da classe SubFiltro
     * 
     * @param filtro
     *            Filtro para ser usado como par�metro
     * @param nomeAtributo
     *            Nome do atributo do objeto que ter� um subFiltro
     */
    public SubFiltro(Filtro filtro, String nomeAtributo) {
        super(nomeAtributo);
        this.filtro = filtro;
    }

    /**
     * Retorna o valor de filtro
     * 
     * @return O valor de filtro
     */
    public Filtro getFiltro() {
        return filtro;
    }

	@Override
	public Object getValor() {
		return filtro;
	}

}
