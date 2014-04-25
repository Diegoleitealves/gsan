package gcom.financeiro;

import gcom.batch.ProcessoIniciado;
import gcom.financeiro.lancamento.LancamentoItemContabil;
import gcom.relatorio.financeiro.RelatorioEvolucaoContasAReceberContabilBean;
import gcom.relatorio.financeiro.RelatorioParametrosContabeisArrecadacaoBean;
import gcom.relatorio.financeiro.RelatorioParametrosContabeisFaturamentoBean;
import gcom.relatorio.financeiro.RelatorioVolumesConsumidosNaoFaturadosBean;
import gcom.relatorio.financeiro.ResumoReceitaHelper;
import gcom.util.ControladorException;
import gcom.util.ErroRepositorioException;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Interface Controlador Financeiro PADR�O
 *
 * @author Raphael Rossiter
 * @date 26/06/2007
 */
public interface IControladorFinanceiro {

	/**
	 * Pesquisa uma cole��o de lan�amento item cont�bil
	 * 	 
	 * @return Cole��o de Lan�amentos de Item Cont�bil 
	 * @exception ErroRepositorioException  Erro no hibernate
	 */
	public Collection<LancamentoItemContabil> pesquisarLancamentoItemContabil() throws ControladorException;
	
	/**
	 * Gera Lan�amentos Contabeis do Faturamento
	 *
	 * [UC000348] - Gerar Lan�amento Const�beis da Arrecada��o
	 *
	 * @author Rafael Santos
	 * @date 22/05/2006
	 *
	 * @param anoMesArrecadacao
	 * @throws ControladorException 
	 */
	public void gerarLancamentoContabeisArrecadacao(Integer anoMesReferenciaArrecadacao, Integer idLocalidade, int idFuncionalidadeIniciada) throws ControladorException; 
	
	/**
	 * este caso de uso gera a integra��o para a contabilidade
	 *
	 * [UC0469] Gerar Integra��o para a Contabilidade
	 *
	 * @author Pedro Alexandre
	 * @date 28/05/2007
	 *
	 * @param idLancamentoOrigem
	 * @param anoMes
	 * @param data
	 * @throws ControladorException
	 */
	public void gerarIntegracaoContabilidade(String idLancamentoOrigem, String anoMes, String data) throws ControladorException;

	
	/**
	 * [UC0485] - Gerar Resumo dos Devedores Duvidosos
	 *
	 * @author Rafael Pinto
	 * @date 22/11/2006
	 *
	 * @param anoMesReferenciaContabil
	 * @throws ControladorException
	 */		
	public void gerarResumoDevedoresDuvidosos(int anoMesReferenciaContabil, Integer idLocalidade, int idFuncionalidadeIniciada)
		throws ControladorException;
	
	/**
	 * [UC0345] - Gerar Relatorio de Resumo da Arrecada��o
	 *
	 * @author Vivianne Sousa
	 * @date 10/04/2007
	 *
	 * @param idLancamentoTipo
	 * @throws ErroRepositorioException
	 */	
	public String obterDescricaoLancamentoTipo(Integer idLancamentoTipo) 
		throws ControladorException;
	

	/**
	 * [UC0175] - Gerar Lan�amentos Cont�beis do Faturamento
	 * Author: Raphael Rossiter, Pedro Alexandre 
	 * Data: 16/01/2006, 23/05/2007
	 * 
	 * Gera os lan�amentos cont�beis a partir dos dados selecionados na tabela RESUMO_FATURAMENTO
	 * 
	 * @param anoMesReferenciaFaturamento
	 * @param idLocalidade
	 * @param idFuncionalidadeIniciada
	 * @throws ControladorException
	 */
	public void gerarLancamentosContabeisFaturamento(Integer anoMesReferenciaFaturamento, Integer idLocalidade, int idFuncionalidadeIniciada ) throws ControladorException;

