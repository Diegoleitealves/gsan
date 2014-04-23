package gcom.gui.batch;

import java.util.Collection;

import gcom.fachada.Fachada;
import gcom.faturamento.FaturamentoAtividadeCronograma;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action respons�vel pela pre-exibi��o da pagina de inserir processo
 * faturamento
 * 
 * @author Rodrigo Silveira
 * @created 11/08/2006
 */
public class ExibirInserirProcessoFaturamentoComandadoAction extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping
				.findForward("inserirProcessoFaturamentoComandado");

		Fachada fachada = Fachada.getInstancia();

		// 1� Passo - Pegar o total de registros atrav�s de um count da
		// consulta que aparecer� na tela
		Integer totalRegistros = fachada
				.pesquisarFaturamentoAtividadeCronogramaComandadasNaoRealizadasCount();
		if (totalRegistros.intValue() <= 0 || totalRegistros == null) {
			throw new ActionServletException("atencao.pesquisa.nenhumresultado");
		}

		
		// 2� Passo - Chamar a fun��o de Pagina��o passando o total de
		// registros
		retorno = this.controlarPaginacao(httpServletRequest, retorno,
				totalRegistros);

		// 3� Passo - Obter a cole��o da consulta que aparecer� na tela
		// passando o numero de paginas
		// da pesquisa que est� no request
		Collection<FaturamentoAtividadeCronograma> colecaoFaturamentoAtividadeCronograma = fachada
				.pesquisarFaturamentoAtividadeCronogramaComandadasNaoRealizadas((Integer) httpServletRequest
						.getAttribute("numeroPaginasPesquisa"));

		httpServletRequest.setAttribute(
				"colecaoFaturamentoAtividadeCronograma",
				colecaoFaturamentoAtividadeCronograma);

		return retorno;
	}

}
