package gcom.gui.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Filtro respons�vel por verificar se a sess�o do usu�rio expirou o tempo
 * 
 * @author Pedro Alexandre
 * @date 05/07/2006
 */
public class FiltroSessaoExpirada extends HttpServlet implements Filter {
	// Vari�vel que vai armazenar a configura��o do filtro
	private FilterConfig filterConfig;

	private static final long serialVersionUID = 1L;

	/**
	 * <Breve descri��o sobre o caso de uso>
	 * 
	 * @author Pedro Alexandre
	 * @date 05/07/2006
	 * 
	 * @param filterConfig
	 */
	public void init(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}

	/**
	 * <Breve descri��o sobre o caso de uso>
	 * 
	 * @author Pedro Alexandre
	 * @date 05/07/2006
	 * 
	 * @param request
	 * @param response
	 * @param filterChain
	 * @throws ServletException
	 * @throws IOException
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws ServletException, IOException {
		try {
			// Faz um cast no request para recuperar a sess�o do usu�rio
			HttpServletRequest requestPagina = ((HttpServletRequest) request);
			HttpSession sessao = requestPagina.getSession(false);

			// Recupera a url do request
			String enderecoURL = requestPagina.getServletPath();

			// Caso a sess�o esteja nula(expirou) redireciona o usu�rio para a
			// p�gina de sess�o expirada
			// Caso contr�rio chama o pr�ximo filtro do web.xml se existir
			if (sessao == null
					&& (!enderecoURL
							.contains("exibirEmitirSegundaViaContaInternetAcessoGeralAction")
							&& !enderecoURL
									.contains("emitirSegundaViaContaInternetAcessoGeralAction")
							&& !enderecoURL
									.contains("enviarDadosBancosAcessoGeralAction")
							&& !enderecoURL
									.contains("exibirSelecionarBancoAction")
							&& !enderecoURL.contains("enviarDadosBancosAction")
							&& !enderecoURL
									.contains("exibirSelecionarBancoAcessoGeralAction")
							&& !enderecoURL
									.contains("exibirLogTelaInicialAction")
							&& !enderecoURL
									.contains("exibirLogTelaFinalAction")
							&& !enderecoURL
									.contains("processarRequisicaoDipositivoMovelAction")
							&& !enderecoURL
									.contains("processarRequisicaoTelemetriaAction")
							&& !enderecoURL.contains("efetuarLoginAction")
							&& !enderecoURL
									.contains("processarRequisicaoGisAction")
							&& !enderecoURL
									.contains("processarCoordenadasGisAction")
							&& !enderecoURL
									.contains("processarRequisicaoDipositivoMovelImpressaoSimultaneaAction")
                            && !enderecoURL
                            		.contains ("processarRequisicaoDispositivoMovelAcompanhamentoServicoAction") 									
							&& !enderecoURL
									.contains("exibirInserirCadastroEmailClienteAction")
							&& !enderecoURL
									.contains("inserirCadastroEmailClienteAction")
							&& !enderecoURL
									.contains("gerarRelatorio2ViaContaAction")
							&& !enderecoURL
									.contains("exibirInserirCadastroContaBraileAction")
							&& !enderecoURL
									.contains("inserirCadastroContaBraileAction")
							&& !enderecoURL
									.contains("exibirServicosPortalCompesaAction")
							&& !enderecoURL
									.contains("inserirCadastroContaBrailePortalAction")
							&& !enderecoURL
									.contains("exibirInserirSolicitacaoServicosPortalAction")
							&& !enderecoURL
									.contains("inserirSolicitacaoServicosPortalAction")
							&& !enderecoURL
									.contains("exibirInserirCadastroContaBrailePortalAction")
							&& !enderecoURL
									.contains("inserirCadastroEmailClientePortalAction") 
							&& !enderecoURL
									.contains("exibirInserirCadastroEmailClientePortalAction")
							&& !enderecoURL
									.contains("emitirSegundaViaContaAction")
							&& !enderecoURL
									.contains("exibirCanaisAtendimentoCompesaAction")
							&& !enderecoURL
									.contains("exibirQuestionarioSatisfacaoAction")
							&& !enderecoURL
									.contains("questionarioSatisfacaoAction")
							&& !enderecoURL
									.contains("exibirInformacoesPortalCompesaAction")
							&& !enderecoURL
									.contains("exibirInformacoesTarifaSocialPortalCompesaAction")
							&& !enderecoURL
									.contains("exibirInformacoesNegociacaoDebitosPortalCompesaAction")
							&& !enderecoURL
									.contains("exibirNormasInstalacaoPortalCompesaAction")
							&& !enderecoURL
									.contains("exibirCalendarioAbastecimentoPortalCompesaAction")
							&& !enderecoURL.
									contains("exibirEfetuarParcelamentoDebitosPortalAction")
							&& !enderecoURL.
									contains("efetuarParcelamentoDebitosPortalAction")
							&& !enderecoURL.
									contains("exibirLojasAtendimentoPresencialPortalCompesaAction")
							&& !enderecoURL.
									contains("exibirConsultarEstruturaTarifariaPortalAction")

					)) {

				RequestDispatcher rd = filterConfig.getServletContext()
						.getRequestDispatcher("/jsp/util/sessao_expirada.jsp");
				rd.forward(request, response);
			} else {
				if (enderecoURL
						.contains("exibirEmitirSegundaViaContaInternetAcessoGeralAction")) {
					// Cria uma sessao temporaria para o usuario que entra no
					// EmitirSegundaViaContaInternet sem logar no sistema
					sessao = requestPagina.getSession(true);

				}
				filterChain.doFilter(request, response);
			}

		} catch (ServletException sx) {
			throw sx;
		} catch (IOException iox) {
			throw iox;
		}
	}

	/**
	 * <Breve descri��o sobre o caso de uso>
	 * 
	 * @author Pedro Alexandre
	 * @date 05/07/2006
	 * 
	 */
	public void destroy() {
	}
}
