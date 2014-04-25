package gcom.gui.relatorio.atendimentopublico;

import java.util.Collection;

import gcom.atendimentopublico.ligacaoesgoto.FiltroLigacaoEsgotoEsgotamento;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoEsgotamento;
import gcom.fachada.Fachada;
import gcom.faturamento.FaturamentoSituacaoMotivo;
import gcom.faturamento.FaturamentoSituacaoTipo;
import gcom.faturamento.FiltroFaturamentoSituacaoMotivo;
import gcom.faturamento.FiltroFaturamentoSituacaoTipo;
import gcom.gui.atendimentopublico.FiltrarLigacaoEsgotoEsgotamentoActionForm;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.atendimentopublico.RelatorioManterLigacaoEsgotoEsgotamento;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.SistemaException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

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

public class GerarRelatorioLigacaoEsgotoEsgotamentoManterAction extends
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

		FiltrarLigacaoEsgotoEsgotamentoActionForm filtrarLigacaoEsgotoEsgotamentoActionForm = (FiltrarLigacaoEsgotoEsgotamentoActionForm) actionForm;

		FiltroLigacaoEsgotoEsgotamento filtroLigacaoEsgotoEsgotamento = (FiltroLigacaoEsgotoEsgotamento) sessao
				.getAttribute("filtroLigacaoEsgotoEsgotamento");

		// Inicio da parte que vai mandar os parametros para o relat�rio
		Fachada fachada = Fachada.getInstancia();
		
		LigacaoEsgotoEsgotamento ligacaoEsgotoEsgotamentoParametros = new LigacaoEsgotoEsgotamento();

		String id = null;

		String idLigacaoEsgotoEsgotamentoPesquisar = (String) filtrarLigacaoEsgotoEsgotamentoActionForm.getId();

		if (idLigacaoEsgotoEsgotamentoPesquisar != null && !idLigacaoEsgotoEsgotamentoPesquisar.equals("")) {
			id = idLigacaoEsgotoEsgotamentoPesquisar;
		}
		
		Short indicadorUso = null;
		
		if(filtrarLigacaoEsgotoEsgotamentoActionForm.getIndicadorUso()!= null && !filtrarLigacaoEsgotoEsgotamentoActionForm.getIndicadorUso().equals("")){
			
			indicadorUso = new Short ("" + filtrarLigacaoEsgotoEsgotamentoActionForm.getIndicadorUso());
		}
		
		if (filtrarLigacaoEsgotoEsgotamentoActionForm.getFaturamentoSituacaoTipo() != null && !filtrarLigacaoEsgotoEsgotamentoActionForm.getFaturamentoSituacaoTipo().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)) {
			
			FiltroFaturamentoSituacaoTipo filtroFaturamentoSituacaoTipo = new FiltroFaturamentoSituacaoTipo();
			filtroFaturamentoSituacaoTipo.adicionarParametro(new ParametroSimples(FiltroFaturamentoSituacaoTipo.ID, filtrarLigacaoEsgotoEsgotamentoActionForm.getFaturamentoSituacaoTipo()));
			
			Collection colecaoFaturamentoSituacaoTipo = fachada.pesquisar(filtroFaturamentoSituacaoTipo, FaturamentoSituacaoTipo.class.getName());
			
			if (colecaoFaturamentoSituacaoTipo != null && !colecaoFaturamentoSituacaoTipo.isEmpty()) {
				FaturamentoSituacaoTipo faturamentoSituacaoTipo = (FaturamentoSituacaoTipo) Util.retonarObjetoDeColecao(colecaoFaturamentoSituacaoTipo);
				ligacaoEsgotoEsgotamentoParametros.setFaturamentoSituacaoTipo(faturamentoSituacaoTipo);
			}
			
		}
		
		if (filtrarLigacaoEsgotoEsgotamentoActionForm.getFaturamentoSituacaoMotivo() != null && !filtrarLigacaoEsgotoEsgotamentoActionForm.getFaturamentoSituacaoMotivo().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)) {
			
			FiltroFaturamentoSituacaoMotivo filtroFaturamentoSituacaoMotivo = new FiltroFaturamentoSituacaoMotivo();
			filtroFaturamentoSituacaoMotivo.adicionarParametro(new ParametroSimples(FiltroFaturamentoSituacaoMotivo.ID, filtrarLigacaoEsgotoEsgotamentoActionForm.getFaturamentoSituacaoMotivo()));
			
			Collection colecaoFaturamentoSituacaoMotivo = fachada.pesquisar(filtroFaturamentoSituacaoMotivo, FaturamentoSituacaoMotivo.class.getName());
			
			if (colecaoFaturamentoSituacaoMotivo != null && !colecaoFaturamentoSituacaoMotivo.isEmpty()) {
				FaturamentoSituacaoMotivo faturamentoSituacaoMotivo = (FaturamentoSituacaoMotivo) Util.retonarObjetoDeColecao(colecaoFaturamentoSituacaoMotivo);
				ligacaoEsgotoEsgotamentoParametros.setFaturamentoSituacaoMotivo(faturamentoSituacaoMotivo);
			}
			
		}
		
		Integer quantidadeMesesSituacaoEspecial = null;
		
		if(filtrarLigacaoEsgotoEsgotamentoActionForm.getQuantidadeMesesSituacaoEspecial() != null && !filtrarLigacaoEsgotoEsgotamentoActionForm.getQuantidadeMesesSituacaoEspecial().equals("")){
			
			quantidadeMesesSituacaoEspecial = new Integer(""
					+ filtrarLigacaoEsgotoEsgotamentoActionForm.getQuantidadeMesesSituacaoEspecial());

		}
		
		// seta os parametros que ser�o mostrados no relat�rio

		ligacaoEsgotoEsgotamentoParametros.setId(id == null ? null : new Integer(
				id));
		ligacaoEsgotoEsgotamentoParametros.setDescricao(filtrarLigacaoEsgotoEsgotamentoActionForm.getDescricao());
		ligacaoEsgotoEsgotamentoParametros.setQuantidadeMesesSituacaoEspecial(quantidadeMesesSituacaoEspecial);
		ligacaoEsgotoEsgotamentoParametros.setIndicadorUso(indicadorUso);
		// Fim da parte que vai mandar os parametros para o relat�rio

		// cria uma inst�ncia da classe do relat�rio
	
		RelatorioManterLigacaoEsgotoEsgotamento relatorioManterLigacaoEsgotoEsgotamento = new RelatorioManterLigacaoEsgotoEsgotamento(
				(Usuario)(httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));
		relatorioManterLigacaoEsgotoEsgotamento.addParametro("filtroLigacaoEsgotoEsgotamento",
				filtroLigacaoEsgotoEsgotamento);
		relatorioManterLigacaoEsgotoEsgotamento.addParametro("ligacaoEsgotoEsgotamentoParametros",
				ligacaoEsgotoEsgotamentoParametros);

		// chama o met�do de gerar relat�rio passando o c�digo da analise
		// como par�metro
		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");
		if (tipoRelatorio == null) {
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		relatorioManterLigacaoEsgotoEsgotamento.addParametro("tipoFormatoRelatorio", Integer
				.parseInt(tipoRelatorio));
		try {
			retorno = processarExibicaoRelatorio(relatorioManterLigacaoEsgotoEsgotamento,
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
