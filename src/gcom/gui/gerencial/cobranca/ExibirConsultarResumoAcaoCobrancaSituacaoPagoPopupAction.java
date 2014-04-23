package gcom.gui.gerencial.cobranca;

import gcom.cobranca.CobrancaAcao;
import gcom.cobranca.FiltroCobrancaAcao;
import gcom.fachada.Fachada;
import gcom.gerencial.bean.InformarDadosGeracaoResumoAcaoConsultaHelper;
import gcom.gui.GcomAction;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Ana Maria
 * @date 09/11/2006
 * 
 */
public class ExibirConsultarResumoAcaoCobrancaSituacaoPagoPopupAction extends
		GcomAction {
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

		// Seta a a��o de retorno
		ActionForward retorno = actionMapping
				.findForward("consultarResumoAcaoCobrancaSituacaoPagoPopup");

		// Obt�m a facahda
		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = getSessao(httpServletRequest);

		InformarDadosGeracaoResumoAcaoConsultaHelper informarDadosGeracaoResumoAcaoConsultaHelper = (InformarDadosGeracaoResumoAcaoConsultaHelper) sessao
				.getAttribute("informarDadosGeracaoResumoAcaoConsultaHelper");

		// Integer anoMesReferencia =
		// Util.formatarMesAnoComBarraParaAnoMes(sessao.getAttribute("mesAnoReferencia").toString());
		Integer idCobrancaAcao = new Integer(httpServletRequest.getParameter(
				"idCobrancaAcao").trim());
		FiltroCobrancaAcao filtroCobrancaAcao = new FiltroCobrancaAcao();
		filtroCobrancaAcao.adicionarParametro(new ParametroSimples(
				FiltroCobrancaAcao.ID, idCobrancaAcao));
		Collection colecaoCobrancaAcao = fachada.pesquisar(filtroCobrancaAcao,
				CobrancaAcao.class.getName());

		if (colecaoCobrancaAcao != null && !colecaoCobrancaAcao.isEmpty()) {
			Iterator iteratorCobrancaAcao = colecaoCobrancaAcao.iterator();
			CobrancaAcao cobrancaAcao = (CobrancaAcao) iteratorCobrancaAcao
					.next();
			httpServletRequest.setAttribute("cobrancaAcao", cobrancaAcao
					.getDescricaoCobrancaAcao());
		}
		Integer idCobrancaAcaoSituacao = new Integer(httpServletRequest
				.getParameter("idCobrancaAcaoSituacao").trim());
		httpServletRequest.setAttribute("cobrancaAcaoSituacao",
				httpServletRequest.getParameter("cobrancaAcaoSituacao"));
		httpServletRequest.setAttribute("quantidadeTotal", httpServletRequest
				.getParameter("quantidadeTotal"));
		httpServletRequest.setAttribute("valorTotal", httpServletRequest
				.getParameter("valorTotal").trim());
		String valorTotalFormatado = Util.formatarMoedaReal(new BigDecimal(
				httpServletRequest.getParameter("valorTotal").trim()));
		httpServletRequest.setAttribute("valorTotalFormatado",
				valorTotalFormatado);
		Integer idCobrancaAcaoDebito = null;

		if (httpServletRequest.getParameter("idCobrancaAcaoDebito") != null
				&& !httpServletRequest.getParameter("idCobrancaAcaoDebito").equals("")) {
			idCobrancaAcaoDebito = new Integer(httpServletRequest.getParameter(
					"idCobrancaAcaoDebito").trim());
			httpServletRequest.setAttribute("cobrancaAcaoDebito",
					httpServletRequest.getParameter("cobrancaAcaoDebito"));
			httpServletRequest.setAttribute("idCobrancaAcaoDebito",
					idCobrancaAcaoDebito);
		}

		Collection cobrancaAcaoDebitoHelperParaPago = fachada
				.consultarCobrancaAcaoDebitoComIndicador(
						informarDadosGeracaoResumoAcaoConsultaHelper,
						idCobrancaAcao, idCobrancaAcaoSituacao,
						idCobrancaAcaoDebito);

		sessao.setAttribute("cobrancaAcaoDebitoHelperParaPago",
				cobrancaAcaoDebitoHelperParaPago);

		return retorno;
	}
}
