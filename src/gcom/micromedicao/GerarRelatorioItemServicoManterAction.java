package gcom.micromedicao;


import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.micromedicao.RelatorioManterItemServico;
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

public class GerarRelatorioItemServicoManterAction extends
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

		FiltrarItemServicoActionForm form = (FiltrarItemServicoActionForm) actionForm;

		FiltroItemServico filtroItemServico = (FiltroItemServico) sessao
				.getAttribute("filtroItemServico");

		// Inicio da parte que vai mandar os parametros para o relat�rio

		ItemServico itemServicoParametros = new ItemServico();

		Short indicadordeUso = null;
		
		if(form.getIndicadorUso()!= null && !form.getIndicadorUso().equals("")){
			
			indicadordeUso = new Short ("" + form.getIndicadorUso());
			itemServicoParametros.setIndicadorUso(indicadordeUso);
		}
		
		
		// seta os parametros que ser�o mostrados no relat�rio
		
		if(form.getDescricao() != null && !form.getDescricao().equals("")){
			
			itemServicoParametros.setDescricao(form.getDescricao());
			
		}
		
		if(form.getDescricaoAbreviada() != null && !form.getDescricaoAbreviada().equals("")){
			
			itemServicoParametros.setDescricaoAbreviada(form.getDescricaoAbreviada());
		}
		
		
		Integer codigoConstanteCalculo = null;
		
		if(form.getCodigoConstanteCalculo()!= null && !form.getCodigoConstanteCalculo().equals("")){
			
			codigoConstanteCalculo = new Integer ("" + form.getCodigoConstanteCalculo());
			
			itemServicoParametros.setCodigoConstanteCalculo(codigoConstanteCalculo);
		}
		
		Long codigoItem = null;
		
		if(form.getCodigoItem()!= null && !form.getCodigoItem().equals("")){
			
			codigoItem = new Long ("" + form.getCodigoItem());
			
			itemServicoParametros.setCodigoItem(codigoItem);
		}
		
		// Fim da parte que vai mandar os parametros para o relat�rio

		// cria uma inst�ncia da classe do relat�rio
		RelatorioManterItemServico relatorioManterItemServico = new RelatorioManterItemServico(
				(Usuario)(httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));
		relatorioManterItemServico.addParametro("filtroItemServico",
				filtroItemServico);
		relatorioManterItemServico.addParametro("itemServicoParametros",
				itemServicoParametros);

		// chama o met�do de gerar relat�rio passando o c�digo da analise
		// como par�metro
		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");
		if (tipoRelatorio == null) {
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		relatorioManterItemServico.addParametro("tipoFormatoRelatorio", Integer
				.parseInt(tipoRelatorio));
		try {
			retorno = processarExibicaoRelatorio(relatorioManterItemServico,
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
