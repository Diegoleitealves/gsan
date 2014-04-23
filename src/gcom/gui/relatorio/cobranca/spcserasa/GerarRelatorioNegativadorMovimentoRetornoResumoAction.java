package gcom.gui.relatorio.cobranca.spcserasa;

import gcom.cobranca.bean.NegativadorMovimentoRetornoResumoHelper;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.cobranca.spcserasa.RelatorioNegativadorMovimentoRetornoResumo;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.SistemaException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * action respons�vel pela exibi��o do relat�rio de Tipo do Registro do Negativador manter
 * 
 * @author Yara Taciane
 * @created 26 de Fevereiro de 2008
 */
public class GerarRelatorioNegativadorMovimentoRetornoResumoAction extends
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

		// Inicio da parte que vai mandar os parametros para o relat�rio
		NegativadorMovimentoRetornoResumoHelper parametros = new NegativadorMovimentoRetornoResumoHelper();
		
			// cria uma inst�ncia da classe do relat�rio
		  RelatorioNegativadorMovimentoRetornoResumo relatorio = new RelatorioNegativadorMovimentoRetornoResumo(
					(Usuario)(httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));
			
	         String   nomeNegativador = (String)sessao.getAttribute("nomeNegativador");	       
	         String   dataProcessamento = (String)sessao.getAttribute("dataProcessamento");
	         String   horaProcessamento = (String)sessao.getAttribute("horaProcessamento");
	         String   numeroSequencialArquivo = (String)sessao.getAttribute("numeroSequencialArquivo");
	         String   numeroRegistros = (String)sessao.getAttribute("numeroRegistros");	         
	         
	         parametros.setNomeNegativador(nomeNegativador);
	         parametros.setDataProcessamento(dataProcessamento);
	         parametros.setHoraProcessamento(horaProcessamento);
	         parametros.setNumeroSequencialArquivo(numeroSequencialArquivo);
	         parametros.setNumeroRegistros(numeroRegistros);
			
			// chama o met�do de gerar relat�rio passando o c�digo da analise
			// como par�metro
			String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");
			if (tipoRelatorio == null) {
				tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
			}
			
			   relatorio.addParametro("parametros",parametros);
			   relatorio.addParametro("tipoFormatoRelatorio", Integer.parseInt(tipoRelatorio));
			try {
				retorno = processarExibicaoRelatorio(relatorio,
						tipoRelatorio, httpServletRequest, httpServletResponse,
						actionMapping);

		} catch (SistemaException ex) {
			// manda o erro para a p�gina no request atual
			reportarErros(httpServletRequest, "erro.sistema");

			// seta o mapeamento de retorno para a tela de erro de popup
			retorno = actionMapping.findForward("telaErroPopup");

		} catch (RelatorioVazioException ex1) {
			// manda o erro para a p�gina no request atual
			reportarErros(httpServletRequest, "erro.relatorio.vazio");

			// seta o mapeamento de retorno para a tela de aten��o de popup
			retorno = actionMapping.findForward("telaAtencaoPopup");
		}

		// devolve o mapeamento contido na vari�vel retorno
		return retorno;
	}



}
