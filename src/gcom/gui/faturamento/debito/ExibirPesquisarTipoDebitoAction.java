package gcom.gui.faturamento.debito;

import gcom.fachada.Fachada;
import gcom.financeiro.FiltroFinanciamentoTipo;
import gcom.financeiro.FinanciamentoTipo;
import gcom.financeiro.lancamento.FiltroLancamentoItemContabil;
import gcom.financeiro.lancamento.LancamentoItemContabil;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
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
 * Action que define o pr�-processamento da p�gina de pesquisa de tipos d�bitos 
 * 
 * @author Pedro Alexandre
 * @created 09 de mar�o de 2006
 */
public class ExibirPesquisarTipoDebitoAction extends GcomAction {
	
	/**
	 * consiste em pesquisar os tipos de d�bitos cadastrados no sistema
	 *
	 * [UC0303] Pesquisar Tipo de D�bito
	 *
	 * <Breve descri��o sobre o subfluxo>
	 *
	 * <Identificador e nome do subfluxo>	
	 *
	 * <Breve descri��o sobre o fluxo secund�rio>
	 *
	 * <Identificador e nome do fluxo secund�rio> 
	 *
	 * @author Administrador
	 * @date 09/03/2006
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

		//seta o mapeamento de retorno para a tela de pesquisar tipos de d�bitos
		ActionForward retorno = actionMapping.findForward("pesquisarTipoDebito");
		
		//cria uma inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();
		
		HttpSession sessao = httpServletRequest.getSession(false);
		  
		//se essa variavel tiver algum valor, isso indica que apenas do Tipo de Financimento SERVI�O deve ser carregado na colecao
		String tipoFinanciamentoServico = "";
		
		if (httpServletRequest.getParameter("tipoFinanciamentoServico") != null &&
				!httpServletRequest.getParameter("tipoFinanciamentoServico").equals("")){
			tipoFinanciamentoServico = httpServletRequest.getParameter("tipoFinanciamentoServico");
			sessao.setAttribute("tipoFinanciamentoServico",httpServletRequest.getParameter("tipoFinanciamentoServico"));
		}else if(sessao.getAttribute("tipoFinanciamentoServico")!= null &&
				!sessao.getAttribute("tipoFinanciamentoServico").equals("")){
			tipoFinanciamentoServico = (String)sessao.getAttribute("tipoFinanciamentoServico");
		}
		
		
		PesquisarTipoDebitoActionForm pesquisarTipoDebitoActionForm = (PesquisarTipoDebitoActionForm) actionForm;
        if ((httpServletRequest.getParameter("limparForm") != null
				&& httpServletRequest.getParameter("limparForm").equalsIgnoreCase("1")) 
				|| (httpServletRequest.getParameter("objetoConsulta") == null
						&& httpServletRequest.getParameter("tipoConsulta") == null
						&& httpServletRequest.getParameter("voltarPesquisa") == null)){
        	
        	pesquisarTipoDebitoActionForm.setIdTipoDebito("");
        	pesquisarTipoDebitoActionForm.setDescricao("");
        	pesquisarTipoDebitoActionForm.setIdTipoFinanciamento(null);
        	pesquisarTipoDebitoActionForm.setIdItemLancamentoContabil(null);
        	pesquisarTipoDebitoActionForm.setIntervaloValorLimiteInicial("");
        	pesquisarTipoDebitoActionForm.setIntervaloValorLimiteFinal("");
        	pesquisarTipoDebitoActionForm.setTipoPesquisa(ConstantesSistema.TIPO_PESQUISA_INICIAL.toString());
        	
        }
        
		//cria o filtro de lan�amentos de item cont�bil para pesquisa  
		FiltroLancamentoItemContabil filtroLancamentoItemContabil = new FiltroLancamentoItemContabil();

		//seta a ordena��o do resultado da pesquisa de lan�amentos de item cont�bil pela descri��o
		filtroLancamentoItemContabil.setCampoOrderBy(FiltroLancamentoItemContabil.DESCRICAO);
		
		//pesquisa a cole��o de lan�amentos de item cont�bil no sistema
		Collection colecaoLancamentoItemContabil = fachada.pesquisar(filtroLancamentoItemContabil, LancamentoItemContabil.class.getName());
		
		//se nenhum lan�amento de item cont�bil cadastrado no sistema
        if(colecaoLancamentoItemContabil == null || colecaoLancamentoItemContabil.isEmpty()){
        	//levanta a exce��o para a pr�xima camada
        	throw new ActionServletException("atencao.naocadastrado",null, "Lan�amento de Item Cont�bil");
        	
        }else{
        	//se existir lan�amento de item cont�bil cadastrado(s) no sistema, manda 
        	//a cole��o pesquisada no request para a p�gina de pesquisar item de lan�amento cont�bil
        	httpServletRequest.setAttribute("colecaoLancamentoItemContabil",colecaoLancamentoItemContabil);
        }
        
        //cria o filtro de tipo de financiamento para pesquisa  
        FiltroFinanciamentoTipo filtroFinanciamentoTipo = new FiltroFinanciamentoTipo();

		//seta para pesquisar apenas o tipo de financiamento SERVI�O
		if(tipoFinanciamentoServico != null && !tipoFinanciamentoServico.equals("")){
			filtroFinanciamentoTipo.adicionarParametro(new ParametroSimples(FiltroFinanciamentoTipo.ID,FinanciamentoTipo.SERVICO_NORMAL));
		}
        //seta a ordena��o do resultado da pesquisa de tipo de financiamento pela descri��o  
        filtroFinanciamentoTipo.setCampoOrderBy(FiltroFinanciamentoTipo.DESCRICAO);
        
        //pesquisa a cole��o de tipo(s) de financiamento no sistema
        Collection colecaoFinanciamentoTipo = fachada.pesquisar(filtroFinanciamentoTipo, FinanciamentoTipo.class.getName());
        
        //se nenhum tipo de financiamento cadastrado no sistema
        if(colecaoFinanciamentoTipo == null || colecaoFinanciamentoTipo.isEmpty()){
        	//levanta a exce��o para a pr�xima camada
        	throw new ActionServletException("atencao.naocadastrado",null, "Tipo de Financiamento");
        	
        }else{
        	//se existir tipo(s) de financiamento cadastrado(s) no sistema, manda 
        	//a cole��o pesquisada no request para a p�gina de pesquisar tipo de financiamento
        	httpServletRequest.setAttribute("colecaoFinanciamentoTipo",colecaoFinanciamentoTipo);
        }
        
        if (httpServletRequest.getParameter("caminhoRetornoTelaPesquisaTipoDebito") != null) {
			sessao.setAttribute("caminhoRetornoTelaPesquisaTipoDebito",
					httpServletRequest
							.getParameter("caminhoRetornoTelaPesquisaTipoDebito"));

		}
        

        
        //retorna o mapeamento contido na vari�vel "retorno"
		return retorno;
	}
}
