package gcom.gui.relatorio.faturamento;

import gcom.faturamento.FaturamentoAtividadeCronograma;
import gcom.faturamento.FaturamentoGrupo;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.faturamento.RelatorioPosicaoFaturamento;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;

import java.util.Collection;
import java.util.Map;

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
public class GerarRelatorioPosicaoFaturamentoAction extends
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
		
		Map<FaturamentoGrupo, Collection<FaturamentoAtividadeCronograma>> map = (Map<FaturamentoGrupo, Collection<FaturamentoAtividadeCronograma>>) sessao.getAttribute("mapPagina");

		// Fim da parte que vai mandar os parametros para o relat�rio
		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");

		RelatorioPosicaoFaturamento relatorioPosicaoFaturamento = new RelatorioPosicaoFaturamento((Usuario)(httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));
		relatorioPosicaoFaturamento.addParametro("map",
				map);
		
		if (tipoRelatorio == null) {
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		relatorioPosicaoFaturamento.addParametro("tipoFormatoRelatorio", Integer
				.parseInt(tipoRelatorio));
		retorno = processarExibicaoRelatorio(relatorioPosicaoFaturamento, tipoRelatorio,
				httpServletRequest, httpServletResponse, actionMapping);

		// devolve o mapeamento contido na vari�vel retorno
		return retorno;
		
	}

}
