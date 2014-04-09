package gcom.gui.relatorio.arrecadacao;

import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.arrecadacao.RelatorioMovimentoDebitoAutomaticoBanco;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.SistemaException;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * action respons�vel pela exibi��o do relat�rio de bairro manter
 * 
 * @author S�vio Luiz
 * @created 11 de Julho de 2005
 */
public class GerarRelatorioMovimentoDebitoAutomaticoBancoAction extends
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

		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		Collection colecaoGerarMovimentoDebitoAutomatico = (Collection) sessao
				.getAttribute("colecaogerarMovimentoDebitoAutomatico");

		//byte[] relatorioRetorno = null;

		//OutputStream out = null;

		try {

			// cria uma inst�ncia da classe do relat�rio
			RelatorioMovimentoDebitoAutomaticoBanco relatorioMovimentoDebitoAutomaticoBanco = new RelatorioMovimentoDebitoAutomaticoBanco(
					(Usuario)(httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));

			relatorioMovimentoDebitoAutomaticoBanco.addParametro(
					"colecaoGerarMovimentoDebitoAutomatico",
					colecaoGerarMovimentoDebitoAutomatico);

			String tipoRelatorio = httpServletRequest
					.getParameter("tipoRelatorio");
			if (tipoRelatorio == null) {
				tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
			}
			

			relatorioMovimentoDebitoAutomaticoBanco.addParametro(
					"tipoFormatoRelatorio", Integer.parseInt(tipoRelatorio));
			retorno = processarExibicaoRelatorio(
					relatorioMovimentoDebitoAutomaticoBanco, tipoRelatorio,
					httpServletRequest, httpServletResponse, actionMapping);

		}  catch (SistemaException ex) {
			// manda o erro para a p�gina no request atual
			reportarErros(httpServletRequest, "erro.sistema");

			// seta o mapeamento de retorno para a tela de erro de popup
			retorno = actionMapping.findForward("telaErroPopup");

		} catch (RelatorioVazioException ex1) {
			// manda o erro para a p�gina no request atual
			reportarErros(httpServletRequest, "erro.relatorio.vazio");

			// seta o mapeamento de retorno para a tela de aten��o de popup
			retorno = actionMapping.findForward("telaAtencaoPopup");
		}

		// devolve o mapeamento contido na vari�vel retorno
		return retorno;
	}

}
