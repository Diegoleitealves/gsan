package gcom.gui.atendimentopublico.ligacaoagua;

import gcom.atendimentopublico.ligacaoagua.CorteTipo;
import gcom.atendimentopublico.ligacaoagua.FiltroCorteTipo;
import gcom.atendimentopublico.ordemservico.FiltroServicoNaoCobrancaMotivo;
import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.atendimentopublico.ordemservico.ServicoNaoCobrancaMotivo;
import gcom.atendimentopublico.ordemservico.ServicoTipo;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.ClienteImovel;
import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.cliente.FiltroClienteImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.faturamento.debito.FiltroDebitoTipo;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroNulo;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action respons�vel pela pre-exibi��o da pagina de efetuar corte de liga��o de
 * �gua
 * 
 * @author Thiago Ten�rio
 * @created 20 de Junho de 2006
 */
public class ExibirEfetuarCorteAdministrativoLigacaoAguaAction extends
		GcomAction {
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

		HttpSession sessao = getSessao(httpServletRequest);
		ActionForward retorno = actionMapping
				.findForward("efetuarCorteAdministrativoLigacaoAgua");

		EfetuarCorteAdministrativoLigacaoAguaActionForm corteAdministrativoLigacaoAguaActionForm = (EfetuarCorteAdministrativoLigacaoAguaActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();
		
		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

		Boolean veioEncerrarOS = null;
		if (httpServletRequest.getAttribute("veioEncerrarOS") != null) {
			veioEncerrarOS = Boolean.TRUE;
		} else {
			if (corteAdministrativoLigacaoAguaActionForm.getVeioEncerrarOS() != null
					&& !corteAdministrativoLigacaoAguaActionForm
							.getVeioEncerrarOS().equals("")) {
				if (corteAdministrativoLigacaoAguaActionForm.getVeioEncerrarOS()
						.toLowerCase().equals("true")) {
					veioEncerrarOS = veioEncerrarOS = Boolean.TRUE;
				} else {
					veioEncerrarOS = veioEncerrarOS = Boolean.FALSE;
				}
			} else {
				veioEncerrarOS = Boolean.FALSE;
			}
		}
		
		if (corteAdministrativoLigacaoAguaActionForm.getReset().equals("true")) {
			corteAdministrativoLigacaoAguaActionForm.reset();
		}

		getTipoCorteCollection(sessao);

		String idOrdemServico = null;
		if(corteAdministrativoLigacaoAguaActionForm.getIdOrdemServico() != null){
			idOrdemServico = corteAdministrativoLigacaoAguaActionForm.getIdOrdemServico();
		}else{
			idOrdemServico = (String)httpServletRequest.getAttribute("veioEncerrarOS");
			corteAdministrativoLigacaoAguaActionForm.setDataCorte((String) httpServletRequest
					.getAttribute("dataEncerramento"));
			sessao.setAttribute("caminhoRetornoIntegracaoComercial",httpServletRequest
					.getAttribute("caminhoRetornoIntegracaoComercial"));
		}
		
		if(httpServletRequest.getAttribute("semMenu") != null){
			sessao.setAttribute("semMenu", "SIM");
		}else{
			sessao.removeAttribute("semMenu");
		}
		
		OrdemServico ordemServico = null;
		
		if (idOrdemServico != null && !idOrdemServico.trim().equals("")) {

			ordemServico = fachada.recuperaOSPorId(new Integer(idOrdemServico));

			if (ordemServico != null) {

				fachada.validarExibirCorteAdministrativoLigacaoAgua(
						ordemServico, veioEncerrarOS);

				sessao.setAttribute("ordemServico", ordemServico);

				corteAdministrativoLigacaoAguaActionForm.setVeioEncerrarOS(""
						+ veioEncerrarOS);

				corteAdministrativoLigacaoAguaActionForm.setIdOrdemServico(""
						+ ordemServico.getId());
				corteAdministrativoLigacaoAguaActionForm
						.setNomeOrdemServico(ordemServico.getServicoTipo()
								.getDescricao());

				
				//Comentado por Raphael Rossiter em 28/02/2007
				//Imovel imovel = ordemServico.getRegistroAtendimento().getImovel();
				Imovel imovel = ordemServico.getImovel();

				/*
				 * Validar campo Leitura do Corte Verefica se existe hidrometro
				 * na ligacao de �gua, pois se true o usu�rio poderar ou n�o
				 * informar o n�mero de leitura do corte, mas se false a caixa
				 * de texto ser� desabilitada
				 */
				if (imovel.getLigacaoAgua().getHidrometroInstalacaoHistorico() != null) {
					corteAdministrativoLigacaoAguaActionForm
							.setHidrometro(imovel.getLigacaoAgua()
									.getHidrometroInstalacaoHistorico().getId()
									.toString());
				}

				// Matricula Imovel
				String matriculaImovel = imovel.getId().toString();
				corteAdministrativoLigacaoAguaActionForm
						.setMatriculaImovel(matriculaImovel);

				// Inscri��o Im�vel
				String inscricaoImovel = fachada
						.pesquisarInscricaoImovel(imovel.getId());
				corteAdministrativoLigacaoAguaActionForm
						.setInscricaoImovel(inscricaoImovel);
				
				corteAdministrativoLigacaoAguaActionForm
				.setMatriculaImovel(matriculaImovel);

				// Cliente Usu�rio
				this.pesquisarCliente(corteAdministrativoLigacaoAguaActionForm);

				// Situa��o da Liga��o de Agua
				String situacaoLigacaoAgua = imovel.getLigacaoAguaSituacao()
						.getDescricao();
				corteAdministrativoLigacaoAguaActionForm
						.setSituacaoLigacaoAgua(situacaoLigacaoAgua);

				// Situa��o da Liga��o de Esgoto
				String situacaoLigacaoEsgoto = imovel
						.getLigacaoEsgotoSituacao().getDescricao();
				corteAdministrativoLigacaoAguaActionForm
						.setSituacaoLigacaoEsgoto(situacaoLigacaoEsgoto);

				Date dataCorte = ordemServico.getDataEncerramento();
				if(dataCorte != null && !dataCorte.equals("")){
				  corteAdministrativoLigacaoAguaActionForm.setDataCorte(""
						+ Util.formatarData(dataCorte));
				}

				if (imovel != null && imovel.getLigacaoAgua() != null
						&& imovel.getLigacaoAgua().getCorteTipo() != null) {
					corteAdministrativoLigacaoAguaActionForm.setTipoCorte(""
							+ imovel.getLigacaoAgua().getCorteTipo().getId());
				}

				if (ordemServico.getServicoTipo().getDebitoTipo() != null) {
					corteAdministrativoLigacaoAguaActionForm
							.setIdTipoDebito(ordemServico.getServicoTipo()
									.getDebitoTipo().getId()
									+ "");
					corteAdministrativoLigacaoAguaActionForm
							.setDescricaoTipoDebito(ordemServico
									.getServicoTipo().getDebitoTipo()
									.getDescricao());
				}else{
					corteAdministrativoLigacaoAguaActionForm.setIdTipoDebito("");
					corteAdministrativoLigacaoAguaActionForm.setDescricaoTipoDebito("");
				}
				
				
				//[FS0013] - Altera��o de Valor
				this.permitirAlteracaoValor(ordemServico.getServicoTipo(), corteAdministrativoLigacaoAguaActionForm);
				
				String calculaValores = httpServletRequest.getParameter("calculaValores");
				
				BigDecimal valorDebito = new BigDecimal(0);
				SistemaParametro sistemaParametro = this.getFachada().pesquisarParametrosDoSistema();
				Integer qtdeParcelas = null;
				
				if(calculaValores != null && calculaValores.equals("S")){
					
					//[UC0186] - Calcular Presta��o
					BigDecimal  taxaJurosFinanciamento = null; 
					qtdeParcelas = new Integer(corteAdministrativoLigacaoAguaActionForm.getQuantidadeParcelas());
					
					if(ordemServico.getServicoTipo().getIndicadorCobrarJuros() == ConstantesSistema.SIM.shortValue() && 
						qtdeParcelas.intValue() != 1){
						
						taxaJurosFinanciamento = sistemaParametro.getPercentualTaxaJurosFinanciamento();
					}else{
						taxaJurosFinanciamento = new BigDecimal(0);
					}
					
					BigDecimal valorPrestacao = null;
					if(taxaJurosFinanciamento != null){
						
						valorDebito = new BigDecimal(corteAdministrativoLigacaoAguaActionForm.getValorDebito().replace(",","."));
						
						String percentualCobranca = corteAdministrativoLigacaoAguaActionForm.getPercentualCobranca();
						
						if(percentualCobranca.equals("70")){
							valorDebito = valorDebito.multiply(new BigDecimal(0.7));
						}else if (percentualCobranca.equals("50")){
							valorDebito = valorDebito.multiply(new BigDecimal(0.5));
						}
						
						valorPrestacao =
							this.getFachada().calcularPrestacao(
								taxaJurosFinanciamento,
								qtdeParcelas, 
								valorDebito, 
								new BigDecimal("0.00"));
						
						valorPrestacao.setScale(2,BigDecimal.ROUND_HALF_UP);
					}
					
					if (valorPrestacao != null) {
						String valorPrestacaoComVirgula = Util.formataBigDecimal(valorPrestacao,2,true);
						corteAdministrativoLigacaoAguaActionForm.setValorParcelas(valorPrestacaoComVirgula);
					} else {
						corteAdministrativoLigacaoAguaActionForm.setValorParcelas("0,00");
					}						
					
				}else{
					// Valor D�bito
					valorDebito = fachada.obterValorDebito(ordemServico
							.getServicoTipo().getId(),
							new Integer(matriculaImovel), new Short("1"));
					
					if (valorDebito != null) {
						corteAdministrativoLigacaoAguaActionForm
								.setValorDebito(valorDebito + "");
					} else {
						corteAdministrativoLigacaoAguaActionForm
								.setValorDebito("0");
					}
				}

				// Filtro para o campo Tpo Debito
				Collection colecaoNaoCobranca = (Collection) sessao
						.getAttribute("colecaoNaoCobranca");
				if (colecaoNaoCobranca == null) {
					FiltroServicoNaoCobrancaMotivo filtroServicoNaoCobrancaMotivo = new FiltroServicoNaoCobrancaMotivo();

					filtroServicoNaoCobrancaMotivo
							.setCampoOrderBy(FiltroDebitoTipo.DESCRICAO);

					colecaoNaoCobranca = fachada.pesquisar(
							filtroServicoNaoCobrancaMotivo,
							ServicoNaoCobrancaMotivo.class.getName());

					if (colecaoNaoCobranca != null
							&& !colecaoNaoCobranca.isEmpty()) {
						sessao.setAttribute("colecaoNaoCobranca",
								colecaoNaoCobranca);
					} else {
						throw new ActionServletException(
								"atencao.naocadastrado", null,
								"Motivo da N�o Cobran�a");
					}
				}

				corteAdministrativoLigacaoAguaActionForm
						.setQtdeMaxParcelas(sistemaParametro
								.getNumeroMaximoParcelasFinanciamento()
								+ "");
				
				// -----------------------------------------------------------
				// Verificar permiss�o especial
				boolean temPermissaoMotivoNaoCobranca = fachada.verificarPermissaoInformarMotivoNaoCobranca(usuarioLogado);
				// -----------------------------------------------------------
				
				if (temPermissaoMotivoNaoCobranca) {
					httpServletRequest.setAttribute("permissaoMotivoNaoCobranca", temPermissaoMotivoNaoCobranca);
				}else{
					corteAdministrativoLigacaoAguaActionForm.setPercentualCobranca("100");
					corteAdministrativoLigacaoAguaActionForm.setQuantidadeParcelas("1");
					corteAdministrativoLigacaoAguaActionForm.setValorParcelas(Util.formataBigDecimal(valorDebito,2,true));
				}
			} else {
				corteAdministrativoLigacaoAguaActionForm.setNomeOrdemServico("Ordem de Servi�o inexistente");
				corteAdministrativoLigacaoAguaActionForm.setIdOrdemServico("");
			}
		} else {
			httpServletRequest.setAttribute("nomeCampo", "idOrdemServico");
			corteAdministrativoLigacaoAguaActionForm.reset();
		}
		return retorno;
	}
	
	
	/*
	 * [FS0013 - Altera��o de Valor]
	 * 
	 * autor: Raphael Rossiter
	 * data: 19/04/2007
	 */
	private void permitirAlteracaoValor(ServicoTipo servicoTipo, EfetuarCorteAdministrativoLigacaoAguaActionForm form){
		
		if (servicoTipo.getIndicadorPermiteAlterarValor() == 
			ConstantesSistema.INDICADOR_USO_ATIVO.shortValue()){
			
			form.setAlteracaoValor("OK");
		}
		else{
			form.setAlteracaoValor("");
		}
		
	}
	

	private void getTipoCorteCollection(HttpSession sessao) {
		// Filtro para o campo Motivo do Corte
		FiltroCorteTipo filtroTipoCorteLigacaoAgua = new FiltroCorteTipo();
		filtroTipoCorteLigacaoAgua.adicionarParametro(new ParametroSimples(FiltroCorteTipo.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroTipoCorteLigacaoAgua.adicionarParametro(new ParametroSimples(FiltroCorteTipo.INDICADOR_CORTE_ADMINISTRATIVO, ConstantesSistema.SIM));
		filtroTipoCorteLigacaoAgua.setCampoOrderBy(FiltroCorteTipo.DESCRICAO);

		Collection colecaoTipoCorteLigacaoAgua = Fachada.getInstancia().pesquisar(filtroTipoCorteLigacaoAgua, CorteTipo.class.getName());
		if (colecaoTipoCorteLigacaoAgua != null && !colecaoTipoCorteLigacaoAgua.isEmpty()) {
			sessao.setAttribute("colecaoTipoCorteLigacaoAgua",colecaoTipoCorteLigacaoAgua);
		} else {
			throw new ActionServletException("atencao.naocadastrado",null, "Tipo do Corte");
		}
	}	


	/**
	 * Pesquisa Cliente
	 * 
	 * @author Rafael Pinto
	 * @date 25/08/2006
	 */
	private void pesquisarCliente(
			EfetuarCorteAdministrativoLigacaoAguaActionForm corteAdministrativoLigacaoAguaActionForm) {

		// Filtro para carregar o Cliente
		FiltroClienteImovel filtroClienteImovel = new FiltroClienteImovel();

		filtroClienteImovel.adicionarParametro(new ParametroSimples(
				FiltroClienteImovel.IMOVEL_ID,
				corteAdministrativoLigacaoAguaActionForm.getMatriculaImovel()));

		filtroClienteImovel.adicionarParametro(new ParametroSimples(
				FiltroClienteImovel.CLIENTE_RELACAO_TIPO,
				ClienteRelacaoTipo.USUARIO));

		filtroClienteImovel.adicionarParametro(new ParametroNulo(
				FiltroClienteImovel.DATA_FIM_RELACAO));

		filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("cliente");

		Collection colecaoClienteImovel = Fachada.getInstancia().pesquisar(
				filtroClienteImovel, ClienteImovel.class.getName());

		if (colecaoClienteImovel != null && !colecaoClienteImovel.isEmpty()) {

			ClienteImovel clienteImovel = (ClienteImovel) colecaoClienteImovel
					.iterator().next();

			Cliente cliente = clienteImovel.getCliente();

			String documento = "";

			if (cliente.getCpf() != null && !cliente.getCpf().equals("")) {
				documento = cliente.getCpfFormatado();
			} else {
				documento = cliente.getCnpjFormatado();
			}
			// Cliente Nome/CPF-CNPJ
			corteAdministrativoLigacaoAguaActionForm.setClienteUsuario(cliente
					.getNome());
			corteAdministrativoLigacaoAguaActionForm
					.setCpfCnpjCliente(documento);

		} else {
			throw new ActionServletException("atencao.naocadastrado", null,
					"Cliente");
		}
	}

}
