package gcom.gui.faturamento;

import gcom.cadastro.localidade.FiltroGerenciaRegional;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.FiltroSetorComercial;
import gcom.cadastro.localidade.GerenciaRegional;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.SetorComercial;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.FiltroRota;
import gcom.micromedicao.Rota;
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
import org.apache.struts.validator.DynaValidatorForm;

public class ExibirSelecionarRotaAction extends GcomAction {

    private String localidadeID;
    
    private Collection colecaoPesquisa;

    public ActionForward execute(ActionMapping actionMapping,
            ActionForm actionForm, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {

        //Seta o retorno
        ActionForward retorno = actionMapping
                .findForward("exibirSelecionarRota");

        //Obt�m a inst�ncia da fachada
        Fachada fachada = Fachada.getInstancia();

        //Obt�m a sess�o
        HttpSession sessao = httpServletRequest.getSession(false);

        //Formul�rio de pesquisa
        DynaValidatorForm pesquisarActionForm = (DynaValidatorForm) actionForm;
        
    	Integer grupoSelecionado = (Integer) sessao.getAttribute("faturamentoGrupoId");

    	//Limpar formul�rio, caso necess�rio
        if (httpServletRequest.getParameter("limparForm") != null){
        	pesquisarActionForm.set("idGrupoFaturamento", String
                    .valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO));
        	pesquisarActionForm.set("idGerenciaRegional", String
                    .valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO));
        	pesquisarActionForm.set("idLocalidade", "");
        	pesquisarActionForm.set("descricaoLocalidade", "");
        	pesquisarActionForm.set("numeroSetorComercial", "");
        	pesquisarActionForm.set("descricaoSetorComercial", "");
        	pesquisarActionForm.set("idRota", "");
        	pesquisarActionForm.set("descricaoRota", "");
        	
        	//Retira da sess�o a cole��o de rotas que foi selecionada anteriormente
        	sessao.removeAttribute("colecaoRotasSelecionadas");
        }

        
        //Grupo de faturamento (Carregar cole��o)
        sessao.removeAttribute("colecaoGrupoFaturamento");
        
        Collection colecaoFaturamentoGrupo = fachada.
        	pesquisarFaturamentoGrupoComCronogramaMensalParaMesCorrenteSemGupoSelecionado(
        			grupoSelecionado);

        sessao.setAttribute("colecaoGrupoFaturamento", colecaoFaturamentoGrupo);

        /*if (sessao.getAttribute("colecaoGrupoFaturamento") == null) {
        	
        	Collection colecaoFaturamentoGrupo;

            FiltroFaturamentoGrupo filtroFaturamentoGrupo = new FiltroFaturamentoGrupo(
                    FiltroFaturamentoGrupo.DESCRICAO);

            filtroFaturamentoGrupo.adicionarParametro(new ParametroSimples(
                    FiltroFaturamentoGrupo.INDICADOR_USO,
                    ConstantesSistema.INDICADOR_USO_ATIVO));
            
            filtroFaturamentoGrupo.adicionarParametro(new ParametroSimplesDiferenteDe(
                    FiltroFaturamentoGrupo.ID,grupo));
            
            colecaoFaturamentoGrupo = fachada.pesquisar(filtroFaturamentoGrupo,
                    FaturamentoGrupo.class.getName());

            if (colecaoFaturamentoGrupo == null
                    || colecaoFaturamentoGrupo.isEmpty()) {
                throw new ActionServletException(
                        "atencao.pesquisa.nenhum_registro_tabela", null,
                        "FATURAMENTO_GRUPO");
            } else {
                sessao.setAttribute("colecaoGrupoFaturamento",
                        colecaoFaturamentoGrupo);
            }
        }*/

        //Gerencia Regional (Carregar cole��o)
        if (sessao.getAttribute("colecaoGerenciaRegional") == null) {

            Collection colecaoGerenciaRegional;

            FiltroGerenciaRegional filtroGerenciaRegional = new FiltroGerenciaRegional(
                    FiltroGerenciaRegional.NOME_ABREVIADO);

            filtroGerenciaRegional.adicionarParametro(new ParametroSimples(
                    FiltroGerenciaRegional.INDICADOR_USO,
                    ConstantesSistema.INDICADOR_USO_ATIVO));

            colecaoGerenciaRegional = fachada.pesquisar(filtroGerenciaRegional,
                    GerenciaRegional.class.getName());

            if (colecaoGerenciaRegional == null
                    || colecaoGerenciaRegional.isEmpty()) {
                throw new ActionServletException(
                        "atencao.pesquisa.nenhum_registro_tabela", null,
                        "GERENCIA_REGIONAL");
            } else {
                sessao.setAttribute("colecaoGerenciaRegional",
                        colecaoGerenciaRegional);
            }
        }

        String objetoConsulta = (String) httpServletRequest
                .getParameter("objetoConsulta");

