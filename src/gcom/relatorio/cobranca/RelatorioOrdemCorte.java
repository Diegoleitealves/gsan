package gcom.relatorio.cobranca;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.bean.OrdemCorteHelper;
import gcom.fachada.Fachada;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.RelatorioPadraoBatch;
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
 * classe respons�vel por criar as ordens de corte apartir do txt
 * 
 * @author Rafael Pinto
 * @created 08/08/2007
 */
public class RelatorioOrdemCorte extends TarefaRelatorio {
	
	private static final long serialVersionUID = 1L;
	
	public RelatorioOrdemCorte(Usuario usuario) {
		super(usuario, ConstantesRelatorios.RELATORIO_ORDEM_CORTE);
	}

	@Deprecated
	public RelatorioOrdemCorte() {
		super(null, "");
	}

	public Object executar() throws TarefaException {
		
		System.out.println("********************************************");
		System.out.println("ENTROU NO EXECUTAR RELATORIO ORDEM DE CORTE ");
		System.out.println("********************************************");

		// valor de retorno
		byte[] retorno = null;

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------
		
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");
		
		String nomeArquivo = "RELATORIO"+((String) getParametro("nomeArquivo"));
		String nomeArquvioZip = nomeArquivo+".zip";
		
		
		RelatorioOrdemCorteBean relatorioOrdemCorteBean = null;

		final int quantidadeRegistros = 1000;

		List colecaoOrdemCorteHelper = 
			(ArrayList) getParametro("colecaoOrdemCorteHelper");
		
		if(colecaoOrdemCorteHelper != null && !colecaoOrdemCorteHelper.isEmpty()){

			ZipOutputStream zos = null;
			File compactadoTipo = new File(nomeArquvioZip);
			
			try {
				zos = new ZipOutputStream(new FileOutputStream(compactadoTipo));
			} catch (IOException e) {
				e.printStackTrace();
				throw new TarefaException("Erro ao gerar o arquivo zip", e);
			}

			Fachada fachada = Fachada.getInstancia();
			SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

			boolean flagTerminou = false;
			
			int totalContas = colecaoOrdemCorteHelper.size();
			int quantidadeContas = 0;
			int parte = 1;

			
			while (!flagTerminou) {

				// cole��o de beans do relat�rio
				List relatorioBeans = new ArrayList();
				
				OrdemCorteHelper ordemCorteHelperPrimeiraConta = null;
				OrdemCorteHelper ordemCorteHelperSegundaConta = null;

				for (int i = 0; i < quantidadeRegistros; i++) {
					
					int index = quantidadeContas;
					
					OrdemCorteHelper ordemCorteHelper = 
						(OrdemCorteHelper) colecaoOrdemCorteHelper.get(index);

					if(ordemCorteHelperPrimeiraConta == null){
						ordemCorteHelperPrimeiraConta = ordemCorteHelper;
					}else{
						ordemCorteHelperSegundaConta = ordemCorteHelper;
					}
					
					if(ordemCorteHelperPrimeiraConta != null && ordemCorteHelperSegundaConta != null){
						
						relatorioOrdemCorteBean = 
							new RelatorioOrdemCorteBean(ordemCorteHelperPrimeiraConta,
									ordemCorteHelperSegundaConta);
						
						relatorioBeans.add(relatorioOrdemCorteBean);
						
						ordemCorteHelperPrimeiraConta = null;
						ordemCorteHelperSegundaConta = null;
					}
					
					quantidadeContas++;
					
					if(quantidadeContas == totalContas){
						break;
					}

				}
				
				//Caso tenha sobrado apenas uma conta
				if(ordemCorteHelperPrimeiraConta != null){
					
					ordemCorteHelperSegundaConta = new OrdemCorteHelper();
					ordemCorteHelperSegundaConta.setInscricao(null);
					
					relatorioOrdemCorteBean = 
						new RelatorioOrdemCorteBean(ordemCorteHelperPrimeiraConta,
								ordemCorteHelperSegundaConta);
					
					relatorioBeans.add(relatorioOrdemCorteBean);
				}
				
				if(quantidadeContas == totalContas){
					flagTerminou = true;
				}
				
				// Par�metros do relat�rio
				Map parametros = new HashMap();
				
				// adiciona os par�metros do relat�rio
				// adiciona o laudo da an�lise
				
				parametros.put("imagem", sistemaParametro.getImagemRelatorio());

				// cria uma inst�ncia do dataSource do relat�rio
				RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

				System.out.println("******************************************");
				System.out.println("GERA RELATORIO ORDEM CORTE (Parte "+parte+") - PDF");
				System.out.println("*******************************************");

				retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_ORDEM_CORTE,
						parametros, ds, tipoFormatoRelatorio);
				
				// ------------------------------------
				// Grava o relat�rio no sistema
				try {

					System.out.println("***********************************************");
					System.out.println("COLOCA NO ZIP O RELATORIO ORDEM CORTE (Parte "+parte+")");
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
		
		// ------------------------------------
		// Grava o relat�rio no sistema
		try {
			
			RelatorioPadraoBatch relatorio = 
				new RelatorioPadraoBatch(Usuario.USUARIO_BATCH);

			relatorio.addParametro("titulo","RELAT�RIO DE FATURAS CLIENTE RESPONS�VEL");
			relatorio.addParametro("tipoFormatoRelatorio", TarefaRelatorio.TIPO_PDF);
			relatorio.addParametro("nomeArquivo", nomeArquvioZip);
			
			byte[] relatorioGerado = (byte[]) relatorio.executar();
			
			
			persistirRelatorioConcluido(relatorioGerado, Relatorio.ORDEM_CORTE,
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
		AgendadorTarefas.agendarTarefa("RelatorioOrdemCorte", this);

	}

}
