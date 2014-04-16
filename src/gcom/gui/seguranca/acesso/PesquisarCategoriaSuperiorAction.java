package gcom.gui.seguranca.acesso;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.FiltroFuncionalidadeCategoria;
import gcom.seguranca.acesso.FuncionalidadeCategoria;
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

/**
 * @author Fernando Fontelles
 * 
 * @created 25/08/2009
 */
public class PesquisarCategoriaSuperiorAction extends GcomAction {
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
	public ActionForward execute(ActionMapping actionMapping,ActionForm actionForm, 
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		ActionForward retorno = actionMapping.findForward("listaCategoriaSuperiorResultado");
		
		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);
		

		PesquisarCategoriaSuperiorActionForm pesquisarCategoriaSuperiorActionForm 
			= (PesquisarCategoriaSuperiorActionForm) actionForm;

		// Recupera os par�metros do form
		
		String descricao = pesquisarCategoriaSuperiorActionForm.getDescricao();
		String tipoPesquisa = pesquisarCategoriaSuperiorActionForm.getTipoPesquisa();
		String modulo = pesquisarCategoriaSuperiorActionForm.getIdModulo();
		String indicadorUso = pesquisarCategoriaSuperiorActionForm.getIndicadorUso();
		
		// filtro para a pesquisa da unidade organizacional
		
		FiltroFuncionalidadeCategoria filtroFuncionalidadeCategoria = new FiltroFuncionalidadeCategoria();

		boolean peloMenosUmParametroInformado = false;

		// Insere os par�metros informados no filtro
		if (descricao != null && !descricao.equalsIgnoreCase("")) {
			
			peloMenosUmParametroInformado = true;
			
			if (tipoPesquisa != null && 
				tipoPesquisa.equals(ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString())) {
				
				filtroFuncionalidadeCategoria.adicionarParametro(
					new ComparacaoTextoCompleto(FiltroFuncionalidadeCategoria.DESCRICAO_MENU_ITEM, descricao));
			} else {
				filtroFuncionalidadeCategoria.adicionarParametro(
					new ComparacaoTexto(FiltroFuncionalidadeCategoria.DESCRICAO_MENU_ITEM, descricao));
			}
		}	

		if (modulo != null && !modulo.trim().equals(
				"" + ConstantesSistema.NUMERO_NAO_INFORMADO)) {
			
			peloMenosUmParametroInformado = true;
			
			filtroFuncionalidadeCategoria.adicionarParametro(
					new ParametroSimples(FiltroFuncionalidadeCategoria.MODULO_ID, modulo));
		}
		
		if (indicadorUso !=null && !indicadorUso.equalsIgnoreCase( "" )){
			filtroFuncionalidadeCategoria.adicionarParametro( new ParametroSimples
					(FiltroFuncionalidadeCategoria.INDICADOR_USO, indicadorUso ) );
        	peloMenosUmParametroInformado = true;
        }
		
		// Erro caso o usu�rio mandou filtrar sem nenhum par�metro
		if (!peloMenosUmParametroInformado) {
			throw new ActionServletException(
					"atencao.filtro.nenhum_parametro_informado");
		}

		// adiciona as depend�ncias para serem mostradas na p�gina
		
		filtroFuncionalidadeCategoria.adicionarCaminhoParaCarregamentoEntidade("modulo");
		
		//Faz a busca das Categorias das Funcionalidades
        Collection colecaoCategoriaSuperior = Fachada.getInstancia()
        	.pesquisar(filtroFuncionalidadeCategoria, FuncionalidadeCategoria.class.getName());
		
		// Aciona o controle de pagina��o para que sejam pesquisados apenas
		// os registros que aparecem na p�gina
		Map resultado = controlarPaginacao(httpServletRequest, retorno,
				filtroFuncionalidadeCategoria, FuncionalidadeCategoria.class.getName());
		
		colecaoCategoriaSuperior = (Collection) resultado.get("colecaoRetorno");
		retorno = (ActionForward) resultado.get("destinoActionForward");
		
		// Coloca a cole��o na sess�o			
		sessao.setAttribute("colecaoCategoriaSuperior",colecaoCategoriaSuperior);
		
		if(resultado.isEmpty()){
			throw new ActionServletException(
					"atencao.pesquisa.nenhumresultado", null, "categoria superior");
		}
		
		return retorno;
	}

}
