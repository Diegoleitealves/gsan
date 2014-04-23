package gcom.gui.cadastro.geografico;

import gcom.cadastro.geografico.FiltroMunicipio;
import gcom.cadastro.geografico.Municipio;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

/**
 * Esta classe tem por finalidade exibir para o usu�rio a tela que receber� os
 * par�metros para realiza��o da pesquisa dos bairros
 * 
 * @author Raphael Rossiter
 * @date 28/06/2006
 */
public class ExibirPesquisarBairroAction extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		ActionForward retorno = actionMapping.findForward("pesquisarBairro");

		Fachada fachada = Fachada.getInstancia();

		DynaValidatorForm pesquisarActionForm = (DynaValidatorForm) actionForm;

		HttpSession sessao = httpServletRequest.getSession(false);

		// Verifica se o pesquisar bairro foi chamado a partir do inserir bairro
		// e em caso afirmativo recebe o par�metro e manda-o pela sess�o para
		// ser verificado no bairro_resultado_pesquisa e depois retirado da
		// sess�o no ExibirFiltrarBairroAction
		if (httpServletRequest.getParameter("consulta") != null) {
			String consulta = httpServletRequest.getParameter("consulta");
			sessao.setAttribute("consulta", consulta);
		} else {
			if (httpServletRequest.getParameter("limparForm") == null
					&& httpServletRequest.getParameter("novaPesquisa") == null
					&& httpServletRequest.getParameter("pesquisarMunicipio") == null) {
				sessao.removeAttribute("consulta");
			}
		}

		/*
		 * Caso o par�metro "Munic�pio" seja previamente definido pelo caso de
		 * uso que chama est� funcionalidade, o mesmo dever� ser mantido para
		 * realiza��o da filtragem dos bairros
		 */
		if (httpServletRequest.getParameter("objetoConsulta") == null
				&& httpServletRequest.getParameter("tipoConsulta") == null) {
			pesquisarActionForm.set("idMunicipio", "");
			pesquisarActionForm.set("nomeMunicipio", "");
			pesquisarActionForm.set("nomeBairro", "");

		}

		if (httpServletRequest.getParameter("tipoConsulta") != null
				&& !httpServletRequest.getParameter("tipoConsulta").equals("")) {

			// Verifica se o tipo da consulta de imovel � de municipio
			// se for os parametros de enviarDadosParametros ser�o mandados para
			// a pagina logradouro_pesuisar.jsp
			if (httpServletRequest.getParameter("tipoConsulta").equals(
					"municipio")) {
				pesquisarActionForm.set("idMunicipio", httpServletRequest
						.getParameter("idCampoEnviarDados"));
				pesquisarActionForm.set("descricaoMunicipio",
						httpServletRequest
								.getParameter("descricaoCampoEnviarDados"));
			}
		}

		String idMunicipio = null;
		if (httpServletRequest.getParameter("idMunicipio") != null
				&& !httpServletRequest.getParameter("idMunicipio").trim()
						.equalsIgnoreCase("")) {
			idMunicipio = (String) httpServletRequest
					.getParameter("idMunicipio");
		} else {
			if (httpServletRequest.getAttribute("idMunicipio") != null
					&& !httpServletRequest.getAttribute("idMunicipio").equals(
							"")) {
				idMunicipio = (String) httpServletRequest
						.getAttribute("idMunicipio");
			}
		}

		if (idMunicipio != null
				&& !idMunicipio.trim().equalsIgnoreCase("")
				&& httpServletRequest.getParameter("pesquisarMunicipio") == null
				&& httpServletRequest.getParameter("limparForm") == null) {

			sessao.setAttribute("municipioRecebido", idMunicipio);
		}

		if (idMunicipio != null && !idMunicipio.equals("")
				&& httpServletRequest.getParameter("limparForm") == null) {

			FiltroMunicipio filtroMunicipio = new FiltroMunicipio();

			filtroMunicipio.adicionarParametro(new ParametroSimples(
					FiltroMunicipio.ID, idMunicipio));

			Collection colecaoMunicipio = fachada.pesquisar(filtroMunicipio,
					Municipio.class.getName());

			if (colecaoMunicipio != null && !colecaoMunicipio.isEmpty()) {

				Municipio municipio = (Municipio) Util
						.retonarObjetoDeColecao(colecaoMunicipio);

				pesquisarActionForm.set("idMunicipio", municipio.getId()
						.toString());
				pesquisarActionForm.set("descricaoMunicipio", municipio
						.getNome());

				httpServletRequest.setAttribute("nomeCampo", "nomeBairro");
			} else {

				pesquisarActionForm.set("idMunicipio", "");
				pesquisarActionForm.set("descricaoMunicipio",
						"MUNIC�PIO INEXISTENTE");

				httpServletRequest.setAttribute("nomeCampo", "idMunicipio");
				httpServletRequest.setAttribute("idMunicipioNaoEncontrado",
						"exception");
			}
		}

		if (httpServletRequest.getParameter("limparForm") != null
				&& sessao.getAttribute("municipioRecebido") == null
				&& httpServletRequest.getParameter("objetoConsulta") == null) {

			pesquisarActionForm.set("idMunicipio", "");
			pesquisarActionForm.set("descricaoMunicipio", "");
			pesquisarActionForm.set("nomeBairro", "");

			httpServletRequest.setAttribute("nomeCampo", "idMunicipio");
		} else if (httpServletRequest.getParameter("limparForm") != null
				&& sessao.getAttribute("municipioRecebido") != null
				&& httpServletRequest.getParameter("objetoConsulta") == null) {

			pesquisarActionForm.set("nomeBairro", "");

			httpServletRequest.setAttribute("nomeCampo", "nomeBairro");
		}

		/*
		 * Nova Pesquisa (Limpa o formul�rio caso o usu�rio clique no bot�o de
		 * "Nova Pesquisa" no resulta da consulta realizada)
		 */
		if (httpServletRequest.getParameter("novaPesquisa") != null
				&& sessao.getAttribute("municipioRecebido") == null
				&& httpServletRequest.getParameter("objetoConsulta") == null) {

			pesquisarActionForm.set("idMunicipio", "");
			pesquisarActionForm.set("descricaoMunicipio", "");
			pesquisarActionForm.set("nomeBairro", "");

			httpServletRequest.setAttribute("nomeCampo", "idMunicipio");
		} else if (httpServletRequest.getParameter("novaPesquisa") != null
				&& sessao.getAttribute("municipioRecebido") != null
				&& httpServletRequest.getParameter("objetoConsulta") == null) {

			pesquisarActionForm.set("nomeBairro", "");

			httpServletRequest.setAttribute("nomeCampo", "nomeBairro");
		}

		/*
		 * Envia uma flag que ser� verificada no bairro_resultado_pesquisa.jsp
		 * para saber se ir� usar o enviar dados ou o enviar dados parametros
		 */
		if (httpServletRequest.getParameter("tipo") != null) {
			sessao
					.setAttribute("tipo", httpServletRequest
							.getParameter("tipo"));
		}

		/*
		 * Define o retorno da pesquisa
		 */

		if (httpServletRequest.getParameter("caminhoRetornoTelaPesquisaBairro") != null) {
			sessao.setAttribute("caminhoRetornoTelaPesquisaBairro",
					httpServletRequest
							.getParameter("caminhoRetornoTelaPesquisaBairro"));
		}
		/*
		 * else { sessao.removeAttribute("caminhoRetornoTelaPesquisaBairro");
		 * sessao.setAttribute("tipo", ""); }
		 */

		if (httpServletRequest.getParameter("indicadorUsoTodos") != null) {
			sessao.setAttribute("indicadorUsoTodos", httpServletRequest
					.getParameter("indicadorUsoTodos"));
		}

		if (pesquisarActionForm.get("tipoPesquisa") == null
				|| pesquisarActionForm.get("tipoPesquisa").equals("")) {

			pesquisarActionForm.set("tipoPesquisa",
					ConstantesSistema.TIPO_PESQUISA_INICIAL.toString());

		}

		return retorno;
	}
}
