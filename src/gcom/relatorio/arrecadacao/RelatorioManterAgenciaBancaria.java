package gcom.relatorio.arrecadacao;

import gcom.arrecadacao.banco.Agencia;
import gcom.arrecadacao.banco.FiltroAgencia;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.RelatorioVazioException;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
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
 * @author Fernando Fontelles
 * @version 1.0
 */

public class RelatorioManterAgenciaBancaria extends TarefaRelatorio {
	
	private static final long serialVersionUID = 1L;
	
	public RelatorioManterAgenciaBancaria(Usuario usuario) {
		super(usuario, ConstantesRelatorios.RELATORIO_MANTER_AGENCIA_BANCARIA);
	}
	
	@Deprecated
	public RelatorioManterAgenciaBancaria() {
		super(null, "");
	}

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param situacao agenciaBancaria
	 *            Description of the Parameter
	 * @param agenciaBancariaParametros
	 *            Description of the Parameter
	 * @return Descri��o do retorno
	 * @exception RelatorioVazioException
	 *                Descri��o da exce��o
	 */

	public Object executar() throws TarefaException {

		FiltroAgencia filtroAgenciaBancaria = (FiltroAgencia) getParametro("filtroAgenciaBancaria");
		
		Agencia agenciaBancariaParametros = (Agencia) getParametro("agenciaBancariaParametros");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");
		
		// valor de retorno
		byte[] retorno = null;

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		RelatorioManterAgenciaBancariaBean relatorioBean = null;

		Fachada fachada = Fachada.getInstancia();

		Collection colecaoAgenciaBancaria = fachada.pesquisar(filtroAgenciaBancaria,
				Agencia.class.getName());

		// se a cole��o de par�metros da analise n�o for vazia
		if (colecaoAgenciaBancaria != null && !colecaoAgenciaBancaria.isEmpty()) {

			// coloca a cole��o de par�metros da analise no iterator
			Iterator agenciaBancariaIterator = colecaoAgenciaBancaria.iterator();

			// la�o para criar a cole��o de par�metros da analise
			while (agenciaBancariaIterator.hasNext()) {

				Agencia agenciaBancaria = (Agencia) agenciaBancariaIterator.next();
				
				relatorioBean = new RelatorioManterAgenciaBancariaBean(
						
						//Cod. Banco
						
						agenciaBancaria.getBanco().getId(),
						
						//Cod. Agencia
						
						agenciaBancaria.getCodigoAgencia(),
						
						//Nome Agencia
						
						agenciaBancaria.getNomeAgencia() );
						
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

		//Banco
		if(agenciaBancariaParametros.getBanco() != null
				&& !agenciaBancariaParametros.getBanco().equals("")){
			parametros.put("banco", agenciaBancariaParametros.getBanco().getDescricao());
		}
		//Codigo Agencia
		if(agenciaBancariaParametros.getCodigoAgencia() != null 
				&& !agenciaBancariaParametros.getCodigoAgencia().equals("")){
			
			parametros.put("codigoAgencia",
				agenciaBancariaParametros.getCodigoAgencia() == null ? "" : ""
						+ agenciaBancariaParametros.getCodigoAgencia());
		}
		//Nome Agencia
		if( agenciaBancariaParametros.getNomeAgencia() != null
				&& !agenciaBancariaParametros.getNomeAgencia().equals("")){
			parametros.put("nomeAgencia", agenciaBancariaParametros.getNomeAgencia());
		}
		// cria uma inst�ncia do dataSource do relat�rio

		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = this.gerarRelatorio(
				ConstantesRelatorios.RELATORIO_MANTER_AGENCIA_BANCARIA, parametros,
				ds, tipoFormatoRelatorio);
		
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
		
		AgendadorTarefas.agendarTarefa("RelatorioManterAgenciaBancaria", this);
	
	}

}
