package gcom.relatorio.cobranca.spcserasa;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.FiltroNegativadorExclusaoMotivo;
import gcom.cobranca.NegativadorExclusaoMotivo;
import gcom.fachada.Fachada;
import gcom.relatorio.ConstantesExecucaoRelatorios;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.RelatorioVazioException;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.agendadortarefas.AgendadorTarefas;

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
 * @created 28 de Fevereiro de 2008
 * @version 1.0
 */

public class RelatorioManterNegativadorExclusaoMotivo extends TarefaRelatorio {
	private static final long serialVersionUID = 1L;
	/**
	 * Constructor for the RelatorioManterNegativadorExclusaoMotivo object
	 */
	public RelatorioManterNegativadorExclusaoMotivo(Usuario usuario) {
		super(usuario, ConstantesRelatorios.RELATORIO_MANTER_NEGATIVADOR_EXCLUSAO_MOTIVO);
	}

	@Deprecated
	public RelatorioManterNegativadorExclusaoMotivo() {
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
		FiltroNegativadorExclusaoMotivo  filtroNegativadorExclusaoMotivo  = (FiltroNegativadorExclusaoMotivo) getParametro("filtroNegativadorExclusaoMotivo");
		NegativadorExclusaoMotivo negativadorExclusaoMotivoParametros = (NegativadorExclusaoMotivo) getParametro("negativadorExclusaoMotivoParametros");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// valor de retorno
		byte[] retorno = null;

		// cole��o de beans do relat�rio

		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		RelatorioManterNegativadorExclusaoMotivoBean relatorioBean = null;

		// Cria adiciona os carregamentos dos objetos necess�rios para
		// a impress�o do relat�rio
		filtroNegativadorExclusaoMotivo.adicionarCaminhoParaCarregamentoEntidade("negativador.cliente");
		filtroNegativadorExclusaoMotivo.setConsultaSemLimites(true);

		// Nova consulta para trazer objeto completo
		Collection<NegativadorExclusaoMotivo> colecaoNegativadorExclusaoMotivoNovos = fachada.pesquisar(filtroNegativadorExclusaoMotivo, NegativadorExclusaoMotivo.class
				.getName());

		if (colecaoNegativadorExclusaoMotivoNovos != null && !colecaoNegativadorExclusaoMotivoNovos.isEmpty()) {
			// coloca a cole��o de par�metros da analise no iterator
			for (NegativadorExclusaoMotivo negativadorExclusaoMotivo : colecaoNegativadorExclusaoMotivoNovos) {
				
				// Faz as valida��es dos campos necess�riose e formata a String
				// para a forma como ir� aparecer no relat�rio
				
				// ID
				String id = "";
				
				if (negativadorExclusaoMotivo.getId() != null) {
					id = negativadorExclusaoMotivo.getId().toString();
				}
				
				// Descri��o
				String descricao = "";
				
				if (negativadorExclusaoMotivo.getDescricaoExclusaoMotivo() != null
						&& !negativadorExclusaoMotivo.getDescricaoExclusaoMotivo()
								.trim().equals("")) {
					descricao = negativadorExclusaoMotivo.getDescricaoExclusaoMotivo();
				}
				
				// codigoRegistro
				String codigoMotivo = "";				
				if (negativadorExclusaoMotivo.getCodigoExclusaoMotivo() != null) {
					codigoMotivo = Short.toString(negativadorExclusaoMotivo.getCodigoExclusaoMotivo()) ;
				}

				
				// negativador
				String negativador = "";				
				if (negativadorExclusaoMotivo.getNegativador().getId() != null) {
					negativador = negativadorExclusaoMotivo.getNegativador().getCliente().getNome();
				}
				
			
				// indicadorUso
				String indicadorUso = "";				
				if (negativadorExclusaoMotivo.getIndicadorUso() != null) {					
					if(negativadorExclusaoMotivo.getIndicadorUso().equals(ConstantesSistema.SIM)){
						indicadorUso = "Ativo";
					}else if(negativadorExclusaoMotivo.getIndicadorUso().equals(ConstantesSistema.NAO)){
						indicadorUso = "Inativo";
					}
				}
				

				// Inicializa o construtor constitu�do dos campos
				// necess�rios para a impress�o do relatorio
				relatorioBean = new RelatorioManterNegativadorExclusaoMotivoBean(
						
						// ID
						id,

						// Descri��o
						descricao,

						// C�digo Motivo
						codigoMotivo,

						// Descri��o do Negativador
						negativador,					
					
						// Indicador de Uso
						indicadorUso);

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

		parametros.put("negativador", negativadorExclusaoMotivoParametros.getNegativador().getId() == -1 ? ""
				: "" + negativadorExclusaoMotivoParametros.getNegativador().getCliente().getNome());	

		parametros.put("descricao", negativadorExclusaoMotivoParametros.getDescricaoExclusaoMotivo());

		parametros.put("codigoMotivo", negativadorExclusaoMotivoParametros.getCodigoExclusaoMotivo());		
		
		parametros.put("indicadorUso", negativadorExclusaoMotivoParametros.getIndicadorUso());

	

		// cria uma inst�ncia do dataSource do relat�rio

		RelatorioDataSource ds = new RelatorioDataSource((List) relatorioBeans);

		retorno = this.gerarRelatorio(
				ConstantesRelatorios.RELATORIO_MANTER_NEGATIVADOR_EXCLUSAO_MOTIVO, parametros, ds,
				tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relat�rio no sistema
		try {
			persistirRelatorioConcluido(retorno, Relatorio.MANTER_NEGATIVADOR_EXCLUSAO_MOTIVO,
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

		retorno = Fachada.getInstancia().totalRegistrosPesquisa(
				(FiltroNegativadorExclusaoMotivo) getParametro("filtroNegativadorExclusaoMotivo"),
				NegativadorExclusaoMotivo.class.getName());

		return retorno;
	}

	public void agendarTarefaBatch() {
		AgendadorTarefas.agendarTarefa("RelatorioManterNegativadorExclusaoMotivo", this);
	}

}
