package gcom.gui.cobranca;

import gcom.gui.WizardAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC0214] - Efetuar Parcelamento de D�bitos
 * 		Permite Efetuar Parcelamento de D�bitos de um im�vel
 * 
 * Navega��o das Abas e Conclus�o  
 * 
 * @author  Rodrigo Avellar/Roberta Costa
 * @created 24/01/2006
 */
public class EfetuarParcelamentoDebitosWizardAction extends WizardAction {

	/**
	 * Redireciona para o exibir que � respons�vel pelas valida��es do lado cliente (formul�rio) da aba 1
	 */
	public ActionForward exibirProcesso1Action(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		return new
			 ExibirEfetuarParcelamentoDebitosProcesso1Action()
			 	.execute(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}

	/**
	 * Redireciona para o exibir que � respons�vel pelas valida��es do lado cliente (formul�rio) da aba 1
	 */
	public ActionForward exibirEfetuarParcelamentoDebitosProcesso1Action(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		return new
			 ExibirEfetuarParcelamentoDebitosProcesso1Action()
			 	.execute(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}

	
	/**
	 * Redireciona para o exibir que � respons�vel pelas valida��es do lado cliente (formul�rio) da aba 2
	 */
	public ActionForward exibirProcesso2Action(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		 
		return new
		 	ExibirEfetuarParcelamentoDebitosProcesso2Action()
		 		.execute(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}

	/**
	 * Redireciona para o exibir que � respons�vel pelas valida��es do lado cliente (formul�rio) da aba 2
	 */
	public ActionForward exibirEfetuarParcelamentoDebitosProcesso2Action(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		 
		return new
		 	ExibirEfetuarParcelamentoDebitosProcesso2Action()
		 		.execute(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}

	
	/**
	 * Redireciona para o exibir que � respons�vel pelas valida��es do lado cliente (formul�rio) da aba 3
	 */
	public ActionForward exibirProcesso3Action(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		 return new
		 	ExibirEfetuarParcelamentoDebitosProcesso3Action()
		 		.execute(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}

	/**
	 * Redireciona para o exibir que � respons�vel pelas valida��es do lado cliente (formul�rio) da aba 3
	 */
	public ActionForward exibirEfetuarParcelamentoDebitosProcesso3Action(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		 return new
		 	ExibirEfetuarParcelamentoDebitosProcesso3Action()
		 		.execute(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}

	
	/**
	 * Redireciona para o exibir que � respons�vel pelas valida��es do lado cliente (formul�rio) da aba 4
	 */
	public ActionForward exibirProcesso4Action(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		 return new
		 	ExibirEfetuarParcelamentoDebitosProcesso4Action()
		 		.execute(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
	
	/**
	 * Redireciona para o exibir que � respons�vel pelas valida��es do lado cliente (formul�rio) da aba 4
	 */
	public ActionForward exibirEfetuarParcelamentoDebitosProcesso4Action(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		 return new
		 	ExibirEfetuarParcelamentoDebitosProcesso4Action()
		 		.execute(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}

	/**
	 * Redireciona para o processar que � respons�vel pelas valida��es do lado servidor (regras de neg�cio) da aba 1
	 */
	public ActionForward processarProcesso1Action(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		 new
		 	ProcessarEfetuarParcelamentoDebitosProcesso1Action()
		 		.execute(actionMapping, actionForm, httpServletRequest, httpServletResponse);
		 return this
		 	.redirecionadorWizard(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
	
	/**
	 * Redireciona para o processar que � respons�vel pelas valida��es do lado servidor (regras de neg�cio) da aba 1
	 */
	public ActionForward processarEfetuarParcelamentoDebitosProcesso1Action(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		 new
		 	ProcessarEfetuarParcelamentoDebitosProcesso1Action()
		 		.execute(actionMapping, actionForm, httpServletRequest, httpServletResponse);
		 return this
		 	.redirecionadorWizard(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}

	/**
	 * Redireciona para o processar que � respons�vel pelas valida��es do lado servidor (regras de neg�cio) da aba 2
	 */
	public ActionForward processarProcesso2Action(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		 new
		 	ProcessarEfetuarParcelamentoDebitosProcesso2Action()
		 		.execute(actionMapping, actionForm, httpServletRequest, httpServletResponse);
		 return this
		 	.redirecionadorWizard(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
	
	/**
	 * Redireciona para o processar que � respons�vel pelas valida��es do lado servidor (regras de neg�cio) da aba 2
	 */
	public ActionForward processarEfetuarParcelamentoDebitosProcesso2Action(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		 new
		 	ProcessarEfetuarParcelamentoDebitosProcesso2Action()
		 		.execute(actionMapping, actionForm, httpServletRequest, httpServletResponse);
		 return this
		 	.redirecionadorWizard(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}

	/**
	 * Redireciona para o processar que � respons�vel pelas valida��es do lado servidor (regras de neg�cio) da aba 3
	 */
	public ActionForward processarProcesso3Action(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		 new
		 	ProcessarEfetuarParcelamentoDebitosProcesso3Action()
		 		.execute(actionMapping, actionForm, httpServletRequest, httpServletResponse);
		 return this
		 	.redirecionadorWizard(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}

	/**
	 * Redireciona para o processar que � respons�vel pelas valida��es do lado servidor (regras de neg�cio) da aba 3
	 */
	public ActionForward processarEfetuarParcelamentoDebitosProcesso3Action(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		 new
		 	ProcessarEfetuarParcelamentoDebitosProcesso3Action()
		 		.execute(actionMapping, actionForm, httpServletRequest, httpServletResponse);
		 return this
		 	.redirecionadorWizard(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
	
	/**
	 * Redireciona para o processar que � respons�vel pelas valida��es do lado servidor (regras de neg�cio) da aba 4
	 */
	public ActionForward processarProcesso4Action(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		 new
		 	ProcessarEfetuarParcelamentoDebitosProcesso4Action()
		 		.execute(actionMapping, actionForm, httpServletRequest, httpServletResponse);
		 return this
		 	.redirecionadorWizard(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
	
	/**
	 * Redireciona para o processar que � respons�vel pelas valida��es do lado servidor (regras de neg�cio) da aba 4
	 */
	public ActionForward processarEfetuarParcelamentoDebitosProcesso4Action(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		 new
		 	ProcessarEfetuarParcelamentoDebitosProcesso4Action()
		 		.execute(actionMapping, actionForm, httpServletRequest, httpServletResponse);
		 return this
		 	.redirecionadorWizard(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}

	public ActionForward concluirProcessoAction(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		 return new ConcluirEfetuarParcelamentoDebitosAction().execute(actionMapping,
		 		actionForm, httpServletRequest, httpServletResponse);
	}
}
