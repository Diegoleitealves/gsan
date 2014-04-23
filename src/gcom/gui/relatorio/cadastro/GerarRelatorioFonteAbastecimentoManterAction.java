package gcom.gui.relatorio.cadastro;

import gcom.cadastro.imovel.FiltroFonteAbastecimento;
import gcom.cadastro.imovel.FonteAbastecimento;
import gcom.gui.cadastro.FiltrarFonteAbastecimentoActionForm;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.cadastro.RelatorioManterFonteAbastecimento;
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

public class GerarRelatorioFonteAbastecimentoManterAction extends
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

		FiltrarFonteAbastecimentoActionForm filtrarFonteAbastecimentoActionForm = (FiltrarFonteAbastecimentoActionForm) actionForm;

		FiltroFonteAbastecimento filtroFonteAbastecimento= (FiltroFonteAbastecimento) sessao
				.getAttribute("filtroFonteAbastecimento");

		// Inicio da parte que vai mandar os parametros para o relat�rio

		FonteAbastecimento fonteAbastecimentoParametros = new FonteAbastecimento();

		String id = null;

		String idFonteAbastecimentoPesquisar = (String) filtrarFonteAbastecimentoActionForm.getId();

		if (idFonteAbastecimentoPesquisar != null && !idFonteAbastecimentoPesquisar.equals("")) {
			id = idFonteAbastecimentoPesquisar;
		}
		
		Short indicadorUso = null;
		
		if(filtrarFonteAbastecimentoActionForm.getIndicadorUso()!= null && !filtrarFonteAbastecimentoActionForm.getIndicadorUso().equals("")){
			
			indicadorUso = new Short ("" + filtrarFonteAbastecimentoActionForm.getIndicadorUso());
		}
		
		Short indicadorCalcularVolumeFixo = null;
		
		if(filtrarFonteAbastecimentoActionForm.getIndicadorCalcularVolumeFixo() != null && !filtrarFonteAbastecimentoActionForm.getIndicadorCalcularVolumeFixo().equals("")){
			
			indicadorCalcularVolumeFixo = new Short ("" + filtrarFonteAbastecimentoActionForm.getIndicadorCalcularVolumeFixo());
		}
		
		// seta os parametros que ser�o mostrados no relat�rio

		fonteAbastecimentoParametros.setId(id == null ? null : new Integer(
				id));
		fonteAbastecimentoParametros.setDescricao(filtrarFonteAbastecimentoActionForm.getDescricao());
		fonteAbastecimentoParametros.setDescricaoAbreviada(filtrarFonteAbastecimentoActionForm.getDescricaoAbreviada());
		fonteAbastecimentoParametros.setIndicadorUso(indicadorUso);
		fonteAbastecimentoParametros.setIndicadorCalcularVolumeFixo(indicadorCalcularVolumeFixo);
		// Fim da parte que vai mandar os parametros para o relat�rio

		// cria uma inst�ncia da classe do relat�rio
	
		RelatorioManterFonteAbastecimento relatorioManterFonteAbastecimento = new RelatorioManterFonteAbastecimento(
				(Usuario)(httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));
		relatorioManterFonteAbastecimento.addParametro("filtroFonteAbastecimento",
				filtroFonteAbastecimento);
		relatorioManterFonteAbastecimento.addParametro("fonteAbastecimentoParametros",
				fonteAbastecimentoParametros);

		// chama o met�do de gerar relat�rio passando o c�digo da analise
		// como par�metro
		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");
		if (tipoRelatorio == null) {
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		relatorioManterFonteAbastecimento.addParametro("tipoFormatoRelatorio", Integer
				.parseInt(tipoRelatorio));
		try {
			retorno = processarExibicaoRelatorio(relatorioManterFonteAbastecimento,
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
