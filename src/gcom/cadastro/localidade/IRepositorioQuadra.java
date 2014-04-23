package gcom.cadastro.localidade;

import gcom.micromedicao.bean.NumeroQuadraMinimoMaximoDaRotaHelper;
import gcom.util.ControladorException;
import gcom.util.ErroRepositorioException;

import java.util.Collection;

/**
 * <p>
 * 
 * Title: GCOM
 * </p>
 * <p>
 * 
 * Description: Sistema de Gest�o Comercial
 * </p>
 * <p>
 * 
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * 
 * Company: COMPESA - Companhia Pernambucana de Saneamento
 * </p>
 * 
 * @author Rossiter
 * @created 6 de Setembro de 2005
 * @version 1.0
 */

public interface IRepositorioQuadra {

    /**
     * Pesquisa uma cole��o de quadra com uma query especifica
     * 
     * @param idSetorComercial
     *            Description of the Parameter
     * @return Description of the Return Value
     * @exception ErroRepositorioException
     *                Description of the Exception
     */

    public Collection pesquisarQuadra(int idSetorComercial)
            throws ErroRepositorioException;
    
	/**
	 * M�todo que retorna o maior n�mero da quadra de um setor comercial
	 * 
	 * @author Rafael Corr�a
	 * @date 11/07/2006
	 * 
	 * @param idSetorComercial
	 * @return
	 * @throws ControladorException
	 */
	
	public int pesquisarMaximoCodigoQuadra(Integer idSetorComercial)
			throws ErroRepositorioException;

    /**
     * Pesquisa uma cole��o de quadra com uma query especifica
     * 
     * @param idSetorComercial
     *            Description of the Parameter
     * @return Description of the Return Value
     * @exception ErroRepositorioException
     *                Description of the Exception
     */

    //    public Collection pesquisarQuadraRelatorio(Quadra quadraParametros)
    // throws ErroRepositorioException;
	
	public Collection pesquisarQuadrasPorSetorComercialFaturamentoGrupo(int localidade, int idSetoresComercial[], 
			Integer idFaturamentoGrupo) throws ErroRepositorioException;
	
	/**
	 * 
	 * [UC608] Efetuar Liga��o de Esgoto sem RA
	 * @author S�vio Luiz
	 * @date 10/09/2007
	 * 
	 * @return
	 */

	public Short pesquisarIndicadorRedeEsgotoQuadra(int idImovel)
			throws ErroRepositorioException;

	/**
	 * Pesquisar quadras de um roteiro empresa 
	 * @param idRoteiroEmpresa
	 * @return cole��o de quadras
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarQuadrasPorRoteiroEmpresa(int idRoteiroEmpresa) throws ErroRepositorioException;
    
    /**
     * @author: Vivianne Sousa
     * @date: 16/05/2008 
     */
    public Collection pesquisarIdQuadraPorSetorComercial(Integer idSetorComercial)
        throws ErroRepositorioException ;
    
    /**
     * [????] Informar Subdivis�es de Rota
     * 
     * Atualiza o campo rota das quadras que pertencem ao intervalo de numero informado e ao setor comercial
     * 
     * @author: Victor Cisneiros
     * @date: 30/09/2008
     */
    public void atualizarRotaDasQuadrasNoIntervaloNumero(
    		int idNovaRota, int idSetorComercial, int numeroInicial, int numeroFinal) throws ErroRepositorioException;
	
    /**
     * [????] Informar Subdivis�es de Rota
     * 
     * @author: Victor Cisneiros
     * @date: 30/09/2008
     */
    public Collection<NumeroQuadraMinimoMaximoDaRotaHelper> pesquisarNumeroQuadraMininoMaximoDaRota(
    		Collection<Integer> idsRotas) throws ErroRepositorioException;
    
    /**
     * [UC0???] Informar Subdivis�es de Rota
     * 
     * @author: Victor Cisneiros
     * @date: 27/10/2008
     */
    public Integer pesqisarQuantidadeQuadrasDaRota(
    		Integer idRota, Integer quadraInicial, Integer quadraFinal) throws ErroRepositorioException;
    
    /**
	 * Integra��o com o conceito de face da quadra
	 *
	 * @author Raphael Rossiter
	 * @date 21/05/2009
	 *
	 * @param idImovel
	 * @return Quadra
	 * @throws ErroRepositorioException
	 */
	public Quadra pesquisarQuadra(Integer idImovel)throws ErroRepositorioException ;
	
	/**
	 * Integra��o com o conceito de face da quadra
	 *
	 * @author Raphael Rossiter
	 * @date 21/05/2009
	 *
	 * @param idImovel
	 * @return QuadraFace
	 * @throws ErroRepositorioException
	 */
	public QuadraFace pesquisarQuadraFace(Integer idImovel)throws ErroRepositorioException ;

}
