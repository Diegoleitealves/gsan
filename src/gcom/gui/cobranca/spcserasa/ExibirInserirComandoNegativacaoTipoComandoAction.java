package gcom.gui.cobranca.spcserasa;

import gcom.cobranca.Negativador;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.spcserasa.FiltroNegativador;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC0651] Inserir Comando de Negativa��o
 * Esta caso de uso permite a inclus�o de um novo comando de negativa��o.
 * 
 * @author Ana Maria
 * @date 06/11/2007
 */
public class ExibirInserirComandoNegativacaoTipoComandoAction extends
		GcomAction {

	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		ActionForward retorno = actionMapping
				.findForward("exibirInserirComandoNegativacaoTipoComando");

		InserirComandoNegativacaoActionForm inserirRegistroAtendimentoActionForm = (InserirComandoNegativacaoActionForm) actionForm;

		if(inserirRegistroAtendimentoActionForm.getTipo() == null){
		  //Tipo do Comando - exibir com a op��o "Por Crit�rio" selecionada
		  inserirRegistroAtendimentoActionForm.setTipo("1");
		}
		
		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();

		//Pesquisar Negativador
		Collection colecaoNegativador = (Collection) sessao.getAttribute("colecaoNegativador");
		if (colecaoNegativador == null) {

			FiltroNegativador filtroNegativador = new FiltroNegativador();		
			filtroNegativador.adicionarParametro(new ParametroSimples(FiltroNegativador.INDICADOR_USO,
					ConstantesSistema.INDICADOR_USO_ATIVO));
			filtroNegativador.setCampoOrderBy(FiltroNegativador.CLIENTE);			
			filtroNegativador.adicionarCaminhoParaCarregamentoEntidade("cliente");
			filtroNegativador.setConsultaSemLimites(true);

			colecaoNegativador = fachada.pesquisar(filtroNegativador,
					Negativador.class.getName());

			if (colecaoNegativador == null || colecaoNegativador.isEmpty()) {
				throw new ActionServletException(
						"atencao.entidade_sem_dados_para_selecao", null,
						"NEGATIVADOR");
			} else {
				sessao.setAttribute("colecaoNegativador", colecaoNegativador);
			}
		}

		return retorno;
	}

}
