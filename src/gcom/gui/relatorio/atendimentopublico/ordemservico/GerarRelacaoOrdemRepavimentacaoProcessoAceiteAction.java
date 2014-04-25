package gcom.gui.relatorio.atendimentopublico.ordemservico;

import gcom.atendimentopublico.ordemservico.bean.OrdemRepavimentacaoProcessoAceiteHelper;
import gcom.gui.atendimentopublico.ordemservico.FiltrarOrdemRepavimentacaoProcessoAceiteActionForm;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.atendimentopublico.ordemservico.RelatorioRelacaoOrdemRepavimentacaoProcessoAceite;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.SistemaException;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action respons�vel pela exibi��o da Relacao de Ordem de Repavimentacao em Processo de Aceite.
 * 
 * @author Hugo Leonardo
 * @created 20/05/2010
 */
public class GerarRelacaoOrdemRepavimentacaoProcessoAceiteAction extends
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
		
		FiltrarOrdemRepavimentacaoProcessoAceiteActionForm form = (FiltrarOrdemRepavimentacaoProcessoAceiteActionForm) actionForm;
		
		Collection parametros = (Collection) sessao.getAttribute("collOrdemServicoPavimentoAceite");
		// seta os parametros que ser�o mostrados no relat�rio
		OrdemRepavimentacaoProcessoAceiteHelper osPavimentoAceiteHelper = (OrdemRepavimentacaoProcessoAceiteHelper) sessao.getAttribute("osPavimentoAceiteHelper");	

		// cria uma inst�ncia da classe do relat�rio
		RelatorioRelacaoOrdemRepavimentacaoProcessoAceite relatorio = new RelatorioRelacaoOrdemRepavimentacaoProcessoAceite(
					(Usuario)(httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));

		relatorio.addParametro("parametros",parametros);
		relatorio.addParametro("osPavimentoAceiteHelper",osPavimentoAceiteHelper);
		relatorio.addParametro("periodoAceiteServicoInicial", form.getPeriodoAceiteServicoInicial());
		relatorio.addParametro("periodoAceiteServicoFinal", form.getPeriodoAceiteServicoFinal());
		
		relatorio.addParametro("retornoServicoInicial", form.getPeriodoRetornoServicoInicial());
		relatorio.addParametro("retornoServicoFinal", form.getPeriodoRetornoServicoFinal());
		
		relatorio.addParametro("situacaoAceiteDescricao", form.getSituacaoAceiteDescricao());
		
		relatorio.addParametro("descricaoUnidadeOrganizacional", form.getDescricaoUnidadeOrganizacional());
		
		// chama o met�do de gerar relat�rio passando o c�digo da analise
		// como par�metro
		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");
		if (tipoRelatorio == null) {
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

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
