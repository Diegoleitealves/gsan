package gcom.gui.atendimentopublico.ligacaoagua;

import gcom.atendimentopublico.bean.IntegracaoComercialHelper;
import gcom.atendimentopublico.ligacaoagua.LigacaoAgua;
import gcom.cadastro.imovel.Imovel;
import gcom.fachada.Fachada;
import gcom.faturamento.consumotarifa.ConsumoTarifa;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC0463] Atualizar Consumo M�nimo da Liga��o de �gua
 * 
 * Apresenta��o da atualiza��o de consumo m�nimo de liga��o de �gua
 * 
 * @author Leonardo Regis
 * @date 30/08/2006
 */
public class AtualizarConsumoMinimoLigacaoAguaAction extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		// Seta mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");
		AtualizarConsumoMinimoLigacaoAguaActionForm atualizarConsumoMinimoLigacaoAguaActionForm = (AtualizarConsumoMinimoLigacaoAguaActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);

		// Usuario logado no sistema
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		// Imovel
		Imovel imovel = new Imovel();
		imovel.setId(new Integer(atualizarConsumoMinimoLigacaoAguaActionForm
				.getMatriculaImovel()));
		ConsumoTarifa consumoTarifa = new ConsumoTarifa();
		consumoTarifa.setId(new Integer(
				atualizarConsumoMinimoLigacaoAguaActionForm
						.getConsumoTarifaId()));
		imovel.setConsumoTarifa(consumoTarifa);
		// Liga��o �gua
		LigacaoAgua ligacaoAgua = new LigacaoAgua();
		ligacaoAgua.setId(imovel.getId());
		
		if (atualizarConsumoMinimoLigacaoAguaActionForm
				.getConsumoMinimoFixado() != null && !atualizarConsumoMinimoLigacaoAguaActionForm
				.getConsumoMinimoFixado().trim().equals("")) {
			ligacaoAgua.setNumeroConsumoMinimoAgua(new Integer(
				atualizarConsumoMinimoLigacaoAguaActionForm
						.getConsumoMinimoFixado()));
		}
		
		ligacaoAgua
				.setUltimaAlteracao(atualizarConsumoMinimoLigacaoAguaActionForm
						.getDataConcorrencia());
		imovel.setLigacaoAgua(ligacaoAgua);
		// [FS0004] Validar Consumo M�nimo
		if (atualizarConsumoMinimoLigacaoAguaActionForm
				.getConsumoMinimoFixado() != null && !atualizarConsumoMinimoLigacaoAguaActionForm
				.getConsumoMinimoFixado().trim().equals("")) {
			fachada.validarConsumoMinimoLigacaoAgua(imovel);
		}

		IntegracaoComercialHelper integracaoComercialHelper = new IntegracaoComercialHelper();

		integracaoComercialHelper.setLigacaoAgua(ligacaoAgua);
		integracaoComercialHelper.setUsuarioLogado(usuario);
		
		if (atualizarConsumoMinimoLigacaoAguaActionForm.getVeioEncerrarOS()
				.equalsIgnoreCase("FALSE")) {
			integracaoComercialHelper.setVeioEncerrarOS(Boolean.FALSE);
			
			// Efetuando Atualiza��o volume m�nimo da Liga��o de �gua
			fachada.atualizarConsumoMinimoLigacaoAgua(integracaoComercialHelper);

		} else {
			integracaoComercialHelper.setVeioEncerrarOS(Boolean.TRUE);
			sessao.setAttribute("integracaoComercialHelper",
					integracaoComercialHelper);

			if (sessao.getAttribute("semMenu") == null) {
				retorno = actionMapping
						.findForward("encerrarOrdemServicoAction");
			} else {
				retorno = actionMapping
						.findForward("encerrarOrdemServicoPopupAction");
			}
			sessao.removeAttribute("caminhoRetornoIntegracaoComercial");
		}
		if (retorno.getName().equalsIgnoreCase("telaSucesso")) {

			// Monta a p�gina de sucesso
			montarPaginaSucesso(
					httpServletRequest,
					"Atualiza��o do Consumo M�nimo da Liga��o de �gua "
							+ ligacaoAgua.getId() + " efetuada com Sucesso",
					"Atualizar o Consumo M�nimo de outra Liga��o de �gua",
					"exibirAtualizarConsumoMinimoLigacaoAguaAction.do?menu=sim",
					"exibirAtualizarConsumoMinimoLigacaoAguaAction.do?idOrdemServico="
							+ atualizarConsumoMinimoLigacaoAguaActionForm
									.getIdOrdemServico(),
					"Atualizar o Consumo M�nimo da Liga��o de �gua alterada");
		}

		return retorno;
	}
}
