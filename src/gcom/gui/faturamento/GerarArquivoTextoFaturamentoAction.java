package gcom.gui.faturamento;

import gcom.fachada.Fachada;
import gcom.gui.GcomAction;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * < <Descri��o da Classe>>
 * 
 * @author Administrador
 */
public class GerarArquivoTextoFaturamentoAction extends GcomAction {

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
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		GerarArquivoTextoFaturamentoActionForm form = (GerarArquivoTextoFaturamentoActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		String mes = form.getMesAno().substring(0, 2);
		String ano = form.getMesAno().substring(3, 7);

		String anoMes = ano + mes;

		fachada.chamarGerarArquivoTextoFaturamento(new Integer(anoMes), form.getIdCliente(), (Collection) sessao.getAttribute("colecaoCliente"));

		// montando p�gina de sucesso
		montarPaginaSucesso(httpServletRequest, "Gerar Arquivo Texto do Faturamento", "Voltar", "/exibirGerarArquivoTextoFaturamentoAction.do");

		return retorno;

	}
}
