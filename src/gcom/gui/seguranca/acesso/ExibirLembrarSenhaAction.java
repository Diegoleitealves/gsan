package gcom.gui.seguranca.acesso;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.FiltroUsuario;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * Action respons�vel por exibir a tela para lembrar a senha do usu�rio
 *
 * @author Pedro Alexandre
 * @date 07/07/2006
 */
public class ExibirLembrarSenhaAction extends GcomAction {

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

		// Prepara o retorno da a��o para a tela de lembrar senha
		ActionForward retorno = actionMapping.findForward("lembrarSenha");
		
		//Recupera o login informado pelo usu�rio na p�gina de login
		String login = httpServletRequest.getParameter("login");
		String cpf = httpServletRequest.getParameter("cpf");
		String dataNascimentoString = httpServletRequest.getParameter("dataNascimento");
		
		if(login != null && !login.trim().equals("")){
			// [FS0001] - Verificar exist�ncia do login
			if (!this.verificarExistenciaLogin(login)) {
				throw new ActionServletException("atencao.login.inexistente",null, login);
			} else {
				// Cria o filtro e pesquisa o usu�rio com o login informado
				FiltroUsuario filtroUsuario = new FiltroUsuario();
				filtroUsuario.adicionarParametro(new ParametroSimples(FiltroUsuario.LOGIN, login));
				Collection usuarios = Fachada.getInstancia().pesquisar(filtroUsuario,Usuario.class.getName());
	
				//Recupera o usu�rio que est� sendo logado
				Usuario usuarioLogado = (Usuario)usuarios.iterator().next();
				
				String lembreteSenha = usuarioLogado.getLembreteSenha();
				
				//[FS0006] - Validar data
				Date dataNascimento = null;
		        SimpleDateFormat dataFormato = new SimpleDateFormat("dd/MM/yyyy");
		        try {
		            dataNascimento = dataFormato.parse(dataNascimentoString);
		        } catch (ParseException ex) {
		        	throw new ActionServletException("atencao.data.invalida",null, "Data de Nascimento");
		        }

		        //[FS0007] Verificar data maior ou igual a data corrente
		        Date dataAtual = new Date();
		        if(!dataNascimento.before(dataAtual)){
		        	throw new ActionServletException("atencao.data_nascimento.anterior.dataatual",login,Util.formatarData(dataAtual));
		        }
		        
		        //[UC0008] - Verificar data de nascimento do login
				Date dataNascimentoUsuarioLogado = usuarioLogado.getDataNascimento();
				if(dataNascimento.compareTo(dataNascimentoUsuarioLogado) != 0 ){
					throw new ActionServletException("atencao.data_nascimento.incorreta.login",null,login);
				}
				
				//Recupera o CPF do usu�rio que est� logado e verifica 
				//se � o mesmo que foi informado n� p�gina
				String cpfUsuarioLogado = usuarioLogado.getCpf();
				if(cpfUsuarioLogado != null && !cpfUsuarioLogado.trim().equals("")){
					//[FS0010] - Verificar CPF do login
					if(!cpf.equals(cpfUsuarioLogado)){
						throw new ActionServletException("atencao.cpf.incorreto.login",null,login);
					}
				}
				
				//Seta no request o login e o lembrete da senha do usu�rio 
				//para ser recuperados na jsp do lembrete 
				httpServletRequest.setAttribute("lembreteSenha",lembreteSenha);
				httpServletRequest.setAttribute("login",login);
				httpServletRequest.setAttribute("dataNascimento",Util.formatarData(dataNascimento));
				httpServletRequest.setAttribute("cpf",cpf);
			}
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
