package gcom.gui.cadastro.imovel;

import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.cliente.FiltroCliente;
import gcom.cadastro.cliente.FiltroClienteRelacaoTipo;
import gcom.cadastro.cliente.FiltroClienteTipo;
import gcom.cadastro.imovel.AreaConstruidaFaixa;
import gcom.cadastro.imovel.FiltroAreaConstruidaFaixa;
import gcom.cadastro.imovel.ImovelEconomia;
import gcom.cadastro.imovel.ImovelPerfil;
import gcom.cadastro.imovel.ImovelSubcategoria;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action para exibir a p�gina de economia popup
 * 
 * @author S�vio Luiz
 * @created 19 de Maio de 2004
 */
public class ExibirAtualizarEconomiaPopupAction extends GcomAction {

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

		// Prepara o retorno da A��o
		ActionForward retorno = actionMapping
				.findForward("atualizarEconomiaPopup");

		// Cria a sess�o
		HttpSession sessao = httpServletRequest.getSession(false);

		Collection colecaoImovelSubCategoriasCadastradas = null;

		// Cole��o vinda do exibirInserirEconomiaAcion
		// nessa cole��o est�o todos os imoveis sub categorias que foi
		// pesquisado no economia_inserir_jsp
		if (sessao.getAttribute("colecaoImovelSubCategoriasCadastradas") != null) {
			colecaoImovelSubCategoriasCadastradas = (Collection) sessao
					.getAttribute("colecaoImovelSubCategoriasCadastradas");

		} else {
			colecaoImovelSubCategoriasCadastradas = new ArrayList();
		}

		// Obt�m o action form
		EconomiaPopupActionForm economiaPopupActionForm = (EconomiaPopupActionForm) actionForm;

		// Obt�m a fachada
		Fachada fachada = Fachada.getInstancia();

		ImovelEconomia imovelEconomia = null;

		// incicializa o achou para false e caso entre no loop do while ele
		// passa a ser true
		boolean achou = false;

		// Verifica se veio algum parametro no economia_inserir.jsp
		// caso tenha vindo pega o parametro e procura na cole��o um objeto
		// que tenha um hashCode igual ao do parametro
		if (httpServletRequest.getParameter("codigoImovelEconomia") != null
				&& !httpServletRequest.getParameter("codigoImovelEconomia")
						.equals("")) {

			// para incluir mais rela��es entre cliente e imoveis, se preciso
			sessao.setAttribute("colecaoClientesImoveisEconomia",
					new ArrayList());

			Iterator imovelSubCategoriaIterator = colecaoImovelSubCategoriasCadastradas
					.iterator();

			String codigoImovelEconomia = (String) httpServletRequest
					.getParameter("codigoImovelEconomia");

			while (imovelSubCategoriaIterator.hasNext()) {

				if (!achou) {

					ImovelSubcategoria imovelSubcategoria = (ImovelSubcategoria) imovelSubCategoriaIterator
							.next();
					Iterator imovelEconomiaIterator = imovelSubcategoria
							.getImovelEconomias().iterator();
					while (imovelEconomiaIterator.hasNext()) {
						imovelEconomia = (ImovelEconomia) imovelEconomiaIterator
								.next();
						if (imovelEconomia.getUltimaAlteracao().getTime() == Long
								.parseLong(codigoImovelEconomia)) {

							sessao.setAttribute("imovelEconomia",
									imovelEconomia);
							// manda os parametros para o form
							economiaPopupActionForm
									.setComplementoEndereco(formatarResultado(imovelEconomia
											.getComplementoEndereco()));
							economiaPopupActionForm
									.setNumeroPontosUtilizacao(formatarResultado(""
											+ imovelEconomia
													.getNumeroPontosUtilizacao()));
							economiaPopupActionForm
									.setNumeroMorador(formatarResultado(""
											+ imovelEconomia.getNumeroMorador()));
							economiaPopupActionForm
									.setNumeroIptu(formatarResultado(""
											+ imovelEconomia.getNumeroIptu()));
							economiaPopupActionForm
									.setNumeroCelpe(formatarResultado(""
											+ imovelEconomia.getNumeroCelpe()));
							economiaPopupActionForm.setAreaConstruida(Util
									.formatarMoedaReal(imovelEconomia
											.getAreaConstruida()));
							economiaPopupActionForm.setIdCliente("");
							economiaPopupActionForm.setNomeCliente("");

							SimpleDateFormat dataFormatoAtual = new SimpleDateFormat(
									"dd/MM/yyyy");
							// joga em dataInicial a parte da data
							String dataAtual = dataFormatoAtual
									.format(new Date());

							economiaPopupActionForm
									.setDataInicioClienteImovelRelacao(dataAtual);

							// verifica se o im�vel � de tarifa social caso seja
							// desabilita alguns campos.
							if (imovelEconomia.getImovelSubcategoria()
									.getComp_id().getImovel().getImovelPerfil()
									.getId().equals(ImovelPerfil.TARIFA_SOCIAL)) {
								sessao.setAttribute("tarifaSocial", "1");
							} else {
								sessao.removeAttribute("tarifaSocial");
							}

							achou = true;
							break;
						}
					}
				} else {
					break;
				}
			}

		}

