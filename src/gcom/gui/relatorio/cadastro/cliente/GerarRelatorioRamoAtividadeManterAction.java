package gcom.gui.relatorio.cadastro.cliente;

import gcom.cadastro.cliente.FiltroRamoAtividade;
import gcom.cadastro.cliente.RamoAtividade;
import gcom.gui.cadastro.cliente.FiltrarRamoAtividadeActionForm;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.cadastro.cliente.RelatorioManterRamoAtividade;
import gcom.seguranca.acesso.usuario.Usuario;
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
 * @author Fernando Fontelles Filho
 * @version 1.0
 */

public class GerarRelatorioRamoAtividadeManterAction extends
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

		FiltrarRamoAtividadeActionForm form = (FiltrarRamoAtividadeActionForm) actionForm;

		FiltroRamoAtividade filtroRamoAtividade = (FiltroRamoAtividade) sessao
				.getAttribute("filtroRamoAtividade");

		// Inicio da parte que vai mandar os parametros para o relat�rio

		RamoAtividade ramoAtividadeParametros = new RamoAtividade();

		Short codigo = null;

		Short indicadordeUso = null;
		
		if(form.getIndicadorUso()!= null && !form.getIndicadorUso().equals("")){
			
			indicadordeUso = new Short ("" + form.getIndicadorUso());
		}
		
		if(form.getCodigo()!= null && !form.getCodigo().equals("")){
			
			codigo = new Short(form.getCodigo());
		}
		
		// seta os parametros que ser�o mostrados no relat�rio
		if(codigo != null && !codigo.equals("")){
			
			ramoAtividadeParametros.setCodigo(codigo);
			
		}
		
		if(form.getDescricao() != null && !form.getDescricao().equals("")){
			
			ramoAtividadeParametros.setDescricao(form.getDescricao());
			
		}
		
		if(indicadordeUso != null && indicadordeUso.equals("")){
			
			ramoAtividadeParametros.setIndicadorUso(indicadordeUso);
			
		}
		
		// Fim da parte que vai mandar os parametros para o relat�rio

		// cria uma inst�ncia da classe do relat�rio
		RelatorioManterRamoAtividade relatorioManterRamoAtividade = new RelatorioManterRamoAtividade(
				(Usuario)(httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));
		relatorioManterRamoAtividade.addParametro("filtroRamoAtividade",
				filtroRamoAtividade);
		relatorioManterRamoAtividade.addParametro("ramoAtividadeParametros",
				ramoAtividadeParametros);

		// chama o met�do de gerar relat�rio passando o c�digo da analise
		// como par�metro
		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");
		if (tipoRelatorio == null) {
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		relatorioManterRamoAtividade.addParametro("tipoFormatoRelatorio", Integer
				.parseInt(tipoRelatorio));
		try {
			retorno = processarExibicaoRelatorio(relatorioManterRamoAtividade,
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
