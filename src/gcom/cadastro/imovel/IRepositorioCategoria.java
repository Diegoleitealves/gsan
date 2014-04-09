package gcom.cadastro.imovel;

import gcom.util.ErroRepositorioException;

import java.util.Collection;

/**
 * 
 * Title: GCOM
 * 
 * Description: Interface do Reposit�rio de Categoria
 * 
 * Copyright: Copyright (c) 2005
 * 
 * Company: COMPESA - Companhia Pernambucana de Saneamento
 * 
 * @author Pedro Alexandre
 * @created 13 de Janeiro de 2006
 * @version 1.0
 */
public interface IRepositorioCategoria {

    /**
     * Pesquisa uma cole��o de categorias
     * @return Cole��o de Categorias
     * @exception ErroRepositorioException Erro no hibernate
     */
    public Collection<Categoria> pesquisarCategoria() throws ErroRepositorioException;
    
    
    /**
     * 
     * @return Quantidade de categorias cadastradas no BD
     * @throws ErroRepositorioException
     */
    public Object pesquisarObterQuantidadeCategoria() throws ErroRepositorioException;
    
    
    /**
	 * 
	 * Autor: Raphael Rossiter
	 * Data: 18/04/2007
	 * 
	 * @return Quantidade de subcategorias
	 * @throws ErroRepositorioException
	 */
	public Object pesquisarObterQuantidadeSubcategoria()
			throws ErroRepositorioException ;
	
	/**
	 * 
	 * Autor: S�vio Luiz
	 * Data: 07/05/2009
	 * 
	 * Pesquisa o Fator de Economia
	 * 
	 * 
	 */
	public Short pesquisarFatorEconomiasCategoria(Integer idCategoria)
			throws ErroRepositorioException;

}
