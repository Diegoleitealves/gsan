package gcom.gui.relatorio.atendimentopublico;

import gcom.gui.atendimentopublico.registroatendimento.ConsultarRegistroAtendimentoActionForm;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.atendimentopublico.RelatorioConsultarRegistroAtendimento;
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
public class GerarRelatorioRegistroAtendimentoConsultarAction extends
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

		ConsultarRegistroAtendimentoActionForm consultarRegistroAtendimentoActionForm = (ConsultarRegistroAtendimentoActionForm) actionForm;

		String idRegistroAtendimento = consultarRegistroAtendimentoActionForm
				.getNumeroRAPesquisado();

		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");

		RelatorioConsultarRegistroAtendimento relatorioConsultarRegistroAtendimento = new RelatorioConsultarRegistroAtendimento(
				(Usuario)(httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));
		relatorioConsultarRegistroAtendimento.addParametro(
				"idRegistroAtendimento", new Integer(idRegistroAtendimento));
		if (tipoRelatorio == null) {
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		relatorioConsultarRegistroAtendimento.addParametro(
				"tipoFormatoRelatorio", Integer.parseInt(tipoRelatorio));
		retorno = processarExibicaoRelatorio(
				relatorioConsultarRegistroAtendimento, tipoRelatorio,
				httpServletRequest, httpServletResponse, actionMapping);

		// devolve o mapeamento contido na vari�vel retorno
		return retorno;
	}

}
