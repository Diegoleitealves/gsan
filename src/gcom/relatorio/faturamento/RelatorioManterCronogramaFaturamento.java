package gcom.relatorio.faturamento;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.faturamento.FaturamentoAtividadeCronograma;
import gcom.faturamento.FaturamentoGrupoCronogramaMensal;
import gcom.faturamento.FiltroFaturamentoAtividadeCronograma;
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

public class RelatorioManterCronogramaFaturamento extends TarefaRelatorio {
	private static final long serialVersionUID = 1L;
	public RelatorioManterCronogramaFaturamento(Usuario usuario) {
		super(usuario,
				ConstantesRelatorios.RELATORIO_CRONOGRAMA_FATURAMENTO_MANTER);
	}

	@Deprecated
	public RelatorioManterCronogramaFaturamento() {
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

		FiltroFaturamentoAtividadeCronograma filtroFaturamentoAtividadeCronograma = (FiltroFaturamentoAtividadeCronograma) getParametro("filtroFaturamentoAtividadeCronograma");
		FaturamentoGrupoCronogramaMensal faturamentoGrupoCronogramaMensalParametros = (FaturamentoGrupoCronogramaMensal) getParametro("faturamentoGrupoCronogramaMensalParametros");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// valor de retorno
		byte[] retorno = null;

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		RelatorioManterCronogramaFaturamentoBean relatorioBean = null;

		filtroFaturamentoAtividadeCronograma
				.adicionarCaminhoParaCarregamentoEntidade("faturamentoAtividade");
		filtroFaturamentoAtividadeCronograma
				.adicionarCaminhoParaCarregamentoEntidade("faturamentoGrupoCronogramaMensal.faturamentoGrupo");
		filtroFaturamentoAtividadeCronograma.setConsultaSemLimites(true);
		filtroFaturamentoAtividadeCronograma
				.setCampoOrderBy(
						FiltroFaturamentoAtividadeCronograma.FATURAMENTO_GRUPO_CRONOGRAMA_MENSAL_ANO_MES_REFERENCIA,
						FiltroFaturamentoAtividadeCronograma.FATURAMENTO_GRUPO_CRONOGRAMA_MENSAL_ID,
						FiltroFaturamentoAtividadeCronograma.FATURAMENTO_ATIVIDADE_ORDEM_REALIZACAO);

		Collection colecaoFaturamentoAtividadeCronograma = fachada.pesquisar(
				filtroFaturamentoAtividadeCronograma,
				FaturamentoAtividadeCronograma.class.getName());

		// se a cole��o de par�metros da analise n�o for vazia
		if (colecaoFaturamentoAtividadeCronograma != null
				&& !colecaoFaturamentoAtividadeCronograma.isEmpty()) {
			// coloca a cole��o de par�metros da analise no iterator
			Iterator colecaoFaturamentoAtividadeCronogramaIterator = colecaoFaturamentoAtividadeCronograma
					.iterator();

			// la�o para criar a cole��o de par�metros da analise
			while (colecaoFaturamentoAtividadeCronogramaIterator.hasNext()) {

				FaturamentoAtividadeCronograma faturamentoAtividadeCronograma = (FaturamentoAtividadeCronograma) colecaoFaturamentoAtividadeCronogramaIterator
						.next();

				// Faz as valida��es dos campos necess�riose e formata a String
				// para a forma como ir� aparecer no relat�rio

				// Predecessora
				String predecessora = "";

				if (faturamentoAtividadeCronograma.getFaturamentoAtividade()
						.getFaturamentoAtividadePrecedente() != null) {
					predecessora = faturamentoAtividadeCronograma
							.getFaturamentoAtividade()
							.getFaturamentoAtividadePrecedente().getDescricao();
				}

				// Obrigat�ria
				String obrigatoria = "";

				if (faturamentoAtividadeCronograma.getFaturamentoAtividade()
						.getIndicadorObrigatoriedadeAtividade() != null
						&& faturamentoAtividadeCronograma
								.getFaturamentoAtividade()
								.getIndicadorObrigatoriedadeAtividade()
								.equals(
										new Short(
												ConstantesSistema.INDICADOR_USO_ATIVO))) {

					obrigatoria = "SIM";

				} else {
					obrigatoria = "N�O";
				}

				// Data Prevista
				String dataPrevista = "";

				if (faturamentoAtividadeCronograma.getDataPrevista() != null) {
					dataPrevista = Util
							.formatarData(faturamentoAtividadeCronograma
									.getDataPrevista());
				}

				// Data Realiza��o
				String dataRealizacao = "";

				if (faturamentoAtividadeCronograma.getDataRealizacao() != null) {
					dataRealizacao = Util
							.formatarDataComHora(faturamentoAtividadeCronograma
									.getDataRealizacao());
				}

				// Data Comando
				String dataComando = "";

				if (faturamentoAtividadeCronograma.getComando() != null) {
					dataComando = Util
							.formatarDataComHora(faturamentoAtividadeCronograma
									.getComando());
				}

				relatorioBean = new RelatorioManterCronogramaFaturamentoBean(

						// Grupo
						faturamentoAtividadeCronograma
								.getFaturamentoGrupoCronogramaMensal()
								.getFaturamentoGrupo().getDescricao(),

						// Ano/M�s
						faturamentoAtividadeCronograma
								.getFaturamentoGrupoCronogramaMensal()
								.getMesAno(),

						// Atividade
						faturamentoAtividadeCronograma
								.getFaturamentoAtividade().getDescricao(),

						// Predecessora
						predecessora,

						// Obrigat�ria
						obrigatoria,

						// Data Prevista
						dataPrevista,

						// Data Realiza��o
						dataRealizacao,

						// Data Comando
						dataComando);

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

		if (faturamentoGrupoCronogramaMensalParametros.getFaturamentoGrupo()
				.getDescricao() != null) {
			parametros.put("grupo", faturamentoGrupoCronogramaMensalParametros
					.getFaturamentoGrupo().getDescricao());
		} else {
			parametros.put("grupo", "");
		}

		if (faturamentoGrupoCronogramaMensalParametros.getAnoMesReferencia() != null
				&& faturamentoGrupoCronogramaMensalParametros
						.getAnoMesReferencia().intValue() != 0) {
			parametros.put("mesAno", faturamentoGrupoCronogramaMensalParametros
					.getMesAno());
		} else {
			parametros.put("mesAno", "");
		}

		// cria uma inst�ncia do dataSource do relat�rio

		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = this.gerarRelatorio(
				ConstantesRelatorios.RELATORIO_CRONOGRAMA_FATURAMENTO_MANTER,
				parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relat�rio no sistema
		try {
			persistirRelatorioConcluido(retorno,
					Relatorio.MANTER_CRONOGRAMA_FATURAMENTO,
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
						(FiltroFaturamentoAtividadeCronograma) getParametro("filtroFaturamentoAtividadeCronograma"),
						FaturamentoAtividadeCronograma.class.getName());

		return retorno;
	}

	public void agendarTarefaBatch() {
		AgendadorTarefas.agendarTarefa("RelatorioManterCronogramaFaturamento",
				this);
	}

}
