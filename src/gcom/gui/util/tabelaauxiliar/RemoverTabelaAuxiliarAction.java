package gcom.gui.util.tabelaauxiliar;

import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.gui.ManutencaoRegistroActionForm;

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
public class RemoverTabelaAuxiliarAction extends GcomAction {

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

        //Obt�m o action form
        ManutencaoRegistroActionForm manutencaoRegistroActionForm = (ManutencaoRegistroActionForm) actionForm;

        //Obt�m a inst�ncia da sess�o
        HttpSession sessao = httpServletRequest.getSession(false);

        //Cria os objetos
        String pacoteNomeObjeto = null;

        // Obt�m os ids de remo��o
        String[] ids = manutencaoRegistroActionForm.getIdRegistrosRemocao();

        //Seta o mapeamento de retorno
        ActionForward retorno = actionMapping.findForward("telaSucesso");

        //Obt�m a inst�ncia da fachada
        Fachada fachada = Fachada.getInstancia();

        //mensagem de erro quando o usu�rio tenta excluir sem ter selecionado
        // nenhum
        //registro
        if (ids == null || ids.length == 0) {
            reportarErros(httpServletRequest, "erro.registros.nao_selecionados");
            retorno = actionMapping.findForward("telaAtencao");
        }

        //Pega o path do pacote do objeto mais o tipo
        pacoteNomeObjeto = (String) sessao.getAttribute("pacoteNomeObjeto");

        //Remove o objeto
        fachada.removerTabelaAuxiliar(ids, pacoteNomeObjeto,null, null);

        String titulo = (String) sessao.getAttribute("titulo");
        
        //Monta a p�gina de sucesso
        if (retorno.getName().equalsIgnoreCase("telaSucesso")) {
            montarPaginaSucesso(
                    httpServletRequest,
                    titulo+ " removido(a)(s) com sucesso",
                    "Realizar outra manuten��o de "+ titulo,
                    ((String) sessao.getAttribute("funcionalidadeTabelaAuxiliarFiltrar")));
        }

        //Remove os objetos da sess�o
        sessao.removeAttribute("funcionalidadeTabelaAuxiliarFiltrar");
        sessao.removeAttribute("titulo");
        sessao.removeAttribute("descricao");
        sessao.removeAttribute("tabelaAuxiliar");
        sessao.removeAttribute("tamanhoMaximoCampo");
        sessao.removeAttribute("pacoteNomeObjeto");
        sessao.removeAttribute("nomeSistema");
        sessao.removeAttribute("tabelasAuxiliares");
        sessao.removeAttribute("tela");
        sessao.removeAttribute("totalRegistros");

        //devolve o mapeamento de retorno
        return retorno;
    }
}
