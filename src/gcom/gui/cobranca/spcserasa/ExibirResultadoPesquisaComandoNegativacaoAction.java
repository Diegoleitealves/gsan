package gcom.gui.cobranca.spcserasa;

import gcom.cobranca.bean.ComandoNegativacaoHelper;
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
 * [UC 0653] Pesquisar Comando Negativa��o
 * 
 * @author K�ssia Albuquerque
 * @date 22/10/2007
 */

public class ExibirResultadoPesquisaComandoNegativacaoAction extends GcomAction{
	
	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse){
		
			ActionForward retorno = actionMapping.findForward("resultadoPesquisaComandoNegativacao");
			
			HttpSession sessao = httpServletRequest.getSession(false);
			
			Fachada fachada = Fachada.getInstancia();
	
			ComandoNegativacaoHelper comandoNegativacaoHelper = (ComandoNegativacaoHelper) sessao.getAttribute("comandoNegativacaoHelper");
			
			
	        if (comandoNegativacaoHelper != null) {
	        	Integer totalRegistrosPrimeiraPaginacao = 0;
	        	if(sessao.getAttribute("totalRegistrosPrimeiraPaginacao") == null ){
	        		
	        		totalRegistrosPrimeiraPaginacao = fachada.pesquisarComandoNegativacao(comandoNegativacaoHelper);
	        	
	        		// 1� Passo - Pegar o total de registros atrav�s de um count da consulta que aparecer� na tela
	        		
	        		
	        	}else{
	        		totalRegistrosPrimeiraPaginacao = (Integer)sessao.getAttribute("totalRegistrosPrimeiraPaginacao");
	        	}
				// 2� Passo - Chamar a fun��o de Pagina��o passando o total de registros
				retorno = this.controlarPaginacao(httpServletRequest, retorno,totalRegistrosPrimeiraPaginacao,true);

				// 3� Passo - Obter a cole��o da consulta que aparecer� na tela passando o numero de paginas
				// da pesquisa que est� no request
				Collection collectionComandoNegativacao = fachada.pesquisarComandoNegativacaoParaPaginacao(comandoNegativacaoHelper,
						(Integer) httpServletRequest.getAttribute("numeroPaginasPesquisaPrimeiraPaginacao"));

				
				//[FS0006 NENHUM REGISTRO ENCONTRADO]
				if (collectionComandoNegativacao == null || collectionComandoNegativacao.isEmpty()) {
					throw new ActionServletException("atencao.pesquisa.nenhumresultado");
				}
				String popup = (String) sessao.getAttribute("popup");
				if (popup != null && popup.equals("2")) {
					sessao.setAttribute("popup", popup);
				} else {
					sessao.removeAttribute("popup");
				}
				
				sessao.setAttribute("collectionComandoNegativacao", collectionComandoNegativacao);
				sessao.setAttribute("totalRegistrosPrimeiraPaginacao", totalRegistrosPrimeiraPaginacao);
				sessao.setAttribute("numeroPaginasPesquisaPrimeiraPaginacao",httpServletRequest.getAttribute("numeroPaginasPesquisaPrimeiraPaginacao"));
				
			}
			
			return retorno;
		
		
	} 
	
}
