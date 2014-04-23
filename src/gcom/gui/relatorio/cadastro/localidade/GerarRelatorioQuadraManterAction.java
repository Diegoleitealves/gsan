package gcom.gui.relatorio.cadastro.localidade;

import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.FiltroQuadra;
import gcom.cadastro.localidade.FiltroSetorComercial;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.Quadra;
import gcom.cadastro.localidade.SetorComercial;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.cadastro.localidade.FiltrarQuadraActionForm;
import gcom.micromedicao.Rota;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.cadastro.localidade.RelatorioManterQuadra;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
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
 * < <Descri��o da Classe>>
 * 
 * @author Rafael Corr�a
 * @created 7 de Novembro de 2005
 */
public class GerarRelatorioQuadraManterAction extends ExibidorProcessamentoTarefaRelatorio {

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

		FiltrarQuadraActionForm filtrarQuadraActionForm = (FiltrarQuadraActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		FiltroQuadra filtroQuadra = (FiltroQuadra) sessao.getAttribute("filtroQuadra");

		// Inicio da parte que vai mandar os parametros para o relat�rio

		Quadra quadraParametros = new Quadra();

		String idLocalidade = (String) filtrarQuadraActionForm
				.getLocalidadeID();
		String nomeLocalidade = (String) filtrarQuadraActionForm
				.getLocalidadeNome();

		Localidade localidade = new Localidade();

		if (idLocalidade != null && !idLocalidade.equals("")) {
			if (nomeLocalidade != null && !nomeLocalidade.equals("")) {
				localidade.setId(new Integer(idLocalidade));
				localidade.setDescricao(nomeLocalidade);

			} else {

				FiltroLocalidade filtroLocalidade = new FiltroLocalidade();

				filtroLocalidade.adicionarParametro(new ParametroSimples(
						FiltroLocalidade.ID, idLocalidade));

				Collection localidades = fachada.pesquisar(filtroLocalidade,
						Localidade.class.getName());

				if (localidades != null && !localidades.isEmpty()) {
					// A localidade foi encontrada
					Iterator localidadeIterator = localidades.iterator();

					Localidade localidadePesquisa = (Localidade) localidadeIterator
							.next();

					localidade.setId(localidadePesquisa.getId());
					localidade.setDescricao(localidadePesquisa.getDescricao());

				} else {
					throw new ActionServletException(
							"atencao.pesquisa_inexistente", null, "Localidade");
				}
			}

		}

		String idSetorComercial = (String) filtrarQuadraActionForm
				.getSetorComercialCD();
		String nomeSetorComercial = (String) filtrarQuadraActionForm
				.getSetorComercialNome();

		SetorComercial setorComercial = new SetorComercial();

		if (idSetorComercial != null && !idSetorComercial.equals("")) {
			if (nomeSetorComercial != null && !nomeSetorComercial.equals("")) {
				setorComercial.setCodigo(new Integer(idSetorComercial));
				setorComercial.setDescricao(nomeSetorComercial);
			} else {
				FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();

				filtroSetorComercial.adicionarParametro(new ParametroSimples(
						FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, idSetorComercial));
				filtroSetorComercial.adicionarParametro(new ParametroSimples(
						FiltroSetorComercial.ID_LOCALIDADE, idLocalidade));

				Collection setoresComerciais = fachada.pesquisar(
						filtroSetorComercial, SetorComercial.class.getName());

				if (setoresComerciais != null && !setoresComerciais.isEmpty()) {
					// O Setor Comercial foi encontrado
					Iterator setorComercialIterator = setoresComerciais
							.iterator();

					SetorComercial setorComercialPesquisa = (SetorComercial) setorComercialIterator
							.next();

					setorComercial.setCodigo(setorComercialPesquisa.getCodigo());
					setorComercial.setDescricao(setorComercialPesquisa
							.getDescricao());

				} else {
					throw new ActionServletException(
							"atencao.pesquisa_inexistente", null,
							"Setor Comercial");
				}
			}

		}

		String quadraNM = null;

		String quadraNMPesquisar = (String) filtrarQuadraActionForm
				.getQuadraNM();

		if (quadraNMPesquisar != null && !quadraNMPesquisar.equals("")) {
			quadraNM = quadraNMPesquisar;
		}

		Short indicadorDeUso = null;

		if (filtrarQuadraActionForm.getIndicadorUso() != null
				&& !filtrarQuadraActionForm.getIndicadorUso()
						.equals("")) {

			indicadorDeUso = new Short(filtrarQuadraActionForm
					.getIndicadorUso());
		}

		// seta os parametros que ser�o mostrados no relat�rio

		setorComercial.setLocalidade(localidade);
		quadraParametros.setSetorComercial(setorComercial);
		if (quadraNM != null) {
			quadraParametros
					.setNumeroQuadra(Integer
							.parseInt(quadraNM));
		} else {
			quadraParametros
			.setNumeroQuadra(-1);
		}
		
		if (filtrarQuadraActionForm.getCodigoRota() != null && !filtrarQuadraActionForm.getCodigoRota().trim().equals("")) {
			Rota rota = new Rota();
			rota.setCodigo(new Short(filtrarQuadraActionForm.getCodigoRota()));
			quadraParametros.setRota(rota);
		}

		quadraParametros.setIndicadorUso(indicadorDeUso);

		// Fim da parte que vai mandar os parametros para o relat�rio

			// cria uma inst�ncia da classe do relat�rio
			RelatorioManterQuadra relatorioManterQuadra = new RelatorioManterQuadra(
					(Usuario)(httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));

			relatorioManterQuadra.addParametro("filtroQuadra", filtroQuadra);
			relatorioManterQuadra.addParametro("quadraParametros",
					quadraParametros);

			// chama o met�do de gerar relat�rio passando o c�digo da analise
			// como par�metro
			String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");
			if (tipoRelatorio == null) {
				tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
			}

			relatorioManterQuadra.addParametro("tipoFormatoRelatorio", Integer
					.parseInt(tipoRelatorio));
			try {
				retorno = processarExibicaoRelatorio(relatorioManterQuadra,
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
