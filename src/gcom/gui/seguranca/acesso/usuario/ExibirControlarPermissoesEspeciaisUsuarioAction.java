package gcom.gui.seguranca.acesso.usuario;

import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.Grupo;
import gcom.seguranca.acesso.PermissaoEspecial;
import gcom.seguranca.acesso.usuario.FiltroPemissaoEspecial;
import gcom.seguranca.acesso.usuario.FiltroUsuarioGrupo;
import gcom.seguranca.acesso.usuario.FiltroUsuarioPemissaoEspecial;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioGrupo;
import gcom.seguranca.acesso.usuario.UsuarioPermissaoEspecial;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action respons�vel pela pre-exibi��o das permiss�es especiais do usu�rio.
 * 
 * @author S�vio Luiz
 * @date 12/07/2006
 */
public class ExibirControlarPermissoesEspeciaisUsuarioAction extends GcomAction {

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

		ActionForward retorno = actionMapping
				.findForward("controlarPermissoesEspeciaisUsuario");

		ControlarAcessoUsuarioActionForm controlarAcessoUsuarioActionForm = (ControlarAcessoUsuarioActionForm) actionForm;

		HttpSession sessao = httpServletRequest.getSession(false);

		// Recupera os acessos do grupo da sess�o
		//Collection grupoFuncionalidades = (Collection) sessao
		//		.getAttribute("grupoFuncionalidades");

		// Usuario logado no sistema
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		
		// Usuario que vai ser cadastrado no sistema, usado s� nessa
		// funcionalidade
		Usuario usuarioParaAtualizar = (Usuario) sessao
				.getAttribute("usuarioParaAtualizar");

		Collection colecaoPermissaoEspecial = null;

		Collection colecaoPermissaoEspecialDesalibitado = null;
		
		String permissoesCheckBoxVazias = controlarAcessoUsuarioActionForm
		.getPermissoesCheckBoxVazias(); 

		String[] permissaoEspecial = controlarAcessoUsuarioActionForm
				.getPermissoesEspeciais();