		// parametro que testa se dar� um reload(true) ou nao(false)
		httpServletRequest.setAttribute("testeInserir", new Boolean(false));
		Collection colecaoClientesImoveisEconomia = null;
		// HashSet verifica se existe objeto igual na collection
		if (sessao.getAttribute("colecaoClientesImoveisEconomia") != null) {
			colecaoClientesImoveisEconomia = (Collection) sessao
					.getAttribute("colecaoClientesImoveisEconomia");

		} else {
			colecaoClientesImoveisEconomia = new HashSet();
		}

		// caso o parametro de pesquisa enter que � colocado no jsp de
		// atualizar_economia_popup estiver nulo ent�o
		// n�o foi feita a pesquisa de enter e entra no if.
		if (httpServletRequest.getParameter("pesquisaEnter") == null
				|| httpServletRequest.getParameter("pesquisaEnter")
						.equalsIgnoreCase("")) {

			// Cria��o das cole��es
			Collection areasConstruidasFaixas = null;
			Collection clientesRelacoesTipos = null;

			// caso venha do jsp imovel_economia_fim_relacao_cliente e n�o entre
			// do if do codigoImovelEconomia que � onde o achou
			// fica true.
			if (!achou) {

				if (sessao.getAttribute("imovelEconomia") != null
						&& !sessao.getAttribute("imovelEconomia").equals("")) {
					imovelEconomia = (ImovelEconomia) sessao
							.getAttribute("imovelEconomia");
					// manda os parametros para o form
					economiaPopupActionForm
							.setComplementoEndereco(formatarResultado(imovelEconomia
									.getComplementoEndereco()));
					economiaPopupActionForm
							.setNumeroPontosUtilizacao(formatarResultado(""
									+ imovelEconomia
											.getNumeroPontosUtilizacao()));
					economiaPopupActionForm
							.setNumeroMorador(formatarResultado(""
									+ imovelEconomia.getNumeroMorador()));
					economiaPopupActionForm.setNumeroIptu(formatarResultado(""
							+ imovelEconomia.getNumeroIptu()));
					economiaPopupActionForm.setNumeroCelpe(formatarResultado(""
							+ imovelEconomia.getNumeroCelpe()));
					economiaPopupActionForm
							.setAreaConstruida(formatarResultado(""
									+ imovelEconomia.getAreaConstruida()));

					SimpleDateFormat dataFormatoAtual = new SimpleDateFormat(
							"dd/MM/yyyy");
					// joga em dataInicial a parte da data
					String dataAtual = dataFormatoAtual.format(new Date());

					economiaPopupActionForm
							.setDataInicioClienteImovelRelacao(dataAtual);

				}
			}

			// Verifica se o tipoConsulta � diferente de nulo ou vazio esse tipo
			// consulta vem do
			// cliente_resultado_pesquisa.jsp.
			// � feita essa verifica��o pois pode ser que ainda n�o tenha
			// feito a pesquisa de cliente.
			if (httpServletRequest.getParameter("tipoConsulta") == null
					|| httpServletRequest.getParameter("tipoConsulta").equals(
							"")) {

				// Filtro AreaConstruidaFaixa
				FiltroAreaConstruidaFaixa filtroAreaConstruidaFaixa = new FiltroAreaConstruidaFaixa(
						FiltroAreaConstruidaFaixa.MENOR_FAIXA);

				filtroAreaConstruidaFaixa
						.adicionarParametro(new ParametroSimples(
								FiltroAreaConstruidaFaixa.INDICADOR_USO,
								ConstantesSistema.INDICADOR_USO_ATIVO));
				areasConstruidasFaixas = fachada.pesquisar(
						filtroAreaConstruidaFaixa, AreaConstruidaFaixa.class
								.getName());

				// Filtro TipoClienteImovel
				FiltroClienteRelacaoTipo filtroClienteRelacaoTipo = new FiltroClienteRelacaoTipo(
						FiltroClienteTipo.DESCRICAO);
				filtroClienteRelacaoTipo
						.adicionarParametro(new ParametroSimples(
								FiltroClienteRelacaoTipo.INDICADOR_USO,
								ConstantesSistema.INDICADOR_USO_ATIVO));
				filtroClienteRelacaoTipo
						.adicionarParametro(new ParametroSimples(
								FiltroClienteRelacaoTipo.CLIENTE_RELACAO_TIPO_ID,
								ClienteRelacaoTipo.USUARIO,
								ParametroSimples.CONECTOR_OR));
				filtroClienteRelacaoTipo
						.adicionarParametro(new ParametroSimples(
								FiltroClienteRelacaoTipo.CLIENTE_RELACAO_TIPO_ID,
								ClienteRelacaoTipo.PROPRIETARIO));
				clientesRelacoesTipos = fachada.pesquisar(
						filtroClienteRelacaoTipo, ClienteRelacaoTipo.class
								.getName());
				// a cole��o de clientesImoveisTipos � obrigat�rio
				if (clientesRelacoesTipos == null
						|| clientesRelacoesTipos.equals("")) {

					throw new ActionServletException(
							"atencao.pesquisa.nenhumresultado", null,
							"cliente tipo");
				} else {

					if (economiaPopupActionForm.getTextoSelecionadoEconomia() == null
							|| economiaPopupActionForm
									.getTextoSelecionadoEconomia().equals("")) {
						economiaPopupActionForm
								.setTextoSelecionadoEconomia(((ClienteRelacaoTipo) ((List) clientesRelacoesTipos)
										.get(0)).getDescricao());
					}
				}

				if (imovelEconomia.getAreaConstruidaFaixa() != null
						&& !imovelEconomia.getAreaConstruidaFaixa().equals("")) {
					economiaPopupActionForm
							.setIdAreaConstruidaFaixa(imovelEconomia
									.getAreaConstruidaFaixa().getId()
									.toString());
				}
				// Envia os objetos no request
				sessao.setAttribute("areasConstruidasFaixas",
						areasConstruidasFaixas);

				sessao.setAttribute("clientesRelacoesTipos",
						clientesRelacoesTipos);
				// caso venha algum parametro do tipoConsulta ent�o
			} else {
				// Verifica se o tipo da consulta � de cliente
				// se for os parametros de enviarDadosParametros ser�o mandados
				// para
				// a pagina atualizar_economia_popup.jsp
				if (httpServletRequest.getParameter("tipoConsulta").equals(
						"cliente")) {

					economiaPopupActionForm.setIdCliente(httpServletRequest
							.getParameter("idCampoEnviarDados"));

					economiaPopupActionForm.setNomeCliente(httpServletRequest
							.getParameter("descricaoCampoEnviarDados"));

				}
			}

			// Realiza a pesquisa de Cliente se necess�rio (caso o usu�rio
			// informou um c�digo do cliente e teclou <enter>)
		} else {

			Collection clientes;
			// Cria��o dos objetos
			String idCliente = null;
			// Cliente cliente = null;

			idCliente = economiaPopupActionForm.getIdCliente();
			FiltroCliente filtroCliente = new FiltroCliente();

			// Adiciona parametro
			filtroCliente.adicionarParametro(new ParametroSimples(
					FiltroCliente.ID, idCliente));
			// Realiza a pesquisa de cliente
			clientes = fachada
					.pesquisar(filtroCliente, Cliente.class.getName());
			if (clientes != null && !clientes.isEmpty()) {
				// O cliente foi encontrado
				economiaPopupActionForm
						.setIdCliente(((Cliente) ((List) clientes).get(0))
								.getId().toString());
				economiaPopupActionForm
						.setNomeCliente(((Cliente) ((List) clientes).get(0))
								.getNome());
				// cliente = new Cliente();
				/* cliente = (Cliente) */clientes.iterator().next();

			} else {
				httpServletRequest.setAttribute("codigoClienteNaoEncontrado",
						"true");
				economiaPopupActionForm.setNomeCliente("");
			}
		}

		sessao.setAttribute("colecaoClientesImoveisEconomia",
				colecaoClientesImoveisEconomia);

		if (httpServletRequest.getParameter("limpa") != null) {
			economiaPopupActionForm
					.setClienteRelacaoTipo(ConstantesSistema.NUMERO_NAO_INFORMADO
							+ "");
		}

		return retorno;
	}

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param parametro
	 *            Descri��o do par�metro
	 * @return Descri��o do retorno
	 */
	private String formatarResultado(String parametro) {
		if (parametro != null && !parametro.trim().equals("")) {
			if (parametro.equals("null")) {
				return "";
			} else {
				return parametro.trim();
			}
		} else {
			return "";
		}
	}

}
