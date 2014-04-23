package gcom.relatorio.micromedicao;

import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.micromedicao.FiltroItemServico;
import gcom.micromedicao.ItemServico;
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
 * @author Rodrigo Cabral
 * @version 1.0
 */

public class RelatorioManterItemServico extends TarefaRelatorio {
	private static final long serialVersionUID = 1L;
	public RelatorioManterItemServico(Usuario usuario) {
		super(usuario, ConstantesRelatorios.RELATORIO_MANTER_ITEM_SERVICO);
	}
	
	@Deprecated
	public RelatorioManterItemServico() {
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

		FiltroItemServico filtroItemServico = (FiltroItemServico) 
							getParametro("filtroItemServico");
		ItemServico itemServicoParametros = (ItemServico) getParametro("itemServicoParametros");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// valor de retorno
		byte[] retorno = null;

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		RelatorioManterItemServicoBean relatorioBean = null;

		Fachada fachada = Fachada.getInstancia();

		Collection colecaoItemServico = fachada.pesquisar(filtroItemServico,
				ItemServico.class.getName());

		// se a cole��o de par�metros da analise n�o for vazia
		if (colecaoItemServico != null && !colecaoItemServico.isEmpty()) {

			// coloca a cole��o de par�metros da analise no iterator
			Iterator itemServicoIterator = colecaoItemServico.iterator();

			// la�o para criar a cole��o de par�metros da analise
			while (itemServicoIterator.hasNext()) {

				ItemServico itemServico = (ItemServico) itemServicoIterator.next();
				
				String codigo = "" + itemServico.getCodigoItem();
				
				String codigoConstanteCalculo = "" ;
				if ( itemServico.getCodigoConstanteCalculo() != null ) {
					codigoConstanteCalculo = itemServico.getCodigoConstanteCalculo().toString();
				}
				
				relatorioBean = new RelatorioManterItemServicoBean(
						// Descri��o
						itemServico.getDescricao(),
						
						//Descri��o Abreviada
						itemServico.getDescricaoAbreviada(),
						
						//Indicador de Uso						
						itemServico.getIndicadorUso().toString(),
				
						//C�digo Constante de C�lculo
						codigoConstanteCalculo,

						// CODIGO
						codigo);
				
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
		
		if(itemServicoParametros.getCodigoItem() != null 
				&& itemServicoParametros.getCodigoItem() != 0){
			
			parametros.put("codigoItem",
					""+ itemServicoParametros.getCodigoItem());
			
		}else{
			parametros.put("codigoItem","");
		}
		
		if(itemServicoParametros.getDescricao() != null &&
				!itemServicoParametros.getDescricao().equals("")){
			
			parametros.put("descricao", itemServicoParametros.getDescricao());
			
		}
		
		if(itemServicoParametros.getDescricaoAbreviada() != null &&
				!itemServicoParametros.getDescricaoAbreviada().equals("")){
			
			parametros.put("descricaoAbreviada", itemServicoParametros.getDescricaoAbreviada());
			
		}
		
		if(itemServicoParametros.getCodigoConstanteCalculo() != null &&
				!itemServicoParametros.getCodigoConstanteCalculo().equals("")){
			
			parametros.put("codigoConstanteCalculo", "" +itemServicoParametros.getCodigoConstanteCalculo());
			
		}
		
		parametros.put("tipo", "R1065" );
		
		String indicadorUso = "";

		if (itemServicoParametros.getIndicadorUso() != null
				&& !itemServicoParametros.getIndicadorUso().equals("")) {
			if (itemServicoParametros.getIndicadorUso().equals(new Short("1"))) {
				indicadorUso = "Ativo";
			} else if (itemServicoParametros.getIndicadorUso().equals(
					new Short("2"))) {
				indicadorUso = "Inativo";
			}
			
		}
		
		parametros.put("indicadorUso", indicadorUso);
		
		// cria uma inst�ncia do dataSource do relat�rio

		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = this.gerarRelatorio(
				ConstantesRelatorios.RELATORIO_MANTER_ITEM_SERVICO, parametros,
				ds, tipoFormatoRelatorio);
		
		// retorna o relat�rio gerado
		return retorno;
	}

	@Override
	public int calcularTotalRegistrosRelatorio() {
		int retorno = 1;

		return retorno;
	}

	public void agendarTarefaBatch() {
		AgendadorTarefas.agendarTarefa("RelatorioManterItemServico", this);
	}

}
