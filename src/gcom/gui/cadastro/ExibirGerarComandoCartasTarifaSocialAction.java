package gcom.gui.cadastro;

import gcom.cadastro.localidade.FiltroGerenciaRegional;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.FiltroUnidadeNegocio;
import gcom.cadastro.localidade.GerenciaRegional;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.UnidadeNegocio;
import gcom.cadastro.tarifasocial.bean.CriterioSelecaoHelper;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 
 * @author Vivianne Sousa
 * @data 23/03/2011
 */
public class ExibirGerarComandoCartasTarifaSocialAction extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		ActionForward retorno = actionMapping
				.findForward("exibirGerarComandoCartasTarifaSocial");

		Fachada fachada = Fachada.getInstancia();
		GerarComandoCartasTarifaSocialActionForm form = (GerarComandoCartasTarifaSocialActionForm) actionForm;
		 
		if(httpServletRequest.getParameter("menu") != null){
			limparForm(form);
		}
		
		//pesquisa Ger�ncia Regional
		FiltroGerenciaRegional filtroGerenciaRegional = new FiltroGerenciaRegional();
		Collection<GerenciaRegional> gerenciasRegionais = fachada.pesquisar(
				filtroGerenciaRegional, GerenciaRegional.class.getName());
		httpServletRequest.setAttribute("colecaoGerenciaRegional",gerenciasRegionais);
		
		//pesquisa Unidade de Neg�cio
		FiltroUnidadeNegocio filtroUnidadeNegocio = new FiltroUnidadeNegocio();
		filtroUnidadeNegocio.setCampoOrderBy(FiltroUnidadeNegocio.NOME);
		Collection<UnidadeNegocio> colecaoUnidadeNegocio = fachada.pesquisar(
				filtroUnidadeNegocio, UnidadeNegocio.class.getName());
		httpServletRequest.setAttribute("colecaoUnidadeNegocio", colecaoUnidadeNegocio);
		
		montarTabelaCriteriosSelecao(httpServletRequest);
		
		//Pega os codigos que o usuario digitou para a pesquisa direta de localidade
		String codigoLocalidade = httpServletRequest.getParameter("codigoLocalidade");

		if (codigoLocalidade != null && !codigoLocalidade.trim().equals("")) {
			pesquisarLocalidade(codigoLocalidade, httpServletRequest);

		}
		
		if(form.getTipoCarta() != null && !form.getTipoCarta().equals("")){
			httpServletRequest.setAttribute("tipoCarta", (new Integer(form.getTipoCarta())).intValue());
		}
		
		return retorno;
	}
	
	/**
	 * Pesquisa uma localidade informada e prepara os dados para exibi��o na tela
	 */
	private void pesquisarLocalidade(String idLocalidade,
			HttpServletRequest httpServletRequest) {

		Fachada fachada = Fachada.getInstancia();

		// Pesquisa a localidade na base
		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
		filtroLocalidade.adicionarParametro(new ParametroSimples(
				FiltroLocalidade.ID, idLocalidade));

		Collection<Localidade> localidadePesquisada = fachada.pesquisar(
				filtroLocalidade, Localidade.class.getName());

		// Se nenhuma localidade for encontrada a mensagem � enviada para a p�gina
		if (localidadePesquisada == null || localidadePesquisada.isEmpty()) {
			// [FS0001 - Verificar exist�ncia de dados]
			httpServletRequest.setAttribute("codigoLocalidade", "");
			httpServletRequest.setAttribute("descricaoLocalidade","Localidade Inexistente".toUpperCase());
		}

		// obtem o imovel pesquisado
		if (localidadePesquisada != null && !localidadePesquisada.isEmpty()) {
			Localidade localidade = localidadePesquisada.iterator().next();
			// Manda a Localidade pelo request
			httpServletRequest.setAttribute("codigoLocalidade", idLocalidade);
			httpServletRequest.setAttribute("descricaoLocalidade", localidade.getDescricao());
		}

	}
	
	
	private void montarTabelaCriteriosSelecao(HttpServletRequest httpServletRequest) {

		CriterioSelecaoHelper helper = new CriterioSelecaoHelper();
		Collection colecaoCriterios = new ArrayList();
		
		helper.setCodigoCriterio(new Integer(1));
		helper.setDescricaoCriterio("Cliente sem CPF cadastrado");
		colecaoCriterios.add(helper);
		
		helper = new CriterioSelecaoHelper();
		helper.setCodigoCriterio(new Integer(2));
		helper.setDescricaoCriterio("Cliente sem o n�mero da identidade");
		colecaoCriterios.add(helper);
		
		helper = new CriterioSelecaoHelper();
		helper.setCodigoCriterio(new Integer(3));
		helper.setDescricaoCriterio("Im�vel sem o n�mero do contrato de energia");
		colecaoCriterios.add(helper);
		
		helper = new CriterioSelecaoHelper();
		helper.setCodigoCriterio(new Integer(4));
		helper.setDescricaoCriterio("Im�vel com pend�ncia nos dados do contrato de energia");
		colecaoCriterios.add(helper);
		
		helper = new CriterioSelecaoHelper();
		helper.setCodigoCriterio(new Integer(5));
		helper.setDescricaoCriterio("Cliente com cart�o do tipo programa social");
		colecaoCriterios.add(helper);
		
		helper = new CriterioSelecaoHelper();
		helper.setCodigoCriterio(new Integer(6));
		helper.setDescricaoCriterio("Cliente com validade do cart�o seguro desemprego vencido");
		colecaoCriterios.add(helper);
		
		helper = new CriterioSelecaoHelper();
		helper.setCodigoCriterio(new Integer(7));
		helper.setDescricaoCriterio("Cliente com renda comprovada");
		colecaoCriterios.add(helper);
		
		helper = new CriterioSelecaoHelper();
		helper.setCodigoCriterio(new Integer(8));
		helper.setDescricaoCriterio("Cliente com renda declarada");
		colecaoCriterios.add(helper);
		
		helper = new CriterioSelecaoHelper();
		helper.setCodigoCriterio(new Integer(9));
		helper.setDescricaoCriterio("Im�vel com mais de uma economia");
		colecaoCriterios.add(helper);
		
		helper = new CriterioSelecaoHelper();
		helper.setCodigoCriterio(new Integer(10));
		helper.setDescricaoCriterio("Cliente sem recadastramento h� mais de 02(dois) anos");
		colecaoCriterios.add(helper);
		
		httpServletRequest.setAttribute("colecaoCriterios",colecaoCriterios);

	}
	
	private void limparForm(GerarComandoCartasTarifaSocialActionForm form) {

		form.setCodigoLocalidade("");
		
		form.setDescricaoLocalidade("");
		
		form.setUnidadeNegocioId("");
		
		form.setGerenciaRegionalId("");
		
	    form.setTipoCarta("");

	    form.setQtdeDiasAtraso("");
	    
	    form.setPrazoComparecerCompesa("");
	    
	    form.setAnoMesPesquisaInicial("");
	    
	    form.setAnoMesPesquisaFinal("");

	}
}
