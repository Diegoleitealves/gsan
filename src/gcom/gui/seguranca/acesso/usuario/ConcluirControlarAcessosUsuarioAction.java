package gcom.gui.seguranca.acesso.usuario;

import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.usuario.Usuario;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action conclui o Manter Usuario
 * 
 * @author S�vio Luiz
 * @date 09/05/2006
 */
public class ConcluirControlarAcessosUsuarioAction extends GcomAction {

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
	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		ActionForward retorno = actionMapping.findForward("telaSucesso");

		HttpSession sessao = httpServletRequest.getSession(false);

		ControlarAcessoUsuarioActionForm controlarAcessoUsuarioActionForm = (ControlarAcessoUsuarioActionForm) actionForm;

		// Usuario que vai ser cadastrado no sistema, usado s� nessa
		// funcionalidade
		Usuario usuarioParaAtualizar = (Usuario) sessao
				.getAttribute("usuarioParaAtualizar");

		Integer[] idsGrupos = (Integer[]) sessao.getAttribute("grupo");

		// Recupera os acessos do grupo da sess�o
		Map<Integer, Map<Integer, Collection<Operacao>>> funcionalidadesMap = (Map<Integer, Map<Integer, Collection<Operacao>>>) sessao
				.getAttribute("funcionalidadesMap");

		String[] idsPermissoesEspeciais = controlarAcessoUsuarioActionForm
				.getPermissoesEspeciais();
		
		String permissoesCheckBoxVazias = controlarAcessoUsuarioActionForm.getPermissoesCheckBoxVazias();

		//Passa o usu�rio logado para registrar opera��o.
    	Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");
    	
		Fachada.getInstancia().atualizarControleAcessoUsuario(
				idsPermissoesEspeciais, funcionalidadesMap,
				usuarioParaAtualizar, idsGrupos,permissoesCheckBoxVazias, usuarioLogado);

		montarPaginaSucesso(httpServletRequest, "Usu�rio de Login "
				+ usuarioParaAtualizar.getLogin() + " atualizado com sucesso!",
				"Realizar outra Manuten��o de Usu�rio",
				"exibirFiltrarUsuarioAction.do?menu=sim");

		sessao.removeAttribute("usuario");
		sessao.removeAttribute("grupo");
		sessao.removeAttribute("usuarioParaAtualizar");

		return retorno;
	}
}
