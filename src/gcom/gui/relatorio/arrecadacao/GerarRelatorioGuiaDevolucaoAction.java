package gcom.gui.relatorio.arrecadacao;

import gcom.gui.arrecadacao.ManterGuiaDevolucaoActionForm;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.arrecadacao.RelatorioGuiaDevolucao;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.SistemaException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * action respons�vel pela exibi��o do relat�rio guia de devolu��o
 * 
 * @author Ana Maria
 * @date 06/10/2006
 */
public class GerarRelatorioGuiaDevolucaoAction extends
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
		//HttpSession sessao = httpServletRequest.getSession(false);
		
		//Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");
		
		// cria uma inst�ncia da classe do relat�rio
		RelatorioGuiaDevolucao relatorioGuiaDevolucao = new RelatorioGuiaDevolucao((Usuario)(httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));
		
		 String[] ids = null;
		 
		 if (actionForm instanceof ManterGuiaDevolucaoActionForm) {
			 //tela de Manter Guia de Devolu��o
			 ManterGuiaDevolucaoActionForm manterGuiaDevolucaoActionForm = (ManterGuiaDevolucaoActionForm) actionForm;
			 ids = manterGuiaDevolucaoActionForm.getIdRegistrosRemocao();
		 }else{
			 //tela de Inserir Guia de Devolu��o
			 String idGuiaDevolucao = (String) httpServletRequest.getParameter("idGuiaDevolucao");
        	 ids = new String[1];
        	 ids[0] = idGuiaDevolucao; 
		 }
		
		relatorioGuiaDevolucao.addParametro("idsGuiaDevolucao",ids);
		//relatorioGuiaDevolucao.addParametro("usuarioLogado",usuarioLogado);

		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");
		if (tipoRelatorio == null) {
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		relatorioGuiaDevolucao.addParametro("tipoFormatoRelatorio",
				Integer.parseInt(tipoRelatorio));
		try {
			retorno = processarExibicaoRelatorio(relatorioGuiaDevolucao,
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
