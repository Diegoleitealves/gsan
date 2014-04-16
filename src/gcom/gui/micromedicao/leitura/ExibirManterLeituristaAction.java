package gcom.gui.micromedicao.leitura;

import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.FiltroLeiturista;
import gcom.micromedicao.Leiturista;
import gcom.seguranca.acesso.usuario.Usuario;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 
 * Permite exibir uma lista com os leituristas retornados do
 * FiltrarManterLeituristaAction ou ir para o
 * ExibirManterLeituristaAction
 * 
 * @author Thiago Ten�rio
 * @since 22/07/2007
 */
public class ExibirManterLeituristaAction extends GcomAction {

	/**
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

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping
				.findForward("exibirManterLeiturista");

		// Obt�m a inst�ncia da fachada
		// Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		// Recupera o filtro passado pelo FiltrarLeituristaAction para
		// ser efetuada pesquisa
		FiltroLeiturista filtroLeiturista = (FiltroLeiturista) sessao
				.getAttribute("filtroLeiturista");
		
		filtroLeiturista.adicionarCaminhoParaCarregamentoEntidade(FiltroLeiturista.EMPRESA);
		filtroLeiturista.adicionarCaminhoParaCarregamentoEntidade(FiltroLeiturista.CLIENTE);
		filtroLeiturista.adicionarCaminhoParaCarregamentoEntidade(FiltroLeiturista.FUNCIONARIO);
		
		// Bloquear a opcao de remover quando a empresa do usuario nao seja a empresa do sistema
		
		SistemaParametro sistemaParametro = Fachada.getInstancia().pesquisarParametrosDoSistema();
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		if (usuario.getEmpresa() == null) {
			throw new ActionServletException("atencao.pesquisa_inexistente", null, "Empresa do usu�rio logado.");
		}
		if (!usuario.getEmpresa().getDescricao().equals(sistemaParametro.getNomeAbreviadoEmpresa())) {
			sessao.setAttribute("bloquearRemover", true); 
		}

		// Aciona o controle de pagina��o para que sejam pesquisados apenas
		// os registros que aparecem na p�gina
		Collection colecaoLeiturista = new ArrayList();
		if(filtroLeiturista != null && !filtroLeiturista.equals("")){
			Map resultado = controlarPaginacao(httpServletRequest, retorno,
				filtroLeiturista, Leiturista.class.getName());
			colecaoLeiturista = (Collection) resultado
				.get("colecaoRetorno");
			retorno = (ActionForward) resultado.get("destinoActionForward");
		}
		// Verifica se a cole��o retornada pela pesquisa � nula, em caso
		// afirmativo comunica ao usu�rio que n�o existe nenhum leiturista
		// cadastrado para a pesquisa efetuada e em caso negativo e se
		// atender a algumas condi��es seta o retorno para o
		// ExibirAtualizarLeituristaAction, se n�o atender manda a
		// cole��o pelo request para ser recuperado e exibido pelo jsp.
		if (colecaoLeiturista != null
				&& !colecaoLeiturista.isEmpty()) {

			// Verifica se a cole��o cont�m apenas um objeto, se est� retornando
			// da pagina��o (devido ao esquema de pagina��o de 10 em 10 faz uma
			// nova busca), evitando, assim, que caso haja 11 elementos no
			// retorno da pesquisa e o usu�rio selecione o link para ir para a
			// segunda p�gina ele n�o v� para tela de atualizar.
			if (colecaoLeiturista.size() == 1
					&& (httpServletRequest.getParameter("page.offset") == null || httpServletRequest
							.getParameter("page.offset").equals("1"))) {
				// Verifica se o usu�rio marcou o checkbox de atualizar no jsp
				// leiturista_filtrar. Caso todas as condi��es sejam
				// verdadeiras seta o retorno para o
				// ExibirAtualizarLeituristaAction e em caso negativo
				// manda a cole��o pelo request.
				if (httpServletRequest.getParameter("indicadorAtualizar") != null) {
					retorno = actionMapping
							.findForward("exibirAtualizarLeiturista");
					Leiturista leiturista = (Leiturista) colecaoLeiturista
							.iterator().next();
					sessao.setAttribute("leiturista",
							leiturista);
				} else {
					httpServletRequest.setAttribute(
							"colecaoLeiturista",
							colecaoLeiturista);
					Leiturista leiturista = (Leiturista) colecaoLeiturista
							.iterator().next();
					sessao.setAttribute("leiturista",
							leiturista);
				}
			} else {
				httpServletRequest.setAttribute("colecaoLeiturista",
						colecaoLeiturista);
			}
		} else {
			// Caso a pesquisa n�o retorne nenhum objeto comunica ao usu�rio;
			throw new ActionServletException("atencao.pesquisa.nenhumresultado");
		}

		return retorno;

	}

}
