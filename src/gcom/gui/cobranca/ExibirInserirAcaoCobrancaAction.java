package gcom.gui.cobranca;

import java.util.Collection;

import gcom.atendimentopublico.ligacaoagua.FiltroLigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.FiltroLigacaoEsgotoSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.atendimentopublico.ordemservico.FiltroServicoTipo;
import gcom.atendimentopublico.ordemservico.ServicoTipo;
import gcom.cobranca.CobrancaAcao;
import gcom.cobranca.CobrancaCriterio;
import gcom.cobranca.DocumentoTipo;
import gcom.cobranca.FiltroCobrancaAcao;
import gcom.cobranca.FiltroCobrancaCriterio;
import gcom.cobranca.FiltroDocumentoTipo;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Pre- processamento para inserir o criterio da cobran�a
 * 
 * @author S�vio Luiz
 * @date 17/04/2006
 */
public class ExibirInserirAcaoCobrancaAction extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		// Seta o retorno
		ActionForward retorno = actionMapping
				.findForward("inserirAcaoCobranca");

		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();

		AcaoCobrancaActionForm acaoCobrancaActionForm = (AcaoCobrancaActionForm) actionForm;
		if (httpServletRequest.getParameter("menu") != null
				&& !httpServletRequest.getParameter("menu").equals("")) {
			acaoCobrancaActionForm.setDescricaoAcao("");
			acaoCobrancaActionForm.setDescricaoCobrancaCriterio("");
			acaoCobrancaActionForm.setDescricaoServicoTipo("");
			acaoCobrancaActionForm.setIcAcaoObrigatoria("");
			acaoCobrancaActionForm.setIcAcrescimosImpontualidade("");
			acaoCobrancaActionForm.setIcCompoeCronograma("");
			acaoCobrancaActionForm.setIcDebitosACobrar("");
			acaoCobrancaActionForm.setIcCreditosARealizar("2");
			acaoCobrancaActionForm.setIcNotasPromissoria("2");
			acaoCobrancaActionForm.setIcEmitirBoletimCadastro("");
			acaoCobrancaActionForm.setIcGeraTaxa("");
			acaoCobrancaActionForm.setIcImoveisSemDebitos("");
			acaoCobrancaActionForm.setIcRepetidaCiclo("");
			acaoCobrancaActionForm.setIcSuspensaoAbastecimento("");
			acaoCobrancaActionForm.setIdAcaoPredecessora("");
			acaoCobrancaActionForm.setIdCobrancaCriterio("");
			acaoCobrancaActionForm.setIdServicoTipo("");
			acaoCobrancaActionForm.setIdSituacaoLigacaoAgua("");
			acaoCobrancaActionForm.setIdSituacaoLigacaoEsgoto("");
			acaoCobrancaActionForm.setIdTipoDocumentoGerado("");
			acaoCobrancaActionForm.setNumeroDiasEntreAcoes("");
			acaoCobrancaActionForm.setNumeroDiasValidade("");
			acaoCobrancaActionForm.setOrdemCronograma("");
			acaoCobrancaActionForm.setIcMetasCronograma("");
			acaoCobrancaActionForm.setIcOrdenamentoCronograma("");
			acaoCobrancaActionForm.setIcOrdenamentoEventual("");
			acaoCobrancaActionForm.setIcDebitoInterfereAcao("");
			acaoCobrancaActionForm.setNumeroDiasRemuneracaoTerceiro("");
			
			// faz as pesquisas obrigat�rias
			pesquisasObrigatorias(fachada, sessao);

		}

		// pesquisa os dados do enter
		pesquisarEnter(acaoCobrancaActionForm, httpServletRequest, fachada);

		return retorno;
	}

	private void pesquisarEnter(AcaoCobrancaActionForm acaoCobrancaActionForm,
			HttpServletRequest httpServletRequest, Fachada fachada) {

		// pesquisa enter de crit�rio de cobran�a
		if (acaoCobrancaActionForm.getIdCobrancaCriterio() != null
				&& !acaoCobrancaActionForm.getIdCobrancaCriterio().equals("")
				&& (acaoCobrancaActionForm.getDescricaoCobrancaCriterio() == null || acaoCobrancaActionForm
						.getDescricaoCobrancaCriterio().equals(""))) {

			FiltroCobrancaCriterio filtroCobrancaCriterio = new FiltroCobrancaCriterio();
			try {
				filtroCobrancaCriterio
						.adicionarParametro(new ParametroSimples(
								FiltroCobrancaCriterio.ID, new Integer(
										acaoCobrancaActionForm
												.getIdCobrancaCriterio())));
			} catch (NumberFormatException ex) {
				throw new ActionServletException(
						"atencao.campo_texto.numero_obrigatorio", null,
						"Crit�rio de Cobran�a");
			}
			filtroCobrancaCriterio
					.setCampoOrderBy(FiltroCobrancaCriterio.DESCRICAO_COBRANCA_CRITERIO);
			Collection colecaoCobrancaCriterio = fachada.pesquisar(
					filtroCobrancaCriterio, CobrancaCriterio.class.getName());

			if (colecaoCobrancaCriterio != null
					&& !colecaoCobrancaCriterio.isEmpty()) {
				CobrancaCriterio cobrancaCriterio = (CobrancaCriterio) Util
						.retonarObjetoDeColecao(colecaoCobrancaCriterio);
				acaoCobrancaActionForm
						.setDescricaoCobrancaCriterio(cobrancaCriterio
								.getDescricaoCobrancaCriterio());
			} else {
				acaoCobrancaActionForm.setIdCobrancaCriterio("");
				acaoCobrancaActionForm
						.setDescricaoCobrancaCriterio("COBRAN�A CRIT�RIO INEXISTENTE");
			}

		}

		// pesquisa enter de tipo de servi�o
		if (acaoCobrancaActionForm.getIdServicoTipo() != null
				&& !acaoCobrancaActionForm.getIdServicoTipo().equals("")
				&& (acaoCobrancaActionForm.getDescricaoServicoTipo() == null || acaoCobrancaActionForm
						.getDescricaoServicoTipo().equals(""))) {

			FiltroServicoTipo filtroServicoTipo = new FiltroServicoTipo();
			try {
				filtroServicoTipo.adicionarParametro(new ParametroSimples(
						FiltroServicoTipo.ID, new Integer(
								acaoCobrancaActionForm.getIdServicoTipo())));
			} catch (NumberFormatException ex) {
				throw new ActionServletException(
						"atencao.campo_texto.numero_obrigatorio", null,
						"Servi�o Tipo");
			}
			filtroServicoTipo.setCampoOrderBy(FiltroServicoTipo.DESCRICAO);
			Collection colecaoServicoTipo = fachada.pesquisar(
					filtroServicoTipo, ServicoTipo.class.getName());

			if (colecaoServicoTipo != null && !colecaoServicoTipo.isEmpty()) {
				ServicoTipo servicoTipo = (ServicoTipo) Util
						.retonarObjetoDeColecao(colecaoServicoTipo);
				acaoCobrancaActionForm.setDescricaoServicoTipo(servicoTipo
						.getDescricao());
			} else {
				acaoCobrancaActionForm.setIdServicoTipo("");
				acaoCobrancaActionForm
						.setDescricaoServicoTipo("TIPO DE SERVI�O INEXISTENTE");
			}

		}
	}

	private void pesquisasObrigatorias(Fachada fachada, HttpSession sessao) {
		// pesquisa as a��es predecessoras
		FiltroCobrancaAcao filtroCobrancaAcao = new FiltroCobrancaAcao();
		filtroCobrancaAcao.setCampoOrderBy(FiltroCobrancaAcao.DESCRICAO);
		filtroCobrancaAcao.adicionarParametro(new ParametroSimples(
				FiltroCobrancaAcao.INDICADOR_USO,
				ConstantesSistema.INDICADOR_USO_ATIVO));
		Collection colecaoAcaoPredecessora = fachada.pesquisar(
				filtroCobrancaAcao, CobrancaAcao.class.getName());
		if (colecaoAcaoPredecessora == null
				|| colecaoAcaoPredecessora.isEmpty()) {
			throw new ActionServletException("atencao.pesquisa_inexistente",
					null, "Cobran�a A��o");
		} else {
			sessao.setAttribute("colecaoAcaoPredecessora",
					colecaoAcaoPredecessora);
		}

		// pesquisa os tipos de documentos
		FiltroDocumentoTipo filtroDocumentoTipo = new FiltroDocumentoTipo();
		filtroDocumentoTipo.setCampoOrderBy(FiltroDocumentoTipo.DESCRICAO);
		filtroDocumentoTipo.adicionarParametro(new ParametroSimples(
				FiltroDocumentoTipo.INDICADOR_USO,
				ConstantesSistema.INDICADOR_USO_ATIVO));
		Collection colecaoDocumentoTipo = fachada.pesquisar(
				filtroDocumentoTipo, DocumentoTipo.class.getName());
		if (colecaoDocumentoTipo == null || colecaoDocumentoTipo.isEmpty()) {
			throw new ActionServletException("atencao.pesquisa_inexistente",
					null, "Documento Tipo");
		} else {
			sessao.setAttribute("colecaoDocumentoTipo", colecaoDocumentoTipo);
		}

		// pesquisa as situa��es de liga��es de agua
		FiltroLigacaoAguaSituacao filtroLigacaoAguaSituacao = new FiltroLigacaoAguaSituacao();
		filtroDocumentoTipo
				.setCampoOrderBy(FiltroLigacaoAguaSituacao.DESCRICAO);
		filtroDocumentoTipo.adicionarParametro(new ParametroSimples(
				FiltroLigacaoAguaSituacao.INDICADOR_USO,
				ConstantesSistema.INDICADOR_USO_ATIVO));
		Collection colecaoLigacaoAguaSituacao = fachada.pesquisar(
				filtroLigacaoAguaSituacao, LigacaoAguaSituacao.class.getName());
		if (colecaoLigacaoAguaSituacao == null
				|| colecaoLigacaoAguaSituacao.isEmpty()) {
			throw new ActionServletException("atencao.pesquisa_inexistente",
					null, "Liga��o Agua Situa��o");
		} else {
			sessao.setAttribute("colecaoLigacaoAguaSituacao",
					colecaoLigacaoAguaSituacao);
		}

		// pesquisa as situa��es de liga��es de agua
		FiltroLigacaoEsgotoSituacao filtroLigacaoEsgotoSituacao = new FiltroLigacaoEsgotoSituacao();
		filtroDocumentoTipo
				.setCampoOrderBy(FiltroLigacaoEsgotoSituacao.DESCRICAO);
		filtroDocumentoTipo.adicionarParametro(new ParametroSimples(
				FiltroLigacaoEsgotoSituacao.INDICADOR_USO,
				ConstantesSistema.INDICADOR_USO_ATIVO));
		Collection colecaoLigacaoEsgotoSituacao = fachada.pesquisar(
				filtroLigacaoEsgotoSituacao, LigacaoEsgotoSituacao.class
						.getName());
		if (colecaoLigacaoEsgotoSituacao == null
				|| colecaoLigacaoEsgotoSituacao.isEmpty()) {
			throw new ActionServletException("atencao.pesquisa_inexistente",
					null, "Liga��o Esgoto Situa��o");
		} else {
			sessao.setAttribute("colecaoLigacaoEsgotoSituacao",
					colecaoLigacaoEsgotoSituacao);
		}
	}
}
