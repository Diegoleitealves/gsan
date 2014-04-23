package gcom.gui.batch;

import gcom.batch.FiltroProcesso;
import gcom.batch.FiltroProcessoSituacao;
import gcom.batch.Processo;
import gcom.batch.ProcessoSituacao;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.FiltroUsuario;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action respons�vel pela pre-exibi��o da pagina de filtrar processo iniciado
 * 
 * @author Rodrigo Silveira
 * @created 22/07/2006
 */
public class ExibirFiltrarProcessoAction extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("filtrarProcesso");

		// Pesquisa as situa��es do processos para o select da p�gina
		this.pesquisarProcessoSituacao(httpServletRequest);

		this.resetForm((FiltrarProcessoActionForm) actionForm);
		
		// Pesquisa o Processo digitado
		this.pesquisarProcessoDigitado(httpServletRequest,
				(FiltrarProcessoActionForm) actionForm);
		
		this.pesquisarUsuarioDigitado(httpServletRequest,
				(FiltrarProcessoActionForm) actionForm);

		return retorno;
	}

	/**
	 * Pesquisa todas as Situa��es do Processo para popular o select da p�gina
	 * de inserir processo
	 * 
	 * @author Rodrigo Silveira
	 * @date 22/07/2006
	 * 
	 * @param httpServletRequest
	 */
	private void pesquisarProcessoSituacao(HttpServletRequest httpServletRequest) {
		FiltroProcessoSituacao filtroProcessoSituacao = new FiltroProcessoSituacao();
		Collection<ProcessoSituacao> colecao = Fachada.getInstancia()
				.pesquisar(filtroProcessoSituacao,
						ProcessoSituacao.class.getName());
		httpServletRequest.setAttribute("colecaoProcessoSituacao", colecao);

	}

	/**
	 * Pesquisa todas as Situa��es do Processo para popular o select da p�gina
	 * de inserir processo
	 * 
	 * @author Rodrigo Silveira
	 * @date 15/03/2007
	 * 
	 * @param httpServletRequest
	 */

	private void resetForm(FiltrarProcessoActionForm form) {

		String idDigitadoEnterProcesso = (String) form.getIdProcesso();

		if (idDigitadoEnterProcesso == null
				|| idDigitadoEnterProcesso.trim().equals("")) {
			form.resetFormCustom();
		}

	}

	/**
	 * Fun��o que pesquisa um Processo iniciado precedente digitado
	 * 
	 * @author Rodrigo Silveira
	 * @date 24/07/2006
	 * 
	 * @param httpServletRequest
	 * @param actionForm
	 */
	private void pesquisarProcessoDigitado(
			HttpServletRequest httpServletRequest,
			FiltrarProcessoActionForm actionForm) {

		String idDigitadoEnterProcesso = (String) actionForm.getIdProcesso();

		actionForm.setDescricaoProcesso("");
		actionForm.setIdProcesso("");

		// Verifica se o c�digo foi digitado
		if (idDigitadoEnterProcesso != null
				&& !idDigitadoEnterProcesso.trim().equals("")
				&& Integer.parseInt(idDigitadoEnterProcesso) > 0
				&& !idDigitadoEnterProcesso.trim().equals(
						actionForm.getIdProcesso())) {
			Fachada fachada = Fachada.getInstancia();

			// Este trecho pesquisa o processo digitado e altera o form para
			// refletir o resultado da busca na p�gina de filtrar processo

			FiltroProcesso filtroProcesso = new FiltroProcesso();

			filtroProcesso.adicionarParametro(new ParametroSimples(
					FiltroProcesso.ID, idDigitadoEnterProcesso));

			Collection processos = fachada.pesquisar(filtroProcesso,
					Processo.class.getName());

			if (processos != null && !processos.isEmpty()) {
				// O processo foi encontrado
				actionForm.setIdProcesso(((Processo) ((List) processos).get(0))
						.getId().toString());
				actionForm.setDescricaoProcesso(((Processo) ((List) processos)
						.get(0)).getDescricaoProcesso());

			} else {
				actionForm.setIdProcesso("");
				httpServletRequest.setAttribute("idProcessoNaoEncontrado",
						"true");
				actionForm.setDescricaoProcesso("Processo inexistente");

			}
		}

	}
	
	//CRC-1466 Fl�vio Leonardo 17/03/09
	private void pesquisarUsuarioDigitado(HttpServletRequest httpServletRequest,
			FiltrarProcessoActionForm actionForm){
		
		String idDigitadoEnterUsuario = (String) actionForm.getUsuarioId();

		actionForm.setUsuarioDescricao("");
		actionForm.setUsuarioId("");
		
		if(idDigitadoEnterUsuario != null
		&& !idDigitadoEnterUsuario.trim().equals("")
		&& !idDigitadoEnterUsuario.trim().equals(
				actionForm.getIdProcesso())) {
			
			Fachada fachada = Fachada.getInstancia();
		
			FiltroUsuario filtroUsuario = new FiltroUsuario();
			
			filtroUsuario.adicionarParametro(new ParametroSimples(FiltroUsuario.ID, idDigitadoEnterUsuario));
			
			Collection colecaoUsuario = fachada.pesquisar(filtroUsuario, Usuario.class.getName());
			
			if(!colecaoUsuario.isEmpty()){
				Usuario usuario = (Usuario)Util.retonarObjetoDeColecao(colecaoUsuario);
				
				actionForm.setUsuarioId(usuario.getId().toString());
				actionForm.setUsuarioDescricao(usuario.getNomeUsuario());
			}else{
				
				httpServletRequest.setAttribute("idUsuarioNaoEncontrado",
						"true");
				actionForm.setUsuarioDescricao("Usu�rio inexistente");
				actionForm.setUsuarioId("");
			}
			
		}
		
	}

}
