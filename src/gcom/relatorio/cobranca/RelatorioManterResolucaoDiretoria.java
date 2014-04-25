package gcom.relatorio.cobranca;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.FiltroResolucaoDiretoria;
import gcom.cobranca.ResolucaoDiretoria;
import gcom.fachada.Fachada;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.RelatorioVazioException;
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
 * classe respons�vel por criar o relat�rio de bairro manter de �gua
 * 
 * @author S�vio Luiz
 * @created 11 de Julho de 2005
 */
public class RelatorioManterResolucaoDiretoria extends TarefaRelatorio {
	private static final long serialVersionUID = 1L;
	public RelatorioManterResolucaoDiretoria(Usuario usuario) {
		super(usuario, ConstantesRelatorios.RELATORIO_RESOLUCAO_DIRETORIA_MANTER);
	}
	
	@Deprecated
	public RelatorioManterResolucaoDiretoria() {
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

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		FiltroResolucaoDiretoria filtroResolucaoDiretoria = (FiltroResolucaoDiretoria) getParametro("filtroResolucaoDiretoria");
		ResolucaoDiretoria resolucaoDiretoriaParametros = (ResolucaoDiretoria) getParametro("resolucaoDiretoriaParametros");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// valor de retorno
		byte[] retorno = null;

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		RelatorioManterResolucaoDiretoriaBean relatorioBean = null;

		filtroResolucaoDiretoria.setConsultaSemLimites(true);

		Collection colecaoResolucoesDiretoria = fachada.pesquisar(
				filtroResolucaoDiretoria, ResolucaoDiretoria.class.getName());

		// se a cole��o de par�metros da analise n�o for vazia
		if (colecaoResolucoesDiretoria != null
				&& !colecaoResolucoesDiretoria.isEmpty()) {

			// coloca a cole��o de par�metros da analise no iterator
			Iterator colecaoResolucoesDiretoriaIterator = colecaoResolucoesDiretoria
					.iterator();

			// la�o para criar a cole��o de par�metros da analise
			while (colecaoResolucoesDiretoriaIterator.hasNext()) {

				ResolucaoDiretoria resolucaoDiretoria = (ResolucaoDiretoria) colecaoResolucoesDiretoriaIterator
						.next();
				
				// Faz as valida��es dos campos necess�riose e formata a String
				// para a forma como ir� aparecer no relat�rio
				
				// Data Vig�ncia In�cio
				String dataVigenciaInicio = "";
				
				if (resolucaoDiretoria.getDataVigenciaInicio() != null) {
					dataVigenciaInicio = Util.formatarData(resolucaoDiretoria
							.getDataVigenciaInicio());
				}
				
				// Data Vig�ncia Fim
				String dataVigenciaFim = "";
				
				if (resolucaoDiretoria.getDataVigenciaFim() != null) {
					dataVigenciaFim = Util.formatarData(resolucaoDiretoria
							.getDataVigenciaFim());
				}

				relatorioBean = new RelatorioManterResolucaoDiretoriaBean(
						
						// N�mero
						resolucaoDiretoria.getNumeroResolucaoDiretoria(),
						
						// Assunto
						resolucaoDiretoria.getDescricaoAssunto(),
						
						// Data da Vig�ncia In�cio
						dataVigenciaInicio,
										
						// Data da Vig�ncia Fim
						dataVigenciaFim);

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
		
		parametros.put("numero", resolucaoDiretoriaParametros
				.getNumeroResolucaoDiretoria());

		parametros.put("assunto", resolucaoDiretoriaParametros
				.getDescricaoAssunto());

		if (resolucaoDiretoriaParametros.getDataVigenciaInicio() != null) {
			parametros.put("dataInicio", Util
					.formatarData(resolucaoDiretoriaParametros
							.getDataVigenciaInicio()));
		} else {
			parametros.put("dataInicio", "");
		}

		if (resolucaoDiretoriaParametros.getDataVigenciaFim() != null) {
			parametros.put("dataTermino", Util
					.formatarData(resolucaoDiretoriaParametros
							.getDataVigenciaFim()));
		} else {
			parametros.put("dataTermino", "");
		}

		// cria uma inst�ncia do dataSource do relat�rio
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = gerarRelatorio(
				ConstantesRelatorios.RELATORIO_RESOLUCAO_DIRETORIA_MANTER,
				parametros, ds, tipoFormatoRelatorio);
		
		// ------------------------------------
		// Grava o relat�rio no sistema
		try {
			persistirRelatorioConcluido(retorno, Relatorio.MANTER_RESOLUCAO_DIRETORIA,
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

		retorno = Fachada
				.getInstancia()
				.totalRegistrosPesquisa(
						(FiltroResolucaoDiretoria) getParametro("filtroResolucaoDiretoria"),
						ResolucaoDiretoria.class.getName());

		return retorno;
	}

	public void agendarTarefaBatch() {
		AgendadorTarefas.agendarTarefa("RelatorioManterResolucaoDiretoria", this);
	}
}
