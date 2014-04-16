package gcom.gui.util.tabelaauxiliar.faixa;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.gui.ManutencaoRegistroActionForm;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ParametroSimples;
import gcom.util.tabelaauxiliar.faixa.FiltroTabelaAuxiliarFaixaReal;
import gcom.util.tabelaauxiliar.faixa.TabelaAuxiliarFaixaReal;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author R�mulo Aurelio
 * 
 */
public class ExibirAtualizarTabelaAuxiliarFaixaRealAction extends GcomAction {
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
				.findForward("atualizarTabelaAuxiliarFaixaReal");

		// Obt�m a inst�ncia da sess�o
		HttpSession sessao = httpServletRequest.getSession(false);

		// Obt�m a inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		int tamMaxCampoVolumeMenorFaixa = 6;

		int tamMaxCampoVolumeMaiorFaixa = 6;

		if (httpServletRequest.getParameter("manter") != null) {
			sessao.setAttribute("manter", true);
		} else {
			sessao.removeAttribute("manter");
		}
		
		
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

		
		// String com path do pacote mais o nome do objeto
		String pacoteNomeObjeto = (String) sessao
				.getAttribute("pacoteNomeObjeto");

		
		// Cria o filtro para atividade
		FiltroTabelaAuxiliarFaixaReal filtroTabelaAuxiliarFaixaReal = new FiltroTabelaAuxiliarFaixaReal();
		
		String tela = (String) sessao.getAttribute("tela");

		if (httpServletRequest.getAttribute("desfazer") == null) {

			// Adiciona o par�metro no filtro
			filtroTabelaAuxiliarFaixaReal
					.adicionarParametro(new ParametroSimples(
							FiltroTabelaAuxiliarFaixaReal.ID, id));

		
			// Pesquisa a tabela auxiliar no sistema
			Collection tabelasAuxiliaresFaixaReais = fachada
					.pesquisarTabelaAuxiliar(filtroTabelaAuxiliarFaixaReal,
					pacoteNomeObjeto);

			// Caso a cole��o esteja vazia, indica erro inesperado
			if (tabelasAuxiliaresFaixaReais == null
					|| tabelasAuxiliaresFaixaReais.isEmpty()) {
				throw new ActionServletException("erro.sistema");
			}

			Iterator iteratorTabelaAuxiliarFaixaReal = tabelasAuxiliaresFaixaReais
					.iterator();

			// A tabela auxiliar abreviada que ser� atualizada
			TabelaAuxiliarFaixaReal tabelaAuxiliarFaixaReal = (TabelaAuxiliarFaixaReal) iteratorTabelaAuxiliarFaixaReal
					.next();

			// Manda a tabela auxiliar na sess�o
			sessao.setAttribute("tabelaAuxiliarFaixaReal",
					tabelaAuxiliarFaixaReal);

			

			if (tela.equalsIgnoreCase("piscinaVolumeFaixa") || tela.equalsIgnoreCase("reservatorioVolumeFaixa")) {

				if (tabelaAuxiliarFaixaReal.getIndicadorUso().intValue() == ConstantesSistema.INDICADOR_USO_ATIVO
						.intValue()) {

					sessao.setAttribute("indicadorUso", "sim");
				} else {
					sessao.setAttribute("indicadorUso", "nao");
				}

			}
			DadosTelaTabelaAuxiliarFaixaReal dados = (DadosTelaTabelaAuxiliarFaixaReal) DadosTelaTabelaAuxiliarFaixaReal
					.obterDadosTelaTabelaAuxiliar(tela);

			sessao.setAttribute("funcionalidadeTabelaAuxiliarFaixaRealFiltrar",
					dados.getFuncionalidadeTabelaFaixaRealFiltrar());

			sessao.setAttribute("tamMaxCampoVolumeMenorFaixa", new Integer(
					tamMaxCampoVolumeMenorFaixa));
			sessao.setAttribute("tamMaxCampoVolumeMaiorFaixa", new Integer(
					tamMaxCampoVolumeMaiorFaixa));
		}

		if (httpServletRequest.getAttribute("desfazer") != null) {

			// Cria o filtro para atividade
			filtroTabelaAuxiliarFaixaReal.limparListaParametros();

			// Adiciona o par�metro no filtro
			filtroTabelaAuxiliarFaixaReal
					.adicionarParametro(new ParametroSimples(
							FiltroTabelaAuxiliarFaixaReal.ID, id));

			// Pesquisa a tabela auxiliar no sistema
			Collection tabelasAuxiliaresFaixaReaisBase = fachada
					.pesquisarTabelaAuxiliar(filtroTabelaAuxiliarFaixaReal,
							pacoteNomeObjeto);

			// Caso a cole��o esteja vazia, indica erro inesperado
			if (tabelasAuxiliaresFaixaReaisBase == null
					|| tabelasAuxiliaresFaixaReaisBase.isEmpty()) {
				throw new ActionServletException("erro.sistema");
			}

			Iterator iteratorTabelaAuxiliarFaixaRealBase = tabelasAuxiliaresFaixaReaisBase
					.iterator();

			// A tabela auxiliar abreviada que ser� atualizada
			TabelaAuxiliarFaixaReal tabelaAuxiliarFaixaRealBase = (TabelaAuxiliarFaixaReal) iteratorTabelaAuxiliarFaixaRealBase
					.next();

			// Manda a tabela auxiliar na sess�o
			sessao.setAttribute("tabelaAuxiliarFaixaReal",
					tabelaAuxiliarFaixaRealBase);

		}
		
		//seta o parametro tela a parte de acesso a funcionalidade ou opera��o
		httpServletRequest.setAttribute("tela",tela);

		//Devolve o mapeamento de retorno
		return retorno;
	}

}
