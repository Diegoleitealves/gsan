package gcom.gui.faturamento.conta;

import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.faturamento.debito.DebitoCobrado;
import gcom.gui.GcomAction;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class RemoverSelecaoDebitoCobradoAction extends GcomAction {

    
    public ActionForward execute(ActionMapping actionMapping,
            ActionForm actionForm, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
    	
    	//Define o caso de uso que receber� o resultado desta remo��o
    	String mapeamentoStruts = httpServletRequest.getParameter("mapeamento");

        //Seta o mapeamento de retorno
        ActionForward retorno = actionMapping
                .findForward(mapeamentoStruts);

        //Mudar isso quando tiver esquema de seguran�a
        HttpSession sessao = httpServletRequest.getSession(false);

        String debitoCobradoUltimaAlteracao = httpServletRequest.getParameter("debitoCobradoUltimaAlteracao");
        
        if (debitoCobradoUltimaAlteracao != null && !debitoCobradoUltimaAlteracao.equalsIgnoreCase("") &&
        	sessao.getAttribute("colecaoDebitoCobrado") != null){
        	
        	long debitoCobradoUltimaAlteracaoLong = Long.parseLong(debitoCobradoUltimaAlteracao);
        	
        	Collection colecaoDebitoCobrado = (Collection) sessao.getAttribute("colecaoDebitoCobrado");
        	
        	Iterator colecaoDebitoCobradoIt = colecaoDebitoCobrado.iterator();
        	DebitoCobrado debitoCobradoColecao;
        	
        	while (colecaoDebitoCobradoIt.hasNext()){
        		debitoCobradoColecao = (DebitoCobrado) colecaoDebitoCobradoIt.next(); 
        		
        		if (GcomAction.obterTimestampIdObjeto(debitoCobradoColecao) ==  debitoCobradoUltimaAlteracaoLong){
        			colecaoDebitoCobrado.remove(debitoCobradoColecao);
        			break;
        		}
        	}
        }
        
        //Obt�m a inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();
        
        /*
		 * Colocado por Raphael Rossiter em 29/03/2007
		 * Objetivo: Manipula��o dos objetos que ser�o exibidos no formul�rio de acordo com a empresa
		 */
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		httpServletRequest.setAttribute("empresaNome", sistemaParametro.getNomeAbreviadoEmpresa().trim());
        
        return retorno;
    }

}
