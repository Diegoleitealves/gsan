package gcom.gui.faturamento;

import gcom.arrecadacao.pagamento.GuiaPagamento;
import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.atendimentopublico.registroatendimento.RegistroAtendimento;
import gcom.atendimentopublico.registroatendimento.bean.ObterDadosRegistroAtendimentoHelper;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.imovel.Imovel;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class InserirGuiaPagamentoAction extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Obt�m a inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();
		
		HttpSession sessao = httpServletRequest.getSession(false);

		InserirGuiaPagamentoActionForm inserirGuiaPagamentoActionForm = (InserirGuiaPagamentoActionForm) actionForm;

		SimpleDateFormat dataFormatada = new SimpleDateFormat("dd/MM/yyyy");

		String idImovel = inserirGuiaPagamentoActionForm.getIdImovel();
		String codigoCliente = inserirGuiaPagamentoActionForm.getCodigoCliente();
		
		String idOrdemServico = inserirGuiaPagamentoActionForm.getOrdemServico();
		String dataVencimento = inserirGuiaPagamentoActionForm.getDataVencimento();
		
		String observacao = inserirGuiaPagamentoActionForm.getObservacao();
		String indicadorEmitirObservacao = inserirGuiaPagamentoActionForm.getIndicadorEmitirObservacao();
		
		if (inserirGuiaPagamentoActionForm.getObservacaoPagamentoParcial() != null &&
			!inserirGuiaPagamentoActionForm.getObservacaoPagamentoParcial().equals("")){
			
			if (observacao != null && !observacao.equals("")){
				
				observacao = "REFER�NCIA DA CONTA: " + inserirGuiaPagamentoActionForm.getObservacaoPagamentoParcial() + " - " +
				observacao;
			}
			else{
				observacao = "REFER�NCIA DA CONTA: " + inserirGuiaPagamentoActionForm.getObservacaoPagamentoParcial();
			}
			
			indicadorEmitirObservacao = ConstantesSistema.SIM.toString();
		}
		
		
        
        GuiaPagamento guiaPagamento = new GuiaPagamento();
        
		Imovel imovel = new Imovel();
		if (idImovel != null && !idImovel.equals("")) {
			imovel.setId(new Integer(idImovel));
		}
		guiaPagamento.setImovel(imovel);

		Cliente cliente = new Cliente();
		if (codigoCliente != null && !codigoCliente.equals("")) {
			cliente.setId(new Integer(codigoCliente));

		}
		guiaPagamento.setCliente(cliente);


		OrdemServico ordemServico = new OrdemServico();

		if (idOrdemServico != null && !idOrdemServico.equals("")) {

			ordemServico = 
				Fachada.getInstancia().recuperaOSPorId(new Integer(idOrdemServico));

			// Verifica se a pesquisa retornou algum objeto para a cole��o
			if (ordemServico != null) {
				Fachada.getInstancia().validarExibirInserirGuiaPagamento(null,ordemServico,
					imovel.getId(),cliente.getId());
				
				RegistroAtendimento ra = ordemServico.getRegistroAtendimento();
				
				inserirGuiaPagamentoActionForm.setRegistroAtendimento(""+ra.getId());
				inserirGuiaPagamentoActionForm.setNomeRegistroAtendimento(
					ra.getSolicitacaoTipoEspecificacao().getDescricao());
				
				if(ordemServico.getServicoTipo().getDebitoTipo() != null){
					
					inserirGuiaPagamentoActionForm.setIdTipoDebito(
						""+ordemServico.getServicoTipo().getDebitoTipo().getId());
					
					inserirGuiaPagamentoActionForm.setDescricaoTipoDebito(
						ordemServico.getServicoTipo().getDebitoTipo().getDescricao());
				}
				
			}else{
				throw new ActionServletException("atencao.naocadastrado", null,"Ordem de Servi�o");
			}

		}
		guiaPagamento.setOrdemServico(ordemServico);
		
		String idRegistroAtendimento = inserirGuiaPagamentoActionForm.getRegistroAtendimento();
		
		RegistroAtendimento registroAtendimento = new RegistroAtendimento();
		if (idRegistroAtendimento != null && !idRegistroAtendimento.equals("")) {
			
			ObterDadosRegistroAtendimentoHelper obterDadosRegistroAtendimentoHelper = 
				Fachada.getInstancia().obterDadosRegistroAtendimento(new Integer(idRegistroAtendimento));
			
			if (obterDadosRegistroAtendimentoHelper.getRegistroAtendimento() != null) {

				registroAtendimento = obterDadosRegistroAtendimentoHelper.getRegistroAtendimento();
				fachada.validarExibirInserirGuiaPagamento(registroAtendimento,null,imovel.getId(),cliente.getId());
				
			}else{
				throw new ActionServletException("atencao.naocadastrado", null,"Registro Atendimento");				
			}
		}
		guiaPagamento.setRegistroAtendimento(registroAtendimento);
		
		Date dataVencimentoFormatada = null;

		try {
			dataVencimentoFormatada = dataFormatada.parse(dataVencimento);
		} catch (ParseException ex) {
			throw new ActionServletException("erro.sistema");
		}

		guiaPagamento.setDataVencimento(dataVencimentoFormatada);

        guiaPagamento.setNumeroPrestacaoTotal(new Short(inserirGuiaPagamentoActionForm.getNumeroTotalPrestacao()));
        
        // Adicionado por Rafael Corr�a em 17/12/2008
        if (observacao != null && !observacao.trim().equals("")) {
        	guiaPagamento.setObservacao(observacao.trim());
        } else {
        	if (indicadorEmitirObservacao != null && indicadorEmitirObservacao.trim().equals(ConstantesSistema.SIM.toString())) {
        		throw new ActionServletException("atencao.campo_selecionado.obrigatorio", null, "Observa��o");
        	}
        }        
        
        guiaPagamento.setIndicadorEmitirObservacao(new Short(indicadorEmitirObservacao));

        /** alterado por pedro alexandre dia 20/11/2006 
         * Recupera o usu�rio logado para passar no met�do de inserir guia de pagamento 
         * para verificar se o usu�rio tem abrang�ncia para inserir a guia de pagamento
         * para o im�vel caso a guia de pagamentoseja para o im�vel.
         */
        Usuario usuarioLogado = (Usuario)sessao.getAttribute(Usuario.USUARIO_LOGADO);
        
        guiaPagamento.setUsuario(usuarioLogado);
        
        Collection colecaoGuiaPagamentoItem = (Collection)sessao.getAttribute("colecaoGuiaDebitoTipo");        
        
        if(colecaoGuiaPagamentoItem == null || colecaoGuiaPagamentoItem.isEmpty()){
        	throw new ActionServletException("atencao.nao.existe.debito.tipo.guia");
        }
        
        String[] idGuiaPagamento = fachada.inserirGuiaPagamento(guiaPagamento,usuarioLogado, 
                new Integer(inserirGuiaPagamentoActionForm.getQtdeDiasVencimento()),colecaoGuiaPagamentoItem, null);
        sessao.setAttribute("idGuiaPagamento",idGuiaPagamento);
        /** fim da altera��o ***************************************************/ 
        
		if (idImovel != null && !idImovel.equals("")) {

//			montarPaginaSucesso(httpServletRequest, "Guia de Pagamento de "
//					+ debitoTipo.getDescricao() + " para o im�vel " + idImovel
//					+ " inserida com sucesso.",
//					"Inserir outra Guia de Pagamento",
//					"exibirInserirGuiaPagamentoAction.do?menu=sim",
//					"exibirManterGuiaPagamentoAction.do?idImovel=" + idImovel,
//					"Cancelar Guia(s) de Pagamento do im�vel " + idImovel);
			
			montarPaginaSucesso(httpServletRequest ,
					"Guia de Pagamento para o im�vel " + idImovel
					+ " inserida com sucesso.", "Inserir outra Guia de Pagamento",
					"exibirInserirGuiaPagamentoAction.do?menu=sim",
					"exibirManterGuiaPagamentoAction.do?idImovel=" + idImovel,
					"Cancelar Guia(s) de Pagamento do im�vel " + idImovel,
					"Imprimir Guia de Pagamento",
					"gerarRelatorioEmitirGuiaPagamentoActionInserir.do");

		} else {
//			montarPaginaSucesso(httpServletRequest, "Guia de Pagamento de "
//					+ debitoTipo.getDescricao() + " para o cliente "
//					+ codigoCliente + " inserida com sucesso.",
//					"Inserir outra Guia de Pagamento",
//					"exibirInserirGuiaPagamentoAction.do?menu=sim",
//					"exibirManterGuiaPagamentoAction.do?idCliente="
//							+ codigoCliente,
//					"Cancelar Guia(s) de Pagamento do cliente " + codigoCliente);

			montarPaginaSucesso(httpServletRequest, "Guia de Pagamento para o cliente "
					+ codigoCliente + " inserida com sucesso.",
					"Inserir outra Guia de Pagamento",
					"exibirInserirGuiaPagamentoAction.do?menu=sim",
					"exibirManterGuiaPagamentoAction.do?idCliente="
							+ codigoCliente,
					"Cancelar Guia(s) de Pagamento do cliente " + codigoCliente,
					"Imprimir Guia de Pagamento",
					"gerarRelatorioEmitirGuiaPagamentoActionInserir.do");
			
		}

		return retorno;
	}
}
