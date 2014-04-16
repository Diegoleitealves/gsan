package gcom.relatorio.faturamento;

import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.faturamento.FaturamentoAtividadeCronograma;
import gcom.faturamento.FaturamentoGrupo;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.RelatorioVazioException;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * classe respons�vel por criar o relat�rio de bairro manter de �gua
 * 
 * @author S�vio Luiz
 * @created 11 de Julho de 2005
 */
public class RelatorioPosicaoFaturamento extends TarefaRelatorio {
	private static final long serialVersionUID = 1L;

	public RelatorioPosicaoFaturamento(Usuario usuario) {
		super(usuario, ConstantesRelatorios.RELATORIO_POSICAO_FATURAMENTO);
	}

	@Deprecated
	public RelatorioPosicaoFaturamento() {
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

		Map<FaturamentoGrupo, Collection<FaturamentoAtividadeCronograma>> map = (Map<FaturamentoGrupo, Collection<FaturamentoAtividadeCronograma>>) getParametro("map");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// valor de retorno
		byte[] retorno = null;

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		if (map != null && !map.isEmpty()) {

			Collection<FaturamentoGrupo> colecaoFaturamentoGrupo = map.keySet();

			for (FaturamentoGrupo grupo : colecaoFaturamentoGrupo) {

				Collection<FaturamentoAtividadeCronograma> colecaoFaturamentoAtividadeCronograma = map
						.get(grupo);

				for (FaturamentoAtividadeCronograma faturamentoAtividadeCronograma : colecaoFaturamentoAtividadeCronograma) {

					// Faz as valida��es dos campos necess�riose e formata a
					// String
					// para a forma como ir� aparecer no relat�rio

					// Grupo de Faturamento / M�s/Ano
					String grupoFaturamento = "";
					String mesAno = "";

					if (faturamentoAtividadeCronograma
							.getFaturamentoGrupoCronogramaMensal() != null
							&& faturamentoAtividadeCronograma
									.getFaturamentoGrupoCronogramaMensal()
									.getFaturamentoGrupo() != null) {
						grupoFaturamento = faturamentoAtividadeCronograma
								.getFaturamentoGrupoCronogramaMensal()
								.getFaturamentoGrupo().getDescricao();
						mesAno = Util
								.formatarAnoMesParaMesAno(faturamentoAtividadeCronograma
										.getFaturamentoGrupoCronogramaMensal()
										.getFaturamentoGrupo()
										.getAnoMesReferencia());
					}

					// Atividade / Predecessora / Obrigatoriedade
					String atividade = "";
					String predecessora = "";
					String obrigatoria = "";

					if (faturamentoAtividadeCronograma
							.getFaturamentoAtividade() != null) {

						atividade = faturamentoAtividadeCronograma
								.getFaturamentoAtividade().getDescricao();

						if (faturamentoAtividadeCronograma
								.getFaturamentoAtividade()
								.getIndicadorObrigatoriedadeAtividade().equals(
										ConstantesSistema.SIM)) {
							obrigatoria = "SIM";
						} else {
							obrigatoria = "N�O";
						}

						if (faturamentoAtividadeCronograma
								.getFaturamentoAtividade()
								.getFaturamentoAtividadePrecedente() != null) {
							predecessora = faturamentoAtividadeCronograma
									.getFaturamentoAtividade()
									.getFaturamentoAtividadePrecedente()
									.getDescricao();
						}

					}

					// Data Previs�o / Usu�rio Previs�o
					String dataPrevisao = "";
					String usuarioPrevisao = "";

					if (faturamentoAtividadeCronograma.getDataPrevista() != null) {
						dataPrevisao = Util
								.formatarData(faturamentoAtividadeCronograma
										.getDataPrevista());

						if (faturamentoAtividadeCronograma
								.getFaturamentoGrupoCronogramaMensal() != null
								&& faturamentoAtividadeCronograma
										.getFaturamentoGrupoCronogramaMensal()
										.getUsuario() != null) {
							usuarioPrevisao = faturamentoAtividadeCronograma
									.getFaturamentoGrupoCronogramaMensal()
									.getUsuario().getNomeUsuario();
						}
					}

					// Data Comando / Usu�rio Comando
					String dataComando = "";
					String usuarioComando = "";

					if (faturamentoAtividadeCronograma.getComando() != null) {
						dataComando = Util
								.formatarDataComHora(faturamentoAtividadeCronograma
										.getComando());

						if (faturamentoAtividadeCronograma.getUsuario() != null) {
							usuarioComando = faturamentoAtividadeCronograma
									.getUsuario().getNomeUsuario();
						}

					}

					// Data Realiza��o
					String dataRealizacao = "";

					if (faturamentoAtividadeCronograma.getDataRealizacao() != null) {
						dataRealizacao = Util
								.formatarDataComHora(faturamentoAtividadeCronograma
										.getDataRealizacao());
					}

					RelatorioPosicaoFaturamentoBean relatorioBean = new RelatorioPosicaoFaturamentoBean(

					// Grupo de Faturamento
							grupoFaturamento,

							// M�s/Ano
							mesAno,

							// Atividade
							atividade,

							// Predecessora
							predecessora,

							// Obrigat�ria
							obrigatoria,

							// Data Previs�o
							dataPrevisao,

							// Usu�rio Previs�o
							usuarioPrevisao,

							// Data Comando
							dataComando,

							// Usu�rio Comando
							usuarioComando,

							// Data Realiza��o
							dataRealizacao);

					// adiciona o bean a cole��o
					relatorioBeans.add(relatorioBean);
				}
			}
		}

		// Par�metros do relat�rio
		Map parametros = new HashMap();

		// adiciona os par�metros do relat�rio
		// adiciona o laudo da an�lise
		SistemaParametro sistemaParametro = fachada
				.pesquisarParametrosDoSistema();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		// cria uma inst�ncia do dataSource do relat�rio
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = gerarRelatorio(
				ConstantesRelatorios.RELATORIO_POSICAO_FATURAMENTO, parametros,
				ds, tipoFormatoRelatorio);

		// retorna o relat�rio gerado
		return retorno;
	}

	@Override
	public int calcularTotalRegistrosRelatorio() {
		int retorno = 0;

		return retorno;
	}

	public void agendarTarefaBatch() {
		AgendadorTarefas.agendarTarefa("RelatorioPosicaoFaturamento", this);
	}
}
