package gcom.gui.atendimentopublico.registroatendimento;

import gcom.gui.GcomAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Esta classe tem por finalidade gerar o formul�rio que receber� os par�metros para realiza��o
 * da pesquisa de Observa��o do Registro Atendimento (Solicita��o da CAER)
 *
 * @author Rafael Pinto
 * @date 14/03/2007
 */
public class ExibirPesquisarObservacaoRegistroAtendimentoAction extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		ActionForward retorno = actionMapping.findForward("exibirPesquisarObservacaoRegistroAtendimento");
		
		PesquisarObservacaoRegistroAtendimentoActionForm form = 
        	(PesquisarObservacaoRegistroAtendimentoActionForm) actionForm;
		
		if (form.getMatriculaImovel() != null && !form.getMatriculaImovel().equals("")){
			
			String inscricaoImovel = 
				this.getFachada().pesquisarInscricaoImovel(new Integer(form.getMatriculaImovel()));
			
			if(inscricaoImovel != null){
				form.setInscricaoImovel(inscricaoImovel);
			}else{
				httpServletRequest.setAttribute("imovelNaoEncontrado",true);
				form.setInscricaoImovel("Im�vel inexistente");
				form.setMatriculaImovel(null);
				
			}
		}

		return retorno;
	}

}
