package gcom.gui.relatorio.gerencial;

import gcom.gerencial.bean.InformarDadosGeracaoRelatorioConsultaHelper;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.gerencial.micromedicao.RelatorioResumoAnormalidadeLeitura;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.SistemaException;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Descri��o da classe 
 *
 * @author Pedro Alexandre
 * @date 07/06/2006
 */
public class GerarRelatorioConsultarComparativoResumosFaturamentoArrecadacaoPendenciaAction extends
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

		//ResumoAnormalidadeActionForm resumoAnormalidadeActionForm = (ResumoAnormalidadeActionForm) actionForm;

		Collection colecaoResumosAnormalidadeLeitura = (Collection) sessao
				.getAttribute("colecaoResumoAnormalidadeRelatorio");
		InformarDadosGeracaoRelatorioConsultaHelper informarDadosGeracaoRelatorioConsultaHelper = 
				(InformarDadosGeracaoRelatorioConsultaHelper)sessao.getAttribute("informarDadosGeracaoRelatorioConsultaHelper");

		// Inicio da parte que vai mandar os parametros para o relat�rio

		// Fim da parte que vai mandar os parametros para o relat�rio

		byte[] relatorioRetorno = null;

		OutputStream out = null;

		try {

			RelatorioResumoAnormalidadeLeitura relatorioResumoAnormalidadeLeitura = new RelatorioResumoAnormalidadeLeitura(
					(Usuario)(httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));
			relatorioResumoAnormalidadeLeitura.addParametro(
					"opcaoTotalizacao",
					informarDadosGeracaoRelatorioConsultaHelper.getOpcaoTotalizacao());
			
			relatorioResumoAnormalidadeLeitura.addParametro(
						"colecaoResumosAnormalidadeLeitura",
						colecaoResumosAnormalidadeLeitura);

			// chama o met�do de gerar relat�rio passando o c�digo da analise
			// como par�metro
			relatorioRetorno = (byte[]) relatorioResumoAnormalidadeLeitura
					.executar();

			if (retorno == null) {
				// prepara a resposta para o popup
				httpServletResponse.setContentType("application/pdf");
				out = httpServletResponse.getOutputStream();
				out.write(relatorioRetorno);
				out.flush();
				out.close();
			}
		} catch (IOException ex) {
			// manda o erro para a p�gina no request atual
			reportarErros(httpServletRequest, "erro.sistema");

			// seta o mapeamento de retorno para a tela de erro de popup
			retorno = actionMapping.findForward("telaErroPopup");

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
