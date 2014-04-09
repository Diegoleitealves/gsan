package gcom.seguranca.acesso;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * Representa uma categoria de funcionalidade que ser� utilizada no sistema e no
 * menu
 *
 * @author   rodrigo
 */
public class FuncionalidadeCategoria implements Serializable {
	
	private static final long serialVersionUID = 1L;
    
	private boolean acessivel = false;	
	
	private Integer id;
    private String nome;
    
    private Long numeroOrdemMenu;
    private Short indicadorUso;
    private Date ultimaAlteracao;
    
    private FuncionalidadeCategoria funcionalidadeCategoriaSuperior;
    private Modulo modulo;

    //Na cole��o de funcionalidades pode existir tamb�m FuncionalidadesCategoria
    private Set elementos;
    
    public final static Integer FUNCIONALIDADE_SUPERIOR_GSAN = new Integer(9);

	public FuncionalidadeCategoria() { }

	public Date getUltimaAlteracao() {
		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao) {
		this.ultimaAlteracao = ultimaAlteracao;
	}

	/**
     * Construtor da classe FuncionalidadeCategoria
     *
     * @param nome  Descri��o do par�metro
     */
    public FuncionalidadeCategoria(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna o valor de elementos
     *
     * @return   O valor de elementos
     */
    public Set getElementos() {
        return elementos;
    }

    /**
     * Seta o valor de elementos
     *
     * @param elementos  O novo valor de elementos
     */
    public void setElementos(Set elementos) {
        this.elementos = elementos;
    }

    /**
     * Retorna o valor de nome
     *
     * @return   O valor de nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Seta o valor de nome
     *
     * @param nome  O novo valor de nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    //Retorna a inst�ncia da categoria
    //O caminho do menu deve ser separado por /
    /**
     * <<Descri��o do m�todo>>
     *
     * @param nomeCategoria  Descri��o do par�metro
     * @return               Descri��o do retorno
     */
    public FuncionalidadeCategoria pesquisarCategoria(String nomeCategoria) {

        FuncionalidadeCategoria retorno = null;

        if (nomeCategoria.equals(this.getNome())) {
            retorno = this;
        } else {
            Iterator iterator = this.elementos.iterator();

            while (iterator.hasNext()) {
                FuncionalidadeCategoria funcionalidadeCategoria = null;
                Object item = iterator.next();

                if (item instanceof Funcionalidade) {
                    continue;
                } else if (item instanceof FuncionalidadeCategoria) {
                    funcionalidadeCategoria = (FuncionalidadeCategoria) item;
                }

                if (funcionalidadeCategoria.getNome().equals(nomeCategoria)) {
                    retorno = funcionalidadeCategoria;
                    break;
                }
            }

        }
        return retorno;
    }

    /**
     * <<Descri��o do m�todo>>
     *
     * @param funcionalidade  Descri��o do par�metro
     */
    public void adicionarFuncionalidadeCategoria(Object funcionalidade) {
        if (!elementos.contains(funcionalidade)) {
            this.elementos.add(funcionalidade);
        }
    }
    
    public boolean adicionarFuncionalidade(Funcionalidade funcionalidade) {
    	
    	boolean adicionou = false;
    	
    	FuncionalidadeCategoria categoria = funcionalidade.getFuncionalidadeCategoria();
    	int pasta = categoria.getId();
    	
    	if (this.id == pasta) {
    		this.elementos.add(funcionalidade);
    		adicionou = true;
    	}

    	return adicionou;
    }

    /**
     * Retorna o valor de acessivel
     *
     * @return   O valor de acessivel
     */
    public boolean isAcessivel() {
        return acessivel;
    }

    /**
     * Seta o valor de acessivel
     *
     * @param acessivel  O novo valor de acessivel
     */
    public void setAcessivel(boolean acessivel) {
        this.acessivel = acessivel;
    }

	public Long getNumeroOrdemMenu() {
		return this.numeroOrdemMenu;
	}
	
	public void setNumeroOrdemMenu(Long numeroOrdemMenu) {
		this.numeroOrdemMenu = numeroOrdemMenu;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public FuncionalidadeCategoria getFuncionalidadeCategoriaSuperior() {
		return funcionalidadeCategoriaSuperior;
	}

	public void setFuncionalidadeCategoriaSuperior(
			FuncionalidadeCategoria funcionalidadeCategoriaSuperior) {
		this.funcionalidadeCategoriaSuperior = funcionalidadeCategoriaSuperior;
	}

	public Short getIndicadorUso() {
		return indicadorUso;
	}

	public void setIndicadorUso(Short indicadorUso) {
		this.indicadorUso = indicadorUso;
	}

	public Modulo getModulo() {
		return modulo;
	}

	public void setModulo(Modulo modulo) {
		this.modulo = modulo;
	}

	
}
