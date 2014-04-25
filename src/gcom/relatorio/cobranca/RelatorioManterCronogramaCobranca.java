package gcom.relatorio.cobranca;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.CobrancaAcaoAtividadeCronograma;
import gcom.cobranca.CobrancaGrupoCronogramaMes;
import gcom.cobranca.FiltroCobrancaAcaoAtividadeCronograma;
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

public class RelatorioManterCronogramaCobranca extends TarefaRelatorio {
	private static final long serialVersionUID = 1L;
	public RelatorioManterCronogramaCobranca(Usuario usuario) {
		super(usuario,
				ConstantesRelatorios.RELATORIO_CRONOGRAMA_COBRANCA_MANTER);
	}

	@Deprecated
	public RelatorioManterCronogramaCobranca() {
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

		FiltroCobrancaAcaoAtividadeCronograma filtroCobrancaAcaoAtividadeCronograma = (FiltroCobrancaAcaoAtividadeCronograma) getParametro("filtroCobrancaAcaoAtividadeCronograma");
		CobrancaGrupoCronogramaMes cobrancaGrupoCronogramaMesParametros = (CobrancaGrupoCronogramaMes) getParametro("cobrancaGrupoCronogramaMesParametros");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// valor de retorno
		byte[] retorno = null;

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		RelatorioManterCronogramaCobrancaBean relatorioBean = null;

		filtroCobrancaAcaoAtividadeCronograma
				.adicionarCaminhoParaCarregamentoEntidade("cobrancaAtividade");
		filtroCobrancaAcaoAtividadeCronograma
				.adicionarCaminhoParaCarregamentoEntidade("cobrancaAcaoCronograma.cobrancaAcao");
		filtroCobrancaAcaoAtividadeCronograma
				.adicionarCaminhoParaCarregamentoEntidade("cobrancaAcaoCronograma.cobrancaGrupoCronogramaMes.cobrancaGrupo");
		filtroCobrancaAcaoAtividadeCronograma.setConsultaSemLimites(true);
		filtroCobrancaAcaoAtividadeCronograma
				.setCampoOrderBy(
						FiltroCobrancaAcaoAtividadeCronograma.COBRANCA_GRUPO_CRONOGRAMA_MES_MES_ANO,
						FiltroCobrancaAcaoAtividadeCronograma.COBRANCA_GRUPO_CRONOGRAMA_MES_COBRANCA_GRUPO_ID,
						FiltroCobrancaAcaoAtividadeCronograma.COBRANCA_ACAO_CRONOGRAMA_COBRANCA_ACAO_ORDEM_REALIZACAO,
						FiltroCobrancaAcaoAtividadeCronograma.COBRANCA_ATIVIDADE_ORDEM_REALIZACAO);

		Collection colecaoCobrancaAcaoAtividadeCronograma = fachada.pesquisar(
				filtroCobrancaAcaoAtividadeCronograma,
				CobrancaAcaoAtividadeCronograma.class.getName());

		// se a cole��o de par�metros da analise n�o for vazia
		if (colecaoCobrancaAcaoAtividadeCronograma != null
				&& !colecaoCobrancaAcaoAtividadeCronograma.isEmpty()) {
			// coloca a cole��o de par�metros da analise no iterator
			Iterator colecaoCobrancaAcaoAtividadeCronogramaIterator = colecaoCobrancaAcaoAtividadeCronograma
					.iterator();

			// la�o para criar a cole��o de par�metros da analise
			while (colecaoCobrancaAcaoAtividadeCronogramaIterator.hasNext()) {

				CobrancaAcaoAtividadeCronograma cobrancaAcaoAtividadeCronograma = (CobrancaAcaoAtividadeCronograma) colecaoCobrancaAcaoAtividadeCronogramaIterator
						.next();

				// Faz as valida��es dos campos necess�riose e formata a String
				// para a forma como ir� aparecer no relat�rio

				// Predecessora
				String predecessora = "";

				if (cobrancaAcaoAtividadeCronograma.getCobrancaAtividade()
						.getCobrancaAtividadePredecessora() != null) {
					predecessora = cobrancaAcaoAtividadeCronograma
							.getCobrancaAtividade().getCobrancaAtividadePredecessora()
							.getDescricaoCobrancaAtividade();
				}

				// Predecessora A��o
				String predecessoraAcao = "";

				if (cobrancaAcaoAtividadeCronograma.getCobrancaAcaoCronograma()
						.getCobrancaAcao().getCobrancaAcaoPredecessora() != null) {
					predecessoraAcao = cobrancaAcaoAtividadeCronograma
							.getCobrancaAcaoCronograma().getCobrancaAcao()
							.getCobrancaAcaoPredecessora()
							.getDescricaoCobrancaAcao();
				}

				// Obrigat�ria
				String obrigatoria = "";

				if (cobrancaAcaoAtividadeCronograma.getCobrancaAcaoCronograma()
						.getCobrancaAcao().getIndicadorObrigatoriedade() != null
						&& cobrancaAcaoAtividadeCronograma
								.getCobrancaAcaoCronograma()
								.getCobrancaAcao()
								.getIndicadorObrigatoriedade()
								.equals(
										new Short(
												ConstantesSistema.INDICADOR_USO_ATIVO))) {
					obrigatoria = "SIM";
				} else {
					obrigatoria = "N�O";
				}

				// Data Prevista
				String dataPrevista = "";

				if (cobrancaAcaoAtividadeCronograma.getDataPrevista() != null) {
					dataPrevista = Util
							.formatarData(cobrancaAcaoAtividadeCronograma
									.getDataPrevista());
				}

				// Data Realiza��o
				String dataRealizacao = "";

				if (cobrancaAcaoAtividadeCronograma.getRealizacao() != null) {
					dataRealizacao = Util
							.formatarDataComHora(cobrancaAcaoAtividadeCronograma
									.getRealizacao());
				}

				// Data Comando
				String dataComando = "";

				if (cobrancaAcaoAtividadeCronograma.getComando() != null) {
					dataComando = Util
							.formatarDataComHora(cobrancaAcaoAtividadeCronograma
									.getComando());
				}

				relatorioBean = new RelatorioManterCronogramaCobrancaBean(

				// Grupo
						cobrancaAcaoAtividadeCronograma
								.getCobrancaAcaoCronograma()
								.getCobrancaGrupoCronogramaMes()
								.getCobrancaGrupo().getDescricao(),

						// Ano/M�s
						cobrancaAcaoAtividadeCronograma
								.getCobrancaAcaoCronograma()
								.getCobrancaGrupoCronogramaMes().getMesAno(),

						// A��o Cobran�a
						cobrancaAcaoAtividadeCronograma
								.getCobrancaAcaoCronograma().getCobrancaAcao()
								.getDescricaoCobrancaAcao(),

						// Atividade
						cobrancaAcaoAtividadeCronograma.getCobrancaAtividade()
								.getDescricaoCobrancaAtividade(),

						// Predecessora
						predecessora,

						// Predecessora A��o
						predecessoraAcao,

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

		if (cobrancaGrupoCronogramaMesParametros.getCobrancaGrupo()
				.getDescricao() != null) {
			parametros.put("grupo", cobrancaGrupoCronogramaMesParametros
					.getCobrancaGrupo().getDescricao());
		} else {
			parametros.put("grupo", "");
		}

		if (cobrancaGrupoCronogramaMesParametros.getAnoMesReferencia() != 0) {
			parametros.put("mesAno", cobrancaGrupoCronogramaMesParametros
					.getMesAno());
		} else {
			parametros.put("mesAno", "");
		}

		// cria uma inst�ncia do dataSource do relat�rio

		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = this.gerarRelatorio(
				ConstantesRelatorios.RELATORIO_CRONOGRAMA_COBRANCA_MANTER,
				parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relat�rio no sistema
		try {
			persistirRelatorioConcluido(retorno,
					Relatorio.MANTER_CRONOGRAMA_COBRANCA,
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
						(FiltroCobrancaAcaoAtividadeCronograma) getParametro("filtroCobrancaAcaoAtividadeCronograma"),
						CobrancaAcaoAtividadeCronograma.class.getName());

		return retorno;
	}

	public void agendarTarefaBatch() {
		AgendadorTarefas.agendarTarefa("RelatorioManterCronogramaCobranca",
				this);
	}

}
