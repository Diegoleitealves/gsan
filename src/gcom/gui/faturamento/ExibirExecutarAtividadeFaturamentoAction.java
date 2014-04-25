package gcom.gui.faturamento;

import gcom.fachada.Fachada;
import gcom.faturamento.bean.ExecutarAtividadeFaturamentoHelper;
import gcom.gui.GcomAction;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Esta classe tem por finalidade exibir para o usu�rio a tela que exibir� a
 * lista com as atividades de faturamento do cronograma que foram comandadas
 * 
 * @author Roberta Costa
 * @date 29/03/2006
 */
public class ExibirExecutarAtividadeFaturamentoAction extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		ActionForward retorno = actionMapping
				.findForward("exibirExecutarAtividadeFaturamento");

		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		// 1� Passo - Pegar o total de registros atrav�s de um count da consulta que aparecer� na tela
		Integer totalRegistros = fachada
				.pesquisarFaturamentoAtividadeCronogramaComandadaNaoRealizadaCount();

		// 2� Passo - Chamar a fun��o de Pagina��o passando o total de registros
		retorno = this.controlarPaginacao(httpServletRequest, retorno,
				totalRegistros);

		// 3� Passo - Obter a cole��o da consulta que aparecer� na tela passando o numero de paginas
		// da pesquisa que est� no request
		Collection<ExecutarAtividadeFaturamentoHelper> colecaoExecutarAtividadeFaturamento = fachada
				.obterAtividadesFaturamentoCronogramaComandada(((Integer) httpServletRequest
						.getAttribute("numeroPaginasPesquisa")));

		sessao.setAttribute("colecaoExecutarAtividadeFaturamento",
				colecaoExecutarAtividadeFaturamento);

		return retorno;
	}

}
