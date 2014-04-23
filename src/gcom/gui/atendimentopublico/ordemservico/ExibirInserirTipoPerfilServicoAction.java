package gcom.gui.atendimentopublico.ordemservico;


import gcom.atendimentopublico.ordemservico.EquipamentosEspeciais;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.filtro.ParametroSimples;
import gcom.util.tabelaauxiliar.abreviada.FiltroTabelaAuxiliarAbreviada;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 
 * @author Ana Maria
 *  @date 31/07/2006
 */
public class ExibirInserirTipoPerfilServicoAction extends GcomAction {
	/**
	 * Este caso de uso permite a inclus�o de um Perfil de Servi�o
	 * 
	 * [UC0385] Efetuar Instala��o de Hidr�metro
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

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("inserirTipoPerfilServico");	
		InserirTipoPerfilServicoActionForm inserirTipoPerfilServicoActionForm = (InserirTipoPerfilServicoActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();
		
		String equipamentoEspecial = inserirTipoPerfilServicoActionForm.getEquipamentosEspeciais();
		// Verificar se o n�mero do hidr�metro n�o est� cadastrado
		if (equipamentoEspecial != null && !equipamentoEspecial.trim().equals("")) {

			// Filtro para descobrir id do Hidrometro
			FiltroTabelaAuxiliarAbreviada filtroEquipamentoEspecial = new FiltroTabelaAuxiliarAbreviada();

			filtroEquipamentoEspecial.adicionarParametro(new ParametroSimples(FiltroTabelaAuxiliarAbreviada.ID, equipamentoEspecial));

			Collection colecaoEquipamentosEspeciais = fachada.pesquisar(filtroEquipamentoEspecial, EquipamentosEspeciais.class.getName());
	
            if (colecaoEquipamentosEspeciais != null && !colecaoEquipamentosEspeciais.isEmpty()){
				EquipamentosEspeciais equipamentoEspecialIterator = (EquipamentosEspeciais) colecaoEquipamentosEspeciais.iterator().next();
				inserirTipoPerfilServicoActionForm.setEquipamentosEspeciais(equipamentoEspecialIterator.getId().toString());
				inserirTipoPerfilServicoActionForm.setDescricaoEquipamentoEspecial(equipamentoEspecialIterator.getDescricao());
				httpServletRequest.setAttribute("equipamentosEspecialEncontrado", "true");
			} else {
				// Exibe mensagem de c�digo inexiste e limpa o campo de c�digo
				httpServletRequest.setAttribute("equipamentosEspecialEncontrado", "exception");
				inserirTipoPerfilServicoActionForm.setEquipamentosEspeciais("");
				inserirTipoPerfilServicoActionForm.setDescricaoEquipamentoEspecial("Equipamento Especial inexistente");
			}
            
		}

		return retorno;
	}
}
