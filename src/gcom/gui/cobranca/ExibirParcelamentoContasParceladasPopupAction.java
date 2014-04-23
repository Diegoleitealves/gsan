package gcom.gui.cobranca;

import gcom.cobranca.parcelamento.FiltroParcelamentoItem;
import gcom.cobranca.parcelamento.ParcelamentoItem;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * < <Descri��o da Classe>>
 * 
 * @author Administrador
 */
public class ExibirParcelamentoContasParceladasPopupAction extends
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
				.findForward("parcelamentoContasParceladasPopup");
		
		// Mudar isso quando tiver esquema de seguran�a
		//HttpSession sessao = httpServletRequest.getSession(false);

		// Obt�m a facahda
		Fachada fachada = Fachada.getInstancia();

		// Pega os codigos que o usuario selecionou para a pesquisa direta da conta
		String codigo = httpServletRequest.getParameter("codigoParcelamento");
		
		if (codigo != null && !codigo.trim().equals("")) {
			
			// Pesquisa o parfelamento Item na base
			FiltroParcelamentoItem filtroParcelamentoItem = new FiltroParcelamentoItem();

			filtroParcelamentoItem.adicionarParametro(new ParametroSimples(
					FiltroParcelamentoItem.PARCELAMENTO, codigo));

			filtroParcelamentoItem
					.adicionarCaminhoParaCarregamentoEntidade("contaGeral.conta");
			
			filtroParcelamentoItem
			.adicionarCaminhoParaCarregamentoEntidade("contaGeral.contaHistorico");

			filtroParcelamentoItem
					.adicionarCaminhoParaCarregamentoEntidade("parcelamento");

			Collection<ParcelamentoItem> colecaoParcelamentoItem = fachada.pesquisar(
					filtroParcelamentoItem, ParcelamentoItem.class.getName());

			if (colecaoParcelamentoItem != null && !colecaoParcelamentoItem.isEmpty()) 
			{
				httpServletRequest.setAttribute("colecaoParcelamentoItem", colecaoParcelamentoItem);
			}
			else
			{
				throw new ActionServletException("atencao.contas.parceladas.inexistente");
        	}
		}
		return retorno;
	}
}
