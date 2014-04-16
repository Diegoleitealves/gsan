package gcom.gui.cadastro.imovel;

import gcom.cadastro.imovel.AreaConstruidaFaixa;
import gcom.cadastro.imovel.FiltroAreaConstruidaFaixa;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.ImovelEconomia;
import gcom.cadastro.imovel.ImovelSubcategoria;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.MaiorQue;
import gcom.util.filtro.MenorQue;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action responsavel para inserir o im�vel economia na cole��o do cliente
 * im�vel economia
 * 
 * @author S�vio Luiz
 * @created 20 de Maio de 2004
 */
public class InserirEconomiaPopupAction extends GcomAction {
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
				.findForward("inserirEconomiaPopup");

		// Obt�m o action form
		EconomiaPopupActionForm economiaPopupActionForm = (EconomiaPopupActionForm) actionForm;

		// Obt�m a inst�ncia da sess�o
		HttpSession sessao = httpServletRequest.getSession(false);

		Collection colecaoClientesImoveisEconomia = null;

		//Collection colecaoImovelEconomiaClientes = null;

		Collection colecaoImovelEconomiasModificadas = null;

		ImovelSubcategoria imovelSubCategoria = (ImovelSubcategoria) sessao
				.getAttribute("imovelSubCategoria");

		Fachada fachada = Fachada.getInstancia();

		Collection imovelEconomias = null;

		if (imovelSubCategoria.getImovelEconomias() == null
				|| imovelSubCategoria.getImovelEconomias().equals("")) {

			imovelEconomias = new ArrayList();

		} else {
			imovelEconomias = imovelSubCategoria.getImovelEconomias();
		}

