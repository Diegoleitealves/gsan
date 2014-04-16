package gcom.gui.cadastro.localidade;

import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.FiltroQuadra;
import gcom.cadastro.localidade.FiltroSetorComercial;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.SetorComercial;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
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

/**
 * Descri��o da classe
 * 
 * @author Administrador
 * @date 08/07/2006
 */
public class FiltrarQuadraAction extends GcomAction {

	/**
	 * <Breve descri��o sobre o caso de uso>
	 * 
	 * <Identificador e nome do caso de uso>
	 * 
	 * <Breve descri��o sobre o subfluxo>
	 * 
	 * <Identificador e nome do subfluxo>
	 * 
	 * <Breve descri��o sobre o fluxo secund�rio>
	 * 
	 * <Identificador e nome do fluxo secund�rio>
	 * 
	 * @author Administrador
	 * @date 08/07/2006
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

		ActionForward retorno = actionMapping
				.findForward("retornarFiltroQuadra");

		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		FiltrarQuadraActionForm filtrarQuadraActionForm = (FiltrarQuadraActionForm) actionForm;

		FiltroQuadra filtroQuadra = new FiltroQuadra(FiltroQuadra.ID_LOCALIDADE);

		// Objetos que ser�o retornados pelo hibernate.
		filtroQuadra
				.adicionarCaminhoParaCarregamentoEntidade("setorComercial.localidade");

		String localidadeID = filtrarQuadraActionForm.getLocalidadeID();
		String setorComercialCD = filtrarQuadraActionForm.getSetorComercialCD();
		String quadraNM = filtrarQuadraActionForm.getQuadraNM();
		//String bairroNome = filtrarQuadraActionForm.getBairroNome();
		String idRota = filtrarQuadraActionForm.getIdRota();
		String indicadorUso = filtrarQuadraActionForm.getIndicadorUso();
		//String tipoPesquisa = filtrarQuadraActionForm.getTipoPesquisa();
		// 1 check --- null uncheck
		String indicadorAtualizar = httpServletRequest
				.getParameter("indicadorAtualizar");

		boolean peloMenosUmParametroInformado = false;

		if (localidadeID != null && !localidadeID.equalsIgnoreCase("")) {
			
			pesquisarLocalidade(filtrarQuadraActionForm,fachada,localidadeID);
			
			peloMenosUmParametroInformado = true;
			filtroQuadra.adicionarParametro(new ParametroSimples(
					FiltroQuadra.ID_LOCALIDADE, new Integer(localidadeID)));
		}

		if (setorComercialCD != null && !setorComercialCD.equalsIgnoreCase("")) {
			
			pesquisarSetorComercial(filtrarQuadraActionForm,fachada,setorComercialCD,localidadeID);
			
			peloMenosUmParametroInformado = true;
			filtroQuadra.adicionarParametro(new ParametroSimples(
					FiltroQuadra.CODIGO_SETORCOMERCIAL, new Integer(setorComercialCD)));
		}

		if (quadraNM != null && !quadraNM.equalsIgnoreCase("")) {
			peloMenosUmParametroInformado = true;
			filtroQuadra.adicionarParametro(new ParametroSimples(
					FiltroQuadra.NUMERO_QUADRA, new Integer(quadraNM)));
		}
		
		if (idRota != null && !idRota.equalsIgnoreCase("")) {
			peloMenosUmParametroInformado = true;
			filtroQuadra.adicionarParametro(new ParametroSimples(
					FiltroQuadra.ROTA_ID, new Integer(idRota)));
		}
/*
		if (bairroNome != null && !bairroNome.equalsIgnoreCase("")) {
			peloMenosUmParametroInformado = true;
			if (tipoPesquisa != null
					&& tipoPesquisa
							.equals(ConstantesSistema.TIPO_PESQUISA_COMPLETA
									.toString())) {
				filtroQuadra.adicionarParametro(new ComparacaoTextoCompleto(
						FiltroQuadra.NOME_BAIRRO, bairroNome));
			} else {
				filtroQuadra.adicionarParametro(new ComparacaoTexto(
						FiltroQuadra.NOME_BAIRRO, bairroNome));
			}
		}
*/
		if (indicadorUso != null && !indicadorUso.equalsIgnoreCase("")
				&& !indicadorUso.equalsIgnoreCase("3")) {
			peloMenosUmParametroInformado = true;
			if (indicadorUso.equalsIgnoreCase(String
					.valueOf(ConstantesSistema.INDICADOR_USO_ATIVO))) {
				filtroQuadra.adicionarParametro(new ParametroSimples(
						FiltroQuadra.INDICADORUSO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
			} else {
				filtroQuadra.adicionarParametro(new ParametroSimples(
						FiltroQuadra.INDICADORUSO,
						ConstantesSistema.INDICADOR_USO_DESATIVO));
			}
		}

		// Erro caso o usu�rio mandou filtrar sem nenhum par�metro
		if (!peloMenosUmParametroInformado) {
			throw new ActionServletException(
					"atencao.filtro.nenhum_parametro_informado");
		}

		filtroQuadra.setCampoOrderBy(FiltroQuadra.NUMERO_QUADRA,
				FiltroQuadra.DESCRICAO_LOCALIDADE);

		sessao.setAttribute("filtroQuadra", filtroQuadra);
		sessao.setAttribute("indicadorAtualizar", indicadorAtualizar);

		// devolve o mapeamento de retorno
		return retorno;
	}
	
	 private void pesquisarLocalidade(
	    		FiltrarQuadraActionForm filtrarQuadraActionForm,
	            Fachada fachada, String localidadeID) {
		 
		Collection colecaoPesquisa;
		 
        FiltroLocalidade filtroLocalidade = new FiltroLocalidade();

        filtroLocalidade.adicionarParametro(new ParametroSimples(
                FiltroLocalidade.ID, localidadeID));

        //Retorna localidade
        colecaoPesquisa = fachada.pesquisar(filtroLocalidade,
                Localidade.class.getName());

        if (colecaoPesquisa == null || colecaoPesquisa.isEmpty()) {
            //Localidade nao encontrada
            //Limpa o campo localidadeID do formul�rio
        	filtrarQuadraActionForm.setLocalidadeID("");
        	filtrarQuadraActionForm
                    .setLocalidadeNome("Localidade inexistente.");
        	
        	throw new ActionServletException("atencao.pesquisa_inexistente",
					null,"Localidade");	

        }else {
            Localidade objetoLocalidade = (Localidade) Util
            .retonarObjetoDeColecao(colecaoPesquisa);
		    filtrarQuadraActionForm.setLocalidadeID(String
		            .valueOf(objetoLocalidade.getId()));
		    filtrarQuadraActionForm
		            .setLocalidadeNome(objetoLocalidade.getDescricao());
		
		}
	
	}

	    private void pesquisarSetorComercial(
	    		FiltrarQuadraActionForm filtrarQuadraActionForm,
	            Fachada fachada,String setorComercialCD,String localidadeID) {

			 Collection colecaoPesquisa;
	    	
	        if (localidadeID == null || localidadeID.trim().equalsIgnoreCase("")) {
	            //Limpa os campos setorComercialCD e setorComercialID do formulario
	        	filtrarQuadraActionForm.setSetorComercialCD("");
	        	filtrarQuadraActionForm.setSetorComercialID("");
	        	filtrarQuadraActionForm
	                    .setSetorComercialNome("Informe Localidade.");
	        	  throw new ActionServletException("atencao.campo_selecionado.obrigatorio",
	    					null,"Localidade");	
	        	
	        } else {

	            FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();

	            filtroSetorComercial.adicionarParametro(new ParametroSimples(
	                    FiltroSetorComercial.ID_LOCALIDADE, localidadeID));

	            filtroSetorComercial.adicionarParametro(new ParametroSimples(
	                    FiltroSetorComercial.CODIGO_SETOR_COMERCIAL,
	                    setorComercialCD));

	            //Retorna setorComercial
	            colecaoPesquisa = fachada.pesquisar(filtroSetorComercial,
	                    SetorComercial.class.getName());

	            if (colecaoPesquisa == null || colecaoPesquisa.isEmpty()) {
	                //setorComercial nao encontrado
	                //Limpa os campos setorComercialCD e setorComercialID do
	                // formulario
	            	filtrarQuadraActionForm.setSetorComercialCD("");
	                filtrarQuadraActionForm.setSetorComercialID("");
	                filtrarQuadraActionForm
	                        .setSetorComercialNome("Setor comercial inexistente.");
	                throw new ActionServletException("atencao.setor_comercial_nao_existe");	
	            } else {
	                SetorComercial objetoSetorComercial = (SetorComercial) Util
                    .retonarObjetoDeColecao(colecaoPesquisa);
			        filtrarQuadraActionForm.setSetorComercialCD(String
			                .valueOf(objetoSetorComercial.getCodigo()));
			        filtrarQuadraActionForm.setSetorComercialID(String
			                .valueOf(objetoSetorComercial.getId()));
			        filtrarQuadraActionForm
			                .setSetorComercialNome(objetoSetorComercial
			                        .getDescricao());
			
			    }

	        }
	    }

}
