package gcom.batch;

import gcom.arrecadacao.Devolucao;
import gcom.arrecadacao.pagamento.GuiaPagamento;
import gcom.arrecadacao.pagamento.GuiaPagamentoCategoria;
import gcom.arrecadacao.pagamento.Pagamento;
import gcom.cadastro.cliente.ClienteConta;
import gcom.cadastro.cliente.ClienteGuiaPagamento;
import gcom.cobranca.RelatorioPagamentosContasCobrancaEmpresaHelper;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.conta.ContaCategoria;
import gcom.faturamento.conta.ContaCategoriaConsumoFaixa;
import gcom.faturamento.conta.ContaImpostosDeduzidos;
import gcom.faturamento.credito.CreditoARealizar;
import gcom.faturamento.credito.CreditoRealizado;
import gcom.faturamento.credito.CreditoRealizadoCategoria;
import gcom.faturamento.debito.DebitoACobrar;
import gcom.faturamento.debito.DebitoACobrarCategoria;
import gcom.faturamento.debito.DebitoCobrado;
import gcom.faturamento.debito.DebitoCobradoCategoria;
import gcom.micromedicao.MovimentoHidrometroHelper;
import gcom.micromedicao.hidrometro.Hidrometro;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.ErroRepositorioException;

import java.util.Collection;
import java.util.Map;

/**
 * Declara��o p�blica de servi�os do Session Bean de ControladorCliente
 * 
 * @author S�vio Luiz
 * @created 25 de Abril de 2005
 */
public interface ControladorBatchLocal extends javax.ejb.EJBLocalObject {

	/**
	 * Insere um processo iniciado no sistema e suas funcionalidades iniciadas
	 * 
	 * @author Rodrigo Silveira
	 * @date 28/07/2006
	 * 
	 * @param processoIniciado
	 * @throws ControladorException
	 * @throws ControladorException
	 */
	public Integer inserirProcessoIniciado(ProcessoIniciado processoIniciado)
			throws ControladorException;

	public Integer inserirProcessoIniciadoFaturamentoComandado(
			Collection<Integer> idsFaturamentoAtividadeCronograma,
			Usuario usuario) throws ControladorException;

	/**
	 * Verifica no sistema a presenca de ProcessosIniciados nao agendados para
	 * iniciar a execucao
	 * 
	 * @author Rodrigo Silveira
	 * @date 22/08/2006
	 * 
	 */
	public void verificarProcessosIniciados() throws ControladorException;

	/**
	 * Encerra os Processos Iniciados no sistema quando todas as funcionalidades
	 * do mesmo finalizarem a execu��o
	 * 
	 * @author Rodrigo Silveira
	 * @date 22/08/2006
	 * 
	 */
	public void encerrarProcessosIniciados() throws ControladorException;

	/**
	 * Encerra as Funcionalidades Iniciadas no sistema quando todas as unidades
	 * de processamento do mesmo finalizarem a execu��o
	 * 
	 * @author Rodrigo Silveira
	 * @date 22/08/2006
	 * 
	 */
	public void encerrarFuncionalidadesIniciadas() throws ControladorException;

	/**
	 * Inicia a Unidade de Processamento de um processo Batch
	 * 
	 * @author Rodrigo Silveira
	 * @date 22/08/2006
	 * 
	 * @param idFuncionalidadeIniciada
	 * @param idUnidadeProcessamento
	 * @return
	 */
	public int iniciarUnidadeProcessamentoBatch(int idFuncionalidadeIniciada,
			int idUnidadeProcessamento, int codigoRealUnidadeProcessamento)
			throws ControladorException;

	/**
	 * Encerra a Unidade de Processamento associada a um processamento batch
	 * 
	 * @author Rodrigo Silveira
	 * @date 24/08/2006
	 * 
	 * @param idUnidadeIniciada
	 * @param executouComErro
	 */
	public void encerrarUnidadeProcessamentoBatch(Throwable ex,
			int idUnidadeIniciada, boolean executouComErro)
			throws ControladorException;

