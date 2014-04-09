package gcom.gui.operacional.abastecimento;

import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.operacional.SistemaEsgoto;
import gcom.seguranca.acesso.usuario.Usuario;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;



/**
 * [UC0525] Manter Sistema Esgoto [SB0001]Atualizar Sistema Esgoto
 *
 * @author K�ssia Albuquerque
 * @date 19/03/2007
 */


public class AtualizarSistemaEsgotoAction extends GcomAction{
	
	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

			ActionForward retorno = actionMapping.findForward("telaSucesso");
			
			AtualizarSistemaEsgotoActionForm atualizarSistemaEsgotoActionForm = 
												(AtualizarSistemaEsgotoActionForm) actionForm;
			Fachada fachada = Fachada.getInstancia();
			
			HttpSession sessao = httpServletRequest.getSession(false);		
			
			Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");
			
			SistemaEsgoto sistemaEsgoto = (SistemaEsgoto) sessao.getAttribute("sistemaEsgoto");
			
			//Atualiza a entidade com os valores do formul�rio
			atualizarSistemaEsgotoActionForm.setFormValues(sistemaEsgoto);
			
			//atualiza na base de dados de Sistema Esgoto
			fachada.atualizarSistemaEsgoto(sistemaEsgoto,usuarioLogado);
			
			//Monta a p�gina de sucesso
			montarPaginaSucesso(httpServletRequest, "Sistema de Esgoto de c�digo "+ sistemaEsgoto.getId() +" alterado com sucesso", "Realizar outra Manuten��o de Sistema de Esgoto",
					"exibirFiltrarSistemaEsgotoAction.do?menu=sim");
			
			
			return retorno;
	}
	
}	      
    



