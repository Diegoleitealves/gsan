package gcom.gui.atendimentopublico.ordemservico;

import gcom.atendimentopublico.ordemservico.FiltroServicoPerfilTipo;
import gcom.atendimentopublico.ordemservico.ServicoPerfilTipo;
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

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC0388] Pesquisar Tipo Perfil Servi�o
 * 
 * @author Ana Maria
 * @date 01/08/2006
 * 
 */
public class PesquisarTipoPerfilServicoAction extends GcomAction {
	/**
	 * [UC0388] Esse caso de uso efetua pesquisa do perfil de servi�o
	 * 
	 * @author Ana Maria
	 * @date 01/08/2006
	 * 
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return ActionForward
	 */
	   public ActionForward execute(ActionMapping actionMapping,
	            ActionForm actionForm, HttpServletRequest httpServletRequest,
	            HttpServletResponse httpServletResponse) {

	        ActionForward retorno = actionMapping.findForward("listaTipoPerfilServico");
	        
	        //Obt�m a inst�ncia da Fachada
	        Fachada fachada = Fachada.getInstancia();
	        
			// Obt�m o action form
	        PesquisarTipoPerfilServicoActionForm pesquisarTipoPerfilServicoActionForm = (PesquisarTipoPerfilServicoActionForm) actionForm;

			// Recupera os par�metros do form
	        String idServicoPerfil = (String) pesquisarTipoPerfilServicoActionForm.getIdServicoPerfil();
	        String descricaoServicoPerfil = (String) pesquisarTipoPerfilServicoActionForm.getDescricaoServicoPerfil();
	        String abreviaturaServicoPerfil = (String) pesquisarTipoPerfilServicoActionForm.getAbreviaturaServicoPerfil();
	        String componenteEquipe = (String) pesquisarTipoPerfilServicoActionForm.getComponenteEquipe();
	        String equipeEspecial = (String) pesquisarTipoPerfilServicoActionForm.getEquipamentoEspecial();
	        String veiculoPropio = (String) pesquisarTipoPerfilServicoActionForm.getVeiculoProprio();
	        String tipoPesquisa = (String) pesquisarTipoPerfilServicoActionForm.getTipoPesquisa();
	        String tipoPesquisaAbreviada = (String) pesquisarTipoPerfilServicoActionForm.getTipoPesquisaAbreviada();

	        boolean peloMenosUmParametroInformado = false;

	        FiltroServicoPerfilTipo filtroServicoPerfilTipo = new FiltroServicoPerfilTipo(FiltroServicoPerfilTipo.DESCRICAO);
	        
			// Insere os par�metros informados no filtro
	        if (idServicoPerfil != null && !idServicoPerfil.trim().equalsIgnoreCase("")) {
	        	filtroServicoPerfilTipo.adicionarParametro(new ParametroSimples(
	        			FiltroServicoPerfilTipo.ID, new Integer(idServicoPerfil)));
	            peloMenosUmParametroInformado = true;
	        }
	        
	        if (descricaoServicoPerfil != null && !descricaoServicoPerfil.trim().equalsIgnoreCase("")) {
	            peloMenosUmParametroInformado = true; 
    			if (tipoPesquisa != null && tipoPesquisa.equals(ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString())) {
    				filtroServicoPerfilTipo.adicionarParametro(new ComparacaoTextoCompleto(
    						FiltroServicoPerfilTipo.DESCRICAO, descricaoServicoPerfil));
    			} else {
    				filtroServicoPerfilTipo.adicionarParametro(new ComparacaoTexto(
    						FiltroServicoPerfilTipo.DESCRICAO, descricaoServicoPerfil));
    			}
	        }
	        
	        if (abreviaturaServicoPerfil != null && !abreviaturaServicoPerfil.trim().equalsIgnoreCase("")) {
	            peloMenosUmParametroInformado = true;	            
    			if (tipoPesquisaAbreviada != null && tipoPesquisaAbreviada.equals(ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString())) {
    				filtroServicoPerfilTipo.adicionarParametro(new ComparacaoTextoCompleto(
    						FiltroServicoPerfilTipo.DESCRICAO_ABREVIADA, abreviaturaServicoPerfil));
    			} else {
    				filtroServicoPerfilTipo.adicionarParametro(new ComparacaoTexto(
    						FiltroServicoPerfilTipo.DESCRICAO_ABREVIADA, abreviaturaServicoPerfil));
    			}
	        }
	        
	        if (componenteEquipe != null && !componenteEquipe.trim().equalsIgnoreCase("")) {
	            peloMenosUmParametroInformado = true;
	        	filtroServicoPerfilTipo.adicionarParametro(new ParametroSimples(
	        			FiltroServicoPerfilTipo.COMPONENTES_EQUIPE, componenteEquipe));	        
	        }
	        
	        if (equipeEspecial != null && !equipeEspecial.trim().equalsIgnoreCase("")) {
	            peloMenosUmParametroInformado = true;
	        	filtroServicoPerfilTipo.adicionarParametro(new ParametroSimples(
	        			FiltroServicoPerfilTipo.ID_EQUIPES_COMPONENTES, equipeEspecial));	        
	        }
	        
	        if (veiculoPropio != null && !veiculoPropio.trim().equalsIgnoreCase("") &&!veiculoPropio.equals("3")) {
	            peloMenosUmParametroInformado = true;
	        	filtroServicoPerfilTipo.adicionarParametro(new ParametroSimples(
	        			FiltroServicoPerfilTipo.VEICULO_PROPIO, veiculoPropio));	        
	        }
	        
			// Erro caso o usu�rio mandou filtrar sem nenhum par�metro
	        if (!peloMenosUmParametroInformado) {
	            throw new ActionServletException(
	                    "atencao.filtro.nenhum_parametro_informado");
	        }
	        
	        filtroServicoPerfilTipo.adicionarParametro(new ParametroSimples(
					FiltroServicoPerfilTipo.INDICADOR_USO,ConstantesSistema.INDICADOR_USO_ATIVO));

			// Faz a pesquisa baseada no filtro
	        Collection servicosPerfilTipo = fachada.pesquisar(filtroServicoPerfilTipo, ServicoPerfilTipo.class.getName());

			// Verificar se a pesquisa de servicoPerfilTipo n�o est� vazia
	        if (servicosPerfilTipo != null && !servicosPerfilTipo.isEmpty()) {
                 //Aciona o controle de pagina��o para que sejam pesquisados apenas
				// os registros que aparecem na p�gina
				Map resultado = controlarPaginacao(httpServletRequest, retorno,
						filtroServicoPerfilTipo, ServicoPerfilTipo.class.getName());
				servicosPerfilTipo = (Collection) resultado.get("colecaoRetorno");
				retorno = (ActionForward) resultado.get("destinoActionForward");
				
				// Manda a cole��o dos servicosPerfilTipo pesquisados para o request
				httpServletRequest.getSession(false).setAttribute("servicosPerfilTipo",
						servicosPerfilTipo);
				
	        } else {
	            throw new ActionServletException(
	                    "atencao.pesquisa.nenhumresultado", null, "servicoTipoPerfil");
	        }
	        
	        return retorno;
	    }

	}
