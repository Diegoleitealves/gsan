package gcom.gui.cadastro.imovel;

import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.FiltroCategoria;
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
 * Descri��o da classe 
 *
 * @author Roberta Costa
 * @date 28/12/2006
 */
public class ExibirManterCategoriaAction extends GcomAction {
    /**
     * Description of the Method
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

        ActionForward retorno = actionMapping.findForward("manterCategoria");

        Collection categorias = null;

        //Mudar isso quando implementar a parte de seguran�a
        HttpSession sessao = httpServletRequest.getSession(false);

        //Parte da verifica��o do filtro
        FiltroCategoria filtroCategoria = new FiltroCategoria();

        //Verifica se o filtro foi informado pela p�gina de filtragem de categoria
        if(httpServletRequest.getAttribute("filtroCategoria") != null){
            filtroCategoria = (FiltroCategoria) httpServletRequest
                    .getAttribute("filtroCategoria");
        }else{
            //Se o limite de registros foi atingido, a p�gina de filtragem � chamada
            retorno = actionMapping.findForward("filtrarCategoria");
        }

        //A pesquisa de categorias s� ser� feita se o forward estiver direcionado para a p�gina de manterCategoria
        if(retorno.getName().equalsIgnoreCase("manterCategoria")){
            //Seta a ordena��o desejada do filtro
            filtroCategoria.setCampoOrderBy(FiltroCategoria.DESCRICAO);
            
            // Aciona o controle de pagina��o para que sejam pesquisados apenas
			// os registros que aparecem na p�gina
			Map resultado = controlarPaginacao(httpServletRequest, retorno,
					filtroCategoria, Categoria.class.getName());
			categorias = (Collection) resultado.get("colecaoRetorno");
			retorno = (ActionForward) resultado.get("destinoActionForward");

			
            if(categorias == null || categorias.isEmpty()) {
                //Nenhuma Categoria cadastrada
                throw new ActionServletException("atencao.categoria_inexistente");
            }

            sessao.setAttribute("categorias", categorias);
        }
        return retorno;
    }
}
