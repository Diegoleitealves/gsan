package gcom.gui.faturamento.debito;

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
 * Action que define o pr�-processamento da p�gina de pesquisar d�bitos a cobrar do im�vel 
 *
 * @author Pedro Alexandre
 * @date 13/03/2006
 */
public class ExibirPesquisarDebitoACobrarAction extends GcomAction {
	/**
	 * Pesquisa os d�bitos a cobrar existentes para o im�vel 
	 *
	 * [UC0271] Pesquisar D�bito a Cobrar
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
	 * @date 13/03/2006
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

		//Seta o mapeamento de retorno para a tela de pesquisar d�bitos a cobrar do im�vel
		ActionForward retorno = actionMapping.findForward("pesquisarDebitoACobrar");
		
		//Cria uma inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);
		//Recupera o c�digo do im�vel do request
		String idImovel = (String)httpServletRequest.getParameter("idImovel");
		
		//PesquisarDebitoACobrarActionForm pesquisarDebitoACobrarActionForm = (PesquisarDebitoACobrarActionForm)actionForm;
		
		
		
		
		/*
		  if (httpServletRequest.getParameter("limparForm") != null
	                && !httpServletRequest.getParameter("limparForm").equalsIgnoreCase("")) {
			  
			  pesquisarDebitoACobrarActionForm.setDataGeracaoDebitoFinal("");
			  pesquisarDebitoACobrarActionForm.setDataGeracaoDebitoInicial("");
			  pesquisarDebitoACobrarActionForm.setReferenciaDebitoFinal("");
			  pesquisarDebitoACobrarActionForm.setReferenciaDebitoInicial("");
			  
			  pesquisarDebitoACobrarActionForm.setIdTipoDebitoSelecionados(null);			  
			  pesquisarDebitoACobrarActionForm.setIdTipoDebito(null);
			  pesquisarDebitoACobrarActionForm.setIdSituacaoDebitoACobrar(null);
			  
			 // pesquisarDebitoACobrarActionForm.reset(actionMapping,httpServletRequest);
			  
		  }
		*/

	
		
		
		//Caso o c�digo do im�vel tenha sido informado pelo caso de uso que chamou a tela de pesquisar d�bito a cobrar,
		//Caso contr�rio levanta a exce��o para o usu�rio indicando que o im�vel n�o foi informado
		if(idImovel != null && !idImovel.trim().equalsIgnoreCase("")){
			
			//Pesquisa se o im�vel informado esta cadastrado no sistema,
			//Caso contr�rio, levanta a exce��o para o usu�rio indicando que o im�vel n�o existe
			FiltroImovel filtroImovel = new FiltroImovel();
        	filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, idImovel));
        	Collection colecaoImovel = fachada.pesquisar(filtroImovel, Imovel.class.getName());
        	
        	//Caso o im�vel informado n�o tenha sido cadastrado no sistema, levantaa exce��o para o usu�rio indicando que o
        	//im�vel informado n�o est� cadastrado no sistema
        	//Caso contr�rio manda o c�digo do im�vel no request
        	if (colecaoImovel == null || colecaoImovel.isEmpty()){
        		throw new ActionServletException("atencao.naocadastrado", null, "Im�vel");
        	}else{
        		httpServletRequest.setAttribute("idImovel",idImovel);
        	}
        	
		}else{
			throw new ActionServletException("atencao.naoinformado",null, "Im�vel");
		}
		
		//Cria o filtro para pesquisar as situa��es de conta no sistema
		FiltroDebitoCreditoSituacao filtroDebitoCreditoSituacao = new FiltroDebitoCreditoSituacao();
		filtroDebitoCreditoSituacao.setCampoOrderBy(FiltroDebitoCreditoSituacao.DESCRICAO);
		
		//Pesquisa todas as situa��es de d�bito a cobrar cadastradas no sistema
		Collection colecaoSituacaoDebitoACobrar = fachada.pesquisar(filtroDebitoCreditoSituacao, DebitoCreditoSituacao.class.getName());
		
		//[FS0005] Caso n�o exista nenhuma situa��o de d�bito a cobrar no sistema, levanta a exce��o para o usu�rio indicando que 
		//nenhuma situa��o de d�bito a cobrar est� cadastrada no sistema.
		//Caso contr�rio, manda as situa��es de d�bito a cobrar cadastradas no request 
        if(colecaoSituacaoDebitoACobrar == null || colecaoSituacaoDebitoACobrar.isEmpty()){
        	throw new ActionServletException("atencao.naocadastrado",null, "Situa��o de D�bito a Cobrar");
        }else{
        	httpServletRequest.setAttribute("colecaoSituacaoDebitoACobrar",colecaoSituacaoDebitoACobrar);
        }
        
        //Cria o filtro para pesquisar os tipos de d�bitos cadastrados no sistema
        //e seta a ordena��o do resultado pela descri��o do tipo de d�bito
        FiltroDebitoTipo filtroDebitoTipo = new FiltroDebitoTipo();
        filtroDebitoTipo.setConsultaSemLimites(true);
        filtroDebitoTipo.setCampoOrderBy(FiltroDebitoTipo.DESCRICAO);
        
        //Pesquisa todos os tipos de d�bitos cadastrados no sistema
        Collection colecaoTipoDebito = fachada.pesquisar(filtroDebitoTipo, DebitoTipo.class.getName());
        
        //[FS0005] Caso n�o exista nenhum tipo de d�bito de d�bito a cobrar no sistema, levanta a exce��o para o usu�rio indicando que 
		//nenhum tipo de d�bito de d�bito a cobrar est� cadastrado no sistema.
		//Caso contr�rio, manda os tipos de d�bito de d�bito a cobrar cadastrados no request 
        if(colecaoTipoDebito == null || colecaoTipoDebito.isEmpty()){
        	throw new ActionServletException("atencao.naocadastrado",null, "Tipo de D�bito");
        }else{
        	httpServletRequest.setAttribute("colecaoTipoDebito",colecaoTipoDebito);
        }
        
        if (httpServletRequest
				.getParameter("caminhoRetornoTelaPesquisaDebitoACobrar") != null) {
			sessao
					.setAttribute(
							"caminhoRetornoTelaPesquisaDebitoACobrar",
							httpServletRequest
									.getParameter("caminhoRetornoTelaPesquisaDebitoACobrar"));
		}
        
        
        //Retorna o mapeamento contido na vari�vel retorno
		return retorno;
	}
}
