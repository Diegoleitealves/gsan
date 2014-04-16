package gcom.gui.cadastro.endereco;

import gcom.cadastro.endereco.Cep;
import gcom.cadastro.endereco.FiltroCep;
import gcom.cadastro.endereco.FiltroLogradouroTipo;
import gcom.cadastro.endereco.FiltroLogradouroTitulo;
import gcom.cadastro.endereco.LogradouroTipo;
import gcom.cadastro.endereco.LogradouroTitulo;
import gcom.cadastro.geografico.Bairro;
import gcom.cadastro.geografico.FiltroBairro;
import gcom.cadastro.geografico.FiltroMunicipio;
import gcom.cadastro.geografico.Municipio;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

/**
 * Action respons�vel pela pre-exibi��o da pagina de filtrar logradouro
 * 
 * @author S�vio Luiz
 * @date   28/06/2006 
 */
public class ExibirFiltrarLogradouroAction extends GcomAction {
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

		ActionForward retorno = actionMapping.findForward("filtrarLogradouro");

		DynaValidatorForm pesquisarActionForm = (DynaValidatorForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);
		
		if (sessao.getAttribute("consultaLogradouro") != null) {
			sessao.removeAttribute("consultaLogradouro");
		}

		// -------Parte que trata do c�digo quando o usu�rio tecla enter
		// caso seja o id do municipio
		String idDigitadoEnterMunicipio = (String) pesquisarActionForm.get("idMunicipioFiltro");
		
		String idDigitadoEnterBairro = (String) pesquisarActionForm.get("idBairro");
		
		String codigoDigitadoEnterCep = (String) pesquisarActionForm.get("cep");
		
		if (sessao.getAttribute("atualizar") != null){
			sessao.removeAttribute("atualizar");
		}
		if (sessao.getAttribute("manter") != null){
			sessao.removeAttribute("manter");
		}
		
		// Verifica se o c�digo foi digitado
		if (idDigitadoEnterMunicipio != null
				&& !idDigitadoEnterMunicipio.trim().equals("")
				&& Integer.parseInt(idDigitadoEnterMunicipio) > 0) {
			FiltroMunicipio filtroMunicipio = new FiltroMunicipio();

			filtroMunicipio.adicionarParametro(new ParametroSimples(
					FiltroMunicipio.ID, idDigitadoEnterMunicipio));
			filtroMunicipio.adicionarParametro(new ParametroSimples(
					FiltroMunicipio.INDICADOR_USO,
					ConstantesSistema.INDICADOR_USO_ATIVO));

			Collection municipioEncontrado = fachada.pesquisar(filtroMunicipio,
					Municipio.class.getName());

			if (municipioEncontrado != null && !municipioEncontrado.isEmpty()) {
				// O municipio foi encontrado
				pesquisarActionForm.set("idMunicipioFiltro",
						((Municipio) ((List) municipioEncontrado).get(0))
								.getId().toString());
				pesquisarActionForm.set("nomeMunicipio",
						((Municipio) ((List) municipioEncontrado).get(0))
								.getNome());
				httpServletRequest.setAttribute("nomeCampo", "idBairro");

			} else {
				pesquisarActionForm.set("idMunicipioFiltro", "");
				httpServletRequest.setAttribute("idMunicipioNaoEncontrado",
						"true");
				pesquisarActionForm.set("nomeMunicipio",
						"Munic�pio inexistente");

				httpServletRequest.setAttribute("nomeCampo", "idMunicipioFiltro");

			}
		}
		