	/**
	 * Inseri uma cole��o de objetos gen�ricos na base com um flush para cada 50
	 * registros inseridos.
	 * 
	 * @author Pedro Alexandre
	 * @date 11/09/2006
	 * 
	 * @param colecaoObjetos
	 * @throws ControladorException
	 */
	public void inserirColecaoObjetoParaBatch(Collection<? extends Object> colecaoObjetos)
			throws ControladorException;

	/**
	 * Inseri uma cole��o de objetos gen�ricos na base com um flush para cada 50
	 * registros inseridos.
	 * 
	 * @author Bruno Barros
	 * @date 26/04/2007
	 * 
	 * @param colecaoObjetos
	 * @throws ControladorException
	 */
	public void inserirColecaoObjetoParaBatchGerencial(
			Collection<? extends Object> colecaoObjetos) throws ControladorException;

	/**
	 * Atualiza uma cole��o de objetos gen�ricos na base com um flush para cada
	 * 50 registros inseridos.
	 * 
	 * @author Leonardo Vieira
	 * @date 12/10/2006
	 * 
	 * @param colecaoObjetos
	 * @throws ControladorException
	 */
	public void atualizarColecaoObjetoParaBatch(
			Collection<? extends Object> colecaoObjetos) throws ControladorException;

	public void verificadorProcessosSistema() throws ControladorException;

	/**
	 * Inicia uma funcionalidade iniciada do relatorio
	 * 
	 * @author Rodrigo Silveira
	 * @date 26/09/2006
	 * 
	 * @param idFuncionalidadeIniciada
	 * @throws ControladorException
	 */
	public void iniciarFuncionalidadeIniciadaRelatorio(
			int idFuncionalidadeIniciada) throws ControladorException;

	/**
	 * Encerra uma funcionalidade iniciada do relatorio
	 * 
	 * @author Rodrigo Silveira
	 * @date 26/09/2006
	 * 
	 * @param idFuncionalidadeIniciada
	 * @throws ControladorException
	 */
	public void encerrarFuncionalidadeIniciadaRelatorio(
			int idFuncionalidadeIniciada, boolean concluiuComErro)
			throws ControladorException;

	/**
	 * Pesquisa todos as funcionalidades iniciadas que representam os relat�rios
	 * batch do sistema
	 * 
	 * @author Rodrigo Silveira
	 * @date 09/10/2006
	 * 
	 */
	public Collection<Object[]> pesquisarRelatoriosBatchSistema()
			throws ControladorException;

	/**
	 * Remove uma cole��o de objetos gen�ricos na base com um flush para cada 50
	 * registros removidos.
	 * 
	 * @author Pedro Alexandre
	 * @date 09/10/2006
	 * 
	 * @param colecaoObjetos
	 * @throws ControladorException
	 */
	public void removerColecaoObjetoParaBatch(Collection<Object> colecaoObjetos)
			throws ControladorException;

	/**
	 * Inicia um processo relacionado com um relat�rio que ser� processado em
	 * batch
	 * 
	 * @author Rodrigo Silveira
	 * @date 23/10/2006
	 * 
	 * @throws ControladorException
	 */

	public void iniciarProcessoRelatorio(TarefaRelatorio tarefaRelatorio)
			throws ControladorException;

	/**
	 * Pesquisa todos as funcionalidades iniciadas que representam os relat�rios
	 * batch do sistema por Usu�rio
	 * 
	 * @author Rodrigo Silveira
	 * @date 25/10/2006
	 * 
	 */
	public Collection<Object[]> pesquisarRelatoriosBatchPorUsuarioSistema(
			int idProcesso) throws ControladorException;

	/**
	 * Remove do sistema todos os relat�rios batch que est�o na data de
	 * expira��o
	 * 
	 * @author Rodrigo Silveira
	 * @date 26/10/2006
	 * 
	 */
	public void deletarRelatoriosBatchDataExpiracao()
			throws ControladorException;

	public Integer inserirProcessoIniciadoCobrancaComandado(
			Collection<Integer> idsCronograma,
			Collection<Integer> idsEventuais, Usuario usuario)
			throws ControladorException;

