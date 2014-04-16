package gcom.gui.cadastro.imovel;

import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * < <Descri��o da Classe>>
 * 
 * @author Administrador
 */
public class RemoverImovelSituacaoCobrancaAction extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		ActionForward retorno = actionMapping.findForward("telaSucesso");

		InformarImovelSituacaoCobrancaActionForm informarImovelSituacaoCobrancaActionForm = (InformarImovelSituacaoCobrancaActionForm) actionForm;
		
		Integer idImovel = new Integer(informarImovelSituacaoCobrancaActionForm.getIdImovel());
		
		HttpSession sessao = httpServletRequest.getSession(false);
		
		Usuario usuarioLogado = (Usuario)sessao.getAttribute(Usuario.USUARIO_LOGADO);
		
		httpServletRequest.getAttribute("situacoes");
		
		Fachada.getInstancia().retirarImovelSitucaoCobranca(idImovel, usuarioLogado,
				informarImovelSituacaoCobrancaActionForm.getIdRegistrosRemocao());
		
//		montando pagina de sucesso
		montarPaginaSucesso(httpServletRequest, "Situa��o de Cobran�a do Im�vel retirada com sucesso.",
				"Informar outra Situa��o de Cobranca de Im�vel",
				"exibirInformarImovelSituacaoCobrancaAction.do?menu=sim");
		

		return retorno;
	}
}
