package gcom.gui.operacional.abastecimento;


import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.operacional.DivisaoEsgoto;
import gcom.operacional.FiltroDivisaoEsgoto;
import gcom.operacional.SistemaEsgotoTratamentoTipo;
import gcom.operacional.abastecimento.FiltroSistemaEsgotoTratamentoTipo;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * [UC0526]	FILTRAR SISTEMA DE ESGOTO
 * 
 * @author K�ssia Albuquerque
 * @date 12/03/2007
 */
 
public class ExibirFiltrarSistemaEsgotoAction extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		
			ActionForward retorno = actionMapping.findForward("filtrarSistemaEsgoto");	
			
			Fachada fachada = Fachada.getInstancia();
			
			HttpSession sessao = httpServletRequest.getSession(false);
			
			FiltrarSistemaEsgotoActionForm form = (FiltrarSistemaEsgotoActionForm) actionForm;
		
		
			//	C�digo para checar ou n�o o ATUALIZAR
	
			String primeiraVez = httpServletRequest.getParameter("menu");
			if (primeiraVez != null && !primeiraVez.equals("")) {
				sessao.setAttribute("indicadorAtualizar", "1");
				form.setTipoPesquisa(ConstantesSistema.TIPO_PESQUISA_INICIAL.toString());
				
			}
			
			//  Verificar a exist�ncia de dados
	
			// DIVIS�O DE ESGOTO
			
			FiltroDivisaoEsgoto filtroDivisaoEsgoto = new FiltroDivisaoEsgoto();
			
			filtroDivisaoEsgoto.adicionarParametro(new ParametroSimples
					(FiltroDivisaoEsgoto.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
			
			filtroDivisaoEsgoto.setCampoOrderBy(FiltroDivisaoEsgoto.DESCRICAO);
			
			Collection<DivisaoEsgoto> colecaoDivisaoEsgoto = fachada.pesquisar
						(filtroDivisaoEsgoto, DivisaoEsgoto.class.getName());
	
			if (colecaoDivisaoEsgoto == null || colecaoDivisaoEsgoto.isEmpty()) {
				throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null,"Divis�o de Esgoto");
			}
	
			httpServletRequest.setAttribute("colecaoDivisaoEsgoto",colecaoDivisaoEsgoto);
			

			
			// TIPO DE TRATAMENTO 
			
			FiltroSistemaEsgotoTratamentoTipo filtroSistemaEsgotoTratamentoTipo = new FiltroSistemaEsgotoTratamentoTipo();

			filtroSistemaEsgotoTratamentoTipo.adicionarParametro(new ParametroSimples
					(FiltroSistemaEsgotoTratamentoTipo.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
			
			filtroSistemaEsgotoTratamentoTipo.setCampoOrderBy(FiltroSistemaEsgotoTratamentoTipo.NOME);
			
			Collection<SistemaEsgotoTratamentoTipo> colecaoTipoTratamento = fachada.pesquisar
					(filtroSistemaEsgotoTratamentoTipo,SistemaEsgotoTratamentoTipo.class.getName());
	
			if (colecaoTipoTratamento == null || colecaoTipoTratamento.isEmpty()) {
				throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null,"Tipo de Tratamento");
			}
	
			httpServletRequest.setAttribute("colecaoTipoTratamento",colecaoTipoTratamento);
			
			
			//	Se voltou da tela de Atualizar Sistema de Esgoto
			if (sessao.getAttribute("voltar") != null && sessao.getAttribute("voltar").equals("filtrar")) {
				sessao.setAttribute("indicadorAtualizar", "1");
				if(sessao.getAttribute("tipoPesquisa") != null){
					form.setTipoPesquisa(sessao.getAttribute("tipoPesquisa").toString());
				}
			}
			sessao.removeAttribute("voltar");
			sessao.removeAttribute("tipoPesquisa");

		
		return retorno;
	}
}
