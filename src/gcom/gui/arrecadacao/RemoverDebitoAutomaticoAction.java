package gcom.gui.arrecadacao;

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
 * [UC0803]	Remover Debito Autimatico 
 * 
 * @author Bruno Barros
 * @date 11/06/2008
 */
public class RemoverDebitoAutomaticoAction extends GcomAction {
	
	public ActionForward execute(ActionMapping actionMapping,
            ActionForm actionForm, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {

        ManutencaoRegistroActionForm manutencaoRegistroActionForm = (ManutencaoRegistroActionForm) actionForm;

        String[] ids = manutencaoRegistroActionForm.getIdRegistrosRemocao();

        ActionForward retorno = actionMapping.findForward("telaSucesso");

        Fachada fachada = Fachada.getInstancia();
        
        // mensagem de erro quando o usu�rio tenta excluir sem ter selecionado
        // nenhum registro
        if (ids == null || ids.length == 0) {
            throw new ActionServletException("atencao.registros.nao_selecionados");
        }

		fachada.removerDebitoAutomatico( ids );
        
        // [FS0004] Verificar Sucesso de Transa��o
        //Monta a p�gina de sucesso
        if (retorno.getName().equalsIgnoreCase("telaSucesso")) {
            montarPaginaSucesso(httpServletRequest,
            		ids.length + " D�bito(s) Autom�tico(s) removidos com sucesso.",
                    "Realizar outra Manuten��o de D�bito Autom�tico",
                    "exibirFiltrarDebitoAutomaticoAction.do?menu=sim");
        }

        return retorno;

	}
}
