package gcom.gui.seguranca.acesso;

import java.util.Date;

import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioSituacao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;



/**
 * Action respons�vel pela pr�-exibi��o da tela de alterar a senha do usu�rio
 *
 * @author Pedro Alexandre
 * @date 17/07/2006
 */
public class ExibirEfetuarAlteracaoSenhaAction extends GcomAction {

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
		ActionForward retorno = actionMapping.findForward("efetuarAlteracaoSenha");
		
		
		//Recupera uma inst�ncia da sess�o
		HttpSession sessao = httpServletRequest.getSession(false);
		
		//Recupera o usu�rio que est� logado
		Usuario usuarioLogado = (Usuario)sessao.getAttribute("usuarioLogado");
		
		/*================================================================================
		Altera��o: CRC1959 - Remocao do link de Alterar
		Senha e Combo de Ultimos Acessos caso o usuario logado ainda n�o tenha realizado
		a alteracao da senha padrao recebida no cadastro do mesmo no sistema
		autor: Anderson Italo
		data:19/06/2009*/
		Date dataExpiracaoAcesso = usuarioLogado.getDataExpiracaoAcesso();
		if (dataExpiracaoAcesso != null) {
			if (dataExpiracaoAcesso.before(new Date())) {
				sessao.setAttribute("indicadorSenhaPendente", 1);
			}
		}
		
		UsuarioSituacao usuarioSituacao = usuarioLogado.getUsuarioSituacao();
		if (usuarioSituacao.getId().equals(UsuarioSituacao.PENDENTE_SENHA)) {
			sessao.setAttribute("indicadorSenhaPendente", 1);
		}
		/*========================Fim da Altera��o=======================================*/
		
		//Recupera o lembrete da senha do usu�rio
		String lembreteSenha = usuarioLogado.getLembreteSenha();
		
		//Caso o lembrete esteja nulo, seta o lembrete para uma string vazia 
		if(lembreteSenha == null){
			lembreteSenha = "";
		}
		
		String mensagem = "A nova senha deve conter de seis a oito caracteres, " +
		"dentre as quais pelo menos uma letra(A, B, C,...,a,b,c,...), " +
	    "pelo menos um n�mero(0,1,2...) ," +
	    "n�o possuir seguencia de 3 caracteres iguais(aaa,111,...). exemplo: Gsan10";
		
		httpServletRequest.setAttribute("mensagem",mensagem);
		
		//Seta o lembrete da senha do usu�rio no request
		httpServletRequest.setAttribute("lembreteSenha",lembreteSenha);
		
		//Retornao mapeamento contido na vari�vel retorno
		return retorno;
	}
}
