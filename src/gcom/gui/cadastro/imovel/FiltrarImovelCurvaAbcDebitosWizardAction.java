package gcom.gui.cadastro.imovel;

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
public class FiltrarImovelCurvaAbcDebitosWizardAction extends WizardAction {
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
    
	public ActionForward exibirFiltrarImovelCurvaAbcDebitosParametros(
            ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        //recebe o parametros e consulta o objeto da sessao para chamar outro
        // metodo desta classe
        return new ExibirFiltrarImovelCurvaAbcDebitosParametros().execute(actionMapping,
                actionForm, httpServletRequest, httpServletResponse);
    }
    
	public ActionForward exibirFiltrarImovelCurvaAbcDebitosLocalizacao(
            ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        //recebe o parametros e consulta o objeto da sessao para chamar outro
        // metodo desta classe
        return new ExibirFiltrarImovelCurvaAbcDebitosLocalizacao().execute(actionMapping,
                actionForm, httpServletRequest, httpServletResponse);
    }
    
	public ActionForward exibirFiltrarImovelCurvaAbcDebitosLigacaoAguaEsgoto(
            ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        //recebe o parametros e consulta o objeto da sessao para chamar outro
        // metodo desta classe
        return new ExibirFiltrarImovelCurvaAbcDebitosLigacaoAguaEsgoto().execute(actionMapping,
                actionForm, httpServletRequest, httpServletResponse);
    }
    
	public ActionForward exibirFiltrarImovelCurvaAbcDebitosCaracteristicas(
            ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        //recebe o parametros e consulta o objeto da sessao para chamar outro
        // metodo desta classe
        return new ExibirFiltrarImovelCurvaAbcDebitosCaracteristicas().execute(actionMapping,
                actionForm, httpServletRequest, httpServletResponse);
    }
	
	public ActionForward exibirFiltrarImovelCurvaAbcDebitosDebito(
            ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        //recebe o parametros e consulta o objeto da sessao para chamar outro
        // metodo desta classe
        return new ExibirFiltrarImovelCurvaAbcDebitosDebito().execute(actionMapping,
                actionForm, httpServletRequest, httpServletResponse);
    }
	
	public ActionForward filtrarImovelCurvaAbcDebitos(ActionMapping actionMapping,
            ActionForm actionForm, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        //recebe o parametros e consulta o objeto da sessao para chamar outro
        // metodo desta classe
        return new FiltrarImovelCurvaAbcDebitosAction().execute(actionMapping,
                actionForm, httpServletRequest, httpServletResponse);
    }
    
	
    public ActionForward validarImovelCurvaAbcDebitos(
            ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        //recebe o parametros e consulta o objeto da sessao para chamar outro
        // metodo desta classe
        new ValidarImovelCurvaAbcDebitosAction().execute(actionMapping, actionForm,
                httpServletRequest, httpServletResponse);
        return this.redirecionadorWizard(actionMapping, actionForm,
                httpServletRequest, httpServletResponse);
    }
}
