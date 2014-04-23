package gcom.gui.cobranca.parcelamento;

import gcom.cobranca.parcelamento.ParcelamentoFaixaValor;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.Util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class AdicionarPercentualFaixaValorPopupAction extends GcomAction {

    
    public ActionForward execute(ActionMapping actionMapping,
            ActionForm actionForm, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {

        //Seta o mapeamento de retorno
        ActionForward retorno = actionMapping
                .findForward("inserirPrestacoesParcelamentoPerfilPercentualFaixaValorPopup");

        //Mudar isso quando tiver esquema de seguran�a
        HttpSession sessao = httpServletRequest.getSession(false);

        //Inst�ncia do formul�rio que est� sendo utilizado
        InserirPrestacoesParcelamentoPerfilActionForm inserirPrestacoesParcelamentoPerfilActionForm = 
        	(InserirPrestacoesParcelamentoPerfilActionForm) actionForm;
        
        //Par�metros recebidos para adicionar um Percentual Por Faixa de Valor
        String valorMaximo = inserirPrestacoesParcelamentoPerfilActionForm.getValorMaxPercFaixaValor();
        String percentual = inserirPrestacoesParcelamentoPerfilActionForm.getPercentualPercFaixaValor();
        
    	Collection collectionParcelamentoFaixaValor = null;
    	ParcelamentoFaixaValor parcelamentoFaixaValor = new ParcelamentoFaixaValor();
        
        if (sessao.getAttribute("collectionParcelamentoFaixaValor") != null) {
        	collectionParcelamentoFaixaValor = (Collection) sessao
                    .getAttribute("collectionParcelamentoFaixaValor");
        } else {
        	collectionParcelamentoFaixaValor = new ArrayList();
        }
        
        //Valida��o dos campos recebidos
        if (valorMaximo == null || valorMaximo.equalsIgnoreCase("")){
        	throw new ActionServletException(
                    "atencao.campo_texto.obrigatorio", null, "Valor M�ximo");
        }
        parcelamentoFaixaValor.setValorFaixa(Util.formatarMoedaRealparaBigDecimal(valorMaximo));
        
        if (percentual == null || percentual.equalsIgnoreCase("")){
        	throw new ActionServletException(
                    "atencao.campo_texto.obrigatorio", null, "Percentual");
        }
        
		if (collectionParcelamentoFaixaValor != null && !collectionParcelamentoFaixaValor.isEmpty()){
			// se cole��o n�o estiver vazia
			// verificar se  Valor M�ximo ja foi informada	
			ParcelamentoFaixaValor parcelamentoFaixaValorAntigo = null;
			Iterator iterator = collectionParcelamentoFaixaValor.iterator();
		
			while (iterator.hasNext()) {
				parcelamentoFaixaValorAntigo = (ParcelamentoFaixaValor) iterator.next();
				
				if ((Util.formatarMoedaRealparaBigDecimal(valorMaximo)).equals
						(parcelamentoFaixaValorAntigo.getValorFaixa())){
			        //Limpa o formul�rio que adiciona Percentual Por Faixa de Valor
			        inserirPrestacoesParcelamentoPerfilActionForm.setValorMaxPercFaixaValor("");
			        inserirPrestacoesParcelamentoPerfilActionForm.setPercentualPercFaixaValor("");
					
					// Valor M�ximo j� informado.
					throw new ActionServletException(
					"atencao.valor_maximo_ja_informado");
				}
			}
		}
        
        parcelamentoFaixaValor.setPercentualFaixa(Util.formatarMoedaRealparaBigDecimal(percentual));

        parcelamentoFaixaValor.setUltimaAlteracao(new Date());
        
        collectionParcelamentoFaixaValor.add(parcelamentoFaixaValor);
        
        
        //Ordena a cole��o pelo valor m�ximo
		Collections.sort((List) collectionParcelamentoFaixaValor, new Comparator() {
			public int compare(Object a, Object b) {
				BigDecimal valorMax1 = new BigDecimal(((ParcelamentoFaixaValor) a).getValorFaixa().toString()) ;
				BigDecimal valorMax2 = new BigDecimal(((ParcelamentoFaixaValor) b).getValorFaixa().toString()) ;
		
				return valorMax1.compareTo(valorMax2);

			}
		});
				
        
        
        sessao.setAttribute("collectionParcelamentoFaixaValor",collectionParcelamentoFaixaValor);
        
        if (collectionParcelamentoFaixaValor == null || 
        		collectionParcelamentoFaixaValor.size() == 0){
			sessao.setAttribute("collectionParcelamentoFaixaValorVazia","1");
        }else{
			sessao.setAttribute("collectionParcelamentoFaixaValorVazia","2");
        }
        
        //Limpa o formul�rio que adiciona Percentual Por Faixa de Valor
        inserirPrestacoesParcelamentoPerfilActionForm.setValorMaxPercFaixaValor("");
        inserirPrestacoesParcelamentoPerfilActionForm.setPercentualPercFaixaValor("");
        
        return retorno;
    }

}
