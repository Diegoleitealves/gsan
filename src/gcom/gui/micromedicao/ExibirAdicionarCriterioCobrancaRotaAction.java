package gcom.gui.micromedicao;

import gcom.cobranca.CobrancaAcao;
import gcom.cobranca.CobrancaCriterio;
import gcom.cobranca.FiltroCobrancaAcao;
import gcom.cobranca.FiltroCobrancaCriterio;
import gcom.cobranca.RotaAcaoCriterio;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 
 * Action de exibir adicionar crit�rio de cobran�a da rota
 * 
 * @author Vivianne Sousa
 * @created 25/04/2006
 */

public class ExibirAdicionarCriterioCobrancaRotaAction extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		HttpSession sessao = httpServletRequest.getSession(false);
		// Inicializando Variaveis
		ActionForward retorno = actionMapping
				.findForward("adicionarCriterioCobrancaRota");
		
		Fachada fachada = Fachada.getInstancia();
		
		AdicionarCriterioCobrancaRotaActionForm adicionarCriterioCobrancaRotaActionForm = (AdicionarCriterioCobrancaRotaActionForm) actionForm;

		//Pesquisando grupo de cobran�a
		FiltroCobrancaAcao filtroCobrancaAcao = new FiltroCobrancaAcao();
		filtroCobrancaAcao.adicionarParametro(new ParametroSimples(
				FiltroCobrancaAcao.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
		Collection<CobrancaAcao> collectionCobrancaAcao = fachada.pesquisar(
				filtroCobrancaAcao, CobrancaAcao.class.getName());
		
		//Cole��o de A��o Crit�rios j� escolhido na se��o
		Collection<RotaAcaoCriterio> collectionRotaAcaoCriterio = null;
        if (sessao.getAttribute("collectionRotaAcaoCriterio") != null) {
        	collectionRotaAcaoCriterio = (Collection) sessao
                    .getAttribute("collectionRotaAcaoCriterio");
        } else {
        	collectionRotaAcaoCriterio = new ArrayList<RotaAcaoCriterio>();
        }
        
        List<CobrancaAcao> acoesParaRemover = new ArrayList<CobrancaAcao>();
        
        //Remove dos criterios da pesquisa os ja escolhidos que est�o na colecao da sessao
        for (CobrancaAcao acao : collectionCobrancaAcao) {
        	for (RotaAcaoCriterio criterio : collectionRotaAcaoCriterio) {
        		if (criterio.getCobrancaAcao().getId().equals(acao.getId())) {
        			acoesParaRemover.add(acao);
        			break;
        		}
        	}
        }
		
        collectionCobrancaAcao.removeAll(acoesParaRemover);
		
		sessao.setAttribute("collectionCobrancaAcao", collectionCobrancaAcao);
		// Fim de pesquisando grupo de cobran�a/

		if (sessao.getAttribute("idCobrancaAcao") != null && !sessao.getAttribute("idCobrancaAcao").equals("")){
			adicionarCriterioCobrancaRotaActionForm.setCobrancaAcao((String)sessao.getAttribute("idCobrancaAcao"));
		}

		if (httpServletRequest.getParameter("desfazer") != null
				&& httpServletRequest.getParameter("desfazer")
						.equalsIgnoreCase("S")) {
			// limpar tela
			adicionarCriterioCobrancaRotaActionForm.setCobrancaAcao(""
					+ ConstantesSistema.NUMERO_NAO_INFORMADO);
			adicionarCriterioCobrancaRotaActionForm.setIdCobrancaCriterio("");
			adicionarCriterioCobrancaRotaActionForm
					.setDescricaoCobrancaCriterio("");

		}

		// -------Parte que trata do c�digo quando o usu�rio tecla enter
		if (adicionarCriterioCobrancaRotaActionForm != null){
			String idDigitadoEnterCobrancaCriterio = adicionarCriterioCobrancaRotaActionForm.getIdCobrancaCriterio();
			verificaExistenciaCodCobrancaCriterio(idDigitadoEnterCobrancaCriterio,
					adicionarCriterioCobrancaRotaActionForm, httpServletRequest,
					fachada);
		}
		// -------Fim de parte que trata do c�digo quando o usu�rio tecla enter

		sessao.removeAttribute("caminhoRetornoTelaPesquisa");

		if (httpServletRequest.getParameter("tipoConsulta") != null
				&& !httpServletRequest.getParameter("tipoConsulta").equals("")) {
			// se for os parametros de enviarDadosParametros ser�o mandados para
			// a pagina criterio_cobranca_rota_adicionar_popup.jsp
			adicionarCriterioCobrancaRotaActionForm
					.setIdCobrancaCriterio(httpServletRequest
							.getParameter("idCampoEnviarDados"));
			adicionarCriterioCobrancaRotaActionForm
					.setDescricaoCobrancaCriterio(httpServletRequest
							.getParameter("descricaoCampoEnviarDados"));
			//sessao.setAttribute("AdicionarCriterioCobrancaRotaActionForm",adicionarCriterioCobrancaRotaActionForm);
		}

		return retorno;

	}

	private void verificaExistenciaCodCobrancaCriterio(
			String idDigitadoEnterCobrancaCriterio,
			AdicionarCriterioCobrancaRotaActionForm adicionarCriterioCobrancaRotaActionForm,
			HttpServletRequest httpServletRequest, Fachada fachada) {

		// Verifica se o c�digo da CobrancaCriterio foi digitado
		if (idDigitadoEnterCobrancaCriterio != null
				&& !idDigitadoEnterCobrancaCriterio.trim().equals("")
				&& Integer.parseInt(idDigitadoEnterCobrancaCriterio) > 0) {

			// Recupera a CobrancaCriterio informada pelo usu�rio
			FiltroCobrancaCriterio filtroCobrancaCriterio = new FiltroCobrancaCriterio();

			filtroCobrancaCriterio.adicionarParametro(new ParametroSimples(
					FiltroCobrancaCriterio.ID, new Integer(
							idDigitadoEnterCobrancaCriterio)));

			Collection<CobrancaCriterio> cobrancaCriterios = fachada.pesquisar(
					filtroCobrancaCriterio, CobrancaCriterio.class.getName());

			if (cobrancaCriterios != null && !cobrancaCriterios.isEmpty()) {
				// a CobrancaCriterio foi encontrada
				CobrancaCriterio cobrancaCriterioEncontrada = (CobrancaCriterio) Util
						.retonarObjetoDeColecao(cobrancaCriterios);
				adicionarCriterioCobrancaRotaActionForm
						.setIdCobrancaCriterio(""
								+ (cobrancaCriterioEncontrada.getId()));
				adicionarCriterioCobrancaRotaActionForm
						.setDescricaoCobrancaCriterio(cobrancaCriterioEncontrada
								.getDescricaoCobrancaCriterio());
				httpServletRequest.setAttribute(
						"idCobrancaCriterioNaoEncontrado", "true");

			} else {
				// a CobrancaCriterio n�o foi encontrada
				adicionarCriterioCobrancaRotaActionForm
						.setIdCobrancaCriterio("");
				httpServletRequest.setAttribute(
						"idCobrancaCriterioNaoEncontrado", "exception");
				adicionarCriterioCobrancaRotaActionForm
						.setDescricaoCobrancaCriterio("CRIT�RIO DE COBRAN�A INEXISTENTE");

			}
		}

	}

}
