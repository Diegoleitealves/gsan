package gcom.gui.faturamento.conta;

import gcom.atendimentopublico.ligacaoagua.FiltroLigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.FiltroLigacaoEsgotoPerfil;
import gcom.atendimentopublico.ligacaoesgoto.FiltroLigacaoEsgotoSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoPerfil;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.Subcategoria;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.faturamento.FaturamentoGrupo;
import gcom.faturamento.FiltroFaturamentoGrupo;
import gcom.faturamento.consumotarifa.ConsumoTarifa;
import gcom.faturamento.consumotarifa.FiltroConsumoTarifa;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroNaoNulo;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExibirSimularCalculoContaAction extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		// Define a a��o de retorno
		ActionForward retorno = actionMapping
				.findForward("exibirSimularCalculoConta");
		
		SimularCalculoContaActionForm form = (SimularCalculoContaActionForm) actionForm;
		
		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);
		
		/*
         * Costante que informa o ano limite para o campo anoMesReferencia da conta
         */
        httpServletRequest.setAttribute("anoLimite", ConstantesSistema.ANO_LIMITE);
		
		String limparForm = httpServletRequest.getParameter("limparForm");
		
		 //Removendo cole��es da sess�o
        if (limparForm != null && !limparForm.equalsIgnoreCase("")){
        	sessao.removeAttribute("colecaoLigacaoAguaSituacao");
        	sessao.removeAttribute("colecaoLigacaoEsgotoSituacao");
        	sessao.removeAttribute("colecaoConsumoTarifa");
        	sessao.removeAttribute("colecaoFaturamentoGrupo");
        	sessao.removeAttribute("colecaoCategoria");
        }
		
		Fachada fachada = Fachada.getInstancia();

		
		// Verifica se existe a cole��o de liga��o de �gua
		if (sessao.getAttribute("colecaoLigacaoAguaSituacao") == null) {

			// Cria��o do filtro para liga��o �gua situa��o
			FiltroLigacaoAguaSituacao filtroLigacaoAguaSituacao = new FiltroLigacaoAguaSituacao(
					FiltroLigacaoAguaSituacao.DESCRICAO);

			filtroLigacaoAguaSituacao.adicionarParametro(new ParametroSimples(
					FiltroLigacaoAguaSituacao.INDICADOR_USO,
					ConstantesSistema.INDICADOR_USO_ATIVO));

			// Pesquisa liga��o �gua situa��o
			Collection colecaoLigacaoAguaSituacao = fachada.pesquisar(
					filtroLigacaoAguaSituacao, LigacaoAguaSituacao.class
							.getName());

			sessao.setAttribute("colecaoLigacaoAguaSituacao",
					colecaoLigacaoAguaSituacao);

		}

		if (sessao.getAttribute("colecaoLigacaoEsgotoSituacao")  == null) {

			// Cria��o do filtro para liga��o esgoto situa��o
			FiltroLigacaoEsgotoSituacao filtroLigacaoEsgotoSituacao = new FiltroLigacaoEsgotoSituacao(
					FiltroLigacaoEsgotoSituacao.DESCRICAO);

			filtroLigacaoEsgotoSituacao
					.adicionarParametro(new ParametroSimples(
							FiltroLigacaoEsgotoSituacao.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			// Pesquisa liga��o esgoto situa��o
			Collection colecaoLigacaoEsgotoSituacao = fachada.pesquisar(
					filtroLigacaoEsgotoSituacao, LigacaoEsgotoSituacao.class
							.getName());
			
			sessao.setAttribute("colecaoLigacaoEsgotoSituacao",
					colecaoLigacaoEsgotoSituacao);
			

		}
		if (sessao.getAttribute("colecaoConsumoTarifa")  == null) {

			// Cria��o do filtro para consumo tarifa
			FiltroConsumoTarifa filtroConsumoTarifa = new FiltroConsumoTarifa(
					FiltroConsumoTarifa.DESCRICAO);
			filtroConsumoTarifa.adicionarParametro(new ParametroSimples(
					FiltroConsumoTarifa.INDICADOR_USO,
					ConstantesSistema.INDICADOR_USO_ATIVO));

			// Pesquisa consumo tarifa
			Collection colecaoConsumoTarifa = fachada.pesquisar(filtroConsumoTarifa,
					ConsumoTarifa.class.getName());
			
			sessao.setAttribute("colecaoConsumoTarifa", colecaoConsumoTarifa);
		}

		if (sessao.getAttribute("colecaoFaturamentoGrupo") == null) {
			// Cria��o do filtro para faturamento grupo
			FiltroFaturamentoGrupo filtroFaturamentoGrupo = new FiltroFaturamentoGrupo(
					FiltroFaturamentoGrupo.DESCRICAO);
			filtroFaturamentoGrupo.adicionarParametro(new ParametroSimples(
					FiltroFaturamentoGrupo.INDICADOR_USO,
					ConstantesSistema.INDICADOR_USO_ATIVO));

			Collection colecaoFaturamentoGrupo = fachada.pesquisar(filtroFaturamentoGrupo,
					FaturamentoGrupo.class.getName());
			
			sessao.setAttribute("colecaoFaturamentoGrupo", colecaoFaturamentoGrupo);
		}
		
		Map<String, String[]> requestMap = httpServletRequest.getParameterMap();
		if(sessao.getAttribute("colecaoCategoria") != null)
        {
        	Collection colecao = (Collection) sessao.getAttribute("colecaoCategoria");
        	Iterator iteratorColecaoCategoria = colecao.iterator();
        	
        	Categoria categoria = null;
        	String quantidadeEconomia = null;
        	Integer qtdEconnomia = 0;
        	while (iteratorColecaoCategoria.hasNext()) {
    			categoria = (Categoria) iteratorColecaoCategoria.next();
    			// valor minimo
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
    				qtdEconnomia = Util.somaInteiros(qtdEconnomia,new Integer(quantidadeEconomia));
    			}
        	}
        	if(!sessao.getAttribute("retorno").equals("sim")){
        		sessao.setAttribute("totalEconomia",qtdEconnomia);
        	}
        }


		// DEFINI��O QUE IR� AUXILIAR O RETORNO DOS POPUPS
		sessao.setAttribute("UseCase", "SIMULARCALCULOCONTA");
		
		if(sessao.getAttribute("retorno") == null){
			sessao.setAttribute("existeColecao","nao");
			sessao.removeAttribute("colecao");
		}else if (sessao.getAttribute("colecao") == null){
			sessao.removeAttribute("existeColecao");
		}else if(sessao.getAttribute("colecao") != null && sessao.getAttribute("adicionar") == null){
			sessao.setAttribute("existeColecao","nao");
		}else if(sessao.getAttribute("adicionar") != null){
			sessao.removeAttribute("existeColecao");
		}
		
		
		
		/*
		 * Colocado por Raphael Rossiter em 14/03/2007
		 * Objetivo: Manipula��o dos objetos que ser�o exibidos no formul�rio de acordo com a empresa
		 */
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		//httpServletRequest.setAttribute("empresaNome", sistemaParametro.getNomeAbreviadoEmpresa().trim());
		
		httpServletRequest.setAttribute("indicadorTarifaCategoria", sistemaParametro.getIndicadorTarifaCategoria().toString());
		
		/*
		 * Colocado por Raphael Rossiter em 02/09/2008
		 * Objetivo: Obtendo o percentual default de esgoto
		 */
		String percentualDefault = Util.formatarMoedaReal(ConstantesSistema.CEM);
		
		FiltroLigacaoEsgotoPerfil filtroLigacaoEsgotoPerfil = new FiltroLigacaoEsgotoPerfil();
		
		filtroLigacaoEsgotoPerfil.adicionarParametro(new ParametroSimples(
		FiltroLigacaoEsgotoPerfil.INDICADOR_PRINCIPAL, ConstantesSistema.SIM));
		
		filtroLigacaoEsgotoPerfil.adicionarParametro(new ParametroNaoNulo(
		FiltroLigacaoEsgotoPerfil.PERCENTUAL));
		
		Collection colecaoLigacaoEsgotoPerfil = fachada.pesquisar(filtroLigacaoEsgotoPerfil,
		LigacaoEsgotoPerfil.class.getName());
		
		if (colecaoLigacaoEsgotoPerfil != null && !colecaoLigacaoEsgotoPerfil.isEmpty()){
			
			LigacaoEsgotoPerfil ligacaoEsgotoPerfil = (LigacaoEsgotoPerfil) 
			Util.retonarObjetoDeColecao(colecaoLigacaoEsgotoPerfil);
			
			percentualDefault = Util.formatarMoedaReal(ligacaoEsgotoPerfil
			.getPercentualEsgotoConsumidaColetada());
		}
		
		httpServletRequest.setAttribute("percentualEsgotoDefault", percentualDefault);
		
		String areaInformada = form.getArea();
		String mesAnoInformado = form.getMesAnoReferencia();
		String pontosUtilizacaoInformado = form.getPontosUtilizacao();
		String numeroMoradoresInformado = form.getNumeroMoradores(); 
		
		/*
		 * [SB0001] - Determinar consumo m�nimo por �rea
		 */
		if (mesAnoInformado != null && !mesAnoInformado.trim().equals("")
				&& (areaInformada != null && !areaInformada.trim().equals("") 
				|| pontosUtilizacaoInformado != null && !pontosUtilizacaoInformado.trim().equals("")
				|| numeroMoradoresInformado != null && !numeroMoradoresInformado.trim().equals(""))) {
		
			Integer anoMes = Util.formatarMesAnoComBarraParaAnoMes(mesAnoInformado);
			
			BigDecimal areaTotal = null;
			if (areaInformada != null && !areaInformada.trim().equals("")) {
				areaTotal = Util.formatarMoedaRealparaBigDecimal(areaInformada);
			}

			BigDecimal pontosUtilizacao = null;
			if (pontosUtilizacaoInformado != null && !pontosUtilizacaoInformado.trim().equals("")) {
				pontosUtilizacao = Util.formatarMoedaRealparaBigDecimal(pontosUtilizacaoInformado);
			}
			
			BigDecimal numeroMoradores = null;
			if (numeroMoradoresInformado != null && !numeroMoradoresInformado.trim().equals("")) {
				numeroMoradores = Util.formatarMoedaRealparaBigDecimal(numeroMoradoresInformado);
			}
			
			Collection<Categoria> colecaoCategoria = (Collection<Categoria>) sessao.getAttribute("colecaoCategoria");
			Collection<Subcategoria> colecaoSubcategoria = (Collection<Subcategoria>) sessao.getAttribute("colecaoSubcategoria");
			
			Integer consumoMinimo = fachada.calcularConsumoMinimo(areaTotal, anoMes, colecaoCategoria, colecaoSubcategoria,
					pontosUtilizacao, numeroMoradores);
			
			if (consumoMinimo != null) {
				form.setConsumoFaturadoAgua(consumoMinimo.toString());
				form.setConsumoFaturadoEsgoto(consumoMinimo.toString());
			} else {
				form.setConsumoFaturadoAgua("");
				form.setConsumoFaturadoEsgoto("");
			}
			
		}
		

		return retorno;

	}
	
}
