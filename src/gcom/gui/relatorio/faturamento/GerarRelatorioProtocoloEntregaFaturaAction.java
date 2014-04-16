package gcom.gui.relatorio.faturamento;

import java.util.ArrayList;
import java.util.Collection;

import gcom.cadastro.cliente.Cliente;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.faturamento.RelatorioProtocoloEntregaFatura;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.Util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <p>
 * Title: GCOM
 * </p>
 * <p>
 * Description: Sistema de Gest�o Comercial
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: COMPESA - Companhia Pernambucana de Saneamento
 * </p>
 * 
 * @author Fernanda Paiva
 * @version 1.0
 */

public class GerarRelatorioProtocoloEntregaFaturaAction extends
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
		
		GerarRelatorioFaturasAgrupadasActionForm gerarRelatorioFaturasAgrupadasActionForm = (GerarRelatorioFaturasAgrupadasActionForm) actionForm;
		
		Fachada fachada = Fachada.getInstancia();
		
		Integer anoMes = null;
		
		if (gerarRelatorioFaturasAgrupadasActionForm.getMesAno() != null && !gerarRelatorioFaturasAgrupadasActionForm.getMesAno().trim().equals("")) {
			anoMes = Util.formatarMesAnoComBarraParaAnoMes(gerarRelatorioFaturasAgrupadasActionForm.getMesAno());
		}
		
		Cliente cliente = new Cliente();
		
		if (gerarRelatorioFaturasAgrupadasActionForm.getIdCliente() != null && !gerarRelatorioFaturasAgrupadasActionForm.getIdCliente().trim().equals("")) {
			cliente = fachada.pesquisarClienteDigitado(new Integer(gerarRelatorioFaturasAgrupadasActionForm.getIdCliente()));
			
			if (cliente != null) {
				cliente.setCliente(null);
			} else {
				throw new ActionServletException(
						"atencao.pesquisa_inexistente", null, "Cliente");
			}
		}
		
		Collection<Integer> idsTodosClientes = new ArrayList<Integer>();
		
		if (gerarRelatorioFaturasAgrupadasActionForm.getIdClienteSuperior() != null && !gerarRelatorioFaturasAgrupadasActionForm.getIdClienteSuperior().trim().equals("")) {
		
			Integer idClienteInformado = new Integer(gerarRelatorioFaturasAgrupadasActionForm.getIdClienteSuperior());
			
			Collection<Integer> idsClientes = fachada.pesquisarClientesAssociadosResponsavel(idClienteInformado);
			idsClientes.add(idClienteInformado);
		
			Collection<Integer> idsClientesAdicionados = new ArrayList<Integer>();
		
			while (idsClientes != null && !idsClientes.isEmpty()) {

				idsClientesAdicionados = new ArrayList<Integer>();

				for (Integer idCliente : idsClientes) {

					if (idsTodosClientes != null && !idsTodosClientes.contains(idCliente)) {

						Collection<Integer> idsClientesNovos = fachada
								.pesquisarClientesAssociadosResponsavel(idCliente);

						idsClientesAdicionados.addAll(idsClientesNovos);
						idsTodosClientes.add(idCliente);
					}
				}
			
				idsClientes = idsClientesAdicionados;
			
			}
		}
		
		if (gerarRelatorioFaturasAgrupadasActionForm.getIdEsferaPoder() != null
				&& !gerarRelatorioFaturasAgrupadasActionForm.getIdEsferaPoder()
						.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)) {
			
			String[] clientesSelecionados = gerarRelatorioFaturasAgrupadasActionForm.getIdsClientesAssociados();
			
			for (int i = 0; i < clientesSelecionados.length; i++) {
				String idCliente = clientesSelecionados[i];
				
				if (idCliente != null && !idCliente.trim().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)) {
					idsTodosClientes.add(new Integer(idCliente));
				}
			}
			
		}

		// cria uma inst�ncia da classe do relat�rio
		RelatorioProtocoloEntregaFatura relatorioProtocoloEntregaFatura = new RelatorioProtocoloEntregaFatura(
				(Usuario) (httpServletRequest.getSession(false))
						.getAttribute("usuarioLogado"));
		
		relatorioProtocoloEntregaFatura.addParametro("anoMes", anoMes);
		relatorioProtocoloEntregaFatura.addParametro("cliente", cliente);
		relatorioProtocoloEntregaFatura.addParametro("idsClientes", idsTodosClientes);

		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");
		if (tipoRelatorio == null) {
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		relatorioProtocoloEntregaFatura.addParametro("tipoFormatoRelatorio",
				Integer.parseInt(tipoRelatorio));
		try {
			retorno = processarExibicaoRelatorio(relatorioProtocoloEntregaFatura,
					tipoRelatorio, httpServletRequest, httpServletResponse,
					actionMapping);

		} catch (RelatorioVazioException ex) {
			// manda o erro para a p�gina no request atual
			reportarErros(httpServletRequest, "atencao.relatorio.vazio");

			// seta o mapeamento de retorno para a tela de aten��o de popup
			retorno = actionMapping.findForward("telaAtencaoPopup");

		}

		return retorno;
	}

}
