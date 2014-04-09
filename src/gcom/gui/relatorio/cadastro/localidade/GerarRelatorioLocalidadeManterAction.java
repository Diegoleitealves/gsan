package gcom.gui.relatorio.cadastro.localidade;

import gcom.cadastro.localidade.FiltroGerenciaRegional;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.GerenciaRegional;
import gcom.cadastro.localidade.Localidade;
import gcom.fachada.Fachada;
import gcom.gui.cadastro.localidade.FiltrarLocalidadeActionForm;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.cadastro.localidade.RelatorioManterLocalidade;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.SistemaException;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <p>
 * Title: GCOM
 * </p>
 * <p>
 * Description: Sistema de Gest�o Comercial
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: COMPESA - Companhia Pernambucana de Saneamento
 * </p>
 * 
 * @author Rafael Corr�a
 * @version 1.0
 */

public class GerarRelatorioLocalidadeManterAction extends
		ExibidorProcessamentoTarefaRelatorio {

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

		// cria a vari�vel de retorno
		ActionForward retorno = null;

		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		FiltrarLocalidadeActionForm filtrarLocalidadeActionForm = (FiltrarLocalidadeActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		FiltroLocalidade filtroLocalidade = (FiltroLocalidade) sessao
				.getAttribute("filtroLocalidade");

		// Inicio da parte que vai mandar os parametros para o relat�rio

		Localidade localidadeParametros = new Localidade();

		String idLocalidade = null;

		String idLocalidadePesquisar = (String) filtrarLocalidadeActionForm
				.getLocalidadeID();

		if (idLocalidadePesquisar != null && !idLocalidadePesquisar.equals("")) {
			idLocalidade = idLocalidadePesquisar;
		}

		String idGerenciaRegional = (String) filtrarLocalidadeActionForm
				.getGerenciaID();

		GerenciaRegional gerenciaRegional = null;

		if (idGerenciaRegional != null
				&& !idGerenciaRegional.equals("")
				&& !idGerenciaRegional.equals(""
						+ ConstantesSistema.NUMERO_NAO_INFORMADO)) {
			FiltroGerenciaRegional filtroGerenciaRegional = new FiltroGerenciaRegional();

			filtroGerenciaRegional.adicionarParametro(new ParametroSimples(
					FiltroGerenciaRegional.ID, idGerenciaRegional));
			filtroGerenciaRegional.adicionarParametro(new ParametroSimples(
					FiltroGerenciaRegional.INDICADOR_USO,
					ConstantesSistema.INDICADOR_USO_ATIVO));

			Collection gerenciasRegionais = fachada.pesquisar(
					filtroGerenciaRegional, GerenciaRegional.class.getName());

			if (gerenciasRegionais != null && !gerenciasRegionais.isEmpty()) {
				// Titulo do Logradouro Foi Encontrado
				Iterator gerenciaRegionalIterator = gerenciasRegionais
						.iterator();

				gerenciaRegional = (GerenciaRegional) gerenciaRegionalIterator
						.next();

			} else {
				gerenciaRegional = new GerenciaRegional();
			}
		}

		Short indicadorDeUso = null;

		if (filtrarLocalidadeActionForm.getIndicadorUso() != null
				&& !filtrarLocalidadeActionForm.getIndicadorUso().equals("")) {

			indicadorDeUso = new Short(""
					+ filtrarLocalidadeActionForm.getIndicadorUso());
		}

		// seta os parametros que ser�o mostrados no relat�rio

		localidadeParametros.setId(idLocalidade == null ? null : new Integer(
				idLocalidade));
		localidadeParametros.setDescricao(""
				+ filtrarLocalidadeActionForm.getLocalidadeNome());
		localidadeParametros.setGerenciaRegional(gerenciaRegional);
		localidadeParametros.setIndicadorUso(indicadorDeUso);

		// Fim da parte que vai mandar os parametros para o relat�rio

		// cria uma inst�ncia da classe do relat�rio
		RelatorioManterLocalidade relatorioManterLocalidade = new RelatorioManterLocalidade(
				(Usuario)(httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));
		relatorioManterLocalidade.addParametro("filtroLocalidade",
				filtroLocalidade);
		relatorioManterLocalidade.addParametro("localidadeParametros",
				localidadeParametros);

		// chama o met�do de gerar relat�rio passando o c�digo da analise
		// como par�metro
		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");
		if (tipoRelatorio == null) {
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		relatorioManterLocalidade.addParametro("tipoFormatoRelatorio", Integer
				.parseInt(tipoRelatorio));
		try {
			retorno = processarExibicaoRelatorio(relatorioManterLocalidade,
					tipoRelatorio, httpServletRequest, httpServletResponse,
					actionMapping);

		} catch (SistemaException ex) {
			// manda o erro para a p�gina no request atual
			reportarErros(httpServletRequest, "erro.sistema");

			// seta o mapeamento de retorno para a tela de erro de popup
			retorno = actionMapping.findForward("telaErroPopup");

		} catch (RelatorioVazioException ex1) {
			// manda o erro para a p�gina no request atual
			reportarErros(httpServletRequest, "erro.relatorio.vazio");

			// seta o mapeamento de retorno para a tela de aten��o de popup
			retorno = actionMapping.findForward("telaAtencaoPopup");
		}

		// devolve o mapeamento contido na vari�vel retorno
		return retorno;
	}

}
