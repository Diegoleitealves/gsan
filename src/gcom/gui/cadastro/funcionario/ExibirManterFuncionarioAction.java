package gcom.gui.cadastro.funcionario;

import gcom.cadastro.funcionario.FiltroFuncionario;
import gcom.cadastro.funcionario.Funcionario;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author R�mulo Aur�lio
 *
 */
public class ExibirManterFuncionarioAction extends GcomAction{
	
	/**
	 * 
	 * [UC????] Manter Funcionario
	 * 
	 * 
	 * @author R�mulo Aur�lio
	 * @date 08/04/2007
	 * 
	 * 
	 * @param actionMapping
	 *            Description of the Parameter
	 * @param actionForm
	 *            Description of the Parameter
	 * @param httpServletRequest
	 *            Description of the Parameter
	 * @param httpServletResponse
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	
	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		ActionForward retorno = actionMapping
				.findForward("exibirManterFuncionarioAction");

		// Obt�m a inst�ncia da fachada
		// Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		// Limpa o atributo se o usu�rio voltou para o manter
		if (sessao.getAttribute("colecaoFuncionarioTela") != null) {
			sessao.removeAttribute("colecaoFuncionarioTela");
		}

		// Recupera o filtro passado pelo FiltrarFuncionalidadeAction para
		// ser efetuada pesquisa
		FiltroFuncionario filtroFuncionario = (FiltroFuncionario) sessao
				.getAttribute("filtroFuncionario");
		
		filtroFuncionario.adicionarCaminhoParaCarregamentoEntidade("empresa");
		filtroFuncionario.adicionarCaminhoParaCarregamentoEntidade("unidadeOrganizacional");

		// Aciona o controle de pagina��o para que sejam pesquisados apenas
		// os registros que aparecem na p�gina
		Map resultado = controlarPaginacao(httpServletRequest, retorno,
				filtroFuncionario, Funcionario.class.getName());
		Collection colecaoFuncionario = (Collection) resultado
				.get("colecaoRetorno");
		retorno = (ActionForward) resultado.get("destinoActionForward");

		// Verifica se a cole��o retornada pela pesquisa � nula, em caso
		// afirmativo comunica ao usu�rio que n�o existe nenhuma fuuncionalidade
		// cadastrada
		// para a pesquisa efetuada e em caso negativo e se
		// atender a algumas condi��es seta o retorno para o
		// ExibirAtualizarFuncionalidadeAction, se n�o atender manda a
		// cole��o pelo request para ser recuperado e exibido pelo jsp.

		if (colecaoFuncionario != null && !colecaoFuncionario.isEmpty()) {

			// Verifica se a cole��o cont�m apenas um objeto, se est� retornando
			// da pagina��o (devido ao esquema de pagina��o de 10 em 10 faz uma
			// nova busca), evitando, assim, que caso haja 11 elementos no
			// retorno da pesquisa e o usu�rio selecione o link para ir para a
			// segunda p�gina ele n�o v� para tela de atualizar.

			if (colecaoFuncionario.size() == 1
					&& (httpServletRequest.getParameter("page.offset") == null || httpServletRequest
							.getParameter("page.offset").equals("1"))) {

				// Verifica se o usu�rio marcou o checkbox de atualizar no jsp
				// funcionario_filtrar. Caso todas as condi��es sejam
				// verdadeiras seta o retorno para o
				// ExibirAtualizarFuncionarioAction e em caso negativo
				// manda a cole��o pelo request.

				if (httpServletRequest.getParameter("atualizar") != null) {
					retorno = actionMapping
							.findForward("atualizarFuncionario");
					Funcionario funcionario = (Funcionario) colecaoFuncionario
							.iterator().next();
					sessao.setAttribute("objetoFuncionario", funcionario);

				} else {
					httpServletRequest.setAttribute("colecaoFuncionario",
							colecaoFuncionario);
				}
			} else {
				httpServletRequest.setAttribute("colecaoFuncionario",
						colecaoFuncionario);
			}
		} else {
			// Nenhuma funcionario cadastrado
			throw new ActionServletException("atencao.pesquisa.nenhumresultado");
		}

		return retorno;
	}
}
