package gcom.relatorio.financeiro;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.financeiro.bean.ResumoDevedoresDuvidososRelatorioHelper;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.RelatorioVazioException;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * [UC0487] Gerar Relat�rio de Resumo de Devedores Duvidosos
 * 
 * @author Vivianne Sousa
 * @created 20/07/2007
 */
public class RelatorioResumoDevedoresDuvidosos extends TarefaRelatorio {
	private static final long serialVersionUID = 1L;
	public RelatorioResumoDevedoresDuvidosos (Usuario usuario) {
		super(usuario,ConstantesRelatorios.RELATORIO_RESUMO_DEVEDORES_DUVIDOSOS);
	}


	private Collection<RelatorioResumoDevedoresDuvidososBean> inicializarBeanRelatorio(
			Collection listaCamposConsultaRelatorio) {

		Iterator iterator = listaCamposConsultaRelatorio.iterator();

		Collection<RelatorioResumoDevedoresDuvidososBean> retorno = new ArrayList();

		while (iterator.hasNext()) {
			ResumoDevedoresDuvidososRelatorioHelper resumoDevedoresDuvidososRelatorioHelper = 
				(ResumoDevedoresDuvidososRelatorioHelper) iterator.next();
			
			RelatorioResumoDevedoresDuvidososBean relatorioResumoDevedoresDuvidososBean = 
				new RelatorioResumoDevedoresDuvidososBean(
						resumoDevedoresDuvidososRelatorioHelper.getValorItemDevedoresDuvidosos(),
						resumoDevedoresDuvidososRelatorioHelper.getDescricaoTipoLancamento(),
						resumoDevedoresDuvidososRelatorioHelper.getDescricaoItemLancamento(),
						resumoDevedoresDuvidososRelatorioHelper.getDescricaoItemContabil(),
						resumoDevedoresDuvidososRelatorioHelper.getIndicadorImpressao(),
						resumoDevedoresDuvidososRelatorioHelper.getIndicadorTotal(),
						resumoDevedoresDuvidososRelatorioHelper.getLancamentoTipo(),
						resumoDevedoresDuvidososRelatorioHelper.getLancamentoTipoSuperior(),
						false,
						resumoDevedoresDuvidososRelatorioHelper.getDescricaoGerencia(),
						resumoDevedoresDuvidososRelatorioHelper.getGerencia(),
						resumoDevedoresDuvidososRelatorioHelper.getDescricaoLocalidade(),
						resumoDevedoresDuvidososRelatorioHelper.getLocalidade(),
						resumoDevedoresDuvidososRelatorioHelper.getDescLancamentoTipoSuperior(),
						resumoDevedoresDuvidososRelatorioHelper.getDescricaoUnidadeNegocio(),
						resumoDevedoresDuvidososRelatorioHelper.getUnidadeNegocio());

			retorno.add(relatorioResumoDevedoresDuvidososBean);
			
			
		}		


		return retorno;
	}

	
	public Object executar() throws TarefaException {
		
		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------
		
		String opcaoTotalizacao = (String) getParametro("opcaoTotalizacao");
		int mesAno = (Integer) getParametro("mesAnoInteger");
		Integer codigoLocalidade = (Integer) getParametro("localidade");
		Integer codigoGerencia = (Integer) getParametro("gerenciaRegional");
		Integer unidadeNegocio = (Integer) getParametro("unidadeNegocio");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// valor de retorno
		byte[] retorno = null;

		// Par�metros do relat�rio
		Map parametros = new HashMap();

		Fachada fachada = Fachada.getInstancia();

		Collection dadosRelatorio = fachada
				.consultarResumoDevedoresDuvidososRelatorio(opcaoTotalizacao, mesAno,
						codigoGerencia, codigoLocalidade, unidadeNegocio);

		Collection<RelatorioResumoDevedoresDuvidososBean> colecaoBean = this
				.inicializarBeanRelatorio(dadosRelatorio);

		Integer lancamentoTipoAnterior = null;
		
		for (Iterator<RelatorioResumoDevedoresDuvidososBean> iter = colecaoBean
				.iterator(); iter.hasNext();) {
			RelatorioResumoDevedoresDuvidososBean bean = iter.next();

			// Se o tipo de lan�amento for subordinado a outro tipo de
			// lan�amento
			if (!bean.getLancamentoTipo().equals(bean.getLancamentoTipoSuperior())) {

				// Recua a descri��o neste caso
				bean.setDescricaoTipoLancamento("    "	+ bean.getDescricaoTipoLancamento());

				bean.setDescricaoItemLancamento("    " 	+ bean.getDescricaoItemLancamento());

				if (bean.getDescricaoItemContabil() != null){
					bean.setDescricaoItemContabil("    "  + bean.getDescricaoItemContabil());	
				}
 
				if (lancamentoTipoAnterior != null && !lancamentoTipoAnterior.equals(bean.getLancamentoTipoSuperior())){
					String descricaoLancamentoTipoSuperior = fachada.obterDescricaoLancamentoTipo(bean.getLancamentoTipoSuperior());
					bean.setDescLancamentoTipoSuperior(descricaoLancamentoTipoSuperior);
				}
				
			}
			
			lancamentoTipoAnterior = bean.getLancamentoTipo();
			
			// Se o indicador impress�o n�o estiver setado ent�o a linha de
			// detalhe n�o aparecer� no relat�rio
			if (bean.getIndicadorImpressao() == null || !bean.getIndicadorImpressao().toString().equals("1")) {
				bean.setDescricaoTipoLancamento("");
				//iter.remove();
				continue;
			}
			
		}

		if (colecaoBean == null || colecaoBean.isEmpty()) {
			// N�o existem dados para a exibi��o do relat�rio.
			throw new RelatorioVazioException("atencao.relatorio.vazio");
		}

		String opcaoTotalizacaoDesc = "";
		if (opcaoTotalizacao.equalsIgnoreCase("estado")) {
			opcaoTotalizacaoDesc = "Estado";
		} else if (opcaoTotalizacao.equalsIgnoreCase("estadoGerencia")) {
			opcaoTotalizacaoDesc = "Estado por Ger�ncia Regional";
		} else if (opcaoTotalizacao.equalsIgnoreCase("estadoLocalidade")) {
			opcaoTotalizacaoDesc = "Estado por Localidade";
		} else if (opcaoTotalizacao.equalsIgnoreCase("gerenciaRegional")) {
			opcaoTotalizacaoDesc = "Ger�ncia Regional";
		} else if (opcaoTotalizacao.equalsIgnoreCase("gerenciaRegionalLocalidade")) {
			opcaoTotalizacaoDesc = "Ger�ncia Regional por Localidade";
		} else if (opcaoTotalizacao.equalsIgnoreCase("localidade")) {
			opcaoTotalizacaoDesc = "Localidade";
		} else if (opcaoTotalizacao.equals("estadoUnidadeNegocio")) {
			opcaoTotalizacaoDesc = "Estado por Unidade de Neg�cio";
		} else if (opcaoTotalizacao.equals("unidadeNegocio")) {
			opcaoTotalizacaoDesc = "Unidade de Neg�cio";
		}

		parametros.put("opcaoTotalizacaoDesc", opcaoTotalizacaoDesc);

		String mesAnoString = "" + mesAno;
		if (mesAnoString.length() == 5) {
			mesAnoString = "0" + mesAnoString;
		}
		mesAnoString = mesAnoString.substring(0, 2) + "/"
				+ mesAnoString.substring(2, 6);

		parametros.put("mesAnoArrecadacao", mesAnoString);

		parametros.put("tipoFormatoRelatorio", "");

		if (opcaoTotalizacao.equalsIgnoreCase("unidadeNegocio") || opcaoTotalizacao.equalsIgnoreCase("estadoUnidadeNegocio")) {
			parametros.put("agrupaPorUnidadeNegocio", "1");
		}else if (opcaoTotalizacao.equalsIgnoreCase("estadoGerencia")) {
			parametros.put("agrupaPorGerencia", "1");
		} else {
			parametros.put("agrupaPorGerencia", "0");
		}
		
			
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		
		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		RelatorioDataSource ds = new RelatorioDataSource((List) colecaoBean);

		retorno = this.gerarRelatorio(
				ConstantesRelatorios.RELATORIO_RESUMO_DEVEDORES_DUVIDOSOS, parametros,
				ds, tipoFormatoRelatorio);
		
		// ------------------------------------
		// Grava o relat�rio no sistema
		try {
			persistirRelatorioConcluido(retorno, Relatorio.RESUMO_DEVEDORES_DUVIDOSOS,idFuncionalidadeIniciada);
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

//		totalRegistrosRel = fachada
//				.consultarQtdeRegistrosResumoDevedoresDuvidososRelatorio(mesAno, idLocalidade, 
//			    		idGerencia, opcaoTotalizacao);
//		return totalRegistrosRel.intValue();
		return 1;
	}


	public void agendarTarefaBatch() {
	}
}
