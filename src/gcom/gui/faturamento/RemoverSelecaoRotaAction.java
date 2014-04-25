package gcom.gui.faturamento;

import gcom.gui.GcomAction;
import gcom.micromedicao.Rota;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

public class RemoverSelecaoRotaAction extends GcomAction {

    public ActionForward execute(ActionMapping actionMapping,
            ActionForm actionForm, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
    	
    	String forward = httpServletRequest.getParameter("forward");

        //Seta o retorno
        ActionForward retorno = actionMapping
                .findForward(forward);

        //Obt�m a sess�o
        HttpSession sessao = httpServletRequest.getSession(false);

        //Formul�rio de pesquisa
        DynaValidatorForm pesquisarActionForm = (DynaValidatorForm) actionForm;

        if (forward.equalsIgnoreCase("exibirSelecionarRota") &&
        	sessao.getAttribute("colecaoRotasSelecionadasUsuario") != null) {

            // Cole��o retornada pela pesquisa
            Collection colecaoRotasSelecionadasUsuario = (Collection) sessao
                    .getAttribute("colecaoRotasSelecionadasUsuario");

            // Cole��o que ir� ser gerada a partir da sele��o efetuada pelo
            // usu�rio
            if (pesquisarActionForm.get("idRotaSelecao") != null) {

                Iterator colecaoRotasSelecionadasUsuarioIterator;

                Rota rotaInserir;

                int indexArray = 0;
                Integer rotaID;

                // Rotas selecionadas pelo usu�rio para serem removidas da
                // sess�o
                String[] rotasSelecionadas = (String[]) pesquisarActionForm
                        .get("idRotaSelecao");

                while (rotasSelecionadas.length > indexArray) {
                    rotaID = new Integer(rotasSelecionadas[indexArray]);

                    colecaoRotasSelecionadasUsuarioIterator = colecaoRotasSelecionadasUsuario
                            .iterator();

                    while (colecaoRotasSelecionadasUsuarioIterator.hasNext()) {

                        rotaInserir = (Rota) colecaoRotasSelecionadasUsuarioIterator
                                .next();

                        if (rotaInserir.getId().equals(rotaID)) {
                            colecaoRotasSelecionadasUsuario.remove(rotaInserir);
                            break;
                        }
                    }

                    indexArray++;
                }
            }
        }
        else if (forward.equalsIgnoreCase("exibirFiltrarRotaComandoAtividade") &&
            	sessao.getAttribute("colecaoRotasSelecionadas") != null){
        	
        	//Cole��o retornada pela pesquisa
            Collection colecaoRotasSelecionadasUsuario = (Collection) sessao
                    .getAttribute("colecaoRotasSelecionadas");

            // Cole��o que ir� ser gerada a partir da sele��o efetuada pelo
            // usu�rio
            if (pesquisarActionForm.get("idRotaSelecao") != null) {

                Iterator colecaoRotasSelecionadasUsuarioIterator;

                Rota rotaInserir;

                int indexArray = 0;
                Integer rotaID;

                // Rotas selecionadas pelo usu�rio para serem removidas da
                // sess�o
                String[] rotasSelecionadas = (String[]) pesquisarActionForm
                        .get("idRotaSelecao");

                while (rotasSelecionadas.length > indexArray) {
                    rotaID = new Integer(rotasSelecionadas[indexArray]);

                    colecaoRotasSelecionadasUsuarioIterator = colecaoRotasSelecionadasUsuario
                            .iterator();

                    while (colecaoRotasSelecionadasUsuarioIterator.hasNext()) {

                        rotaInserir = (Rota) colecaoRotasSelecionadasUsuarioIterator
                                .next();

                        if (rotaInserir.getId().equals(rotaID)) {
                            colecaoRotasSelecionadasUsuario.remove(rotaInserir);
                            break;
                        }
                    }

                    indexArray++;
                }
            }
        }

        //devolve o mapeamento de retorno
        return retorno;
    }
}
