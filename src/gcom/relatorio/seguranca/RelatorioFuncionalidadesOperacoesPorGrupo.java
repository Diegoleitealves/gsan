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
 * classe respons�vel por criar o relat�rio de Funcionalidades e Operacoes por Grupo
 * 
 * [UC1039] Gerar Relat�rio de Funcionalidades e Operacoes por Grupo
 * 
 * @author Hugo Leonardo
 *
 * @date 15/07/2010
 */
public class RelatorioFuncionalidadesOperacoesPorGrupo extends TarefaRelatorio {
	
	private static final long serialVersionUID = 1L;
	
	public RelatorioFuncionalidadesOperacoesPorGrupo(Usuario usuario) {
		super(usuario, ConstantesRelatorios.RELATORIO_FUNCIONALIDADE_E_OPERACAO_POR_GRUPO);
	}

	@Deprecated
	public RelatorioFuncionalidadesOperacoesPorGrupo() {
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

		FiltrarRelatorioFuncionalidadeOperacoesPorGrupoHelper relatorioHelper = 
			(FiltrarRelatorioFuncionalidadeOperacoesPorGrupoHelper) getParametro("FiltrarRelatorioFuncionalidadeOperacoesPorGrupoHelper");
		
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		RelatorioFuncionalidadeOperacoesPorGrupoBean relatorioFuncionalidadeOperacoesPorGrupoBean = null;

		Collection<RelatorioFuncionalidadeOperacoesPorGrupoHelper> colecao =  
			fachada.pesquisarRelatorioFuncionalidadeOperacoesPorGrupo(relatorioHelper);

		// se a cole��o de par�metros da analise n�o for vazia
		if (colecao != null && !colecao.isEmpty()) {

			// coloca a cole��o de par�metros da analise no iterator
			Iterator colecaoIterator = colecao.iterator();

			// la�o para criar a cole��o de par�metros da analise
			while (colecaoIterator.hasNext()) {

				RelatorioFuncionalidadeOperacoesPorGrupoHelper helper = 
					(RelatorioFuncionalidadeOperacoesPorGrupoHelper) colecaoIterator.next();
				
				relatorioFuncionalidadeOperacoesPorGrupoBean = 
					new RelatorioFuncionalidadeOperacoesPorGrupoBean(
							helper.getGrupo(),
							helper.getModulo(),
							helper.getFuncionalidade(),
							helper.getOperacao() );

				// adiciona o bean a cole��o
				relatorioBeans.add(relatorioFuncionalidadeOperacoesPorGrupoBean);				
			}
		}
		// __________________________________________________________________

		// Par�metros do relat�rio
		Map parametros = new HashMap();
		
		// adiciona os par�metros do relat�rio
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		
		parametros.put("imagem", sistemaParametro.getImagemRelatorio());		
		
		// cria uma inst�ncia do dataSource do relat�rio
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_FUNCIONALIDADE_E_OPERACAO_POR_GRUPO,
				parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relat�rio no sistema
		try {
			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_FUNCIONALIDADE_E_OPERACAO_POR_GRUPO,
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

		retorno = Fachada.getInstancia().pesquisarTotalRelatorioFuncionalidadeOperacoesPorGrupo(
				(FiltrarRelatorioFuncionalidadeOperacoesPorGrupoHelper) 
					getParametro("FiltrarRelatorioFuncionalidadeOperacoesPorGrupoHelper"));
        
		if (retorno == 0) {
			// Caso a pesquisa n�o retorne nenhum resultado comunica ao
			// usu�rio;
			throw new RelatorioVazioException("atencao.relatorio.vazio");
		}
		
		return retorno;		
	}

	public void agendarTarefaBatch() {
		AgendadorTarefas.agendarTarefa("RelatorioFuncionalidadesOperacoesPorGrupo", this);
	}

}