	/**
	 * Pesquisa no sistema todos os processos que pararam na metade devido a uma
	 * falha no servidor e marca com 'EXECU��O INTERROMPIDA'
	 * 
	 * @author Rodrigo Silveira
	 * @date 27/01/2007
	 * 
	 */
	public void marcarProcessosInterrompidos() throws ControladorException;

	/**
	 * Inseri uma objeto gen�rico na base
	 * 
	 * @author Marcio Roberto
	 * @date 18/05/2007
	 * 
	 * @param Objeto
	 * @throws ErroRepositorioException
	 */
	public Object inserirObjetoParaBatchGerencial(Object objeto)
			throws ControladorException;

	/**
	 * <Breve descri��o sobre o caso de uso>
	 * 
	 * <Identificador e nome do caso de uso>
	 * 
	 * @author Pedro Alexandre
	 * @date 18/06/2007
	 * 
	 * @param processoIniciado
	 * @param dadosProcessamento
	 * @return
	 * @throws ControladorException
	 */
	public Integer inserirProcessoIniciado(ProcessoIniciado processoIniciado,
			Map<String, Object> dadosProcessamento) throws ControladorException;

	/**
	 * Reinicia uma funcionalidade iniciada
	 * 
	 * <Identificador e nome do caso de uso>
	 * 
	 * @author Rafael Corr�a
	 * @date 06/10/2007
	 * 
	 * @param idsFuncionalidadesIniciadas
	 * @param idProcessoIniciado
	 * @return
	 * @throws ControladorException
	 */
	public void reiniciarFuncionalidadesIniciadas(
			String[] idsFuncionalidadesIniciadas, Integer idProcessoIniciado)
			throws ControladorException;

	/**
	 * Remove uma cole��o de GuiaPagamentoCategoria
	 * 
	 * [UC0276] Encerrar Arrecada��o do M�s
	 * 
	 * @author Pedro Alexandre
	 * @date 12/02/2008
	 * 
	 * @param colecaoGuiaPagamentoCategoria
	 * @throws ControladorException
	 */
	public void removerColecaoGuiaPagamentoCategoriaParaBatch(
			Collection<GuiaPagamentoCategoria> colecaoGuiaPagamentoCategoria)
			throws ControladorException;

	/**
	 * Remove uma cole��o de ClienteGuiaPagamento
	 * 
	 * [UC0276] Encerrar Arrecada��o do M�s
	 * 
	 * @author Pedro Alexandre
	 * @date 12/02/2008
	 * 
	 * @param colecaoGuiaPagamentoCategoria
	 * @throws ControladorException
	 */
	public void removerColecaoClienteGuiaPagamentoParaBatch(
			Collection<ClienteGuiaPagamento> colecaoClienteGuiaPagamento)
			throws ControladorException;

	/**
	 * Remove uma cole��o de GuiaPagamento
	 * 
	 * [UC0276] Encerrar Arrecada��o do M�s
	 * 
	 * @author Pedro Alexandre
	 * @date 12/02/2008
	 * 
	 * @param colecaoGuiaPagamento
	 * @throws ControladorException
	 */
	public void removerColecaoGuiaPagamentoParaBatch(
			Collection<GuiaPagamento> colecaoGuiaPagamento)
			throws ControladorException;

	/**
	 * Remove uma cole��o de DebitoACobrar
	 * 
	 * [UC0276] Encerrar Arrecada��o do M�s
	 * 
	 * @author Pedro Alexandre
	 * @date 12/02/2008
	 * 
	 * @param colecaoDebitoACobrar
	 * @throws ControladorException
	 */
	public void removerColecaoDebitoACobrarParaBatch(
			Collection<DebitoACobrar> colecaoDebitoACobrar)
			throws ControladorException;

