package gcom.gui.cadastro.geografico;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import gcom.cadastro.geografico.FiltroMunicipio;
import gcom.cadastro.geografico.Municipio;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.filtro.ParametroSimples;
import gcom.util.Util;

/**
 * Esta classe tem por finalidade exibir para o usu�rio a tela que receber� os par�metros para realiza��o
 * da filtragem dos bairros para sele��o 
 *
 * @author Raphael Rossiter
 * @date 29/04/2006
 */
public class ExibirSelecionarBairroAction extends GcomAction {
	
	public ActionForward execute(ActionMapping actionMapping,
            ActionForm actionForm, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {

        ActionForward retorno = actionMapping.findForward("exibirSelecionarBairro");
        
        Fachada fachada = Fachada.getInstancia();
        
        HttpSession sessao = httpServletRequest.getSession(false);

        SelecionarBairroActionForm selecionarBairroActionForm = (SelecionarBairroActionForm) actionForm;
        
        String tipoRetorno = (String) httpServletRequest.getParameter("tipoPesquisaEndereco");
        String tipoOperacao = (String) httpServletRequest.getParameter("operacao");
        
        if (tipoRetorno != null && !tipoRetorno.trim().equalsIgnoreCase("")) {
			sessao.setAttribute("tipoPesquisaRetorno", tipoRetorno);
			sessao.setAttribute("operacao", tipoOperacao);
		}
        
        
        //Limpar formul�rio, caso necess�rio
        if (httpServletRequest.getParameter("limparForm") != null){

        	selecionarBairroActionForm.setIdMunicipio("");
        	selecionarBairroActionForm.setNomeMunicipio("");
        	selecionarBairroActionForm.setNomeBairro("");
        	
        	//Retira da sess�o a cole��o de bairros que foi selecionada anteriormente
        	sessao.removeAttribute("colecaoBairrosSelecionados");
        }
        
        /*
         * Caso o par�metro "Munic�pio" seja previamente definido pelo caso de uso que chama est� funcionalidade,
         * o mesmo dever� ser mantido para realiza��o da filtragem dos bairros
         */
        String idMunicipio = httpServletRequest.getParameter("idMunicipio");
        
        if (idMunicipio != null && !idMunicipio.equals("")){
        	
        	FiltroMunicipio filtroMunicipio = new FiltroMunicipio();
        	
        	filtroMunicipio.adicionarParametro(new ParametroSimples(FiltroMunicipio.ID, idMunicipio));
        	
        	Collection colecaoMunicipio = fachada.pesquisar(filtroMunicipio, Municipio.class.getName());
        	
        	Municipio municipio = (Municipio) Util.retonarObjetoDeColecao(colecaoMunicipio);
        	
        	selecionarBairroActionForm.setIdMunicipio(municipio.getId().toString());
        	selecionarBairroActionForm.setNomeMunicipio(municipio.getNome());
        	
        }
        
        
        return retorno;
	}

}
