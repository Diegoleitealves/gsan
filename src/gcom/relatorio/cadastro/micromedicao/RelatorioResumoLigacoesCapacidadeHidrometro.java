package gcom.relatorio.cadastro.micromedicao;

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
 * [UC0997] Gerar Resumo de Liga��es por Capacidade de Hidr�metro
 * 
 * @author Hugo Leonardo
 *
 * @date 29/03/2010
 */
public class RelatorioResumoLigacoesCapacidadeHidrometro extends TarefaRelatorio {
	
	private static final long serialVersionUID = 1L;
	
	public RelatorioResumoLigacoesCapacidadeHidrometro(Usuario usuario) {
		super(usuario, ConstantesRelatorios.RELATORIO_RESUMO_LIGACOES_CAPACIDADE_HIDROMETRO);
	}

	@Deprecated
	public RelatorioResumoLigacoesCapacidadeHidrometro() {
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

		RelatorioResumoLigacoesCapacidadeHidrometroHelper relatorioHelper = 
			(RelatorioResumoLigacoesCapacidadeHidrometroHelper) getParametro("relatorioResumoLigacoesCapacidadeHidrometroHelper");
		
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		String mesAno = (String) getParametro("mesAno");

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		Collection colecao =  
			fachada.pesquisarRelatorioResumoLigacoesCapacidadeHidrometro(relatorioHelper);

		// se a cole��o de par�metros da analise n�o for vazia
		if (colecao != null && !colecao.isEmpty()) {

			for (Iterator iterator = colecao.iterator(); iterator.hasNext();) {
				RelatorioResumoLigacoesCapacidadeHidrometroBean bean = (RelatorioResumoLigacoesCapacidadeHidrometroBean) iterator.next();
				relatorioBeans.add(bean);
			}
		
		}
		// __________________________________________________________________

		// Par�metros do relat�rio
		Map parametros = new HashMap();
		
		// adiciona os par�metros do relat�rio
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		
		parametros.put("imagem", sistemaParametro.getImagemRelatorio());		
	
		parametros.put("mesAno" , mesAno);
				
		// cria uma inst�ncia do dataSource do relat�rio
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_RESUMO_LIGACOES_CAPACIDADE_HIDROMETRO,
				parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relat�rio no sistema
		try {
			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_RESUMO_LIGACOES_CAPACIDADE_HIDROMETRO,
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
		
		Integer retorno = 0;
		
		retorno = 
			Fachada.getInstancia().countRelatorioResumoLigacoesCapacidadeHidrometro(
				(RelatorioResumoLigacoesCapacidadeHidrometroHelper) 
					getParametro("relatorioResumoLigacoesCapacidadeHidrometroHelper"));
        
		if (retorno == 0) {
			// Caso a pesquisa n�o retorne nenhum resultado comunica ao
			// usu�rio;
			throw new RelatorioVazioException("atencao.relatorio.vazio");

		}
		
		return retorno;		
	}

	public void agendarTarefaBatch() {
		AgendadorTarefas.agendarTarefa("RelatorioResumoLigacoesCapacidadeHidrometro", this);

	}

}
