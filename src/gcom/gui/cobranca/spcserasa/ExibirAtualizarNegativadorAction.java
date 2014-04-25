package gcom.gui.cobranca.spcserasa;

import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.FiltroCliente;
import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.cobranca.Negativador;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.spcserasa.FiltroNegativador;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.filtro.ParametroSimplesDiferenteDe;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action de exibir manter Negativador
 * @author Thiago Vieira
 * @created 22/12/2007
 */

public class ExibirAtualizarNegativadorAction extends GcomAction {
	
    public ActionForward execute(ActionMapping actionMapping,
            ActionForm actionForm, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {

    	//Inicializando Variaveis
        ActionForward retorno = actionMapping.findForward("atualizarNegativador");
        AtualizarNegativadorActionForm form = (AtualizarNegativadorActionForm) actionForm;
        HttpSession sessao = httpServletRequest.getSession(false);
        Collection collectionNegativador = null;
        FiltroNegativador filtroNegativador = new FiltroNegativador();
        Fachada fachada = Fachada.getInstancia();
        
        if (httpServletRequest.getParameter("reexibir") == null || !httpServletRequest.getParameter("reexibir").equals("true")){ 
	        // volta da msg de Contrato do Negativador j� utilizado, n�o pode ser
			// alterado nem exclu�do.
			String confirmado = httpServletRequest.getParameter("confirmado");
	
			String idNegativador = null;
			if (httpServletRequest.getParameter("reload") == null
					|| httpServletRequest.getParameter("reload").equalsIgnoreCase(
							"") && (confirmado == null || confirmado.equals(""))) {
				
				if (httpServletRequest.getParameter("idAtualizar") != null) {
					idNegativador = httpServletRequest.getParameter("idAtualizar");
					httpServletRequest.setAttribute("voltar", "filtrar");
					sessao.setAttribute("idRegistroAtualizacao", idNegativador);
				} else if (httpServletRequest.getParameter("idRegistroAtualizacao") == null) {
					idNegativador = (String) sessao.getAttribute("idRegistroAtualizacao");
					httpServletRequest.setAttribute("voltar", "filtrar");
				} else if (httpServletRequest.getParameter("idRegistroAtualizacao") != null) {
					idNegativador = httpServletRequest.getParameter("idRegistroAtualizacao");
					httpServletRequest.setAttribute("voltar", "manter");
					sessao.setAttribute("idRegistroAtualizacao", idNegativador);
				}
			} else {
				idNegativador = (String) sessao.getAttribute("idRegistroAtualizacao");
			}
			
			filtroNegativador.adicionarParametro(new ParametroSimples(FiltroNegativador.ID, idNegativador));
			filtroNegativador.adicionarCaminhoParaCarregamentoEntidade("cliente");
			filtroNegativador.adicionarCaminhoParaCarregamentoEntidade("imovel");
			
			collectionNegativador = fachada.pesquisar(filtroNegativador, Negativador.class.getName());
			
			Negativador negativador = (Negativador) ((List) collectionNegativador).get(0);
			
			form.setCodigoAgente(Short.toString(negativador.getCodigoAgente()));

			if (negativador.getCliente() != null){
				form.setCodigoCliente(negativador.getCliente().getId().toString());
				form.setNomeCliente(negativador.getCliente().getNome());
			}
			if (negativador.getImovel() != null){
				form.setCodigoImovel(negativador.getImovel().getId().toString());
				form.setInscricaoImovel(fachada.pesquisarInscricaoImovel(new Integer(negativador.getImovel().getId().toString())));
			}

			form.setInscricaoEstadual(negativador.getNumeroInscricaoEstadual());
			form.setAtivo(Short.toString(negativador.getIndicadorUso()));
			form.setIdNegativador(negativador.getId().toString());
			form.setTime(Long.toString(negativador.getUltimaAlteracao().getTime()));
			
			form.setOkAgente("1");
	        form.setOkCliente("1");
	        form.setOkImovel("1");
		
		// carregar dados a partir das entradas do form. 
    	}else {
    		String idNegativador = (String) sessao.getAttribute("idRegistroAtualizacao");
    		
			String idDigitadoEnterCliente = (String) form.getCodigoCliente();
	        String idDigitadoEnterImovel = (String) form.getCodigoImovel();
	        String idDigitadoEnterAgente = (String) form.getCodigoAgente();
	        
	        form.setOkAgente("2");
	        form.setOkCliente("2");
	        form.setOkImovel("2");
	        
	        // verifica se o codigo do cliente foi digitado
	        if (idDigitadoEnterCliente != null
	                && !idDigitadoEnterCliente.trim().equals("")
	                && Integer.parseInt(idDigitadoEnterCliente) > 0) {
	            
	        	FiltroCliente filtroCliente = new FiltroCliente();
				
				filtroCliente.adicionarParametro(new ParametroSimples(
						FiltroCliente.ID, idDigitadoEnterCliente));
				filtroCliente.adicionarParametro(new ParametroSimples(
						FiltroCliente.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
	
				Collection clienteEncontrado = fachada.pesquisar(filtroCliente,
						Cliente.class.getName());
	
				if (clienteEncontrado != null && !clienteEncontrado.isEmpty()) {
					// O Cliente foi encontrado
					if (((Cliente) ((List) clienteEncontrado).get(0))
							.getIndicadorUso().equals(ConstantesSistema.INDICADOR_USO_DESATIVO)) {
						throw new ActionServletException("atencao.cliente.inativo",	null, ""
										+ ((Cliente) ((List) clienteEncontrado).get(0)).getId());
					}
					
					
					
				 //-------------------------------------------------------------------
					
					Cliente cliente = (Cliente) Util.retonarObjetoDeColecao(clienteEncontrado);
					FiltroNegativador filtroNegat = new FiltroNegativador();
					filtroNegat.adicionarCaminhoParaCarregamentoEntidade("cliente");
					filtroNegat.adicionarParametro(new ParametroSimples(
							FiltroNegativador.NEGATIVADOR_CLIENTE, cliente.getId()));				
					filtroNegat.adicionarParametro(new ParametroSimplesDiferenteDe(
							FiltroNegativador.ID, idNegativador));				

					Collection collNegativador = fachada.pesquisar(filtroNegat,	Negativador.class.getName());
					
//					if(collNegativador != null && !collNegativador.isEmpty()){
//						Iterator i = collNegativador.iterator();
//						while(i.hasNext()){
//							Negativador negativador = (Negativador) i.next();
//							if (negativador.getCliente().getId().compareTo(new Integer(idDigitadoEnterCliente)) == 0 && negativador.getId() != new Integer(idNegativador)){
//								throw new ActionServletException("atencao.negativador_associado_cliente");
//							}
//						}
//					}
					
					if(collNegativador != null && !collNegativador.isEmpty()){
						throw new ActionServletException("atencao.negativador_associado_cliente");
					}
					
	                //---------------------------------------------------------------------
						
	
					form.setCodigoCliente(((Cliente) ((List) clienteEncontrado)
									.get(0)).getId().toString());
					form.setNomeCliente(((Cliente) ((List) clienteEncontrado)
									.get(0)).getNome());
					form.setOkCliente("1");
				} else {
					httpServletRequest.setAttribute("corCliente","exception");
	               	form.setNomeCliente(ConstantesSistema.CODIGO_CLIENTE_INEXISTENTE);
	               	form.setCodigoCliente("");
	               	form.setOkCliente("2");
				}
	        }
	        
	        if (idDigitadoEnterImovel != null && !idDigitadoEnterImovel.trim().equals("")) {
				// Pesquisa o imovel na base
	        	
	        	FiltroImovel filtroImovel = new FiltroImovel();
	        	filtroImovel.adicionarParametro(new ParametroSimples(
	        			FiltroImovel.ID, idDigitadoEnterImovel));
	        	
	        	Collection collImovel = fachada.pesquisar(filtroImovel,
	        			Imovel.class.getName());
	        	
	        	Imovel imovel = (Imovel) Util.retonarObjetoDeColecao(collImovel);
	        	
				if (imovel != null) {
					//-------------------------------------------------------------------
								
					FiltroNegativador filtroNegat = new FiltroNegativador();
					filtroNegat.adicionarParametro(new ParametroSimples(
							FiltroNegativador.NEGATIVADOR_IMOVEL, imovel.getId()));
					filtroNegat.adicionarCaminhoParaCarregamentoEntidade("imovel");
					filtroNegat.adicionarParametro(new ParametroSimplesDiferenteDe(
							FiltroNegativador.ID, idNegativador));
					Collection collNegativador = fachada.pesquisar(filtroNegat,
							Negativador.class.getName());
					
					if(collNegativador != null && !collNegativador.isEmpty()){
						throw new ActionServletException("atencao.negativador_associado_imovel");
					}
					
//					if(collNegativador != null && !collNegativador.isEmpty()){
//						Iterator i = collNegativador.iterator();
//						while(i.hasNext()){
//							Negativador negativador = (Negativador) i.next();
//							if (negativador.getImovel().getId() == new Integer(idDigitadoEnterImovel) && negativador.getId() != new Integer(idNegativador)){
//								throw new ActionServletException("atencao.negativador_associado_imovel");
//							}
//						}
//					}
					
					
	           //------------------------------------------------------------------------------------------------
	        	
	        	
				String imovelEncontrado = fachada.pesquisarInscricaoImovel(new Integer(idDigitadoEnterImovel));
	        	
				if (imovelEncontrado != null && !imovelEncontrado.equalsIgnoreCase("")) {					
					// O imovel foi encontrado
					form.setCodigoImovel(idDigitadoEnterImovel);
					form.setInscricaoImovel(imovelEncontrado);
					form.setOkImovel("1");
					
				}	
					
				} else {
					httpServletRequest.setAttribute("corImovel","exception");
	           		form.setInscricaoImovel(ConstantesSistema.CODIGO_IMOVEL_INEXISTENTE);
	           		form.setCodigoImovel("");
	           		form.setOkImovel("2");
				}
	        }
	        
	        if (idDigitadoEnterAgente != null && !idDigitadoEnterAgente.trim().equals("")){
	        	FiltroNegativador fNegativador = new FiltroNegativador();
	        	fNegativador.adicionarParametro(new ParametroSimples(FiltroNegativador.CODIGO_AGENTE, idDigitadoEnterAgente));
	        	
	        	Collection codigoAgenteEncontrado = Fachada.getInstancia().pesquisar(fNegativador, Negativador.class.getName());
	        	        	
	        	if (codigoAgenteEncontrado != null && !codigoAgenteEncontrado.isEmpty()) {
	        		
	        		if (((Negativador) ((List) codigoAgenteEncontrado).get(0)).getIndicadorUso() 
	        				== ConstantesSistema.INDICADOR_USO_DESATIVO.shortValue()) {
						throw new ActionServletException("atencao.negativador.inativo",
								null, ""
										+ ((Negativador) ((List) codigoAgenteEncontrado)
												.get(0)).getId());
					}
	        		
	           		form.setOkAgente("1");
	        	} else {
	        		form.setCodigoAgente("");
	        		httpServletRequest.setAttribute("corAgente","exception");
	           		form.setMensagemAgente(ConstantesSistema.CODIGO_AGENTE_NAO_CADASTRADO);
	        		form.setOkAgente("2");
	        	}
	        }
		}
		
//        sessao.removeAttribute("idRegistroAtualizacao");
        
        return retorno;
        
    }

}
