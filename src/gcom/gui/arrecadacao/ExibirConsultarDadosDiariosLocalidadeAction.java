package gcom.gui.arrecadacao;

import gcom.arrecadacao.FiltroConsultarDadosDiariosArrecadacao;
import gcom.arrecadacao.FiltroConsultarDadosDiariosArrecadacao.GROUP_BY;
import gcom.arrecadacao.bean.FiltrarDadosDiariosArrecadacaoHelper;
import gcom.cadastro.localidade.FiltroGerenciaRegional;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.FiltroUnidadeNegocio;
import gcom.cadastro.localidade.GerenciaRegional;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.UnidadeNegocio;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Description of the Class
 * 
 * @author Fernanda Paiva, Francisco do Nascimento
 * @date 26 de Maio de 2006, 08/10/2008
**/
public class ExibirConsultarDadosDiariosLocalidadeAction extends GcomAction {
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

		ActionForward retorno = actionMapping
				.findForward("exibirConsultarDadosDiariosLocalidade");
		
		String referencia = httpServletRequest.getParameter("referencia");
		
		String idGerencia = httpServletRequest.getParameter("idGerencia");
		
		String idEloPopup = httpServletRequest.getParameter("idEloPopup");
		
		String idUnidadeNegocio = httpServletRequest.getParameter("idUnidadeNegocio");
		
		Fachada fachada = Fachada.getInstancia();
		
		// Mudar isso quando implementar a parte de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		FiltroConsultarDadosDiariosArrecadacao filtro = (FiltroConsultarDadosDiariosArrecadacao)
			sessao.getAttribute("filtroConsultarDadosDiariosArrecadacao");
		Integer periodoArrecadacaoInicial = (Integer) 
			sessao.getAttribute("periodoArrecadacaoInicial");
		Integer periodoArrecadacaoFinal = (Integer) 
			sessao.getAttribute("periodoArrecadacaoFinal");
		
		BigDecimal valorTotalGerencia = new BigDecimal(httpServletRequest.getParameter("valorTotalGerencia"));
		BigDecimal valorTotalUNEG = new BigDecimal(httpServletRequest.getParameter("valorTotalUnidadeNegocio"));
		BigDecimal valorTotalElo = new BigDecimal(httpServletRequest.getParameter("valorTotalElo"));
		sessao.setAttribute("valorTotalGerencia", valorTotalGerencia);
		sessao.setAttribute("valorTotalUnidadeNegocio", valorTotalUNEG);		
		sessao.setAttribute("valorTotalElo",valorTotalElo);
		sessao.setAttribute("idGerencia", idGerencia);
		sessao.setAttribute("idUnidadeNegocio", idUnidadeNegocio);
		sessao.setAttribute("idEloPopup", idEloPopup);
		
		if (idGerencia != null && !idGerencia.equals("") && !idGerencia.equals("-1")){
			
			// pesquisar na base a gerencia Regional
			FiltroGerenciaRegional filtroGerenciaRegional = new FiltroGerenciaRegional ();
			filtroGerenciaRegional.adicionarParametro(new ParametroSimples(FiltroGerenciaRegional.ID,
					idGerencia));
			
			Collection colecaoGerenciaRegional = fachada.pesquisar(filtroGerenciaRegional,
					GerenciaRegional.class.getName());
			
			GerenciaRegional gerenciaRegional = (GerenciaRegional) 
				Util.retonarObjetoDeColecao(colecaoGerenciaRegional);
			if (gerenciaRegional != null) {
				sessao.setAttribute("nomeGerencia", gerenciaRegional.getNomeAbreviado() + " - " + gerenciaRegional.getNome());
			} else {
				sessao.removeAttribute("nomeGerencia");
			}
			
		} else if(idGerencia.equals("-1")){
			sessao.setAttribute("nomeGerencia", "TODAS");
		} else {
			sessao.removeAttribute("nomeGerencia");
		}
		
		
		if (idUnidadeNegocio != null && !idUnidadeNegocio.equals("") && 
			!idUnidadeNegocio.equals("-1")){
			
			//pesquisar na base a gerencia
			FiltroUnidadeNegocio filtroUnidadeNegocio = new FiltroUnidadeNegocio();
			
			filtroUnidadeNegocio.adicionarParametro(new ParametroSimples(FiltroUnidadeNegocio.ID,
					idUnidadeNegocio));
			
			Collection colecaoUnidadeNegocio = fachada.pesquisar(filtroUnidadeNegocio,
					UnidadeNegocio.class.getName());
			
			UnidadeNegocio unidadeNegocio = (UnidadeNegocio) 
				Util.retonarObjetoDeColecao(colecaoUnidadeNegocio);
			if (unidadeNegocio != null) {
				sessao.setAttribute("nomeUnidadeNegocio",unidadeNegocio.getNome());
			} else {
				sessao.removeAttribute("nomeUnidadeNegocio");
			}
				
		} else if(idUnidadeNegocio.equals("-1")){
			sessao.setAttribute("nomeUnidadeNegocio","TODAS");
		} else {
			sessao.removeAttribute("nomeUnidadeNegocio");
		}
		