	/**
	 * Remove uma cole��o de DebitoACobrarCategoria
	 * 
	 * [UC0276] Encerrar Arrecada��o do M�s
	 * 
	 * @author Pedro Alexandre
	 * @date 12/02/2008
	 * 
	 * @param colecaoDebitoACobrarCategoria
	 * @throws ControladorException
	 */
	public void removerColecaoDebitoACobrarCategoriaParaBatch(
			Collection<DebitoACobrarCategoria> colecaoDebitoACobrarCategoria)
			throws ControladorException;

	/**
	 * Remove uma cole��o de Pagamento
	 * 
	 * [UC0276] Encerrar Arrecada��o do M�s
	 * 
	 * @author Pedro Alexandre
	 * @date 12/02/2008
	 * 
	 * @param colecaoPagamento
	 * @throws ControladorException
	 */
	public void removerColecaoPagamentoParaBatch(
			Collection<Pagamento> colecaoPagamento) throws ControladorException;

	/**
	 * Remove uma cole��o de Devolucao
	 * 
	 * [UC0276] Encerrar Arrecada��o do M�s
	 * 
	 * @author Pedro Alexandre
	 * @date 12/02/2008
	 * 
	 * @param colecaoDevolucao
	 * @throws ControladorException
	 */
	public void removerColecaoDevolucaoParaBatch(
			Collection<Devolucao> colecaoDevolucao) throws ControladorException;

	/**
	 * Remove uma cole��o de Conta
	 * 
	 * [UC0276] Encerrar Arrecada��o do M�s
	 * 
	 * @author Pedro Alexandre
	 * @date 12/02/2008
	 * 
	 * @param colecaoConta
	 * @throws ControladorException
	 */
	public void removerColecaoContaParaBatch(Collection<Conta> colecaoConta)
			throws ControladorException;

	/**
	 * Remove uma cole��o de ContaCategoria
	 * 
	 * [UC0276] Encerrar Arrecada��o do M�s
	 * 
	 * @author Pedro Alexandre
	 * @date 12/02/2008
	 * 
	 * @param colecaoContaCategoria
	 * @throws ControladorException
	 */
	public void removerColecaoContaCategoriaParaBatch(
			Collection<ContaCategoria> colecaoContaCategoria)
			throws ControladorException;

	/**
	 * Remove uma cole��o de ContaCategoriaConsumoFaixa
	 * 
	 * [UC0276] Encerrar Arrecada��o do M�s
	 * 
	 * @author Pedro Alexandre
	 * @date 12/02/2008
	 * 
	 * @param colecaoContaCategoriaConsumoFaixa
	 * @throws ControladorException
	 */
	public void removerColecaoContaCategoriaConsumoFaixaParaBatch(
			Collection<ContaCategoriaConsumoFaixa> colecaoContaCategoriaConsumoFaixa)
			throws ControladorException;

	/**
	 * Remove uma cole��o de CreditoRealizado
	 * 
	 * [UC0276] Encerrar Arrecada��o do M�s
	 * 
	 * @author Pedro Alexandre
	 * @date 12/02/2008
	 * 
	 * @param colecaoCreditoRealizado
	 * @throws ControladorException
	 */
	public void removerColecaoCreditoRealizadoParaBatch(
			Collection<CreditoRealizado> colecaoCreditoRealizado)
			throws ControladorException;

	/**
	 * Remove uma cole��o de DebitoCobrado
	 * 
	 * [UC0276] Encerrar Arrecada��o do M�s
	 * 
	 * @author Pedro Alexandre
	 * @date 12/02/2008
	 * 
	 * @param colecaoDebitoCobrado
	 * @throws ControladorException
	 */
	public void removerColecaoDebitoCobradoParaBatch(
			Collection<DebitoCobrado> colecaoDebitoCobrado)
			throws ControladorException;

	/**
	 * Remove uma cole��o de ContaImpostosDeduzidos
	 * 
	 * [UC0276] Encerrar Arrecada��o do M�s
	 * 
	 * @author Pedro Alexandre
	 * @date 12/02/2008
	 * 
	 * @param colecaoContaImpostosDeduzidos
	 * @throws ControladorException
	 */
	public void removerColecaoContaImpostosDeduzidosParaBatch(
			Collection<ContaImpostosDeduzidos> colecaoContaImpostosDeduzidos)
			throws ControladorException;

