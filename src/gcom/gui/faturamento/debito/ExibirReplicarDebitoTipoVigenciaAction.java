package gcom.gui.faturamento.debito;

import gcom.fachada.Fachada;
import gcom.faturamento.debito.DebitoTipoVigencia;
import gcom.gui.GcomAction;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC0985] Inserir tipo de d�bito com vig�ncia
 * 
 * Este caso de uso permite a inclus�o de um novo debito tipo vigencia 
 *
 * @author Josenildo Neves
 * @date 22/02/2010
 */
public class ExibirReplicarDebitoTipoVigenciaAction extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

		ActionForward retorno = actionMapping.findForward("replicarDebitoTipoVigencia");

		ReplicarDebitoTipoVigenciaActionForm replicarDebitoTipoVigenciaActionForm = (ReplicarDebitoTipoVigenciaActionForm) actionForm;

		retorno = this.getDebitoTipoVigencia(replicarDebitoTipoVigenciaActionForm,httpServletRequest, retorno);
		
		return retorno;
		
	}
	
	/**
	 * [SB0002] � Replicar os servi�os existentes para uma nova vig�ncia e valor
	 * 
	 * Este m�todo exibe a cole��o de D�bito Tipo Vig�ncia que tem a �ltima data de vig�ncia. 
	 *
	 * @author Josenildo Neves
	 * @date 22/02/2010
	 */
	private ActionForward getDebitoTipoVigencia(ReplicarDebitoTipoVigenciaActionForm form, HttpServletRequest httpServletRequest, ActionForward retorno) {
		
		Collection<DebitoTipoVigencia> colecaoDebitoTipoVigencia = null;
		
		// 1� Passo - Pegar o total de registros atrav�s de um count da
		// consulta que aparecer� na tela
		Integer totalRegistros = Fachada.getInstancia().pesquisarDebitoTipoVigenciaUltimaVigenciaTotal();

		// 2� Passo - Chamar a fun��o de Pagina��o passando o total de
		// registros
		retorno = this.controlarPaginacao(httpServletRequest, retorno,
				totalRegistros);
		
		if (httpServletRequest.getParameter("page.offset") != null) {
			colecaoDebitoTipoVigencia = Fachada.getInstancia().pesquisarDebitoTipoVigenciaUltimaVigencia(Integer.parseInt(httpServletRequest.getParameter("page.offset")) - 1);
		
		}else{
			colecaoDebitoTipoVigencia = Fachada.getInstancia().pesquisarDebitoTipoVigenciaUltimaVigencia(0);
		
		}
		
		if (colecaoDebitoTipoVigencia != null && !colecaoDebitoTipoVigencia.isEmpty()) {
			
			form.setMensagem(true);
			form.setCollDebitoTipoVigencia(colecaoDebitoTipoVigencia);			
			
		} else {
			form.setMensagem(false);
			form.setCollDebitoTipoVigencia(null);
		}
		
		return retorno;
	}
	
}
