package gcom.gui.relatorio.operacional;

import gcom.gui.operacional.FiltrarSistemaAbastecimentoActionForm;
import gcom.operacional.FiltroSistemaAbastecimento;
import gcom.operacional.FonteCaptacao;
import gcom.operacional.SistemaAbastecimento;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.operacional.RelatorioManterSistemaAbastecimento;
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
 * @author Fernando Fontelles Filho
 * @version 1.0
 */

public class GerarRelatorioSistemaAbastecimentoManterAction extends
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

		FiltrarSistemaAbastecimentoActionForm form = (FiltrarSistemaAbastecimentoActionForm) actionForm;

		FiltroSistemaAbastecimento filtroSistemaAbastecimento = (FiltroSistemaAbastecimento) sessao
				.getAttribute("filtroSistemaAbastecimento");

		// Inicio da parte que vai mandar os parametros para o relat�rio

		SistemaAbastecimento sistemaAbastecimentoParametros = new SistemaAbastecimento();

		String id = null;

		Short indicadordeUso = null;
		
		if(form.getIndicadorUso()!= null && !form.getIndicadorUso().equals("")){
			
			indicadordeUso = new Short ("" + form.getIndicadorUso());
		}
		
		
		// seta os parametros que ser�o mostrados no relat�rio

		sistemaAbastecimentoParametros.setId(id == null ? null : new Integer(
				id));
		
		sistemaAbastecimentoParametros.setDescricao(""
				+ form.getDescricao());
		
		sistemaAbastecimentoParametros.setDescricaoAbreviada(form.getDescricaoAbreviada());
		
		sistemaAbastecimentoParametros.setIndicadorUso(indicadordeUso);
		
		if(form.getIdFonteCaptacao() != null && !form.getIdFonteCaptacao().equals("")){
			
			FonteCaptacao fonteCaptacao = new FonteCaptacao();
			fonteCaptacao.setDescricao(form.getDescricaoFonteCaptacao());
			
			sistemaAbastecimentoParametros.setFonteCaptacao(fonteCaptacao);
			
		}
		
		// Fim da parte que vai mandar os parametros para o relat�rio

		// cria uma inst�ncia da classe do relat�rio
		RelatorioManterSistemaAbastecimento relatorioManterSistemaAbastecimento = new RelatorioManterSistemaAbastecimento(
				(Usuario)(httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));
		relatorioManterSistemaAbastecimento.addParametro("filtroSistemaAbastecimento",
				filtroSistemaAbastecimento);
		relatorioManterSistemaAbastecimento.addParametro("sistemaAbastecimentoParametros",
				sistemaAbastecimentoParametros);

		// chama o met�do de gerar relat�rio passando o c�digo da analise
		// como par�metro
		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");
		if (tipoRelatorio == null) {
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		relatorioManterSistemaAbastecimento.addParametro("tipoFormatoRelatorio", Integer
				.parseInt(tipoRelatorio));
		try {
			retorno = processarExibicaoRelatorio(relatorioManterSistemaAbastecimento,
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