		if (permissaoEspecial == null && permissoesCheckBoxVazias == null) {

			// caso o usu�rio que esteja efetuando a inser��o n�o
			// seja
			// do grupo de administradores
			FiltroUsuarioGrupo filtroUsuarioGrupo = new FiltroUsuarioGrupo();

			filtroUsuarioGrupo.adicionarParametro(new ParametroSimples(
					FiltroUsuarioGrupo.USUARIO_ID, usuario.getId()));
			filtroUsuarioGrupo.adicionarParametro(new ParametroSimples(
					FiltroUsuarioGrupo.GRUPO_ID, Grupo.ADMINISTRADOR));
			Collection colecaoUsuarioGrupo = Fachada.getInstancia().pesquisar(
					filtroUsuarioGrupo, UsuarioGrupo.class.getName());
			if (colecaoUsuarioGrupo != null && !colecaoUsuarioGrupo.isEmpty()) {

				FiltroPemissaoEspecial filtroPemissaoEspecial = new FiltroPemissaoEspecial(FiltroPemissaoEspecial.DESCRICAO);
				filtroPemissaoEspecial.adicionarParametro(new ParametroSimples(FiltroPemissaoEspecial.INDICADOR_USO,ConstantesSistema.INDICADOR_USO_ATIVO)); 

				colecaoPermissaoEspecial = Fachada.getInstancia().pesquisar(
						filtroPemissaoEspecial,
						PermissaoEspecial.class.getName());

				FiltroUsuarioPemissaoEspecial filtroUsuarioPermissaoEspecialAtualizar = new FiltroUsuarioPemissaoEspecial();
				filtroUsuarioPermissaoEspecialAtualizar
						.adicionarCaminhoParaCarregamentoEntidade("permissaoEspecial");
				filtroUsuarioPermissaoEspecialAtualizar
						.adicionarParametro(new ParametroSimples(
								FiltroUsuarioPemissaoEspecial.USUARIO_COMP_ID,
								usuarioParaAtualizar.getId()));
				// colecao de usu�rio permiss�o especial
				Collection colecaoUsuarioPermissaoEspecialParaAtualizar = Fachada
						.getInstancia().pesquisar(
								filtroUsuarioPermissaoEspecialAtualizar,
								UsuarioPermissaoEspecial.class.getName());
				if (colecaoUsuarioPermissaoEspecialParaAtualizar != null
						&& !colecaoUsuarioPermissaoEspecialParaAtualizar
								.isEmpty()) {
					
					Collection colecaoPermissaoEspecialAux = new ArrayList();
					
					Iterator ite = colecaoUsuarioPermissaoEspecialParaAtualizar.iterator();
					while(ite.hasNext()){
						UsuarioPermissaoEspecial usuarioPermissaoEspecial = (UsuarioPermissaoEspecial)ite.next();
						colecaoPermissaoEspecialAux.add(usuarioPermissaoEspecial.getPermissaoEspecial());		
					}
					// seta os campos de permiss�o especial no form para
					// aparecer no
					// jsp como checado
					permissaoEspecial = Fachada
							.getInstancia()
							.retornarPermissoesMarcadas(
									colecaoPermissaoEspecialAux);

				}
			} else {
				Object[] permissoesEspeciais = Fachada.getInstancia()
						.pesquisarPermissoesEspeciaisUsuarioEUsuarioLogado(
								usuarioParaAtualizar, usuario);
				colecaoPermissaoEspecial = (Collection) permissoesEspeciais[0];
				colecaoPermissaoEspecialDesalibitado = (Collection) permissoesEspeciais[1];
				permissaoEspecial = (String[]) permissoesEspeciais[2];
				/*
				 * // caso o usu�rio n�o seja do grupos de administradores
				 * FiltroUsuarioPemissaoEspecial
				 * filtroUsuarioPemissaoEspecialLogado = new
				 * FiltroUsuarioPemissaoEspecial();
				 * filtroUsuarioPemissaoEspecialLogado .adicionarParametro(new
				 * ParametroSimples(
				 * FiltroUsuarioPemissaoEspecial.USUARIO_COMP_ID,
				 * usuario.getId())); filtroUsuarioPemissaoEspecialLogado
				 * .adicionarCaminhoParaCarregamentoEntidade("permissaoEspecial"); //
				 * recupera as permiss�es do usuario logado Collection
				 * colecaoUsuarioLogadoPermissaoEspecial =
				 * Fachada.getInstancia()
				 * .pesquisar(filtroUsuarioPemissaoEspecialLogado,
				 * UsuarioPermissaoEspecial.class.getName());
				 * 
				 * FiltroUsuarioPemissaoEspecial filtroUsuarioPermissaoEspecial =
				 * new FiltroUsuarioPemissaoEspecial();
				 * filtroUsuarioPermissaoEspecial .adicionarParametro(new
				 * ParametroSimples(
				 * FiltroUsuarioPemissaoEspecial.USUARIO_COMP_ID,
				 * usuarioParaAtualizar.getId()));
				 * filtroUsuarioPermissaoEspecial
				 * .adicionarCaminhoParaCarregamentoEntidade("usuario");
				 * filtroUsuarioPemissaoEspecialLogado
				 * .adicionarCaminhoParaCarregamentoEntidade("permissaoEspecial"); //
				 * colecao de usu�rio permiss�o especial Collection
				 * colecaoUsuarioPermissaoEspecial =
				 * Fachada.getInstancia().pesquisar(
				 * filtroUsuarioPermissaoEspecial,
				 * UsuarioPermissaoEspecial.class.getName()); if
				 * (colecaoUsuarioPermissaoEspecial != null &&
				 * !colecaoUsuarioPermissaoEspecial.isEmpty()) { // remove os
				 * usuario permiss�o especial da cole��o de // usu�rio // logado
				 * com permiss�o especial que tenha na cole��o de // usu�rio //
				 * permiss�o usu�rio que est� sendo atualizado Iterator
				 * iteratorUsuarioPermissaoEspecialLogado =
				 * colecaoUsuarioLogadoPermissaoEspecial.iterator(); // seta os
				 * campos de permiss�o especial no form para // aparecer no jsp
				 * como checado Iterator iteratorUsuarioPermissaoEspecial =
				 * colecaoPermissaoEspecial .iterator(); int i = 0;
				 * permissaoEspecial = new String[colecaoPermissaoEspecial
				 * .size()];
				 * 
				 * while (iteratorUsuarioPermissaoEspecial.hasNext()) {
				 * UsuarioPermissaoEspecial usuarioPermissaoEspecialObject =
				 * (UsuarioPermissaoEspecial) iteratorUsuarioPermissaoEspecial
				 * .next(); permissaoEspecial[i] = "" +
				 * usuarioPermissaoEspecialObject.getComp_id()
				 * .getPermissaoEspecialId(); } }
				 */
			}
			// seta os campos que v�o aparecer como checado
			controlarAcessoUsuarioActionForm
					.setPermissoesEspeciais(permissaoEspecial);
			sessao.setAttribute("colecaoPermissaoEspecial",
					colecaoPermissaoEspecial);
			sessao.setAttribute("colecaoPermissaoEspecialDesalibitado",
					colecaoPermissaoEspecialDesalibitado);
		}

		return retorno;
	}
}
