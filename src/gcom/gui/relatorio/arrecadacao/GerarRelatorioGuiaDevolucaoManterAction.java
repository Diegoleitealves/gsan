package gcom.gui.relatorio.arrecadacao;

import gcom.arrecadacao.FiltroGuiaDevolucao;
import gcom.arrecadacao.GuiaDevolucao;
import gcom.gui.arrecadacao.FiltrarGuiaDevolucaoActionForm;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.arrecadacao.RelatorioManterGuiaDevolucao;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.SistemaException;
import gcom.util.Util;

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
public class GerarRelatorioGuiaDevolucaoManterAction extends
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

		FiltrarGuiaDevolucaoActionForm filtrarGuiaDevolucaoActionForm = (FiltrarGuiaDevolucaoActionForm) actionForm;

		FiltroGuiaDevolucao filtroGuiaDevolucao = null;

		if (sessao.getAttribute("filtroGuiaDevolucaoCliente") != null) {

			filtroGuiaDevolucao = (FiltroGuiaDevolucao) sessao
					.getAttribute("filtroGuiaDevolucaoCliente");

		} else {

			filtroGuiaDevolucao = (FiltroGuiaDevolucao) sessao
					.getAttribute("filtroGuiaDevolucaoImovel");

		}

		// Collection colecaoDevolucoes = (Collection) sessao
		// .getAttribute("colecaoLocalidadeDevolucoes");

		// Inicio da parte que vai mandar os parametros para o relat�rio

		GuiaDevolucao guiaDevolucaoParametrosInicial = new GuiaDevolucao();
		GuiaDevolucao guiaDevolucaoParametrosFinal = new GuiaDevolucao();

		// seta os parametros que ser�o mostrados no relat�rio
		if (filtrarGuiaDevolucaoActionForm.getPeriodoArrecadacaoInicio() != null
				&& !filtrarGuiaDevolucaoActionForm
						.getPeriodoArrecadacaoInicio().equals("")) {
			guiaDevolucaoParametrosInicial
					.setAnoMesReferenciaContabil(new Integer(
							Util
									.formatarMesAnoParaAnoMesSemBarra(filtrarGuiaDevolucaoActionForm
											.getPeriodoArrecadacaoInicio())));
			guiaDevolucaoParametrosFinal
					.setAnoMesReferenciaContabil(new Integer(
							Util
									.formatarMesAnoParaAnoMesSemBarra(filtrarGuiaDevolucaoActionForm
											.getPeriodoArrecadacaoFim())));
		}

		if (filtrarGuiaDevolucaoActionForm.getPeriodoGuiaInicio() != null
				&& !filtrarGuiaDevolucaoActionForm.getPeriodoGuiaInicio()
						.equals("")) {
			guiaDevolucaoParametrosInicial
					.setAnoMesReferenciaGuiaDevolucao(new Integer(
							Util
									.formatarMesAnoParaAnoMesSemBarra(filtrarGuiaDevolucaoActionForm
											.getPeriodoGuiaInicio())));
			guiaDevolucaoParametrosFinal
					.setAnoMesReferenciaGuiaDevolucao(new Integer(
							Util
									.formatarMesAnoParaAnoMesSemBarra(filtrarGuiaDevolucaoActionForm
											.getPeriodoGuiaFim())));
		}

		if (filtrarGuiaDevolucaoActionForm.getDataEmissaoInicio() != null
				&& !filtrarGuiaDevolucaoActionForm.getDataEmissaoInicio()
						.equals("")) {
			guiaDevolucaoParametrosInicial.setDataEmissao(Util
					.converteStringParaDate(filtrarGuiaDevolucaoActionForm
							.getDataEmissaoInicio()));
			guiaDevolucaoParametrosFinal.setDataEmissao(Util
					.converteStringParaDate(filtrarGuiaDevolucaoActionForm
							.getDataEmissaoFim()));
		}

		if (filtrarGuiaDevolucaoActionForm.getDataValidadeInicio() != null
				&& !filtrarGuiaDevolucaoActionForm.getDataValidadeInicio()
						.equals("")) {
			guiaDevolucaoParametrosInicial.setDataValidade(Util
					.converteStringParaDate(filtrarGuiaDevolucaoActionForm
							.getDataValidadeInicio()));
			guiaDevolucaoParametrosFinal.setDataEmissao(Util
					.converteStringParaDate(filtrarGuiaDevolucaoActionForm
							.getDataValidadeFim()));
		}

		// Fim da parte que vai mandar os parametros para o relat�rio

		// cria uma inst�ncia da classe do relat�rio
		RelatorioManterGuiaDevolucao relatorioManterGuiaDevolucao = new RelatorioManterGuiaDevolucao(
				(Usuario)(httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));

		relatorioManterGuiaDevolucao.addParametro("filtroGuiaDevolucao",
				filtroGuiaDevolucao);
		relatorioManterGuiaDevolucao.addParametro(
				"guiaDevolucaoParametrosInicial",
				guiaDevolucaoParametrosInicial);
		relatorioManterGuiaDevolucao.addParametro(
				"guiaDevolucaoParametrosFinal", guiaDevolucaoParametrosFinal);

		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");
		if (tipoRelatorio == null) {
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		relatorioManterGuiaDevolucao.addParametro("tipoFormatoRelatorio",
				Integer.parseInt(tipoRelatorio));
		try {
			retorno = processarExibicaoRelatorio(relatorioManterGuiaDevolucao,
					tipoRelatorio, httpServletRequest, httpServletResponse,
					actionMapping);
		} catch (SistemaException ex) {
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
