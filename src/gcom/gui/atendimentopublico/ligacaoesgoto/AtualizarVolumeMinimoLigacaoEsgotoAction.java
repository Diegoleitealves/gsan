package gcom.gui.atendimentopublico.ligacaoesgoto;

import gcom.atendimentopublico.bean.IntegracaoComercialHelper;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgoto;
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
 * [UC0464] Atualizar Volume M�nimo da Liga��o de Esgoto
 * 
 * Apresenta��o da atualiza��o de volume m�nimo de liga��o de esgoto
 * 
 * @author Leonardo Regis
 * @date 22/09/2006
 */
public class AtualizarVolumeMinimoLigacaoEsgotoAction extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		// Seta mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");
		AtualizarVolumeMinimoLigacaoEsgotoActionForm form = (AtualizarVolumeMinimoLigacaoEsgotoActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);

		// Usuario logado no sistema
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

		// Imovel
		Imovel imovel = new Imovel();
		imovel.setId(new Integer(form.getMatriculaImovel()));
		ConsumoTarifa consumoTarifa = new ConsumoTarifa();
		consumoTarifa.setId(new Integer(form.getConsumoTarifaId()));
		imovel.setConsumoTarifa(consumoTarifa);
		// Liga��o Esgoto
		LigacaoEsgoto ligacaoEsgoto = new LigacaoEsgoto();
		ligacaoEsgoto.setId(imovel.getId());
		
		if (form.getConsumoMinimoFixado() != null
				&& !form.getConsumoMinimoFixado().trim().equals("")) {
			ligacaoEsgoto.setConsumoMinimo(new Integer(form
					.getConsumoMinimoFixado()));
		}
		
		ligacaoEsgoto.setUltimaAlteracao(form.getDataConcorrencia());
		imovel.setLigacaoEsgoto(ligacaoEsgoto);
		
		// [FS0004] Validar Volume M�nimo
		if (form.getConsumoMinimoFixado() != null
				&& !form.getConsumoMinimoFixado().trim().equals("")) {
			fachada.validarVolumeMinimoLigacaoEsgoto(imovel);
		}
		
		IntegracaoComercialHelper integracaoComercialHelper = new IntegracaoComercialHelper();

		integracaoComercialHelper.setLigacaoEsgoto(ligacaoEsgoto);
		integracaoComercialHelper.setUsuarioLogado(usuario);

		if (form.getVeioEncerrarOS().equalsIgnoreCase("FALSE")) {
			integracaoComercialHelper.setVeioEncerrarOS(Boolean.FALSE);

			// Efetuando Atualiza��o volume m�nimo da Liga��o de Esgoto
			fachada.atualizarVolumeMinimoLigacaoEsgoto(integracaoComercialHelper);
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
					"Atualiza��o do Volume M�nimo da Liga��o de Esgoto "
							+ ligacaoEsgoto.getId() + " efetuada com Sucesso",
					"Atualizar o Volume M�nimo de outra Liga��o de Esgoto",
					"exibirAtualizarVolumeMinimoLigacaoEsgotoAction.do?menu=sim",
					"exibirAtualizarVolumeMinimoLigacaoEsgotoAction.do?idOrdemServico="
							+ form.getIdOrdemServico(),
					"Atualizar o Volume M�nimo da Liga��o de Esgoto alterada");
		}

		return retorno;
	}
}
