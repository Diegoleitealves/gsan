package gcom.gui.gerencial.faturamento;

import gcom.fachada.Fachada;
import gcom.gerencial.bean.InformarDadosGeracaoRelatorioConsultaHelper;
import gcom.gui.GcomAction;
import gcom.micromedicao.ResumoAnormalidadeLeitura;
import gcom.micromedicao.medicao.MedicaoTipo;
import gcom.util.Util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Descri��o da classe
 * 
 * @author Fernanda Paiva
 * @date 30/05/2006
 * 
 */
public class ExibirResultadoConsultaResumoAnaliseFaturamentoAction extends GcomAction {

	/**
	 * <Breve descri��o sobre o caso de uso>
	 * 
	 * <Identificador e nome do caso de uso>
	 * 
	 * <Breve descri��o sobre o subfluxo>
	 * 
	 * <Identificador e nome do subfluxo>
	 * 
	 * <Breve descri��o sobre o fluxo secund�rio>
	 * 
	 * <Identificador e nome do fluxo secund�rio>
	 * 
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		ActionForward retorno = actionMapping.findForward("resultadoResumoAnaliseFaturamento");

		Fachada fachada = Fachada.getInstancia();

		// Mudar isso quando implementar a parte de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		List colecaoResumoAnormalidade = (List) sessao
				.getAttribute("colecaoResumoAnaliseFaturamento");
		
		InformarDadosGeracaoRelatorioConsultaHelper informarDadosGeracaoRelatorioConsultaHelper = 
        	(InformarDadosGeracaoRelatorioConsultaHelper)sessao.getAttribute("informarDadosGeracaoRelatorioConsultaHelper");
		
		List colecaoResumoAnormalidadeAgua = new ArrayList();
		List colecaoResumoAnormalidadeEsgoto = new ArrayList();

		Iterator iterator = colecaoResumoAnormalidade.iterator();
		
		ResumoAnormalidadeLeitura resumoAnormalidadeLeitura = null;
		int somatorioAnormalidadeAgua = 0;
		int somatorioAnormalidadeEsgoto = 0;
		/**
		 * Divide o resultado da cole��o em dois subresultados
		 * um por liga��o agua e outro por liga��o esgoto
		 */
		while(iterator.hasNext()){
			resumoAnormalidadeLeitura = (ResumoAnormalidadeLeitura)iterator.next();
			if(resumoAnormalidadeLeitura.getMedicaoTipo().getId().equals(MedicaoTipo.LIGACAO_AGUA)){
				/**
				 * guarda o somatorio total das quantidades de medicao de ligacao agua 
				 */
				somatorioAnormalidadeAgua += resumoAnormalidadeLeitura.getQuantidadeMedicao().intValue();
				colecaoResumoAnormalidadeAgua.add(resumoAnormalidadeLeitura);
			}else{
				/**
				 * guarda o somatorio total das quantidades de medicao de ligacao esgoto 
				 */
				somatorioAnormalidadeEsgoto += resumoAnormalidadeLeitura.getQuantidadeMedicao().intValue();
				colecaoResumoAnormalidadeEsgoto.add(resumoAnormalidadeLeitura);
			}
		}
		
		/**
		 * Cria cole��o de agrupamntos(uma cole��o de object[3], agrupamento, id, descricao)
		 */
		Collection colecaoAgrupamento = fachada.criarColecaoAgrupamentoResumos(informarDadosGeracaoRelatorioConsultaHelper);
		httpServletRequest.setAttribute("somatorioAnormalidadeAgua",somatorioAnormalidadeAgua+"");
		sessao.setAttribute("colecaoAgrupamento", colecaoAgrupamento);
		sessao.setAttribute("mesAnoReferencia", Util.formatarAnoMesParaMesAno(informarDadosGeracaoRelatorioConsultaHelper.getAnoMesReferencia()));
		httpServletRequest.setAttribute("colecaoResumoAnormalidadeAgua", colecaoResumoAnormalidadeAgua);
		httpServletRequest.setAttribute("colecaoResumoAnormalidadeEsgoto", colecaoResumoAnormalidadeEsgoto);
		
		return retorno;
	}

}
