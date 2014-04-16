package gcom.gui.cadastro.tarifasocial;

import java.util.Collection;

import gcom.cadastro.imovel.Imovel;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * < <Descri��o da Classe>>
 * 
 * @author rodrigo
 */
public class ExibirManterTarifaSocialDadosEconomiaAction extends GcomAction {
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

		ActionForward retorno = null;
		// Pega uma instancia da sessao
		HttpSession sessao = httpServletRequest.getSession(false);

		// Pega uma instancia do actionform
		ManterTarifaSocialActionForm manterTarifaSocialActionForm = (ManterTarifaSocialActionForm) actionForm;

		String idImovel = manterTarifaSocialActionForm.getIdImovel();
		
		String idRegistroAtendimento = manterTarifaSocialActionForm.getRegistroAtendimento();
		
		// Verifica se foi feita a pesquisa pelo RA ou pelo im�vel e em caso de
		// ter sido feita pelo im�vel seta um atributo na sess�o para bloquear
		// algumas a��es do usu�rio
		if (idRegistroAtendimento != null && !idRegistroAtendimento.trim().equals("")) {
			sessao.removeAttribute("pesquisaImovel");
		} else {
			sessao.setAttribute("pesquisaImovel", true);
		}
		
		Imovel imovel = new Imovel();
		
		imovel.setId(new Integer(idImovel));

		int quantidadeEconomiasImovel = new Integer(manterTarifaSocialActionForm.getQtdeEconomias());

		// Dependendo da quantidade de economias do imovel, o action ser�
		// redirecionado para o
		// caso de uso correspondente
		
			Collection colecaoTarifaSocialHelper = null;
        
        	colecaoTarifaSocialHelper = Fachada.getInstancia().pesquisarDadosClienteTarifaSocial(new Integer(idImovel));
        	if ( colecaoTarifaSocialHelper != null ) {
        		quantidadeEconomiasImovel = colecaoTarifaSocialHelper.size();
        	}
		if (quantidadeEconomiasImovel == 1) {
			// Chama o caso de uso [UC0065 - Inserir Dados Tarifa Social - Uma
			// Economia]
			sessao.removeAttribute("colecaoClienteImovelEconomia");
			retorno = actionMapping
					.findForward("manterTarifaSocialDadosUmaEconomia");

		} else if (quantidadeEconomiasImovel > 1) {
			// Chama o caso de uso [UC0066 - Inserir Dados Tarifa Social - Mais
			// de Uma Economia]
			sessao.removeAttribute("clienteImovel");
			sessao.removeAttribute("colecaoClienteImovel");
			sessao.removeAttribute("colecaoDadosTarifaSocial");
			retorno = actionMapping
					.findForward("manterTarifaSocialDadosMultiplasEconomias");
		}

		return retorno;
	}

}
