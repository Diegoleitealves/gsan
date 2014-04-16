package gcom.relatorio.faturamento;

import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.faturamento.FaturamentoSituacaoTipo;
import gcom.faturamento.FiltroFaturamentoSituacaoTipo;
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

public class RelatorioManterFaturamentoSituacaoTipo extends TarefaRelatorio {
	private static final long serialVersionUID = 1L;
	public RelatorioManterFaturamentoSituacaoTipo(Usuario usuario) {
		super(usuario, ConstantesRelatorios.RELATORIO_FATURAMENTO_SITUACAO_TIPO_MANTER);
	}
	
	@Deprecated
	public RelatorioManterFaturamentoSituacaoTipo() {
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

		FiltroFaturamentoSituacaoTipo filtroFaturamentoSituacaoTipo= (FiltroFaturamentoSituacaoTipo) getParametro("filtroFaturamentoSituacaoTipo");
		FaturamentoSituacaoTipo faturamentoSituacaoTipoParametros = (FaturamentoSituacaoTipo) getParametro("faturamentoSituacaoTipoParametros");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		//filtroFaturamentoSituacaoTipo.adicionarCaminhoParaCarregamentoEntidade(FiltroFaturamentoSituacaoTipo.ID);
		
		// valor de retorno
		byte[] retorno = null;

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		RelatorioManterFaturamentoSituacaoTipoBean relatorioBean = null;

		Fachada fachada = Fachada.getInstancia();

		Collection colecaoFaturamentoSituacaoTipo = fachada.pesquisar(filtroFaturamentoSituacaoTipo,
				FaturamentoSituacaoTipo.class.getName());

		// se a cole��o de par�metros da analise n�o for vazia
		if (colecaoFaturamentoSituacaoTipo != null && !colecaoFaturamentoSituacaoTipo.isEmpty()) {

			// coloca a cole��o de par�metros da analise no iterator
			Iterator faturamentoSituacaoTipoIterator = colecaoFaturamentoSituacaoTipo.iterator();

			// la�o para criar a cole��o de par�metros da analise
			while (faturamentoSituacaoTipoIterator.hasNext()) {

				FaturamentoSituacaoTipo faturamentoSituacaoTipo = (FaturamentoSituacaoTipo) faturamentoSituacaoTipoIterator.next();

				String indicadorParalisacaoFaturamento = "";
				
				if(faturamentoSituacaoTipo.getIndicadorParalisacaoFaturamento().equals(ConstantesSistema.SIM)){
					indicadorParalisacaoFaturamento = "SIM";
				} else {
					indicadorParalisacaoFaturamento = "N�O";
				}
				
				String indicadorParalisacaoLeitura = "";
				
				if(faturamentoSituacaoTipo.getIndicadorParalisacaoLeitura().equals(ConstantesSistema.SIM)){
					indicadorParalisacaoLeitura = "SIM";
				} else {
					indicadorParalisacaoLeitura = "N�O";
				}
				
				String indicadorValidoAgua = "";
				
				if(faturamentoSituacaoTipo.getIndicadorValidoAgua().equals(ConstantesSistema.SIM)){
					indicadorValidoAgua = "SIM";
				} else {
					indicadorValidoAgua = "N�O";
				}
				
				String indicadorValidoEsgoto = "";
				
				if(faturamentoSituacaoTipo.getIndicadorValidoEsgoto().equals(ConstantesSistema.SIM)){
					indicadorValidoEsgoto = "SIM";
				} else {
					indicadorValidoEsgoto = "N�O";
				}
				
				
				relatorioBean = new RelatorioManterFaturamentoSituacaoTipoBean(
						// ID
						faturamentoSituacaoTipo.getId().toString(), 
						
						// Descri��o
						faturamentoSituacaoTipo.getDescricao(), 
						
						// paralisacao faturamento
						indicadorParalisacaoFaturamento,
						
						//paralisacao leitura
						indicadorParalisacaoLeitura,
						
						//indicador valido agua
						indicadorValidoAgua,
						
						//indicador valido esgoto
						indicadorValidoEsgoto);
						
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
				faturamentoSituacaoTipoParametros.getId() == null ? "" : ""
						+ faturamentoSituacaoTipoParametros.getId());

		parametros.put("descricao", faturamentoSituacaoTipoParametros.getDescricao());
		
		
		String indicadorParalisacaoFaturamento = "";

		if (faturamentoSituacaoTipoParametros.getIndicadorParalisacaoFaturamento() != null
				&& !faturamentoSituacaoTipoParametros.getIndicadorParalisacaoFaturamento().equals("")) {
			if (faturamentoSituacaoTipoParametros.getIndicadorParalisacaoFaturamento().equals(new Short("1"))) {
				indicadorParalisacaoFaturamento = "Sim";
			} else if (faturamentoSituacaoTipoParametros.getIndicadorParalisacaoFaturamento().equals(
					new Short("2"))) {
				indicadorParalisacaoFaturamento = "N�o";
			}
		}

		parametros.put("indicadorParalisacaoFaturamento", indicadorParalisacaoFaturamento);
		
		
		String indicadorParalisacaoLeitura = "";

		if (faturamentoSituacaoTipoParametros.getIndicadorParalisacaoLeitura() != null
				&& !faturamentoSituacaoTipoParametros.getIndicadorParalisacaoLeitura().equals("")) {
			if (faturamentoSituacaoTipoParametros.getIndicadorParalisacaoLeitura().equals(new Short("1"))) {
				indicadorParalisacaoLeitura = "Sim";
			} else if (faturamentoSituacaoTipoParametros.getIndicadorParalisacaoLeitura().equals(
					new Short("2"))) {
				indicadorParalisacaoLeitura = "N�o";
			}
		}

		parametros.put("indicadorParalisacaoLeitura", indicadorParalisacaoLeitura);
		
		String indicadorValidoAgua = "";

		if (faturamentoSituacaoTipoParametros.getIndicadorValidoAgua() != null
				&& !faturamentoSituacaoTipoParametros.getIndicadorValidoAgua().equals("")) {
			if (faturamentoSituacaoTipoParametros.getIndicadorValidoAgua().equals(new Short("1"))) {
				indicadorValidoAgua = "Sim";
			} else if (faturamentoSituacaoTipoParametros.getIndicadorValidoAgua().equals(
					new Short("2"))) {
				indicadorValidoAgua = "N�o";
			}
		}

		parametros.put("indicadorValidoAgua", indicadorValidoAgua);
		
		String indicadorValidoEsgoto = "";

		if (faturamentoSituacaoTipoParametros.getIndicadorValidoEsgoto() != null
				&& !faturamentoSituacaoTipoParametros.getIndicadorValidoEsgoto().equals("")) {
			if (faturamentoSituacaoTipoParametros.getIndicadorValidoEsgoto().equals(new Short("1"))) {
				indicadorValidoEsgoto = "Sim";
			} else if (faturamentoSituacaoTipoParametros.getIndicadorValidoEsgoto().equals(
					new Short("2"))) {
				indicadorValidoEsgoto = "N�o";
			}
		}

		parametros.put("indicadorValidoEsgoto", indicadorValidoEsgoto);

		// cria uma inst�ncia do dataSource do relat�rio

		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = this.gerarRelatorio(
				ConstantesRelatorios.RELATORIO_FATURAMENTO_SITUACAO_TIPO_MANTER, parametros,
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
		AgendadorTarefas.agendarTarefa("RelatorioManterFaturamentoSituacaoTipo", this);
	}

}
