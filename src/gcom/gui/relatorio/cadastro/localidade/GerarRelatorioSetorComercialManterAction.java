package gcom.gui.relatorio.cadastro.localidade;

import gcom.cadastro.geografico.FiltroMunicipio;
import gcom.cadastro.geografico.Municipio;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.FiltroSetorComercial;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.SetorComercial;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.cadastro.localidade.PesquisarFiltrarSetorComercialActionForm;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.cadastro.localidade.RelatorioManterSetorComercial;
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

public class GerarRelatorioSetorComercialManterAction extends ExibidorProcessamentoTarefaRelatorio {

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

		PesquisarFiltrarSetorComercialActionForm pesquisarAtualizarSetorComercialActionForm = (PesquisarFiltrarSetorComercialActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		FiltroSetorComercial filtroSetorComercial = (FiltroSetorComercial) sessao
				.getAttribute("filtroSetorComercial");

		// Inicio da parte que vai mandar os parametros para o relat�rio

		SetorComercial setorComercialParametros = new SetorComercial();

		String idMunicipio = (String) pesquisarAtualizarSetorComercialActionForm
				.getMunicipioID();

		Municipio municipio = null;

		if (idMunicipio != null && !idMunicipio.equals("")) {
			FiltroMunicipio filtroMunicipio = new FiltroMunicipio();

			filtroMunicipio.adicionarParametro(new ParametroSimples(
					FiltroMunicipio.ID, idMunicipio));
			filtroMunicipio.adicionarParametro(new ParametroSimples(
					FiltroMunicipio.INDICADOR_USO,
					ConstantesSistema.INDICADOR_USO_ATIVO));

			Collection municipios = fachada.pesquisar(filtroMunicipio,
					Municipio.class.getName());

			if (municipios != null && !municipios.isEmpty()) {
				// O municipio foi encontrado
				Iterator municipioIterator = municipios.iterator();

				municipio = (Municipio) municipioIterator.next();

			} else {
				throw new ActionServletException(
						"atencao.pesquisa_inexistente", null, "Munic�pio");
			}

		} else {
			municipio = new Municipio();
		}

		String idLocalidade = (String) pesquisarAtualizarSetorComercialActionForm
				.getLocalidadeID();

		Localidade localidade = null;

		if (idLocalidade != null && !idLocalidade.equals("")) {
			FiltroLocalidade filtroLocalidade = new FiltroLocalidade();

			filtroLocalidade.adicionarParametro(new ParametroSimples(
					FiltroLocalidade.ID, idLocalidade));
			filtroLocalidade.adicionarParametro(new ParametroSimples(
					FiltroLocalidade.INDICADORUSO,
					ConstantesSistema.INDICADOR_USO_ATIVO));

			Collection localidades = fachada.pesquisar(filtroLocalidade,
					Localidade.class.getName());

			if (localidades != null && !localidades.isEmpty()) {
				// A localidade foi encontrada
				Iterator localidadeIterator = localidades.iterator();

				localidade = (Localidade) localidadeIterator.next();

			} else {
				throw new ActionServletException(
						"atencao.pesquisa_inexistente", null, "Localidade");
			}

		} else {
			localidade = new Localidade();
		}

		int codigoSetorComercial = 0;

		String codigoSetorComercialPesquisar = (String) pesquisarAtualizarSetorComercialActionForm
				.getSetorComercialCD();

		if (codigoSetorComercialPesquisar != null
				&& !codigoSetorComercialPesquisar.equals("")) {
			codigoSetorComercial = Integer
					.parseInt(codigoSetorComercialPesquisar);
		}

		Short indicadorDeUso = null;

		if (pesquisarAtualizarSetorComercialActionForm.getIndicadorUso() != null
				&& !pesquisarAtualizarSetorComercialActionForm
						.getIndicadorUso().equals("")) {

			indicadorDeUso = new Short(""
					+ pesquisarAtualizarSetorComercialActionForm
							.getIndicadorUso());
		}
		
		String nomeSetorComercial = "";
		
		if (pesquisarAtualizarSetorComercialActionForm
				.getSetorComercialNome() != null) {
			nomeSetorComercial = pesquisarAtualizarSetorComercialActionForm
			.getSetorComercialNome();
		}

		// seta os parametros que ser�o mostrados no relat�rio
		setorComercialParametros.setLocalidade(localidade);
		setorComercialParametros.setMunicipio(municipio);
		setorComercialParametros.setCodigo(codigoSetorComercial);
		setorComercialParametros.setDescricao(nomeSetorComercial);
		setorComercialParametros.setIndicadorUso(indicadorDeUso);

		// Fim da parte que vai mandar os parametros para o relat�rio

			// cria uma inst�ncia da classe do relat�rio
			RelatorioManterSetorComercial relatorioManterSetorComercial = new RelatorioManterSetorComercial(
					(Usuario)(httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));

			relatorioManterSetorComercial.addParametro("filtroSetorComercial",
					filtroSetorComercial);
			relatorioManterSetorComercial.addParametro(
					"setorComercialParametros", setorComercialParametros);

			// chama o met�do de gerar relat�rio passando o c�digo da analise
			// como par�metro
			String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");
			if (tipoRelatorio == null) {
				tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
			}

			relatorioManterSetorComercial.addParametro("tipoFormatoRelatorio", Integer
					.parseInt(tipoRelatorio));
			try {
				retorno = processarExibicaoRelatorio(relatorioManterSetorComercial,
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
