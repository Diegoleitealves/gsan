package gcom.gui.cobranca;

import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.cobranca.RelatorioEmitirDeclaracaoTransferenciaDebito;
import gcom.tarefa.TarefaRelatorio;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GerarRelatorioEmitirDeclaracaoTransferenciaDebitoCredito extends ExibidorProcessamentoTarefaRelatorio {

	@Override
	public ActionForward execute(
			ActionMapping mapping, 
			ActionForm actionForm, 
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		
		// cria a vari�vel de retorno
		ActionForward retorno = null;		
		
		int tipoRelatorio = TarefaRelatorio.TIPO_PDF;
		// cria uma inst�ncia da classe do relat�rio
		 
		RelatorioEmitirDeclaracaoTransferenciaDebito relatorio = 
			(RelatorioEmitirDeclaracaoTransferenciaDebito) 
				this.getSessao(request).getAttribute("declaracaoTransferencia");			

		try {
			retorno = processarExibicaoRelatorio(
				relatorio, 
				tipoRelatorio,
				request, 
				response, 
				mapping);
		
		} catch (RelatorioVazioException ex) {
			// manda o erro para a p�gina no request atual
			reportarErros(request, "atencao.relatorio.vazio");

			// seta o mapeamento de retorno para a tela de aten��o de popup
			retorno = mapping.findForward("telaAtencaoPopup");
		}
		
		// devolve o mapeamento contido na vari�vel retorno
		return retorno;
	}		
}

