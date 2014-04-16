package gcom.gui.relatorio.atendimentopublico;

import java.util.Collection;

import gcom.cadastro.sistemaparametro.FiltroSistemaParametro;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.gui.atendimentopublico.registroatendimento.ConsultarRegistroAtendimentoActionForm;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.atendimentopublico.RelatorioConsultarRegistroAtendimentoViaCliente;
import gcom.relatorio.atendimentopublico.RelatorioConsultarRegistroAtendimentoViaClienteCosanpa;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;

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
public class GerarRelatorioRegistroAtendimentoViaClienteConsultarAction extends
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
		
		HttpSession sessao = httpServletRequest.getSession(false);
		
		Fachada fachada = Fachada.getInstancia();

		ConsultarRegistroAtendimentoActionForm consultarRegistroAtendimentoActionForm = (ConsultarRegistroAtendimentoActionForm) actionForm;

		String idRegistroAtendimento = "";

		if (sessao.getAttribute("idRegistroAtendimento") != null) {
			idRegistroAtendimento = (String) sessao
					.getAttribute("idRegistroAtendimento");
		} else {
			idRegistroAtendimento = consultarRegistroAtendimentoActionForm.getNumeroRAPesquisado();
		}

		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");

		FiltroSistemaParametro filtroSistemaParametro = new FiltroSistemaParametro();
		Collection colecaoSistemaParametro = fachada.pesquisar(filtroSistemaParametro, SistemaParametro.class.getName());
		SistemaParametro sistemaParametro = (SistemaParametro) colecaoSistemaParametro.iterator().next();
					
		String nomeEmpresa = sistemaParametro.getNomeAbreviadoEmpresa();
		
		/*
		 * @autor Hugo Leoanrdo
		 * @date 08/04/2010
		 * 
		 * CRC_3205 - Altera��o de Layout de PROTOCOLO DE ATENDIMENTO VIA CLIENTE
		 * 			  especifico para a COSANPA. 
		*/
		if(nomeEmpresa.equals("COSANPA")){
		
			RelatorioConsultarRegistroAtendimentoViaClienteCosanpa relatorioConsultarRegistroAtendimentoViaClienteCosanpa = 
				new RelatorioConsultarRegistroAtendimentoViaClienteCosanpa(
					(Usuario)(httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));
			
			relatorioConsultarRegistroAtendimentoViaClienteCosanpa.addParametro(
					"idRegistroAtendimento", new Integer(idRegistroAtendimento));
			if (tipoRelatorio == null) {
				tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
			}
	
			relatorioConsultarRegistroAtendimentoViaClienteCosanpa.addParametro(
					"tipoFormatoRelatorio", Integer.parseInt(tipoRelatorio));
			
			relatorioConsultarRegistroAtendimentoViaClienteCosanpa.addParametro("funcionario", consultarRegistroAtendimentoActionForm.getUsuario());
			
			retorno = processarExibicaoRelatorio(
					relatorioConsultarRegistroAtendimentoViaClienteCosanpa, tipoRelatorio,
					httpServletRequest, httpServletResponse, actionMapping);
	
			// devolve o mapeamento contido na vari�vel retorno
			return retorno;
		}else{
			RelatorioConsultarRegistroAtendimentoViaCliente relatorioConsultarRegistroAtendimentoViaCliente = new RelatorioConsultarRegistroAtendimentoViaCliente(
					(Usuario)(httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));
			relatorioConsultarRegistroAtendimentoViaCliente.addParametro(
					"idRegistroAtendimento", new Integer(idRegistroAtendimento));
			if (tipoRelatorio == null) {
				tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
			}
	
			relatorioConsultarRegistroAtendimentoViaCliente.addParametro(
					"tipoFormatoRelatorio", Integer.parseInt(tipoRelatorio));
			retorno = processarExibicaoRelatorio(
					relatorioConsultarRegistroAtendimentoViaCliente, tipoRelatorio,
					httpServletRequest, httpServletResponse, actionMapping);
	
			// devolve o mapeamento contido na vari�vel retorno
			return retorno;
		}
	}

}
