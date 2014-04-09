package gcom.gui.micromedicao;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Hugo Leonardo
 * @date 15/03/2010
 * 
 */
public class AtualizarMedidorEnergiaRotaAction extends GcomAction {
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

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Obt�m a inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		// Obt�m a sess�o
		HttpSession sessao = httpServletRequest.getSession(false);
		
		Map<String, String[]> requestMap = httpServletRequest.getParameterMap();
		
		// Recupera o ponto de coleta da sess�o
		Collection colecao = (Collection) sessao.getAttribute("colecaoColetaMedidorEnergia");
    	
    	ColetaMedidorEnergiaHelper helper = null;
    	String numeroMedidor = null;
    	String matricula = null;
		
		if(colecao != null && !colecao.isEmpty()){
        	
        	Iterator iteratorColecaoMedidor = colecao.iterator();
        	
        	int valor = 0;
        	// Atualiza os valores do helper
    		while (iteratorColecaoMedidor.hasNext()) {
    			helper = (ColetaMedidorEnergiaHelper) iteratorColecaoMedidor.next();
    			// teste para ver se existe na p�gina alguma categoria 
    			valor++;
    			if (requestMap.get("medidor"+ helper.getImovel()) != null) {

    				numeroMedidor = (requestMap.get("medidor" + helper.getImovel()))[0];
    				if(numeroMedidor != null && !numeroMedidor.trim().equals("")){
    					helper.setMedidorEnergia(numeroMedidor);	
    				}
    			}
        	}
    		
    		iteratorColecaoMedidor = colecao.iterator();
    		//Atualiza os novos valores na base
    		while (iteratorColecaoMedidor.hasNext()) {
    			helper = (ColetaMedidorEnergiaHelper) iteratorColecaoMedidor.next();
    			
    			matricula = helper.getImovel();
    			numeroMedidor = helper.getMedidorEnergia();
    			
    			// Atualiza os dados
    			fachada.atualizarNumeroMedidorEnergiaImovel(matricula, numeroMedidor);
    		}
    		
        } else {
			// Caso a pesquisa n�o retorne nenhum objeto comunica ao usu�rio;
			throw new ActionServletException("atencao.pesquisa.nenhumresultado");
		}

		// Monta a p�gina de sucesso
		if (retorno.getName().equalsIgnoreCase("telaSucesso")) {
			
			sessao.removeAttribute("colecaoColetaMedidorEnergia");
			
			montarPaginaSucesso(
					httpServletRequest,"Medidor de energia atualizado com sucesso.",
					"Realizar outra Atualiza��o de Medidor de energia.",
					"exibirInformarMedidorEnergiaRotaAction.do?menu=sim");
		}

		// devolve o mapeamento de retorno
		return retorno;
	}

}
