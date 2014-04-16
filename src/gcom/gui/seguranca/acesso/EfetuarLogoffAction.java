package gcom.gui.seguranca.acesso;

import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.FiltroUsuario;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class EfetuarLogoffAction extends GcomAction {

	/**
	 * <Breve descri��o sobre o caso de uso>
	 * 
	 * [UC0287] - Efetuar Login
	 * 
	 * @author Pedro Alexandre
	 * @date 04/07/2006
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

		// Prepara o retorno da a��o para a tela principal
		ActionForward retorno = actionMapping.findForward("telaLogin");

		HttpSession sessao = httpServletRequest.getSession(false);
		
		// Obt�m a inst�ncia da Fachada
		Fachada fachada = Fachada.getInstancia();

		//Verifica se a sessao do usuario ja expirou
		if (sessao != null) {
        
			// Vari�vel que vai armazenar o usu�rio logado
			Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");
			
			if (usuarioLogado != null) {
	        
			        FiltroUsuario filtroUsuario = new FiltroUsuario();
			        filtroUsuario.adicionarParametro(new ParametroSimples(FiltroUsuario.ID,usuarioLogado.getId()));
			
			        Usuario usuarioNabase =(Usuario) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroUsuario,Usuario.class.getName()));
			        
					//Atualiza a data de ultimo acesso e de �ltima altera��o do usu�rio
			        usuarioNabase.setUltimoAcesso(new Date());
			        usuarioNabase.setUltimaAlteracao(new Date());
					
					//Atualiza o usu�rio 
					fachada.atualizar(usuarioNabase);
			
			}
			
			//Encerra a sess�o do usu�rio logado
			sessao.invalidate();
		
		}	
		
		//Retorna para a tela de login
		return retorno;
	}

}
