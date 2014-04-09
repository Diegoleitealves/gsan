package gcom.gui.atendimentopublico.ordemservico;

import gcom.atendimentopublico.ordemservico.ServicoCobrancaValor;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC0391] Inserir Valor de Cobran�a de Servi�o
 * 
 * Este caso de uso permite a inclus�o de um novo valor de cobran�a de servi�o 
 *
 * @author Leonardo Regis
 * @date 28/09/2006
 */
public class ExibirReplicarValorCobrancaServicoAction extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

		ActionForward retorno = actionMapping.findForward("replicarValorCobrancaServico");

		ReplicarValorCobrancaServicoActionForm replicarCobrancaServicoActionForm = (ReplicarValorCobrancaServicoActionForm) actionForm;

		retorno = this.getServicoCobracaValor(replicarCobrancaServicoActionForm,httpServletRequest, retorno);
		
		return retorno;
		
	}
	
	/**
	 * [SB0002] � Replicar os servi�os existentes para uma nova vig�ncia e valor
	 * 
	 * Este m�todo exibe a cole��o de Valor Servi�o Cobran�a que tem a �ltima data de vig�ncia. 
	 *
	 * @author Josenildo Neves
	 * @date 04/02/2010
	 */
	private ActionForward getServicoCobracaValor(ReplicarValorCobrancaServicoActionForm form, HttpServletRequest httpServletRequest, ActionForward retorno) {
		
		Collection<ServicoCobrancaValor> colecaoServicoCobrancaValor = null;
		
		// 1� Passo - Pegar o total de registros atrav�s de um count da
		// consulta que aparecer� na tela
		Integer totalRegistros = Fachada.getInstancia().pesquisarServicoCobrancaValorUltimaVigenciaTotal();

		// 2� Passo - Chamar a fun��o de Pagina��o passando o total de
		// registros
		retorno = this.controlarPaginacao(httpServletRequest, retorno,
				totalRegistros);

		colecaoServicoCobrancaValor = Fachada.getInstancia().pesquisarServicoCobrancaValorUltimaVigencia(
				(Integer) httpServletRequest.getAttribute("numeroPaginasPesquisa"));

		if (colecaoServicoCobrancaValor != null && !colecaoServicoCobrancaValor.isEmpty()) {
			
			form.setMensagem(true);
			form.setCollServicoCobrancaValor(colecaoServicoCobrancaValor);			
			
		} else {
			
			form.setMensagem(false);
			form.setCollServicoCobrancaValor(null);
		}
		
		return retorno;
		
	}
	
	
}
