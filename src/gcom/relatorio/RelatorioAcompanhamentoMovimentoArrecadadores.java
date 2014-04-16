package gcom.relatorio;

import gcom.arrecadacao.ArrecadacaoForma;
import gcom.arrecadacao.Arrecadador;
import gcom.arrecadacao.bean.MovimentoArrecadadoresRelatorioHelper;
import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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
public class RelatorioAcompanhamentoMovimentoArrecadadores extends TarefaRelatorio {
	private static final long serialVersionUID = 1L;
	public RelatorioAcompanhamentoMovimentoArrecadadores(Usuario usuario) {
		super(usuario, ConstantesRelatorios.RELATORIO_ACOMPANHAMENTO_MOVIMENTO_ARRECADADORES);
	}

	@Deprecated
	public RelatorioAcompanhamentoMovimentoArrecadadores() {
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

		String mesAnoReferencia = (String) getParametro("mesAnoReferencia");
		Arrecadador arrecadador = (Arrecadador) getParametro("arrecadador");
		ArrecadacaoForma arrecadacaoForma = (ArrecadacaoForma) getParametro("arrecadacaoForma");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		RelatorioAcompanhamentoMovimentoArrecadadoresBean relatorioBean = null;
		
		Integer anoMesReferencia = Util.formatarMesAnoComBarraParaAnoMes(mesAnoReferencia);
		
		String dataPagamentoInicial = "01/" + mesAnoReferencia; 
		
		Date dataPagamentoInicialFormatada = Util.converteStringParaDate(dataPagamentoInicial);
		
		Integer anoReferencia = Util.obterAno(anoMesReferencia);
		Integer mesReferencia = Util.obterMes(anoMesReferencia);
		
		String ultimoDia = Util.obterUltimoDiaMes(mesReferencia, anoReferencia);
		
		String dataPagamentoFinal = ultimoDia + "/" + mesAnoReferencia;
		
		Date dataPagamentoFinalFormatada = Util.converteStringParaDate(dataPagamentoFinal);

		Collection colecaoMovimentoArrecadadoresRelatorioHelper = fachada
				.pesquisarMovimentoArrecadadoresRelatorio(anoMesReferencia,
						arrecadador.getId(), arrecadacaoForma.getId(),
						dataPagamentoInicialFormatada,
						dataPagamentoFinalFormatada);

		// se a cole��o de par�metros da analise n�o for vazia
		if (colecaoMovimentoArrecadadoresRelatorioHelper != null && !colecaoMovimentoArrecadadoresRelatorioHelper.isEmpty()) {

			// coloca a cole��o de par�metros da analise no iterator
			Iterator colecaoMovimentoArrecadadoresRelatorioHelperIterator = colecaoMovimentoArrecadadoresRelatorioHelper.iterator();
			
			BigDecimal valorAteDia = new BigDecimal("0.00");
			Integer qtdePagamentosAteDia = 0;
			Integer qtdeDocumentosAteDia = 0;
			
			MovimentoArrecadadoresRelatorioHelper movimentoArrecadadoresRelatorioHelperAnterior = null;

			// la�o para criar a cole��o de par�metros da analise
			while (colecaoMovimentoArrecadadoresRelatorioHelperIterator.hasNext()) {

				MovimentoArrecadadoresRelatorioHelper movimentoArrecadadoresRelatorioHelper = (MovimentoArrecadadoresRelatorioHelper) colecaoMovimentoArrecadadoresRelatorioHelperIterator
						.next();
				
				if (movimentoArrecadadoresRelatorioHelperAnterior != null
						&& (!movimentoArrecadadoresRelatorioHelperAnterior
								.getArrecadador().equals(
										movimentoArrecadadoresRelatorioHelper
												.getArrecadador())
						|| !movimentoArrecadadoresRelatorioHelperAnterior
								.getDescricaoArrecadacaoForma()
								.equals(
										movimentoArrecadadoresRelatorioHelper
												.getDescricaoArrecadacaoForma()))) {
					
					// Zera os valores, pois a totaliza��o � feita em fun��o da forma de arrecada��o
					valorAteDia = new BigDecimal("0.00");
					qtdePagamentosAteDia = 0;
					qtdeDocumentosAteDia = 0;
					
				}

				String banco = movimentoArrecadadoresRelatorioHelper.getArrecadador();
				String formaArrecadacao = movimentoArrecadadoresRelatorioHelper.getDescricaoArrecadacaoForma();
				String valorPagamento = Util.formatarMoedaReal(movimentoArrecadadoresRelatorioHelper.getValorPagamento());
				
				Integer anoMesDataPagamento = Util.recuperaAnoMesDaData(movimentoArrecadadoresRelatorioHelper.getDataPagamento());
				Integer ano = Util.obterAno(anoMesDataPagamento);
				
				String dia = "";
				
				// Adiciona os valores atuais ao total
				qtdePagamentosAteDia = qtdePagamentosAteDia + movimentoArrecadadoresRelatorioHelper.getQtdePagamentos();
				qtdeDocumentosAteDia = qtdeDocumentosAteDia + movimentoArrecadadoresRelatorioHelper.getQtdeDocumentos();
				valorAteDia = valorAteDia.add(movimentoArrecadadoresRelatorioHelper.getValorPagamento());
				
				// Transforma em String os valores para ser setado no relat�rio
				String qtdePagamentosAteDiaFormatado = qtdePagamentosAteDia.toString();
				String qtdeDocumentosAteDiaFormatado = qtdeDocumentosAteDia.toString();
				String valorAteDiaFormatado = Util.formatarMoedaReal(valorAteDia);
				
				// Verifica se � o �ltimo movimento daquela forma de arrecada��o
				if (ano == 9999) {
					dia = "AA";
				} else {
					String dataPagamentoFormatada = Util.formatarData(movimentoArrecadadoresRelatorioHelper.getDataPagamento());
					dia = dataPagamentoFormatada.substring(0, 2);
				}

				relatorioBean = new RelatorioAcompanhamentoMovimentoArrecadadoresBean(banco, formaArrecadacao, dia,
						valorPagamento, movimentoArrecadadoresRelatorioHelper.getQtdePagamentos().toString(),
						movimentoArrecadadoresRelatorioHelper.getQtdeDocumentos().toString(), valorAteDiaFormatado, qtdePagamentosAteDiaFormatado, qtdeDocumentosAteDiaFormatado);

				// adiciona o bean a cole��o
				relatorioBeans.add(relatorioBean);
				
				movimentoArrecadadoresRelatorioHelperAnterior = movimentoArrecadadoresRelatorioHelper;
			}
		}
		// __________________________________________________________________

		// Par�metros do relat�rio
		Map parametros = new HashMap();
		
		// adiciona os par�metros do relat�rio
		// adiciona o laudo da an�lise
		
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		
		parametros.put("imagem", sistemaParametro.getImagemRelatorio());
		
		parametros.put("mesAnoReferencia", mesAnoReferencia);

		if (arrecadador != null && arrecadador.getId() != null) {
			parametros.put("idArrecadador", arrecadador.getId().toString());
			parametros.put("nomeArrecadador", arrecadador.getCliente()
					.getNome());
		}
		
		if (arrecadacaoForma != null && arrecadacaoForma.getId() != null) {
			parametros.put("formaArrecadacao", arrecadacaoForma.getDescricao());
		}

		// cria uma inst�ncia do dataSource do relat�rio
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_ACOMPANHAMENTO_MOVIMENTO_ARRECADADORES,
				parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relat�rio no sistema
		try {
			persistirRelatorioConcluido(retorno, Relatorio.MANTER_BAIRRO,
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
		AgendadorTarefas.agendarTarefa("RelatorioAcompanhamentoMovimentoArrecadadores", this);

	}

}
