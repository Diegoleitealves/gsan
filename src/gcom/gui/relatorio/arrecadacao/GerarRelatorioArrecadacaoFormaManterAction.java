package gcom.gui.relatorio.arrecadacao;

import gcom.arrecadacao.ArrecadacaoForma;
import gcom.arrecadacao.FiltroArrecadacaoForma;
import gcom.gui.arrecadacao.FiltrarArrecadacaoFormaActionForm;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.arrecadacao.RelatorioManterArrecadacaoForma;
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

public class GerarRelatorioArrecadacaoFormaManterAction extends
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

		FiltrarArrecadacaoFormaActionForm filtrarArrecadacaoFormaActionForm = (FiltrarArrecadacaoFormaActionForm) actionForm;

		FiltroArrecadacaoForma filtroArrecadacaoForma = (FiltroArrecadacaoForma) sessao
				.getAttribute("filtroArrecadacaoForma");

		// Inicio da parte que vai mandar os parametros para o relat�rio

		ArrecadacaoForma arrecadacaoFormaParametros = new ArrecadacaoForma();

		String idArrecadacaoForma = null;

		String idArrecadacaoFormaPesquisar = (String) filtrarArrecadacaoFormaActionForm
				.getIdArrecadacaoForma();

		if (idArrecadacaoFormaPesquisar != null && !idArrecadacaoFormaPesquisar.equals("")) {
			idArrecadacaoForma = idArrecadacaoFormaPesquisar;
		}
		
		String codigoArrecadacaoForma = null;

		String codigoArrecadacaoFormaPesquisar = filtrarArrecadacaoFormaActionForm.getCodigoArrecadacaoForma();

		if (codigoArrecadacaoFormaPesquisar != null && !codigoArrecadacaoFormaPesquisar.equals("")) {
			codigoArrecadacaoForma = codigoArrecadacaoFormaPesquisar;
		}

		// seta os parametros que ser�o mostrados no relat�rio

		arrecadacaoFormaParametros.setId(idArrecadacaoForma == null ? null : new Integer(
				idArrecadacaoForma));
		arrecadacaoFormaParametros.setCodigoArrecadacaoForma(codigoArrecadacaoForma);
		arrecadacaoFormaParametros.setDescricao(""
				+ filtrarArrecadacaoFormaActionForm.getDescricao());
		
		// Fim da parte que vai mandar os parametros para o relat�rio

		// cria uma inst�ncia da classe do relat�rio
		RelatorioManterArrecadacaoForma relatorioManterArrecadacaoForma = new RelatorioManterArrecadacaoForma(
				(Usuario)(httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));
		relatorioManterArrecadacaoForma.addParametro("filtroArrecadacaoForma",
				filtroArrecadacaoForma);
		relatorioManterArrecadacaoForma.addParametro("arrecadacaoFormaParametros",
				arrecadacaoFormaParametros);

		// chama o met�do de gerar relat�rio passando o c�digo da analise
		// como par�metro
		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");
		if (tipoRelatorio == null) {
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		relatorioManterArrecadacaoForma.addParametro("tipoFormatoRelatorio", Integer
				.parseInt(tipoRelatorio));
		try {
			retorno = processarExibicaoRelatorio(relatorioManterArrecadacaoForma,
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
