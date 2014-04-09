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
public class ExibirFiltrarTabelaAuxiliarFaixaInteiroAction extends GcomAction{
	
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
				.findForward("filtrarTabelaAuxiliarFaixaInteiro");

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

		int tamMaxCampoMaior = 4;
		int tamMaxCampoMenor = 4;

		DadosTelaTabelaAuxiliarFaixaInteiro dados = (DadosTelaTabelaAuxiliarFaixaInteiro) DadosTelaTabelaAuxiliarFaixaInteiro
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
		sessao.setAttribute("tamMaxCampoMaior", tamMaxCampoMaior);
		sessao.setAttribute("tamMaxCampoMenor",tamMaxCampoMenor);

		sessao.setAttribute("funcionalidadeTabelaAuxiliarFaixaInteiroFiltrar", dados
				.getFuncionalidadeTabelaFaixaInteiroFiltrar());
		
		//Adiciona o objeto no request
		sessao.setAttribute("tamMaxCampoMenor", new Integer(
				tamMaxCampoMenor));
		sessao.setAttribute("tamMaxCampoMenor",
				new Integer(tamMaxCampoMenor));
		
		httpServletRequest.setAttribute("tela",tela);
		

		return retorno;
	}

}
