package gcom.gui.relatorio.faturamento.conta;

import gcom.cadastro.localidade.FiltroGerenciaRegional;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.FiltroSetorComercial;
import gcom.cadastro.localidade.GerenciaRegional;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.SetorComercial;
import gcom.fachada.Fachada;
import gcom.faturamento.FaturamentoGrupo;
import gcom.faturamento.FiltroFaturamentoGrupo;
import gcom.faturamento.conta.ContaMensagem;
import gcom.faturamento.conta.FiltroContaMensagem;
import gcom.gui.faturamento.conta.FiltrarMensagemContaActionForm;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.faturamento.conta.RelatorioManterMensagemConta;
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
 * @author Rafael Corr�a
 * @version 1.0
 */

public class GerarRelatorioMensagemContaManterAction extends
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

		FiltrarMensagemContaActionForm filtrarMensagemContaActionForm = (FiltrarMensagemContaActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		FiltroContaMensagem filtroContaMensagem = (FiltroContaMensagem) sessao
				.getAttribute("filtroContaMensagem");

		// Inicio da parte que vai mandar os parametros para o relat�rio

		ContaMensagem contaMensagemParametros = new ContaMensagem();

		Integer referencia = null;

		String referenciaPesquisar = filtrarMensagemContaActionForm
				.getReferenciaFaturamento();

		if (referenciaPesquisar != null && !referenciaPesquisar.equals("")) {
			referencia = new Integer(Util
					.formatarMesAnoParaAnoMesSemBarra(referenciaPesquisar));
		}

		String idGerenciaRegional = (String) filtrarMensagemContaActionForm
				.getGerenciaRegional();

		GerenciaRegional gerenciaRegional = new GerenciaRegional();

		if (idGerenciaRegional != null
				&& !idGerenciaRegional.equals("")
				&& !idGerenciaRegional.equals(""
						+ ConstantesSistema.NUMERO_NAO_INFORMADO)) {
			FiltroGerenciaRegional filtroGerenciaRegional = new FiltroGerenciaRegional();

			filtroGerenciaRegional.adicionarParametro(new ParametroSimples(
					FiltroGerenciaRegional.ID, idGerenciaRegional));

			Collection colecaoGerenciasRegionais = fachada.pesquisar(
					filtroGerenciaRegional, GerenciaRegional.class.getName());

			if (colecaoGerenciasRegionais != null
					&& !colecaoGerenciasRegionais.isEmpty()) {
				// Titulo do Logradouro Foi Encontrado
				Iterator colecaoGerenciaRegionalIterator = colecaoGerenciasRegionais
						.iterator();

				gerenciaRegional = (GerenciaRegional) colecaoGerenciaRegionalIterator
						.next();

			}
		}

		String idLocalidade = (String) filtrarMensagemContaActionForm
				.getLocalidade();

		Localidade localidade = new Localidade();

		if (idLocalidade != null && !idLocalidade.equals("")) {
			FiltroLocalidade filtroLocalidade = new FiltroLocalidade();

			filtroLocalidade.adicionarParametro(new ParametroSimples(
					FiltroLocalidade.ID, idLocalidade));

			Collection colecaoLocalidade = fachada.pesquisar(filtroLocalidade,
					Localidade.class.getName());

			if (colecaoLocalidade != null && !colecaoLocalidade.isEmpty()) {
				// Titulo do Logradouro Foi Encontrado
				Iterator colecaoLocalidadeIterator = colecaoLocalidade
						.iterator();

				localidade = (Localidade) colecaoLocalidadeIterator.next();

			}
		}

		String codigoSetorComercial = (String) filtrarMensagemContaActionForm
				.getSetorComercial();

		SetorComercial setorComercial = new SetorComercial();

		if (codigoSetorComercial != null && !codigoSetorComercial.equals("")) {
			FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();

			filtroSetorComercial.adicionarParametro(new ParametroSimples(
					FiltroSetorComercial.CODIGO_SETOR_COMERCIAL,
					codigoSetorComercial));
			filtroSetorComercial.adicionarParametro(new ParametroSimples(
					FiltroSetorComercial.ID_LOCALIDADE, idLocalidade));

			Collection colecaoSetorComercial = fachada.pesquisar(
					filtroSetorComercial, SetorComercial.class.getName());

			if (colecaoSetorComercial != null
					&& !colecaoSetorComercial.isEmpty()) {
				Iterator colecaoSetorComercialIterator = colecaoSetorComercial
						.iterator();

				setorComercial = (SetorComercial) colecaoSetorComercialIterator
						.next();

			}
		}

		String idGrupoFaturamento = (String) filtrarMensagemContaActionForm
				.getGrupoFaturamento();

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

				Iterator colecaoGruposFaturamentosIterator = colecaoGruposFaturamentos
						.iterator();

				faturamentoGrupo = (FaturamentoGrupo) colecaoGruposFaturamentosIterator
						.next();

			}
		}

		// seta os parametros que ser�o mostrados no relat�rio

		contaMensagemParametros.setAnoMesRreferenciaFaturamento(referencia);
		contaMensagemParametros.setGerenciaRegional(gerenciaRegional);
		contaMensagemParametros.setLocalidade(localidade);
		contaMensagemParametros.setSetorComercial(setorComercial);
		contaMensagemParametros.setFaturamentoGrupo(faturamentoGrupo);
		contaMensagemParametros
				.setDescricaoContaMensagem01(filtrarMensagemContaActionForm
						.getMensagemConta01());

		// Fim da parte que vai mandar os parametros para o relat�rio

		// cria uma inst�ncia da classe do relat�rio
		RelatorioManterMensagemConta relatorioManterMensagemConta = new RelatorioManterMensagemConta(
				(Usuario)(httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));
		relatorioManterMensagemConta.addParametro("filtroContaMensagem",
				filtroContaMensagem);
		relatorioManterMensagemConta.addParametro("contaMensagemParametros",
				contaMensagemParametros);

		// chama o met�do de gerar relat�rio passando o c�digo da analise
		// como par�metro
		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");
		if (tipoRelatorio == null) {
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		relatorioManterMensagemConta.addParametro("tipoFormatoRelatorio",
				Integer.parseInt(tipoRelatorio));
		try {
			retorno = processarExibicaoRelatorio(relatorioManterMensagemConta,
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
