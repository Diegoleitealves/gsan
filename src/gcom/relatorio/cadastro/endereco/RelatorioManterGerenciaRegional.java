package gcom.relatorio.cadastro.endereco;

import gcom.batch.Relatorio;
import gcom.cadastro.localidade.FiltroGerenciaRegional;
import gcom.cadastro.localidade.GerenciaRegional;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.cadastro.localidade.RelatorioManterGerenciaRegionalBean;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.Util;
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
 * @author not attributable
 * @version 1.0
 */

public class RelatorioManterGerenciaRegional extends TarefaRelatorio {
	private static final long serialVersionUID = 1L;
	public RelatorioManterGerenciaRegional(Usuario usuario) {
		super(usuario, ConstantesRelatorios.RELATORIO_LOGRADOURO_MANTER);
	}

	@Deprecated
	public RelatorioManterGerenciaRegional() {
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
		FiltroGerenciaRegional filtroGerenciaRegional = (FiltroGerenciaRegional) getParametro("filtroGerenciaRegional");
		GerenciaRegional gerenciaRegionalParametros = (GerenciaRegional) getParametro("gerenciaRegionalParametros");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");
						
		
		// valor de retorno
		byte[] retorno = null;

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();
		Collection gerenciasRegionais = (Collection) fachada.pesquisar(filtroGerenciaRegional,
				GerenciaRegional.class.getName());

		RelatorioManterGerenciaRegionalBean relatorioBean = null;

		// se a cole��o de par�metros da analise n�o for vazia
		if (gerenciasRegionais != null && !gerenciasRegionais.isEmpty()) {
			// coloca a cole��o de par�metros da analise no iterator
			Iterator gerenciaRegionalIterator = gerenciasRegionais.iterator();

			// la�o para criar a cole��o de par�metros da analise
			while (gerenciaRegionalIterator.hasNext()) {

				GerenciaRegional gerenciaRegional = (GerenciaRegional) gerenciaRegionalIterator.next();			

				relatorioBean = new RelatorioManterGerenciaRegionalBean(
						// C�digo
						gerenciaRegional.getId().toString(),

						// Nome
						gerenciaRegional.getNome(),

						// Nome Abreviado
						gerenciaRegional.getNomeAbreviado(),

						// Indicador Uso
						gerenciaRegional.getIndicadorUso() == null? "" : gerenciaRegional.getIndicadorUso().toString());

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

		parametros.put("codigo", gerenciaRegionalParametros.getId() == null ? "" : ""	+ gerenciaRegionalParametros.getId());

		parametros.put("nome", gerenciaRegionalParametros.getNome());

		parametros.put("nomeAbreviado", gerenciaRegionalParametros.getNomeAbreviado());
		
		String cnpj = null;
		if(gerenciaRegionalParametros.getCnpjGerenciaRegional() != null && !gerenciaRegionalParametros.getCnpjGerenciaRegional().equals("")){
			cnpj = Util.formatarCnpj(gerenciaRegionalParametros.getCnpjGerenciaRegional());
		}
		
		parametros.put("cnpj", cnpj);
			
		String indicadorUso = "";

		if (gerenciaRegionalParametros.getIndicadorUso() != null
				&& !gerenciaRegionalParametros.getIndicadorUso().equals("")) {
			if (gerenciaRegionalParametros.getIndicadorUso().equals(new Short("1"))) {
				indicadorUso = "Ativos";
			} else {
				if (gerenciaRegionalParametros.getIndicadorUso().equals(new Short("2"))) {
					indicadorUso = "Inativos";
				}else{
					indicadorUso = "Todos";
				}
			}
		}

		parametros.put("indicadorUso", indicadorUso);
		
		parametros.put("tipo", "R0016");

		// cria uma inst�ncia do dataSource do relat�rio

		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = this.gerarRelatorio(
				ConstantesRelatorios.RELATORIO_GERENCIA_REGIONAL_MANTER, parametros,
				ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relat�rio no sistema
		try {
			persistirRelatorioConcluido(retorno, Relatorio.MANTER_GERENCIA_REGIONAL,
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

//		if (getParametro("logradouros") != null
//				&& getParametro("logradouros") instanceof Collection) {
//			retorno = ((Collection) getParametro("logradouros")).size();
//		}

		return retorno;
	}

	public void agendarTarefaBatch() {
		AgendadorTarefas.agendarTarefa("RelatorioManterGerenciaRegional", this);
	}

}
