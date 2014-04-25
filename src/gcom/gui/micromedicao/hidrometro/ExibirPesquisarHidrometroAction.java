package gcom.gui.micromedicao.hidrometro;

import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.micromedicao.hidrometro.FiltroHidrometroCapacidade;
import gcom.micromedicao.hidrometro.FiltroHidrometroClasseMetrologica;
import gcom.micromedicao.hidrometro.FiltroHidrometroDiametro;
import gcom.micromedicao.hidrometro.FiltroHidrometroLocalArmazenagem;
import gcom.micromedicao.hidrometro.FiltroHidrometroMarca;
import gcom.micromedicao.hidrometro.FiltroHidrometroSituacao;
import gcom.micromedicao.hidrometro.FiltroHidrometroTipo;
import gcom.micromedicao.hidrometro.Hidrometro;
import gcom.micromedicao.hidrometro.HidrometroCapacidade;
import gcom.micromedicao.hidrometro.HidrometroClasseMetrologica;
import gcom.micromedicao.hidrometro.HidrometroDiametro;
import gcom.micromedicao.hidrometro.HidrometroLocalArmazenagem;
import gcom.micromedicao.hidrometro.HidrometroMarca;
import gcom.micromedicao.hidrometro.HidrometroSituacao;
import gcom.micromedicao.hidrometro.HidrometroTipo;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExibirPesquisarHidrometroAction extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		// Obt�m o action form
		PesquisarHidrometroActionForm pesquisarHidrometroActionForm = (PesquisarHidrometroActionForm) actionForm;

		//obtendo uma instancia da sessao
        HttpSession sessao = httpServletRequest.getSession(false);
        
		// Seta o caminho de retorno
		ActionForward retorno = actionMapping
				.findForward("pesquisarHidrometro");

		

		if (httpServletRequest.getParameter("limparForm") != null){
			
			pesquisarHidrometroActionForm.setAnoFabricacao("");
			pesquisarHidrometroActionForm.setDataAquisicao("");
			pesquisarHidrometroActionForm.setDescricaoLocalArmazenamento("");
			pesquisarHidrometroActionForm.setFaixaFinal("");
			pesquisarHidrometroActionForm.setFaixaInicial("");
			pesquisarHidrometroActionForm.setFinalidade("" + ConstantesSistema.NUMERO_NAO_INFORMADO);
			pesquisarHidrometroActionForm.setFixo("");
			pesquisarHidrometroActionForm.setIdHidrometroCapacidade("" + ConstantesSistema.NUMERO_NAO_INFORMADO);
			pesquisarHidrometroActionForm.setIdHidrometroClasseMetrologica("" + ConstantesSistema.NUMERO_NAO_INFORMADO);
			pesquisarHidrometroActionForm.setIdHidrometroDiametro("" + ConstantesSistema.NUMERO_NAO_INFORMADO);
			pesquisarHidrometroActionForm.setIdHidrometroMarca("" + ConstantesSistema.NUMERO_NAO_INFORMADO);
			pesquisarHidrometroActionForm.setIdHidrometroSituacao("" + ConstantesSistema.NUMERO_NAO_INFORMADO);
			pesquisarHidrometroActionForm.setIdHidrometroTipo("" + ConstantesSistema.NUMERO_NAO_INFORMADO);
			pesquisarHidrometroActionForm.setIdLocalArmazenamento("");
			pesquisarHidrometroActionForm.setNumeroHidrometro("");
			sessao.removeAttribute("bloquearLocalArmazenagem");
			sessao.removeAttribute("caminhoRetornoTelaPesquisaHidrometro");
	
		}
		if (pesquisarHidrometroActionForm.getFinalidade() != null 
				 && pesquisarHidrometroActionForm.getFinalidade().equals("" + Hidrometro.INDICADOR_COMERCIAL)){
			 httpServletRequest.setAttribute("finalidade" ,Hidrometro.INDICADOR_COMERCIAL);
		}else if(pesquisarHidrometroActionForm.getFinalidade() != null 
				 && pesquisarHidrometroActionForm.getFinalidade().equals("" + Hidrometro.INDICADOR_OPERACIONAL)){
			 httpServletRequest.setAttribute("finalidade" ,Hidrometro.INDICADOR_OPERACIONAL);
		}
		 
		
		// Pega a instancia da fachada
		Fachada fachada = Fachada.getInstancia();

		// Busca no sistema as tabelas que v�o auxiliar a exibi��o da p�gina
		FiltroHidrometroClasseMetrologica filtroHidrometroClasseMetrologica = new FiltroHidrometroClasseMetrologica();
		FiltroHidrometroMarca filtroHidrometroMarca = new FiltroHidrometroMarca();
		FiltroHidrometroDiametro filtroHidrometroDiametro = new FiltroHidrometroDiametro();
		FiltroHidrometroCapacidade filtroHidrometroCapacidade = new FiltroHidrometroCapacidade();
		FiltroHidrometroTipo filtroHidrometroTipo = new FiltroHidrometroTipo();
		FiltroHidrometroSituacao filtroHidrometroSituacao = new FiltroHidrometroSituacao();

		// Seta o campo que vai ordenar a apresenta��o das cole��es na p�gina
		filtroHidrometroClasseMetrologica
				.setCampoOrderBy(FiltroHidrometroClasseMetrologica.DESCRICAO);
		filtroHidrometroMarca
				.setCampoOrderBy(FiltroHidrometroMarca.DESCRICAO);
		filtroHidrometroDiametro
				.setCampoOrderBy(FiltroHidrometroDiametro.DESCRICAO);
		filtroHidrometroCapacidade
				.setCampoOrderBy(FiltroHidrometroCapacidade.DESCRICAO);
		filtroHidrometroTipo
				.setCampoOrderBy(FiltroHidrometroTipo.DESCRICAO);
		filtroHidrometroSituacao
				.setCampoOrderBy(FiltroHidrometroSituacao.DESCRICAO);

		// Pesquisa das cole��es
		Collection<HidrometroClasseMetrologica> hidrometrosClasseMetrologica = fachada
				.pesquisar(filtroHidrometroClasseMetrologica,
						HidrometroClasseMetrologica.class.getName());

		Collection<HidrometroMarca> hidrometrosMarca = fachada.pesquisar(
				filtroHidrometroMarca, HidrometroMarca.class.getName());

		Collection<HidrometroDiametro> hidrometrosDiametro = fachada.pesquisar(
				filtroHidrometroDiametro, HidrometroDiametro.class.getName());

		Collection<HidrometroCapacidade> hidrometrosCapacidade = fachada
				.pesquisar(filtroHidrometroCapacidade,
						HidrometroCapacidade.class.getName());

		Collection<HidrometroTipo> hidrometrosTipo = fachada.pesquisar(
				filtroHidrometroTipo, HidrometroTipo.class.getName());

		Collection<HidrometroSituacao> hidrometrosSituacao = fachada.pesquisar(
				filtroHidrometroSituacao, HidrometroSituacao.class.getName());

		// Manda as cole��es pesquisadas pelo request
		httpServletRequest.setAttribute("hidrometrosClasseMetrologica",
				hidrometrosClasseMetrologica);
		httpServletRequest.setAttribute("hidrometrosMarca",
				hidrometrosMarca);
		httpServletRequest.setAttribute("hidrometrosDiametro",
				hidrometrosDiametro);
		httpServletRequest.setAttribute("hidrometrosCapacidade",
				hidrometrosCapacidade);
		httpServletRequest.setAttribute("hidrometrosTipo", 
				hidrometrosTipo);
		httpServletRequest.setAttribute("hidrometrosSituacao",
				hidrometrosSituacao);

		// verifica se o usu�rio solicitou uma consulta de Local de Armazenagem
		String idLocalArmazenamento = httpServletRequest
				.getParameter("id");
		String descricaoLocalArmazenamento = httpServletRequest
				.getParameter("descricao");

		// Flag indicando que o usu�rio fez uma consulta a partir da tecla Enter
		String consultarLocalArmazenamento = httpServletRequest
				.getParameter("consultarLocalArmazenamento");

		// Seta no form os valores da pesquisa feita no popup de local
		// armazenamento
		if (idLocalArmazenamento != null
				&& !idLocalArmazenamento.trim().equals("")
				&& descricaoLocalArmazenamento != null
				&& !descricaoLocalArmazenamento.trim().equals("")) {
			// Indica que o local de armazenagem foi encontrado
			httpServletRequest.setAttribute("localArmazenagemEncontrado",
					"true");

			pesquisarHidrometroActionForm
					.setIdLocalArmazenamento(idLocalArmazenamento.trim());
			pesquisarHidrometroActionForm
					.setDescricaoLocalArmazenamento(descricaoLocalArmazenamento
							.trim());
		}

		if (consultarLocalArmazenamento != null
				&& !consultarLocalArmazenamento.trim().equals("")) {

			// Faz a consulta de Local de Armazenagem
			pesquisarLocalArmazenamento(httpServletRequest, retorno,
					pesquisarHidrometroActionForm);

		}

		
        if (httpServletRequest.getParameter("tipoConsulta") != null
                && !httpServletRequest.getParameter("tipoConsulta").equals("")) {
            //se for os parametros de enviarDadosParametros ser�o mandados para
            // a pagina hidrometro_pesquisar.jsp
        	pesquisarHidrometroActionForm.setIdLocalArmazenamento(
                        httpServletRequest.getParameter("idCampoEnviarDados"));
        	pesquisarHidrometroActionForm.setDescricaoLocalArmazenamento(
        			    httpServletRequest.getParameter("descricaoCampoEnviarDados"));
        }
		
		//envia uma flag que ser� verificado no
        // hidrometro_resultado_pesquisa.jsp
        //para saber se ir� usar o enviar dados ou o enviar dados parametros
        if (httpServletRequest.getParameter("caminhoRetornoTelaPesquisaHidrometro") != null) {
        	  sessao.setAttribute("caminhoRetornoTelaPesquisaHidrometro",
        	          httpServletRequest
        	                  .getParameter("caminhoRetornoTelaPesquisaHidrometro"));
        }
        
        if (httpServletRequest.getParameter("idLocalArmazenagem") != null && 
        	!httpServletRequest.getParameter("idLocalArmazenagem").trim().equals("")) {
        	
			sessao.setAttribute("bloquearLocalArmazenagem", true);
			
			pesquisarHidrometroActionForm
					.setIdLocalArmazenamento(httpServletRequest.getParameter(
							"idLocalArmazenagem").trim());
			
			// Faz a consulta de Local de Armazenagem
			pesquisarLocalArmazenamento(httpServletRequest, retorno,
					pesquisarHidrometroActionForm);
			
			
		}
        
        if (sessao.getAttribute("semMenu") != null
                && sessao.getAttribute("semMenu").equals("SIM")) {
        	
        	sessao.setAttribute("enviarDadosComParametro", "SIM");
        }
        

		return retorno;
	}

	private void pesquisarLocalArmazenamento(
			HttpServletRequest httpServletRequest, ActionForward retorno,
			PesquisarHidrometroActionForm pesquisarHidrometroActionForm) {
		// Filtro para obter o local de armazenagem ativo de id informado
		FiltroHidrometroLocalArmazenagem filtroHidrometroLocalArmazenagem = new FiltroHidrometroLocalArmazenagem();

		filtroHidrometroLocalArmazenagem
				.adicionarParametro(new ParametroSimples(
						FiltroHidrometroLocalArmazenagem.ID, new Integer(
								pesquisarHidrometroActionForm
										.getIdLocalArmazenamento()),
						ParametroSimples.CONECTOR_AND));
		filtroHidrometroLocalArmazenagem
				.adicionarParametro(new ParametroSimples(
						FiltroHidrometroLocalArmazenagem.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		// Pesquisa de acordo com os par�metros informados no filtro
		Collection colecaoHidrometroLocalArmazenagem = Fachada.getInstancia()
				.pesquisar(filtroHidrometroLocalArmazenagem,
						HidrometroLocalArmazenagem.class.getName());

		// Verifica se a pesquisa retornou algum objeto para a cole��o
		if (colecaoHidrometroLocalArmazenagem != null
				&& !colecaoHidrometroLocalArmazenagem.isEmpty()) {

			// Obt�m o objeto da cole��o pesquisada
			HidrometroLocalArmazenagem hidrometroLocalArmazenagem = (HidrometroLocalArmazenagem) Util
					.retonarObjetoDeColecao(colecaoHidrometroLocalArmazenagem);

			// Exibe o c�digo e a descri��o pesquisa na p�gina
			httpServletRequest.setAttribute("localArmazenagemEncontrado",
					"true");
			pesquisarHidrometroActionForm
					.setIdLocalArmazenamento(hidrometroLocalArmazenagem.getId()
							.toString());
			pesquisarHidrometroActionForm
					.setDescricaoLocalArmazenamento(hidrometroLocalArmazenagem
							.getDescricao());

		} else {

			// Exibe mensagem de c�digo inexiste e limpa o campo de c�digo
			// httpServletRequest.setAttribute("corLocalArmazenagem",
			// "exception");
			pesquisarHidrometroActionForm.setIdLocalArmazenamento("");
			pesquisarHidrometroActionForm
					.setDescricaoLocalArmazenamento("Local de armazenagem inexistente");

		}

	}
}
