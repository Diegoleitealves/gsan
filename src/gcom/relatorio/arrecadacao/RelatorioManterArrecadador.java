package gcom.relatorio.arrecadacao;

import gcom.arrecadacao.Arrecadador;
import gcom.arrecadacao.FiltroArrecadador;
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

public class RelatorioManterArrecadador extends TarefaRelatorio {
	
	private static final long serialVersionUID = 1L;
	
	public RelatorioManterArrecadador(Usuario usuario) {
		super(usuario, ConstantesRelatorios.RELATORIO_MANTER_ARRECADADOR);
	}
	
	@Deprecated
	public RelatorioManterArrecadador() {
		super(null, "");
	}

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param situacao arrecadador
	 *            Description of the Parameter
	 * @param arrecadadorParametros
	 *            Description of the Parameter
	 * @return Descri��o do retorno
	 * @exception RelatorioVazioException
	 *                Descri��o da exce��o
	 */

	public Object executar() throws TarefaException {

		FiltroArrecadador filtroArrecadador = (FiltroArrecadador) getParametro("filtroArrecadador");
		
		Arrecadador arrecadadorParametros = (Arrecadador) getParametro("arrecadadorParametros");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");
		
		// valor de retorno
		byte[] retorno = null;

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		RelatorioManterArrecadadorBean relatorioBean = null;

		Fachada fachada = Fachada.getInstancia();

		Collection colecaoArrecadador = fachada.pesquisar(filtroArrecadador,
				Arrecadador.class.getName());

		
		
		// se a cole��o de par�metros da analise n�o for vazia
		if (colecaoArrecadador != null && !colecaoArrecadador.isEmpty()) {

			// coloca a cole��o de par�metros da analise no iterator
			Iterator arrecadadorIterator = colecaoArrecadador.iterator();

			// la�o para criar a cole��o de par�metros da analise
			while (arrecadadorIterator.hasNext()) {

				Arrecadador arrecadador = (Arrecadador) arrecadadorIterator.next();
				
				String codigoAgente = "";
				if(arrecadador.getCodigoAgente()!= null 
						&& !arrecadador.getCodigoAgente().equals("")){
					
					codigoAgente = arrecadador.getCodigoAgente() + "";
					
				}
					
				String idCliente = "";
				if(arrecadador.getCliente().getId() != null
						&& !arrecadador.getCliente().getId().equals("")){
					
					idCliente = arrecadador.getCliente().getId() + "";
					
				}
				
				String nomeCliente = "";
				if(arrecadador.getCliente().getNome() != null
						&& !arrecadador.getCliente().getNome().equals("")){
					
					nomeCliente = arrecadador.getCliente().getNome();
					
				}
				
				String idImovel = "";
				String inscricaoEstadual = "";
				if(arrecadador.getImovel() != null
						&& !arrecadador.getImovel().equals("")){
					
					idImovel = arrecadador.getImovel().getId()+"";
					inscricaoEstadual = arrecadador.getImovel().getInscricaoFormatada() + "";
					
				}
				
				
//				String inscricaoEstadual = "";
//				if(arrecadador.getImovel().getInscricaoFormatada() != null
//						&& !arrecadador.getImovel().getInscricaoFormatada().equals("")){
//					
//					inscricaoEstadual = arrecadador.getImovel().getInscricaoFormatada() + "";
//					
//				}
				
				relatorioBean = new RelatorioManterArrecadadorBean(
						
						//Agente
						
						codigoAgente,
						
						//ID Cliente
						
						idCliente,
						
						//Nome cliente
						
						nomeCliente,
						
						//Im�vel
						
						idImovel,
						
						//Inscri��o Estadual
						
						inscricaoEstadual );
						
						
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

		//C�digo do Agente
		if ( arrecadadorParametros.getCodigoAgente() != null
				&& arrecadadorParametros.getCodigoAgente().equals("")){
			parametros.put("codigoAgente", arrecadadorParametros.getCodigoAgente());
		}
		
		//Cliente
		if( arrecadadorParametros.getCliente() != null
				&& !arrecadadorParametros.getCliente().equals("")){
			
			parametros.put("cliente",
				arrecadadorParametros.getCliente().getId());
		
		}
		
		//Im�vel Id e Inscri��o Estadual
		if( arrecadadorParametros.getImovel() != null
				&& !arrecadadorParametros.getImovel().equals("")){
			if ( arrecadadorParametros.getImovel().getId() != null
					&& !arrecadadorParametros.getImovel().getId().equals("")){
				
				parametros.put("imovel", arrecadadorParametros.getImovel().getId());
			
			}
			
			if ( arrecadadorParametros.getImovel().getInscricaoFormatada() != null
					&& !arrecadadorParametros.getImovel().getInscricaoFormatada().equals("")){
				
				parametros.put("inscricaoEstadual", new String( arrecadadorParametros.getImovel().getInscricaoFormatada()));
				
			}
		}
		
		//Indicador de Uso
		
		parametros.put("indicadorUso", new String(arrecadadorParametros.getIndicadorUso().toString()));
		
		// cria uma inst�ncia do dataSource do relat�rio

		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = this.gerarRelatorio(
				ConstantesRelatorios.RELATORIO_MANTER_ARRECADADOR, parametros,
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
		
		AgendadorTarefas.agendarTarefa("RelatorioManterArrecadador", this);
	
	}

}
