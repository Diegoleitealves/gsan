package gcom.relatorio.cobranca.spcserasa;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.FiltroNegativadorRegistroTipo;
import gcom.cobranca.NegativadorRegistroTipo;
import gcom.fachada.Fachada;
import gcom.relatorio.ConstantesExecucaoRelatorios;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.RelatorioVazioException;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
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

public class RelatorioManterNegativadorRegistroTipo extends TarefaRelatorio {
	private static final long serialVersionUID = 1L;
	/**
	 * Constructor for the RelatorioManterNegativadorRegistroTipo object
	 */
	public RelatorioManterNegativadorRegistroTipo(Usuario usuario) {
		super(usuario, ConstantesRelatorios.RELATORIO_MANTER_NEGATIVADOR_REGISTRO_TIPO);
	}

	@Deprecated
	public RelatorioManterNegativadorRegistroTipo() {
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
		FiltroNegativadorRegistroTipo filtroNegativadorRegistroTipo = (FiltroNegativadorRegistroTipo) getParametro("filtroNegativadorRegistroTipo");
		NegativadorRegistroTipo negativadorRegistroTipoParametros = (NegativadorRegistroTipo) getParametro("negativadorRegistroTipoParametros");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// valor de retorno
		byte[] retorno = null;

		// cole��o de beans do relat�rio

		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		RelatorioManterNegativadorRegistroTipoBean relatorioBean = null;

		// Cria adiciona os carregamentos dos objetos necess�rios para
		// a impress�o do relat�rio
		filtroNegativadorRegistroTipo.adicionarCaminhoParaCarregamentoEntidade("negativador.cliente");
		filtroNegativadorRegistroTipo.setConsultaSemLimites(true);

		// Nova consulta para trazer objeto completo
		Collection<NegativadorRegistroTipo> colecaoNegativadorRegistroTiposNovas = fachada.pesquisar(filtroNegativadorRegistroTipo, NegativadorRegistroTipo.class
				.getName());

		if (colecaoNegativadorRegistroTiposNovas != null && !colecaoNegativadorRegistroTiposNovas.isEmpty()) {
			// coloca a cole��o de par�metros da analise no iterator
			for (NegativadorRegistroTipo negativadorRegistroTipo : colecaoNegativadorRegistroTiposNovas) {
				
				// Faz as valida��es dos campos necess�riose e formata a String
				// para a forma como ir� aparecer no relat�rio
				
				// ID
				String id = "";
				
				if (negativadorRegistroTipo.getId() != null) {
					id = negativadorRegistroTipo.getId().toString();
				}
				
				// Descri��o
				String descricao = "";
				
				if (negativadorRegistroTipo.getDescricaoRegistroTipo() != null
						&& !negativadorRegistroTipo.getDescricaoRegistroTipo()
								.trim().equals("")) {
					descricao = negativadorRegistroTipo.getDescricaoRegistroTipo();
				}
				
				// codigoRegistro
				String codigoRegistro = "";
				
				if (negativadorRegistroTipo.getCodigoRegistro() != null
						&& !negativadorRegistroTipo.getCodigoRegistro()
								.trim().equals("")) {
					codigoRegistro = negativadorRegistroTipo.getCodigoRegistro();
				}

				
				// negativador
				String negativador = "";				
				if (negativadorRegistroTipo.getNegativador().getId() != null) {
					negativador = negativadorRegistroTipo.getNegativador().getCliente().getNome();
				}
				
				

				// Inicializa o construtor constitu�do dos campos
				// necess�rios para a impress�o do relatorio
				relatorioBean = new RelatorioManterNegativadorRegistroTipoBean(
						
						// ID
						id,

						// Descri��o
						descricao,

						// C�digo Registro
						codigoRegistro,

						// Descri��o do Negativador
						negativador);

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

		parametros.put("negativador", negativadorRegistroTipoParametros.getNegativador().getId() == -1 ? ""
				: "" + negativadorRegistroTipoParametros.getNegativador().getCliente().getNome());	

		parametros.put("descricao", negativadorRegistroTipoParametros.getDescricaoRegistroTipo());

		parametros.put("codigoTipoRegistro", negativadorRegistroTipoParametros.getCodigoRegistro());

	

		// cria uma inst�ncia do dataSource do relat�rio

		RelatorioDataSource ds = new RelatorioDataSource((List) relatorioBeans);

		retorno = this.gerarRelatorio(
				ConstantesRelatorios.RELATORIO_MANTER_NEGATIVADOR_REGISTRO_TIPO, parametros, ds,
				tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relat�rio no sistema
		try {
			persistirRelatorioConcluido(retorno, Relatorio.MANTER_NEGATIVADOR_REGISTRO_TIPO,
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
//				(FiltroNegativadorRegistroTipo) getParametro("filtroNegativadorRegistroTipo"),
//				NegativadorRegistroTipo.class.getName());

		return retorno;
	}

	public void agendarTarefaBatch() {
		AgendadorTarefas.agendarTarefa("RelatorioManterNegativadorRegistroTipo", this);
	}

}
