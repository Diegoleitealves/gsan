package gcom.relatorio.atendimentopublico;

import gcom.atendimentopublico.bean.ContratoPrestacaoServicoHelper;
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

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * classe respons�vel por criar o relat�rio de bairro manter de �gua
 * 
 * @author S�vio Luiz
 * @created 11 de Julho de 2005
 */
public class RelatorioContratoPrestacaoServico extends TarefaRelatorio {
	private static final long serialVersionUID = 1L;
	public RelatorioContratoPrestacaoServico(Usuario usuario) {
		super(usuario, ConstantesRelatorios.RELATORIO_CONTRATO_PRESTACAO_SERVICO);
	}
	
	@Deprecated
	public RelatorioContratoPrestacaoServico() {
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

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");
		Integer idImovel = (Integer) getParametro("idImovel");

		Integer idCliente = (Integer) getParametro("idCliente");

		// valor de retorno
		byte[] retorno = null;
		
		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		RelatorioContratoPrestacaoServicoBean relatorioBean = null;
		
		Collection colecaoContratoPrestacaoServicoHelper = fachada.obterDadosContratoPrestacaoServico(idImovel, idCliente);


		// se a cole��o de par�metros da analise n�o for vazia
		if (colecaoContratoPrestacaoServicoHelper != null
				&& !colecaoContratoPrestacaoServicoHelper.isEmpty()) {

			// coloca a cole��o de par�metros da analise no iterator
			Iterator colecaoContratoPrestacaoServicoHelperIterator = colecaoContratoPrestacaoServicoHelper
					.iterator();
			
			Date dataCorrente = new Date();

			// la�o para criar a cole��o de par�metros da analise
			while (colecaoContratoPrestacaoServicoHelperIterator.hasNext()) {

				ContratoPrestacaoServicoHelper contratoPrestacaoServicoHelper = (ContratoPrestacaoServicoHelper) colecaoContratoPrestacaoServicoHelperIterator
						.next();
				
				// Faz as valida��es dos campos necess�rios e formata a String
				// para a forma como ir� aparecer no relat�rio
				
				// Nome Cliente
				String nomeCliente = "";
				if (contratoPrestacaoServicoHelper.getCliente().getNome() != null) {
					nomeCliente = contratoPrestacaoServicoHelper.getCliente().getNome(); 
				}
				
				// Nome Localidade
				String nomeLocalidade = "";
				if (contratoPrestacaoServicoHelper.getNomeLocalidade() != null) {
					nomeLocalidade = contratoPrestacaoServicoHelper.getNomeLocalidade(); 
				}
				
				// Nome Respons�vel
				String nomeResponsavel = "";
				if (contratoPrestacaoServicoHelper.getClienteResponsavel().getNome() != null) {
					nomeResponsavel = contratoPrestacaoServicoHelper.getClienteResponsavel().getNome(); 
				}
				
				// CPF Respons�vel
				String cpfResponsavel = "";
				if (contratoPrestacaoServicoHelper.getClienteResponsavel().getCpf() != null) {
					cpfResponsavel = contratoPrestacaoServicoHelper.getClienteResponsavel().getCpfFormatado(); 
				}
				
				// RG Respons�vel
				String rgResponsavel = "";
				if (contratoPrestacaoServicoHelper.getClienteResponsavel().getRg() != null) {
					rgResponsavel = contratoPrestacaoServicoHelper.getClienteResponsavel().getRg(); 
				}
				
				// CPF Cliente
				String cpfCliente = "";
				if (contratoPrestacaoServicoHelper.getCliente().getCpf() != null) {
					cpfCliente = contratoPrestacaoServicoHelper.getCliente().getCpfFormatado(); 
				}
				
				// RG Cliente
				String rgCliente = "";
				if (contratoPrestacaoServicoHelper.getCliente().getRg() != null) {
					rgCliente = contratoPrestacaoServicoHelper.getCliente().getRg(); 
				}
				
				// Consumo M�nimo
				String consumoMinimo = "";
				if (contratoPrestacaoServicoHelper.getConsumoMinimo() != null) {
					consumoMinimo = contratoPrestacaoServicoHelper.getConsumoMinimo().toString(); 
				}
				
				String anoCorrente = "" + Util.getAno(dataCorrente);
				
//				 Pega a Data Atual formatada da seguinte forma: dd de m�s(por
				// extenso) de aaaa
				// Ex: 23 de maio de 1985
				DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, Locale
						.getDefault());
				String dataCorrenteFormatada = df.format(new Date());
				
				
				// Dados da 1� p�gina
				relatorioBean = new RelatorioContratoPrestacaoServicoBean(
						
						// Indicador Pessoa F�sica
						"1",
						
						// N�mero P�gina
						"1",
						
						// N�mero Contrato
						idImovel.toString() + anoCorrente,
						
						// Nome Cliente
						nomeCliente,
						
						// Nome Localidade
						nomeLocalidade,
						
						// Nome Respons�vel
						nomeResponsavel,
						
						// CPF Respons�vel
						cpfResponsavel,
						
						// RG Respons�vel
						rgResponsavel,
						
						// CPF Cliente
						cpfCliente,
						
						// RG Cliente
						rgCliente,
						
						// Endere�o Cliente
						contratoPrestacaoServicoHelper.getEnderecoCliente(),
						
						// Id Im�vel
						idImovel.toString(),
						
						// Endere�o Im�vel
						contratoPrestacaoServicoHelper.getEnderecoImovel(),
						
						// Categoria
						contratoPrestacaoServicoHelper.getCategoria(),
						
						// Consumo M�nimo
						consumoMinimo,
						
						// Data Corrente
						"",
				
						// Munic�pio
						"");
				
				relatorioBeans.add(relatorioBean);
				
				// Dados da 2� p�gina
				relatorioBean = new RelatorioContratoPrestacaoServicoBean(
						
						// Indicador Pessoa F�sica
						"1",
						
						// N�mero P�gina
						"2",
						
						// N�mero Contrato
						"",
						
						// Nome Cliente
						"",
						
						// Nome Unidade Neg�cio
						nomeLocalidade,
						
						// Nome Respons�vel
						"",
						
						// CPF Respons�vel
						"",
						
						// RG Respons�vel
						"",
						
						// CPF Cliente
						"",
						
						// RG Cliente
						"",
						
						// Endere�o Cliente
						"",
						
						// Id Im�vel
						"",
						
						// Endere�o Im�vel
						"",
						
						// Categoria
						"",
						
						// Consumo M�nimo
						"",
						
						// Data Corrente
						dataCorrenteFormatada,
						
						// Munic�pio
						contratoPrestacaoServicoHelper.getNomeMunicipio());

				// adiciona o bean a cole��o
				relatorioBeans.add(relatorioBean);
				
//				relatorioBean = new RelatorioContratoPrestacaoServicoBean(
//						
//						// Indicador Pessoa F�sica
//						"1",
//						
//						// N�mero P�gina
//						"2");

				// adiciona o bean a cole��o
//				relatorioBeans.add(relatorioBean);
				
//				relatorioBean = new RelatorioContratoPrestacaoServicoBean(
//						
//						// Indicador Pessoa F�sica
//						"2",
//						
//						// N�mero P�gina
//						"1");
//
//				// adiciona o bean a cole��o
//				relatorioBeans.add(relatorioBean);
//				
//				relatorioBean = new RelatorioContratoPrestacaoServicoBean(
//						
//						// Indicador Pessoa F�sica
//						"2",
//						
//						// N�mero P�gina
//						"2");
//
//				// adiciona o bean a cole��o
//				relatorioBeans.add(relatorioBean);
			}
		}
		// __________________________________________________________________

		// Par�metros do relat�rio
		Map parametros = new HashMap();

		// adiciona os par�metros do relat�rio
		// adiciona o laudo da an�lise
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		
		parametros.put("imagem", sistemaParametro.getImagemRelatorio());
		
		// cria uma inst�ncia do dataSource do relat�rio
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = gerarRelatorio(
				ConstantesRelatorios.RELATORIO_CONTRATO_PRESTACAO_SERVICO,
				parametros, ds, tipoFormatoRelatorio);
		
		// ------------------------------------
		// Grava o relat�rio no sistema
		try {
			persistirRelatorioConcluido(retorno, Relatorio.MANTER_RESOLUCAO_DIRETORIA,
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

		return retorno;
	}

	public void agendarTarefaBatch() {
		AgendadorTarefas.agendarTarefa("RelatorioManterResolucaoDiretoria", this);
	}
}
