package gcom.gui.faturamento;

import gcom.arrecadacao.pagamento.GuiaPagamento;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.ImovelCobrancaSituacao;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ManterGuiaPagamentoAction extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Obt�m a inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		// Obt�m a sess�o
		HttpSession sessao = httpServletRequest.getSession(false);

		Collection guiasPagamento = (Collection) sessao
				.getAttribute("guiasPagamentos");

		ManterGuiaPagamentoActionForm manterGuiaPagamentoActionForm = (ManterGuiaPagamentoActionForm) actionForm;

		String[] registrosRemocao = manterGuiaPagamentoActionForm
				.getIdRegistrosRemocao();
		String idImovel = manterGuiaPagamentoActionForm.getIdImovel();
		String idCliente = manterGuiaPagamentoActionForm.getCodigoCliente();

		GuiaPagamento guiaPagamento = new GuiaPagamento();

		Cliente cliente = new Cliente();

		if (idCliente != null && !idCliente.equals("")) {
			cliente.setId(new Integer(idCliente));

		}

		guiaPagamento.setCliente(cliente);

		Imovel imovel = new Imovel();

		ImovelCobrancaSituacao imovelCobrancaSituacao = null;

		if (idImovel != null && !idImovel.equals("")) {
			imovel.setId(new Integer(idImovel));
			imovelCobrancaSituacao = (ImovelCobrancaSituacao) sessao
					.getAttribute("imovelCobrancaSituacao");
		}

		guiaPagamento.setImovel(imovel);

        /** alterado por pedro alexandre dia 20/11/2006 
         * Recupera o usu�rio logado para passar no met�do de inserir guia de pagamento 
         * para verificar se o usu�rio tem abrang�ncia para inserir a guia de pagamento
         * para o im�vel caso a guia de pagamentoseja para o im�vel.
         */
        Usuario usuarioLogado = (Usuario)sessao.getAttribute(Usuario.USUARIO_LOGADO);
        guiaPagamento.setUsuario(usuarioLogado);
        fachada.manterGuiaPagamento(guiaPagamento, guiasPagamento,registrosRemocao, imovelCobrancaSituacao,usuarioLogado);
		//fachada.manterGuiaPagamento(guiaPagamento, guiasPagamento,registrosRemocao, imovelCobrancaSituacao);

		sessao.removeAttribute("imovelCobrancaSituacao");
		sessao.removeAttribute("guiasPagamentos");
		sessao.removeAttribute("ManterGuiaPagamentoActionForm");

		if (idImovel != null && !idImovel.equals("")) {

			montarPaginaSucesso(httpServletRequest, registrosRemocao.length
					+ " Guia(s) de Pagamento do im�vel " + idImovel
					+ " cancelada(s) com sucesso.",
					"Realizar outro Cancelamento de Guia de Pagamento",
					"exibirManterGuiaPagamentoAction.do?menu=sim");

		}

		if (idCliente != null && !idCliente.equals("")) {

			montarPaginaSucesso(httpServletRequest, registrosRemocao.length
					+ " Guia(s) de Pagamento do cliente " + idCliente
					+ " cancelada(s) com sucesso.",
					"Realizar outro Cancelamento de Guia de Pagamento",
					"exibirManterGuiaPagamentoAction.do?menu=sim");

		}

		return retorno;
	}
}
