package gcom.gui.arrecadacao.pagamento;

import gcom.arrecadacao.ArrecadacaoForma;
import gcom.arrecadacao.FiltroArrecadacaoForma;
import gcom.arrecadacao.FiltroAvisoBancario;
import gcom.arrecadacao.aviso.AvisoBancario;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action que inicializa a primeira p�gina do processo de inserir pagamentos
 * [UC0971] Inserir Pagamentos para Faturas Especiais
 * 
 * @author 	Vivianne Sousa
 * @created	21/12/2009
 */
public class ExibirInserirPagamentosFaturasEspeciaisAvisoBancarioAction extends GcomAction {

    public ActionForward execute(ActionMapping actionMapping,
            					 ActionForm actionForm, 
            					 HttpServletRequest httpServletRequest,
            					 HttpServletResponse httpServletResponse) {
    	
        //seta o mapeamento de retorno para a p�gina da primeira aba
        ActionForward retorno = actionMapping.findForward("inserirPagamentosFaturasEspeciaisAvisoBancario");
        
        PagamentosFaturasEspeciaisActionForm pagamentoActionForm = (PagamentosFaturasEspeciaisActionForm) actionForm;

        //cria uma inst�ncia da fachada
        Fachada fachada = Fachada.getInstancia();
        
        //cria uma inst�ncia da sess�o
        HttpSession sessao = httpServletRequest.getSession(false);
        
        //cria o filtro de forma de arrecada��o
        FiltroArrecadacaoForma filtroArrecadacaoForma = new FiltroArrecadacaoForma();
        
        //seta a ordena��o do resultado da pesquisa
        filtroArrecadacaoForma.setCampoOrderBy(FiltroArrecadacaoForma.DESCRICAO);

        //pesquisa as formas de arrecada��o no sistema
        Collection<ArrecadacaoForma> colecaoFormaArrecadacao = fachada.pesquisar(filtroArrecadacaoForma, ArrecadacaoForma.class.getName());
        	
        //se a cole��o de forma de arrecada��o estiver nula ou vazia 
        if(colecaoFormaArrecadacao == null || colecaoFormaArrecadacao.isEmpty()){
        	//[FS0002] levanta exce��o
        	throw new ActionServletException("atencao.naocadastrado", null,"Forma de Arrecada��o");
        }else{
        	//manda a cole��o de forma de arrecada��o para a sess�o
        	sessao.setAttribute("colecaoFormaArrecadacao", colecaoFormaArrecadacao);	
        }
        
        SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
        Calendar dataCorrente = new GregorianCalendar();
        
        //Data Corrente
        httpServletRequest.setAttribute("dataAtual", formatoData.format(dataCorrente.getTime()));
        
        String idAvisoBancario = pagamentoActionForm.getIdAvisoBancario();
        
        if (idAvisoBancario != null && !idAvisoBancario.trim().equals("")) {
        	FiltroAvisoBancario filtroAvisoBancario = new FiltroAvisoBancario();
        	filtroAvisoBancario.adicionarCaminhoParaCarregamentoEntidade(FiltroAvisoBancario.ARRECADADOR);
        	filtroAvisoBancario.adicionarParametro(new ParametroSimples(FiltroAvisoBancario.ID, idAvisoBancario));
        	
        	Collection colecaoAvisoBancario = fachada.pesquisar(filtroAvisoBancario, AvisoBancario.class.getName());
        	
        	if (colecaoAvisoBancario != null && !colecaoAvisoBancario.isEmpty()) {
        		AvisoBancario avisoBancario = (AvisoBancario) Util.retonarObjetoDeColecao(colecaoAvisoBancario);
        		
        		pagamentoActionForm.setCodigoAgenteArrecadador(avisoBancario.getArrecadador().getCodigoAgente().toString());
        		pagamentoActionForm.setDataLancamentoAviso(Util.formatarData(avisoBancario.getDataLancamento()));
        		pagamentoActionForm.setNumeroSequencialAviso(avisoBancario.getNumeroSequencial().toString());
        		
        	} else {
        		
        		pagamentoActionForm.setIdAvisoBancario("");
        		pagamentoActionForm.setCodigoAgenteArrecadador("");
        		pagamentoActionForm.setDataLancamentoAviso("AVISO INEXISTENTE");
        		pagamentoActionForm.setNumeroSequencialAviso("");
        		
        		httpServletRequest.setAttribute("avisoInexistente", true);
        	}
        } else {
        	pagamentoActionForm.setDataLancamentoAviso("");
        }
        
        //retorna o mapeamento contido na vari�vel retorno
        return retorno;
    }
}
