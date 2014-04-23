package gcom.gui.arrecadacao.pagamento;

import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.FiltroCliente;
import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.fachada.Fachada;
import gcom.faturamento.debito.DebitoCreditoSituacao;
import gcom.faturamento.debito.DebitoTipo;
import gcom.faturamento.debito.FiltroDebitoCreditoSituacao;
import gcom.faturamento.debito.FiltroDebitoTipo;
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
 * Action que define o pr�-processamento da p�gina de pesquisa de guia de pagamento para cliente ou im�vel
 *
 * @author Pedro Alexandre
 * @date 07/03/2006
 */
public class ExibirPesquisarGuiaPagamentoAction extends GcomAction {
	
	/**
	 * Pesquisa as guias de pagamento existentes para o im�vel ou cliente 
	 *
	 * [UC0249] Pesquisar Guia de Pagamento
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
	 * @date 07/03/2006
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

		//Seta o mapeamento de retorno para a tela de pesquisar guia de pagamento
		ActionForward retorno = actionMapping.findForward("pesquisarGuiaPagamento");
		
		//Cria uma inst�ncia da fachada 
		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);
		
		//Recupera o c�digo do im�vel e o do cliente do request
		String idImovel = httpServletRequest.getParameter("idImovel");
		String idCliente = httpServletRequest.getParameter("idCliente");
		
		//PesquisarGuiaPagamentoActionForm pesquisarGuiaPagamentoActionForm = (PesquisarGuiaPagamentoActionForm) actionForm;
			
		//Caso o c�digo do im�vel tenha sido informado pelo caso de uso que chamou a tela de pesquisar guia de pagamento,
		//Caso contr�rio verifica se o cliente foi informado
		if(idImovel != null && !idImovel.trim().equalsIgnoreCase("")){
			
			//Pesquisa se o im�vel informado esta cadastrado no sistema,
			//Caso contr�rio, levanta a exce��o para o usu�rio indicando que o im�vel n�o existe
			FiltroImovel filtroImovel = new FiltroImovel();
        	filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, idImovel));
        	Collection colecaoImovel = fachada.pesquisar(filtroImovel, Imovel.class.getName());
        	
        	//Caso o im�vel informado n�o tenha sido cadastrado no sistema
        	//Caso contr�rio manda o c�digo do im�vel no request e o c�digo do cliente como nulo
        	if (colecaoImovel == null || colecaoImovel.isEmpty()){
        		throw new ActionServletException("atencao.naocadastrado", null, "Im�vel");
        	}else{
        		httpServletRequest.setAttribute("idImovel",idImovel);
        		httpServletRequest.setAttribute("idCliente","");
        	}
        	
        	//Caso o c�digo do cliente tenha sido informado pelo caso de uso que chamou a tela de pesquisar guia de pagamento,
    		//Caso contr�rio levanta aexce��o para o usu�rio indicando que o im�vel ou o cliente tem que ser informado
		}else if(idCliente != null && !idCliente.trim().equalsIgnoreCase("")){
			
			//Pesquisa se o cliente informado esta cadastrado no sistema,
			//Caso contr�rio, levanta a exce��o para o usu�rio indicando que o cliente n�o existe
			FiltroCliente filtroCliente = new FiltroCliente();
        	filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, idCliente));
        	Collection colecaoCliente = fachada.pesquisar(filtroCliente, Cliente.class.getName());
        	
        	//Caso o cliente informado n�o tenha sido cadastrado no sistema
        	//Caso contr�rio manda o c�digo do cliente no request e o c�digo do im�vel como nulo
        	if (colecaoCliente == null || colecaoCliente.isEmpty()){
        		throw new ActionServletException("atencao.naocadastrado", null, "Cliente");
        	}else{
        		httpServletRequest.setAttribute("idImovel","");
        		httpServletRequest.setAttribute("idCliente",idCliente);
        	}
        	
		}else{
			//Indica que o usu�rio tem de informar o im�vel ou o cliente
			throw new ActionServletException("atencao.naoinformado",null, "Im�vel ou Cliente");
		}
		
		//Cria o filtro para pesquisar as situa��es de guia de pagamento no sistema
		//e seta a ordena��o do resultado da pesquisa pela descri��o da situa��o
		FiltroDebitoCreditoSituacao filtroDebitoCreditoSituacao = new FiltroDebitoCreditoSituacao();
		filtroDebitoCreditoSituacao.setCampoOrderBy(FiltroDebitoCreditoSituacao.DESCRICAO);
		
		//Pesquisa as situa��es de guia de pagamento no sistema  
		Collection colecaoSituacaoGuiaPagamento = fachada.pesquisar(filtroDebitoCreditoSituacao, DebitoCreditoSituacao.class.getName());
		
		//Caso nenhuma situa��o de guia de pagamento cadastrada no sistema
        if(colecaoSituacaoGuiaPagamento == null || colecaoSituacaoGuiaPagamento.isEmpty()){
        	//Levanta a exce��o para a pr�xima camada
        	throw new ActionServletException("atencao.naocadastrado",null, "Situa��o de Guia de Pagamento");
        	
        }else{
        	//Manda a cole��o de situa��o de guia de pagamento no request para a p�gina de pesquisar
        	httpServletRequest.setAttribute("colecaoSituacaoGuiaPagamento",colecaoSituacaoGuiaPagamento);
        }
        
        //Cria o filtro para pesquisar ao tipos de d�bito sem limite de registros de guia de pagamento no sistema
		//e seta a ordena��o do resultado da pesquisa pela descri��o do tipo de d�bito
        FiltroDebitoTipo filtroDebitoTipo = new FiltroDebitoTipo();
        filtroDebitoTipo.setConsultaSemLimites(true);
        filtroDebitoTipo.setCampoOrderBy(FiltroDebitoTipo.DESCRICAO);
        
        //Pesquisa os tipos de d�bito no sistema 
        Collection colecaoTipoDebito = fachada.pesquisar(filtroDebitoTipo, DebitoTipo.class.getName());
        
        //Caso nenhum tipo de d�bito 
        if(colecaoTipoDebito == null || colecaoTipoDebito.isEmpty()){
        	//Levanta a exce��o para a pr�xima camada
        	throw new ActionServletException("atencao.naocadastrado",null, "Tipo de D�bito");
        	
        }else{
        	//Manda a cole��o de tipo de d�bito de guia de pagamento no request para a p�gina de pesquisar
        	httpServletRequest.setAttribute("colecaoTipoDebito",colecaoTipoDebito);
        }
        
        
        
        if (httpServletRequest
				.getParameter("caminhoRetornoTelaPesquisaGuiaPagamento") != null) {
			sessao
					.setAttribute(
							"caminhoRetornoTelaPesquisaGuiaPagamento",
							httpServletRequest
									.getParameter("caminhoRetornoTelaPesquisaGuiaPagamento"));
		}
        
        
        
        //Retorna o mapeamento contido na vari�vel retorno 
		return retorno;
	}
}