		if (imovelEconomias.size() <= imovelSubCategoria
				.getQuantidadeEconomias()) {

			// Cole��o vinda do imovel_inserir_economia_popup
			// com a cole��o de cliente, imovel e tipo da rela��o
			if (sessao.getAttribute("colecaoClientesImoveisEconomia") != null) {
				colecaoClientesImoveisEconomia = (Collection) sessao
						.getAttribute("colecaoClientesImoveisEconomia");

			} else {
				colecaoClientesImoveisEconomia = new ArrayList();
			}
			// pega da sess�o a cole��o para os imoveis economias inseridos e/ou
			// modificados
			colecaoImovelEconomiasModificadas = (Collection) sessao
					.getAttribute("colecaoImovelEconomiasModificadas");

			Imovel imovel = (Imovel) sessao.getAttribute("imovel");

			// Cria objeto areaConstruida
			AreaConstruidaFaixa areaConstruidaFaixa = null;
			BigDecimal areaConstruida = null;
			Integer areaContruidaFaixaForm = (new Integer(
					economiaPopupActionForm.getIdAreaConstruidaFaixa()
							.toString()));

			if (areaContruidaFaixaForm != null
					&& areaContruidaFaixaForm.intValue() != ConstantesSistema.NUMERO_NAO_INFORMADO) {
				areaConstruidaFaixa = new AreaConstruidaFaixa();
				areaConstruidaFaixa.setId(areaContruidaFaixaForm);
				/*if (economiaPopupActionForm.getAreaConstruida() != null
						&& !economiaPopupActionForm.getAreaConstruida().equals(
								"")) {
					
				}*/
			} else {
				if (economiaPopupActionForm.getAreaConstruida() != null
						&& !economiaPopupActionForm.getAreaConstruida().equals(
								"")) {
					Collection areasConstruidasFaixas = null;
					
					
					String areaConstuidaPrrv = economiaPopupActionForm.getAreaConstruida().replace(".", "");
					areaConstruida = (new BigDecimal(areaConstuidaPrrv.replace(",",".")));

					// Filtro AreaConstruidaFaixa
					FiltroAreaConstruidaFaixa filtroAreaConstruidaFaixa = new FiltroAreaConstruidaFaixa();

					// fazer a parte de filtro adicionar parametro maior que
					// e filtro adicionar parametro menos que
					filtroAreaConstruidaFaixa
							.adicionarParametro(new ParametroSimples(
									FiltroAreaConstruidaFaixa.INDICADOR_USO,
									ConstantesSistema.INDICADOR_USO_ATIVO));

					filtroAreaConstruidaFaixa.adicionarParametro(new MaiorQue(
							FiltroAreaConstruidaFaixa.MAIOR_FAIXA,
							areaConstruida));

					filtroAreaConstruidaFaixa.adicionarParametro(new MenorQue(
							FiltroAreaConstruidaFaixa.MENOR_FAIXA,
							areaConstruida));

					areasConstruidasFaixas = fachada.pesquisar(
							filtroAreaConstruidaFaixa,
							AreaConstruidaFaixa.class.getName());

					if (areasConstruidasFaixas != null
							&& !areasConstruidasFaixas.isEmpty()) {
						Iterator areaContruidaFaixaIterator = areasConstruidasFaixas
								.iterator();

						areaConstruidaFaixa = (AreaConstruidaFaixa) areaContruidaFaixaIterator
								.next();

					}
				}
			}

			// recupera os dados do form
			String complementoEndereco = (String) economiaPopupActionForm
					.getComplementoEndereco();
			
			Short numeroMoradores = null;
			
			if (economiaPopupActionForm.getNumeroMorador() != null
					&& !economiaPopupActionForm.getNumeroMorador().equalsIgnoreCase("")){
				numeroMoradores = (new Short(economiaPopupActionForm
						.getNumeroMorador().toString()));
			}
			
			Short numeroPontoUtilizacao = null;
			if (economiaPopupActionForm.getNumeroPontosUtilizacao() != null 
					&& !economiaPopupActionForm.getNumeroPontosUtilizacao().equalsIgnoreCase("")){
				numeroPontoUtilizacao = (new Short(economiaPopupActionForm
						.getNumeroPontosUtilizacao().toString()));
			}
			
			String numeroIptu = null;
			if (economiaPopupActionForm.getNumeroIptu() != null
					&& !economiaPopupActionForm.getNumeroIptu().equals("")) {
				// verifica se existe no imovel, no imovelEconomia ou na cole��o
				// que ser�
				// inserida se existe algum iptu cadastrado com o numero do iptu
				// digitado no
				// mesmo municipio
				fachada.verificarExistenciaIPTU(
						colecaoImovelEconomiasModificadas, imovel,
						economiaPopupActionForm.getNumeroIptu(), new Date());

				numeroIptu = Util.formatarIPTU(economiaPopupActionForm
						.getNumeroIptu().toString());

			}

			Long numeroContratoEnergia = null;
			if (economiaPopupActionForm.getNumeroCelpe() != null
					&& !economiaPopupActionForm.getNumeroCelpe().equals("")) {

				// verifica se existe no imovel, no imovelEconomia ou na cole��o
				// que ser�
				// inserida se existe algum iptu cadastrado com o numero da
				// celpe digitado
				fachada.verificarExistenciaCelpe(
						colecaoImovelEconomiasModificadas, imovel,
						economiaPopupActionForm.getNumeroCelpe(), new Date());
				numeroContratoEnergia = (new Long(economiaPopupActionForm
						.getNumeroCelpe().toString()));

			}

			ImovelEconomia imovelEconomia = new ImovelEconomia(
					complementoEndereco, numeroMoradores,
					numeroPontoUtilizacao, numeroIptu, numeroContratoEnergia,
					areaConstruida, new Date(), areaConstruidaFaixa,
					imovelSubCategoria, new HashSet(
							colecaoClientesImoveisEconomia));

			// seta um codigo modificado para quando for atualizado esse imovel
			// economia
			// n�o colocar na cole��o de colecaoImovelEconomiasModificadas
			imovelEconomia.setCodigoModificado(imovelEconomia.getHashCode());

			if (!imovelEconomias.contains(imovelEconomia)) {
				imovelEconomias.add(imovelEconomia);
				imovelSubCategoria.setImovelEconomias(new HashSet(
						imovelEconomias));
				colecaoImovelEconomiasModificadas.add(imovelEconomia);

			} else {
				throw new ActionServletException(
						"atencao.ja_cadastrado.imovel_economia");
			}
			// para incluir mais rela��es entre cliente e imoveis, se preciso
			sessao.setAttribute("colecaoClientesImoveisEconomia",
					new ArrayList());

			// manda a cole��o para a sess�o
			sessao.setAttribute("colecaoImovelEconomiasModificadas",
					colecaoImovelEconomiasModificadas);

			// inicializa o property idClienteImovelUsuarioEconomias para a
			// inclus�o de
			// novas rela��es entre cliente imovel do tipo usu�rio
			economiaPopupActionForm.setIdClienteImovelUsuario(null);

			// inicializa todos os propertys para nulo
			economiaPopupActionForm.setComplementoEndereco(null);
			economiaPopupActionForm.setNumeroPontosUtilizacao(null);
			economiaPopupActionForm.setNumeroMorador(null);
			economiaPopupActionForm.setNumeroIptu(null);
			economiaPopupActionForm.setNumeroCelpe(null);
			economiaPopupActionForm.setAreaConstruida(null);
			economiaPopupActionForm.setIdAreaConstruidaFaixa(null);
			economiaPopupActionForm.setIdCliente(null);
			economiaPopupActionForm.setNomeCliente(null);

			if (httpServletRequest.getParameter("testeInserir")
					.equalsIgnoreCase("true")) {
				httpServletRequest.setAttribute("testeInserir", "true");
			}

			// Manda a cole��o do imovel_inserir_economia_popup
			// com a cole��o de cliente, imovel e tipo da rela��o
			sessao.setAttribute("contIdentificadorTemp", new Integer(
					imovelEconomias.size() + 1));

		} else {
			throw new ActionServletException(
					"atencao.quantidade_ultrapassada.imovel_economia", null,
					new Integer(imovelSubCategoria.getImovelEconomias().size())
							.toString());
		}

		return retorno;
	}
}
