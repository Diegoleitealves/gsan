package gcom.gui.micromedicao;

import gcom.fachada.Fachada;
import gcom.faturamento.FaturamentoGrupo;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.medicao.FiltroMedicaoHistoricoSql;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Descri��o da classe
 * 
 * @author Administrador
 * @date 07/03/2006
 * 
 */
public class ExibirManterAnaliseExcecoesConsumosAction extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		ActionForward retorno = actionMapping.findForward("exibirManterAnaliseExcecoesConsumo");
		
		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();

		/*
		 * Colocado por Raphael Rossiter em 04/12/2007 - Analista: Claudio Lira
		 * OBJ: N�o perder os registros selecionados na pagina��o
		 */
		//========================================================================================
		this.capturarSelecao(sessao, httpServletRequest);
		
		this.capturarImoveisJaSelecionados(sessao, httpServletRequest);
		
		this.redirecionarAnalise(httpServletRequest);
		//========================================================================================
		
				
		FiltroMedicaoHistoricoSql filtroMedicaoHistoricoSql = new FiltroMedicaoHistoricoSql();

		if (sessao.getAttribute("filtroMedicaoHistoricoSql") != null) {
			filtroMedicaoHistoricoSql = (FiltroMedicaoHistoricoSql) sessao
					.getAttribute("filtroMedicaoHistoricoSql");
			
			FaturamentoGrupo faturamentoGrupo = (FaturamentoGrupo)sessao.getAttribute("faturamentoGrupo");
			
			// 1� Passo - Pegar o total de registros atrav�s de um count da
			// consulta que aparecer� na tela
			Integer totalRegistros = fachada
				.filtrarExcecoesLeiturasConsumosCount(faturamentoGrupo, filtroMedicaoHistoricoSql, (String)sessao.getAttribute("mesAnoPesquisa"),(String)sessao.getAttribute("valorAguaEsgotoInicial"), (String)sessao.getAttribute("valorAguaEsgotoFinal"));

			// 2� Passo - Chamar a fun��o de Pagina��o passando o total de
			// registros
			retorno = this.controlarPaginacao(httpServletRequest, retorno,
					totalRegistros);

			String tipoApresentacao = (String)sessao.getAttribute("tipoApresentacao");
			// 3� Passo - Obter a cole��o da consulta que aparecer� na tela
			// passando o numero de paginas
			// da pesquisa que est� no request
			Collection colecaoImovelMedicao = null;
			colecaoImovelMedicao = fachada
						.filtrarExcecoesLeiturasConsumos(faturamentoGrupo, filtroMedicaoHistoricoSql,
								(Integer) httpServletRequest
										.getAttribute("numeroPaginasPesquisa"), false, 
										(String)sessao.getAttribute("mesAnoPesquisa"),
										(String)sessao.getAttribute("valorAguaEsgotoInicial"), 
										(String)sessao.getAttribute("valorAguaEsgotoFinal"));


			if (colecaoImovelMedicao == null || colecaoImovelMedicao.isEmpty()) {
				// Nenhum cliente cadastrado
				throw new ActionServletException(
						"atencao.pesquisa.nenhumresultado");
			}
			
			sessao.setAttribute("colecaoImovelMedicao", colecaoImovelMedicao);
			sessao.setAttribute("totalRegistros", totalRegistros);
			sessao.setAttribute("numeroPaginasPesquisa",httpServletRequest
					.getAttribute("numeroPaginasPesquisa"));
			
			// se or igual a normal coloca um indicador na sessao para resolver no jsp
			if(tipoApresentacao.trim().equalsIgnoreCase("1")){
				sessao.setAttribute("indicadorTipoApresentacao", true);
			}else{
				sessao.removeAttribute("indicadorTipoApresentacao");
			}

			// Muitos registros encontrados
			// throw new ActionServletException(
			// "atencao.pesquisa.muitosregistros", null);

		}

		return retorno;
	}
	
	/**
	 * Capturar os im�veis selecionados pelo usu�rio
	 * 
	 * @author Raphael Rossiter
	 * @date 05/12/2007
	 * 
	 */
	public void capturarSelecao(HttpSession sessao, HttpServletRequest httpServletRequest){
		
		//CASO VENHA DA TELA DE ANALISE
		String telaAnalise = httpServletRequest.getParameter("concluir");
		
		if (telaAnalise == null){
		
			String paginaCorrente = httpServletRequest.getParameter("paginaCorrente");
			
			String idsImoveisJuntos = httpServletRequest.getParameter("idRegistrosImovel");
			String[] idsImovel = null;
			
			if (idsImoveisJuntos != null && idsImoveisJuntos.length() > 0){
				idsImovel = idsImoveisJuntos.split(",");
			}
			
			HashMap<String, String[]> imoveisPorPagina = (HashMap) sessao.getAttribute("idsImoveisJaSelecionados");
				
			if (imoveisPorPagina != null && !imoveisPorPagina.isEmpty()){
				
				if (imoveisPorPagina.containsKey(paginaCorrente)){
					
					if (idsImovel != null && idsImovel.length > 0){
						
						//ATUALIZA��O
						imoveisPorPagina.put(paginaCorrente, idsImovel);
					}
					else{
						
						//REMO��O
						imoveisPorPagina.remove(paginaCorrente);
					}
				}
				else if (idsImovel != null && idsImovel.length > 0){
					//PAGINA NAO CADASTRADA 
					imoveisPorPagina.put(paginaCorrente, idsImovel);
				}
				
			}
			else if (idsImovel != null && idsImovel.length > 0){
				
				//PRIMEIRA SELECAO
				imoveisPorPagina = new HashMap<String, String[]>();
				imoveisPorPagina.put(paginaCorrente, idsImovel);
					
				sessao.setAttribute("idsImoveisJaSelecionados", imoveisPorPagina);
			}
		}
		
	}
	
	/**
	 * Capturar os im�veis selecionados pelo usu�rio
	 * 
	 * @author Raphael Rossiter
	 * @date 05/12/2007
	 * 
	 */
	public void capturarImoveisJaSelecionados(HttpSession sessao, HttpServletRequest httpServletRequest){
		
		String proximaPagina = httpServletRequest.getParameter("page.offset");
		
		if (proximaPagina == null){
			proximaPagina = "1";
		}
			
		HashMap<String, String[]> imoveisPorPagina = (HashMap) sessao.getAttribute("idsImoveisJaSelecionados");
			
		if (imoveisPorPagina != null && !imoveisPorPagina.isEmpty() &&
			imoveisPorPagina.containsKey(proximaPagina)){
				
			String[] idsImovel = imoveisPorPagina.get(proximaPagina);
			Collection<String> selecionados = new ArrayList();
				
			for (int i = 0; i < idsImovel.length; i++) {
				selecionados.add(idsImovel[i]);
			}
				
			httpServletRequest.setAttribute("selecionados", selecionados);
		
		}
	}

	
	/**
	 * Redirecionar para tela de an�lise
	 * 
	 * @author Raphael Rossiter
	 * @date 05/12/2007
	 * 
	 */
	public void redirecionarAnalise(HttpServletRequest httpServletRequest){
		
		String idRegistroAtualizacao = httpServletRequest.getParameter("idRegistroAtualizacao");
		
		if (idRegistroAtualizacao != null){
			
			String linkAnalise = httpServletRequest.getParameter("linkAnalise");
			String medicaoTipo = httpServletRequest.getParameter("medicaoTipo");
			
			linkAnalise = linkAnalise + "?idRegistroAtualizacao=" + idRegistroAtualizacao;
			linkAnalise = linkAnalise + "&medicaoTipo=" + medicaoTipo;
			
			httpServletRequest.setAttribute("telaAnalise", linkAnalise);
		}
	}
}
