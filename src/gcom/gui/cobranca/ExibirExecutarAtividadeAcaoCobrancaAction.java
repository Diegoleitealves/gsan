package gcom.gui.cobranca;

import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * action respons�vel pela exibi��o da p�gina de executar atividade de a��o de cobran�a
 * 
 * @author  pedro alexandre
 * @created 31 de Janeiro de 2006
 */
public class ExibirExecutarAtividadeAcaoCobrancaAction extends GcomAction {

    
    public ActionForward execute(ActionMapping actionMapping,
            					 ActionForm actionForm, 
            					 HttpServletRequest httpServletRequest,
            					 HttpServletResponse httpServletResponse) {
    	
    	//cria a vari�vel de retorno e seta o mapeamento para a tela de executar atividade de a��o de cobran�a 
        ActionForward retorno = actionMapping.findForward("exibirExecutarAtividadeAcaoCobrancaAction");

        //cria uma inst�ncia da fachada
        Fachada fachada = Fachada.getInstancia();
                
        //pesquisa os par�metros do sistema
		SistemaParametro sistemaParametros = fachada.pesquisarParametrosDoSistema();
		
		//recupera o Ano/M�s de refer�ncia do ciclo de cobran�a
		Integer anoMesCicloCobranca = sistemaParametros.getAnoMesFaturamento();
        
		//manda o Ano/M�s de refer�ncia do ciclo de cobran�a no request
		httpServletRequest.setAttribute("anoMesCicloCobranca",anoMesCicloCobranca);
		
        /*Pesquisar as atividades de cobran�a do cronograma que foram previamente comandas
        =============================================================================== */
        //realizando a pesquisa das atividades de cobran�a de cronograma
        Collection colecaoAtividadesCobrancaCronograma = fachada.pesquisarCobrancaAcaoAtividadeCronograma();	
        
        
        /*Pesquisar as atividades de cobran�a eventuais que foram previamente comandas
        =========================================================================== */
        //realizando a pesquisa das atividades de cobran�a eventuais	
        Collection colecaoAtividadesCobrancaEventuais = fachada.pesquisarCobrancaAcaoAtividadeComando();
        
        
        //[FS0002] - Verificar a exist�ncia de atividade do cronograma comandada
        if (colecaoAtividadesCobrancaCronograma == null || colecaoAtividadesCobrancaCronograma.isEmpty()){
        	//[FS0003] - Verificar exist�ncia de atividade eventual comandada
        	if (colecaoAtividadesCobrancaEventuais == null || colecaoAtividadesCobrancaEventuais.isEmpty()){
        		throw new ActionServletException("atencao.naocadastrado", null, "Atividade(s) de a��o de cobran�a");
        	}
        }

        //manda a cole��o de atividades de cobran�a de cronograma no request para a p�gina
        httpServletRequest.setAttribute("colecaoAtividadesCobrancaCronograma",colecaoAtividadesCobrancaCronograma);
        
        //manda a cole��o de atividades de cobran�a eventuais no request para a p�gina
        httpServletRequest.setAttribute("colecaoAtividadesCobrancaEventuais",colecaoAtividadesCobrancaEventuais);
        
        //retorna o mapeamento contido na vari�vel retorno
        return retorno;
    }
}


