package gcom.gui.cobranca;

import gcom.cobranca.CobrancaAcao;
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

/**
 * Esta classe tem por finalidade remover uma ou v�rias a��es de cobran�a que tenham 
 * sido selecionadas anteriormente pelo usu�rio
 * 
 * @author Raphael Rossiter
 * @date 13/09/2007
 */
public class RemoverAcaoCobrancaAction extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping,
            ActionForm actionForm, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
		
		ActionForward retorno = actionMapping.findForward("telaSucesso");
		
		AcaoCobrancaFiltrarActionForm acaoCobrancaFiltrarActionForm = 
		(AcaoCobrancaFiltrarActionForm) actionForm;
		
		HttpSession sessao = httpServletRequest.getSession(false);
		
		String[] selecaoCobrancaAcao = acaoCobrancaFiltrarActionForm.getCobrancaAcaoSelectID();
		
		if (selecaoCobrancaAcao == null || selecaoCobrancaAcao.length < 1) {
            throw new ActionServletException("atencao.registros.nao_selecionados");
        } 
		else{
        	
			Fachada fachada = Fachada.getInstancia();
			
			//------------ REGISTRAR TRANSA��O ----------------
	        Operacao operacao = new Operacao();
	        operacao.setId(Operacao.OPERACAO_COBRANCA_ACAO_REMOVER);

	        OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
	        operacaoEfetuada.setOperacao(operacao);
	        
	        //USU�RIO
	        Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
	        UsuarioAcaoUsuarioHelper usuarioAcaoUsuarioHelper = 
	        new UsuarioAcaoUsuarioHelper(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
        	
	        Collection<UsuarioAcaoUsuarioHelper> colecaoUsuarios = new ArrayList();
        	colecaoUsuarios.add(usuarioAcaoUsuarioHelper);
	        //------------ REGISTRAR TRANSA��O ----------------
	        
			fachada.remover(selecaoCobrancaAcao, CobrancaAcao.class.getName(), operacaoEfetuada, colecaoUsuarios);
        }
		
		sessao.removeAttribute("colecaoCobrancaAcao");
		
		montarPaginaSucesso(httpServletRequest,
				selecaoCobrancaAcao.length +
				" A��es de Cobran�a removida(s) com sucesso.",
				"Realizar outra manuten��o de A��o de Cobran�a",
				"exibirFiltrarAcaoCobrancaAction.do?menu=sim");
        
		
        return retorno;
	}
}
