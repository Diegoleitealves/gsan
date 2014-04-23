package gcom.gui.cadastro.tarifasocial;

import gcom.cadastro.tarifasocial.TarifaSocialDadoEconomia;
import gcom.cadastro.tarifasocial.bean.TarifaSocialHelper;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * < <Descri��o da Classe>>
 * 
 * @author rodrigo
 */
public class ExibirManterTarifaSocialDadosUmaEconomiaAction extends GcomAction {
	
	
    /**
     * < <Descri��o do m�todo>>
     * 
     * @param actionMapping
     *            Descri��o do par�metro
     * @param actionForm
     *            Descri��o do par�metro
     * @param httpServletRequest
     *            Descri��o do par�metro
     * @param httpServletResponse
     *            Descri��o do par�metro
     * @return Descri��o do retorno
     */
    public ActionForward execute(ActionMapping actionMapping,
            ActionForm actionForm, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {

        ActionForward retorno = actionMapping
                .findForward("manterTarifaSocialUmaEconomia");

        // Instancia da Fachada
        Fachada fachada = Fachada.getInstancia();

        //Pega uma instancia da sessao
        HttpSession sessao = httpServletRequest.getSession(false);

        //Pega uma instancia do actionform
        ManterTarifaSocialActionForm manterTarifaSocialActionForm = (ManterTarifaSocialActionForm) actionForm;

        String idImovel = manterTarifaSocialActionForm.getIdImovel();
        
        Collection colecaoTarifaSocialHelper = null;
        
        if (sessao.getAttribute("atualizar") != null) {
        	colecaoTarifaSocialHelper = (Collection) sessao.getAttribute("colecaoTarifaSocialHelper");
        	sessao.removeAttribute("atualizar");
        } else {
        	colecaoTarifaSocialHelper = fachada.pesquisarDadosClienteTarifaSocial(new Integer(idImovel));
        }
        
        if (colecaoTarifaSocialHelper != null && !colecaoTarifaSocialHelper.isEmpty()) {

        	sessao.setAttribute("colecaoTarifaSocialHelper", colecaoTarifaSocialHelper);
        	
        	TarifaSocialHelper tarifaSocialHelper = (TarifaSocialHelper) colecaoTarifaSocialHelper.iterator().next();
        	
        	sessao.setAttribute("clienteImovel", tarifaSocialHelper.getClienteImovel());
        
        }else{
        	 //O imovel n�o possui dados em tarifa_social_dado_economia
            throw new ActionServletException("atencao.imovel.sem_dados_tarifasocial");
        }
        
        String idTarifaSocial = httpServletRequest.getParameter("idTarifaSocial");
        
        if (idTarifaSocial != null && !idTarifaSocial.equals("")) {
        	Collection colecaoTarifaSocialExcluida = (Collection) sessao.getAttribute("colecaoTarifaSocialExcluida");
        	
        	if (colecaoTarifaSocialExcluida  != null ) {
	        	
        		Iterator colecaoTarifaSocialExcluidaIterator = colecaoTarifaSocialExcluida.iterator();
	        	
	        	while (colecaoTarifaSocialExcluidaIterator.hasNext()) {
	        		
	        		TarifaSocialDadoEconomia tarifaSocialDadoEconomia = (TarifaSocialDadoEconomia) colecaoTarifaSocialExcluidaIterator.next();
	        		
	        		if (tarifaSocialDadoEconomia.getId().toString().equals(idTarifaSocial)) {
	        			colecaoTarifaSocialExcluida.remove(tarifaSocialDadoEconomia);
	        			break;
	        		}
	        	}
        	}
        }

        return retorno;
    }
    
}
