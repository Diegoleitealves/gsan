package gcom.relatorio.cadastro.imovel;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
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
 * [UC0731] Gerar Relat�rio de Im�veis com os Ultimos Consumos de Agua
 * 
 * @author Rafael Pinto
 * @date 18/12/2007
 */
public class RelatorioImoveisUltimosConsumosAgua extends TarefaRelatorio {
	
	private static final long serialVersionUID = 1L;
	
	public RelatorioImoveisUltimosConsumosAgua(Usuario usuario) {
		super(usuario, ConstantesRelatorios.RELATORIO_IMOVEIS_ULTIMOS_CONSUMOS_AGUA);
	}

	@Deprecated
	public RelatorioImoveisUltimosConsumosAgua() {
		super(null, "");
	}

	public Object executar() throws TarefaException {

		// valor de retorno
		byte[] retorno = null;

		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();

		FiltrarRelatorioImoveisUltimosConsumosAguaHelper filtro = 
			(FiltrarRelatorioImoveisUltimosConsumosAguaHelper) getParametro("filtrarRelatorioImoveisUltimosConsumosAguaHelper");
		
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		RelatorioImoveisUltimosConsumosAguaBean relatorioBean = null;

		Collection<RelatorioImoveisUltimosConsumosAguaHelper> colecao =
			fachada.pesquisarRelatorioImoveisUltimosConsumosAgua(filtro);

		// se a cole��o de par�metros da analise n�o for vazia
		if (colecao != null && !colecao.isEmpty()) {

			// coloca a cole��o de par�metros da analise no iterator
			Iterator colecaoIterator = colecao.iterator();

			// la�o para criar a cole��o de par�metros da analise
			while (colecaoIterator.hasNext()) {

				RelatorioImoveisUltimosConsumosAguaHelper helper = 
					(RelatorioImoveisUltimosConsumosAguaHelper) colecaoIterator.next();
				
				relatorioBean = 
					new RelatorioImoveisUltimosConsumosAguaBean(helper);

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

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_IMOVEIS_ULTIMOS_CONSUMOS_AGUA,
				parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relat�rio no sistema
		try {
			persistirRelatorioConcluido(retorno, Relatorio.IMOVEIS_ULTIMOS_CONSUMOS_AGUA,
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
			Fachada.getInstancia().pesquisarTotalRegistroRelatorioImoveisUltimosConsumosAgua(
				(FiltrarRelatorioImoveisUltimosConsumosAguaHelper) 
					getParametro("filtrarRelatorioImoveisUltimosConsumosAguaHelper"));
		
		if(retorno == 0 ){			
			throw new ActionServletException("atencao.pesquisa.nenhumresultado");
		}

		return retorno;		
	}

	public void agendarTarefaBatch() {
		AgendadorTarefas.agendarTarefa("RelatorioImoveisUltimosConsumosAgua", this);

	}

}
