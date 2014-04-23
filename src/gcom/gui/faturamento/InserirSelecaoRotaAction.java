package gcom.gui.faturamento;

import gcom.gui.GcomAction;
import gcom.micromedicao.Rota;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

public class InserirSelecaoRotaAction extends GcomAction {

    public ActionForward execute(ActionMapping actionMapping,
            ActionForm actionForm, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {

        //Seta o retorno
        ActionForward retorno = actionMapping
                .findForward("exibirSelecionarRota");

        //Obt�m a sess�o
        HttpSession sessao = httpServletRequest.getSession(false);

        //Formul�rio de pesquisa
        DynaValidatorForm pesquisarActionForm = (DynaValidatorForm) actionForm;

        if (sessao.getAttribute("colecaoRotasSelecionadas") != null) {

            // Cole��o retornada pela pesquisa
            Collection colecaoRotasSelecionadas = (Collection) sessao
                    .getAttribute("colecaoRotasSelecionadas");

            // Cole��o que ir� ser gerada a partir da sele��o efetuada pelo
            // usu�rio
            List colecaoRotasSelecionadasUsuario = new Vector();

            if (pesquisarActionForm.get("idRotaSelecao") != null) {

                Iterator colecaoRotasSelecionadasIterator;

                Rota rotaInserir;

                int indexArray = 0;
                Integer rotaID;

                // Rotas selecionadas pelo usu�rio
                String[] rotasSelecionadas = (String[]) pesquisarActionForm
                        .get("idRotaSelecao");

                while (rotasSelecionadas.length > indexArray) {
                    rotaID = new Integer(rotasSelecionadas[indexArray]);

                    colecaoRotasSelecionadasIterator = colecaoRotasSelecionadas
                            .iterator();

                    while (colecaoRotasSelecionadasIterator.hasNext()) {

                        rotaInserir = (Rota) colecaoRotasSelecionadasIterator
                                .next();

                        if (rotaInserir.getId().equals(rotaID)) {
                            colecaoRotasSelecionadasUsuario.add(rotaInserir);
                            break;
                        }
                    }

                    indexArray++;
                }

                // A cole��o pode ser acumulativa ou est� sendo gerada pela
                // primeira vez
                if (sessao.getAttribute("colecaoRotasSelecionadasUsuario") != null) {
                    Collection colecaoRotasSelecionadasUsuarioSessao = (Collection) sessao
                            .getAttribute("colecaoRotasSelecionadasUsuario");

                    colecaoRotasSelecionadasUsuarioSessao
                            .addAll(colecaoRotasSelecionadasUsuario);
                } else {
                    sessao.setAttribute("colecaoRotasSelecionadasUsuario",
                            colecaoRotasSelecionadasUsuario);
                }

                // Remove a cole��o gerada pela pesquisa efetuada pelo usu�rio
                sessao.removeAttribute("colecaoRotasSelecionadas");

                // Flag para indicar o retorno para o caso de uso que chamou a
                // funcionalidade
                httpServletRequest.setAttribute("retornarUseCase", "OK");
            }

        }

        //devolve o mapeamento de retorno
        return retorno;
    }

}