	/**
	 * Pesquisa as localidades que tem resumo de faturamento 
	 * para o ano/m�s de faturamento informado.
	 *
	 * [UC00175] Gerar Lan�amentos Cont�beis do Faturamento
	 *
	 * @author Pedro Alexandre
	 * @date 25/05/2007
	 *
	 * @param anoMesFaturamento
	 * @return
	 * @throws ControladorException
	 */
	public Collection<Integer> pesquisarIdsLocalidadesParaGerarLancamentosContabeisFaturamento(Integer anoMesFaturamento) throws ControladorException;

	/**
	 * Pesquisa as localidades que tem resumo de arrecada��o 
	 * para o ano/m�s de arrecada��o informado.
	 *
	 * [UC00348] Gerar Lan�amentos Cont�beis da arrecada��o
	 *
	 * @author Pedro Alexandre
	 * @date 31/05/2007
	 *
	 * @param anoMesArrecadacao
	 * @return
	 * @throws ControladorException
	 */
	public Collection<Integer> pesquisarIdsLocalidadesParaGerarLancamentosContabeisArrecadacao(Integer anoMesArrecadacao) throws ControladorException;

	/**
	 * @param anoMesReferenciaContabil
	 * @return
	 * @throws ControladorException
	 */
	public Collection<Integer> pesquisarIdsLocalidadesParaGerarResumoDevedoresDuvidosos(Integer anoMesReferenciaContabil) throws ControladorException;
	
	/**
	 * este caso de uso gera a integra��o para a contabilidade
	 *
	 * [UC0469] Gerar Integra��o para a Contabilidade
	 *
	 * @author Fl�vio Leonardo
	 * @date 06/06/2007
	 *
	 * @param idLancamentoOrigem
	 * @param anoMes
	 * @param data
	 * @throws ControladorException
	 */
	public void gerarIntegracaoContabilidadeCaern(String idLancamentoOrigem, String anoMes, String data) throws ControladorException;

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
	public Integer gerarResumoDevedoresDuvidosos(ProcessoIniciado processoIniciado,Map<String, Object> dadosProcessamento)	throws ControladorException;

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
	public ParametrosDevedoresDuvidosos pesquisarParametrosDevedoresDuvidosos(Integer anoMesReferenciaContabil)	throws ControladorException ;

	/**
	 * Gera os lan�amentos dos devedores duvidosos.
	 *
	 * [UC0486] Gerar Lan�amentos Cont�beis dos Devedores Duvidosos
	 *
	 * @author Pedro Alexandre
	 * @date 21/06/2007
	 *
	 * @param anoMesReferenciaContabil
	 * @param idLocalidade
	 * @param idFuncionalidadeIniciada
	 * @throws ControladorException
	 */
	public void gerarLancamentosContabeisDevedoresDuvidosos(Integer anoMesReferenciaContabil, Integer idLocalidade, int idFuncionalidadeIniciada) throws ControladorException;

	/**
	 * Pesquisa a cole��o de ids das localidades para processar o lan�amentos  
	 * cont�beis dos devedores duvidosos.
	 *
	 * [UC0485] Gerar Lan�amentos Cont�beis dos Devedores Duvidosos
	 *
	 * @author Pedro Alexandre
	 * @date 25/06/2007
	 *
	 * @param anoMesReferenciaContabil
	 * @return
	 * @throws ControladorException
	 */
	public Collection<Integer> pesquisarIdsLocalidadesParaGerarLancamentosContabeisDevedoresDuvidosos(Integer anoMesReferenciaContabil) throws ControladorException;
	
	/**
	 * Remove os lan�amentos cont�beis e seus respectivos itens 
	 * de acordo com os par�metros informados. 
	 *
	 * @author Pedro Alexandre
	 * @date 26/06/2007
	 *
	 * @param anoMesReferenciaContabil
	 * @param idLocalidade
	 * @param idLancamentoOrigem
	 * @throws ControladorException
	 */
	public void removerLancamentoContabil(Integer anoMesReferenciaContabil, Integer idLocalidade, Integer idLancamentoOrigem) throws ControladorException;
	
