package gcom.gui.cadastro.endereco;

import gcom.cadastro.endereco.Cep;
import gcom.cadastro.endereco.FiltroCep;
import gcom.cadastro.geografico.FiltroBairro;
import gcom.cadastro.geografico.FiltroMunicipio;
import gcom.cadastro.geografico.Municipio;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action que define o processamento da p�gina de pesquisa de Logradouro
 * 
 * @author rossiter
 */
public class PesquisarLogradouroAction extends GcomAction {

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

		// Seta o retorno
		ActionForward retorno = actionMapping
				.findForward("pesquisarLogradouro");

		// Obt�m a inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		// Obt�m a sess�o
		HttpSession sessao = httpServletRequest.getSession(false);

		ExibirPesquisarLogradouroActionForm exibirPesquisarLogradouroActionForm = (ExibirPesquisarLogradouroActionForm) actionForm;

		// Carregando as vari�veis do action form
		String codigoMunicipioJSP = exibirPesquisarLogradouroActionForm
				.getCodigoMunicipio();
		String nomeJSP = exibirPesquisarLogradouroActionForm.getNome();
		String nomepopularJSP = exibirPesquisarLogradouroActionForm
				.getNomePopular();
		String logradouroTipoJSP = exibirPesquisarLogradouroActionForm
				.getTipoLogradouro();
		String logradouroTituloJSP = exibirPesquisarLogradouroActionForm
				.getTitulo();
		String cep = exibirPesquisarLogradouroActionForm.getCep();
		String codigoBairroJSP = exibirPesquisarLogradouroActionForm
				.getCodigoBairro();
		String tipoPesquisa = exibirPesquisarLogradouroActionForm
				.getTipoPesquisa();
		String tipoPesquisaPopular = exibirPesquisarLogradouroActionForm
				.getTipoPesquisaPopular();

		// Criando o filtro e o objeto de retorno da pesquisa de munic�pio
		FiltroMunicipio filtroMunicipio = new FiltroMunicipio();
		FiltroBairro filtroBairro = new FiltroBairro();
		Collection colecaoMunicipio = null;
		//Collection colecaoBairro = null;

		// Validando o campo c�digo e o campo nome do munic�pio
		if (codigoMunicipioJSP != null
				&& !codigoMunicipioJSP.equalsIgnoreCase("")) {

			filtroMunicipio.adicionarParametro(new ParametroSimples(
					FiltroMunicipio.ID, new Integer(codigoMunicipioJSP)));

			// Validando as informa��es referentes ao munic�pio
			// ----------------------------------------------------------------------
			colecaoMunicipio = fachada.pesquisar(filtroMunicipio,
					Municipio.class.getName());

			if (colecaoMunicipio == null || colecaoMunicipio.isEmpty()) {
				// Nenhum munic�pio cadastrado de acordo com os par�metros
				// passados
				throw new ActionServletException(
						"atencao.pesquisa.municipio_inexistente");
			}

		} else {
			throw new ActionServletException(
					"atencao.pesquisa.logradouro_codigo_nome_municipio");
		}

		// Validando o campo c�digo e o campo nome do Bairro
		if (codigoBairroJSP != null && !codigoBairroJSP.equalsIgnoreCase("")) {

			filtroBairro.adicionarParametro(new ParametroSimples(
					FiltroBairro.ID, new Integer(codigoBairroJSP)));

			// Validando as informa��es referentes ao munic�pio
			// ----------------------------------------------------------------------
			//colecaoBairro = fachada.pesquisar(filtroBairro, Bairro.class
			//		.getName());

			if (colecaoMunicipio == null || colecaoMunicipio.isEmpty()) {
				// Nenhum munic�pio cadastrado de acordo com os par�metros
				// passados
				throw new ActionServletException(
						"atencao.pesquisa.bairro_inexistente");
			}

		}

