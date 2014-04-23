package gcom.gui.seguranca.acesso;

import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.Funcionalidade;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.OperacaoTabela;
import gcom.seguranca.acesso.OperacaoTabelaPK;
import gcom.seguranca.acesso.OperacaoTipo;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.transacao.Tabela;
import gcom.seguranca.transacao.TabelaColuna;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action respons�vel por atualizar uma opera��o no sistema 
 * assim como seus relacionamentos
 *
 * @author Pedro Alexandre
 * @date 07/08/2006
 */
public class AtualizarOperacaoAction extends GcomAction {

	/**
	 * [UC0281] Manter Opera��o
	 *
	 * @author Pedro Alexandre
	 * @date 07/08/2006
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

		//Seta o mapeamento de retorno para a tela de sucesso
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		//Cria uma inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		//Cria uma inst�ncia da sess�o
		HttpSession sessao = httpServletRequest.getSession(false);

		//Recupera o usuario que est� logado na aplica��o
		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");
		
		//Recupera o form de atualizar opra��o
		AtualizarOperacaoActionForm atualizarOperacaoActionForm = (AtualizarOperacaoActionForm) actionForm;

		//Recupera os dados informados na p�gina de atualizar opera��o
		String idTipoOperacao = atualizarOperacaoActionForm.getIdTipoOperacao();
		String idFuncionalidade = atualizarOperacaoActionForm.getIdFuncionalidade();
		String idArgumentoPesquisa = atualizarOperacaoActionForm.getIdArgumentoPesquisa();
		String idOperacaoPesquisa = atualizarOperacaoActionForm.getIdOperacaoPesquisa();
		
		//Cria as vari�veis para armazear o tipo da opera��o a funcionalidade o
		//argumento de pesquisa e a opera��o de pesquisa
		OperacaoTipo operacaoTipo = null;
		Funcionalidade funcionalidade = null;
		TabelaColuna argumentoPesquisa = null;
		Operacao operacaoPesquisa = null;
		
		/*
		 * Caso o usu�rio tenha informado o tipo da opera��o
		 * seta o id do tipo da opera��o
		 */
		if (idTipoOperacao != null && !idTipoOperacao.trim().equals("")) {
			operacaoTipo = new OperacaoTipo();
			operacaoTipo.setId(new Integer(idTipoOperacao));
		}

		/*
		 * Caso o usu�rio tenha informado a funcionalidade da opera��o
		 * seta o id da funcionalidade
		 */
		if (idFuncionalidade != null && !idFuncionalidade.trim().equals("")) {
			funcionalidade = new Funcionalidade();
			funcionalidade.setId(new Integer(idFuncionalidade));
		}

		/*
		 * Caso o usu�rio tenha informado o argumento de pesquisa
		 * seta o id do argumento de pesquisa
		 */
		if (idArgumentoPesquisa != null && !idArgumentoPesquisa.trim().equals("")) {
			argumentoPesquisa = new TabelaColuna();
			argumentoPesquisa.setId(new Integer(idArgumentoPesquisa));
		}

		/*
		 * Caso o usu�rio tenha informado aopera��o de pesquisa
		 * seta o id da opera��o de pesquisa
		 */		
		if (idOperacaoPesquisa != null && !idOperacaoPesquisa.trim().equals("")) {
			operacaoPesquisa = new Operacao();
			operacaoPesquisa.setId(new Integer(idOperacaoPesquisa));
		}

		//Monta a opera��o que vai ser atualizada
		Operacao operacao = (Operacao) sessao.getAttribute("operacaoAtualizar");
		operacao.setDescricao(atualizarOperacaoActionForm.getDescricao());
		operacao.setDescricaoAbreviada(atualizarOperacaoActionForm.getDescricaoAbreviada());
		operacao.setCaminhoUrl(atualizarOperacaoActionForm.getCaminhoUrl());
		operacao.setOperacaoTipo(operacaoTipo);
		operacao.setFuncionalidade(funcionalidade);
//		operacao.setTabelaColuna(argumentoPesquisa);
		operacao.setArgumentoPesquisa(argumentoPesquisa);
		operacao.setIdOperacaoPesquisa(operacaoPesquisa);

		//Vari�vel que vai armazenar uma cole��o de TabelaOperacao
		Collection<OperacaoTabela> colecaoOperacaoTabela = null;

		/*
		 * Caso exista a cole��o de tabela opera��o na sess�o
		 * recuperaa cole��o e cria os relacionamentos entre 
		 * a opera��o e as tabelas informadas pelo usu�rio 
		 */
		if (sessao.getAttribute("colecaoOperacaoTabela") != null) {
			//Recupera a cole��o de tabelas da sees�o  
			Collection<Tabela> colecaoTabela = (Collection<Tabela>) sessao.getAttribute("colecaoOperacaoTabela");
			
			/*
			 * Caso a cole��o detabelas n�o esteja vazia 
			 * colocaa cole��o no iterator 
			 * para criar todos os relacionamentos entre tabela e opera��o 
			 */
			if(!colecaoTabela.isEmpty()){
				//Coloca a cole��o no iterator
				Iterator<Tabela> iteratorTabela = colecaoTabela.iterator();
				
				//Inst�ncia a cole��o
				colecaoOperacaoTabela = new ArrayList();
				
				//La�o para criar os relacionamentos entre a opera��o e as tabelas
				while (iteratorTabela.hasNext()) {
					Tabela tabela = iteratorTabela.next();
					OperacaoTabela operacaoTabela = new OperacaoTabela(new OperacaoTabelaPK(operacao.getId(),tabela.getId()));
					colecaoOperacaoTabela.add(operacaoTabela);
				}
			}
		}

		//Chamao met�do para atualizar a opera��o
		fachada.atualizarOperacao(operacao, colecaoOperacaoTabela,usuarioLogado);

		//Limpa a sess�o
		sessao.removeAttribute("colecaoOperacaoTabela");

		//Monta a tela de sucesso
		montarPaginaSucesso(httpServletRequest, "Opera��o "
				+ operacao.getId() + " atualizado com sucesso.",
				"Realizar outra Manuten��o Opera��o",
				"exibirFiltrarOperacaoAction.do?menu=sim");

		//Retorna o mapeamento de retorno 
		return retorno;
	}
}
