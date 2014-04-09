package gcom.gui.relatorio.seguranca;

import gcom.gui.seguranca.FiltrarUsuarioTipoActionForm;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.seguranca.RelatorioManterUsuarioTipo;
import gcom.seguranca.acesso.usuario.FiltroUsuarioTipo;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioTipo;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.SistemaException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <p>
 * Title: GCOM
 * </p>
 * <p>
 * Description: Sistema de Gest�o Comercial
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: COMPESA - Companhia Pernambucana de Saneamento
 * </p>
 * 
 * @author Arthur Carvalho
 * @version 1.0
 */

public class GerarRelatorioUsuarioTipoManterAction extends
		ExibidorProcessamentoTarefaRelatorio {

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

		// cria a vari�vel de retorno
		ActionForward retorno = null;

		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		FiltrarUsuarioTipoActionForm filtrarUsuarioTipoActionForm = (FiltrarUsuarioTipoActionForm) actionForm;

		FiltroUsuarioTipo filtroUsuarioTipo = (FiltroUsuarioTipo) sessao
				.getAttribute("filtroUsuarioTipo");

		// Inicio da parte que vai mandar os parametros para o relat�rio

		UsuarioTipo usuarioTipoParametros = new UsuarioTipo();

		String id = null;

		String idUsuarioTipoPesquisar = (String) filtrarUsuarioTipoActionForm.getId();

		if (idUsuarioTipoPesquisar != null && !idUsuarioTipoPesquisar.equals("")) {
			id = idUsuarioTipoPesquisar;
		}
		
		Short indicadorUso = null;
		
		if(filtrarUsuarioTipoActionForm.getIndicadorUso()!= null && !filtrarUsuarioTipoActionForm.getIndicadorUso().equals("")){
			
			indicadorUso = new Short ("" + filtrarUsuarioTipoActionForm.getIndicadorUso());
		}
		
		Short indicadorFuncionario = null;
		
		if(filtrarUsuarioTipoActionForm.getIndicadorFuncionario() != null && !filtrarUsuarioTipoActionForm.getIndicadorFuncionario().equals("")){
			
			indicadorFuncionario = new Short ("" + filtrarUsuarioTipoActionForm.getIndicadorFuncionario());
		}
		
		// seta os parametros que ser�o mostrados no relat�rio

		usuarioTipoParametros.setId(id == null ? null : new Integer(
				id));
		usuarioTipoParametros.setDescricao(filtrarUsuarioTipoActionForm.getDescricao());
		usuarioTipoParametros.setIndicadorUso(indicadorUso);
		usuarioTipoParametros.setIndicadorFuncionario(indicadorFuncionario);
		// Fim da parte que vai mandar os parametros para o relat�rio

		// cria uma inst�ncia da classe do relat�rio
	
		RelatorioManterUsuarioTipo relatorioManterUsuarioTipo = new RelatorioManterUsuarioTipo(
				(Usuario)(httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));
		relatorioManterUsuarioTipo.addParametro("filtroUsuarioTipo",
				filtroUsuarioTipo);
		relatorioManterUsuarioTipo.addParametro("usuarioTipoParametros",
				usuarioTipoParametros);

		// chama o met�do de gerar relat�rio passando o c�digo da analise
		// como par�metro
		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");
		if (tipoRelatorio == null) {
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		relatorioManterUsuarioTipo.addParametro("tipoFormatoRelatorio", Integer
				.parseInt(tipoRelatorio));
		try {
			retorno = processarExibicaoRelatorio(relatorioManterUsuarioTipo,
					tipoRelatorio, httpServletRequest, httpServletResponse,
					actionMapping);

		} catch (SistemaException ex) {
			// manda o erro para a p�gina no request atual
			reportarErros(httpServletRequest, "erro.sistema");

			// seta o mapeamento de retorno para a tela de erro de popup
			retorno = actionMapping.findForward("telaErroPopup");

		} catch (RelatorioVazioException ex1) {
			// manda o erro para a p�gina no request atual
			reportarErros(httpServletRequest, "erro.relatorio.vazio");

			// seta o mapeamento de retorno para a tela de aten��o de popup
			retorno = actionMapping.findForward("telaAtencaoPopup");
		}

		// devolve o mapeamento contido na vari�vel retorno
		return retorno;
	}

}
