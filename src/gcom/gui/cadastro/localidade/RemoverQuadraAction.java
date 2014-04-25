package gcom.gui.cadastro.localidade;

import gcom.cadastro.localidade.Quadra;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.OperacaoEfetuada;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioAcao;
import gcom.seguranca.acesso.usuario.UsuarioAcaoUsuarioHelper;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class RemoverQuadraAction extends GcomAction {

    public ActionForward execute(ActionMapping actionMapping,
            ActionForm actionForm, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {

        //Seta o retorno
        ActionForward retorno = actionMapping.findForward("telaSucesso");

        //Obt�m a inst�ncia da fachada
        Fachada fachada = Fachada.getInstancia();

        HttpSession sessao = httpServletRequest.getSession(false);
        
        FiltrarQuadraActionForm filtrarQuadraActionForm = (FiltrarQuadraActionForm) actionForm;
        
        Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
        
        //------------ REGISTRAR TRANSA��O ----------------
        Operacao operacao = new Operacao();
        operacao.setId(Operacao.OPERACAO_QUADRA_REMOVER);

        OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
        operacaoEfetuada.setOperacao(operacao);
        //------------ REGISTRAR TRANSA��O ----------------

        String[] selecaoQuadra = filtrarQuadraActionForm
                .getQuadraID();

        if (selecaoQuadra == null || selecaoQuadra.length < 1) {
        	//Nenhum registro selecionado para remo��o.
            throw new ActionServletException("atencao.registros.nao_selecionados");
        } else {
            //------------ REGISTRAR TRANSA��O ----------------
        	UsuarioAcaoUsuarioHelper usuarioAcaoUsuarioHelper = new UsuarioAcaoUsuarioHelper(usuario,UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
        	Collection<UsuarioAcaoUsuarioHelper> colecaoUsuarios = new ArrayList();
        	colecaoUsuarios.add(usuarioAcaoUsuarioHelper);
            //------------ REGISTRAR TRANSA��O ----------------  
        	
            /** alterado por pedro alexandre dia 18/11/2006 
             * Recupera o usu�rio logado para passar no met�do de remover quadra
             * para verificar se o usu�rio tem abrang�ncia para remover as quadras 
             * selecionadas.
             */
            Usuario usuarioLogado = (Usuario)sessao.getAttribute(Usuario.USUARIO_LOGADO);
            fachada.removerQuadra(selecaoQuadra, Quadra.class.getName(),operacaoEfetuada, colecaoUsuarios,usuarioLogado);
            
            //fachada.remover(selecaoQuadra, Quadra.class.getName(),operacaoEfetuada, colecaoUsuarios);
        }

        montarPaginaSucesso(httpServletRequest,
        		selecaoQuadra.length +
				" Quadra(s) removida(s) com sucesso.",
				"Realizar outra manuten��o de Quadra",
				"exibirFiltrarQuadraAction.do?desfazer=S");
        
        //devolve o mapeamento de retorno
        return retorno;
    }

}
