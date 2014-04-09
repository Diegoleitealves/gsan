package gcom.gui.cobranca;

import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action para pre-exibi��o dos dados da situa��o especial de faturamento que
 * ser�o retirados
 * 
 * @author S�vio Luiz
 * @date 10/03/2006
 */
public class ExibirSituacaoEspecialCobrancaRetirarAction extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		// Seta o retorno
		ActionForward retorno = actionMapping
				.findForward("situacaoEspecialRetirar");

		SituacaoEspecialCobrancaActionForm situacaoEspecialCobrancaActionForm = (SituacaoEspecialCobrancaActionForm) actionForm;

		// Pegar Quantidade de Imoveis que serao atualizados
		String COM = situacaoEspecialCobrancaActionForm
				.getQuantidadeImoveisCOMSituacaoEspecialCobranca();
		String SEM = situacaoEspecialCobrancaActionForm
				.getQuantidadeImoveisSEMSituacaoEspecialCobranca();
		String quantidadeDeImoveisAtualizados = Integer.toString(Integer
				.parseInt(COM)
				+ Integer.parseInt(SEM));

		if (quantidadeDeImoveisAtualizados.equals("0"))
			throw new ActionServletException(
					"atencao.imovel.sem.situacao.especial.faturamento", null,
					"");

		situacaoEspecialCobrancaActionForm
				.setQuantidadeDeImoveis(quantidadeDeImoveisAtualizados);

		return retorno;
	}

}
