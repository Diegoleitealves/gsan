package gcom.gui.faturamento;

import gcom.cadastro.cliente.ClienteImovel;
import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.cliente.FiltroClienteImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.fachada.Fachada;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.conta.ContaHistorico;
import gcom.faturamento.conta.FiltroConta;
import gcom.faturamento.conta.FiltroContaHistorico;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.Util;
import gcom.util.filtro.ParametroNulo;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * action respons�vel pela exibi��o da p�gina de consultar hist�rico de faturamento
 * 
 * @author  pedro alexandre
 * @created 04 de Janeiro de 2006
 */
public class ExibirConsultarHistoricoFaturamentoAction extends GcomAction {

    
    public ActionForward execute(ActionMapping actionMapping,
            					 ActionForm actionForm, 
            					 HttpServletRequest httpServletRequest,
            					 HttpServletResponse httpServletResponse) {
    	
    	//cria a vari�vel de retorno e seta o mapeamento para a tela de consultar hist�rico do faturamento
        ActionForward retorno = actionMapping.findForward("exibirConsultarHistoricoFaturamento");

        //cria uma inst�ncia da fachada
        Fachada fachada = Fachada.getInstancia();
                
        //cria uma inst�ncia da sess�o
        HttpSession sessao = httpServletRequest.getSession(false);

        //recupera o form de consultar hist�rico do faturamento
        ConsultarHistoricoFaturamentoActionForm consultarHistoricoFaturamentoActionForm = (ConsultarHistoricoFaturamentoActionForm) actionForm;
        
        //recupera a flag de limpar o form  
        String limparForm = httpServletRequest.getParameter("limparForm");
                        
        //se a flag n�o estiver vazia
        if (limparForm != null && !limparForm.equalsIgnoreCase("")){
        	//remove a cole��o de conta e im�vel da sess�o  
        	sessao.removeAttribute("colecaoContaImovel");
        }
        
        Collection colecaoContaHistoricoImovel = new ArrayList();
        Collection colecaoContaImovel = new ArrayList();
        Collection colecaoDebitoACobrarHistoricoImovel = new ArrayList();
        Collection colecaoDebitoACobrarImovel = new ArrayList();
        Collection colecaoCreditoARealizarHistoricoImovel = new ArrayList();
        Collection colecaoCreditoARealizarImovel = new ArrayList();
        Collection colecaoGuiaPagamentoHistoricoImovel = new ArrayList();
        Collection colecaoGuiaPagamentoImovel = new ArrayList();
        /*Pesquisar o im�vel a partir da matr�cula do im�vel informada na p�gina
        ====================================================================== */
        //recupera o c�digo do im�vel
        String idImovel = consultarHistoricoFaturamentoActionForm.getIdImovel();
        
        //recupera a flag de recarregar a p�gina
        String reloadPage = httpServletRequest.getParameter("reloadPage");
        
        //verifica se o c�digo im�vel n�o � nulo
        if (idImovel != null && !idImovel.equalsIgnoreCase("") &&
           (reloadPage == null || reloadPage.equalsIgnoreCase(""))){
        	
        	//recupera o objeto im�vel da cole��o
        	Imovel objetoImovel = fachada.pesquisarImovelDigitado(new Integer(idImovel));        	
        	       	
           	//Caso o im�vel informado pelo usu�rio esteja cadastrado no sistema
           	//Seta os dados o im�vel no form
           	//Caso contr�rio seta as informa��es o im�vel para vazio 
           	//e indica ao usu�rio que o im�vel informado n�o existe 
            if (objetoImovel != null ) {
              consultarHistoricoFaturamentoActionForm.setIdImovel("" + objetoImovel.getId());
              consultarHistoricoFaturamentoActionForm.setDescricaoImovel("" + objetoImovel.getInscricaoFormatada());
              httpServletRequest.setAttribute("idImovelNaoEncontrado", "true");
              
              /*Pesquisar o cliente usu�rio do im�vel selecionado
          	  ====================================================================== */
          	  //cria o filtro de cliente im�vel
          	  FiltroClienteImovel filtroClienteImovel = new FiltroClienteImovel();
          	
          	  //Objetos que ser�o retornados pelo hibernate.
          	  //carrega o cliente do im�vel no filtro
          	  filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("cliente");
          	
          	  //seta o c�digo do im�vel no filtro
          	  filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.IMOVEL_ID, idImovel));
          	
          	  //seta o tipo de rela��o do cliente com o im�vel no filtro 
          	  filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.CLIENTE_RELACAO_TIPO, ClienteRelacaoTipo.USUARIO));
          	
          	  //seta o fim da motivo da rela��o no filtro
          	  filtroClienteImovel.adicionarParametro(new ParametroNulo(FiltroClienteImovel.FIM_RELACAO_MOTIVO));
          	
          	  //pesquisa a rela��o do cliente im�vel
          	  Collection colecaoClienteImovel = fachada.pesquisar(filtroClienteImovel, ClienteImovel.class.getName());
          	
          	  // Verifica exist�ncia do cliente usu�rio
          	  if (colecaoClienteImovel == null || colecaoClienteImovel.isEmpty()){
          		throw new ActionServletException(
                          "atencao.naocadastrado", null, "cliente do tipo usu�rio foi");
          	  }
          	
          	  //recupera o objeto ClienteImovel da cole��o
          	  ClienteImovel objetoClienteImovel = (ClienteImovel) Util.retonarObjetoDeColecao(colecaoClienteImovel);
          	        	
          	  /*Pesquisar as contas do im�vel a partir da matr�cula do im�vel informada na p�gina
          	  ====================================================================== */
          	  //cria o filtro de conta
          	  FiltroConta filtroConta = new FiltroConta();
          	
          	  //seta a ordena��o da pesquisa no filtro
          	  filtroConta.setCampoOrderBy(FiltroConta.REFERENCIA + " desc");

          	  //Elimina o limite de 50 registro para a pesquisa
          	  filtroConta.setConsultaSemLimites(true);
          	
          	  //Objetos que ser�o retornados pelo hibernate
          	  filtroConta.adicionarCaminhoParaCarregamentoEntidade("debitoCreditoSituacaoAtual");
          	
          	        	        	        	        	        	             
          	  //seta o c�digo do im�vel no filtro
          	  filtroConta.adicionarParametro(new ParametroSimples(FiltroConta.IMOVEL_ID, objetoImovel.getId()));
          	  
          	        	
          	  //pesquisa a cole��o de contas do im�vel 
          	  colecaoContaImovel = fachada.pesquisar(filtroConta, Conta.class.getName());
          	  
          	  /*Fim da Pesquisa de contas do im�vel
          	   ===================================================================== */

          	  
          	  /*Pesquisar os hist�ricos das contas hist�rico do im�vel a partir da matr�cula do im�vel informada na p�gina
          	  ====================================================================== */
          	  //cria o filtro de hist�rico da conta
          	  FiltroContaHistorico filtroContaHistorico = new FiltroContaHistorico();
          	
          	  //seta a ordena��o da pesquisa no filtro
          	  filtroContaHistorico.setCampoOrderBy(FiltroContaHistorico.ANO_MES_REFERENCIA + " desc");

          	  //Elimina o limite de 50 registro para a pesquisa
          	  filtroContaHistorico.setConsultaSemLimites(true);
          	
          	  //Objetos que ser�o retornados pelo hibernate
          	  filtroContaHistorico.adicionarCaminhoParaCarregamentoEntidade("debitoCreditoSituacaoAtual");
          	
          	  //seta o c�digo do im�vel no filtro
          	  filtroContaHistorico.adicionarParametro(new ParametroSimples(FiltroContaHistorico.IMOVEL_ID, objetoImovel.getId()));
          	        	
          	  //pesquisa a cole��o de hist�rico das contas do im�vel 
          	  colecaoContaHistoricoImovel = fachada.pesquisar(filtroContaHistorico, ContaHistorico.class.getName());
          	  
          	  /*Fim da Pesquisa de hist�rico das contas do im�vel
          	   ===================================================================== */

          	  
          	  //Carregando as informa��es do im�vel no formul�rio de exibi��o.
          	  //seta a inscri��o do im�vel no form
          	  consultarHistoricoFaturamentoActionForm.setInscricaoImovel(objetoImovel.getInscricaoFormatada());      	
          	  //seta o nome do cliente no form 
          	  consultarHistoricoFaturamentoActionForm.setNomeClienteUsuario(objetoClienteImovel.getCliente().getNome());        	
          	  //seta a descri��o da situa��o de �gua no form
          	  consultarHistoricoFaturamentoActionForm.setSituacaoAguaImovel(objetoImovel.getLigacaoAguaSituacao().getDescricao());        	
          	  //seta a descri��o da situa��o de �gua no form
          	  consultarHistoricoFaturamentoActionForm.setSituacaoEsgotoImovel(objetoImovel.getLigacaoEsgotoSituacao().getDescricao());
          	
          	  //coloca na sess�o a cole��o com as contas do im�vel selecionado
          	  sessao.setAttribute("colecaoContaImovel", colecaoContaImovel); 
          	  sessao.setAttribute("colecaoContaHistoricoImovel", colecaoContaHistoricoImovel); 
          	  
          	  
          	  
          	 /* Pesquisar os debitos a cobrar e os debitos a cobrar historico do im�vel 
          	  * a partir da matr�cula do im�vel informada na p�gina
          	  ====================================================================== */
          	  colecaoDebitoACobrarImovel = fachada.obterDebitoACobrarImovel(objetoImovel.getId());
          	  colecaoDebitoACobrarHistoricoImovel = fachada.obterDebitoACobrarHistoricoImovel(objetoImovel.getId());
          	  
          	 //coloca na sess�o a cole��o com os debitos do im�vel selecionado
