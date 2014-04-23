package gcom.gui.cobranca.spcserasa;

import gcom.atendimentopublico.ligacaoagua.FiltroLigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.FiltroLigacaoEsgotoSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.cliente.ClienteTipo;
import gcom.cadastro.cliente.FiltroCliente;
import gcom.cadastro.cliente.FiltroClienteRelacaoTipo;
import gcom.cadastro.cliente.FiltroClienteTipo;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.FiltroImovelPerfil;
import gcom.cadastro.imovel.FiltroSubCategoria;
import gcom.cadastro.imovel.ImovelPerfil;
import gcom.cadastro.imovel.Subcategoria;
import gcom.cobranca.CobrancaSituacao;
import gcom.cobranca.CobrancaSituacaoTipo;
import gcom.cobranca.FiltroCobrancaSituacao;
import gcom.cobranca.FiltroCobrancaSituacaoTipo;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Esta classe tem por finalidade exibir para o usu�rio a tela que receber� os par�metros para realiza��o
 * da inser��o de um Comando de Negativa��o (Aba n� 03 - Dados do Im�vel) 
 *
 * @author Ana Maria
 * @date 06/11/2007
 */
public class ExibirInserirComandoNegativacaoDadosImovelAction extends GcomAction {
	
	
	public ActionForward execute(ActionMapping actionMapping,
            ActionForm actionForm, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {

        ActionForward retorno = actionMapping.findForward("inserirComandoNegativacaoDadosImovel");
        
    	Fachada fachada = Fachada.getInstancia();
    	
    	HttpSession sessao = httpServletRequest.getSession(false);
    	
		InserirComandoNegativacaoActionForm inserirComandoNegativacaoActionForm = (InserirComandoNegativacaoActionForm) actionForm;
		
        //Pesquisa Cliente
        String idCliente = inserirComandoNegativacaoActionForm.getIdCliente();
        if(idCliente != null && !idCliente.equals("")){
        	
        	FiltroCliente filtroCliente = new FiltroCliente();  
            
        	filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, idCliente));
            
            Collection colecaoCliente = fachada.pesquisar(
            		filtroCliente,Cliente.class.getName());
            
            if (colecaoCliente != null && !colecaoCliente.isEmpty()) {
            	httpServletRequest.setAttribute("funcionalidadeEncontrada", "valor");
            	
            	inserirComandoNegativacaoActionForm.setIdCliente(""
						+ ((Cliente) ((List) colecaoCliente).get(0)).getId());
            	inserirComandoNegativacaoActionForm.setDescricaoCliente(""
						+ ((Cliente) ((List) colecaoCliente).get(0)).getNome());
			} else {
				httpServletRequest.setAttribute("funcionalidadeEncontrada","exception");
				inserirComandoNegativacaoActionForm.setIdCliente(null);
				inserirComandoNegativacaoActionForm.setDescricaoCliente("Cliente Inexistente");
			}
        }
        
        //Pesquisar Tipo Rela��o
        if(sessao.getAttribute("colecaoClienteRelacaoTipo") == null){
        	FiltroClienteRelacaoTipo filtroClienteRelacaoTipo = new FiltroClienteRelacaoTipo();
    		
    		Collection colecaoClienteRelacaoTipo = fachada.pesquisar(filtroClienteRelacaoTipo,ClienteRelacaoTipo.class.getName());
    		
    		sessao.setAttribute("colecaoClienteRelacaoTipo", colecaoClienteRelacaoTipo);
        }
        
        if(inserirComandoNegativacaoActionForm.getImovSitEspecialCobranca() == null){
        	//Im�vel com Sit. Especial de Cobran�a - exibir com op��o "Sim" selecionada    
        	inserirComandoNegativacaoActionForm.setImovSitEspecialCobranca(ConstantesSistema.CONFIRMADA);   
        }
        
        if(inserirComandoNegativacaoActionForm.getImovSitCobranca() == null){
        	//CRC3323 - adicionado por Vivianne Sousa - analista:Fatima Sampaio - 05/05/2010
        	//Im�vel com Sit. de Cobran�a - exibir com op��o "Sim" selecionada    		
        	inserirComandoNegativacaoActionForm.setImovSitCobranca(ConstantesSistema.CONFIRMADA);   
        }
        
        if(inserirComandoNegativacaoActionForm.getIndicadorBaixaRenda() == null){
        	//CRC4496 - adicionado por Vivianne Sousa - analista:Adriana - 29/06/2010
        	//Im�vel com Baixa Renda - exibir com op��o "SIM" selecionada    		
        	inserirComandoNegativacaoActionForm.setIndicadorBaixaRenda(ConstantesSistema.CONFIRMADA);   
        }
        
        if(inserirComandoNegativacaoActionForm.getIndicadorImovelCategoriaPublico() == null){
        	//RM3388 - adicionado por Ivan Sergioo - analista:Adriana - 26/01/2011
        	//Im�vel com - exibir com op��o "SIM" selecionada    		
        	inserirComandoNegativacaoActionForm.setIndicadorImovelCategoriaPublico(ConstantesSistema.NAO_CONFIRMADA);
        }

