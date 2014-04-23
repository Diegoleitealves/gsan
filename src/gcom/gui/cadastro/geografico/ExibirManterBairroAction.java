package gcom.gui.cadastro.geografico;

import gcom.cadastro.geografico.Bairro;
import gcom.cadastro.geografico.FiltroBairro;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

/**
 * Descri��o da classe
 * 
 * @author Administrador
 * @date 07/03/2006
 * 
 */
public class ExibirManterBairroAction extends GcomAction {

	/**
	 * <Breve descri��o sobre o caso de uso>
	 * 
	 * <Identificador e nome do caso de uso>
	 * 
	 * <Breve descri��o sobre o subfluxo>
	 * 
	 * <Identificador e nome do subfluxo>
	 * 
	 * <Breve descri��o sobre o fluxo secund�rio>
	 * 
	 * <Identificador e nome do fluxo secund�rio>
	 * 
	 * @author Administrador
	 * @date 07/03/2006
	 * 
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		ActionForward retorno = actionMapping.findForward("manterBairro");

		Fachada fachada = Fachada.getInstancia();

		DynaValidatorForm pesquisarActionForm = (DynaValidatorForm) actionForm;

		Collection bairros = null;

		// Mudar isso quando implementar a parte de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		// Parte da verifica��o do filtro
		FiltroBairro filtroBairro = null;

		// Verifica se o filtro foi informado pela p�gina de filtragem de
		// cliente
		
		if (sessao.getAttribute("i") != null){
			sessao.removeAttribute("i");
		}
		
		if (httpServletRequest.getAttribute("filtroBairro") != null) {
			filtroBairro = (FiltroBairro) httpServletRequest
					.getAttribute("filtroBairro");
		} else {

			// Caso o exibirManterBairro n�o tenha passado por algum esquema de
			// filtro,
			// a quantidade de registros � verificada para avaliar a necessidade
			// de filtragem
			filtroBairro = new FiltroBairro();

			if (fachada.registroMaximo(Bairro.class) > ConstantesSistema.NUMERO_MAXIMO_REGISTROS_MANUTENCAO) {
				// Se o limite de registros foi atingido, a p�gina de filtragem
				// � chamada
				retorno = actionMapping.findForward("filtrarBairro");

				// limpa os parametros do form pesquisar
				pesquisarActionForm.set("idMunicipio", "");
				pesquisarActionForm.set("nomeMunicipio", "");
				pesquisarActionForm.set("codigoBairro", "");
				pesquisarActionForm.set("nomeBairro", "");
				pesquisarActionForm.set("indicadorUso", "");

			}
		}

		// A pesquisa de bairros s� ser� feita se o forward estiver direcionado
		// para a p�gina de manterBairro
		if (retorno.getName().equalsIgnoreCase("manterBairro")) {
			// Seta a ordena��o desejada do filtro
			filtroBairro.setCampoOrderBy(FiltroBairro.NOME);

			// Informa ao filtro para ele buscar objetos associados ao Bairro
			filtroBairro
					.adicionarCaminhoParaCarregamentoEntidade("municipio");

			// Aciona o controle de pagina��o para que sejam pesquisados apenas
			// os registros que aparecem na p�gina
			Map resultado = controlarPaginacao(httpServletRequest, retorno,
					filtroBairro, Bairro.class.getName());
			bairros = (Collection) resultado.get("colecaoRetorno");
			retorno = (ActionForward) resultado.get("destinoActionForward");

			if (bairros == null || bairros.isEmpty()) {
				// Nenhum cliente cadastrado
				throw new ActionServletException("atencao.naocadastrado", null,
						"bairro");
			}

			sessao.setAttribute("bairros", bairros);

		}
		return retorno;
	}

}
