package gcom.relatorio.arrecadacao;

import gcom.arrecadacao.bean.GerarMovimentoDebitoAutomaticoBancoHelper;
import gcom.batch.Relatorio;
import gcom.cadastro.geografico.FiltroBairro;
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
public class RelatorioMovimentoDebitoAutomaticoBanco extends TarefaRelatorio {
	private static final long serialVersionUID = 1L;
	/**
	 * Construtor da classe RelatorioAnaliseFisicoQuimicaAgua
	 */
	public RelatorioMovimentoDebitoAutomaticoBanco(Usuario usuario) {
		super(usuario, ConstantesRelatorios.RELATORIO_MOVIMENTO_DEBITO_AUTOMATICO_BANCO);
	}
	
	@Deprecated
	public RelatorioMovimentoDebitoAutomaticoBanco() {
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

		Collection colecaoMovimentoDebitoAutomaticoBanco = (Collection) getParametro("colecaoGerarMovimentoDebitoAutomatico");

		// valor de retorno
		byte[] retorno = null;

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();
		
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		RelatorioMovimentoDebitoAutomaticoBancoBean relatorioMovimentoDebitoAutomaticoBancoBean = null;
		// se a cole��o de par�metros da analise n�o for vazia
		if (colecaoMovimentoDebitoAutomaticoBanco != null
				&& !colecaoMovimentoDebitoAutomaticoBanco.isEmpty()) {
			// coloca a cole��o de par�metros do Movimento Debito Automatico
			// Banco no iterator
			Iterator movimentoDebitoAutomaticoBancoIterator = colecaoMovimentoDebitoAutomaticoBanco
					.iterator();

			FiltroBairro filtroBairro = new FiltroBairro();
			filtroBairro.adicionarCaminhoParaCarregamentoEntidade("municipio");

			// la�o para criar a cole��o de par�metros da analise
			while (movimentoDebitoAutomaticoBancoIterator.hasNext()) {

				GerarMovimentoDebitoAutomaticoBancoHelper gerarMovimentoDebitoAutomaticoBancoHelper = (GerarMovimentoDebitoAutomaticoBancoHelper) movimentoDebitoAutomaticoBancoIterator
						.next();

				relatorioMovimentoDebitoAutomaticoBancoBean = new RelatorioMovimentoDebitoAutomaticoBancoBean(
						""
								+ gerarMovimentoDebitoAutomaticoBancoHelper
										.getBanco().getId(),
						gerarMovimentoDebitoAutomaticoBancoHelper.getBanco()
								.getDescricao(),
						""
								+ gerarMovimentoDebitoAutomaticoBancoHelper
										.getArrecadadorMovimento()
										.getNumeroSequencialArquivo(),
						""
								+ gerarMovimentoDebitoAutomaticoBancoHelper
										.getArrecadadorMovimento()
										.getNumeroRegistrosMovimento(),
						Util
								.formatarMoedaReal(gerarMovimentoDebitoAutomaticoBancoHelper
										.getArrecadadorMovimento()
										.getValorTotalMovimento()),
						gerarMovimentoDebitoAutomaticoBancoHelper
								.getDescricaoEmail(),
						gerarMovimentoDebitoAutomaticoBancoHelper
								.getSituacaoEnvioEmail());

				// adiciona o bean a cole��o
				relatorioBeans.add(relatorioMovimentoDebitoAutomaticoBancoBean);
			}
		}
		// __________________________________________________________________

		// Par�metros do relat�rio
		Map parametros = new HashMap();
		
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		
		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		// cria uma inst�ncia do dataSource do relat�rio
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = this.gerarRelatorio(
				ConstantesRelatorios.RELATORIO_MOVIMENTO_DEBITO_AUTOMATICO_BANCO, parametros,
				ds, tipoFormatoRelatorio);
		
		// ------------------------------------
		// Grava o relat�rio no sistema
		try {
			persistirRelatorioConcluido(retorno, Relatorio.MOVIMENTO_DEBITO_AUTOMATICO_BANCO,
					idFuncionalidadeIniciada);
		} catch (ControladorException e) {
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relat�rio no sistema", e);
		}
		// ------------------------------------

		// retorna o relat�rio gerado
		return retorno;
	}

	public int calcularTotalRegistrosRelatorio() {

		int retorno = 0;

		if (getParametro("colecaoGerarMovimentoDebitoAutomatico") != null
				&& getParametro("colecaoGerarMovimentoDebitoAutomatico") instanceof Collection) {
			retorno = ((Collection) getParametro("colecaoGerarMovimentoDebitoAutomatico")).size();
		}
		return retorno;
	}

	public void agendarTarefaBatch() {
		AgendadorTarefas.agendarTarefa("RelatorioMovimentoDebitoAutomaticoBanco", this);
	}

}
