package gcom.gui.seguranca.acesso;

import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.FiltroOperacao;
import gcom.seguranca.acesso.Operacao;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * Action respons�vel pela exibi��o da p�gina de manter opera��o 
 *
 * @author Pedro Alexandre
 * @date 01/08/2006
 */
public class ExibirManterOperacaoAction extends GcomAction {

	
	/**
	 * [UC0281] - Manter Opera��o 
	 *
	 * @author Pedro Alexandre
	 * @date 05/08/2006
	 *
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping,
								ActionForm actionForm, 
								HttpServletRequest httpServletRequest,
								HttpServletResponse httpServletResponse) {

		//Seta o mapeamento de retorno para a tela de manter opera��o
        ActionForward retorno = actionMapping.findForward("exibirManterOperacao");
        
        //Cria uma inst�ncia da sess�o
		HttpSession sessao = httpServletRequest.getSession(false);

		sessao.setAttribute("AtualizarOperacaoActionForm",null);
		
		//Cria a vari�vel que vai armazenar a cole��o de opera��es filtradas
        Collection colecaoOperacao = null;

        //Recupera o filtro da opera��o caso tenha na sess�o
        FiltroOperacao filtroOperacao = null;
		if (sessao.getAttribute("filtroOperacao") != null) {
			filtroOperacao = (FiltroOperacao) sessao.getAttribute("filtroOperacao");
		}
		filtroOperacao.adicionarCaminhoParaCarregamentoEntidade("funcionalidade");
		filtroOperacao.adicionarCaminhoParaCarregamentoEntidade("operacaoTipo");
		filtroOperacao.adicionarCaminhoParaCarregamentoEntidade("idOperacaoPesquisa");
		filtroOperacao.adicionarCaminhoParaCarregamentoEntidade("tabelaColuna");
		filtroOperacao.adicionarCaminhoParaCarregamentoEntidade(FiltroOperacao.ARGUMENTO_PESQUISA);
		
		/*
		 * Pesquisa a cole��o de opera��es para o esquema de pagina��o
		 * e recupera o mapeamnento de retorno 
		 */
		Map resultado = controlarPaginacao(httpServletRequest, retorno,	filtroOperacao, Operacao.class.getName());
		colecaoOperacao = (Collection) resultado.get("colecaoRetorno");
		retorno = (ActionForward) resultado.get("destinoActionForward");
			
		/*
		 * Caso a cole��o de pesquisa esteja vazia 
		 * levanta a exce��o para o usu�rio indicando que a pesquisa 
		 * n�o retornou nenhum registro
		 */
		if (colecaoOperacao == null || colecaoOperacao.isEmpty()) {
			throw new ActionServletException("atencao.pesquisa.nenhumresultado");
		}
		
		//Recupera a flag que indica que se o usu�rio quer ir direto para a tela do atualizar
		String identificadorAtualizar = (String)sessao.getAttribute("indicadorAtualizar");
			
		/*
		 * Caso a cloe��o de pesquisa tenha um �nico registro e a a
		 * flag do atualizar esteja marcada, recupera a opera��o 
		 * da cole��o e seta o c�digo da opera��o para ser atualizada 
		 * na sess�o. 
		 * Caso contr�rio manda a cole��o de opera��es para a 
		 * p�gina do manter.
		 */
		if (colecaoOperacao.size()== 1 && identificadorAtualizar != null){
			retorno = actionMapping.findForward("atualizarOperacao");
			Operacao operacao = (Operacao)colecaoOperacao.iterator().next();
			sessao.setAttribute("idRegistroAtualizar", new Integer (operacao.getId()).toString());
		}else{
			sessao.setAttribute("colecaoOperacao", colecaoOperacao);
		}

		//Seta o tipo da pesquisa na sess�o
		sessao.removeAttribute("tipoPesquisaRetorno");
		
		//Retorna o mapeamento contido na vari�vel retorno 
        return retorno;
    }
}
