package gcom.gui.faturamento.conta;

import gcom.arrecadacao.pagamento.FiltroPagamento;
import gcom.arrecadacao.pagamento.Pagamento;
import gcom.fachada.Fachada;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.conta.ContaMotivoCancelamento;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class CancelarContaAction extends GcomAction {

    
    public ActionForward execute(ActionMapping actionMapping,
            ActionForm actionForm, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {

        //Seta o mapeamento de retorno
        ActionForward retorno = actionMapping
                .findForward("exibirCancelarConta");
        
        HttpSession sessao = httpServletRequest.getSession(false);
        
        //Inst�ncia do formul�rio que est� sendo utilizado
        CancelarContaActionForm cancelarContaActionForm = (CancelarContaActionForm) actionForm;

        Fachada fachada = Fachada.getInstancia();
        
        //Contas selecionadas pelo usu�rio
        String identificadoresConta = cancelarContaActionForm.getContaSelected();
        
        String[] arrayIdentificadores = identificadoresConta.split(",");
        
        //Controlamos o cancelamento de contas pagas
        Object[] arrayValidaContasCanceladasPagas = validarContasCanceladasPagasRA( 
                arrayIdentificadores,
                httpServletRequest,
                actionMapping );
        
        if ( ( Boolean ) arrayValidaContasCanceladasPagas[2] ){
            return ( ActionForward ) arrayValidaContasCanceladasPagas[0];
        }
        
        Boolean confirmadoCancelamentoContasPagas = ( Boolean )arrayValidaContasCanceladasPagas[1];
        
        if ( confirmadoCancelamentoContasPagas != null && !confirmadoCancelamentoContasPagas ){
            return retorno;
        }
        
        if ( confirmadoCancelamentoContasPagas == null ){
        	confirmadoCancelamentoContasPagas = new Boolean( Boolean.FALSE );
        }
        
		int flag = 0;
		
		for (int i = 0; i < arrayIdentificadores.length; i++) {
			// Carregando a conta que est� na base
			String dadosConta = arrayIdentificadores[i];
			String[] idUltimaAlteracao = dadosConta.split("-");
			
			Calendar data = new GregorianCalendar();
			data.setTimeInMillis(new Long(idUltimaAlteracao[1].trim())
					.longValue());
			data.set(Calendar.MILLISECOND, 0);
			
			Object dataUltimaAlteracaoConta = fachada.pesquisarDataUltimaAlteracaoConta(new Integer(idUltimaAlteracao[0]));
			
			Calendar calendarDataUltimaAlteracaoNaBase = Calendar.getInstance();
			calendarDataUltimaAlteracaoNaBase.setTime((Date)dataUltimaAlteracaoConta);
			calendarDataUltimaAlteracaoNaBase.set(Calendar.MILLISECOND, 0);
			
			if (calendarDataUltimaAlteracaoNaBase.compareTo(data) > 0 ) {
				httpServletRequest.setAttribute("reloadPage", "OK");
				flag = 1;
				sessao.setAttribute("erroConcorrencia","erroConcorrencia");
			}
		}

        
        //MotivoCancelamentoConta selecinado pelo usu�rio
        ContaMotivoCancelamento contaMotivoCancelamento = new ContaMotivoCancelamento();
        contaMotivoCancelamento.setId(new Integer(cancelarContaActionForm.getMotivoCancelamentoContaID()));
        
        
        if (sessao.getAttribute("colecaoContaImovel") != null && (identificadoresConta != null &&
        	!identificadoresConta.equalsIgnoreCase("")) && flag == 0){
        
        	Collection<Conta> colecaoContaImovel = (Collection) sessao.getAttribute("colecaoContaImovel");

        	Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");
        	
        	//Cancelando uma ou v�rias contas
        	fachada.cancelarConta(colecaoContaImovel, identificadoresConta, contaMotivoCancelamento,
        	usuarioLogado, confirmadoCancelamentoContasPagas );
        	
        	//Realizar um reload na tela de manter conta
        	httpServletRequest.setAttribute("reloadPage", "OK");
        	
        }
        sessao.setAttribute("cancelar", "1");
                
        return retorno;
    }
    
    /**
     * 
     * [UC0146ManterConta] - Manter Conta
     * 
     * Metodo verifica se existe contas j� pagas nas contas que ser�o canceladas.
     * Em caso afirmativo, apresenta todos os meses de referencia das contas
     * para informar ao usu�rio.
     *
     * @author bruno
     * @date 06/05/2009
     *
     * @param String[]: Vetor com todos os id's das contas
     * @param request: Onde se ser�o informados os parametros para gera��o da p�gina
     * @param actionMapping: Necess�rio para gera��o da p�gina
     * 
     * @return Object[3]
     * 
     *      Object[0]: ActionFoward com a tela a ser mostrada
     *      Object[1]: Se o usu�rio confimou ou n�o a inser��o do novo ra
     *      Object[2]: Se ser� redirecionado ao usu�rio perguntando se as
     *                 contas ter�o os cnta_id removidos dos pagamentos.
     */
    private Object[] validarContasCanceladasPagasRA( 
            String[] idsContas, 
            HttpServletRequest request,
            ActionMapping actionMapping ){
        
        Object[] retorno = new Object[3];
        
        // Verificamos se ja foi confimado...
        retorno[1] = 
            ( request.getParameter("confirmado") != null ? 
                    request.getParameter("confirmado").equals("ok") : 
                        null );
        
        retorno[2] = new Boolean( Boolean.FALSE );
        
        if ( retorno[1] == null ){
            Fachada fachada = Fachada.getInstancia();
            
            Collection<String> colContasPagas = new ArrayList();

            for ( int i=0; i<idsContas.length; i++ ){
                String dadosConta = idsContas[i];
                String id = dadosConta.split("-")[0];
                
                
                FiltroPagamento filtro = 
                    new FiltroPagamento();
                
                filtro.adicionarParametro( new ParametroSimples( 
                        FiltroPagamento.CONTA_ID, 
                        Integer.valueOf(id) ) );
                
                Collection<Pagamento> colPagamento = 
                    fachada.pesquisar( 
                            filtro, 
                            Pagamento.class.getName() ); 
                
                Pagamento pagamento = ( Pagamento )Util.retonarObjetoDeColecao( colPagamento );
                
                if ( pagamento != null ){
                    if ( colContasPagas.size() == 0 ){
                        colContasPagas.add( Util.formatarAnoMesParaMesAno( pagamento.getAnoMesReferenciaPagamento() ) );
                    } else {
                        colContasPagas.add( ", " + Util.formatarAnoMesParaMesAno( pagamento.getAnoMesReferenciaPagamento() ) );    
                    }
                    
                }
            }
            
            if ( colContasPagas.size() > 0 ){            
                String[] arrayIdsContas = new String[ colContasPagas.size() ];            
                System.arraycopy( colContasPagas.toArray(), 0, arrayIdsContas, 0, arrayIdsContas.length );            
                
                request.setAttribute("caminhoActionConclusao",
                        "/gsan/cancelarContaAction.do");
                request.setAttribute("cancelamento", "TRUE");
                request.setAttribute("nomeBotao1", "Sim");
                request.setAttribute("nomeBotao2", "N�o");
    
                retorno[0] = montarPaginaConfirmacao(
                        "atencao.contas_cancelamento_pagas",
                        request, 
                        actionMapping, 
                        arrayIdsContas );
                
                retorno[2] = new Boolean( Boolean.TRUE );
            }
            return retorno;
        }       
        
        retorno[0] = actionMapping.findForward("telaSucesso");
        return retorno;
    }
}