//           sessao.setAttribute("colecaoDebitoACobrarImovel", colecaoDebitoACobrarImovel); 
//           sessao.setAttribute("colecaoDebitoACobrarHistoricoImovel", colecaoDebitoACobrarHistoricoImovel); 
          	  
          	 /*Fim da Pesquisa de debitos a cobrar e os debitos a cobrar historico do im�vel
         	   ===================================================================== */
          	  
          	  
          	/* Pesquisar os creditos a realizar e os creditos a realizar historico do im�vel 
           	  * a partir da matr�cula do im�vel informada na p�gina
           	  ====================================================================== */
           	  colecaoCreditoARealizarImovel = fachada.obterCreditoARealizarImovel(objetoImovel.getId());
           	  colecaoCreditoARealizarHistoricoImovel = fachada.obterCreditoARealizarHistoricoImovel(objetoImovel.getId());
           	  
           	 //coloca na sess�o a cole��o com os creditos do im�vel selecionado
//           sessao.setAttribute("colecaoCreditoARealizarImovel", colecaoCreditoARealizarImovel); 
//           sessao.setAttribute("colecaoCreditoARealizarHistoricoImovel", colecaoCreditoARealizarHistoricoImovel); 
           	  
           	 /*Fim da Pesquisa de creditos a realizar e os creditos a realizar historico do im�vel
          	   ===================================================================== */
           	  
           	  
           	/* Pesquisar os creditos a realizar e os creditos a realizar historico do im�vel 
        	  * a partir da matr�cula do im�vel informada na p�gina
        	  ====================================================================== */
        	  colecaoGuiaPagamentoImovel = fachada.obterGuiaPagamentoImovel(objetoImovel.getId());
        	  colecaoGuiaPagamentoHistoricoImovel = fachada.obterGuiaPagamentoHistoricoImovel(objetoImovel.getId());
        	  
        	 //coloca na sess�o a cole��o com os creditos do im�vel selecionado
