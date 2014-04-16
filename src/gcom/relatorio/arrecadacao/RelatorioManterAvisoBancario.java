package gcom.relatorio.arrecadacao;

import gcom.arrecadacao.Arrecadador;
import gcom.arrecadacao.ArrecadadorMovimento;
import gcom.arrecadacao.aviso.AvisoBancario;
import gcom.arrecadacao.aviso.bean.AvisoBancarioHelper;
import gcom.arrecadacao.banco.ContaBancaria;
import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.RelatorioVazioException;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.math.BigDecimal;
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
public class RelatorioManterAvisoBancario extends TarefaRelatorio {
	private static final long serialVersionUID = 1L;
	public RelatorioManterAvisoBancario(Usuario usuario) {
		super(usuario, ConstantesRelatorios.RELATORIO_AVISO_BANCARIO_MANTER);
	}
	
	@Deprecated
	public RelatorioManterAvisoBancario() {
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

		AvisoBancarioHelper avisoBancarioHelper = (AvisoBancarioHelper) getParametro("avisoBancarioHelper");
		AvisoBancarioHelper avisoBancarioHelperParametros = (AvisoBancarioHelper) getParametro("avisoBancarioHelperParametros");
		Arrecadador arrecadadorParametros = (Arrecadador) getParametro("arrecadadorParametros");
		ContaBancaria contaBancariaParametros = (ContaBancaria) getParametro("contaBancariaParametros");
		ArrecadadorMovimento arrecadadorMovimentoParametros = (ArrecadadorMovimento) getParametro("arrecadadorMovimentoParametros");
		String avisosAbertosFechadosParametros = (String) getParametro("avisosAbertosFechadosParametros");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// valor de retorno
		byte[] retorno = null;

		// cole��o de beans do relat�rio
		List relatorioAvisosBancariosBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		RelatorioManterAvisoBancarioBean relatorioAvisoBancarioBean = null;

		Collection colecaoAvisosBancarios = fachada
				.pesquisarAvisoBancarioRelatorio(avisoBancarioHelper);

		// se a cole��o de par�metros da analise n�o for vazia
		if (colecaoAvisosBancarios != null && !colecaoAvisosBancarios.isEmpty()) {

			// coloca a cole��o de par�metros da analise no iterator
			Iterator colecaoAvisosBancariosIterator = colecaoAvisosBancarios
					.iterator();

//			BigDecimal totalDeducoes = new BigDecimal("0.00");

			// la�o para criar a cole��o de par�metros da analise
			while (colecaoAvisosBancariosIterator.hasNext()) {

				AvisoBancarioRelatorioHelper avisoBancarioRelatorioHelper = (AvisoBancarioRelatorioHelper) colecaoAvisosBancariosIterator
						.next();
				
				BigDecimal totalDeducoes = new BigDecimal("0.00");

				Collection colecaoDeducoes = fachada
						.pesquisarAvisoDeducoesAvisoBancarioRelatorio(avisoBancarioRelatorioHelper
								.getIdAvisoBancario());

				Collection colecaoRelatorioAvisoBancarioDeducoesBeans = new ArrayList();

				if (colecaoDeducoes != null && !colecaoDeducoes.isEmpty()) {
					Iterator colecaoDeducoesIterator = colecaoDeducoes
							.iterator();

					RelatorioManterAvisoBancarioDeducoesBean relatorioDeducoesBean = null;

					while (colecaoDeducoesIterator.hasNext()) {
						DeducoesRelatorioHelper deducoesRelatorioHelper = (DeducoesRelatorioHelper) colecaoDeducoesIterator
								.next();
						totalDeducoes = totalDeducoes
								.add(deducoesRelatorioHelper.getValorDeducao());

						relatorioDeducoesBean = new RelatorioManterAvisoBancarioDeducoesBean(

								// Tipo da Dedu��o
								deducoesRelatorioHelper.getTipo(),

								// Valor da Dedu��o
								Util.formatarMoedaReal(deducoesRelatorioHelper
										.getValorDeducao()));

						// adiciona o bean a cole��o
						colecaoRelatorioAvisoBancarioDeducoesBeans
								.add(relatorioDeducoesBean);

					}
					
				}

				Collection colecaoAcertos = fachada
						.pesquisarAvisoAcertosAvisoBancarioRelatorio(avisoBancarioRelatorioHelper
								.getIdAvisoBancario());

				Collection colecaoRelatorioAvisoBancarioAcertosBeans = new ArrayList();

				if (colecaoAcertos != null && !colecaoAcertos.isEmpty()) {
					Iterator colecaoAcertosIterator = colecaoAcertos.iterator();

					RelatorioManterAvisoBancarioAcertosBean relatorioAcetosBean = null;

					while (colecaoAcertosIterator.hasNext()) {
						AcertosRelatorioHelper acertosRelatorioHelper = (AcertosRelatorioHelper) colecaoAcertosIterator
								.next();

						relatorioAcetosBean = new RelatorioManterAvisoBancarioAcertosBean(

								// N�mero Documento
								"",

								// Banco
								acertosRelatorioHelper.getBanco(),

								// Agencia
								acertosRelatorioHelper.getAgencia().toString(),

								// N�mero Conta
								acertosRelatorioHelper.getNumeroConta(),

								// Data
								Util.formatarData(acertosRelatorioHelper
										.getDataAcerto()),

								// Valor
								Util.formatarMoedaReal(acertosRelatorioHelper
										.getValorAcerto()),

								// Tipo
								acertosRelatorioHelper.getTipo().shortValue() == AvisoBancario.INDICADOR_CREDITO
										.shortValue() ? "CR�DITO" : "D�BITO"

						);

						// adiciona o bean a cole��o
						colecaoRelatorioAvisoBancarioAcertosBeans
								.add(relatorioAcetosBean);

					}
				}

				relatorioAvisoBancarioBean = new RelatorioManterAvisoBancarioBean(

						// Id Aviso Banc�rio
						avisoBancarioRelatorioHelper.getIdAvisoBancario()
								.toString(),

						// Arrecadador
						avisoBancarioRelatorioHelper.getNomeArrecadador(),

						// Data Lan�amento
						Util.formatarData(avisoBancarioRelatorioHelper
								.getDataLancamento()),

						// Sequencial
						avisoBancarioRelatorioHelper.getSequencial() == null ? ""
								: avisoBancarioRelatorioHelper.getSequencial()
										.toString(),

						// Tipo
						avisoBancarioRelatorioHelper.getTipo().shortValue() == AvisoBancario.INDICADOR_CREDITO
								.shortValue() ? "CR�DITO" : "D�BITO",

						// N�mero Documento
						avisoBancarioRelatorioHelper.getNumeroDocumento()
								.toString(),

						// Banco
						avisoBancarioRelatorioHelper.getBanco(),

						// Ag�ncia
						avisoBancarioRelatorioHelper.getAgencia().toString(),

						// N�mero Conta
						avisoBancarioRelatorioHelper.getNumeroConta(),

						// Data Realiza��o
						Util.formatarData(avisoBancarioRelatorioHelper
								.getDataRealizacao()),

						// Total Arrecada��o
						avisoBancarioRelatorioHelper
								.getTotalArrecadacao(),

						// Total Dedu��es
						totalDeducoes,

						// Total Devolu��o
						avisoBancarioRelatorioHelper
								.getTotalDevolucao(),

						// Valor Aviso
						avisoBancarioRelatorioHelper
								.getValorAviso(),

						// Cole��o de Dedu��es
						colecaoRelatorioAvisoBancarioDeducoesBeans,

						// Cole��o de Acertos
						colecaoRelatorioAvisoBancarioAcertosBeans);

				// adiciona o bean a cole��o
				relatorioAvisosBancariosBeans.add(relatorioAvisoBancarioBean);
			}
		}
		// __________________________________________________________________

		// Par�metros do relat�rio
		Map parametros = new HashMap();

		// adiciona os par�metros do relat�rio
		// adiciona o laudo da an�lise
		
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		
		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		// Id Arrecadador

		if (arrecadadorParametros != null) {

			parametros.put("idArrecadador", arrecadadorParametros.getCodigoAgente()
					.toString());

		} else {
			parametros.put("idArrecadador", "");
		}

		// Nome Arrecadador
		if (arrecadadorParametros != null) {
			parametros.put("nomeArrecadador", arrecadadorParametros
					.getCliente().getNome());
		} else {
			parametros.put("nomeArrecadador", "");
		}

		// Per�odo Lan�amento

		if (avisoBancarioHelperParametros.getDataLancamentoInicial() != null) {

			parametros.put("periodoLancamentoInicial", Util
					.formatarData(avisoBancarioHelperParametros
							.getDataLancamentoInicial()));

		} else {
			parametros.put("periodoLancamentoInicial", null);
		}

		if (avisoBancarioHelperParametros.getDataLancamentoFinal() != null) {

			parametros.put("periodoLancamentoFinal", Util
					.formatarData(avisoBancarioHelperParametros
							.getDataLancamentoFinal()));

		} else {
			parametros.put("periodoLancamentoFinal", null);
		}

		// Tipo Aviso

		String tipoAviso = "";

		if (avisoBancarioHelperParametros.getTipoAviso() != null
				&& !avisoBancarioHelperParametros.getTipoAviso().equals("")) {
			if (avisoBancarioHelperParametros.getTipoAviso().equals(
					new Short("1"))) {
				tipoAviso = "Cr�dito";
			} else if (avisoBancarioHelperParametros.getTipoAviso().equals(
					new Short("2"))) {
				tipoAviso = "D�bito";
			}
		}

		parametros.put("tipoAviso", tipoAviso);

		// Conta Banc�ria

		if (contaBancariaParametros != null) {

			parametros.put("contaBancaria", contaBancariaParametros
					.getAgencia().getBanco().getId().toString()
					+ contaBancariaParametros.getAgencia().getCodigoAgencia()
							.toString()
					+ contaBancariaParametros.getNumeroConta());

		} else {
			parametros.put("contaBancaria", "");
		}

		// Movimento

		if (arrecadadorMovimentoParametros != null) {

			parametros.put("movimento", arrecadadorMovimentoParametros
					.getCodigoBanco().toString()
					+ arrecadadorMovimentoParametros.getCodigoRemessa()
							.toString()
					+ arrecadadorMovimentoParametros
							.getDescricaoIdentificacaoServico()
					+ arrecadadorMovimentoParametros
							.getNumeroSequencialArquivo().toString());

		} else {
			parametros.put("movimento", "");
		}

		// Per�odo Arrecada��o

		if (avisoBancarioHelperParametros
				.getAnoMesReferenciaArrecadacaoInicial() != 0) {

			parametros.put("periodoArrecadacaoInicial", Util
					.formatarAnoMesParaMesAno(avisoBancarioHelperParametros
							.getAnoMesReferenciaArrecadacaoInicial()));

		} else {
			parametros.put("periodoArrecadacaoInicial", null);
		}

		if (avisoBancarioHelperParametros.getAnoMesReferenciaArrecadacaoFinal() != 0) {

			parametros.put("periodoArrecadacaoFinal", Util
					.formatarAnoMesParaMesAno(avisoBancarioHelperParametros
							.getAnoMesReferenciaArrecadacaoFinal()));

		} else {
			parametros.put("periodoArrecadacaoFinal", null);
		}

		// Per�odo Previs�o Cr�dito/D�bito

		if (avisoBancarioHelperParametros.getDataPrevistaInicial() != null) {

			parametros.put("periodoPrevisaoCreditoDebitoInicial", Util
					.formatarData(avisoBancarioHelperParametros
							.getDataPrevistaInicial()));

		} else {
			parametros.put("periodoPrevisaoCreditoDebitoInicial", null);
		}

		if (avisoBancarioHelperParametros.getDataPrevistaFinal() != null) {

			parametros.put("periodoPrevisaoCreditoDebitoFinal", Util
					.formatarData(avisoBancarioHelperParametros
							.getDataPrevistaFinal()));

		} else {
			parametros.put("periodoPrevisaoCreditoDebitoFinal", null);
		}

		// Per�odo Realiza��o Cr�dito/D�bito

		if (avisoBancarioHelperParametros.getDataRealizadaInicial() != null) {

			parametros.put("periodoRealizacaoCreditoDebitoInicial", Util
					.formatarData(avisoBancarioHelperParametros
							.getDataRealizadaInicial()));

		} else {
			parametros.put("periodoRealizacaoCreditoDebitoInicial", null);
		}

		if (avisoBancarioHelperParametros.getDataRealizadaFinal() != null) {

			parametros.put("periodoRealizacaoCreditoDebitoFinal", Util
					.formatarData(avisoBancarioHelperParametros
							.getDataRealizadaFinal()));

		} else {
			parametros.put("periodoRealizacaoCreditoDebitoFinal", null);
		}

		// Intervalo Valor Realizado

		if (avisoBancarioHelperParametros.getValorRealizadoInicial() != null) {

			parametros.put("intervaloValorRealizadoInicial", Util
					.formatarMoedaReal(avisoBancarioHelperParametros
							.getValorRealizadoInicial()));

		} else {
			parametros.put("intervaloValorRealizadoInicial", null);
		}

		if (avisoBancarioHelperParametros.getValorRealizadoFinal() != null) {

			parametros.put("intervaloValorRealizadoFinal", Util
					.formatarMoedaReal(avisoBancarioHelperParametros
							.getValorRealizadoFinal()));

		} else {
			parametros.put("intervaloValorRealizadoInicial", null);
		}

		// Avisos Abertos/Fechados

		String avisosAbertosFechados = "";

		if (avisosAbertosFechadosParametros != null
				&& !avisosAbertosFechadosParametros.equals("")
				&& !avisosAbertosFechadosParametros.equals(""
						+ ConstantesSistema.NUMERO_NAO_INFORMADO)) {
			if (avisosAbertosFechadosParametros.equals(""
					+ ConstantesSistema.MOVIMENTO_ABERTO)) {
				avisosAbertosFechados = "Aberto";
			} else if (avisosAbertosFechadosParametros.equals(""
					+ ConstantesSistema.MOVIMENTO_FECHADO)) {
				avisosAbertosFechados = "Fechado";
			}
		}

		parametros.put("avisosAbertosFechados", avisosAbertosFechados);

		// cria uma inst�ncia do dataSource do relat�rio
		RelatorioDataSource ds = new RelatorioDataSource(
				relatorioAvisosBancariosBeans);

		retorno = gerarRelatorio(
				ConstantesRelatorios.RELATORIO_AVISO_BANCARIO_MANTER,
				parametros, ds, tipoFormatoRelatorio);
		
		// ------------------------------------
		// Grava o relat�rio no sistema
		try {
			persistirRelatorioConcluido(retorno,
					Relatorio.MANTER_AVISO_BANCARIO, idFuncionalidadeIniciada);
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
		Fachada fachada = Fachada.getInstancia();

		AvisoBancarioHelper avisoBancarioHelper = (AvisoBancarioHelper) getParametro("avisoBancarioHelper");
		Collection colecaoAvisosBancario = fachada.filtrarAvisoBancarioAbertoFechado(avisoBancarioHelper);

		AvisoBancarioHelper objetoAvisoBancario = null;
		AvisoBancarioHelper avisoBancarioHelperNovo = null;
		Iterator iterator = colecaoAvisosBancario.iterator();
	
		while (iterator.hasNext()) {
			objetoAvisoBancario = (AvisoBancarioHelper) iterator.next();
			avisoBancarioHelperNovo = new AvisoBancarioHelper();
	
			avisoBancarioHelperNovo
					.setAvisoBancario(objetoAvisoBancario.getAvisoBancario());
			avisoBancarioHelperNovo
				.setTipoAviso(avisoBancarioHelper.getTipoAviso());
		}
		
		retorno = Fachada.getInstancia()
				.filtrarAvisoBancarioAbertoFechadoCount(avisoBancarioHelper,avisoBancarioHelperNovo);

		return retorno;
	}


	@Override
	public void agendarTarefaBatch() {
		AgendadorTarefas.agendarTarefa("RelatorioManterAvisoBancario", this);
	}
}
