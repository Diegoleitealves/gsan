package gcom.gui.relatorio.cadastro;

import gcom.cadastro.funcionario.FiltroFuncionarioCargo;
import gcom.cadastro.funcionario.FuncionarioCargo;
import gcom.gui.cadastro.FiltrarCargoFuncionarioActionForm;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.cadastro.RelatorioManterCargoFuncionario;
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

public class GerarRelatorioCargoFuncionarioManterAction extends
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

		FiltrarCargoFuncionarioActionForm filtrarCargoFuncionarioActionForm = (FiltrarCargoFuncionarioActionForm) actionForm;

		FiltroFuncionarioCargo filtroFuncionarioCargo= (FiltroFuncionarioCargo) sessao
				.getAttribute("filtroFuncionarioCargo");

		// Inicio da parte que vai mandar os parametros para o relat�rio

		FuncionarioCargo funcionarioCargoParametros = new FuncionarioCargo();

		String codigo = null;

		String codigoFuncionarioCargoPesquisar = (String) filtrarCargoFuncionarioActionForm.getCodigo();

		if (codigoFuncionarioCargoPesquisar != null && !codigoFuncionarioCargoPesquisar.equals("")) {
			codigo = codigoFuncionarioCargoPesquisar;
		}
		
		Short indicadorUso = null;
		
		if(filtrarCargoFuncionarioActionForm.getIndicadorUso()!= null && !filtrarCargoFuncionarioActionForm.getIndicadorUso().equals("")){
			
			indicadorUso = new Short ("" + filtrarCargoFuncionarioActionForm.getIndicadorUso());
		}
		
		
		// seta os parametros que ser�o mostrados no relat�rio

		funcionarioCargoParametros.setCodigo(codigo == null ? null : new Integer(
				codigo));
		funcionarioCargoParametros.setDescricao(filtrarCargoFuncionarioActionForm.getDescricao());
		funcionarioCargoParametros.setDescricaoAbreviada(filtrarCargoFuncionarioActionForm.getDescricaoAbreviada());
		funcionarioCargoParametros.setIndicadorUso(indicadorUso);
		
		// Fim da parte que vai mandar os parametros para o relat�rio

		// cria uma inst�ncia da classe do relat�rio
	
		RelatorioManterCargoFuncionario relatorioManterCargoFuncionario = new RelatorioManterCargoFuncionario(
				(Usuario)(httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));
		relatorioManterCargoFuncionario.addParametro("filtroFuncionarioCargo",
				filtroFuncionarioCargo);
		relatorioManterCargoFuncionario.addParametro("funcionarioCargoParametros",
				funcionarioCargoParametros);

		// chama o met�do de gerar relat�rio passando o c�digo da analise
		// como par�metro
		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");
		if (tipoRelatorio == null) {
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		relatorioManterCargoFuncionario.addParametro("tipoFormatoRelatorio", Integer
				.parseInt(tipoRelatorio));
		try {
			retorno = processarExibicaoRelatorio(relatorioManterCargoFuncionario,
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
