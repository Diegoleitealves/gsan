package gcom.gui.faturamento;

import gcom.gui.WizardAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Descri��o da classe
 * 
 * @author R�mulo Aur�lio
 * @date 16/09/2008
 */
public class InserirQualidadeAguaWizardAction extends WizardAction {

	/**
	 * Description of the Method
	 */
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
	public ActionForward exibirInserirQualidadeAguaDadosAction(
			ActionMapping actionMapping, ActionForm actionForm,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		// recebe o parametros e consulta o objeto da sessao para chamar outro
		// metodo desta classe
		return new ExibirInserirQualidadeAguaDadosAction().execute(
				actionMapping, actionForm, httpServletRequest,
				httpServletResponse);
	}

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
	public ActionForward exibirInserirQualidadeAguaAnaliseAction(
			ActionMapping actionMapping, ActionForm actionForm,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		return new ExibirInserirQualidadeAguaAnaliseAction().execute(
				actionMapping, actionForm, httpServletRequest,
				httpServletResponse);
	}

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
	public ActionForward inserirQualidadeAguaDadosAction(
			ActionMapping actionMapping, ActionForm actionForm,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		new InserirQualidadeAguaDadosAction().execute(actionMapping,
				actionForm, httpServletRequest, httpServletResponse);
		return this.redirecionadorWizard(actionMapping, actionForm,
				httpServletRequest, httpServletResponse);
	}

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
	public ActionForward inserirQualidadeAguaAnaliseAction(
			ActionMapping actionMapping, ActionForm actionForm,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		new InserirQualidadeAguaAnaliseAction().execute(actionMapping,
				actionForm, httpServletRequest, httpServletResponse);
		return this.redirecionadorWizard(actionMapping, actionForm,
				httpServletRequest, httpServletResponse);
	}

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
	public ActionForward inserirQualidadeAguaAction(
			ActionMapping actionMapping, ActionForm actionForm,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		// recebe o parametros e consulta o objeto da sessao para chamar outro
		// metodo desta classe
		return new InserirQualidadeAguaAction().execute(actionMapping,
				actionForm, httpServletRequest, httpServletResponse);
		
	}

}
