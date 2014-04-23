package gcom.gui.cadastro.imovel;

import gcom.cadastro.endereco.FiltroLogradouro;
import gcom.cadastro.endereco.Logradouro;
import gcom.cadastro.geografico.Bairro;
import gcom.cadastro.geografico.FiltroBairro;
import gcom.cadastro.geografico.FiltroMunicipio;
import gcom.cadastro.geografico.Municipio;
import gcom.cadastro.localidade.FiltroGerenciaRegional;
import gcom.cadastro.localidade.GerenciaRegional;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorActionForm;

public class ExibirFiltrarImovelLocalizacaoAction extends GcomAction {
	/**
	 * < <Descri��o do m�todo>>
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
	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		ActionForward retorno = actionMapping
				.findForward("filtrarClienteOutrosCriterios");

		DynaValidatorActionForm pesquisarActionForm = (DynaValidatorActionForm) actionForm;

		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();

		// Parte que pega as cole��es da sess�o

		// Ger�ncia Regional
		if(sessao.getAttribute("gerenciasRegionais") == null){
			FiltroGerenciaRegional filtroGerenciaRegional = new FiltroGerenciaRegional();
			filtroGerenciaRegional.adicionarParametro(new ParametroSimples(
					FiltroGerenciaRegional.INDICADOR_USO,
					ConstantesSistema.INDICADOR_USO_ATIVO));
			Collection gerenciasRegionais = fachada.pesquisar(filtroGerenciaRegional,
					GerenciaRegional.class.getName());
			
			if (gerenciasRegionais == null || gerenciasRegionais.isEmpty()) {
				throw new ActionServletException("atencao.naocadastrado", null,
						"Ger�ncia Regional");
			} else {
				sessao.setAttribute("gerenciasRegionais", gerenciasRegionais);
			}
		}

		// Unidade Negocio
		if(sessao.getAttribute("colecaoUnidadeNegocio") == null){
			sessao.setAttribute("colecaoUnidadeNegocio", fachada
					.obterColecaoUnidadeNegocio());
		}

		
		// -------Parte que trata do c�digo quando o usu�rio tecla enter

		// C�digo do Munic�pio
        String codigoDigitadoMunicipioEnter = (String) pesquisarActionForm
        .get("idMunicipio");

		// Verifica se o c�digo foi digitado
		if (codigoDigitadoMunicipioEnter != null
				&& !codigoDigitadoMunicipioEnter.trim().equals("")
				&& Integer.parseInt(codigoDigitadoMunicipioEnter) > 0) {
			FiltroMunicipio filtroMunicipio = new FiltroMunicipio();

			filtroMunicipio.adicionarParametro(new ParametroSimples(
					FiltroMunicipio.ID, codigoDigitadoMunicipioEnter));
			filtroMunicipio.adicionarParametro(new ParametroSimples(
					FiltroMunicipio.INDICADOR_USO,
					ConstantesSistema.INDICADOR_USO_ATIVO));

			Collection municipioEncontrado = fachada.pesquisar(filtroMunicipio,
					Municipio.class.getName());

			if (municipioEncontrado != null && !municipioEncontrado.isEmpty()) {
				// O municipio foi encontrado
                pesquisarActionForm.set("idMunicipio", ""
                        + ((Municipio) ((List) municipioEncontrado).get(0))
                                .getId());
                
                pesquisarActionForm.set("descricaoMunicipioCliente",
                        ((Municipio) ((List) municipioEncontrado).get(0))
                                .getNome());
				//httpServletRequest.setAttribute("municipioNaoEncontrado",
					//	"true");

			} else {
                pesquisarActionForm.set("idMunicipioCliente", "");
                httpServletRequest.setAttribute("municipioNaoEncontrado",
                        "exception");
                pesquisarActionForm.set("descricaoMunicipioCliente",
                        "C�digo inexistente");
			}
		}

		// C�digo do Bairro
        String codigoDigitadoBairroEnter = (String) pesquisarActionForm
        .get("codigoBairro");
		// Verifica se o c�digo foi digitado
		if (codigoDigitadoBairroEnter != null
				&& !codigoDigitadoBairroEnter.trim().equals("")
				&& Integer.parseInt(codigoDigitadoBairroEnter) > 0) {
			FiltroBairro filtroBairro = new FiltroBairro();

			filtroBairro.adicionarParametro(new ParametroSimples(
					FiltroBairro.CODIGO, codigoDigitadoBairroEnter));
			filtroBairro.adicionarParametro(new ParametroSimples(
					FiltroBairro.INDICADOR_USO,
					ConstantesSistema.INDICADOR_USO_ATIVO));
			// Adiciona a busca por munic�pio se ele foi digitado na p�gina
			if (codigoDigitadoMunicipioEnter != null
					&& !codigoDigitadoMunicipioEnter.equalsIgnoreCase("")) {
				filtroBairro
						.adicionarParametro(new ParametroSimples(
								FiltroBairro.MUNICIPIO_ID,
								codigoDigitadoMunicipioEnter));
			}

			Collection bairroEncontrado = fachada.pesquisar(filtroBairro,
					Bairro.class.getName());

			if (bairroEncontrado != null && !bairroEncontrado.isEmpty()) {
				// O bairro foi encontrado
                pesquisarActionForm.set("codigoBairro", ""
                        + ((Bairro) ((List) bairroEncontrado).get(0))
                                .getId());
                pesquisarActionForm.set("descricaoMunicipioCliente",
                        ((Bairro) ((List) bairroEncontrado).get(0))
                                .getNome());
				//httpServletRequest.setAttribute("bairroNaoEncontrado",
					//	"true");

			} else {
                pesquisarActionForm.set("codigoBairro", "");
                httpServletRequest.setAttribute("bairroNaoEncontrado",
                        "exception");
                pesquisarActionForm.set("descricaoBairroCliente",
                        "C�digo inexistente");
			}
		}

		// C�digo do Logradouro
        String codigoDigitadoLogradouroEnter = (String) pesquisarActionForm
        .get("idLogradouro");

		// Verifica se o c�digo foi digitado
		if (codigoDigitadoLogradouroEnter != null
				&& !codigoDigitadoLogradouroEnter.trim().equals("")
				&& Integer.parseInt(codigoDigitadoLogradouroEnter) > 0) {
			FiltroLogradouro filtroLogradouro = new FiltroLogradouro();
			filtroLogradouro.adicionarParametro(new ParametroSimples(
					FiltroLogradouro.ID, codigoDigitadoLogradouroEnter));
			filtroLogradouro.adicionarParametro(new ParametroSimples(
					FiltroLogradouro.INDICADORUSO,
					ConstantesSistema.INDICADOR_USO_ATIVO));
			// Adiciona a busca por munic�pio se ele foi digitado na p�gina
			// if (codigoDigitadoMunicipioEnter != null
			// && !codigoDigitadoMunicipioEnter.equalsIgnoreCase("")) {
			// filtroLogradouro.adicionarParametro(new
			// ParametroSimples(FiltroLogradouro.ID_MUNICIPIO,
			// codigoDigitadoMunicipioEnter));
			// }

			Collection logradouroEncontrado = fachada.pesquisar(
					filtroLogradouro, Logradouro.class.getName());

			if (logradouroEncontrado != null && !logradouroEncontrado.isEmpty()) {
				// O logradouro foi encontrado
                pesquisarActionForm.set("idLogradouro", ""
                        + ((Logradouro) ((List) logradouroEncontrado).get(0))
                                .getId());
                pesquisarActionForm.set("descricaoMunicipioCliente",
                        ((Logradouro) ((List) logradouroEncontrado).get(0))
                                .getNome());
				//httpServletRequest.setAttribute("bairroNaoEncontrado",
					//	"true");

			} else {
                pesquisarActionForm.set("idLogradouro", "");
                httpServletRequest.setAttribute("logradouroNaoEncontrado",
                        "exception");
                pesquisarActionForm.set("descricaoLogradouroCliente",
                        "C�digo inexistente");
			}
		}

		return retorno;
	}

}
