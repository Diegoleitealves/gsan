package gcom.gui.cadastro.imovel;

import gcom.gui.StatusWizard;
import gcom.gui.WizardAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Description of the Class
 * 
 * @author Rossiter
 */
public class AtualizarImovelWizardAction extends WizardAction {

    // M�TODOS DE EXIBI��O
    // =======================================================

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
    public ActionForward adicionarAtualizarImovelClienteAction(
            ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        //recebe o parametros e consulta o objeto da sessao para chamar outro
        // metodo desta classe
        return new AdicionarAtualizarImovelClienteAction().execute(actionMapping,
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
    public ActionForward exibirAtualizarImovelLocalidadeAction(
            ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        //recebe o parametros e consulta o objeto da sessao para chamar outro
        // metodo desta classe
    	
    	//**********************************************************************
    	// CRC2103
    	// Autor: Ivan Sergio
    	// Data: 28/07/2009
    	// Esta verificacao guarda na sessao o wizard e deve ser feita por conta
    	// do popup de inserir cliente que tambem possui um wizard.
    	//**********************************************************************
    	HttpSession sessao = httpServletRequest.getSession(false);
    	if (sessao.getAttribute("statusWizardAnterior") == null) {
    		StatusWizard statusWizardAnterior = (StatusWizard) sessao.getAttribute("statusWizard");
        	sessao.setAttribute("statusWizardAnterior", statusWizardAnterior);
    	} else {
    		StatusWizard statusWizardAnterior = (StatusWizard) sessao.getAttribute("statusWizardAnterior");
    		sessao.setAttribute("statusWizard", statusWizardAnterior);
    	}
    	//**********************************************************************
    	
        return new ExibirAtualizarImovelLocalidadeAction().execute(
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
    public ActionForward exibirAtualizarImovelEnderecoAction(
            ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
    	
    	//**********************************************************************
    	// CRC2103
    	// Autor: Ivan Sergio
    	// Data: 28/07/2009
    	// Esta verificacao guarda na sessao o wizard e deve ser feita por conta
    	// do popup de inserir cliente que tambem possui um wizard.
    	//**********************************************************************
    	HttpSession sessao = httpServletRequest.getSession(false);
    	if (sessao.getAttribute("statusWizardAnterior") == null) {
    		StatusWizard statusWizardAnterior = (StatusWizard) sessao.getAttribute("statusWizard");
        	sessao.setAttribute("statusWizardAnterior", statusWizardAnterior);
    	} else {
    		StatusWizard statusWizardAnterior = (StatusWizard) sessao.getAttribute("statusWizardAnterior");
    		sessao.setAttribute("statusWizard", statusWizardAnterior);
    	}
    	//**********************************************************************

        return new ExibirAtualizarImovelEnderecoAction().execute(actionMapping,
                actionForm, httpServletRequest, httpServletResponse);
    }

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
    public ActionForward exibirAtualizarImovelPrincipalAction(
            ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        //recebe o parametros e consulta o objeto da sessao para chamar outro
        // metodo desta classe
        return new ExibirAtualizarImovelPrincipalAction().execute(
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
    public ActionForward exibirAtualizarImovelClienteAction(
            ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
    	
    	//**********************************************************************
    	// CRC2103
    	// Autor: Ivan Sergio
    	// Data: 28/07/2009
    	// Esta verificacao guarda na sessao o wizard e deve ser feita por conta
    	// do popup de inserir cliente que tambem possui um wizard.
    	//**********************************************************************
    	HttpSession sessao = httpServletRequest.getSession(false);
    	if (sessao.getAttribute("statusWizardAnterior") == null) {
    		StatusWizard statusWizardAnterior = (StatusWizard) sessao.getAttribute("statusWizard");
        	sessao.setAttribute("statusWizardAnterior", statusWizardAnterior);
    	} else {
    		StatusWizard statusWizardAnterior = (StatusWizard) sessao.getAttribute("statusWizardAnterior");
    		sessao.setAttribute("statusWizard", statusWizardAnterior);
    	}
    	//**********************************************************************

        return new ExibirAtualizarImovelClienteAction().execute(actionMapping,
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
    public ActionForward exibirAtualizarImovelSubCategoriaAction(
            ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        //recebe o parametros e consulta o objeto da sessao para chamar outro
        // metodo desta classe
        return new ExibirAtualizarImovelSubCategoriaAction().execute(
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
    public ActionForward exibirAtualizarImovelCaracteristicasAction(
            ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {

        return new ExibirAtualizarImovelCaracteristicasAction().execute(
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
    public ActionForward exibirAtualizarImovelConclusaoAction(
            ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {

        return new ExibirAtualizarImovelConclusaoAction().execute(
                actionMapping, actionForm, httpServletRequest,
                httpServletResponse);
    }

    // FIM DOS M�TODOS DE EXIBI��O =============================================
    //==========================================================================

    // M�TODOS DE INSER��O =====================================================

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
    public ActionForward atualizarImovelLocalidadeAction(
            ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        new AtualizarImovelLocalidadeAction().execute(actionMapping,
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
    public ActionForward atualizarImovelEnderecoAction(
            ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        new AtualizarImovelEnderecoAction().execute(actionMapping, actionForm,
                httpServletRequest, httpServletResponse);
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
    public ActionForward atualizarImovelPrincipalAction(
            ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        new AtualizarImovelPrincipalAction().execute(actionMapping, actionForm,
                httpServletRequest, httpServletResponse);
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
    public ActionForward atualizarImovelClienteAction(
            ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        new AtualizarImovelClienteAction().execute(actionMapping, actionForm,
                httpServletRequest, httpServletResponse);
        
    	//**********************************************************************
    	// CRC2103
    	// Autor: Ivan Sergio
    	// Data: 28/07/2009
    	// Esta verificacao guarda na sessao o wizard e deve ser feita por conta
    	// do popup de inserir cliente que tambem possui um wizard.
    	//**********************************************************************
    	HttpSession sessao = httpServletRequest.getSession(false);
    	if (sessao.getAttribute("statusWizardAnterior") == null) {
    		StatusWizard statusWizardAnterior = (StatusWizard) sessao.getAttribute("statusWizard");
        	sessao.setAttribute("statusWizardAnterior", statusWizardAnterior);
    	} else {
    		StatusWizard statusWizardAnterior = (StatusWizard) sessao.getAttribute("statusWizardAnterior");
    		sessao.setAttribute("statusWizard", statusWizardAnterior);
    	}
    	//**********************************************************************
        
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
    public ActionForward atualizarImovelSubCategoriaAction(
            ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        new AtualizarImovelSubCategoriaAction().execute(actionMapping,
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
    public ActionForward atualizarImovelCaracteristicasAction(
            ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        new AtualizarImovelCaracteristicasAction().execute(actionMapping,
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
    public ActionForward atualizarImovelConclusaoAction(
            ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        new AtualizarImovelConclusaoAction().execute(actionMapping, actionForm,
                httpServletRequest, httpServletResponse);
        return this.redirecionadorWizard(actionMapping, actionForm,
                httpServletRequest, httpServletResponse);
    }

    // FIM DOS M�TODOS DE EXIBI��O =============================================
    //==========================================================================

    // M�TODO PARA CONCLUIR O PROCESSO =========================================

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
    public ActionForward atualizarImovelAction(ActionMapping actionMapping,
            ActionForm actionForm, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        //recebe o parametros e consulta o objeto da sessao para chamar outro
        // metodo desta classe
        return new AtualizarImovelAction().execute(actionMapping, actionForm,
                httpServletRequest, httpServletResponse);
    }

}
