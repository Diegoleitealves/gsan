package gcom.gui.cadastro.endereco;

import gcom.cadastro.geografico.FiltroMunicipio;
import gcom.cadastro.geografico.Municipio;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Esta classe tem por finalidade exibir para o usu�rio a tela que receber� os par�metros para realiza��o
 * da filtragem dos CEPs para sele��o 
 *
 * @author Raphael Rossiter
 * @date 03/05/2006
 */
public class ExibirSelecionarCepAction extends GcomAction {
	
	public ActionForward execute(ActionMapping actionMapping,
            ActionForm actionForm, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {

        ActionForward retorno = actionMapping.findForward("exibirSelecionarCep");
        
        Fachada fachada = Fachada.getInstancia();
        
        HttpSession sessao = httpServletRequest.getSession(false);

        SelecionarCepActionForm selecionarCepActionForm = (SelecionarCepActionForm) actionForm;
        
        
        String tipoRetorno = (String) httpServletRequest.getParameter("tipoPesquisaEndereco");
        String tipoOperacao = (String) httpServletRequest.getParameter("operacao");
        
        if (tipoRetorno != null && !tipoRetorno.trim().equalsIgnoreCase("")) {
			sessao.setAttribute("tipoPesquisaRetorno", tipoRetorno);
			sessao.setAttribute("operacao", tipoOperacao);
		}
        
        
        //Limpar formul�rio, caso necess�rio
        if (httpServletRequest.getParameter("limparForm") != null){

        	selecionarCepActionForm.setIdMunicipio("");
        	selecionarCepActionForm.setNomeMunicipio("");
        	selecionarCepActionForm.setNomeLogradouro("");
        	
        	//Retira da sess�o a cole��o de CEPs que foi selecionada anteriormente
        	sessao.removeAttribute("municipioInformado");
        	sessao.removeAttribute("colecaoCepSelecionados");
        }
        
        /*
         * Caso o par�metro "Munic�pio" seja previamente definido pelo caso de uso que chama est� funcionalidade,
         * o mesmo dever� ser mantido para realiza��o da filtragem dos bairros
         */
        String idMunicipio = httpServletRequest.getParameter("idMunicipio");
        Collection colecaoMunicipio = null;
        Municipio municipio = null;
        
        if (idMunicipio != null && !idMunicipio.equals("")){
        	
        	FiltroMunicipio filtroMunicipio = new FiltroMunicipio();
        	
        	filtroMunicipio.adicionarParametro(new ParametroSimples(FiltroMunicipio.ID, idMunicipio));
        	
        	colecaoMunicipio = fachada.pesquisar(filtroMunicipio, Municipio.class.getName());
        	
        	if (colecaoMunicipio != null && !colecaoMunicipio.isEmpty()){
        		
        		sessao.setAttribute("municipioInformado", idMunicipio);
        		
        		municipio = (Municipio) Util.retonarObjetoDeColecao(colecaoMunicipio);
        		
        		selecionarCepActionForm.setIdMunicipio(municipio.getId().toString());
            	selecionarCepActionForm.setNomeMunicipio(municipio.getNome());
        	}
        }
        
        
        return retorno;
	}

}
