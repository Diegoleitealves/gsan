package gcom.gui.cadastro.imovel;

import gcom.cadastro.imovel.ImovelEconomia;
import gcom.cadastro.imovel.ImovelSubcategoria;
import gcom.gui.GcomAction;

import java.util.Collection;
import java.util.HashSet;
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
 */
public class RemoverEconomiaAction extends GcomAction {
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
		ActionForward retorno = actionMapping.findForward("informarEconomia");

		// Obt�m a inst�ncia da sess�o
		HttpSession sessao = httpServletRequest.getSession(false);

		//Fachada fachada = Fachada.getInstancia();

		// Cria variaveis
		Collection colecaoImovelSubCategoriasCadastradas = (Collection) sessao
				.getAttribute("colecaoImovelSubCategoriasCadastradas");

		Collection colecaoImovelEconomiasModificadas = (Collection) sessao
				.getAttribute("colecaoImovelEconomiasModificadas");

		String codigoImovelEconomia = (String) httpServletRequest
				.getParameter("codigoImovelEconomia");

		Collection colecaoImovelSubCategoriaNova = new HashSet();
		// Verifica se veio algum parametro no economia_inserir.jsp
		// caso tenha vindo pega o parametro e procura na cole��o um objeto
		// que tenha um hashCode igual ao do parametro
		if (httpServletRequest.getParameter("codigoImovelEconomia") != null
				&& !httpServletRequest.getParameter("codigoImovelEconomia")
						.trim().equals("")) {

			Iterator imovelSubCategoriaIterator = colecaoImovelSubCategoriasCadastradas
					.iterator();
			while (imovelSubCategoriaIterator.hasNext()) {
				ImovelSubcategoria imovelSubCategoria = null;
				imovelSubCategoria = (ImovelSubcategoria) imovelSubCategoriaIterator
						.next();

				Iterator imovelEconomiaIterator = imovelSubCategoria
						.getImovelEconomias().iterator();

				Collection colecaoImovelEconomiaiNaoRemovidas = new HashSet();

				
				while (imovelEconomiaIterator.hasNext()) {

					ImovelEconomia imovelEconomia = (ImovelEconomia) imovelEconomiaIterator
							.next();

					if (imovelEconomia.getUltimaAlteracao().getTime() == Long
							.parseLong(codigoImovelEconomia)) {
						// caso o imovel economia tenha codigo igual a nulo

						if (imovelEconomia.getId() == null
								|| imovelEconomia.getId().equals("")) {
							// remove o imovel economia s� da cole��o
							// pois n�o existe na base ainda.
							imovelEconomiaIterator.remove();

							// caso o imovel economia tenha codigo n�o seja
							// igual a nulo
						} else {
							// remove o imovel economia s� da cole��o
							// e como ja existe na base tamb�m remove da
							// base

							//fachada.removerImovelEconomia(imovelEconomia);
							
							if (sessao.getAttribute("colecaoRemovidas") != null){
								Collection colecaoRemovidas = (Collection) sessao.getAttribute("colecaoRemovidas");
								colecaoRemovidas.add(imovelEconomia);
								sessao.setAttribute("colecaoRemovidas", colecaoRemovidas);
							} else {
								Collection<ImovelEconomia> colecaoRemovidas = new HashSet();
								colecaoRemovidas.add(imovelEconomia);
								sessao.setAttribute("colecaoRemovidas", colecaoRemovidas);
							}
							
							imovelEconomiaIterator.remove();

						}

					} else {
						if (imovelSubCategoria.getComp_id().getSubcategoria()
								.getId().equals(
										imovelEconomia.getImovelSubcategoria()
												.getComp_id().getSubcategoria()
												.getId())) {
							colecaoImovelEconomiaiNaoRemovidas
									.add(imovelEconomia);
							imovelEconomiaIterator.remove();
						}
					}

				}

				imovelSubCategoria.setImovelEconomias(new HashSet(
						colecaoImovelEconomiaiNaoRemovidas));
				colecaoImovelSubCategoriaNova.add(imovelSubCategoria);
			}
		}

		// � preciso remover , caso o getTime da ultimaAltera��o sej� igual,os
		// im�veis economia
		// da colecao de imovelEconomiasModificadas que foi removido.
		Iterator imoveisEconomiaModificadasIterator = colecaoImovelEconomiasModificadas
				.iterator();
		while (imoveisEconomiaModificadasIterator.hasNext()) {
			ImovelEconomia imovelEconomiaModificada = (ImovelEconomia) imoveisEconomiaModificadasIterator
					.next();

			if (imovelEconomiaModificada.getUltimaAlteracao().getTime() == Long
					.parseLong(codigoImovelEconomia)) {

				imoveisEconomiaModificadasIterator.remove();
				break;
			}
		}

		sessao.setAttribute("colecaoImovelSubCategoriasCadastradas",
				colecaoImovelSubCategoriaNova);

		return retorno;
	}
}
