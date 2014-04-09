package gcom.gui.seguranca.acesso;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.FiltroFuncionalidade;
import gcom.seguranca.acesso.FiltroOperacaoTipo;
import gcom.seguranca.acesso.Funcionalidade;
import gcom.seguranca.acesso.OperacaoTipo;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action respons�vel pelo processamento da exibi��o da p�gina de filtrar opera��o 
 *
 * @author Pedro Alexandre
 * @date 12/05/2006
 */
public class ExibirFiltrarOperacaoAction  extends GcomAction {
  
    public ActionForward execute(ActionMapping actionMapping,
            					 ActionForm actionForm, 
            					 HttpServletRequest httpServletRequest,
            					 HttpServletResponse httpServletResponse) {
    	
    	//Seta o mapeamento de retorno para a p�gina de filtrar
        ActionForward retorno = actionMapping.findForward("filtrarOperacao");
 		
        //Cria uma inst�ncia da fachada
        Fachada fachada = Fachada.getInstancia();
        
        //Recuperao form de filtrar opera��o
		FiltrarOperacaoActionForm filtrarOperacaoActionForm = (FiltrarOperacaoActionForm) actionForm;

		//Cria uma inst�ncia da sess�o
        HttpSession sessao = httpServletRequest.getSession(false);

        //[FS0001] - Verificar exist�ncia de dados
        //Pesquisa os tipos de opera��es cadastradas no sistema
        //Caso n�o exista nenhum tipo de opera��o cadastrado levanta uma exce��o indicando que 
        //n�o existem tipos de opera��es para sele��o
		FiltroOperacaoTipo filtroOperacaoTipo = new FiltroOperacaoTipo();
		Collection<OperacaoTipo> colecaoOperacaoTipo = fachada.pesquisar(filtroOperacaoTipo,OperacaoTipo.class.getName());
		if (colecaoOperacaoTipo == null || colecaoOperacaoTipo.isEmpty()) {
			throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null,"Opera��o Tipo");
		}

		//Caso exista tipo de opera��o cadastrada
		//manda a cole��o de tipo de opera��o para a p�gina pela sess�o
		sessao.setAttribute("colecaoOperacaoTipo", colecaoOperacaoTipo);

		//Recupera o c�digo da funcionalidade se ela for digitada
		String idFuncionalidadeDigitada = filtrarOperacaoActionForm.getIdFuncionalidade();
		
		//Caso o c�digo da funcionalidade tenha sido informado 
		if (idFuncionalidadeDigitada != null && !idFuncionalidadeDigitada.trim().equalsIgnoreCase("")) {
			
			//Pesquisa a funcionalidade digitada na base de dados
			Funcionalidade funcionalidade = this.pesquisarFuncionalidade(idFuncionalidadeDigitada);
			
			//[FS0002] - Verificar exist�ncia da funcionalidade
			//Caso exista a funcionalidade digitada na base de dados 
			//seta as informa��es da funcionalidade no form 
			//Caso contr�rio indica que a funcionalidade digitada n�o existe 
			if(funcionalidade != null){	
				filtrarOperacaoActionForm.setIdFuncionalidade(String.valueOf(funcionalidade.getId()));
				filtrarOperacaoActionForm.setDescricaoFuncionalidade(funcionalidade.getDescricao());
				httpServletRequest.setAttribute("funcionalidadeEncontrada", "true");

			} else {
				filtrarOperacaoActionForm.setIdFuncionalidade("");
				filtrarOperacaoActionForm.setDescricaoFuncionalidade("FUNCIONALIDADE INEXISTENTE");
				httpServletRequest.setAttribute("funcionalidadeNaoEncontrada","exception");
			}
		} 
		
		String primeiraVez = httpServletRequest.getParameter("menu");
		if (primeiraVez != null && !primeiraVez.equals("")) {
			sessao.setAttribute("indicadorAtualizar", "1");
		}
		
		//SETA O TIPO DE PESQUISA DA DESCRICAO
		if (filtrarOperacaoActionForm.getTipoPesquisa() == null || filtrarOperacaoActionForm.getTipoPesquisa().equalsIgnoreCase("")) {
			filtrarOperacaoActionForm.setTipoPesquisa(""+ConstantesSistema.TIPO_PESQUISA_INICIAL);
		}
		
		/*
		 * ESQUEMA DO LIMPAR FORM
		 */
		if (httpServletRequest.getParameter("desfazer") != null && httpServletRequest.getParameter("desfazer").equalsIgnoreCase("S")) {
			filtrarOperacaoActionForm.setIdOperacao("");
			filtrarOperacaoActionForm.setDescricaoOperacao("");
			filtrarOperacaoActionForm.setIdTipoOperacao(""+ ConstantesSistema.NUMERO_NAO_INFORMADO);
			filtrarOperacaoActionForm.setIdFuncionalidade("");
			filtrarOperacaoActionForm.setDescricaoFuncionalidade("");
			sessao.setAttribute("indicadorAtualizar", "1");
		}
		
		//Retorna o mapeamento contido na vari�vel retorno
        return retorno;
    }

    
    /**
	 * Pesquisa a funcionalidade digitada na base de dados de acordo com o c�digo passado
	 *
	 * [FS0002 - Pesquisar Funcionalidade]
	 *
	 * @author Pedro Alexandre
	 * @date 11/05/2006
	 *
	 * @param idFuncionalidade
	 * @return
	 */
	private Funcionalidade pesquisarFuncionalidade(String idFuncionalidade){
		//Cria a vari�vel que vai armazenar a funcionalidade pesquisada
		Funcionalidade funcionalidade = null;
		
		//Cria o filtro para pesquisa e seta o c�digo da funcionalidade informada no filtro
		FiltroFuncionalidade filtroFuncionalidade = new FiltroFuncionalidade();
		filtroFuncionalidade.adicionarParametro(new ParametroSimples(FiltroFuncionalidade.ID, idFuncionalidade));
		
		//Pesquisa a funcionalidade na base de dados
		Collection colecaoFuncionalidade = Fachada.getInstancia().pesquisar(filtroFuncionalidade,Funcionalidade.class.getName());
		
		//Caso exista a funcionalidade cadastrada na base de dados 
		//recupera a funcionalidade da cole��o
		if(colecaoFuncionalidade != null && !colecaoFuncionalidade.isEmpty()){
			funcionalidade = (Funcionalidade) Util.retonarObjetoDeColecao(colecaoFuncionalidade);
		}
		
		//Retorna a funcionalidade pesquisa ou nulo se a funcionalidade n�o for encontrada
		return funcionalidade;
		
	}
}
