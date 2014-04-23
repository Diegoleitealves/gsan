package gcom.relatorio.cobranca;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.bean.ReavisoDeDebitoHelper;
import gcom.fachada.Fachada;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.RelatorioVazioException;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.ZipUtil;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

/**
 * classe respons�vel por criar as contas apartir do txt
 * 
 * @author Fl�vio Leonardo
 * @created 27/06/2007
 */
public class RelatorioAvisoDeDebito extends TarefaRelatorio {
	
	private static final long serialVersionUID = 1L;
	
	public RelatorioAvisoDeDebito(Usuario usuario) {
		super(usuario, ConstantesRelatorios.RELATORIO_CONTA_TIPO_2);
	}

	@Deprecated
	public RelatorioAvisoDeDebito() {
		super(null, "");
	}

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param bairros
	 *            Description of the Parameter
	 * @param bairroParametros
	 *            Description of the Parameter
	 * @return Descri��o do retorno
	 * @exception RelatorioVazioException
	 *                Descri��o da exce��o
	 */
	public Object executar() throws TarefaException {

		// valor de retorno
		byte[] retorno = null;

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");
		RelatorioReavisoDeDebitoBean relatorioReavisoDeDebitoBean = null;

		List colecaoEmitirReavisoDeDebitoHelper = 
			(ArrayList) getParametro("colecaoEmitirReavisoDeDebitoHelper");
		
		String nomeArquivo = "RELATORIO"+((String) getParametro("nomeArquivo"));
		final int quantidadeRegistros = 1000;
		int parte = 1;
		
		
		if(colecaoEmitirReavisoDeDebitoHelper != null && !colecaoEmitirReavisoDeDebitoHelper.isEmpty()){
			
			ZipOutputStream zos = null;
			File compactadoTipo = new File(nomeArquivo+".zip");
			
			try {
				zos = new ZipOutputStream(new FileOutputStream(compactadoTipo));
			} catch (IOException e) {
				e.printStackTrace();
				throw new TarefaException("Erro ao gerar o arquivo zip", e);
			}
			
			boolean flagTerminou = false;
			
			int totalContas = colecaoEmitirReavisoDeDebitoHelper.size();
			int quantidadeContas = 0;

			ReavisoDeDebitoHelper emitirReavisoDeDebitoHelperPrimeiraConta = null;
			ReavisoDeDebitoHelper emitirReavisoDeDebitoHelperSegundaConta = null;
			while (!flagTerminou) {
				
				// cole��o de beans do relat�rio
				List relatorioBeans = new ArrayList();				
				
				for (int i = 0; i < quantidadeRegistros; i++) {
					int index = quantidadeContas;
					ReavisoDeDebitoHelper emitirContaTipo2Helper = (ReavisoDeDebitoHelper) colecaoEmitirReavisoDeDebitoHelper.get(index);
					
					if(emitirReavisoDeDebitoHelperPrimeiraConta == null){
						emitirReavisoDeDebitoHelperPrimeiraConta = emitirContaTipo2Helper;
					}else{
						emitirReavisoDeDebitoHelperSegundaConta = emitirContaTipo2Helper;
					}
					
					if(emitirReavisoDeDebitoHelperPrimeiraConta != null && emitirReavisoDeDebitoHelperSegundaConta != null){
						
						relatorioReavisoDeDebitoBean = 
							new RelatorioReavisoDeDebitoBean(emitirReavisoDeDebitoHelperPrimeiraConta,
									emitirReavisoDeDebitoHelperSegundaConta);
						
						relatorioBeans.add(relatorioReavisoDeDebitoBean);
						
						emitirReavisoDeDebitoHelperPrimeiraConta = null;
						emitirReavisoDeDebitoHelperSegundaConta = null;
					}
					
					quantidadeContas++;
					
					if(quantidadeContas == totalContas){
						break;
					}
					emitirContaTipo2Helper = null;
				}
			
			
			//Caso tenha sobrado apenas uma conta
			if(emitirReavisoDeDebitoHelperPrimeiraConta != null){
				
				emitirReavisoDeDebitoHelperSegundaConta = new ReavisoDeDebitoHelper();
				
				relatorioReavisoDeDebitoBean = 
					new RelatorioReavisoDeDebitoBean(emitirReavisoDeDebitoHelperPrimeiraConta,
							emitirReavisoDeDebitoHelperSegundaConta);
				
				relatorioBeans.add(relatorioReavisoDeDebitoBean);

			}
			if(quantidadeContas == totalContas){
				flagTerminou = true;
			}
			
		
		Fachada fachada = Fachada.getInstancia();

		// __________________________________________________________________

		// Par�metros do relat�rio
		Map parametros = new HashMap();
		
		// adiciona os par�metros do relat�rio
		// adiciona o laudo da an�lise
		
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		
		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		// cria uma inst�ncia do dataSource do relat�rio
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		System.out.println("******************************************");
		System.out.println("GERA RELATORIO AVISO CORTE (Parte "+parte+") - PDF");
		System.out.println("*******************************************");
		
		retorno = gerarRelatorio(ConstantesRelatorios.REAVISO_DE_DEBITO,
				parametros, ds, tipoFormatoRelatorio);
		
		

	//		 Grava o relat�rio no sistema
			try {
	
				System.out.println("***********************************************");
				System.out.println("COLOCA NO ZIP O RELATORIO AVISO CORTE (Parte "+parte+")");
				System.out.println("***********************************************");
	
				File leitura = new File(nomeArquivo+"-Parte-"+parte+".pdf");
				
				FileOutputStream out = new FileOutputStream(leitura.getAbsolutePath());
				
				out.write(retorno);
				out.close();
	
				parte++;
	
				ZipUtil.adicionarArquivo(zos, leitura);
				
				leitura.delete();
				
			} catch (IOException e) {
				e.printStackTrace();
				throw new TarefaException("Erro ao gravar relat�rio no diretorio", e);
			}
			
			// limpamos a cole��o de beans do relat�rio
			relatorioBeans.clear();
			relatorioBeans = null;			
		}
		try {
			System.out.println("**************");
			System.out.println("FINALIZA O ZIP");
			System.out.println("**************");
	
			zos.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new TarefaException("Erro ao fechar o zip do relatorio", e);
		}
	}
//	 ------------------------------------
	// Grava o relat�rio no sistema
	try {
		persistirRelatorioConcluido(retorno, Relatorio.AVISO_DEBITO,
				idFuncionalidadeIniciada);
	} catch (ControladorException e) {
		e.printStackTrace();
		throw new TarefaException("Erro ao gravar relat�rio no sistema", e);
	}

		// retorna o relat�rio gerado
		return retorno;
	
}

	@Override
	public int calcularTotalRegistrosRelatorio() {
		int retorno = 0;

		return retorno;
	}

	public void agendarTarefaBatch() {
		AgendadorTarefas.agendarTarefa("RelatorioContaTipo2", this);

	}

}
