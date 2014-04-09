package gcom.gui.arrecadacao;

import gcom.arrecadacao.ArrecadacaoForma;
import gcom.arrecadacao.Arrecadador;
import gcom.arrecadacao.FiltroArrecadacaoForma;
import gcom.arrecadacao.FiltroArrecadador;
import gcom.arrecadacao.FiltroConsultarDadosDiariosArrecadacao;
import gcom.arrecadacao.FiltroConsultarDadosDiariosArrecadacao.GROUP_BY;
import gcom.arrecadacao.FiltroConsultarDadosDiariosArrecadacaoAuxiliar;
import gcom.arrecadacao.FiltroConsultarDadosDiariosArrecadacaoAuxiliar.GROUP_BY_AUX;
import gcom.arrecadacao.bean.FiltrarDadosDiariosArrecadacaoHelper;
import gcom.batch.FiltroFuncionalidadeIniciada;
import gcom.batch.FuncionalidadeIniciada;
import gcom.batch.FuncionalidadeSituacao;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.FiltroCategoria;
import gcom.cadastro.imovel.FiltroImovelPerfil;
import gcom.cadastro.imovel.ImovelPerfil;
import gcom.cadastro.localidade.FiltroGerenciaRegional;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.FiltroUnidadeNegocio;
import gcom.cadastro.localidade.GerenciaRegional;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.UnidadeNegocio;
import gcom.cobranca.DocumentoTipo;
import gcom.cobranca.FiltroDocumentoTipo;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.Funcionalidade;
import gcom.util.Util;
import gcom.util.filtro.ConectorOr;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Description of the Class
 * 
 * @author Fernanda Paiva
 * @created 25 de Maio de 2006
**/
public class ExibirConsultarDadosDiariosValoresDiariosComTarifaAction extends GcomAction {
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
				.findForward("exibirConsultarDadosDiariosValoresDiariosComTarifa");
		
        /** filtro para verificar se a funcionalidade de gerar dados di�rios de arrecada��o esta executando */
        FiltroFuncionalidadeIniciada filtroFuncionalidadeIniciada = new FiltroFuncionalidadeIniciada();
        filtroFuncionalidadeIniciada.adicionarParametro(new ParametroSimples(FiltroFuncionalidadeIniciada.FUNCIONALIDADE_ID,Funcionalidade.GERAR_DADOS_DIARIOS_ARRECADACAO));
        filtroFuncionalidadeIniciada.adicionarParametro(new ParametroSimples(FiltroFuncionalidadeIniciada.FUNCIONALIDADE_SITUACAO,FuncionalidadeSituacao.EM_ESPERA, ConectorOr.CONECTOR_OR, 2));
        filtroFuncionalidadeIniciada.adicionarParametro(new ParametroSimples(FiltroFuncionalidadeIniciada.FUNCIONALIDADE_SITUACAO,FuncionalidadeSituacao.EM_PROCESSAMENTO));
        
        Collection colecaoFuncionalidadeEmProcessamento = Fachada.getInstancia().pesquisar(filtroFuncionalidadeIniciada,FuncionalidadeIniciada.class.getName());
        
        /*
         * Caso a funcionalidade esteja emprocessamento ou em espera
         * envia uma mensagem ao usu�rio negando o acesso a consulta.  
         */
        if(colecaoFuncionalidadeEmProcessamento != null && !colecaoFuncionalidadeEmProcessamento.isEmpty()){
        	throw new ActionServletException("atencao.funcionalidade.processando");
        }
		
		String referencia = httpServletRequest.getParameter("referencia");
		
		String idGerencia = httpServletRequest.getParameter("idGerencia");
		
		String idUnidadeNegocio = httpServletRequest.getParameter("idUnidadeNegocio");
		
		String idArrecadadorPopup = httpServletRequest.getParameter("idArrecadadorPopup");
		httpServletRequest.setAttribute("idArrecadadorPopup", idArrecadadorPopup);

		String idEloPopup = httpServletRequest.getParameter("idEloPopup");
		
		String idLocalidade = httpServletRequest.getParameter("idLocalidade");
		
		String idDocumentoTipoPopup = httpServletRequest.getParameter("idDocumentoTipoPopup");
		
		String idDocumentoTipoAgregador = httpServletRequest.getParameter("idDocumentoTipoAgregador");
		
		String idArrecadacaoForma = httpServletRequest.getParameter("idArrecadacaoForma");
		httpServletRequest.setAttribute("idArrecadacaoForma",idArrecadacaoForma);
		        
		String mostraUnidadeGerencia = httpServletRequest.getParameter("mostraUnidadeGerencia");
		httpServletRequest.setAttribute("mostraUnidadeGerencia",mostraUnidadeGerencia);
		
		httpServletRequest.setAttribute("nomeAgente", httpServletRequest.getParameter("nomeAgente"));
			
		Fachada fachada = Fachada.getInstancia();
		
		HttpSession sessao = httpServletRequest.getSession(false);
		
