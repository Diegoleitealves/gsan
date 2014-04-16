package gcom.gui.atendimentopublico.registroatendimento;

import gcom.atendimentopublico.registroatendimento.RegistroAtendimentoSolicitante;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.funcionario.FiltroFuncionario;
import gcom.cadastro.funcionario.Funcionario;
import gcom.cadastro.unidade.FiltroUnidadeOrganizacional;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Esta classe tem por finalidade inserir um solicitante em uma RA j� cadastrada
 * 
 * @author Raphael Rossiter
 * @date 25/08/2006
 */
public class AdicionarSolicitanteRegistroAtendimentoAction extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		ActionForward retorno = actionMapping
				.findForward("adicionarSolicitanteRegistroAtendimento");

		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		AdicionarSolicitanteRegistroAtendimentoActionForm form = (AdicionarSolicitanteRegistroAtendimentoActionForm) actionForm;

		/*
		 * Pesquisas realizadas a partir do ENTER
		 * ===========================================================================================================
		 */
		String objetoColecao = (String) sessao.getAttribute("objetoColecao");

		String idRASolicitante = (String) sessao
				.getAttribute("idRASolicitante");

		if ((form.getIdCliente() != null && !form.getIdCliente()
				.equalsIgnoreCase(""))
				&& (form.getNomeCliente() == null || form.getNomeCliente()
						.equals(""))) {

			/*
			 * Obtendo o id do im�vel informado na aba de inser��o n� 02 (Caso
			 * n�o tenho sido informado ser� passado como par�metro NULL).
			 */
			String idImovel = null;
			// caso seja do processo de atualizar registro atendimento
			if (objetoColecao != null && !objetoColecao.equals("")) {
				AtualizarRegistroAtendimentoActionForm atualizarRegistroAtendimentoActionForm = (AtualizarRegistroAtendimentoActionForm) sessao
						.getAttribute("AtualizarRegistroAtendimentoActionForm");
				idImovel = atualizarRegistroAtendimentoActionForm.getIdImovel();
			} else {
				// caso seja do processo de inserir registro atendimento
				InserirRegistroAtendimentoActionForm inserirRegistroAtendimentoActionForm = (InserirRegistroAtendimentoActionForm) sessao
						.getAttribute("InserirRegistroAtendimentoActionForm");
				idImovel = inserirRegistroAtendimentoActionForm.getIdImovel();
			}

			// [FS0027] - Verificar informa��o do im�vel
			Cliente cliente = fachada.verificarInformacaoImovel(Util
					.converterStringParaInteger(form.getIdCliente()), Util
					.converterStringParaInteger(idImovel), false);

			if (cliente == null) {

				// Nenhuma cliente encontrado
				throw new ActionServletException(
						"atencao.pesquisa_inexistente", null, "Cliente");

			}

			// caso seja do processo de atualizar registro atendimento
			if (objetoColecao == null || objetoColecao.equals("")) {
				// [FS0012] - Verificar exist�ncia do cliente solicitante
				fachada.verificarExistenciaClienteSolicitante(Util
						.converterStringParaInteger(form.getIdRA()),
						cliente.getId());
			} else {
				if (idRASolicitante != null && !idRASolicitante.equals("")) {
					// [FS0012] - Verificar exist�ncia do cliente
					// solicitante
					fachada
							.verificarExistenciaClienteSolicitanteAtualizar(
									Util.converterStringParaInteger(form
											.getIdRA()),
									cliente.getId(),
									Util
											.converterStringParaInteger(idRASolicitante));
				}

			}

			form.setIdCliente(cliente.getId().toString());
			form.setNomeCliente(cliente.getNome());

			Collection colecaoEnderecos = fachada
					.pesquisarEnderecosClienteAbreviado(cliente.getId());

			if (colecaoEnderecos != null && !colecaoEnderecos.isEmpty()) {

				sessao.setAttribute("colecaoEnderecosSolicitante",
						colecaoEnderecos);
			}

			Collection colecaoFones = fachada.pesquisarClienteFone(cliente
					.getId());

			sessao.setAttribute("colecaoFonesSolicitante", colecaoFones);
			this.limparUnidadeSolicitante(sessao);
			this.limparNomeSolicitante(sessao);

			
		}

		if ((form.getIdUnidadeSolicitanteInformar() != null && !form
				.getIdUnidadeSolicitanteInformar().equals(""))
				&& (form.getDescricaoUnidadeSolicitanteInformar() == null || form
						.getDescricaoUnidadeSolicitanteInformar().equals(""))) {

			FiltroUnidadeOrganizacional filtroUnidadeOrganizacional = new FiltroUnidadeOrganizacional();

			filtroUnidadeOrganizacional
					.adicionarParametro(new ParametroSimples(
							FiltroUnidadeOrganizacional.ID, form
									.getIdUnidadeSolicitanteInformar()));

			filtroUnidadeOrganizacional
					.adicionarParametro(new ParametroSimples(
							FiltroUnidadeOrganizacional.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			Collection colecaoUnidade = fachada.pesquisar(
					filtroUnidadeOrganizacional, UnidadeOrganizacional.class
							.getName());

			if (colecaoUnidade == null || colecaoUnidade.isEmpty()) {

				// Nenhuma unidade encontrado
				throw new ActionServletException(
						"atencao.pesquisa_inexistente", null,
						"Unidade Solicitante");

			}
			UnidadeOrganizacional unidade = (UnidadeOrganizacional) Util
					.retonarObjetoDeColecao(colecaoUnidade);

			form
					.setIdUnidadeSolicitanteInformar(unidade.getId()
							.toString());
			form.setDescricaoUnidadeSolicitanteInformar(unidade
					.getDescricao());

			// caso seja do processo de atualizar registro atendimento
			if (objetoColecao == null || objetoColecao.equals("")) {

				// [FS0026] - Verificar exist�ncia da unidade solicitante
				fachada.verificarExistenciaUnidadeSolicitante(Util
						.converterStringParaInteger(form.getIdRA()), Util
						.converterStringParaInteger(form
								.getIdUnidadeSolicitanteInformar()));
			} else {
				if (idRASolicitante != null && !idRASolicitante.equals("")) {
					// [FS0026] - Verificar exist�ncia da unidade
					// solicitante
					fachada
							.verificarExistenciaUnidadeSolicitanteAtualizar(
									Util.converterStringParaInteger(form
											.getIdRA()),
									Util
											.converterStringParaInteger(form
													.getIdUnidadeSolicitanteInformar()),
									Util
											.converterStringParaInteger(idRASolicitante));
				}

			}

				// this.limparCliente(sessao);
				// this.limparNomeSolicitante(sessao);
			
		}

		if ((form.getIdFuncionarioResponsavel() != null && !form
				.getIdFuncionarioResponsavel().equalsIgnoreCase(""))
				&& (form.getDescricaoFuncionarioResponsavel() == null || form
						.getDescricaoFuncionarioResponsavel().equals(""))) {

			FiltroFuncionario filtroFuncionario = new FiltroFuncionario();

			filtroFuncionario.adicionarParametro(new ParametroSimples(
					FiltroFuncionario.ID, form.getIdFuncionarioResponsavel()));

			filtroFuncionario.adicionarParametro(new ParametroSimples(
					FiltroFuncionario.UNIDADE_ORGANIZACIONAL_ID, form
							.getIdUnidadeSolicitanteInformar()));

			Collection colecaoFuncionario = fachada.pesquisar(
					filtroFuncionario, Funcionario.class.getName());

			if (colecaoFuncionario == null || colecaoFuncionario.isEmpty()) {

				// Nenhum funcion�rio encontrado
				throw new ActionServletException(
						"atencao.pesquisa_inexistente", null, "Funcion�rio");

			}
			Funcionario funcionario = (Funcionario) Util
					.retonarObjetoDeColecao(colecaoFuncionario);

			form
					.setIdFuncionarioResponsavel(funcionario.getId()
							.toString());
			form.setDescricaoFuncionarioResponsavel(funcionario.getNome());

			httpServletRequest.setAttribute("nomeCampo",
					"botaoAdicionarSolicitante");
		}

		/*
		 * Fim das pesquisas realizadas pelo ENTER
		 * ===========================================================================================================
		 * ===========================================================================================================
		 */

		String nomeSolicitante = null;
		if (form.getNomeSolicitanteInformar() != null
				&& !form.getNomeSolicitanteInformar().equalsIgnoreCase("")) {
			nomeSolicitante = form.getNomeSolicitanteInformar();
		}

		Collection colecaoEndereco = null;
		if (sessao.getAttribute("colecaoEnderecosSolicitante") != null) {
			colecaoEndereco = (Collection) sessao
					.getAttribute("colecaoEnderecosSolicitante");
		}

		Collection colecaoFone = null;
		if (sessao.getAttribute("colecaoFonesSolicitante") != null) {
			colecaoFone = (Collection) sessao
					.getAttribute("colecaoFonesSolicitante");
		}

		Short indicadorClienteEspecificacao = null;
		if (form.getIndicadorClienteEspecificacao() != null
				&& !form.getIndicadorClienteEspecificacao()
						.equalsIgnoreCase("")) {
			indicadorClienteEspecificacao = new Short(form
					.getIndicadorClienteEspecificacao());
		}

		String pontoReferencia = null;
		if (form.getPontoReferencia() != null
				&& !form.getPontoReferencia().equalsIgnoreCase("")) {
			pontoReferencia = form.getPontoReferencia();
		}

		/*
		 * Obtendo o id do im�vel informado na aba de inser��o n� 02 (Caso n�o
		 * tenho sido informado ser� passado como par�metro NULL).
		 */
		String idImovel = null;
		
		//PROTOCOLO DE ATENDIMENTO
		String protocoloAtendimento = form.getProtocoloAtendimento();
		
		if ((protocoloAtendimento == null || protocoloAtendimento.equals("")) &&
			sessao.getAttribute("protocoloAtendimento") != null){
			
			protocoloAtendimento = (String) sessao.getAttribute("protocoloAtendimento");
		}

		// CASO SEJA DO PROCESSO DE ATUALIZAR REGISTRO DE ATENDIMENTO 
		if (objetoColecao != null && !objetoColecao.equals("")) {
			
			//FORMUL�RIO
			AtualizarRegistroAtendimentoActionForm atualizarRegistroAtendimentoActionForm = 
			(AtualizarRegistroAtendimentoActionForm) sessao.getAttribute("AtualizarRegistroAtendimentoActionForm");
			
			//IM�VEL
			idImovel = atualizarRegistroAtendimentoActionForm.getIdImovel();
			
			//VALIDANDO UM SOLICITANTE J� EXISTENTE
			if (!objetoColecao.equals("SIM") && 
				idRASolicitante != null && !idRASolicitante.equals("")) {
				
				// [FS0030] - Verificar preenchimento dos dados de identifica��o do solicitante
				fachada.verificaDadosSolicitanteAtualizar(
						Util.converterStringParaInteger(form.getIdCliente()), 
						Util.converterStringParaInteger(form.getIdUnidadeSolicitanteInformar()), 
						Util.converterStringParaInteger(form.getIdFuncionarioResponsavel()),
						nomeSolicitante, colecaoEndereco, colecaoFone, indicadorClienteEspecificacao, 
						Util.converterStringParaInteger(idImovel), 
						Util.converterStringParaInteger(form.getIdRA()),
						Util.converterStringParaInteger(idRASolicitante));

			}
			
			//VALIDANDO UM NOVO SOLICITANTE
			else {
				
				// [FS0030] - Verificar preenchimento dos dados de identifica��o do solicitante
				fachada.verificaDadosSolicitante(
						Util.converterStringParaInteger(form.getIdCliente()), 
						Util.converterStringParaInteger(form.getIdUnidadeSolicitanteInformar()), 
						Util.converterStringParaInteger(form.getIdFuncionarioResponsavel()),
						nomeSolicitante, colecaoEndereco, colecaoFone, indicadorClienteEspecificacao, 
						Util.converterStringParaInteger(idImovel), 
						Util.converterStringParaInteger(form.getIdRA()), null, 
						Util.converterStringParaInteger(form.getIdMeioSolicitacao()) );
			}

			//COLE��O COM OS SOLICITANTES CADASTRADOS NA BASE
			Collection colecaoRASolicitante = (Collection) sessao
			.getAttribute("colecaoRASolicitante");

			//MONTANDO O REGISTRO DE ATENDIMENTO SOLICITANTE
			RegistroAtendimentoSolicitante registroAtendimentoSolicitante = fachada
			.inserirDadosNoRegistroAtendimentoSolicitante(
					Util.converterStringParaInteger(form.getIdRA()), 
					Util.converterStringParaInteger(form.getIdCliente()),
					colecaoEndereco, pontoReferencia, nomeSolicitante,
					Util.converterStringParaInteger(form.getIdUnidadeSolicitanteInformar()), 
					Util.converterStringParaInteger(form.getIdFuncionarioResponsavel()),
					colecaoFone, form.getClienteFoneSelected());
			
			//[UC0405] RM1039 MONTANDO O DADOS DE ENVIO DE EMAIL PARA O QUESTIONARIO DE SATISFACAO
			if(form.getEnviarEmailSatisfacao() != null && !form.getEnviarEmailSatisfacao().equals("")){
				registroAtendimentoSolicitante.setEnderecoEmail(form.getEnderecoEmail());
				registroAtendimentoSolicitante.setIndicadorEnvioEmailPesquisa(Short.parseShort(form.getEnviarEmailSatisfacao()));
			}
			
			//INFORMANDO O PROTOCOLO DE ATENDIMENTO
			if (protocoloAtendimento != null && !protocoloAtendimento.equals("")){
				
				registroAtendimentoSolicitante.setNumeroProtocoloAtendimento(protocoloAtendimento);
			}
			

			if (objetoColecao != null && !objetoColecao.equals("SIM")) {
				
				Iterator iteRASolicitante = colecaoRASolicitante.iterator();
				
				while (iteRASolicitante.hasNext()) {
					
					RegistroAtendimentoSolicitante registroAtendimentoSolicitanteColecao = 
					(RegistroAtendimentoSolicitante) iteRASolicitante.next();
					
					if (registroAtendimentoSolicitanteColecao.getUltimaAlteracao().getTime() == 
						Long.parseLong(objetoColecao)) {
						
						if (registroAtendimentoSolicitanteColecao.getID() != null) {
							
							registroAtendimentoSolicitante.setID(registroAtendimentoSolicitanteColecao.getID());

							registroAtendimentoSolicitante
							.setIndicadorSolicitantePrincipal(registroAtendimentoSolicitanteColecao
							.getIndicadorSolicitantePrincipal());

						}
						
						registroAtendimentoSolicitante.setUltimaAlteracao(registroAtendimentoSolicitanteColecao
						.getUltimaAlteracao());
						
						iteRASolicitante.remove();
						
						break;
					}
				}

			} 
			else {
				
				// seta a ultima altera��o
				registroAtendimentoSolicitante.setUltimaAlteracao(new Date());
			}

			colecaoRASolicitante.add(registroAtendimentoSolicitante);

			// Iniciar Funcionalidade
			httpServletRequest.setAttribute("iniciarFuncionalidade",
			"/gsan/atualizarRegistroAtendimentoWizardAction.do?action=exibirAtualizarRegistroAtendimentoDadosSolicitanteAction");
			
		} 
		else {
			
			//CASO SEJA DO PROCESSO DE INSERIR REGISTRO DE ATENDIMENTO
			InserirRegistroAtendimentoActionForm inserirRegistroAtendimentoActionForm = 
			(InserirRegistroAtendimentoActionForm) sessao.getAttribute("InserirRegistroAtendimentoActionForm");
			
			idImovel = inserirRegistroAtendimentoActionForm.getIdImovel();
			
			// [FS0030] - Verificar preenchimento dos dados de identifica��o do solicitante
			fachada.verificaDadosSolicitante(
					Util.converterStringParaInteger(form.getIdCliente()), 
					Util.converterStringParaInteger(form.getIdUnidadeSolicitanteInformar()), 
					Util.converterStringParaInteger(form.getIdFuncionarioResponsavel()), 
					nomeSolicitante, colecaoEndereco, colecaoFone, indicadorClienteEspecificacao, 
					Util.converterStringParaInteger(idImovel), 
					Util.converterStringParaInteger(form.getIdRA()), null,
					Util.converterStringParaInteger(form.getIdMeioSolicitacao()) );

			// [SB0027] - Inclui Solicitante do Registro de Atendimento
			fachada.inserirRegistroAtendimentoSolicitante(
					Util.converterStringParaInteger(form.getIdRA()), 
					Util.converterStringParaInteger(form.getIdCliente()),
					colecaoEndereco, pontoReferencia, nomeSolicitante, true,
					Util.converterStringParaInteger(form.getIdUnidadeSolicitanteInformar()), 
					Util.converterStringParaInteger(form.getIdFuncionarioResponsavel()),
					colecaoFone, protocoloAtendimento);

			// Iniciar Funcionalidade
			httpServletRequest.setAttribute("iniciarFuncionalidade", "/gsan/exibirInserirRegistroAtendimentoAction.do");
		}

		sessao.removeAttribute("atualizacaoRA");
		sessao.removeAttribute("objetoColecao");

		sessao.removeAttribute("idRASolicitante");
		sessao.removeAttribute("enderecoOcorrenciaRA");
		sessao.removeAttribute("protocoloAtendimento");
		limparCliente(sessao);
		limparUnidadeSolicitante(sessao);
		limparNomeSolicitante(sessao);

		return retorno;
	}

	private void limparCliente(HttpSession sessao) {

		sessao.removeAttribute("colecaoEnderecosSolicitante");
		sessao.removeAttribute("colecaoFonesSolicitante");
		sessao.removeAttribute("desabilitarDadosSolicitanteUnidade");
		sessao.removeAttribute("desabilitarDadosSolicitanteFuncionario");
		sessao.removeAttribute("desabilitarDadosSolicitanteNome");
		sessao.removeAttribute("habilitarAlteracaoEnderecoSolicitante");

	}

	private void limparUnidadeSolicitante(HttpSession sessao) {

		sessao.removeAttribute("desabilitarDadosSolicitanteCliente");
		sessao.removeAttribute("desabilitarDadosSolicitanteNome");
		sessao.removeAttribute("habilitarAlteracaoEnderecoSolicitante");

	}

	private void limparNomeSolicitante(HttpSession sessao) {

		sessao.removeAttribute("desabilitarDadosSolicitanteCliente");
		sessao.removeAttribute("desabilitarDadosSolicitanteUnidade");
		sessao.removeAttribute("desabilitarDadosSolicitanteFuncionario");
		sessao.removeAttribute("habilitarAlteracaoEnderecoSolicitante");

	}
}
