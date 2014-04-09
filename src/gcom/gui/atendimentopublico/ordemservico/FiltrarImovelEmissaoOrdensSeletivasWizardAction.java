package gcom.gui.atendimentopublico.ordemservico;

import gcom.gui.WizardAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Description of the Class
 * 
 * @author Ivan S�rgio
 */
public class FiltrarImovelEmissaoOrdensSeletivasWizardAction extends WizardAction {
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
    
	public ActionForward exibirFiltrarImovelEmissaoOrdensSeletivasParametros(
            ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
		
		return new ExibirFiltrarImovelEmissaoOrdensSeletivasParametros().execute(actionMapping,
                actionForm, httpServletRequest, httpServletResponse);
    }
    
	public ActionForward exibirFiltrarImovelEmissaoOrdensSeletivasCaracteristicas(
            ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {

		return new ExibirFiltrarImovelEmissaoOrdensSeletivasCaracteristicas().execute(actionMapping,
                actionForm, httpServletRequest, httpServletResponse);
    }
	
	public ActionForward exibirFiltrarImovelEmissaoOrdensSeletivasHidrometro(
            ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
		
		return new ExibirFiltrarImovelEmissaoOrdensSeletivasHidrometro().execute(actionMapping,
                actionForm, httpServletRequest, httpServletResponse);
    }
	
	public ActionForward filtrarImovelEmissaoOrdensSeletivas(ActionMapping actionMapping,
            ActionForm actionForm, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {

		return new FiltrarImovelEmissaoOrdensSeletivasAction().execute(actionMapping,
                actionForm, httpServletRequest, httpServletResponse);
    }
    
	
    public ActionForward validarImovelEmissaoOrdensSeletivas(
            ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {

    	new ValidarImovelEmissaoOrdensSeletivasAction().execute(actionMapping, actionForm,
    			httpServletRequest, httpServletResponse);
    	
        return this.redirecionadorWizard(actionMapping, actionForm,
                httpServletRequest, httpServletResponse);
    }
}
