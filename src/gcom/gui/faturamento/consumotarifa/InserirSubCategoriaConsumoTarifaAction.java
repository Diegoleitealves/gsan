package gcom.gui.faturamento.consumotarifa;

import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.FiltroCategoria;
import gcom.cadastro.imovel.Subcategoria;
import gcom.fachada.Fachada;
import gcom.faturamento.consumotarifa.ConsumoTarifaCategoria;
import gcom.faturamento.consumotarifa.ConsumoTarifaFaixa;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.gui.faturamento.consumotarifa.bean.CategoriaFaixaConsumoTarifaHelper;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * < <Descri��o da Classe>>
 * 
 * @author Administrador
 */
public class InserirSubCategoriaConsumoTarifaAction extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		ActionForward retorno = actionMapping
				.findForward("exibirInserirSubCategoriaConsumoTarifa");

		Fachada fachada = Fachada.getInstancia();

		InserirSubCategoriaConsumoTarifaActionForm inserirSubCategoriaConsumoTarifaActionForm = (InserirSubCategoriaConsumoTarifaActionForm) actionForm;

		HttpSession sessao = httpServletRequest.getSession(false);

		Collection colecaoFaixa = (Collection) sessao
				.getAttribute("colecaoFaixa");

		if (colecaoFaixa != null) {

			if (colecaoFaixa == null || colecaoFaixa.isEmpty()) {
				throw new ActionServletException(
						"atencao.faixa_categoria_consumo_tarifa");
			}

		} else {
			throw new ActionServletException(
					"atencao.faixa_categoria_consumo_tarifa");
		}

		Iterator iteratorColecaoFaixa = colecaoFaixa.iterator();

		while (iteratorColecaoFaixa.hasNext()) {
			ConsumoTarifaFaixa consumoTarifaFaixa = (ConsumoTarifaFaixa) iteratorColecaoFaixa
					.next();
			String parametroConsumoTarifaFaixa = "valorConsumoTarifa"
					+ consumoTarifaFaixa.getUltimaAlteracao().getTime();
			consumoTarifaFaixa.setValorConsumoTarifa(Util
					.formatarMoedaRealparaBigDecimal(httpServletRequest
							.getParameter(parametroConsumoTarifaFaixa)));

		}

		// ######## Colocando os dados dos Forms na SessaoCategoria

		String idCategoria = inserirSubCategoriaConsumoTarifaActionForm
				.getSlcCategoria();

		String idSubCategoria = inserirSubCategoriaConsumoTarifaActionForm
		.getSlcSubCategoria();
		
		FiltroCategoria filtroCategoria = new FiltroCategoria();

		filtroCategoria.adicionarParametro(new ParametroSimples(
				FiltroCategoria.CODIGO, idCategoria));

		Collection colecaoCategoria = fachada.pesquisar(filtroCategoria,
				Categoria.class.getName());

		Categoria categoriaSelected = (Categoria) Util
				.retonarObjetoDeColecao(colecaoCategoria);

		ConsumoTarifaCategoria consumoTarifaCategoria = new ConsumoTarifaCategoria();

		consumoTarifaCategoria.setCategoria(categoriaSelected);
		Subcategoria subCategoria = new Subcategoria();
		subCategoria.setId(new Integer(idSubCategoria));
		consumoTarifaCategoria.setSubCategoria(subCategoria);

		Collection<CategoriaFaixaConsumoTarifaHelper> colecaoConsumoTarifaCategoria = new ArrayList();
		int numeroFaixasCategoria = 0;

		if (colecaoFaixa != null) {
			numeroFaixasCategoria = colecaoFaixa.size();
		}

		if (sessao.getAttribute("colecaoConsumoTarifaCategoria") != null) {

			colecaoConsumoTarifaCategoria = (Collection) sessao
					.getAttribute("colecaoConsumoTarifaCategoria");

			Collection colecaoCategoriaSemHelper = new ArrayList();

			if (colecaoCategoriaSemHelper.contains(consumoTarifaCategoria)) {
				throw new ActionServletException(
						"atencao.consumotaria.categoria_existente");
			}

			for (CategoriaFaixaConsumoTarifaHelper categoriaFaixaConsumoTarifaHelper : colecaoConsumoTarifaCategoria) {
				colecaoCategoriaSemHelper.add(categoriaFaixaConsumoTarifaHelper
						.getConsumoTarifaCategoria());

			}

			if ((colecaoFaixa != null) && (!colecaoFaixa.isEmpty())) {
				Iterator colecaoFaixaIt = colecaoFaixa.iterator();
				boolean i = false;
				while (colecaoFaixaIt.hasNext()) {
					ConsumoTarifaFaixa consumoTarifaFaixa = (ConsumoTarifaFaixa) colecaoFaixaIt
							.next();
					if (consumoTarifaFaixa.getNumeroConsumoFaixaFim()
							.toString().equals("999999")) {
						i = true;
					}

				}
				if (!i) {
					throw new ActionServletException(
							"atencao.faixa_limite_superior");
				}
			}

			if (colecaoCategoriaSemHelper.contains(consumoTarifaCategoria)) {
				throw new ActionServletException(
						"atencao.consumotaria.categoria_existente");

			} else {

				colecaoConsumoTarifaCategoria
						.add(new CategoriaFaixaConsumoTarifaHelper(
								numeroFaixasCategoria, consumoTarifaCategoria));
			}
		} else {

			if ((colecaoFaixa != null) && (!colecaoFaixa.isEmpty())) {
				Iterator colecaoFaixaIt = colecaoFaixa.iterator();
				boolean i = false;
				while (colecaoFaixaIt.hasNext()) {
					ConsumoTarifaFaixa consumoTarifaFaixa = (ConsumoTarifaFaixa) colecaoFaixaIt
							.next();
					if (consumoTarifaFaixa.getNumeroConsumoFaixaFim()
							.toString().equals("999999")) {
						i = true;
					}

				}
				if (!i) {
					throw new ActionServletException(
							"atencao.faixa_limite_superior");
				}
			}

			colecaoConsumoTarifaCategoria
					.add(new CategoriaFaixaConsumoTarifaHelper(
							numeroFaixasCategoria, consumoTarifaCategoria));

			sessao.setAttribute("colecaoConsumoTarifaCategoria",
					colecaoConsumoTarifaCategoria);
		}

		// Categoria
		consumoTarifaCategoria.setCategoria(categoriaSelected);

		// Consumo m�nimo
		consumoTarifaCategoria.setNumeroConsumoMinimo(new Integer(
				inserirSubCategoriaConsumoTarifaActionForm.getConsumoMinimo()));

		// Tarifa m�nima
		consumoTarifaCategoria
				.setValorTarifaMinima(Util
						.formatarMoedaRealparaBigDecimal(inserirSubCategoriaConsumoTarifaActionForm
								.getValorTarifaMinima()));

		// Ultima altera��o
		consumoTarifaCategoria.setUltimaAlteracao(new Date());

		// Atribuindo a colecao faixa valores da categoria
		colecaoFaixa = (Collection) sessao.getAttribute("colecaoFaixa");

		/*Iterator colecaoFaixaIterator =*/ colecaoFaixa.iterator();

		// while (colecaoFaixaIterator.hasNext()) {
		// ConsumoTarifaFaixa consumoTarifaFaixa = (ConsumoTarifaFaixa)
		// colecaoFaixaIterator
		// .next();
		Set colecaoFaixaSet = new HashSet();
		colecaoFaixaSet.addAll(colecaoFaixa);
		consumoTarifaCategoria.setConsumoTarifaFaixas(colecaoFaixaSet);
		// }
		// fim.
		if (httpServletRequest.getParameter("testeInserir").equalsIgnoreCase(
				"true")) {
			httpServletRequest.setAttribute("testeInserir", "true");
		}

		return retorno;
	}
}
