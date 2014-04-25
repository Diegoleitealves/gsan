package gcom.gui.relatorio.micromedicao;

import gcom.gui.micromedicao.FiltrarConsumoAnormalidadeActionForm;
import gcom.micromedicao.consumo.ConsumoAnormalidade;
import gcom.micromedicao.consumo.FiltroConsumoAnormalidade;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.micromedicao.RelatorioManterConsumoAnormalidade;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.SistemaException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GerarRelatorioConsumoAnormalidadeManterAction extends
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

		FiltrarConsumoAnormalidadeActionForm filtrarConsumoAnormalidadeActionForm = (FiltrarConsumoAnormalidadeActionForm) actionForm;

		FiltroConsumoAnormalidade filtroConsumoAnormalidade = (FiltroConsumoAnormalidade) sessao
				.getAttribute("filtroConsumoAnormalidade");

		// Inicio da parte que vai mandar os parametros para o relat�rio

		ConsumoAnormalidade consumoAnormalidadeParametros = new ConsumoAnormalidade();

		String id = null;

		String idConsumoAnormalidadePesquisar = (String) filtrarConsumoAnormalidadeActionForm
				.getId();

		if (idConsumoAnormalidadePesquisar != null && !idConsumoAnormalidadePesquisar.equals("")) {
			id = idConsumoAnormalidadePesquisar;
		}
		
		Short indicadorDeUso = null;

		if (filtrarConsumoAnormalidadeActionForm.getIndicadorUso() != null
				&& !filtrarConsumoAnormalidadeActionForm.getIndicadorUso().equals("")) {

			indicadorDeUso = new Short(""
					+ filtrarConsumoAnormalidadeActionForm.getIndicadorUso());
		}
		
		// seta os parametros que ser�o mostrados no relat�rio

		consumoAnormalidadeParametros.setId(id == null ? null : new Integer(
				id));
		consumoAnormalidadeParametros.setDescricao(""
				+ filtrarConsumoAnormalidadeActionForm.getDescricao());
		consumoAnormalidadeParametros.setDescricaoAbreviada(""
				+ filtrarConsumoAnormalidadeActionForm.getDescricaoAbreviada());
		consumoAnormalidadeParametros.setIndicadorUso(indicadorDeUso);

		// Fim da parte que vai mandar os parametros para o relat�rio

		// cria uma inst�ncia da classe do relat�rio
		RelatorioManterConsumoAnormalidade relatorioManterConsumoAnormalidade = new RelatorioManterConsumoAnormalidade(
				(Usuario)(httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));
		relatorioManterConsumoAnormalidade.addParametro("filtroConsumoAnormalidade",
				filtroConsumoAnormalidade);
		relatorioManterConsumoAnormalidade.addParametro("consumoAnormalidadeParametros",
				consumoAnormalidadeParametros);

		// chama o met�do de gerar relat�rio passando o c�digo da analise
		// como par�metro
		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");
		if (tipoRelatorio == null) {
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		relatorioManterConsumoAnormalidade.addParametro("tipoFormatoRelatorio", Integer
				.parseInt(tipoRelatorio));
		try {
			retorno = processarExibicaoRelatorio(relatorioManterConsumoAnormalidade,
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
