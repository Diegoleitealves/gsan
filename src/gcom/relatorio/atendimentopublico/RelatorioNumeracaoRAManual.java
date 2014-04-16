package gcom.relatorio.atendimentopublico;

import gcom.atendimentopublico.registroatendimento.bean.GerarNumeracaoRAManualHelper;
import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * classe respons�vel por criar o relat�rio de bairro manter de �gua
 * 
 * @author S�vio Luiz
 * @created 11 de Julho de 2005
 */
public class RelatorioNumeracaoRAManual extends TarefaRelatorio {
	private static final long serialVersionUID = 1L;
	public RelatorioNumeracaoRAManual(Usuario usuario) {
		super(usuario, ConstantesRelatorios.RELATORIO_NUMERACAO_RA_MANUAL);
	}
	
	@Deprecated
	public RelatorioNumeracaoRAManual() {
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

		GerarNumeracaoRAManualHelper gerarNumeracaoRAManualHelper = (GerarNumeracaoRAManualHelper) getParametro("gerarNumeracaoRAManualHelper");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// valor de retorno
		byte[] retorno = null;

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		RelatorioNumeracaoRAManualBean relatorioBean = null;
		
		Collection colecaoNumeracao = gerarNumeracaoRAManualHelper.getColecaoNumeracao();

		// se a cole��o de par�metros da analise n�o for vazia
		if (colecaoNumeracao != null
				&& !colecaoNumeracao.isEmpty()) {

			// coloca a cole��o de par�metros da analise no iterator
			Iterator colecaoNumeracaoIterator = colecaoNumeracao
					.iterator();

			// la�o para criar a cole��o de par�metros da analise
			while (colecaoNumeracaoIterator.hasNext()) {

				String numeracao = (String) colecaoNumeracaoIterator
						.next();
				
				relatorioBean = new RelatorioNumeracaoRAManualBean(
						
						// Unidade
						gerarNumeracaoRAManualHelper.getDescricaoUnidadeOrganizacional(),
						
						// Numera��o
						numeracao);

				// adiciona o bean a cole��o
				relatorioBeans.add(relatorioBean);
			}
		}
		// __________________________________________________________________

		// Par�metros do relat�rio
		Map parametros = new HashMap();

		// adiciona os par�metros do relat�rio
		// adiciona o laudo da an�lise
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		
		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		// cria uma inst�ncia do dataSource do relat�rio
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = gerarRelatorio(
				ConstantesRelatorios.RELATORIO_NUMERACAO_RA_MANUAL,
				parametros, ds, tipoFormatoRelatorio);
		
		// ------------------------------------
		// Grava o relat�rio no sistema
		try {
			persistirRelatorioConcluido(retorno, Relatorio.NUMERACAO_RA_MANUAL,
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
		AgendadorTarefas.agendarTarefa("RelatorioNumeracaoRAManual", this);
	}
}
