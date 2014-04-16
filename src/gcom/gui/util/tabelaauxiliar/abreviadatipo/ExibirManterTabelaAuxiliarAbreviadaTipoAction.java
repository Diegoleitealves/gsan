package gcom.gui.util.tabelaauxiliar.abreviadatipo;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.operacional.FiltroSistemaAbastecimento;
import gcom.operacional.SistemaAbastecimento;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ParametroSimples;
import gcom.util.tabelaauxiliar.abreviadatipo.FiltroTabelaAuxiliarAbreviadaTipo;
import gcom.util.tabelaauxiliar.abreviadatipo.TabelaAuxiliarAbreviadaTipo;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <Esse componente serve para SetorAbastecimento e ZonaAbastecimento, sendo o
 * tipo SistemaAbastecimento>
 * 
 * @author Administrador
 * @created 11 de Fevereiro de 2005
 */
public class ExibirManterTabelaAuxiliarAbreviadaTipoAction extends GcomAction {
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

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping
				.findForward("manterTabelaAuxiliarAbreviadaTipo");

		// Obt�m a instancia da fachada
		Fachada fachada = Fachada.getInstancia();

		// Cria a cole��o de tabelas auxiliares
		Collection tabelasAuxiliaresAbreviadasTipo = new ArrayList();

		// Obt�m a inst�ncia da sess�o
		HttpSession sessao = httpServletRequest.getSession(false);

		// Obt�m o nome da tela passado no get do request
		String tela = httpServletRequest.getParameter("tela");

		// Declara��o de objetos e tipos primitivos
		String titulo = null;
//		TabelaAuxiliarAbreviadaTipo tabelaAuxiliarAbreviadaTipo = null;
		int tamMaxCampoDescricao = 20;
		int tamMaxCampoDescricaoAbreviada = 6;
		String pacoteNomeObjeto = (String) sessao
				.getAttribute("pacoteNomeObjeto");

		String descricao = "descricao";
		String tituloTipo = null;

		if (sessao.getAttribute("titulo") != null) {
			titulo = (String) sessao.getAttribute("titulo");
		}

		// Verifica se o exibir manter foi chamado da tela de filtro
		if (tela != null) {
			tela = (String) sessao.getAttribute("tela");
		}

		// Parte da verifica��o do filtro
		FiltroTabelaAuxiliarAbreviadaTipo filtroTabelaAuxiliarAbreviadaTipo = null;

		DadosTelaTabelaAuxiliarAbreviadaTipo dados = DadosTelaTabelaAuxiliarAbreviadaTipo
				.obterDadosTelaTabelaAuxiliar(tela);

		if (dados.getNomeParametroFuncionalidade().equals("setorAbastecimento")) {

			Collection colecaoObject = new ArrayList();

			FiltroSistemaAbastecimento filtro = new FiltroSistemaAbastecimento();
			filtro.adicionarParametro(new ParametroSimples(
					FiltroSistemaAbastecimento.INDICADOR_USO,
					ConstantesSistema.INDICADOR_USO_ATIVO));

			colecaoObject = this.getFachada().pesquisar(filtro,
					SistemaAbastecimento.class.getName());

			sessao.setAttribute("colecaoObject", colecaoObject);
		}

		if (dados.getNomeParametroFuncionalidade().equals("zonaAbastecimento")) {

			Collection colecaoObject = new ArrayList();

			FiltroSistemaAbastecimento filtro = new FiltroSistemaAbastecimento();
			filtro.adicionarParametro(new ParametroSimples(
					FiltroSistemaAbastecimento.INDICADOR_USO,
					ConstantesSistema.INDICADOR_USO_ATIVO));

			colecaoObject = this.getFachada().pesquisar(filtro,
					SistemaAbastecimento.class.getName());

			sessao.setAttribute("colecaoObject", colecaoObject);
		}

		// Verifica se o filtro foi informado pela p�gina de filtragem da tabela
		// auxiliar
		if (httpServletRequest
				.getAttribute("filtroTabelaAuxiliarAbreviadaTipo") != null) {
			filtroTabelaAuxiliarAbreviadaTipo = (FiltroTabelaAuxiliarAbreviadaTipo) httpServletRequest
					.getAttribute("filtroTabelaAuxiliarAbreviadaTipo");
			filtroTabelaAuxiliarAbreviadaTipo
					.adicionarCaminhoParaCarregamentoEntidade("sistemaAbastecimento");
		} else {
			// Caso o exibirManterTabelaAuxiliarAbreviadaTipo n�o tenha passado
			// por algum
			// esquema de filtro, a quantidade de registros � verificada para
			// avaliar a necessidade
			// de filtragem
			filtroTabelaAuxiliarAbreviadaTipo = new FiltroTabelaAuxiliarAbreviadaTipo();

			if (fachada.registroMaximo(TabelaAuxiliarAbreviadaTipo.class) > ConstantesSistema.NUMERO_MAXIMO_REGISTROS_MANUTENCAO) {
				// Se o limite de registros foi atingido, a p�gina de filtragem
				// � chamada
				retorno = actionMapping
						.findForward("filtrarTabelaAuxiliarAbreviadaTipo");
				sessao.setAttribute("tela", tela);
			}
		}

