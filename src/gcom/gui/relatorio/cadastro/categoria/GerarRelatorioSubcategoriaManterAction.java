package gcom.gui.relatorio.cadastro.categoria;

import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.FiltroCategoria;
import gcom.cadastro.imovel.FiltroSubCategoria;
import gcom.cadastro.imovel.Subcategoria;
import gcom.fachada.Fachada;
import gcom.gui.cadastro.imovel.FiltrarSubcategoriaActionForm;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.cadastro.categoria.RelatorioManterSubcategoria;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * action respons�vel pela exibi��o do relat�rio de bairro manter
 * 
 * @author S�vio Luiz
 * @created 11 de Julho de 2005
 */
public class GerarRelatorioSubcategoriaManterAction extends
		ExibidorProcessamentoTarefaRelatorio {

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

		// cria a vari�vel de retorno
		ActionForward retorno = null;

		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		FiltrarSubcategoriaActionForm filtrarSubcategoriaActionForm = (FiltrarSubcategoriaActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		FiltroSubCategoria filtroSubcategoria = (FiltroSubCategoria) sessao
				.getAttribute("filtroSubcategoria");

		// Inicio da parte que vai mandar os parametros para o relat�rio

		Subcategoria subcategoriaParametros = new Subcategoria();

		String idCategoria = (String) filtrarSubcategoriaActionForm
				.getIdCategoria();

		Categoria categoria = null;

		if (idCategoria != null && !idCategoria.equals("")) {
			FiltroCategoria filtroCategoria = new FiltroCategoria();

			filtroCategoria.adicionarParametro(new ParametroSimples(
					FiltroCategoria.CODIGO, idCategoria));

			Collection colecaoCategorias = fachada.pesquisar(filtroCategoria,
					Categoria.class.getName());

			if (colecaoCategorias != null && !colecaoCategorias.isEmpty()) {
				// O municipio foi encontrado
				Iterator colecaoCategoriasIterator = colecaoCategorias
						.iterator();

				categoria = (Categoria) colecaoCategoriasIterator.next();

			}
		} else {
			categoria = new Categoria();
		}

		int codigo = -1;

		String codigoPesquisar = (String) filtrarSubcategoriaActionForm
				.getCodigoSubcategoria();

		if (codigoPesquisar != null && !codigoPesquisar.equals("")) {
			codigo = Integer.parseInt(codigoPesquisar);
		}

		Short indicadorDeUso = null;

		if (filtrarSubcategoriaActionForm.getIndicadorUso() != null
				&& !filtrarSubcategoriaActionForm.getIndicadorUso().equals("")) {

			indicadorDeUso = new Short(filtrarSubcategoriaActionForm
					.getIndicadorUso());
		}

		// seta os parametros que ser�o mostrados no relat�rio
		subcategoriaParametros.setCategoria(categoria);
		subcategoriaParametros.setCodigo(codigo);
		subcategoriaParametros.setDescricao(filtrarSubcategoriaActionForm
				.getDescricao());
		subcategoriaParametros.setIndicadorUso(indicadorDeUso);

		// Fim da parte que vai mandar os parametros para o relat�rio
		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");

		RelatorioManterSubcategoria relatorio = new RelatorioManterSubcategoria(
				(Usuario)(httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));
		relatorio.addParametro("filtroSubcategoria", filtroSubcategoria);
		relatorio.addParametro("subcategoriaParametros", subcategoriaParametros);
		if (tipoRelatorio == null) {
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		relatorio.addParametro("tipoFormatoRelatorio", Integer
				.parseInt(tipoRelatorio));
		retorno = processarExibicaoRelatorio(relatorio, tipoRelatorio,
				httpServletRequest, httpServletResponse, actionMapping);

		// devolve o mapeamento contido na vari�vel retorno
		return retorno;
	}

}
