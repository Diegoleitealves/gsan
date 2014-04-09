package gcom.gui.seguranca.acesso;

import gcom.gui.GcomAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;



/**
 * Action respons�vel pelo cancelamento da atualiza��o de um grupo de usu�rios,
 * e por remover da sess�o todos os objetos inseridos.  
 *
 * @author Pedro Alexandre
 * @date 26/06/2006
 */
public class CancelarAtualizarGrupoAction extends GcomAction {

   
    /**
     * <Breve descri��o sobre o caso de uso>
     *
     * [UC0279] - Manter Grupo
     *
     * @author Pedro Alexandre
     * @date 26/06/2006
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

    	//Seta o mapeamento de retorno para a tela principal
        ActionForward retorno = actionMapping.findForward("telaPrincipal");

        //Obt�m a inst�ncia da sess�o
        HttpSession sessao = httpServletRequest.getSession(false);

        //Limpa a sess�o
        sessao.removeAttribute("grupo");
        sessao.removeAttribute("GrupoActionForm");
        sessao.removeAttribute("grupoFuncionalidades");

        //Retorna o mapeamento contido na vari�vel retorno
        return retorno;
    }
}
