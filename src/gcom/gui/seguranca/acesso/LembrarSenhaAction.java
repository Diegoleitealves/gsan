package gcom.gui.seguranca.acesso;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.FiltroUsuario;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * Action respons�vel por enviar um email para o usu�rio 
 * com uma senha provis�ria
 *
 * @author Pedro Alexandre
 * @date 07/07/2006
 */
public class LembrarSenhaAction extends GcomAction {

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
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Obt�m a inst�ncia da Fachada
		Fachada fachada = Fachada.getInstancia();
		
		// Recupera o ActionForm
		LembrarSenhaActionForm lembrarSenhaActionForm = (LembrarSenhaActionForm) actionForm;

		//Recupera todos os dados necess�rios para verificar se o usu�rio 
		//pode ser lembrada sua senha
		String login = lembrarSenhaActionForm.getLogin();
		String dataNascimentoString = lembrarSenhaActionForm.getDataNascimento();
		String cpf = lembrarSenhaActionForm.getCpf();
		
		// [FS0001] - Verificar exist�ncia do login
		if (!this.verificarExistenciaLogin(login)) {
			throw new ActionServletException("atencao.login.inexistente",null,login);
		} else {
			fachada.lembrarSenha(login,cpf,dataNascimentoString);
		}

		//Caso o mapeamento seja para a tela de sucesso do popup
		//monta a tela de sucesso indicando que o email foi enviado 
		if (retorno.getName().equalsIgnoreCase("telaSucesso")) {
		  montarPaginaSucesso(httpServletRequest,"E-mail enviado com sucesso.", "", "");
	    }
		 
		return retorno;
	}

	/**
	 * Verifica se o login informado existe para algum usu�rio do sistema
	 * retorna true se existir caso contr�rio retorna false.
	 * 
	 * [FS0001] - Verificar exist�ncia do login
	 *
	 * @author Pedro Alexandre
	 * @date 07/07/2006
	 *
	 * @param login
	 * @return
	 */
	private boolean verificarExistenciaLogin(String login) {
		// Inicializa o retorno para falso(login n�o existe)
		boolean retorno = false;

		// Cria o filtro e pesquisa o usu�rio com o login informado
		FiltroUsuario filtroUsuario = new FiltroUsuario();
		filtroUsuario.adicionarParametro(new ParametroSimples(FiltroUsuario.LOGIN, login));
		Collection usuarios = Fachada.getInstancia().pesquisar(filtroUsuario,Usuario.class.getName());

		// Caso exista o usu�rio com o login informado
		// seta o retorno para verdadeiro(login existe no sistema)
		if (usuarios != null && !usuarios.isEmpty()) {
			retorno = true;
		}
		// Retorna um indicador se o login informado existe ou n�o no sistema
		return retorno;
	}
	
	
}
