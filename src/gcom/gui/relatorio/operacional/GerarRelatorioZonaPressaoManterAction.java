package gcom.gui.relatorio.operacional;

import gcom.fachada.Fachada;
import gcom.gui.operacional.FiltrarZonaPressaoActionForm;
import gcom.operacional.DistritoOperacional;
import gcom.operacional.FiltroDistritoOperacional;
import gcom.operacional.FiltroZonaPressao;
import gcom.operacional.ZonaPressao;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.operacional.RelatorioManterZonaPressao;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.SistemaException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import java.util.Collection;
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

public class GerarRelatorioZonaPressaoManterAction extends
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
		
		Fachada fachada = Fachada.getInstancia();

		FiltrarZonaPressaoActionForm filtrarZonaPressaoActionForm = (FiltrarZonaPressaoActionForm) actionForm;

		FiltroZonaPressao filtroZonaPressao = (FiltroZonaPressao) sessao
				.getAttribute("filtroZonaPressao");

		// Inicio da parte que vai mandar os parametros para o relat�rio

		ZonaPressao zonaPressaoParametros = new ZonaPressao();

		String id = null;

		String idZonaPressaoPesquisar = (String) filtrarZonaPressaoActionForm
				.getId();

		if (idZonaPressaoPesquisar != null && !idZonaPressaoPesquisar.equals("")) {
			id = idZonaPressaoPesquisar;
		}

		Short indicadorDeUso = null;

		if (filtrarZonaPressaoActionForm.getIndicadorUso() != null
				&& !filtrarZonaPressaoActionForm.getIndicadorUso().equals("")) {

			indicadorDeUso = new Short(""
					+ filtrarZonaPressaoActionForm.getIndicadorUso());
		}
		
		// seta os parametros que ser�o mostrados no relat�rio

		zonaPressaoParametros.setId(id == null ? null : new Integer(
				id));
		zonaPressaoParametros.setDescricaoZonaPressao(""
				+ filtrarZonaPressaoActionForm.getDescricao());
		zonaPressaoParametros.setDescricaoAbreviada(""
				+ filtrarZonaPressaoActionForm.getDescricaoAbreviada());
		
		if (filtrarZonaPressaoActionForm.getDistritoOperacionalID() != null && !filtrarZonaPressaoActionForm.getDistritoOperacionalID().equals("")) {
			
			FiltroDistritoOperacional filtroDistritoOperacional = new FiltroDistritoOperacional();
			filtroDistritoOperacional.adicionarParametro(new ParametroSimples(FiltroDistritoOperacional.ID, filtrarZonaPressaoActionForm.getDistritoOperacionalID()));
			
			Collection colecaoDistritoOperacional = fachada.pesquisar(filtroDistritoOperacional, DistritoOperacional.class.getName());
			
			if (colecaoDistritoOperacional != null && !colecaoDistritoOperacional.isEmpty()) {
				DistritoOperacional distritoOperacional = (DistritoOperacional) Util.retonarObjetoDeColecao(colecaoDistritoOperacional);
				zonaPressaoParametros.setDistritoOperacional(distritoOperacional);
			}
			
		}
		
		zonaPressaoParametros.setIndicadorUso(indicadorDeUso);

		// Fim da parte que vai mandar os parametros para o relat�rio

		// cria uma inst�ncia da classe do relat�rio
		RelatorioManterZonaPressao relatorioManterZonaPressao = new RelatorioManterZonaPressao(
				(Usuario)(httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));
		relatorioManterZonaPressao.addParametro("filtroZonaPressao",
				filtroZonaPressao);
		relatorioManterZonaPressao.addParametro("zonaPressaoParametros",
				zonaPressaoParametros);

		// chama o met�do de gerar relat�rio passando o c�digo da analise
		// como par�metro
		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");
		if (tipoRelatorio == null) {
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		relatorioManterZonaPressao.addParametro("tipoFormatoRelatorio", Integer
				.parseInt(tipoRelatorio));
		try {
			retorno = processarExibicaoRelatorio(relatorioManterZonaPressao,
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
