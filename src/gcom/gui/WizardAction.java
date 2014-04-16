package gcom.gui;

import gcom.gui.StatusWizard.StatusWizardItem;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

/**
 * < <Descri��o da Classe>>
 * 
 * @author rodrigo
 */
public class WizardAction extends DispatchAction {

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
	protected ActionForward redirecionadorWizard(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		if (httpServletRequest.getAttribute("confirmacao") != null
				&& ((String) httpServletRequest.getAttribute("confirmacao"))
						.trim().equalsIgnoreCase("true")) {
			return actionMapping.findForward("telaConfirmacao");
		} else {

			HttpSession sessao = httpServletRequest.getSession(false);
			StatusWizard statusWizard = (StatusWizard) sessao
					.getAttribute("statusWizard");
			String destino = httpServletRequest.getParameter("destino");
			String concluir = null;

			if (httpServletRequest.getParameter("concluir") != null
					&& !httpServletRequest.getParameter("concluir").equals("")) {
				concluir = httpServletRequest.getParameter("concluir");
			} else if (httpServletRequest.getAttribute("concluir") != null
					&& !httpServletRequest.getAttribute("concluir").equals("")) {
				concluir = (String) httpServletRequest.getAttribute("concluir");
			}

			String proximoCaminhoAction = null;

			if (concluir != null && concluir.trim().equalsIgnoreCase("true")) {
				// se o action for o de concluir validar o form
				ActionErrors errors = actionForm.validate(actionMapping,
						httpServletRequest);

				if (errors != null && !errors.isEmpty()) {
					saveErrors(httpServletRequest, errors);
					return actionMapping.findForward("telaErrosApresentacao");

				}

				proximoCaminhoAction = statusWizard.getCaminhoActionConclusao();
			} else {

				StatusWizardItem statusWizardItem = statusWizard
						.retornarItemWizard(Integer.parseInt(destino));

				proximoCaminhoAction = statusWizardItem
						.getCaminhoActionInicial();
			}

			try {
				return ((ActionForward) getClass().getMethod(
						proximoCaminhoAction,
						new Class[] { ActionMapping.class, ActionForm.class,
								HttpServletRequest.class,
								HttpServletResponse.class }).invoke(
						this,
						new Object[] { actionMapping, actionForm,
								httpServletRequest, httpServletResponse }));
			} catch (SecurityException ex) {
				throw new ActionServletException("erro.sistema", ex);
			} catch (NoSuchMethodException ex) {
				throw new ActionServletException("erro.sistema", ex);
			} catch (InvocationTargetException ex) {
				// caso o m�todo execute jogue ActionServletException ou
				// ControladorException
				throw ((RuntimeException) ex.getCause());
			} catch (IllegalArgumentException ex) {
				throw new ActionServletException("erro.sistema", ex);
			} catch (IllegalAccessException ex) {
				throw new ActionServletException("erro.sistema", ex);
			}
		}
	}

}
