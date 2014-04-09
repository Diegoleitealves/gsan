package gcom.relatorio.faturamento;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.micromedicao.bean.MonitorarLeituraMobilePopupHelper;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.RelatorioVazioException;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RelatorioMonitorarLeituraMobile extends TarefaRelatorio {
	private static final long serialVersionUID = 1L;
	public RelatorioMonitorarLeituraMobile(Usuario usuario) {
		super(usuario, ConstantesRelatorios.RELATORIO_MONITORAR_LEITURA_MOBILE);
	}

	@Deprecated
	public RelatorioMonitorarLeituraMobile() {
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
		
		Collection<MonitorarLeituraMobilePopupHelper> colHelper =
			( Collection<MonitorarLeituraMobilePopupHelper> ) getParametro( "colHelper" );
		
		String anoMes = (String)getParametro( "anoMes");
		String grupoFaturamento = (String)getParametro( "grupoFaturamento");
		String nomeLocalidade = (String)getParametro( "nomeLocalidade");
		String nomeEmpresa = (String)getParametro( "nomeEmpresa");
		String nomeLeiturista = (String)getParametro( "nomeLeiturista");
		String tipoServico = (String)getParametro( "tipoServico");
		String situacaoTextoLeitura = (String)getParametro( "situacaoTextoLeitura");
		String cdRota = (String)getParametro( "cdRota");
		String tipoMedicao = (String)getParametro( "tipoMedicao");
		String imovelImpresso = (String)getParametro( "imovelImpresso");
		
		
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		for ( MonitorarLeituraMobilePopupHelper helper : colHelper) {
			RelatorioMonitorarLeituraMobileBean bean = new RelatorioMonitorarLeituraMobileBean( helper );
			
			relatorioBeans.add( bean );
		}

		// Par�metros do relat�rio
		Map parametros = new HashMap();
		
		// adiciona os par�metros do relat�rio
		// adiciona o laudo da an�lise
		
		SistemaParametro sistemaParametro = Fachada.getInstancia().pesquisarParametrosDoSistema();		
		parametros.put("imagem", sistemaParametro.getImagemRelatorio());		
		parametros.put("mesAno", anoMes);
		parametros.put("grupoFaturamento", grupoFaturamento);
		parametros.put("nomeLocalidade", nomeLocalidade);
		parametros.put("nomeEmpresa", nomeEmpresa);
		parametros.put("nomeLeiturista", nomeLeiturista);
		parametros.put("tipoServico", tipoServico);
		parametros.put("situacaoTextoLeitura", situacaoTextoLeitura);
		parametros.put("cdRota", cdRota );
		parametros.put("tipoMedicao", tipoMedicao );
		parametros.put("imovelImpresso", imovelImpresso );
		parametros.put("tipoRelatorio", "");

		// cria uma inst�ncia do dataSource do relat�rio
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_MONITORAR_LEITURA_MOBILE,
				parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relat�rio no sistema
		try {
			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_MONITORAR_LEITURA_MOBILE,
					idFuncionalidadeIniciada);
		} catch (ControladorException e) {
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relat�rio no sistema", e);
		}
		// ------------------------------------

		// retorna o relat�rio gerado
		return retorno;
	}

	public void agendarTarefaBatch() {
		AgendadorTarefas.agendarTarefa("RelatorioMonitorarLeituraMobile", this);

	}

	@Override
	public int calcularTotalRegistrosRelatorio() {		
		return -2;
	}

}
