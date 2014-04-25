package gcom.relatorio.gerencial.faturamento;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.gerencial.bean.FiltrarRelatorioQuadroMetasExercicioHelper;
import gcom.gerencial.bean.QuadroMetasExercicioHelper;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.faturamento.RelatorioQuadroMetasExercicioBean;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * classe respons�vel por criar o relat�rio de quadro de metas por exercicio
 * 
 * @author Bruno Barros
 * @created 10/03/2008
 */
public class RelatorioQuadroMetasExercicio extends TarefaRelatorio {
	
	private static final long serialVersionUID = 1L;
	
	public RelatorioQuadroMetasExercicio(Usuario usuario) {
		super(usuario, ConstantesRelatorios.RELATORIO_QUADRO_METAS_EXERCICIO);
	}

	@Deprecated
	public RelatorioQuadroMetasExercicio() {
		super(null, "");
	}

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param bairros
	 *            Description of the Parameter
	 * @param bairroParametros
	 *            Description of the Parameter
	 * @return Descri��o do retorno
	 * @exception RelatorioVazioException
	 *                Descri��o da exce��o
	 */
	public Object executar() throws TarefaException {

		// valor de retorno
		byte[] retorno = null;

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		FiltrarRelatorioQuadroMetasExercicioHelper filtro = 
			(FiltrarRelatorioQuadroMetasExercicioHelper) getParametro("filtrarRelatorioQuadroMetasExercicioHelper");
		
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		RelatorioQuadroMetasExercicioBean relatorioQuadroMetasExercicioBean = null;

		Collection<QuadroMetasExercicioHelper> colecao = 
			fachada.pesquisarRelatorioQuadroMetasExercicio(filtro);

		// se a cole��o de par�metros da analise n�o for vazia
		if (colecao != null && !colecao.isEmpty()) {

			// coloca a cole��o de par�metros da analise no iterator
			Iterator colecaoIterator = colecao.iterator();

			// la�o para criar a cole��o de par�metros da analise
			while (colecaoIterator.hasNext()) {

				QuadroMetasExercicioHelper helper = 
					(QuadroMetasExercicioHelper) colecaoIterator.next();
				
				relatorioQuadroMetasExercicioBean = 
					new RelatorioQuadroMetasExercicioBean(helper);

				// adiciona o bean a cole��o
				relatorioBeans.add(relatorioQuadroMetasExercicioBean);				
				
				
			}
		}
		// __________________________________________________________________

		// Par�metros do relat�rio
		Map parametros = new HashMap();
		
		// adiciona os par�metros do relat�rio
		// adiciona o laudo da an�lise
		
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		
		parametros.put("imagem", sistemaParametro.getImagemRelatorio());
		parametros.put("ano", Util.formatarAnoMesParaMesAno(filtro.getAnoExercicio()));
		

		// cria uma inst�ncia do dataSource do relat�rio
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_QUADRO_METAS_EXERCICIO,
				parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relat�rio no sistema
		try {
			persistirRelatorioConcluido(retorno, Relatorio.QUADRO_METAS_EXERCICIO,
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
		int retorno = -1;

		return retorno;
	}

	public void agendarTarefaBatch() {
		AgendadorTarefas.agendarTarefa("RelatorioQuadoMetasExercicio", this);

	}

}