	/**
	 * Remove uma cole��o de ClienteConta
	 * 
	 * [UC0276] Encerrar Arrecada��o do M�s
	 * 
	 * @author Pedro Alexandre
	 * @date 12/02/2008
	 * 
	 * @param colecaoClienteConta
	 * @throws ControladorException
	 */
	public void removerColecaoClienteContaParaBatch(
			Collection<ClienteConta> colecaoClienteConta)
			throws ControladorException;

	/**
	 * Remove uma cole��o de DebitoCobradoCategoria
	 * 
	 * [UC0276] Encerrar Arrecada��o do M�s
	 * 
	 * @author Pedro Alexandre
	 * @date 12/02/2008
	 * 
	 * @param colecaoDebitoCobradoCategoria
	 * @throws ControladorException
	 */
	public void removerColecaoDebitoCobradoCategoriaParaBatch(
			Collection<DebitoCobradoCategoria> colecaoDebitoCobradoCategoria)
			throws ControladorException;

	/**
	 * Remove uma cole��o de CreditoRealizadoCategoria
	 * 
	 * [UC0276] Encerrar Arrecada��o do M�s
	 * 
	 * @author Pedro Alexandre
	 * @date 12/02/2008
	 * 
	 * @param colecaoCreditoRealizadoCategoria
	 * @throws ControladorException
	 */
	public void removerColecaoCreditoRealizadoCategoriaParaBatch(
			Collection<CreditoRealizadoCategoria> colecaoCreditoRealizadoCategoria)
			throws ControladorException;

	/**
	 * Funcao que executa as rotinas de execucao da integra��o da SAM
	 * 
	 * @author Rodrigo Silveira
	 * @date 27/02/2008
	 * 
	 */
	public void verificadorProcessosIntegracaoUPA() throws ControladorException;

	/**
	 * Funcao que executa as rotinas de execucao da integra��o da SAM
	 * 
	 * @author Rodrigo Silveira
	 * @date 27/02/2008
	 * 
	 */
	public void verificadorQueriesDemoradasSistema()
			throws ControladorException;

	/**
	 * Remove uma cole��o de CreditoARealizar
	 * 
	 * [UC0276] Encerrar Arrecada��o do M�s
	 * 
	 * @author Pedro Alexandre
	 * @date 12/02/2008
	 * 
	 * @param colecaoCreditoARealizar
	 * @throws ControladorException
	 */
	public void removerColecaoCreditoARealizarParaBatch(
			Collection<CreditoARealizar> colecaoCreditoARealizar)
			throws ControladorException;

	/**
	 * Remove uma cole��o de CreditoARealizarCategoria
	 * 
	 * [UC0276] Encerrar Arrecada��o do M�s
	 * 
	 * @author Pedro Alexandre
	 * @date 09/04/2008
	 * 
	 * @param colecaoIdsCreditoARealizar
	 * @throws ControladorException
	 */
	public void removerColecaoCreditoARealizarCategoriaParaBatch(
			Collection<Integer> colecaoIdsCreditoARealizar)
			throws ControladorException;

	/**
	 * Insere um processo batch ativado por um usu�rio atrav�s de uma
	 * funcionalidade comum
	 * 
	 * @author Rodrigo Silveira
	 * @date 02/05/2008
	 * 
	 * @param processoIniciado
	 * @throws ControladorException
	 * @throws ControladorException
	 */
	public Integer inserirProcessoIniciadoParametrosLivres(Map parametros,
			int idProcesso, Usuario usuario) throws ControladorException;

	/**
	 * Inseri uma cole��o de objetos gen�ricos na base
	 * 
	 * @author Rafael Pinto
	 * @date 20/05/2008
	 * 
	 * @param colecaoObjetos
	 * @throws ErroRepositorioException
	 */
	public void inserirColecaoObjetoParaBatchTransacao(
			Collection<Object> colecaoObjetos) throws ControladorException;

