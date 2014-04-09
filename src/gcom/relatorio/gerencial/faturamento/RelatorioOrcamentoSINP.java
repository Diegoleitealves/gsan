package gcom.relatorio.gerencial.faturamento;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.gerencial.bean.FiltrarRelatorioOrcamentoSINPHelper;
import gcom.gerencial.bean.OrcamentoSINPHelper;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.faturamento.RelatorioOrcamentoSINPBean;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * classe respons�vel por criar o relat�rio de or�amento e SINP
 * 
 * @author Rafael Pinto
 * @created 22/11/2007
 */
public class RelatorioOrcamentoSINP extends TarefaRelatorio {
	
	private static final long serialVersionUID = 1L;
	
	public RelatorioOrcamentoSINP(Usuario usuario) {
		super(usuario, ConstantesRelatorios.RELATORIO_ORCAMENTO_SINP);
	}

	@Deprecated
	public RelatorioOrcamentoSINP() {
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

		FiltrarRelatorioOrcamentoSINPHelper filtro = 
			(FiltrarRelatorioOrcamentoSINPHelper) getParametro("filtrarRelatorioOrcamentoSINPHelper");
		
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		RelatorioOrcamentoSINPBean relatorioOrcamentoSINPBean = null;

		Collection<OrcamentoSINPHelper> colecao = 
			fachada.pesquisarRelatorioOrcamentoSINP(filtro);

		// se a cole��o de par�metros da analise n�o for vazia
		if (colecao != null && !colecao.isEmpty()) {

			// coloca a cole��o de par�metros da analise no iterator
			Iterator colecaoIterator = colecao.iterator();

			// la�o para criar a cole��o de par�metros da analise
			while (colecaoIterator.hasNext()) {

				OrcamentoSINPHelper helper = 
					(OrcamentoSINPHelper) colecaoIterator.next();
				
				//Atendendo a [CRC 4214]
				//Filtrar as p�ginas que n�o tenham D�bito e ou Economias.
				if(!(helper.getAguaTotalLigacoesCadastradas() == 0 &&
						helper.getAguaTotalEconomiasCadastradas() == 0 &&
						helper.getEsgotoTotalLigacoesCadastradas() == 0 &&
						helper.getEsgotoTotalEconomiasCadastradas() == 0 &&
						helper.getSaldoContasReceber().compareTo(new BigDecimal("0"))== 0)){
					relatorioOrcamentoSINPBean = 
						new RelatorioOrcamentoSINPBean(helper);

					// adiciona o bean a cole��o
					relatorioBeans.add(relatorioOrcamentoSINPBean);		
				}	
				
				
			}
		}
		// __________________________________________________________________

		// Par�metros do relat�rio
		Map parametros = new HashMap();
		
		// adiciona os par�metros do relat�rio
		// adiciona o laudo da an�lise
		
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		
		parametros.put("imagem", sistemaParametro.getImagemRelatorio());
		parametros.put("anoMes", Util.formatarAnoMesParaMesAno(filtro.getAnoMesReferencia()));
		
		String opcaoTotalizacaoParametro = "";
		switch (filtro.getOpcaoTotalizacao()){
		
		case 1:
			opcaoTotalizacaoParametro = "ESTADO";
			break;
		case 2:
			opcaoTotalizacaoParametro = "ESTADO POR GER�NCIAL REGIONAL";
			break;
		case 3:
			opcaoTotalizacaoParametro = "ESTADO POR UNIDADE DE NEG�CIO";
			break;
		case 5:
			opcaoTotalizacaoParametro = "ESTADO POR CENTRO CUSTO/LOCALIDADE";
			break;
		case 6:
			opcaoTotalizacaoParametro = "GER�NCIA REGIONAL";
			break;
		case 10:
			opcaoTotalizacaoParametro = "UNIDADE DE NEG�CIO";
			break;
		case 16:
			opcaoTotalizacaoParametro = "LOCALIDADE";
			break;
		case 19:
			opcaoTotalizacaoParametro = "MUNIC�PIO";
			break;
		case 26:
			opcaoTotalizacaoParametro = "ESTADO POR MUNIC�PIO";
			break;
		}
		
		parametros.put("opcaoTotalizacaoParametro", opcaoTotalizacaoParametro);
		parametros.put("tipoFormatoRelatorio", "R0722");

		// cria uma inst�ncia do dataSource do relat�rio
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_ORCAMENTO_SINP,
				parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relat�rio no sistema
		try {
			persistirRelatorioConcluido(retorno, Relatorio.ORCAMENTO_SINP,
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

		return retorno;
	}

	public void agendarTarefaBatch() {
		AgendadorTarefas.agendarTarefa("RelatorioOrcamentoSINP", this);

	}

}
