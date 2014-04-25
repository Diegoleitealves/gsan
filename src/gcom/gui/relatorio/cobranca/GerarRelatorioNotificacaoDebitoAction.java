package gcom.gui.relatorio.cobranca;

import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.cobranca.RelatorioNotificacaoDebito;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * action respons�vel pela exibi��o do relat�rio de bairro manter
 * 
 * @author S�vio Luiz
 * @created 11 de Julho de 2005
 */
public class GerarRelatorioNotificacaoDebitoAction extends
		ExibidorProcessamentoTarefaRelatorio {

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param actionMapping
	 *            Descri��o do par�metro
	 * @param actionForm
	 *            Descri��o do par�metro
	 * @param httpServletRequest
	 *            Descri��o do par�metro
	 * @param httpServletResponse
	 *            Descri��o do par�metro
	 * @return Descri��o do retorno
	 */
	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		// cria a vari�vel de retorno
		ActionForward retorno = null;

//		ExibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm form = (ExibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm) actionForm;
		
		String idCobrancaAcaoAtividadeCronograma = httpServletRequest.getParameter("idCobrancaAcaoAtividadeCronograma");
		
		Integer idCobrancaAcaoCronograma = null;
		
		if (idCobrancaAcaoAtividadeCronograma != null && !idCobrancaAcaoAtividadeCronograma.trim().equals("")) {
			idCobrancaAcaoCronograma = new Integer(idCobrancaAcaoAtividadeCronograma);
		}
		
		String idCobrancaAcaoAtividadeComando = httpServletRequest.getParameter("idCobrancaAcaoAtividadeComando");
		
		Integer idCobrancaAcaoComando = null;
		
		if (idCobrancaAcaoAtividadeComando != null && !idCobrancaAcaoAtividadeComando.trim().equals("")) {
			idCobrancaAcaoComando = new Integer(idCobrancaAcaoAtividadeComando);
		}
		
		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");

		RelatorioNotificacaoDebito relatorio = new RelatorioNotificacaoDebito((Usuario)(httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));
		relatorio.addParametro("idCobrancaAcaoCronograma", idCobrancaAcaoCronograma);
		relatorio.addParametro("idCobrancaAcaoComando", idCobrancaAcaoComando);

		if (tipoRelatorio == null) {
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		relatorio.addParametro("tipoFormatoRelatorio", Integer
				.parseInt(tipoRelatorio));
		retorno = processarExibicaoRelatorio(relatorio, tipoRelatorio,
				httpServletRequest, httpServletResponse, actionMapping);

		// devolve o mapeamento contido na vari�vel retorno
		return retorno;
	}

}