	/**
	 * Reinicia uma funcionalidade iniciada
	 * 
	 * <Identificador e nome do caso de uso>
	 * 
	 * @author Rafael Corr�a
	 * @date 06/10/2007
	 * 
	 * @param idsFuncionalidadesIniciadas
	 * @param idProcessoIniciado
	 * @return
	 * @throws ControladorException
	 */
	public void continuarFuncionalidadesIniciadas(
			String[] idsFuncionalidadesIniciadas, Integer idProcessoIniciado)
			throws ControladorException;

	/**
	 * Verifica se o processo est� em execu��o
	 * 
	 * @author Ana Maria
	 * @date 18/12/2008
	 * 
	 */
	public boolean verificarProcessoEmExecucao(Integer idProcesso)throws ControladorException;

	/**
 	 * Continua o processamento de um batch 
 	 * 
 	 * @author R�mulo Aur�lio 
 	 * @date 03/12/2008
 	 * 
 	 * @param ids
 	 * @param idEmpresa
 	 * @param idFuncionalidadeIniciada
 	 * @param usuario
 	 */
	
	public Integer inserirProcessoIniciadoContasCobranca(Collection ids,
			Integer idEmpresa, Usuario usuario) throws ControladorException;

	/**
	 * Continua o processamento de um batch
	 * 
	 * @author R�mulo Aur�lio
	 * @date 12/01/2009
	 * 
	 * @param ids
	 * @param idEmpresa
	 * @param idFuncionalidadeIniciada
	 * @param usuario
	 */

	public Integer inserirProcessoIniciadoRelatorioPagamentosContasCobranca(
			RelatorioPagamentosContasCobrancaEmpresaHelper helper,
			int opcaoRelatorio,Usuario usuario)
			throws ControladorException;

	
	/**
	 * Inicia um processo relacionado com um relatoio que seria processado em
	 * batch dependendo de crit�rios de autoriza��o
	 * 
	 * @author Rodrigo Silveira
	 * @date 08/06/2009
	 * 
	 * @throws ControladorException
	 */

	public void iniciarProcessoRelatorioControleAutorizacao(TarefaRelatorio tarefaRelatorio)
			throws ControladorException; 

	
	 /**
 	 * Atualiza hidrometros em batch
 	 * 
 	 * @author Hugo Amorim
 	 * @date 08/06/2009
 	 * 
 	 */
	public Integer inserirProcessoAtualizarConjuntoHidrometro(String fixo,String inicialFixo,String finalFixo,
			Hidrometro hidrometroAtualizado,Usuario usuarioLogado,Integer totalRegistros)
			throws ControladorException;


	
	/**
	 * Insere e Atualiza a movimenta��o de Hidrometro
	 * 
	 * @author Arthur Carvalho
	 * @date 17/06/2009
	 * 
	 * @param helper
	 */
	
	public Integer inserirAtualizarMovimentacaoHidrometroIdsBatch( MovimentoHidrometroHelper helper ) throws ControladorException;
	
	
	
	/**
	 * Autoriza Processo Iniciado
	 * 
	 * @author Genival Barbosa
	 * @date 06/08/2009
	 * 
	 * @param ProcessoIniciado
	 */
	
	public void autorizarProcessoIniciado(ProcessoIniciado processoIniciado,Integer processoSituacao,Integer funcionalidadeSituacao) throws ControladorException;

	/**
 	 * 
 	 * 
 	 * @author Hugo Amorim
 	 * @date 06/10/2009
 	 * 
 	 */
	public Integer inserirProcessoIniciadoPagamentosContasCobranca(
			Integer idEmpresa,Integer referenciaInicial, Integer referenciaFinal, Usuario usuario)
			throws ControladorException;
	
	/**
 	 * [UC0972] Gerar TXT das Contas dos Projetos Especiais
 	 * 
 	 * @author Hugo Amorim
 	 * @date 14/12/2009
 	 * 
 	 */
	public Integer inserirProcessoGerarTxtContasProcessosEspeciais(
			String anoMes, Integer idCliente, Usuario usuario)
			throws ControladorException;
	
