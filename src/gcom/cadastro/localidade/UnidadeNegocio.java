package gcom.cadastro.localidade;

import gcom.cadastro.cliente.Cliente;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;
import java.util.Date;

/**
 * 
 * Entidade de Unidade de Negocio 
 *
 * @author Rafael Santos
 * @since 11/10/2006 
 */
public class UnidadeNegocio extends ObjetoTransacao {



	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** identifier field */
    private Integer id;

	/** persistent field */
	private String nome;
    
	/** persistent field */
	private String nomeAbreviado;
    
	/** nullable persistent field */
	private Short indicadorUso;
    
	/** persistent field */
	private gcom.cadastro.localidade.GerenciaRegional gerenciaRegional;
    
    /** nullable persistent field */
    private Date ultimaAlteracao;
    
    /** nullable persistent field */
    private String cnpj;
    
    private Cliente cliente;

    
    public Cliente getCliente() {
        return cliente;
    }

    
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String[] retornaCamposChavePrimaria(){
		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}
	
	public Filtro retornaFiltro(){
		FiltroUnidadeNegocio filtroUnidadeNegocio = new FiltroUnidadeNegocio();
		
		filtroUnidadeNegocio.adicionarParametro(new ParametroSimples(FiltroUnidadeNegocio.ID, this.getId()));		
		return filtroUnidadeNegocio; 
	}

	/**
	 * @return Retorna o campo gerenciaRegional.
	 */
	public gcom.cadastro.localidade.GerenciaRegional getGerenciaRegional() {
		return gerenciaRegional;
	}

	/**
	 * @param gerenciaRegional O gerenciaRegional a ser setado.
	 */
	public void setGerenciaRegional(
			gcom.cadastro.localidade.GerenciaRegional gerenciaRegional) {
		this.gerenciaRegional = gerenciaRegional;
	}

	/**
	 * @return Retorna o campo id.
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id O id a ser setado.
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return Retorna o campo indicadorUso.
	 */
	public Short getIndicadorUso() {
		return indicadorUso;
	}

	/**
	 * @param indicadorUso O indicadorUso a ser setado.
	 */
	public void setIndicadorUso(Short indicadorUso) {
		this.indicadorUso = indicadorUso;
	}

	/**
	 * @return Retorna o campo nome.
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome O nome a ser setado.
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return Retorna o campo nomeAbreviado.
	 */
	public String getNomeAbreviado() {
		return nomeAbreviado;
	}

	/**
	 * @param nomeAbreviado O nomeAbreviado a ser setado.
	 */
	public void setNomeAbreviado(String nomeAbreviado) {
		this.nomeAbreviado = nomeAbreviado;
	}

	/**
	 * @return Retorna o campo ultimaAlteracao.
	 */
	public Date getUltimaAlteracao() {
		return ultimaAlteracao;
	}
	

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	/**
	 * @param ultimaAlteracao O ultimaAlteracao a ser setado.
	 */
	public void setUltimaAlteracao(Date ultimaAlteracao) {
		this.ultimaAlteracao = ultimaAlteracao;
	}

	public UnidadeNegocio() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UnidadeNegocio(Integer id, String nome, String nomeAbreviado, Short indicadorUso, GerenciaRegional gerenciaRegional, Date ultimaAlteracao) {
		super();
		// TODO Auto-generated constructor stub
		this.id = id;
		this.nome = nome;
		this.nomeAbreviado = nomeAbreviado;
		this.indicadorUso = indicadorUso;
		this.gerenciaRegional = gerenciaRegional;
		this.ultimaAlteracao = ultimaAlteracao;
	}
	
	/**
	 * Retorna o valor de cnpjFormatado
	 * 
	 * @return O valor de cnpjFormatado
	 */
	public String getCnpjFormatado() {
		String cnpjFormatado = this.cnpj;
		String zeros = "";
		
		if (cnpjFormatado != null) {
			
			for (int a = 0; a < (14 - cnpjFormatado.length()); a++) {
				zeros = zeros.concat("0");
			}
			// concatena os zeros ao numero
			// caso o numero seja diferente de nulo
			cnpjFormatado = zeros.concat(cnpjFormatado);
			
			cnpjFormatado = cnpjFormatado.substring(0, 2) + "."
					+ cnpjFormatado.substring(2, 5) + "."
					+ cnpjFormatado.substring(5, 8) + "/"
					+ cnpjFormatado.substring(8, 12) + "-"
					+ cnpjFormatado.substring(12, 14);
		}
		
		return cnpjFormatado;
	}
	
	@Override
	public String getDescricaoParaRegistroTransacao() {
		return getNome();
	}
    

}
