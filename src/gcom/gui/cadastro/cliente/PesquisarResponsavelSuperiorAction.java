package gcom.gui.cadastro.cliente;

import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.ClienteTipo;
import gcom.cadastro.cliente.FiltroCliente;
import gcom.cadastro.cliente.bean.PesquisarClienteResponsavelSuperiorHelper;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ComparacaoTexto;
import gcom.util.filtro.ComparacaoTextoCompleto;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

/**
 * Realiza a pesquisa de responsavel superior de acordo com os par�metros
 * informados
 * 
 * @author S�vio Luiz
 * @created 25 de Abril de 2005
 */
public class PesquisarResponsavelSuperiorAction extends GcomAction {
	/**
	 * Description of the Method
	 * 
	 * @param actionMapping
	 *            Description of the Parameter
	 * @param actionForm
	 *            Description of the Parameter
	 * @param httpServletRequest
	 *            Description of the Parameter
	 * @param httpServletResponse
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		ActionForward retorno = actionMapping
				.findForward("listaResponsavelSuperior");

		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		DynaValidatorForm pesquisarActionForm = (DynaValidatorForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		boolean peloMenosUmParametroInformado = false;

		// Recupera os par�metros do form
		String nome = (String) pesquisarActionForm.get("nome");
		String tipoPesquisa = (String) pesquisarActionForm.get("tipoPesquisa");
		String cnpj = (String) pesquisarActionForm.get("cnpj");
		String idEsferaPoder = (String) pesquisarActionForm
				.get("idEsferaPoder");
		String indicadorUso = (String) pesquisarActionForm.get("indicadorUso");

		if (nome == null || nome.equalsIgnoreCase("")) {
			nome = (String) sessao.getAttribute("nome");
		}
		if (cnpj == null || cnpj.equalsIgnoreCase("")) {
			cnpj = (String) sessao.getAttribute("cnpj");
		}
		if (tipoPesquisa == null || tipoPesquisa.equalsIgnoreCase("")) {
			tipoPesquisa = (String) sessao.getAttribute("tipoPesquisa");
		}
		if (idEsferaPoder == null || idEsferaPoder.equalsIgnoreCase("")) {
			idEsferaPoder = (String) sessao.getAttribute("idEsferaPoder");
		}
		if (indicadorUso == null || indicadorUso.equalsIgnoreCase("")) {
			indicadorUso = (String) sessao.getAttribute("indicadorUso");
		}
		FiltroCliente filtroCliente = new FiltroCliente();

		filtroCliente.setCampoOrderBy(FiltroCliente.NOME);

		PesquisarClienteResponsavelSuperiorHelper helper = new PesquisarClienteResponsavelSuperiorHelper();

		// Insere os par�metros informados no filtro
		if (nome != null && !nome.trim().equalsIgnoreCase("")) {
			peloMenosUmParametroInformado = true;
			helper.setNome(nome);
			if (tipoPesquisa != null
					&& tipoPesquisa
							.equals(ConstantesSistema.TIPO_PESQUISA_COMPLETA
									.toString())) {
				helper
						.setTipoPesquisa(ConstantesSistema.TIPO_PESQUISA_COMPLETA);
				filtroCliente.adicionarParametro(new ComparacaoTextoCompleto(
						FiltroCliente.NOME, nome));
			} else {
				helper.setTipoPesquisa(ConstantesSistema.TIPO_PESQUISA_INICIAL);
				filtroCliente.adicionarParametro(new ComparacaoTexto(
						FiltroCliente.NOME, nome));
			}
		}

		if (cnpj != null && !cnpj.trim().equalsIgnoreCase("")) {
			peloMenosUmParametroInformado = true;
			helper.setCnpj(cnpj);
			filtroCliente.adicionarParametro(new ParametroSimples(
					FiltroCliente.CNPJ, cnpj));
		}

		if (idEsferaPoder != null
				&& !idEsferaPoder.trim().equals(
						"" + ConstantesSistema.NUMERO_NAO_INFORMADO)) {
			peloMenosUmParametroInformado = true;
			helper.setIdEsferaPoder(new Integer(idEsferaPoder));
			filtroCliente.adicionarParametro(new ParametroSimples(
					FiltroCliente.ESFERA_PODER_ID, idEsferaPoder));
		}

		if (indicadorUso != null && !indicadorUso.trim().equals("")) {
			peloMenosUmParametroInformado = true;
			helper.setIndicadorUso(new Short(indicadorUso));
			filtroCliente.adicionarParametro(new ParametroSimples(
					FiltroCliente.INDICADOR_USO, indicadorUso));
		}

		filtroCliente.adicionarParametro(new ParametroSimples(
				FiltroCliente.TIPOCLIENTE_IPFJ,
				ClienteTipo.INDICADOR_PESSOA_JURIDICA));

		// filtroCliente.adicionarParametro(new ParametroSimples(
		// FiltroCliente.INDICADOR_USO,
		// ConstantesSistema.INDICADOR_USO_ATIVO));

		// Erro caso o usu�rio mandou filtrar sem nenhum par�metro

		Collection responsavelSuperiores = null;

		// Aciona o controle de pagina��o para que sejam pesquisados apenas
		// os registros que aparecem na p�gina
		if (sessao.getAttribute("pesquisaSuperior") != null
				&& !sessao.getAttribute("pesquisaSuperior").equals("")) {
			// 1� Passo - Pegar o total de registros atrav�s de um count
			// da
			// consulta que aparecer� na tela
			Integer totalRegistros = fachada
					.pesquisarClienteResponsavelSuperiorParaPaginacaoCount(helper);

			// 2� Passo - Chamar a fun��o de Pagina��o passando o total
			// de registros
			retorno = this.controlarPaginacao(httpServletRequest, retorno,
					totalRegistros);

			// 3� Passo - Obter a cole��o da consulta que aparecer� na
			// tela
			// passando o numero de paginas da pesquisa que est� no
			// request
			responsavelSuperiores = fachada
					.pesquisarClienteResponsavelSuperiorParaPaginacao(helper,
							(Integer) httpServletRequest
									.getAttribute("numeroPaginasPesquisa"));

		} else {
			Map resultado = controlarPaginacao(httpServletRequest, retorno,
					filtroCliente, Cliente.class.getName());
			responsavelSuperiores = (Collection) resultado
					.get("colecaoRetorno");
			retorno = (ActionForward) resultado.get("destinoActionForward");
		}

		if (responsavelSuperiores == null || responsavelSuperiores.isEmpty()) {
			// Nenhuma empresa cadastrada
			throw new ActionServletException(
					"atencao.pesquisa.nenhumresultado", null, "cliente");
		} else {
			if (peloMenosUmParametroInformado) {
				// Coloca a cole��o na sess�o
				sessao.setAttribute("colecaoResponsavelSuperiores",
						responsavelSuperiores);
				sessao.setAttribute("nome", nome);
				sessao.setAttribute("cnpj", cnpj);
				sessao.setAttribute("idEsferaPoder", idEsferaPoder);
				sessao.setAttribute("indicadorUso", indicadorUso);
				sessao.setAttribute("tipoPesquisa", tipoPesquisa);
			}
		}

		if (!peloMenosUmParametroInformado) {
			throw new ActionServletException(
					"atencao.filtro.nenhum_parametro_informado");

		}
		pesquisarActionForm.set("nome", "");
		pesquisarActionForm.set("cnpj", "");
		
		
		//Coloca na sessao o parametro tipoConsulta
		String tipoConsulta = httpServletRequest.getParameter("tipoConsulta");
		if(tipoConsulta == null || tipoConsulta.equals("")){
			tipoConsulta = (String) sessao.getAttribute("tipoConsulta");
		}
		if(tipoConsulta != null && !tipoConsulta.equals("")){
			sessao.setAttribute("tipoConsulta", tipoConsulta);
		}else{
			sessao.removeAttribute("tipoConsulta");
		}

		return retorno;
	}
}
