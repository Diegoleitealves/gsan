package gcom.gui.atendimentopublico.ordemservico;

import gcom.atendimentopublico.ordemservico.Equipe;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 
 * Permite exibir uma lista com as resolu��es de diretoria retornadas do
 * FiltrarResolucaoDiretoriaAction ou ir para o
 * ExibirAtualizarResolucaoDiretoriaAction
 * 
 * @author Rafael Corr�a
 * @since 31/03/2006
 */
public class ExibirManterEquipeAction extends GcomAction {

	/**
	 * 
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirManterEquipe");

		// Obt�m a inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);
		
		sessao.removeAttribute("equipeAtualizar");

		// Recupera os par�metros da sess�o para ser efetuada a pesquisa
		String idEquipe = (String) sessao.getAttribute("idEquipe");
		String nome = (String) sessao.getAttribute("nome");
		String placa = (String) sessao.getAttribute("placa");
		String cargaTrabalho = (String) sessao.getAttribute("cargaTrabalho");
		String idUnidade = (String) sessao.getAttribute("idUnidade");
		String idFuncionario = (String) sessao.getAttribute("idFuncionario");
		String idPerfilServico = (String) sessao
				.getAttribute("idPerfilServico");
		String indicadorUso = (String) sessao.getAttribute("indicadorUso");
		String indicadorProgramacaoAutomatica = (String) sessao.getAttribute("indicadorProgramacaoAutomatica");
		String tipoPesquisa = (String) sessao.getAttribute("tipoPesquisa");
		String codigoDdd = (String) sessao.getAttribute("codigoDdd");
		String numeroTelefone = (String) sessao.getAttribute("numeroTelefone");
		String numeroImei = (String) sessao.getAttribute("numeroImei");
		String equipamentosEspeciasId = (String) sessao.getAttribute("equipamentosEspeciasId");
		String cdUsuarioRespExcServico = (String) sessao.getAttribute("cdUsuarioRespExcServico");

		
		// Aciona o controle de pagina��o para que sejam pesquisados apenas
		// os registros que aparecem na p�gina
		Integer totalRegistros = fachada.pesquisarEquipesCount(idEquipe, nome,
				placa, cargaTrabalho, codigoDdd, numeroTelefone, numeroImei, idUnidade, idFuncionario,
				idPerfilServico, indicadorUso, tipoPesquisa,equipamentosEspeciasId, cdUsuarioRespExcServico, indicadorProgramacaoAutomatica);

		retorno = this.controlarPaginacao(httpServletRequest, retorno,
				totalRegistros);

		Collection colecaoEquipes = fachada.pesquisarEquipes(idEquipe, nome,
				placa, cargaTrabalho, codigoDdd, numeroTelefone, numeroImei, idUnidade, idFuncionario,
				idPerfilServico, indicadorUso, tipoPesquisa, (Integer) httpServletRequest
						.getAttribute("numeroPaginasPesquisa"),equipamentosEspeciasId, cdUsuarioRespExcServico, indicadorProgramacaoAutomatica);

		// Verifica se a cole��o retornada pela pesquisa � nula, em caso
		// afirmativo comunica ao usu�rio que n�o existe nenhuma equipe
		// cadastrada para a pesquisa efetuada e em caso negativo e se
		// atender a algumas condi��es seta o retorno para o
		// ExibirAtualizarEquipeAction, se n�o atender manda a
		// cole��o pelo request para ser recuperado e exibido pelo jsp.
		if (colecaoEquipes != null && !colecaoEquipes.isEmpty()) {

			// Verifica se a cole��o cont�m apenas um objeto, se est� retornando
			// da pagina��o (devido ao esquema de pagina��o de 10 em 10 faz uma
			// nova busca), evitando, assim, que caso haja 11 elementos no
			// retorno da pesquisa e o usu�rio selecione o link para ir para a
			// segunda p�gina ele n�o v� para tela de atualizar.
			if (colecaoEquipes.size() == 1
					&& (httpServletRequest.getParameter("page.offset") == null || httpServletRequest
							.getParameter("page.offset").equals("1"))) {
				// Verifica se o usu�rio marcou o checkbox de atualizar no jsp
				// equipe_filtrar. Caso todas as condi��es sejam
				// verdadeiras seta o retorno para o
				// ExibirAtualizarEquipeAction e em caso negativo
				// manda a cole��o pelo request.
				if (sessao.getAttribute("atualizar") != null) {
					retorno = actionMapping
							.findForward("exibirAtualizarEquipe");
					Equipe equipe = (Equipe) colecaoEquipes.iterator().next();
					sessao.setAttribute("equipe", equipe);
				} else {
					httpServletRequest.setAttribute("colecaoEquipes",
							colecaoEquipes);
				}
			} else {
				httpServletRequest.setAttribute("colecaoEquipes",
						colecaoEquipes);
			}
		} else {
			// Caso a pesquisa n�o retorne nenhum objeto comunica ao usu�rio;
			throw new ActionServletException("atencao.pesquisa.nenhumresultado");
		}

		return retorno;

	}

}
