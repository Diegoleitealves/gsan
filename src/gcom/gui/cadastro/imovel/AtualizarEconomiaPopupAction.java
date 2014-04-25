package gcom.gui.cadastro.imovel;

import gcom.cadastro.imovel.AreaConstruidaFaixa;
import gcom.cadastro.imovel.FiltroAreaConstruidaFaixa;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.ImovelEconomia;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.MaiorQue;
import gcom.util.filtro.MenorQue;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action responsavel para atualizar o im�vel economia na cole��o do cliente
 * im�vel economia
 * 
 * @author S�vio Luiz
 * @created 20 de Maio de 2004
 */
public class AtualizarEconomiaPopupAction extends GcomAction {
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

		// localiza o action no objeto actionmapping
		ActionForward retorno = actionMapping
				.findForward("atualizarEconomiaPopup");

		// Obt�m o action form
		EconomiaPopupActionForm economiaPopupActionForm = (EconomiaPopupActionForm) actionForm;

		// Obt�m a inst�ncia da sess�o
		HttpSession sessao = httpServletRequest.getSession(false);

		Collection colecaoClientesImoveisEconomia = null;

		Collection colecaoImovelEconomiasModificadas = null;

		ImovelEconomia imovelEconomia = (ImovelEconomia) sessao
				.getAttribute("imovelEconomia");

		Fachada fachada = Fachada.getInstancia();

		// Cole��o vinda do imovel_inserir_economia_popup
		// com a cole��o de cliente, imovel e tipo da rela��o
		if (sessao.getAttribute("colecaoClientesImoveisEconomia") != null) {
			colecaoClientesImoveisEconomia = (Collection) sessao
					.getAttribute("colecaoClientesImoveisEconomia");

		} else {
			colecaoClientesImoveisEconomia = new ArrayList();
		}

		Imovel imovel = (Imovel) sessao.getAttribute("imovel");

		// pega da sess�o a cole��o para os imoveis economias inseridos e/ou
		// modificados
		colecaoImovelEconomiasModificadas = (Collection) sessao
				.getAttribute("colecaoImovelEconomiasModificadas");

		// Cria objeto areaConstruida
		AreaConstruidaFaixa areaConstruidaFaixa = null;
		BigDecimal  areaConstruida = null;

		Integer areaContruidaFaixaForm = (new Integer(economiaPopupActionForm
				.getIdAreaConstruidaFaixa()));

		if (areaContruidaFaixaForm != null
				&& areaContruidaFaixaForm.intValue() != ConstantesSistema.NUMERO_NAO_INFORMADO) {
			areaConstruidaFaixa = new AreaConstruidaFaixa();
			areaConstruidaFaixa.setId(areaContruidaFaixaForm);
			imovelEconomia.setAreaConstruidaFaixa(areaConstruidaFaixa);
			if (economiaPopupActionForm.getAreaConstruida() != null
					&& !economiaPopupActionForm.getAreaConstruida().equals("")) {
				
				String areaConstuidaPrrv = economiaPopupActionForm.getAreaConstruida().replace(".", "");
				areaConstruida = (new BigDecimal(areaConstuidaPrrv.replace(",",".")));
				imovelEconomia.setAreaConstruida(areaConstruida);
			} else {
				imovelEconomia.setAreaConstruida(null);
			}
		} else {
			if (economiaPopupActionForm.getAreaConstruida() != null
					&& !economiaPopupActionForm.getAreaConstruida().equals("")) {
				Collection areasConstruidasFaixas = null;
				
				String areaConstuidaPrrv = economiaPopupActionForm.getAreaConstruida().replace(".", "");
				areaConstruida = (new BigDecimal(areaConstuidaPrrv.replace(",",".")));
//				areaConstruida = (new BigDecimal(economiaPopupActionForm
//						.getAreaConstruida().toString().replace(",",".")));

				imovelEconomia.setAreaConstruida(areaConstruida);

				// Filtro AreaConstruidaFaixa
				FiltroAreaConstruidaFaixa filtroAreaConstruidaFaixa = new FiltroAreaConstruidaFaixa();

				// fazer a parte de filtro adicionar parametro maior que
				// e filtro adicionar parametro menos que
				filtroAreaConstruidaFaixa
						.adicionarParametro(new ParametroSimples(
								FiltroAreaConstruidaFaixa.INDICADOR_USO,
								ConstantesSistema.INDICADOR_USO_ATIVO));

				filtroAreaConstruidaFaixa.adicionarParametro(new MaiorQue(
						FiltroAreaConstruidaFaixa.MAIOR_FAIXA, areaConstruida));

				filtroAreaConstruidaFaixa.adicionarParametro(new MenorQue(
						FiltroAreaConstruidaFaixa.MENOR_FAIXA, areaConstruida));

				areasConstruidasFaixas = fachada.pesquisar(
						filtroAreaConstruidaFaixa, AreaConstruidaFaixa.class
								.getName());

				if (areasConstruidasFaixas != null
						&& !areasConstruidasFaixas.isEmpty()) {
					Iterator areaContruidaFaixaIterator = areasConstruidasFaixas
							.iterator();

					areaConstruidaFaixa = (AreaConstruidaFaixa) areaContruidaFaixaIterator
							.next();
					imovelEconomia.setAreaConstruidaFaixa(areaConstruidaFaixa);

				} else {
					imovelEconomia.setAreaConstruidaFaixa(null);
				}

			} else {
				imovelEconomia.setAreaConstruidaFaixa(null);
				imovelEconomia.setAreaConstruida(null);
			}
		}

