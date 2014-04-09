package gcom.relatorio.faturamento;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.RelatorioVazioException;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * classe respons�vel por criar o Relat�rio Devolu��o dos Pagamentos em Duplicidade.
 * 
 * [UC1129] Gerar Relat�rio Devolu��o dos Pagamentos em Duplicidade
 * 
 * @author Hugo Leonardo
 *
 * @date 10/03/2011
 */
public class RelatorioDevolucaoPagamentosDuplicidade extends TarefaRelatorio {
	
	private static final long serialVersionUID = 1L;
	
	public RelatorioDevolucaoPagamentosDuplicidade(Usuario usuario) {
		super(usuario, ConstantesRelatorios.RELATORIO_DEVOLUCAO_PAGAMENTO_DUPLICIDADE);
	}

	@Deprecated
	public RelatorioDevolucaoPagamentosDuplicidade() {
		super(null, "");
	}

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 */
	public Object executar() throws TarefaException {

		// valor de retorno
		byte[] retorno = null;

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		FiltrarRelatorioDevolucaoPagamentosDuplicidadeHelper relatorioHelper = 
			(FiltrarRelatorioDevolucaoPagamentosDuplicidadeHelper) getParametro("filtrarRelatorioDevolucaoPagamentosDuplicidadeHelper");
		
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");
		
		String gerenciaDescricao = (String) getParametro("gerenciaDescricao");
		String unidadeDescricao = (String) getParametro("unidadeDescricao");
		String perfilDescricao = (String) getParametro("perfilDescricao");
		String categoriaDescricao = (String) getParametro("categoriaDescricao");
		String periodoReferencia = (String) getParametro("periodoReferencia");

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		RelatorioDevolucaoPagamentosDuplicidadeBean relatorioDevolucaoPagamentosDuplicidadeBean = null;

		Collection<RelatorioDevolucaoPagamentosDuplicidadeHelper> colecao =  
			fachada.pesquisarRelatorioDevolucaoPagamentosDuplicidade(relatorioHelper);

		// se a cole��o de par�metros da analise n�o for vazia
		if (colecao != null && !colecao.isEmpty()) {

			// coloca a cole��o de par�metros da analise no iterator
			Iterator colecaoIterator = colecao.iterator();

			// la�o para criar a cole��o de par�metros da analise
			while (colecaoIterator.hasNext()) {

				RelatorioDevolucaoPagamentosDuplicidadeHelper helper = 
					(RelatorioDevolucaoPagamentosDuplicidadeHelper) colecaoIterator.next();
				
				relatorioDevolucaoPagamentosDuplicidadeBean = 
					new RelatorioDevolucaoPagamentosDuplicidadeBean(
							
							helper.getIdGerencia(),
							helper.getNomeGerencia(),
							helper.getIdUnidade(),
							helper.getNomeUnidade(),
							helper.getIdLocalidade(),
							helper.getNomeLocalidade(),
							helper.getNumeroRA(),
							helper.getMatricula(),
							helper.getMesAnoPagamentoDuplicidade(),
							helper.getValorPagamentoDuplicidade(),
							helper.getMesAnoConta(),
							helper.getValorConta(),
							helper.getCreditoRealizado(),
							helper.getCreditoARealizar(),
							helper.getDataAtualizacao());

				// adiciona o bean a cole��o
				relatorioBeans.add(relatorioDevolucaoPagamentosDuplicidadeBean);				
			}
		}else{
			
			throw new RelatorioVazioException("atencao.relatorio.vazio");
		}
		// __________________________________________________________________

		// Par�metros do relat�rio
		Map parametros = new HashMap();
		
		// adiciona os par�metros do relat�rio
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		
		parametros.put("imagem", sistemaParametro.getImagemRelatorio());	
		
		parametros.put("gerenciaDescricao", gerenciaDescricao); 
		parametros.put("unidadeDescricao", unidadeDescricao);
		parametros.put("perfilDescricao", perfilDescricao);
		parametros.put("categoriaDescricao", categoriaDescricao);
		parametros.put("periodoReferencia", periodoReferencia);
		
		// cria uma inst�ncia do dataSource do relat�rio
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_DEVOLUCAO_PAGAMENTO_DUPLICIDADE,
				parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relat�rio no sistema
		try {
			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_DEVOLUCAO_PAGAMENTO_DUPLICIDADE,
					idFuncionalidadeIniciada);
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
		
		int retorno = 10;

		/*
		retorno = Fachada.getInstancia().pesquisarTotalRelatorioAcessosPorUsuario(
				(FiltrarRelatorioDevolucaoPagamentosDuplicidadeHelper) 
					getParametro("filtrarRelatorioDevolucaoPagamentosDuplicidadeHelper"));
        */
        
		if (retorno == 0) {
			// Caso a pesquisa n�o retorne nenhum resultado comunica ao
			// usu�rio;
			throw new RelatorioVazioException("atencao.relatorio.vazio");
		}
		
		return retorno;		
	}

	public void agendarTarefaBatch() {
		AgendadorTarefas.agendarTarefa("RelatorioDevolucaoPagamentosDuplicidade", this);
	}

}
