package gcom.gui.faturamento;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Executar as atividades do faturamento previamente comandadas
 * 
 * [UC0111] Executar Atividade do Faturamento
 *
 * @author Raphael Rossiter, Roberta Costa
 * @date 29/03/2006, 02/05/2006
 */
public class ExecutarAtividadeFaturamentoAction extends GcomAction {
	
	public ActionForward execute(ActionMapping actionMapping,
            ActionForm actionForm, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {

        ActionForward retorno = actionMapping.findForward("telaSucesso");
        
        // cria uma inst�ncia da fachada
        Fachada fachada = Fachada.getInstancia();
        
        // recupera o form
        ExecutarAtividadeFaturamentoActionForm executarAtividadeFaturamentoActionForm = 
        	(ExecutarAtividadeFaturamentoActionForm) actionForm;

        
        // Recupera o array de ids de atividades de faturamento do cronograma do form
        //String[] idsFaturamentoAtividadeCronograma = 
        	//httpServletRequest.getParameter("idFaturamentoAtividadeCronograma").split(",");
        
        // Recupera o array de ids de atividades de faturamento do cronograma do form
        String[] idsFaturamentoAtividadeCronograma = executarAtividadeFaturamentoActionForm
        	.getIdsFaturamentoAtividadeCronograma();

        // [FS0004] Verifica a sele��o de atividade para execu��o
        if(idsFaturamentoAtividadeCronograma == null 
        		|| idsFaturamentoAtividadeCronograma.equals("")){  
       		throw new ActionServletException("Nenhuma atividade de faturamento foi selecionada para execu��o");       
        }   
        
        //chama o met�do de executar a��o de atividade e cobran�a da fachada
        fachada.executarAtividadeFaturamento(idsFaturamentoAtividadeCronograma);
        
        //monta a p�gina de sucesso
        montarPaginaSucesso(httpServletRequest,
				"Atividade(s) de faturamento executada(s) com sucesso.",
		        "Executar outra(s) atividade(s) de faturamento",
		        "exibirExecutarAtividadeFaturamentoAction.do");
        
        return retorno;
    }

}
