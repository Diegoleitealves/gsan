package gcom.relatorio.micromedicao.hidrometro;

import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.micromedicao.hidrometro.FiltroRetornoControleHidrometro;
import gcom.micromedicao.hidrometro.RetornoControleHidrometro;
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
import java.util.List;
import java.util.Map;


public class RelatorioManterRetornoControleHidrometro extends TarefaRelatorio {
	private static final long serialVersionUID = 1L;
	public RelatorioManterRetornoControleHidrometro(Usuario usuario) {
		super(usuario, ConstantesRelatorios.RELATORIO_MANTER_RETORNO_CONTROLE_HIDROMETRO);
	}
	
	@Deprecated
	public RelatorioManterRetornoControleHidrometro() {
		super(null, "");
	}

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param localidades
	 *            Description of the Parameter
	 * @param localidadeParametros
	 *            Description of the Parameter
	 * @return Descri��o do retorno
	 * @exception RelatorioVazioException
	 *                Descri��o da exce��o
	 */

	public Object executar() throws TarefaException {

		// ------------------------------------
//		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		FiltroRetornoControleHidrometro filtroRetornoControleHidrometro = (FiltroRetornoControleHidrometro) getParametro("filtroRetornoControleHidrometro");
		RetornoControleHidrometro retornoControleHidrometroParametros = (RetornoControleHidrometro) getParametro("retornoControleHidrometroParametros");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// valor de retorno
		byte[] retorno = null;

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		RelatorioManterRetornoControleHidrometroBean relatorioBean = null;

		Fachada fachada = Fachada.getInstancia();

		filtroRetornoControleHidrometro.setConsultaSemLimites(true);

		Collection<RetornoControleHidrometro> colecaoRetornoControleHidrometros = fachada.pesquisar(filtroRetornoControleHidrometro,
				RetornoControleHidrometro.class.getName());

		// se a cole��o de par�metros da analise n�o for vazia
		if (colecaoRetornoControleHidrometros != null && !colecaoRetornoControleHidrometros.isEmpty()) {

			// la�o para criar a cole��o de par�metros da analise
			for (RetornoControleHidrometro retornoControleHidrometro : colecaoRetornoControleHidrometros) {

				String ativoInativo = "";

				if ( retornoControleHidrometro.getIndicadorUso().equals( ConstantesSistema.INDICADOR_USO_ATIVO ) ){
					ativoInativo = "Ativo";
				} else if ( retornoControleHidrometro.getIndicadorUso().equals( ConstantesSistema.INDICADOR_USO_DESATIVO ) ){
					ativoInativo = "Inativo";
				} else {
					ativoInativo = "Todos";
				}
				
				String indicadorGeracao = "";
				if ( retornoControleHidrometro.getIndicadorGeracao().equals( ConstantesSistema.INDICADOR_USO_ATIVO ) ){
					indicadorGeracao = "Sim";
				} else if ( retornoControleHidrometro.getIndicadorGeracao().equals( ConstantesSistema.INDICADOR_USO_DESATIVO ) ){
					indicadorGeracao = "N�o";
				}
				
				relatorioBean = new RelatorioManterRetornoControleHidrometroBean(
						// C�digo
						retornoControleHidrometro.getId().toString(),					 
						
						// Descri��o
						retornoControleHidrometro.getDescricao(),					
						
						
						// Indicador de Gera��o
						indicadorGeracao,
						
						// Indicador de Uso
						ativoInativo);

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

		if (retornoControleHidrometroParametros.getId() != null) {
			parametros.put("id",
					retornoControleHidrometroParametros.getId().toString());
		} else {
			parametros.put("id", "");
		}

		if ( retornoControleHidrometroParametros.getDescricao() != null &&
				!retornoControleHidrometroParametros.getDescricao().equals("") ) {
			
			parametros.put("descricao", retornoControleHidrometroParametros.getDescricao());
		} else {
			parametros.put("descricao", "" );
		}
		
		if ( retornoControleHidrometroParametros.getIndicadorGeracao() != null &&
				!retornoControleHidrometroParametros.getIndicadorGeracao().equals("") ) {
			parametros.put("indicadorDeGeracao", retornoControleHidrometroParametros.getIndicadorGeracao().toString());
		} else {
			parametros.put("indicadorDeGeracao", "");
		}
		
		String indicadorUso = "";

		if (retornoControleHidrometroParametros.getIndicadorUso() != null
				&& !retornoControleHidrometroParametros.getIndicadorUso().equals("")) {
			if (retornoControleHidrometroParametros.getIndicadorUso().equals(new Short("1"))) {
				indicadorUso = "Ativo";
			} else if (retornoControleHidrometroParametros.getIndicadorUso().equals(
					new Short("2"))) {
				indicadorUso = "Inativo";
			} else if (retornoControleHidrometroParametros.getIndicadorUso().equals("3")) {
				indicadorUso = "Todos";
			}


		}
		parametros.put("indicadorUso", indicadorUso);
		
		String indicadorGeracao = "";

		if (retornoControleHidrometroParametros.getIndicadorGeracao() != null
				&& !retornoControleHidrometroParametros.getIndicadorGeracao().equals("")) {
			if (retornoControleHidrometroParametros.getIndicadorGeracao().equals(new Short("1"))) {
				indicadorGeracao = "Sim";
			} else if (retornoControleHidrometroParametros.getIndicadorGeracao().equals(
					new Short("2"))) {
				indicadorUso = "N�o";
			} 
		}


		parametros.put("indicadorGeracao", indicadorGeracao);

		// cria uma inst�ncia do dataSource do relat�rio

		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = this.gerarRelatorio(
				ConstantesRelatorios.RELATORIO_MANTER_RETORNO_CONTROLE_HIDROMETRO, parametros,
				ds, tipoFormatoRelatorio);
		
		return retorno;
	}

	@Override
	public int calcularTotalRegistrosRelatorio() {
		int retorno = 1;

		return retorno;
	}

	public void agendarTarefaBatch() {
		AgendadorTarefas.agendarTarefa("RelatorioManterRetornoControleHidrometro", this);
	}

}
