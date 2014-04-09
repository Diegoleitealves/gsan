package gcom.relatorio.cadastro.localidade;

import gcom.batch.Relatorio;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
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

public class RelatorioManterLocalidade extends TarefaRelatorio {
	private static final long serialVersionUID = 1L;
	public RelatorioManterLocalidade(Usuario usuario) {
		super(usuario, ConstantesRelatorios.RELATORIO_LOCALIDADE_MANTER);
	}
	
	@Deprecated
	public RelatorioManterLocalidade() {
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

		FiltroLocalidade filtroLocalidade = (FiltroLocalidade) getParametro("filtroLocalidade");
		Localidade localidadeParametros = (Localidade) getParametro("localidadeParametros");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// valor de retorno
		byte[] retorno = null;

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		RelatorioManterLocalidadeBean relatorioBean = null;

		Fachada fachada = Fachada.getInstancia();

		filtroLocalidade
				.adicionarCaminhoParaCarregamentoEntidade("gerenciaRegional");
		filtroLocalidade
				.adicionarCaminhoParaCarregamentoEntidade("unidadeNegocio");
		filtroLocalidade.setConsultaSemLimites(true);

		Collection localidades = fachada.pesquisar(filtroLocalidade,
				Localidade.class.getName());

		// se a cole��o de par�metros da analise n�o for vazia
		if (localidades != null && !localidades.isEmpty()) {

			// Organizar a cole��o

			Collections.sort((List) localidades, new Comparator() {
				public int compare(Object a, Object b) {
					String unidade1 = ((Localidade) a).getUnidadeNegocio()
							.getNome();
					String unidade2 = ((Localidade) b).getUnidadeNegocio()
							.getNome();

					return unidade1.compareTo(unidade2);
				}
			});

			// coloca a cole��o de par�metros da analise no iterator
			Iterator localidadeIterator = localidades.iterator();

			// la�o para criar a cole��o de par�metros da analise
			while (localidadeIterator.hasNext()) {

				Localidade localidade = (Localidade) localidadeIterator.next();
				
				String gerenciaRegional = "";
				if (localidade.getGerenciaRegional() != null) {
					gerenciaRegional = localidade.getGerenciaRegional().getId() + " - " + localidade.getGerenciaRegional().getNome();
				}
				
				String unidadeNegocio = "";
				if (localidade.getUnidadeNegocio() != null) {
					unidadeNegocio = localidade.getUnidadeNegocio().getId() + " - " + localidade.getUnidadeNegocio().getNome();
				}

				relatorioBean = new RelatorioManterLocalidadeBean(
						// C�digo
						localidade.getId().toString(), 
						
						// Ger�ncia Regional
						gerenciaRegional, 
						
						// Unidade Neg�cio
						unidadeNegocio,
						
						// Descri��o
						localidade.getDescricao(), 
						
						// Indicador de Uso
						localidade.getIndicadorUso().toString());

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

		parametros.put("idLocalidade",
				localidadeParametros.getId() == null ? "" : ""
						+ localidadeParametros.getId());

		parametros.put("gerenciaRegional", localidadeParametros
				.getGerenciaRegional() == null ? "" : localidadeParametros
				.getGerenciaRegional().getNomeAbreviado());

		parametros.put("nomeLocalidade", localidadeParametros.getDescricao());

		String indicadorUso = "";

		if (localidadeParametros.getIndicadorUso() != null
				&& !localidadeParametros.getIndicadorUso().equals("")) {
			if (localidadeParametros.getIndicadorUso().equals(new Short("1"))) {
				indicadorUso = "Ativo";
			} else if (localidadeParametros.getIndicadorUso().equals(
					new Short("2"))) {
				indicadorUso = "Inativo";
			}
		}

		parametros.put("indicadorUso", indicadorUso);

		// cria uma inst�ncia do dataSource do relat�rio

		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = this.gerarRelatorio(
				ConstantesRelatorios.RELATORIO_LOCALIDADE_MANTER, parametros,
				ds, tipoFormatoRelatorio);
		
		// ------------------------------------
		// Grava o relat�rio no sistema
		try {
			persistirRelatorioConcluido(retorno, Relatorio.MANTER_LOCALIDADE,
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
				(FiltroLocalidade) getParametro("filtroLocalidade"),
				Localidade.class.getName());

		return retorno;
	}

	public void agendarTarefaBatch() {
		AgendadorTarefas.agendarTarefa("RelatorioManterLocalidade", this);
	}

}
