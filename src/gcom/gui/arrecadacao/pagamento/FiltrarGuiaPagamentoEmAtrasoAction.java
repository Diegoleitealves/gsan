package gcom.gui.arrecadacao.pagamento;

import gcom.arrecadacao.pagamento.FiltroGuiaPagamento;
import gcom.gui.ActionServletException;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.arrecadacao.pagamento.RelatorioGuiaPagamentoEmAtraso;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.Intervalo;
import gcom.util.filtro.ParametroSimples;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class FiltrarGuiaPagamentoEmAtrasoAction extends
	ExibidorProcessamentoTarefaRelatorio {

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
                .findForward("retornarFiltroFaturamentoCronograma");

        
        //Mudar isso quando tiver esquema de seguran�a
        FiltrarGuiaPagamentoEmAtrasoActionForm form = (FiltrarGuiaPagamentoEmAtrasoActionForm) actionForm;
        
        RelatorioGuiaPagamentoEmAtraso relatorioGuiaPagamentoEmAtraso = 
        	new RelatorioGuiaPagamentoEmAtraso();
        
        FiltroGuiaPagamento filtroGuiaPagamento = new FiltroGuiaPagamento();
        
        boolean peloMenosUmParametroInformado = false;
        
        //Financiamento Tipo
        if(form.getFinanciamentoTipoId() != null 
        		&& !form.getFinanciamentoTipoId().equals(ConstantesSistema.NUMERO_NAO_INFORMADO + "")){
        	
        	filtroGuiaPagamento.adicionarParametro(new ParametroSimples(FiltroGuiaPagamento.FINANCIAMENTO_TIPO_ID,
        			form.getFinanciamentoTipoId()));
        	
        	relatorioGuiaPagamentoEmAtraso.addParametro("financiamentoTipo",
    				form.getFinanciamentoTipoId());
        	
        	peloMenosUmParametroInformado = true;
        	
        }
        
        //Vencimento Inicial
        if(form.getVencimentoInicial() != null && !form.getVencimentoInicial().trim().equals("")){
        	Date vencimentoInicial = Util.converteStringParaDate(form.getVencimentoInicial());
        	Date vencimentoFinal = null;
        	if(form.getVencimentoFinal() != null && !form.getVencimentoFinal().trim().equals("")){
        		vencimentoFinal = Util.converteStringParaDate(form.getVencimentoFinal());
        		if(vencimentoInicial.compareTo(vencimentoFinal) <= 0){
        			filtroGuiaPagamento.adicionarParametro(new Intervalo(FiltroGuiaPagamento.DATA_VENCIMENTO,vencimentoInicial, vencimentoFinal));
        		}else{
        			throw new ActionServletException("atencao.data.inicial.maior.final");
        		}
        		
        	}else{
        		vencimentoFinal = vencimentoInicial;
        		
        		filtroGuiaPagamento.adicionarParametro(new ParametroSimples(FiltroGuiaPagamento.DATA_VENCIMENTO,vencimentoInicial));
        	}
        	
        	Date dataAtual = new Date();
        	
        	if(vencimentoFinal.compareTo(dataAtual) > 0){
        		//FS0002
        		throw new ActionServletException("atencao.data.maior.que.atual", null, Util.formatarData(dataAtual));
        	}
        	
        	relatorioGuiaPagamentoEmAtraso.addParametro("vencimentoInicial",
    				form.getVencimentoInicial());
        	relatorioGuiaPagamentoEmAtraso.addParametro("vencimentoFinal",
    				form.getVencimentoFinal());
        	
        	peloMenosUmParametroInformado = true;
        	
        }
        
        //Vencimento Inicial
        if(form.getReferenciaInicialContabil() != null && !form.getReferenciaInicialContabil().trim().equals("")){
        	int refInicial = Util.formatarMesAnoComBarraParaAnoMes(form.getReferenciaInicialContabil());
        	int refFinal = 0;
        	if(form.getReferenciaFinalContabil() != null && !form.getReferenciaFinalContabil().trim().equals("")){
        		refFinal = Util.formatarMesAnoComBarraParaAnoMes(form.getReferenciaFinalContabil());
        		if(refInicial <= refFinal){
        			filtroGuiaPagamento.adicionarParametro(new Intervalo(FiltroGuiaPagamento.ANO_MES_REFERENCIA_CONTABIL,refInicial, refFinal));
        		}else{
        			throw new ActionServletException("atencao.referencia.inicial.maior.final");
        		}
        		
        	}else{
        		refFinal = refInicial;
        		
        		filtroGuiaPagamento.adicionarParametro(new ParametroSimples(FiltroGuiaPagamento.ANO_MES_REFERENCIA_CONTABIL,refInicial));
        	}
        	
        	relatorioGuiaPagamentoEmAtraso.addParametro("referenciaInicial",
    				form.getReferenciaInicialContabil());
        	relatorioGuiaPagamentoEmAtraso.addParametro("referenciaFinal",
    				form.getReferenciaFinalContabil());
        	
        	peloMenosUmParametroInformado = true;
        	
        }
        
        //Erro caso o usu�rio mandou filtrar sem nenhum par�metro
		if (!peloMenosUmParametroInformado) {
			throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
		}
		
		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");
		if (tipoRelatorio == null) {
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		relatorioGuiaPagamentoEmAtraso.addParametro("tipoFormatoRelatorio",
				Integer.parseInt(tipoRelatorio));
		
		relatorioGuiaPagamentoEmAtraso.addParametro("filtroGuiaPagamento",
				filtroGuiaPagamento);
		try {
			retorno = processarExibicaoRelatorio(relatorioGuiaPagamentoEmAtraso,
					tipoRelatorio, httpServletRequest, httpServletResponse,
					actionMapping);

		} catch (RelatorioVazioException ex) {
			// manda o erro para a p�gina no request atual
			reportarErros(httpServletRequest, "atencao.relatorio.vazio");

			// seta o mapeamento de retorno para a tela de aten��o de popup
			retorno = actionMapping.findForward("telaAtencaoPopup");

		}
        
        return retorno;
    }

}
