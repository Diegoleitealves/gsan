package gcom.cadastro.localidade;

import gcom.cadastro.endereco.LogradouroBairro;
import gcom.cadastro.endereco.LogradouroCep;
import gcom.util.ControladorException;
import gcom.util.ErroRepositorioException;

import java.util.Collection;

/**
 * 
 * Title: GCOM
 * 
 * Description: Interface do Reposit�rio de Localidade
 * 
 * Copyright: Copyright (c) 2005
 * 
 * Company: COMPESA - Companhia Pernambucana de Saneamento
 * 
 * @author Pedro Alexandre
 * @created 13 de Janeiro de 2006
 * @version 1.0
 */
public interface IRepositorioLocalidade {

    /**
	 * Pesquisa uma cole��o de localidades por ger�ncia regional
	 * 
	 * @param idGerenciaRegional
	 *            C�digo da ger�ncia regional solicitada
	 * @return Cole��o de Localidades da Ger�ncia Regional solicitada
	 * @exception ErroRepositorioException
	 *                Erro no hibernate
	 */
    public Collection<Localidade> pesquisarLocalidadePorGerenciaRegional(int idGerenciaRegional) throws ErroRepositorioException;
    
    /**
	 * Obt�m o id da localidade
	 * 
	 * @author S�vio Luiz
	 * @date 08/03/2006
	 * 
	 * @param idImovel
	 * @return Um integer que representa o id da localidade
	 * @throws ControladorException
	 */
	public Integer pesquisarIdLocalidade(Integer idImovel) throws ErroRepositorioException;

	 /**
	 * M�todo que retorna o maior id da Localidade
	 * 
	 * @author Vivianne Sousa
	 * @date 12/07/2006
	 * 
	 * @return
	 * @throws ControladorException
	 */   
    public int pesquisarMaximoIdLocalidade() throws ErroRepositorioException;
    
	/**
	 * Pesquisa uma localidade pelo id
	 * 
	 * @author Rafael Corr�a
	 * @date 01/08/2006
	 * 
	 * @return Localidade
	 * @exception ErroRepositorioException
	 *                Erro no hibernate
	 */
	public Object[] pesquisarObjetoLocalidadeRelatorio(
			Integer idLocalidade) throws ErroRepositorioException;
	
	public Integer verificarExistenciaLocalidade(
			Integer idLocalidade) throws ErroRepositorioException;
	
	
	/**
	 * Atualiza logradouroBairro de um ou mais im�veis  
	 * 
	 * [UC0] Atualizar Logradouro
	 * 
	 * @author Raphael Rossiter
	 * @date 22/02/2007
	 * 
	 * @param 
	 * @return void
	 */
	public void atualizarLogradouroBairroGerenciaRegional(LogradouroBairro logradouroBairroAntigo, 
			LogradouroBairro logradouroBairroNovo) throws ErroRepositorioException ;
	
	/**
	 * Atualiza logradouroCep de um ou mais im�veis  
	 * 
	 * [UC0] Atualizar Logradouro
	 * 
	 * @author Raphael Rossiter
	 * @date 22/02/2007
	 * 
	 * @param 
	 * @return void
	 */
	public void atualizarLogradouroCepGerenciaRegional(LogradouroCep logradouroCepAntigo, 
			LogradouroCep logradouroCepNovo) throws ErroRepositorioException ;
	
	/**
	 * Atualiza logradouroBairro de um ou mais im�veis  
	 * 
	 * [UC0] Atualizar Logradouro
	 * 
	 * @author Raphael Rossiter
	 * @date 22/02/2007
	 * 
	 * @param 
	 * @return void
	 */
	public void atualizarLogradouroBairro(LogradouroBairro logradouroBairroAntigo, 
			LogradouroBairro logradouroBairroNovo) throws ErroRepositorioException ;
	
