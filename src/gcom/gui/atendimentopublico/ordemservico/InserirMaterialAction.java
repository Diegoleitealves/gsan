package gcom.gui.atendimentopublico.ordemservico;

import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Descri��o da classe
 * 
 * @author R�mulo Aur�lio
 * @date 31/07/2006
 */
public class InserirMaterialAction extends GcomAction {

	/**
	 * Este caso de uso permite a inclus�o de um novo material
	 * 
	 * [UC0381] Inserir Material
	 * 
	 * 
	 * @author R�mulo Aur�lio
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

		ActionForward retorno = actionMapping.findForward("telaSucesso");

		InserirMaterialActionForm inserirMaterialActionForm = (InserirMaterialActionForm) actionForm;
		
		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();
		
		//Atualiza a entidade com os valores do formul�rio
		String descricao = inserirMaterialActionForm.getDescricao();

		String descricaoAbreviada = inserirMaterialActionForm.getDescricaoAbreviada();

		String unidadeMaterial = inserirMaterialActionForm.getUnidadeMaterial();
		
		String codigoMaterial = inserirMaterialActionForm.getCodigoMaterial();
		
		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");
		
		//Inserir na base de dados Material
		Integer idMaterial = fachada.inserirMaterial(codigoMaterial, descricao,descricaoAbreviada, unidadeMaterial, usuarioLogado);
		
		sessao.setAttribute("caminhoRetornoVoltar", "/gsan/exibirFiltrarMaterialAction.do");
			
		
		// Monta a p�gina de sucesso
		montarPaginaSucesso(httpServletRequest, "Material " + descricao
				+ " inserido com sucesso.", "Inserir outro Material",
				"exibirInserirMaterialAction.do?menu=sim",
				"exibirAtualizarMaterialAction.do?idRegistroInseridoAtualizar="+
				idMaterial ,"Atualizar Material Inserido" );
		
		sessao.removeAttribute("InserirMaterialActionForm");

		return retorno;
	}

}
