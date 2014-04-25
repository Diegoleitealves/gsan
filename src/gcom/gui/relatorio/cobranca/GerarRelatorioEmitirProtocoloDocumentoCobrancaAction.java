package gcom.gui.relatorio.cobranca;

import gcom.cobranca.CobrancaAcaoAtividadeCronograma;
import gcom.fachada.Fachada;
import gcom.gui.cobranca.ExibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.cobranca.RelatorioEmitirProtocoloDocumentoCobranca;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.SistemaException;
import gcom.util.Util;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * action respons�vel pela exibi��o do relat�rio 
 * [UC0580]Emitir Protocolo de Documento de Cobran�a do Cronogrma
 * 
 * @author Ana Maria
 * @date 15/05/2007
 */
public class GerarRelatorioEmitirProtocoloDocumentoCobrancaAction extends
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

		Fachada fachada =  Fachada.getInstancia();
		
		 HttpSession sessao = httpServletRequest.getSession(false);    
		
		// cria uma inst�ncia da classe do relat�rio
		RelatorioEmitirProtocoloDocumentoCobranca relatorioEmitirProtocoloDocumentoCobranca = new RelatorioEmitirProtocoloDocumentoCobranca((Usuario)(httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));		
		
		Collection protocoloDocumentoCobranca = null;

		if(sessao.getAttribute("cobrancaAcaoAtividadeCronograma")!= null){
			CobrancaAcaoAtividadeCronograma cobrancaAcaoAtividadeCronograma = (CobrancaAcaoAtividadeCronograma)sessao.getAttribute("cobrancaAcaoAtividadeCronograma");
			Integer idCobrancaAcaoAtividadeCronograma = cobrancaAcaoAtividadeCronograma.getId();
			protocoloDocumentoCobranca = fachada.pesquisarProtocoloDocumentoCobrancaCronograma(idCobrancaAcaoAtividadeCronograma);
			String primeiroTitulo = "COBRAN�A: "+ Util.formatarAnoMesParaMesAno(cobrancaAcaoAtividadeCronograma.getCobrancaAcaoCronograma().getCobrancaGrupoCronogramaMes().getAnoMesReferencia())+"    "+
									"DATA DA REALIZA��O: "+ Util.formatarData(cobrancaAcaoAtividadeCronograma.getRealizacao())+"    "+
									"HORA DA REALIZA��O: "+ Util.formatarHoraSemData(cobrancaAcaoAtividadeCronograma.getRealizacao());
			relatorioEmitirProtocoloDocumentoCobranca.addParametro("grupo", cobrancaAcaoAtividadeCronograma.getCobrancaAcaoCronograma().getCobrancaGrupoCronogramaMes().getCobrancaGrupo().getDescricao());
			relatorioEmitirProtocoloDocumentoCobranca.addParametro("primeiroTitulo",primeiroTitulo);
			relatorioEmitirProtocoloDocumentoCobranca.addParametro("acaoCobranca", "RESUMO DAS ORDENS DE COBRAN�A EMITIDAS "+cobrancaAcaoAtividadeCronograma.getCobrancaAcaoCronograma().getCobrancaAcao().getDescricaoCobrancaAcao());
			relatorioEmitirProtocoloDocumentoCobranca.addParametro("R0000","R0580");
		}else{
			ExibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm form = (ExibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm)actionForm;
			Integer idCobrancaAcaoAtividadeComand = new Integer(form.getIdCobrancaAcaoAtividadeComando());
			protocoloDocumentoCobranca = fachada.pesquisarProtocoloDocumentoCobrancaEventual(idCobrancaAcaoAtividadeComand);
			relatorioEmitirProtocoloDocumentoCobranca.addParametro("grupo", null);
			String primeiroTitulo = "DATA DA REALIZA��O: "+ form.getDataRealizacao()+"    "+
									"HORA DA REALIZA��O: "+ form.getHoraRealizacao();
			relatorioEmitirProtocoloDocumentoCobranca.addParametro("primeiroTitulo",primeiroTitulo);
			relatorioEmitirProtocoloDocumentoCobranca.addParametro("acaoCobranca", "RESUMO DAS ORDENS DE COBRAN�A EMITIDAS "+form.getAcaoCobranca()+ " - EVENTUAL");
			relatorioEmitirProtocoloDocumentoCobranca.addParametro("R0000","R0581");
		}
		
		relatorioEmitirProtocoloDocumentoCobranca.addParametro("protocoloDocumentoCobranca",protocoloDocumentoCobranca);
		
		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");
		if (tipoRelatorio == null) {
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		relatorioEmitirProtocoloDocumentoCobranca.addParametro("tipoFormatoRelatorio",Integer.parseInt(tipoRelatorio));
		
		try {
			
			retorno = processarExibicaoRelatorio(relatorioEmitirProtocoloDocumentoCobranca,
					tipoRelatorio, httpServletRequest, httpServletResponse, actionMapping);
			
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
