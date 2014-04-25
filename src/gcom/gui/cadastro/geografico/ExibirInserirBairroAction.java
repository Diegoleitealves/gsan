package gcom.gui.cadastro.geografico;

import gcom.cadastro.geografico.FiltroMunicipio;
import gcom.cadastro.geografico.Municipio;
import gcom.fachada.Fachada;
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

/**
 * Action respons�vel pela pre-exibi��o da pagina de inserir bairro
 * 
 * @author S�vio Luiz
 * @created 28 de Junho de 2004
 */
public class ExibirInserirBairroAction extends GcomAction {
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

		// Seta o mapeamento de retorno
		ActionForward retorno = null;

		BairroActionForm bairroActionForm = (BairroActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();
		
		HttpSession sessao = httpServletRequest.getSession(false);
		
		String redirecionarPagina = httpServletRequest.getParameter("redirecionarPagina");
		
		if(redirecionarPagina != null){
			retorno = actionMapping.findForward(redirecionarPagina);
		}else{
		    retorno = actionMapping.findForward("inserirBairro");
		}

		// Mudar isso quando tiver esquema de seguran�a
		//HttpSession sessao = httpServletRequest.getSession(false);

		// -------Parte que trata do c�digo quando o usu�rio tecla enter
		// caso seja o id do municipio
		String idDigitadoEnterMunicipio = (String) bairroActionForm
				.getIdMunicipio();

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
				bairroActionForm
						.setIdMunicipio(((Municipio) ((List) municipioEncontrado)
								.get(0)).getId().toString());
				bairroActionForm
						.setNomeMunicipio(((Municipio) ((List) municipioEncontrado)
								.get(0)).getNome());
				
				// Retorna o maior c�digo existente para o mun�cipio pesquisado
				int codigo = fachada.pesquisarMaximoCodigoBairro(new Integer(idDigitadoEnterMunicipio));
				
				// Acrescenta 1 no valor do c�digo para setar o primeiro c�digo vazio para o usu�rio
				codigo = codigo + 1;
				
				bairroActionForm.setCodigoBairro("" + codigo);
				
				httpServletRequest.setAttribute("nomeCampo", "codigoBairro");

			} else {
				bairroActionForm.setIdMunicipio("");
				httpServletRequest.setAttribute("idMunicipioNaoEncontrado",
						"true");
				bairroActionForm.setNomeMunicipio("Munic�pio inexistente");
				httpServletRequest.setAttribute("nomeCampo", "idMunicipio");

			}
		}

		// -------Fim da Parte que trata do c�digo quando o usu�rio tecla enter

		
		
		if (httpServletRequest.getParameter("limparForm") != null){
			bairroActionForm.setIdMunicipio("");
			bairroActionForm.setNomeMunicipio("");
			bairroActionForm.setCodigoBairro("");
			bairroActionForm.setNomeBairro("");
			bairroActionForm.setCodigoBairroPrefeitura("");
			sessao.removeAttribute("colecaoBairroArea");
			
		}
		
		
		sessao.setAttribute("reloadPage","INSERIRBAIRRO");
		
		return retorno;
	}
}
