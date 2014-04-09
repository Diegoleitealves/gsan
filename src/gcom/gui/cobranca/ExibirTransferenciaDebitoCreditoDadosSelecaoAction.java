package gcom.gui.cobranca;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import gcom.cobranca.bean.ObterDebitoImovelOuClienteHelper;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;


/**
 * Esta classe tem por finalidade exibir para o usu�rio a tela que exibir�
 * as contas, d�bitos, cr�ditos e guias para sele��o e posteriormente 
 * realiza��o da transfer�ncia dos selecionados
 * 
 * @author Raphael Rossiter
 * @date 08/06/2007
 */
public class ExibirTransferenciaDebitoCreditoDadosSelecaoAction extends GcomAction {
	
	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		ActionForward retorno = actionMapping
		.findForward("transferenciaDebitoCreditoDadosSelecao");

		TransferenciaDebitoCreditoDadosImovelActionForm form = 
		(TransferenciaDebitoCreditoDadosImovelActionForm) actionForm;

		form.setIndicadorEmissao("true");
		form.setIndicadorTipoEmissao("novoDevedor");

		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();
		
		Integer idRa = new Integer(form.getIdRegistroAtendimento());
		Integer idImovelDestino = new Integer(form.getIdImovelDestino());
		
		// Valida��o dos dados informados referentes aos im�veis de origem e destino
		Integer idImovelOrigem = fachada.validarTransferenciaDebitoCreditoDadosImoveis(idRa, idImovelDestino);
		
		//[SB0001] - Apresentar D�bitos/Cr�ditos do Im�vel de Origem
		ObterDebitoImovelOuClienteHelper obterDebitoImovelOuClienteHelper = 
		fachada.apresentarDebitoCreditoImovelOrigem(idImovelOrigem);
		
		// CONTA
		sessao.setAttribute("colecaoConta", obterDebitoImovelOuClienteHelper.getColecaoContasValoresImovel());
		
		// DEBITO_A_COBRAR
		sessao.setAttribute("colecaoDebitoACobrar", obterDebitoImovelOuClienteHelper.getColecaoDebitoACobrar());
		
		// CREDITO_A_REALIZAR
		sessao.setAttribute("colecaoCreditoARealizar", obterDebitoImovelOuClienteHelper.getColecaoCreditoARealizar());
		
		// GUIA_PAGAMENTO
		sessao.setAttribute("colecaoGuiaPagamento", obterDebitoImovelOuClienteHelper.getColecaoGuiasPagamentoValores());
		
		//Declara��o de tranferencia
		sessao.setAttribute("indicadorEmissao", null);
				
		return retorno;
	}

}
