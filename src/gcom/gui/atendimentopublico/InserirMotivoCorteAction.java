package gcom.gui.atendimentopublico;

import gcom.atendimentopublico.ligacaoagua.FiltroMotivoCorte;
import gcom.atendimentopublico.ligacaoagua.MotivoCorte;
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
public class InserirMotivoCorteAction extends GcomAction {

	/**
	 * Este caso de uso permite a inclus�o de um motivo de corte
	 * 
	 * [UC0754] Inserir Motivo de Corte
	 * 
	 * 
	 * @author Vin�cius Medeiros
	 * @date 27/03/2008
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
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		// Obt�m a inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		InserirMotivoCorteActionForm inserirMotivoCorteActionForm = (InserirMotivoCorteActionForm) actionForm;
				
		String descricao = inserirMotivoCorteActionForm.getDescricao();

		MotivoCorte motivoCorte = new MotivoCorte();
		Collection colecaoPesquisa = null;

		// Verifica se o campo Descri��o est� preenchido
		if (!"".equals(inserirMotivoCorteActionForm.getDescricao())) {
			motivoCorte.setDescricao(inserirMotivoCorteActionForm
					.getDescricao());
		} else {
			throw new ActionServletException("atencao.required", null,
					"descri��o");
		}
		
		// Ultima altera��o
		motivoCorte.setUltimaAlteracao(new Date());
		
		// Indicador de uso
		Short iu = 1;
		motivoCorte.setIndicadorUso(iu);

		FiltroMotivoCorte filtroMotivoCorte = new FiltroMotivoCorte();

		filtroMotivoCorte.adicionarParametro(new ParametroSimples(
				FiltroMotivoCorte.DESCRICAO, motivoCorte.getDescricao()));

		colecaoPesquisa = (Collection) fachada.pesquisar(filtroMotivoCorte,
				MotivoCorte.class.getName());

		// Caso j� haja um Motivo Corte com a descri�ao passada
		if (colecaoPesquisa != null && !colecaoPesquisa.isEmpty()) {
			// 
			throw new ActionServletException(
					"atencao.motivo_corte_ja_cadastrado", null, motivoCorte
							.getDescricao());
		} else {
			// Caso n�o haja, ir� inserir
			motivoCorte.setDescricao(descricao);

			Integer idMotivoCorte = (Integer) fachada.inserir(motivoCorte);

			montarPaginaSucesso(httpServletRequest,
					"Motivo de Corte de c�digo " + idMotivoCorte
							+ " inserido com sucesso.",
					"Inserir outro Motivo de Corte",
					"exibirInserirMotivoCorteAction.do?menu=sim",
					"exibirAtualizarMotivoCorteAction.do?idRegistroAtualizacao="
							+ idMotivoCorte,
					"Atualizar Motivo de Corte Inserido");

			sessao.removeAttribute("InserirMotivoCorteActionForm");

			return retorno;
		}

	}
}
