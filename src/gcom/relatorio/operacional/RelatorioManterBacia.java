package gcom.relatorio.operacional;

import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.operacional.Bacia;
import gcom.operacional.FiltroBacia;
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

public class RelatorioManterBacia extends TarefaRelatorio {
	private static final long serialVersionUID = 1L;
	public RelatorioManterBacia(Usuario usuario) {
		super(usuario, ConstantesRelatorios.RELATORIO_BACIA_MANTER);
	}
	
	@Deprecated
	public RelatorioManterBacia() {
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

		FiltroBacia filtroBacia = (FiltroBacia) getParametro("filtroBacia");
		Bacia baciaParametros = (Bacia) getParametro("baciaParametros");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		filtroBacia.adicionarCaminhoParaCarregamentoEntidade(FiltroBacia.SISTEMA_ESGOTO);
		
		// valor de retorno
		byte[] retorno = null;

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		RelatorioManterBaciaBean relatorioBean = null;

		Fachada fachada = Fachada.getInstancia();

		Collection colecaoBacia= fachada.pesquisar(filtroBacia,
				Bacia.class.getName());

		// se a cole��o de par�metros da analise n�o for vazia
		if (colecaoBacia != null && !colecaoBacia.isEmpty()) {

			// coloca a cole��o de par�metros da analise no iterator
			Iterator baciaIterator = colecaoBacia.iterator();

			// la�o para criar a cole��o de par�metros da analise
			while (baciaIterator.hasNext()) {

				Bacia bacia = (Bacia) baciaIterator.next();
				relatorioBean = new RelatorioManterBaciaBean(
						// ID
						bacia.getId().toString(), 
						
						// Descri��o
						bacia.getDescricao(), 
						
						// Sistema Esgoto
						bacia.getSistemaEsgoto().getDescricao(),
						
						bacia.getIndicadorUso().toString());
						
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
				baciaParametros.getId() == null ? "" : ""
						+ baciaParametros.getId());

		parametros.put("descricao", baciaParametros.getDescricao());
		
		if (baciaParametros.getSistemaEsgoto() != null) {
			parametros.put("sistemaEsgoto", baciaParametros.getSistemaEsgoto().getDescricao());
		} else {
			parametros.put("sistemaEsgoto", "");	
		}
		
		
		String indicadorUso = "";

		if (baciaParametros.getIndicadorUso() != null
				&& !baciaParametros.getIndicadorUso().equals("")) {
			if (baciaParametros.getIndicadorUso().equals(new Short("1"))) {
				indicadorUso = "Ativo";
			} else if (baciaParametros.getIndicadorUso().equals(
					new Short("2"))) {
				indicadorUso = "Inativo";
			}
		}

		parametros.put("indicadorUso", indicadorUso);

		// cria uma inst�ncia do dataSource do relat�rio

		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = this.gerarRelatorio(
				ConstantesRelatorios.RELATORIO_BACIA_MANTER, parametros,
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
		AgendadorTarefas.agendarTarefa("RelatorioManterBacia", this);
	}

}
