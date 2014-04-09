package gcom.gui.cobranca.spcserasa;

import gcom.gui.WizardAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * Esta classe tem por finalidade gerar as abas que ser�o respons�veis pelo processo de inser��o de um
 * Comando de Negativa��o
 *
 * @author Ana Maria
 * @date 06/11/2007
 */
public class InserirComandoNegativacaPorCriterioWizardAction extends WizardAction {
		
	/*
	 * ABA N� 01 - DADOS GERAIS
	 */
	public ActionForward exibirInserirComandoNegativacaoDadosGeraisAction(
			ActionMapping actionMapping, ActionForm actionForm,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		
		return new ExibirInserirComandoNegativacaoDadosGeraisAction().execute(
				actionMapping, actionForm, httpServletRequest,
				httpServletResponse);
	}
	
	public ActionForward inserirComandoNegativacaoDadosGeraisAction(
			ActionMapping actionMapping, ActionForm actionForm,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		
		new InserirComandoNegativacaoDadosGeraisAction().execute(actionMapping,
				actionForm, httpServletRequest, httpServletResponse);
		
		return this.redirecionadorWizard(actionMapping, actionForm,
				httpServletRequest, httpServletResponse);
	}
	
	/*
	 * ABA N� 02 - DADOS DO D�BITO
	 */
	public ActionForward exibirInserirComandoNegativacaoDadosDebitoAction(
			ActionMapping actionMapping, ActionForm actionForm,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		
		return new ExibirInserirComandoNegativacaoDadosDebitoAction().execute(
				actionMapping, actionForm, httpServletRequest,
				httpServletResponse);
	}
	
	public ActionForward inserirComandoNegativacaoDadosDebitoAction(
			ActionMapping actionMapping, ActionForm actionForm,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		
		new InserirComandoNegativacaoDadosDebitoAction().execute(actionMapping,
				actionForm, httpServletRequest, httpServletResponse);
		
		return this.redirecionadorWizard(actionMapping, actionForm,
				httpServletRequest, httpServletResponse);
	}

	/*
	 * ABA N� 03 - DADOS DO IMOVEL
	 */
	public ActionForward exibirInserirComandoNegativacaoDadosImovelAction(
			ActionMapping actionMapping, ActionForm actionForm,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		
		return new ExibirInserirComandoNegativacaoDadosImovelAction().execute(
				actionMapping, actionForm, httpServletRequest,
				httpServletResponse);
	}
	
	public ActionForward inserirComandoNegativacaoDadosImovelAction(
			ActionMapping actionMapping, ActionForm actionForm,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		
		new InserirComandoNegativacaoDadosImovelAction().execute(actionMapping,
				actionForm, httpServletRequest, httpServletResponse);
		
		return this.redirecionadorWizard(actionMapping, actionForm,
				httpServletRequest, httpServletResponse);
	}
	
	/*
	 * ABA N� 04 - LOCALIZA��O
	 */
	public ActionForward exibirInserirComandoNegativacaoLocalizacaoAction(
			ActionMapping actionMapping, ActionForm actionForm,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		
		return new ExibirInserirComandoNegativacaoLocalizacaoAction().execute(
				actionMapping, actionForm, httpServletRequest,
				httpServletResponse);
	}
	
	public ActionForward inserirComandoNegativacaoLocalizacaoAction(
			ActionMapping actionMapping, ActionForm actionForm,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		
		new InserirComandoNegativacaoLocalizacaoAction().execute(actionMapping,
				actionForm, httpServletRequest, httpServletResponse);
		
		return this.redirecionadorWizard(actionMapping, actionForm,
				httpServletRequest, httpServletResponse);
	}
	
	/*
	 * CONCLUIR
	 */
	public ActionForward concluirInserirComandoNegativacaoPorCriterioAction(
			ActionMapping actionMapping, ActionForm actionForm,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		return new ConcluirInserirComandoNegativacaoPorCriterioAction().execute(actionMapping,
				actionForm, httpServletRequest, httpServletResponse);
	}
	
	/*
	 * CANCELAR
	 */	 
	public ActionForward cancelarInserirComandoNegativacaoAction(
			ActionMapping actionMapping, ActionForm actionForm,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		return new CancelarInserirComandoNegativacaoAction().execute(actionMapping,
				actionForm, httpServletRequest, httpServletResponse);
	}

	
	/*
	 * ABA N� 05 - EXCLUS�O
	 */
	public ActionForward exibirInserirComandoNegativacaoExclusaoAction(
			ActionMapping actionMapping, ActionForm actionForm,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		
		return new ExibirInserirComandoNegativacaoExclusaoAction().execute(
				actionMapping, actionForm, httpServletRequest,
				httpServletResponse);
	}
	
	public ActionForward inserirComandoNegativacaoExclusaoAction(
			ActionMapping actionMapping, ActionForm actionForm,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		
		new InserirComandoNegativacaoExclusaoAction().execute(actionMapping,
				actionForm, httpServletRequest, httpServletResponse);
		
		return this.redirecionadorWizard(actionMapping, actionForm,
				httpServletRequest, httpServletResponse);
	}
	
}