        if (objetoConsulta != null
                && !objetoConsulta.trim().equalsIgnoreCase("")) {

            //Recebe o valor do campo idLocalidade do formul�rio.
            if (pesquisarActionForm.get("idLocalidade") != null) {
                localidadeID = (String) pesquisarActionForm.get("idLocalidade");
            }
            
            
            switch (Integer.parseInt(objetoConsulta)) {
            //Localidade
            case 1:

                pesquisarLocalidade(pesquisarActionForm, fachada,
                        httpServletRequest);

                break;
            //Setor Comercial
            case 2:

                pesquisarLocalidade(pesquisarActionForm, fachada,
                        httpServletRequest);

                pesquisarSetorComercial(pesquisarActionForm, fachada,
                        httpServletRequest);

                break;
            //Rota
            case 3:
            	
            	pesquisarLocalidade(pesquisarActionForm, fachada,
                        httpServletRequest);

                SetorComercial setorComercial  = pesquisarSetorComercial(pesquisarActionForm, 
                fachada, httpServletRequest);
                
                if (setorComercial != null){
                	
                	pesquisarRota(pesquisarActionForm, fachada, httpServletRequest, setorComercial);
                	
                }
                else{
                	
                	pesquisarActionForm.set("idRota", "");
                    pesquisarActionForm.set("descricaoRota", "Informe Setor Comercial");
                    httpServletRequest.setAttribute("corRotaMensagem", "exception");
                    
                    httpServletRequest.setAttribute("nomeCampo", "numeroSetorComercial");
                }

                

                break;

            default:

                break;
            }
        }
        
        
        //Tratamento dos registros retornados por um popup
        
        //Tipo de consulta retonada pelo popup
        if (httpServletRequest.getParameter("tipoConsulta") != null){
        	
        	String tipoConsulta = (String) httpServletRequest.getParameter("tipoConsulta");
            
            //Define onde ser� exibido os dados retornados pela consulta realizada pelo popup
            if (tipoConsulta.equalsIgnoreCase("localidade")){
            	pesquisarActionForm.set("idLocalidade", String.valueOf(httpServletRequest
                .getParameter("idCampoEnviarDados")));
            	pesquisarActionForm.set("descricaoLocalidade", String.valueOf(httpServletRequest
                .getParameter("descricaoCampoEnviarDados")));
            }
            else{
            	pesquisarActionForm.set("numeroSetorComercial", String.valueOf(httpServletRequest
                .getParameter("idCampoEnviarDados")));
                pesquisarActionForm.set("descricaoSetorComercial", String.valueOf(httpServletRequest
                .getParameter("descricaoCampoEnviarDados")));
            }
            
        }
        

