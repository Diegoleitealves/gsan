package gcom.gui.util.tabelaauxiliar.faixa;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.tabelaauxiliar.faixa.TabelaAuxiliarFaixaReal;

import java.math.BigDecimal;

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
public class AtualizarTabelaAuxiliarFaixaRealAction extends GcomAction {
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
		TabelaAuxiliarFaixaRealActionForm form = (TabelaAuxiliarFaixaRealActionForm) actionForm;

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Obt�m a inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		// Obt�m a sess�o
		HttpSession sessao = httpServletRequest.getSession(false);

		// Recupera o ponto de coleta da sess�o
		TabelaAuxiliarFaixaReal tabelaAuxiliarFaixaReal = (TabelaAuxiliarFaixaReal) sessao
				.getAttribute("tabelaAuxiliarFaixaReal");

		// Atualiza a tabela auxiliar com os dados inseridos pelo usu�rio
		// A data de �ltima altera��o n�o � alterada, pois ser� usada na
		// verifica��o de atualiza��o

		BigDecimal volumeMaiorFaixa = null;
		
		if (!form.getVolumeMaiorFaixa().equalsIgnoreCase("")) {
			
			volumeMaiorFaixa = new BigDecimal(0);

			String valorAux = form.getVolumeMaiorFaixa().toString().replace(
					".", "");

			valorAux = valorAux.replace(",", ".");

			volumeMaiorFaixa = new BigDecimal(valorAux);

			tabelaAuxiliarFaixaReal.setVolumeMaiorFaixa(volumeMaiorFaixa);
		}
		if (!form.getVolumeMenorFaixa().equalsIgnoreCase("")) {
			BigDecimal volumeMenorFaixa = new BigDecimal(0);

			String valorAux1 = form.getVolumeMenorFaixa().toString().replace(
					".", "");

			valorAux1 = valorAux1.replace(",", ".");

			volumeMenorFaixa = new BigDecimal(valorAux1);

			tabelaAuxiliarFaixaReal.setVolumeMenorFaixa(volumeMenorFaixa);
			
			
			if (volumeMaiorFaixa != null					
					&& volumeMenorFaixa != null) {
				if (volumeMenorFaixa.compareTo(volumeMaiorFaixa) > 0) {
					throw new ActionServletException("atencao.volume_menor_faixa_superior_maior_faixa");
				}
			}
			
			
		}
		if (form.getIndicadorUso() != null) {
			tabelaAuxiliarFaixaReal.setIndicadorUso(new Short(form
					.getIndicadorUso()));
		}

		// Atualiza os dados
		fachada.atualizarTabelaAuxiliar(tabelaAuxiliarFaixaReal);

		// Monta a p�gina de sucesso
		if (retorno.getName().equalsIgnoreCase("telaSucesso")) {

			montarPaginaSucesso(
					httpServletRequest,
					((String) sessao.getAttribute("titulo")) + " "
							+ tabelaAuxiliarFaixaReal.getId().toString()
							+ " atualizado(a) com sucesso.",
					"Realizar outra manuten��o de "
							+ ((String) sessao.getAttribute("titulo")),
					((String) sessao
							.getAttribute("funcionalidadeTabelaAuxiliarFaixaRealFiltrar")));

		}

		// Remove os objetos da sess�o
		sessao.removeAttribute("funcionalidadeTabelaAuxiliarFaixaRealManter");
		sessao.removeAttribute("titulo");
		sessao.removeAttribute("tabelaAuxiliarFaixaReal");
		sessao.removeAttribute("tamMaxCampoDescricao");
		sessao.removeAttribute("tamMaxCampoDescricaoAbreviada");

		// devolve o mapeamento de retorno
		return retorno;
	}

}
