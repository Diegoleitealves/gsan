package gcom.gui.faturamento.conta;

import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.fachada.Fachada;
import gcom.faturamento.debito.DebitoCreditoSituacao;
import gcom.faturamento.debito.FiltroDebitoCreditoSituacao;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action que define o pr�-processamento da p�gina de pesquisar contas do im�vel 
 *
 * @author Pedro Alexandre
 * @date 02/03/2006
 */
public class ExibirPesquisarContaAction extends GcomAction {
	
	/**
	 * Pesquisa as contas existentes para o im�vel 
	 *
	 * [UC0248] Pesquisar Contas do Im�vel
	 *
	 * <Breve descri��o sobre o subfluxo>
	 *
	 * <Identificador e nome do subfluxo>	
	 *
	 * <Breve descri��o sobre o fluxo secund�rio>
	 *
	 * <Identificador e nome do fluxo secund�rio> 
	 *
	 * @author Pedro Alexandre
	 * @date 02/03/2006
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

		//Seta o mapeamento de retorno para a tela de pesquisar contas do im�vel
		ActionForward retorno = actionMapping.findForward("pesquisarConta");
		
		//Cria uma inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);
		
		//Recupera o c�digo do im�vel do request
		String idImovel = httpServletRequest.getParameter("idImovel");
		
		//PesquisarContaActionForm pesquisarContaActionForm = (PesquisarContaActionForm) actionForm;
		
				
		//Caso o c�digo do im�vel tenha sido informado pelo caso de uso que chamou a tela de pesquisar contas,
		//Caso contr�rio levanta a exce��o para o usu�rio indicando que o im�vel n�o foi informado
		if(idImovel != null && !idImovel.trim().equalsIgnoreCase("")){
			
			//Pesquisa se o im�vel informado esta cadastrado no sistema,
			//Caso contr�rio, levanta a exce��o para o usu�rio indicando que o im�vel n�o existe
			FiltroImovel filtroImovel = new FiltroImovel();
        	filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, idImovel));
        	Collection colecaoImovel = fachada.pesquisar(filtroImovel, Imovel.class.getName());

        	//Caso o im�vel informado n�o tenha sido cadastrado no sistema
        	if (colecaoImovel == null || colecaoImovel.isEmpty()){
        		throw new ActionServletException("atencao.naocadastrado", null, "Im�vel");
        	}
		}else{
			throw new ActionServletException("atencao.naoinformado",null, "Im�vel");
		}
	
		//Cria o filtro para pesquisar as situa��es de conta no sistema
		FiltroDebitoCreditoSituacao filtroDebitoCreditoSituacao = new FiltroDebitoCreditoSituacao();
		filtroDebitoCreditoSituacao.setCampoOrderBy(FiltroDebitoCreditoSituacao.DESCRICAO);
		
		
		Collection colecaoSituacaoConta = null;
		if (httpServletRequest.getParameter("situacaoConta") != null){
			// so mostras as situa��es : normal, retificada e incluida
			// utilizado a partir do inserir Pagamento (Manual)
			sessao.setAttribute("situacaoConta",httpServletRequest.getParameter("situacaoConta"));	
			
		}else{
			//Pesquisa todas as situa��es de conta cadastradas no sistema
			colecaoSituacaoConta = fachada.pesquisar(filtroDebitoCreditoSituacao, DebitoCreditoSituacao.class.getName());
			sessao.removeAttribute("situacaoConta");
		}
		
	
		//[FS0005] Caso n�o exista nenhuma situa��o de conta no sistema, levanta a exce��o para o usu�rio indicando que 
		//nenhuma situa��o de conta est� cadastrada no sistema.
		//Caso contr�rio, manda as situa��es de contas cadastradas no request e o c�digo do im�vel
        if((colecaoSituacaoConta == null || colecaoSituacaoConta.isEmpty())&& httpServletRequest.getParameter("situacaoConta") == null ){
        	throw new ActionServletException("atencao.naocadastrado",null, "Situa��o de Conta");
        }else{
        	httpServletRequest.setAttribute("colecaoSituacaoConta",colecaoSituacaoConta);
        	httpServletRequest.setAttribute("idImovel",idImovel);
        }
        
        
        if (httpServletRequest
				.getParameter("caminhoRetornoTelaPesquisaConta") != null) {
			sessao
					.setAttribute(
							"caminhoRetornoTelaPesquisaConta",
							httpServletRequest
									.getParameter("caminhoRetornoTelaPesquisaConta"));
		}
        
        
        //Retorna o mapeamento contido na vari�vel retorno
		return retorno;
	}
}
