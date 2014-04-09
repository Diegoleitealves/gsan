package gcom.gui.seguranca.acesso;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.transacao.FiltroTabela;
import gcom.seguranca.transacao.Tabela;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;


/**
 * Descri��o da classe 
 *
 * @author Pedro Alexandre
 * @date 05/05/2006
 */
public class AdicionarOperacaoTabelaAction extends GcomAction{
	
	
	public ActionForward execute(ActionMapping actionMapping,
								ActionForm actionForm, 
								HttpServletRequest httpServletRequest,
								HttpServletResponse httpServletResponse) {

		//Seta o mapeamento para a p�gina de adicionar tabela 
		ActionForward retorno = actionMapping.findForward("adicionarOperacaoTabela");
	
		//Recupera o form de adicionar tabela 
		AdicionarOperacaoTabelaActionForm adicionarOperacaoTabelaActionForm = (AdicionarOperacaoTabelaActionForm) actionForm;
		
		//Cria uma inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();
		
		//Cria uma inst�ncia da sess�o
		HttpSession sessao = httpServletRequest.getSession(false);
		
		//Recupera o c�digo da tabela 
		String idTabela = adicionarOperacaoTabelaActionForm.getIdTabela();
		
		//Cria a vari�vel que vai armazenar a tabela 
		Tabela tabela = null;
		
		//Cria o filtro para pesquisar a tabela e seta o c�digo da tabela informada no filtro
		FiltroTabela filtroTabela = new FiltroTabela();
		filtroTabela.adicionarParametro(new ParametroSimples(FiltroTabela.ID, idTabela));
		
		//Pesquisa a tabela de acordo com o c�digo informado
		Collection colecaoTabela = fachada.pesquisar(filtroTabela,Tabela.class.getName());
		
		//Caso a tabela n�o esteja cadastrada levanta uma exce��o para o usu�rio
		//caso contr�rio recupera a tabela pesquisada 
		if(colecaoTabela==null || colecaoTabela.isEmpty()){
			throw new ActionServletException("atencao.tabela.inexistente");
		}
		tabela = (Tabela) Util.retonarObjetoDeColecao(colecaoTabela);
		
		//Cria a vari�vel que vai armazenar as tabelas adicionadas
 		Collection<Tabela> colecaoOperacaoTabela = null;
		
 		//Caso j� exista a cole��o na sess�o recupera a cole��o
 		//caso contr�rio cria uma inst�ncia nova  
		if (sessao.getAttribute("colecaoOperacaoTabela") != null) {
			colecaoOperacaoTabela = (Collection<Tabela>) sessao.getAttribute("colecaoOperacaoTabela");
        } else {
        	colecaoOperacaoTabela = new ArrayList();
        }
		
		//Caso a cole��o n�o contenha ainda a tabela informada
		//adiciona a tabela a cole��o 
		//caso contr�rio levanta uma exce��o para o usu�rio
		if(!colecaoOperacaoTabela.contains(tabela)){
			colecaoOperacaoTabela.add(tabela);
		}else{
			throw new ActionServletException("atencao.tabela.ja.informada");
		}
		
		//Seta a cole��o de tabelas na sess�o 
		sessao.setAttribute("colecaoOperacaoTabela",colecaoOperacaoTabela);
		
		//Seta a flag que indica para fechar o popup
		httpServletRequest.setAttribute("fechaPopup", "true");
		
		//Retorna o mapeamento contido na vari�vel retorno 
		return retorno;
	}
}
