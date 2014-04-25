package gcom.relatorio;

import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * respons�vel de pegar os parametros que ser�o exibidos na parte de detail
 * do relat�rio.
 * 
 * @author Rafael Pinto
 * @created 04/09/2007
 */
public class RelatorioPadraoBatch extends TarefaRelatorio {
	
	private static final long serialVersionUID = 1L;
	
	public RelatorioPadraoBatch(Usuario usuario) {
		super(usuario, ConstantesRelatorios.RELATORIO_PADRAO_BATCH);
	}
	
	@Deprecated
	public RelatorioPadraoBatch() {
		super(null, "");
	}
	
	/**
	 * M�todo que executa a tarefa
	 * 
	 * @return Object
	 */
	public Object executar() throws TarefaException {
		
		
		String titulo = (String) getParametro("titulo");
		String nomeArquivo = (String) getParametro("nomeArquivo");
		
		String observacao = "O arquivo  "+nomeArquivo+"  encontra-se no diret�rio padr�o";
		
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// valor de retorno
		byte[] retorno = null;

		Fachada fachada = Fachada.getInstancia();
		
		// Par�metros do relat�rio
		Map parametros = new HashMap();
		
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		
		parametros.put("imagem", sistemaParametro.getImagemRelatorio());
		parametros.put("titulo", titulo);
		
		List relatorioBeans = new ArrayList();
		
		RelatorioPadraoBatchBean relatorioBean = new RelatorioPadraoBatchBean(observacao);
		relatorioBeans.add(relatorioBean);
		
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);
		
		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_PADRAO_BATCH,
				parametros, ds, tipoFormatoRelatorio);

		// retorna o relat�rio gerado
		return retorno;

	}

	@Override
	public int calcularTotalRegistrosRelatorio() {
		int retorno = 0;
		return retorno;
	}

	@Override
	public void agendarTarefaBatch() {
		AgendadorTarefas.agendarTarefa("RelatorioPadraoBatch", this);
	}
}
