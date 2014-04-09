package gcom.gui.arrecadacao.pagamento;

import gcom.gui.GcomAction;
import gcom.gui.StatusWizard;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action respons�vel por configurar todo o processo de inserir pagamentos para faturas especiais
 * [UC0971] Inserir Pagamentos para Faturas Especiais
 * 
 * @author 	Vivianne Sousa
 * @created	21/12/2009
 */
public class ExibirInserirPagamentosFaturasEspeciaisAction extends GcomAction {

    public ActionForward execute(ActionMapping actionMapping,
            					 ActionForm actionForm, 
            					 HttpServletRequest httpServletRequest,
            					 HttpServletResponse httpServletResponse) {

        //Localiza o action no objeto actionmapping
        ActionForward retorno = actionMapping.findForward("inserirPagamentosFaturasEspeciaisAvisoBancario");

        //Obt�m a inst�ncia da sess�o
        HttpSession sessao = httpServletRequest.getSession(false);

        //Limpa a sess�o
        sessao.removeAttribute("colecaoFormaArrecadacao");
        sessao.removeAttribute("colecaoInserirPagamentoViaCanetaHelper");

        //Monta o Status do Wizard
        StatusWizard statusWizard = new StatusWizard(
                "inserirPagamentosFaturasEspeciaisWizardAction", "inserirPagamentosFaturasEspeciaisAction",
                "cancelarInserirPagamentosFaturasEspeciaisAction","exibirInserirPagamentosFaturasEspeciaisAction.do");
        
        //monta a primeira aba do processo
        statusWizard.inserirNumeroPaginaCaminho(statusWizard.new StatusWizardItem(
                        1, "AvisoBancarioA.gif", "AvisoBancarioD.gif",
                        "exibirInserirPagamentosFaturasEspeciaisAvisoBancarioAction",
                        "inserirPagamentosFaturasEspeciaisAvisoBancarioAction"));
        
        //monta a segunda aba do processo
        statusWizard.inserirNumeroPaginaCaminho(statusWizard.new StatusWizardItem(
                        2, "TipoInclusaoA.gif", "TipoInclusaoD.gif",
                        "exibirInserirPagamentosFaturasEspeciaisTipoInclusaoCanetaAction",
                        "inserirPagamentosFaturasEspeciaisTipoInclusaoCanetaAction"));
        
        //manda o statusWizard para a sess�o
        sessao.setAttribute("statusWizard", statusWizard);

        //retorna o mapeamento contido na vari�vel retorno
        return retorno;
    }
}
