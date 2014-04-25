package gcom.gui.seguranca.acesso;

import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.Funcionalidade;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.OperacaoTipo;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.transacao.Tabela;
import gcom.seguranca.transacao.TabelaColuna;

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action respons�vel por inserir uma opera��o de uma funcionalidade espec�fica no sistema
 * o action inseri tamb�m o relacionamento entre a opera��o e as tabelas 
 * 
 * @author Pedro Alexandre
 * @date 05/05/2006
 */
public class InserirOperacaoAction extends GcomAction {

	/**
	 * Inseri uma opera��o de uma funcionalida de no sistema
	 *
	 * [UC0284] Inserir Opera��o
	 *
	 * @author Pedro Alexandre
	 * @date 05/05/2006
	 *
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping,
								 ActionForm actionForm, 
								 HttpServletRequest httpServletRequest,
								 HttpServletResponse httpServletResponse) {

		//Cria a vari�vel de retorno, seta para a p�gina de sucesso 
		ActionForward retorno = actionMapping.findForward("telaSucesso");

        //Obt�m a sess�o
        HttpSession sessao = httpServletRequest.getSession(false);
        
		//Recupera o form de inserir opera��o
		InserirOperacaoActionForm inserirOperacaoActionForm = (InserirOperacaoActionForm) actionForm;
		
		//Recupera as informa��es do form 
		String descricao = inserirOperacaoActionForm.getDescricao();
		String descricaoAbreviada = inserirOperacaoActionForm.getDescricaoAbreviada();
		String caminhoURL = inserirOperacaoActionForm.getCaminhoUrl();
		String idFuncionalidade = inserirOperacaoActionForm.getIdFuncionalidade();
		Funcionalidade funcionalidade = new Funcionalidade();
		funcionalidade.setId(new Integer(idFuncionalidade));
        
		String idTipoOperacao = inserirOperacaoActionForm.getIdTipoOperacao();
		OperacaoTipo operacaoTipo = new OperacaoTipo();
		operacaoTipo.setId(new Integer(idTipoOperacao));
		
		//Recupera o argumento de pesquisa 
		//Caso tenha sido informado cria o objeto
		String idArgumentoPesquisa = inserirOperacaoActionForm.getIdArgumentoPesquisa();
		TabelaColuna argumentoPesquisa = null;
		if(idArgumentoPesquisa != null && !idArgumentoPesquisa.equalsIgnoreCase("")){
			argumentoPesquisa = new TabelaColuna();
			argumentoPesquisa.setId(new Integer(idArgumentoPesquisa));
		}
		
		//Recupera o c�digo da opera��o de pesquisa 
		//Caso tenha sido informado cria o objeto
		String idOperacaoPesquisa = inserirOperacaoActionForm.getIdOperacaoPesquisa();
		Operacao operacaoPesquisa = null;
		if(idOperacaoPesquisa != null && !idOperacaoPesquisa.equalsIgnoreCase("")){
			operacaoPesquisa = new Operacao();
			operacaoPesquisa.setId(new Integer(idOperacaoPesquisa));
		}
		
		//Cria uma inst�ncia da fachada 
		Fachada fachada = Fachada.getInstancia();

		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");
		
		//Recupera a  cole��o de tabelas da sess�o  
		Collection<Tabela> colecaoTabela = (Collection<Tabela>)sessao.getAttribute("colecaoOperacaoTabela");

		//Cria o objeto opera��o que vai ser inserido
		Operacao operacao = new Operacao();
		operacao.setDescricao(descricao);
		operacao.setDescricaoAbreviada(descricaoAbreviada);
		operacao.setCaminhoUrl(caminhoURL);
		operacao.setFuncionalidade(funcionalidade);
		operacao.setOperacaoTipo(operacaoTipo);
		operacao.setIdOperacaoPesquisa(operacaoPesquisa);
		operacao.setTabelaColuna(argumentoPesquisa);
		operacao.setUltimaAlteracao(new Date());
		
		//Chamao met�do de inserir opera��o da fachada
		fachada.inserirOperacao(operacao, colecaoTabela, usuarioLogado);
		
		//Limpa a sess�o depois da inser��o 
		sessao.removeAttribute("habilitarArgumentoPesquisa");
		sessao.removeAttribute("habilitarOperacaoPesquisa");
		sessao.removeAttribute("colecaoOperacaoTabela");
		sessao.removeAttribute("InserirOperacaoActionForm");
		sessao.removeAttribute("retornarTela");
		
		//Monta a p�gina de sucesso
		montarPaginaSucesso(httpServletRequest, "Opera��o inserida com sucesso.",
				"Inserir outra opera��o",
				"exibirInserirOperacaoAction.do?menu=sim");


		//Retorna o mapeamento contido na vari�vel retorno 
		return retorno;
	}
}
