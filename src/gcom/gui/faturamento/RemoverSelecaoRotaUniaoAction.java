package gcom.gui.faturamento;

import gcom.faturamento.FaturamentoAtivCronRota;
import gcom.gui.GcomAction;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class RemoverSelecaoRotaUniaoAction extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		// Seta o retorno
		ActionForward retorno = actionMapping
				.findForward("atualizarComandoAtividadeFaturamentoDataVencimento");

		// Obt�m a sess�o
		HttpSession sessao = httpServletRequest.getSession(false);

		// Formul�rio de pesquisa
		InserirComandoAtividadeFaturamentoActionForm inserirComandoAtividadeFaturamentoActionForm = (InserirComandoAtividadeFaturamentoActionForm) actionForm;

		if (sessao
				.getAttribute("colecaoFaturamentoAtividadeCronogramaRotaUniao") != null) {

			// Cole��o retornada pela pesquisa
			Collection colecaoFaturamentoAtividadeCronogramaRotaUniao = (Collection) sessao
					.getAttribute("colecaoFaturamentoAtividadeCronogramaRotaUniao");

			// Cole��o que ir� ser gerada a partir da sele��o efetuada pelo
			// usu�rio
			// Collection colecaoRotasSelecionadasUsuario = new Vector();

			if (inserirComandoAtividadeFaturamentoActionForm.getIdRotaSelecao() != null) {

				Iterator colecaoFaturamentoAtividadeCronogramaRotaUniaoIterator;

				FaturamentoAtivCronRota faturamentoAtivCronRota;

				int indexArray = 0;
				Integer rotaID;

				// Rotas selecionadas pelo usu�rio para serem removidas da
				// sess�o
				String[] rotasSelecionadas = inserirComandoAtividadeFaturamentoActionForm
						.getIdRotaSelecao();

				while (rotasSelecionadas.length > indexArray) {
					rotaID = new Integer(rotasSelecionadas[indexArray]);

					colecaoFaturamentoAtividadeCronogramaRotaUniaoIterator = colecaoFaturamentoAtividadeCronogramaRotaUniao
							.iterator();

					while (colecaoFaturamentoAtividadeCronogramaRotaUniaoIterator
							.hasNext()) {

						faturamentoAtivCronRota = (FaturamentoAtivCronRota) colecaoFaturamentoAtividadeCronogramaRotaUniaoIterator
								.next();

						if (faturamentoAtivCronRota.getRota().getId().equals(
								rotaID)) {
							colecaoFaturamentoAtividadeCronogramaRotaUniao
									.remove(faturamentoAtivCronRota);
							break;
						}
					}

					indexArray++;
				}
				
				//Ser� utilizado para verificar se houve alguma remo��o na cole��o
				httpServletRequest.setAttribute("remocaoRealizada", "OK");
			}
		}

		// devolve o mapeamento de retorno
		return retorno;
	}

}
