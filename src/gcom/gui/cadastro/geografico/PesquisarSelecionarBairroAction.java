package gcom.gui.cadastro.geografico;

import gcom.cadastro.geografico.Bairro;
import gcom.cadastro.geografico.FiltroBairro;
import gcom.cadastro.geografico.FiltroMunicipio;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.filtro.ComparacaoTexto;
import gcom.util.filtro.ParametroSimples;
import gcom.cadastro.geografico.Municipio;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Esta classe tem por finalidade exibir para o usu�rio os bairros que ser�o retornados
 * a partir dos par�metros que foram passados pelo filtro
 *
 * @author Raphael Rossiter
 * @date 02/05/2006
 */
public class PesquisarSelecionarBairroAction extends GcomAction {
	
	
	public ActionForward execute(ActionMapping actionMapping,
            ActionForm actionForm, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {

        ActionForward retorno = actionMapping.findForward("exibirSelecionarBairro");

        Fachada fachada = Fachada.getInstancia();

        HttpSession sessao = httpServletRequest.getSession(false);

        SelecionarBairroActionForm selecionarBairroActionForm = (SelecionarBairroActionForm) actionForm;

        FiltroBairro filtroBairro = new FiltroBairro();
        filtroBairro.setConsultaSemLimites(true);
        
        //Objetos que ser�o retornados pelo hibernate.
        filtroBairro.adicionarCaminhoParaCarregamentoEntidade("municipio");
        
        filtroBairro.setCampoOrderBy(FiltroBairro.NOME);
        
        Collection colecaoBairro = null;

        //Validando os campos do formulario
        boolean peloMenosUmParametroInformado = false;

        
        //Munic�pio
        if (selecionarBairroActionForm.getIdMunicipio() != null && 
        	!selecionarBairroActionForm.getIdMunicipio().equals("")) {

            String municipioJSP = selecionarBairroActionForm.getIdMunicipio();

            peloMenosUmParametroInformado = true;

            FiltroMunicipio filtroMunicipio = new FiltroMunicipio();
            
            filtroMunicipio.adicionarParametro(new ParametroSimples(
            FiltroMunicipio.ID, municipioJSP));
            
            Collection colecaoMunicipio = fachada.pesquisar(filtroMunicipio, Municipio.class.getName());
            
            if (colecaoMunicipio == null || colecaoMunicipio.isEmpty()){
            	
            	throw new ActionServletException("atencao.municipio.inexistente");
            }
            
            filtroBairro.adicionarParametro(new ParametroSimples(
            FiltroBairro.MUNICIPIO_ID, municipioJSP));
        }

        
        //Bairro
        if (selecionarBairroActionForm.getNomeBairro() != null &&
        	!selecionarBairroActionForm.getNomeBairro().equals("")) {

            String nomeBairroJSP = selecionarBairroActionForm.getNomeBairro();

            peloMenosUmParametroInformado = true;

            filtroBairro.adicionarParametro(new ComparacaoTexto(
            FiltroBairro.NOME, nomeBairroJSP));
            
            filtroBairro.setCampoOrderBy(FiltroBairro.NOME);
        }
        
        
        //Erro caso o usu�rio mandou filtrar sem nenhum par�metro
        if (!peloMenosUmParametroInformado) {
            throw new ActionServletException(
                    "atencao.filtro.nenhum_parametro_informado");
        }

        //Retorna o(s) bairro(s)
        colecaoBairro = fachada.pesquisar(filtroBairro, Bairro.class.getName());
        

        if (colecaoBairro == null || colecaoBairro.isEmpty()) {
            //Nenhum bairro cadastrado de acordo com os par�metros passados
            throw new ActionServletException(
                    "atencao.pesquisa.nenhumresultado", null, "bairro");
        } else {
        	sessao.setAttribute("colecaoBairrosSelecionados", colecaoBairro);
        }
        
        httpServletRequest.setAttribute("nomeCampo", "botaoSelecionar");

        return retorno;
    }

}
