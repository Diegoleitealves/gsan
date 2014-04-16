package gcom.relatorio.faturamento;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.faturamento.bean.EmitirHistogramaEsgotoEconomiaDetalheHelper;
import gcom.faturamento.bean.EmitirHistogramaEsgotoEconomiaHelper;
import gcom.faturamento.bean.FiltrarEmitirHistogramaEsgotoEconomiaHelper;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.RelatorioVazioException;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * classe respons�vel por criar o relat�rio de histograma de liga��o de esgoto por economia
 * 
 * @author Rafael Pinto
 * @created 07/11/2007
 */
public class RelatorioHistogramaEsgotoEconomia extends TarefaRelatorio {
	
	private static final long serialVersionUID = 1L;
	
	public RelatorioHistogramaEsgotoEconomia(Usuario usuario) {
		super(usuario, ConstantesRelatorios.RELATORIO_EMITIR_HISTOGRAMA_ESGOTO_ECONOMIA);
	}

	@Deprecated
	public RelatorioHistogramaEsgotoEconomia() {
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

		// valor de retorno
		byte[] retorno = null;

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		FiltrarEmitirHistogramaEsgotoEconomiaHelper filtrarEmitirHistogramaEsgotoEconomiaHelper = 
			(FiltrarEmitirHistogramaEsgotoEconomiaHelper) getParametro("filtrarEmitirHistogramaEsgotoEconomiaHelper");
		
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		RelatorioHistogramaEsgotoEconomiaBean relatorioHistogramaEsgotoEconomiaBean = null;
		
		Collection<EmitirHistogramaEsgotoEconomiaHelper> colecao =  
			fachada.pesquisarEmitirHistogramaEsgotoEconomia(filtrarEmitirHistogramaEsgotoEconomiaHelper);
		
		// se a cole��o de par�metros da analise n�o for vazia
		if (colecao != null && !colecao.isEmpty()) {

			// coloca a cole��o de par�metros da analise no iterator
			Iterator colecaoIterator = colecao.iterator();

			// la�o para criar a cole��o de par�metros da analise
			while (colecaoIterator.hasNext()) {

				EmitirHistogramaEsgotoEconomiaHelper emitirHistogramaEsgotoEconomiaHelper = 
					(EmitirHistogramaEsgotoEconomiaHelper) colecaoIterator.next();
				
				String opcaoTotalizacao = emitirHistogramaEsgotoEconomiaHelper.getOpcaoTotalizacao();
				String descricao = emitirHistogramaEsgotoEconomiaHelper.getDescricaoCategoria();
				
				String descricaoPercentual = emitirHistogramaEsgotoEconomiaHelper.getDescricaoPercentual();
				
				if(!descricaoPercentual.equals("")){
					descricaoPercentual = "AN�LISE DE CONSUMO DE ESGOTO "+descricaoPercentual+" POR ECONOMIA";
				}else{
					descricaoPercentual = "AN�LISE DE CONSUMO DE ESGOTO POR ECONOMIA";
				}
				
				String descricaoTarifa = emitirHistogramaEsgotoEconomiaHelper.getDescricaoTarifa();
				
				Collection colecaoDetalhe = 
					emitirHistogramaEsgotoEconomiaHelper.getColecaoEmitirHistogramaEsgotoEconomiaDetalhe();
				
				String economiasMedido = null;
				String consumoMedioMedido = null;
				String consumoExcedenteMedido = null;
				String volumeConsumoMedido = null;
				String volumeFaturadoMedido = null;
				String receitaMedido = null;
				String ligacoesMedido = null;
				
				String economiasNaoMedido = null;
				String consumoMedioNaoMedido = null;
				String consumoExcedenteNaoMedido = null;
				String volumeConsumoNaoMedido = null;
				String volumeFaturadoNaoMedido = null;
				String receitaNaoMedido = null;
				String ligacoesNaoMedido = null;

				
				NumberFormat formato = NumberFormat.getInstance(new Locale("pt", "BR"));
				
				if (colecaoDetalhe != null && !colecaoDetalhe.isEmpty()) {
				
					Iterator colecaoDetalheIterator = colecaoDetalhe.iterator();
					
					while (colecaoDetalheIterator.hasNext()) {
						
						EmitirHistogramaEsgotoEconomiaDetalheHelper detalhe = 
							(EmitirHistogramaEsgotoEconomiaDetalheHelper) colecaoDetalheIterator.next();
						
						String faixa = detalhe.getDescricaoFaixa();
						
						economiasMedido = formato.format(detalhe.getEconomiasMedido());
						
						if(detalhe.getConsumoMedioMedido() != null){
							consumoMedioMedido = (""+detalhe.getConsumoMedioMedido()).replace(".",",");	
						}
						if(detalhe.getConsumoExcedenteMedido() != null){
							consumoExcedenteMedido = (""+detalhe.getConsumoExcedenteMedido()).replace(".",",");	
						}
						
						volumeConsumoMedido = formato.format(detalhe.getVolumeConsumoFaixaMedido());
						volumeFaturadoMedido = formato.format(detalhe.getVolumeFaturadoFaixaMedido());
						receitaMedido = Util.formatarMoedaReal(detalhe.getReceitaMedido());
						ligacoesMedido = formato.format(detalhe.getLigacoesMedido());
						
						economiasNaoMedido = formato.format(detalhe.getEconomiasNaoMedido());

						if(detalhe.getConsumoMedioNaoMedido() != null){
							consumoMedioNaoMedido = (""+detalhe.getConsumoMedioNaoMedido()).replace(".",",");	
						}
						if(detalhe.getConsumoExcedenteNaoMedido() != null){
							consumoExcedenteNaoMedido = (""+detalhe.getConsumoExcedenteNaoMedido()).replace(".",",");	
						}
						
						volumeConsumoNaoMedido = formato.format(detalhe.getVolumeConsumoFaixaNaoMedido());
						volumeFaturadoNaoMedido = formato.format(detalhe.getVolumeFaturadoFaixaNaoMedido());
						receitaNaoMedido = Util.formatarMoedaReal(detalhe.getReceitaNaoMedido());
						ligacoesNaoMedido = formato.format(detalhe.getLigacoesNaoMedido());
						
						relatorioHistogramaEsgotoEconomiaBean = 
							new RelatorioHistogramaEsgotoEconomiaBean(
								// Op��o de Totaliza��o
								opcaoTotalizacao,
								// Descri��o
								descricao,
								// Descri��o da Faixa
								faixa,
								// N�mero de Economias
								economiasMedido,
								// Consumo Medio para Medido
								consumoMedioMedido,
								// Consumo Excedente para Medido
								consumoExcedenteMedido,						
								// Volume Consumo para Medido
								volumeConsumoMedido,						
								// Volume Faturado para Medido
								volumeFaturadoMedido,						
								// Receita para Medido
								receitaMedido,						
								// N�mero de Economias para N�o Medido
								economiasNaoMedido,						
								// Consumo Medio para N�o Medido
								consumoMedioNaoMedido,						
								// Consumo Excedente para N�o Medido
								consumoExcedenteNaoMedido,						
								// Volume Consumo para N�o Medido
								volumeConsumoNaoMedido,						
								// Volume Faturado para N�o Medido
								volumeFaturadoNaoMedido,					
								// Receita para N�o Medido 
								receitaNaoMedido,
								// Percentual de Esgoto
								descricaoPercentual,
								// Descricao Tarifa 
								descricaoTarifa,
								// Ligacoes Medido
								ligacoesMedido,
								// Ligacoes Nao Medido
								ligacoesNaoMedido);
		
						// adiciona o bean a cole��o
						relatorioBeans.add(relatorioHistogramaEsgotoEconomiaBean);
					
					}
				
				}
				
				String faixa = emitirHistogramaEsgotoEconomiaHelper.getDescricaoFaixa();
				
				economiasMedido = formato.format(emitirHistogramaEsgotoEconomiaHelper.getTotalEconomiasMedido());
				volumeConsumoMedido = formato.format(emitirHistogramaEsgotoEconomiaHelper.getTotalVolumeConsumoMedido());
				volumeFaturadoMedido = formato.format(emitirHistogramaEsgotoEconomiaHelper.getTotalVolumeFaturadoMedido());
				receitaMedido = Util.formatarMoedaReal(emitirHistogramaEsgotoEconomiaHelper.getTotalReceitaMedido());
				ligacoesMedido = formato.format(emitirHistogramaEsgotoEconomiaHelper.getTotalLigacoesMedido());
				
				economiasNaoMedido = formato.format(emitirHistogramaEsgotoEconomiaHelper.getTotalEconomiasNaoMedido());
				volumeConsumoNaoMedido = formato.format(emitirHistogramaEsgotoEconomiaHelper.getTotalVolumeConsumoNaoMedido());
				volumeFaturadoNaoMedido = formato.format(emitirHistogramaEsgotoEconomiaHelper.getTotalVolumeFaturadoNaoMedido());
				receitaNaoMedido = Util.formatarMoedaReal(emitirHistogramaEsgotoEconomiaHelper.getTotalReceitaNaoMedido());
				ligacoesNaoMedido = formato.format(emitirHistogramaEsgotoEconomiaHelper.getTotalLigacoesNaoMedido());
				
				relatorioHistogramaEsgotoEconomiaBean = 
					new RelatorioHistogramaEsgotoEconomiaBean(
						// Op��o de Totaliza��o
						opcaoTotalizacao,
						// Descri��o
						descricao,
						// Descri��o da Faixa
						faixa,
						// N�mero de Economias
						economiasMedido,
						// Consumo Medio para Medido
						null,
						// Consumo Excedente para Medido
						null,						
						// Volume Consumo para Medido
						volumeConsumoMedido,						
						// Volume Faturado para Medido
						volumeFaturadoMedido,						
						// Receita para Medido
						receitaMedido,						
						// N�mero de Economias para N�o Medido
						economiasNaoMedido,						
						// Consumo Medio para N�o Medido
						null,						
						// Consumo Excedente para N�o Medido
						null,						
						// Volume Consumo para N�o Medido
						volumeConsumoNaoMedido,						
						// Volume Faturado para N�o Medido
						volumeFaturadoNaoMedido,					
						// Receita para N�o Medido 
						receitaNaoMedido,
						// Percentual de Esgoto
						descricaoPercentual,
						// Descricao Tarifa 
						descricaoTarifa,
						// Ligacoes Medido
						ligacoesMedido,
						// Ligacoes Nao Medido
						ligacoesNaoMedido);

				// adiciona o bean a cole��o
				relatorioBeans.add(relatorioHistogramaEsgotoEconomiaBean);

				
			}
		}
		// __________________________________________________________________

		// Par�metros do relat�rio
		Map parametros = new HashMap();
		
		// adiciona os par�metros do relat�rio
		// adiciona o laudo da an�lise
		
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		
		parametros.put("imagem", sistemaParametro.getImagemRelatorio());
		parametros.put("anoMes", Util.formatarAnoMesParaMesAno(filtrarEmitirHistogramaEsgotoEconomiaHelper.getMesAnoFaturamento()));
		
		parametros.put("tipoFormatoRelatorio", "R0606");
		

		// cria uma inst�ncia do dataSource do relat�rio
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_EMITIR_HISTOGRAMA_ESGOTO_ECONOMIA,
				parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relat�rio no sistema
		try {
			persistirRelatorioConcluido(retorno, Relatorio.HISTOGRAMA_ESGOTO_POR_ECONOMIA,
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
		int retorno = 0;

		return retorno;
	}

	public void agendarTarefaBatch() {
		AgendadorTarefas.agendarTarefa("RelatorioHistogramaEsgotoEconomia", this);

	}

}
