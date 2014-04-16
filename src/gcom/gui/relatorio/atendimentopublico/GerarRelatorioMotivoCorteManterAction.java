package gcom.gui.relatorio.atendimentopublico;

import gcom.atendimentopublico.ligacaoagua.FiltroMotivoCorte;
import gcom.atendimentopublico.ligacaoagua.MotivoCorte;
import gcom.gui.atendimentopublico.FiltrarMotivoCorteActionForm;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.atendimentopublico.RelatorioManterMotivoCorte;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.SistemaException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <p>
 * Title: GCOM
 * </p>
 * <p>
 * Description: Sistema de Gest�o Comercial
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: COMPESA - Companhia Pernambucana de Saneamento
 * </p>
 * 
 * @author Rafael Corr�a
 * @version 1.0
 */

public class GerarRelatorioMotivoCorteManterAction extends
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

		FiltrarMotivoCorteActionForm filtrarMotivoCorteActionForm = (FiltrarMotivoCorteActionForm) actionForm;

		FiltroMotivoCorte filtroMotivoCorte = (FiltroMotivoCorte) sessao
				.getAttribute("filtroMotivoCorte");

		// Inicio da parte que vai mandar os parametros para o relat�rio

		MotivoCorte motivoCorteParametros = new MotivoCorte();

		String idMotivoCorte = null;

		String idMotivoCortePesquisar = (String) filtrarMotivoCorteActionForm
				.getIdMotivoCorte();

		if (idMotivoCortePesquisar != null && !idMotivoCortePesquisar.equals("")) {
			idMotivoCorte = idMotivoCortePesquisar;
		}

		Short indicadorDeUso = null;

		if (filtrarMotivoCorteActionForm.getIndicadorUso() != null
				&& !filtrarMotivoCorteActionForm.getIndicadorUso().equals("")) {

			indicadorDeUso = new Short(""
					+ filtrarMotivoCorteActionForm.getIndicadorUso());
		}

		// seta os parametros que ser�o mostrados no relat�rio

		motivoCorteParametros.setId(idMotivoCorte == null ? null : new Integer(
				idMotivoCorte));
		motivoCorteParametros.setDescricao(""
				+ filtrarMotivoCorteActionForm.getDescricao());
		motivoCorteParametros.setIndicadorUso(indicadorDeUso);

		// Fim da parte que vai mandar os parametros para o relat�rio

		// cria uma inst�ncia da classe do relat�rio
		RelatorioManterMotivoCorte relatorioManterMotivoCorte = new RelatorioManterMotivoCorte(
				(Usuario)(httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));
		relatorioManterMotivoCorte.addParametro("filtroMotivoCorte",
				filtroMotivoCorte);
		relatorioManterMotivoCorte.addParametro("motivoCorteParametros",
				motivoCorteParametros);

		// chama o met�do de gerar relat�rio passando o c�digo da analise
		// como par�metro
		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");
		if (tipoRelatorio == null) {
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		relatorioManterMotivoCorte.addParametro("tipoFormatoRelatorio", Integer
				.parseInt(tipoRelatorio));
		try {
			retorno = processarExibicaoRelatorio(relatorioManterMotivoCorte,
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
