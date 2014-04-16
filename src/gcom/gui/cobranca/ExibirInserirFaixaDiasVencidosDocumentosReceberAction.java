package gcom.gui.cobranca;

import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Hugo Leonardo
 * 
 */
public class ExibirInserirFaixaDiasVencidosDocumentosReceberAction extends GcomAction {
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
				.findForward("exibirInserirFaixaDiasVencidosDocumentosReceberAction");

		// Pega o parametro passado no request
		// String tela = (String) httpServletRequest.getParameter("tela");

		// Declara��o de objetos e tipos primitivos

		int tamMaxDescricaoFaixa = 10;
		int tamMaxValorInicialFaixa = 5;
		int tamMaxValorFinalFaixa = 5;

		// Cria a sess�o
		HttpSession sessao = httpServletRequest.getSession(false);

		String tela = httpServletRequest.getParameter("tela");

		// Obt�m a inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		SistemaParametro sistemaParametro = (SistemaParametro) fachada
				.pesquisarParametrosDoSistema();
		
		String titulo = "Faixa de Dias Vencidos ";
		sessao.setAttribute("titulo", titulo);
		sessao.setAttribute("nomeSistema", sistemaParametro.getNomeEmpresa());

		// Adiciona o objeto no request
		httpServletRequest.setAttribute("valorInicialFaixa", new Integer(
				tamMaxValorInicialFaixa));
		httpServletRequest.setAttribute("valorFinalFaixa", new Integer(
				tamMaxValorFinalFaixa));
		httpServletRequest.setAttribute("tamMaxCampoFaixaCompleta",
				new Integer(tamMaxDescricaoFaixa));
		
		httpServletRequest.setAttribute("tela",tela);

		return retorno;
	}

}
