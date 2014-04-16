package gcom.gui.faturamento.credito;

import gcom.cadastro.imovel.FiltroImovelCobrancaSituacao;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.ImovelCobrancaSituacao;
import gcom.cobranca.CobrancaSituacao;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC0194] Cr�dito a Realizar
 * Permite cancelar um ou mais cr�ditos a realizar de um determinado im�vel
 * @author Roberta Costa
 * @since  11/01/2006 
 */
public class ManterCreditoARealizarAction extends GcomAction {

    
    public ActionForward execute(ActionMapping actionMapping,
            ActionForm actionForm, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
    	
        // Seta o mapeamento de retorno
        ActionForward retorno = actionMapping.findForward("telaSucesso");
        
        // Pega uma inst�ncia da fachada
        Fachada fachada = Fachada.getInstancia();

        // Mudar isso quando tiver esquema de seguran�a
        HttpSession sessao = httpServletRequest.getSession(false);

        Usuario usuarioLogado = (Usuario)sessao.getAttribute(Usuario.USUARIO_LOGADO);
        
        // Inst�ncia do formul�rio que est� sendo utilizado
        ManterCreditoARealizarActionForm manterCreditoARealizarActionForm = (ManterCreditoARealizarActionForm) actionForm;

        // Pega o Im�vel da sess�o
        Imovel imovel = null;
        if ( sessao.getAttribute("imovelPesquisado") != null ){
        	imovel = (Imovel)sessao.getAttribute("imovelPesquisado");
        }

        // Obt�m os ids de remo��o
        String[] ids = manterCreditoARealizarActionForm.getIdCreditoARealizar();
        
        //mensagem de erro quando o usu�rio tenta excluir sem ter selecionado nenhum registro
        if (ids == null || ids.length == 0) {
            throw new ActionServletException("atencao.registros.nao_selecionados");
        }        
        
		// [FS0003 - Verificar usu�rio com d�bito em cobran�a administrativa
		if (imovel != null) {
			FiltroImovelCobrancaSituacao filtroImovelCobrancaSituacao = new FiltroImovelCobrancaSituacao();

			filtroImovelCobrancaSituacao
					.adicionarCaminhoParaCarregamentoEntidade("cobrancaSituacao");
			filtroImovelCobrancaSituacao
					.adicionarParametro(new ParametroSimples(
							FiltroImovelCobrancaSituacao.IMOVEL_ID, imovel
									.getId()));

			Collection imovelCobrancaSituacaoEncontrada = fachada
					.pesquisar(filtroImovelCobrancaSituacao,
							ImovelCobrancaSituacao.class.getName());

			// Verifica se o im�vel tem d�bito em cobran�a administrativa
			if (imovelCobrancaSituacaoEncontrada != null
					&& !imovelCobrancaSituacaoEncontrada.isEmpty()) {
				
				if (((ImovelCobrancaSituacao) ((List) imovelCobrancaSituacaoEncontrada)
						.get(0)).getCobrancaSituacao() != null) {
					
					if (((ImovelCobrancaSituacao) ((List) imovelCobrancaSituacaoEncontrada)
							.get(0)).getCobrancaSituacao().getId().equals(
							CobrancaSituacao.COBRANCA_ADMINISTRATIVA) && ((ImovelCobrancaSituacao) ((List) imovelCobrancaSituacaoEncontrada)
									.get(0)).getDataRetiradaCobranca() == null) {
						
						throw new ActionServletException(
								"atencao.pesquisa.imovel.cobranca_administrativa");
					}
				}
			}
		}

        fachada.cancelarCreditoARealizar(ids, imovel, usuarioLogado);

        //Monta a p�gina de sucesso
        if (retorno.getName().equalsIgnoreCase("telaSucesso")) {
            montarPaginaSucesso(httpServletRequest,
            		ids.length + " Cr�dito(s) a Realizar(s) do Im�vel " + imovel.getId() + " cancelado(s) com sucesso.",
                    "Cancelar outro Cr�dito a Realizar",
                    "exibirManterCreditoARealizarAction.do");
        }

		return retorno;
	}
}