        //devolve o mapeamento de retorno
        return retorno;
    }

    /**
     * 
     * @param pesquisarActionForm
     * @param fachada
     * @param httpServletRequest
     */
    private void pesquisarLocalidade(DynaValidatorForm pesquisarActionForm,
            Fachada fachada, HttpServletRequest httpServletRequest) {

        FiltroLocalidade filtroLocalidade = new FiltroLocalidade();

        filtroLocalidade.adicionarParametro(new ParametroSimples(
                FiltroLocalidade.ID, localidadeID));

        filtroLocalidade.adicionarParametro(new ParametroSimples(
                FiltroLocalidade.INDICADORUSO,
                ConstantesSistema.INDICADOR_USO_ATIVO));

        //Retorna localidade
        colecaoPesquisa = fachada.pesquisar(filtroLocalidade, Localidade.class
                .getName());

        if (colecaoPesquisa == null || colecaoPesquisa.isEmpty()) {
            //Localidade nao encontrada
            //Limpa os campos localidadeID e localidadeNome do formul�rio
            httpServletRequest.setAttribute("corLocalidade", "exception");
            pesquisarActionForm.set("idLocalidade", "");
            pesquisarActionForm.set("descricaoLocalidade",
                    "Localidade inexistente");
            
            httpServletRequest.setAttribute("nomeCampo", "idLocalidade");
            
        } else {
            Localidade objetoLocalidade = (Localidade) Util
                    .retonarObjetoDeColecao(colecaoPesquisa);
            pesquisarActionForm.set("idLocalidade", String
                    .valueOf(objetoLocalidade.getId()));
            pesquisarActionForm.set("descricaoLocalidade", objetoLocalidade
                    .getDescricao());
            httpServletRequest.setAttribute("corLocalidade", "valor");
        
            httpServletRequest.setAttribute("nomeCampo", "numeroSetorComercial");
        }
    }

    /**
     * 
     * @param pesquisarActionForm
     * @param fachada
     * @param httpServletRequest
     */
    private SetorComercial pesquisarSetorComercial(DynaValidatorForm pesquisarActionForm,
            Fachada fachada, HttpServletRequest httpServletRequest) {
    	
    	SetorComercial objetoSetorComercial = null;

        if (localidadeID == null || localidadeID.trim().equalsIgnoreCase("")) {
            //Limpa os campos numeroSetorComercial e idSetorComercial do
            // formulario
            pesquisarActionForm.set("numeroSetorComercial", "");
            pesquisarActionForm.set("idSetorComercial", "");
            pesquisarActionForm.set("descricaoSetorComercial",
                    "Informe a localidade.");
            httpServletRequest.setAttribute("corSetorComercial", "exception");
            
            httpServletRequest.setAttribute("nomeCampo", "idLocalidade");
            
        } else {
            //Recebe o valor do campo numeroSetorComercial do formul�rio.
            String setorComercialCD = (String) pesquisarActionForm
                    .get("numeroSetorComercial");

            FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();

            filtroSetorComercial.adicionarParametro(new ParametroSimples(
                    FiltroSetorComercial.ID_LOCALIDADE, localidadeID));

            filtroSetorComercial.adicionarParametro(new ParametroSimples(
                    FiltroSetorComercial.CODIGO_SETOR_COMERCIAL,
                    setorComercialCD));

            filtroSetorComercial.adicionarParametro(new ParametroSimples(
                    FiltroSetorComercial.INDICADORUSO,
                    ConstantesSistema.INDICADOR_USO_ATIVO));

            //Retorna setorComercial
            colecaoPesquisa = fachada.pesquisar(filtroSetorComercial,
                    SetorComercial.class.getName());

            if (colecaoPesquisa == null || colecaoPesquisa.isEmpty()) {
                //setorComercial nao encontrado
                //Limpa os campos numeroSetorComercial e idSetorComercial do
                // formulario
                pesquisarActionForm.set("numeroSetorComercial", "");
                pesquisarActionForm.set("idSetorComercial", "");
                pesquisarActionForm.set("descricaoSetorComercial",
                        "Setor comercial inexistente.");
                httpServletRequest.setAttribute("corSetorComercial",
                        "exception");
                
                httpServletRequest.setAttribute("nomeCampo", "numeroSetorComercial");
                
            } else {
                objetoSetorComercial = (SetorComercial) Util
                        .retonarObjetoDeColecao(colecaoPesquisa);

                pesquisarActionForm.set("numeroSetorComercial", String
                        .valueOf(objetoSetorComercial.getCodigo()));
                pesquisarActionForm.set("idSetorComercial", String
                        .valueOf(objetoSetorComercial.getId()));
                pesquisarActionForm.set("descricaoSetorComercial",
                        objetoSetorComercial.getDescricao());
                httpServletRequest.setAttribute("corSetorComercial", "valor");
                
                httpServletRequest.setAttribute("nomeCampo", "idRota");
            }
        }
        
        
        return objetoSetorComercial;
    }
    
    
    
    /**
     * 
     * @param pesquisarActionForm
     * @param fachada
     * @param httpServletRequest
     */
    private Rota pesquisarRota(DynaValidatorForm pesquisarActionForm,
            Fachada fachada, HttpServletRequest httpServletRequest, SetorComercial setorComercial) {
    	
    	Rota objetoRota = null;
    	
    	if (setorComercial == null) {
    		
    		pesquisarActionForm.set("idRota", "");
            pesquisarActionForm.set("descricaoRota", "Informe Setor Comercial");
            httpServletRequest.setAttribute("corRotaMensagem", "exception");
            
            httpServletRequest.setAttribute("nomeCampo", "numeroSetorComercial");
            
        } else {
        	
        	//Recebe o valor do campo rotaID do formul�rio.
            String rotaCodigo = (String) pesquisarActionForm.get("idRota");

            FiltroRota filtroRota = new FiltroRota();
            
            //Objetos que ser�o retornados pelo hibernate.
            filtroRota.adicionarCaminhoParaCarregamentoEntidade("leituraTipo");

            filtroRota.adicionarParametro(new ParametroSimples(
            FiltroRota.SETOR_COMERCIAL_ID, setorComercial.getId()));
            
            filtroRota.adicionarParametro(new ParametroSimples(
            FiltroRota.CODIGO_ROTA, rotaCodigo));

            filtroRota.adicionarParametro(new ParametroSimples(
            FiltroRota.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));

            //Retorna Rota
            colecaoPesquisa = fachada.pesquisar(filtroRota, Rota.class
                    .getName());

            if (colecaoPesquisa == null || colecaoPesquisa.isEmpty()) {
                //Rota nao encontrada
                //Limpa o campo idRota do formul�rio
                pesquisarActionForm.set("idRota", "");
                pesquisarActionForm.set("descricaoRota",
                        "Rota inexistente.");
                httpServletRequest.setAttribute("corRotaMensagem",
                        "exception");
                
                httpServletRequest.setAttribute("nomeCampo", "idRota");
                
            } else {
                objetoRota = (Rota) Util
                        .retonarObjetoDeColecao(colecaoPesquisa);

                pesquisarActionForm.set("idRota", String.valueOf(objetoRota
                        .getCodigo()));
                pesquisarActionForm.set("descricaoRota", objetoRota
                        .getLeituraTipo().getDescricao());
                httpServletRequest.setAttribute("corRotaMensagem", "valor");
                
                httpServletRequest.setAttribute("nomeCampo", "botaoSelecionar");
            }
            
        }
    	
    	
    	return objetoRota;
    }

}
