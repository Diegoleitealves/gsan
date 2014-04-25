package gcom.gui.cadastro.imovel;

import gcom.cadastro.imovel.ImovelSituacao;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Descri��o da classe
 * 
 * @author R�mulo Aur�lio
 * @date 24/03/2006
 */

public class InserirImovelSituacaoAction extends GcomAction {

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

		ActionForward retorno = actionMapping.findForward("telaSucesso");
		InserirImovelSituacaoActionForm inserirImovelSituacaoActionForm = (InserirImovelSituacaoActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();

		// Recebe os dados do ActionForm e envia para o metodo
		// inserirSituacaoImovel do controlador

		String idImovelSituacaoTipo = inserirImovelSituacaoActionForm
				.getImovelSituacaoTipo();
		String idLigacaoAguaSituacao = inserirImovelSituacaoActionForm
				.getLigacaoAguaSituacao();
		String idLigacaoEsgotoSituacao = inserirImovelSituacaoActionForm
				.getLigacaoEsgotoSituacao();

		ImovelSituacao imovelSituacao = new ImovelSituacao();

		imovelSituacao.setUltimaAlteracao(new Date());

		/*Integer idImovelSituacao = (Integer)*/ fachada.inserirSituacaoImovel(
				idImovelSituacaoTipo, idLigacaoAguaSituacao,
				idLigacaoEsgotoSituacao);

		// Monta a Pagina de sucesso

		montarPaginaSucesso(httpServletRequest,
				"Situa��o de im�vel inserida com sucesso.",
				"Inserir outra situa��o",
				"exibirInserirImovelSituacaoAction.do?menu=sim",
				"exibirFiltrarImovelSituacaoAction.do?menu=sim",
				"Consultar outra Situa��o de Im�vel");

		return retorno;

	}
}
