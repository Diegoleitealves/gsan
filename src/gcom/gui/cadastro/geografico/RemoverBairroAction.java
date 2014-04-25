package gcom.gui.cadastro.geografico;

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
 * Remove os bairros selecionados na lista da funcionalidade Manter Bairro
 * 
 * @author S�vio Luiz
 * @created 4 de Julho de 2004
 */
public class RemoverBairroAction extends GcomAction {
    /**
     * Description of the Method
     * 
     * @param actionMapping
     *            Description of the Parameter
     * @param actionForm
     *            Description of the Parameter
     * @param httpServletRequest
     *            Description of the Parameter
     * @param httpServletResponse
     *            Description of the Parameter
     * @return Description of the Return Value
     */
    public ActionForward execute(ActionMapping actionMapping,
            ActionForm actionForm, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {

        ManutencaoRegistroActionForm manutencaoRegistroActionForm = (ManutencaoRegistroActionForm) actionForm;

        // Obt�m os ids de remo��o
        String[] ids = manutencaoRegistroActionForm.getIdRegistrosRemocao();

        //Seta o mapeamento de retorno
        ActionForward retorno = actionMapping.findForward("telaSucesso");
        
    	HttpSession sessao = httpServletRequest.getSession(false);
    	
        Usuario usuarioLogado = (Usuario)sessao.getAttribute(Usuario.USUARIO_LOGADO);
        
        //Obt�m a sess�o
       // HttpSession sessao = httpServletRequest.getSession(false);

        //Obt�m a inst�ncia da fachada
        Fachada fachada = Fachada.getInstancia();

        //------------ REGISTRAR TRANSA��O ----------------
//        Operacao operacao = new Operacao();
//        operacao.setId(Operacao.OPERACAO_BAIRRO_REMOVER);
//
//        OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
//        operacaoEfetuada.setOperacao(operacao);
        //------------ REGISTRAR TRANSA��O ----------------
        
        //mensagem de erro quando o usu�rio tenta excluir sem ter selecionado
        // nenhum
        //registro
        if (ids == null || ids.length == 0) {
            throw new ActionServletException(
                    "atencao.registros.nao_selecionados");
        }
        
        Integer idQt = ids.length;
        
        //------------ REGISTRAR TRANSA��O ----------------
//    	UsuarioAcaoUsuarioHelper usuarioAcaoUsuarioHelper = new UsuarioAcaoUsuarioHelper(usuarioLogado,UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
//    	Collection<UsuarioAcaoUsuarioHelper> colecaoUsuarios = new ArrayList();
//    	colecaoUsuarios.add(usuarioAcaoUsuarioHelper);
        //------------ REGISTRAR TRANSA��O ----------------
        
//        fachada.remover(ids, Bairro.class.getName(),operacaoEfetuada, colecaoUsuarios);
    	fachada.removerBairro(ids,usuarioLogado);

        //Monta a p�gina de sucesso
        if (retorno.getName().equalsIgnoreCase("telaSucesso")) {
            montarPaginaSucesso(httpServletRequest, idQt.toString() +
                    " Bairro(s) removido(s) com sucesso.",
                    "Realizar outra manuten��o de Bairro",
                    "exibirFiltrarBairroAction.do?menu=sim");
        }

        return retorno;
    }

}
