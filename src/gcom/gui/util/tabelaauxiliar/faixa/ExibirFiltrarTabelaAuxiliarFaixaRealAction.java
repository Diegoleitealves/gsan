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
import org.apache.struts.validator.DynaValidatorForm;

/**
 * @author R�mulo Aur�lio
 *
 */
public class ExibirFiltrarTabelaAuxiliarFaixaRealAction extends GcomAction{
	
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

		ActionForward retorno = actionMapping
				.findForward("filtrarTabelaAuxiliarFaixaReal");

		//Cria a sess�o
		HttpSession sessao = httpServletRequest.getSession(false);

		String tela=null;
		
		if(httpServletRequest.getParameter("tela")!=null){
		tela = httpServletRequest.getParameter("tela");
		sessao.setAttribute("tela",tela);
		}else{
			if(sessao.getAttribute("tela")!=null){
				tela =(String) sessao.getAttribute("tela"); 	
			}
			
		}
		//      Obt�m a inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		DynaValidatorForm pesquisarActionForm = (DynaValidatorForm) actionForm;
		
		pesquisarActionForm.set("atualizar","1");
		
		SistemaParametro sistemaParametro = (SistemaParametro) fachada
				.pesquisarParametrosDoSistema();

		sessao.setAttribute("nomeSistema", sistemaParametro.getNomeEmpresa());

		String descricao = "Descri��o";
		String descricaoAbreviada = "Descri��o Abreviada";

		int tamMaxCampoVolumeMaior = 6;
		int tamMaxCampoVolumeMenor = 6;

		DadosTelaTabelaAuxiliarFaixaReal dados = (DadosTelaTabelaAuxiliarFaixaReal) DadosTelaTabelaAuxiliarFaixaReal
				.obterDadosTelaTabelaAuxiliar(tela);


		sessao.setAttribute("dados", dados);
		sessao.setAttribute("titulo", dados.getTitulo());
		sessao.setAttribute("tabela", dados.getTabelaAuxiliarAbstrata());
		sessao.setAttribute("pacoteNomeObjeto", dados.getTabelaAuxiliarAbstrata().getClass().getName());
		sessao.setAttribute("funcionalidadeTabelaAuxiliarAbreviadaInserir",
				dados.getFuncionalidadeTabelaAuxInserir());
		sessao.setAttribute("nomeParametroFuncionalidade", dados
				.getNomeParametroFuncionalidade());
		sessao.setAttribute("descricao",descricao);
		sessao.setAttribute("descricaoAbreviada",descricaoAbreviada);
		sessao.setAttribute("tamMaxCampoVolumeMaior", tamMaxCampoVolumeMaior);
		sessao.setAttribute("tamMaxCampoVolumeMenor",tamMaxCampoVolumeMenor);

		sessao.setAttribute("funcionalidadeTabelaAuxiliarFaixaRealFiltrar", dados
				.getFuncionalidadeTabelaFaixaRealFiltrar());
		
		//Adiciona o objeto no request
		sessao.setAttribute("tamMaxCampoVolumeMaior", new Integer(
				tamMaxCampoVolumeMaior));
		sessao.setAttribute("tamMaxCampoVolumeMenor",
				new Integer(tamMaxCampoVolumeMenor));
		
		//seta o parametro para o controle de acesso a fucionalidade ou opera��o
		httpServletRequest.setAttribute("tela",tela);
		

		return retorno;
	}

}
