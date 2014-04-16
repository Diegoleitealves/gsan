package gcom.gui.relatorio.cobranca;

import gcom.cadastro.empresa.Empresa;
import gcom.cadastro.empresa.FiltroEmpresa;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.FiltroUnidadeNegocio;
import gcom.cadastro.localidade.Localidade;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.cobranca.RelatorioPagamentosContasCobrancaEmpresa;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC0868]-Gerar Relat�rio de Pagamentos das Contas em Cobranca por EmpresaS
 * 
 * @author R�mulo Aur�lio
 * @date 08/01/2009
 */
public class GerarRelatorioPagamentosContasCobrancaEmpresaAction extends
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

		GerarRelatorioPagamentosContasCobrancaEmpresaActionForm form = (GerarRelatorioPagamentosContasCobrancaEmpresaActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		// Inicio da parte que vai mandar os parametros para o relat�rio

		Empresa empresa = new Empresa();

		String idEmpresa = form.getIdEmpresa();

		Integer gerenciaRegional = null;
		Integer localidade = null;
		Integer unidadeNegocio = null;

		String opcaoTotalizacao = form.getOpcaoTotalizacao();

		if (opcaoTotalizacao == null || opcaoTotalizacao.equalsIgnoreCase("")) {
			throw new ActionServletException("atencao.required", null,
					"Op��o de Totaliza��o ");
		}

		if (opcaoTotalizacao.trim().equals("gerenciaRegional")) {
			
			gerenciaRegional = Integer.parseInt(form.getGerenciaRegionalId());
			if (gerenciaRegional == null
					|| gerenciaRegional
							.equals(ConstantesSistema.NUMERO_NAO_INFORMADO)) {
				throw new ActionServletException("atencao.required", null,
						"Ger�ncia Regional");
			}
		} else if (opcaoTotalizacao.trim().equals("gerenciaRegionalLocalidade")) {
			gerenciaRegional = Integer.parseInt(form
					.getGerenciaRegionalporLocalidadeId());
			if (gerenciaRegional == null
					|| gerenciaRegional
							.equals(ConstantesSistema.NUMERO_NAO_INFORMADO)) {
				throw new ActionServletException("atencao.required", null,
						"Ger�ncia Regional");
			}
		}

		if (opcaoTotalizacao.trim().equals("localidade")) {
			String codigoLocalidade = form.getCodigoLocalidade();

			if (codigoLocalidade == null
					|| codigoLocalidade.equalsIgnoreCase("")) {
				throw new ActionServletException("atencao.required", null,
						"Localidade ");

			} else {
				pesquisarLocalidade(codigoLocalidade, httpServletRequest);
			}

			localidade = Integer.parseInt(codigoLocalidade);
		}

		if (opcaoTotalizacao.trim().equals("unidadeNegocio")) {
			String idUnidadeNegocio = form.getUnidadeNegocioId();

			if (idUnidadeNegocio == null
					|| idUnidadeNegocio
							.equals(""+ConstantesSistema.NUMERO_NAO_INFORMADO)) {
				throw new ActionServletException("atencao.required", null,
						"Unidade de Neg�cio ");

			}

			unidadeNegocio = Integer.parseInt(idUnidadeNegocio);
		}
		


		Integer referenciaInicialFormatada = null;

		Integer referenciaFinalFormatada = null;

		RelatorioPagamentosContasCobrancaEmpresa relatorioPagamentosContasCobrancaEmpresa = new RelatorioPagamentosContasCobrancaEmpresa(
				(Usuario) (httpServletRequest.getSession(false))
						.getAttribute("usuarioLogado"));

		if (idEmpresa != null
				&& !idEmpresa.trim().equals(
						"" + ConstantesSistema.NUMERO_NAO_INFORMADO)) {
			FiltroEmpresa filtroEmpresa = new FiltroEmpresa();
			filtroEmpresa.adicionarParametro(new ParametroSimples(
					FiltroUnidadeNegocio.ID, idEmpresa));

			Collection colecaoEmpresa = fachada.pesquisar(filtroEmpresa,
					Empresa.class.getName());

			if (colecaoEmpresa != null && !colecaoEmpresa.isEmpty()) {
				empresa = (Empresa) Util.retonarObjetoDeColecao(colecaoEmpresa);
			}

		}

		String dataInicialForm = form.getPeriodoComandoInicial();
		String dataFinalForm = form.getPeriodoComandoFinal();

		if (dataInicialForm != null && !dataInicialForm.equalsIgnoreCase("")) {
			referenciaInicialFormatada = Util
					.formatarMesAnoComBarraParaAnoMes(dataInicialForm);
			referenciaFinalFormatada = Util
					.formatarMesAnoComBarraParaAnoMes(dataFinalForm);
			if (referenciaInicialFormatada.compareTo(referenciaFinalFormatada) > 0) {
				throw new ActionServletException(
						"atencao.referencia.final.menor.referencia.inicial");
			}

			String opcaoRelatorio = form.getOpcaoRelatorio();

			relatorioPagamentosContasCobrancaEmpresa.addParametro(
					"opcaoTotalizacao", opcaoTotalizacao);

			relatorioPagamentosContasCobrancaEmpresa.addParametro("localidade",
					localidade);

			relatorioPagamentosContasCobrancaEmpresa.addParametro(
					"gerenciaRegional", gerenciaRegional);

			relatorioPagamentosContasCobrancaEmpresa.addParametro(
					"unidadeNegocio", unidadeNegocio);

			relatorioPagamentosContasCobrancaEmpresa.addParametro("empresa",
					empresa);

			relatorioPagamentosContasCobrancaEmpresa.addParametro(
					"opcaoRelatorio", opcaoRelatorio);

			relatorioPagamentosContasCobrancaEmpresa.addParametro(
					"referenciaPagamentoInicial", referenciaInicialFormatada);

			relatorioPagamentosContasCobrancaEmpresa.addParametro(
					"referenciaPagamentoFinal", referenciaFinalFormatada);
		}

		// Verifica se a pesquisa retorno algum resultado
		int qtdeResultados = fachada
				.pesquisarDadosGerarRelatorioPagamentosContasCobrancaEmpresaCount(
						new Integer(idEmpresa), referenciaInicialFormatada,
						referenciaFinalFormatada);

		if (qtdeResultados == 0) {
			// Caso a pesquisa n�o retorne nenhum resultado comunica ao
			// usu�rio;
			throw new ActionServletException("atencao.pesquisa.nenhumresultado");

		}
		// Flag para tela de sucesso apos a tela de espera de processamento de relatorio
		httpServletRequest.setAttribute("telaSucessoRelatorio",true);

		// Fim da parte que vai mandar os parametros para o relat�rio
		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");

		if (tipoRelatorio == null) {
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		relatorioPagamentosContasCobrancaEmpresa.addParametro(
				"tipoFormatoRelatorio", Integer.parseInt(tipoRelatorio));

		retorno = processarExibicaoRelatorio(
				relatorioPagamentosContasCobrancaEmpresa, tipoRelatorio,
				httpServletRequest, httpServletResponse, actionMapping);
		// devolve o mapeamento contido na vari�vel retorno
		return retorno;
	}

	/**
	 * Pesquisa uma localidade informada e prepara os dados para exibi��o na
	 * tela
	 * 
	 */
	private void pesquisarLocalidade(String idLocalidade,
			HttpServletRequest httpServletRequest) {

		Fachada fachada = Fachada.getInstancia();

		// Pesquisa a localidade na base
		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
		filtroLocalidade.adicionarParametro(new ParametroSimples(
				FiltroLocalidade.ID, idLocalidade));

		Collection<Localidade> localidadePesquisada = fachada.pesquisar(
				filtroLocalidade, Localidade.class.getName());

		// Se nenhuma localidade for encontrada a mensagem � enviada para a
		// p�gina
		if (localidadePesquisada == null || localidadePesquisada.isEmpty()) {
			// [FS0001 - Verificar exist�ncia de dados]
			httpServletRequest.setAttribute("codigoLocalidade", "");
			httpServletRequest.setAttribute("descricaoLocalidade",
					"Localidade Inexistente".toUpperCase());
		}

		// obtem o imovel pesquisado
		if (localidadePesquisada != null && !localidadePesquisada.isEmpty()) {
			Localidade localidade = localidadePesquisada.iterator().next();
			// Manda a Localidade pelo request
			httpServletRequest.setAttribute("codigoLocalidade", idLocalidade);
			httpServletRequest.setAttribute("descricaoLocalidade", localidade
					.getDescricao());
		}

	}

}
