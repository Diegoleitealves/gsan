package gcom.gui.seguranca.acesso.usuario;

import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.FiltroAbrangenciaUsuario;
import gcom.seguranca.acesso.usuario.UsuarioAbrangencia;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action para pre-exibi��o dos dados da situa��o especial de faturamento que
 * ser�o retirados
 *
 * Descri��o da classe 
 *
 * @author Thiago Ten�rio
 * @date 06/05/2006
 */
public class ExibirInserirAbrangenciaUsuarioAction extends GcomAction {

    public ActionForward execute(ActionMapping actionMapping,
            ActionForm actionForm, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {


        //Seta o mapeamento de retorno
        ActionForward retorno = actionMapping
                .findForward("inserirAbrangenciaUsuario");
        
        Fachada fachada = Fachada.getInstancia();
        
		FiltroAbrangenciaUsuario filtroAbrangenciaUsuario = new FiltroAbrangenciaUsuario();
		Collection<UsuarioAbrangencia> colecaoUsuarioAbrangencia = fachada.pesquisar(filtroAbrangenciaUsuario,UsuarioAbrangencia.class.getName());

        httpServletRequest.setAttribute("colecaoUsuarioAbrangencia",colecaoUsuarioAbrangencia);
        
        
        
        
        return retorno;
        
    }
    
}
