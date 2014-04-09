package gcom.gui.util.tabelaauxiliar;

import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.tabelaauxiliar.TabelaAuxiliar;

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
public class AtualizarTabelaAuxiliarAction extends GcomAction {
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
        TabelaAuxiliarActionForm tabelaAuxiliarActionForm = (TabelaAuxiliarActionForm) actionForm;

        //Seta o retorno
        ActionForward retorno = actionMapping.findForward("telaSucesso");

        //Obt�m a inst�ncia da fachada
        Fachada fachada = Fachada.getInstancia();

        //Obt�m a sess�o
        HttpSession sessao = httpServletRequest.getSession(false);

        //Recupera o ponto de coleta da sess�o
        TabelaAuxiliar tabelaAuxiliar = (TabelaAuxiliar) sessao
                .getAttribute("tabelaAuxiliar");

        //Atualiza a tabela auxiliar com os dados inseridos pelo usu�rio
        //A data de �ltima altera��o n�o � alterada, pois ser� usada na
        //verifica��o de atualiza��o
        tabelaAuxiliar.setDescricao(tabelaAuxiliarActionForm.getDescricao().toUpperCase());
        tabelaAuxiliar.setIndicadorUso(new Short(tabelaAuxiliarActionForm.getIndicadorUso()));

        //Atualiza os dados
        fachada.atualizarTabelaAuxiliar(tabelaAuxiliar);
        
        String titulo = (String) sessao.getAttribute("titulo");
        
        //Monta a p�gina de sucesso
        if (retorno.getName().equalsIgnoreCase("telaSucesso")) {

            montarPaginaSucesso(httpServletRequest,
            	titulo+" atualizada com sucesso",
                "Realizar outra manuten��o de "+ titulo,
                ((String) sessao.getAttribute("funcionalidadeTabelaAuxiliarFiltrar"))+"&menu=sim");
        }

        //Remove os objetos da sess�o
        sessao.removeAttribute("funcionalidadeTabelaAuxiliarFiltrar");
        sessao.removeAttribute("titulo");
        sessao.removeAttribute("descricao");
        sessao.removeAttribute("tabelaAuxiliar");
        sessao.removeAttribute("tamanhoMaximoCampo");
        sessao.removeAttribute("pacoteNomeObjeto");
        sessao.removeAttribute("tabelasAuxiliares");
        sessao.removeAttribute("tela");
        sessao.removeAttribute("totalRegistros");
        
        //devolve o mapeamento de retorno
        return retorno;
    }

}
