package gcom.gui.operacional;

import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

/**
 * Action que define o pr�-processamento da p�gina de pesquisa de Setor Comercial
 * 
 * @author Fl�vio
 */
public class ExibirPesquisarFonteCaptacaoAction extends GcomAction {

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
                .findForward("pesquisarFonteCaptacao");

        //obtendo uma instancia da sessao
        HttpSession sessao = httpServletRequest.getSession(false);

        DynaValidatorForm pesquisarActionForm = (DynaValidatorForm) actionForm;
        
        if(httpServletRequest.getParameter("objetoConsulta") == null){
	        pesquisarActionForm.set("codigoFonteCaptacao", "");
	        pesquisarActionForm.set("descricaoFonteCaptacao", "");
	        pesquisarActionForm.set("descricaoAbreviadaFonteCaptacao", "");
        }

        String tipo = httpServletRequest.getParameter("tipo");
        String idTipoCaptacao = httpServletRequest.getParameter("idTipoCaptacao");

        if (tipo != null && !tipo.trim().equalsIgnoreCase("")) {
            sessao.setAttribute("tipoPesquisa", tipo.trim());
        }
        if(idTipoCaptacao != null && !idTipoCaptacao.trim().equalsIgnoreCase("")){
        	sessao.setAttribute("idTipoCaptacao", idTipoCaptacao);
        }
        
        if( httpServletRequest.getParameter("indicadorUsoTodos") != null )
        {
        	sessao.setAttribute("indicadorUsoTodos",
				httpServletRequest.getParameter("indicadorUsoTodos"));
        }
        else
        {
        	sessao.removeAttribute("indicadorUsoTodos");
        }
        
        
        
        // Verifica se o pesquisar tipo captacao foi chamado a partir do inserir tipo captacao
		// e em caso afirmativo recebe o par�metro e manda-o pela sess�o para
		// ser verificado no tipo_captacao_resultado_pesquisa e depois retirado da
		// sess�o no ExibirFiltrarTipoCaptacaoAction
		if (httpServletRequest.getParameter("consulta") != null) {
			String consulta = httpServletRequest.getParameter("consulta");
			sessao.setAttribute("consulta", consulta);
		}
        
        //envia uma flag que ser� verificado no
        // tipo_captacao_resultado_pesquisa.jsp
        //para saber se ir� usar o enviar dados ou o enviar dados parametros
        if(httpServletRequest.getParameter("caminhoRetornoTelaPesquisa") != null && !"".equals(httpServletRequest.getParameter("caminhoRetornoTelaPesquisa")) ){
        	sessao.setAttribute("caminhoRetornoTelaPesquisaFonteCaptacao", httpServletRequest
                .getParameter("caminhoRetornoTelaPesquisa"));
        }else{
        	sessao.removeAttribute("caminhoRetornoTelaPesquisaFonteCaptacao");
        }
        		
        if(httpServletRequest.getParameter("Popup") != null){
        	sessao.setAttribute("Popup", httpServletRequest
                .getParameter("Popup"));
        }else{
        	sessao.removeAttribute("Popup");
        }
        
        
        if (pesquisarActionForm.get("tipoPesquisaDescricao") == null
				|| pesquisarActionForm.get("tipoPesquisaDescricao").equals("")) {

			pesquisarActionForm.set("tipoPesquisaDescricao",
					ConstantesSistema.TIPO_PESQUISA_INICIAL.toString());

		}

        return retorno;
    }

}
