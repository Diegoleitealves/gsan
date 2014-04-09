package gcom.gui.seguranca.acesso;

import gcom.gui.GcomAction;
import gcom.seguranca.transacao.Tabela;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * Remove uma tabela da cole��o das tabelas da opera��o
 *
 * @author Pedro Alexandre
 * @date 09/05/2006
 */
public class RemoverOperacaoTabelaAction extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping,
								ActionForm actionForm, 
								HttpServletRequest httpServletRequest,
								HttpServletResponse httpServletResponse) {

		//Seta o mapeamento para tela de inseri opera��o
		ActionForward retorno = null;
		
		
		String telaRetorno = httpServletRequest.getParameter("telaRetorno");
		
		if(telaRetorno.equalsIgnoreCase("inserirOperacao")){
			retorno = actionMapping.findForward("operacaoInserir");
		}else if(telaRetorno.equalsIgnoreCase("atualizarOperacao")){
			retorno = actionMapping.findForward("operacaoAtualizar");
		}

		//Cria uma inst�ncia da sess�o
		HttpSession sessao = httpServletRequest.getSession(false);

		//Recupera a cole��o de tabelas da opera��o
		Collection<Tabela> colecaoOperacaoTabela = (Collection<Tabela>) sessao.getAttribute("colecaoOperacaoTabela");

		//Cria o iterator da cole��o de tabelas 
		Iterator iteratorColecaoOperacaoTabela = colecaoOperacaoTabela.iterator();
		
		//Recupera o c�digo da tabela selecionada para ser removida
		String idTabelaExcluir = httpServletRequest.getParameter("idTabelaExcluir");
		
		//La�o pararemover a tabelaselecionada da cole��o
		while (iteratorColecaoOperacaoTabela.hasNext()) {
			//Recupera a tabela atual
			Tabela tabela = (Tabela) iteratorColecaoOperacaoTabela.next();
			
			//Caso seja a tabela selecionada remove a tabela da cole��o
			if (Integer.parseInt(idTabelaExcluir) == tabela.getId().intValue()) {
				iteratorColecaoOperacaoTabela.remove();
				break;
			}

		}

		//Seta a cole��o alterada na sess�o
		sessao.setAttribute("colecaoOperacaoTabela",colecaoOperacaoTabela);

		//Retorna o mapeamento contido na vari�vel retorno
		return retorno;
	}
}
