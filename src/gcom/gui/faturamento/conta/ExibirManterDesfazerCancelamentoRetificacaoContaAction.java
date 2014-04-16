package gcom.gui.faturamento.conta;

import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.imovel.Imovel;
import gcom.fachada.Fachada;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.conta.FiltroConta;
import gcom.faturamento.debito.DebitoCreditoSituacao;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action respons�vel pela exibi��o dos dados na tela do 
 * 	desfazer cancelamento e/ou retifica��o da conta. 
 * 
 * [UC0327] Desfazer Cancelamento e/ou Retifica��o de Conta 
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
public class ExibirManterDesfazerCancelamentoRetificacaoContaAction extends
		GcomAction {
	
	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		ActionForward retorno = actionMapping
				.findForward("desfazerCancelamentoRetificacaoConta");

		DesfazerCancelamentoRetificacaoContaActionForm desfazerCancelamentoRetificacaoContaActionForm = (DesfazerCancelamentoRetificacaoContaActionForm) actionForm;

		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();

		// -------Parte que trata do c�digo quando o usu�rio tecla enter
		// Matr�cula do Im�vel
		String codigoDigitadoImovelEnter = (String) desfazerCancelamentoRetificacaoContaActionForm
				.getIdImovel();

		// Se o c�digo do im�vel tiver sido digitado seta no form os dados do im�vel
		if (codigoDigitadoImovelEnter != null
				&& !codigoDigitadoImovelEnter.trim().equals("")
				&& Integer.parseInt(codigoDigitadoImovelEnter) > 0) {

			Imovel imovel = fachada.pesquisarImovelRegistroAtendimento(new Integer(codigoDigitadoImovelEnter));
			
			if (imovel != null) {

				// O imovel foi encontrado
				desfazerCancelamentoRetificacaoContaActionForm.setIdImovel(""
						+ imovel.getId());

				desfazerCancelamentoRetificacaoContaActionForm
						.setInscricaoImovel(imovel.getInscricaoFormatada());

				desfazerCancelamentoRetificacaoContaActionForm
						.setSituacaoAgua(imovel.getLigacaoAguaSituacao()
								.getDescricao());

				desfazerCancelamentoRetificacaoContaActionForm
						.setSituacaoEsgoto(imovel.getLigacaoEsgotoSituacao()
								.getDescricao());

				
				Cliente cliente = fachada.pesquisarClienteUsuarioImovel(new Integer(codigoDigitadoImovelEnter));

				// Manda os dados do cliente para a p�gina
				if (cliente != null) {
					desfazerCancelamentoRetificacaoContaActionForm.setNomeClienteUsuario(cliente
							.getNome());
				}
				

				Collection contas = fachada.obterContasImovelManter(imovel, DebitoCreditoSituacao.CANCELADA,
						DebitoCreditoSituacao.CANCELADA, DebitoCreditoSituacao.CANCELADA_POR_RETIFICACAO);

				/**
				 * S� sera enviada contas que ta na situa��o retificada ou cancelada
				 * Alterado por Arthur Carvalho
				 * Analista Eduardo Rosa
				 * Data:14/05/2010
				 */
				Iterator iteratorContas = contas.iterator();
				Collection colecaoContas = new ArrayList();
				while ( iteratorContas.hasNext() ) {
					
					Conta conta = (Conta) iteratorContas.next();
					//RETIFICADA
					if ( conta.getDebitoCreditoSituacaoAtual().getId().equals(
							DebitoCreditoSituacao.CANCELADA_POR_RETIFICACAO ) ) {
					
						FiltroConta filtroConta = new FiltroConta();
						filtroConta.adicionarParametro( new ParametroSimples( FiltroConta.REFERENCIA, 
								conta.getReferencia()));
						filtroConta.adicionarParametro( new ParametroSimples( FiltroConta.IMOVEL_ID, 
								conta.getImovel().getId()));
						filtroConta.adicionarParametro( new ParametroSimples( FiltroConta.DEBITO_CREDITO_SITUACAO_ATUAL,
								DebitoCreditoSituacao.RETIFICADA ));
						
						Collection colecaoConta = fachada.pesquisar(filtroConta, Conta.class.getName() ) ;
						
						if( colecaoConta != null && !colecaoConta.isEmpty() ) {
							
							colecaoContas.add(conta);
						}
						//CANCELADA
					} else if ( conta.getDebitoCreditoSituacaoAtual().getId().equals(
							DebitoCreditoSituacao.CANCELADA ) ) {
						
						FiltroConta filtroConta = new FiltroConta();
						filtroConta.adicionarParametro( new ParametroSimples( FiltroConta.REFERENCIA, 
								conta.getReferencia()));
						filtroConta.adicionarParametro( new ParametroSimples( FiltroConta.IMOVEL_ID, 
								conta.getImovel().getId()));
						filtroConta.adicionarParametro( new ParametroSimples( FiltroConta.DEBITO_CREDITO_SITUACAO_ATUAL,
								DebitoCreditoSituacao.CANCELADA ));
						
						Collection colecaoConta = fachada.pesquisar(filtroConta, Conta.class.getName() ) ;
						
						if( colecaoConta != null && !colecaoConta.isEmpty() ) {
							
							colecaoContas.add(conta);
						}
					}
					
				}
				// Manda os dados da conta para a p�gina
				if (colecaoContas != null && !colecaoContas.isEmpty()) {
					sessao.setAttribute("contas", colecaoContas);
				}
				else
				{
					throw new ActionServletException(
							"atencao.pesquisa.nenhuma.conta_cancelada_retificada_imovel", null, ""
									+ codigoDigitadoImovelEnter);
				}
			} else {
				httpServletRequest.setAttribute("corImovel", "exception");
				desfazerCancelamentoRetificacaoContaActionForm
						.setInscricaoImovel(ConstantesSistema.CODIGO_IMOVEL_INEXISTENTE);
				desfazerCancelamentoRetificacaoContaActionForm
						.setIdImovel("");
				desfazerCancelamentoRetificacaoContaActionForm
						.setNomeClienteUsuario("");
				desfazerCancelamentoRetificacaoContaActionForm
						.setSituacaoAgua("");
				desfazerCancelamentoRetificacaoContaActionForm
						.setSituacaoEsgoto("");
				sessao.removeAttribute("contas");
			}
		}
		return retorno;
	}
}
