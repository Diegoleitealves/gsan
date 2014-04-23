package gcom.relatorio.faturamento;

import gcom.cadastro.cliente.Cliente;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
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
import java.util.List;
import java.util.Map;

/**
 * classe respons�vel por criar o relat�rio de bairro manter de �gua
 * 
 * @author Fernanda Paiva
 * @created 11 de Julho de 2005
 */
public class RelatorioProtocoloEntregaFatura extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;
	public RelatorioProtocoloEntregaFatura(Usuario usuario) {
		super(usuario, ConstantesRelatorios.RELATORIO_PROTOCOLO_ENTREGA_FATURA);
	}

	@Deprecated
	public RelatorioProtocoloEntregaFatura() {
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

	@SuppressWarnings("unchecked")
	public Object executar() throws TarefaException {
		
		Fachada fachada = Fachada.getInstancia();
		
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");
		Integer anoMes = (Integer) getParametro("anoMes");
		Cliente cliente = (Cliente) getParametro("cliente");
		Collection<Integer> idsClientes = (Collection<Integer>) getParametro("idsClientes");
		
		// valor de retorno
		byte[] retorno = null;

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();
		
		Collection colecaoRelatorioProtocoloEntregaFatura = fachada.pesquisarDadosRelatorioProtocoloEntregaFatura(anoMes, cliente, idsClientes);
		
		if(colecaoRelatorioProtocoloEntregaFatura == null || colecaoRelatorioProtocoloEntregaFatura.isEmpty()){
			throw new ActionServletException(
					"atencao.pesquisa.nenhumresultado", null,"");
		}
		
		
		relatorioBeans.addAll(colecaoRelatorioProtocoloEntregaFatura);
        
		// Par�metros do relat�rio
		Map parametros = new HashMap();

		// cria uma inst�ncia do dataSource do relat�rio
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = gerarRelatorio(
				ConstantesRelatorios.RELATORIO_PROTOCOLO_ENTREGA_FATURA, parametros,
				ds, tipoFormatoRelatorio);
		
		// retorna o relat�rio gerado
		return retorno;
	}
	
	@Override
	public int calcularTotalRegistrosRelatorio() {
		
		Integer retorno = 0;
		
		return retorno;
	}

	public void agendarTarefaBatch() {
		AgendadorTarefas.agendarTarefa("RelatorioProtocoloEntregaFatura", this);
	}

}
