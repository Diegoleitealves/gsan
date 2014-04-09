package gcom.gui.faturamento;

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
public class AtualizarComandoAtividadeFaturamentoWizardAction extends
        WizardAction {

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
    public ActionForward exibirAtualizarComandoAtividadeFaturamentoDadosAction(
            ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        //recebe o parametros e consulta o objeto da sessao para chamar outro
        // metodo desta classe
        return new ExibirAtualizarComandoAtividadeFaturamentoDadosAction()
                .execute(actionMapping, actionForm, httpServletRequest,
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
    public ActionForward exibirAtualizarComandoAtividadeFaturamentoRotasAction(
            ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        //recebe o parametros e consulta o objeto da sessao para chamar outro
        // metodo desta classe
        return new ExibirAtualizarComandoAtividadeFaturamentoRotasAction()
                .execute(actionMapping, actionForm, httpServletRequest,
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
    public ActionForward exibirAtualizarComandoAtividadeFaturamentoDataVencimentoAction(
            ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        //recebe o parametros e consulta o objeto da sessao para chamar outro
        // metodo desta classe
        return new ExibirAtualizarComandoAtividadeFaturamentoDataVencimentoAction()
                .execute(actionMapping, actionForm, httpServletRequest,
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
    public ActionForward removerSelecaoRotaUniaoAction(
            ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        //recebe o parametros e consulta o objeto da sessao para chamar outro
        // metodo desta classe
        return new RemoverSelecaoRotaUniaoAction().execute(actionMapping,
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
    public ActionForward atualizarComandoAtividadeFaturamentoDadosAction(
            ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        new AtualizarComandoAtividadeFaturamentoDadosAction().execute(
                actionMapping, actionForm, httpServletRequest,
                httpServletResponse);
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
    public ActionForward atualizarComandoAtividadeFaturamentoRotasAction(
            ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        new AtualizarComandoAtividadeFaturamentoRotasAction().execute(
                actionMapping, actionForm, httpServletRequest,
                httpServletResponse);
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
    public ActionForward atualizarComandoAtividadeFaturamentoDataVencimentoAction(
            ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        new AtualizarComandoAtividadeFaturamentoDataVencimentoAction().execute(
                actionMapping, actionForm, httpServletRequest,
                httpServletResponse);
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
    public ActionForward atualizarComandoAtividadeFaturamentoAction(
            ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        //recebe o parametros e consulta o objeto da sessao para chamar outro
        // metodo desta classe
        return new AtualizarComandoAtividadeFaturamentoAction().execute(
                actionMapping, actionForm, httpServletRequest,
                httpServletResponse);
    }

}
