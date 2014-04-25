package gcom.gui.cobranca.spcserasa;

import gcom.cobranca.bean.ComandoNegativacaoTipoCriterioHelper;
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
 * Description of the Class
 * 
 * @author Thiago Vieira
 */
public class ExibirConsultarComandoNegativacaoTipoCriterioAction extends GcomAction {

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
		
//   	 localiza o action no objeto actionmapping
		ActionForward retorno = actionMapping.findForward("exibirConsultarComandoCriterio");
		HttpSession sessao = httpServletRequest.getSession(false);

		ComandoNegativacaoTipoCriterioHelper comandoNegativacaoTipoCriterioHelper = null;
		
		// Verifica se o filtro foi informado pela p�gina de filtragem do Negativador
//		 Verifica se o filtro foi informado pela p�gina de filtragem do Negativador
		if (sessao.getAttribute("comandoNegativacaoTipoCriterioHelper") != null) {
			comandoNegativacaoTipoCriterioHelper = (ComandoNegativacaoTipoCriterioHelper) sessao.getAttribute("comandoNegativacaoTipoCriterioHelper");
		}
    	
		Fachada fachada = Fachada.getInstancia();
    	Collection collNegativacaoCriterio = fachada.pesquisarComandoNegativacaoTipoCriterio(comandoNegativacaoTipoCriterioHelper);
    	
    	if (collNegativacaoCriterio == null || collNegativacaoCriterio.isEmpty()) {
			throw new ActionServletException(
					"atencao.pesquisa.nenhumresultado");
		} 
		
//		 1� Passo - Pegar o total de registros atrav�s de um count da consulta que aparecer� na tela
		Integer totalRegistros = new Integer(collNegativacaoCriterio.size()); 

		// 2� Passo - Chamar a fun��o de Pagina��o passando o total de registros
		retorno = this.controlarPaginacao(httpServletRequest, retorno,	totalRegistros);

		// 3� Passo - Obter a cole��o da consulta que aparecer� na tela passando o numero de paginas
		// da pesquisa que est� no request
		
		collNegativacaoCriterio = Fachada.getInstancia().pesquisarComandoNegativacaoTipoCriterio(comandoNegativacaoTipoCriterioHelper,(Integer) httpServletRequest
				.getAttribute("numeroPaginasPesquisa"));
		
		sessao.setAttribute("collNegativacaoCriterio", collNegativacaoCriterio);
		
		return retorno;
        
    }
}
