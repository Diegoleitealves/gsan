package gcom.gui.util.tabelaauxiliar.abreviadatipo;

import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.operacional.FiltroSistemaAbastecimento;
import gcom.operacional.SistemaAbastecimento;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <Esse componente serve para SetorAbastecimento e ZonaAbastecimento, sendo o tipo SistemaAbastecimento>
 * 
 * @author Administrador
 */
public class ExibirInserirTabelaAuxiliarAbreviadaTipoAction extends GcomAction {
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
		ActionForward retorno = actionMapping.findForward("inserirTabelaAuxiliarAbreviadaTipo");
		
		// Cria a sess�o
		HttpSession sessao = this.getSessao(httpServletRequest);

		String tela = httpServletRequest.getParameter("tela");

		// tempo da sess�o
		sessao.setMaxInactiveInterval(1000);
		
		// Adiciona os objetos na sess�o

		// Obt�m a inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		SistemaParametro sistemaParametro = 
			(SistemaParametro) fachada.pesquisarParametrosDoSistema();

		sessao.setAttribute("nomeSistema", sistemaParametro.getNomeEmpresa());

		// Declara��o de objetos e tipos primitivos
		String descricao = "Descri��o";
		String descricaoAbreviada = "Descri��o Abreviada";
				
		DadosTelaTabelaAuxiliarAbreviadaTipo dados = 
			DadosTelaTabelaAuxiliarAbreviadaTipo.obterDadosTelaTabelaAuxiliar(tela);
		
		if (dados.getNomeParametroFuncionalidade().equals("setorAbastecimento")) {
			
			Collection colecaoObject = new ArrayList();	
		
			FiltroSistemaAbastecimento filtro = new FiltroSistemaAbastecimento();
			filtro.adicionarParametro(
				new ParametroSimples(
					FiltroSistemaAbastecimento.INDICADOR_USO, 
					ConstantesSistema.INDICADOR_USO_ATIVO));
			sessao.setAttribute("indicadorUso",1);
			
			colecaoObject = 
				this.getFachada().pesquisar(filtro,SistemaAbastecimento.class.getName());
			
			sessao.setAttribute("colecaoObject", colecaoObject);
		}
		if (dados.getNomeParametroFuncionalidade().equals("zonaAbastecimento")) {
			
			Collection colecaoObject = new ArrayList();	
			
			FiltroSistemaAbastecimento filtro = new FiltroSistemaAbastecimento();
			filtro.adicionarParametro(
				new ParametroSimples(
					FiltroSistemaAbastecimento.INDICADOR_USO, 
					ConstantesSistema.INDICADOR_USO_ATIVO));
			sessao.setAttribute("indicadorUso",1);

			colecaoObject = 
				this.getFachada().pesquisar(filtro,SistemaAbastecimento.class.getName());
			
			sessao.setAttribute("colecaoObject", colecaoObject);
		}

		// Adiciona o objeto na sessao
		sessao.setAttribute("dados", dados);
		sessao.setAttribute("titulo", dados.getTitulo());
		sessao.setAttribute("tabela", dados.getTabela());
		
		sessao.setAttribute("funcionalidadeTabelaAuxiliarAbreviadaTipoInserir",
			dados.getFuncionalidadeTabelaAuxInserir());
		sessao.setAttribute("nomeParametroFuncionalidade", 
			dados.getNomeParametroFuncionalidade());

		// Adiciona o objeto no request
		httpServletRequest.setAttribute("descricao", descricao);
		httpServletRequest.setAttribute("descricaoAbreviada",descricaoAbreviada);
		
		return retorno;
	}

}
