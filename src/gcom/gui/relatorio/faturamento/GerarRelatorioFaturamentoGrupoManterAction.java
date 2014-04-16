package gcom.gui.relatorio.faturamento;

import gcom.faturamento.FaturamentoGrupo;
import gcom.faturamento.FiltroFaturamentoGrupo;
import gcom.gui.faturamento.FiltrarFaturamentoGrupoActionForm;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.faturamento.RelatorioManterFaturamentoGrupo;
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
 * @author Vinicius Medeiros
 * @version 1.0
 */

public class GerarRelatorioFaturamentoGrupoManterAction extends
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

		FiltrarFaturamentoGrupoActionForm filtrarFaturamentoGrupoActionForm = (FiltrarFaturamentoGrupoActionForm) actionForm;

		FiltroFaturamentoGrupo filtroFaturamentoGrupo = (FiltroFaturamentoGrupo) sessao
				.getAttribute("filtroFaturamentoGrupo");

		// Inicio da parte que vai mandar os parametros para o relat�rio

		FaturamentoGrupo faturamentoGrupoParametros = new FaturamentoGrupo();

		String id = null;

		String idFaturamentoGrupoPesquisar = (String) filtrarFaturamentoGrupoActionForm
				.getId();

		if (idFaturamentoGrupoPesquisar != null
				&& !idFaturamentoGrupoPesquisar.equals("")) {
			id = idFaturamentoGrupoPesquisar;
		}
		
		

		Short indicadorDeUso = null;

		if (filtrarFaturamentoGrupoActionForm.getIndicadorUso() != null
				&& !filtrarFaturamentoGrupoActionForm.getIndicadorUso().equals(
						"")) {

			indicadorDeUso = new Short(""
					+ filtrarFaturamentoGrupoActionForm.getIndicadorUso());
		}
		
		String anoMesReferencia = filtrarFaturamentoGrupoActionForm
		.getAnoMesReferencia();
		
		if(anoMesReferencia != null && !anoMesReferencia.trim().equals("")){
        	String mes = anoMesReferencia.substring(0, 2);
        	String ano = anoMesReferencia.substring(3, 7);
        	String anoMes = ano+mes;
			
        	anoMesReferencia = anoMes;
        	
		}
		
		Short diaVencimento = null;
		
		if(filtrarFaturamentoGrupoActionForm.getDiaVencimento() != null
				&& !filtrarFaturamentoGrupoActionForm.getDiaVencimento().equals("")){
			
			diaVencimento = new Short(""
					+ filtrarFaturamentoGrupoActionForm.getDiaVencimento());
		}
		
		Short indicadorVencimento = null;
		
		if (filtrarFaturamentoGrupoActionForm.getIndicadorVencimentoMesFatura() != null
				&& !filtrarFaturamentoGrupoActionForm.getIndicadorVencimentoMesFatura().equals(
						"")) {

			indicadorVencimento = new Short(""
					+ filtrarFaturamentoGrupoActionForm.getIndicadorVencimentoMesFatura());
		}

		// seta os parametros que ser�o mostrados no relat�rio

		faturamentoGrupoParametros.setId(id == null ? null : new Integer(id));
		
		if(anoMesReferencia == null || anoMesReferencia.equals("")){
			faturamentoGrupoParametros.setAnoMesReferencia(null);
		} else {
			faturamentoGrupoParametros.setAnoMesReferencia(new Integer(anoMesReferencia));
		}
		
		faturamentoGrupoParametros.setDescricao(""
				+ filtrarFaturamentoGrupoActionForm.getDescricao());
		faturamentoGrupoParametros.setDescricaoAbreviada(""
				+ filtrarFaturamentoGrupoActionForm.getDescricaoAbreviada());
		faturamentoGrupoParametros.setDiaVencimento(diaVencimento);
		faturamentoGrupoParametros.setIndicadorUso(indicadorDeUso);
		faturamentoGrupoParametros.setIndicadorVencimentoMesFatura(indicadorVencimento);

		// Fim da parte que vai mandar os parametros para o relat�rio

		// cria uma inst�ncia da classe do relat�rio
		RelatorioManterFaturamentoGrupo relatorioManterFaturamentoGrupo = new RelatorioManterFaturamentoGrupo(
				(Usuario) (httpServletRequest.getSession(false))
						.getAttribute("usuarioLogado"));
		relatorioManterFaturamentoGrupo.addParametro("filtroFaturamentoGrupo",
				filtroFaturamentoGrupo);
		relatorioManterFaturamentoGrupo.addParametro(
				"faturamentoGrupoParametros", faturamentoGrupoParametros);

		// chama o met�do de gerar relat�rio passando o c�digo da analise
		// como par�metro
		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");
		if (tipoRelatorio == null) {
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		relatorioManterFaturamentoGrupo.addParametro("tipoFormatoRelatorio",
				Integer.parseInt(tipoRelatorio));
		try {
			retorno = processarExibicaoRelatorio(
					relatorioManterFaturamentoGrupo, tipoRelatorio,
					httpServletRequest, httpServletResponse, actionMapping);

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
	}}
