package gcom.gui.micromedicao;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.RoteiroEmpresaHelper;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 
 * Permite exibir uma lista com os roteiros da empresa retornadas do
 * FiltrarManterRoteiroEmpresaAction ou ir para o
 * ExibirManterRoteiroEmpresaReferidaAction
 * 
 * @author Thiago Ten�rio
 * @since 23/08/2007
 */
public class ExibirManterRoteiroEmpresaAction extends GcomAction {

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
			ActionForward retorno = actionMapping.findForward("exibirManterRoteiroEmpresa");

			// Obt�m a inst�ncia da fachada
			Fachada fachada = Fachada.getInstancia();

			HttpSession sessao = httpServletRequest.getSession(false);
			
			sessao.removeAttribute("roteiroEmpresaAtualizar");
        	sessao.removeAttribute("colecaoQuadras");

			// Recupera os par�metros da sess�o para ser efetuada a pesquisa
			String empresa = (String) sessao.getAttribute("empresa");
			String idLocalidade = (String) sessao.getAttribute("idLocalidade");
			String idLeiturista = (String) sessao.getAttribute("idLeiturista");
			String idSetorComercial = (String) sessao.getAttribute("idSetorComercial");

			String indicadorUso = (String) sessao.getAttribute("indicadorUso");
		
			// Aciona o controle de pagina��o para que sejam pesquisados apenas
			// os registros que aparecem na p�gina
			Integer totalRegistros = fachada.pesquisarRoteiroEmpresaCount(empresa, idLocalidade,
					idLeiturista, idSetorComercial, indicadorUso);

			retorno = this.controlarPaginacao(httpServletRequest, retorno,
					totalRegistros);

			Collection colecaoRoteiroEmpresas = fachada.pesquisarRoteiroEmpresa(empresa, idLocalidade,
					idSetorComercial, idLeiturista, indicadorUso, (Integer) httpServletRequest
							.getAttribute("numeroPaginasPesquisa"));

			
			// Verifica se a cole��o retornada pela pesquisa � nula, em caso
			// afirmativo comunica ao usu�rio que n�o existe nenhuma roteiroEmpresa
			// cadastrada para a pesquisa efetuada e em caso negativo e se
			// atender a algumas condi��es seta o retorno para o
			// ExibirAtualizarRoteiroEmpresaAction, se n�o atender manda a
			// cole��o pelo request para ser recuperado e exibido pelo jsp.
			if (colecaoRoteiroEmpresas != null && !colecaoRoteiroEmpresas.isEmpty()) {

				// Verifica se a cole��o cont�m apenas um objeto, se est� retornando
				// da pagina��o (devido ao esquema de pagina��o de 10 em 10 faz uma
				// nova busca), evitando, assim, que caso haja 11 elementos no
				// retorno da pesquisa e o usu�rio selecione o link para ir para a
				// segunda p�gina ele n�o v� para tela de atualizar.
				if (colecaoRoteiroEmpresas.size() == 1
						&& (httpServletRequest.getParameter("page.offset") == null || httpServletRequest
								.getParameter("page.offset").equals("1"))) {
					// Verifica se o usu�rio marcou o checkbox de atualizar no jsp
					// roteiroEmpresa_filtrar. Caso todas as condi��es sejam
					// verdadeiras seta o retorno para o
					// ExibirAtualizarRoteiroEmpresaAction e em caso negativo
					// manda a cole��o pelo request.
					if (sessao.getAttribute("atualizar") != null) {
						retorno = actionMapping
								.findForward("exibirAtualizarRoteiroEmpresa");
						RoteiroEmpresaHelper roteiroEmpresa = (RoteiroEmpresaHelper) colecaoRoteiroEmpresas.iterator().next();
						sessao.setAttribute("idRegistroAtualizacao", roteiroEmpresa.getIdRoteiroEmpresa() + "");
					} else {
						httpServletRequest.setAttribute("colecaoRoteiroEmpresa",
								colecaoRoteiroEmpresas);
					}
				} else {
					httpServletRequest.setAttribute("colecaoRoteiroEmpresa",
							colecaoRoteiroEmpresas);
				}
			} else {
				// Caso a pesquisa n�o retorne nenhum objeto comunica ao usu�rio;
				throw new ActionServletException("atencao.pesquisa.nenhumresultado");
			}

			return retorno;

		}

	}
