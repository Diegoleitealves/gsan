package gcom.gui.util.tabelaauxiliar.tipo;

import gcom.util.ConstantesSistema;
import gcom.util.filtro.ComparacaoTexto;
import gcom.util.filtro.ParametroSimples;
import gcom.util.tabelaauxiliar.tipo.FiltroTabelaAuxiliarTipo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

/**
 * < <Descri��o da Classe>>
 * 
 * @author Administrador
 */
public class FiltrarTabelaAuxiliarTipoAction extends Action {
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

        //Seta o mapeamento de retorno
        ActionForward retorno = actionMapping
                .findForward("retornarFiltroTabelaAuxiliarTipo");

        DynaValidatorForm pesquisarActionForm = (DynaValidatorForm) actionForm;

        //Obt�m a inst�ncia da sess�o
        HttpSession sessao = httpServletRequest.getSession(false);

        //pega na sess�o o nome do tipo do objeto mandado pelo
        // ExibirManterTabelaAuxiliarTipoAction
        String tipoObjeto = (String) sessao.getAttribute("tipo");

        //Recupera os par�metros do form
        String id = (String) pesquisarActionForm.get("id");
        String descricao = (String) pesquisarActionForm.get("descricao");
        Integer tipo = (Integer) pesquisarActionForm.get("tipo");

        //cria o filtro para Tabela Auxiliar
        FiltroTabelaAuxiliarTipo filtroTabelaAuxiliarTipo = new FiltroTabelaAuxiliarTipo();

        //Insere os par�metros informados no filtro
        if (id != null && !id.trim().equalsIgnoreCase("")) {
            filtroTabelaAuxiliarTipo.adicionarParametro(new ParametroSimples(
                    FiltroTabelaAuxiliarTipo.ID, id));
        }
        if (descricao != null && !descricao.trim().equalsIgnoreCase("")) {
            filtroTabelaAuxiliarTipo.adicionarParametro(new ComparacaoTexto(
                    FiltroTabelaAuxiliarTipo.DESCRICAO, descricao));
        }

        if (tipo != null
                && tipo.intValue() != ConstantesSistema.NUMERO_NAO_INFORMADO) {
            filtroTabelaAuxiliarTipo.adicionarParametro(new ParametroSimples(
                    tipoObjeto, tipo));
        }

        //Manda o filtro pelo request para o ExibirManterTabelaAuxiliar
        httpServletRequest.setAttribute("filtroTabelaAuxiliarTipo",
                filtroTabelaAuxiliarTipo);
        httpServletRequest.setAttribute("tipo", tipo);
        sessao.removeAttribute("tipo");

        //Devolve o mapeamento de retorno
        return retorno;
    }
}
