package gcom.gui.relatorio.atendimentopublico.ordemservico;

import gcom.gui.GcomAction;
import gcom.gui.atendimentopublico.ordemservico.FiltrarOrdemProcessoRepavimentacaoActionForm;
import gcom.util.ConstantesSistema;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * action respons�vel pela exibi��o do relat�rio de ordem servico repavimentacao
 * 
 * @author Rafael Pinto
 * @created 30/03/2011
 */
public class ExibirImprimirRelacaoOrdemServicoRepavimentacaoAction extends GcomAction {

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

		// cria a vari�vel de retorno
		ActionForward retorno = null;
		retorno = actionMapping.findForward("exibirImprimirRelacao");
		
		FiltrarOrdemProcessoRepavimentacaoActionForm form = (FiltrarOrdemProcessoRepavimentacaoActionForm) actionForm;
		
		form.setIndicadorOsObservacaoRetorno(ConstantesSistema.NAO.toString());

		// devolve o mapeamento contido na vari�vel retorno
		return retorno;
	}



}
