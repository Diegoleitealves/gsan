package gcom.relatorio.arrecadacao.pagamento;

import gcom.arrecadacao.pagamento.FiltroPagamentoSituacao;
import gcom.arrecadacao.pagamento.PagamentoSituacao;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.RelatorioVazioException;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
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
 * @author Arthur Carvalho
 * @version 1.0
 */

public class RelatorioManterSituacaoPagamento extends TarefaRelatorio {
	private static final long serialVersionUID = 1L;
	public RelatorioManterSituacaoPagamento(Usuario usuario) {
		super(usuario, ConstantesRelatorios.RELATORIO_SITUACAO_PAGAMENTO_MANTER);
	}
	
	@Deprecated
	public RelatorioManterSituacaoPagamento() {
		super(null, "");
	}

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param situacao pagamento
	 *            Description of the Parameter
	 * @param SituacaoPagamentoParametros
	 *            Description of the Parameter
	 * @return Descri��o do retorno
	 * @exception RelatorioVazioException
	 *                Descri��o da exce��o
	 */

	public Object executar() throws TarefaException {

		FiltroPagamentoSituacao filtroPagamentoSituacao = (FiltroPagamentoSituacao) getParametro("filtroPagamentoSituacao");
		PagamentoSituacao pagamentoSituacaoParametros = (PagamentoSituacao) getParametro("pagamentoSituacaoParametros");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// valor de retorno
		byte[] retorno = null;

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		RelatorioManterSituacaoPagamentoBean relatorioBean = null;

		Fachada fachada = Fachada.getInstancia();

		Collection colecaoPagamentoSituacao = fachada.pesquisar(filtroPagamentoSituacao,
				PagamentoSituacao.class.getName());

		// se a cole��o de par�metros da analise n�o for vazia
		if (colecaoPagamentoSituacao != null && !colecaoPagamentoSituacao.isEmpty()) {

			// coloca a cole��o de par�metros da analise no iterator
			Iterator pagamentoSituacaoIterator = colecaoPagamentoSituacao.iterator();

			// la�o para criar a cole��o de par�metros da analise
			while (pagamentoSituacaoIterator.hasNext()) {

				PagamentoSituacao pagamentoSituacao = (PagamentoSituacao) pagamentoSituacaoIterator.next();

				relatorioBean = new RelatorioManterSituacaoPagamentoBean(
						// C�digo
						pagamentoSituacao.getId().toString(), 
						
						// Descri��o
						pagamentoSituacao.getDescricao(), 
						
						// Descri��o Abreviada
						pagamentoSituacao.getDescricaoAbreviada(),
						
						// Indicador de Uso
						pagamentoSituacao.getIndicadorUso().toString());

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

		parametros.put("idSituacaoPagamento",
				pagamentoSituacaoParametros.getId() == null ? "" : ""
						+ pagamentoSituacaoParametros.getId());

		parametros.put("descricao", pagamentoSituacaoParametros.getDescricao());
		parametros.put("descricaoAbreviada", pagamentoSituacaoParametros.getDescricaoAbreviada());
		
		String indicadorUso = "";

		if (pagamentoSituacaoParametros.getIndicadorUso() != null
				&& !pagamentoSituacaoParametros.getIndicadorUso().equals("")) {
			if (pagamentoSituacaoParametros.getIndicadorUso().equals(new Short("1"))) {
				indicadorUso = "Ativo";
			} else if (pagamentoSituacaoParametros.getIndicadorUso().equals(
					new Short("2"))) {
				indicadorUso = "Inativo";
			}
		}

		parametros.put("indicadorUso", indicadorUso);

		// cria uma inst�ncia do dataSource do relat�rio

		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = this.gerarRelatorio(
				ConstantesRelatorios.RELATORIO_SITUACAO_PAGAMENTO_MANTER, parametros,
				ds, tipoFormatoRelatorio);
		
		// ------------------------------------
		// Grava o relat�rio no sistema
//		try {
//			persistirRelatorioConcluido(retorno, Relatorio.MANTER_LOCALIDADE,
//					idFuncionalidadeIniciada);
//		} catch (ControladorException e) {
//			e.printStackTrace();
//			throw new TarefaException("Erro ao gravar relat�rio no sistema", e);
//		}
		// ------------------------------------

		// retorna o relat�rio gerado
		return retorno;
	}

	@Override
	public int calcularTotalRegistrosRelatorio() {
		int retorno = 0;

//		retorno = Fachada.getInstancia().totalRegistrosPesquisa(
//				(FiltroLocalidade) getParametro("filtroLocalidade"),
//				Localidade.class.getName());

		return retorno;
	}

	public void agendarTarefaBatch() {
		AgendadorTarefas.agendarTarefa("RelatorioManterSituacaoPagamento", this);
	}

}
