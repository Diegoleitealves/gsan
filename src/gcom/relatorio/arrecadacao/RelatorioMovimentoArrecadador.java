package gcom.relatorio.arrecadacao;

import gcom.arrecadacao.ArrecadadorMovimento;
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
public class RelatorioMovimentoArrecadador extends TarefaRelatorio {
	private static final long serialVersionUID = 1L;
	/**
	 * Construtor da classe RelatorioAnaliseFisicoQuimicaAgua
	 */
	public RelatorioMovimentoArrecadador(Usuario usuario) {
		super(usuario, ConstantesRelatorios.RELATORIO_MOVIMENTO_ARRECADADOR);
	}

	@Deprecated
	public RelatorioMovimentoArrecadador() {
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

		Fachada fachada = Fachada.getInstancia();

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		Collection arrecadadoresMovimentos = (Collection) getParametro("arrecadadoresMovimentos");

		// valor de retorno
		byte[] retorno = null;

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		RelatorioMovimentoArrecadadorBean relatorioMovimentoArrecadadorBean = null;
		// se a cole��o de par�metros da analise n�o for vazia
		if (arrecadadoresMovimentos != null
				&& !arrecadadoresMovimentos.isEmpty()) {

			Iterator iteArrecadadorMovimento = arrecadadoresMovimentos
					.iterator();

			while (iteArrecadadorMovimento.hasNext()) {

				ArrecadadorMovimento arrecadadorMovimento = (ArrecadadorMovimento) iteArrecadadorMovimento
						.next();
				relatorioMovimentoArrecadadorBean = new RelatorioMovimentoArrecadadorBean(
						"" + arrecadadorMovimento.getCodigoBanco(),
						arrecadadorMovimento.getNomeBanco(),
						"" + arrecadadorMovimento.getNumeroSequencialArquivo(),
						"" + arrecadadorMovimento.getNumeroRegistrosMovimento(),
						Util.formatarMoedaReal(arrecadadorMovimento
								.getValorTotalMovimento()),
						arrecadadorMovimento.getDescricaoIdentificacaoServico(),
						Util.formatarHoraSemData(arrecadadorMovimento
								.getUltimaAlteracao()));

				// adiciona o bean a cole��o
				relatorioBeans.add(relatorioMovimentoArrecadadorBean);
			}

		}
		// __________________________________________________________________

		// Par�metros do relat�rio
		Map parametros = new HashMap();

		SistemaParametro sistemaParametro = fachada
				.pesquisarParametrosDoSistema();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		// cria uma inst�ncia do dataSource do relat�rio
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = this.gerarRelatorio(
				ConstantesRelatorios.RELATORIO_MOVIMENTO_ARRECADADOR,
				parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relat�rio no sistema
		try {
			persistirRelatorioConcluido(retorno,
					Relatorio.MOVIMENTO_ARRECADADOR, idFuncionalidadeIniciada);
		} catch (ControladorException e) {
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relat�rio no sistema", e);
		}
		// ------------------------------------

		// retorna o relat�rio gerado
		return retorno;
	}

	public int calcularTotalRegistrosRelatorio() {

		int retorno = 1;
		return retorno;
	}

	public void agendarTarefaBatch() {
		AgendadorTarefas.agendarTarefa("RelatorioMovimentoArrecadador", this);
	}

}
