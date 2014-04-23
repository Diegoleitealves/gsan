package gcom.gui.seguranca.acesso;

import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.gui.ManutencaoRegistroActionForm;
import gcom.seguranca.acesso.usuario.Usuario;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * Action respons�vel por remover uma opera��o do sistema 
 *
 * @author Pedro Alexandre
 * @date 02/08/2006
 */
public class RemoverOperacaoAction extends GcomAction {
	
	/**
	 *
	 * [UC0281] - Manter Opera��o
	 *
	 * @author Pedro Alexandre
	 * @date 02/08/2006
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

		//Seta o mapeamento de retorno para a tela de sucesso
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		//Cria uma inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		//Cria uma inst�ncia da sess�o
		HttpSession sessao = httpServletRequest.getSession(false);
		
		//Recupera o usu�rio logado no sistema
		Usuario usuarioLogado =(Usuario) sessao.getAttribute("usuarioLogado");
		
		//Recupera o form de manuten��o 
		ManutencaoRegistroActionForm manutencaoRegistroActionForm = (ManutencaoRegistroActionForm) actionForm;

		//Recupera os ids das opera��es para remo��o 
		String[] idsRegistrosRemocao = manutencaoRegistroActionForm.getIdRegistrosRemocao();

		//Chama o met�do de remover opera��o da fachada
		fachada.removerOperacao(idsRegistrosRemocao, usuarioLogado);

		//Monta a tela de sucesso 
		montarPaginaSucesso(httpServletRequest, idsRegistrosRemocao.length
				+ " Opera��o(�es) exclu�da(s) com sucesso.",
				"Manter outra Opera��o",
				"exibirFiltrarOperacaoAction.do?menu=sim");

		//Retorna o mapeamento contido na vari�vel retorno 
		return retorno;
	}
}
