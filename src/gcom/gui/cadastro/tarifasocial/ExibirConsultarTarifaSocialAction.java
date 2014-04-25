package gcom.gui.cadastro.tarifasocial;

import gcom.cadastro.cliente.ClienteImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Prepara a p�gina para a exibi��o de Inserir Tarifa Social
 * 
 * @author rodrigo
 */
public class ExibirConsultarTarifaSocialAction extends GcomAction {

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
				.findForward("consultarTarifaSocial");

		// Pega uma instancia da sessao
		HttpSession sessao = httpServletRequest.getSession(false);

		// Pega uma instancia do actionform
		ConsultarTarifaSocialActionForm consultarTarifaSocialActionForm = (ConsultarTarifaSocialActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();		
		
		String idImovel = consultarTarifaSocialActionForm.getIdImovel();


		// Verifica se foi feita uma pesquisa de imovel que retornou para este
		// exibir
		if (idImovel != null  && !idImovel.equals("")) {

			Collection clientesImoveis = fachada.pesquisarClienteImovelPeloImovelParaEndereco(
					new Integer(idImovel));

			if (!clientesImoveis.isEmpty()) {
				ClienteImovel clienteImovel = (ClienteImovel) ((List) clientesImoveis)
						.get(0);
				
				Imovel imovel = clienteImovel.getImovel();

//				if (imovel.getImovelPerfil().getId().intValue() != ImovelPerfil.TARIFA_SOCIAL.intValue()){
//						consultarTarifaSocialActionForm.setIdImovel("");
//						consultarTarifaSocialActionForm.setInscricaoImovel("");
//						
//				}
				
				consultarTarifaSocialActionForm.setInscricaoImovel(imovel.getInscricaoFormatada());
				
				// Obter a quantidade de economias do im�vel escolhido
				int quantEconomias = Fachada.getInstancia()
						.obterQuantidadeEconomias(imovel);

				// Seta na sess�o
				sessao.setAttribute("imovel", imovel);
				sessao.setAttribute("quantEconomias", String
						.valueOf(quantEconomias));
				
				Collection colecaoTarifaSocialHelper = null;
				
				if (quantEconomias == 1) {
					colecaoTarifaSocialHelper = fachada.pesquisarDadosClienteTarifaSocial(new Integer(idImovel));
				} else {
					colecaoTarifaSocialHelper = fachada.pesquisarDadosClienteEconomiaTarifaSocial(new Integer(idImovel));
		        }
		        
		        if (colecaoTarifaSocialHelper != null && !colecaoTarifaSocialHelper.isEmpty()) {
		        	sessao.setAttribute("colecaoTarifaSocialHelper", colecaoTarifaSocialHelper);
		        } else {
		        	sessao.removeAttribute("colecaoTarifaSocialHelper");
		        	throw new ActionServletException(
							"atencao.imovel.nao.esta.tarifa_social",
							null, imovel.getId().toString());
		        } 
				
			} else {
				// Matr�cula inexistente
				httpServletRequest.setAttribute("imovelNaoEncontrado", true);
				consultarTarifaSocialActionForm.setIdImovel("");
				consultarTarifaSocialActionForm.setInscricaoImovel("Im�vel Inexistente");
				httpServletRequest.setAttribute("nomeCampo", "idImovel");
			}
		}
		
		return retorno;
	}

}
