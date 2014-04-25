package gcom.gui.gerencial.micromedicao;

import gcom.fachada.FachadaBatch;
import gcom.gui.GcomAction;
import gcom.util.email.ErroEmailException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * < <Descri��o da Classe>>
 * 
 * @author Administrador
 */
public class GerarResumoAnormalidadeAction extends GcomAction {
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
			HttpServletResponse httpServletResponse) throws ErroEmailException {

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("telaPrincipal");
		// Obt�m a inst�ncia da fachada
		FachadaBatch fachadaBatch = FachadaBatch.getInstancia();

		fachadaBatch.gerarResumoAnormalidadeLeitura();

		return retorno;
	}
}
