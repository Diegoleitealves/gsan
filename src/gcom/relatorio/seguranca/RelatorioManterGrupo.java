package gcom.relatorio.seguranca;

import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.RelatorioVazioException;
import gcom.seguranca.acesso.FiltroGrupo;
import gcom.seguranca.acesso.Grupo;
import gcom.seguranca.acesso.usuario.Usuario;
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

public class RelatorioManterGrupo extends TarefaRelatorio {
	private static final long serialVersionUID = 1L;
	public RelatorioManterGrupo(Usuario usuario) {
		super(usuario, ConstantesRelatorios.RELATORIO_GRUPO_MANTER);
	}
	
	@Deprecated
	public RelatorioManterGrupo() {
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

		//FiltroGrupo filtroGrupo = (FiltroGrupo) getParametro("filtroGrupo");
		Grupo grupoParametros = (Grupo) getParametro("grupoParametros");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");
		
		FiltroGrupo filtroGrupo = new FiltroGrupo();
		filtroGrupo.setCampoOrderBy(FiltroGrupo.DESCRICAO);
		
		// valor de retorno
		byte[] retorno = null;

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		RelatorioManterGrupoBean relatorioBean = null;

		Fachada fachada = Fachada.getInstancia();

		Collection colecaoGrupo = fachada.pesquisar(filtroGrupo,
				Grupo.class.getName());

		// se a cole��o de par�metros da analise n�o for vazia
		if (colecaoGrupo != null && !colecaoGrupo.isEmpty()) {

			// coloca a cole��o de par�metros da analise no iterator
			Iterator grupoIterator = colecaoGrupo.iterator();

			// la�o para criar a cole��o de par�metros da analise
			while (grupoIterator.hasNext()) {

				Grupo grupo = (Grupo) grupoIterator.next();

				
				String indicadorUso = "";
				
				if(grupo.getIndicadorUso().equals(ConstantesSistema.SIM)){
					indicadorUso = "ATIVO";
				} else {
					indicadorUso = "INATIVO";
				}
				
				
				relatorioBean = new RelatorioManterGrupoBean(
						// CODIGO
						grupo.getId().toString(), 
						
						// Descri��o
						grupo.getDescricao(), 
						
						//Descricao Abreviada
						grupo.getDescricaoAbreviada(),
						
						//Indicador de Uso
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

		parametros.put("id",
				grupoParametros.getId() == null ? "" : ""
						+ grupoParametros.getId());
		
		
		if (grupoParametros.getDescricao() != null){
			parametros.put("descricao", grupoParametros.getDescricao());
		} else {
			parametros.put("descricao", "");
		}
		
		if (grupoParametros.getDescricaoAbreviada() != null) {
			parametros.put("descricaoAbreviada", grupoParametros.getDescricaoAbreviada());
		} else {
			parametros.put("descricaoAbreviada", "");	
		}
		
				
		String indicadorUso = "";

		if (grupoParametros.getIndicadorUso() != null && !grupoParametros.getIndicadorUso().equals("")) {
			if (grupoParametros.getIndicadorUso().equals(new Short("1"))) {
				indicadorUso = "Ativo";
			} else if (grupoParametros.getIndicadorUso().equals(new Short("2"))) {
				indicadorUso = "Inativo";
			}
		}

		parametros.put("indicadorUso", indicadorUso);
		
		// cria uma inst�ncia do dataSource do relat�rio

		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = this.gerarRelatorio(
				ConstantesRelatorios.RELATORIO_GRUPO_MANTER, parametros,
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
		AgendadorTarefas.agendarTarefa("RelatorioManterGrupo", this);
	}

}
