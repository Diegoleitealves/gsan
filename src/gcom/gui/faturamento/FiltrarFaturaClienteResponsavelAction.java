package gcom.gui.faturamento;

import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.FiltroCliente;
import gcom.fachada.Fachada;
import gcom.faturamento.conta.FiltroFaturaItem;
import gcom.gui.GcomAction;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 
 * @author Fl�vio Leonardo
 * 24/11/2008
 *
 */

public class FiltrarFaturaClienteResponsavelAction extends GcomAction {

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
	@SuppressWarnings("static-access")
	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping
				.findForward("exibirAtualizarFaturaClienteResponsavel");

		FiltrarFaturaClienteResponsavelActionForm form = (FiltrarFaturaClienteResponsavelActionForm) actionForm;
		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		// Variaveis
		String indicadorAtualizar = httpServletRequest
		.getParameter("indicadorAtualizar");
		
		if (indicadorAtualizar == null) {
			form.setIndicadorAtualizar("2");
		} else {
			form.setIndicadorAtualizar(indicadorAtualizar);
		}

		FiltroFaturaItem filtroFaturaItem = new FiltroFaturaItem();
		
		Cliente cliente = pesquisarCliente(form.getClienteId());
    	
    	if(cliente != null){
    		filtroFaturaItem.adicionarParametro(new ParametroSimples(FiltroFaturaItem.CLIENTE_ID, cliente.getId() ));
    	}
    	
    	if(form.getMesAno() != null && !form.getMesAno().equals("")){
    		filtroFaturaItem.adicionarParametro(new ParametroSimples(filtroFaturaItem.ANO_MES_REFERENCIA, 
    				Util.formatarMesAnoComBarraParaAnoMes(form.getMesAno())));
    	}
    	
    	filtroFaturaItem.setCampoOrderBy(FiltroFaturaItem.ANO_MES_REFERENCIA);
    	filtroFaturaItem.adicionarCaminhoParaCarregamentoEntidade("imovel");
    	filtroFaturaItem.adicionarCaminhoParaCarregamentoEntidade("fatura");
    	filtroFaturaItem.adicionarCaminhoParaCarregamentoEntidade("contaGeral.conta");
    	filtroFaturaItem.adicionarCaminhoParaCarregamentoEntidade("contaGeral.contaHistorico");

		sessao.setAttribute("filtroFaturaItem",
				filtroFaturaItem);

		return retorno;
	}
	
	//pesquisa de cliente
    private Cliente pesquisarCliente(String idCliente){
		
		//Cria a vari�vel que vai armazenar o cliente pesquisado
		Cliente clienteEncontrado = null;
		
		//Cria uma inst�ncia da fachada 
		Fachada fachada = Fachada.getInstancia();
		
		//Pesquisa o cliente informado pelo usu�rio no sistema 
		FiltroCliente filtroCliente = new FiltroCliente();
		filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, idCliente));
		Collection<Cliente> colecaoCliente =  fachada.pesquisar(filtroCliente, Cliente.class.getName());
		
		//Caso exista o cliente no sistema 
		//Retorna para o usu�rio o cliente retornado pela pesquisa
		//Caso contr�rio retorna um objeto nulo 
		if(colecaoCliente != null && !colecaoCliente.isEmpty()){
			clienteEncontrado =(Cliente) Util.retonarObjetoDeColecao(colecaoCliente);
		}
		
		//Retorna o cliente encontrado ou nulo se n�o existir 
		return clienteEncontrado;
	}

}
