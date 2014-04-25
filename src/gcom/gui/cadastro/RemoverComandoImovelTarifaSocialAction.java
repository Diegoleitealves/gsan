package gcom.gui.cadastro;

import gcom.cadastro.tarifasocial.TarifaSocialComandoCarta;
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
 * @author Vivianne Sousa
 * @created 31/03/2011
 */
public class RemoverComandoImovelTarifaSocialAction extends GcomAction {
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

		// Prepara o retorno da A��o
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Obt�m a inst�ncia da sess�o
		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();
	
		String idsComandos = httpServletRequest.getParameter("comando");
		if (idsComandos != null && !idsComandos.equals("")){
			
			Collection colacaoTarifaSocialComandoCartaSessao = (Collection) sessao.getAttribute("colacaoTarifaSocialComandoCarta");
			Iterator itcolacaoTarifaSocialComandoCartaSessao = colacaoTarifaSocialComandoCartaSessao.iterator();
			TarifaSocialComandoCarta comando = null;
			
			String[] idsComandosArray = idsComandos.split(",");
			Integer idRemover = 0;
			while (itcolacaoTarifaSocialComandoCartaSessao.hasNext() && idRemover == 0){
				
				comando = (TarifaSocialComandoCarta) itcolacaoTarifaSocialComandoCartaSessao.next();
				
				for(int x=0; x<idsComandosArray.length; x++){
					
					if (comando.getId().equals(new Integer(idsComandosArray[x]))){
						if(comando.getDataGeracao() != null &&
						comando.getQuantidadeCartasGeradas() != null && 
						!comando.getQuantidadeCartasGeradas().equals(new Integer("0"))){
							throw new ActionServletException("atencao.cartas_geradas");
						}
						
						idRemover = comando.getId();
						colacaoTarifaSocialComandoCartaSessao.remove(comando);
					}
				}
			}
			
			fachada.removerComando(idRemover);
			
			sessao.setAttribute("colacaoTarifaSocialComandoCarta",colacaoTarifaSocialComandoCartaSessao);
		}

		if (retorno.getName().equalsIgnoreCase("telaSucesso")) {
			montarPaginaSucesso(httpServletRequest,
					" Comando removido com sucesso.",
					"Selecionar outro Comando",
					"exibirSelecionarComandoRetirarImovelTarifaSocialAction.do?menu=S");
		}

		
		return retorno;
	}
	

}