	/**
	 * [UC0714] Gerar Contas a Receber Cont�bil
	 * 
	 * M�todo respons�vel pela gera��o de contas a receber cont�bil
	 * 
	 * @author Rafael Corr�a
	 * @date 08/11/2007
	 * 
	 * @param idLocalidade
	 * @throws ControladorException
	 */
	public void gerarContasAReceberContabil(Integer idLocalidade,
			int idFuncionalidadeIniciada) throws ControladorException;
	
	/**
	 * [UC0751] Gerar Valor Referente a Volumes Consumidos e N�o Faturados
	 * 
	 * M�todo respons�vel pela gera��o de valor dos volumes consumidos e n�o faturados
	 * 
	 * @author Rafael Corr�a
	 * @date 19/02/2008
	 * 
	 * @param idLocalidade
	 * @throws ControladorException
	 */
	public void gerarValorVolumesConsumidosNaoFaturados(Integer idLocalidade,
			int idFuncionalidadeIniciada) throws ControladorException;
	
	/**
	 * Consulta ResumoDevedoresDuvidosos para a gera��o do relat�rio 
	 * [UC0487] Gerar Relat�rio de Resumo de Devedores Duvidosos
	 * de acordo com a op��o de totaliza��o.
	 * 
	 * @author Vivianne Sousa
	 * @created 20/07/2007
	 * 
	 * @param opcaoTotalizacao
	 * @return
	 * @throws ControladorException
	 */
	public Collection consultarResumoDevedoresDuvidososRelatorio(String opcaoTotalizacao, int mesAnoReferencia,
			Integer gerenciaRegional, Integer localidade, Integer unidadeNegocio)throws ControladorException;

	/**
	 * [UC0718] Gerar Relat�rio de Evolucao do Contas a Receber Contabil
	 * 
	 * @author Francisco Junior
	 * @date 02/01/08
	 * 
	 * @param opcaoTotalizacao
	 * @param mesAno
	 * @param codigoGerencia
	 * @param codigoLocalidade
	 * @param unidadeNegocio
	 * @return Colecao 
	 * @throws ControladorException
	 */
	public Collection<RelatorioEvolucaoContasAReceberContabilBean> consultarDadosEvolucaoContasAReceberContabilRelatorio(String opcaoTotalizacao,
			int mesAno, Integer codigoGerencia, Integer codigoLocalidade, Integer codigoMunicipio, Integer unidadeNegocio) throws ControladorException;

	/**
	 * [UC0717] - Consultar dados do relatorio de Saldo do Contas a Receber Contabil
	 * 
	 * @date 17/01/08
	 * @author Frncisco do Nascimento
	 * 
	 * @param anoMesReferencia
	 * @param gerencia
	 * @param unidadeNegocio
	 * @param localidade
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection consultarDadosRelatorioSaldoContasAReceberContabil(String opcaoTotalizacao,
			int anoMesReferencia, Integer gerencia, Integer unidadeNegocio, Integer localidade, Integer municipio) 
			throws ControladorException;	
    
     
   /**
     * [UC0799] - Gerar Txt das Contas Baixadas Contabilmente
     * 
     * @author: Vivianne Sousa
     * @date: 09/05/2008 
     */
     public void gerarTXTContasBaixadasContabilmente(
                Map parametros, Integer idSetorComercial, Integer idFuncionalidadeIniciada,Integer faixa)
                throws ControladorException;
     
     
 	/**
 	 * [UC0824] Gerar Relat�rio dos Par�metros Cont�beis
 	 * 
 	 * @author Bruno Barros
 	 * @date 08/07/2008
 	 * 
 	 * @return Collection<RelatorioParametrosContabeisFaturamentoBean>
 	 * @throws ErroRepositorioException
 	 */
 	public Collection<RelatorioParametrosContabeisFaturamentoBean> 
 		pesquisarDadosRelatorioParametrosContabeisFaturamento( String referenciaContabil ) throws ControladorException;
 	/**
 	 * [UC0822] Gerar Relat�rio do Valor Referente a Volumes Consumidos e N�o Faturados
 	 * 
 	 * @author Victor Cisneiros
 	 * @date 15/07/2008
 	 */
     public List<RelatorioVolumesConsumidosNaoFaturadosBean> pesquisarVolumesConsumidosNaoFaturados(
     		Integer mesAno, String opcaoTotalizacao, Integer idEntidade) throws ControladorException;
     
