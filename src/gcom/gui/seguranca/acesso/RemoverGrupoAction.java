package gcom.gui.seguranca.acesso;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.gui.ManutencaoRegistroActionForm;
import gcom.seguranca.acesso.usuario.Usuario;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action respons�vel por remover um grupo do sistema e 
 * suas permiss�es se existir. 
 *
 * @author Pedro Alexandre
 * @date 29/06/2006
 */
public class RemoverGrupoAction extends GcomAction {
	
    /**
     * <Breve descri��o sobre o caso de uso>
     *
     * [UC0279] - Manter Grupo
     *
     * @author Pedro Alexandre
     * @date 29/06/2006
     *
     * @param actionMapping
     * @param actionForm
     * @param httpServletRequest
     * @param httpServletResponse
     * @return
     */
    public ActionForward execute(ActionMapping actionMapping,
            					 ActionForm actionForm, 
            					 HttpServletRequest httpServletRequest,
            					 HttpServletResponse httpServletResponse) {

    	//Seta o mapeamento de retorno para a tela de sucesso
        ActionForward retorno = actionMapping.findForward("telaSucesso");

        //Recupera o form de manuten��o de registros
        ManutencaoRegistroActionForm manterGrupoActionForm = (ManutencaoRegistroActionForm) actionForm;

        //Cria uma inst�ncia da fachada
        Fachada fachada = Fachada.getInstancia();
        
        //Recupera os id's de grupo selecionados para remo��o.
        String[] idsRegistrosRemocao = manterGrupoActionForm.getIdRegistrosRemocao();

        /* Caso nenhum grupo tenha sido selecionado para remo��o
         * levanta uma exce�a� indicando que nenhum registro foi selecionado
         * caso contr�rio remove todos os grupos selecionados e suas permi��es.
         */         
        if (idsRegistrosRemocao == null || idsRegistrosRemocao.length < 1) {
            //Nenhum Grupo foi escolhido
            throw new ActionServletException("atencao.registros.nao_selecionados");
        } else {
        	//Remove Grupo(s) selecionado(s)
            //REGISTRAR OPERA��O
        	//Cria uma inst�ncia da sess�o
        	HttpSession sessao = httpServletRequest.getSession(false);
        	
        	Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");
        	        	
        	fachada.removerGrupo(idsRegistrosRemocao, usuarioLogado);
        }
        
        //Monta a p�gina de sucesso
        montarPaginaSucesso(httpServletRequest,
        		idsRegistrosRemocao.length +
				" Grupo(s) removido(s) com sucesso.",
				"Realizar outra manuten��o de grupo",
				"exibirManterGrupoAction.do?menu=sim");
        
        //Retorna o mapeamento contido na vari�vel retorno 
        return retorno;
    }
}