		FiltroConsultarDadosDiariosArrecadacaoAuxiliar filtro = (FiltroConsultarDadosDiariosArrecadacaoAuxiliar)
			sessao.getAttribute("filtroConsultarDadosDiariosArrecadacaoAuxiliar");
		Integer periodoArrecadacaoInicial = (Integer) 
			sessao.getAttribute("periodoArrecadacaoInicial");
		Integer periodoArrecadacaoFinal = (Integer) 
			sessao.getAttribute("periodoArrecadacaoFinal");
		
		if (filtro != null){
			
			filtro = filtro.clone();
			
			filtro.setAgrupamento(GROUP_BY_AUX.DATA);
			
			filtro.setAnoMesArrecadacao(referencia);
			if (idGerencia != null && !idGerencia.equals("") && !idGerencia.equals("-1")){
				filtro.setIdGerenciaRegional(idGerencia);
				
				// pesquisar na base a gerencia Regional
				FiltroGerenciaRegional filtroGerenciaRegional = new FiltroGerenciaRegional ();
				filtroGerenciaRegional.adicionarParametro(new ParametroSimples(FiltroGerenciaRegional.ID,
						idGerencia));
				
				Collection colecaoGerenciaRegional = fachada.pesquisar(filtroGerenciaRegional,
						GerenciaRegional.class.getName());
				
				GerenciaRegional gerenciaRegional = (GerenciaRegional) 
					Util.retonarObjetoDeColecao(colecaoGerenciaRegional); 
				httpServletRequest.setAttribute("nomeGerencia",gerenciaRegional.getNome());
				
			}
			if (idUnidadeNegocio != null && !idUnidadeNegocio.equals("") && 
				!idUnidadeNegocio.equals("-1")){
				filtro.setIdUnidadeNegocio(idUnidadeNegocio);	
				
				//pesquisar na base a gerencia
				FiltroUnidadeNegocio filtroUnidadeNegocio = new FiltroUnidadeNegocio();
				
				filtroUnidadeNegocio.adicionarParametro(new ParametroSimples(FiltroUnidadeNegocio.ID,
						idUnidadeNegocio));
				
				Collection colecaoUnidadeNegocio = fachada.pesquisar(filtroUnidadeNegocio,
						UnidadeNegocio.class.getName());
				
				UnidadeNegocio unidadeNegocio = (UnidadeNegocio) 
					Util.retonarObjetoDeColecao(colecaoUnidadeNegocio);
				httpServletRequest.setAttribute("nomeUnidadeNegocio",unidadeNegocio.getNome());					
								
			}
			if (idArrecadadorPopup != null && !idArrecadadorPopup.equals("") 
				&& !idArrecadadorPopup.equals("-1")){
				filtro.setIdArrecadador(idArrecadadorPopup);
				
				//pesquisar na base o arrecadador
				FiltroArrecadador filtroArrecadador = new FiltroArrecadador();
				filtroArrecadador.adicionarParametro(new ParametroSimples(FiltroArrecadador.ID,
						idArrecadadorPopup));
				filtroArrecadador.adicionarCaminhoParaCarregamentoEntidade("cliente");
				
				Collection colecaoArrecadador = fachada.pesquisar(filtroArrecadador,
						Arrecadador.class.getName());
				
				Arrecadador arrecadador = (Arrecadador)Util.retonarObjetoDeColecao(colecaoArrecadador);
				httpServletRequest.setAttribute("nomeAgente", arrecadador.getCliente().getNome());
				
			}
			if (idEloPopup != null && !idEloPopup.equals("") && !idEloPopup.equals("-1")){
				filtro.setIdElo(idEloPopup);
				
				// pesquisar na base a localidade
				FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
				filtroLocalidade.adicionarParametro(new ParametroSimples("localidade.id",
					idEloPopup));
				filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("localidade");
				
				Collection colecaoLocalidade = fachada.pesquisar(filtroLocalidade,
						Localidade.class.getName());
				
				Localidade localidade = (Localidade)Util.retonarObjetoDeColecao(colecaoLocalidade);
				httpServletRequest.setAttribute("descricaoElo", localidade.getLocalidade().getDescricao());
				
			}
			if (idLocalidade != null && !idLocalidade.equals("") && !idLocalidade.equals("-1")){
				filtro.setIdLocalidade(idLocalidade);
				
				// pesquisar na base a localidade
				FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID,
					idLocalidade));
				
				Collection colecaoLocalidade = fachada.pesquisar(filtroLocalidade,
						Localidade.class.getName());
				
