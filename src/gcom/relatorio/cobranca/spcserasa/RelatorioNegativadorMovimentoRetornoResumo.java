package gcom.relatorio.cobranca.spcserasa;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.bean.NegativadorMovimentoRetornoResumoHelper;
import gcom.fachada.Fachada;
import gcom.relatorio.ConstantesExecucaoRelatorios;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.RelatorioVazioException;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 
 * Title: GCOM
 * </p>
 * <p>
 * 
 * Description: Sistema de Gest�o Comercial
 * </p>
 * <p>
 * 
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * 
 * Company: COMPESA - Companhia Pernambucana de Saneamento
 * </p>
 * 
 * @author Yara Taciane
 * @created 23 de Abril de 2008
 * @version 1.0
 */

public class RelatorioNegativadorMovimentoRetornoResumo extends TarefaRelatorio {
	private static final long serialVersionUID = 1L;
	/**
	 * Constructor for the RelatorioNegativadorMovimentoRetornoResumo object
	 */
	public RelatorioNegativadorMovimentoRetornoResumo(Usuario usuario) {
		super(usuario, ConstantesRelatorios.RELATORIO_2_VIA_CONTA);
	}

	@Deprecated
	public RelatorioNegativadorMovimentoRetornoResumo() {
		super(null, "");
	}

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param NegativadorRegistroTipoParametros
	 *            Description of the Parameter
	 * @return Descri��o do retorno
	 * @exception RelatorioVazioException
	 *                Descri��o da exce��o
	 */

	public Object executar() throws TarefaException {

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		// Recebe os par�metros que ser�o utilizados no relat�rio		
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");
		
		NegativadorMovimentoRetornoResumoHelper helper = (NegativadorMovimentoRetornoResumoHelper) getParametro("parametros");
	

		// valor de retorno
		byte[] retorno = null;

		// cole��o de beans do relat�rio

		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		RelatorioNegativadorMovimentoRetornoResumoBean relatorioBean = null;

		// Cria adiciona os carregamentos dos objetos necess�rios para
		// a impress�o do relat�rio
		

		// Nova consulta para trazer objeto completo		

	
			// coloca a cole��o de par�metros da analise no iterator
		
				
				// Faz as valida��es dos campos necess�riose e formata a String
				// para a forma como ir� aparecer no relat�rio

		  
				String nomeNegativador = "";				
				if (helper.getNomeNegativador() != null) {
					nomeNegativador = helper.getNomeNegativador();
				}
				
			
				String dataProcessamento = "";				
				if (helper.getDataProcessamento() != null) {
					dataProcessamento = helper.getDataProcessamento();
				}
						
				String horaProcessamento = "";				
				if (helper.getHoraProcessamento() != null) {
					horaProcessamento = helper.getHoraProcessamento();
				}

		
				String numeroSequencialArquivo = "";				
				if (helper.getNumeroSequencialArquivo() != null) {
					numeroSequencialArquivo = helper.getNumeroSequencialArquivo();
				
				}
				
				String numeroRegistros = "";				
				if (helper.getNumeroRegistros() != null) {
					numeroRegistros = helper.getNumeroRegistros();
				
				}
				

				// Inicializa o construtor constitu�do dos campos
				// necess�rios para a impress�o do relatorio
				relatorioBean = new RelatorioNegativadorMovimentoRetornoResumoBean(
					
						nomeNegativador,
						
						dataProcessamento,
						
						horaProcessamento,
					
						numeroSequencialArquivo,
						
						numeroRegistros);

				// adiciona o bean a cole��o
				relatorioBeans.add(relatorioBean);
			
	

		// Par�metros do relat�rio
		Map parametros = new HashMap();

		// adiciona os par�metros do relat�rio
		// adiciona o laudo da an�lise
		SistemaParametro sistemaParametro = fachada
				.pesquisarParametrosDoSistema();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());	

		// cria uma inst�ncia do dataSource do relat�rio

		RelatorioDataSource ds = new RelatorioDataSource((List) relatorioBeans);

		retorno = this.gerarRelatorio(
				ConstantesRelatorios.RELATORIO_2_VIA_CONTA, parametros, ds,
				tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relat�rio no sistema
		try {
			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_BOLETIM_CADASTRO,
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
		int retorno = ConstantesExecucaoRelatorios.QUANTIDADE_NAO_INFORMADA;

		return retorno;
	}

	public void agendarTarefaBatch() {
		AgendadorTarefas.agendarTarefa("RelatorioNegativadorMovimentoRetornoResumo", this);
	}

}
