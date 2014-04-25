package gcom.gui.relatorio.atendimentopublico;

import gcom.gui.atendimentopublico.ordemservico.AcompanharRoteiroProgramacaoOrdemServicoActionForm;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.atendimentopublico.RelatorioRoteiroProgramacao;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.Util;

import java.util.Date;
import java.util.HashMap;
import java.util.StringTokenizer;

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
public class GerarRelatorioRoteiroProgramacaoAction extends
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

		AcompanharRoteiroProgramacaoOrdemServicoActionForm acompanharRoteiroProgramacaoOrdemServicoActionForm = 
			(AcompanharRoteiroProgramacaoOrdemServicoActionForm) actionForm;

		String dataRoteiroParametro = acompanharRoteiroProgramacaoOrdemServicoActionForm.getDataRoteiro();
		String chaves = acompanharRoteiroProgramacaoOrdemServicoActionForm.getChavesRelatorio();

		HashMap mapEquipe = (HashMap) sessao.getAttribute("mapEquipe");

		StringTokenizer nomesEquipes = new StringTokenizer(chaves, "$");

		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");

		Date dataRoteiro = Util.converteStringParaDate(dataRoteiroParametro);

		RelatorioRoteiroProgramacao relatorioRoteiroProgramacao = new RelatorioRoteiroProgramacao(
				(Usuario)(httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));
		relatorioRoteiroProgramacao.addParametro("dataRoteiro", dataRoteiro);
		relatorioRoteiroProgramacao.addParametro("mapEquipe", mapEquipe);
		relatorioRoteiroProgramacao.addParametro("nomesEquipes", nomesEquipes);

		if (tipoRelatorio == null) {
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		relatorioRoteiroProgramacao.addParametro("tipoFormatoRelatorio",
				Integer.parseInt(tipoRelatorio));
		retorno = processarExibicaoRelatorio(relatorioRoteiroProgramacao,
				tipoRelatorio, httpServletRequest, httpServletResponse,
				actionMapping);

		// devolve o mapeamento contido na vari�vel retorno
		return retorno;
	}

}
