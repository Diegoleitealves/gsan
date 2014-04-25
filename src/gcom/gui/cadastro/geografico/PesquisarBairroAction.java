package gcom.gui.cadastro.geografico;

import gcom.cadastro.geografico.Bairro;
import gcom.cadastro.geografico.FiltroBairro;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ComparacaoTexto;
import gcom.util.filtro.ComparacaoTextoCompleto;
import gcom.util.filtro.ParametroSimples;

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
 * Action respons�vel para recuperar os parametros da pesquisa e fazer a
 * pesquisa de bairro
 * 
 * @author compesa
 * @created 27 de Maio de 2004
 */
public class PesquisarBairroAction extends GcomAction {
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

		ActionForward retorno = actionMapping.findForward("listaBairro");

		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		DynaValidatorForm pesquisarActionForm = (DynaValidatorForm) actionForm;

		// Recupera os par�metros do form
		String idMunicipio = (String) pesquisarActionForm.get("idMunicipio");
		String nomeBairro = (String) pesquisarActionForm.get("nomeBairro");
		String tipoPesquisa = (String) pesquisarActionForm.get("tipoPesquisa");

		FiltroBairro filtroBairro = new FiltroBairro(FiltroBairro.NOME);

		boolean peloMenosUmParametroInformado = false;

		// Insere os par�metros informados no filtro

		if (idMunicipio != null && !idMunicipio.trim().equalsIgnoreCase("")) {
			peloMenosUmParametroInformado = true;
			filtroBairro.adicionarParametro(new ParametroSimples(
					FiltroBairro.MUNICIPIO_ID, idMunicipio));
		}

		if (nomeBairro != null && !nomeBairro.trim().equalsIgnoreCase("")) {
			peloMenosUmParametroInformado = true;
			if (tipoPesquisa != null
					&& tipoPesquisa
							.equals(ConstantesSistema.TIPO_PESQUISA_COMPLETA
									.toString())) {
				filtroBairro.adicionarParametro(new ComparacaoTextoCompleto(
						FiltroBairro.NOME, nomeBairro));
			} else {
				filtroBairro.adicionarParametro(new ComparacaoTexto(
						FiltroBairro.NOME, nomeBairro));
			}
		}
		if( sessao.getAttribute("indicadorUsoTodos") == null ){
			filtroBairro.adicionarParametro(new ParametroSimples(
					FiltroBairro.INDICADOR_USO,
					ConstantesSistema.INDICADOR_USO_ATIVO));

        }
        
		
		// Erro caso o usu�rio mandou filtrar sem nenhum par�metro
		if (!peloMenosUmParametroInformado) {
			throw new ActionServletException(
					"atencao.filtro.nenhum_parametro_informado");
		}

		Collection bairros = null;

		// Obt�m a inst�ncia da Fachada
		Fachada fachada = Fachada.getInstancia();

		// adiciona as depend�ncias para serem mostradas na p�gina
		filtroBairro.adicionarCaminhoParaCarregamentoEntidade("municipio");

		// Faz a busca das empresas
		bairros = fachada.pesquisar(filtroBairro, Bairro.class.getName());

		if (bairros == null || bairros.isEmpty()) {

			// Nenhum municipio cadastrado
			throw new ActionServletException(
					"atencao.pesquisa.nenhumresultado", null, "bairro");
		} else {

			// Aciona o controle de pagina��o para que sejam pesquisados apenas
			// os registros que aparecem na p�gina
			Map resultado = controlarPaginacao(httpServletRequest, retorno,
					filtroBairro, Bairro.class.getName());
			bairros = (Collection) resultado.get("colecaoRetorno");
			retorno = (ActionForward) resultado.get("destinoActionForward");

			// Coloca a cole��o na sess�o
			sessao.setAttribute("colecaoBairros", bairros);

		}

		return retorno;
	}
}