	/**
	 * Atualiza logradouroCep de um ou mais im�veis  
	 * 
	 * [UC0] Atualizar Logradouro
	 * 
	 * @author Raphael Rossiter
	 * @date 22/02/2007
	 * 
	 * @param 
	 * @return void
	 */
	public void atualizarLogradouroCep(LogradouroCep logradouroCepAntigo, 
			LogradouroCep logradouroCepNovo) throws ErroRepositorioException ;
	
	/**
	 * Atualiza logradouroCep de um ou mais im�veis  
	 * 
	 * [UC0] Atualizar Logradouro
	 * 
	 * @author Raphael Rossiter
	 * @date 22/02/2007
	 * 
	 * @param 
	 * @return void
	 */
	public Collection pesquisarTodosIdLocalidade() throws ErroRepositorioException ;
	
	/**
	 * Obt�m Elo P�lo
	 * @author Ana Maria
	 * @date 10/12/2007
	 *
	 * @throws ControladorException
	 */
	public Collection pesquisarEloPolo()throws ErroRepositorioException;	
	
	
	/**
	 * [UC0242] - Registrar Movimento dos Arrecadadores
	 * 
	 * @author Raphael Rossiter
	 * @date 09/01/2008
	 *
	 * @param idConta
	 * @return Um integer que representa o id da localidade
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarIdLocalidadePorConta(Integer idConta)throws ErroRepositorioException ;
	
	
	/**
	 * [UC0259] - Processar Pagamento com C�digo de Barras
	 * 
	 * @author Raphael Rossiter
	 * @date 11/01/2008
	 *
	 * @param idGuiaPagamento
	 * @return Um integer que representa o id da localidade
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarIdLocalidadePorGuiaPagamento(Integer idGuiaPagamento)throws ErroRepositorioException ;
	
	/**
	 * 
	 * Pesquisar as localidades do munic�pio
	 * 
	 * @author S�vio Luiz
	 * @date 25/02/2008
	 * 
	 */

	public Collection pesquisarLocalidadesMunicipio(Integer idMunicipio) throws ErroRepositorioException;
	
	/**
	 * Pesquisa os ids das localidades que possuem im�veis.
	 *
	 * @author Pedro Alexandre
	 * @date 07/07/2008
	 *
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Integer> pesquisarIdsLocalidadesImoveis()throws ErroRepositorioException ;
	
	/**
	 * [UC0259] - Processar Pagamento com C�digo de Barras
	 * 
	 * @author Raphael Rossiter
	 * @date 26/11/2008
	 *
	 * @param idDebitoACobrar
	 * @return Um integer que representa o id da localidade
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarIdLocalidadePorDebitoACobrar(Integer idDebitoACobrar)throws ErroRepositorioException ;
	
	/**
	 * [UC0242] - Registrar Movimento dos Arrecadadores
	 * 
	 * @author Raphael Rossiter
	 * @date 09/01/2008
	 *
	 * @param idConta
	 * @return Um integer que representa o id da localidade
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarIdLocalidadePorContaHistorico(Integer idContaHistorico)throws ErroRepositorioException ;
	
	/**
	 * [UC0259] - Processar Pagamento com C�digo de Barras
	 * 
	 * @author Raphael Rossiter
	 * @date 11/01/2008
	 *
	 * @param idGuiaPagamento
	 * @return Um integer que representa o id da localidade
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarIdLocalidadePorGuiaPagamentoHistorico(Integer idGuiaPagamentoHistorico)throws ErroRepositorioException ;
	
	/**
	 * [UC0259] - Processar Pagamento com C�digo de Barras
	 * 
	 * @author Raphael Rossiter
	 * @date 26/11/2008
	 *
	 * @param idDebitoACobrar
	 * @return Um integer que representa o id da localidade
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarIdLocalidadePorDebitoACobrarHistorico(Integer idDebitoACobrarHistorico)throws ErroRepositorioException ;

	/**
	 * Pesquisa os ids de Todas as localidaes.
	 *
	 * @author Hugo Leonardo
	 * @date 08/07/2010
	 *
	 * @return
	 * @throws ControladorException
	 */
	public Collection pesquisarIdsLocalidades() throws ErroRepositorioException;
	
}
