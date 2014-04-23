package gcom.gui.relatorio.seguranca;

import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.FiltroGrupo;
import gcom.seguranca.acesso.Grupo;
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
 * [UC1039] Gerar Relat�rio de Funcionalidades e Operacoes por Grupo.
 * 
 * @author Hugo Leonardo
 *
 * @date 15/07/2010
 */

public class ExibirGerarRelatorioFuncionalidadesOperacoesPorGrupoAction extends GcomAction {
	
	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping
			.findForward("exibirGerarRelatorioFuncionalidadesOperacoesPorGrupoAction");
		
		HttpSession sessao = httpServletRequest.getSession(false);
		
		FiltroGrupo filtroGrupo = new FiltroGrupo();
		
		filtroGrupo.setConsultaSemLimites(true);
		filtroGrupo.setCampoOrderBy(FiltroGrupo.DESCRICAO);

		filtroGrupo.adicionarParametro(
				new ParametroSimples(FiltroGrupo.INDICADOR_USO, 
				ConstantesSistema.INDICADOR_USO_ATIVO));
		
		Collection collectionGrupos = 
			this.getFachada().pesquisar(filtroGrupo, Grupo.class.getName());

		if (retorno != null) {
			if (collectionGrupos != null && !collectionGrupos.isEmpty()) {
				
				/*
				 * 
				// 1� Passo - Pegar o total de registros atrav�s de um count da
				// consulta que aparecer� na tela
				Integer totalRegistros = collectionGrupos.size();
	
				// 2� Passo - Chamar a fun��o de Pagina��o passando o total de
				// registros
				retorno = this.controlarPaginacao(httpServletRequest, retorno,
						totalRegistros);
	
				// 3� Passo - Obter a cole��o da consulta que aparecer� na tela
				// passando o numero de paginas
				// da pesquisa que est� no request
				collectionGrupos = Fachada.getInstancia()
						.pesquisarGrupos(
								filtroGrupo,
								((Integer) httpServletRequest
										.getAttribute("numeroPaginasPesquisa")));
				// [FS0003] Nenhum registro encontrado
				if (collectionGrupos == null
						|| collectionGrupos.isEmpty()) {
					// Nenhum criterio de cobran�a cadastrada
					throw new ActionServletException(
							"atencao.pesquisa.nenhumresultado");
				}
				
				*/
				sessao.setAttribute("collectionGrupos",	collectionGrupos);
			
			}else{
				throw new ActionServletException("atencao.naocadastrado", null, "Grupos");
			}
		}

		//sessao.removeAttribute("desabilita");
		return retorno;
	}

}
