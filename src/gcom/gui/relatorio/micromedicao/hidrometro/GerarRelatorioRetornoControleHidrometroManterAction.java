package gcom.gui.relatorio.micromedicao.hidrometro;

import gcom.gui.micromedicao.hidrometro.FiltrarRetornoControleHidrometroActionForm;
import gcom.micromedicao.hidrometro.FiltroRetornoControleHidrometro;
import gcom.micromedicao.hidrometro.RetornoControleHidrometro;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.micromedicao.hidrometro.RelatorioManterRetornoControleHidrometro;
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
 * @author Wallace Thierre
 * @version 1.0
 */

public class GerarRelatorioRetornoControleHidrometroManterAction extends
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

		FiltrarRetornoControleHidrometroActionForm filtrarRetornoControleHidrometroActionForm = (FiltrarRetornoControleHidrometroActionForm) actionForm;

		FiltroRetornoControleHidrometro filtroRetornoControleHidrometro = (FiltroRetornoControleHidrometro) sessao
				.getAttribute("filtroRetornoControleHidrometro");

		// Inicio da parte que vai mandar os parametros para o relat�rio

		RetornoControleHidrometro retornoControleHidrometroParametros = new RetornoControleHidrometro();
		String id = null;

		String idRetornoControleHidrometroPesquisar = (String) filtrarRetornoControleHidrometroActionForm.getId();

		if (idRetornoControleHidrometroPesquisar != null && !idRetornoControleHidrometroPesquisar.equals("")) {
			id = idRetornoControleHidrometroPesquisar;
		}	
		
		Short indicadorDeGeracao = null;

		if (filtrarRetornoControleHidrometroActionForm.getIndicadorUso() != null
				&& !filtrarRetornoControleHidrometroActionForm.getIndicadorUso().equals("")) {

			indicadorDeGeracao = new Short(""
					+ filtrarRetornoControleHidrometroActionForm.getIndicadorUso());
		}
		
		Short indicadorDeUso = null;

		if (filtrarRetornoControleHidrometroActionForm.getIndicadorUso() != null
				&& !filtrarRetornoControleHidrometroActionForm.getIndicadorUso().equals("")) {

			indicadorDeUso = new Short(""
					+ filtrarRetornoControleHidrometroActionForm.getIndicadorUso());
		}
		
		// seta os parametros que ser�o mostrados no relat�rio

		retornoControleHidrometroParametros.setId(id == null ? null : new Integer(id));
		retornoControleHidrometroParametros.setDescricao(""
				+filtrarRetornoControleHidrometroActionForm.getDescricao());
		retornoControleHidrometroParametros.setIndicadorGeracao(indicadorDeGeracao);
		retornoControleHidrometroParametros.setIndicadorGeracao(indicadorDeUso);
	

		// Fim da parte que vai mandar os parametros para o relat�rio

		// cria uma inst�ncia da classe do relat�rio
		RelatorioManterRetornoControleHidrometro relatorioManterRetornoControleHidrometro = new RelatorioManterRetornoControleHidrometro(
				(Usuario)(httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));
		relatorioManterRetornoControleHidrometro.addParametro("filtroRetornoControleHidrometro",
				filtroRetornoControleHidrometro);
		relatorioManterRetornoControleHidrometro.addParametro("retornoControleHidrometroParametros",
				retornoControleHidrometroParametros);

		// chama o met�do de gerar relat�rio passando o c�digo da analise
		// como par�metro
		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");
		if (tipoRelatorio == null) {
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		relatorioManterRetornoControleHidrometro.addParametro("tipoFormatoRelatorio", Integer
				.parseInt(tipoRelatorio));
		try {
			retorno = processarExibicaoRelatorio(relatorioManterRetornoControleHidrometro,
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
