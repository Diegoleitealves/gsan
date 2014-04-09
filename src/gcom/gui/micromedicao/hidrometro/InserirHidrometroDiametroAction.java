package gcom.gui.micromedicao.hidrometro;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.hidrometro.FiltroHidrometroDiametro;
import gcom.micromedicao.hidrometro.HidrometroDiametro;
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
 * @date 16/05/2008
 */
public class InserirHidrometroDiametroAction extends GcomAction {

	/**
	 * Este caso de uso permite a inclus�o de uma Diametro do Hidrometro
	 * 
	 * [UC0791] Inserir Diametro do Hidrometro
	 * 
	 * 
	 * @author Vin�cius Medeiros
	 * @date 16/05/2008
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

		InserirHidrometroDiametroActionForm inserirHidrometroDiametroActionForm = (InserirHidrometroDiametroActionForm) actionForm;

		// Mudar quando houver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		// Obt�m a inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		String descricao = inserirHidrometroDiametroActionForm.getDescricao();
		String descricaoAbreviada = inserirHidrometroDiametroActionForm
				.getDescricaoAbreviada();
		
		HidrometroDiametro hidrometroDiametro = new HidrometroDiametro();
		Collection colecaoPesquisa = null;

		// Descri��o
		if (!"".equals(inserirHidrometroDiametroActionForm.getDescricao())) {
			hidrometroDiametro.setDescricao(inserirHidrometroDiametroActionForm
					.getDescricao());
		} else {
			throw new ActionServletException("atencao.required", null,
					"descri��o");
		}
		
		// Descri��o Abreviada
		if (!"".equals(inserirHidrometroDiametroActionForm.getDescricaoAbreviada())) {
			hidrometroDiametro.setDescricaoAbreviada(inserirHidrometroDiametroActionForm
					.getDescricaoAbreviada());
		} else {
			throw new ActionServletException("atencao.required", null,
					"descri��o Abreviada");
		}

		// N�mero de Ordem
		if (!"".equals(inserirHidrometroDiametroActionForm.getNumeroOrdem())) {
			hidrometroDiametro.setNumeroOrdem(new Short (inserirHidrometroDiametroActionForm
					.getNumeroOrdem()));
		} else {
			throw new ActionServletException("atencao.required", null,
					"N�mero de ordem");
		}

		
		
		hidrometroDiametro.setUltimaAlteracao(new Date());

		Short iu = 1;
		hidrometroDiametro.setIndicadorUso(iu);

		FiltroHidrometroDiametro filtroHidrometroDiametro = new FiltroHidrometroDiametro();

		filtroHidrometroDiametro.adicionarParametro(new ParametroSimples(
				FiltroHidrometroDiametro.DESCRICAO, hidrometroDiametro.getDescricao()));
		
		colecaoPesquisa = (Collection) fachada.pesquisar(
				filtroHidrometroDiametro, HidrometroDiametro.class.getName());

		if (colecaoPesquisa != null && !colecaoPesquisa.isEmpty()) {
			// Caso j� haja um Diametro do Hidrometro com os dados passados 
			throw new ActionServletException(
					"atencao.diametrohidrometro_ja_cadastrada", null,
					hidrometroDiametro.getDescricao());
		} else {
			hidrometroDiametro.setDescricao(descricao);
			hidrometroDiametro.setDescricaoAbreviada(descricaoAbreviada);

			Integer idHidrometroDiametro = (Integer) fachada
					.inserir(hidrometroDiametro);

			montarPaginaSucesso(httpServletRequest,
					"Di�metro do Hidr�metro " + descricao
							+ " inserido com sucesso.",
					"Inserir outro Di�metro do Hidr�metro",
					"exibirInserirHidrometroDiametroAction.do?menu=sim",
					"exibirAtualizarHidrometroDiametroAction.do?idRegistroAtualizacao="
							+ idHidrometroDiametro,
					"Atualizar Di�metro do Hidr�metro Inserida");

			sessao.removeAttribute("InserirHidrometroDiametroActionForm");

			return retorno;
		}

	}
}
