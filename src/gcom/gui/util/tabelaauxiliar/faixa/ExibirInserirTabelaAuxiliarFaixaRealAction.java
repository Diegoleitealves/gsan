package gcom.gui.util.tabelaauxiliar.faixa;

import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author gcom
 * 
 */
public class ExibirInserirTabelaAuxiliarFaixaRealAction extends GcomAction {
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

		// Prepara o retorno da A��o
		ActionForward retorno = actionMapping
				.findForward("inserirTabelaAuxiliarFaixaReal");

		// Pega o parametro passado no request
		// String tela = (String) httpServletRequest.getParameter("tela");

		// Declara��o de objetos e tipos primitivos

		int tamMaxCampoFaixaInicial = 6;
		int tamMaxCampoFaixaLFinal = 6;
		int tamMaxCampoFaixaCompleta = 6;

		// Cria a sess�o
		HttpSession sessao = httpServletRequest.getSession(false);

		String tela = httpServletRequest.getParameter("tela");
		// tempo da sess�o
		// sessao.setMaxInactiveInterval(1000);
		// Adiciona os objetos na sess�o

		// Obt�m a inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		SistemaParametro sistemaParametro = (SistemaParametro) fachada
				.pesquisarParametrosDoSistema();

		sessao.setAttribute("nomeSistema", sistemaParametro.getNomeEmpresa());

		DadosTelaTabelaAuxiliarFaixaReal dados = (DadosTelaTabelaAuxiliarFaixaReal) DadosTelaTabelaAuxiliarFaixaReal
				.obterDadosTelaTabelaAuxiliar(tela);


		sessao.setAttribute("dados", dados);
		sessao.setAttribute("titulo", dados.getTitulo());
		sessao.setAttribute("tabela", dados.getTabelaAuxiliarAbstrata());
		sessao.setAttribute("funcionalidadeTabelaAuxiliarFaixaRealInserir",
				dados.getFuncionalidadeTabelaFaixaRealInserir());
		sessao.setAttribute("nomeParametroFuncionalidade", dados
				.getNomeParametroFuncionalidade());

		// Adiciona o objeto no request
		httpServletRequest.setAttribute("tamMaxCampoFaixaInicial", new Integer(
				tamMaxCampoFaixaInicial));
		httpServletRequest.setAttribute("tamMaxCampoFaixaLFinal", new Integer(
				tamMaxCampoFaixaLFinal));
		httpServletRequest.setAttribute("tamMaxCampoFaixaCompleta",
				new Integer(tamMaxCampoFaixaCompleta));
		
		httpServletRequest.setAttribute("tela",tela);

		return retorno;
	}

}
