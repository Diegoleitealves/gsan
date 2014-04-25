package gcom.gerencial.cobranca;

import gcom.gerencial.bean.InformarDadosGeracaoRelatorioConsultaHelper;
import gcom.gerencial.bean.InformarDadosGeracaoResumoAcaoConsultaEventualHelper;
import gcom.gerencial.bean.InformarDadosGeracaoResumoAcaoConsultaHelper;
import gcom.gerencial.cobranca.bean.ResumoPendenciaAcumuladoHelper;
import gcom.gerencial.faturamento.bean.ConsultarResumoSituacaoEspecialHelper;
import gcom.util.ControladorException;
import gcom.util.ErroRepositorioException;

import java.util.Collection;
import java.util.List;



/**
 * 
 * 
 * @author Thiago Toscano
 * @created 19/04/2006
 */
public interface ControladorGerencialCobrancaLocal extends javax.ejb.EJBLocalObject {
	
	/**
	 * M�todo que gera o resumo Resumo Situacao Especial Faturamento  
	 *
	 * [UC0341] 
	 *
	 * @author Thiago Toscano
	 * @date 19/04/2006
	 *
	 */
	public void gerarResumoSituacaoEspecialCobranca(int idLocalidade,
			int idFuncionalidadeIniciada) throws ControladorException;

	/**
	 * M�todo que gera o resumo da pendencia
	 * 
	 * [UC0335] - Gerar Resumo da Pendencia
	 * 
	 * @author Bruno Barros
	 * @date 19/07/2007
	 * 
	 */	
	public void gerarResumoPendencia( int idSetorComercial,
			int idFuncionalidadeIniciada) throws ControladorException;		
	/**
	 * Este caso de uso permite consultar o resumo da pend�ncia, com a op��o de impress�o da  consulta.
	 * Dependendo da op��o de totaliza��o sempre � gerado o relat�rio, sem a fera��o da consulta.
	 *
	 * [UC0338] Consultar Resumo da Pend�ncia
	 *
	 * Gera a lista de pend�ncias das Contas e Guias de Pagamento
	 *
	 * consultarResumoPendencia	
	 *
	 * @author Roberta Costa
	 * @date 24/05/2006
	 *
	 * @param informarDadosGeracaoRelatorioConsultaHelper
	 * @return
	 * @throws ControladorException
	 */
	public List consultarResumoPendencia(InformarDadosGeracaoRelatorioConsultaHelper 
			informarDadosGeracaoRelatorioConsultaHelper) throws ControladorException;
	
	public Collection<ResumoCobrancaSituacaoEspecialConsultaGerenciaRegHelper> recuperaResumoSituacaoEspecialCobranca(ConsultarResumoSituacaoEspecialHelper helper) throws ControladorException;
	
	/**
	 * Este caso de uso permite consultar o resumo da pend�ncia, com a op��o de impress�o da  consulta.
	 * Dependendo da op��o de totaliza��o sempre � gerado o relat�rio, sem a fera��o da consulta.
	 *
	 * [UC0338] Consultar Resumo da Pend�ncia
	 *
	 * Retorna os registro de resumo pend�ncia dividindo em cole��es de categoria RESIDENCIAL, COMERCIAL,
	 * INDUSTRIAL e PUBLICA
	 *
	 * retornaConsultaResumoPendencia
	 *
	 * @author Roberta Costa
	 * @date 31/05/2006
	 *
	 * @param informarDadosGeracaoRelatorioConsultaHelper
	 * @return
	 * @throws ControladorException
	 */
	public Collection<ResumoPendenciaAcumuladoHelper> retornaConsultaResumoPendencia( InformarDadosGeracaoRelatorioConsultaHelper
			informarDadosGeracaoRelatorioConsultaHelper) throws ControladorException;
	
