package gcom.gui.arrecadacao;

import gcom.arrecadacao.GuiaDevolucao;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ManterGuiaDevolucaoAction extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Obt�m a inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		ManterGuiaDevolucaoActionForm manterGuiaDevolucaoActionForm = (ManterGuiaDevolucaoActionForm) actionForm;

        String idImovel = manterGuiaDevolucaoActionForm.getIdImovel();
        
		String[] idsRegistrosRemocao = manterGuiaDevolucaoActionForm
				.getIdRegistrosRemocao();

        /*
         * Caso o usu�rio tenha informado o id do im�vel
         * verifica o controle de abrang�ncia 
         * Caso contrario n�o tem controle de abrang�ncia.
         */
        if(idImovel != null && !idImovel.trim().equals("")){
            /** alterado por pedro alexandre dia 24/11/2006 
             * Recupera o usu�rio logado para passar no met�do de remover guia de devolu��o 
             * para verificar se o usu�rio tem abrang�ncia para remover a guia de devolu��o
             * para o im�vel informado.
             */
            HttpSession sessao = httpServletRequest.getSession(false);
            Usuario usuarioLogado = (Usuario)sessao.getAttribute(Usuario.USUARIO_LOGADO);
            fachada.removerGuiaDevolucao(idImovel, usuarioLogado, idsRegistrosRemocao, GuiaDevolucao.class.getName(), null, null);
        }else{
            fachada.remover(idsRegistrosRemocao, GuiaDevolucao.class.getName(), null, null);
        }

		montarPaginaSucesso(httpServletRequest, idsRegistrosRemocao.length
				+ " Guia(s) de Devolu��o removida(s) com sucesso.",
				"Realizar outra Manuten��o de Guia de Devolu��o",
				"exibirFiltrarGuiaDevolucaoAction.do?menu=sim");

		return retorno;

	}
}
