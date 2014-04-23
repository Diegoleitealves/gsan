package gcom.gui.cobranca.spcserasa;

import gcom.cobranca.NegativacaoCriterioCpfTipo;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.FiltroUsuario;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Esta classe tem por finalidade validar as informa��es da 1� aba do processo de inser��o
 * de um Comando de Negativa��o
 *
 * @author Ana Maria
 * @date 06/11/2007
 */
public class InserirComandoNegativacaoDadosGeraisAction extends GcomAction {
	
	
	public ActionForward execute(ActionMapping actionMapping,
            ActionForm actionForm, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {

        ActionForward retorno = actionMapping.findForward("");

		HttpSession sessao = httpServletRequest.getSession(false);
		
		Fachada fachada = Fachada.getInstancia();
		
		InserirComandoNegativacaoActionForm inserirComandoNegativacaoActionForm = (InserirComandoNegativacaoActionForm) actionForm;
		
		//[FS0026] Verificar exist�ncia de comando para o negativador na data
		boolean existeComando = fachada.verificarExistenciaComandoNegativador(inserirComandoNegativacaoActionForm.getIdNegativador(),Util.converteStringParaDate(inserirComandoNegativacaoActionForm.getDataPrevista()));
	          
	    if(existeComando){
	    	throw new ActionServletException("atencao.existe_comando_negativado_data", "inserirComandoNegativacaPorCriterioWizardAction.do?voltar=ok&entrou=ok&action=exibirInserirComandoNegativacaoDadosGeraisAction"
    				,new Exception(), inserirComandoNegativacaoActionForm.getDataPrevista(),inserirComandoNegativacaoActionForm.getNomeNegativador());
	    }
		
        //Verificar exist�ncia Usu�rio 
        if(inserirComandoNegativacaoActionForm.getUsuario() != null && !inserirComandoNegativacaoActionForm.getUsuario().equals("")){        	
	        String usuario = inserirComandoNegativacaoActionForm.getUsuario();
	        	
	        	FiltroUsuario filtroUsuario = new FiltroUsuario();  	            
	        	filtroUsuario.adicionarParametro(new ParametroSimples(FiltroUsuario.ID, usuario));
	            
	            Collection colecaoUsuario = fachada.pesquisar(
	                    filtroUsuario,Usuario.class.getName());
	            
	            if (colecaoUsuario != null && !colecaoUsuario.isEmpty()) {	            	
	            	inserirComandoNegativacaoActionForm.setUsuario(""
							+ ((Usuario) ((List) colecaoUsuario).get(0)).getId());
	            	inserirComandoNegativacaoActionForm.setNomeUsuario(""
							+ ((Usuario) ((List) colecaoUsuario).get(0)).getNomeUsuario());
				} else {
					throw new ActionServletException("atencao.pesquisa.usuario.inexistente");
				}
        }
        
		//Verificar exist�ncia de Titularidades adicionadas
		Collection colecaoNegativacaoCriterioCpfTipo = (Collection)sessao.getAttribute("colecaoNegativacaoCriterioCpfTipo"); 
		if(colecaoNegativacaoCriterioCpfTipo == null || colecaoNegativacaoCriterioCpfTipo.isEmpty()){
			throw new ActionServletException("atencao.campo.informado", null, "Titularidade do CPF/CNPJ da Negativa��o");
		}else{ //Verificar ordem e coincidente das Titularidade
			Integer tamanhoColecao =colecaoNegativacaoCriterioCpfTipo.size();
			if(tamanhoColecao.equals(1)){
				NegativacaoCriterioCpfTipo ncCpfTipo = (NegativacaoCriterioCpfTipo) Util.retonarObjetoDeColecao(colecaoNegativacaoCriterioCpfTipo);
				ncCpfTipo.setIndicadorCoincidente(new Short("2"));
			}
		}
        
		atualizaColecoesNaSessao( sessao, httpServletRequest);
	
        return retorno;
	}
	
	
	private void atualizaColecoesNaSessao(HttpSession sessao,
			HttpServletRequest httpServletRequest){
    	     	
		//colecaoNegativacaoCriterioCpfTipo
		if (sessao.getAttribute("colecaoNegativacaoCriterioCpfTipo") != null
		&& !sessao.getAttribute("colecaoNegativacaoCriterioCpfTipo").equals("")) {
		
			Collection colecaoNegativacaoCriterioCpfTipo = (Collection) sessao
			.getAttribute("colecaoNegativacaoCriterioCpfTipo");
			
			Integer tamanhoColecao = colecaoNegativacaoCriterioCpfTipo.size();
			if(!tamanhoColecao.equals(1)){		
				// cria as vari�veis para recuperar os par�metros do request e jogar
				// no objeto  NegativacaoCriterioCpfTipo
				String ordem = null;
				String coincidente = null;
				
				Integer qtdOrdem = 0;
				Integer qtdCoincidente = 0;
				
				Iterator iteratorNegativacaoCriterioCpfTipo = colecaoNegativacaoCriterioCpfTipo.iterator();
				
				while (iteratorNegativacaoCriterioCpfTipo.hasNext()) {
					NegativacaoCriterioCpfTipo negativacaoCriterioCpfTipo = 
						(NegativacaoCriterioCpfTipo) iteratorNegativacaoCriterioCpfTipo.next();
					
					Integer idTitularidade = negativacaoCriterioCpfTipo.getCpfTipo().getId();
	
					ordem = (String) httpServletRequest.getParameter("ordem" + idTitularidade);
					coincidente = (String) httpServletRequest.getParameter("coincidente" + idTitularidade);
					
					if((ordem == null || ordem.equals("")) && (coincidente == null || coincidente.equals(""))){
						throw new ActionServletException("atencao.informe_ordem");	
					}
					
					if (ordem != null && !ordem.equals("")) {
						qtdOrdem = qtdOrdem + 1;
						negativacaoCriterioCpfTipo.setNumeroOrdemSelecao(new Short(ordem));
					}
					
					if(coincidente != null && !coincidente.equals("")){
						qtdCoincidente = qtdCoincidente + 1;
						negativacaoCriterioCpfTipo.setIndicadorCoincidente(new Short("1"));	
					}else{
						negativacaoCriterioCpfTipo.setIndicadorCoincidente(new Short("2"));			
					}

				}
				
				if(qtdCoincidente.equals(tamanhoColecao) && !qtdOrdem.equals(0)){
					throw new ActionServletException("atencao.nao_informe_oredem");						
				}
				if(!qtdCoincidente.equals(tamanhoColecao) && !qtdOrdem.equals(tamanhoColecao)){
					throw new ActionServletException("atencao.informe_ordem");				
				}
			}			
		}	        	
    }


}
