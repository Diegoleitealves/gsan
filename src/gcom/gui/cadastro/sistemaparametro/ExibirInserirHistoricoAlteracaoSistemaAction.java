package gcom.gui.cadastro.sistemaparametro;

import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.Funcionalidade;
import gcom.util.filtro.ParametroSimples;
import gcom.util.tabelaauxiliar.abreviada.FiltroTabelaAuxiliarAbreviada;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC_?_]Inserir Sistema Alteracao Historico
 * 
 * @author Tiago Ten�rio
 * @date 07/12/2006
 */

public class ExibirInserirHistoricoAlteracaoSistemaAction extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		ActionForward retorno = actionMapping
				.findForward("exibirInserirHistoricoAlteracaoSistema");

		Fachada fachada = Fachada.getInstancia();

		InserirHistoricoAlteracaoSistemaActionForm form = (InserirHistoricoAlteracaoSistemaActionForm) actionForm;

		// -- [UC_?_]Inserir Sistema Alteracao Historico
		// -- [FS0001] Verificar Exist�ncia da Funcionalidade --

		if ((form.getIdFuncionalidade() != null && !form.getIdFuncionalidade()
				.equals(""))) {

			FiltroTabelaAuxiliarAbreviada filtroTabelaAuxiliarAbreviada = new FiltroTabelaAuxiliarAbreviada();

			filtroTabelaAuxiliarAbreviada
					.adicionarParametro(new ParametroSimples(
							FiltroTabelaAuxiliarAbreviada.ID, form
									.getIdFuncionalidade()));

			Collection colecaoFuncionalidade = fachada.pesquisar(
					filtroTabelaAuxiliarAbreviada, Funcionalidade.class
							.getName());

			if (colecaoFuncionalidade != null
					&& !colecaoFuncionalidade.isEmpty()) {

				Funcionalidade funcionalidade = (Funcionalidade) colecaoFuncionalidade
						.iterator().next();
				form.setDescricaoFuncionalidade(funcionalidade.getDescricao());

			} else {
				httpServletRequest.setAttribute("funcionalidadeEncontrada",
						"exception");
				form.setIdFuncionalidade("");
				form.setDescricaoFuncionalidade("FUNCIONALIDADE INEXISTENTE");
			}

		}
		if (httpServletRequest.getParameter("menu") != null
				&& !httpServletRequest.getParameter("menu").equals("")) {
			httpServletRequest.setAttribute("nomeCampo", "idFuncionalidade");
		}
		return retorno;
	}
}
