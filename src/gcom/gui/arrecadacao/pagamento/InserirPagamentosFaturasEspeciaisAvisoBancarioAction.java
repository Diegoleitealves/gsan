package gcom.gui.arrecadacao.pagamento;

import gcom.arrecadacao.ArrecadacaoForma;
import gcom.arrecadacao.FiltroArrecadacaoForma;
import gcom.arrecadacao.FiltroAvisoBancario;
import gcom.arrecadacao.aviso.AvisoBancario;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.gui.StatusWizard;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action que finaliza a primeira p�gina do processo de inserir pagamentos
 * 
 * [UC0971] Inserir Pagamentos para Faturas Especiais
 * 
 * @author 	Vivianne Sousa
 * @created	21/12/2009
 */
public class InserirPagamentosFaturasEspeciaisAvisoBancarioAction extends GcomAction {

    public ActionForward execute(ActionMapping actionMapping,
            					 ActionForm actionForm, 
            					 HttpServletRequest httpServletRequest,
            					 HttpServletResponse httpServletResponse) {

    	//cria a vari�vel que vai armazenar o retorno
    	ActionForward retorno = null;
    	
    	//cria uma inst�ncia da sess�o
        HttpSession sessao = httpServletRequest.getSession(false);

        //recupera o form 
        PagamentosFaturasEspeciaisActionForm pagamentoActionForm = (PagamentosFaturasEspeciaisActionForm) actionForm;

        //cria uma inst�ncia da fachada
        Fachada fachada = Fachada.getInstancia();

        //recupera os dados informados na p�gina de aviso banc�rio
        String dataPagamento =  pagamentoActionForm.getDataPagamento();
        String idAvisoBancario =  pagamentoActionForm.getIdAvisoBancario();  
        String idFormaArrecadacao = pagamentoActionForm.getIdFormaArrecadacao();

        //se a data de pagamento n�o for informada 
        if(dataPagamento == null || dataPagamento.trim().equalsIgnoreCase("")){
        	//levanta a exce��o para a pr�xima camada
        	throw new ActionServletException("atencao.naoinformado",null, "Data de Pagamento");
        }
        
        //se o aviso banc�rio n�o for informado
        if(idAvisoBancario == null || idAvisoBancario.trim().equalsIgnoreCase("")){
        	//levanta a exce��o para a pr�xima camada
        	throw new ActionServletException("atencao.naoinformado",null, "Aviso Banc�rio");
        } else {

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
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Aviso Banc�rio");
			}

		}
        
        // se a forma de arrecada��o n�o for informada
        if(idFormaArrecadacao == null || idFormaArrecadacao.trim().equalsIgnoreCase("")){
        	//levanta a exce��o para a pr�xima camada
        	throw new ActionServletException("atencao.naoinformado",null, "Forma de Arrecada��o");
        }
        
        //cria o formato da data
        SimpleDateFormat dataFormato = new SimpleDateFormat("dd/MM/yyyy");

        try {
        	//tenta converter a data de apagamento
            dataFormato.parse(dataPagamento);

            //se n�o conseguir converter
        } catch (ParseException ex) {
        	//levanta a exce��o para a pr�xima camada
        	throw new ActionServletException("atencao.data_pagamento_invalida");
        }
        
        //recupera o status atual do wizard
        StatusWizard statusWizard = (StatusWizard) sessao.getAttribute("statusWizard");
                
        //cria o filtro de forma de arrecada��o
        FiltroArrecadacaoForma filtroArrecadacaoForma = new FiltroArrecadacaoForma();
        
        //seta o c�digo da forma de arrecada��o no filtro
        filtroArrecadacaoForma.adicionarParametro(new ParametroSimples(FiltroArrecadacaoForma.CODIGO, idFormaArrecadacao));
        
        //pesquisa a forma de arrecada��o no sistema
        Collection colecaoFormaArrecadacao = fachada.pesquisar(filtroArrecadacaoForma, ArrecadacaoForma.class.getName());
        
        //se n�o existir a forma de arrecada��o no sistema
        if(colecaoFormaArrecadacao == null || colecaoFormaArrecadacao.isEmpty()){
        	//levanta a exce��o para a pr�xima camada
        	throw new ActionServletException("atencao.naocadastrado",null, "Forma de Arrecada��o");
        	
        }else{
        	//recupera a forma de arrecada��o da cole��o
        	ArrecadacaoForma formaArrecadacao =(ArrecadacaoForma) Util.retonarObjetoDeColecao(colecaoFormaArrecadacao);
        	
        	//setaa descri��o da forma de arrecada��o no form
        	pagamentoActionForm.setDescricaoFormaArrecadacao(formaArrecadacao.getDescricao());
        }
        	
//        } else if(tipoInclusao != null && tipoInclusao.equalsIgnoreCase("caneta")){
            
        	//seta o status do wizard para a p�gina de inclus�o por caneta
        	statusWizard.inserirNumeroPaginaCaminho(statusWizard.new StatusWizardItem(
                    2, "TipoInclusaoA.gif", "TipoInclusaoD.gif",
                    "exibirInserirPagamentosFaturasEspeciaisTipoInclusaoCanetaAction",
                    "inserirPagamentosFaturasEspeciaisTipoInclusaoCanetaAction"));
        	
        	retorno = actionMapping.findForward("inserirPagamentosTipoInclusaoCaneta");
        	

//        }

 
    	
        //seta o form na sess�o
//        sessao.setAttribute("PagamentosFaturasEspeciaisActionForm",pagamentoActionForm);
        
        //seta o status do wizard na sess�o
        sessao.setAttribute("statusWizard",statusWizard);
        
        //retorna o mapeamento contido na vari�vel retorno
        return retorno;
    }
}
