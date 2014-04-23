package gcom.gui.micromedicao;

import gcom.gui.WizardAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Description of the Class
 * 
 * @author Rodrigo
 */
public class FiltrarExcecoesLeiturasConsumosWizardAction extends WizardAction {

    
	
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
   public ActionForward validarExcecoesLeiturasConsumosLocalizacao(
            ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        //recebe o parametros e consulta o objeto da sessao para chamar outro
        // metodo desta classe
        new ValidarExcecoesLeiturasConsumosLocalizacaoAction().execute(actionMapping, actionForm,
                httpServletRequest, httpServletResponse);
        return this.redirecionadorWizard(actionMapping, actionForm,
                httpServletRequest, httpServletResponse);
    }

  
    public ActionForward exibirFiltrarExcecoesLeiturasConsumosLocalidade(
            ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        //recebe o parametros e consulta o objeto da sessao para chamar outro
        // metodo desta classe
        return new ExibirFiltrarExcecoesLeiturasConsumosLocalidadeAction().execute(actionMapping,
                actionForm, httpServletRequest, httpServletResponse);
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
    
    public ActionForward validarExcecoesLeiturasConsumosCaracteristica(
            ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        //recebe o parametros e consulta o objeto da sessao para chamar outro
        // metodo desta classe
        new ValidarExcecoesLeiturasConsumosCaracteristicaAction().execute(actionMapping, actionForm,
                httpServletRequest, httpServletResponse);
        return this.redirecionadorWizard(actionMapping, actionForm,
                httpServletRequest, httpServletResponse);
    }

    public ActionForward exibirFiltrarExcecoesLeiturasConsumosCaracteristicas(
            ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
    	return new ExibirFiltrarExcecoesLeiturasConsumosCaracteristicasAction().execute(actionMapping,
                actionForm, httpServletRequest, httpServletResponse);
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
    
    public ActionForward validarExcecoesLeiturasConsumosAnormalidade(
            ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        //recebe o parametros e consulta o objeto da sessao para chamar outro
        // metodo desta classe
        new ValidarExcecoesLeiturasConsumosAnormalidadeAction().execute(actionMapping, actionForm,
                httpServletRequest, httpServletResponse);
        return this.redirecionadorWizard(actionMapping, actionForm,
                httpServletRequest, httpServletResponse);
    }

    public ActionForward exibirFiltrarExcecoesLeiturasConsumosAnormalidade(
            ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
    	return new ExibirFiltrarExcecoesLeiturasConsumosAnormalidadeAction().execute(actionMapping,
                actionForm, httpServletRequest, httpServletResponse);
    }      
    
    public ActionForward filtrarExcecoesLeiturasConsumosAction(
            ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
    	return new FiltrarExcecoesLeiturasConsumosAction().execute(actionMapping,
                actionForm, httpServletRequest, httpServletResponse);
    }
    


}
