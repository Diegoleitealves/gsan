package gcom.gui.cadastro.endereco;

import gcom.cadastro.endereco.FiltroLogradouro;
import gcom.cadastro.endereco.Logradouro;
import gcom.cadastro.endereco.LogradouroTipo;
import gcom.cadastro.endereco.LogradouroTitulo;
import gcom.cadastro.geografico.FiltroMunicipio;
import gcom.cadastro.geografico.Municipio;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.interceptor.RegistradorOperacao;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioAcao;
import gcom.seguranca.acesso.usuario.UsuarioAcaoUsuarioHelper;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ParametroNulo;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action respons�vel pela inser��o do logradouro
 * 
 * @author S�vio Luiz, Raphael Rossiter
 * @created 15 de Julho de 2005
 */
public class InserirLogradouroAction extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		ActionForward retorno = actionMapping.findForward("telaSucesso");

		LogradouroActionForm logradouroActionForm = (LogradouroActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);
		
        Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

		// Recupera a vari�vel para indicar se o usu�rio apertou o bot�o de
		// confirmar da tela de
		// confirma��o do wizard
		String confirmado = httpServletRequest.getParameter("confirmado");

		RegistradorOperacao registradorOperacao = new RegistradorOperacao(
				Operacao.OPERACAO_LOGRADOURO_INSERIR,
				new UsuarioAcaoUsuarioHelper(usuario,
						UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		/*
		 * [UC0107] Registrar Transa��o Operacao operacao = new Operacao();
		 * operacao.setId(Operacao.OPERACAO_LOGRADOURO_INSERIR);
		 * 
		 * OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		 * operacaoEfetuada.setOperacao(operacao);
		 */

		// Bairro bairro = null;
		Municipio municipio = null;

		String idMunicipio = (String) logradouroActionForm.getIdMunicipio();
		// String codigoBairro = (String)
		// logradouroActionForm.getCodigoBairro();

		if (idMunicipio != null && !idMunicipio.trim().equals("")
				&& Integer.parseInt(idMunicipio) > 0) {

			FiltroMunicipio filtroMunicipio = new FiltroMunicipio();

			filtroMunicipio.adicionarParametro(new ParametroSimples(
					FiltroMunicipio.ID, idMunicipio));

			filtroMunicipio.adicionarParametro(new ParametroSimples(
					FiltroMunicipio.INDICADOR_USO,
					ConstantesSistema.INDICADOR_USO_ATIVO));

			Collection municipioEncontrado = fachada.pesquisar(filtroMunicipio,
					Municipio.class.getName());

			if (municipioEncontrado != null && !municipioEncontrado.isEmpty()) {
				municipio = ((Municipio) ((List) municipioEncontrado).get(0));

			} else {
				throw new ActionServletException(
						"atencao.pesquisa.nenhumresultado", null, "munic�pio");
			}
		}

		// Verifica se o c�digo foi digitado
		/*
		 * if (codigoBairro != null && !codigoBairro.trim().equals("") &&
		 * Integer.parseInt(codigoBairro) > 0) { FiltroBairro filtroBairro = new
		 * FiltroBairro();
		 * 
		 * filtroBairro.adicionarParametro(new ParametroSimples(
		 * FiltroBairro.CODIGO, codigoBairro));
		 * filtroBairro.adicionarParametro(new ParametroSimples(
		 * FiltroBairro.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
		 *  // verifica se o bairro pesquisado � de um municipio existente if
		 * (idMunicipio != null && !idMunicipio.trim().equals("") &&
		 * Integer.parseInt(idMunicipio) > 0) {
		 * 
		 * filtroBairro.adicionarParametro(new ParametroSimples(
		 * FiltroBairro.MUNICIPIO_ID, idMunicipio)); }
		 * 
		 * Collection bairroEncontrado = fachada.pesquisar(filtroBairro,
		 * Bairro.class.getName());
		 * 
		 * if (bairroEncontrado != null && !bairroEncontrado.isEmpty()) { //O
		 * bairro foi encontrado bairro = ((Bairro) ((List)
		 * bairroEncontrado).get(0));
		 *  } else { throw new ActionServletException(
		 * "atencao.pesquisa.nenhumresultado", null, "bairro"); } }
		 */

		LogradouroTipo logradouroTipo = new LogradouroTipo();

		if (logradouroActionForm.getIdTipo() != null
				&& !logradouroActionForm.getIdTipo().equals(0)) {

			logradouroTipo.setId(new Integer(""
					+ logradouroActionForm.getIdTipo()));
		} else {
			throw new ActionServletException("atencao.required", null, "Tipo");
		}

		LogradouroTitulo logradouroTitulo = null;

		if (logradouroActionForm.getIdTitulo() != null
				&& !logradouroActionForm.getIdTitulo().equals(0)) {

			logradouroTitulo = new LogradouroTitulo();
			logradouroTitulo.setId(new Integer(""
					+ logradouroActionForm.getIdTitulo()));
		}

		Short indicadorDeUso = ConstantesSistema.INDICADOR_USO_ATIVO;

		Collection colecaoBairros = (Collection) sessao
				.getAttribute("colecaoBairrosSelecionadosUsuario");
		Collection colecaoCeps = (Collection) sessao
				.getAttribute("colecaoCepSelecionadosUsuario");

		if (colecaoBairros == null || colecaoBairros.isEmpty()) {
			throw new ActionServletException("atencao.required", null,
					"Bairro(s)");
		}

		if (colecaoCeps == null || colecaoCeps.isEmpty()) {
			throw new ActionServletException("atencao.required", null, "CEP(s)");
		}

		Logradouro logradouro = new Logradouro(logradouroActionForm.getNome(),
				logradouroActionForm.getNomePopular(), indicadorDeUso,
				new Date(), municipio, logradouroTitulo, logradouroTipo);

		/*
		 * [UC0107] Registrar Transa��o
		 * logradouro.setOperacaoEfetuada(operacaoEfetuada);
		 * logradouro.adicionarUsuario(Usuario.USUARIO_TESTE,
		 * UsuarioAcao.USUARIO_ACAO_TESTE);
		 */

		registradorOperacao.registrarOperacao(logradouro);

		FiltroLogradouro filtroLogradouro = new FiltroLogradouro();

		filtroLogradouro.adicionarParametro(new ParametroSimples(
				FiltroLogradouro.ID_LOGRADOUROTIPO, logradouro
						.getLogradouroTipo().getId()));

		filtroLogradouro.adicionarParametro(new ParametroSimples(
				FiltroLogradouro.ID_MUNICIPIO, logradouro.getMunicipio()
						.getId()));

		filtroLogradouro.adicionarParametro(new ParametroSimples(
				FiltroLogradouro.NOME, logradouro.getNome()));

		if (logradouro.getLogradouroTitulo() == null
				|| logradouro.getLogradouroTitulo().equals("")) {
			filtroLogradouro.adicionarParametro(new ParametroNulo(
					FiltroLogradouro.ID_LOGRADOUROTITULO));
		} else {
			filtroLogradouro.adicionarParametro(new ParametroSimples(
					FiltroLogradouro.ID_LOGRADOUROTITULO, logradouro
							.getLogradouroTitulo().getId()));
		}

		Collection logradouros = fachada.pesquisar(filtroLogradouro,
				Logradouro.class.getName());

		if (logradouros != null && !logradouros.isEmpty()) {
			if (confirmado == null || !confirmado.trim().equalsIgnoreCase("ok")) {

				httpServletRequest.setAttribute("caminhoActionConclusao",
						"/gsan/inserirLogradouroAction.do");
				// Monta a p�gina de confirma��o para perguntar se o usu�rio
				// quer inserir
				// o logradouro cadastrado com este Tipo, T�tulo e Nome para
				// este Munic�pio
				return montarPaginaConfirmacao(
						"atencao.logradouro_ja_existente.confirmacao",
						httpServletRequest, actionMapping);
			}
		}

		Integer codigoLogradouro = fachada.inserirLogradouro(logradouro,
				colecaoBairros, colecaoCeps);

		montarPaginaSucesso(httpServletRequest, "Logradouro de c�digo "
				+ codigoLogradouro + " inserido com sucesso.",
				"Inserir outro Logradouro",
				"exibirInserirLogradouroAction.do?menu=sim",
				"exibirAtualizarLogradouroAction.do?idRegistroAtualizacao="
						+ codigoLogradouro, "Atualizar Logradouro Inserido");

		return retorno;
	}
}