		// A pesquisa de tabelas auxiliares s� ser� feita se o forward estiver
		// direcionado para a p�gina de manterTabelaAuxiliarAbreviadaTipo
		if (retorno.getName().equalsIgnoreCase(
				"manterTabelaAuxiliarAbreviadaTipo")) {

			// Seta a ordena��o desejada do filtro
			filtroTabelaAuxiliarAbreviadaTipo
					.setCampoOrderBy(FiltroTabelaAuxiliarAbreviadaTipo.ID);

			// Pesquisa de tabelas auxiliares
			tabelasAuxiliaresAbreviadasTipo = fachada.pesquisarTabelaAuxiliar(
					filtroTabelaAuxiliarAbreviadaTipo, pacoteNomeObjeto);

			if (tabelasAuxiliaresAbreviadasTipo == null
					|| tabelasAuxiliaresAbreviadasTipo.isEmpty()) {
				// Nenhum atividade cadastrado
				throw new ActionServletException("atencao.naocadastrado", null,
						titulo);
			}

	
			if (tabelasAuxiliaresAbreviadasTipo != null
					&& !tabelasAuxiliaresAbreviadasTipo.isEmpty()) {

				// Verifica se a cole��o cont�m apenas um objeto, se est�
				// retornando
				// da pagina��o (devido ao esquema de pagina��o de 10 em 10 faz
				// uma
				// nova busca), evitando, assim, que caso haja 11 elementos no
				// retorno da pesquisa e o usu�rio selecione o link para ir para
				// a
				// segunda p�gina ele n�o v� para tela de atualizar.

				if (tabelasAuxiliaresAbreviadasTipo.size() == 1
						&& (httpServletRequest.getParameter("page.offset") == null || httpServletRequest
								.getParameter("page.offset").equals("1"))) {

					// Verifica se o usu�rio marcou o checkbox de atualizar no
					// jsp
					// funcionalidade_filtrar. Caso todas as condi��es sejam
					// verdadeiras seta o retorno para o
					// ExibirAtualizarFuncionalidadeAction e em caso negativo
					// manda a cole��o pelo request.

					if (httpServletRequest.getParameter("atualizar") != null) {
						retorno = actionMapping
								.findForward("atualizarTabelaAuxiliarAbreviadaTipo");
						TabelaAuxiliarAbreviadaTipo tabelaAuxiliarabreviadaTipo = (TabelaAuxiliarAbreviadaTipo) tabelasAuxiliaresAbreviadasTipo
								.iterator().next();
						httpServletRequest.setAttribute("id",
								tabelaAuxiliarabreviadaTipo.getId().toString());

					} else {
						sessao.setAttribute("tabelasAuxiliaresAbreviadasTipo",
								tabelasAuxiliaresAbreviadasTipo);
					}
				} else {
					sessao.setAttribute("tabelasAuxiliaresAbreviadasTipo",
							tabelasAuxiliaresAbreviadasTipo);
				}
			} else {
				// Nenhuma funcionalidade cadastrada
				throw new ActionServletException(
						"atencao.pesquisa.nenhumresultado");
			}
		}	
			

		// Envia o path do pacote na sess�o
		sessao.setAttribute("pacoteNomeObjeto", pacoteNomeObjeto);
		
		// Envia os objetos na sess�o
		sessao.setAttribute("titulo", titulo);
		sessao.setAttribute("totalRegistros", tabelasAuxiliaresAbreviadasTipo
				.size());
		sessao.setAttribute("descricao", descricao);
		sessao.setAttribute("tamMaxCampoDescricao", new Integer(
				tamMaxCampoDescricao));
		sessao.setAttribute("tamMaxCampoDescricaoAbreviada", new Integer(
				tamMaxCampoDescricaoAbreviada));
		sessao.setAttribute("tituloTipo", tituloTipo);

		// Devolve o mapeamento de retorno
		return retorno;
	}
}
