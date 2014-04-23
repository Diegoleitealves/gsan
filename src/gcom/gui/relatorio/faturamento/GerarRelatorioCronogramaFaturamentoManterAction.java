package gcom.gui.relatorio.faturamento;

import gcom.fachada.Fachada;
import gcom.faturamento.FaturamentoGrupo;
import gcom.faturamento.FaturamentoGrupoCronogramaMensal;
import gcom.faturamento.FiltroFaturamentoAtividadeCronograma;
import gcom.faturamento.FiltroFaturamentoGrupo;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.faturamento.RelatorioManterCronogramaFaturamento;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.SistemaException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

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
 * @author Rafael Corr�a
 * @version 1.0
 */

public class GerarRelatorioCronogramaFaturamentoManterAction extends
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

		DynaValidatorForm pesquisarActionForm = (DynaValidatorForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		FiltroFaturamentoAtividadeCronograma filtroFaturamentoAtividadeCronograma = new FiltroFaturamentoAtividadeCronograma();

		// Inicio da parte que vai mandar os parametros para o relat�rio

		FaturamentoGrupoCronogramaMensal faturamentoGrupoCronogramaMensalParametros = new FaturamentoGrupoCronogramaMensal();

		String idGrupoFaturamento = (String) pesquisarActionForm
				.get("idGrupoFaturamento");

		FaturamentoGrupo faturamentoGrupo = new FaturamentoGrupo();

		if (idGrupoFaturamento != null
				&& !idGrupoFaturamento.equals("")
				&& !idGrupoFaturamento.equals(""
						+ ConstantesSistema.NUMERO_NAO_INFORMADO)) {
			FiltroFaturamentoGrupo filtroFaturamentoGrupo = new FiltroFaturamentoGrupo();

			filtroFaturamentoGrupo.adicionarParametro(new ParametroSimples(
					FiltroFaturamentoGrupo.ID, idGrupoFaturamento));

			Collection colecaoGruposFaturamentos = fachada.pesquisar(
					filtroFaturamentoGrupo, FaturamentoGrupo.class.getName());

			if (colecaoGruposFaturamentos != null
					&& !colecaoGruposFaturamentos.isEmpty()) {

				filtroFaturamentoAtividadeCronograma
						.adicionarParametro(new ParametroSimples(
								FiltroFaturamentoAtividadeCronograma.FATURAMENTO_GRUPO_CRONOGRAMA_MENSAL_FATURAMENTO_GRUPO_ID,
								idGrupoFaturamento));

				Iterator colecaoGruposFaturamentosIterator = colecaoGruposFaturamentos
						.iterator();

				faturamentoGrupo = (FaturamentoGrupo) colecaoGruposFaturamentosIterator
						.next();

			}
		}

		Integer anoMes = null;

		String mesAnoPesquisar = (String) pesquisarActionForm.get("mesAno");

		if (mesAnoPesquisar != null && !mesAnoPesquisar.equals("")) {

			anoMes = new Integer(Util.formatarMesAnoParaAnoMesSemBarra(mesAnoPesquisar));

			filtroFaturamentoAtividadeCronograma
					.adicionarParametro(new ParametroSimples(
							FiltroFaturamentoAtividadeCronograma.FATURAMENTO_GRUPO_CRONOGRAMA_MENSAL_ANO_MES_REFERENCIA,
							anoMes));
		}

		// seta os parametros que ser�o mostrados no relat�rio

		if (faturamentoGrupo != null) {
			faturamentoGrupoCronogramaMensalParametros
				.setFaturamentoGrupo(faturamentoGrupo);
		}
		
		if (anoMes != null) {
			faturamentoGrupoCronogramaMensalParametros.setAnoMesReferencia(anoMes);
		}

		// Fim da parte que vai mandar os parametros para o relat�rio

		// cria uma inst�ncia da classe do relat�rio
		RelatorioManterCronogramaFaturamento relatorioManterCronogramaFaturamento = new RelatorioManterCronogramaFaturamento(
				(Usuario)(httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));
		relatorioManterCronogramaFaturamento.addParametro(
				"filtroFaturamentoAtividadeCronograma",
				filtroFaturamentoAtividadeCronograma);
		relatorioManterCronogramaFaturamento.addParametro(
				"faturamentoGrupoCronogramaMensalParametros",
				faturamentoGrupoCronogramaMensalParametros);

		// chama o met�do de gerar relat�rio passando o c�digo da analise
		// como par�metro
		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");
		if (tipoRelatorio == null) {
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		relatorioManterCronogramaFaturamento.addParametro(
				"tipoFormatoRelatorio", Integer.parseInt(tipoRelatorio));
		try {
			retorno = processarExibicaoRelatorio(
					relatorioManterCronogramaFaturamento, tipoRelatorio,
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
	}
}
