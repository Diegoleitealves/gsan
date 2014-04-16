package gcom.relatorio.arrecadacao;

import gcom.arrecadacao.ArrecadacaoForma;
import gcom.arrecadacao.FiltroArrecadacaoForma;
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

public class RelatorioManterArrecadacaoForma extends TarefaRelatorio {
	private static final long serialVersionUID = 1L;
	public RelatorioManterArrecadacaoForma(Usuario usuario) {
		super(usuario, ConstantesRelatorios.RELATORIO_MANTER_MOTIVO_CORTE);
	}
	
	@Deprecated
	public RelatorioManterArrecadacaoForma() {
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
//		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		FiltroArrecadacaoForma filtroArrecadacaoForma = (FiltroArrecadacaoForma) getParametro("filtroArrecadacaoForma");
		ArrecadacaoForma arrecadacaoFormaParametros = (ArrecadacaoForma) getParametro("arrecadacaoFormaParametros");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// valor de retorno
		byte[] retorno = null;

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		RelatorioManterArrecadacaoFormaBean relatorioBean = null;

		Fachada fachada = Fachada.getInstancia();

		filtroArrecadacaoForma.setConsultaSemLimites(true);

		Collection<ArrecadacaoForma> colecaoArrecadacaoFormas = fachada.pesquisar(filtroArrecadacaoForma,
				ArrecadacaoForma.class.getName());

		// se a cole��o de par�metros da analise n�o for vazia
		if (colecaoArrecadacaoFormas != null && !colecaoArrecadacaoFormas.isEmpty()) {

			// la�o para criar a cole��o de par�metros da analise
			for (ArrecadacaoForma arrecadacaoForma : colecaoArrecadacaoFormas) {

				relatorioBean = new RelatorioManterArrecadacaoFormaBean(
						// Identificador
						arrecadacaoForma.getId().toString(), 
						
						// C�digo Arrecadacao Forma
						arrecadacaoForma.getCodigoArrecadacaoForma(), 
						
						// Descri��o
						arrecadacaoForma.getDescricao()); 
						
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

		if (arrecadacaoFormaParametros.getId() != null) {
			parametros.put("idArrecadacaoForma",
					arrecadacaoFormaParametros.getId().toString());
		} else {
			parametros.put("idArrecadacaoForma", "");
		}
		
		parametros.put("codigoArrecadacaoForma", arrecadacaoFormaParametros.getCodigoArrecadacaoForma());

		parametros.put("descricao", arrecadacaoFormaParametros.getDescricao());

		// cria uma inst�ncia do dataSource do relat�rio

		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = this.gerarRelatorio(
				ConstantesRelatorios.RELATORIO_MANTER_ARRECADACAO_FORMA, parametros,
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

		return retorno;
	}

	public void agendarTarefaBatch() {
		AgendadorTarefas.agendarTarefa("RelatorioManterArrecadacaoForma", this);
	}

}
