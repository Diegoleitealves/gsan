package gcom.gui.micromedicao.hidrometro;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.hidrometro.FiltroHidrometroCapacidade;
import gcom.micromedicao.hidrometro.HidrometroCapacidade;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AtualizarCapacidadeHidrometroAction extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Obt�m a inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		AtualizarCapacidadeHidrometroActionForm atualizarCapacidadeHidrometroActionForm = (AtualizarCapacidadeHidrometroActionForm) actionForm;

		HidrometroCapacidade hidrometroCapacidade = (HidrometroCapacidade) sessao
				.getAttribute("hidrometroCapacidadeAtualizar");

		hidrometroCapacidade.setId(new Integer(
				atualizarCapacidadeHidrometroActionForm.getIdentificador()));
		hidrometroCapacidade
				.setDescricao(atualizarCapacidadeHidrometroActionForm
						.getDescricao());
		hidrometroCapacidade
				.setDescricaoAbreviada(atualizarCapacidadeHidrometroActionForm
						.getAbreviatura());
		hidrometroCapacidade.setLeituraMinimo(new Short(
				atualizarCapacidadeHidrometroActionForm.getNumMinimo()));
		hidrometroCapacidade.setLeituraMaximo((new Short(
				atualizarCapacidadeHidrometroActionForm.getNumMaximo())));
		hidrometroCapacidade
				.setIndicadorUso(atualizarCapacidadeHidrometroActionForm
						.getIndicadoruso());
		hidrometroCapacidade.setNumeroOrdem(new Short(
				atualizarCapacidadeHidrometroActionForm.getNumOrdem()));
		hidrometroCapacidade
				.setCodigoHidrometroCapacidade(atualizarCapacidadeHidrometroActionForm
						.getCodigo());

		String numMinimo = atualizarCapacidadeHidrometroActionForm
				.getNumMinimo();
		String numMaximo = atualizarCapacidadeHidrometroActionForm
				.getNumMaximo();
		String codigo = atualizarCapacidadeHidrometroActionForm.getCodigo();
		
		String numOrdem = atualizarCapacidadeHidrometroActionForm.getNumOrdem();

		Collection colecaoPesquisa = null;

		// O numero maximo de digitos de leitura do hidr�metro � obrigat�rio.
		if (numMaximo != null && !numMaximo.equalsIgnoreCase("")) {
			if (new Integer(numMaximo).intValue() < new Integer(numMinimo)
					.intValue()) {
				throw new ActionServletException(
						"atencao.numero_minimo_nao_pode_ser_maior_que_numero_maximo",
						null,
						"Numero maximo de digitos de leitura do hidr�metro");
			}
			hidrometroCapacidade.setLeituraMaximo(new Short(numMaximo));
		}

		// Verificar exist�ncia do c�digo da Capacidade do Hidrometro que n�o seja ele mesmo
		FiltroHidrometroCapacidade filtroHidrometroCapacidade = new FiltroHidrometroCapacidade();

		filtroHidrometroCapacidade.adicionarParametro(new ParametroSimples(
				FiltroHidrometroCapacidade.CODIGO_HIDROMETRO_CAPACIDADE,
				hidrometroCapacidade.getCodigoHidrometroCapacidade()));

		// Verificar exist�ncia da Capacidade do Hidrometro
		colecaoPesquisa = fachada.pesquisar(filtroHidrometroCapacidade,
				HidrometroCapacidade.class.getName());

		if (colecaoPesquisa != null && !colecaoPesquisa.isEmpty()) {

			HidrometroCapacidade hidrometroCapacidadeBase = (HidrometroCapacidade) colecaoPesquisa
					.iterator().next();

			if ((hidrometroCapacidadeBase.getId().intValue() != hidrometroCapacidade
					.getId().intValue())
					&& (hidrometroCapacidadeBase
							.getCodigoHidrometroCapacidade()
							.equalsIgnoreCase(hidrometroCapacidade
									.getCodigoHidrometroCapacidade()))) {

				// Capacidade de hidrometro j� existe
				throw new ActionServletException(
						"atencao.pesquisa_capacidade_do_hidrometro_ja_cadastrada",
						null, codigo);
			}

		}
		
		//Verifica a Existencia de um Numero de Ordem J� existente na base que n�o seja ele mesmo
		
		filtroHidrometroCapacidade = new FiltroHidrometroCapacidade();
		
		filtroHidrometroCapacidade.adicionarParametro(new ParametroSimples(
				FiltroHidrometroCapacidade.NUMERO_ORDEM,
				hidrometroCapacidade.getNumeroOrdem()));

		// Verificar exist�ncia do numero de ordem da Capacidade do Hidrometro
		colecaoPesquisa = fachada.pesquisar(filtroHidrometroCapacidade,
				HidrometroCapacidade.class.getName());

		if (colecaoPesquisa != null && !colecaoPesquisa.isEmpty()) {

			HidrometroCapacidade hidrometroCapacidadeBase = (HidrometroCapacidade) colecaoPesquisa
					.iterator().next();

			if ((hidrometroCapacidadeBase.getId().intValue() != hidrometroCapacidade
					.getId().intValue())
					&& (hidrometroCapacidadeBase
							.getNumeroOrdem().toString()
							.equalsIgnoreCase(hidrometroCapacidade
									.getNumeroOrdem().toString()))) {

				// Numero de Ordem j� existe
				throw new ActionServletException(
						"atencao.pesquisa_numero_de_ordem_da_capacidade_do_hidrometro_ja_cadastrada",
						null, numOrdem);
			}

		}

		fachada.atualizarCapacidadeHidrometro(hidrometroCapacidade);

		montarPaginaSucesso(httpServletRequest,
				"Capacidade de Hidr�metro de c�digo "
						+ hidrometroCapacidade.getId().toString()
						+ " atualizado com sucesso.",
				"Realizar outra Manuten��o de Capacidade de Hid�metro",
				"exibirFiltrarCapacidadeHidrometroAction.do?menu=sim");
		return retorno;
	}
}
