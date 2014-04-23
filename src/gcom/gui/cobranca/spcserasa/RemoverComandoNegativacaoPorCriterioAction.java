package gcom.gui.cobranca.spcserasa;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.gui.ManutencaoRegistroActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Remove os comandos negativa��o selecionados na lista da funcionalidade 
 * [UC652]Manter Coamndo de Negativa��o por Crit�rio
 * 
 * @author Ana Maria 
 */
public class RemoverComandoNegativacaoPorCriterioAction extends GcomAction {

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

        ManutencaoRegistroActionForm manutencaoRegistroActionForm = (ManutencaoRegistroActionForm) actionForm;
        
        //HttpSession sessao = httpServletRequest.getSession(false);
        
        //Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
        //------------ REGISTRAR TRANSA��O ----------------
/*        Operacao operacao = new Operacao();
        operacao.setId(Operacao.OPERACAO_CLIENTE_REMOVER);

        OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
        operacaoEfetuada.setOperacao(operacao);*/
        //------------ REGISTRAR TRANSA��O ----------------


        String[] ids = manutencaoRegistroActionForm.getIdRegistrosRemocao();

        ActionForward retorno = actionMapping.findForward("telaSucesso");

        Fachada fachada = Fachada.getInstancia();

        // mensagem de erro quando o usu�rio tenta excluir sem ter selecionado
        // nenhum registro
        if (ids == null || ids.length == 0) {
            throw new ActionServletException(
                    "atencao.registros.nao_selecionados");
        }

    	//------------ REGISTRAR TRANSA��O ----------------
/*    	UsuarioAcaoUsuarioHelper usuarioAcaoUsuarioHelper = new UsuarioAcaoUsuarioHelper(usuario,UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
    	Collection<UsuarioAcaoUsuarioHelper> colecaoUsuarios = new ArrayList();
    	colecaoUsuarios.add(usuarioAcaoUsuarioHelper);*/
        //------------ REGISTRAR TRANSA��O ----------------

        fachada.removerComandoNegativacaoPorCriterio(ids);

        //Monta a p�gina de sucesso
        if (retorno.getName().equalsIgnoreCase("telaSucesso")) {
            montarPaginaSucesso(httpServletRequest,
            		ids.length + " Comando(s) de Negativa��o Crit�rio removido(s) com sucesso",
                    "Realizar outra Manuten��o de Comando Negativa��o Crit�rio",
                    "exibirFiltrarComandoNegativacaoPorCriterioAction.do?menu=sim");
        }

        return retorno;
    }
}
