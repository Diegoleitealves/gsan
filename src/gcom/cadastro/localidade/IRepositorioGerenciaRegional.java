package gcom.cadastro.localidade;

import gcom.util.ErroRepositorioException;

import java.util.Collection;

/**
 * 
 * Title: GCOM
 * 
 * Description: Interface do Reposit�rio de Ger�ncia Regional
 * 
 * Copyright: Copyright (c) 2005
 * 
 * Company: COMPESA - Companhia Pernambucana de Saneamento
 * 
 * @author Pedro Alexandre
 * @created 13 de Janeiro de 2006
 * @version 1.0
 */
public interface IRepositorioGerenciaRegional {

	/**
	 * Pesquisa uma cole��o de ger�ncias regionais
	 * 
	 * @return Cole��o de Ger�ncias Regionais
	 * @exception ErroRepositorioException
	 *                Erro no hibernate
	 */
	public Collection<GerenciaRegional> pesquisarGerenciaRegional()
			throws ErroRepositorioException;

	/**
	 * Pesquisa uma ger�ncia regional pelo id
	 * 
	 * @author Rafael Corr�a
	 * @date 01/08/2006
	 * 
	 * @return Ger�ncia Regional
	 * @exception ErroRepositorioException
	 *                Erro no hibernate
	 */
	public Object[] pesquisarObjetoGerenciaRegionalRelatorio(
			Integer idGerenciaRegional) throws ErroRepositorioException;

    /**
     * Pesquisa o id da ger�ncia regional para a qual a localidade pertence.
     * 
     * [UC0267] Encerrar Arrecada��o do M�s
     * 
     * @author Pedro Alexandre
     * @date 05/01/2007
     * 
     * @param idLocalidade
     * @return
     * @throws ErroRepositorioException
     */
    public Integer pesquisarIdGerenciaParaLocalidade(Integer idLocalidade) throws ErroRepositorioException ;

}
