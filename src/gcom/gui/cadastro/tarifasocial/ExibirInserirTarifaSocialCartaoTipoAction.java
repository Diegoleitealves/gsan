package gcom.gui.cadastro.tarifasocial;

import gcom.cadastro.tarifasocial.TarifaSocialCartaoTipo;
import gcom.gui.GcomAction;

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
public class ExibirInserirTarifaSocialCartaoTipoAction extends GcomAction {
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

        ActionForward retorno = actionMapping
                .findForward("inserirTarifaSocialCartaoTipo");

        //Obt�m o action form
        TarifaSocialCartaoTipoActionForm tarifaSocialCartaoTipoActionForm = (TarifaSocialCartaoTipoActionForm) actionForm;

        //Mudar isso quando tiver esquema de seguran�a
        HttpSession sessao = httpServletRequest.getSession(false);

        sessao.removeAttribute("TarifaSocialCartaoTipoActionForm");

        //Seta a propriedade (validade) do action form como SIM
        tarifaSocialCartaoTipoActionForm
                .setValidade((TarifaSocialCartaoTipo.INDICADOR_EXISTENCIA_VALIDADE_SIM)
                        .toString());

        return retorno;
    }
}
