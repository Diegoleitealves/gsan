package gcom.gui.relatorio.cobranca;

import gcom.cobranca.FiltroResolucaoDiretoria;
import gcom.cobranca.ResolucaoDiretoria;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.cobranca.RelatorioManterResolucaoDiretoria;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.Util;

import java.util.Date;

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
public class GerarRelatorioResolucaoDiretoriaManterAction extends
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

		FiltroResolucaoDiretoria filtroResolucaoDiretoria = (FiltroResolucaoDiretoria) sessao
				.getAttribute("filtroResolucaoDiretoria");

		// Inicio da parte que vai mandar os parametros para o relat�rio

		ResolucaoDiretoria resolucaoDiretoriaParametros = new ResolucaoDiretoria();

		String numero = "";

		String numeroPesquisar = (String) sessao.getAttribute("numero");

		if (numeroPesquisar != null && !numeroPesquisar.equals("")) {
			numero = numeroPesquisar;
		}

		String assunto = "";

		String assuntoPesquisar = (String) sessao.getAttribute("assunto");

		if (assuntoPesquisar != null && !assuntoPesquisar.equals("")) {
			assunto = assuntoPesquisar;
		}

		Date dataInicio = null;

		String dataInicioPesquisar = (String) sessao.getAttribute("dataInicio");

		if (dataInicioPesquisar != null && !dataInicioPesquisar.equals("")) {
			dataInicio = Util.converteStringParaDate(dataInicioPesquisar);
		}

		Date dataTermino = null;

		String dataTerminoPesquisar = (String) sessao.getAttribute("dataFim");

		if (dataTerminoPesquisar != null && !dataTerminoPesquisar.equals("")) {
			dataTermino = Util.converteStringParaDate(dataTerminoPesquisar);
		}

		// seta os parametros que ser�o mostrados no relat�rio
		resolucaoDiretoriaParametros.setNumeroResolucaoDiretoria(numero);
		resolucaoDiretoriaParametros.setDescricaoAssunto(assunto);
		resolucaoDiretoriaParametros.setDataVigenciaInicio(dataInicio);
		resolucaoDiretoriaParametros.setDataVigenciaFim(dataTermino);

		// Fim da parte que vai mandar os parametros para o relat�rio
		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");

		RelatorioManterResolucaoDiretoria relatorioManterResolucaoDiretoria = new RelatorioManterResolucaoDiretoria((Usuario)(httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));
		relatorioManterResolucaoDiretoria.addParametro("filtroResolucaoDiretoria",
				filtroResolucaoDiretoria);
		relatorioManterResolucaoDiretoria.addParametro("resolucaoDiretoriaParametros",
				resolucaoDiretoriaParametros);
		if (tipoRelatorio == null) {
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		relatorioManterResolucaoDiretoria.addParametro("tipoFormatoRelatorio", Integer
				.parseInt(tipoRelatorio));
		retorno = processarExibicaoRelatorio(relatorioManterResolucaoDiretoria, tipoRelatorio,
				httpServletRequest, httpServletResponse, actionMapping);

		// devolve o mapeamento contido na vari�vel retorno
		return retorno;
	}

}
