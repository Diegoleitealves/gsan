package gcom.seguranca.transacao;

import gcom.cadastro.atualizacaocadastral.bean.ConsultarMovimentoAtualizacaoCadastralHelper;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.ClienteFone;
import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.imovel.ImovelSubcategoria;
import gcom.gui.cadastro.atualizacaocadastral.FiltrarAlteracaoAtualizacaoCadastralActionHelper;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ControladorException;
import gcom.util.ErroRepositorioException;

import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

/**
 * < <Descri��o da Classe>>
 * 
 * @author Administrador
 * @created 22 de Julho de 2005
 */
public interface IRepositorioTransacao {


	/**
	 * M�todo que consulta os usuario alteracao de uma determinada operacao com as restricoes passadas
	 *  
	 * @param idOperacao
	 * @param idUsuario
	 * @param dataInicial
	 * @param dataFinal
	 * @param horaInicial
	 * @param hotaFinal
	 * @param idTabela
	 * @param idTabelaColuna
	 * @param id
	 * @return
	 * @throws ControladorException
	 * 
	 * @author thiago toscano
	 * @date 17/02/2006
	 */	
	public Collection pesquisarUsuarioAlteracaoDasOperacoesEfetuadas(Integer idUsuarioAcao,
			Integer idOperacao, Integer idUsuario,Date dataInicial, Date dataFinal, 
			Date horaInicial, Date horaFinal, Hashtable<String, String> argumentos, 
			Integer id1, String unidadeNegocio) 
		throws ErroRepositorioException;
	
	public Collection pesquisarUsuarioAlteracaoDasOperacoesEfetuadasHql(Integer idUsuarioAcao,
			String[] idOperacoes, String idUsuario,Date dataInicial, Date dataFinal, 
			Date horaInicial, Date horaFinal, Hashtable<String,String> argumentos, 
			Integer id1, Integer numeroPagina, String unidadeNegocio)
		throws ErroRepositorioException;
	
	public Collection pesquisarUsuarioAlteracaoDasOperacoesEfetuadasHqlRelatorio(Integer idUsuarioAcao,
			 String[] idOperacoes, String idUsuario,Date dataInicial, Date dataFinal, 
			Date horaInicial, Date horaFinal, Hashtable<String,String> argumentos, 
			Integer id1, String unidadeNegocio)
		throws ErroRepositorioException;
	
	public Integer pesquisarUsuarioAlteracaoDasOperacoesEfetuadasHqlCount(Integer idUsuarioAcao,
			 String[] idOperacoes, String idUsuario,Date dataInicial, Date dataFinal, 
			Date horaInicial, Date horaFinal, Hashtable<String,String> argumentos, 
			Integer id1, String unidadeNegocio) 
		throws ErroRepositorioException;

	public Collection pesquisarOperacaoOrdemExibicao(int[] idTabelaColuna, int idOperacao) 
		throws ErroRepositorioException;
	
	/**
	 * Pesquisa a quantidade de registros na tabela de Opera��o Efetuada
	 * para os argumentos passados.
	 * 		
	 * @author Yara Taciane
	 * @date 15/07/2008
	 *
	 * @param idOperacao
	 * @param argumentoValor
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarOperacaoEfetuada(Integer idOperacao,
			Integer argumentoValor, Integer id2)throws ErroRepositorioException;

	/**
	 * 
	 * Pesquisa os registros na TabelaLinhaColunaAlteracao para o argumento passado.
	 * 
	 * @author Yara Taciane
	 * @date 15/07/2008
	 *
	 * @param idTabelaColuna
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarTabelaLinhaColunaAlteracao(Integer idObjetoAlterado, 
			Integer idTabelaColuna)throws ErroRepositorioException;
	
	/**
	 * Consultar Movimento Atualiza��o Cadastral 
	 * 
	 * @author Ana Maria
	 * @date 02/05/2009
	 * 
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection<ConsultarMovimentoAtualizacaoCadastralHelper> pesquisarMovimentoAtualizacaoCadastral(
			FiltrarAlteracaoAtualizacaoCadastralActionHelper helper)throws ErroRepositorioException;
	
	/**
	 * @author Ivan Sergio
	 * @date 03/06/2009
	 *
	 * @param idRegistroAlterado
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List consultarDadosTabelaColunaAtualizacaoCadastral(
			Long idRegistroAlterado,
			Integer idArquivo, Integer idImovel, Long idCliente,Integer idTipoAlteracao) throws ErroRepositorioException;
	
	/**
	 * @author Ivan Sergio
	 * @date 12/06/2009
	 *
	 * @param idAtualizacaoCadastral
	 * @param indicador
	 * @throws ErroRepositorioException
	 */
	public void atualizarIndicadorAutorizacaoColunaAtualizacaoCadastral(Integer idAtualizacaoCadastral,	Short indicador, Usuario usuario) throws ErroRepositorioException;
	
	/**
	 * @author Ana Maria
	 * @date 16/06/2009
	 *
	 * @param idArgumento
	 * @param indicador
	 * @throws ErroRepositorioException
	 */
	public void atualizarIndicadorAutorizacaoTabelaAtualizacaoCadastral(
			Integer idArgumento, Short indicador) throws ErroRepositorioException;
	
	/**
	 * @author Ana Maria
	 * @date 25/06/2009
	 *
	 * @param idEmpresa
	 * @param idArquivo
	 * @param idLeiturista
	 * @return List
	 * @throws ErroRepositorioException
	 */
	public List pesquisarRegistroAutorizadoTabelaAtualizacaoCadastral(
			String idEmpresa, String idArquivo, String idLeiturista) throws ErroRepositorioException;
	
