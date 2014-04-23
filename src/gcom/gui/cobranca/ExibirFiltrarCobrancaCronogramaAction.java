package gcom.gui.cobranca;

import gcom.cobranca.CobrancaGrupo;
import gcom.cobranca.FiltroCobrancaGrupo;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * < <Descri��o da Classe>>
 * 
 * @author Administrador
 */
public class ExibirFiltrarCobrancaCronogramaAction extends GcomAction {

    /**
     * < <Descri��o do m�todo>>
     * 
     * @param actionMapping
     *            Descri��o do par�metro
     * @param actionForm
     *            Descri��o do par�metro
     * @param httpServletRequest
     *            Descri��o do par�metro
     * @param httpServletResponse
     *            Descri��o do par�metro
     * @return Descri��o do retorno
     */
    public ActionForward execute(ActionMapping actionMapping,
            ActionForm actionForm, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {

        //Seta o mapeamento de retorno
        ActionForward retorno = actionMapping
                .findForward("filtrarCobrancaCronograma");

        //CobrancaActionForm cobrancaActionForm = (CobrancaActionForm) actionForm;

        Fachada fachada = Fachada.getInstancia();

        //Mudar isso quando tiver esquema de seguran�a
        HttpSession sessao = httpServletRequest.getSession(false);

        sessao.removeAttribute("PesquisarActionForm");
        
        FiltroCobrancaGrupo filtroCobrancaGrupo = new FiltroCobrancaGrupo();
        
        filtroCobrancaGrupo.adicionarParametro(new ParametroSimples(FiltroCobrancaGrupo.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
        filtroCobrancaGrupo.setCampoOrderBy(FiltroCobrancaGrupo.DESCRICAO);
        
        Collection colecaoCobrancaGrupo = fachada.pesquisar(filtroCobrancaGrupo, CobrancaGrupo.class.getName());
        
        if(colecaoCobrancaGrupo.isEmpty()){
        	throw new ActionServletException("atencao.naocadastrado", null,
            	"grupo de cobranca");
        }else{
        	sessao.setAttribute("colecaoCobrancaGrupo", colecaoCobrancaGrupo);
        }

       

        return retorno;

    }
}
