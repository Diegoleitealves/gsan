package gcom.relatorio.seguranca;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.RelatorioVazioException;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * classe respons�vel por criar o relat�rio de Solicita��o de Acesso
 * 
 * [UC1093] Gerar Relat�rio de Solicita��o de Acesso
 * 
 * @author Hugo Leonardo
 *
 * @date 22/011/2010
 */
public class RelatorioSolicitacaoAcesso extends TarefaRelatorio {
	
	private static final long serialVersionUID = 1L;
	
	public RelatorioSolicitacaoAcesso(Usuario usuario) {
		super(usuario, ConstantesRelatorios.RELATORIO_SOLICITACAO_ACESSO);
	}

	@Deprecated
	public RelatorioSolicitacaoAcesso() {
		super(null, "");
	}

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 */
	public Object executar() throws TarefaException {

		// valor de retorno
		byte[] retorno = null;

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------
		
		FiltrarRelatorioSolicitacaoAcessoHelper filtroHelper = 
			(FiltrarRelatorioSolicitacaoAcessoHelper) getParametro("filtroSolicitacaoAcesso");
		
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");
		
		String periodo = (String) getParametro("periodo");

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		RelatorioSolicitacaoAcessoBean relatorioSolicitacaoAcessoBean = null;

		Collection<RelatorioSolicitacaoAcessoHelper> colecao = 
			Fachada.getInstancia().pesquisarRelatorioSolicitacaoAcesso(filtroHelper);

		// se a cole��o de par�metros da analise n�o for vazia
		if (colecao != null && !colecao.isEmpty()) {

			// coloca a cole��o de par�metros da analise no iterator
			Iterator colecaoIterator = colecao.iterator();

			// la�o para criar a cole��o de par�metros da analise
			while (colecaoIterator.hasNext()) {
				
				RelatorioSolicitacaoAcessoHelper relatorioHelper= (RelatorioSolicitacaoAcessoHelper) colecaoIterator.next();

				relatorioSolicitacaoAcessoBean = new RelatorioSolicitacaoAcessoBean(
						relatorioHelper.getMatricula(),
						relatorioHelper.getIdFuncionarioSolicitante(),
						relatorioHelper.getNomeFuncionarioSolicitante(),
						relatorioHelper.getIdFuncionarioSuperior(),
						relatorioHelper.getNomeFuncionarioSuperior(),
						relatorioHelper.getNomeUsuario(),
						relatorioHelper.getCpf(),
						relatorioHelper.getIdLotacao(),
						relatorioHelper.getNomeLotacao(),
						relatorioHelper.getDataInicial(),
						relatorioHelper.getDataFinal(),
						relatorioHelper.getDataSolicitacao(),
						relatorioHelper.getDataAutorizacao(),
						relatorioHelper.getSituacaoAcesso()
				);
				
				// adiciona o bean a cole��o
				relatorioBeans.add(relatorioSolicitacaoAcessoBean);				
			}
		}
		// __________________________________________________________________

		// Par�metros do relat�rio
		Map parametros = new HashMap();
		
		// adiciona os par�metros do relat�rio
		SistemaParametro sistemaParametro = Fachada.getInstancia().pesquisarParametrosDoSistema();
		
		parametros.put("imagem", sistemaParametro.getImagemRelatorio());		
		
		parametros.put("periodo", periodo);
		
		// cria uma inst�ncia do dataSource do relat�rio
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_SOLICITACAO_ACESSO,
				parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relat�rio no sistema
		try {
			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_SOLICITACAO_ACESSO,
					idFuncionalidadeIniciada);
		} catch (ControladorException e) {
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relat�rio no sistema", e);
		}
		// ------------------------------------

		// retorna o relat�rio gerado
		return retorno;
	}

	@Override
	public int calcularTotalRegistrosRelatorio() {
		
		int retorno = 0;

		retorno = 10;
        
		if (retorno == 0) {
			// Caso a pesquisa n�o retorne nenhum resultado comunica ao
			// usu�rio;
			throw new RelatorioVazioException("atencao.relatorio.vazio");
		}
		
		return retorno;		
	}

	public void agendarTarefaBatch() {
		AgendadorTarefas.agendarTarefa("RelatorioSolicitacaoAcesso", this);
	}

}
