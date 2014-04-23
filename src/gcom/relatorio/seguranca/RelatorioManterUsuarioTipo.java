package gcom.relatorio.seguranca;

import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.RelatorioVazioException;
import gcom.seguranca.acesso.usuario.FiltroUsuarioTipo;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioTipo;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Title: GCOM
 * </p>
 * <p>
 * Description: Sistema de Gest�o Comercial
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: COMPESA - Companhia Pernambucana de Saneamento
 * </p>
 * 
 * @author Arthur Carvalho
 * @version 1.0
 */

public class RelatorioManterUsuarioTipo extends TarefaRelatorio {
	private static final long serialVersionUID = 1L;
	public RelatorioManterUsuarioTipo(Usuario usuario) {
		super(usuario, ConstantesRelatorios.RELATORIO_USUARIO_TIPO_MANTER);
	}
	
	@Deprecated
	public RelatorioManterUsuarioTipo() {
		super(null, "");
	}

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param situacao pagamento
	 *            Description of the Parameter
	 * @param SituacaoPagamentoParametros
	 *            Description of the Parameter
	 * @return Descri��o do retorno
	 * @exception RelatorioVazioException
	 *                Descri��o da exce��o
	 */

	public Object executar() throws TarefaException {

		// ------------------------------------
		//		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		FiltroUsuarioTipo filtroUsuarioTipo = (FiltroUsuarioTipo) getParametro("filtroUsuarioTipo");
		UsuarioTipo usuarioTipoParametros = (UsuarioTipo) getParametro("usuarioTipoParametros");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		
		// valor de retorno
		byte[] retorno = null;

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		RelatorioManterUsuarioTipoBean relatorioBean = null;

		Fachada fachada = Fachada.getInstancia();

		Collection colecaoUsuarioTipo = fachada.pesquisar(filtroUsuarioTipo,
				UsuarioTipo.class.getName());

		// se a cole��o de par�metros da analise n�o for vazia
		if (colecaoUsuarioTipo != null && !colecaoUsuarioTipo.isEmpty()) {

			// coloca a cole��o de par�metros da analise no iterator
			Iterator usuarioTipoIterator = colecaoUsuarioTipo.iterator();

			// la�o para criar a cole��o de par�metros da analise
			while (usuarioTipoIterator.hasNext()) {

				UsuarioTipo usuarioTipo = (UsuarioTipo) usuarioTipoIterator.next();

				
				String indicadorFuncionario = "";
				
				if(usuarioTipo.getIndicadorFuncionario().equals(ConstantesSistema.SIM)){
					indicadorFuncionario = "SIM";
				} else {
					indicadorFuncionario = "N�O";
				}
				
				String indicadorUso = "";
				
				if(usuarioTipo.getIndicadorUso().equals(ConstantesSistema.SIM)){
					indicadorUso = "ATIVO";
				} else {
					indicadorUso = "INATIVO";
				}
				
				relatorioBean = new RelatorioManterUsuarioTipoBean(
						// CODIGO
						usuarioTipo.getId().toString(), 
						
						// Descri��o
						usuarioTipo.getDescricao(), 
						
						//Indicador de Uso
						indicadorUso,
						
						//Indicador de Calcular Volume Fixo de Esgoto
						indicadorFuncionario);
						
						
						
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

		parametros.put("id",
				usuarioTipoParametros.getId() == null ? "" : ""
						+ usuarioTipoParametros.getId());
		
		
		if (usuarioTipoParametros.getDescricao() != null){
			parametros.put("descricao", usuarioTipoParametros.getDescricao());
		} else {
			parametros.put("descricao", "");
		}
		
		String indicadorUso = "";

		if (usuarioTipoParametros.getIndicadorUso() != null
				&& !usuarioTipoParametros.getIndicadorUso().equals("")) {
			if (usuarioTipoParametros.getIndicadorUso().equals(new Short("1"))) {
				indicadorUso = "Ativo";
			} else if (usuarioTipoParametros.getIndicadorUso().equals(
					new Short("2"))) {
				indicadorUso = "Inativo";
			}
		}

		parametros.put("indicadorUso", indicadorUso);
		
		String indicadorFuncionario = "";

		if (usuarioTipoParametros.getIndicadorFuncionario() != null && !usuarioTipoParametros.getIndicadorFuncionario().equals("")) {
			if (usuarioTipoParametros.getIndicadorFuncionario().equals(new Short("1"))) {
				indicadorFuncionario = "Sim";
			} else if (usuarioTipoParametros.getIndicadorFuncionario().equals(
					new Short("2"))) {
				indicadorFuncionario = "N�o";
			}
		}

		parametros.put("indicadorFuncionario", indicadorFuncionario);
		
		// cria uma inst�ncia do dataSource do relat�rio

		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = this.gerarRelatorio(
				ConstantesRelatorios.RELATORIO_USUARIO_TIPO_MANTER, parametros,
				ds, tipoFormatoRelatorio);
		
		// ------------------------------------
		// Grava o relat�rio no sistema
//		try {
//			persistirRelatorioConcluido(retorno, Relatorio.MANTER_LOCALIDADE,
//					idFuncionalidadeIniciada);
//		} catch (ControladorException e) {
//			e.printStackTrace();
//			throw new TarefaException("Erro ao gravar relat�rio no sistema", e);
//		}
		// ------------------------------------

		// retorna o relat�rio gerado
		return retorno;
	}

	@Override
	public int calcularTotalRegistrosRelatorio() {
		int retorno = 0;

//		retorno = Fachada.getInstancia().totalRegistrosPesquisa(
//				(FiltroLocalidade) getParametro("filtroLocalidade"),
//				Localidade.class.getName());

		return retorno;
	}

	public void agendarTarefaBatch() {
		AgendadorTarefas.agendarTarefa("RelatorioManterUsuarioTipo", this);
	}

}
