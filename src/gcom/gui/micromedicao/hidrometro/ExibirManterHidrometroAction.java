package gcom.gui.micromedicao.hidrometro;

import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.FiltrarHidrometroHelper;
import gcom.micromedicao.hidrometro.FiltroHidrometro;
import gcom.micromedicao.hidrometro.Hidrometro;
import gcom.util.Util;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * < <Descri��o da Classe>>
 * 
 * @author Administrador
 * @created 8 de Setembro de 2005
 */
public class ExibirManterHidrometroAction extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping,
            ActionForm actionForm, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {

        ActionForward retorno = actionMapping.findForward("manterHidrometro");

        Collection hidrometros = null;

        //Mudar isso quando implementar a parte de seguran�a
        HttpSession sessao = httpServletRequest.getSession(false);
        
       if (httpServletRequest.getParameter("limpaSessao") != null && 
    	!httpServletRequest.getParameter("limpaSessao").equals("")) {

            sessao.removeAttribute("faixaInicial");
            sessao.removeAttribute("faixaFinal");
            sessao.removeAttribute("fixo");
        }
        
        if (sessao.getAttribute("i") != null){
			sessao.removeAttribute("i");
		}
        
        String atualizar = httpServletRequest.getParameter("atualizar");
        
        //Parte da verifica��o do filtro
        FiltroHidrometro filtroHidrometro = new FiltroHidrometro();
        
        //Verifica se o filtro foi informado pela p�gina de filtragem de
        // hidrometro
        if (sessao.getAttribute("filtroHidrometro") != null && 
        	sessao.getAttribute("voltarFiltrar") != null ) {
        	
        	filtroHidrometro = (FiltroHidrometro) sessao.getAttribute("filtroHidrometro");
        	sessao.removeAttribute("voltarFiltrar");
        	
        } else {
            if (sessao.getAttribute("fixo") == null && sessao.getAttribute("instalado") == null) {

            	//Caso o exibirManterHidrometro n�o tenha passado por algum esquema
                // de filtro, a quantidade de registros � verificada para avaliar a
                // necessidade de filtragem
                filtroHidrometro = new FiltroHidrometro();

                //Se o limite de registros foi atingido, a p�gina de
                // filtragem � chamada
                retorno = actionMapping.findForward("filtrarHidrometro");
            }else if (httpServletRequest.getParameter("voltarFiltro") != null){
            	retorno = actionMapping.findForward("filtrarHidrometro");
            }
        }

        //A pesquisa de hidrometros s� ser� feita se o forward estiver
        // direcionado para a p�gina de manterHidrometro
        if (retorno.getName().equalsIgnoreCase("manterHidrometro")) {

        	//caso venha do FiltrarHidrometro e foi escolhido como parametros o
            // fixo e a faixa da numera��o do hidrometro ent�o faz outra pesquisa sem o filtro.
            //Como � obrigat�rio a faixa caso coloque como parametro o fixo ,
            // ent�o n�o � necessario fazer a verifica��o da faixa s� do fixo.
            if (sessao.getAttribute("fixo") != null && 
            	!sessao.getAttribute("fixo").equals("")) {
                
            	String fixo = (String) sessao.getAttribute("fixo");
                String faixaInicial = (String) sessao.getAttribute("faixaInicial");
                String faixaFinal = (String) sessao.getAttribute("faixaFinal");
                
                // 1� Passo - Pegar o total de registros atrav�s de um count da consulta
        		// que aparecer� na tela
                String numeroFormatadoInicial = "";
    			String numeroFormatadoFinal = "";

    			numeroFormatadoInicial = Util.adicionarZerosEsquedaNumero(6,faixaInicial);
    			numeroFormatadoFinal = Util.adicionarZerosEsquedaNumero(6,faixaFinal);

                Integer totalRegistros = 
                	this.getFachada().pesquisarNumeroHidrometroFaixaCount(fixo,
                		fixo + numeroFormatadoInicial, 
                		fixo + numeroFormatadoFinal);
                
                // 2� Passo - Chamar a fun��o de Pagina��o passando o total de registros
        		retorno = 
        			this.controlarPaginacao(httpServletRequest, retorno,totalRegistros);
                
        		hidrometros = 
        			this.getFachada().pesquisarNumeroHidrometroFaixaPaginacao(fixo,
        				faixaInicial, 
        				faixaFinal, 
        				((Integer) httpServletRequest.getAttribute("numeroPaginasPesquisa")));
        		
        		if (hidrometros == null || hidrometros.isEmpty()) {

                    //Nenhum hidrometro cadastrado
        			throw new ActionServletException("atencao.pesquisa.nenhumresultado");

                }
//        		else
//                {
//                	hidrometros2 = fachada.pesquisarNumeroHidrometroFaixa(fixo,
//                            faixaInicial, faixaFinal);
//                }

                //seta um valor no form para que o bot�o
                // atualizarConjuntoHidrometro possa atualizar um
                //conjunto de hidrometro
                //hidrometroActionForm.setConjuntoHidrometro("1");
                httpServletRequest.setAttribute("conjuntoHidrometro",new Boolean(true));
                
            }else if(sessao.getAttribute("instalado") != null){
            	
            	FiltrarHidrometroHelper helper = (FiltrarHidrometroHelper) sessao.getAttribute("helper");
            
            	Integer totalRegistros = 
            		this.getFachada().pesquisarNumeroHidrometroSituacaoInstaladoPaginacaoCount(helper);
                
                // 2� Passo - Chamar a fun��o de Pagina��o passando o total de registros
        		retorno = 
        			this.controlarPaginacao(httpServletRequest, retorno,totalRegistros);
                
        		hidrometros = 
        			this.getFachada().pesquisarNumeroHidrometroSituacaoInstaladoPaginacao(helper,
        				((Integer) httpServletRequest.getAttribute("numeroPaginasPesquisa")));
        		
        		if (hidrometros == null || hidrometros.isEmpty()) {
                    //Nenhum hidrometro cadastrado
        			throw new ActionServletException("atencao.pesquisa.nenhumresultado");
                }
            	
            }else {
                //Seta a ordena��o desejada do filtro
            	filtroHidrometro.adicionarCaminhoParaCarregamentoEntidade("hidrometroMarca");
            	filtroHidrometro.adicionarCaminhoParaCarregamentoEntidade("hidrometroCapacidade");
            	filtroHidrometro.adicionarCaminhoParaCarregamentoEntidade("hidrometroSituacao");
                filtroHidrometro.setCampoOrderBy(FiltroHidrometro.NUMERO_HIDROMETRO);

                // Aciona o controle de pagina��o para que sejam pesquisados apenas
    			// os registros que aparecem na p�gina
    			Map resultado = 
    				controlarPaginacao(httpServletRequest, 
    					retorno,
    					filtroHidrometro, 
    					Hidrometro.class.getName());
    			
    			hidrometros = (Collection) resultado.get("colecaoRetorno");
    			retorno = (ActionForward) resultado.get("destinoActionForward");

                if (hidrometros == null || hidrometros.isEmpty()) {
                    throw new ActionServletException("atencao.pesquisa.nenhumresultado");
                } else if (atualizar != null && hidrometros.size() == 1) {
    	        	
                	Hidrometro hidrometro = (Hidrometro) hidrometros.iterator().next();
    	        	httpServletRequest.setAttribute("idRegistroAtualizacao",hidrometro.getId());
    	        	
    	        	retorno = actionMapping.findForward("exibirAtualizarHidrometro");
    	        } 
            }


//            if(hidrometros2 != null && !hidrometros2.isEmpty()){
//            	sessao.setAttribute("hidrometros2", hidrometros2);
//            }

            sessao.setAttribute("hidrometros", hidrometros);
            sessao.setAttribute("parametroInformado", "sim");
        }
        
        httpServletRequest.setAttribute("nomeCampo","numeroHidrometro");
        
        
        return retorno;
    }
}
