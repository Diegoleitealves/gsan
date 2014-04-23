package gcom.gui.seguranca.acesso.usuario;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.gui.ManutencaoRegistroActionForm;
import gcom.seguranca.acesso.usuario.FiltroSolicitacaoAcesso;
import gcom.seguranca.acesso.usuario.FiltroSolicitacaoAcessoSituacao;
import gcom.seguranca.acesso.usuario.SolicitacaoAcesso;
import gcom.seguranca.acesso.usuario.SolicitacaoAcessoSituacao;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.filtro.ParametroSimplesIn;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC1093] Manter Solicita��o de Acesso.
 * 
 * @author Hugo Leonardo
 * @date 17/11/2010
 */
public class AutorizarSolicitacaoAcessoAction extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");
		
		HttpSession sessao = httpServletRequest.getSession(false);
		
		ManutencaoRegistroActionForm manutencaoRegistroActionForm = (ManutencaoRegistroActionForm) actionForm;

		String[] ids = manutencaoRegistroActionForm.getIdRegistrosAutorizar();

		// mensagem de erro quando o usu�rio tenta Atualizar sem ter selecionado
		// nenhum registro
		if (ids == null || ids.length == 0) {
			throw new ActionServletException(
					"atencao.registros.nao_selecionados");
		}
		
		FiltroSolicitacaoAcesso filtroSolicitacaoAcesso = new FiltroSolicitacaoAcesso();
		
		Collection idsSolicitacaoAcesso = new ArrayList(ids.length);
		
		for (int i = 0; i < ids.length; i++) {
			idsSolicitacaoAcesso.add(new Integer(ids[i]));
		}
		
		filtroSolicitacaoAcesso.adicionarParametro(
				new ParametroSimplesIn(FiltroSolicitacaoAcesso.ID, idsSolicitacaoAcesso));

		Collection coletionSolicitacaoAcesso = Fachada.getInstancia().pesquisar(filtroSolicitacaoAcesso,
				SolicitacaoAcesso.class.getName());
		
		Iterator itera = coletionSolicitacaoAcesso.iterator();
		
		while(itera.hasNext()){
			
			SolicitacaoAcesso solicitacaoAcesso = (SolicitacaoAcesso) itera.next();
			
			FiltroSolicitacaoAcessoSituacao filtroSolicitacaoAcessoSituacao = new FiltroSolicitacaoAcessoSituacao();
			
			filtroSolicitacaoAcessoSituacao.adicionarParametro(
					new ParametroSimples(FiltroSolicitacaoAcessoSituacao.CODIGO_SITUACAO, 
							SolicitacaoAcessoSituacao.AG_CADASTRAMENTO));
			filtroSolicitacaoAcessoSituacao.adicionarParametro(
					new ParametroSimples(FiltroSolicitacaoAcessoSituacao.INDICADOR_USO, ConstantesSistema.SIM));
			
			Collection colecaoSolicitacaoAcessoSituacao = 
				this.getFachada().pesquisar(filtroSolicitacaoAcessoSituacao, 
					SolicitacaoAcessoSituacao.class.getName());
			
			if(!Util.isVazioOrNulo(colecaoSolicitacaoAcessoSituacao)){
				
				SolicitacaoAcessoSituacao solicitacaoAcessoSituacao = 
					(SolicitacaoAcessoSituacao) Util.retonarObjetoDeColecao(colecaoSolicitacaoAcessoSituacao);
				
				solicitacaoAcesso.setSolicitacaoAcessoSituacao(solicitacaoAcessoSituacao);
			}else{
				
				throw new ActionServletException("atencao.solicitacao_acesso_situacao.ag_cadastramento");
			}
			
			solicitacaoAcesso.setUltimaAlteracao(new Date());
			solicitacaoAcesso.setDataAutorizacao(new Date());
			
			Fachada.getInstancia().atualizar(solicitacaoAcesso);
		}

		Integer idQt = ids.length;

		montarPaginaSucesso(httpServletRequest, idQt.toString()
				+ " Solicita��es de Acesso(s) Autorizada(s) com sucesso.",
				"Manter outra Solicita��o de Acesso",
				"exibirFiltrarSolicitacaoAcessoAction.do?menu=sim");
		
		if(sessao.getAttribute("objeto") != null){
			String objeto = (String) sessao.getAttribute("objeto");
			
			if(objeto.equals("atualizar")){
				
				montarPaginaSucesso(httpServletRequest, idQt.toString()
						+ " Solicita��es de Acesso(s) Autorizada(s) com sucesso.",
						"Manter outra Solicita��o de Acesso",
						"exibirFiltrarSolicitacaoAcessoAction.do?menu=sim&objeto=atualizar");
				
			}else if(objeto.equals("autorizar")){
				
				montarPaginaSucesso(httpServletRequest, idQt.toString()
						+ " Solicita��es de Acesso(s) Autorizada(s) com sucesso.",
						"Manter outra Solicita��o de Acesso",
						"exibirFiltrarSolicitacaoAcessoAction.do?menu=sim&objeto=autorizar");
	
			}else if(objeto.equals("cadastrar")){
				
				montarPaginaSucesso(httpServletRequest, idQt.toString()
						+ " Solicita��es de Acesso(s) Autorizada(s) com sucesso.",
						"Manter outra Solicita��o de Acesso",
						"exibirFiltrarSolicitacaoAcessoAction.do?menu=sim&objeto=cadastrar");
			}
		}

		return retorno;
	}

}
