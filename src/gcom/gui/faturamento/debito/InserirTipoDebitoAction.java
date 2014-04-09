package gcom.gui.faturamento.debito;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
/**
 * Descri��o da classe
 * 
 * @author R�mulo Aur�lio
 * @date 09/03/2007
 */
public class InserirTipoDebitoAction extends GcomAction {
	/**	 * Este caso de uso permite a inclus�o de um novo Tipo de D�bito	 * 	 * [UC0529] Inserir Tipo de D�bito	 * 	 * 	 * @author R�mulo Aur�lio	 * @date 09/03/2007	 * 	 * @param actionMapping	 * @param actionForm	 * @param httpServletRequest	 * @param httpServletResponse	 * @return	 */
	public ActionForward execute(ActionMapping actionMapping,			ActionForm actionForm, HttpServletRequest httpServletRequest,			HttpServletResponse httpServletResponse) {
		ActionForward retorno = actionMapping.findForward("telaSucesso");
		HttpSession sessao = httpServletRequest.getSession(false);
		Usuario usuarioLogado = (Usuario) sessao				.getAttribute(Usuario.USUARIO_LOGADO);
		InserirTipoDebitoActionForm form = (InserirTipoDebitoActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();
		String descricao = form.getDescricao();
		String descricaoAbreviada = form.getDescricaoAbreviada();
		String idTipoFinanciamento = form.getFinanciamentoTipo();				String valorSugerido = form.getValorSugerido();
		String indicadorGeracaoDebitoAutomatica = form				.getIndicadorGeracaoDebitoAutomatica();
		String indicadorGeracaoDebitoConta = form				.getIndicadorGeracaoDebitoConta();
		String idLancamentoItemContabil = form.getLancamentoItemContabil();
		String valorLimeteDebito = form.getValorLimiteDebito();				String indicadorDebitoCartaoCredito = form.getIndicadorDebitoCartaoCredito();				String indicadorJurosParCliente = form.getIndicadorJurosParCliente();
		Integer idDebitoTipo = (Integer) fachada.inserirDebitoTipo(descricao,				descricaoAbreviada, idTipoFinanciamento,				indicadorGeracaoDebitoAutomatica, indicadorGeracaoDebitoConta,				idLancamentoItemContabil, valorLimeteDebito, usuarioLogado, valorSugerido, 				indicadorDebitoCartaoCredito, indicadorJurosParCliente);
		montarPaginaSucesso(httpServletRequest, "Tipo de D�bito de c�digo "				+ idDebitoTipo + " inserido com sucesso.",				"Inserir outro Tipo de D�bito",				"exibirInserirTipoDebitoAction.do?menu=sim"		/*		 * ,"exibirAtualizarFuncionalidadeAction.do?idFuncionalidade=" +		 * idFuncionalidade , "Atualizar Funcionalidade inserida"		 */);
		return retorno;
	}
}
