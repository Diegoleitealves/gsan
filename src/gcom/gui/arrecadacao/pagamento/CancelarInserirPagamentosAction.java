package gcom.gui.arrecadacao.pagamento;

import gcom.gui.GcomAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * Action respons�vel pelo cancelamento de inserir pagamentos
 * Este action � respons�vel tamb�m por remover da sess�o todos os 
 * objetos utilizados no processode inserir pagamentos
 *
 * @author Pedro Alexandre
 * @date 16/02/2006
 */
public class CancelarInserirPagamentosAction extends GcomAction {

   
    /**
     * <Breve descri��o sobre o caso de uso>
     *
     * <Identificador e nome do caso de uso>
     *
     * <Breve descri��o sobre o subfluxo>
     *
     * <Identificador e nome do subfluxo>	
     *
     * <Breve descri��o sobre o fluxo secund�rio>
     *
     * <Identificador e nome do fluxo secund�rio> 
     *
     * @author Pedro Alexandre
     * @date 16/02/2006
     *
     * @param actionMapping
     * @param actionForm
     * @param httpServletRequest
     * @param httpServletResponse
     * @return
     */
    public ActionForward execute(ActionMapping actionMapping,
            ActionForm actionForm, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {

    	//Seta o mapeamento de retorno para a tela principal
        ActionForward retorno = actionMapping.findForward("telaPrincipal");

        //Obt�m a inst�ncia da sess�o
        HttpSession sessao = httpServletRequest.getSession(false);

        //Limpa a sess�o
        sessao.removeAttribute("colecaoFormaArrecadacao");
        sessao.removeAttribute("PagamentoActionForm");
        sessao.removeAttribute("colecaoDocumentoTipo");
        sessao.removeAttribute("colecaoPagamentos");
        sessao.removeAttribute("colecaoInserirPagamentoViaCanetaHelper");

        //Retorna o mapeamento contido na vari�vel retorno
        return retorno;
    }
}
