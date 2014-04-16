package gcom.gui.arrecadacao.banco;

import gcom.arrecadacao.banco.FiltroAgencia;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ComparacaoTexto;
import gcom.util.filtro.ComparacaoTextoCompleto;
import gcom.util.filtro.ParametroSimples;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Descri��o da classe
 * 
 * @author Thiago Ten�rio
 * @date 05/08/2006
 */
public class FiltrarAgenciaBancariaAction extends GcomAction {

	/**
	 * Este caso de uso permite Pesquisar um Tipo de Servic�o
	 * 
	 * [UC0437] Pesquisar Tipo de Servi�o de Refer�ncia
	 * 
	 * 
	 * @author Thiago Ten�rio
	 * @date 31/07/2006
	 * 
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */

	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		ActionForward retorno = actionMapping
				.findForward("exibirManterAgenciaBancaria");

		HttpSession sessao = httpServletRequest.getSession(false);

		FiltrarAgenciaBancariaActionForm filtrarAgenciaBancariaActionForm = (FiltrarAgenciaBancariaActionForm) actionForm;

		FiltroAgencia filtroAgencia = new FiltroAgencia();

		// Fachada fachada = Fachada.getInstancia();

		boolean peloMenosUmParametroInformado = false;

		String bancoID = filtrarAgenciaBancariaActionForm.getBancoID();

		String codigo = filtrarAgenciaBancariaActionForm.getCodigo();

		String nome = filtrarAgenciaBancariaActionForm.getNome();
		
		String tipoPesquisa = filtrarAgenciaBancariaActionForm.getTipoPesquisa();

		// Verifica se o campo C�digo foi informado

		if (codigo != null && !codigo.trim().equalsIgnoreCase("")) {

			peloMenosUmParametroInformado = true;

			filtroAgencia.adicionarParametro(new ComparacaoTexto(
					FiltroAgencia.CODIGO_AGENCIA, codigo));

		}

		// Verifica se o campo Descri��o foi informado

		if (nome != null && !nome.trim().equalsIgnoreCase("")) {

			peloMenosUmParametroInformado = true;

			if (tipoPesquisa != null
					&& tipoPesquisa
							.equals(ConstantesSistema.TIPO_PESQUISA_COMPLETA
									.toString())) {
				filtroAgencia.adicionarParametro(new ComparacaoTextoCompleto(
						FiltroAgencia.NOME_AGENCIA, nome));
			} else {
				filtroAgencia.adicionarParametro(new ComparacaoTexto(
						FiltroAgencia.NOME_AGENCIA, nome));
			}

		}

		// Verifica se o campo Refer�ncia do Tipo de Servi�o foi informado

		if (bancoID != null
				&& !bancoID.trim().equalsIgnoreCase(
						"" + ConstantesSistema.NUMERO_NAO_INFORMADO)) {

			peloMenosUmParametroInformado = true;

			filtroAgencia.adicionarParametro(new ParametroSimples(
					FiltroAgencia.BANCO_ID, bancoID));

		}

		// Erro caso o usu�rio mandou Pesquisar sem nenhum par�metro
		if (!peloMenosUmParametroInformado) {
			throw new ActionServletException(
					"atencao.filtro.nenhum_parametro_informado");
		}

		// filtroGerenciaRegional.adicionarCaminhoParaCarregamentoEntidade("gerenciaRegional");
		
		// Verifica se o checkbox Atualizar est� marcado e em caso afirmativo
		// manda pela sess�o uma vari�vel para o
		// ExibirManterEquipeAction e nele verificar se ir� para o
		// atualizar ou para o manter, caso o checkbox esteja desmarcado remove
		// da sess�o
		String indicadorAtualizar = httpServletRequest
				.getParameter("atualizar");

		if (indicadorAtualizar != null && !indicadorAtualizar.equals("")) {
			sessao.setAttribute("atualizar", indicadorAtualizar);
		} else {
			sessao.removeAttribute("atualizar");
		}

		sessao.setAttribute("filtroAgencia", filtroAgencia);

		return retorno;
	}

}
