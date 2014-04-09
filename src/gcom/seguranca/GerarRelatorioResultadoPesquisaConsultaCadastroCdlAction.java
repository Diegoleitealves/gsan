package gcom.seguranca;


import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.seguranca.RelatorioResultadoPesquisaConsultaCadastroCdl;
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
 * @author Rodrigo de Abreu Cabral
 * @version 1.0
 */

public class GerarRelatorioResultadoPesquisaConsultaCadastroCdlAction extends
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

		HttpSession sessao = httpServletRequest.getSession(false);

		FiltrarConsultaCadastroCdlActionForm form = (FiltrarConsultaCadastroCdlActionForm) actionForm;
		
		FiltroConsultaCadastroCdl filtroConsultaCadastroCdl = 
			(FiltroConsultaCadastroCdl) sessao.getAttribute("filtroConsultaCadastroCdl");

		
		// Inicio da parte que vai mandar os parametros para o relat�rio

		// seta os parametros que ser�o mostrados no relat�rio
		String periodoAcessoInicial = "";
		
		if(form.getPeriodoAcessoInicial()!= null && !form.getPeriodoAcessoInicial().equals("")){
			
			periodoAcessoInicial = form.getPeriodoAcessoInicial();
		}
		
		String periodoAcessoFinal = "";
		
		if(form.getPeriodoAcessoFinal()!= null && !form.getPeriodoAcessoFinal().equals("")){
			
			periodoAcessoFinal = form.getPeriodoAcessoFinal();
		}
		
		
		
		// Fim da parte que vai mandar os parametros para o relat�rio

		// cria uma inst�ncia da classe do relat�rio
		RelatorioResultadoPesquisaConsultaCadastroCdl relatorioResultadoPesquisaConsultaCadastroCdl = new RelatorioResultadoPesquisaConsultaCadastroCdl(
				(Usuario)(httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));
		relatorioResultadoPesquisaConsultaCadastroCdl.addParametro("filtroConsultaCadastroCdl",
				filtroConsultaCadastroCdl);
		relatorioResultadoPesquisaConsultaCadastroCdl.addParametro("periodoAcessoInicial",
				periodoAcessoInicial);
		relatorioResultadoPesquisaConsultaCadastroCdl.addParametro("periodoAcessoFinal",
				periodoAcessoFinal);
		
		

		// chama o met�do de gerar relat�rio passando o c�digo da analise
		// como par�metro
		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");
		if (tipoRelatorio == null) {
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		relatorioResultadoPesquisaConsultaCadastroCdl.addParametro("tipoFormatoRelatorio", Integer
				.parseInt(tipoRelatorio));
		try {
			retorno = processarExibicaoRelatorio(relatorioResultadoPesquisaConsultaCadastroCdl,
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
