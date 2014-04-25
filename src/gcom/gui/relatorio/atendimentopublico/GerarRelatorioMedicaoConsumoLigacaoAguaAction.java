package gcom.gui.relatorio.atendimentopublico;

import gcom.cadastro.cliente.ClienteImovel;
import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.gui.cadastro.imovel.ConsultarImovelActionForm;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.atendimentopublico.RelatorioMedicaoConsumoLigacaoAgua;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.SistemaException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * action respons�vel pela exibi��o do relat�rio guia de devolu��o
 * 
 * @author Ana Maria
 * @date 13/02/2007
 */
public class GerarRelatorioMedicaoConsumoLigacaoAguaAction extends
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

		// cria uma inst�ncia da classe do relat�rio
		RelatorioMedicaoConsumoLigacaoAgua relatorioMedicaoConsumoLigacaoAgua = new RelatorioMedicaoConsumoLigacaoAgua((Usuario)(httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));
		
		ConsultarImovelActionForm consultarImovelActionForm = (ConsultarImovelActionForm) actionForm;
		
		// Obtendo uma instancia da sessao
		HttpSession sessao = httpServletRequest.getSession(false);
		
		Collection colecaoMedicaoHistorico = new ArrayList();
		Collection colecaoimoveisMicromedicao = new ArrayList();
		Collection imovelClientes = new ArrayList();
		 
		 if (sessao.getAttribute("medicoesHistoricos") != null || sessao.getAttribute("imoveisMicromedicao") != null) {
			colecaoMedicaoHistorico = (Collection)sessao.getAttribute("medicoesHistoricos");
			colecaoimoveisMicromedicao = (Collection)sessao.getAttribute("imoveisMicromedicao");
		 }
		
		 String clienteUsuario = "";
		 if(sessao.getAttribute("imovelClientes") != null && !sessao.getAttribute("imovelClientes").equals("")){
			 imovelClientes = (Collection)sessao.getAttribute("imovelClientes"); 
			 Iterator iteratorImovelCliete = imovelClientes.iterator();
			 while (iteratorImovelCliete.hasNext()) {
				ClienteImovel imovelCliente = (ClienteImovel) iteratorImovelCliete.next();
				if(imovelCliente.getClienteRelacaoTipo().getId().equals(new Integer(ClienteRelacaoTipo.USUARIO))){
					clienteUsuario = imovelCliente.getCliente().getNome();
				}				
			}
		 }
		 relatorioMedicaoConsumoLigacaoAgua.addParametro("colecaoMedicaoHistorico", colecaoMedicaoHistorico);
		 relatorioMedicaoConsumoLigacaoAgua.addParametro("colecaoimoveisMicromedicao", colecaoimoveisMicromedicao);
		 relatorioMedicaoConsumoLigacaoAgua.addParametro("matricula", consultarImovelActionForm.getMatriculaImovelAnaliseMedicaoConsumo());
		 relatorioMedicaoConsumoLigacaoAgua.addParametro("sitLigacaoAgua", consultarImovelActionForm.getSituacaoAguaAnaliseMedicaoConsumo());
		 relatorioMedicaoConsumoLigacaoAgua.addParametro("sitLigacaoEsgoto", consultarImovelActionForm.getSituacaoEsgotoAnaliseMedicaoConsumo());
		 relatorioMedicaoConsumoLigacaoAgua.addParametro("endereco", consultarImovelActionForm.getEnderecoImovelDadosCadastrais());
		 relatorioMedicaoConsumoLigacaoAgua.addParametro("clienteUsuario", clienteUsuario);

		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");
		
		if (tipoRelatorio == null) {
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		relatorioMedicaoConsumoLigacaoAgua.addParametro("tipoFormatoRelatorio",
				Integer.parseInt(tipoRelatorio));
		try {
			retorno = processarExibicaoRelatorio(relatorioMedicaoConsumoLigacaoAgua,
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
