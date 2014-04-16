package gcom.relatorio.cadastro.imovel;

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
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * classe respons�vel por criar o relat�rio de imoveis por Programas Especiais
 * 
 * @author Hugo Leonrado
 * @created 18/01/2010
 */
public class RelatorioImoveisProgramasEspeciaisSintetico extends TarefaRelatorio {
	
	private static final long serialVersionUID = 1L;
	
	public RelatorioImoveisProgramasEspeciaisSintetico(Usuario usuario) {
		super(usuario, ConstantesRelatorios.RELATORIO_IMOVEIS_PROGRAMAS_ESPECIAIS_SINTETICO);
	}

	@Deprecated
	public RelatorioImoveisProgramasEspeciaisSintetico() {
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

		FiltrarRelatorioImoveisProgramasEspeciaisHelper relatorioHelper = 
			(FiltrarRelatorioImoveisProgramasEspeciaisHelper) getParametro("filtrarRelatorioImoveisProgramasEspeciaisHelper");
		
		
		String anoMesReferencia = relatorioHelper.getMesAnoReferencia();
		Integer tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");
		String perfilImovel = relatorioHelper.getNomePerfilImovel();
		String opcaoTotalizacao = relatorioHelper.getOpcaoTotalizacao();
		String opcaoRelatorio = relatorioHelper.getTipo();
		
		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		RelatorioImoveisProgramasEspeciaisBean relatorioImoveisProgramasEspeciaisBean = null;

		
		Collection<RelatorioImoveisProgramasEspeciaisHelper> colecao =  
			fachada.pesquisarRelatorioImoveisProgramasEspeciaisSintetico(relatorioHelper);

		// se a cole��o de par�metros da analise n�o for vazia
		if (colecao != null && !colecao.isEmpty()) {

			// coloca a cole��o de par�metros da analise no iterator
			Iterator colecaoIterator = colecao.iterator();

			
			// la�o para criar a cole��o de par�metros da analise
			while (colecaoIterator.hasNext()) {

				RelatorioImoveisProgramasEspeciaisHelper helper = 
					(RelatorioImoveisProgramasEspeciaisHelper) colecaoIterator.next();
				
				Integer qtdTotalImoveis = helper.getQtdImoveisSemHidr() + helper.getQtdImoveisComHidr();
				 
				BigDecimal valor1 = helper.getValorContasSemHidr();
				BigDecimal valor2 = helper.getValorContasComHidr();
				BigDecimal valorTotalContas = valor1.add(valor2);
				
				relatorioImoveisProgramasEspeciaisBean = 
					new RelatorioImoveisProgramasEspeciaisBean(
							helper.getIdRegiaoDesenvolvimento(),
							helper.getNomeRegiaoDesenvolvimento(),
							helper.getIdLocalidade(),
							helper.getNomeLocalidade(),
							helper.getQtdImoveisSemHidr(),
							helper.getValorContasSemHidr(),
							helper.getQtdImoveisComHidr(),
							helper.getValorContasComHidr(),
							qtdTotalImoveis,
							valorTotalContas
							);

				// adiciona o bean a cole��o
				relatorioBeans.add(relatorioImoveisProgramasEspeciaisBean);	
			}
	
		}
		// __________________________________________________________________

		// Par�metros do relat�rio
		Map parametros = new HashMap();
		
		// adiciona os par�metros do relat�rio
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		
		parametros.put("imagem", sistemaParametro.getImagemRelatorio());		
	
		parametros.put("anoMesReferencia" , Util.formatarAnoMesParaMesAno(anoMesReferencia));
		
		// selecionar o tipo de relat�rio
		
		if(opcaoRelatorio.equals("0")) {
			opcaoRelatorio = "ANAL�TICO";
		} else {
			opcaoRelatorio = "SINT�TICO";
		}
		
		parametros.put("tipo", opcaoRelatorio);
		
		parametros.put("perfilImovel", perfilImovel);
		
		// selecionar a op��o de Totaliza��o
		if ( opcaoTotalizacao != null){
			if (opcaoTotalizacao.equals("0")) {
				opcaoTotalizacao = "ESTADO";
			} else if ( opcaoTotalizacao.equals("1")) {
				opcaoTotalizacao = "REGI�O DE DESENVOLVIMENTO";
			} else if (opcaoTotalizacao.equals("2")) {
				opcaoTotalizacao = "LOCALIDADE";
			}
		}
		parametros.put("opcaoTotalizacao", opcaoTotalizacao);
		
		// cria uma inst�ncia do dataSource do relat�rio
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_IMOVEIS_PROGRAMAS_ESPECIAIS_SINTETICO,
				parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relat�rio no sistema
		try {
			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_IMOVEIS_PROGRAMAS_ESPECIAIS_SINTETICO,
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
		
		int retorno = 0;

		
		retorno = 
			Fachada.getInstancia().pesquisarTotalRegistroRelatorioImoveisProgramaEspecial(
				(FiltrarRelatorioImoveisProgramasEspeciaisHelper) 
					getParametro("filtrarRelatorioImoveisProgramasEspeciaisHelper"));
        
		if (retorno == 0) {
			// Caso a pesquisa n�o retorne nenhum resultado comunica ao
			// usu�rio;
			throw new RelatorioVazioException("atencao.relatorio.vazio");

		}
		
		return retorno;		
	}

	public void agendarTarefaBatch() {
		AgendadorTarefas.agendarTarefa("RelatorioImoveisProgramasEspeciaisSintetico", this);

	}

}
