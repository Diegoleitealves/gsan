package gcom.gui.cobranca;

import gcom.fachada.Fachada;
import gcom.gui.GcomAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Inserir D�bito a Cobrar ao Imovel
 * [UC0183] Inserir D�bito a Cobrar
 * @author Rafael Santos
 * @since 21/12/2005 
 * 
 */
public class ExibirInserirReligarImoveisCortadosAction extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping
				.findForward("exibirInserirReligarImoveisCortados");

		Fachada fachada = Fachada.getInstancia();

		
		fachada.religarAutomaticamenteImovelCortado();
		
		return retorno;
	}
}