		if (idEloPopup != null && !idEloPopup.equals("") && !idEloPopup.equals("-1")){
			FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
			filtroLocalidade.adicionarParametro(new ParametroSimples("id",idEloPopup));
			
			Collection colecaoElo = fachada.pesquisar(filtroLocalidade,
					Localidade.class.getName());
			
			if(colecaoElo != null && !colecaoElo.isEmpty()){
				Localidade elo = (Localidade) colecaoElo.iterator().next();
				sessao.setAttribute("nomeElo",elo.getDescricao());
			} else {
				sessao.removeAttribute("nomeElo");
			}
			
		} else if(idEloPopup.equals("-1")){
			sessao.setAttribute("nomeElo","TODOS");
		} else {
			sessao.removeAttribute("nomeElo");
		}
		
		sessao.setAttribute("referencia", referencia);

		Integer anoMesAnterior = Util.subtrairMesDoAnoMes(new Integer(referencia).intValue(), 1);
		BigDecimal faturamentoCobradoEmConta = fachada.pesquisarFaturamentoCobradoEmConta(anoMesAnterior);
		sessao.setAttribute("faturamentoCobradoEmConta", Util.formatarMoedaReal(faturamentoCobradoEmConta));
		
		if (filtro != null){
			
			filtro = filtro.clone();
			
			filtro.setAgrupamento(GROUP_BY.LOCALIDADE);
			filtro.setIdGerenciaRegional(idGerencia);
			filtro.setIdUnidadeNegocio(idUnidadeNegocio);
			filtro.setIdElo(idEloPopup);
			filtro.setAnoMesArrecadacao(referencia);
			
			Map<Integer, Collection<FiltrarDadosDiariosArrecadacaoHelper>> 
			 mapDadosDiariosAnoMes = fachada.filtrarDadosDiariosArrecadacao(
				periodoArrecadacaoInicial,
				periodoArrecadacaoFinal,
				filtro);
			
	    	BigDecimal valorTotal = new BigDecimal(0.0);
	    	
	    	// variaveis para gerar a linha de TODOS para as localidades
       		BigDecimal totalDebitos = new BigDecimal(0.0);
    		BigDecimal totalDescontos = new BigDecimal(0.0);
    		BigDecimal totalArrecadacao = new BigDecimal(0.0);
    		BigDecimal totalDevolucoes = new BigDecimal(0.0);
    		BigDecimal totalArrecadacaoLiquida = new BigDecimal(0.0);   		    	
	    	
	    	Collection<FiltrarDadosDiariosArrecadacaoHelper> colecaoDadosDiarios = 
	    		mapDadosDiariosAnoMes.get(new Integer(referencia));
	    	
	    	for (FiltrarDadosDiariosArrecadacaoHelper helper : colecaoDadosDiarios){
	    		valorTotal = valorTotal.add(helper.getValorArrecadacaoLiquida());
	    		
        		totalDebitos = totalDebitos.add(helper.getValorDebitos());
        		totalDescontos = totalDescontos.add(helper.getValorDescontos());
        		totalArrecadacao = totalArrecadacao.add(helper.getValorArrecadacao());
        		totalDevolucoes = totalDevolucoes.add(helper.getValorDevolucoes());
        		totalArrecadacaoLiquida = totalArrecadacaoLiquida.add(helper.getValorArrecadacaoLiquida());            		
	    		
	    	}
	    	
	    	// criando helper para ser incluido como um ultimo item da lista 
	    	Localidade localidade = new Localidade();
	    	localidade.setId(-1);
	    	localidade.setDescricao("TODAS");
    		
    		FiltrarDadosDiariosArrecadacaoHelper helperTodos = new FiltrarDadosDiariosArrecadacaoHelper();
    		helperTodos.setItemAgrupado(localidade);
    		helperTodos.setValorDebitos(totalDebitos);
    		helperTodos.setValorDescontos(totalDescontos);
    		helperTodos.setValorArrecadacao(totalArrecadacao);
    		helperTodos.setValorDevolucoes(totalDevolucoes);
    		helperTodos.setValorArrecadacaoLiquida(totalArrecadacaoLiquida);
    		helperTodos.setPercentual(new BigDecimal(100.00));
    		
    		colecaoDadosDiarios.add(helperTodos);	    	

    		sessao.setAttribute("colecaoDadosDiarios", colecaoDadosDiarios);	        
			sessao.setAttribute("valorTotal", valorTotal);	  
			
			 Date dataMesInformado = fachada.pesquisarDataProcessamentoMes(new Integer(referencia));
	    		Date dataAtual = fachada.pesquisarDataProcessamentoMes(this.getSistemaParametro().getAnoMesArrecadacao());

	    		
	    	if(dataMesInformado!=null){ 			
	    		sessao
	    			.setAttribute("dadosMesInformado", 
						Util.formatarDataComHora(dataMesInformado));
	    	} else {
	    		sessao.removeAttribute("dadosMesInformado");
	    	}
	    	if(dataAtual!=null){   			
	    		sessao
					.setAttribute("dadosAtual", 
						Util.formatarDataComHora(dataAtual));
	    	} else {
	    		sessao.removeAttribute("dadosAtual");
	    	}
			
		} else {
    		sessao.removeAttribute("colecaoDadosDiarios");
    		sessao.removeAttribute("valorTotal");
    		sessao.removeAttribute("dadosMesInformado");
    		sessao.removeAttribute("dadosAtual");
		}

//		////////////////////////////////////
//		Collection colecaoArrecadacaoDadosDiariosLocalidade = new ArrayList();
//		
//		String descricao = "LOCALIDADE";
//		
//        Collection colecaoArrecadacaoDadosDiarios = (Collection) sessao
//		.getAttribute("colecaoArrecadacaoDadosDiarios");
//
//		if(idGerencia != null){
//			FiltroGerenciaRegional filtroGerenciaRegional = new FiltroGerenciaRegional();
//
//			filtroGerenciaRegional.adicionarParametro(new ParametroSimples(
//					FiltroArrecadacaoDadosDiarios.ID,
//					idGerencia));
//			
//			Collection colecaoGerenciaRegional = fachada.pesquisar(filtroGerenciaRegional,
//					GerenciaRegional.class.getName());
//			
//			if (colecaoGerenciaRegional != null
//					&& !colecaoGerenciaRegional.isEmpty()){
//				
//				GerenciaRegional dadosGerencia = (GerenciaRegional) ((List) colecaoGerenciaRegional).get(0);
//				
//				if (dadosGerencia.getNome() != null
//						&& !dadosGerencia.getNome().equals("")) {
//					httpServletRequest.setAttribute("nomeGerencia",dadosGerencia.getNome());
//				}
//			}
//		}
//		
//		if(idUnidadeNegocio != null){
//			FiltroUnidadeNegocio filtroUnidadeNegocio = new FiltroUnidadeNegocio();
//
//			filtroUnidadeNegocio.adicionarParametro(new ParametroSimples(
//					FiltroUnidadeNegocio.ID,
//					idUnidadeNegocio));
//			
//			Collection colecaoUnidadeNegocio = fachada.pesquisar(filtroUnidadeNegocio,
//					UnidadeNegocio.class.getName());
//			
//			if (colecaoUnidadeNegocio != null
//					&& !colecaoUnidadeNegocio.isEmpty()){
//				
//				UnidadeNegocio unidadeNegocio = (UnidadeNegocio) ((List) colecaoUnidadeNegocio).get(0);
//				
//				if (unidadeNegocio.getNome() != null
//						&& !unidadeNegocio.getNome().equals("")) {
//					sessao.setAttribute("nomeUnidadeNegocio",unidadeNegocio.getNome());
//					httpServletRequest.setAttribute("nomeUnidadeNegocio",unidadeNegocio.getNome());
//					httpServletRequest.setAttribute("idUnidadeNegocio",unidadeNegocio.getId().toString());
//				}
//			}
//		}
//		
//        ArrecadacaoDadosDiariosAcumuladorHelper acumuladorHelper = new  ArrecadacaoDadosDiariosAcumuladorHelper(
//        		ArrecadacaoDadosDiariosItemAcumuladorHelper.GROUP_BY_LOCALIDADE);
//        
//        colecaoArrecadacaoDadosDiariosLocalidade = acumuladorHelper.aplicarFiltroEAcumularValores(        		
//        		colecaoArrecadacaoDadosDiarios, 
//        		referencia, null, null, idGerencia, idUnidadeNegocio, null, null,
//        		null, null, null, null, false, false, false, false, false);
//        
//        Collections.sort((List) colecaoArrecadacaoDadosDiariosLocalidade,
//				new Comparator() {
//					public int compare(Object a, Object b) {
//						String codigo1 = ((ArrecadacaoDadosDiariosItemAcumuladorHelper) a)
//								.getUnidadeNegocio().getNomeAbreviado();
//						String codigo2 = ((ArrecadacaoDadosDiariosItemAcumuladorHelper) b)
//								.getUnidadeNegocio().getNomeAbreviado();
//						if (codigo1 == null || codigo1.equals("")) {
//							return -1;
//						} else {
//							return codigo1.compareTo(codigo2);
//						}
//					}
//				});
//        
//        valorTotal = acumuladorHelper.getValorLiquidoTotal(); 
//        	
//		if(colecaoArrecadacaoDadosDiariosLocalidade != null)
//		{
//			sessao.setAttribute("colecaoDadosDiarios",colecaoArrecadacaoDadosDiariosLocalidade);
//			sessao.setAttribute("valorTotal",valorTotal);
//			sessao.setAttribute("valorTotalElo",valorTotalElo);
//		}
//		sessao.setAttribute("idGerencia", idGerencia);
//		sessao.setAttribute("referencia", referencia);
//		httpServletRequest.setAttribute("idEloPopup",idEloPopup);
		return retorno;
	}
}
