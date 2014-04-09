package gcom.gui.util.tabelaauxiliar.abreviadatipo;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.operacional.SistemaAbastecimento;
import gcom.util.ConstantesSistema;
import gcom.util.ErroRepositorioException;
import gcom.util.filtro.ParametroSimples;
import gcom.util.tabelaauxiliar.abreviadatipo.FiltroTabelaAuxiliarAbreviadaTipo;
import gcom.util.tabelaauxiliar.abreviadatipo.TabelaAuxiliarAbreviadaTipo;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <Esse componente serve para SetorAbastecimento e ZonaAbastecimento, sendo o tipo SistemaAbastecimento>
 * 
 * @author Administrador
 */
public class InserirTabelaAuxiliarAbreviadaTipoAction extends GcomAction {
	/**
	 * <Descri��o do m�todo>
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
	 * @exception RemoteException
	 *                Descri��o da exce��o
	 * @exception ErroRepositorioException
	 *                Descri��o da exce��o
	 */
	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws RemoteException,
			ErroRepositorioException {

		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Prepara o retorno da A��o
		HttpSession sessao = httpServletRequest.getSession(false);

		// Obt�m o action form
		TabelaAuxiliarAbreviadaTipoActionForm tabelaAuxiliarAbreviadaTipoActionForm = (TabelaAuxiliarAbreviadaTipoActionForm) actionForm;

		// Obt�m a fachada
		Fachada fachada = Fachada.getInstancia();

		// Recebe o objeto TabelaAuxiliarAbreviadaTipo
		TabelaAuxiliarAbreviadaTipo tabelaAuxiliarAbreviadaTipo = (TabelaAuxiliarAbreviadaTipo) sessao
				.getAttribute("tabela");

		// Seta a descri��o informada
		tabelaAuxiliarAbreviadaTipo.setDescricao(
				tabelaAuxiliarAbreviadaTipoActionForm.getDescricao().toUpperCase());

		// Seta a descri��o abreviada informada
		tabelaAuxiliarAbreviadaTipo
				.setDescricaoAbreviada(tabelaAuxiliarAbreviadaTipoActionForm
						.getDescricaoAbreviada().toUpperCase());
		
		// Seta o Indicador de Uso
		tabelaAuxiliarAbreviadaTipo.setIndicadorUso(ConstantesSistema.INDICADOR_USO_ATIVO);
		
		// Seta a data e hora
		tabelaAuxiliarAbreviadaTipo.setUltimaAlteracao(new Date());

		SistemaAbastecimento sistemaAbastecimento = new SistemaAbastecimento();
		sistemaAbastecimento.setId(new Integer(tabelaAuxiliarAbreviadaTipoActionForm.getTipo()));
		
		tabelaAuxiliarAbreviadaTipo.setSistemaAbastecimento(sistemaAbastecimento);

		FiltroTabelaAuxiliarAbreviadaTipo filtroTabelaAuxiliarAbreviadaTipo = new FiltroTabelaAuxiliarAbreviadaTipo();

		filtroTabelaAuxiliarAbreviadaTipo
				.adicionarParametro(new ParametroSimples(
						FiltroTabelaAuxiliarAbreviadaTipo.DESCRICAO,
						tabelaAuxiliarAbreviadaTipoActionForm.getDescricao()
								.toUpperCase()));

		Collection colecaoTabelaAuxiliar = fachada.pesquisar(
				filtroTabelaAuxiliarAbreviadaTipo, TabelaAuxiliarAbreviadaTipo.class
						.getName());

		if (colecaoTabelaAuxiliar != null && !colecaoTabelaAuxiliar.isEmpty()) {
			throw new ActionServletException(
					"atencao.descricao_tabela_auxiliar_ja_existente",
					(String) sessao.getAttribute("titulo"),
					tabelaAuxiliarAbreviadaTipoActionForm.getDescricao().toUpperCase());
		}

		// Insere objeto
		fachada.inserirTabelaAuxiliar(tabelaAuxiliarAbreviadaTipo);

		// Monta a p�gina de sucesso
		if (retorno.getName().equalsIgnoreCase("telaSucesso")) {
			montarPaginaSucesso(
					httpServletRequest,
					((String) sessao.getAttribute("titulo"))
							+ " inserido(a) com sucesso.",
					"Inserir outro(a) "
							+ ((String) sessao.getAttribute("titulo")),
					((String) sessao
							.getAttribute("funcionalidadeTabelaAuxiliarAbreviadaTipoInserir")));
		}

		// Remove os objetos da sess�o
		sessao.removeAttribute("funcionalidadeTabelaAuxiliarAbreviadaTipoInserir");
		sessao.removeAttribute("titulo");
		sessao.removeAttribute("tituloTipo");
		sessao.removeAttribute("tabelaAuxiliarAbreviadaTipo");
		sessao.removeAttribute("tabela");

		return retorno;
	}
}
