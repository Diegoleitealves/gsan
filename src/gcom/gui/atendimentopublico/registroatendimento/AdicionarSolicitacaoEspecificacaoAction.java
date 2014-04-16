package gcom.gui.atendimentopublico.registroatendimento;

import gcom.atendimentopublico.ordemservico.FiltroServicoTipo;
import gcom.atendimentopublico.ordemservico.ServicoTipo;
import gcom.atendimentopublico.registroatendimento.EspecificacaoImovelSituacao;
import gcom.atendimentopublico.registroatendimento.SolicitacaoTipo;
import gcom.atendimentopublico.registroatendimento.SolicitacaoTipoEspecificacao;
import gcom.cadastro.unidade.FiltroUnidadeOrganizacional;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.fachada.Fachada;
import gcom.faturamento.debito.DebitoTipo;
import gcom.faturamento.debito.FiltroDebitoTipo;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action respons�vel pela pre-exibi��o da pagina de inserir bairro
 * 
 * @author S�vio Luiz
 * @created 28 de Julho de 2006
 */
public class AdicionarSolicitacaoEspecificacaoAction extends GcomAction {
	/**
	 * Description of the Method
	 * 
	 * @param actionMapping
	 *            Description of the Parameter
	 * @param actionForm
	 *            Description of the Parameter
	 * @param httpServletRequest
	 *            Description of the Parameter
	 * @param httpServletResponse
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		// Set no mapeamento de retorno
		ActionForward retorno = actionMapping
				.findForward("adicionarSolicitacaoEspecificacao");

		Collection colecaoSolicitacaoTipoEspecificacao = null;
		if (sessao.getAttribute("colecaoSolicitacaoTipoEspecificacao") == null) {
			colecaoSolicitacaoTipoEspecificacao = new ArrayList();
		} else {
			colecaoSolicitacaoTipoEspecificacao = (Collection) sessao
					.getAttribute("colecaoSolicitacaoTipoEspecificacao");
		}

		AdicionarSolicitacaoEspecificacaoActionForm adicionarSolicitacaoEspecificacaoActionForm = (AdicionarSolicitacaoEspecificacaoActionForm) actionForm;

		// Obt�m a inst�ncia da Fachada
		Fachada fachada = Fachada.getInstancia();

		// seta os campos do form no objeto SolicitacaoTipoEspecificacao
		SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao = new SolicitacaoTipoEspecificacao();

		solicitacaoTipoEspecificacao.setIndicadorSolicitante(new Short("1"));

		if (adicionarSolicitacaoEspecificacaoActionForm
				.getPrazoPrevisaoAtendimento() != null
				&& !adicionarSolicitacaoEspecificacaoActionForm
						.getPrazoPrevisaoAtendimento().equals("")) {
			// Prazo de previs�o de atendimento
			solicitacaoTipoEspecificacao.setDiasPrazo(new Integer(
					adicionarSolicitacaoEspecificacaoActionForm
							.getPrazoPrevisaoAtendimento()));
		}
		// Descri��o da especifica��o
		solicitacaoTipoEspecificacao
				.setDescricao(adicionarSolicitacaoEspecificacaoActionForm
						.getDescricaoSolicitacao());

		// Pavimento de cal�ada obrigat�rio
		if (adicionarSolicitacaoEspecificacaoActionForm
				.getIndicadorPavimentoCalcada() != null
				&& !adicionarSolicitacaoEspecificacaoActionForm
						.getIndicadorPavimentoCalcada().equals("")) {
			solicitacaoTipoEspecificacao
					.setIndicadorPavimentoCalcada(new Short(
							adicionarSolicitacaoEspecificacaoActionForm
									.getIndicadorPavimentoCalcada()));
		}
		// Pavimento de rua obrigat�rio
		if (adicionarSolicitacaoEspecificacaoActionForm
				.getIndicadorPavimentoRua() != null
				&& !adicionarSolicitacaoEspecificacaoActionForm
						.getIndicadorPavimentoRua().equals("")) {
			solicitacaoTipoEspecificacao.setIndicadorPavimentoRua(new Short(
					adicionarSolicitacaoEspecificacaoActionForm
							.getIndicadorPavimentoRua()));
		}

		// refere-se a liga��o de agua
		if (adicionarSolicitacaoEspecificacaoActionForm
				.getIndicadorLigacaoAgua() != null
				&& !adicionarSolicitacaoEspecificacaoActionForm
						.getIndicadorLigacaoAgua().equals("")) {
			solicitacaoTipoEspecificacao.setIndicadorLigacaoAgua(new Short(
					adicionarSolicitacaoEspecificacaoActionForm
							.getIndicadorLigacaoAgua()));
		}
		// Cobran�a de material
		if (adicionarSolicitacaoEspecificacaoActionForm
				.getIndicadorCobrancaMaterial() != null
				&& !adicionarSolicitacaoEspecificacaoActionForm
						.getIndicadorCobrancaMaterial().equals("")) {
			solicitacaoTipoEspecificacao
					.setIndicadorCobrancaMaterial(new Integer(
							adicionarSolicitacaoEspecificacaoActionForm
									.getIndicadorCobrancaMaterial()));
		}
		// Matricula do im�vel obrigat�rio
		if (adicionarSolicitacaoEspecificacaoActionForm
				.getIndicadorMatriculaImovel() != null
				&& !adicionarSolicitacaoEspecificacaoActionForm
						.getIndicadorMatriculaImovel().equals("")) {
			solicitacaoTipoEspecificacao.setIndicadorMatricula(new Integer(
					adicionarSolicitacaoEspecificacaoActionForm
							.getIndicadorMatriculaImovel()));
		}

		// Indicador urgencia
		if (adicionarSolicitacaoEspecificacaoActionForm.getIndicadorUrgencia() != null
				&& !adicionarSolicitacaoEspecificacaoActionForm
						.getIndicadorUrgencia().equals("")) {
			solicitacaoTipoEspecificacao.setIndicadorUrgencia(new Short(
					adicionarSolicitacaoEspecificacaoActionForm
							.getIndicadorUrgencia()));
		}

		// Parecer de encerramento obrigat�rio
		if (adicionarSolicitacaoEspecificacaoActionForm
				.getIndicadorParecerEncerramento() != null
				&& !adicionarSolicitacaoEspecificacaoActionForm
						.getIndicadorParecerEncerramento().equals("")) {
			solicitacaoTipoEspecificacao
					.setIndicadorParecerEncerramento(new Integer(
							adicionarSolicitacaoEspecificacaoActionForm
									.getIndicadorParecerEncerramento()));
		}
		// Gera d�bito
		if (adicionarSolicitacaoEspecificacaoActionForm
				.getIndicadorGerarDebito() != null
				&& !adicionarSolicitacaoEspecificacaoActionForm
						.getIndicadorGerarDebito().equals("")) {
			solicitacaoTipoEspecificacao.setIndicadorGeracaoDebito(new Integer(
					adicionarSolicitacaoEspecificacaoActionForm
							.getIndicadorGerarDebito()));
		}
		// Gera Cr�dito
		if (adicionarSolicitacaoEspecificacaoActionForm
				.getIndicadorGerarCredito() != null
				&& !adicionarSolicitacaoEspecificacaoActionForm
						.getIndicadorGerarCredito().equals("")) {
			solicitacaoTipoEspecificacao
					.setIndicadorGeracaoCredito(new Integer(
							adicionarSolicitacaoEspecificacaoActionForm
									.getIndicadorGerarCredito()));
		}

		// seta indicador de validar Documento Respons�vel para 2
		solicitacaoTipoEspecificacao
				.setIndicadorValidarDocResponsavel(ConstantesSistema.NAO);

		// hora e data correntes
		solicitacaoTipoEspecificacao.setUltimaAlteracao(new Date());

		// Unidade inicial tramita��o
		if (adicionarSolicitacaoEspecificacaoActionForm
				.getIdUnidadeTramitacao() != null
				&& !adicionarSolicitacaoEspecificacaoActionForm
						.getIdUnidadeTramitacao().equals("")) {
			// Verifica se o c�digo foi modificado
			if (adicionarSolicitacaoEspecificacaoActionForm
					.getDescricaoUnidadeTramitacao() == null
					|| adicionarSolicitacaoEspecificacaoActionForm
							.getDescricaoUnidadeTramitacao().trim().equals("")) {

				FiltroUnidadeOrganizacional filtroUnidadeOrganizacional = new FiltroUnidadeOrganizacional();

				filtroUnidadeOrganizacional
						.adicionarParametro(new ParametroSimples(
								FiltroUnidadeOrganizacional.ID,
								adicionarSolicitacaoEspecificacaoActionForm
										.getIdUnidadeTramitacao()));

				filtroUnidadeOrganizacional
						.adicionarParametro(new ParametroSimples(
								FiltroUnidadeOrganizacional.INDICADOR_USO,
								ConstantesSistema.INDICADOR_USO_ATIVO));

				Collection unidadeOrganizacionalEncontrado = fachada.pesquisar(
						filtroUnidadeOrganizacional,
						UnidadeOrganizacional.class.getName());

				if (unidadeOrganizacionalEncontrado != null
						&& !unidadeOrganizacionalEncontrado.isEmpty()) {
					UnidadeOrganizacional uinidadeOrganizacional = (UnidadeOrganizacional) ((List) unidadeOrganizacionalEncontrado)
							.get(0);
					solicitacaoTipoEspecificacao
							.setUnidadeOrganizacional(uinidadeOrganizacional);

				} else {
					throw new ActionServletException(
							"atencao.pesquisa_inexistente", null,
							"Unidade Organizacional");
				}

			} else {
				UnidadeOrganizacional unidadeOrganizacional = new UnidadeOrganizacional();
				unidadeOrganizacional.setId(new Integer(
						adicionarSolicitacaoEspecificacaoActionForm
								.getIdUnidadeTramitacao()));
				solicitacaoTipoEspecificacao
						.setUnidadeOrganizacional(unidadeOrganizacional);
			}
		}

		// id do tipo da solicita��o gerada
		if (adicionarSolicitacaoEspecificacaoActionForm.getIdServicoOS() != null
				&& !adicionarSolicitacaoEspecificacaoActionForm
						.getIdServicoOS().equals("")) {

			// Verifica se o c�digo foi modificado
			if (adicionarSolicitacaoEspecificacaoActionForm
					.getDescricaoServicoOS() == null
					|| adicionarSolicitacaoEspecificacaoActionForm
							.getDescricaoServicoOS().trim().equals("")) {

				FiltroServicoTipo filtroServicoTipo = new FiltroServicoTipo();

				filtroServicoTipo.adicionarParametro(new ParametroSimples(
						FiltroServicoTipo.ID,
						adicionarSolicitacaoEspecificacaoActionForm
								.getIdServicoOS()));

				Collection servicoTipoEncontrado = fachada.pesquisar(
						filtroServicoTipo, ServicoTipo.class.getName());

				if (servicoTipoEncontrado != null
						&& !servicoTipoEncontrado.isEmpty()) {
					// [SF0003] - Validar Tipo Servi�o
					fachada.verificarServicoTipoReferencia(new Integer(
							adicionarSolicitacaoEspecificacaoActionForm
									.getIdServicoOS()));

					ServicoTipo servicoTipo = (ServicoTipo) ((List) servicoTipoEncontrado)
							.get(0);
					solicitacaoTipoEspecificacao.setServicoTipo(servicoTipo);
				} else {
					throw new ActionServletException(
							"atencao.pesquisa_inexistente", null,
							"Servi�o Tipo");
				}
			} else {
				ServicoTipo servicoTipo = new ServicoTipo();
				servicoTipo.setId(new Integer(
						adicionarSolicitacaoEspecificacaoActionForm
								.getIdServicoOS()));
				solicitacaoTipoEspecificacao.setServicoTipo(servicoTipo);
			}

		}

		// Gera ordem de servi�o
		if (adicionarSolicitacaoEspecificacaoActionForm
				.getIndicadorGeraOrdemServico() != null
				&& !adicionarSolicitacaoEspecificacaoActionForm
						.getIndicadorGeraOrdemServico().equals("")) {

			solicitacaoTipoEspecificacao
					.setIndicadorGeracaoOrdemServico(new Short(
							adicionarSolicitacaoEspecificacaoActionForm
									.getIndicadorGeraOrdemServico()));
		}
		// Cliente Obrigat�rio
		if (adicionarSolicitacaoEspecificacaoActionForm.getIndicadorCliente() != null
				&& !adicionarSolicitacaoEspecificacaoActionForm
						.getIndicadorCliente().equals("")) {

			solicitacaoTipoEspecificacao.setIndicadorCliente(new Short(
					adicionarSolicitacaoEspecificacaoActionForm
							.getIndicadorCliente()));
		}

		// Indicador verfificca��o de d�bito
		if (adicionarSolicitacaoEspecificacaoActionForm
				.getIndicadorVerificarDebito() != null
				&& !adicionarSolicitacaoEspecificacaoActionForm
						.getIndicadorVerificarDebito().equals("")) {

			solicitacaoTipoEspecificacao.setIndicadorVerificarDebito(new Short(
					adicionarSolicitacaoEspecificacaoActionForm
							.getIndicadorVerificarDebito()));
		}

		// Situa��o imovel
		if (adicionarSolicitacaoEspecificacaoActionForm.getIdSituacaoImovel() != null
				&& !adicionarSolicitacaoEspecificacaoActionForm
						.getIdSituacaoImovel().equals("")) {
			EspecificacaoImovelSituacao especificacaoImovelSituacao = new EspecificacaoImovelSituacao();
			especificacaoImovelSituacao.setId(new Integer(
					adicionarSolicitacaoEspecificacaoActionForm
							.getIdSituacaoImovel()));
			solicitacaoTipoEspecificacao
					.setEspecificacaoImovelSituacao(especificacaoImovelSituacao);
		}
		Collection colecaoEspecificacaoServicoTipo = (Collection) sessao
				.getAttribute("colecaoEspecificacaoServicoTipo");
		
		// recupera a cole��o de especificacao servico tipo
		if (colecaoEspecificacaoServicoTipo != null
				&& !colecaoEspecificacaoServicoTipo.isEmpty()) {
			// [SF0004] - Validar Valor Ordem de Servi�o 2� parte
			fachada
					.verificarOrdemExecucaoForaOrdem(colecaoEspecificacaoServicoTipo);
			solicitacaoTipoEspecificacao
					.setEspecificacaoServicoTipos(new HashSet(
							colecaoEspecificacaoServicoTipo));
			sessao.removeAttribute("colecaoEspecificacaoServicoTipo");
		}

		// Colocado por Raphael Rossiter em 25/02/2008
		// Tipo de D�bito
		if (adicionarSolicitacaoEspecificacaoActionForm.getIdDebitoTipo() != null
				&& !adicionarSolicitacaoEspecificacaoActionForm
						.getIdDebitoTipo().equals("")) {

			FiltroDebitoTipo filtroDebitoTipo = new FiltroDebitoTipo();

			filtroDebitoTipo.adicionarParametro(new ParametroSimples(
					FiltroDebitoTipo.ID,
					adicionarSolicitacaoEspecificacaoActionForm
							.getIdDebitoTipo()));

			Collection debitoTipoEncontrado = fachada.pesquisar(
					filtroDebitoTipo, DebitoTipo.class.getName());

			if (debitoTipoEncontrado != null && !debitoTipoEncontrado.isEmpty()) {

				DebitoTipo debitoTipo = (DebitoTipo) Util
						.retonarObjetoDeColecao(debitoTipoEncontrado);

				solicitacaoTipoEspecificacao.setDebitoTipo(debitoTipo);
			} else {

				// [FS0007] - Validar Tipo de d�bito
				throw new ActionServletException(
						"atencao.pesquisa_inexistente", null, "Tipo de D�bito");
			}

		}

		// Valor do D�bito
		if (adicionarSolicitacaoEspecificacaoActionForm.getValorDebito() != null
				&& !adicionarSolicitacaoEspecificacaoActionForm
						.getValorDebito().equals("")) {

			solicitacaoTipoEspecificacao
					.setValorDebito(Util
							.formatarMoedaRealparaBigDecimal(adicionarSolicitacaoEspecificacaoActionForm
									.getValorDebito()));
		}

		// Alterar Valor do d�bito
		if (adicionarSolicitacaoEspecificacaoActionForm
				.getIndicadorPermiteAlterarValor() != null
				&& !adicionarSolicitacaoEspecificacaoActionForm
						.getIndicadorPermiteAlterarValor().equals("")) {
			solicitacaoTipoEspecificacao
					.setIndicadorPermiteAlterarValor(new Short(
							adicionarSolicitacaoEspecificacaoActionForm
									.getIndicadorPermiteAlterarValor()));
		}

		// Cobrar Juros
		if (adicionarSolicitacaoEspecificacaoActionForm
				.getIndicadorCobrarJuros() != null
				&& !adicionarSolicitacaoEspecificacaoActionForm
						.getIndicadorCobrarJuros().equals("")) {
			solicitacaoTipoEspecificacao.setIndicadorCobrarJuros(new Short(
					adicionarSolicitacaoEspecificacaoActionForm
							.getIndicadorCobrarJuros()));
		}

		// Indicador encerramento autom�tico
		if (adicionarSolicitacaoEspecificacaoActionForm
				.getIndicadorEncerramentoAutomatico() != null
				&& !adicionarSolicitacaoEspecificacaoActionForm
						.getIndicadorEncerramentoAutomatico().equals("")) {

			solicitacaoTipoEspecificacao
					.setIndicadorEncerramentoAutomatico(new Short(
							adicionarSolicitacaoEspecificacaoActionForm
									.getIndicadorEncerramentoAutomatico()));
		}
		
		//indicador de loja virtual
		if(Util.verificarNaoVazio(adicionarSolicitacaoEspecificacaoActionForm.getIndicadorLojaVirtual())){
			solicitacaoTipoEspecificacao.setIndicadorLojaVirtual(new Short(adicionarSolicitacaoEspecificacaoActionForm.getIndicadorLojaVirtual()));
		}

		// indicador de uso ativo
		solicitacaoTipoEspecificacao.setIndicadorUso(new Short(
				ConstantesSistema.INDICADOR_USO_ATIVO));

		// Especifica��o do novo RA

		if (adicionarSolicitacaoEspecificacaoActionForm.getIdEspecificacao() != null
				&& !adicionarSolicitacaoEspecificacaoActionForm
						.getIdEspecificacao().equals("")) {

			SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacaoNovoRA = new SolicitacaoTipoEspecificacao();
			solicitacaoTipoEspecificacaoNovoRA.setId(Integer
					.parseInt(adicionarSolicitacaoEspecificacaoActionForm
							.getIdEspecificacao()));

			SolicitacaoTipo solicitacaoTipo = new SolicitacaoTipo();
			solicitacaoTipo.setId(Integer
					.parseInt(adicionarSolicitacaoEspecificacaoActionForm
							.getIdTipoSolicitacao()));
			solicitacaoTipoEspecificacaoNovoRA
					.setSolicitacaoTipo(solicitacaoTipo);

			solicitacaoTipoEspecificacao
					.setSolicitacaoTipoEspecificacaoNovoRA(solicitacaoTipoEspecificacaoNovoRA);
		}

		// Informar conta no Registro de Atendimento
		if (adicionarSolicitacaoEspecificacaoActionForm
				.getIndicadorInformarContaRA() != null
				&& !adicionarSolicitacaoEspecificacaoActionForm
						.getIndicadorInformarContaRA().equals("")) {

			solicitacaoTipoEspecificacao.setIndicadorInformarContaRA(new Short(
					adicionarSolicitacaoEspecificacaoActionForm
							.getIndicadorInformarContaRA()));
		}

		// Informar Pagamento em Duplicidade no Registro de Atendimento
		if (adicionarSolicitacaoEspecificacaoActionForm
				.getIndicadorInformarPagamentoDP() != null
				&& !adicionarSolicitacaoEspecificacaoActionForm
						.getIndicadorInformarPagamentoDP().equals("")) {

			solicitacaoTipoEspecificacao
					.setIndicadorInformarPagamentoDuplicidade(new Short(
							adicionarSolicitacaoEspecificacaoActionForm
									.getIndicadorInformarPagamentoDP()));
		}

		// Informar vencimento alternativo
		if (adicionarSolicitacaoEspecificacaoActionForm
				.getIndicadorAlterarVencimentoAlternativo() != null
				&& !adicionarSolicitacaoEspecificacaoActionForm
						.getIndicadorAlterarVencimentoAlternativo().equals("")) {

			solicitacaoTipoEspecificacao
					.setIndicadorAlterarVencimentoAlternativo(new Short(
							adicionarSolicitacaoEspecificacaoActionForm
									.getIndicadorAlterarVencimentoAlternativo()));
		}

		// adiciona na cole��o o tipo de solicita��o especificado
		if (!colecaoSolicitacaoTipoEspecificacao
				.contains(solicitacaoTipoEspecificacao)) {

			colecaoSolicitacaoTipoEspecificacao
					.add(solicitacaoTipoEspecificacao);

		} else {
			throw new ActionServletException(
					"atencao.tipo.especificacao.ja.existe");
		}

		sessao.setAttribute("colecaoSolicitacaoTipoEspecificacao",
				colecaoSolicitacaoTipoEspecificacao);

		// manda um parametro para fechar o popup
		httpServletRequest.setAttribute("fecharPopup", 1);

		return retorno;
	}
}
