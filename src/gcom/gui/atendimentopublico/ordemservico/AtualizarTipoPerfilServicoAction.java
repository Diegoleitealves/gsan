package gcom.gui.atendimentopublico.ordemservico;
import gcom.atendimentopublico.ordemservico.EquipamentosEspeciais;
import gcom.atendimentopublico.ordemservico.ServicoPerfilTipo;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.filtro.ParametroSimples;
import gcom.util.tabelaauxiliar.abreviada.FiltroTabelaAuxiliarAbreviada;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;



/**
 * [UC0387] MANTER TIPO PERFIL SERVICO
 * [SB0001] Atualizar Tipo Perfil de Servi�o
 *
 * @author K�ssia Albuquerque
 * @date 01/11/2006
 */


public class AtualizarTipoPerfilServicoAction extends GcomAction{
	
	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		ActionForward retorno = actionMapping.findForward("telaSucesso");
		AtualizarTipoPerfilServicoActionForm atualizarTipoPerfilServicoActionForm = (AtualizarTipoPerfilServicoActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);		
		
		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");
		
		String equipamentoEspecial = atualizarTipoPerfilServicoActionForm.getEquipamentosEspeciais();
	 
		if (equipamentoEspecial != null && !equipamentoEspecial.trim().equals("")) {


			// [UC0387] MANTER TIPO PERFIL SERVICO 
			// [SB0001] Atualizar Tipo Perfil de Servi�o
			// [FS0001] Verifica a Exist�ncia de Equipamento Especial
			
			FiltroTabelaAuxiliarAbreviada filtroEquipamentoEspecial = new FiltroTabelaAuxiliarAbreviada();

			filtroEquipamentoEspecial.adicionarParametro(new ParametroSimples(FiltroTabelaAuxiliarAbreviada.ID, equipamentoEspecial));

			Collection colecaoEquipamentosEspeciais = fachada.pesquisar(filtroEquipamentoEspecial, EquipamentosEspeciais.class.getName());
	
			if (colecaoEquipamentosEspeciais == null || colecaoEquipamentosEspeciais.isEmpty()) {
				
				atualizarTipoPerfilServicoActionForm.setEquipamentosEspeciais("");
				throw new ActionServletException("atencao.equipamento_especial_inexistente");
			}
		}
		
		ServicoPerfilTipo servicoPerfilTipo = (ServicoPerfilTipo) sessao.getAttribute("servicoPerfilTipo");
		
		//Atualiza a entidade com os valores do formul�rio
		atualizarTipoPerfilServicoActionForm.setFormValues(servicoPerfilTipo);
		
		//atualiza na base de dados Tipo Perfil Servi�o
		 fachada.atualizarServicoTipoPerfil(servicoPerfilTipo,usuarioLogado);
		
		//Monta a p�gina de sucesso
		montarPaginaSucesso(httpServletRequest, "Perfil de Servi�o "+ atualizarTipoPerfilServicoActionForm.getDescricaoPerfil() +" atualizado com sucesso.", "Realizar outra Manuten��o de Perfil de Servi�o",
				"exibirFiltrarTipoPerfilServicoAction.do?menu=sim");
		
		return retorno;
	}
	
}	      
    



