package gcom.gui.cadastro.tarifasocial;

import gcom.atendimentopublico.registroatendimento.RegistroAtendimento;
import gcom.cadastro.cliente.ClienteImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.ImovelPerfil;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Prepara a p�gina para a exibi��o de Inserir Tarifa Social
 * 
 * @author rodrigo
 */
public class ExibirManterTarifaSocialImovelAction extends GcomAction {

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

		ActionForward retorno = actionMapping
				.findForward("manterTarifaSocialImovel");

		// Pega uma instancia da sessao
		HttpSession sessao = httpServletRequest.getSession(false);

		// Pega uma instancia do actionform
		ManterTarifaSocialActionForm manterTarifaSocialActionForm = (ManterTarifaSocialActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();		
		
		String idImovel = manterTarifaSocialActionForm.getIdImovel();

		String idRegistroAtendimento = manterTarifaSocialActionForm.getRegistroAtendimento();
		
		// Retira da sess�o para n�o ficar com a tela suja
		sessao.removeAttribute("clienteImovel");
		sessao.removeAttribute("quantEconomias");
		
		// pesquisa o imovel pelo registro de atendimento
		if (idRegistroAtendimento != null
				&& !idRegistroAtendimento.trim().equals("")) {
			
			
			RegistroAtendimento registroAtendimento = fachada
					.verificarRegistroAtendimentoManterTarifaSocial(idRegistroAtendimento);
			
  		    if (registroAtendimento != null) {

				// Registro de Atendimento n�o est� associado a um im�vel
				if (registroAtendimento.getImovel() == null) {
					
					manterTarifaSocialActionForm.setNomeRegistroAtendimento("");
					manterTarifaSocialActionForm.setIdImovel("");
					manterTarifaSocialActionForm.setInscricaoImovel("");
					
					// FS0001 - Validar Registro de Atendimento
					throw new ActionServletException(
							"atencao.registro_atendimento.nao.associado.imovel");
				}

				// Registro de Atendimento est� encerrado
				if (registroAtendimento.getAtendimentoMotivoEncerramento() != null) {
					
					manterTarifaSocialActionForm.setNomeRegistroAtendimento("");
					manterTarifaSocialActionForm.setIdImovel("");
					manterTarifaSocialActionForm.setInscricaoImovel("");
					
					// FS0001 - Validar Registro de Atendimento
					throw new ActionServletException(
							"atencao.registro_atendimento.esta.encerrado");
				}

				// Tipo de Solicita��o do registro de atendimento n�o permite a inclus�o na tarifa social
				if (registroAtendimento.getSolicitacaoTipoEspecificacao().getSolicitacaoTipo()
						.getIndicadorTarifaSocial() == 2) {
					
					manterTarifaSocialActionForm.setNomeRegistroAtendimento("");
					manterTarifaSocialActionForm.setIdImovel("");
					manterTarifaSocialActionForm.setInscricaoImovel("");
					
					// FS0001 - Validar Registro de Atendimento
					throw new ActionServletException(
							"atencao.registro_atendimento.nao.permite.manutencao.tarifa_social");
				}

				// caso tenha o imovel
				idImovel = registroAtendimento.getImovel().getId().toString();
				
				manterTarifaSocialActionForm
						.setRegistroAtendimento(registroAtendimento.getId()
								.toString());
				manterTarifaSocialActionForm
						.setNomeRegistroAtendimento(registroAtendimento
								.getSolicitacaoTipoEspecificacao()
								.getDescricao());
				
				manterTarifaSocialActionForm.setIdImovel(idImovel);
				
				sessao.setAttribute("ra", registroAtendimento);
				
				httpServletRequest.setAttribute("corRegistroAtendimento","valor");
				httpServletRequest.setAttribute("nomeCampo", "registroAtendimento");

			} else {
				
				manterTarifaSocialActionForm.setIdImovel("");
				
				sessao.removeAttribute("clienteImovel");
				sessao.removeAttribute("quantEconomias");				
				
				// FS0001-Validar Registro de Atendimento
				
				manterTarifaSocialActionForm.setRegistroAtendimento("");
				manterTarifaSocialActionForm.setNomeRegistroAtendimento("RA inexistente");
				httpServletRequest.setAttribute("corRegistroAtendimento",
						"exception");
				httpServletRequest.setAttribute("nomeCampo",
						"registroAtendimento");
			}
		}

		// Verifica se foi feita uma pesquisa de imovel que retornou para este
		// exibir
		if (idImovel != null  && !idImovel.equals("")) {

			Collection clientesImoveis = fachada.pesquisarClienteImovelPeloImovelParaEndereco(
					new Integer(idImovel));

			if (!clientesImoveis.isEmpty()) {
				ClienteImovel clienteImovel = (ClienteImovel) ((List) clientesImoveis)
						.get(0);
				
				Imovel imovel = clienteImovel.getImovel();

				if (imovel.getImovelPerfil().getId().intValue() != ImovelPerfil.TARIFA_SOCIAL.intValue()){
					
					// FS0002 - Verificar im�vel na tarifa social
					if (idRegistroAtendimento != null && !idRegistroAtendimento.trim().equals("")) {
						
						manterTarifaSocialActionForm.setNomeRegistroAtendimento("");
						manterTarifaSocialActionForm.setIdImovel("");
						manterTarifaSocialActionForm.setInscricaoImovel("");
						
						throw new ActionServletException(
								"atencao.imovel.associado.registro_atendimento.nao.esta.tarifa_social",
								null, imovel.getId().toString());
					} else {
						
						manterTarifaSocialActionForm.setNomeRegistroAtendimento("");
						manterTarifaSocialActionForm.setIdImovel("");
						manterTarifaSocialActionForm.setInscricaoImovel("");
						
						throw new ActionServletException(
								"atencao.imovel.nao.esta.tarifa_social",
								null, imovel.getId().toString());
					}
				}
				
				manterTarifaSocialActionForm.setInscricaoImovel(imovel.getInscricaoFormatada());
				
				// Obter a quantidade de economias do im�vel escolhido
				int quantEconomias = Fachada.getInstancia()
						.obterQuantidadeEconomias(imovel);

				// Seta na sess�o
				sessao.setAttribute("clienteImovel", clienteImovel);
				sessao.setAttribute("quantEconomias", String
						.valueOf(quantEconomias));
			} else {
				// Matr�cula inexistente
				httpServletRequest.setAttribute("imovelNaoEncontrado", true);
				manterTarifaSocialActionForm.setIdImovel("");
				manterTarifaSocialActionForm.setInscricaoImovel("Im�vel Inexistente");
				httpServletRequest.setAttribute("nomeCampo", "idImovel");
			}
		}

		return retorno;
	}

}
