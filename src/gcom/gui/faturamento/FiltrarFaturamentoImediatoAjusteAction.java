package gcom.gui.faturamento;

import gcom.gui.ActionServletException;
import gcom.util.Util;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC0991] Filtrar Faturamento Imediato Ajuste
 * 
 * @author Hugo Leonardo
 *
 * @date 26/02/2010
 */

public class FiltrarFaturamentoImediatoAjusteAction extends Action {
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

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping
				.findForward("consultarFaturamentoImediatoAjuste");

		FiltrarFaturamentoImediatoAjusteActionForm form = (FiltrarFaturamentoImediatoAjusteActionForm) actionForm;
		FaturamentoImediatoAjusteHelper helper = new FaturamentoImediatoAjusteHelper();
		
		// mesAno Referencia 
		if (form != null && form.getMesAnoReferencia() != null && 
			!form.getMesAnoReferencia().equals("") && !form.getMesAnoReferencia().equals("00/0000")) {
			
			Integer anoMesReferencia = Util.formatarMesAnoComBarraParaAnoMes(form.getMesAnoReferencia().toString());
				
			//Validar o Ano/Mes Referencia
			helper.setMesAnoReferencia(anoMesReferencia.toString());
			
		}else {
			throw new ActionServletException("atencao.anomesreferencia.invalida", null, form.getMesAnoReferencia());
		}
		
		// Grupo Faturamento
		if ( form.getFaturamentoGrupo() != null && 
				!form.getFaturamentoGrupo().equals("-1")) {
			
			helper.setFaturamentoGrupo(form.getFaturamentoGrupo());
		}
		
		// Imovel
		if(form.getImovelId() != null && !form.getImovelId().equals("")){
			helper.setImovelId(form.getImovelId());
		}
		
		// ROTA
		if(form.getRotaId() != null && !form.getRotaId().equals("")){
			helper.setRotaId(form.getRotaId());
		}

		//Manda a Collection pelo request para o
		// Faturamento Imediato Ajuste
		httpServletRequest.setAttribute("helper", helper);

		//Devolve o mapeamento de retorno
		return retorno;
	}
}
