package gcom.relatorio.seguranca;


import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.RelatorioVazioException;
import gcom.seguranca.acesso.FiltroOperacao;
import gcom.seguranca.acesso.Funcionalidade;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.OperacaoTipo;
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

public class RelatorioManterOperacao extends TarefaRelatorio {
	private static final long serialVersionUID = 1L;
	public RelatorioManterOperacao(Usuario usuario) {
		super(usuario, ConstantesRelatorios.RELATORIO_OPERACAO_MANTER);
	}
	
	@Deprecated
	public RelatorioManterOperacao() {
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

		FiltroOperacao filtroOperacao = (FiltroOperacao) getParametro("filtroOperacao");
		
		Operacao operacaoParametros = (Operacao) getParametro("operacaoParametros");
		OperacaoTipo operacaoTipoParametros = (OperacaoTipo) getParametro("operacaoTipoParametros");
		Funcionalidade funcionalidadeParametros = (Funcionalidade) getParametro("funcionalidadeParametros");
		
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");
		
		
		filtroOperacao.adicionarCaminhoParaCarregamentoEntidade("operacaoTipo");
		filtroOperacao.adicionarCaminhoParaCarregamentoEntidade("funcionalidade");
		filtroOperacao.adicionarCaminhoParaCarregamentoEntidade("argumentoPesquisa");
		
		// valor de retorno
		byte[] retorno = null;

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		RelatorioManterOperacaoBean relatorioBean = null;

		Fachada fachada = Fachada.getInstancia();

		Collection colecaoOperacao = fachada.pesquisar(filtroOperacao,
				Operacao.class.getName());

		// se a cole��o de par�metros da analise n�o for vazia
		if (colecaoOperacao != null && !colecaoOperacao.isEmpty()) {

			// coloca a cole��o de par�metros da analise no iterator
			Iterator operacaoIterator = colecaoOperacao.iterator();

			// la�o para criar a cole��o de par�metros da analise
			while (operacaoIterator.hasNext()) {

				Operacao operacao = (Operacao) operacaoIterator.next();
				
				String argumentoPesquisa = "";
				
				if (operacao.getTabelaColuna() != null) {
					
					argumentoPesquisa = operacao.getTabelaColuna().getColuna();
				}
				
				String operacaoTipo = "";
				
				if(operacao.getOperacaoTipo() != null ) {
					
					operacaoTipo = operacao.getOperacaoTipo().getDescricao();
				} 
				
				String funcionalidade ="";
				
				if (operacao.getFuncionalidade() != null ) {
					
					funcionalidade = operacao.getFuncionalidade().getDescricao();
				}
				
				relatorioBean = new RelatorioManterOperacaoBean(
						// CODIGO
						operacao.getId().toString(), 
						
						// Descri��o
						operacao.getDescricao(), 
						
						//Argumento de Pesquisa
						argumentoPesquisa,
						
						//Tipo de Operacao
						operacaoTipo,
						
						//Funcionalidade
						funcionalidade);
						
						
						
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
				operacaoParametros.getId() == null ? "" : ""
						+ operacaoParametros.getId());
		
		
		if (operacaoParametros.getDescricao() != null){
			parametros.put("descricao", operacaoParametros.getDescricao());
		} else {
			parametros.put("descricao", "");
		}
		
		if (operacaoParametros.getArgumentoPesquisa() != null) {
			parametros.put("argumentoPesquisa", operacaoParametros.getArgumentoPesquisa());
		} else {
			parametros.put("argumentoPesquisa", "");	
		}
		
		if (operacaoTipoParametros.getId() != null) {
			parametros.put("operacaoTipo", operacaoTipoParametros.getDescricao());
		} else {
			parametros.put("operacaoTipo", "");	
		}

		if (funcionalidadeParametros.getId() != null) {
			parametros.put("funcionalidade", funcionalidadeParametros.getDescricao());
		} else {
			parametros.put("funcionalidade", "");	
		}
		
		
		// cria uma inst�ncia do dataSource do relat�rio

		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = this.gerarRelatorio(
				ConstantesRelatorios.RELATORIO_OPERACAO_MANTER, parametros,
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
		AgendadorTarefas.agendarTarefa("RelatorioManterOperacao", this);
	}

}
