package gcom.gui.cadastro.imovel;

import gcom.cadastro.imovel.ImovelRamoAtividade;
import gcom.gui.GcomAction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action que remove a o objeto selecionado de sub categoria em Manter Imovel
 * 
 * @author Rafael Santos
 * @created 11/02/2006
 * 
 */
public class RemoverAtualizarImovelRamoAtividadeAction extends GcomAction {
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
		ActionForward retorno = actionMapping
				.findForward("inserirImovelSubCategoria");

		// Obt�m a inst�ncia da sess�o
		HttpSession sessao = httpServletRequest.getSession(false);
		
		// Cria variaveis
		Collection colecaoImovelRamosAtividades = (Collection) sessao.getAttribute("colecaoImovelRamosAtividade");

		Collection colecaoImovelRamosAtividadesRemovidos = (Collection) sessao.getAttribute(
				"colecaoImovelRamosAtividadesRemovidos");
		if (colecaoImovelRamosAtividadesRemovidos == null){
			colecaoImovelRamosAtividadesRemovidos = new ArrayList();
		}

		ImovelRamoAtividade imovelRamoAtividade = null;
		
		// Obt�m os ids de remo��o
		String[] removerImovelRamoAtividade = httpServletRequest
				.getParameterValues("removerImovelRamoAtividade");

		if (colecaoImovelRamosAtividades != null && !colecaoImovelRamosAtividades.equals("")) {
			
			Iterator imovelRamoAtividadeIterator = colecaoImovelRamosAtividades.iterator();

			while (imovelRamoAtividadeIterator.hasNext()) {
				
				imovelRamoAtividade= (ImovelRamoAtividade) imovelRamoAtividadeIterator.next();
				
				for (int i = 0; i < removerImovelRamoAtividade.length; i++) {
					
					if (ExibirAtualizarImovelSubCategoriaAction.obterTimestampIdImovelRamoAtividade(imovelRamoAtividade) == 
						new Long(removerImovelRamoAtividade[i]).longValue()) {
						
						boolean podeRemover = true;
						
						if(podeRemover && !(colecaoImovelRamosAtividadesRemovidos.contains(imovelRamoAtividade))){
							colecaoImovelRamosAtividadesRemovidos.add(imovelRamoAtividade);
							imovelRamoAtividadeIterator.remove();
						}
					}
				}
			}
			sessao.setAttribute(
					"colecaoImovelRamosAtividadesRemovidos",
					colecaoImovelRamosAtividadesRemovidos);

		}

		return retorno;
	}
}
