package gcom.gui.atendimentopublico.registroatendimento;

import gcom.atendimentopublico.registroatendimento.EspecificacaoImovSitCriterio;
import gcom.atendimentopublico.registroatendimento.EspecificacaoImovelSituacao;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC0402] Inserir Especifica��o da Situa��o do Imovel
 * 
 * @author Rafael Pinto
 * @created 04/08/2006
 */
public class InserirEspecificacaoSituacaoImovelAction extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		
		// Seta Retorno (Forward = Sucesso)
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Form
		InserirEspecificacaoSituacaoImovelActionForm inserirEspecificacaoSituacaoImovelActionForm = 
			(InserirEspecificacaoSituacaoImovelActionForm) actionForm;

		// Fachada
		Fachada fachada = Fachada.getInstancia();

		// EspecificacaoImovelSituacao
		EspecificacaoImovelSituacao especificacaoImovelSituacao = new EspecificacaoImovelSituacao();
		
		especificacaoImovelSituacao.setDescricao(
			inserirEspecificacaoSituacaoImovelActionForm.getDescricaoEspecificacao());
		especificacaoImovelSituacao.setUltimaAlteracao(new Date());
		
		// Insere EspecificacaoImovelSituacao
		fachada.inserir(especificacaoImovelSituacao);
		
		// Insere Equipe Componentes
		this.inserirEspecificacaoImovelSituacaoCriterio(inserirEspecificacaoSituacaoImovelActionForm, fachada, especificacaoImovelSituacao);
		
		// [FS008] Monta a p�gina de sucesso
		montarPaginaSucesso(httpServletRequest, 
			"Especifica��o Situa��o do Im�vel "+especificacaoImovelSituacao.getDescricao()+" inserida com sucesso!", 
			"Inserir outra Especifica��o Situa��o do Im�vel",
			"exibirInserirEspecificacaoSituacaoImovelAction.do?menu=sim",
			"exibirAtualizarEspecificacaoSituacaoImovelAction.do?idEspecificacao="+especificacaoImovelSituacao.getId(),
			"Atualizar Especifica��o Situa��o do Im�vel Inserida");

		return retorno;
	}

	/**
	 * Insere Cole��o de EspecificacaoImovelSituacaoCriterio devidamente validados na base 
	 *
	 * @author Rafael Pinto
	 * @date 04/08/2006
	 *
	 * @param InserirEspecificacaoSituacaoImovelActionForm
	 * @param fachada
	 * @param especificacaoImovelSituacao
	 */
	private void inserirEspecificacaoImovelSituacaoCriterio(InserirEspecificacaoSituacaoImovelActionForm inserirActionForm, 
		Fachada fachada, EspecificacaoImovelSituacao especificacaoImovelSituacao) {
		
		// Cole��o de EspecificacaoImovelSituacaoCriterio
		Collection colecaoEspecificacaoImovelSituacaoCriterio = inserirActionForm.getEspecificacaoImovelSituacaoCriterio();
		for (Iterator iter = colecaoEspecificacaoImovelSituacaoCriterio.iterator(); iter.hasNext();) {
			
			EspecificacaoImovSitCriterio element = (EspecificacaoImovSitCriterio) iter.next();
			element.setEspecificacaoImovelSituacao(especificacaoImovelSituacao);
			
			fachada.inserir(element);
		}
	}

}