//        	  sessao.setAttribute("colecaoGuiaPagamentoImovel", colecaoGuiaPagamentoImovel); 
//        	  sessao.setAttribute("colecaoGuiaPagamentoHistoricoImovel", colecaoGuiaPagamentoHistoricoImovel); 
       	  
        	 /*Fim da Pesquisa de creditos a realizar e os creditos a realizar historico do im�vel
       	       ===================================================================== */
                                                   
            } else {
              consultarHistoricoFaturamentoActionForm.setIdImovel("");
              httpServletRequest.setAttribute("idImovelNaoEncontrado", "exception");
              consultarHistoricoFaturamentoActionForm.setDescricaoImovel("Matr�cula Inexistente");
            }
        	       	
        }else{
        	//remove da sess�o a cole��o das contas do im�vel
        	sessao.removeAttribute("colecaoContaImovel");
        	sessao.removeAttribute("colecaoContaHistoricoImovel");
        	
//			sessao.removeAttribute("colecaoDebitoACobrarImovel"); 
//			sessao.removeAttribute("colecaoDebitoACobrarHistoricoImovel"); 
//			sessao.removeAttribute("colecaoCreditoARealizarImovel"); 
//          sessao.removeAttribute("colecaoCreditoARealizarHistoricoImovel");
//          sessao.removeAttribute("colecaoGuiaPagamentoImovel"); 
//          sessao.removeAttribute("colecaoGuiaPagamentoHistoricoImovel");
        	
        }
        
        httpServletRequest.setAttribute("colecaoContaImovel", colecaoContaImovel); 
        httpServletRequest.setAttribute("colecaoContaHistoricoImovel", colecaoContaHistoricoImovel); 
        
        httpServletRequest.setAttribute("colecaoDebitoACobrarImovel", colecaoDebitoACobrarImovel); 
        httpServletRequest.setAttribute("colecaoDebitoACobrarHistoricoImovel", colecaoDebitoACobrarHistoricoImovel); 
        
        httpServletRequest.setAttribute("colecaoCreditoARealizarImovel", colecaoCreditoARealizarImovel); 
        httpServletRequest.setAttribute("colecaoCreditoARealizarHistoricoImovel", colecaoCreditoARealizarHistoricoImovel);
        
        httpServletRequest.setAttribute("colecaoGuiaPagamentoImovel", colecaoGuiaPagamentoImovel); 
        httpServletRequest.setAttribute("colecaoGuiaPagamentoHistoricoImovel", colecaoGuiaPagamentoHistoricoImovel);
        
        setarTamanhoColacoesRequest(colecaoContaHistoricoImovel, colecaoContaImovel,
                 colecaoDebitoACobrarHistoricoImovel, colecaoDebitoACobrarImovel,
                 colecaoCreditoARealizarHistoricoImovel, colecaoCreditoARealizarImovel,
                 colecaoGuiaPagamentoHistoricoImovel, colecaoGuiaPagamentoImovel, httpServletRequest);
        
        //retorna o mapeamento contido na vari�vel retorno
        return retorno;
    }
    
    
    private void setarTamanhoColacoesRequest(
    		Collection colecaoContaHistoricoImovel,
            Collection colecaoContaImovel,
            Collection colecaoDebitoACobrarHistoricoImovel,
            Collection colecaoDebitoACobrarImovel,
            Collection colecaoCreditoARealizarHistoricoImovel,
            Collection colecaoCreditoARealizarImovel,
            Collection colecaoGuiaPagamentoHistoricoImovel,
            Collection colecaoGuiaPagamentoImovel,
            HttpServletRequest httpServletRequest) {

        Integer tamanhoColecaoContas = 0;
        Integer tamanhoColecaoDebitos = 0;
        Integer tamanhoColecaoCreditos = 0;
        Integer tamanhoColecaoGuias = 0;
        
        if(colecaoContaImovel != null){
        	tamanhoColecaoContas = colecaoContaImovel.size();
        }
        if(colecaoContaHistoricoImovel != null){
        	tamanhoColecaoContas = tamanhoColecaoContas + colecaoContaHistoricoImovel.size();
        }
        
        if(colecaoDebitoACobrarImovel != null){
        	tamanhoColecaoDebitos = colecaoDebitoACobrarImovel.size();
        }
        if(colecaoDebitoACobrarHistoricoImovel != null){
        	tamanhoColecaoDebitos = tamanhoColecaoDebitos + colecaoDebitoACobrarHistoricoImovel.size();
        }
        
        if(colecaoCreditoARealizarImovel != null){
        	tamanhoColecaoCreditos = colecaoCreditoARealizarImovel.size();
        }
        if(colecaoCreditoARealizarHistoricoImovel != null){
        	tamanhoColecaoCreditos = tamanhoColecaoCreditos + colecaoCreditoARealizarHistoricoImovel.size();
        }
        
        if(colecaoGuiaPagamentoImovel != null){
        	tamanhoColecaoGuias = colecaoGuiaPagamentoImovel.size();
        }
        if(colecaoGuiaPagamentoHistoricoImovel != null){
        	tamanhoColecaoGuias = tamanhoColecaoGuias + colecaoGuiaPagamentoHistoricoImovel.size();
        }
        
        httpServletRequest.setAttribute("tamanhoColecaoContas", tamanhoColecaoContas);
        httpServletRequest.setAttribute("tamanhoColecaoDebitos", tamanhoColecaoDebitos); 
        httpServletRequest.setAttribute("tamanhoColecaoCreditos", tamanhoColecaoCreditos); 
        httpServletRequest.setAttribute("tamanhoColecaoGuias", tamanhoColecaoGuias); 
		
		
	}
}


