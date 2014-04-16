package gcom.relatorio.atendimentopublico;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.RelatorioPadraoBatch;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.Util;
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
 * @author Raphael Rossiter
 * @data 04/01/2008
 */
public class RelatorioOrdemFiscalizacao extends TarefaRelatorio {
	
	private static final long serialVersionUID = 1L;
	
	public RelatorioOrdemFiscalizacao(Usuario usuario) {
		super(usuario, ConstantesRelatorios.RELATORIO_ORDEM_FISCALIZACAO);
	}

	@Deprecated
	public RelatorioOrdemFiscalizacao() {
		super(null, "");
	}

	public Object executar() throws TarefaException {
		
		System.out.println("********************************************");
		System.out.println("ENTROU NO EXECUTAR RELATORIO ORDEM DE FISCALIZACAO ");
		System.out.println("********************************************");

		byte[] retorno = null;

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------
		
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");
		
		String nomeArquivo = (String) getParametro("nomeArquivo");
		String nomeArquivoZip = nomeArquivo+".zip";
		
		//Quantidade de ordens de fiscaliza��o que ser�o geradas por arquivo compactado 
		final int quantidadeRegistros = 1000;

		List colecaoOrdemFiscalizacao = (ArrayList) getParametro("colecaoOrdemFiscalizacao");
		
		
		if(colecaoOrdemFiscalizacao != null && !colecaoOrdemFiscalizacao.isEmpty()){

			ZipOutputStream zos = null;
			File compactadoTipo = new File(nomeArquivoZip);
			
			try {
				zos = new ZipOutputStream(new FileOutputStream(compactadoTipo));
			} catch (IOException e) {
				e.printStackTrace();
				throw new TarefaException("Erro ao gerar o arquivo zip", e);
			}

			Fachada fachada = Fachada.getInstancia();
			SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

			boolean flagTerminou = false;
			
			//Quantidade total de ordens que ser�o geradas
			int totalOrdemFiscalizacao = colecaoOrdemFiscalizacao.size();
			
			int quantidadeOrdemFiscalizacao = 0;
			int parte = 1;

			while (!flagTerminou) {

				List relatorioBeans = new ArrayList();

				for (int i = 0; i < quantidadeRegistros; i++) {
					
					int index = quantidadeOrdemFiscalizacao;
					
					RelatorioOrdemFiscalizacaoBean relatorioOrdemFiscalizacaoBean = 
					(RelatorioOrdemFiscalizacaoBean) colecaoOrdemFiscalizacao.get(index);

					relatorioBeans.add(relatorioOrdemFiscalizacaoBean);
						
					quantidadeOrdemFiscalizacao++;
					
					if(quantidadeOrdemFiscalizacao == totalOrdemFiscalizacao){
						break;
					}
				}
				
				if(quantidadeOrdemFiscalizacao == totalOrdemFiscalizacao){
					flagTerminou = true;
				}
				
				// Par�metros do relat�rio
				Map parametros = new HashMap();
				
				parametros.put("imagem", sistemaParametro.getImagemRelatorio());
				parametros.put("nomeCompletoEmpresa", sistemaParametro.getNomeEmpresa());
				parametros.put("nomeEmpresa", sistemaParametro.getNomeAbreviadoEmpresa());
				parametros.put("cnpjEmpresa", Util.formatarCnpj(sistemaParametro.getCnpjEmpresa()));
				parametros.put("inscricaoEmpresa", "");

				// cria uma inst�ncia do dataSource do relat�rio
				RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

				System.out.println("******************************************");
				System.out.println("GERA RELATORIO ORDEM FISCALIZACAO (Parte "+parte+") - PDF");
				System.out.println("*******************************************");

				retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_ORDEM_FISCALIZACAO,
				parametros, ds, tipoFormatoRelatorio);
				
				// ------------------------------------
				// Grava o relat�rio no sistema
				try {

					System.out.println("***********************************************");
					System.out.println("COLOCA NO ZIP O RELATORIO ORDEM FISCALIZACAO (Parte "+parte+")");
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

			relatorio.addParametro("titulo","RELAT�RIO ORDEM FISCALIZA��O");
			relatorio.addParametro("tipoFormatoRelatorio", TarefaRelatorio.TIPO_PDF);
			relatorio.addParametro("nomeArquivo", nomeArquivoZip);
			
			byte[] relatorioGerado = (byte[]) relatorio.executar();
			
			
			persistirRelatorioConcluido(relatorioGerado, Relatorio.RELATORIO_ORDEM_FISCALIZACAO,
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
		
		List colecaoOrdemFiscalizacao = (ArrayList) getParametro("colecaoOrdemFiscalizacao");
		
		retorno = colecaoOrdemFiscalizacao.size();

		return retorno;
	}

	public void agendarTarefaBatch() {
		AgendadorTarefas.agendarTarefa("RelatorioOrdemFiscalizacao", this);

	}

}
