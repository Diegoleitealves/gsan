package gcom.gui.cobranca.spcserasa;

import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action que inicializa o pr�-processamento da pagina de exibi��o do filtro 
 * para pesquisa de comandos de negativa��o
 * 
 * @author: Thiago Vieira
 * @date: 10/01/2007
 */
public class ExibirFiltrarComandoNegativacaoAction extends GcomAction {

    /**
     * M�todo de execu��o principal do action
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

        ActionForward retorno = actionMapping.findForward("filtrarComandoNegativacao");
        FiltrarComandoNegativacaoActionForm form = (FiltrarComandoNegativacaoActionForm) actionForm;
        
        if (!form.getTipoComando().equals("") && form.getTipoComando() != null){
        	String tipoComando = form.getTipoComando(); 
        	if (tipoComando.equals(ConstantesSistema.TIPO_COMANDO_POR_CRITERIO.toString())){
        		retorno = actionMapping.findForward("exibirFiltrarComandoNegativacaoTipoCriterio");
        	} else {
        		retorno = actionMapping.findForward("exibirFiltrarComandoNegativacaoTipoMatricula");
        	}
        } else {
        	form.setTipoComando(ConstantesSistema.TIPO_COMANDO_POR_CRITERIO.toString());
        }
        
        return retorno;
    }
    
}
