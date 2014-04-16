package gcom.gui.relatorio.operacional;

import gcom.cadastro.unidade.FiltroUnidadeOrganizacional;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.fachada.Fachada;
import gcom.gui.operacional.FiltrarDivisaoEsgotoActionForm;
import gcom.operacional.DivisaoEsgoto;
import gcom.operacional.FiltroDivisaoEsgoto;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.operacional.RelatorioManterDivisaoEsgoto;
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
 * @author Arthur Carvalho
 * @version 1.0
 */

public class GerarRelatorioDivisaoEsgotoManterAction extends
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

		FiltrarDivisaoEsgotoActionForm filtrarDivisaoEsgotoActionForm = (FiltrarDivisaoEsgotoActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		FiltroDivisaoEsgoto filtroDivisaoEsgoto= (FiltroDivisaoEsgoto) sessao
				.getAttribute("filtroDivisaoEsgoto");

		// Inicio da parte que vai mandar os parametros para o relat�rio

		DivisaoEsgoto divisaoEsgotoParametros = new DivisaoEsgoto();

		// seta os parametros que serao mostrados no relatorio
		
		String id = null;

		String idDivisaoEsgotoPesquisar = (String) filtrarDivisaoEsgotoActionForm.getId();

		if (idDivisaoEsgotoPesquisar != null && !idDivisaoEsgotoPesquisar.equals("")) {
		id = idDivisaoEsgotoPesquisar;
		}


		Short indicadorDeUso = null;
		
		if(filtrarDivisaoEsgotoActionForm.getIndicadorUso() != null && !filtrarDivisaoEsgotoActionForm.getIndicadorUso().equals("")){
			
			indicadorDeUso = new Short(""
					+ filtrarDivisaoEsgotoActionForm.getIndicadorUso());

		}
				
//		divisaoEsgotoParametros.setId(filtrarDivisaoEsgotoActionForm.getId() == null ? null : new Integer(
//				id));
		
		if(filtrarDivisaoEsgotoActionForm.getId() == null || filtrarDivisaoEsgotoActionForm.getId().equals("")){
			divisaoEsgotoParametros.setId(null);
		} else {
			divisaoEsgotoParametros.setId(new Integer (id));
		}
		
		divisaoEsgotoParametros.setDescricao(""
				+ filtrarDivisaoEsgotoActionForm.getDescricao());
		divisaoEsgotoParametros.setIndicadorUso(indicadorDeUso);
		
		if (filtrarDivisaoEsgotoActionForm.getUnidadeOrganizacionalId() != null && !filtrarDivisaoEsgotoActionForm.getUnidadeOrganizacionalId().equals("")) {
			
			FiltroUnidadeOrganizacional filtroUnidadeOrganizacional = new FiltroUnidadeOrganizacional();
			filtroUnidadeOrganizacional.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID, filtrarDivisaoEsgotoActionForm.getUnidadeOrganizacionalId()));
			
			Collection colecaoUnidadeOrganizacional = fachada.pesquisar(filtroUnidadeOrganizacional, UnidadeOrganizacional.class.getName());
			
			if (colecaoUnidadeOrganizacional != null && !colecaoUnidadeOrganizacional.isEmpty()) {
				UnidadeOrganizacional unidadeOrganizacional = (UnidadeOrganizacional) Util.retonarObjetoDeColecao(colecaoUnidadeOrganizacional);
				divisaoEsgotoParametros.setUnidadeOrganizacional(unidadeOrganizacional);
			}
			
		}		
		
		// Fim da parte que vai mandar os parametros para o relat�rio

		// cria uma inst�ncia da classe do relat�rio
		RelatorioManterDivisaoEsgoto relatorioManterDivisaoEsgoto= new RelatorioManterDivisaoEsgoto(
				(Usuario)(httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));
		relatorioManterDivisaoEsgoto.addParametro("filtroDivisaoEsgoto",
				filtroDivisaoEsgoto);
		relatorioManterDivisaoEsgoto.addParametro("divisaoEsgotoParametros",
				divisaoEsgotoParametros);

		// chama o met�do de gerar relat�rio passando o c�digo da analise
		// como par�metro
		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");
		if (tipoRelatorio == null) {
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		relatorioManterDivisaoEsgoto.addParametro("tipoFormatoRelatorio", Integer
				.parseInt(tipoRelatorio));
		try {
			retorno = processarExibicaoRelatorio(relatorioManterDivisaoEsgoto,
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
