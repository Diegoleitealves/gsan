package gcom.gui.atendimentopublico.ligacaoesgoto;

import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
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
 * [UC0789] Filtrar Situacao de Liga��o de Esgoto
 * 
 * @author Bruno Barros
 *
 * @date 20/05/2008
 */
public class RemoverLigacaoEsgotoSituacaoAction extends GcomAction {
	
	public ActionForward execute(ActionMapping actionMapping,
            ActionForm actionForm, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {

        ManutencaoRegistroActionForm manutencaoRegistroActionForm = (ManutencaoRegistroActionForm) actionForm;

        String[] ids = manutencaoRegistroActionForm.getIdRegistrosRemocao();

        ActionForward retorno = actionMapping.findForward("telaSucesso");

        Fachada fachada = Fachada.getInstancia();

        if (ids == null || ids.length == 0) {
            throw new ActionServletException("atencao.registros.nao_selecionados");
        }

        fachada.remover(ids, LigacaoEsgotoSituacao.class.getName(),
				null, null);
        
        if (retorno.getName().equalsIgnoreCase("telaSucesso")) {
            montarPaginaSucesso(httpServletRequest,
            		ids.length + " Situa��o(�es) de Liga��o(�es) de Esgoto removida(s) com sucesso.",
                    "Realizar outra Manuten��o de Situa��o de Liga��o de Esgoto",
                    "exibirFiltrarLigacaoEsgotoSituacaoAction.do?menu=sim");
        }

        return retorno;

	}
}
