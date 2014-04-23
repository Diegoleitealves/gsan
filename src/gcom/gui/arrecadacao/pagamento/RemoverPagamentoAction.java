package gcom.gui.arrecadacao.pagamento;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action respons�vel por remover pagamentos do sistema 
 *
 * @author Pedro Alexandre
 * @date 21/03/2006
 */
public class RemoverPagamentoAction extends GcomAction {

    /**
     * <Breve descri��o sobre o caso de uso>
     *
     * <Identificador e nome do caso de uso>
     *
     * <Breve descri��o sobre o subfluxo>
     *
     * <Identificador e nome do subfluxo>	
     *
     * <Breve descri��o sobre o fluxo secund�rio>
     *
     * <Identificador e nome do fluxo secund�rio> 
     *
     * @author Pedro Alexandre
     * @date 21/03/2006
     *
     * @param actionMapping
     * @param actionForm
     * @param httpServletRequest
     * @param httpServletResponse
     * @return
     */
    public ActionForward execute(ActionMapping actionMapping,
            					 ActionForm actionForm, 
            					 HttpServletRequest httpServletRequest,
            					 HttpServletResponse httpServletResponse) {

    	//Recupera o form de manter pagamentos
        ManterPagamentoActionForm manterPagamentoActionForm = (ManterPagamentoActionForm) actionForm;

        //Recupera do form oarray comos c�digo de pagamentos selecionados para exclus�o
        String[] idsPagamentos = manterPagamentoActionForm.getIdRegistrosRemocao();

        //Seta o mapeamento de retorno para a tela de sucesso
        ActionForward retorno = actionMapping.findForward("telaSucesso");

        //Cria uma inst�ncia da fachada
        Fachada fachada = Fachada.getInstancia();

        HttpSession sessao = httpServletRequest.getSession(false);

        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");
        
        //Caso o usu�rio n�o tenha selecionado nenhum pagamento pra exclus�o
        //Levanta uma exce��o para o usu�rio indicando que nenhum registro foi selecionado
        //Caso contr�rio chama o met�do de remover pagamentos da fachada
        if (idsPagamentos == null || idsPagamentos.length == 0) {
            throw new ActionServletException("atencao.registros.nao_selecionados");
        }else{
        	fachada.removerPagamentos(idsPagamentos,usuarioLogado);	
        }

        //Monta a p�gina de sucesso
        if (retorno.getName().equalsIgnoreCase("telaSucesso")) {
            montarPaginaSucesso(httpServletRequest, idsPagamentos.length +
                    " Pagamento(s) removido(s) com sucesso.",
                    "Realizar outra Manuten��o de Pagamento",
                    "exibirFiltrarPagamentoAction.do?tela=manterPagamento&menu=sim");
        }

        //Retorna o mapeamento contido na vari�vel retorno
        return retorno;
    }
}
