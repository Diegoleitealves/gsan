package gcom.gui.faturamento;

import javax.servlet.http.*;

import org.apache.struts.action.*;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.Util;

public class AtualizarVencimentoFaturaClienteResponsavelAction extends GcomAction {
	
	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		ActionForward retorno = actionMapping.findForward("telaSucesso");
		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);

		AtualizarVencimentoFaturaClienteResponsavelActionForm form = (AtualizarVencimentoFaturaClienteResponsavelActionForm) actionForm;
		String dataVencimento = form.getDataVencimento();
		String anoMesReferencia = form.getAnoMesReferencia();
		
		if(dataVencimento == null) {
			throw new ActionServletException("atencao.informe_campo_obrigatorio", null,
					"Data vencimento");
		}
		
		if(anoMesReferencia == null) {
			throw new ActionServletException("atencao.informe_campo_obrigatorio", null,
					"M�s/Ano de Refer�ncia");
		}
		
		fachada.atualizarVecimentoFaturaClienteResponsavel(Util.converteStringParaDate(dataVencimento), 
				Util.formatarMesAnoParaAnoMesSemBarra(anoMesReferencia));

		montarPaginaSucesso(httpServletRequest, "Atualiza��o do vencimento de faturas realizada com sucesso",
				"Voltar", "/exibirAtualizarVencimentoFaturaClienteResponsavel.do");


		return retorno;
	}
}
