package gcom.gui.cadastro.unidade;

import gcom.cadastro.unidade.FiltroUnidadeOrganizacional;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action de exibir manter unidade oraganizacional
 * @author Ana Maria 
 * @created 21/11/2006
 */

public class ExibirManterUnidadeOrganizacionalAction extends GcomAction {
	
    public ActionForward execute(ActionMapping actionMapping,
            ActionForm actionForm, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {

        ActionForward retorno = actionMapping.findForward("manterUnidadeOrganizacional");
        
		HttpSession sessao = httpServletRequest.getSession(false);
		
		Fachada fachada = Fachada.getInstancia();

        Collection colecaoUnidadeOrganizacional = null;
        
        FiltroUnidadeOrganizacional filtroUnidadeOrganizacional = new FiltroUnidadeOrganizacional();
        
		if (sessao.getAttribute("filtroUnidadeOrganizacional") != null) {
			filtroUnidadeOrganizacional = (FiltroUnidadeOrganizacional) sessao
					.getAttribute("filtroUnidadeOrganizacional");

			// 1� Passo - Pegar o total de registros atrav�s de um count da
			// consulta que aparecer� na tela
			Integer totalRegistros = fachada
					.pesquisarUnidadeOrganizacionalFiltroCount(filtroUnidadeOrganizacional);

			// 2� Passo - Chamar a fun��o de Pagina��o passando o total de
			// registros
			retorno = this.controlarPaginacao(httpServletRequest, retorno,
					totalRegistros);
			
			// 3� Passo - Obter a cole��o da consulta que aparecer� na tela
			// passando o numero de paginas
			// da pesquisa que est� no request
			colecaoUnidadeOrganizacional = fachada
						.pesquisarUnidadeOrganizacionalFiltro(filtroUnidadeOrganizacional,
								(Integer) httpServletRequest
										.getAttribute("numeroPaginasPesquisa"));
			
			// [FS0003] Nenhum registro encontrado				
			if (colecaoUnidadeOrganizacional == null || colecaoUnidadeOrganizacional.isEmpty()) {
				throw new ActionServletException(
						"atencao.pesquisa.nenhumresultado");
			}

			String identificadorAtualizar = (String)sessao.getAttribute("indicadorAtualizar");
			
			if (colecaoUnidadeOrganizacional.size()== 1 && identificadorAtualizar != null){
				// caso o resultado do filtro s� retorne um registro 
				// e o check box Atualizar estiver selecionado
				//o sistema n�o exibe a tela de manter, exibe a de atualizar 
				retorno = actionMapping.findForward("atualizarUnidadeOrganizacional");
				UnidadeOrganizacional unidadeOrganizacional = (UnidadeOrganizacional)colecaoUnidadeOrganizacional.iterator().next();
				sessao.setAttribute("idRegistroAtualizacao", new Integer (unidadeOrganizacional.getId()).toString());
				sessao.removeAttribute("manter");				
			}else{
				sessao.setAttribute("collectionUnidadeOrganizacional", colecaoUnidadeOrganizacional);
				sessao.setAttribute("manter", "manter");
			}
			
			sessao.setAttribute("collectionUnidadeOrganizacional", colecaoUnidadeOrganizacional);
			sessao.setAttribute("totalRegistros", totalRegistros);
			sessao.setAttribute("numeroPaginasPesquisa",httpServletRequest
					.getAttribute("numeroPaginasPesquisa"));
			
		}
		/*// Parte da verifica��o do filtro
		FiltroUnidadeOrganizacional filtroUnidadeOrganizacional = null;

		// Verifica se o filtro foi informado pela p�gina de filtragem de unidade organizacional
		if (sessao.getAttribute("filtroUnidadeOrganizacional") != null) {
			filtroUnidadeOrganizacional = (FiltroUnidadeOrganizacional) sessao
					.getAttribute("filtroUnidadeOrganizacional");
		}

		if ((retorno != null)&&(retorno.getName().equalsIgnoreCase("manterUnidadeOrganizacional"))) {
		
			filtroUnidadeOrganizacional.adicionarCaminhoParaCarregamentoEntidade("unidadeTipo");
			 
			filtroUnidadeOrganizacional.setCampoOrderBy(FiltroUnidadeOrganizacional.ID,FiltroUnidadeOrganizacional.DESCRICAO);

			Map resultado = controlarPaginacao(httpServletRequest, retorno,
					filtroUnidadeOrganizacional, UnidadeOrganizacional.class.getName());
			collectionUnidadeOrganizacional = (Collection) resultado.get("colecaoRetorno");
			retorno = (ActionForward) resultado.get("destinoActionForward");
			

			// [FS0003] Nenhum registro encontrado				
			if (collectionUnidadeOrganizacional == null || collectionUnidadeOrganizacional.isEmpty()) {
				throw new ActionServletException(
						"atencao.pesquisa.nenhumresultado");
			}
			
			String identificadorAtualizar = (String)sessao.getAttribute("indicadorAtualizar");
			
			if (collectionUnidadeOrganizacional.size()== 1 && identificadorAtualizar != null){
				// caso o resultado do filtro s� retorne um registro 
				// e o check box Atualizar estiver selecionado
				//o sistema n�o exibe a tela de manter, exibe a de atualizar 
				retorno = actionMapping.findForward("atualizarUnidadeOrganizacional");
				UnidadeOrganizacional unidadeOrganizacional = (UnidadeOrganizacional)collectionUnidadeOrganizacional.iterator().next();
				sessao.setAttribute("idRegistroAtualizacao", new Integer (unidadeOrganizacional.getId()).toString());
				sessao.removeAttribute("manter");				
			}else{
				sessao.setAttribute("collectionUnidadeOrganizacional", collectionUnidadeOrganizacional);
				sessao.setAttribute("manter", "manter");
			}

		}
*/
		sessao.removeAttribute("UseCase");
		return retorno;
    }

}
