package gcom.gui.relatorio.cadastro.categoria;

import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.FiltroCategoria;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.cadastro.categoria.RelatorioManterCategoria;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

/**
 * action respons�vel pela exibi��o do relat�rio de bairro manter
 * 
 * @author S�vio Luiz
 * @created 11 de Julho de 2005
 */
public class GerarRelatorioCategoriaManterAction extends
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

		DynaValidatorForm pesquisarActionForm = (DynaValidatorForm) actionForm;

		FiltroCategoria filtroCategoria = (FiltroCategoria) sessao
				.getAttribute("filtroCategoria");

		// Inicio da parte que vai mandar os parametros para o relat�rio

		Categoria categoriaParametros = new Categoria();

		Integer id = null;
		
		String idPesquisar = (String) pesquisarActionForm.get("id");
		
		if (idPesquisar != null && !idPesquisar.equals("")) {
			id = new Integer(idPesquisar);
		}

		Short indicadorDeUso = null;

		if (pesquisarActionForm.get("indicadorUso") != null
				&& !pesquisarActionForm.get("indicadorUso").equals("")) {

			indicadorDeUso = new Short((String) pesquisarActionForm.get("indicadorUso"));
		}

		// seta os parametros que ser�o mostrados no relat�rio
		categoriaParametros.setId(id);
		categoriaParametros.setDescricao((String) pesquisarActionForm.get("descricao"));
		categoriaParametros.setIndicadorUso(indicadorDeUso);

		// Fim da parte que vai mandar os parametros para o relat�rio
		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");

		RelatorioManterCategoria relatorioManterCategoria = new RelatorioManterCategoria(
				(Usuario)(httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));
		relatorioManterCategoria.addParametro("filtroCategoria", filtroCategoria);
		relatorioManterCategoria.addParametro("categoriaParametros", categoriaParametros);
		if (tipoRelatorio == null) {
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		relatorioManterCategoria.addParametro("tipoFormatoRelatorio", Integer
				.parseInt(tipoRelatorio));
		retorno = processarExibicaoRelatorio(relatorioManterCategoria, tipoRelatorio,
				httpServletRequest, httpServletResponse, actionMapping);

		// devolve o mapeamento contido na vari�vel retorno
		return retorno;
	}

}
