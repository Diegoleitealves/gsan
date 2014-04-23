package gcom.gui.seguranca.acesso;

import gcom.gui.GcomAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action respons�vel por exibir a p�ginade dados gerais do processo 
 * de inserir grupo.
 * @author Pedro Alexandre
 * @date 15/06/2006
 */
public class ExibirInserirGrupoDadosGeraisAction extends GcomAction {
    
    /**
     * <Breve descri��o sobre o caso de uso>
     *
     * [UC0278] Inserir Grupo
     *
     * @author Pedro Alexandre
     * @date 28/06/2006
     *
     * @param actionMapping
     * @param actionForm
     * @param httpServletRequest
     * @param httpServletResponse
     * @return
     */
    public ActionForward execute(ActionMapping actionMapping,
            					ActionForm actionForm, 
            					HttpServletRequest httpServletRequest,
            					HttpServletResponse httpServletResponse) {

    	//Seta o mapeamento de retorno para a tela de dados gerais  
        ActionForward retorno = actionMapping.findForward("inserirGrupoDadosGerais");

        //Retorna o mapemaneto na vari�vel "retorno"
        return retorno;
    }

}
