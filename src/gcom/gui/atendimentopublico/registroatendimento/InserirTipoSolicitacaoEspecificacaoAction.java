package gcom.gui.atendimentopublico.registroatendimento;

import gcom.atendimentopublico.registroatendimento.SolicitacaoTipo;
import gcom.atendimentopublico.registroatendimento.SolicitacaoTipoGrupo;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action respons�vel pela inser��o de tipo da solicita��o com especifica��o
 * 
 * @author S�vio Luiz
 * @created 01 de Agosto de 2006
 */
public class InserirTipoSolicitacaoEspecificacaoAction extends GcomAction {
	/**
	 * Description of the Method
	 * 
	 * @param actionMapping
	 *            Description of the Parameter
	 * @param actionForm
	 *            Description of the Parameter
	 * @param httpServletRequest
	 *            Description of the Parameter
	 * @param httpServletResponse
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

		// Set no mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");
		InserirTipoSolicitacaoEspecificacaoActionForm inserirTipoSolicitacaoEspecificacaoActionForm = (InserirTipoSolicitacaoEspecificacaoActionForm) actionForm;

		Collection colecaoSolicitacaoTipoEspecificacao = (Collection) sessao
				.getAttribute("colecaoSolicitacaoTipoEspecificacao");
		if (colecaoSolicitacaoTipoEspecificacao == null
				|| colecaoSolicitacaoTipoEspecificacao.isEmpty()) {
			throw new ActionServletException(
					"atencao.required",null," Especifica��o do Tipo da Solicita��o");
		}

		Fachada fachada = Fachada.getInstancia();

		SolicitacaoTipo solicitacaoTipo = new SolicitacaoTipo();
		// descri��o da solicita��o tipo
		if (inserirTipoSolicitacaoEspecificacaoActionForm.getDescricao() != null
				&& !inserirTipoSolicitacaoEspecificacaoActionForm
						.getDescricao().equals("")) {
			solicitacaoTipo
					.setDescricao(inserirTipoSolicitacaoEspecificacaoActionForm
							.getDescricao());
		}
		// id do grupo de solicita��o da descri��o selecionada
		if (inserirTipoSolicitacaoEspecificacaoActionForm
				.getIdgrupoTipoSolicitacao() != null
				&& !inserirTipoSolicitacaoEspecificacaoActionForm
						.getIdgrupoTipoSolicitacao().equals("")) {
			SolicitacaoTipoGrupo solicitacaoTipoGrupo = new SolicitacaoTipoGrupo();
			solicitacaoTipoGrupo.setId(new Integer(
					inserirTipoSolicitacaoEspecificacaoActionForm
							.getIdgrupoTipoSolicitacao()));
			solicitacaoTipo.setSolicitacaoTipoGrupo(solicitacaoTipoGrupo);
		}

		// indicativo de falta d'agua
		if (inserirTipoSolicitacaoEspecificacaoActionForm
				.getIndicadorFaltaAgua() != null
				&& !inserirTipoSolicitacaoEspecificacaoActionForm
						.getIndicadorFaltaAgua().equals("")) {
			solicitacaoTipo.setIndicadorFaltaAgua(new Short(
					inserirTipoSolicitacaoEspecificacaoActionForm
							.getIndicadorFaltaAgua()));
		} else {
			throw new ActionServletException("atencao.indicador.selecionado",
					null, "Falta D'�gua");
		}
		// indicativo de falta d'agua
		if (inserirTipoSolicitacaoEspecificacaoActionForm
				.getIndicadorTarifaSocial() != null
				&& !inserirTipoSolicitacaoEspecificacaoActionForm
						.getIndicadorTarifaSocial().equals("")) {
			solicitacaoTipo.setIndicadorTarifaSocial(new Short(
					inserirTipoSolicitacaoEspecificacaoActionForm
							.getIndicadorTarifaSocial()));
		} else {
			throw new ActionServletException("atencao.indicador.selecionado",
					null, "Tarifa Social");

		}
		// indicativo de uso do sistema
		if (inserirTipoSolicitacaoEspecificacaoActionForm
				.getIndicadorUsoSistema() != null
				&& !inserirTipoSolicitacaoEspecificacaoActionForm
						.getIndicadorUsoSistema().equals("")) {
			solicitacaoTipo.setIndicadorUsoSistema(new Short(
					inserirTipoSolicitacaoEspecificacaoActionForm
							.getIndicadorUsoSistema()));
		} else {
			throw new ActionServletException("atencao.indicador.selecionado",
					null, "Uso Sistema");

		}
		
		// data e hora correntes
		solicitacaoTipo.setUltimaAlteracao(new Date());

		// indicador uso
		solicitacaoTipo.setIndicadorUso(new Short("1"));

		// inseri o tipo de solicita��o com especifica��es na base
		Integer id = fachada.inserirTipoSolicitacaoEspecificacao(solicitacaoTipo,
						colecaoSolicitacaoTipoEspecificacao, usuario);

		// remove o parametro de retorno
		sessao.removeAttribute("retornarTela");
		sessao.removeAttribute("retornarTelaPopup");
		sessao.removeAttribute("colecaoImovelSituacao");
		sessao.removeAttribute("colecaoSolicitacaoTipoGrupo");

		montarPaginaSucesso(httpServletRequest,
				"Tipo de Solicita��o com Especifica��es "
						+ solicitacaoTipo.getDescricao()
						+ " inserido com sucesso",
				"Inserir outro tipo de solicita��o com especifica��es",
				"exibirInserirTipoSolicitacaoEspecificacaoAction.do?menu=sim",
				"exibirAtualizarTipoSolicitacaoEspecificacaoAction.do?idTipoSolicitacao="+id,
				"Atualizar tipo de solicita��o com especifica��o inserida");

		/*
		 * montarPaginaSucesso(httpServletRequest, "Tipo de Solicita��o com
		 * Especifica��es" + idTipoSolicitacaoEspecificacao + " inserido com
		 * sucesso!", "Inserir outro tipo de solicita��o com especifica��es",
		 * "exibirInserirTipoSolicitacaoEspecificacaoAction.do?menu=sim",
		 * "exibirInserirTipoSolicitacaoEspecificacaoAction.do?idRegistroAtualizacao=" +
		 * idTipoSolicitacaoEspecificacao + "&retornoFiltrar=1", "Atualizar tipo
		 * de solicita��o com especifica��es inserido");
		 */return retorno;

	}
}
