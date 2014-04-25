package gcom.gui.cobranca;

import gcom.fachada.Fachada;
import gcom.gui.GcomAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Permite consultar comandos de a��o de cobran�a 
 * [UC0326] Filtrar Comandos de A��o de Conbran�a - Cronograma
 * @author Rafael Santos
 * @since 08/05/2006
 */
public class ExibirFiltrarComandosAcaoCobrancaCronogramaAction  extends GcomAction{
	
	
	/**
	 * 
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
    public ActionForward execute(ActionMapping actionMapping,
            ActionForm actionForm, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {

        //Seta o mapeamento de retorno
        ActionForward retorno = actionMapping
                .findForward("exibirFiltrarComandosAcaoCobrancaCronograma");
        
        Fachada fachada = Fachada.getInstancia();
        
        FiltrarComandosAcaoCobrancaCronogramaActionForm filtrarComandosAcaoCobrancaCronogramaActionForm =
        	(FiltrarComandosAcaoCobrancaCronogramaActionForm) actionForm;
        
        if(filtrarComandosAcaoCobrancaCronogramaActionForm.getSituacaoComando() == null){
        	filtrarComandosAcaoCobrancaCronogramaActionForm.setSituacaoComando("Todos");	
        }
        if(filtrarComandosAcaoCobrancaCronogramaActionForm.getSituacaoCronograma() == null){
        	filtrarComandosAcaoCobrancaCronogramaActionForm.setSituacaoCronograma("Todos");	
        }
        
        HttpSession sessao = httpServletRequest.getSession(false); 
        
        //limpa o filtro da sess�o caso ja tenha utilizado
        if (sessao.getAttribute("colecaoCobrancaAcaoAtividadeEventual") != null){
        	sessao.removeAttribute("colecaoCobrancaAcaoAtividadeEventual");
        }
        
        if (sessao.getAttribute("colecaoCobrancaAcaoAtividadeCronograma") != null){
        	sessao.removeAttribute("colecaoCobrancaAcaoAtividadeCronograma");
        }
        
        //CARREGAR AS COBRAN�AS GRUPO
		if (sessao.getAttribute("colecaoGrupoCobranca") == null) {
			sessao.setAttribute("colecaoGrupoCobranca", fachada
					.obterColecaoCobrancaGrupo());
		}

		// CARREGAR AS COBRAN�AS ATIVIDADE
		if (sessao.getAttribute("colecaoAtividadeCobranca") == null) {
			sessao.setAttribute("colecaoAtividadeCobranca", fachada
					.obterColecaoCobrancaAtividade());
		}

		// CARREGAR AS COBRAN�AS ACAO
		if (sessao.getAttribute("colecaoAcaoCobranca") == null) {
			sessao.setAttribute("colecaoAcaoCobranca", fachada
					.obterColecaoCobrancaAcao());
		}
        
		String carregando = httpServletRequest.getParameter("carregando");
		
		if(carregando != null && !carregando.equals("")){
			if (sessao.getAttribute("filtrarComandosAcaoCobrancaCronogramaActionForm") != null) {
				
		        FiltrarComandosAcaoCobrancaCronogramaActionForm filtrarComandosAcaoCobrancaCronogramaActionFormRecarregar =
		        	(FiltrarComandosAcaoCobrancaCronogramaActionForm) sessao.getAttribute("filtrarComandosAcaoCobrancaCronogramaActionForm");

		        filtrarComandosAcaoCobrancaCronogramaActionForm.setPeriodoRealizacaoComandoInicial(filtrarComandosAcaoCobrancaCronogramaActionFormRecarregar.getPeriodoRealizacaoComandoInicial());
		        filtrarComandosAcaoCobrancaCronogramaActionForm.setPeriodoRealizacaoComandoFinal(filtrarComandosAcaoCobrancaCronogramaActionFormRecarregar.getPeriodoRealizacaoComandoFinal());
		        filtrarComandosAcaoCobrancaCronogramaActionForm.setGrupoCobranca(filtrarComandosAcaoCobrancaCronogramaActionFormRecarregar.getGrupoCobranca());
		        filtrarComandosAcaoCobrancaCronogramaActionForm.setAcaoCobranca(filtrarComandosAcaoCobrancaCronogramaActionFormRecarregar.getAcaoCobranca());
		        filtrarComandosAcaoCobrancaCronogramaActionForm.setAtividadeCobranca(filtrarComandosAcaoCobrancaCronogramaActionFormRecarregar.getAtividadeCobranca());
		        filtrarComandosAcaoCobrancaCronogramaActionForm.setPeriodoPrevisaoComandoInicial(filtrarComandosAcaoCobrancaCronogramaActionFormRecarregar.getPeriodoPrevisaoComandoInicial());
		        filtrarComandosAcaoCobrancaCronogramaActionForm.setPeriodoPrevisaoComandoFinal(filtrarComandosAcaoCobrancaCronogramaActionFormRecarregar.getPeriodoPrevisaoComandoFinal());
		        filtrarComandosAcaoCobrancaCronogramaActionForm.setPeriodoComandoInicial(filtrarComandosAcaoCobrancaCronogramaActionFormRecarregar.getPeriodoComandoInicial());
		        filtrarComandosAcaoCobrancaCronogramaActionForm.setPeriodoComandoFinal(filtrarComandosAcaoCobrancaCronogramaActionFormRecarregar.getPeriodoComandoFinal());
		        filtrarComandosAcaoCobrancaCronogramaActionForm.setPeriodoRealizacaoComandoInicial(filtrarComandosAcaoCobrancaCronogramaActionFormRecarregar.getPeriodoRealizacaoComandoInicial());
		        filtrarComandosAcaoCobrancaCronogramaActionForm.setPeriodoRealizacaoComandoFinal(filtrarComandosAcaoCobrancaCronogramaActionFormRecarregar.getPeriodoRealizacaoComandoFinal());
		        filtrarComandosAcaoCobrancaCronogramaActionForm.setIntervaloValorDocumentosInicial(filtrarComandosAcaoCobrancaCronogramaActionFormRecarregar.getIntervaloValorDocumentosInicial());
		        filtrarComandosAcaoCobrancaCronogramaActionForm.setIntervaloValorDocumentosFinal(filtrarComandosAcaoCobrancaCronogramaActionFormRecarregar.getIntervaloValorDocumentosFinal());
		        filtrarComandosAcaoCobrancaCronogramaActionForm.setIntervaloQuantidadeDocumentosInicial(filtrarComandosAcaoCobrancaCronogramaActionFormRecarregar.getIntervaloQuantidadeDocumentosInicial());
		        filtrarComandosAcaoCobrancaCronogramaActionForm.setIntervaloQuantidadeDocumentosFinal(filtrarComandosAcaoCobrancaCronogramaActionFormRecarregar.getIntervaloQuantidadeDocumentosFinal());
		        filtrarComandosAcaoCobrancaCronogramaActionForm.setIntervaloQuantidadeItensDocumentosInicial(filtrarComandosAcaoCobrancaCronogramaActionFormRecarregar.getIntervaloQuantidadeItensDocumentosInicial());
		        filtrarComandosAcaoCobrancaCronogramaActionForm.setIntervaloQuantidadeItensDocumentosFinal(filtrarComandosAcaoCobrancaCronogramaActionFormRecarregar.getIntervaloQuantidadeItensDocumentosFinal());
		        filtrarComandosAcaoCobrancaCronogramaActionForm.setSituacaoComando(filtrarComandosAcaoCobrancaCronogramaActionFormRecarregar.getSituacaoComando());
		        filtrarComandosAcaoCobrancaCronogramaActionForm.setSituacaoCronograma(filtrarComandosAcaoCobrancaCronogramaActionFormRecarregar.getSituacaoCronograma());
			}
		}
		
        return retorno;
    }

}
