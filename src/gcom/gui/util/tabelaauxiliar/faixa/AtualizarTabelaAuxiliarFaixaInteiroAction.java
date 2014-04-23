package gcom.gui.util.tabelaauxiliar.faixa;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.tabelaauxiliar.faixa.TabelaAuxiliarFaixaInteiro;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author gcom
 * 
 */
public class AtualizarTabelaAuxiliarFaixaInteiroAction extends GcomAction {
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

		// Pega o action form
		TabelaAuxiliarFaixaInteiroActionForm form = (TabelaAuxiliarFaixaInteiroActionForm) actionForm;

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Obt�m a inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		// Obt�m a sess�o
		HttpSession sessao = httpServletRequest.getSession(false);

		// Recupera o ponto de coleta da sess�o
		TabelaAuxiliarFaixaInteiro tabelaAuxiliarFaixaInteiro = (TabelaAuxiliarFaixaInteiro) sessao
				.getAttribute("tabelaAuxiliarFaixaInteiro");

		// Atualiza a tabela auxiliar com os dados inseridos pelo usu�rio
		// A data de �ltima altera��o n�o � alterada, pois ser� usada na
		// verifica��o de atualiza��o

		if (form.getMenorFaixa() != null
				&& !form.getMenorFaixa().trim().equalsIgnoreCase("")
				&& form.getMaiorFaixa() != null
				&& !form.getMaiorFaixa().trim().equalsIgnoreCase("")) {
			if (new Integer(form.getMenorFaixa()).intValue() > new Integer(form
					.getMaiorFaixa())) {
				throw new ActionServletException(
						"atencao.menor_faixa_superior_maior_faixa");
			}
		}

		if (!form.getMaiorFaixa().equalsIgnoreCase("")) {

			tabelaAuxiliarFaixaInteiro.setMaiorFaixa(new Integer(form
					.getMaiorFaixa()));
		}

		if (!form.getMenorFaixa().equalsIgnoreCase("")) {

			tabelaAuxiliarFaixaInteiro.setMenorFaixa(new Integer(form
					.getMenorFaixa()));
		}
		if (form.getIndicadorUso() != null) {
			tabelaAuxiliarFaixaInteiro.setIndicadorUso(new Short(form
					.getIndicadorUso()));
		}

		// Atualiza os dados
		fachada.atualizarTabelaAuxiliar(tabelaAuxiliarFaixaInteiro);

		// Monta a p�gina de sucesso
		if (retorno.getName().equalsIgnoreCase("telaSucesso")) {

			montarPaginaSucesso(
					httpServletRequest,
					((String) sessao.getAttribute("titulo")) + " "
							+ tabelaAuxiliarFaixaInteiro.getId().toString()
							+ " atualizado(a) com sucesso.",
					"Realizar outra manuten��o de "
							+ ((String) sessao.getAttribute("titulo")),
					((String) sessao
							.getAttribute("funcionalidadeTabelaAuxiliarFaixaInteiroFiltrar")));

		}

		// Remove os objetos da sess�o
		sessao
				.removeAttribute("funcionalidadeTabelaAuxiliarFaixaInteiroManter");
		sessao.removeAttribute("titulo");
		sessao.removeAttribute("tabelaAuxiliarFaixaInteiro");
		sessao.removeAttribute("tamMaxCampoDescricao");
		sessao.removeAttribute("tamMaxCampoDescricaoAbreviada");

		// devolve o mapeamento de retorno
		return retorno;
	}

}
