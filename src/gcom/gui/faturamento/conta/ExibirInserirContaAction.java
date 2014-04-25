package gcom.gui.faturamento.conta;

import gcom.atendimentopublico.ligacaoagua.FiltroLigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.FiltroLigacaoEsgotoSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.cadastro.cliente.ClienteImovel;
import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.cliente.FiltroClienteImovel;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.Subcategoria;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.faturamento.conta.ContaMotivoInclusao;
import gcom.faturamento.conta.FiltroMotivoInclusaoConta;
import gcom.faturamento.debito.DebitoCobrado;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroNulo;
import gcom.util.filtro.ParametroSimples;
import gcom.util.filtro.ParametroSimplesDiferenteDe;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class ExibirInserirContaAction extends GcomAction {

    
    public ActionForward execute(ActionMapping actionMapping,
            ActionForm actionForm, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {

        //Seta o mapeamento de retorno
        ActionForward retorno = actionMapping
                .findForward("exibirInserirConta");

        Fachada fachada = Fachada.getInstancia();
        
        //Mudar isso quando tiver esquema de seguran�a
        HttpSession sessao = httpServletRequest.getSession(false);

        
        /*
		 * Colocado por Raphael Rossiter em 29/03/2007
		 * Objetivo: Manipula��o dos objetos que ser�o exibidos no formul�rio de acordo com a empresa
		 */
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		//httpServletRequest.setAttribute("empresaNome", sistemaParametro.getNomeAbreviadoEmpresa().trim());
		httpServletRequest.setAttribute("indicadorTarifaCategoria", sistemaParametro.getIndicadorTarifaCategoria().toString());

        //Inst�ncia do formul�rio que est� sendo utilizado
        InserirContaActionForm inserirContaActionForm = (InserirContaActionForm) actionForm;
        String limparForm = httpServletRequest.getParameter("limparForm");
        
        
        //DEFINI��O QUE IR� AUXILIAR O RETORNO DOS POPUPS
        sessao.setAttribute("UseCase", "INSERIRCONTA");
        
        Map<String, String[]> requestMap = httpServletRequest.getParameterMap();
        
        Collection colecaoCategoria = new ArrayList();
		Collection colecaoSubcategoria = new ArrayList();
        
        //Removendo cole��es da sess�o
        if (limparForm != null && !limparForm.equalsIgnoreCase("")){
        	sessao.removeAttribute("colecaoCategoria");
        	sessao.removeAttribute("colecaoDebitoCobrado");
        	sessao.removeAttribute("colecaoAdicionarCategoria");
        	sessao.removeAttribute("colecaoAdicionarDebitoTipo");
        }
        
        
        
        Collection colecaoMotivoInclusaoConta, colecaoSituacaoLigacaoAgua, colecaoSituacaoLigacaoEsgoto;
        
        //Carregar a data corrente do sistema
        //====================================
        SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
        Calendar dataCorrente = new GregorianCalendar();
        
        //Data Corrente
        httpServletRequest.setAttribute("dataAtual", formatoData
        .format(dataCorrente.getTime()));
        
        //Data Corrente + 60 dias
        dataCorrente.add(Calendar.DATE, 60);
        httpServletRequest.setAttribute("dataAtual60", formatoData
        .format(dataCorrente.getTime()));
        
        /*
         * Costante que informa o ano limite para o campo anoMesReferencia da conta
         */
        httpServletRequest.setAttribute("anoLimite", ConstantesSistema.ANO_LIMITE);
        
        
        /*Motivo da inclus�o (Carregar cole��o) e remover as cole��es que ainda est�o na sess�o
        ====================================================================== */
        if (sessao.getAttribute("colecaoMotivoInclusaoConta") == null) {
        	
        	
        	FiltroMotivoInclusaoConta filtroMotivoInclusaoConta = new FiltroMotivoInclusaoConta(
        			FiltroMotivoInclusaoConta.MOTIVO_INCLUSAO_CONTA);

        	filtroMotivoInclusaoConta.adicionarParametro(new ParametroSimples(
        			FiltroMotivoInclusaoConta.INDICADOR_USO,
                    ConstantesSistema.INDICADOR_USO_ATIVO));

        	colecaoMotivoInclusaoConta = fachada.pesquisar(filtroMotivoInclusaoConta,
        			ContaMotivoInclusao.class.getName());

            if (colecaoMotivoInclusaoConta == null
                    || colecaoMotivoInclusaoConta.isEmpty()) {
                throw new ActionServletException(
                        "atencao.pesquisa.nenhum_registro_tabela", null,
                        "MOTIVO_INCLUSAO_CONTA");
            } else {
                sessao.setAttribute("colecaoMotivoInclusaoConta",
                		colecaoMotivoInclusaoConta);
            }
        }
        
        
        /*Situa��o Liga��o �gua (Carregar cole��o)
        ====================================================================== */
        if (sessao.getAttribute("colecaoSituacaoLigacaoAgua") == null) {

        	FiltroLigacaoAguaSituacao filtroLigacaoAguaSituacao = new FiltroLigacaoAguaSituacao(
        			FiltroLigacaoAguaSituacao.DESCRICAO);

        	filtroLigacaoAguaSituacao.adicionarParametro(new ParametroSimples(
        			FiltroLigacaoAguaSituacao.INDICADOR_USO,
                    ConstantesSistema.INDICADOR_USO_ATIVO));

        	colecaoSituacaoLigacaoAgua = fachada.pesquisar(filtroLigacaoAguaSituacao,
        			LigacaoAguaSituacao.class.getName());

            if (colecaoSituacaoLigacaoAgua == null
                    || colecaoSituacaoLigacaoAgua.isEmpty()) {
                throw new ActionServletException(
                        "atencao.pesquisa.nenhum_registro_tabela", null,
                        "LIGACAO_AGUA_SITUACAO");
            } else {
                sessao.setAttribute("colecaoSituacaoLigacaoAgua",
                		colecaoSituacaoLigacaoAgua);
            }
        }
        
        
        /*Situa��o Liga��o Esgoto (Carregar cole��o)
         ====================================================================== */
        if (sessao.getAttribute("colecaoSituacaoLigacaoEsgoto") == null) {

        	FiltroLigacaoEsgotoSituacao filtroLigacaoEsgotoSituacao = new FiltroLigacaoEsgotoSituacao(
        			FiltroLigacaoEsgotoSituacao.DESCRICAO);

        	filtroLigacaoEsgotoSituacao.adicionarParametro(new ParametroSimples(
        			FiltroLigacaoEsgotoSituacao.INDICADOR_USO,
                    ConstantesSistema.INDICADOR_USO_ATIVO));

        	colecaoSituacaoLigacaoEsgoto = fachada.pesquisar(filtroLigacaoEsgotoSituacao,
        			LigacaoEsgotoSituacao.class.getName());

            if (colecaoSituacaoLigacaoEsgoto == null
                    || colecaoSituacaoLigacaoEsgoto.isEmpty()) {
                throw new ActionServletException(
                        "atencao.pesquisa.nenhum_registro_tabela", null,
                        "LIGACAO_ESGOTO_SITUACAO");
            } else {
                sessao.setAttribute("colecaoSituacaoLigacaoEsgoto",
                		colecaoSituacaoLigacaoEsgoto);
            }
        }
        
        
        /*Pesquisar o im�vel a partir da matr�cula do im�vel
        ====================================================================== */
        String idImovel = inserirContaActionForm.getIdImovel();
        String reloadPage = httpServletRequest.getParameter("reloadPage");
        
        if (idImovel != null && !idImovel.equalsIgnoreCase("") &&
        	(reloadPage == null || reloadPage.equalsIgnoreCase(""))){
        	
        	FiltroImovel filtroImovel = new FiltroImovel();
        	
        	//Objetos que ser�o retornados pelo hobernate
        	filtroImovel.adicionarCaminhoParaCarregamentoEntidade("localidade");
        	/*filtroImovel.adicionarCaminhoParaCarregamentoEntidade("setorComercial.codigo");
        	filtroImovel.adicionarCaminhoParaCarregamentoEntidade("quadra.numeroQuadra");
        	filtroImovel.adicionarCaminhoParaCarregamentoEntidade("ligacaoAguaSituacao.descricao");
        	filtroImovel.adicionarCaminhoParaCarregamentoEntidade("ligacaoEsgotoSituacao.descricao");
        	filtroImovel.adicionarCaminhoParaCarregamentoEntidade("ligacaoEsgoto.percentual");*/
            filtroImovel.adicionarCaminhoParaCarregamentoEntidade("setorComercial");
            filtroImovel.adicionarCaminhoParaCarregamentoEntidade("quadra");
            filtroImovel.adicionarCaminhoParaCarregamentoEntidade("ligacaoAguaSituacao");
            filtroImovel.adicionarCaminhoParaCarregamentoEntidade("ligacaoEsgotoSituacao");
            filtroImovel.adicionarCaminhoParaCarregamentoEntidade("ligacaoEsgoto");
            
        	//Realizando a pesquisa do im�vel a partir da matr�cula recebida
        	filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, idImovel));
        	
        	
        	/*
        	 * Apenas im�veis que n�o foram exclu�dos poder�o inserir conta
        	 * (IMOV_ICEXCLUSAO = 1)
        	 */
        	filtroImovel.adicionarParametro(new ParametroSimplesDiferenteDe(FiltroImovel.INDICADOR_IMOVEL_EXCLUIDO, 
        	Imovel.IMOVEL_EXCLUIDO));
        	
        	Collection colecaoImovel = fachada.pesquisar(filtroImovel, Imovel.class.getName());
        	
        	//[FS0001] - Verificar exist�ncia da matr�cula do im�vel
        	if (colecaoImovel == null || colecaoImovel.isEmpty()){
        		/* throw new ActionServletException(
                        "atencao.imovel.inexistente"); */
        		
        		httpServletRequest.setAttribute("corInscricao", "exception");
        		inserirContaActionForm.setIdImovel("");
        		inserirContaActionForm.setInscricaoImovel("Matr�cula Inexistente");
        		httpServletRequest.setAttribute("nomeCampo", "idImovel");
        	}
        	else{
        	
	        	Imovel objetoImovel = (Imovel) Util.retonarObjetoDeColecao(colecaoImovel);
	        	
	        	
	        	/*Pesquisar o cliente usu�rio do im�vel selecionado
	        	====================================================================== */
	        	FiltroClienteImovel filtroClienteImovel = new FiltroClienteImovel();
	        	
	        	//Objetos que ser�o retornados pelo hibernate.
	        	//filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("cliente.nome");
                filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("cliente");
                
	        	filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.IMOVEL_ID, idImovel));
	        	filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.CLIENTE_RELACAO_TIPO
	        	, ClienteRelacaoTipo.USUARIO));
	        	filtroClienteImovel.adicionarParametro(new ParametroNulo(FiltroClienteImovel.FIM_RELACAO_MOTIVO));
	        	
	        	Collection colecaoClienteImovel = fachada.pesquisar(filtroClienteImovel, ClienteImovel.class.getName());
	        	
	        	// Verifica exist�ncia do cliente usu�tio
	        	if (colecaoClienteImovel == null || colecaoClienteImovel.isEmpty()){
	        		throw new ActionServletException(
	                        "atencao.naocadastrado", null, "cliente do tipo usu�rio foi");
	        	}
	        	
	        	ClienteImovel objetoClienteImovel = (ClienteImovel) Util.retonarObjetoDeColecao(colecaoClienteImovel);
	        	
	        	//Carregando as informa��es do im�vel no formul�rio de exibi��o.
	        	inserirContaActionForm.setInscricaoImovel(objetoImovel.getInscricaoFormatada());
	        	inserirContaActionForm.setNomeClienteUsuario(objetoClienteImovel.getCliente().getNome());
	        	inserirContaActionForm.setSituacaoAguaImovel(objetoImovel.getLigacaoAguaSituacao().getDescricao());
	        	inserirContaActionForm.setSituacaoEsgotoImovel(objetoImovel.getLigacaoEsgotoSituacao().getDescricao());
	        	inserirContaActionForm.setSituacaoAguaConta(String.valueOf(objetoImovel.getLigacaoAguaSituacao().getId().intValue()));
	        	inserirContaActionForm.setSituacaoEsgotoConta(String.valueOf(objetoImovel.getLigacaoEsgotoSituacao().getId().intValue()));
	        	if (objetoImovel.getLigacaoEsgoto() != null){
	        		if (objetoImovel.getLigacaoEsgoto().getPercentual() != null){
	        			inserirContaActionForm.setPercentualEsgoto(Util.formatarMoedaReal(objetoImovel.getLigacaoEsgoto().getPercentual()));
	        		}
	        	}
	        	
	        	//Inicializa o formul�rio
	        	inserirContaActionForm.setValorAgua("");
	        	inserirContaActionForm.setValorEsgoto("");
	        	inserirContaActionForm.setValorDebito("");
	        	inserirContaActionForm.setValorTotal("");
	        	inserirContaActionForm.setConsumoAgua("");
	        	inserirContaActionForm.setConsumoEsgoto("");
	        	
	        	
	        	if (sistemaParametro.getIndicadorTarifaCategoria().equals(SistemaParametro.INDICADOR_TARIFA_SUBCATEGORIA)){
	        		
	        		//[UC0108] - Quantidade de economias por categoria
		        	colecaoSubcategoria = fachada.obterQuantidadeEconomiasSubCategoria(objetoImovel.getId());
		        	
		        	sessao.setAttribute("colecaoSubcategoria", colecaoSubcategoria);
	        	}
	        	else{
	        		
	        		//[UC0108] - Quantidade de economias por categoria
		        	colecaoCategoria = fachada.obterQuantidadeEconomiasCategoria(objetoImovel);
		        	
		        	sessao.setAttribute("colecaoCategoria", colecaoCategoria);
	        	}	        	
	        	
	        	
	        	
	        	//Remove a cole��o de debitos que foi selecionada com a matricula do im�vel anterior
	        	//sessao.removeAttribute("colecaoDebitoCobrado");
	        	
	        	//Colocando o faco pra o campo de ano m�s referencia
	        	httpServletRequest.setAttribute("nomeCampo",
	            "mesAnoConta");
        	}
        }
        if(sessao.getAttribute("colecaoCategoria") != null)
        {
        	Collection colecao = (Collection) sessao.getAttribute("colecaoCategoria");
        	Iterator iteratorColecaoCategoria = colecao.iterator();
        	
        	Categoria categoria = null;
        	String quantidadeEconomia = null;
        	int valor = 0;
    		while (iteratorColecaoCategoria.hasNext()) {
    			categoria = (Categoria) iteratorColecaoCategoria.next();
    			// teste para ver se existe na p�gina alguma categoria 
    			valor++;
    			if (requestMap.get("categoria"
    					+ categoria.getId()) != null) {

    				quantidadeEconomia = (requestMap.get("categoria" + categoria.getId()))[0];

    				if (quantidadeEconomia == null
    						|| quantidadeEconomia.equalsIgnoreCase("")) {
    					throw new ActionServletException(
    							"atencao.required", null,
    							"Quantidade de Economias");
    				}

    				categoria.setQuantidadeEconomiasCategoria(new Integer(quantidadeEconomia));
    			}
        	}
    		if(valor == 0)
    		{
    			sessao.setAttribute("existeColecao","nao");
    		}else{
    			sessao.removeAttribute("existeColecao");
			}
        }
        if(sessao.getAttribute("colecaoSubcategoria") != null)
        {
        	Collection colecao = (Collection) sessao.getAttribute("colecaoSubcategoria");
        	Iterator iteratorColecaoSubcategoria = colecao.iterator();
        	
        	Subcategoria subcategoria = null;
        	String quantidadeEconomia = null;
        	int valor = 0;
    		while (iteratorColecaoSubcategoria.hasNext()) {
    			subcategoria = (Subcategoria) iteratorColecaoSubcategoria.next();
    			// teste para ver se existe na p�gina alguma categoria 
    			valor++;
    			if (requestMap.get("subcategoria"
    					+ subcategoria.getId()) != null) {

    				quantidadeEconomia = (requestMap.get("subcategoria" + subcategoria.getId()))[0];

    				if (quantidadeEconomia == null
    						|| quantidadeEconomia.equalsIgnoreCase("")) {
    					throw new ActionServletException(
    							"atencao.required", null,
    							"Quantidade de Economias");
    				}

    				subcategoria.setQuantidadeEconomias(new Integer(quantidadeEconomia));
    			}
        	}
    		if(valor == 0)
    		{
    			sessao.setAttribute("existeColecao","nao");
    		}else{
    			sessao.removeAttribute("existeColecao");
			}
        }
        if(sessao.getAttribute("colecaoDebitoCobrado") != null)
        {
        	Collection colecaoDebito = (Collection) sessao.getAttribute("colecaoDebitoCobrado");
        	Iterator iteratorColecaoDebito = colecaoDebito.iterator();
        	
        	DebitoCobrado debitoCobrado = null;
        	String valor = null;
        	BigDecimal valor2 = new BigDecimal ("0.00"); 
        	
        	while (iteratorColecaoDebito.hasNext()) {
    			debitoCobrado = (DebitoCobrado) iteratorColecaoDebito.next();
    			// valor minimo
    			if (requestMap.get("debitoCobrado"
    					+ GcomAction.obterTimestampIdObjeto(debitoCobrado)) != null) {

    				valor = (requestMap.get("debitoCobrado" + GcomAction.obterTimestampIdObjeto(debitoCobrado)))[0];
    				
    				if (valor == null
    						|| valor.equalsIgnoreCase("")) {
    					throw new ActionServletException(
    							"atencao.required", null,
    							"D�bito Cobrado");
    				}
    				else{
    					valor2 = Util.formatarMoedaRealparaBigDecimal(valor);
    				}
    				
    				debitoCobrado.setValorPrestacao(valor2);
    			}
        	}
        }

        if (requestMap.get("percentualEsgoto") != null){
        	String percentualEsgoto = (String) requestMap.get("percentualEsgoto")[0];
        	inserirContaActionForm.setPercentualEsgoto(percentualEsgoto);
        	httpServletRequest.setAttribute("percentualEsgoto",percentualEsgoto);
        }
        
        
        //Limpando os campos ap�s remo��o ou inser��o de categorias ou d�bitos
        if (reloadPage != null && !reloadPage.equalsIgnoreCase("")){
        	
        	inserirContaActionForm.setValorAgua("");
        	inserirContaActionForm.setValorEsgoto("");
        	inserirContaActionForm.setValorDebito("");
        	inserirContaActionForm.setValorTotal("");
        }
        
        return retorno;
    }

}

