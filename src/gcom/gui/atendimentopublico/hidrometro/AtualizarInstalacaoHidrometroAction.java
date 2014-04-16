package gcom.gui.atendimentopublico.hidrometro;

import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.PocoTipo;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.micromedicao.hidrometro.HidrometroInstalacaoHistorico;
import gcom.micromedicao.medicao.MedicaoTipo;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action para atualiza��o da instala��o do hidr�metro.
 * 
 * @author lms
 * @date 24/07/2006
 */
public class AtualizarInstalacaoHidrometroAction extends GcomAction {

	/**
	 * Este caso de uso permite efetuar a atualiza��o dos atributos da
	 * instala��o do hidr�metro
	 * 
	 * [UC0368] Atualizar Instala��o de Hidr�metro
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

		ActionForward retorno = actionMapping.findForward("telaSucesso");
		AtualizarInstalacaoHidrometroActionForm form = (AtualizarInstalacaoHidrometroActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);

		Imovel imovel = null;

		// Usuario logado no sistema
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		/*
		 * [UC0107] Registrar Transa��o
		 * 
		 */
//		RegistradorOperacao registradorOperacao = new RegistradorOperacao(
//				Operacao.OPERACAO_INSTALACAO_HIDROMETRO_ATUALIZAR,
//				new UsuarioAcaoUsuarioHelper(usuario,
//						UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));
//
//		Operacao operacao = new Operacao();
//		operacao.setId(Operacao.OPERACAO_INSTALACAO_HIDROMETRO_ATUALIZAR);
//
//		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
//		operacaoEfetuada.setOperacao(operacao);
		// [UC0107] -Fim- Registrar Transa��o

		if (form.getIdOrdemServico() != null && !form.getIdOrdemServico().equals("")) {

			Integer idOrdemServico = Util.converterStringParaInteger(form.getIdOrdemServico());

			OrdemServico ordemServico = fachada.recuperaOSPorId(idOrdemServico);

			// Valida a ordem de servi�o
			fachada.validarExibirAtualizarInstalacaoHidrometro(ordemServico,false);

			imovel = ordemServico.getRegistroAtendimento().getImovel();

		} else {
			String idImovel = form.getIdImovel();

			String inscricaoImovelEncontrado = fachada
					.pesquisarInscricaoImovel(new Integer(idImovel));

			form.setMatriculaImovel(idImovel);

			form.setInscricaoImovel(inscricaoImovelEncontrado);

			FiltroImovel filtroImovel = new FiltroImovel();

			filtroImovel.adicionarParametro(new ParametroSimples(
					FiltroImovel.ID, idImovel));

			filtroImovel
					.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LIGACAO_AGUA_SITUACAO);

			filtroImovel
					.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LIGACAO_ESGOTO_SITUACAO);

			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("hidrometroInstalacaoHistorico.hidrometro");

			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("quadra");

			filtroImovel
					.adicionarCaminhoParaCarregamentoEntidade("ligacaoAgua");
			filtroImovel
					.adicionarCaminhoParaCarregamentoEntidade("ligacaoAgua.hidrometroInstalacaoHistorico");

			Collection colecaoImovel = fachada.pesquisar(filtroImovel,
					Imovel.class.getName());

			imovel = (Imovel) colecaoImovel.iterator().next();

		}

		HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico = null;

		Integer medicaoTipo = Integer.parseInt(form.getMedicaoTipo());

		if (MedicaoTipo.LIGACAO_AGUA.equals(medicaoTipo)) {
			hidrometroInstalacaoHistorico = imovel.getLigacaoAgua()
					.getHidrometroInstalacaoHistorico();
		} else if (MedicaoTipo.POCO.equals(medicaoTipo)) {
			hidrometroInstalacaoHistorico = imovel
					.getHidrometroInstalacaoHistorico();
			
			PocoTipo pocoTipo = new PocoTipo();
			pocoTipo.setId(new Integer(form.getTipoPoco()));
			
			imovel.setPocoTipo(pocoTipo);
		}

		// Valida a exist�ncia do hidr�metro
		fachada.validarExistenciaHidrometro(imovel, medicaoTipo);

		// Atualiza a entidade com os valores do formul�rio
		form.setFormValues(hidrometroInstalacaoHistorico);

		// Informa que o usu�rio que fez a instala��o � o usu�rio logado
		hidrometroInstalacaoHistorico.setUsuarioInstalacao((Usuario) sessao
				.getAttribute("usuarioLogado"));

		// Atualiza a base de dados com as altera��es da instala��o hidr�metro
		fachada.atualizarInstalacaoHidrometro(imovel, Integer.parseInt(form
				.getMedicaoTipo()),usuario);

		// Setando Opera��o
//		hidrometroInstalacaoHistorico.setOperacaoEfetuada(operacaoEfetuada);
//		hidrometroInstalacaoHistorico.adicionarUsuario(usuario,
//				UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
//		registradorOperacao.registrarOperacao(hidrometroInstalacaoHistorico);

		// Exibe a p�gina de sucesso

		montarPaginaSucesso(httpServletRequest,
				"Atualiza��o do Hist�rico da Instala��o de Hidr�metro do im�vel "
						+ imovel.getId() + " efetuada com sucesso.",
				"Efetuar nova Atualiza��o do Hist�rico da Instala��o de Hidr�metro",
				"exibirAtualizarInstalacaoHidrometroAction.do?menu=sim");

		// Exibe a p�gina de sucesso
		/*
		 * montarPaginaSucesso(httpServletRequest, "Atualiza��o do Hist�rico da
		 * Instala��o de Hidr�metro do im�vel " + imovel.getId() + " efetuada
		 * com sucesso.", "Efetuar Atualiza��o do Hist�rico da Instala��o de
		 * Hidr�metro", "exibirEfetuarLigacaoEsgotoAction.do?menu=sim",
		 * "exibirAtualizarInstalacaoHidrometroAction.do");
		 */

		return retorno;
	}

}
