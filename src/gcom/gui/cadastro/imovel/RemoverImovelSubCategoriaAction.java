package gcom.gui.cadastro.imovel;

import gcom.cadastro.imovel.ImovelSubcategoria;
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
 * Action que remove a o objeto selecionado de cliente imovel economia que est�
 * com o imovel economia
 * 
 * @author S�vio Luiz
 * @created 20 de Maio de 2004
 * 
 */
public class RemoverImovelSubCategoriaAction extends GcomAction {
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

		//DynaValidatorForm inserirImovelActionForm = (DynaValidatorForm) actionForm;

		// Cria variaveis
		Collection colecaoImovelSubCategorias = (Collection) sessao
				.getAttribute("colecaoImovelSubCategorias");

		Collection colecaoImovelSubcategoriasRemoviadas = new ArrayList();

		ImovelSubcategoria imovelSubcategoria = null;

		// Obt�m os ids de remo��o
		String[] removerImovelSubCategoria = httpServletRequest
				.getParameterValues("removerImovelSubCategoria");

		if (colecaoImovelSubCategorias != null
				&& !colecaoImovelSubCategorias.equals("")) {

			Iterator imovelSubcategoriaIterator = colecaoImovelSubCategorias
					.iterator();

			while (imovelSubcategoriaIterator.hasNext()) {
				imovelSubcategoria = (ImovelSubcategoria) imovelSubcategoriaIterator
						.next();
				for (int i = 0; i < removerImovelSubCategoria.length; i++) {
					if (imovelSubcategoria.getUltimaAlteracao().getTime() == Long
							.parseLong(removerImovelSubCategoria[i])) {
						if(!(colecaoImovelSubcategoriasRemoviadas.contains(imovelSubcategoria))){
							colecaoImovelSubcategoriasRemoviadas
									.add(imovelSubcategoria);
							imovelSubcategoriaIterator.remove();
						}
					}
				}
			}
			sessao.setAttribute(
					"colecaoImovelSubcategoriasRemoviadas",
					colecaoImovelSubcategoriasRemoviadas);

		}

		return retorno;
	}
}
