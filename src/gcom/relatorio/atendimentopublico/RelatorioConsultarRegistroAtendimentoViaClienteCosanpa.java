package gcom.relatorio.atendimentopublico;

import gcom.atendimentopublico.registroatendimento.bean.ObterDadosRegistroAtendimentoHelper;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * classe respons�vel por criar o Relatorio de Registro de Atendimento 
 * Via Cliente - Especifico para a Cosanpa.
 * 
 * @author Hugo Leonardo
 * @created 08 de Abril de 2010
 */
public class RelatorioConsultarRegistroAtendimentoViaClienteCosanpa extends TarefaRelatorio {
	
	private static final long serialVersionUID = 1L;
	
	public RelatorioConsultarRegistroAtendimentoViaClienteCosanpa(Usuario usuario) {
		super(usuario,
				ConstantesRelatorios.RELATORIO_CONSULTAR_REGISTRO_ATENDIMENTO_VIA_CLIENTE_COSANPA);
	}
	
	@Deprecated
	public RelatorioConsultarRegistroAtendimentoViaClienteCosanpa() {
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

		Integer idRegistroAtendimento = (Integer) getParametro("idRegistroAtendimento");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");
		
		String nomeFuncionario = (String) getParametro("funcionario");

		// valor de retorno
		byte[] retorno = null;

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		RelatorioConsultarRegistroAtendimentoViaClienteBean relatorioBean = null;

		ObterDadosRegistroAtendimentoHelper registroAtendimentoHelper = fachada
				.obterDadosRegistroAtendimento(idRegistroAtendimento);

		// Seta um valor para um indicador que ser� comparado no relat�rio para
		// imprimir o t�tulo associado do n�mero e da situa��o da RA
		String indicadorAssociado = "";

		if (registroAtendimentoHelper.getTituloNumeroRAAssociado() != null
				&& registroAtendimentoHelper.getTituloNumeroRAAssociado()
						.equalsIgnoreCase("N�mero do RA de Refer�ncia")) {
			indicadorAssociado = "1";
		} else if (registroAtendimentoHelper.getTituloNumeroRAAssociado() != null
				&& registroAtendimentoHelper.getTituloNumeroRAAssociado()
						.equalsIgnoreCase("N�mero do RA Atual")) {
			indicadorAssociado = "2";
		} else if (registroAtendimentoHelper.getTituloNumeroRAAssociado() != null
				&& registroAtendimentoHelper.getTituloNumeroRAAssociado()
						.equalsIgnoreCase("N�mero do RA Anterior")) {
			indicadorAssociado = "3";
		}

		relatorioBean = new RelatorioConsultarRegistroAtendimentoViaClienteBean(

				// Dados Gerais

				// N�mero RA
				registroAtendimentoHelper.getRegistroAtendimento().getId()
						.toString(),

				// Situa��o RA
				registroAtendimentoHelper.getDescricaoSituacaoRA(),

				// Indicador RA Associado
				indicadorAssociado,

				// N�mero RA Associado
				registroAtendimentoHelper.getRAAssociado() == null ? ""
						: registroAtendimentoHelper.getRAAssociado().getId()
								.toString(),

				// Situa��o RA Associado
				registroAtendimentoHelper.getDescricaoSituacaoRAAssociado(),

				// Tipo Solicita��o
				registroAtendimentoHelper.getRegistroAtendimento()
						.getSolicitacaoTipoEspecificacao() == null ? ""
						: registroAtendimentoHelper.getRegistroAtendimento()
								.getSolicitacaoTipoEspecificacao()
								.getSolicitacaoTipo().getDescricao(),

				// Especifica��o
				registroAtendimentoHelper.getRegistroAtendimento()
						.getSolicitacaoTipoEspecificacao() == null ? ""
						: registroAtendimentoHelper.getRegistroAtendimento()
								.getSolicitacaoTipoEspecificacao()
								.getDescricao(),

				// Data Atendimento
				Util.formatarDataComHora(registroAtendimentoHelper
						.getRegistroAtendimento().getRegistroAtendimento()),

				// Data Prevista
				Util.formatarData(registroAtendimentoHelper
						.getRegistroAtendimento().getDataPrevistaAtual()),

				// Meio Solicita��o
				registroAtendimentoHelper.getRegistroAtendimento()
						.getMeioSolicitacao() == null ? ""
						: registroAtendimentoHelper.getRegistroAtendimento()
								.getMeioSolicitacao().getDescricao(),

				// Unidade Atendimento
				registroAtendimentoHelper.getUnidadeAtendimento() == null ? ""
						: registroAtendimentoHelper.getUnidadeAtendimento()
								.getDescricao(),

				// Unidade Atual
				registroAtendimentoHelper.getUnidadeAtual() == null ? ""
						: registroAtendimentoHelper.getUnidadeAtual()
								.getDescricao(),

				// Observa��o
				registroAtendimentoHelper.getRegistroAtendimento()
						.getObservacao(),

				// Dados do Local da Ocorr�ncia

				// Matr�cula do Im�vel
				registroAtendimentoHelper.getRegistroAtendimento().getImovel() == null ? ""
						: registroAtendimentoHelper.getRegistroAtendimento()
								.getImovel().getId().toString(),

				// Inscri��o do Im�vel
				registroAtendimentoHelper.getRegistroAtendimento().getImovel() == null ? ""
						: registroAtendimentoHelper.getRegistroAtendimento()
								.getImovel().getInscricaoFormatada(),

				// Endere�o da Ocorr�ncia
				registroAtendimentoHelper.getEnderecoOcorrencia(),

				// Ponto de Refer�ncia
				registroAtendimentoHelper.getRegistroAtendimento()
						.getPontoReferencia(),

				// Munic�pio
				registroAtendimentoHelper.getRegistroAtendimento()
						.getBairroArea() == null ? ""
						: registroAtendimentoHelper.getRegistroAtendimento()
								.getBairroArea().getBairro().getMunicipio()
								.getNome(),

				// Bairro
				registroAtendimentoHelper.getRegistroAtendimento()
						.getBairroArea() == null ? ""
						: registroAtendimentoHelper.getRegistroAtendimento()
								.getBairroArea().getBairro().getNome(),

				// �rea do Bairro
				registroAtendimentoHelper.getRegistroAtendimento()
						.getBairroArea() == null ? ""
						: registroAtendimentoHelper.getRegistroAtendimento()
								.getBairroArea().getNome(),

				// Localidade/Setor/Quadra
				(registroAtendimentoHelper.getRegistroAtendimento()
						.getLocalidade() == null ? "---" : Util
						.adicionarZerosEsquedaNumero(3,
								registroAtendimentoHelper
										.getRegistroAtendimento()
										.getLocalidade().getId().toString()))
						+ "/"
						+ (registroAtendimentoHelper.getRegistroAtendimento()
								.getSetorComercial() == null ? "---" : Util
								.adicionarZerosEsquedaNumero(3, ""
										+ registroAtendimentoHelper
												.getRegistroAtendimento()
												.getSetorComercial()
												.getCodigo()))
						+ "/"
						+ (registroAtendimentoHelper.getRegistroAtendimento()
								.getQuadra() == null ? "---" : Util
								.adicionarZerosEsquedaNumero(3, ""
										+ registroAtendimentoHelper
												.getRegistroAtendimento()
												.getQuadra().getNumeroQuadra())),

				// Divis�o Esgoto
				registroAtendimentoHelper.getRegistroAtendimento()
						.getDivisaoEsgoto() == null ? ""
						: registroAtendimentoHelper.getRegistroAtendimento()
								.getDivisaoEsgoto().getDescricao(),

				// Local da Ocorr�ncia
				registroAtendimentoHelper.getRegistroAtendimento()
						.getLocalOcorrencia() == null ? ""
						: registroAtendimentoHelper.getRegistroAtendimento()
								.getLocalOcorrencia().getDescricao(),

				// Pavimento Rua
				registroAtendimentoHelper.getRegistroAtendimento()
						.getPavimentoRua() == null ? ""
						: registroAtendimentoHelper.getRegistroAtendimento()
								.getPavimentoRua().getDescricao(),

				// Pavimento Cal�ada
				registroAtendimentoHelper.getRegistroAtendimento()
						.getPavimentoCalcada() == null ? ""
						: registroAtendimentoHelper.getRegistroAtendimento()
								.getPavimentoCalcada().getDescricao(),

				// Descri��o do Local da Ocorr�ncia
				registroAtendimentoHelper.getRegistroAtendimento()
						.getDescricaoLocalOcorrencia(),

				// Dados do Solicitante

				// C�digo do Cliente
				registroAtendimentoHelper.getSolicitante() == null ? ""
						: registroAtendimentoHelper.getSolicitante()
								.getCliente() == null ? ""
								: registroAtendimentoHelper.getSolicitante()
										.getCliente().getId().toString(),

				// Nome do Cliente
				registroAtendimentoHelper.getSolicitante() == null ? ""
						: registroAtendimentoHelper.getSolicitante()
								.getCliente() == null ? ""
								: registroAtendimentoHelper.getSolicitante()
										.getCliente().getNome(),
										
				//Protocolo
					registroAtendimentoHelper.getSolicitante() == null ? ""
							: registroAtendimentoHelper.getSolicitante()
								.getNumeroProtocoloAtendimento() == null ? ""
										: registroAtendimentoHelper.getSolicitante()
											.getNumeroProtocoloAtendimento(),						
				
										

				// Unidade Solicitante
				registroAtendimentoHelper.getSolicitante() == null ? ""
						: registroAtendimentoHelper.getSolicitante()
								.getUnidadeOrganizacional() == null ? ""
								: registroAtendimentoHelper.getSolicitante()
										.getUnidadeOrganizacional()
										.getDescricao(),

				// C�digo do Funcion�rio Respons�vel
				registroAtendimentoHelper.getSolicitante() == null ? ""
						: registroAtendimentoHelper.getSolicitante()
								.getFuncionario() == null ? ""
								: registroAtendimentoHelper.getSolicitante()
										.getFuncionario().getId().toString(),

				// Nome do Funcion�rio Respons�vel
				registroAtendimentoHelper.getSolicitante() == null ? ""
						: registroAtendimentoHelper.getSolicitante()
								.getFuncionario() == null ? ""
								: registroAtendimentoHelper.getSolicitante()
										.getFuncionario().getNome(),

				// Nome do Solicitante
				registroAtendimentoHelper.getSolicitante() == null ? ""
						: registroAtendimentoHelper.getSolicitante()
								.getSolicitante(),
								
				// Rota
				registroAtendimentoHelper.getCodigoRota() == null? "": registroAtendimentoHelper.getCodigoRota().toString(),
										
				// Sequencial Rota
				registroAtendimentoHelper.getSequencialRota() == null? "": registroAtendimentoHelper.getSequencialRota().toString());

		//Telefone principal do Solicitante
		String foneSolicitante = fachada.pesquisarClienteFonePrincipal(
				registroAtendimentoHelper.getSolicitante().getCliente().getId());
		relatorioBean.setFoneSolicitante(foneSolicitante);
		
		// adiciona o bean a cole��o
		relatorioBeans.add(relatorioBean);

		// __________________________________________________________________

		// Par�metros do relat�rio
		Map parametros = new HashMap();
		
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		
		parametros.put("imagem", sistemaParametro.getImagemRelatorio());
		
		parametros.put("funcionario", nomeFuncionario);

		// cria uma inst�ncia do dataSource do relat�rio
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = gerarRelatorio(
				ConstantesRelatorios.RELATORIO_CONSULTAR_REGISTRO_ATENDIMENTO_VIA_CLIENTE_COSANPA,
				parametros, ds, tipoFormatoRelatorio);
		
		// ------------------------------------
		// Grava o relat�rio no sistema
		try {
			persistirRelatorioConcluido(retorno, Relatorio.CONSULTAR_REGISTRO_ATENDIMENTO_VIA_CLIENTE_COSANPA,
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
		int retorno = 1;
		return retorno;
	}

	public void agendarTarefaBatch() {
		AgendadorTarefas.agendarTarefa("RelatorioConsultarRegistroAtendimentoViaClienteCosanpa", this);
	}
}