	/**
	 * [UC0489] - Consultar Resumo das A��es de Cobran�a
	 * 
	 * @author Ana Maria
	 * @date 06/11/2006
	 * 
	 * @return CobrancaAcaoHelper
	 * @throws ErroRepositorioException
	 */	
	public Collection consultarResumoCobrancaAcao(InformarDadosGeracaoResumoAcaoConsultaHelper informarDadosGeracaoResumoAcaoConsultaHelper) 
		throws ControladorException;
	
	/**
	 * [UC0489] - Consultar Resumo das A��es de Cobran�a
	 * 
	 * @author Ana Maria
	 * @date 06/11/2006
	 * 
	 * @return CobrancaAcaoHelper
	 * @throws ErroRepositorioException
	 */	
	public Collection consultarResumoCobrancaAcaoPerfil(int anoMesReferencia, Integer idCobrancaAcao, 
			Integer idCobrancaAcaoSituacao, Integer idCobrancaAcaoDebito, Short idIndicador,InformarDadosGeracaoResumoAcaoConsultaHelper informarDadosGeracaoRelatorioConsultaHelper) 
		throws ControladorException;
	
	/**
	 * M�todo que gera resumo indicadores da cobran�a
	 * 
	 * [UC????] - Gerar Resumo Indicadores da Cobran�a
	 * 
	 * @author Rafael Corr�a
	 * @date 25/03/2008
	 * 
	 */
	public void gerarResumoIndicadoresCobranca(int idFuncionalidadeIniciada)
			throws ControladorException;
	
	/**
	 * [UC0489] - Consultar Resumo das A��es de Cobran�a
	 * 
	 * Pesquisa as situa��es de d�bito da situa��o da a��o de acordo com o
	 * indicador antesApos
	 * 
	 * @author S�vio Luiz
	 * @date 06/11/2006
	 * 
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection consultarCobrancaAcaoDebitoComIndicador(
			InformarDadosGeracaoResumoAcaoConsultaHelper informarDadosGeracaoResumoAcaoConsultaHelper,
			Integer idCobrancaAcao, Integer idCobrancaAcaoSituacao,Integer idCobrancaAcaoDebito)throws ControladorException;
	
	/**
	 * [UC0617] Consultar Resumo das A��es de Cobran�a Eventuais
	 * 
	 * Pesquisa as a��es de cobran�a
	 * 
	 * @author S�vio Luiz
	 * @date 26/06/2007
	 * 
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection consultarResumoCobrancaAcaoEventual(
			InformarDadosGeracaoResumoAcaoConsultaEventualHelper informarDadosGeracaoResumoAcaoConsultaEventualHelper)
			throws ControladorException;
	
	/**
	 * [UC0617] Consultar Resumo das A��es de Cobran�a Eventuais
	 * 
	 * Pesquisa as a��es de cobran�a
	 * 
	 * @author S�vio Luiz
	 * @date 26/06/2007
	 * 
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection consultarResumoCobrancaAcaoEventualPerfil(
			Integer idCobrancaAcao,
			Integer idCobrancaAcaoSituacao,
			Integer idCobrancaAcaoDebito,
			Short idIndicador,
			InformarDadosGeracaoResumoAcaoConsultaEventualHelper informarDadosGeracaoResumoAcaoConsultaEventualHelper)
			throws ControladorException;
	
	/**
	 * [UC0617] Consultar Resumo das A��es de Cobran�a Eventuais
	 * 
	 * Pesquisa as a��es de cobran�a
	 * 
	 * @author S�vio Luiz
	 * @date 26/06/2007
	 * 
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection consultarCobrancaAcaoEventualDebitoComIndicador(
			InformarDadosGeracaoResumoAcaoConsultaEventualHelper informarDadosGeracaoResumoAcaoConsultaEventualHelper,
			Integer idCobrancaAcao, Integer idCobrancaAcaoSituacao,
			Integer idCobrancaAcaoDebito) throws ControladorException;
	
	/**
	 * [UC0489] - Consultar Resumo das A��es de Cobran�a
	 * Popup de Motivo de Encerramento 
	 * 
	 * @author Francisco do Nascimento
	 * @date 13/06/2008
	 * 
	 * @return Collection CobrancaAcaoMotivoEncerramentoHelper
	 * @throws ErroRepositorioException
	 */
	public Collection consultarResumoCobrancaAcaoMotivoEncerramento(
			Integer idCobrancaAcao,
			Integer idCobrancaAcaoSituacao,
			InformarDadosGeracaoResumoAcaoConsultaHelper informarDadosGeracaoResumoAcaoConsultaHelper,
			boolean ehExecucao)
			throws ControladorException;
	