    /**
     * [UC0824] Gerar Relat�rio dos Par�metros Cont�beis
     * 
     * @author Bruno Barros
     * @date 08/07/2008
     * 
     * @return Collection<RelatorioParametrosContabeisArrecadacaoBean>
     * @throws ErroRepositorioException
     */
    public Collection<RelatorioParametrosContabeisArrecadacaoBean> 
        pesquisarDadosRelatorioParametrosContabeisArrecadacao( String referenciaContabil ) throws ControladorException;
    
    /**
     * [UC0992] Gerar Lan�amentos Cont�beis dos Avisos Banc�rios 
     *
     * @author Raphael Rossiter
     * @date 22/02/2010
     *
     * @param anoMesArrecadacao
     * @param idFuncionalidadeIniciada
     * @throws ControladorException
     */
    public void gerarLancamentosContabeisAvisosBancarios(Integer anoMesArrecadacao, 
    	int idFuncionalidadeIniciada) throws ControladorException ;
    
    /**
	 * [UC0989] Gerar Resumo de Documentos a Receber
	 *
	 * @author Raphael Rossiter
	 * @date 10/03/2010
	 *
	 * @param idLocalidade
	 * @param idFuncionalidadeIniciada
	 * @throws ControladorException
	 */
	public void gerarResumoDocumentosAReceber(Integer idLocalidade,
            int idFuncionalidadeIniciada) throws ControladorException ;
	
	
	   /**
     * [UC 0982] Gerar Resumo da Receita
     * autor: Fl�vio Cordeiro
     * data: 22/02/2010
     *
     * Este caso de uso gera o resumo da receita aberta e ser� executado
     * atrav�s de um batch
     */
    
    public void gerarResumoReceita(int idFuncionalidadeIniciada) throws ControladorException;
    
    public Collection pesquisarResumoReceitaAgrupadoPorBanco(ResumoReceitaHelper resumo)
		throws ControladorException;
    
    public Collection pesquisarResumoReceitaRelatorioAnalitico(ResumoReceitaHelper resumo)
	throws ControladorException;
    
	/**
	 * [UC0485] - Gerar Resumo dos Devedores Duvidosos
	 *
	 * @author Arthur Carvalho
	 * @date 14/09/2010
	 *
	 * @param anoMesReferenciaContabil
	 * @throws ControladorException
	 */		
	public void apagarResumoDevedoresDuvidosos(int anoMesReferenciaContabil, Integer idLocalidade, int idFuncionalidadeIniciada)
		throws ControladorException;
	
	/**
	 * [UC0485] - Gerar Resumo dos Devedores Duvidosos
	 * 
	 * @author Arthur Carvalho
	 * @date 08/11/2010
	 * @param anoMesReferenciaContabil
	 * @param idLocalidade
	 * @param idFuncionalidadeIniciada
	 * @throws ControladorException
	 */
	public void atualizarResumoDevedoresDuvidosos(int anoMesReferenciaContabil, int idFuncionalidadeIniciada)
		throws ControladorException;
	
	/**
     * [[UC0799] - Gerar Txt das Contas Baixadas Contabilmente
     * 
     * @author: Rodrigo Cabral
     * @date: 16/03/2011 
     * 
     * @return
     * @throws ErroRepositorioException
     */
    public Map consultarSomatorioValorContasBaixadasContabilmenteFaixa(
    		Integer referenciaInicio, Integer referenciaFinal,Integer faixa, Short periodicidade)
    		throws ControladorException;

}
