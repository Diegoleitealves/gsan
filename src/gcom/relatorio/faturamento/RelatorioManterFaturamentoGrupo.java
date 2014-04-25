package gcom.relatorio.faturamento;

import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.faturamento.FaturamentoGrupo;
import gcom.faturamento.FiltroFaturamentoGrupo;
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

public class RelatorioManterFaturamentoGrupo extends TarefaRelatorio {
	private static final long serialVersionUID = 1L;
	public RelatorioManterFaturamentoGrupo(Usuario usuario) {
		super(usuario, ConstantesRelatorios.RELATORIO_MANTER_FATURAMENTO_GRUPO);
	}
	
	@Deprecated
	public RelatorioManterFaturamentoGrupo() {
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

		FiltroFaturamentoGrupo filtroFaturamentoGrupo = (FiltroFaturamentoGrupo) getParametro("filtroFaturamentoGrupo");
		FaturamentoGrupo faturamentoGrupoParametros = (FaturamentoGrupo) getParametro("faturamentoGrupoParametros");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// valor de retorno
		byte[] retorno = null;

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		RelatorioManterFaturamentoGrupoBean relatorioBean = null;

		Fachada fachada = Fachada.getInstancia();

		filtroFaturamentoGrupo.setConsultaSemLimites(true);

		Collection<FaturamentoGrupo> colecaoFaturamentoGrupos = fachada.pesquisar(filtroFaturamentoGrupo,
				FaturamentoGrupo.class.getName());

		// se a cole��o de par�metros da analise n�o for vazia
		if (colecaoFaturamentoGrupos != null && !colecaoFaturamentoGrupos.isEmpty()) {

			// la�o para criar a cole��o de par�metros da analise
			for (FaturamentoGrupo faturamentoGrupo : colecaoFaturamentoGrupos) {

				
				String ativoInativo = "";

				if ( faturamentoGrupo.getIndicadorUso().equals( ConstantesSistema.INDICADOR_USO_ATIVO ) ){
				ativoInativo = "Ativo";
				} else {
				ativoInativo = "Inativo";
				}
				
						
				relatorioBean = new RelatorioManterFaturamentoGrupoBean(
						// C�digo
						faturamentoGrupo.getId().toString(), 
						
						// Descri��o
						faturamentoGrupo.getDescricao(), 
						
						// Descri��o Abreviada
						faturamentoGrupo.getDescricaoAbreviada(),
						
						// Ano/Mes Referencia
						Util.formatarAnoMesParaMesAno(faturamentoGrupo.getAnoMesReferencia().toString()),
						
						// Dia Vencimento
						faturamentoGrupo.getDiaVencimento().toString(),
						
						// Indicador Vencimento
						faturamentoGrupo.getIndicadorVencimentoMesFatura().toString(),
						
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

		if (faturamentoGrupoParametros.getId() != null) {
			parametros.put("id",
					faturamentoGrupoParametros.getId().toString());
		} else {
			parametros.put("id", "");
		}
		if (faturamentoGrupoParametros.getAnoMesReferencia() != null){
			parametros.put("anoMesReferencia", Util.formatarAnoMesParaMesAno(faturamentoGrupoParametros.getAnoMesReferencia().toString()));
		}else{
			parametros.put("anoMesReferencia","");
		}
		
		parametros.put("descricao", faturamentoGrupoParametros.getDescricao());
		
		parametros.put("descricaoAbreviada", faturamentoGrupoParametros.getDescricaoAbreviada());
		
		if(faturamentoGrupoParametros.getDiaVencimento() != null){
			parametros.put("diaVencimento", faturamentoGrupoParametros.getDiaVencimento().toString());
		} else {
			parametros.put("diaVencimento", "");
		}
		
		String indicadorUso = "";

		if (faturamentoGrupoParametros.getIndicadorUso() != null
				&& !faturamentoGrupoParametros.getIndicadorUso().equals("")) {
			if (faturamentoGrupoParametros.getIndicadorUso().equals(new Short("1"))) {
				indicadorUso = "Ativo";
			} else if (faturamentoGrupoParametros.getIndicadorUso().equals(
					new Short("2"))) {
				indicadorUso = "Inativo";
			}
		}

		parametros.put("indicadorUso", indicadorUso);

		
		String indicadorVencimentoMesFatura = "";

		if (faturamentoGrupoParametros.getIndicadorVencimentoMesFatura() != null
				&& !faturamentoGrupoParametros.getIndicadorVencimentoMesFatura().equals("")) {
			if (faturamentoGrupoParametros.getIndicadorVencimentoMesFatura().equals(new Short("1"))) {
				indicadorVencimentoMesFatura = "Sim";
			} else if (faturamentoGrupoParametros.getIndicadorVencimentoMesFatura().equals(new Short("2"))) {
				indicadorVencimentoMesFatura = "N�o";
			}
		}

		parametros.put("indicadorVencimentoMesFatura", indicadorVencimentoMesFatura);
		
		
		
		
		
		
		// cria uma inst�ncia do dataSource do relat�rio

		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = this.gerarRelatorio(
				ConstantesRelatorios.RELATORIO_MANTER_FATURAMENTO_GRUPO, parametros,
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
		AgendadorTarefas.agendarTarefa("RelatorioManterFaturamentoGrupo", this);
	}

}
