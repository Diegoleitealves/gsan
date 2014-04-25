package gcom.gui.cadastro.tarifasocial;

import gcom.cadastro.tarifasocial.FiltroTarifaSocialCartaoTipo;
import gcom.cadastro.tarifasocial.TarifaSocialCartaoTipo;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * < <Descri��o da Classe>>
 * 
 * @author Administrador
 */
public class ExibirManterTarifaSocialCartaoTipoAction extends GcomAction {
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

        //Define a��o de retorno
        ActionForward retorno = actionMapping
                .findForward("manterTarifaSocialCartaoTipo");

        //Obt�m a fachada
        Fachada fachada = Fachada.getInstancia();

        //Form din�mico para obter
        //DynaValidatorForm pesquisarActionForm = (DynaValidatorForm) actionForm;

        //Inicializa a cole��o
        Collection colecaoTarifaSocialCartaoTipo = null;

        //Mudar isso quando implementar a parte de seguran�a
        HttpSession sessao = httpServletRequest.getSession(false);

        //Cria��o do filtro de tarifa social cart�o tipo
        FiltroTarifaSocialCartaoTipo filtroTarifaSocialCartaoTipo = null;

        //Verifica se o filtro foi informado pela p�gina de filtragem de
        // logradouro
        if (httpServletRequest.getAttribute("filtroTarifaSocialCartaoTipo") != null) {
            filtroTarifaSocialCartaoTipo = (FiltroTarifaSocialCartaoTipo) httpServletRequest
                    .getAttribute("filtroTarifaSocialCartaoTipo");
        } else {
            //Caso o exibirManterTarifaSocialCartaoTipo n�o tenha passado por
            // algum esquema de filtro,
            //a quantidade de registros � verificada para avaliar a necessidade
            // de filtragem
            filtroTarifaSocialCartaoTipo = new FiltroTarifaSocialCartaoTipo();

            if (fachada.registroMaximo(TarifaSocialCartaoTipo.class) > ConstantesSistema.NUMERO_MAXIMO_REGISTROS_MANUTENCAO) {
                //Se o limite de registros foi atingido, a p�gina de filtragem
                // � chamada
                retorno = actionMapping
                        .findForward("filtrarTarifaSocialCartaoTipo");
                //limpa os parametros do form pesquisar
                sessao.removeAttribute("PesquisarActionForm");
            }
        }

        //A pesquisa de tarifa social cart�o tipo s� ser� feita se o forward
        // estiver direcionado
        //para a p�gina de manterTarifaSocialCartaoTipo
        if (retorno.getName().equalsIgnoreCase("manterTarifaSocialCartaoTipo")) {
                        
        	Map resultado = controlarPaginacao(httpServletRequest, retorno,
					filtroTarifaSocialCartaoTipo, TarifaSocialCartaoTipo.class.getName());
			colecaoTarifaSocialCartaoTipo = (Collection) resultado.get("colecaoRetorno");
			retorno = (ActionForward) resultado.get("destinoActionForward");

            if (colecaoTarifaSocialCartaoTipo == null
                    || colecaoTarifaSocialCartaoTipo.isEmpty()) {
                //Nenhum cliente cadastrado
                throw new ActionServletException("atencao.naocadastrado", null,
                        "tipo de cart�o da tarifa social");
            }

            /*
             * if (logradouros.size() >
             * ConstantesSistema.NUMERO_MAXIMO_REGISTROS_MANUTENCAO) { throw new
             * ActionServletException("atencao.pesquisa.muitosregistros"); }
             */
            sessao.setAttribute("colecaoTarifaSocialCartaoTipo",
                    colecaoTarifaSocialCartaoTipo);

        }
        return retorno;
    }

}