	/**
	 * Atualiza um objeto gen�rico na base 
	 * 
	 * @author Vivianne Sousa
	 * @date 03/02/2009
	 * 
	 * @param objetoParaAtualizar
	 * @throws ControladorException
	 */
	public void atualizarObjetoParaBatch(
			Object objetoParaAtualizar) throws ControladorException;
	
	
	/**
	 * Insere uma objeto gen�rico na base 
	 * 
	 * @author Vivianne Sousa
	 * @date 03/02/2009
	 * 
	 * @param Objeto
	 * @throws ErroRepositorioException
	 */
	public Object inserirObjetoParaBatch(Object objeto)
	throws ControladorException ;
	
	/**
	 * Remove uma cole�?�?o de objetos gen�?ricos na base com um flush para cada 50
	 * registros removidos.
	 * 
	 * @author Pedro Alexandre
	 * @date 09/10/2006
	 * 
	 * @param colecaoObjetos
	 * @throws ControladorException
	 */
	public void removerColecaoObjetoParaBatchSemTransacao(Collection colecaoObjetos)
			throws ControladorException;
	
	/**
	 * Remove uma cole��o de objetos gen�ricos na base com um flush para cada 50
	 * registros removidos.
	 * 
	 * @author S�vio Luiz
	 * @date 31/03/2010
	 * 
	 * @param colecaoObjetos
	 * @throws ErroRepositorioException
	 */
	public void removerObjetoParaBatchSemTransacao(Object objeto)
			throws ControladorException;
	
	/**
	 * Insere uma cole�?�?o de objetos gen�?ricos na base com um flush para cada 50
	 * registros inseridos.
	 * 
	 * @author S�vio Luiz
	 * @date 31/03/2010
	 * 
	 * @param colecaoObjetos
	 * @throws ControladorException
	 */
	public void inserirColecaoObjetoParaBatchSemTransacao(Collection<? extends Object> colecaoObjetos)
			throws ControladorException; 
	
	/**
	 * Atualiza uma cole�?�?o de objetos gen�?ricos na base com um flush para cada
	 * 50 registros inseridos.
	 * 
	 * @author Pedro Alexandre
	 * @date 11/09/2006
	 * 
	 * @param colecaoObjetos
	 * @throws ControladorException
	 */
	public void atualizarColecaoObjetoParaBatchSemTransacao(
			Collection<? extends Object> colecaoObjetos) throws ControladorException;
	
	/**
	 * Metodo utilizado para gerar txt da declara��o anual de debitos.
	 * 
	 * 
	 * @author Hugo Amorim
	 * @date 12/05/2010
	 * @param idFaturamentoGrupo
	 * 
	 */
	public Integer inserirProcessoGerarTxtDeclaracaoQuitacaoDebitos(
			Integer idGrupoFaturamento, Usuario usuario)
			throws ControladorException;
	
	/**
	 * Retorna o(s) processo(s) que est� em execu��o
	 * 
	 * @author Arthur Carvalho
	 * @date 04/06/2010
	 * 
	 */
	public Collection retornaProcessoFuncionalidadeEmExecucao() throws ControladorException;
	
	/**
	 * Insere uma objeto gen�rico na base, sem controle transacional
	 * 
	 * @author Bruno Barros
	 * @date 23/09/2010
	 * 
	 * @param Objeto
	 * @throws ErroRepositorioException
	 */
	public Object inserirObjetoParaBatchSemTransacao(Object objeto)
			throws ControladorException;
	
	/**
	 * Insere um processo batch ativado por um usu�rio atrav�s de uma
	 * funcionalidade comum
	 * 
	 * @author Vivianne Sousa
	 * @date 29/03/2011
	 * 
	 * @param processoIniciado
	 * @throws ControladorException
	 * @throws ControladorException
	 */
	public Integer inserirProcessoIniciadoParametrosLivresAguardandoAutorizacao(Map parametros,
			int idProcesso, Usuario usuario) throws ControladorException;
}