		Collection colecaoLogradouro = null;
		/**
		 * 
		 * //Criando o filtro e o objeto de retorno da pesquisa FiltroLogradouro
		 * filtroLogradouro = new FiltroLogradouro(FiltroLogradouro.NOME);
		 * FiltroLogradouroCep filtroLogradouroCep = new
		 * FiltroLogradouroCep(FiltroLogradouroCep.NOME_LOGRADOURO);
		 * 
		 * 
		 * //Objetos que ser�o retornados pelo hibernate
		 * filtroLogradouro.adicionarCaminhoParaCarregamentoEntidade("municipio.nome");
		 * filtroLogradouro.adicionarCaminhoParaCarregamentoEntidade("logradouroTipo.descricaoAbreviada");
		 * filtroLogradouro.adicionarCaminhoParaCarregamentoEntidade("logradouroTitulo.descricaoAbreviada");
		 * 
		 * Collection colecaoFiltroLogradouroBairro = null;
		 */

		boolean peloMenosUmParametroInformado = false;

		// Validando os campos do formulario

		// Se o parametro cep estiver preenchido pesquisa pela tabela
		// logradouro_cep
		// sen�o pesquisa pela tabela logradouro
		if (cep == null || cep.trim().equals("")) {

			if (codigoMunicipioJSP != null
					&& !codigoMunicipioJSP.equalsIgnoreCase("")) {

				peloMenosUmParametroInformado = true;

				/**
				 * filtroLogradouro.adicionarParametro(new ParametroSimples(
				 * FiltroLogradouro.ID_MUNICIPIO, new Integer(
				 * codigoMunicipioJSP)));
				 */
			}

			// Validando o campo nome do logradouro
			if (nomeJSP != null && !nomeJSP.equalsIgnoreCase("")) {
				peloMenosUmParametroInformado = true;
				/**
				 * filtroLogradouro.adicionarParametro(new ComparacaoTexto(
				 * FiltroLogradouro.NOME, nomeJSP));
				 */
			}

			// Validando o campo nome do logradouro
			if (nomepopularJSP != null && !nomepopularJSP.equalsIgnoreCase("")) {
				peloMenosUmParametroInformado = true;
				// filtroLogradouro.adicionarParametro(new ComparacaoTexto(
				// FiltroLogradouro.NOME_POPULAR, nomepopularJSP));
			}

			// Validando o campo tipo do logradouro
			if (logradouroTipoJSP != null
					&& !logradouroTipoJSP.equalsIgnoreCase(String
							.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))) {
				peloMenosUmParametroInformado = true;
				// filtroLogradouro.adicionarParametro(new ParametroSimples(
				// FiltroLogradouro.ID_LOGRADOUROTIPO, logradouroTipoJSP));
			}

