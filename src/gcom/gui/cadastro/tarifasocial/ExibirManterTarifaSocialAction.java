package gcom.gui.cadastro.tarifasocial;

import gcom.gui.GcomAction;
import gcom.gui.StatusWizard;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * < <Descri��o da Classe>>
 * 
 * @author rodrigo
 */
public class ExibirManterTarifaSocialAction extends GcomAction {

    /**
     * < <Descri��o do m�todo>> 0
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

        // localiza o action no objeto actionmapping
        ActionForward retorno = actionMapping
                .findForward("exibirManterTarifaSocial");

        //obt�m a inst�ncia da sess�o
        HttpSession sessao = httpServletRequest.getSession(false);

        //limpa a sess�o
        sessao.removeAttribute("colecaoImovelEconomia");
        sessao.removeAttribute("clienteImovel");
        sessao.removeAttribute("quantEconomias");
        sessao.removeAttribute("colecaoDadosTarifaSocial");
        sessao.removeAttribute("colecaoImovelSubcategoria");
        sessao.removeAttribute("ManterTarifaSocialActionForm");

        StatusWizard statusWizard = new StatusWizard(
                "manterTarifaSocialWizardAction", "manterTarifaSocialAction",
                "cancelarManterTarifaSocialAction", "exibirManterTarifaSocialAction.do");
        statusWizard
                .inserirNumeroPaginaCaminho(statusWizard.new StatusWizardItem(
                        1, "TarifaSocialImovelA.gif",
                        "TarifaSocialImovelD.gif",
                        "exibirManterTarifaSocialImovelAction",
                        "manterTarifaSocialImovelAction"));
        statusWizard
                .inserirNumeroPaginaCaminho(statusWizard.new StatusWizardItem(
                        2, "TarifaSocialTarifaA.gif",
                        "TarifaSocialTarifaD.gif",
                        "exibirManterTarifaSocialDadosEconomiaAction",
                        "manterTarifaSocialDadosEconomiaAction"));

        //manda o statusWizard para a sessao
        sessao.setAttribute("statusWizard", statusWizard);

        return retorno;
    }
}
