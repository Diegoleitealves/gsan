package gcom.relatorio.cadastro;

import gcom.cadastro.empresa.Empresa;
import gcom.cadastro.empresa.FiltroEmpresa;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.RelatorioVazioException;
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

public class RelatorioManterEmpresa extends TarefaRelatorio {
	private static final long serialVersionUID = 1L;
	public RelatorioManterEmpresa(Usuario usuario) {
		super(usuario, ConstantesRelatorios.RELATORIO_EMPRESA_MANTER);
	}
	
	@Deprecated
	public RelatorioManterEmpresa() {
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

		FiltroEmpresa filtroEmpresa = (FiltroEmpresa) getParametro("filtroEmpresa");
		Empresa empresaParametros = (Empresa) getParametro("empresaParametros");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		
		// valor de retorno
		byte[] retorno = null;

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		RelatorioManterEmpresaBean relatorioBean = null;

		Fachada fachada = Fachada.getInstancia();

		Collection colecaoEmpresa = fachada.pesquisar(filtroEmpresa,
				Empresa.class.getName());

		// se a cole��o de par�metros da analise n�o for vazia
		if (colecaoEmpresa != null && !colecaoEmpresa.isEmpty()) {

			// coloca a cole��o de par�metros da analise no iterator
			Iterator empresaIterator = colecaoEmpresa.iterator();

			// la�o para criar a cole��o de par�metros da analise
			while (empresaIterator.hasNext()) {

				Empresa empresa = (Empresa) empresaIterator.next();

				String indicadorEmpresaPrincipal = "";
				
				if(empresa.getIndicadorEmpresaPrincipal().equals(ConstantesSistema.SIM)){
					indicadorEmpresaPrincipal = "SIM";
				} else {
					indicadorEmpresaPrincipal = "N�O";
				}
				
				relatorioBean = new RelatorioManterEmpresaBean(
						// ID
						empresa.getId().toString(), 
						
						// Descri��o
						empresa.getDescricao(), 
						
						// Indicador Empresa Principal
						indicadorEmpresaPrincipal,
						
						empresa.getIndicadorUso().toString());
						
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
				empresaParametros.getId() == null ? "" : ""
						+ empresaParametros.getId());

		parametros.put("descricao", empresaParametros.getDescricao());
		
		String indicadorEmpresaPrincipal= "";

		if (empresaParametros.getIndicadorEmpresaPrincipal() != null
				&& !empresaParametros.getIndicadorEmpresaPrincipal().equals("")) {
			if (empresaParametros.getIndicadorEmpresaPrincipal().equals(new Short("1"))) {
				indicadorEmpresaPrincipal = "Sim";
			} else if (empresaParametros.getIndicadorEmpresaPrincipal().equals(
					new Short("2"))) {
				indicadorEmpresaPrincipal = "N�o";
			}
		}

		parametros.put("indicadorEmpresaPrincipal", indicadorEmpresaPrincipal);

		String indicadorUso = "";

		if (empresaParametros.getIndicadorUso() != null
				&& !empresaParametros.getIndicadorUso().equals("")) {
			if (empresaParametros.getIndicadorUso().equals(new Short("1"))) {
				indicadorUso = "Ativo";
			} else if (empresaParametros.getIndicadorUso().equals(
					new Short("2"))) {
				indicadorUso = "Inativo";
			}
		}

		parametros.put("indicadorUso", indicadorUso);
		// cria uma inst�ncia do dataSource do relat�rio

		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = this.gerarRelatorio(
				ConstantesRelatorios.RELATORIO_EMPRESA_MANTER, parametros,
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
		AgendadorTarefas.agendarTarefa("RelatorioManterEmpresa", this);
	}

}
