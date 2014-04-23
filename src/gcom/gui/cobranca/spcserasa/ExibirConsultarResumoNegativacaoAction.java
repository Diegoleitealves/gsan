package gcom.gui.cobranca.spcserasa;

import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.Util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Description of the Class
 * 
 * @author Marcio Roberto
 * @Date 26/02/2008
**/
public class ExibirConsultarResumoNegativacaoAction extends GcomAction {
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
				.findForward("exibirConsultarResumoNegativacao");
		
		//obt�m a inst�ncia da sess�o
		HttpSession sessao = httpServletRequest.getSession(false);
		
		Collection <NegativacaoDescricaoResumoHelper> colecaoDescricaoResumo = new ArrayList<NegativacaoDescricaoResumoHelper>();
		
		InformarDadosConsultaNegativacaoActionForm form = (InformarDadosConsultaNegativacaoActionForm) actionForm;
		
		String periodoEnvioNegativacaoInicio = (String)sessao.getAttribute("periodoEnvioNegativacaoInicio"); 
		String periodoEnvioNegativacaoFim = (String)sessao.getAttribute("periodoEnvioNegativacaoFim");
		
		form.setPeriodoEnvioNegativacaoInicio(periodoEnvioNegativacaoInicio);
		form.setPeriodoEnvioNegativacaoFim(periodoEnvioNegativacaoFim);
		
		
		// [UC0676] Gerar Resumo Di�rio da Negativa��o.
        //-------------------------------------------------------------------------------------------
		// Alterado por :  Yara Taciane  - data : 28/07/2008 
		// Analista :  F�tima Sampaio
        //-------------------------------------------------------------------------------------------			
		
		Fachada fachada = Fachada.getInstancia();
		// Pega as informa��es de Sistema Par�metros
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema(); 
		
		Date ultimaAtualizacao = fachada.getDataUltimaAtualizacaoResumoNegativacao(sistemaParametro.getNumeroExecucaoResumoNegativacao());
		form.setUltimaAtualizacao(Util.formatarDataComHora(ultimaAtualizacao));
		//-------------------------------------------------------------------------------------------
		
		NegativacaoDescricaoResumoHelper descricaoResumo = new NegativacaoDescricaoResumoHelper();
		
		descricaoResumo.setDescricao("NEGATIVA��ES INCLU�DAS");
		colecaoDescricaoResumo.add(descricaoResumo);

		descricaoResumo = new NegativacaoDescricaoResumoHelper();
		descricaoResumo.setDescricao("NEGATIVA��ES INCLU�DAS E CONFIRMADAS");
		colecaoDescricaoResumo.add(descricaoResumo);

		descricaoResumo = new NegativacaoDescricaoResumoHelper();
		descricaoResumo.setDescricao("NEGATIVA��ES INCLU�DAS E N�O CONFIRMADAS");
		colecaoDescricaoResumo.add(descricaoResumo);

		sessao.setAttribute("colecaoDescricaoResumo", colecaoDescricaoResumo);

		return retorno;
	}
}