	/**
	 * [UC0489] - Consultar Resumo das A��es de Cobran�a
	 * Popup de Retorno de fiscalizacao 
	 * 
	 * @author Francisco do Nascimento
	 * @date 18/06/2008
	 * 
	 * @return Collection ResumoAcaoCobrancaSituacaoAcaoDetalheHelper
	 * @throws ErroRepositorioException
	 */
	public Collection consultarResumoCobrancaAcaoRetornoFiscalizacao(
			Integer idCobrancaAcao,
			Integer idCobrancaAcaoSituacao,
			InformarDadosGeracaoResumoAcaoConsultaHelper informarDadosGeracaoResumoAcaoConsultaHelper)
			throws ControladorException;	

	/**
	 * [UC0617] - Consultar Resumo das A��es de Cobran�a Eventual
	 * Popup de Motivo de Encerramento 
	 * 
	 * @author Francisco do Nascimento
	 * @date 19/06/2008
	 * 
	 * @return Collection CobrancaAcaoMotivoEncerramentoHelper
	 * @throws ErroRepositorioException
	 */
	public Collection consultarResumoCobrancaAcaoMotivoEncerramentoEventual(
			Integer idCobrancaAcao,
			Integer idCobrancaAcaoSituacao,
			InformarDadosGeracaoResumoAcaoConsultaEventualHelper informarDadosGeracaoResumoAcaoConsultaEventualHelper,
			boolean ehExecucao)
			throws ControladorException;
	
	/**
	 * [UC0617] - Consultar Resumo das A��es de Cobran�a Eventual
	 * Popup de Retorno de fiscalizacao 
	 * 
	 * @author Francisco do Nascimento
	 * @date 19/06/2008
	 * 
	 * @return Collection ResumoAcaoCobrancaSituacaoAcaoDetalheHelper
	 * @throws ErroRepositorioException
	 */
	public Collection consultarResumoCobrancaAcaoRetornoFiscalizacaoEventual(
			Integer idCobrancaAcao,
			Integer idCobrancaAcaoSituacao,
			InformarDadosGeracaoResumoAcaoConsultaEventualHelper informarDadosGeracaoResumoAcaoConsultaEventualHelper)
			throws ControladorException;
	
	/**
     * M�todo que gera o resumo da pendencia Por Ano
     * 
     * @author Fernando Fontelles Filho
     * @date 23/03/2010
     */
    public void gerarResumoPendenciaPorAno(int idSetor, int idFuncionalidadeIniciada)
            throws ControladorException;
	
    /**
     * 
     * @author Arthur Carvalho
     * @date 30/09/2010
     * @param idLocalidade
     * @param idFuncionalidadeIniciada
     * @throws ControladorException
     */
    public void gerarGuiaPagamentoPorClienteResumoPendencia( int idLocalidade, int idFuncionalidadeIniciada) throws ControladorException;
    
    /**
     * [UC0489] - Consultar Resumo das A��es de Cobran�a Popup de Motivo de
     * Encerramento
     * 
     * @author Ivan Sergio
     * @date 23/12/2010
     * @return Collection CobrancaAcaoMotivoEncerramentoHelper
     * @throws ErroRepositorioException
     */
    public Collection consultarResumoCobrancaAcaoTipoCorte(
            Integer idCobrancaAcao,
            Integer idCobrancaAcaoSituacao,
            InformarDadosGeracaoResumoAcaoConsultaHelper informarDadosGeracaoResumoAcaoConsultaHelper,
            boolean ehExecucao) throws ControladorException;
	
}
