package gcom.relatorio.operacional;

import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.operacional.FiltroZonaPressao;
import gcom.operacional.ZonaPressao;
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
 * @author Vinicius Medeiros
 * @version 1.0
 */

public class RelatorioManterZonaPressao extends TarefaRelatorio {
	private static final long serialVersionUID = 1L;
	public RelatorioManterZonaPressao(Usuario usuario) {
		super(usuario, ConstantesRelatorios.RELATORIO_MANTER_ZONA_PRESSAO);
	}
	
	@Deprecated
	public RelatorioManterZonaPressao() {
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

		FiltroZonaPressao filtroZonaPressao = (FiltroZonaPressao) getParametro("filtroZonaPressao");
		ZonaPressao zonaPressaoParametros = (ZonaPressao) getParametro("zonaPressaoParametros");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		filtroZonaPressao.adicionarCaminhoParaCarregamentoEntidade(FiltroZonaPressao.DISTRITO_OPERACIONAL);
		
		// valor de retorno
		byte[] retorno = null;

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		RelatorioManterZonaPressaoBean relatorioBean = null;

		Fachada fachada = Fachada.getInstancia();

		filtroZonaPressao.setConsultaSemLimites(true);

		Collection<ZonaPressao> colecaoZonaPressao = fachada.pesquisar(filtroZonaPressao,
				ZonaPressao.class.getName());

		// se a cole��o de par�metros da analise n�o for vazia
		if (colecaoZonaPressao != null && !colecaoZonaPressao.isEmpty()) {

			// la�o para criar a cole��o de par�metros da analise
			for (ZonaPressao zonaPressao : colecaoZonaPressao) {

				String ativoInativo = "";

				if ( zonaPressao.getIndicadorUso().equals( ConstantesSistema.INDICADOR_USO_ATIVO ) ){
					ativoInativo = "Ativo";
				} else {
					ativoInativo = "Inativo";
				}
				
				relatorioBean = new RelatorioManterZonaPressaoBean(
						// C�digo
						zonaPressao.getId().toString(), 
						
						// Descri��o
						zonaPressao.getDescricaoZonaPressao(), 
						
						// Descri��o Abreviada
						zonaPressao.getDescricaoAbreviada(), 
						
						// Distrito Operacional
						zonaPressao.getDistritoOperacional().getDescricao(),
						
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

		if (zonaPressaoParametros.getId() != null) {
			parametros.put("id",
					zonaPressaoParametros.getId().toString());
		} else {
			parametros.put("id", "");
		}

		parametros.put("descricao", zonaPressaoParametros.getDescricaoZonaPressao());

		parametros.put("descricaoAbreviada", zonaPressaoParametros.getDescricaoAbreviada());
		
		if (zonaPressaoParametros.getDistritoOperacional() != null) {
			parametros.put("distritoOperacional", zonaPressaoParametros.getDistritoOperacional().getDescricao());
		} else {
			parametros.put("distritoOperacional", "");	
		}
		
		String indicadorUso = "";

		if (zonaPressaoParametros.getIndicadorUso() != null
				&& !zonaPressaoParametros.getIndicadorUso().equals("")) {
			if (zonaPressaoParametros.getIndicadorUso().equals(new Short("1"))) {
				indicadorUso = "Ativo";
			} else if (zonaPressaoParametros.getIndicadorUso().equals(
					new Short("2"))) {
				indicadorUso = "Inativo";
			}
		}

		parametros.put("indicadorUso", indicadorUso);

		// cria uma inst�ncia do dataSource do relat�rio

		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = this.gerarRelatorio(
				ConstantesRelatorios.RELATORIO_MANTER_ZONA_PRESSAO, parametros,
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
		AgendadorTarefas.agendarTarefa("RelatorioManterZonaPressao", this);
	}

}