		if (idDigitadoEnterBairro != null
				&& !idDigitadoEnterBairro.trim().equals("")
				&& Integer.parseInt(idDigitadoEnterBairro) > 0) {
			
			if (idDigitadoEnterMunicipio == null || idDigitadoEnterMunicipio.trim().equalsIgnoreCase("")) {
	            //Limpa os campos bairroCD e bairroID do formulario
	            pesquisarActionForm.set("idBairro", "");
	            pesquisarActionForm.set("nomeBairro", "Informe Munic�pio");
	            httpServletRequest.setAttribute("corBairro", "exception");
	            
	            httpServletRequest.setAttribute("nomeCampo", "idMunicipioFiltro");
	        } else {
	            //Recebe o valor do campo bairroCD do formul�rio.
	            String idBairro = (String) pesquisarActionForm.get("idBairro");

	            FiltroBairro filtroBairro = new FiltroBairro();

	            filtroBairro.adicionarParametro(new ParametroSimples(
	                    FiltroBairro.MUNICIPIO_ID, idDigitadoEnterMunicipio));

	            filtroBairro.adicionarParametro(new ParametroSimples(
	                    FiltroBairro.CODIGO, idBairro));

	            filtroBairro.adicionarParametro(new ParametroSimples(
	                    FiltroBairro.INDICADOR_USO,
	                    ConstantesSistema.INDICADOR_USO_ATIVO));

	            //Retorna bairro
	           Collection<Bairro> colecaoPesquisa = fachada.pesquisar(filtroBairro, Bairro.class
	                    .getName());

	            if (colecaoPesquisa == null || colecaoPesquisa.isEmpty()) {
	                //bairro nao encontrado
	                //Limpa os campos bairroCD, bairroID e bairroNome
	                pesquisarActionForm.set("idBairro", "");
	                pesquisarActionForm.set("nomeBairro","Bairro inexistente.");
	                httpServletRequest.setAttribute("corBairro", "exception");
	                
	                httpServletRequest.setAttribute("nomeCampo", "idLogradouro");
	            } else {
	                Bairro objetoBairro = (Bairro) Util
	                        .retonarObjetoDeColecao(colecaoPesquisa);
	                pesquisarActionForm.set("idBairro", String.valueOf(objetoBairro
	                        .getCodigo()));
	                pesquisarActionForm.set("nomeBairro", objetoBairro.getNome());
	                httpServletRequest.setAttribute("corBairro", "valor");
	                
	                httpServletRequest.setAttribute("nomeCampo", "idBairro");
	            }

	        }
		}
		
		
		//Verifica se o c�digo do CEP foi digitado
		if ((codigoDigitadoEnterCep != null
			&& !codigoDigitadoEnterCep.trim().equals("")
			&& Integer.parseInt(codigoDigitadoEnterCep) > 0)) {
			
			FiltroCep filtroCep = new FiltroCep();

			filtroCep.adicionarParametro(new ParametroSimples(
			FiltroCep.CODIGO, codigoDigitadoEnterCep));
			
			filtroCep.adicionarParametro(new ParametroSimples(
			FiltroCep.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

			Collection cepEncontrado = fachada.pesquisar(filtroCep,
			Cep.class.getName());

			if (cepEncontrado != null && !cepEncontrado.isEmpty()) {
				// O CEP foi encontrado
				pesquisarActionForm.set("cep",
						((Cep) ((List) cepEncontrado).get(0))
								.getCodigo().toString());
				
				pesquisarActionForm.set("retornoCep",
						((Cep) ((List) cepEncontrado).get(0))
								.getDescricaoLogradouroFormatada());
				
				httpServletRequest.setAttribute("nomeCampo", "Button");

			} else {
				pesquisarActionForm.set("cep", "");
				httpServletRequest.setAttribute("cepNaoEncontrado",
						"true");
				pesquisarActionForm.set("retornoCep",
						"CEP INV�LIDO");

				httpServletRequest.setAttribute("nomeCampo", "cep");

			}
		}

		// verifica se n�o foi a pesquisa do enter
		// se n�o for ent�o � a primeira vez que entra no action
		// prepara as cole��es de logradouro tipo e logradouro titulo para
		// exibir
		// na p�gina
		if ((idDigitadoEnterMunicipio == null
			|| idDigitadoEnterMunicipio.trim().equals("")) &&
			(codigoDigitadoEnterCep == null
			|| codigoDigitadoEnterCep.trim().equals(""))) {

			FiltroLogradouroTipo filtroLogradouroTipo = new FiltroLogradouroTipo(
					FiltroLogradouroTipo.DESCRICAO);
			filtroLogradouroTipo.setConsultaSemLimites(true);
			filtroLogradouroTipo.adicionarParametro(new ParametroSimples(
					FiltroLogradouroTipo.INDICADORUSO,
					ConstantesSistema.INDICADOR_USO_ATIVO));

			Collection logradouroTipos = fachada.pesquisar(
					filtroLogradouroTipo, LogradouroTipo.class.getName());

			if (logradouroTipos == null || logradouroTipos.isEmpty()) {
				throw new ActionServletException(
						"atencao.pesquisa.nenhumresultado", null,
						"logradouro tipo");
			} else {
				sessao.setAttribute("logradouroTipos", logradouroTipos);
			}

			FiltroLogradouroTitulo filtroLogradouroTitulo = new FiltroLogradouroTitulo(
					FiltroLogradouroTitulo.DESCRICAO);
			filtroLogradouroTitulo.setConsultaSemLimites(true);
			filtroLogradouroTitulo.adicionarParametro(new ParametroSimples(
					FiltroLogradouroTipo.INDICADORUSO,
					ConstantesSistema.INDICADOR_USO_ATIVO));

			Collection logradouroTitulos = fachada.pesquisar(
					filtroLogradouroTitulo, LogradouroTitulo.class.getName());

			if (logradouroTitulos == null || logradouroTitulos.isEmpty()) {
				throw new ActionServletException(
						"atencao.pesquisa.nenhumresultado", null,
						"logradouro t�tulo");
			} else {
				sessao.setAttribute("logradouroTitulos", logradouroTitulos);
			}

		}
		
		if (pesquisarActionForm.get("tipoPesquisa") == null
				|| pesquisarActionForm.get("tipoPesquisa").equals("")) {

			pesquisarActionForm.set("tipoPesquisa",
					ConstantesSistema.TIPO_PESQUISA_INICIAL.toString());

		}
		
		if (pesquisarActionForm.get("tipoPesquisaPopular") == null
				|| pesquisarActionForm.get("tipoPesquisaPopular").equals("")) {

			pesquisarActionForm.set("tipoPesquisaPopular",
					ConstantesSistema.TIPO_PESQUISA_INICIAL.toString());

		}
		
		
		httpServletRequest.removeAttribute("i");
		
		String atualizar = httpServletRequest.getParameter("atualizar");
		String menu = httpServletRequest.getParameter("menu");
		
		if(httpServletRequest.getParameter("implog")!=null && 
				httpServletRequest.getParameter("implog").equals("sim")){
			pesquisarActionForm.set("indicadorImportanciaLogradouro", "1");
			sessao.setAttribute("indicadorImportanciaLogradouro",
			"true");
		}
		
		if (atualizar == null && menu == null){
			boolean i = true;
			httpServletRequest.setAttribute("i",i);
		} else{
			boolean i = true;
			httpServletRequest.setAttribute("i",i);
		}

		if (httpServletRequest.getAttribute("nomeCampo") == null){
			httpServletRequest.setAttribute("nomeCampo", "idMunicipioFiltro");
		}
		
		
		return retorno;
	}
}
