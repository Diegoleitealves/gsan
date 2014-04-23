package gcom.gui.arrecadacao;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.gui.ManutencaoRegistroActionForm;
import gcom.seguranca.acesso.usuario.Usuario;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * [UC0005] MANTER ARRECADADOR
 * 
 * @author Marcio Roberto
 * @date 08/02/2007
 */



public class RemoverContratoArrecadadorAction extends GcomAction {
	
	public ActionForward execute(ActionMapping actionMapping,
            ActionForm actionForm, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {

        ManutencaoRegistroActionForm manutencaoRegistroActionForm = (ManutencaoRegistroActionForm) actionForm;

        String[] ids = manutencaoRegistroActionForm.getIdRegistrosRemocao();

        ActionForward retorno = actionMapping.findForward("telaSucesso");

        Fachada fachada = Fachada.getInstancia();
        
        HttpSession sessao = httpServletRequest.getSession(false);
        
        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

        // mensagem de erro quando o usu�rio tenta excluir sem ter selecionado
        // nenhum registro
        if (ids == null || ids.length == 0) {
            throw new ActionServletException("atencao.registros.nao_selecionados");
        }

        fachada.removerContratoArrecadador(ids,usuarioLogado);
        
        // [FS0004] Verificar Sucesso de Transa��o
        //Monta a p�gina de sucesso
        if (retorno.getName().equalsIgnoreCase("telaSucesso")) {
            montarPaginaSucesso(httpServletRequest,
            		ids.length + " Contrato(s) de Arrecadador(es) removido(s) com sucesso.",
                    "Realizar outra Manuten��o de Contratos de Arrecadador",
                    "exibirFiltrarContratoArrecadadorAction.do?menu=sim");
        }

        return retorno;

	}
}
