package gcom.gui.util.tabelaauxiliar;

import gcom.gui.ActionServletException;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ComparacaoTexto;
import gcom.util.filtro.ComparacaoTextoCompleto;
import gcom.util.filtro.ParametroSimples;
import gcom.util.tabelaauxiliar.FiltroTabelaAuxiliar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

/**
 * < <Descri��o da Classe>>
 * 
 * @author Administrador
 */
public class FiltrarTabelaAuxiliarAction extends Action {
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

		//Seta o mapeamento de retorno
		ActionForward retorno = actionMapping
				.findForward("retornarFiltroTabelaAuxiliar");

		DynaValidatorForm pesquisarActionForm = (DynaValidatorForm) actionForm;

		//Recupera os par�metros do form
		String id = (String) pesquisarActionForm.get("id");
		String descricao = (String) pesquisarActionForm.get("descricao");
		String indicadorUso = (String) pesquisarActionForm.get("indicadorUso");
		String atualizar = (String) pesquisarActionForm.get("atualizar");
		String tipoPesquisa = (String) pesquisarActionForm.get("tipoPesquisa");

		//cria o filtro para Tabela Auxiliar 
		FiltroTabelaAuxiliar filtroTabelaAuxiliar = new FiltroTabelaAuxiliar();

		
		boolean peloMenosUmParametroInformado = false;
		
		//Insere os par�metros informados no filtro
		if (id != null && !id.trim().equalsIgnoreCase("")) {
			peloMenosUmParametroInformado = true;
			filtroTabelaAuxiliar
					.adicionarParametro(new ParametroSimples(
							FiltroTabelaAuxiliar.ID, id));
		}
		//Adiciona a Descri��o da Tabela Auxiliar ao filtro
		if (descricao != null && !descricao.equalsIgnoreCase("")) {			
			peloMenosUmParametroInformado = true;			
			if (tipoPesquisa != null && tipoPesquisa.equals(ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString())) {
				filtroTabelaAuxiliar.adicionarParametro(new ComparacaoTextoCompleto(FiltroTabelaAuxiliar.DESCRICAO, 
						descricao));
			} else {
				filtroTabelaAuxiliar.adicionarParametro(new ComparacaoTexto(FiltroTabelaAuxiliar.DESCRICAO, descricao));
			}
		}
		
		// Indicador uso
		if (indicadorUso != null && !indicadorUso.trim().equals("")) {
			peloMenosUmParametroInformado = true;
			filtroTabelaAuxiliar.adicionarParametro(new ParametroSimples(
					FiltroTabelaAuxiliar.INDICADORUSO, indicadorUso));
		}
	
		//Verifica se pelo menos um parametro foi informado
		if (!peloMenosUmParametroInformado) {
			throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
		}

		if (atualizar != null && atualizar.equalsIgnoreCase("1")) {
			httpServletRequest.setAttribute("atualizar", atualizar);
		}

		//Manda o filtro pelo request para o
		// ExibirManterTabelaAuxiliar
		httpServletRequest.setAttribute("filtroTabelaAuxiliar",
				filtroTabelaAuxiliar);

		//Devolve o mapeamento de retorno
		return retorno;
	}
}