	/**
	 * @author Ana Maria
	 * @date 25/06/2009
	 *
	 * @param idTabelaAtualizacaoCadastral
	 * @return List
	 * @throws ErroRepositorioException
	 */
	public List pesquisarRegistroAutorizadoTabelaColunaAtualizacaoCadastral(
			Integer idTabelaAtualizacaoCadastral) throws ErroRepositorioException;
	
	/**
	 * @author Genival Barbosa
	 * @date 27/07/2009
	 *
	 * @param idAtualizacaoCadastral
	 * @param indicador
	 * @throws ErroRepositorioException
	 */
	public void atualizarIndicadorAutorizacaoAtualizacaoCadastral(
			Integer idAtualizacaoCadastral,
			Short indicador) throws ErroRepositorioException;
	
	/**
	 * CRC2103 - [FS0026] - Verificar existencia de operacao inserir/manter cliente pendente de atualizacao do imovel.
	 * @author Ivan Sergio
	 * @date 24/07/2009
	 *
	 * @param idObjeto
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer verificarOperacaoPendente(Integer idObjeto, Integer idUsuario) throws ErroRepositorioException;
	
	/**
	 * CRC2103 - [FS0026] - Realiza a alteracao em OperacaoEfetuada em cliente
	 * pendente de atualizacao do imovel.
	 *
	 * @author Ivan Sergio
	 * @date 24/07/2009
	 *
	 * @param idOperacaoEfetuada
	 * @param idGrupoAtributo
	 * @throws ErroRepositorioException
	 */
	public void atualizarOperacaoEfetuadaPendente(Integer idOperacaoEfetuada, Integer idGrupoAtributo) throws ErroRepositorioException;
	
	/**
	 * CRC2103 - [FS0026] - Realiza a alteracao em TabelaLinhaAlteracao em cliente
	 * pendente de atualizacao do imovel. 
	 *
	 * @author Ivan Sergio
	 * @date 24/07/2009
	 *
	 * @param idOperacaoEfetuada
	 * @param idImovel
	 * @throws ErroRepositorioException
	 */
	public void atualizarTabelaLinhaAlteracaoPendente(Integer idOperacaoEfetuada, Integer idImovel) throws ErroRepositorioException;
	
	public void atualizarClienteRelacaoTipoAtualizacaoCadastral(Integer codigoImovel, Integer codigoCliente) throws ErroRepositorioException;
	
	public TabelaColunaAtualizacaoCadastral pesquisarTabelaColunaAtualizacaoCadastral(Integer idAtualizacaoCadastral) throws ErroRepositorioException;
	
	public boolean existeAlteracaoNaoAprovadaParaImovel(Integer idImovel) throws ErroRepositorioException;
	
	public Cliente obterClientePeloCPF(String cpf,Integer idCliente) throws ErroRepositorioException;
	
	public Cliente obterClientePeloCNPJ(String cnpj,Integer idCliente) throws ErroRepositorioException;
	
	public String obterValorAtualTabelaColunaAtualizacaoCadastral(Integer idAtualizacaoCadastral, Integer idTabelaColuna) throws ErroRepositorioException;
	
	public ImovelSubcategoria recuperaImovelSubcategoriaAtualizacaoCadastral(Integer idAtualizacaoCadastral) throws ErroRepositorioException;
	
	/**
	 * [[UC1165] - Confirmar Altera��es Cadastrais
	 * 
	 * Verifica se existe na base algum rela��o de cliente im�vel que seja com outro im�vel
	 * 
	 * @author S�vio Luiz
	 * @date 06/05/2011
	 *
	 * @return Collection
	 * @throws ControladorException
	 */
	public Collection pesquisarClienteImovelDiferenteImovel(Integer idImovel,Integer idCliente)
		throws ErroRepositorioException;
	
	/**
	 * [[UC1165] - Confirmar Altera��es Cadastrais
	 * 
	 * @author S�vio Luiz
	 * @date 11/05/2011
	 * 
	 * @return
	 * @throws ErroRepositorioException
	 */
	public ClienteRelacaoTipo recuperaTipoRelacaoClienteAtualizacaoCadastral(Integer idAtualizacaoCadastral)
			throws ErroRepositorioException;
	
	/**
	 * [[UC1165] - Confirmar Altera��es Cadastrais
	 * 
	 * @author S�vio Luiz
	 * @date 16/05/2011
	 * 
	 * @return
	 * @throws ErroRepositorioException
	 */
	public ClienteFone recuperaClienteFoneAtualizacaoCadastral(Integer idAtualizacaoCadastral)
			throws ErroRepositorioException;
	
	/**
	 * [[UC1165] - Confirmar Altera��es Cadastrais
	 * 
	 * @author S�vio Luiz
	 * @date 20/04/2011
	 * 
	 * @return
	 * @throws ErroRepositorioException
	 */
	public String obterValorAnteriorTabelaColunaAtualizacaoCadastral(Integer idAtualizacaoCadastral, Integer idTabelaColuna)
			throws ErroRepositorioException;
	
	/**
	 * [[UC1165] - Confirmar Altera��es Cadastrais
	 * 
	 * @author S�vio Luiz
	 * @date 20/04/2011
	 * 
	 * @return
	 * @throws ErroRepositorioException
	 */
	public TabelaAtualizacaoCadastral obterIdTabelaAtualizacaoCadastralPorCliente(Integer idCliente, Integer idTabelaColuna)
			throws ErroRepositorioException;
}
