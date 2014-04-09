package gcom.gui.relatorio.faturamento;

import gcom.faturamento.FaturamentoSituacaoTipo;
import gcom.faturamento.FiltroFaturamentoSituacaoTipo;
import gcom.gui.faturamento.FiltrarFaturamentoSituacaoTipoActionForm;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.faturamento.RelatorioManterFaturamentoSituacaoTipo;
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
 * @author Arthur Carvalho
 * @version 1.0
 */

public class GerarRelatorioFaturamentoSituacaoTipoManterAction extends
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

		FiltrarFaturamentoSituacaoTipoActionForm filtrarFaturamentoSituacaoTipoActionForm = (FiltrarFaturamentoSituacaoTipoActionForm) actionForm;


		FiltroFaturamentoSituacaoTipo filtroFaturamentoSituacaoTipo= (FiltroFaturamentoSituacaoTipo) sessao
				.getAttribute("filtroFaturamentoSituacaoTipo");

		// Inicio da parte que vai mandar os parametros para o relat�rio

		FaturamentoSituacaoTipo faturamentoSituacaoTipoParametros = new FaturamentoSituacaoTipo();

		String id = null;

		String idFaturamentoSituacaoTipoPesquisar = (String) filtrarFaturamentoSituacaoTipoActionForm.getId();

		if (idFaturamentoSituacaoTipoPesquisar != null && !idFaturamentoSituacaoTipoPesquisar.equals("")) {
		id = idFaturamentoSituacaoTipoPesquisar;
		}

		Short indicadorParalisacaoFaturamento = null;

		if (filtrarFaturamentoSituacaoTipoActionForm.getIndicadorParalisacaoFaturamento() != null
				&& !filtrarFaturamentoSituacaoTipoActionForm.getIndicadorParalisacaoFaturamento().equals("")) {

			indicadorParalisacaoFaturamento = new Short(""
					+ filtrarFaturamentoSituacaoTipoActionForm.getIndicadorParalisacaoFaturamento());
		}
		
		Short indicadorParalisacaoLeitura = null;

		if (filtrarFaturamentoSituacaoTipoActionForm.getIndicadorParalisacaoLeitura() != null
				&& !filtrarFaturamentoSituacaoTipoActionForm.getIndicadorParalisacaoLeitura().equals("")) {

			indicadorParalisacaoLeitura = new Short(""
					+ filtrarFaturamentoSituacaoTipoActionForm.getIndicadorParalisacaoLeitura());
		}
		
		Short indicadorValidoAgua = null;

		if (filtrarFaturamentoSituacaoTipoActionForm.getIndicadorValidoAgua() != null
				&& !filtrarFaturamentoSituacaoTipoActionForm.getIndicadorValidoAgua().equals("")) {

			indicadorValidoAgua = new Short(""
					+ filtrarFaturamentoSituacaoTipoActionForm.getIndicadorValidoAgua());
		}
		
		Short indicadorValidoEsgoto = null;

		if (filtrarFaturamentoSituacaoTipoActionForm.getIndicadorValidoEsgoto() != null
				&& !filtrarFaturamentoSituacaoTipoActionForm.getIndicadorValidoEsgoto().equals("")) {

			indicadorValidoEsgoto = new Short(""
					+ filtrarFaturamentoSituacaoTipoActionForm.getIndicadorValidoEsgoto());
		}

		
		
		// seta os parametros que ser�o mostrados no relat�rio

		if(filtrarFaturamentoSituacaoTipoActionForm.getId() == null || filtrarFaturamentoSituacaoTipoActionForm.getId().equals("")){
			faturamentoSituacaoTipoParametros.setId(null);
		} else {
			faturamentoSituacaoTipoParametros.setId(new Integer (id));
		}
		
		faturamentoSituacaoTipoParametros.setDescricao(""+ filtrarFaturamentoSituacaoTipoActionForm.getDescricao());
		faturamentoSituacaoTipoParametros.setIndicadorParalisacaoFaturamento(indicadorParalisacaoFaturamento);
		faturamentoSituacaoTipoParametros.setIndicadorParalisacaoLeitura(indicadorParalisacaoLeitura);
		faturamentoSituacaoTipoParametros.setIndicadorValidoAgua(indicadorValidoAgua);
		faturamentoSituacaoTipoParametros.setIndicadorValidoEsgoto(indicadorValidoEsgoto);
		
		// Fim da parte que vai mandar os parametros para o relat�rio

		// cria uma inst�ncia da classe do relat�rio
		RelatorioManterFaturamentoSituacaoTipo relatorioManterFaturamentoSituacaoTipo= new RelatorioManterFaturamentoSituacaoTipo(
				(Usuario)(httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));
		relatorioManterFaturamentoSituacaoTipo.addParametro("filtroFaturamentoSituacaoTipo",
				filtroFaturamentoSituacaoTipo);
		relatorioManterFaturamentoSituacaoTipo.addParametro("faturamentoSituacaoTipoParametros",
				faturamentoSituacaoTipoParametros);

		// chama o met�do de gerar relat�rio passando o c�digo da analise
		// como par�metro
		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");
		if (tipoRelatorio == null) {
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		relatorioManterFaturamentoSituacaoTipo.addParametro("tipoFormatoRelatorio", Integer
				.parseInt(tipoRelatorio));
		try {
			retorno = processarExibicaoRelatorio(relatorioManterFaturamentoSituacaoTipo,
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
