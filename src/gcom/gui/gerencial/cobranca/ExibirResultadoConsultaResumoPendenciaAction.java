package gcom.gui.gerencial.cobranca;

import gcom.cobranca.ResumoPendencia;
import gcom.fachada.Fachada;
import gcom.gerencial.bean.InformarDadosGeracaoRelatorioConsultaHelper;
import gcom.gui.GcomAction;
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
 * @author Roberta Costa
 * @date 26/05/2006
 */
public class ExibirResultadoConsultaResumoPendenciaAction extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		ActionForward retorno = actionMapping.findForward("resultadoResumoPendencia");

		Fachada fachada = Fachada.getInstancia();

		// Mudar isso quando implementar a parte de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		List colecaoResumoPendendica = (List) sessao
				.getAttribute("consultarResumoPendendica");
		
		InformarDadosGeracaoRelatorioConsultaHelper informarDadosGeracaoRelatorioConsultaHelper = 
        	(InformarDadosGeracaoRelatorioConsultaHelper)sessao.getAttribute("informarDadosGeracaoRelatorioConsultaHelper");
		
		List colecaoResumoPendenciaAgua = new ArrayList();
		List colecaoResumoPendenciaEsgoto = new ArrayList();

		Iterator iterator = colecaoResumoPendendica.iterator();
		
		ResumoPendencia resumoPendencia = null;
		
		/**
		 * Divide o resultado da cole��o em dois subresultados
		 * um por liga��o agua e outro por liga��o esgoto
		 */
		while(iterator.hasNext()){
			resumoPendencia = (ResumoPendencia)iterator.next();
			colecaoResumoPendenciaAgua.add(resumoPendencia);
			colecaoResumoPendenciaEsgoto.add(resumoPendencia);
		}
		
		/**
		 * Cria cole��o de agrupamntos(uma cole��o de object[3], agrupamento, id, descricao)
		 */
		Collection colecaoAgrupamento = fachada.criarColecaoAgrupamentoResumos(informarDadosGeracaoRelatorioConsultaHelper);
		
		sessao.setAttribute("colecaoAgrupamento", colecaoAgrupamento);
		sessao.setAttribute("mesAnoReferencia", Util.formatarAnoMesParaMesAno(informarDadosGeracaoRelatorioConsultaHelper.getAnoMesReferencia()));
		httpServletRequest.setAttribute("colecaoResumoPendenciaAgua", colecaoResumoPendenciaAgua);
		httpServletRequest.setAttribute("colecaoResumoPendenciaEsgoto", colecaoResumoPendenciaEsgoto);
		
		return retorno;
	}

}
