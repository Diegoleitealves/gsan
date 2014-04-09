package gcom.relatorio.cobranca;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.CobrancaCriterio;
import gcom.cobranca.CobrancaCriterioLinha;
import gcom.cobranca.FiltroCobrancaCriterio;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Title: GCOM
 * </p>
 * <p>
 * Description: Sistema de Gest�o Comercial
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: COMPESA - Companhia Pernambucana de Saneamento
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

public class RelatorioManterCriterioCobranca extends TarefaRelatorio {
	private static final long serialVersionUID = 1L;
	public RelatorioManterCriterioCobranca(Usuario usuario) {
		super(usuario, ConstantesRelatorios.RELATORIO_CRITERIO_COBRANCA_MANTER);
	}
	
	@Deprecated
	public RelatorioManterCriterioCobranca() {
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

		FiltroCobrancaCriterio filtroCobrancaCriterio = (FiltroCobrancaCriterio) getParametro("filtroCobrancaCriterio");
		CobrancaCriterio cobrancaCriterioParametros = (CobrancaCriterio) getParametro("cobrancaCriterioParametros");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// valor de retorno
		byte[] retorno = null;

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		RelatorioManterCriterioCobrancaBean relatorioBean = null;

		filtroCobrancaCriterio.setConsultaSemLimites(true);
		filtroCobrancaCriterio
				.setCampoOrderBy(FiltroCobrancaCriterio.DESCRICAO_COBRANCA_CRITERIO);

		Collection colecaoCobrancaCriterio = fachada.pesquisar(
				filtroCobrancaCriterio, CobrancaCriterio.class.getName());

		// se a cole��o de par�metros da analise n�o for vazia
		if (colecaoCobrancaCriterio != null
				&& !colecaoCobrancaCriterio.isEmpty()) {
			// coloca a cole��o de par�metros da analise no iterator
			Iterator colecaoCobrancaCriterioIterator = colecaoCobrancaCriterio
					.iterator();

			// la�o para criar a cole��o de par�metros da analise
			while (colecaoCobrancaCriterioIterator.hasNext()) {

				CobrancaCriterio cobrancaCriterio = (CobrancaCriterio) colecaoCobrancaCriterioIterator
						.next();

				Collection colecaoCobrancaCriterioLinha = fachada.pesquisarCobrancaCriterioLinha(cobrancaCriterio.getId());
				
				if (colecaoCobrancaCriterioLinha != null
						&& !colecaoCobrancaCriterioLinha.isEmpty()) {

					Iterator colecaoCobrancaCriterioLinhaIterator = colecaoCobrancaCriterioLinha
							.iterator();

					while (colecaoCobrancaCriterioLinhaIterator.hasNext()) {

						CobrancaCriterioLinha cobrancaCriterioLinha = (CobrancaCriterioLinha) colecaoCobrancaCriterioLinhaIterator
								.next();

						relatorioBean = new RelatorioManterCriterioCobrancaBean(

								// Descricao
								cobrancaCriterio.getDescricaoCobrancaCriterio(),

								// Data In�cio Vig�ncia
								Util.formatarData(cobrancaCriterio
										.getDataInicioVigencia()),

								// N�mero Anos
								cobrancaCriterio.getNumeroContaAntiga()
										.toString(),

								// Im�vel com Situa��o Especial de Cobran�a
								cobrancaCriterio
										.getIndicadorEmissaoImovelParalisacao()
										.equals(
												ConstantesSistema.INDICADOR_USO_ATIVO) ? "SIM"
										: "N�O",

								// Im�vel com Situa��o de Cobran�a
								cobrancaCriterio
										.getIndicadorEmissaoImovelSituacaoCobranca()
										.equals(
												ConstantesSistema.INDICADOR_USO_ATIVO) ? "SIM"
										: "N�O",

								// Considerar Contas em Revis�o
								cobrancaCriterio
										.getIndicadorEmissaoContaRevisao()
										.equals(
												ConstantesSistema.INDICADOR_USO_ATIVO) ? "SIM"
										: "N�O",

								// Im�vel com D�bito s� da Conta do M�s
								cobrancaCriterio
										.getIndicadorEmissaoDebitoContaMes()
										.equals(
												ConstantesSistema.INDICADOR_USO_ATIVO) ? "SIM"
										: "N�O",

								// Inquilino com D�bito s� da Conta do M�s
								cobrancaCriterio
										.getIndicadorEmissaoInquilinoDebitoContaMes()
										.equals(
												ConstantesSistema.INDICADOR_USO_ATIVO) ? "SIM"
										: "N�O",

								// Im�vel com D�bito s� de Contas Antigas
								cobrancaCriterio
										.getIndicadorEmissaoDebitoContaAntiga()
										.equals(
												ConstantesSistema.INDICADOR_USO_ATIVO) ? "SIM"
										: "N�O",

								// Indicador de Uso
								cobrancaCriterio.getIndicadorUso().toString(),

								// Im�vel Perfil
								cobrancaCriterioLinha.getImovelPerfil()
										.getDescricao(),

								// Categoria
								cobrancaCriterioLinha.getCategoria()
										.getDescricao(),

								// Intervalo de Valor do D�bito
								Util.formatarMoedaReal(cobrancaCriterioLinha
										.getValorMinimoDebito())
										+ " a "
										+ Util
												.formatarMoedaReal(cobrancaCriterioLinha
														.getValorMaximoDebito()),

								// Intervalo de Quantidade de Contas
								cobrancaCriterioLinha
										.getQuantidadeMinimaContas()
										+ " a "
										+ cobrancaCriterioLinha
												.getQuantidadeMaximaContas(),

								// Valor M�nimo da Conta do M�s
								Util.formatarMoedaReal(cobrancaCriterioLinha
										.getValorMinimoContaMes()),

								// Valor M�nimo do D�bito para Cliente com
								// D�bito Autom�tico
								Util
										.formatarMoedaReal(cobrancaCriterioLinha
												.getValorMinimoDebitoDebitoAutomatico()),

								// Quantidade M�nima de Contas para Cliente com
								// D�bito Autom�tico
								cobrancaCriterioLinha
										.getQuantidadeMinimaContasDebitoAutomatico()
										.toString());

						// adiciona o bean a cole��o
						relatorioBeans.add(relatorioBean);

					}
				}
			}
		}

		// Par�metros do relat�rio
		Map parametros = new HashMap();

		// adiciona os par�metros do relat�rio
		// adiciona o laudo da an�lise
		
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		
		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		// Descri��o
		parametros.put("descricao", cobrancaCriterioParametros
				.getDescricaoCobrancaCriterio());

		// Data de In�cio da Vig�ncia
		if (cobrancaCriterioParametros.getDataInicioVigencia() != null) {
			parametros.put("dataInicio", Util
					.formatarData(cobrancaCriterioParametros
							.getDataInicioVigencia()));
		} else {
			parametros.put("dataInicio", "");
		}

		// N�mero de Anos para Determinar Conta Antiga
		if (cobrancaCriterioParametros.getNumeroContaAntiga() != null) {
			parametros.put("numeroAnos", cobrancaCriterioParametros
					.getNumeroContaAntiga().toString());
		} else {
			parametros.put("numeroAnos", "");
		}

		// Im�vel com Situa��o Especial de Cobran�a
		if (cobrancaCriterioParametros.getIndicadorEmissaoImovelParalisacao() != null) {
			if (cobrancaCriterioParametros
					.getIndicadorEmissaoImovelParalisacao().equals(
							ConstantesSistema.INDICADOR_USO_ATIVO)) {
				parametros.put("situacaoEspecialCobranca", "SIM");
			} else {
				parametros.put("situacaoEspecialCobranca", "N�O");
			}
		} else {
			parametros.put("situacaoEspecialCobranca", "");
		}
		
		// Im�vel com Situa��o de Cobran�a
		if (cobrancaCriterioParametros.getIndicadorEmissaoImovelSituacaoCobranca() != null) {
			if (cobrancaCriterioParametros
					.getIndicadorEmissaoImovelSituacaoCobranca().equals(
							ConstantesSistema.INDICADOR_USO_ATIVO)) {
				parametros.put("situacaoCobranca", "SIM");
			} else {
				parametros.put("situacaoCobranca", "N�O");
			}
		} else {
			parametros.put("situacaoCobranca", "");
		}
		
		// Contas em Revis�o
		if (cobrancaCriterioParametros.getIndicadorEmissaoContaRevisao() != null) {
			if (cobrancaCriterioParametros
					.getIndicadorEmissaoContaRevisao().equals(
							ConstantesSistema.INDICADOR_USO_ATIVO)) {
				parametros.put("contasRevisao", "SIM");
			} else {
				parametros.put("contasRevisao", "N�O");
			}
		} else {
			parametros.put("contasRevisao", "");
		}
		
		// Im�vel com D�bito s� da Conta do M�s
		if (cobrancaCriterioParametros.getIndicadorEmissaoDebitoContaMes() != null) {
			if (cobrancaCriterioParametros
					.getIndicadorEmissaoDebitoContaMes().equals(
							ConstantesSistema.INDICADOR_USO_ATIVO)) {
				parametros.put("imovelDebitoContaMes", "SIM");
			} else {
				parametros.put("imovelDebitoContaMes", "N�O");
			}
		} else {
			parametros.put("imovelDebitoContaMes", "");
		}
		
		// Inquilino com D�bito s� da Conta do M�s Independente do Valor da Conta
		if (cobrancaCriterioParametros.getIndicadorEmissaoInquilinoDebitoContaMes() != null) {
			if (cobrancaCriterioParametros
					.getIndicadorEmissaoInquilinoDebitoContaMes().equals(
							ConstantesSistema.INDICADOR_USO_ATIVO)) {
				parametros.put("inquilinoDebitoContaMes", "SIM");
			} else {
				parametros.put("inquilinoDebitoContaMes", "N�O");
			}
		} else {
			parametros.put("inquilinoDebitoContaMes", "");
		}
		
		// Im�vel com D�bito s� de Contas do Antigas
		if (cobrancaCriterioParametros.getIndicadorEmissaoDebitoContaAntiga() != null) {
			if (cobrancaCriterioParametros
					.getIndicadorEmissaoDebitoContaAntiga().equals(
							ConstantesSistema.INDICADOR_USO_ATIVO)) {
				parametros.put("imovelDebitoContasAntigas", "SIM");
			} else {
				parametros.put("imovelDebitoContasAntigas", "N�O");
			}
		} else {
			parametros.put("imovelDebitoContasAntigas", "");
		}
		
		// Indicador de Uso
		if (cobrancaCriterioParametros.getIndicadorUso() != null) {
			if (cobrancaCriterioParametros
					.getIndicadorUso().equals(
							ConstantesSistema.INDICADOR_USO_ATIVO)) {
				parametros.put("indicadorUso", "ATIVO");
			} else {
				parametros.put("indicadorUso", "INATIVO");
			}
		} else {
			parametros.put("indicadorUso", "");
		}

		// cria uma inst�ncia do dataSource do relat�rio

		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = this.gerarRelatorio(
				ConstantesRelatorios.RELATORIO_CRITERIO_COBRANCA_MANTER,
				parametros, ds, tipoFormatoRelatorio);
		
		// ------------------------------------
		// Grava o relat�rio no sistema
		try {
			persistirRelatorioConcluido(retorno,
					Relatorio.MANTER_CRITERIO_COBRANCA,
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
						(FiltroCobrancaCriterio) getParametro("filtroCobrancaCriterio"),
						CobrancaCriterio.class.getName());

		return retorno;
	}

	public void agendarTarefaBatch() {
		AgendadorTarefas.agendarTarefa("RelatorioManterCriterioCobranca",
				this);
	}

}
