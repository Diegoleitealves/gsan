package gcom.gui.cobranca;

import gcom.fachada.Fachada;
import gcom.gui.GcomAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Permite excluir um comando de atividade de cobran�a do crongrama 
 * ou alterar/excluir um comando deatividade de cobran�a eventual
 * [UC0244] Manter Comando de A��o de Conbran�a
 * 
 * Exclui Comando de Atividade do Cronograma de A��o de Cobran�a
 *
 * [SB0001] - Excluir Comando de Atividade de A��o de Cobran�a
 *
 * @author Rafael Santos
 * @since 24/03/2006
 */
public class ExcluirComandoAtividadeCronogramaAcaoCobrancaAction  extends GcomAction{
	
	
	/**
	 * 
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
    public ActionForward execute(ActionMapping actionMapping,
            ActionForm actionForm, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {

        //Seta o mapeamento de retorno
        ActionForward retorno = actionMapping
                .findForward("telaSucesso");
        
		Fachada fachada = Fachada.getInstancia();

		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);
        
		ManterComandoAcaoCobrancaActionForm manterComandoAcaoCobrancaActionForm =(ManterComandoAcaoCobrancaActionForm)actionForm;
		
		String[] idsCobrancaAcaoAtividadeCronograma = manterComandoAcaoCobrancaActionForm.getIdCobrancaAcaoAtividadeCronograma();
		
		fachada.excluirComandoAtividadeCronogramaAcaoCobranca(idsCobrancaAcaoAtividadeCronograma);
		
		montarPaginaSucesso(httpServletRequest,idsCobrancaAcaoAtividadeCronograma.length + " Comando(s) de A��o de Cobran�a removidos com sucesso",
                "Manter outro Comando de A��o de Cobran�a",
                "exibirManterComandoAcaoCobrancaAction.do?menu=sim");

   		if(sessao.getAttribute("colecaoAtividadeCronogramaAcaoCobrancaComandadas") != null ){
   			sessao.removeAttribute("colecaoAtividadeCronogramaAcaoCobrancaComandadas");
   		}
		if(sessao.getAttribute("colecaoAtividadesEventuaisAcaoCobrancaComandadas") != null ){
			sessao.removeAttribute("colecaoAtividadesEventuaisAcaoCobrancaComandadas");
		}
		
		
        return retorno;
    }

}
