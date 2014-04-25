package gcom.gui.seguranca.acesso.usuario;

import gcom.gui.GcomAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action para pre-exibi��o dos dados da situa��o especial de faturamento que
 * ser�o retirados
 * 
 * Descri��o da classe
 * 
 * @author Thiago Ten�rio
 * @date 06/05/2006
 */
public class ExibirInserirSituacaoUsuarioAction extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		InserirSituacaoUsuarioActionForm inserirSituacaoUsuarioActionForm = (InserirSituacaoUsuarioActionForm) actionForm;
       
		// Seta o valor 1 no Indicador de Uso do Sistema 
		if (inserirSituacaoUsuarioActionForm.getIndicadorUsoSistema() == null
				|| !inserirSituacaoUsuarioActionForm.equals("")) {
			inserirSituacaoUsuarioActionForm.setIndicadorUsoSistema("1");
		}

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping
				.findForward("inserirSituacaoUsuario");

		return retorno;

	}

}