				Localidade localidade = (Localidade)Util.retonarObjetoDeColecao(colecaoLocalidade);
				httpServletRequest.setAttribute("descricaoLocalidade", localidade.getDescricao());
				
			}
			if (idDocumentoTipoAgregador != null && !idDocumentoTipoAgregador.equals("") &&
				!idDocumentoTipoAgregador.equals("-1")){
				String[] idsDocumentoTipoAgregador = {idDocumentoTipoAgregador};
				filtro.setIdsDocumentoTipoAgregador(idsDocumentoTipoAgregador);
				
				//pesquisar na base o Documento Tipo
				FiltroDocumentoTipo filtroDocumentoTipo = new FiltroDocumentoTipo();
				filtroDocumentoTipo.adicionarParametro(new ParametroSimples(FiltroDocumentoTipo.ID,
						idDocumentoTipoAgregador));
				
				Collection colecaoDocumentoTipo = fachada.pesquisar(filtroDocumentoTipo,
						DocumentoTipo.class.getName());
				
				DocumentoTipo documentoTipo = (DocumentoTipo) Util.retonarObjetoDeColecao(colecaoDocumentoTipo);
				
				if (documentoTipo == null){
					documentoTipo = new DocumentoTipo();
					documentoTipo.setDescricaoDocumentoTipo("Sem tipo de documento");
					documentoTipo.setId(0);							
				}
			
				httpServletRequest.setAttribute("nomeDocumento", documentoTipo.getDescricaoDocumentoTipo());

			}
			if (idDocumentoTipoPopup != null && !idDocumentoTipoPopup.equals("") &&
				!idDocumentoTipoPopup.equals("-1")){
				filtro.setIdDocumentoTipo(idDocumentoTipoPopup);
				
				//pesquisar na base o Documento Tipo
				FiltroDocumentoTipo filtroDocumentoTipo = new FiltroDocumentoTipo();
				filtroDocumentoTipo.adicionarParametro(new ParametroSimples(FiltroDocumentoTipo.ID,
						idDocumentoTipoPopup));
				
				Collection colecaoDocumentoTipo = fachada.pesquisar(filtroDocumentoTipo,
						DocumentoTipo.class.getName());
				
				DocumentoTipo documentoTipo = (DocumentoTipo) Util.retonarObjetoDeColecao(colecaoDocumentoTipo);
				
				if (documentoTipo == null){
					documentoTipo = new DocumentoTipo();
					documentoTipo.setDescricaoDocumentoTipo("Sem tipo de documento");
					documentoTipo.setId(0);							
				}
			
				httpServletRequest.setAttribute("nomeDocumento", documentoTipo.getDescricaoDocumentoTipo());
				
			}
			if (idArrecadacaoForma != null && !idArrecadacaoForma.equals("") 
					&& !idArrecadacaoForma.equals("-1")){
				filtro.setIdFormaArrecadacao(idArrecadacaoForma);
				
				//pesquisar na base o arrecadacao forma
				FiltroArrecadacaoForma filtroArrecadacaoForma = new FiltroArrecadacaoForma();
				filtroArrecadacaoForma.adicionarParametro(new ParametroSimples(FiltroArrecadacaoForma.CODIGO,
						idArrecadacaoForma));
				
				Collection colecaoArrecadacaoForma = fachada.pesquisar(filtroArrecadacaoForma,
						ArrecadacaoForma.class.getName());
				
				ArrecadacaoForma arrecadacaoForma = (ArrecadacaoForma) Util.retonarObjetoDeColecao(colecaoArrecadacaoForma);
				
				if(arrecadacaoForma == null){
					arrecadacaoForma = new ArrecadacaoForma();
					arrecadacaoForma.setId(0);
					arrecadacaoForma.setDescricao("Sem forma de arrecada��o");
				}
				
				httpServletRequest.setAttribute("nomeArrecadacaoForma", arrecadacaoForma.getDescricao());
			}
				
			
			Map<Integer, Collection<FiltrarDadosDiariosArrecadacaoHelper>> 
			 mapDadosDiariosAnoMes = fachada.filtrarDadosDiariosArrecadacaoAuxiliar(
				periodoArrecadacaoInicial,
				periodoArrecadacaoFinal,
				filtro);
						
        	BigDecimal valorTotal = new BigDecimal(0.0);
        	
        	Collection<FiltrarDadosDiariosArrecadacaoHelper> colecaoDadosDiarios = 
        		mapDadosDiariosAnoMes.get(new Integer(referencia));
        	
        	for (FiltrarDadosDiariosArrecadacaoHelper helper : colecaoDadosDiarios){
        		valorTotal = valorTotal.add(helper.getValorArrecadacaoLiquida());
        	}
        	
	        sessao.setAttribute("colecaoDadosDiarios", colecaoDadosDiarios);
	        
			sessao.setAttribute("valorTotal", valorTotal);
			
			Date dataMesInformado = fachada.pesquisarDataProcessamentoMes(new Integer(referencia));
	    	Date dataAtual = fachada.pesquisarDataProcessamentoMes(this.getSistemaParametro().getAnoMesArrecadacao());

	    		
	    	if(dataMesInformado!=null){ 			
	    		httpServletRequest
					.setAttribute("dadosMesInformado", 
						Util.formatarDataComHora(dataMesInformado));
	    	}
	    	if(dataAtual!=null){   			
	    		httpServletRequest
					.setAttribute("dadosAtual", 
						Util.formatarDataComHora(dataAtual));
	    	}
						
		}
		
		sessao.setAttribute("referencia",referencia);
		return retorno;
	}
	
}
