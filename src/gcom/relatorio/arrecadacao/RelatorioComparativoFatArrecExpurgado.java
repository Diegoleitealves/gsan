package gcom.relatorio.arrecadacao;

import gcom.batch.Relatorio;
import gcom.cadastro.localidade.FiltroGerenciaRegional;
import gcom.cadastro.localidade.FiltroUnidadeNegocio;
import gcom.cadastro.localidade.GerenciaRegional;
import gcom.cadastro.localidade.UnidadeNegocio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
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
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * [UC00744] Gerar Comparativo do Faturamento, Arrecada��o e Expurgo
 * 
 * @author S�vio Luiz
 * 
 * @date 09/12/2008
 */

public class RelatorioComparativoFatArrecExpurgado extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioComparativoFatArrecExpurgado(Usuario usuario) {
		super(usuario,
				ConstantesRelatorios.RELATORIO_COMPARATIVO_FATURAMENTO_ARRECADACAO_EXPURGO);
	}
	
	int quantidadeRegistros;

	@Deprecated
	public RelatorioComparativoFatArrecExpurgado() {
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

		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		String gerenciaRegional = (String) getParametro("gerenciaRegional");
		String unidadeNegocio = (String) getParametro("unidadeNegocio");
		String mesAnoReferencia = (String) getParametro("mesAnoreferencia");

		Integer anoMesreferencia = Util
				.formatarMesAnoComBarraParaAnoMes(mesAnoReferencia);

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		Collection<RelatorioComparativoFatArrecExpurgoBean> colecaoComparativoExpurgoBean = fachada
				.pesquisarDadosComparativosFaturamentoArrecadacaoExpurgo(
						anoMesreferencia, gerenciaRegional, unidadeNegocio);

	

				// adiciona o bean a cole��o
				relatorioBeans.addAll(colecaoComparativoExpurgoBean);

		// __________________________________________________________________

		// Par�metros do relat�rio
		Map parametros = new HashMap();

		// adiciona os par�metros do relat�rio
		// adiciona o laudo da an�lise

		SistemaParametro sistemaParametro = fachada
				.pesquisarParametrosDoSistema();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());
		
		parametros.put("mesAnoReferencia", mesAnoReferencia);
		
		if ((gerenciaRegional == null || gerenciaRegional.equals(""))
				&& (unidadeNegocio == null || unidadeNegocio.equals(""))) {
			parametros.put("opcaoTotalizacao", "ESTADO");
		}else{
			if(gerenciaRegional != null && !gerenciaRegional.equals("")){
				FiltroGerenciaRegional filtroGerenciaRegional = new FiltroGerenciaRegional();
				filtroGerenciaRegional.adicionarParametro(new ParametroSimples(FiltroGerenciaRegional.ID,gerenciaRegional));
				Collection colecaoGR = fachada.pesquisar(filtroGerenciaRegional,GerenciaRegional.class.getName());
				GerenciaRegional grBase = (GerenciaRegional)Util.retonarObjetoDeColecao(colecaoGR);
				parametros.put("opcaoTotalizacao", "GER�NCIA REGIONAL - "+grBase.getNome().toUpperCase());
			}
			
			if(unidadeNegocio != null && !unidadeNegocio.equals("")){
				FiltroUnidadeNegocio filtroUnidadeNegocio = new FiltroUnidadeNegocio();
				filtroUnidadeNegocio.adicionarParametro(new ParametroSimples(FiltroUnidadeNegocio.ID,unidadeNegocio));
				Collection colecaoUN = fachada.pesquisar(filtroUnidadeNegocio,UnidadeNegocio.class.getName());
				UnidadeNegocio unBase = (UnidadeNegocio)Util.retonarObjetoDeColecao(colecaoUN);
				parametros.put("opcaoTotalizacao", "UNIDADE NEG�CIO - "+unBase.getNome().toUpperCase());
			}
		}
		
		parametros.put("imagem", sistemaParametro.getImagemRelatorio());
		parametros.put("tipoRelatorio", "R0744");

		// cria uma inst�ncia do dataSource do relat�rio
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = gerarRelatorio(
				ConstantesRelatorios.RELATORIO_COMPARATIVO_FATURAMENTO_ARRECADACAO_EXPURGO,
				parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relat�rio no sistema
		try {
			persistirRelatorioConcluido(retorno,
					Relatorio.COMPARATIVO_FATURAMENTO_ARRECADACAO_EXPURGO, idFuncionalidadeIniciada);
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


		Integer anoMesreferencia = Util
		.formatarMesAnoComBarraParaAnoMes((String)getParametro("mesAnoreferencia"));
		
		int retorno = Fachada
				.getInstancia()
				.pesquisarQuantidadeDadosComparativosFaturamentoArrecadacaoExpurgo(anoMesreferencia,(String) getParametro("gerenciaRegional"),
						(String) getParametro("unidadeNegocio"));

		return retorno;
	}

	public void agendarTarefaBatch() {
		AgendadorTarefas.agendarTarefa("RelatorioComparativoFatArrecExpurgado", this);

	}

}
