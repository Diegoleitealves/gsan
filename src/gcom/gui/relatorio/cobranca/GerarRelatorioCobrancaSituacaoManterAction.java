package gcom.gui.relatorio.cobranca;

import java.util.Collection;

import gcom.cobranca.CobrancaSituacao;
import gcom.cobranca.FiltroCobrancaSituacao;
import gcom.fachada.Fachada;
import gcom.faturamento.conta.ContaMotivoRevisao;
import gcom.faturamento.conta.FiltroContaMotivoRevisao;
import gcom.gui.cobranca.FiltrarCobrancaSituacaoActionForm;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.cobranca.RelatorioManterCobrancaSituacao;
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

public class GerarRelatorioCobrancaSituacaoManterAction extends
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

		FiltrarCobrancaSituacaoActionForm filtrarCobrancaSituacaoActionForm = (FiltrarCobrancaSituacaoActionForm) actionForm;

		FiltroCobrancaSituacao filtroCobrancaSituacao = (FiltroCobrancaSituacao) sessao
				.getAttribute("filtroCobrancaSituacao");

		// Inicio da parte que vai mandar os parametros para o relat�rio
		Fachada fachada = Fachada.getInstancia();
		
		CobrancaSituacao cobrancaSituacaoParametros = new CobrancaSituacao();

		String id = null;

		String idCobrancaSituacaoPesquisar = (String) filtrarCobrancaSituacaoActionForm.getId();

		if (idCobrancaSituacaoPesquisar != null && !idCobrancaSituacaoPesquisar.equals("")) {
			id = idCobrancaSituacaoPesquisar;
		}
		
		Short indicadorUso = null;
		
		if(filtrarCobrancaSituacaoActionForm.getIndicadorUso()!= null && !filtrarCobrancaSituacaoActionForm.getIndicadorUso().equals("")){
			
			indicadorUso = new Short ("" + filtrarCobrancaSituacaoActionForm.getIndicadorUso());
		}

		Short indicadorExigenciaAdvogado = null;
		
		if(filtrarCobrancaSituacaoActionForm.getIndicadorExigenciaAdvogado()!= null && !filtrarCobrancaSituacaoActionForm.getIndicadorExigenciaAdvogado().equals("")){
			
			indicadorExigenciaAdvogado = new Short ("" + filtrarCobrancaSituacaoActionForm.getIndicadorExigenciaAdvogado());
		}

		Short indicadorBloqueioParcelamento = null;
		
		if(filtrarCobrancaSituacaoActionForm.getIndicadorBloqueioParcelamento()!= null && !filtrarCobrancaSituacaoActionForm.getIndicadorBloqueioParcelamento().equals("")){
			
			indicadorBloqueioParcelamento = new Short ("" + filtrarCobrancaSituacaoActionForm.getIndicadorBloqueioParcelamento());
		}
		
		
		if (filtrarCobrancaSituacaoActionForm.getContaMotivoRevisao() != null && !filtrarCobrancaSituacaoActionForm.getContaMotivoRevisao().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)) {
			
			FiltroContaMotivoRevisao filtroContaMotivoRevisao = new FiltroContaMotivoRevisao();
			filtroContaMotivoRevisao.adicionarParametro(new ParametroSimples(FiltroContaMotivoRevisao.ID, filtrarCobrancaSituacaoActionForm.getContaMotivoRevisao()));
			
			Collection colecaoCobrancaSituacao = fachada.pesquisar(filtroContaMotivoRevisao, ContaMotivoRevisao.class.getName());
			
			if (colecaoCobrancaSituacao != null && !colecaoCobrancaSituacao.isEmpty()) {
				ContaMotivoRevisao contaMotivoRevisao = (ContaMotivoRevisao) Util.retonarObjetoDeColecao(colecaoCobrancaSituacao);
				cobrancaSituacaoParametros.setContaMotivoRevisao(contaMotivoRevisao);
			}
			
		}
		
		
		// seta os parametros que ser�o mostrados no relat�rio

		cobrancaSituacaoParametros.setId(id == null ? null : new Integer(
				id));
		cobrancaSituacaoParametros.setDescricao(filtrarCobrancaSituacaoActionForm.getDescricao());
		cobrancaSituacaoParametros.setIndicadorExigenciaAdvogado(indicadorExigenciaAdvogado);
		cobrancaSituacaoParametros.setIndicadorBloqueioParcelamento(indicadorBloqueioParcelamento);
		cobrancaSituacaoParametros.setIndicadorUso(indicadorUso);
		// Fim da parte que vai mandar os parametros para o relat�rio

		// cria uma inst�ncia da classe do relat�rio
	
		RelatorioManterCobrancaSituacao relatorioManterCobrancaSituacao = new RelatorioManterCobrancaSituacao(
				(Usuario)(httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));
		relatorioManterCobrancaSituacao.addParametro("filtroCobrancaSituacao",
				filtroCobrancaSituacao);
		relatorioManterCobrancaSituacao.addParametro("cobrancaSituacaoParametros",
				cobrancaSituacaoParametros);

		// chama o met�do de gerar relat�rio passando o c�digo da analise
		// como par�metro
		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");
		if (tipoRelatorio == null) {
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		relatorioManterCobrancaSituacao.addParametro("tipoFormatoRelatorio", Integer
				.parseInt(tipoRelatorio));
		try {
			retorno = processarExibicaoRelatorio(relatorioManterCobrancaSituacao,
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
