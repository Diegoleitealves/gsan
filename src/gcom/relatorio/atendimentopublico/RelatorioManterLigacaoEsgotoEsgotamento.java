package gcom.relatorio.atendimentopublico;

import gcom.atendimentopublico.ligacaoesgoto.FiltroLigacaoEsgotoEsgotamento;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoEsgotamento;
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

public class RelatorioManterLigacaoEsgotoEsgotamento extends TarefaRelatorio {
	private static final long serialVersionUID = 1L;
	public RelatorioManterLigacaoEsgotoEsgotamento(Usuario usuario) {
		super(usuario, ConstantesRelatorios.RELATORIO_LIGACAO_ESGOTO_ESGOTAMENTO_MANTER);
	}
	
	@Deprecated
	public RelatorioManterLigacaoEsgotoEsgotamento() {
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

		FiltroLigacaoEsgotoEsgotamento filtroLigacaoEsgotoEsgotamento = (FiltroLigacaoEsgotoEsgotamento) getParametro("filtroLigacaoEsgotoEsgotamento");
		LigacaoEsgotoEsgotamento ligacaoEsgotoEsgotamentoParametros = (LigacaoEsgotoEsgotamento) getParametro("ligacaoEsgotoEsgotamentoParametros");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		filtroLigacaoEsgotoEsgotamento.adicionarCaminhoParaCarregamentoEntidade("faturamentoSituacaoTipo");
		filtroLigacaoEsgotoEsgotamento.adicionarCaminhoParaCarregamentoEntidade("faturamentoSituacaoMotivo");
		
		// valor de retorno
		byte[] retorno = null;

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		RelatorioManterLigacaoEsgotoEsgotamentoBean relatorioBean = null;

		Fachada fachada = Fachada.getInstancia();

		Collection colecaoLigacaoEsgotoEsgotamento = fachada.pesquisar(filtroLigacaoEsgotoEsgotamento,
				LigacaoEsgotoEsgotamento.class.getName());

		// se a cole��o de par�metros da analise n�o for vazia
		if (colecaoLigacaoEsgotoEsgotamento != null && !colecaoLigacaoEsgotoEsgotamento.isEmpty()) {

			// coloca a cole��o de par�metros da analise no iterator
			Iterator ligacaoEsgotoEsgotamentoIterator = colecaoLigacaoEsgotoEsgotamento.iterator();

			// la�o para criar a cole��o de par�metros da analise
			while (ligacaoEsgotoEsgotamentoIterator.hasNext()) {

				LigacaoEsgotoEsgotamento ligacaoEsgotoEsgotamento = (LigacaoEsgotoEsgotamento) ligacaoEsgotoEsgotamentoIterator.next();

				
				String indicadorUso = "";
				
				if(ligacaoEsgotoEsgotamento.getIndicadorUso().equals(ConstantesSistema.SIM)){
					indicadorUso = "ATIVO";
				} else {
					indicadorUso = "INATIVO";
				}
				
				String situacaoTipoFaturamento = "";
				
				if (ligacaoEsgotoEsgotamento.getFaturamentoSituacaoTipo() != null) {
					situacaoTipoFaturamento = ligacaoEsgotoEsgotamento.getFaturamentoSituacaoTipo().getDescricao();
				}
				
				String situacaoMotivoFaturamento ="";
				
				if (ligacaoEsgotoEsgotamento.getFaturamentoSituacaoMotivo() != null){
					situacaoMotivoFaturamento = ligacaoEsgotoEsgotamento.getFaturamentoSituacaoMotivo().getDescricao();
				}
				
				relatorioBean = new RelatorioManterLigacaoEsgotoEsgotamentoBean(
						// CODIGO
						ligacaoEsgotoEsgotamento.getId().toString(), 
						
						// Descri��o
						ligacaoEsgotoEsgotamento.getDescricao(), 
						
						//tipo de situacao especial de faturamento
						situacaoTipoFaturamento,
						
						//motivo da situacao especial de faturamento
						situacaoMotivoFaturamento,
						
						//Quantidade de meses para situacao de especial
						ligacaoEsgotoEsgotamento.getQuantidadeMesesSituacaoEspecial().toString(),
						
						//Indicador de Uso
						indicadorUso);
						
						
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

		parametros.put("id",
				ligacaoEsgotoEsgotamentoParametros.getId() == null ? "" : ""
						+ ligacaoEsgotoEsgotamentoParametros.getId());
		
		
		if (ligacaoEsgotoEsgotamentoParametros.getDescricao() != null){
			parametros.put("descricao", ligacaoEsgotoEsgotamentoParametros.getDescricao());
		} else {
			parametros.put("descricao", "");
		}
		
		if (ligacaoEsgotoEsgotamentoParametros.getFaturamentoSituacaoTipo() != null) {
			parametros.put("faturamentoSituacaoTipo", ligacaoEsgotoEsgotamentoParametros.getFaturamentoSituacaoTipo().getDescricao());
		} else {
			parametros.put("faturamentoSituacaoTipo", "");	
		}
		
		if (ligacaoEsgotoEsgotamentoParametros.getFaturamentoSituacaoMotivo() != null) {
			parametros.put("faturamentoSituacaoMotivo", ligacaoEsgotoEsgotamentoParametros.getFaturamentoSituacaoMotivo().getDescricao());
		} else {
			parametros.put("faturamentoSituacaoMotivo", "");	
		}
		
		if (ligacaoEsgotoEsgotamentoParametros.getQuantidadeMesesSituacaoEspecial() != null) {
			parametros.put("quantidadeMesesSituacaoEspecial", ligacaoEsgotoEsgotamentoParametros.getQuantidadeMesesSituacaoEspecial());
		} else {
			parametros.put("quantidadeMesesSituacaoEspecial", "");
		}
		
		
		String indicadorUso = "";

		if (ligacaoEsgotoEsgotamentoParametros.getIndicadorUso() != null
				&& !ligacaoEsgotoEsgotamentoParametros.getIndicadorUso().equals("")) {
			if (ligacaoEsgotoEsgotamentoParametros.getIndicadorUso().equals(new Short("1"))) {
				indicadorUso = "Ativo";
			} else if (ligacaoEsgotoEsgotamentoParametros.getIndicadorUso().equals(
					new Short("2"))) {
				indicadorUso = "Inativo";
			}
		}

		parametros.put("indicadorUso", indicadorUso);
		
		// cria uma inst�ncia do dataSource do relat�rio

		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = this.gerarRelatorio(
				ConstantesRelatorios.RELATORIO_LIGACAO_ESGOTO_ESGOTAMENTO_MANTER, parametros,
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
		AgendadorTarefas.agendarTarefa("RelatorioManterLigacaoEsgotoEsgotamento", this);
	}

}
