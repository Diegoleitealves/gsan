package gcom.gui.cobranca.spcserasa;

import gcom.cobranca.NegativadorRegistroTipo;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.gui.ManutencaoRegistroActionForm;
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
 * Action form de manter tipo de registro do negativador remove um ou mais objeto do tipo
 * NegativadorRegistroTipo
 * 
 * @author Yara Taciane de Souza
 * @created 07/01/2008
 */
public class RemoverNegativadorRegistroTipoAction extends GcomAction {
	/**
	 * @author Yara Taciane de Souza
	 * @date 07/01/2008
	 * 
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		ManutencaoRegistroActionForm manutencaoRegistroActionForm = (ManutencaoRegistroActionForm) actionForm;
		

		//Tenho que criar a opera��o para ContratoNegativador -> OPERACAO_CLIENTE_REMOVER 
		
        
        HttpSession sessao = httpServletRequest.getSession(false);
        
	    Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

  
	   	 

	    //------------ REGISTRAR TRANSA��O ----------------
	        Operacao operacao = new Operacao();
	        operacao.setId(Operacao.OPERACAO_REMOVER_NEGATIVADOR_REGISTRO_TIPO);

	        OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
	        operacaoEfetuada.setOperacao(operacao);
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
    	UsuarioAcaoUsuarioHelper usuarioAcaoUsuarioHelper = new UsuarioAcaoUsuarioHelper(usuario,UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
    	Collection<UsuarioAcaoUsuarioHelper> colecaoUsuarios = new ArrayList();
    	colecaoUsuarios.add(usuarioAcaoUsuarioHelper);
        //------------ REGISTRAR TRANSA��O ----------------

	
        try {
        	 fachada.remover(ids, NegativadorRegistroTipo.class.getName(), operacaoEfetuada, colecaoUsuarios);
        } catch (Exception e) {
			e.getCause();
			if (e != null && e.getMessage() != null	&& "atencao.dependencias.existentes" .equalsIgnoreCase(e.getMessage())) {
				throw new ActionServletException("atencao.dependencias.existentes");
			}

		}
        
       
		// Monta a p�gina de sucesso
		if (retorno.getName().equalsIgnoreCase("telaSucesso")) {
			montarPaginaSucesso(httpServletRequest,
					 ids.length +
					"Tipo(os) de Registro do Negativador removido(s) com sucesso.",
					"Realizar outra manuten��o de Tipo de Registro do Negativador",
					"exibirFiltrarNegativadorRegistroTipoAction.do?desfazer=S");
		}

		return retorno;
	}

}
