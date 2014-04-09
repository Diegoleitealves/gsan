package gcom.gui.util.tabelaauxiliar.indicador;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.gui.ManutencaoRegistroActionForm;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ParametroSimples;
import gcom.util.tabelaauxiliar.indicador.FiltroTabelaAuxiliarIndicador;
import gcom.util.tabelaauxiliar.indicador.TabelaAuxiliarIndicador;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author R�mulo Aur�lio
 *
 */
public class ExibirAtualizarTabelaAuxiliarIndicadorAction extends GcomAction {
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
				.findForward("atualizarTabelaAuxiliarIndicador");

		// Obt�m a inst�ncia da sess�o
		HttpSession sessao = httpServletRequest.getSession(false);

		// Obt�m a inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		int tamMaxCampoDescricao = 40;


		if (httpServletRequest.getParameter("manter") != null) {
			sessao.setAttribute("manter", true);
		} else if (httpServletRequest.getParameter("filtrar") != null) {
			sessao.removeAttribute("manter");
		}
		
		
		// String com path do pacote mais o nome do objeto
		String pacoteNomeObjeto = (String) sessao
				.getAttribute("pacoteNomeObjeto");
		
		ManutencaoRegistroActionForm manutencaoRegistroActionForm = (ManutencaoRegistroActionForm) actionForm;
		String id = null;
		// C�digo da tabela auxiliar a ser atualizada
		if (httpServletRequest.getAttribute("id") != null) {
			id = (String) httpServletRequest.getAttribute("id");
			sessao.setAttribute("id", id);
		} else {
			if (manutencaoRegistroActionForm.getIdRegistroAtualizacao() != null) {
				id = manutencaoRegistroActionForm.getIdRegistroAtualizacao();
				sessao.setAttribute("id", id);
			}
		}

		if (sessao.getAttribute("id") != null) {
			id = (String) sessao.getAttribute("id");
		}

		// Cria o filtro para atividade
		FiltroTabelaAuxiliarIndicador filtroTabelaAuxiliarIndicador = new FiltroTabelaAuxiliarIndicador();
		
		String tela = (String) sessao.getAttribute("tela");

		if (httpServletRequest.getAttribute("desfazer") == null) {

			// Adiciona o par�metro no filtro
			filtroTabelaAuxiliarIndicador
					.adicionarParametro(new ParametroSimples(
							FiltroTabelaAuxiliarIndicador.ID, id));

	

			// Pesquisa a tabela auxiliar no sistema
			Collection tabelasAuxiliaresIndicador = fachada
					.pesquisarTabelaAuxiliar(filtroTabelaAuxiliarIndicador,
					pacoteNomeObjeto);

			// Caso a cole��o esteja vazia, indica erro inesperado
			if (tabelasAuxiliaresIndicador == null
					|| tabelasAuxiliaresIndicador.isEmpty()) {
				throw new ActionServletException("erro.sistema");
			}

			Iterator iteratorTabelaAuxiliarIndicador = tabelasAuxiliaresIndicador
					.iterator();

			// A tabela auxiliar abreviada que ser� atualizada
			TabelaAuxiliarIndicador tabelaAuxiliarIndicador = (TabelaAuxiliarIndicador) iteratorTabelaAuxiliarIndicador
					.next();

			// Manda a tabela auxiliar na sess�o
			sessao.setAttribute("tabelaAuxiliarIndicador",
					tabelaAuxiliarIndicador);


			if (tela.equalsIgnoreCase("quadraPerfil")) {

				if (tabelaAuxiliarIndicador.getIndicadorUso().intValue() == ConstantesSistema.INDICADOR_USO_ATIVO
						.intValue()) {

					sessao.setAttribute("indicadorUso", "sim");
				} else {
					sessao.setAttribute("indicadorUso", "nao");
				}

			}
			
			
			if (tela.equalsIgnoreCase("quadraPerfil")) {

				if (tabelaAuxiliarIndicador.getIndicadorBaixaRenda().intValue() == ConstantesSistema.INDICADOR_USO_ATIVO
						.intValue()) {

					sessao.setAttribute("indicadorBaixaRenda", "sim");
				} else {
					sessao.setAttribute("indicadorBaixaRenda", "nao");
				}

			}
			
			DadosTelaTabelaAuxiliarIndicador dados = (DadosTelaTabelaAuxiliarIndicador) DadosTelaTabelaAuxiliarIndicador
					.obterDadosTelaTabelaAuxiliar(tela);

			sessao.setAttribute("funcionalidadeTabelaAuxiliarIndicadorFiltrar",
					dados.getFuncionalidadeTabelaIndicadorFiltrar());

			sessao.setAttribute("tamMaxCampoDescricao", new Integer(
					tamMaxCampoDescricao));
	
		}

		if (httpServletRequest.getAttribute("desfazer") != null) {

			// Cria o filtro para atividade
			filtroTabelaAuxiliarIndicador.limparListaParametros();

			// Adiciona o par�metro no filtro
			filtroTabelaAuxiliarIndicador
					.adicionarParametro(new ParametroSimples(
							FiltroTabelaAuxiliarIndicador.ID, id));

			// Pesquisa a tabela auxiliar no sistema
			Collection tabelasAuxiliaresIndicadorBase = fachada
					.pesquisarTabelaAuxiliar(filtroTabelaAuxiliarIndicador,
							pacoteNomeObjeto);

			// Caso a cole��o esteja vazia, indica erro inesperado
			if (tabelasAuxiliaresIndicadorBase == null
					|| tabelasAuxiliaresIndicadorBase.isEmpty()) {
				throw new ActionServletException("erro.sistema");
			}

			Iterator iteratorTabelaAuxiliarIndicadorBase = tabelasAuxiliaresIndicadorBase
					.iterator();

			// A tabela auxiliar abreviada que ser� atualizada
			TabelaAuxiliarIndicador tabelaAuxiliarIndicadorBase = (TabelaAuxiliarIndicador) iteratorTabelaAuxiliarIndicadorBase
					.next();

			// Manda a tabela auxiliar na sess�o
			sessao.setAttribute("tabelaAuxiliarIndicadorBase",
					tabelaAuxiliarIndicadorBase);

		}
		
		//Manda a tabela auxiliar na sess�o
		httpServletRequest.setAttribute("tela",tela);

		//Devolve o mapeamento de retorno
		return retorno;
	}

}

