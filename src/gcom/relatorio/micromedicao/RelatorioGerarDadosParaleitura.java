package gcom.relatorio.micromedicao;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.micromedicao.bean.GerarDadosParaLeituraHelper;
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
 * classe respons�vel por criar as contas apartir do txt
 * 
 * @author S�vio Luiz
 * @created 28/09/2007
 */
public class RelatorioGerarDadosParaleitura extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioGerarDadosParaleitura(Usuario usuario) {
		super(usuario, ConstantesRelatorios.RELATORIO_GERAR_DADOS_PARA_LEITURA);
	}

	@Deprecated
	public RelatorioGerarDadosParaleitura() {
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
		RelatorioGerarDadosParaLeituraBean relatorioGerarDadosParaLeituraBean = null;

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		Collection colecaoGerarDadosParaLeituraHelper = (ArrayList) getParametro("colecaoGerarDadosParaLeituraHelper");

		// Par�metros do relat�rio
		Map parametros = new HashMap();

		if (colecaoGerarDadosParaLeituraHelper != null
				&& !colecaoGerarDadosParaLeituraHelper.isEmpty()) {

			Iterator itera = colecaoGerarDadosParaLeituraHelper.iterator();

			boolean primeiraVez = true;

			while (itera.hasNext()) {
				GerarDadosParaLeituraHelper gerarDadosParaLeituraHelper = (GerarDadosParaLeituraHelper) itera
						.next();

				// caso seja a primeira vez, ent�o recupera os parametros do
				// inicio do relat�rio
				if (primeiraVez) {
					parametros.put("codigoRota", gerarDadosParaLeituraHelper
							.getCodigoRota());
					parametros.put("descricaoLocalidade",
							gerarDadosParaLeituraHelper
									.getDescricaoLocalidade());
					parametros.put("anoMesReferencia",
							gerarDadosParaLeituraHelper.getAnoMesReferncia());
					parametros.put("grupo", gerarDadosParaLeituraHelper
							.getGrupo());
					parametros.put("codigoSetor", gerarDadosParaLeituraHelper
							.getCodigoSetor());
					parametros.put("dataPrevistaFaturamento", gerarDadosParaLeituraHelper
							.getDataPrevistaFaturamento());
					primeiraVez = false;
					continue;
				}

				relatorioGerarDadosParaLeituraBean = new RelatorioGerarDadosParaLeituraBean(
						gerarDadosParaLeituraHelper);

				relatorioBeans.add(relatorioGerarDadosParaLeituraBean);

			}

		}

		Fachada fachada = Fachada.getInstancia();

		// __________________________________________________________________

		// adiciona os par�metros do relat�rio
		// adiciona o laudo da an�lise

		SistemaParametro sistemaParametro = fachada
				.pesquisarParametrosDoSistema();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());
		
		parametros.put("tipoFormatoRelatorio", "R0083");

		// cria uma inst�ncia do dataSource do relat�rio
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_GERAR_DADOS_PARA_LEITURA,
				parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relat�rio no sistema
		try {
			persistirRelatorioConcluido(retorno, Relatorio.GERAR_DADOS_PARA_LEITURA,
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

		return retorno;
	}

	public void agendarTarefaBatch() {
		AgendadorTarefas.agendarTarefa("RelatorioGerarDadosParaleitura", this);

	}

}
