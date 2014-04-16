package gcom.relatorio.faturamento;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Title: GCOM
 * Description: Sistema de Gest�o Comercial
 * Copyright: Copyright (c) 2004
 * Company: COMPESA - Companhia Pernambucana de Saneamento
 * @author Arthur Carvalho
 * @version 1.0
 */

public class RelatorioAcrescimoPorImpontualidade extends TarefaRelatorio {
	private static final long serialVersionUID = 1L;
	public RelatorioAcrescimoPorImpontualidade(Usuario usuario) {
		super(usuario, ConstantesRelatorios.RELATORIO_ACRESCIMOS_POR_IMPONTUALIDADE);
	}
	
	@Deprecated
	public RelatorioAcrescimoPorImpontualidade() {
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

		// ------------------------------------
		//		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		String valorMulta = (String) getParametro("valorMulta");
		String jurosDeMora = (String) getParametro("jurosDeMora");
		String atualizacaoMonetaria = (String) getParametro("atualizacaoMonetaria");
		
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		
		// valor de retorno
		byte[] retorno = null;

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		// Par�metros do relat�rio
		Map parametros = new HashMap();

		// adiciona os par�metros do relat�rio
		// adiciona o laudo da an�lise
		Fachada fachada = Fachada.getInstancia();
		SistemaParametro sistemaParametro = fachada
				.pesquisarParametrosDoSistema();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		//valor multa
		if (valorMulta != null
				&& !valorMulta.equals("")) {
			parametros.put("valorMulta", valorMulta);
		}
		
		//juros de mora
		if (jurosDeMora != null
				&& !jurosDeMora.equals("")) {
			parametros.put("jurosDeMora", jurosDeMora);
		}
		
		//AtualizacaoMonetaria
		if (atualizacaoMonetaria != null
				&& !atualizacaoMonetaria.equals("")) {
			parametros.put("atualizacaoMonetaria", atualizacaoMonetaria);
		}
		relatorioBeans.add(1);
		// cria uma inst�ncia do dataSource do relat�rio
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = this.gerarRelatorio(
				ConstantesRelatorios.RELATORIO_ACRESCIMOS_POR_IMPONTUALIDADE, parametros,
				ds, tipoFormatoRelatorio);
		
		return retorno;
	}

	@Override
	public int calcularTotalRegistrosRelatorio() {
		int retorno = 0;

		return retorno;
	}

	public void agendarTarefaBatch() {
		AgendadorTarefas.agendarTarefa("RelatorioAcrescimoPorImpontualidade", this);
	}

}
