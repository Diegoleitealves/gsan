package gcom.gui.arrecadacao;

import gcom.arrecadacao.ArrecadacaoForma;
import gcom.arrecadacao.FiltroArrecadacaoForma;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Descri��o da classe
 * 
 * @author Vin�cius Medeiros
 * @date 27/03/2008
 */
public class InserirArrecadacaoFormaAction extends GcomAction {

	/**
	 * Este caso de uso permite a inclus�o de uma Forma de Arrecadacao
	 * 
	 * [UC0757] Inserir Forma de Arrecadacao
	 * 
	 * 
	 * @author Vin�cius Medeiros
	 * @date 08/04/2008
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

		// Seta o caminho de retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		InserirArrecadacaoFormaActionForm inserirArrecadacaoFormaActionForm = (InserirArrecadacaoFormaActionForm) actionForm;

		// Mudar isso quando houver um esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		// Obt�m a inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();


		String descricao = inserirArrecadacaoFormaActionForm.getDescricao();
		
		ArrecadacaoForma arrecadacaoForma = new ArrecadacaoForma();
		Collection colecaoPesquisa = null;

		// Verifica se a Descri��o foi preenchida.
		if (!"".equals(inserirArrecadacaoFormaActionForm.getDescricao())&& !"".equals(
				inserirArrecadacaoFormaActionForm.getCodigoArrecadacaoForma())) {
			
			arrecadacaoForma.setDescricao(inserirArrecadacaoFormaActionForm.getDescricao());
			arrecadacaoForma.setCodigoArrecadacaoForma(inserirArrecadacaoFormaActionForm.getCodigoArrecadacaoForma());
		} else {
			throw new ActionServletException("atencao.required", null,
					"descricao");
		}
		
		// Seta a Ultima altera��o
		arrecadacaoForma.setUltimaAlteracao(new Date());

		FiltroArrecadacaoForma filtroArrecadacaoForma = new FiltroArrecadacaoForma();

		filtroArrecadacaoForma.adicionarParametro(new ParametroSimples(
				FiltroArrecadacaoForma.DESCRICAO, arrecadacaoForma.getDescricao()));
	
		colecaoPesquisa = (Collection) fachada.pesquisar(filtroArrecadacaoForma,
				ArrecadacaoForma.class.getName());

		if (colecaoPesquisa != null && !colecaoPesquisa.isEmpty()) {
			// Caso j� haja uma Forma de Arrecada��o com a descri��o passada
			throw new ActionServletException(
					"atencao.arrecadacao_forma_ja_cadastrada", null, arrecadacaoForma
							.getDescricao());
		} else {
			arrecadacaoForma.setDescricao(descricao);

			Integer idArrecadacaoForma = (Integer) fachada.inserir(arrecadacaoForma);
			
			montarPaginaSucesso(httpServletRequest,
					"Forma de Arrecada��o de descri��o " + descricao
							+ " inserido com sucesso.",
					"Inserir outra Forma de Arrecada��o",
					"exibirInserirArrecadacaoFormaAction.do?menu=sim",
					"exibirAtualizarArrecadacaoFormaAction.do?idRegistroAtualizacao="
							+ idArrecadacaoForma,
					"Atualizar Forma de Arrecada��o Inserido");

			sessao.removeAttribute("InserirArrecadacaoFormaActionForm");

			return retorno;
		}

	}
}
