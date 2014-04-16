package gcom.gui.relatorio.micromedicao.hidrometro;

import gcom.gui.micromedicao.hidrometro.FiltrarHidrometroDiametroActionForm;
import gcom.micromedicao.hidrometro.FiltroHidrometroDiametro;
import gcom.micromedicao.hidrometro.HidrometroDiametro;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.micromedicao.hidrometro.RelatorioManterHidrometroDiametro;
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
 * @author Vinicius Medeiros
 * @version 1.0
 */

public class GerarRelatorioHidrometroDiametroManterAction extends
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

		FiltrarHidrometroDiametroActionForm filtrarHidrometroDiametroActionForm = (FiltrarHidrometroDiametroActionForm) actionForm;

		FiltroHidrometroDiametro filtroHidrometroDiametro = (FiltroHidrometroDiametro) sessao
				.getAttribute("filtroHidrometroDiametro");

		// Inicio da parte que vai mandar os parametros para o relat�rio

		HidrometroDiametro hidrometroDiametroParametros = new HidrometroDiametro();

		String id = null;

		String idHidrometroDiametroPesquisar = (String) filtrarHidrometroDiametroActionForm
				.getId();

		if (idHidrometroDiametroPesquisar != null && !idHidrometroDiametroPesquisar.equals("")) {
			id = idHidrometroDiametroPesquisar;
		}

		Short numeroOrdem = null;
		
		if (filtrarHidrometroDiametroActionForm.getNumeroOrdem() != null && !filtrarHidrometroDiametroActionForm.getNumeroOrdem().equals("")){
			
			numeroOrdem = new Short("" +
					filtrarHidrometroDiametroActionForm.getNumeroOrdem());
		}
		
		
		Short indicadorDeUso = null;

		if (filtrarHidrometroDiametroActionForm.getIndicadorUso() != null
				&& !filtrarHidrometroDiametroActionForm.getIndicadorUso().equals("")) {

			indicadorDeUso = new Short(""
					+ filtrarHidrometroDiametroActionForm.getIndicadorUso());
		}
		
		// seta os parametros que ser�o mostrados no relat�rio

		hidrometroDiametroParametros.setId(id == null ? null : new Integer(
				id));
		hidrometroDiametroParametros.setDescricao(""
				+ filtrarHidrometroDiametroActionForm.getDescricao());
		hidrometroDiametroParametros.setDescricaoAbreviada(""
				+ filtrarHidrometroDiametroActionForm.getDescricaoAbreviada());
		hidrometroDiametroParametros.setNumeroOrdem(numeroOrdem);
		hidrometroDiametroParametros.setIndicadorUso(indicadorDeUso);

		// Fim da parte que vai mandar os parametros para o relat�rio

		// cria uma inst�ncia da classe do relat�rio
		RelatorioManterHidrometroDiametro relatorioManterHidrometroDiametro = new RelatorioManterHidrometroDiametro(
				(Usuario)(httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));
		relatorioManterHidrometroDiametro.addParametro("filtroHidrometroDiametro",
				filtroHidrometroDiametro);
		relatorioManterHidrometroDiametro.addParametro("hidrometroDiametroParametros",
				hidrometroDiametroParametros);

		// chama o met�do de gerar relat�rio passando o c�digo da analise
		// como par�metro
		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");
		if (tipoRelatorio == null) {
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		relatorioManterHidrometroDiametro.addParametro("tipoFormatoRelatorio", Integer
				.parseInt(tipoRelatorio));
		try {
			retorno = processarExibicaoRelatorio(relatorioManterHidrometroDiametro,
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
