package gcom.gui.cobranca;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * action respons�vel pela 
 * 
 * @author  pedro alexandre
 * @created 31 de Janeiro de 2006
 */
public class ExecutarAtividadeAcaoCobrancaAction extends GcomAction {

    
    public ActionForward execute(ActionMapping actionMapping,
            					 ActionForm actionForm, 
            					 HttpServletRequest httpServletRequest,
            					 HttpServletResponse httpServletResponse) {
    	
    	//cria a vari�vel de retorno e seta o mapeamento para a tela de sucesso
        ActionForward retorno = actionMapping.findForward("telaSucesso");
        
        //recupera o form
        ExecutarAtividadeAcaoCobrancaActionForm executarAtividadeAcaoCobrancaActionForm = (ExecutarAtividadeAcaoCobrancaActionForm) actionForm;
        
        //cria uma inst�ncia da fachada
        Fachada fachada = Fachada.getInstancia();
                
        //recupera o array de ids de atividades de cobran�a do cronograma do form
        String[] idsAtividadesCobrancaCronograma = executarAtividadeAcaoCobrancaActionForm.getIdsAtividadesCobrancaCronograma();
        
        //recupera o array de ids de atividades de cobran�a eventuais
        String[] idsAtividadesCobrancaEventuais = executarAtividadeAcaoCobrancaActionForm.getIdsAtividadesCobrancaEventuais();

        //verifica se o usu�rio selecionou alguma atividade de a��o de cobran�a na p�gina para executar
        if(idsAtividadesCobrancaCronograma == null || idsAtividadesCobrancaCronograma.length == 0){  
        	if(idsAtividadesCobrancaEventuais == null || idsAtividadesCobrancaEventuais.length == 0){
        		throw new ActionServletException("Nenhuma atividade de cobran�a selecionada");       
        	}                                                                                        
        }   
        
        //chama o met�do de executar a��o de atividade e cobran�a da fachada
        fachada.executarAcaoAtividadeCobranca(idsAtividadesCobrancaCronograma,idsAtividadesCobrancaEventuais);
        
        //monta a p�gina de sucesso
        montarPaginaSucesso(httpServletRequest,
				"Atividade(s) de a��o de cobran�a executada(s) com sucesso.",
		        "Executar outra(s) atividade(s) de a��o de cobran�a",
		        "exibirExecutarAtividadeAcaoCobrancaAction.do");
		
        //retorna o mapeamento contido na vari�vel retorno
        return retorno;
    }
}


