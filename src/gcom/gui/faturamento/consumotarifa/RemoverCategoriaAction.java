package gcom.gui.faturamento.consumotarifa;

import gcom.cadastro.imovel.Categoria;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.gui.ManutencaoRegistroActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Remove as categorias selecionadas na lista da funcionalidade Manter Categoria
 * 
 * @author Roberta Costa
 * @created 02 de Janeiro de 2006
 */
public class RemoverCategoriaAction extends GcomAction {
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

        ManutencaoRegistroActionForm manutencaoRegistroActionForm = (ManutencaoRegistroActionForm) actionForm;

        // Obt�m os ids de remo��o
        String[] ids = manutencaoRegistroActionForm.getIdRegistrosRemocao();

        //Seta o mapeamento de retorno
        ActionForward retorno = actionMapping.findForward("telaSucesso");

        //Obt�m a sess�o
        //HttpSession sessao = httpServletRequest.getSession(false);

        //Obt�m a inst�ncia da fachada
        Fachada fachada = Fachada.getInstancia();

        //mensagem de erro quando o usu�rio tenta excluir sem ter selecionado nenhum registro
        if (ids == null || ids.length == 0) {
            throw new ActionServletException(
                    "atencao.registros.nao_selecionados");
        }

        fachada.remover(ids, Categoria.class.getName(),null, null);

        //Monta a p�gina de sucesso
        if (retorno.getName().equalsIgnoreCase("telaSucesso")) {
            montarPaginaSucesso(httpServletRequest,
                    "Categoria(s) removida(s) com sucesso",
                    "Realizar outra manuten��o de Categoria",
                    "exibirManterCategoriaAction.do");
        }

        return retorno;
    }
}
