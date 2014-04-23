package gcom.gui.atendimentopublico.ordemservico;

import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.atendimentopublico.ordemservico.ServicoTipo;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.Util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <<Descri��o da Classe>>
 * 
 * @author lms
 * @date 22/08/2006
 */
public class AtualizarOrdemServicoAction extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		ActionForward retorno = actionMapping.findForward("telaSucesso");
		AtualizarOrdemServicoActionForm form = (AtualizarOrdemServicoActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);
		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

		OrdemServico ordemServico = form.setFormValues(form.getOrdemServico());

		// pesquisa do enter servi�o tipo
		String idServicoTipo = form.getIdServicoTipo();
		String descricaoServicoTipo = form.getDescricaoServicoTipo();
		
		if (idServicoTipo != null
				&& !idServicoTipo.equals("")
				&& (descricaoServicoTipo == null || descricaoServicoTipo
						.equals(""))) {
			Integer idServicoTipoInteger = Util.converterStringParaInteger(form
					.getIdServicoTipo());

			ServicoTipo servicoTipo = fachada
					.pesquisarSevicoTipo(idServicoTipoInteger);

			if (servicoTipo != null && !servicoTipo.equals("")) {
				ordemServico.setServicoTipo(servicoTipo);

			} else {
				throw new ActionServletException(
						"atencao.pesquisa_inexistente", null, "Tipo Servi�o");
			}
		}
		
		if(ordemServico.getObservacao() != null && 
			!ordemServico.getObservacao().equals("") && 
			ordemServico.getObservacao().length() > 200){
					
			String[] msg = new String[2];
			msg[0]="Observa��o";
			msg[1]="200";
				
			throw new ActionServletException("atencao.execedeu_limit_observacao",null,msg);
		}					

		// pesquisa do enter Ordem de Servi�o Refer�ncia
		String idOSReferencia = form.getIdOrdemServicoReferencia();
		String descricaoOsReferencia = form
				.getDescricaoOrdemServicoReferencia();
		if (idOSReferencia != null
				&& !idOSReferencia.equals("")
				&& (descricaoOsReferencia == null || descricaoOsReferencia
						.equals(""))) {
			Integer idOrdemServicoReferencia = Util
					.converterStringParaInteger(form
							.getIdOrdemServicoReferencia());

			OrdemServico os = fachada
					.pesquisarOrdemServico(idOrdemServicoReferencia);

			if (os != null && !os.equals("")) {
				ordemServico.setOsReferencia(os);
			} else {
				throw new ActionServletException(
						"atencao.pesquisa_inexistente", null,
						"Ordem Servi�o Refer�ncia");

			}
		}

		// pesquisa do enter Servi�o Tipo Refer�ncia
		String idServicoTipoReferencia = form.getIdServicoTipoReferencia();
		String descricaoServicoTipoReferencia = form
				.getDescricaoServicoTipoReferencia();
		if (idServicoTipoReferencia != null
				&& !idServicoTipoReferencia.equals("")
				&& (descricaoServicoTipoReferencia == null || descricaoServicoTipoReferencia
						.equals(""))) {

			Integer idTipoServicoReferenciaInteger = Util
					.converterStringParaInteger(form
							.getIdServicoTipoReferencia());

			ServicoTipo st = fachada
					.pesquisarSevicoTipo(idTipoServicoReferenciaInteger);
			if (st != null && !st.equals("")) {
				ordemServico.setServicoTipoReferencia(st);
			} else {
				throw new ActionServletException(
						"atencao.pesquisa_inexistente", null,
						"Servi�o Tipo Refer�ncia");
			}
		}

		String veioAcompanhamentoRoteiro = (String) sessao
				.getAttribute("veioAcompanhamentoRoteiro");		
		
		if (veioAcompanhamentoRoteiro != null && !veioAcompanhamentoRoteiro.equals("")){
			if (ordemServico.getServicoTipo() != null &&
					ordemServico.getServicoTipo().getIndicadorProgramacaoAutomatica() != ConstantesSistema.SIM.shortValue()){
				throw new ActionServletException("atencao.servico_tipo.nao_compativel.atualizar_os");
			}
		}
		
		// gera a ordem de servi�o
		fachada.atualizarOrdemServico(ordemServico, usuarioLogado);

		if (sessao.getAttribute("ehPopup") == null
				|| sessao.getAttribute("ehPopup").equals("")) {
			// Exibe a p�gina de sucesso
			String mensagem = "";
			if(ordemServico.getRegistroAtendimento() != null && ordemServico.getRegistroAtendimento().getId() != null ){
				mensagem = "Ordem de Servi�o "
					+ ordemServico.getId()
					+ " para o registro de Atendimento n�mero "
					+ ordemServico.getRegistroAtendimento().getId()
					+ " atualizada com sucesso.";
			}else if (ordemServico.getCobrancaDocumento() != null && ordemServico.getCobrancaDocumento().getId() != null ){
				mensagem = "Ordem de Servi�o "
					+ ordemServico.getId()
					+ " para o Documento de Cobran�a n�mero "
					+ ordemServico.getCobrancaDocumento().getId()
					+ " atualizada com sucesso.";
			} else {
				mensagem = "Ordem de Servi�o "
					+ ordemServico.getId()
					+ " atualizada com sucesso.";
			}
			
			if (sessao.getAttribute("importarMovimentoACQUAGIS") != null &&
					sessao.getAttribute("importarMovimentoACQUAGIS").equals("sim")) {
				
				montarPaginaSucesso(httpServletRequest,  mensagem,
						"Voltar",
						sessao.getAttribute("caminhoRetornoOS").toString());
				
			}else {
				montarPaginaSucesso(httpServletRequest,  mensagem, "Realizar outra Manuten��o de Ordem de Servi�o",
						"exibirFiltrarOrdemServicoAction.do?menu=sim",
						"exibirConsultarDadosOrdemServicoAction.do?menu=sim&numeroOS=" + ordemServico.getId(), 
						"Voltar");
			}
			
		} else {
			// volta para tela e limpa o popup
			httpServletRequest.setAttribute("fecharPopup", "SIM");
			retorno = actionMapping.findForward("exibirAtualizarOrdemServico");
		}
		
		String dataProgramacao = (String) sessao.getAttribute("dataProgramacaoAtualizar");
		String chaveArquivo = (String) sessao.getAttribute("chaveArquivoAtualizar");
		
		if (veioAcompanhamentoRoteiro != null && !veioAcompanhamentoRoteiro.equals("")){
			fachada.atualizarOSProgramacaoAcompServico(Util.converterStringParaInteger(chaveArquivo), Util.converteStringParaDate(dataProgramacao), ordemServico.getId(),
					ordemServico.getServicoTipo().getId());
		}
		
		sessao.removeAttribute("dataProgramacaoAtualizar");
		sessao.removeAttribute("chaveArquivoAtualizar");
		
		sessao.removeAttribute("ehPopup");
		sessao.removeAttribute("servicoTipo");
		sessao.removeAttribute("colecaoServicoTipoPrioridade");
		sessao.removeAttribute("colecaoServicosTipo");
		
		return retorno;
	}
}
