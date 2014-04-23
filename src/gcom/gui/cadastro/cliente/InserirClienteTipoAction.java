package gcom.gui.cadastro.cliente;

import gcom.cadastro.cliente.ClienteTipo;
import gcom.cadastro.cliente.EsferaPoder;
import gcom.cadastro.cliente.FiltroEsferaPoder;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class InserirClienteTipoAction extends GcomAction {

	/**
	 * Este caso de uso permite inserir um Cliente Tipo
	 * 
	 * [UC????] Inserir Cliente Tipo
	 * 
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 * @author Thiago Ten�rio
	 */

	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Obt�m a inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		// Obt�m a sess�o
		HttpSession sessao = httpServletRequest.getSession(false);

		// Usuario logado no sistema
		Usuario usuarioLogado = (Usuario) sessao
				.getAttribute(Usuario.USUARIO_LOGADO);

		InserirClienteTipoActionForm inserirClienteTipoActionForm = (InserirClienteTipoActionForm) actionForm;

		String descricao = inserirClienteTipoActionForm.getDescricao();
		String tipoPessoa = inserirClienteTipoActionForm.getTipoPessoa();
		// String esferaPoder = inserirClienteTipoActionForm.getEsferaPoder();

		ClienteTipo clienteTipoInserir = new ClienteTipo();

		sessao.removeAttribute("tipoPesquisaRetorno");

		// Esfera Poder

		if (Util.validarNumeroMaiorQueZERO(inserirClienteTipoActionForm
				.getEsferaPoder())) {
			// Constr�i o filtro para pesquisa do servi�o tipo refer�ncia
			FiltroEsferaPoder filtroEsferaPoder = new FiltroEsferaPoder();
			filtroEsferaPoder.adicionarParametro(new ParametroSimples(
					FiltroEsferaPoder.ID, inserirClienteTipoActionForm
							.getEsferaPoder()));

			Collection colecaoEsferaPoder = (Collection) fachada.pesquisar(
					filtroEsferaPoder, EsferaPoder.class.getName());

			// setando
			clienteTipoInserir.setEsferaPoder((EsferaPoder) colecaoEsferaPoder
					.iterator().next());
		}

		// A Descri��o � obrigat�rio.
		if (descricao == null || descricao.equalsIgnoreCase("")) {
			throw new ActionServletException("atencao.required", null,
					"Descri��o");
		}

		// Esfera Poder � obrigat�rio.
		if (tipoPessoa == null || tipoPessoa.equals("")) {
			throw new ActionServletException("atencao.required", null,
					"Tipo de Pessoa");
		}

		//Setando os Objetos no Banco
		clienteTipoInserir.setDescricao(descricao);
		clienteTipoInserir.setIndicadorPessoaFisicaJuridica(new Short(
				tipoPessoa));

		// Ultima altera��o
		clienteTipoInserir.setUltimaAlteracao(new Date());

		// Indicador de uso
		Short iu = 1;
		clienteTipoInserir.setIndicadorUso(iu);

		Integer idClienteTipo = null;

		idClienteTipo = fachada.inserirClienteTipo(clienteTipoInserir,
				usuarioLogado);

		montarPaginaSucesso(httpServletRequest, "Tipo de Cliente de c�digo  "
				+ clienteTipoInserir.getId() + " inserida com sucesso.",
				"Inserir outro Tipo de Cliente",
				"exibirInserirClienteTipoAction.do?menu=sim",
				"exibirAtualizarClienteTipoAction.do?idRegistroAtualizacao="
						+ idClienteTipo, "Atualizar Tipo de Cliente");

		// devolve o mapeamento de retorno
		return retorno;
	}
}
