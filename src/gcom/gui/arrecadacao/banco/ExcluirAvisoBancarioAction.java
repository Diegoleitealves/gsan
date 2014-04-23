package gcom.gui.arrecadacao.banco;

import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.OperacaoEfetuada;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioAcao;
import gcom.seguranca.acesso.usuario.UsuarioAcaoUsuarioHelper;

import java.util.Collection;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * Action que exclui os aviso bancarios selecionado
 *
 * @author thiago toscano
 * @date 10/03/2006
 */
public class ExcluirAvisoBancarioAction  extends GcomAction {
	
    /**
     * M�todo responsavel por responder a requisicao
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
            ActionForm actionForm, HttpServletRequest request,
            HttpServletResponse httpServletResponse) {

        ActionForward retorno = actionMapping.findForward("telaSucesso");

        HttpSession sessao = request.getSession(false);

        Operacao operacao = new Operacao();
        operacao.setId(Operacao.OPERACAO_REMOVER_AVISO_BANCARIO);

        OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
        operacaoEfetuada.setOperacao(operacao);
        
        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");
        UsuarioAcao usuarioAcao = UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO;
        
        Collection<UsuarioAcaoUsuarioHelper> helper = new Vector();
        helper.add(new UsuarioAcaoUsuarioHelper(usuarioLogado, usuarioAcao));        
        

        ExcluirAvisoBancarioActionForm form = (ExcluirAvisoBancarioActionForm)actionForm;
        String[] ids = form.getIdRegistrosRemocao();
        if (ids != null && ids.length > 0) {
        	Integer[] integer = new Integer[ids.length];
        	for (int i = 0; i < ids.length; i++) {
        		try {
        			integer[i] = new Integer(ids[i]);	
				} catch (Exception e) {
					integer[i] = new Integer(0);
				}
			}
        	
        	Fachada.getInstancia().removerAvisosBancarios(integer, operacaoEfetuada, helper);
        	
        }

    	sessao.removeAttribute("avisoDeducoes");
    	sessao.removeAttribute("avisoDeducoesRemover");
        sessao.removeAttribute("avisoAcerto");
    	sessao.removeAttribute("avisoAcertoRemover");

        request.setAttribute("caminhoFuncionalidade","exibirFiltrarAvisoBancarioAction.do?menu=sim");
		request.setAttribute("labelPaginaSucesso","Realizar outra Manuten��o de Aviso Banc�rio");
		request.setAttribute("mensagemPaginaSucesso",ids.length + " Aviso(s) Banc�rio removido(s) com sucesso.");

        return retorno;
   }
    
}
