package gcom.gui.util.tabelaauxiliar.faixa;

import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.ErroRepositorioException;
import gcom.util.tabelaauxiliar.faixa.TabelaAuxiliarFaixa;

import java.rmi.RemoteException;
import java.util.Date;

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
public class InserirTabelaAuxiliarFaixaAction extends GcomAction {
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
     * @exception RemoteException
     *                Descri��o da exce��o
     * @exception ErroRepositorioException
     *                Descri��o da exce��o
     */
    public ActionForward execute(ActionMapping actionMapping,
            ActionForm actionForm, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) throws RemoteException,
            ErroRepositorioException {

        ActionForward retorno = actionMapping.findForward("telaSucesso");

        // Prepara o retorno da A��o
        HttpSession sessao = httpServletRequest.getSession(false);

        //Obt�m o action form
        TabelaAuxiliarFaixaActionForm tabelaAuxiliarFaixaActionForm = (TabelaAuxiliarFaixaActionForm) actionForm;

        //Obt�m a fachada
        Fachada fachada = Fachada.getInstancia();

        //Recebe o objeto TabelaAuxiliarFaixa
        TabelaAuxiliarFaixa tabelaAuxiliarFaixa = (TabelaAuxiliarFaixa) sessao
                .getAttribute("tabelaAuxiliarFaixa");

        //Seta faixa inicial informada
        tabelaAuxiliarFaixa.setFaixaInicial(tabelaAuxiliarFaixaActionForm
                .getFaixaInicial());

        //Seta faixa final faixa informada
        tabelaAuxiliarFaixa.setFaixaFinal(tabelaAuxiliarFaixaActionForm
                .getFaixaFinal());

        //Seta a data atual
        tabelaAuxiliarFaixa.setUltimaAlteracao(new Date());

        //Insere objeto
        fachada.inserirTabelaAuxiliar(tabelaAuxiliarFaixa);

        //Monta a p�gina de sucesso
        if (retorno.getName().equalsIgnoreCase("telaSucesso")) {
            montarPaginaSucesso(
                    httpServletRequest,
                    "Dados inclu�dos com sucesso",
                    "Inserir outro(a) "
                            + ((String) sessao.getAttribute("titulo")),
                    ((String) sessao
                            .getAttribute("funcionalidadeTabelaAuxiliarFaixaInserir")));
        }

        //Remove os objetos da sess�o
        sessao.removeAttribute("funcionalidadeTabelaAuxiliarFaixaInserir");
        sessao.removeAttribute("titulo");
        sessao.removeAttribute("tabelaAuxiliarFaixa");

        return retorno;
    }
}
