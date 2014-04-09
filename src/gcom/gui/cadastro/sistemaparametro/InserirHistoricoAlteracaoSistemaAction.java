package gcom.gui.cadastro.sistemaparametro;

import java.util.Collection;

import gcom.cadastro.sistemaparametro.SistemaAlteracaoHistorico;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.Funcionalidade;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.tabelaauxiliar.abreviada.FiltroTabelaAuxiliarAbreviada;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action utilizado para inserir um Historico Altera��o de Sistema no banco
 * 
 * [UC0217] Inserir Historico Altera��o Sistema Permite inserir um
 * Historico Altera��o do Sistema
 * 
 * @author Thiago Ten�rio
 * @since 30/03/2006
 */
public class InserirHistoricoAlteracaoSistemaAction extends GcomAction {

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
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		Fachada fachada = Fachada.getInstancia();

		InserirHistoricoAlteracaoSistemaActionForm inserirHistoricoAlteracaoSistemaActionForm = (InserirHistoricoAlteracaoSistemaActionForm) actionForm;
		
		if ((inserirHistoricoAlteracaoSistemaActionForm.getIdFuncionalidade() != null && !inserirHistoricoAlteracaoSistemaActionForm.getIdFuncionalidade()
				.equals(""))) {

			FiltroTabelaAuxiliarAbreviada filtroTabelaAuxiliarAbreviada = new FiltroTabelaAuxiliarAbreviada();

			filtroTabelaAuxiliarAbreviada
					.adicionarParametro(new ParametroSimples(
							FiltroTabelaAuxiliarAbreviada.ID, inserirHistoricoAlteracaoSistemaActionForm
									.getIdFuncionalidade()));

			Collection colecaoFuncionalidade = fachada.pesquisar(
					filtroTabelaAuxiliarAbreviada, Funcionalidade.class
							.getName());

			if (colecaoFuncionalidade != null
					&& !colecaoFuncionalidade.isEmpty()) {

				Funcionalidade funcionalidade = (Funcionalidade) colecaoFuncionalidade
						.iterator().next();
				inserirHistoricoAlteracaoSistemaActionForm.setDescricaoFuncionalidade(funcionalidade.getDescricao());

			} else {
				httpServletRequest.setAttribute("funcionalidadeEncontrada",
						"exception");
				throw new ActionServletException("atencao.funcionalidade_inexistente");
			}

		}
	
		// Cria uma resolu��o de diretoria setando os valores informados pelo
		// usu�rio na pagina para ser inserido no banco
		SistemaAlteracaoHistorico sistemaAlteracaoHistorico = new SistemaAlteracaoHistorico();

		Funcionalidade funcionalidade = new Funcionalidade();
		funcionalidade.setId(new Integer(inserirHistoricoAlteracaoSistemaActionForm
				.getIdFuncionalidade()));
		
		sistemaAlteracaoHistorico
		.setFuncionalidade(funcionalidade);
		
		sistemaAlteracaoHistorico
				.setNome(inserirHistoricoAlteracaoSistemaActionForm
						.getTituloAlteracao());

		sistemaAlteracaoHistorico
				.setDescricao(inserirHistoricoAlteracaoSistemaActionForm
						.getDescricao());

		// verifica se a data de Altera��o foi digitada em caso afirmativo
		// seta-a no
		// objeto
		if (inserirHistoricoAlteracaoSistemaActionForm.getDataAlteracao() != null
				&& !inserirHistoricoAlteracaoSistemaActionForm
						.getDataAlteracao().equals("")) {
			sistemaAlteracaoHistorico
					.setData(Util
							.converteStringParaDate(inserirHistoricoAlteracaoSistemaActionForm
									.getDataAlteracao()));
		}
		
		

		// Insere um Historico Altera��o de Sistema na base, mas fazendo, antes,
		// algumas verifica��es no ControladorCadastroSEJB.
		 fachada
		 .inserirHistoricoAlteracaoSistema(sistemaAlteracaoHistorico);

		// Exibe a p�gina de sucesso
		montarPaginaSucesso(httpServletRequest,
				"Hist�rico de Altera��es do Sistema "
						+ sistemaAlteracaoHistorico.getDescricao()
						+ " inserido com sucesso.", "Inserir outro Hist�rico de Altera��es do Sistema",
				"exibirInserirHistoricoAlteracaoSistemaAction.do?menu=sim");

		return retorno;

	}

}
