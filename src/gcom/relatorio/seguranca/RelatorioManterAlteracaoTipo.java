package gcom.relatorio.seguranca;

import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.RelatorioVazioException;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.transacao.AlteracaoTipo;
import gcom.seguranca.transacao.FiltroAlteracaoTipo;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Vinicius Medeiros
 * @version 1.0
 */

public class RelatorioManterAlteracaoTipo extends TarefaRelatorio {
	private static final long serialVersionUID = 1L;
	public RelatorioManterAlteracaoTipo(Usuario usuario) {
		super(usuario, ConstantesRelatorios.RELATORIO_MANTER_ALTERACAO_TIPO);
	}
	
	@Deprecated
	public RelatorioManterAlteracaoTipo() {
		super(null, "");
	}

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param atividades
	 *            Description of the Parameter
	 * @param atividadeParametros
	 *            Description of the Parameter
	 * @return Descri��o do retorno
	 * @exception RelatorioVazioException
	 *                Descri��o da exce��o
	 */

	public Object executar() throws TarefaException {

		// ------------------------------------
//		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		FiltroAlteracaoTipo filtroAlteracaoTipo = (FiltroAlteracaoTipo) getParametro("filtroAlteracaoTipo");
		AlteracaoTipo alteracaoTipoParametros = (AlteracaoTipo) getParametro("alteracaoTipoParametros");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// valor de retorno
		byte[] retorno = null;

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		RelatorioManterAlteracaoTipoBean relatorioBean = null;

		Fachada fachada = Fachada.getInstancia();

		filtroAlteracaoTipo.setConsultaSemLimites(true);

		Collection<AlteracaoTipo> colecaoAlteracoesTipo = fachada.pesquisar(filtroAlteracaoTipo,
				AlteracaoTipo.class.getName());

		// se a cole��o de par�metros da analise n�o for vazia
		if (colecaoAlteracoesTipo != null && !colecaoAlteracoesTipo.isEmpty()) {

			// la�o para criar a cole��o de par�metros da analise
			for (AlteracaoTipo alteracaoTipo : colecaoAlteracoesTipo) {
				
				relatorioBean = new RelatorioManterAlteracaoTipoBean(
						// C�digo
						alteracaoTipo.getId().toString(), 
						
						// Descri��o
						alteracaoTipo.getDescricao(),
						
						// Descri��o Abreviada
						alteracaoTipo.getDescricaoAbreviada());
						
				// adiciona o bean a cole��o
				relatorioBeans.add(relatorioBean);
				
			}
			
		}

		// Par�metros do relat�rio
		Map parametros = new HashMap();

		// adiciona os par�metros do relat�rio
		// adiciona o laudo da an�lise
		SistemaParametro sistemaParametro = fachada
				.pesquisarParametrosDoSistema();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		if (alteracaoTipoParametros.getId() != null) {
			parametros.put("id",
					alteracaoTipoParametros.getId().toString());
		} else {
			parametros.put("id", "");
		}

		parametros.put("descricao", alteracaoTipoParametros.getDescricao());

		parametros.put("descricaoAbreviada", alteracaoTipoParametros.getDescricaoAbreviada());
		
		
		// cria uma inst�ncia do dataSource do relat�rio

		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = this.gerarRelatorio(
				ConstantesRelatorios.RELATORIO_MANTER_ALTERACAO_TIPO, parametros,
				ds, tipoFormatoRelatorio);
		
		// ------------------------------------
		// Grava o relat�rio no sistema
//		try {
//			persistirRelatorioConcluido(retorno, Relatorio.MANTER_ATIVIDADE,
//					idFuncionalidadeIniciada);
//		} catch (ControladorException e) {
//			e.printStackTrace();
//			throw new TarefaException("Erro ao gravar relat�rio no sistema", e);
//		}
		// ------------------------------------

		// retorna o relat�rio gerado
		return retorno;
	}

	@Override
	public int calcularTotalRegistrosRelatorio() {
		int retorno = 0;

		return retorno;
	}

	public void agendarTarefaBatch() {
		AgendadorTarefas.agendarTarefa("RelatorioManterAlteracaoTipo", this);
	}

}
