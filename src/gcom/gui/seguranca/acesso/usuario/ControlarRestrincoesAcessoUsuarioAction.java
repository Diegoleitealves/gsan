package gcom.gui.seguranca.acesso.usuario;

import gcom.gui.GcomAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action respons�vel por chamar o wizard para definir qual a pr�xima a��o
 * 
 * @author S�vio Luiz
 * @date 10/07/2006
 */
public class ControlarRestrincoesAcessoUsuarioAction extends GcomAction {

	/**
	 * <Breve descri��o sobre o caso de uso>
	 * 
	 * [UC0231] - Manter Usu�rio [SB0003] - Controlar Acessos Usu�rios
	 * 
	 * @author Pedro Alexandre
	 * @date 15/06/2006
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

		ActionForward retorno = null;
		
		//ControlarAcessoUsuarioActionForm controlarAcessoUsuarioActionForm = (ControlarAcessoUsuarioActionForm) actionForm;


		return retorno;
	}
}