			// Validando o campo titulo do logradouro
			if (logradouroTituloJSP != null
					&& !logradouroTituloJSP.equalsIgnoreCase(String
							.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))) {
				peloMenosUmParametroInformado = true;
				// filtroLogradouro.adicionarParametro(new ParametroSimples(
				// FiltroLogradouro.ID_LOGRADOUROTITULO, logradouroTituloJSP));
			}
			/**
			 * if( sessao.getAttribute("indicadorUsoTodos") == null ){
			 * filtroLogradouro.adicionarParametro(new ParametroSimples(
			 * FiltroLogradouro.INDICADORUSO,
			 * ConstantesSistema.INDICADOR_USO_ATIVO)); }
			 */

			// Erro caso o usu�rio mandou filtrar sem nenhum par�metro
			if (!peloMenosUmParametroInformado) {
				throw new ActionServletException(
						"atencao.filtro.nenhum_parametro_informado");
			}
			/**
			 * //Retorna o(s) logradouro(s) colecaoLogradouro =
			 * fachada.pesquisar(filtroLogradouro, Logradouro.class.getName());
			 * 
			 */
		} else {

			// Validando as informa��es referentes ao cep
			// ----------------------------------------------------------------------
			FiltroCep filtroCep = new FiltroCep();
			filtroCep.adicionarParametro(new ParametroSimples(FiltroCep.CODIGO,
					cep));

			Collection colecaoCep = fachada.pesquisar(filtroCep, Cep.class
					.getName());
			if (colecaoCep.isEmpty()) {
				// Nenhum cep cadastrado de acordo com os par�metros passados
				throw new ActionServletException(
						"atencao.pesquisa.cep_inexistente");
			}

			if (codigoMunicipioJSP != null
					&& !codigoMunicipioJSP.equalsIgnoreCase("")) {

				peloMenosUmParametroInformado = true;

				// filtroLogradouroCep.adicionarParametro(new ParametroSimples(
				// FiltroLogradouroCep.ID_MUNICIPIO_LOGRADOURO, new Integer(
				// codigoMunicipioJSP)));
			}

			// Validando o campo nome do logradouro
			if (nomeJSP != null && !nomeJSP.equalsIgnoreCase("")) {
				peloMenosUmParametroInformado = true;
				// filtroLogradouroCep.adicionarParametro(new ComparacaoTexto(
				// FiltroLogradouroCep.NOME_LOGRADOURO, nomeJSP));
			}

			// Validando o campo tipo do logradouro
			if (logradouroTipoJSP != null
					&& !logradouroTipoJSP.equalsIgnoreCase(String
							.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))) {
				peloMenosUmParametroInformado = true;
				// filtroLogradouroCep.adicionarParametro(new ParametroSimples(
				// FiltroLogradouroCep.ID_LOGRADOUROTIPO_LOGRADOURO,
				// logradouroTipoJSP));
			}

			// Validando o campo titulo do logradouro
			if (logradouroTituloJSP != null
					&& !logradouroTituloJSP.equalsIgnoreCase(String
							.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))) {
				peloMenosUmParametroInformado = true;
				// filtroLogradouroCep.adicionarParametro(new ParametroSimples(
				// FiltroLogradouroCep.ID_LOGRADOUROTITULO_LOGRADOURO,
				// logradouroTituloJSP));
			}
			/**
			 * if( sessao.getAttribute("indicadorUsoTodos") == null ){
			 * filtroLogradouro.adicionarParametro(new ParametroSimples(
			 * FiltroLogradouroCep.INDICADOR_USO,
			 * ConstantesSistema.INDICADOR_USO_ATIVO)); }
			 */

			// Erro caso o usu�rio mandou filtrar sem nenhum par�metro
			if (!peloMenosUmParametroInformado) {
				throw new ActionServletException(
						"atencao.filtro.nenhum_parametro_informado");
			}
			/**
			 * //Retorna o(s) logradouro(s) colecaoLogradouro =
			 * fachada.pesquisar(filtroLogradouroCep,
			 * LogradouroCep.class.getName());
			 */
		}

		//
		// ----------------------------------------------------------------------

		// 1� Passo - Pegar o total de registros atrav�s de um count da consulta
		// que aparecer� na tela
		
		Integer totalRegistros = fachada.pesquisarLogradouroCompletoCount(
				codigoMunicipioJSP, codigoBairroJSP, nomeJSP, nomepopularJSP,
				logradouroTipoJSP, logradouroTituloJSP, cep, "", "",
				tipoPesquisa, tipoPesquisaPopular);
		if (totalRegistros.intValue() <= 0 || totalRegistros == null) {
			throw new ActionServletException("atencao.pesquisa.nenhumresultado");
		}

		// 2� Passo - Chamar a fun��o de Pagina��o passando o total de registros
		retorno = this.controlarPaginacao(httpServletRequest, retorno,
				totalRegistros);

		// 3� Passo - Obter a cole��o da consulta que aparecer� na tela passando
		// o numero de paginas
		// da pesquisa que est� no request
		colecaoLogradouro = fachada.pesquisarLogradouroCompleto(
				codigoMunicipioJSP, codigoBairroJSP, nomeJSP, nomepopularJSP,
				logradouroTipoJSP, logradouroTituloJSP, cep, "", "",
				tipoPesquisa, tipoPesquisaPopular, (Integer) httpServletRequest
						.getAttribute("numeroPaginasPesquisa"));

		sessao.setAttribute("logradouros", colecaoLogradouro);

		sessao.setAttribute("tipoReultado", "logradouro");

		// devolve o mapeamento de retorno

		sessao.removeAttribute("indicadorUso");

		return retorno;
	}

}
