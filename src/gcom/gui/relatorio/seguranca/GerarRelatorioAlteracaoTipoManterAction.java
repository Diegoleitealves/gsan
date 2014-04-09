package gcom.gui.relatorio.seguranca;

import gcom.gui.seguranca.acesso.FiltrarAlteracaoTipoActionForm;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.seguranca.RelatorioManterAlteracaoTipo;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.transacao.AlteracaoTipo;
import gcom.seguranca.transacao.FiltroAlteracaoTipo;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.SistemaException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 
 * @author Vinicius Medeiros
 * @version 1.0
 */

public class GerarRelatorioAlteracaoTipoManterAction extends
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

		FiltrarAlteracaoTipoActionForm filtrarAlteracaoTipoActionForm = (FiltrarAlteracaoTipoActionForm) actionForm;

		FiltroAlteracaoTipo filtroAlteracaoTipo = (FiltroAlteracaoTipo) sessao
				.getAttribute("filtroAlteracaoTipo");

		// Inicio da parte que vai mandar os parametros para o relat�rio

		AlteracaoTipo alteracaoTipoParametros = new AlteracaoTipo();

		String id = null;

		String idAlteracaoTipoPesquisar = (String) filtrarAlteracaoTipoActionForm
				.getId();

		if (idAlteracaoTipoPesquisar != null && !idAlteracaoTipoPesquisar.equals("")) {
			id = idAlteracaoTipoPesquisar;
		}
		
		// seta os parametros que ser�o mostrados no relat�rio

		alteracaoTipoParametros.setId(id == null ? null : new Integer(
				id));
		alteracaoTipoParametros.setDescricao(""
				+ filtrarAlteracaoTipoActionForm.getDescricao());
		alteracaoTipoParametros.setDescricaoAbreviada(""
				+ filtrarAlteracaoTipoActionForm.getDescricaoAbreviada());
		
		// Fim da parte que vai mandar os parametros para o relat�rio

		// cria uma inst�ncia da classe do relat�rio
		RelatorioManterAlteracaoTipo relatorioManterAlteracaoTipo = new RelatorioManterAlteracaoTipo(
				(Usuario)(httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));
		relatorioManterAlteracaoTipo.addParametro("filtroAlteracaoTipo",
				filtroAlteracaoTipo);
		relatorioManterAlteracaoTipo.addParametro("alteracaoTipoParametros",
				alteracaoTipoParametros);

		// chama o met�do de gerar relat�rio passando o c�digo da analise
		// como par�metro
		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");
		if (tipoRelatorio == null) {
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		relatorioManterAlteracaoTipo.addParametro("tipoFormatoRelatorio", Integer
				.parseInt(tipoRelatorio));
		try {
			retorno = processarExibicaoRelatorio(relatorioManterAlteracaoTipo,
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
