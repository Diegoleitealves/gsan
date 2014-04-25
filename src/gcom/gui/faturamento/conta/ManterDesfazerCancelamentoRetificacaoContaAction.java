package gcom.gui.faturamento.conta;

import gcom.fachada.Fachada;
import gcom.faturamento.conta.Conta;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
/**
 * Desfaz o cancelamento e/ou retifica��o da conta
 * 
 * [UC0327] Desfazer Cancelamento e/ou Retifica��o de Conta 
 * 
 * @param actionMapping
 *            Descri��o do par�metro
 * @param actionForm
 *            Descri��o do par�metro
 * @param httpServletRequest
 *            Descri��o do par�metro
 * @param httpServletResponse
 *            Descri��o do par�metro
 * @return Descri��o do retorno
 */
public class ManterDesfazerCancelamentoRetificacaoContaAction extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Obt�m a inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		// Obt�m a sess�o
		HttpSession sessao = httpServletRequest.getSession(false);

		DesfazerCancelamentoRetificacaoContaActionForm desfazerCancelamentoRetificacaoContaActionForm = (DesfazerCancelamentoRetificacaoContaActionForm) actionForm;
		
		String[] registrosRemocao = desfazerCancelamentoRetificacaoContaActionForm.getIdRegistrosRemocao();
		
		String idImovel = desfazerCancelamentoRetificacaoContaActionForm.getIdImovel();
		
		Collection colecaoContasRemocao = new ArrayList();
		if(sessao.getAttribute("contas") != null){
			Collection contas =	(Collection) sessao.getAttribute("contas");
			
			Iterator iteratorContas = contas.iterator();
			while (iteratorContas.hasNext()) {
				Conta conta = (Conta) iteratorContas.next();
				
				for (int i = 0; i < registrosRemocao.length; i++) {
					String idConta = registrosRemocao[i];
					if(idConta.equals(conta.getId().toString())){
						colecaoContasRemocao.add(conta);
					}
				}
			}
		}
		
		Usuario usuarioLogado = (Usuario)sessao.getAttribute(Usuario.USUARIO_LOGADO);		
		
		//[UC0329] - Restabelecer Situa��o Anterior de Conta
		fachada.restabelecerSituacaoAnteriorConta(colecaoContasRemocao, usuarioLogado);

		montarPaginaSucesso(httpServletRequest,
				"Desfazimento do Cancelamento e/ou Retifica��o de " + registrosRemocao.length + " Conta(s) do im�vel " + idImovel + " efetuado com sucesso.",
				"Desfazer Cancelamento e/ou Retifica��o de outra Conta",
				"exibirManterDesfazerCancelamentoRetificacaoContaAction.do?menu=sim");
		
		sessao.removeAttribute("contas");
		return retorno;
	}
}
