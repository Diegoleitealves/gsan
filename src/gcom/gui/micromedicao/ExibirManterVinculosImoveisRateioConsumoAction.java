package gcom.gui.micromedicao;

import gcom.cadastro.cliente.FiltroClienteImovel;
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
 * Manter Vinculos Imoveis Rateio Consumo
 * 
 * @author Rafael Santos
 * @since 11/01/2006
 */
public class ExibirManterVinculosImoveisRateioConsumoAction extends GcomAction {
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

        ActionForward retorno = actionMapping.findForward("exibirManterVinculosImoveisRateioConsumo");

        //Fachada fachada = Fachada.getInstancia();
        //DynaValidatorForm pesquisarActionForm = (DynaValidatorForm) actionForm;

        Collection imoveisVinculos = null;

        //Mudar isso quando implementar a parte de seguran�a
        HttpSession sessao = httpServletRequest.getSession(false);

        //Parte da verifica��o do filtro
        FiltroClienteImovel filtroClienteImovel = null;
        
        Fachada fachada = Fachada.getInstancia();
        
        //Verifica se o filtro foi informado pela p�gina de filtragem de
        // cliente
        if (httpServletRequest.getAttribute("filtroImovelPreenchido") != null) {
            filtroClienteImovel= (FiltroClienteImovel) httpServletRequest
                    .getAttribute("filtroImovelPreenchido");
        }         
        //A pesquisa de bairros s� ser� feita se o forward estiver direcionado
        //para a p�gina de manterBairro
        if (retorno.getName().equalsIgnoreCase("exibirManterVinculosImoveisRateioConsumo")) {
            //Seta a ordena��o desejada do filtro
            //filtroBairro.setCampoOrderBy(FiltroBairro.NOME);
            
            //Informa ao filtro para ele buscar objetos associados ao Bairro
            //filtroBairro.adicionarCaminhoParaCarregamentoEntidade("municipio.nome");
        	
            //Objetos que ser�o retornados pelo Hibernate atrav�s do clienteImovel
            filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel.logradouroCep.logradouro.logradouroTipo");
            filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel.logradouroCep.logradouro.logradouroTitulo");
            filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel.enderecoReferencia");
            filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel.logradouroBairro.bairro.municipio.unidadeFederacao");
            filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel.logradouroCep.cep");
            filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel.setorComercial");
            filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel.lote");
            filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel.subLote");
            filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel.localidade");
        	
            //imoveisVinculos = fachada.pesquisar(filtroClienteImovel, ClienteImovel.class.getName());

//            Map resultado = controlarPaginacao(httpServletRequest, retorno,
  //          		filtroClienteImovel, ClienteImovel.class.getName());
    //        imoveisVinculos = (Collection) resultado.get("colecaoRetorno");
      //      retorno = (ActionForward) resultado.get("destinoActionForward");
            String idLocalidade = (String) sessao.getAttribute("idLocalidade");
            String codigoSetorComercial = (String) sessao.getAttribute("idSetorComercial");
    	    String numeroQuadra = (String) sessao.getAttribute("idQuadra");
    	    String lote = (String) sessao.getAttribute("lote");
    	    String subLote = (String) sessao.getAttribute("subLote");
    	    String codigoCliente = (String) sessao.getAttribute("codigoCliente");
    	    String idMunicipio = (String) sessao.getAttribute("idMunicipio");
    	    String cep = (String) sessao.getAttribute("cep");
    	    String idBairro = (String) sessao.getAttribute("idBairro");
    	    String idLogradouro = (String) sessao.getAttribute("idLogradouro");
    	    String idImovel = (String) sessao.getAttribute("idImovel");
            

    		// 1� Passo - Pegar o total de registros atrav�s de um count da consulta que aparecer� na tela
    		int totalRegistros = fachada
    				.pesquisarQuantidadeImovel( idImovel,
    		    			 idLocalidade,
    		    		     codigoSetorComercial,
    		    		     numeroQuadra,
    		    		     lote,
    		    		     subLote,
    		    		     codigoCliente,
    		    		     idMunicipio,
    		    		     cep,
    		    		     idBairro,
    		    		     idLogradouro, null, null, true,false).intValue();

    		// 2� Passo - Chamar a fun��o de Pagina��o passando o total de registros
    		retorno = this.controlarPaginacao(httpServletRequest, retorno,
    				totalRegistros);

    		// 3� Passo - Obter a cole��o da consulta que aparecer� na tela passando o numero de paginas
    		// da pesquisa que est� no request
    		imoveisVinculos = fachada.pesquisarImovel(
        			 idImovel, 
        			 idLocalidade,
        		     codigoSetorComercial,
        		     numeroQuadra,
        		     lote,
        		     subLote,
        		     codigoCliente,
        		     idMunicipio,
        		     cep,
        		     idBairro,
        		     idLogradouro, null, null, true,false, ((Integer) httpServletRequest
     						.getAttribute("numeroPaginasPesquisa")));
            
            
            
            
            if (imoveisVinculos == null || imoveisVinculos.isEmpty()) {
                
                throw new ActionServletException(
                        "atencao.pesquisa.nenhumresultado", null, "Im�vel");
                 }

            /*
             * if (bairros.size() >
             * ConstantesSistema.NUMERO_MAXIMO_REGISTROS_MANUTENCAO) { throw new
             * ActionServletException("atencao.pesquisa.muitosregistros"); }
             */
            sessao.setAttribute("colecaoImoveisVinculos", imoveisVinculos);
            
        }
        return retorno;
    }
}
 
