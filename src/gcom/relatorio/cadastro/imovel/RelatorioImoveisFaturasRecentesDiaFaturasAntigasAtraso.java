package gcom.relatorio.cadastro.imovel;

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
 * [UC00730] Gerar Relat�rio de Im�veis com Faturas Recentes em Dia e Faturas Antigas em Atraso
 * 
 * @author Rafael Pinto
 * @date 08/01/2008
 */
public class RelatorioImoveisFaturasRecentesDiaFaturasAntigasAtraso extends TarefaRelatorio {
	
	private static final long serialVersionUID = 1L;
	
	public RelatorioImoveisFaturasRecentesDiaFaturasAntigasAtraso(Usuario usuario) {
		super(usuario, ConstantesRelatorios.RELATORIO_IMOVEIS_FATURAS_RECENTES_DIA_FATURAS_ANTIGAS_ATRASO);
	}

	@Deprecated
	public RelatorioImoveisFaturasRecentesDiaFaturasAntigasAtraso() {
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

		FiltrarRelatorioImoveisFaturasRecentesDiaFaturasAntigasAtrasoHelper filtro = 
			(FiltrarRelatorioImoveisFaturasRecentesDiaFaturasAntigasAtrasoHelper) getParametro("filtrarRelatorioImoveisFaturasRecentesDiaFaturasAntigasAtrasoHelper");
		
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		RelatorioImoveisFaturasRecentesDiaFaturasAntigasAtrasoBean relatorioBean = null;

		Collection<RelatorioImoveisFaturasRecentesDiaFaturasAntigasAtrasoHelper> colecao = 
			fachada.pesquisarRelatorioImoveisFaturasRecentesDiaFaturasAntigasAtraso(filtro);

		// se a cole��o de par�metros da analise n�o for vazia
		if (colecao != null && !colecao.isEmpty()) {

			// coloca a cole��o de par�metros da analise no iterator
			Iterator colecaoIterator = colecao.iterator();

			// la�o para criar a cole��o de par�metros da analise
			while (colecaoIterator.hasNext()) {

				RelatorioImoveisFaturasRecentesDiaFaturasAntigasAtrasoHelper helper = 
					(RelatorioImoveisFaturasRecentesDiaFaturasAntigasAtrasoHelper) colecaoIterator.next();
				
				relatorioBean = 
					new RelatorioImoveisFaturasRecentesDiaFaturasAntigasAtrasoBean(helper);

				// adiciona o bean a cole��o
				relatorioBeans.add(relatorioBean);				
				
				
			}
		}
		// __________________________________________________________________

		// Par�metros do relat�rio
		Map parametros = new HashMap();
		
		// adiciona os par�metros do relat�rio
		// adiciona o laudo da an�lise
		
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		
		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		// cria uma inst�ncia do dataSource do relat�rio
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_IMOVEIS_FATURAS_RECENTES_DIA_FATURAS_ANTIGAS_ATRASO,
				parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relat�rio no sistema
		try {
			persistirRelatorioConcluido(retorno, Relatorio.IMOVEIS_FATURAS_RECENTES_DIA_FATURAS_ANTIGAS_ATRASO,
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

		retorno = 
			Fachada.getInstancia().pesquisarTotalRelatorioImoveisFaturasRecentesDiaFaturasAntigasAtraso(
				(FiltrarRelatorioImoveisFaturasRecentesDiaFaturasAntigasAtrasoHelper) 
					getParametro("filtrarRelatorioImoveisFaturasRecentesDiaFaturasAntigasAtrasoHelper"));

		return retorno;		
	}

	public void agendarTarefaBatch() {
		AgendadorTarefas.agendarTarefa("RelatorioImoveisFaturasRecentesDiaFaturasAntigasAtraso", this);

	}

}
