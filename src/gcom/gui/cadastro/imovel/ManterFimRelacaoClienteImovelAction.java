package gcom.gui.cadastro.imovel;

import gcom.cadastro.cliente.ClienteImovelEconomia;
import gcom.cadastro.cliente.ClienteImovelFimRelacaoMotivo;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Description of the Class
 * 
 * @author compesa
 * @created 15 de Agosto de 2005
 */
public class ManterFimRelacaoClienteImovelAction extends GcomAction {
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

		// Prepara o retorno da A��o
		ActionForward retorno = actionMapping
				.findForward("exibirAtualizarEconomiaPopup");
		// Cria a sess�o
		HttpSession sessao = httpServletRequest.getSession(false);

		FimRelacaoClienteImovelActionForm fimRelacaoClienteImovelActionForm = (FimRelacaoClienteImovelActionForm) actionForm;

		// Obt�m a fachada
		//Fachada fachada = Fachada.getInstancia();

		Collection colecaoClientesImoveisEconomiaFimRelacao = (Collection) sessao
				.getAttribute("colecaoClientesImoveisEconomiaFimRelacao");
		// verifica se tem algum cliente imovel economia que precisa ser
		// atualizado com a data de termino da
		// rela��o e o motivo.
		if (colecaoClientesImoveisEconomiaFimRelacao != null
				&& !colecaoClientesImoveisEconomiaFimRelacao.isEmpty()) {
			Iterator clienteImovelEconomiaIterator = colecaoClientesImoveisEconomiaFimRelacao
					.iterator();
			String idMotivoFimRelacao = fimRelacaoClienteImovelActionForm
					.getIdMotivo();

			ClienteImovelFimRelacaoMotivo clienteImovelFimRelacao = new ClienteImovelFimRelacaoMotivo();
			// seta o id do motivo do fim da rela��o
			clienteImovelFimRelacao.setId(new Integer(idMotivoFimRelacao));

			String dataFimRelacaoForm = fimRelacaoClienteImovelActionForm
					.getDataTerminoRelacao();

			SimpleDateFormat dataFormato = new SimpleDateFormat("dd/MM/yyyy");

			Date dataFimRelacao = null;
			try {
				dataFimRelacao = dataFormato.parse(dataFimRelacaoForm);
			} catch (ParseException ex) {
				dataFimRelacao = null;
			}

			Date dataCorrente = null;
			Calendar a = Calendar.getInstance();
			a.set(Calendar.SECOND, 0);
			a.set(Calendar.MILLISECOND, 0);
			a.set(Calendar.HOUR, 0);
			a.set(Calendar.MINUTE, 0);
			dataCorrente = a.getTime();

			if (dataFimRelacao.after(dataCorrente)) {
				throw new ActionServletException(
						"atencao.data_fim_relacao_cliente_imovel");
			}

			while (clienteImovelEconomiaIterator.hasNext()) {
				ClienteImovelEconomia clienteImovelEconomia = (ClienteImovelEconomia) clienteImovelEconomiaIterator
						.next();
				if (dataFimRelacao.before(clienteImovelEconomia
						.getDataInicioRelacao())) {
					throw new ActionServletException(
							"atencao.data_fim_relacao_cliente_imovel_menor_inicial");

				}

			}

			// caso a data n�o seja menor que a atual ent�o
			// seta a data final no cliente imovel
			clienteImovelEconomiaIterator = colecaoClientesImoveisEconomiaFimRelacao
					.iterator();

			while (clienteImovelEconomiaIterator.hasNext()) {
				ClienteImovelEconomia clienteImovelEconomia = (ClienteImovelEconomia) clienteImovelEconomiaIterator
						.next();
				clienteImovelEconomia
						.setClienteImovelFimRelacaoMotivo(clienteImovelFimRelacao);
				clienteImovelEconomia.setDataFimRelacao(dataFimRelacao);

			}

		}

		return retorno;
	}
}
