package gcom.gui.financeiro;

import gcom.fachada.Fachada;
import gcom.financeiro.FiltroLancamentoOrigem;
import gcom.financeiro.lancamento.LancamentoOrigem;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * Gerar integra��o para contabilidade.
 *
 * @author Fl�vio Leonardo
 * @date 13/06/2007
 */
public class ExibirGerarIntegracaoContabilidadeCaernAction extends GcomAction {
	/**
	 * Description of the Method
	 * 
	 * @param actionMapping
	 *            Description of the Parameter
	 * @param actionForm
	 *            Description of the Parameter
	 * @param httpServletRequest
	 *            Description of the Parameter
	 * @param httpServletResponse
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, 
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		//seta o retorno para a p�gina de gerar integra��o para a contabilidade
		ActionForward retorno = actionMapping.findForward("exibirGerarIntegracaoContabilidadeCaern");

		//cria uma inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		//recupera a sess�o do usu�rio
		HttpSession sessao = httpServletRequest.getSession(false);
		
		//Pesquisa as origens de lan�amentos cadastradas no sistema    
		FiltroLancamentoOrigem filtroLancamentoOrigem = new FiltroLancamentoOrigem();
		Collection colecaoLancamentoOrigem = fachada.pesquisar(filtroLancamentoOrigem, LancamentoOrigem.class.getName());

		//[FS0001 - Verificar exist�ncia de dados]
		//caso n�o exista nenhuma origem de lan�amento cadastrada no sistema 
		//levanta a exce��o para o usu�rio
		if(colecaoLancamentoOrigem == null || colecaoLancamentoOrigem.isEmpty()){
			throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null,"Lan�amento Origem");
		}else{
			sessao.setAttribute("colecaoLancamentoOrigem", colecaoLancamentoOrigem);
		}
		return retorno;
	}
}
