package gcom.gui.cadastro.imovel;

import gcom.atendimentopublico.ligacaoagua.FiltroLigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.FiltroLigacaoEsgotoSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.cadastro.imovel.FiltroImovelSituacaoTipo;
import gcom.cadastro.imovel.ImovelSituacaoTipo;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Descri��o da classe
 * 
 * @author R�mulo Aur�lio
 * @date 26/03/2006
 */
public class ExibirInserirImovelSituacaoAction extends GcomAction {

	/**
	 * Este caso de uso permite a inclus�o de uma nova situa��o do im�vel
	 * 
	 * [UC0224] Inserir Situa��o do Imovel
	 * 
	 * 
	 * @author R�mulo Aur�lio
	 * @date 24/03/2006
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

		ActionForward retorno = actionMapping
				.findForward("situacaoImovelInserir");

		Fachada fachada = Fachada.getInstancia();

		FiltroImovelSituacaoTipo filtroImovelSituacaoTipo = new FiltroImovelSituacaoTipo();

		// Verifica se os dados foram informados da tabela existem e joga numa
		// colecao.
		// Seta a colecao no request

		Collection<ImovelSituacaoTipo> collectionImovelSituacaoTipo = fachada
				.pesquisar(filtroImovelSituacaoTipo, ImovelSituacaoTipo.class
						.getName());
		if (collectionImovelSituacaoTipo == null
				|| collectionImovelSituacaoTipo.isEmpty()) {
			throw new ActionServletException(
					"atencao.entidade_sem_dados_para_selecao", null,
					"Tabela Im�vel Situa��o Tipo");
		}
		httpServletRequest.setAttribute("collectionImovelSituacaoTipo",
				collectionImovelSituacaoTipo);

		// Parte referente ao combo Situa��o da Liga��o de �gua

		FiltroLigacaoAguaSituacao filtroLigacaoAguaSituacao = new FiltroLigacaoAguaSituacao();

		filtroLigacaoAguaSituacao.adicionarParametro(new ParametroSimples(
				FiltroLigacaoAguaSituacao.INDICADOR_USO,
				ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection<LigacaoAguaSituacao> collectionLigacaoAguaSituacao = fachada
				.pesquisar(filtroLigacaoAguaSituacao, LigacaoAguaSituacao.class
						.getName());

		if (collectionLigacaoAguaSituacao == null
				|| collectionLigacaoAguaSituacao.isEmpty()) {
			throw new ActionServletException(
					"atencao.entidade_sem_dados_para_selecao", null,
					"Tabela Liga��o �gua Situa��o");
		}
		httpServletRequest.setAttribute("collectionLigacaoAguaSituacao",
				collectionLigacaoAguaSituacao);

		// Parte referente ao combo Situa��o da Liga��o de Esgoto

		FiltroLigacaoEsgotoSituacao filtroLigacaoEsgotoSituacao = new FiltroLigacaoEsgotoSituacao();

		filtroLigacaoEsgotoSituacao.adicionarParametro(new ParametroSimples(
				FiltroLigacaoEsgotoSituacao.INDICADOR_USO,
				ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection<LigacaoEsgotoSituacao> collectionLigacaoEsgotoSituacao = fachada
				.pesquisar(filtroLigacaoEsgotoSituacao,
						LigacaoEsgotoSituacao.class.getName());

		if (collectionLigacaoEsgotoSituacao == null
				|| collectionLigacaoEsgotoSituacao.isEmpty()) {
			throw new ActionServletException(
					"atencao.entidade_sem_dados_para_selecao", null,
					"Tabela Liga��o Esgoto Situa��o");

		}

		httpServletRequest.setAttribute("collectionLigacaoEsgotoSituacao",
				collectionLigacaoEsgotoSituacao);

		return retorno;
	}

}
