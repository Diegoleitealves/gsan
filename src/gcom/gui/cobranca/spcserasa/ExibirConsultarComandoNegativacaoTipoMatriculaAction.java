package gcom.gui.cobranca.spcserasa;

import java.util.Collection;

import gcom.cobranca.bean.ComandoNegativacaoHelper;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;

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
public class ExibirConsultarComandoNegativacaoTipoMatriculaAction extends GcomAction {

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
		ActionForward retorno = actionMapping.findForward("exibirRespostaConsultaComandoNegativacaoMatricula");
		HttpSession sessao = httpServletRequest.getSession(false);

		ComandoNegativacaoHelper comandoNegativacaoHelper = null;
		
//		 Verifica se o filtro foi informado pela p�gina de filtragem do Negativador
		if (sessao.getAttribute("comandoNegativacaoHelper") != null) {
			comandoNegativacaoHelper = (ComandoNegativacaoHelper) sessao.getAttribute("comandoNegativacaoHelper");
		}
		
		Collection collNegativacaoComando = Fachada.getInstancia().pesquisarComandoNegativacaoTipoMatricula(comandoNegativacaoHelper);
    	
		if (collNegativacaoComando == null || collNegativacaoComando.isEmpty()) {
			throw new ActionServletException(
					"atencao.pesquisa.nenhumresultado");
		} 
		
//		 1� Passo - Pegar o total de registros atrav�s de um count da consulta que aparecer� na tela
		Integer totalRegistros = new Integer(collNegativacaoComando.size()); 

		// 2� Passo - Chamar a fun��o de Pagina��o passando o total de registros
		retorno = this.controlarPaginacao(httpServletRequest, retorno,	totalRegistros);

		// 3� Passo - Obter a cole��o da consulta que aparecer� na tela passando o numero de paginas
		// da pesquisa que est� no request
		
		collNegativacaoComando = Fachada.getInstancia().pesquisarComandoNegativacaoTipoMatricula(comandoNegativacaoHelper,(Integer) httpServletRequest
				.getAttribute("numeroPaginasPesquisa"));
		
		sessao.setAttribute("collectionNegativacaoComando", collNegativacaoComando);
		
		return retorno;
        
    }
}
