package gcom.gui.util.tabelaauxiliar.abreviada;

import gcom.arrecadacao.banco.Banco;
import gcom.atendimentopublico.ordemservico.EquipamentosEspeciais;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.gui.GcomAction;
import gcom.operacional.FonteCaptacao;
import gcom.util.ConstantesSistema;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

/**
 * < <Descri��o da Classe>>
 * 
 * @author Administrador
 */
public class ExibirFiltrarTabelaAuxiliarAbreviadaAction extends GcomAction {
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

		ActionForward retorno = actionMapping.findForward("filtrarTabelaAuxiliarAbreviada");

		//Cria a sess�o
		HttpSession sessao = httpServletRequest.getSession(false);

		String tela = httpServletRequest.getParameter("tela");
		sessao.setAttribute("tela",tela);

		DynaValidatorForm pesquisarActionForm = (DynaValidatorForm) actionForm;
		
		pesquisarActionForm.set("atualizar","1");
		
		SistemaParametro sistemaParametro = 
			(SistemaParametro) this.getFachada().pesquisarParametrosDoSistema();

		sessao.setAttribute("nomeSistema", sistemaParametro.getNomeEmpresa());

		String descricao = "Descri��o";
		String descricaoAbreviada = "Descri��o Abreviada";
		
		int tamMaxCampoDescricao = 20;
		int tamMaxCampoDescricaoAbreviada = 3;

		DadosTelaTabelaAuxiliarAbreviada dados = 
			DadosTelaTabelaAuxiliarAbreviada.obterDadosTelaTabelaAuxiliar(tela);
		
		if (dados.getTabela() instanceof FonteCaptacao) {
			tamMaxCampoDescricao = 30;
			tamMaxCampoDescricaoAbreviada = 10;
		}else if (dados.getTabela() instanceof Banco) {

			descricao = "Nome";
			descricaoAbreviada = "Nome Abreviado";
		}
		
		if (dados.getTabela() instanceof EquipamentosEspeciais) {
			tamMaxCampoDescricao = 40;
			tamMaxCampoDescricaoAbreviada = 8;
		}else if (dados.getTabela() instanceof Banco) {

			descricao = "Nome";
			descricaoAbreviada = "Nome Abreviado";
		}
		
		String primeiraVez = httpServletRequest.getParameter("menu");
		
		//Verifica se a chamada veio do menu 
		if (primeiraVez != null && !primeiraVez.equals("")) {
			sessao.setAttribute("atualizar", "1");
			pesquisarActionForm.set("tipoPesquisa", ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString());
		}

		sessao.setAttribute("dados", dados);
		sessao.setAttribute("titulo", dados.getTitulo());
		sessao.setAttribute("tabela", dados.getTabela());
		sessao.setAttribute("funcionalidadeTabelaAuxiliarAbreviadaInserir",dados.getFuncionalidadeTabelaAuxInserir());
		sessao.setAttribute("nomeParametroFuncionalidade", dados.getNomeParametroFuncionalidade());
		sessao.setAttribute("descricao",descricao);
		sessao.setAttribute("descricaoAbreviada",descricaoAbreviada);
		sessao.setAttribute("tamMaxCampoDescricao", tamMaxCampoDescricao);
		sessao.setAttribute("tamMaxCampoDescricaoAbreviada",tamMaxCampoDescricaoAbreviada);

		sessao.setAttribute("funcionalidadeTabelaAuxiliarAbreviadaFiltrar", dados.getFuncionalidadeTabelaAuxFiltrar());
		sessao.setAttribute("pacoteNomeObjeto", dados.getTabelaAuxiliar().getClass().getName());
		
		
		//Adiciona o objeto no request
		httpServletRequest.setAttribute("tamMaxCampoDescricao", new Integer(tamMaxCampoDescricao));
		httpServletRequest.setAttribute("tamMaxCampoDescricaoAbreviada",new Integer(tamMaxCampoDescricaoAbreviada));
		httpServletRequest.setAttribute("descricao", descricao);
		httpServletRequest.setAttribute("descricaoAbreviada",descricaoAbreviada);
		httpServletRequest.setAttribute("tela",tela);
		
		sessao.removeAttribute("tipoPesquisa");

		return retorno;
	}
}
