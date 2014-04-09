package gcom.gui.cobranca;

import gcom.cobranca.CobrancaAcaoAtividadeCronograma;
import gcom.cobranca.CobrancaGrupo;
import gcom.cobranca.CobrancaGrupoCronogramaMes;
import gcom.cobranca.FiltroCobrancaAcaoAtividadeCronograma;
import gcom.cobranca.FiltroCobrancaGrupo;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * < <Descri��o da Classe>>
 * 
 * @author Administrador
 */
public class ExibirManterCobrancaCronogramaAction extends GcomAction {

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

        //Seta o mapeamento de retorno
        ActionForward retorno = actionMapping
                .findForward("manterCobrancaCronograma");

        Fachada fachada = Fachada.getInstancia();
        
        CobrancaActionForm cobrancaActionForm = (CobrancaActionForm) actionForm;

        //Mudar isso quando tiver esquema de seguran�a
        HttpSession sessao = httpServletRequest.getSession(false);
        
        	FiltroCobrancaAcaoAtividadeCronograma filtroCobrancaAcaoAtividadeCronograma = (FiltroCobrancaAcaoAtividadeCronograma)sessao.getAttribute("filtroCobrancaAcaoAtividade");
        	
//        	 1� Passo - Pegar o total de registros atrav�s de um count da consulta que aparecer� na tela
    		Integer totalRegistros = fachada
    				.pesquisarCobrancaCronogramaCount(filtroCobrancaAcaoAtividadeCronograma);

    		// 2� Passo - Chamar a fun��o de Pagina��o passando o total de registros
    		retorno = this.controlarPaginacao(httpServletRequest, retorno,
    				totalRegistros);
    		
    		FiltroCobrancaAcaoAtividadeCronograma filtroCobrancaAcaoAtividadeCronogramaDois = new FiltroCobrancaAcaoAtividadeCronograma();
    		filtroCobrancaAcaoAtividadeCronogramaDois = (FiltroCobrancaAcaoAtividadeCronograma)sessao.getAttribute("filtroCobrancaAcaoAtividade");
//    		filtroCobrancaAcaoAtividadeCronogramaDois.adicionarCaminhoParaCarregamentoEntidade("cobrancaAcaoCronograma.cobrancaGrupoCronogramaMes.cobrancaGrupo");
    		
    		// 3� Passo - Obter a cole��o da consulta que aparecer� na tela passando o numero de paginas
    		// da pesquisa que est� no request
    		Collection colecaoCobrancaCronograma = fachada
    				.pesquisar(filtroCobrancaAcaoAtividadeCronogramaDois,
    				(Integer) httpServletRequest.getAttribute("numeroPaginasPesquisa"),
    				CobrancaAcaoAtividadeCronograma.class.getName());
    		
    		Collection colecaoCronogramaNova = new ArrayList();
    		
    		
    		
    		if(!colecaoCobrancaCronograma.isEmpty()){
    			Iterator iteratorColecaoCobrancaCronograma = colecaoCobrancaCronograma.iterator();
        		Collection colecaoCobrancaGrupo = new ArrayList();
        		while(iteratorColecaoCobrancaCronograma.hasNext()){
        			CobrancaGrupoCronogramaMes cobrancaGrupoCronogramaMes = (CobrancaGrupoCronogramaMes)iteratorColecaoCobrancaCronograma.next();
        			FiltroCobrancaGrupo filtroCobrancaGrupo = new FiltroCobrancaGrupo();
        			filtroCobrancaGrupo.adicionarParametro(new ParametroSimples(FiltroCobrancaGrupo.ID, cobrancaGrupoCronogramaMes.getCobrancaGrupo().getId()));
        			
        			colecaoCobrancaGrupo = fachada.pesquisar(filtroCobrancaGrupo, CobrancaGrupo.class.getName());
        			
        			cobrancaGrupoCronogramaMes.setCobrancaGrupo((CobrancaGrupo)colecaoCobrancaGrupo.iterator().next());
        			
        			colecaoCronogramaNova.add(cobrancaGrupoCronogramaMes);
        		}
    			//ordena��o atrav�s do sort---Feita pelo mesAnoReferencia
	            Collections.sort((List) colecaoCronogramaNova, new Comparator() {
	                public int compare(Object a, Object b) {
	                        String cobrancaAcaoAtividadeCronograma1 = ((CobrancaGrupoCronogramaMes) a).getMesAno();
	                        String cobrancaAcaoAtividadeCronograma2 = ((CobrancaGrupoCronogramaMes) b).getMesAno();
	
	                        return cobrancaAcaoAtividadeCronograma1.compareTo(cobrancaAcaoAtividadeCronograma2);
	                }
	            });
    		}
        
////      Aciona o controle de pagina��o para que sejam pesquisados apenas
//		// os registros que aparecem na p�gina
//		Map resultado = controlarPaginacao(httpServletRequest, retorno,
//				filtroCobrancaAcaoAtividadeCronograma, CobrancaAcaoAtividadeCronograma.class.getName());
//		Collection colecaoCobrancaCronograma = (Collection) resultado.get("colecaoRetorno");
//		retorno = (ActionForward) resultado.get("destinoActionForward");
//		
		String identificadorAtualizar = cobrancaActionForm.getIndicadorAtualizar();
		
		if (colecaoCobrancaCronograma.size()== 1 && identificadorAtualizar != null
				&& !identificadorAtualizar.trim().equals("2") ){
			// caso o resultado do filtro s� retorne um registro 
			// e o check box Atualizar estiver selecionado
			//o sistema n�o exibe a tela de manter, exibe a de atualizar 
			retorno = actionMapping.findForward("atualizarCobrancaCronograma");
			CobrancaGrupoCronogramaMes cobrancaGrupoCronogramaMes = (CobrancaGrupoCronogramaMes)Util.retonarObjetoDeColecao(colecaoCobrancaCronograma);
			sessao.setAttribute("idRegistroAtualizacao", new Integer (cobrancaGrupoCronogramaMes.getId()).toString());
			sessao.setAttribute("voltaFiltro", true);
		}else if(colecaoCobrancaCronograma.size()== 0){
			throw new
			 ActionServletException("atencao.pesquisa.nenhumresultado");
    	}else{
			sessao.setAttribute("colecaoCobrancaCronograma", colecaoCronogramaNova);
			sessao.removeAttribute("voltaFiltro");
		}


        return retorno;

    }
}
