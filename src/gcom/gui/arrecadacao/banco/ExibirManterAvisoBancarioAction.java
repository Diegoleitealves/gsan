package gcom.gui.arrecadacao.banco;

import gcom.arrecadacao.aviso.bean.AvisoBancarioHelper;
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
 * Action de exibir manter o aviso bancario
 *
 * @author thiago toscano
 * @date 10/03/2006
 */
public class ExibirManterAvisoBancarioAction  extends GcomAction {
	
    /**
     * M�todo responsavel por responder a requisicao
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

        ActionForward retorno = actionMapping.findForward("manter");

        HttpSession sessao = httpServletRequest.getSession(false);
        
        Fachada fachada = Fachada.getInstancia();
  
        if (sessao.getAttribute("i") != null){
			sessao.removeAttribute("i");
		}
        
        AvisoBancarioHelper avisoBancarioHelper = (AvisoBancarioHelper) sessao.getAttribute("filtroAvisoBancarioHelper");
        int count = 0;
        if (avisoBancarioHelper != null) {
        	Integer totalRegistros = 0;
        	if(sessao.getAttribute("totalRegistros") == null )
        	{
        		Collection colecaoAvisosBancario = fachada.filtrarAvisoBancarioAbertoFechado(avisoBancarioHelper);
        	
        		AvisoBancarioHelper objetoAvisoBancario = null;
        		AvisoBancarioHelper avisoBancarioHelperNovo = null;
        		Iterator iterator = colecaoAvisosBancario.iterator();
		
        		while (iterator.hasNext()) {
        			objetoAvisoBancario = (AvisoBancarioHelper) iterator.next();
        			avisoBancarioHelperNovo = new AvisoBancarioHelper();
		
        			avisoBancarioHelperNovo
						.setAvisoBancario(objetoAvisoBancario.getAvisoBancario());
        			avisoBancarioHelperNovo
						.setTipoAviso(avisoBancarioHelper.getTipoAviso());
        			count++; 
        		}
        		// 1� Passo - Pegar o total de registros atrav�s de um count da
        		// 	consulta que aparecer� na tela
        		totalRegistros = count; 
        		//totalRegistros = fachada.filtrarAvisoBancarioAbertoFechadoCount(avisoBancarioHelper, avisoBancarioHelperNovo);
        	}else{
        		totalRegistros = (Integer)sessao.getAttribute("totalRegistros");
        	}
			// 2� Passo - Chamar a fun��o de Pagina��o passando o total de
			// registros
			retorno = this.controlarPaginacao(httpServletRequest, retorno,
					totalRegistros);

			// 3� Passo - Obter a cole��o da consulta que aparecer� na tela
			// passando o numero de paginas
			// da pesquisa que est� no request
			Collection collectionAvisoBancario =  fachada.filtrarAvisoBancarioAbertoFechadoParaPaginacao(avisoBancarioHelper,
					(Integer) httpServletRequest.getAttribute("numeroPaginasPesquisa"));
			
			if (collectionAvisoBancario == null || collectionAvisoBancario.isEmpty()) {
				// Nenhum Aviso Bancario cadastrado
				throw new ActionServletException(
						"atencao.pesquisa.nenhumresultado");
			}
			
			sessao.setAttribute("collAvisoBancarios", collectionAvisoBancario);
			sessao.setAttribute("totalRegistros", totalRegistros);
			sessao.setAttribute("numeroPaginasPesquisa",httpServletRequest
					.getAttribute("numeroPaginasPesquisa"));
			
		}
        
        return retorno;
   }
    
}
