package gcom.gui.util.tabelaauxiliar.abreviada;

import gcom.arrecadacao.banco.Banco;
import gcom.atendimentopublico.ordemservico.EquipamentosEspeciais;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.gui.GcomAction;
import gcom.operacional.FonteCaptacao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * < <Descri��o da Classe>>
 * 
 * @author Administrador
 */
public class ExibirInserirTabelaAuxiliarAbreviadaAction extends GcomAction {
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

		//Prepara o retorno da A��o
		ActionForward retorno = actionMapping
				.findForward("inserirTabelaAuxiliarAbreviada");

		//Declara��o de objetos e tipos primitivos

		int tamMaxCampoDescricao = 20;
		int tamMaxCampoDescricaoAbreviada = 3;
		String descricao = "Descri��o";
		String descricaoAbreviada = "Descri��o Abreviada";

		//Cria a sess�o
		HttpSession sessao = httpServletRequest.getSession(false);

		String tela = httpServletRequest.getParameter("tela");

		SistemaParametro sistemaParametro = 
			(SistemaParametro) this.getFachada().pesquisarParametrosDoSistema();

		sessao.setAttribute("nomeSistema", sistemaParametro.getNomeEmpresa());

		DadosTelaTabelaAuxiliarAbreviada dados = DadosTelaTabelaAuxiliarAbreviada
				.obterDadosTelaTabelaAuxiliar(tela);

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

		sessao.setAttribute("dados", dados);
		sessao.setAttribute("titulo", dados.getTitulo());
		sessao.setAttribute("tabela", dados.getTabela());
		sessao.setAttribute("funcionalidadeTabelaAuxiliarAbreviadaInserir",dados.getFuncionalidadeTabelaAuxInserir());
		sessao.setAttribute("nomeParametroFuncionalidade", dados.getNomeParametroFuncionalidade());

		//Adiciona o objeto no request
		httpServletRequest.setAttribute("tamMaxCampoDescricao", new Integer(tamMaxCampoDescricao));
		httpServletRequest.setAttribute("tamMaxCampoDescricaoAbreviada",new Integer(tamMaxCampoDescricaoAbreviada));
		httpServletRequest.setAttribute("descricao", descricao);
		httpServletRequest.setAttribute("descricaoAbreviada",descricaoAbreviada);
		
		httpServletRequest.setAttribute("tela",tela);

		return retorno;
	}

}
