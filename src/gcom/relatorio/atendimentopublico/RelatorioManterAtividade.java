package gcom.relatorio.atendimentopublico;

import gcom.atendimentopublico.ordemservico.Atividade;
import gcom.atendimentopublico.ordemservico.FiltroAtividade;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.RelatorioVazioException;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
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

public class RelatorioManterAtividade extends TarefaRelatorio {
	private static final long serialVersionUID = 1L;
	public RelatorioManterAtividade(Usuario usuario) {
		super(usuario, ConstantesRelatorios.RELATORIO_MANTER_ATIVIDADE);
	}
	
	@Deprecated
	public RelatorioManterAtividade() {
		super(null, "");
	}

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param atividades
	 *            Description of the Parameter
	 * @param atividadeParametros
	 *            Description of the Parameter
	 * @return Descri��o do retorno
	 * @exception RelatorioVazioException
	 *                Descri��o da exce��o
	 */

	public Object executar() throws TarefaException {

		// ------------------------------------
//		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		FiltroAtividade filtroAtividade = (FiltroAtividade) getParametro("filtroAtividade");
		Atividade atividadeParametros = (Atividade) getParametro("atividadeParametros");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// valor de retorno
		byte[] retorno = null;

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		RelatorioManterAtividadeBean relatorioBean = null;

		Fachada fachada = Fachada.getInstancia();

		filtroAtividade.setConsultaSemLimites(true);

		Collection<Atividade> colecaoAtividades = fachada.pesquisar(filtroAtividade,
				Atividade.class.getName());

		// se a cole��o de par�metros da analise n�o for vazia
		if (colecaoAtividades != null && !colecaoAtividades.isEmpty()) {

			// la�o para criar a cole��o de par�metros da analise
			for (Atividade atividade : colecaoAtividades) {
				
				String ativoInativo = "";

				if ( atividade.getIndicadorUso().equals( ConstantesSistema.INDICADOR_USO_ATIVO ) ){
				ativoInativo = "Ativo";
				} else {
				ativoInativo = "Inativo";
				}

				relatorioBean = new RelatorioManterAtividadeBean(
						// C�digo
						atividade.getId().toString(), 
						
						// Descri��o
						atividade.getDescricao(),
						
						// Descri��o Abreviada
						atividade.getDescricaoAbreviada(),
						
						// Indicador Atividade Unica
						atividade.getIndicadorAtividadeUnica().intValue()== 1 ? "Sim":"N�o" ,
						
						// Indicador de Uso
						ativoInativo);

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

		if (atividadeParametros.getId() != null) {
			parametros.put("id",
					atividadeParametros.getId().toString());
		} else {
			parametros.put("id", "");
		}

		parametros.put("descricao", atividadeParametros.getDescricao());

		parametros.put("descricaoAbreviada", atividadeParametros.getDescricaoAbreviada());
		
		String indicadorAtividadeUnica = "";
		
		if(atividadeParametros.getIndicadorAtividadeUnica() != null && !atividadeParametros.getIndicadorAtividadeUnica().equals("")){
			if (atividadeParametros.getIndicadorAtividadeUnica().equals(ConstantesSistema.SIM)) {
				indicadorAtividadeUnica = "Sim";
			} else if (atividadeParametros.getIndicadorAtividadeUnica().equals(ConstantesSistema.NAO)) {
				indicadorAtividadeUnica = "N�o";
			} else if (atividadeParametros.getIndicadorAtividadeUnica().equals("")) {
				indicadorAtividadeUnica = "Todos";
			}
		}
	
		parametros.put("indicadorAtividadeUnica", indicadorAtividadeUnica);
		
		String indicadorUso = "";

		if (atividadeParametros.getIndicadorUso() != null
				&& !atividadeParametros.getIndicadorUso().equals("")) {
			if (atividadeParametros.getIndicadorUso().equals(new Short("1"))) {
				indicadorUso = "Ativo";
			} else if (atividadeParametros.getIndicadorUso().equals(
					new Short("2"))) {
				indicadorUso = "Inativo";
			}
		}

		parametros.put("indicadorUso", indicadorUso);

		// cria uma inst�ncia do dataSource do relat�rio

		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = this.gerarRelatorio(
				ConstantesRelatorios.RELATORIO_MANTER_ATIVIDADE, parametros,
				ds, tipoFormatoRelatorio);
		
		// ------------------------------------
		// Grava o relat�rio no sistema
//		try {
//			persistirRelatorioConcluido(retorno, Relatorio.MANTER_ATIVIDADE,
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
		AgendadorTarefas.agendarTarefa("RelatorioManterAtividade", this);
	}

}