		// recupera os dados do form
		imovelEconomia.setComplementoEndereco(economiaPopupActionForm
				.getComplementoEndereco());
		
		if (economiaPopupActionForm.getNumeroMorador() != null && !economiaPopupActionForm.getNumeroMorador().equalsIgnoreCase("")){
			imovelEconomia.setNumeroMorador(new Short(economiaPopupActionForm
					.getNumeroMorador().toString()));
		} else {
			imovelEconomia.setNumeroMorador(null);
		}
		
		if (economiaPopupActionForm.getNumeroPontosUtilizacao() != null && !economiaPopupActionForm.getNumeroPontosUtilizacao().equalsIgnoreCase("")){
			imovelEconomia
					.setNumeroPontosUtilizacao(new Short(economiaPopupActionForm
							.getNumeroPontosUtilizacao().toString()));
		}else{
			imovelEconomia.setNumeroPontosUtilizacao(null);
		}

		// verifica se existe um numero de iptu
		if (economiaPopupActionForm.getNumeroIptu() != null
				&& !economiaPopupActionForm.getNumeroIptu().equals("")) {

			// verifica se existe no imovel, no imovelEconomia ou na cole��o que
			// ser�
			// inserida se existe algum iptu cadastrado com o numero do iptu
			// digitado no
			// mesmo municipio.
			fachada.verificarExistenciaIPTU(colecaoImovelEconomiasModificadas,
					imovel, economiaPopupActionForm.getNumeroIptu(),
					imovelEconomia.getUltimaAlteracao());

			imovelEconomia.setNumeroIptu(Util.formatarIPTU(economiaPopupActionForm
					.getNumeroIptu()));
		} else {
			imovelEconomia.setNumeroIptu(null);
		}

		if (economiaPopupActionForm.getNumeroCelpe() != null
				&& !economiaPopupActionForm.getNumeroCelpe().equals("")) {

			// verifica se existe no imovel, no imovelEconomia ou na cole��o que
			// ser�
			// inserida se existe algum iptu cadastrado com o numero da celpe
			// digitado.
			fachada.verificarExistenciaCelpe(colecaoImovelEconomiasModificadas,
					imovel, economiaPopupActionForm.getNumeroCelpe(),
					imovelEconomia.getUltimaAlteracao());

			imovelEconomia.setNumeroCelpe(new Long(economiaPopupActionForm
					.getNumeroCelpe()));
		} else {
			imovelEconomia.setNumeroCelpe(null);
		}

		imovelEconomia.getClienteImovelEconomias().addAll(
				colecaoClientesImoveisEconomia);

		if (imovelEconomia.getCodigoModificado() == 0) {
			imovelEconomia.setCodigoModificado(imovelEconomia.getHashCode());
			colecaoImovelEconomiasModificadas.add(imovelEconomia);
		}

		// para incluir mais rela��es entre cliente e imoveis, se preciso
		sessao.setAttribute("colecaoClientesImoveisEconomia", new ArrayList());

		// inicializa o property idClienteImovelUsuarioEconomias para a inclus�o
		// de
		// novas rela��es entre cliente imovel do tipo usu�rio
		economiaPopupActionForm.setIdClienteImovelUsuario(null);

		if (httpServletRequest.getParameter("testeInserir").equalsIgnoreCase(
				"true")) {
			httpServletRequest.setAttribute("testeInserir", "true");
		}

		// inicializa todos os propertys para nulo
		economiaPopupActionForm.setComplementoEndereco(null);
		economiaPopupActionForm.setNumeroPontosUtilizacao(null);
		economiaPopupActionForm.setNumeroMorador(null);
		economiaPopupActionForm.setNumeroIptu(null);
		economiaPopupActionForm.setNumeroCelpe(null);
		economiaPopupActionForm.setAreaConstruida(null);
		economiaPopupActionForm.setIdAreaConstruidaFaixa(null);

		return retorno;
	}
}
