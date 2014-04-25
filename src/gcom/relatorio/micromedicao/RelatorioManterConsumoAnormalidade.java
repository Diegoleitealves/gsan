package gcom.relatorio.micromedicao;

import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.micromedicao.consumo.ConsumoAnormalidade;
import gcom.micromedicao.consumo.FiltroConsumoAnormalidade;
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

public class RelatorioManterConsumoAnormalidade extends TarefaRelatorio {
	private static final long serialVersionUID = 1L;
	public RelatorioManterConsumoAnormalidade(Usuario usuario) {
		super(usuario, ConstantesRelatorios.RELATORIO_MANTER_ANORMALIDADE_CONSUMO);
	}
	
	@Deprecated
	public RelatorioManterConsumoAnormalidade() {
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

		FiltroConsumoAnormalidade filtroConsumoAnormalidade = (FiltroConsumoAnormalidade) getParametro("filtroConsumoAnormalidade");
		ConsumoAnormalidade consumoAnormalidadeParametros = (ConsumoAnormalidade) getParametro("consumoAnormalidadeParametros");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// valor de retorno
		byte[] retorno = null;

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		RelatorioManterConsumoAnormalidadeBean relatorioBean = null;

		Fachada fachada = Fachada.getInstancia();

		filtroConsumoAnormalidade.setConsultaSemLimites(true);

		Collection<ConsumoAnormalidade> colecaoConsumoAnormalidades = fachada.pesquisar(filtroConsumoAnormalidade,
				ConsumoAnormalidade.class.getName());

		// se a cole��o de par�metros da analise n�o for vazia
		if (colecaoConsumoAnormalidades != null && !colecaoConsumoAnormalidades.isEmpty()) {

			// la�o para criar a cole��o de par�metros da analise
			for (ConsumoAnormalidade consumoAnormalidade : colecaoConsumoAnormalidades) {
				
				String ativoInativo = "";

				if ( consumoAnormalidade.getIndicadorUso().equals( ConstantesSistema.INDICADOR_USO_ATIVO ) ){
				ativoInativo = "Ativo";
				} else {
				ativoInativo = "Inativo";
				}

				relatorioBean = new RelatorioManterConsumoAnormalidadeBean(
						// C�digo
						consumoAnormalidade.getId().toString(), 
						
						// Descri��o
						consumoAnormalidade.getDescricao(),
						
						// Descri��o Abreviada
						consumoAnormalidade.getDescricaoAbreviada(),
						
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

		if (consumoAnormalidadeParametros.getId() != null) {
			parametros.put("id",
					consumoAnormalidadeParametros.getId().toString());
		} else {
			parametros.put("id", "");
		}

		parametros.put("descricao", consumoAnormalidadeParametros.getDescricao());

		parametros.put("descricaoAbreviada", consumoAnormalidadeParametros.getDescricaoAbreviada());
		
		String indicadorUso = "";

		if (consumoAnormalidadeParametros.getIndicadorUso() != null
				&& !consumoAnormalidadeParametros.getIndicadorUso().equals("")) {
			if (consumoAnormalidadeParametros.getIndicadorUso().equals(new Short("1"))) {
				indicadorUso = "Ativo";
			} else if (consumoAnormalidadeParametros.getIndicadorUso().equals(
					new Short("2"))) {
				indicadorUso = "Inativo";
			}
		}

		parametros.put("indicadorUso", indicadorUso);

		// cria uma inst�ncia do dataSource do relat�rio

		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = this.gerarRelatorio(
				ConstantesRelatorios.RELATORIO_MANTER_ANORMALIDADE_CONSUMO, parametros,
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
		AgendadorTarefas.agendarTarefa("RelatorioManterConsumoAnormalidade", this);
	}

}
