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
 * Action respons�vel por configurar todo o processo de inserir pagamentos
 * [UC0262] Inserir Pagamentos
 * 
 * @author 	Pedro Alexandre
 * @created	16/02/2006
 */
public class ExibirInserirPagamentosAction extends GcomAction {

    /**
     * Description of the Method
     * 
     * @param actionMapping
     *            Description of the Parameter
     * @param actionForm
     *            Description of the Parameter
     * @param httpServletRequest
     *            Description of the Parameter
     * @param httpServletResponse
     *            Description of the Parameter
     * @return Description of the Return Value
     */
    public ActionForward execute(ActionMapping actionMapping,
            					 ActionForm actionForm, 
            					 HttpServletRequest httpServletRequest,
            					 HttpServletResponse httpServletResponse) {

        //Localiza o action no objeto actionmapping
        ActionForward retorno = actionMapping.findForward("inserirPagamentosAvisoBancario");


        //Obt�m a inst�ncia da sess�o
        HttpSession sessao = httpServletRequest.getSession(false);

        //Limpa a sess�o
        sessao.removeAttribute("colecaoFormaArrecadacao");
        sessao.removeAttribute("PagamentoActionForm");
        sessao.removeAttribute("colecaoDocumentoTipo");
        sessao.removeAttribute("colecaoPagamentos");
        sessao.removeAttribute("colecaoInserirPagamentoViaCanetaHelper");
        

        //Monta o Status do Wizard
        StatusWizard statusWizard = new StatusWizard(
                "inserirPagamentosWizardAction", "inserirPagamentosAction",
                "cancelarInserirPagamentosAction","exibirInserirPagamentosAction.do");
        
        //monta a primeira aba do processo
        statusWizard
                .inserirNumeroPaginaCaminho(statusWizard.new StatusWizardItem(
                        1, "AvisoBancarioA.gif", "AvisoBancarioD.gif",
                        "exibirInserirPagamentosAvisoBancarioAction",
                        "inserirPagamentosAvisoBancarioAction"));
        
        //monta a segunda aba do processo,se for leitura do c�digo de barra por caneta
        statusWizard
                .inserirNumeroPaginaCaminho(statusWizard.new StatusWizardItem(
                        2, "TipoInclusaoA.gif", "TipoInclusaoD.gif",
                        "exibirInserirPagamentosTipoInclusaoCanetaAction",
                        "inserirPagamentosTipoInclusaoCanetaAction"));
        
        //monta a segunda aba do processo,se o c�digo de barras for digitado manualmente
        statusWizard
                .inserirNumeroPaginaCaminho(statusWizard.new StatusWizardItem(
                        2, "TipoInclusaoA.gif", "TipoInclusaoD.gif",
                        "exibirInserirPagamentosTipoInclusaoManualAction",
                        "inserirPagamentosTipoInclusaoManualAction"));
        
        //monta a segunda aba do processo,se o tipo de inclus�o for ficha de compensa��o
        statusWizard
                .inserirNumeroPaginaCaminho(statusWizard.new StatusWizardItem(
                        2, "TipoInclusaoA.gif", "TipoInclusaoD.gif",
                        "exibirInserirPagamentosTipoInclusaoFichaCompensacaoAction",
                        "inserirPagamentosTipoInclusaoFichaCompensacaoAction"));
        
        
        
        //manda o statusWizard para a sess�o
        sessao.setAttribute("statusWizard", statusWizard);

        //retorna o mapeamento contido na vari�vel retorno
        return retorno;
    }
}
