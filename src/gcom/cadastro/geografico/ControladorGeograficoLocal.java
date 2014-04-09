package gcom.cadastro.geografico;

import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ControladorException;
import gcom.util.ErroRepositorioException;

import java.util.Collection;

/**
 * Declara��o p�blica de servi�os do Session Bean de ControladorCliente
 * 
 * @author S�vio Luiz
 * @created 25 de Abril de 2005
 */
public interface ControladorGeograficoLocal extends javax.ejb.EJBLocalObject {

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param bairro
	 *            Descri��o do par�metro
	 */
    public void atualizarBairro(Bairro bairro,
    		Collection colecaoBairroArea,Collection colecaoBairroAreaRemover,
    		Usuario usuarioLogado) throws ControladorException;

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param codigoSetorComercial
	 *            Descri��o do par�metro
	 * @return Descri��o do retorno
	 * @exception ErroRepositorioException
	 *                Descri��o da exce��o
	 */
	public Collection pesquisarMunicipoPeloSetorComercial(
			String codigoSetorComercial, String idMunicipio)
			throws ControladorException;
	
	
	/**
	 * Verifica se o munic�pio possui CEP por logradouro
	 * 
	 * @author Raphael Rossiter
	 * @date 16/05/2006
	 * 
	 * @param municipio
	 * @return boolean
	 */
	public boolean verificarMunicipioComCepPorLogradouro(Municipio municipio) throws ControladorException ;
	
	/**
	 * M�todo que retorna o maior c�digo do bairro de um munic�pio
	 * 
	 * @author Rafael Corr�a
	 * @date 10/07/2006
	 * 
	 * @param idMunicipio
	 * @return
	 * @throws ControladorException
	 */
	
	public int pesquisarMaximoCodigoBairro(
			Integer idMunicipio)
			throws ControladorException;

	/**
	 * Pesquisa um munic�pio pelo id
	 * 
	 * @author Rafael Corr�a
	 * @date 01/08/2006
	 * 
	 * @return Munic�pio
	 * @exception ErroRepositorioException
	 *                Erro no hibernate
	 */
	public Municipio pesquisarObjetoMunicipioRelatorio(Integer idMunicipio)
			throws ControladorException;
	
	/**
	 * Pesquisa um bairro pelo c�digo e pelo id do munic�pio
	 * 
	 * @author Rafael Corr�a
	 * @date 01/08/2006
	 * 
	 * @return Bairro
	 * @exception ErroRepositorioException
	 *                Erro no hibernate
	 */
	public Bairro pesquisarObjetoBairroRelatorio(Integer codigoBairro, Integer idMunicipio)
			throws ControladorException;
	
	/**
	 * Permite inserir um Municipio
	 * 
	 * [UC0001] Inserir Municipio
	 * 
	 * @author Kassia Albuquerque	
	 * @date 18/12/2006
	 * 
	 */
	public Integer inserirMunicipio(Municipio municipio,Usuario usuarioLogado) throws ControladorException;
	
	/**
     * [UC0035] Inserir Bairro
     * 
     * Insere um objeto do tipo bairro no BD
     * 
     * @author Vivianne Sousa
     * @date 22/12/2006
     * @param bairro
     * @param colecaoBairroArea
     * @return idBairro
     * @throws ControladorException
     */
    public Integer inserirBairro(Bairro bairro,
    		Collection colecaoBairroArea,Usuario usuarioLogado) throws ControladorException;
    
	/**
	 * @author Vivianne Sousa
	 * @date 26/12/2006
	 * 
	 * @return colecao de BairroArea
	 * @exception ErroRepositorioException
	 *                Erro no hibernate
	 */
	public Collection pesquisarBairroArea(Integer idBairro)
		throws ControladorException;
	
	/**
     * Remover Bairro
     * 
     * Remove os bairros e area bairro 
     * selecionados na lista da funcionalidade Manter Bairro 
     * 
     * @author Vivianne Sousa
     * @date 26/12/2006
     * @param bairro
     * @param colecaoBairroArea
     * @return idBairro
     * @throws ControladorException
     */
    public void removerBairro(String[] ids,Usuario usuarioLogado) throws ControladorException;
    
    
	 /**
	 * [UC0006] Manter Municipio
	 * 
	 * 			Filtrar Munic�pio
	 * 
	 * @author Kassia Albuquerque
	 * @date 04/01/2007
	 * 
	 * @param Integer
	 * @return boolean
	 */
	public boolean verificarExistenciaMunicipio(Integer codigoMunicipio)throws ControladorException;
    
	
	/**
	 * [UC0005] Manter Municipio 
	 * 			
	 * 			Remover Municipio
	 * 
	 * @author Kassia Albuquerque
	 * @date 04/01/2007
	 * 
	 * @pparam municpio
	 * @throws ControladorException
	 */
	public void removerMunicipio(String[] ids, Usuario usuarioLogado)throws ControladorException;
	
	
	/**
	 * [UC005] Manter Municipio [SB0001]Atualizar Municipio 
	 * 
	 * @author Kassia Albuquerque
	 * @date 10/01/2007
	 * 
	 * @pparam municipio
	 * @throws ControladorException
	 */
	public void atualizarMunicipio(Municipio municipio,Usuario usuarioLogado) throws ControladorException;
	
	 /**
	 * M�todo que retorna o maior id do Munic�pio
	 * 
	 * @author Rafael Corr�a
	 * @date 24/07/2008
	 * 
	 * @return
	 * @throws ControladorException
	 */   
    public int pesquisarMaximoIdMunicipio() throws ControladorException;
    
    /**
	 * M�todo que retorna o municipio do Imovel
	 * 
	 * @author Hugo Amorim
	 * @date 27/08/2009
	 * 
	 * @return Municipio
	 * @throws ControladorException
	 */  
    public Collection pesquisarMunicipioDoImovel(Integer idImovel) 
    	throws ControladorException;
	
    /**
	 * M�todo repons�vel por retornar todos os munic�pios que possuem alguma
	 * associa��o com uma localidade (localidade.muni_idprincipal != null)
	 * 
	 * @author Diogo Peixoto
	 * @date 26/04/2011
	 * 
	 * @return Collection
	 * @throws ControladorException
	 */  
    public Collection pesquisarMunicipiosAssociadoLocalidade() throws ControladorException;
    
}
