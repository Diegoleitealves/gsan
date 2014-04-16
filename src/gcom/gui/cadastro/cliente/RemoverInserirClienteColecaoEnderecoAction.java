package gcom.gui.cadastro.cliente;

import gcom.cadastro.cliente.ClienteEndereco;
import gcom.gui.GcomAction;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

/**
 * < <Descri��o da Classe>>
 * 
 * @author Administrador
 */
public class RemoverInserirClienteColecaoEnderecoAction extends GcomAction {

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
				.findForward("exibirInserirEndereco");

		// obtendo uma instancia da sessao
		HttpSession sessao = httpServletRequest.getSession(false);

		DynaValidatorForm clienteActionForm = (DynaValidatorForm) sessao
				.getAttribute("ClienteActionForm");

		Collection colecaoEnderecos = (Collection) sessao
				.getAttribute("colecaoEnderecos");

		if (colecaoEnderecos != null) {

			String[] codigoEnderecos = httpServletRequest
					.getParameterValues("enderecoRemoverSelecao");

			Iterator iterator = colecaoEnderecos.iterator();

			if( colecaoEnderecos.size() == 1 ){
				sessao.removeAttribute("colecaoEnderecos");
			}	

			while (iterator.hasNext()) {
				ClienteEndereco clienteEndereco = (ClienteEndereco) iterator
						.next();

				for (int i = 0; i < codigoEnderecos.length; i++) {
					if (obterTimestampIdObjeto(clienteEndereco) == (Long
							.parseLong(codigoEnderecos[i]))) {
						// Verifica se o endere�o removido era o principal para
						// adicionar o indicador de principal
						// para o primeiro da lista

						if (obterTimestampIdObjeto(clienteEndereco) == (((Long) clienteActionForm
								.get("enderecoCorrespondenciaSelecao"))
								.longValue())
								&& colecaoEnderecos.size() > 1) {
							Iterator iteratorTemp = colecaoEnderecos.iterator();
							// Pega o primeiro da lista
							ClienteEndereco clienteEnderecoPrimeiroDaLista = (ClienteEndereco) iteratorTemp
									.next();

							// Verifica se o primeiro da lista � o mesmo que
							// ser� removido
							if (obterTimestampIdObjeto(clienteEnderecoPrimeiroDaLista) == obterTimestampIdObjeto(clienteEndereco)) {
								// Seta como principal o segundo da lista
								clienteEnderecoPrimeiroDaLista = (ClienteEndereco) iteratorTemp
										.next();
							}

							// Seta o indicador no form
							httpServletRequest
									.setAttribute(
											"enderecoCorrespondenciaSelecao",
											new Long(
													obterTimestampIdObjeto(clienteEnderecoPrimeiroDaLista)));

						}

						iterator.remove();
					}
				}
			}
		}
		return retorno;
	}
}
