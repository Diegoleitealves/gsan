package gcom.gui.relatorio.atendimentopublico;

import gcom.atendimentopublico.ligacaoesgoto.FiltroLigacaoEsgotoPerfil;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoPerfil;
import gcom.gui.atendimentopublico.FiltrarPerfilLigacaoEsgotoActionForm;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.atendimentopublico.RelatorioManterPerfilLigacaoEsgoto;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.SistemaException;
import gcom.util.Util;

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

public class GerarRelatorioPerfilLigacaoEsgotoManterAction extends
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

		FiltrarPerfilLigacaoEsgotoActionForm filtrarPerfilLigacaoEsgotoActionForm = (FiltrarPerfilLigacaoEsgotoActionForm) actionForm;


		FiltroLigacaoEsgotoPerfil filtroLigacaoEsgotoPerfil= (FiltroLigacaoEsgotoPerfil) sessao
				.getAttribute("filtroLigacaoEsgotoPerfil");

		// Inicio da parte que vai mandar os parametros para o relat�rio

		LigacaoEsgotoPerfil ligacaoEsgotoPerfilParametros = new LigacaoEsgotoPerfil();

		String id = null;
			if(filtrarPerfilLigacaoEsgotoActionForm.getId()!= null && !filtrarPerfilLigacaoEsgotoActionForm.getId().equals("")){
			
			id =  filtrarPerfilLigacaoEsgotoActionForm.getId();
		}


		Short indicadordeUso = null;
		
		if(filtrarPerfilLigacaoEsgotoActionForm.getIndicadorUso()!= null && !filtrarPerfilLigacaoEsgotoActionForm.getIndicadorUso().equals("")){
			
			indicadordeUso = new Short ("" + filtrarPerfilLigacaoEsgotoActionForm.getIndicadorUso());
		}
		
		if(filtrarPerfilLigacaoEsgotoActionForm.getPercentualEsgotoConsumidaColetada() != null 
				&& !filtrarPerfilLigacaoEsgotoActionForm.getPercentualEsgotoConsumidaColetada().equals("")){
			ligacaoEsgotoPerfilParametros.setPercentualEsgotoConsumidaColetada(Util.formatarMoedaRealparaBigDecimal(filtrarPerfilLigacaoEsgotoActionForm.getPercentualEsgotoConsumidaColetada()));
		} 
		
		// seta os parametros que ser�o mostrados no relat�rio

		ligacaoEsgotoPerfilParametros.setId(id == null ? null : new Integer(
				id));
		ligacaoEsgotoPerfilParametros.setDescricao(""
				+ filtrarPerfilLigacaoEsgotoActionForm.getDescricao());
		
		
		
		ligacaoEsgotoPerfilParametros.setIndicadorUso(indicadordeUso);
		
		// Fim da parte que vai mandar os parametros para o relat�rio

		// cria uma inst�ncia da classe do relat�rio
		RelatorioManterPerfilLigacaoEsgoto relatorioManterPerfilLigacaoEsgoto = new RelatorioManterPerfilLigacaoEsgoto(
				(Usuario)(httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));
		relatorioManterPerfilLigacaoEsgoto.addParametro("filtroLigacaoEsgotoPerfil",
				filtroLigacaoEsgotoPerfil);
		relatorioManterPerfilLigacaoEsgoto.addParametro("ligacaoEsgotoPerfilParametros",
				ligacaoEsgotoPerfilParametros);

		// chama o met�do de gerar relat�rio passando o c�digo da analise
		// como par�metro
		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");
		if (tipoRelatorio == null) {
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		relatorioManterPerfilLigacaoEsgoto.addParametro("tipoFormatoRelatorio", Integer
				.parseInt(tipoRelatorio));
		try {
			retorno = processarExibicaoRelatorio(relatorioManterPerfilLigacaoEsgoto,
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
