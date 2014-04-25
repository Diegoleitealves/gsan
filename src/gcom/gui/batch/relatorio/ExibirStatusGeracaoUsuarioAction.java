package gcom.gui.batch.relatorio;

import gcom.batch.FiltroProcesso;
import gcom.batch.Processo;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action respons�vel pela pre-exibi��o da pagina de status dos relat�rios batch
 * por usu�rio
 * 
 * @author Rodrigo Silveira
 * @created 25/10/2006
 */
public class ExibirStatusGeracaoUsuarioAction extends GcomAction {
	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		ActionForward retorno = actionMapping
				.findForward("statusGeracaoUsuario");

		Fachada fachada = Fachada.getInstancia();

		int idProcesso = converterStringToInt(httpServletRequest
				.getParameter("idProcesso"));

		FiltroProcesso filtroProcesso = new FiltroProcesso();
		filtroProcesso.adicionarParametro(new ParametroSimples(
				FiltroProcesso.ID, idProcesso));

		//Passa para a p�gina o nome do processo
		Processo processo = (Processo) Util.retonarObjetoDeColecao(fachada
				.pesquisar(filtroProcesso, Processo.class.getName()));

		httpServletRequest.setAttribute("nomeProcesso", processo
				.getDescricaoProcesso());

		// Pesquisar todos as funcionalidades iniciadas que representam os
		// relat�rios batch do sistema por usu�rio
		httpServletRequest.setAttribute("colecaoRelatoriosDadosUsuario",
				fachada.pesquisarRelatoriosBatchPorUsuarioSistema(idProcesso));

		return retorno;
	}
}
