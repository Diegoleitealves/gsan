package gcom.gui.util.tabelaauxiliar.faixa;

import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.tabelaauxiliar.faixa.TabelaAuxiliarFaixa;

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
public class AtualizarTabelaAuxiliarFaixaAction extends GcomAction {
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

        //Pega o action form
        TabelaAuxiliarFaixaActionForm tabelaAuxiliarFaixaActionForm = (TabelaAuxiliarFaixaActionForm) actionForm;

        //Seta o retorno
        ActionForward retorno = actionMapping.findForward("telaSucesso");

        //Obt�m a inst�ncia da fachada
        Fachada fachada = Fachada.getInstancia();

        //Obt�m a sess�o
        HttpSession sessao = httpServletRequest.getSession(false);

        //Recupera o ponto de coleta da sess�o
        TabelaAuxiliarFaixa tabelaAuxiliarFaixa = (TabelaAuxiliarFaixa) sessao
                .getAttribute("tabelaAuxiliarFaixa");

        //Atualiza a tabela auxiliar com os dados inseridos pelo usu�rio
        //A data de �ltima altera��o n�o � alterada, pois ser� usada na
        //verifica��o de atualiza��o
        tabelaAuxiliarFaixa.setFaixaInicial(tabelaAuxiliarFaixaActionForm
                .getFaixaInicial());
        tabelaAuxiliarFaixa.setFaixaFinal(tabelaAuxiliarFaixaActionForm
                .getFaixaFinal());

        //Atualiza os dados
        fachada.atualizarTabelaAuxiliar(tabelaAuxiliarFaixa);

        //Monta a p�gina de sucesso
        if (retorno.getName().equalsIgnoreCase("telaSucesso")) {

            montarPaginaSucesso(
                    httpServletRequest,
                    ((String) sessao.getAttribute("titulo"))
                            + " atualizada com sucesso",
                    "Realizar outra manuten��o de "
                            + ((String) sessao.getAttribute("titulo")),
                    ((String) sessao
                            .getAttribute("funcionalidadeTabelaAuxiliarFaixaManter")));

        }

        //Remove os objetos da sess�o
        sessao.removeAttribute("funcionalidadeTabelaAuxiliarFaixaManter");
        sessao.removeAttribute("titulo");
        sessao.removeAttribute("tabelaAuxiliarFaixa");
        sessao.removeAttribute("tamMaxCampoFaixaInicial");
        sessao.removeAttribute("tamMaxCampoFaixaFinal");

        //devolve o mapeamento de retorno
        return retorno;
    }

}
