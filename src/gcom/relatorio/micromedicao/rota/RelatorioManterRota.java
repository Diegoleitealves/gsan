package gcom.relatorio.micromedicao.rota;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.micromedicao.FiltroRota;
import gcom.micromedicao.Rota;
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
import java.util.Collections;
import java.util.Comparator;
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
 * @author Rafael Corr�a
 * @version 1.0
 */

public class RelatorioManterRota extends TarefaRelatorio {
	private static final long serialVersionUID = 1L;
	public RelatorioManterRota(Usuario usuario) {
		super(usuario, ConstantesRelatorios.RELATORIO_ROTA_MANTER);
	}

	@Deprecated
	public RelatorioManterRota() {
		super(null, "");
	}

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param localidades
	 *            Description of the Parameter
	 * @param localidadeParametros
	 *            Description of the Parameter
	 * @return Descri��o do retorno
	 * @exception RelatorioVazioException
	 *                Descri��o da exce��o
	 */

	public Object executar() throws TarefaException {

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		FiltroRota filtroRota = (FiltroRota) getParametro("filtroRota");
		Rota rotaParametros = (Rota) getParametro("rotaParametros");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// valor de retorno
		byte[] retorno = null;

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		RelatorioManterRotaBean relatorioBean = null;

		Fachada fachada = Fachada.getInstancia();

		rotaParametros.getFaturamentoGrupo();

		filtroRota.adicionarCaminhoParaCarregamentoEntidade("empresa");
		filtroRota.adicionarCaminhoParaCarregamentoEntidade("leituraTipo");
		filtroRota.adicionarCaminhoParaCarregamentoEntidade("cobrancaGrupo");
		filtroRota.adicionarCaminhoParaCarregamentoEntidade("faturamentoGrupo");
		filtroRota
				.adicionarCaminhoParaCarregamentoEntidade("setorComercial.localidade");
		filtroRota.adicionarCaminhoParaCarregamentoEntidade("empresaEntregaContas");
		filtroRota.adicionarCaminhoParaCarregamentoEntidade("empresaCobranca");
		filtroRota.setConsultaSemLimites(true);

		Collection colecaoRotas = fachada.pesquisar(filtroRota, Rota.class
				.getName());

		// se a cole��o de par�metros da analise n�o for vazia
		if (colecaoRotas != null && !colecaoRotas.isEmpty()) {

			// Organizar a cole��o

			Collections.sort((List) colecaoRotas, new Comparator() {
				public int compare(Object a, Object b) {
					String setor1 = ""
							+ ((Rota) a).getSetorComercial().getLocalidade()
									.getId()
							+ ((Rota) a).getSetorComercial().getCodigo();
					String setor2 = ""
							+ ((Rota) b).getSetorComercial().getLocalidade()
									.getId()
							+ ((Rota) b).getSetorComercial().getCodigo();

					return setor1.compareTo(setor2);
				}
			});

			// coloca a cole��o de par�metros da analise no iterator
			Iterator colecaoRotasIterator = colecaoRotas.iterator();

			// la�o para criar a cole��o de par�metros da analise
			while (colecaoRotasIterator.hasNext()) {

				Rota rota = (Rota) colecaoRotasIterator.next();

				// Faz as valida��es dos campos necess�riose e formata a String
				// para a forma como ir� aparecer no relat�rio
				String setorComercial = "";
				String localidade = "";

				if (rota.getSetorComercial() != null) {

					setorComercial = rota.getSetorComercial().getCodigo()
							+ " - " + rota.getSetorComercial().getDescricao();

					if (rota.getSetorComercial().getLocalidade() != null) {
						localidade = rota.getSetorComercial().getLocalidade()
								.getId().toString()
								+ " - "
								+ rota.getSetorComercial().getLocalidade()
										.getDescricao();
					}
				}

				String tipoLeitura = "";

				if (rota.getLeituraTipo() != null) {
					tipoLeitura = rota.getLeituraTipo().getDescricao();
				}

				String empresa = "";

				if (rota.getEmpresa() != null) {
					empresa = rota.getEmpresa().getDescricao();
				}
				
				String empresaCobranca = "";

				if (rota.getEmpresaCobranca() != null) {
					empresaCobranca = rota.getEmpresaCobranca().getDescricao();
				}

				String empresaEntregaContas = "";

				if (rota.getEmpresaEntregaContas() != null) {
					empresaEntregaContas = rota.getEmpresaEntregaContas().getDescricao();
				}
				relatorioBean = new RelatorioManterRotaBean(
						// C�digo
						"" + rota.getCodigo(),

						// Localidade
						localidade,

						// Setor Comercial
						setorComercial,

						// Grupo de Faturamento
						rota.getFaturamentoGrupo().getDescricao(),

						// Grupo de Cobran�a
						rota.getCobrancaGrupo().getDescricao(),

						// Tipo de Leitura
						tipoLeitura,

						// Empresa
						empresa,
						
						//Empresa Cobranca
						empresaCobranca,
						
						//Empresa Entrega Contas
						empresaEntregaContas,

						// Indicador Uso
						rota.getIndicadorUso().toString());

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

		if (rotaParametros.getCodigo() != null) {

			parametros.put("codigo", rotaParametros.getCodigo().toString());

		} else {
			parametros.put("codigo", "");
		}

		if (rotaParametros.getSetorComercial().getLocalidade().getId() != null) {

			parametros.put("idLocalidade", rotaParametros.getSetorComercial()
					.getLocalidade().getId().toString());

		} else {
			parametros.put("idLocalidade", "");
		}

		parametros.put("nomeLocalidade", rotaParametros.getSetorComercial()
				.getLocalidade().getDescricao());

		if (rotaParametros.getSetorComercial().getId() != null) {

			parametros.put("idSetorComercial", ""
					+ rotaParametros.getSetorComercial().getCodigo());

		} else {
			parametros.put("idSetorComercial", "");
		}

		parametros.put("nomeSetorComercial", rotaParametros.getSetorComercial()
				.getDescricao());

		parametros.put("grupoFaturamento", rotaParametros.getFaturamentoGrupo()
				.getDescricao());

		String indicadorUso = "";

		if (rotaParametros.getIndicadorUso() != null
				&& !rotaParametros.getIndicadorUso().equals("")) {
			if (rotaParametros.getIndicadorUso().equals(new Short("1"))) {
				indicadorUso = "Ativo";
			} else if (rotaParametros.getIndicadorUso().equals(new Short("2"))) {
				indicadorUso = "Inativo";
			}
		}

		parametros.put("indicadorUso", indicadorUso);

		// cria uma inst�ncia do dataSource do relat�rio

		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = this.gerarRelatorio(
				ConstantesRelatorios.RELATORIO_ROTA_MANTER, parametros, ds,
				tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relat�rio no sistema
		try {
			persistirRelatorioConcluido(retorno, Relatorio.MANTER_ROTA,
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

		retorno = Fachada.getInstancia().totalRegistrosPesquisa(
				(FiltroRota) getParametro("filtroRota"), Rota.class.getName());

		return retorno;
	}

	public void agendarTarefaBatch() {
		AgendadorTarefas.agendarTarefa("RelatorioManterRota", this);
	}

}