        //Pesquisar Situacao Especial de Cobranca
        if(sessao.getAttribute("colecaoCobrancaoSituacaoTipo") == null){
        	FiltroCobrancaSituacaoTipo filtroCobrancaSituacaoTipo = new FiltroCobrancaSituacaoTipo();
        	filtroCobrancaSituacaoTipo.adicionarParametro(
        		new ParametroSimples(FiltroCobrancaSituacaoTipo.INDICADORUSO, ConstantesSistema.SIM));
    		
    		Collection cobrancaSituacaoTipo = 
    			fachada.pesquisar(filtroCobrancaSituacaoTipo,CobrancaSituacaoTipo.class.getName());
    		
    		sessao.setAttribute("colecaoCobrancaoSituacaoTipo", cobrancaSituacaoTipo);
        } 

        //Pesquisar Situacao Cobranca
        if(sessao.getAttribute("colecaoCobrancaoSituacao") == null){
        	FiltroCobrancaSituacao filtroCobrancaSituacao = new FiltroCobrancaSituacao();
        	filtroCobrancaSituacao.adicionarParametro(
            		new ParametroSimples(FiltroCobrancaSituacao.INDICADOR_USO, ConstantesSistema.SIM));

    		Collection cobrancaSituacao = 
    			fachada.pesquisar(filtroCobrancaSituacao,CobrancaSituacao.class.getName());
    		
    		sessao.setAttribute("colecaoCobrancaoSituacao", cobrancaSituacao);
        } 

        //Pesquisar Situa��o da Liga��o de �gua 
        if(sessao.getAttribute("colecaoLigAguaSituacao") == null){
        	FiltroLigacaoAguaSituacao filtroLigacaoAguaSituacao = new FiltroLigacaoAguaSituacao();
    		
    		Collection colecaoLigAguaSituacao = fachada.pesquisar(filtroLigacaoAguaSituacao,LigacaoAguaSituacao.class.getName());
    		
    		sessao.setAttribute("colecaoLigAguaSituacao", colecaoLigAguaSituacao);
        } 
        
        //Pesquisar Situa��o da Liga��o de Esgoto
        if(sessao.getAttribute("colecaoLigEsgotoSituacao") == null){
        	FiltroLigacaoEsgotoSituacao filtroLigacaoEsgotoSituacao = new FiltroLigacaoEsgotoSituacao();
    		
    		Collection colecaoLigEsgotoSituacao = fachada.pesquisar(filtroLigacaoEsgotoSituacao,LigacaoEsgotoSituacao.class.getName());
    		
    		sessao.setAttribute("colecaoLigEsgotoSituacao", colecaoLigEsgotoSituacao);
        } 
        
        //Pesquisar SubCategoria
        if(sessao.getAttribute("colecaoSubcategoria") == null || 
        		(httpServletRequest.getParameter("atualizaListSubcategoria") != null && 
        				httpServletRequest.getParameter("atualizaListSubcategoria").equals("s")) ){
        	FiltroSubCategoria filtroSubCategoria = new FiltroSubCategoria();
    		
        	if (inserirComandoNegativacaoActionForm.getIndicadorImovelCategoriaPublico().equals("1")) {
        		filtroSubCategoria.adicionarParametro(new ParametroSimples(
        				FiltroSubCategoria.CATEGORIA_ID, Categoria.PUBLICO));
        	}
        	
    		Collection colecaoSubcategoria = fachada.pesquisar(filtroSubCategoria,Subcategoria.class.getName());
    		
    		sessao.setAttribute("colecaoSubcategoria", colecaoSubcategoria);
        }
        
        //Pesquisar Perfil do Im�vel
        if(sessao.getAttribute("colecaoPerfilImovel") == null){
        	FiltroImovelPerfil filtroImovelPerfil = new FiltroImovelPerfil();
    		
    		Collection colecaoPerfilImovel = fachada.pesquisar(filtroImovelPerfil,ImovelPerfil.class.getName());
    		
    		sessao.setAttribute("colecaoPerfilImovel", colecaoPerfilImovel);
        }   
        
        //Pesquisar Tipo do Cliente
        if(sessao.getAttribute("colecaoTipoCliente") == null){
        	FiltroClienteTipo filtroClienteTipo = new FiltroClienteTipo();
    		
    		Collection colecaoTipoCliente = fachada.pesquisar(filtroClienteTipo,ClienteTipo.class.getName());
    		
    		sessao.setAttribute("colecaoTipoCliente", colecaoTipoCliente);
        }        
    		
		//Caso informe o id da simula��o, os campos da Aba 5 - Dados da Localiza��o devem ser desabilitados
        if(inserirComandoNegativacaoActionForm.getIdComandoSimulado() != null && !inserirComandoNegativacaoActionForm.getIdComandoSimulado().equals("")){
        	inserirComandoNegativacaoActionForm.setIdCliente(null);
        	inserirComandoNegativacaoActionForm.setDescricaoCliente(null);
        	inserirComandoNegativacaoActionForm.setTipoRelacao(null);
        	inserirComandoNegativacaoActionForm.setImovSitEspecialCobranca(null);
        	inserirComandoNegativacaoActionForm.setImovSitCobranca(null);
        	inserirComandoNegativacaoActionForm.setLigacaoAguaSituacao(null);
        	inserirComandoNegativacaoActionForm.setLigacaoEsgotoSituacao(null);
        	inserirComandoNegativacaoActionForm.setSubCategoria(null);
        	inserirComandoNegativacaoActionForm.setPerfilImovel(null);
        	inserirComandoNegativacaoActionForm.setTipoCliente(null);       	
        	httpServletRequest.setAttribute("desabilitar", "ok");
        }
    	return retorno;
    }    	   

}
