package gcom.gui.cobranca;

import java.util.Collection;

import gcom.fachada.Fachada;
import gcom.faturamento.FaturamentoGrupo;
import gcom.faturamento.FiltroFaturamentoGrupo;
import gcom.gui.GcomAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Pr�-processamento da primeira p�gina de 
 * [UC0911] - Gerar Cartas da Campanha de Solidariedade da Crian�a para Negocia��o a Vista
 * 
 * @author Vivianne Sousa
 * @data 15/06/2009
 */
public class ExibirGerarCartasCampanhaSolidariedadeCriancaNegociacaoAVistaAction extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		ActionForward retorno = actionMapping
				.findForward("exibirGerarCartasCampanhaSolidariedadeCriancaNegociacaoAVista");

//        HttpSession sessao = httpServletRequest.getSession(false);
		Fachada fachada = Fachada.getInstancia();
		
		FiltroFaturamentoGrupo filtroFaturamentoGrupo = new FiltroFaturamentoGrupo();
		filtroFaturamentoGrupo.setCampoOrderBy(FiltroFaturamentoGrupo.DESCRICAO);
		Collection colecaoFaturamentoGrupo = fachada.pesquisar(filtroFaturamentoGrupo, FaturamentoGrupo.class.getName());
		
		httpServletRequest.setAttribute("colecaoFaturamentoGrupo",colecaoFaturamentoGrupo);
		
		return retorno;
	}
	
}
