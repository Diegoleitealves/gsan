package gcom.relatorio.cobranca.spcserasa;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.NegativacaoCriterio;
import gcom.cobranca.NegativadorResultadoSimulacao;
import gcom.fachada.Fachada;
import gcom.relatorio.ConstantesExecucaoRelatorios;
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
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 
 * Title: GCOM
 * </p>
 * <p>
 * 
 * Description: Sistema de Gest�o Comercial
 * </p>
 * <p>
 * 
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * 
 * Company: COMPESA - Companhia Pernambucana de Saneamento
 * </p>
 * 
 * @author Yara Taciane
 * @created 16 de maio de 2008
 * @version 1.0
 */

public class RelatorioNegativadorResultadoSimulacao extends TarefaRelatorio {
	private static final long serialVersionUID = 1L;
	/**
	 * Constructor for the RelatorioManterNegativadorExclusaoMotivo object
	 */
	public RelatorioNegativadorResultadoSimulacao(Usuario usuario) {
		super(usuario, ConstantesRelatorios.RELATORIO_MANTER_NEGATIVADOR_EXCLUSAO_MOTIVO);
	}

	@Deprecated
	public RelatorioNegativadorResultadoSimulacao() {
		super(null, "");
	}

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param NegativadorExclusaoMotivo Parametros
	 *            Description of the Parameter
	 * @return Descri��o do retorno
	 * @exception RelatorioVazioException
	 *                Descri��o da exce��o
	 */

	public Object executar() throws TarefaException {

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		// Recebe os par�metros que ser�o utilizados no relat�rio
		
		NegativadorResultadoSimulacao parametrosNegativadorResultadoSimulacao = (NegativadorResultadoSimulacao) getParametro("parametros");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// valor de retorno
		byte[] retorno = null;

		// cole��o de beans do relat�rio

		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		RelatorioNegativadorResultadoSimulacaoBean relatorioBean = null;

		// Cria adiciona os carregamentos dos objetos necess�rios para
		// a impress�o do relat�rio	
	//	filtro.adicionarCaminhoParaCarregamentoEntidade("negativacaoComando");
	//	filtro.setConsultaSemLimites(true);

		// Nova consulta para trazer objeto completo
//		Collection<NegativadorResultadoSimulacao> colecaoNovos = fachada.pesquisar(filtro, NegativadorResultadoSimulacao.class
//				.getName());

		Collection<NegativadorResultadoSimulacao> colecaoNovos = fachada.pesquisarNegativadorResultadoSimulacao(parametrosNegativadorResultadoSimulacao.getId());
		
		if (colecaoNovos != null && !colecaoNovos.isEmpty()) {
			// coloca a cole��o de par�metros da analise no iterator
			for (NegativadorResultadoSimulacao negativadorResultadoSimulacao : colecaoNovos) {
				
				// Faz as valida��es dos campos necess�riose e formata a String
				// para a forma como ir� aparecer no relat�rio
				
				String idComando = "";				
				if (negativadorResultadoSimulacao.getNegativacaoComando().getId() != null ) {
					idComando = negativadorResultadoSimulacao.getNegativacaoComando().getId().toString();
				}
				
										
				String descricaoTitulo = "";
				NegativacaoCriterio nCriterio = fachada.pesquisarNegativacaoCriterio(new Integer(idComando));
				if(nCriterio != null){
					descricaoTitulo = nCriterio.getDescricaoTitulo();
				}
			
				
				
				// Descri��o
				String sequencial = "";				
				if (negativadorResultadoSimulacao.getId() != null ) {
					sequencial = negativadorResultadoSimulacao.getId().toString();
				}
				
				// idImovel
				String idImovel = "";				
				if (negativadorResultadoSimulacao.getImovel().getId() != null) {
					idImovel = negativadorResultadoSimulacao.getImovel().getId().toString();
				}			
				
				//numero cpf
				String numeroCpf = "";				
				if (negativadorResultadoSimulacao.getNumeroCpf() != null) {					
					numeroCpf = Util.formatarCpf(negativadorResultadoSimulacao.getNumeroCpf());
				}
				
				//numero cnpj
				String numeroCnpj = "";				
				if (negativadorResultadoSimulacao.getNumeroCnpj() != null) {					
					numeroCnpj = Util.formatarCnpj(negativadorResultadoSimulacao.getNumeroCnpj());
				}
				
				

				// valorDebito
				BigDecimal valorDebito = null;				
				if (negativadorResultadoSimulacao.getValorDebito() != null) {					
					valorDebito = negativadorResultadoSimulacao.getValorDebito();
				}

				// Inicializa o construtor constitu�do dos campos
				// necess�rios para a impress�o do relatorio
				relatorioBean = new RelatorioNegativadorResultadoSimulacaoBean(
						
						// ID
						idComando,
						
						descricaoTitulo,

						// Descri��o
						sequencial,

						//idImovel
						idImovel,

						//valorDebito
						valorDebito,					
					
						// numero Cpf
						numeroCpf,
						
						// numero numeroCnpj
						numeroCnpj);

				// adiciona o bean a cole��o
				relatorioBeans.add(relatorioBean);
			}
		}

		// Par�metros do relat�rio
		Map parametros = new HashMap();

		// adiciona os par�metros do relat�rio
		// adiciona o laudo da an�lise
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		parametros.put("idComando", parametrosNegativadorResultadoSimulacao.getId() == -1 ? ""
				: "" + parametrosNegativadorResultadoSimulacao.getId());	


		// cria uma inst�ncia do dataSource do relat�rio
		RelatorioDataSource ds = new RelatorioDataSource((List) relatorioBeans);

		retorno = this.gerarRelatorio(
				ConstantesRelatorios.RELATORIO_NEGATIVADOR_RESULTADO_SIMULACAO, parametros, ds,
				tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relat�rio no sistema
		try {
			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_RESULTADO_SIMULACAO,
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
		int retorno = ConstantesExecucaoRelatorios.QUANTIDADE_NAO_INFORMADA;

//		retorno = Fachada.getInstancia().totalRegistrosPesquisa(
//				(FiltroNegativadorExclusaoMotivo) getParametro("filtroNegativadorResultadoSimulacao"),
//				NegativadorResultadoSimulacao.class.getName());

		return retorno;
	}

	public void agendarTarefaBatch() {
		AgendadorTarefas.agendarTarefa("RelatorioNegativadorResultadoSimulacao", this);
	}

}
