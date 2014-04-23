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
 * Action para exibir a p�gina de endere�o
 * 
 * @author Rodrigo
 */
public class ExibirAtualizarClienteEnderecoAction extends GcomAction {

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
				.findForward("atualizarClienteEndereco");

		HttpSession sessao = httpServletRequest.getSession(false);

		DynaValidatorForm clienteActionForm = (DynaValidatorForm) sessao
				.getAttribute("ClienteActionForm");

		// enderecoCorrespondenciaSelecao novo que pode ser setado no remover de
		// endere�o
		// indica o novo enderecoCorrespondenciaSelecao
		Long enderecoCorrespondenciaSelecaoNovo = (Long) httpServletRequest
				.getAttribute("enderecoCorrespondenciaSelecao");

		if (enderecoCorrespondenciaSelecaoNovo != null) {
			clienteActionForm.set("enderecoCorrespondenciaSelecao",
					enderecoCorrespondenciaSelecaoNovo);
		}

		// Se a cole��o de endere�os tiver apenas um item, ent�o este item tem
		// que estar selecionado
		// como endere�o de correspondencia
		Collection colecaoEnderecos = (Collection) sessao
				.getAttribute("colecaoEnderecos");

		if (colecaoEnderecos != null && colecaoEnderecos.size() == 1) {

			Iterator iterator = colecaoEnderecos.iterator();

			ClienteEndereco clienteEndereco = (ClienteEndereco) iterator.next();

			clienteActionForm.set("enderecoCorrespondenciaSelecao", new Long(
					obterTimestampIdObjeto(clienteEndereco)));

		}
		
		//**********************************************************************
		// CRC2103
		// Autor: Ivan Sergio
		// Data: 02/07/2009
		// Verifica se a tela deve ser exibida como um PopUp
		//**********************************************************************
		if (httpServletRequest.getParameter("POPUP") != null) {
			if (httpServletRequest.getParameter("POPUP").equals("true")) {
				sessao.setAttribute("POPUP", "true");
			}else {
				sessao.setAttribute("POPUP", "false");
			}
		}else if (sessao.getAttribute("POPUP") == null) {
			sessao.setAttribute("POPUP", "false");
		}
		//**********************************************************************		

		return retorno;
	}
}
