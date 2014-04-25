package gcom.gui.atendimentopublico;

import gcom.atendimentopublico.ligacaoesgoto.FiltroLigacaoEsgotoSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * @author Vin�cius de Melo Medeiros
 * @created 14/05/2008
 */
public class ExibirInserirLigacaoEsgotoSituacaoAction extends GcomAction {
	/**
	 * Description of the Method
	 * 
	 * @param actionMapping
	 *            Description of the Parameter
	 * @param actionForm
	 *            Description of the Parameter
	 * @param httpServletRequest
	 *            Description of the Parameter
	 * @param httpServletResponse
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		// Mudar isso quando houver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		// Seta o caminho de retorno
		ActionForward retorno = actionMapping.findForward("inserirLigacaoEsgotoSituacao");

		InserirLigacaoEsgotoSituacaoActionForm inserirLigacaoEsgotoSituacaoActionForm = (InserirLigacaoEsgotoSituacaoActionForm) actionForm;

		// Obt�m a inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		if ((httpServletRequest.getParameter("desfazer") != null && httpServletRequest
				.getParameter("desfazer").equalsIgnoreCase("S"))) {

			inserirLigacaoEsgotoSituacaoActionForm.setDescricao("");
			inserirLigacaoEsgotoSituacaoActionForm.setDescricaoAbreviado("");
			inserirLigacaoEsgotoSituacaoActionForm.setVolumeMinimoFaturamento("");
			inserirLigacaoEsgotoSituacaoActionForm.setIndicadorExistenciaLigacao("");
			inserirLigacaoEsgotoSituacaoActionForm.setIndicadorExistenciaRede("");
			inserirLigacaoEsgotoSituacaoActionForm.setIndicadorFaturamentoSituacao("");
			
			
			if (inserirLigacaoEsgotoSituacaoActionForm.getDescricao() == null
					|| inserirLigacaoEsgotoSituacaoActionForm.getDescricao().equals("")) {

				Collection colecaoPesquisa = null;

				FiltroLigacaoEsgotoSituacao filtroLigacaoEsgotoSituacao = new FiltroLigacaoEsgotoSituacao();

				filtroLigacaoEsgotoSituacao.setCampoOrderBy(FiltroLigacaoEsgotoSituacao.DESCRICAO);

				colecaoPesquisa = fachada.pesquisar(filtroLigacaoEsgotoSituacao,
						LigacaoEsgotoSituacao.class.getName());

				// Verifica se h� registros na tabela de Situa��o de Liga��o de �gua
				if (colecaoPesquisa == null || colecaoPesquisa.isEmpty()) {
					
					throw new ActionServletException(
							"atencao.pesquisa.nenhum_registro_tabela", null,
							"Situa��o de Liga��o de Esgoto");
				
				} else {
				
					sessao.setAttribute("colecaoLigacaoEsgotoSituacao", colecaoPesquisa);
				
				}

				// Cole��o de Situacao de Ligacao de Esgoto
				FiltroLigacaoEsgotoSituacao filtroLigEsgotoSituacao = new FiltroLigacaoEsgotoSituacao();
				filtroLigEsgotoSituacao.setCampoOrderBy(FiltroLigacaoEsgotoSituacao.ID);

				Collection colecaoLigEsgotoSituacao = fachada.pesquisar(filtroLigEsgotoSituacao,
						LigacaoEsgotoSituacao.class.getName());
				sessao.setAttribute("colecaoLigEsgotoSituacao", colecaoLigEsgotoSituacao);

			}

		}
		
		return retorno;
	
	}
}
