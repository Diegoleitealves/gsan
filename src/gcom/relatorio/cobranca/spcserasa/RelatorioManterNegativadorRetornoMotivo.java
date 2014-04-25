package gcom.relatorio.cobranca.spcserasa;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.NegativadorRetornoMotivo;
import gcom.fachada.Fachada;
import gcom.relatorio.ConstantesExecucaoRelatorios;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.RelatorioVazioException;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.spcserasa.FiltroNegativadorRetornoMotivo;
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
 * @author Rafael Corr�a
 * @created 9 de Setembro de 2005
 * @version 1.0
 */

public class RelatorioManterNegativadorRetornoMotivo extends TarefaRelatorio {
	private static final long serialVersionUID = 1L;
	/**
	 * Constructor for the RelatorioManterNegativadorRegistroTipo object
	 */
	public RelatorioManterNegativadorRetornoMotivo(Usuario usuario) {
		super(usuario, ConstantesRelatorios.RELATORIO_MANTER_NEGATIVADOR_RETORNO_MOTIVO);
	}

	@Deprecated
	public RelatorioManterNegativadorRetornoMotivo() {
		super(null, "");
	}

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param NegativadorRegistroTipoParametros
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
		FiltroNegativadorRetornoMotivo filtroNegativadorRetornoMotivo = (FiltroNegativadorRetornoMotivo) getParametro("filtroNegativadorRetornoMotivo");
		NegativadorRetornoMotivo negativadorRetornoMotivoParametros = (NegativadorRetornoMotivo) getParametro("negativadorRetornoMotivoParametros");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// valor de retorno
		byte[] retorno = null;

		// cole��o de beans do relat�rio

		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		RelatorioManterNegativadorRetornoMotivoBean relatorioBean = null;

		// Cria adiciona os carregamentos dos objetos necess�rios para
		// a impress�o do relat�rio
		filtroNegativadorRetornoMotivo.adicionarCaminhoParaCarregamentoEntidade("negativador.cliente");
		filtroNegativadorRetornoMotivo.setConsultaSemLimites(true);

		// Nova consulta para trazer objeto completo
		Collection<NegativadorRetornoMotivo> colecaoNegativadorRetornoMotivoNovos = fachada.pesquisar(filtroNegativadorRetornoMotivo, NegativadorRetornoMotivo.class
				.getName());

		if (colecaoNegativadorRetornoMotivoNovos != null && !colecaoNegativadorRetornoMotivoNovos.isEmpty()) {
			// coloca a cole��o de par�metros da analise no iterator
			for (NegativadorRetornoMotivo negativadorRetornoMotivo : colecaoNegativadorRetornoMotivoNovos) {
				
				// Faz as valida��es dos campos necess�riose e formata a String
				// para a forma como ir� aparecer no relat�rio
				
				// ID
				String id = "";
				
				if (negativadorRetornoMotivo.getId() != null) {
					id = negativadorRetornoMotivo.getId().toString();
				}
				
				// Descri��o
				String descricao = "";
				
				if (negativadorRetornoMotivo.getDescricaoRetornocodigo() != null
						&& !negativadorRetornoMotivo.getDescricaoRetornocodigo()
								.trim().equals("")) {
					descricao = negativadorRetornoMotivo.getDescricaoRetornocodigo();
				}
				
				// codigoRegistro
				String codigoMotivo = "";				
				if (negativadorRetornoMotivo.getCodigoRetornoMotivo() != null) {
					codigoMotivo = Short.toString(negativadorRetornoMotivo.getCodigoRetornoMotivo()) ;
				}

				
				// negativador
				String negativador = "";				
				if (negativadorRetornoMotivo.getNegativador().getId() != null) {
					negativador = negativadorRetornoMotivo.getNegativador().getCliente().getNome();
				}
				
				// indicadorRegistroAceito
				String indicadorRegistroAceito = "";				
				if (negativadorRetornoMotivo.getIndicadorRegistroAceito() != null) {					
					if(negativadorRetornoMotivo.getIndicadorRegistroAceito().equals(ConstantesSistema.SIM)){
						indicadorRegistroAceito = "Sim";
					}else if(negativadorRetornoMotivo.getIndicadorRegistroAceito().equals(ConstantesSistema.NAO)){
						indicadorRegistroAceito = "N�o";
					}
					
					
				}

				
				// indicadorUso
				String indicadorUso = "";				
				if (negativadorRetornoMotivo.getIndicadorUso() != null) {					
					if(negativadorRetornoMotivo.getIndicadorUso().equals(ConstantesSistema.SIM)){
						indicadorUso = "Ativo";
					}else if(negativadorRetornoMotivo.getIndicadorUso().equals(ConstantesSistema.NAO)){
						indicadorUso = "Inativo";
					}
				}
				

				// Inicializa o construtor constitu�do dos campos
				// necess�rios para a impress�o do relatorio
				relatorioBean = new RelatorioManterNegativadorRetornoMotivoBean(
						
						// ID
						id,

						// Descri��o
						descricao,

						// C�digo Motivo
						codigoMotivo,

						// Descri��o do Negativador
						negativador,
						
						// Indicador de Registro Aceito
						indicadorRegistroAceito,
						
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

		parametros.put("negativador", negativadorRetornoMotivoParametros.getNegativador().getId() == -1 ? ""
				: "" + negativadorRetornoMotivoParametros.getNegativador().getCliente().getNome());	

		parametros.put("descricao", negativadorRetornoMotivoParametros.getDescricaoRetornocodigo());

		parametros.put("codigoMotivo", negativadorRetornoMotivoParametros.getCodigoRetornoMotivo());
		
		parametros.put("indicadorRegistroAceito", negativadorRetornoMotivoParametros.getIndicadorRegistroAceito());
	
		parametros.put("indicadorUso", negativadorRetornoMotivoParametros.getIndicadorUso());

	

		// cria uma inst�ncia do dataSource do relat�rio

		RelatorioDataSource ds = new RelatorioDataSource((List) relatorioBeans);

		retorno = this.gerarRelatorio(
				ConstantesRelatorios.RELATORIO_MANTER_NEGATIVADOR_RETORNO_MOTIVO, parametros, ds,
				tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relat�rio no sistema
		try {
			persistirRelatorioConcluido(retorno, Relatorio.MANTER_NEGATIVADOR_RETORNO_MOTIVO,
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
				(FiltroNegativadorRetornoMotivo) getParametro("filtroNegativadorRetornoMotivo"),
				NegativadorRetornoMotivo.class.getName());

		return retorno;
	}

	public void agendarTarefaBatch() {
		AgendadorTarefas.agendarTarefa("RelatorioManterNegativadorRetornoMotivo", this);
	}

}
