package gcom.gui.cobranca;

import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.FiltroSetorComercial;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cobranca.CobrancaAtividade;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.FiltroRota;
import gcom.micromedicao.Rota;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC0243] Inserir Comando de A��o de Conbran�a - Tipo de Comando Cronograma
 * 
 * @author Rafael Santos
 * @since 24/01/2006
 */
public class InserirComandoAcaoCobrancaEventualCriterioComandoConcluirAction extends
		GcomAction {

	/**
	 * 
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

        //Seta o mapeamento de retorno
        ActionForward retorno = actionMapping
                .findForward("telaSucesso");

		// Mudar isso quando implementar a parte de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();
		InserirComandoAcaoCobrancaEventualCriterioComandoActionForm inserirComandoAcaoCobrancaEventualCriterioComandoActionForm = null;
		                        
		if(sessao.getAttribute("inserirComandoAcaoCobrancaEventualCriterioComandoActionForm") != null){
			inserirComandoAcaoCobrancaEventualCriterioComandoActionForm = (InserirComandoAcaoCobrancaEventualCriterioComandoActionForm)sessao.getAttribute("inserirComandoAcaoCobrancaEventualCriterioComandoActionForm"); 
		}
		
		String idComando = httpServletRequest.getParameter("idComando");
		

		String idLocalidade = inserirComandoAcaoCobrancaEventualCriterioComandoActionForm.getLocalidadeOrigemID();
		String codigoSetorComercial = inserirComandoAcaoCobrancaEventualCriterioComandoActionForm.getSetorComercialOrigemCD();

		String idLocalidadeFinal = inserirComandoAcaoCobrancaEventualCriterioComandoActionForm.getLocalidadeDestinoID();
		String codigoSetorComercialFinal = inserirComandoAcaoCobrancaEventualCriterioComandoActionForm.getSetorComercialDestinoCD();

		
		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
        if (idLocalidade != null
                && !idLocalidade.toString().trim().equalsIgnoreCase("")) {
            filtroLocalidade.limparListaParametros();
            //coloca parametro no filtro
            filtroLocalidade.adicionarParametro(new ParametroSimples(
                    FiltroLocalidade.INDICADORUSO,
                    ConstantesSistema.INDICADOR_USO_ATIVO));
            filtroLocalidade.adicionarParametro(new ParametroSimples(
                    FiltroLocalidade.ID, new Integer(idLocalidade)));
            //pesquisa
            Collection localidades = fachada.pesquisar(filtroLocalidade, Localidade.class
                    .getName());
            if (localidades == null || localidades.isEmpty()) {
            	throw new ActionServletException("atencao.pesquisa.localidade_inicial_inexistente");
            }
        }

        FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
        if (codigoSetorComercial != null
                && !codigoSetorComercial.toString().trim().equalsIgnoreCase("")) {
            if (idLocalidade != null
                    && !idLocalidade.toString().trim().equalsIgnoreCase("")) {
                filtroSetorComercial.limparListaParametros();
                //coloca parametro no filtro
                filtroSetorComercial.adicionarParametro(new ParametroSimples(
                        FiltroSetorComercial.INDICADORUSO,
                        ConstantesSistema.INDICADOR_USO_ATIVO));
                filtroSetorComercial.adicionarParametro(new ParametroSimples(
                        FiltroSetorComercial.ID_LOCALIDADE, new Integer(
                                idLocalidade)));
                filtroSetorComercial.adicionarParametro(new ParametroSimples(
                        FiltroSetorComercial.CODIGO_SETOR_COMERCIAL,
                        new Integer(codigoSetorComercial)));
                //pesquisa
                Collection setorComerciais = fachada.pesquisar(filtroSetorComercial,
                        SetorComercial.class.getName());
                if (setorComerciais == null || setorComerciais.isEmpty()) {
                	 throw new ActionServletException("atencao.pesquisa.setor_inicial_inexistente");
                }
            }

        } 

        
		filtroLocalidade = new FiltroLocalidade();
        if (idLocalidade != null
                && !idLocalidade.toString().trim().equalsIgnoreCase("")) {
            filtroLocalidade.limparListaParametros();
            //coloca parametro no filtro
            filtroLocalidade.adicionarParametro(new ParametroSimples(
                    FiltroLocalidade.INDICADORUSO,
                    ConstantesSistema.INDICADOR_USO_ATIVO));
            filtroLocalidade.adicionarParametro(new ParametroSimples(
                    FiltroLocalidade.ID, new Integer(idLocalidadeFinal)));
            //pesquisa
            Collection localidades = fachada.pesquisar(filtroLocalidade, Localidade.class
                    .getName());
            if (localidades == null || localidades.isEmpty()) {
            	throw new ActionServletException("atencao.pesquisa.localidade_final_inexistente");
            }
        }

        filtroSetorComercial = new FiltroSetorComercial();
        if (codigoSetorComercial != null
                && !codigoSetorComercial.toString().trim().equalsIgnoreCase("")) {
            if (idLocalidade != null
                    && !idLocalidade.toString().trim().equalsIgnoreCase("")) {
                filtroSetorComercial.limparListaParametros();
                //coloca parametro no filtro
                filtroSetorComercial.adicionarParametro(new ParametroSimples(
                        FiltroSetorComercial.INDICADORUSO,
                        ConstantesSistema.INDICADOR_USO_ATIVO));
                filtroSetorComercial.adicionarParametro(new ParametroSimples(
                        FiltroSetorComercial.ID_LOCALIDADE, new Integer(
                                idLocalidadeFinal)));
                filtroSetorComercial.adicionarParametro(new ParametroSimples(
                        FiltroSetorComercial.CODIGO_SETOR_COMERCIAL,
                        new Integer(codigoSetorComercialFinal)));
                //pesquisa
                Collection setorComerciais = fachada.pesquisar(filtroSetorComercial,
                        SetorComercial.class.getName());
                if (setorComerciais == null || setorComerciais.isEmpty()) {
                	 throw new ActionServletException("atencao.pesquisa.setor_final_inexistente");
                }
            }
        } 
		
        String codigoRotaInicial = inserirComandoAcaoCobrancaEventualCriterioComandoActionForm.getRotaInicial();
		String idRotaInicial = null;
        if((idLocalidade != null
        		&& !idLocalidade.equals(""))
        		&&	
        		(codigoSetorComercial != null
                		&& !codigoSetorComercial.equals(""))
                &&		
                		(codigoRotaInicial != null
                        		&& !codigoRotaInicial.equals(""))){
    		FiltroRota filtroRota = new FiltroRota();
    		filtroRota.adicionarParametro(new ParametroSimples(
    				FiltroRota.CODIGO_ROTA, codigoRotaInicial));
            filtroRota.adicionarParametro(new ParametroSimples(
                    FiltroRota.LOCALIDADE_ID, idLocalidade));
            filtroRota.adicionarParametro(new ParametroSimples(
                    FiltroRota.SETOR_COMERCIAL_CODIGO, codigoSetorComercial));		
    		
    		Collection rotas = fachada.pesquisar(filtroRota,Rota.class.getName());
    		if(rotas != null && !rotas.isEmpty()){
    			idRotaInicial = ((Rota)rotas.iterator().next()).getId().toString();
    		}else{
    			throw new ActionServletException(
    				"atencao.pesquisa.rota_inicial_inexistente");			
    		}
        }

        
        String codigoRotaFinal = inserirComandoAcaoCobrancaEventualCriterioComandoActionForm.getRotaFinal();
		String idRotaFinal = null;
        
        if((idLocalidadeFinal != null
        		&& !idLocalidadeFinal.equals(""))
        		&&	
        		(codigoSetorComercialFinal != null
                		&& !codigoSetorComercialFinal.equals(""))
                &&		
                		(codigoRotaFinal != null
                        		&& !codigoRotaFinal.equals(""))){
        	FiltroRota filtroRota = new FiltroRota();
    		filtroRota.limparListaParametros();
            filtroRota.adicionarParametro(new ParametroSimples(
                    FiltroRota.LOCALIDADE_ID, idLocalidadeFinal));
            filtroRota.adicionarParametro(new ParametroSimples(
                    FiltroRota.SETOR_COMERCIAL_CODIGO, codigoSetorComercialFinal));
    		filtroRota.adicionarParametro(new ParametroSimples(
    				FiltroRota.CODIGO_ROTA, codigoRotaFinal));
    		Collection rotas = null;
    		rotas = fachada.pesquisar(filtroRota,Rota.class.getName());
    		if(rotas != null && !rotas.isEmpty()){
    			idRotaFinal = ((Rota)rotas.iterator().next()).getId().toString();
    		}else{
    			throw new ActionServletException(
    				"atencao.pesquisa.rota_final_inexistente");			
    		}
        	
        }
        
		
		
        Collection colecaoCobrancaAcaoAtividadeComando = fachada.concluirComandoAcaoCobranca(
        		inserirComandoAcaoCobrancaEventualCriterioComandoActionForm.getPeriodoInicialConta(),
        		inserirComandoAcaoCobrancaEventualCriterioComandoActionForm.getPeriodoFinalConta(),
				inserirComandoAcaoCobrancaEventualCriterioComandoActionForm.getPeriodoVencimentoContaInicial(),
				inserirComandoAcaoCobrancaEventualCriterioComandoActionForm.getPeriodoVencimentoContaFinal(),
				inserirComandoAcaoCobrancaEventualCriterioComandoActionForm.getCobrancaAcao(),
				inserirComandoAcaoCobrancaEventualCriterioComandoActionForm.getCobrancaAtividade(),
				inserirComandoAcaoCobrancaEventualCriterioComandoActionForm.getCobrancaGrupo(),
				inserirComandoAcaoCobrancaEventualCriterioComandoActionForm.getGerenciaRegional(),
				inserirComandoAcaoCobrancaEventualCriterioComandoActionForm.getLocalidadeOrigemID(),
				inserirComandoAcaoCobrancaEventualCriterioComandoActionForm.getLocalidadeDestinoID(),
				inserirComandoAcaoCobrancaEventualCriterioComandoActionForm.getSetorComercialOrigemCD(),
				inserirComandoAcaoCobrancaEventualCriterioComandoActionForm.getSetorComercialDestinoCD(),
				inserirComandoAcaoCobrancaEventualCriterioComandoActionForm.getIdCliente(),
				inserirComandoAcaoCobrancaEventualCriterioComandoActionForm.getClienteRelacaoTipo(),
				inserirComandoAcaoCobrancaEventualCriterioComandoActionForm.getIndicador(),idRotaInicial,
				idRotaFinal,
				inserirComandoAcaoCobrancaEventualCriterioComandoActionForm.getSetorComercialOrigemID(),
				inserirComandoAcaoCobrancaEventualCriterioComandoActionForm.getSetorComercialDestinoID(),idComando,
				inserirComandoAcaoCobrancaEventualCriterioComandoActionForm.getUnidadeNegocio(),
				this.getUsuarioLogado(httpServletRequest),
				inserirComandoAcaoCobrancaEventualCriterioComandoActionForm.getTitulo(),
				inserirComandoAcaoCobrancaEventualCriterioComandoActionForm.getDescricaoSolicitacao(),
				inserirComandoAcaoCobrancaEventualCriterioComandoActionForm.getPrazoExecucao(),
				inserirComandoAcaoCobrancaEventualCriterioComandoActionForm.getQuantidadeMaximaDocumentos(),
				inserirComandoAcaoCobrancaEventualCriterioComandoActionForm.getValorLimiteObrigatoria(),
				inserirComandoAcaoCobrancaEventualCriterioComandoActionForm.getIndicadorImoveisDebito(),
				inserirComandoAcaoCobrancaEventualCriterioComandoActionForm.getIndicadorGerarBoletimCadastro(),
				inserirComandoAcaoCobrancaEventualCriterioComandoActionForm.getCodigoClienteSuperior(), codigoRotaInicial,
				codigoRotaFinal,
				inserirComandoAcaoCobrancaEventualCriterioComandoActionForm.getLogradouroId(),
				inserirComandoAcaoCobrancaEventualCriterioComandoActionForm.getConsumoMedioInicial(),
				inserirComandoAcaoCobrancaEventualCriterioComandoActionForm.getConsumoMedioFinal(),
				inserirComandoAcaoCobrancaEventualCriterioComandoActionForm.getTipoConsumo(),
				inserirComandoAcaoCobrancaEventualCriterioComandoActionForm.getPeriodoInicialFiscalizacao(),
				inserirComandoAcaoCobrancaEventualCriterioComandoActionForm.getPeriodoFinalFiscalizacao(),
				inserirComandoAcaoCobrancaEventualCriterioComandoActionForm.getSituacaoFiscalizacao(),
				inserirComandoAcaoCobrancaEventualCriterioComandoActionForm.getNumeroQuadraInicial(),
				inserirComandoAcaoCobrancaEventualCriterioComandoActionForm.getNumeroQuadraFinal());
		
		//pesquisar cobranca acao
		//CobrancaAcao cobrancaAcao =  fachada.consultarCobrancaAcao(inserirComandoAcaoCobrancaEventualCriterioComandoActionForm.getCobrancaAcao());
		
		//pesquisar cobranca atividade
		CobrancaAtividade cobrancaAtividade = fachada.consultarCobrancaAtividade(inserirComandoAcaoCobrancaEventualCriterioComandoActionForm.getCobrancaAtividade());
				
		montarPaginaSucesso(httpServletRequest,
		           " "+colecaoCobrancaAcaoAtividadeComando.size()+" A��o(�es) de cobran�a para a atividade " 
		           + cobrancaAtividade.getDescricaoCobrancaAtividade() + " comandada(s) com sucesso.",
		           "Inserir outra Comando de A��o de Cobran�a",
		           "exibirInserirComandoAcaoCobrancaAction.do?limparForm=OK&menu=sim");

		return retorno;
	}
		
}
