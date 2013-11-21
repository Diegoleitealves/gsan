/*
* Copyright (C) 2007-2007 the GSAN - Sistema Integrado de Gest�o de Servi�os de Saneamento
*
* This file is part of GSAN, an integrated service management system for Sanitation
*
* GSAN is free software; you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation; either version 2 of the License.
*
* GSAN is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA
*/

/*
* GSAN - Sistema Integrado de Gest�o de Servi�os de Saneamento
* Copyright (C) <2007> 
* Adriano Britto Siqueira
* Alexandre Santos Cabral
* Ana Carolina Alves Breda
* Ana Maria Andrade Cavalcante
* Aryed Lins de Ara�jo
* Bruno Leonardo Rodrigues Barros
* Carlos Elmano Rodrigues Ferreira
* Cl�udio de Andrade Lira
* Denys Guimar�es Guenes Tavares
* Eduardo Breckenfeld da Rosa Borges
* Fab�ola Gomes de Ara�jo
* Fl�vio Leonardo Cavalcanti Cordeiro
* Francisco do Nascimento J�nior
* Homero Sampaio Cavalcanti
* Ivan S�rgio da Silva J�nior
* Jos� Edmar de Siqueira
* Jos� Thiago Ten�rio Lopes
* K�ssia Regina Silvestre de Albuquerque
* Leonardo Luiz Vieira da Silva
* M�rcio Roberto Batista da Silva
* Maria de F�tima Sampaio Leite
* Micaela Maria Coelho de Ara�jo
* Nelson Mendon�a de Carvalho
* Newton Morais e Silva
* Pedro Alexandre Santos da Silva Filho
* Rafael Corr�a Lima e Silva
* Rafael Francisco Pinto
* Rafael Koury Monteiro
* Rafael Palermo de Ara�jo
* Raphael Veras Rossiter
* Roberto Sobreira Barbalho
* Rodrigo Avellar Silveira
* Rosana Carvalho Barbosa
* S�vio Luiz de Andrade Cavalcante
* Tai Mu Shih
* Thiago Augusto Souza do Nascimento
* Tiago Moreno Rodrigues
* Vivianne Barbosa Sousa
* Anderson Italo Felinto de Lima
*
* Este programa � software livre; voc� pode redistribu�-lo e/ou
* modific�-lo sob os termos de Licen�a P�blica Geral GNU, conforme
* publicada pela Free Software Foundation; vers�o 2 da
* Licen�a.
* Este programa � distribu�do na expectativa de ser �til, mas SEM
* QUALQUER GARANTIA; sem mesmo a garantia impl�cita de
* COMERCIALIZA��O ou de ADEQUA��O A QUALQUER PROP�SITO EM
* PARTICULAR. Consulte a Licen�a P�blica Geral GNU para obter mais
* detalhes.
* Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU
* junto com este programa; se n�o, escreva para Free Software
* Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
* 02111-1307, USA.
*/  
package gcom.gui.cobranca;

import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.FiltroSetorComercial;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cobranca.CobrancaAtividade;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.FiltroRota;
import gcom.micromedicao.Rota;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC0243] Inserir Comando de A��o de Conbran�a - Tipo de Comando Cronograma
 * Executado qdo o usu�rio clica em Concluir estando na tela de comandar_acao_cobranca_eventual_inserir_processo2.jsp 
 * @author Rafael Santos
 * @since 24/01/2006
 */
public class InserirComandoAcaoCobrancaEventualConcluirAction extends
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
		 
		InserirComandoAcaoCobrancaEventualCriterioRotaActionForm inserirComandoAcaoCobrancaEventualCriterioRotaActionForm = 
			(InserirComandoAcaoCobrancaEventualCriterioRotaActionForm) actionForm;

		String idLocalidade = inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getLocalidadeOrigemID();
		String codigoSetorComercial = inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getSetorComercialOrigemCD();

		String idLocalidadeFinal = inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getLocalidadeDestinoID();
		String codigoSetorComercialFinal = inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getSetorComercialDestinoCD();

		
		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
        if (idLocalidade != null && !idLocalidade.toString().trim().equalsIgnoreCase("")) {
            
        	filtroLocalidade.limparListaParametros();
            
        	//coloca parametro no filtro
            filtroLocalidade.adicionarParametro(
            	new ParametroSimples(
                    FiltroLocalidade.INDICADORUSO,
                    ConstantesSistema.INDICADOR_USO_ATIVO));
            filtroLocalidade.adicionarParametro(
            	new ParametroSimples(
                    FiltroLocalidade.ID, 
                    new Integer(idLocalidade)));
            //pesquisa
            Collection localidades = 
            	this.getFachada().pesquisar(filtroLocalidade, 
            		Localidade.class.getName());
            
            if (localidades == null || localidades.isEmpty()) {
            	throw new ActionServletException("atencao.pesquisa.localidade_inicial_inexistente");
            }
        }

        FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
        if (codigoSetorComercial != null && 
        	!codigoSetorComercial.toString().trim().equalsIgnoreCase("")) {
            
        	if (idLocalidade != null && 
        		!idLocalidade.toString().trim().equalsIgnoreCase("")) {
                
        		filtroSetorComercial.limparListaParametros();
                //coloca parametro no filtro
                filtroSetorComercial.adicionarParametro(
                	new ParametroSimples(
                        FiltroSetorComercial.INDICADORUSO,
                        ConstantesSistema.INDICADOR_USO_ATIVO));
                
                filtroSetorComercial.adicionarParametro(
                	new ParametroSimples(
                        FiltroSetorComercial.ID_LOCALIDADE, 
                        new Integer(idLocalidade)));
                
                filtroSetorComercial.adicionarParametro(
                	new ParametroSimples(
                        FiltroSetorComercial.CODIGO_SETOR_COMERCIAL,
                        new Integer(codigoSetorComercial)));
                //pesquisa
                Collection setorComerciais = 
                	this.getFachada().pesquisar(filtroSetorComercial,SetorComercial.class.getName());
                
                if (setorComerciais == null || setorComerciais.isEmpty()) {
                	 throw new ActionServletException("atencao.pesquisa.setor_inicial_inexistente");
                }
            }

        } 

        
		filtroLocalidade = new FiltroLocalidade();
        if (idLocalidade != null && 
        	!idLocalidade.toString().trim().equalsIgnoreCase("")) {
            
        	filtroLocalidade.limparListaParametros();
            filtroLocalidade.adicionarParametro(
            	new ParametroSimples(
                    FiltroLocalidade.INDICADORUSO,
                    ConstantesSistema.INDICADOR_USO_ATIVO));
            
            filtroLocalidade.adicionarParametro(
            	new ParametroSimples(
                    FiltroLocalidade.ID, 
                    new Integer(idLocalidadeFinal)));

            Collection localidades = 
            	this.getFachada().pesquisar(filtroLocalidade, 
            		Localidade.class.getName());
            
            if (localidades == null || localidades.isEmpty()) {
            	throw new ActionServletException("atencao.pesquisa.localidade_final_inexistente");
            }
        }

        filtroSetorComercial = new FiltroSetorComercial();
        if (codigoSetorComercial != null && 
        	!codigoSetorComercial.toString().trim().equalsIgnoreCase("")) {
            
        	if (idLocalidade != null && 
        		!idLocalidade.toString().trim().equalsIgnoreCase("")) {
                
        		filtroSetorComercial.limparListaParametros();
                //coloca parametro no filtro
                filtroSetorComercial.adicionarParametro(
                	new ParametroSimples(
                        FiltroSetorComercial.INDICADORUSO,
                        ConstantesSistema.INDICADOR_USO_ATIVO));
                
                filtroSetorComercial.adicionarParametro(
                	new ParametroSimples(
                        FiltroSetorComercial.ID_LOCALIDADE, 
                        new Integer(idLocalidadeFinal)));
                
                filtroSetorComercial.adicionarParametro(
                	new ParametroSimples(
                        FiltroSetorComercial.CODIGO_SETOR_COMERCIAL,
                        new Integer(codigoSetorComercialFinal)));

                //pesquisa
                Collection setorComerciais = 
                	this.getFachada().pesquisar(filtroSetorComercial,
                        SetorComercial.class.getName());
                
                if (setorComerciais == null || setorComerciais.isEmpty()) {
                	 throw new ActionServletException("atencao.pesquisa.setor_final_inexistente");
                }
            }
        } 
		
        String codigoRotaInicial = inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getRotaInicial();
		String idRotaInicial = null;
        if((idLocalidade != null && !idLocalidade.equals("")) &&	
       		(codigoSetorComercial != null && !codigoSetorComercial.equals("")) &&		
       		(codigoRotaInicial != null && !codigoRotaInicial.equals(""))){

        	FiltroRota filtroRota = new FiltroRota();
    		filtroRota.adicionarParametro(new ParametroSimples(
    				FiltroRota.CODIGO_ROTA, codigoRotaInicial));
            filtroRota.adicionarParametro(new ParametroSimples(
                    FiltroRota.LOCALIDADE_ID, idLocalidade));
            filtroRota.adicionarParametro(new ParametroSimples(
                    FiltroRota.SETOR_COMERCIAL_CODIGO, codigoSetorComercial));		
    		
    		Collection rotas = this.getFachada().pesquisar(filtroRota,Rota.class.getName());
    		if(rotas != null && !rotas.isEmpty()){
    			idRotaInicial = ((Rota)rotas.iterator().next()).getId().toString();
    		}else{
    			throw new ActionServletException(
    				"atencao.pesquisa.rota_inicial_inexistente");			
    		}
        }

        
        String codigoRotaFinal = inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getRotaFinal();
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
    		rotas = this.getFachada().pesquisar(filtroRota,Rota.class.getName());
    		if(rotas != null && !rotas.isEmpty()){
    			idRotaFinal = ((Rota)rotas.iterator().next()).getId().toString();
    		}else{
    			throw new ActionServletException(
    				"atencao.pesquisa.rota_final_inexistente");			
    		}
        	
        }
        
		
		
		Collection colecaoCobrancaAcaoAtividadeComando = 
			this.getFachada().concluirComandoAcaoCobranca(
				inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getPeriodoInicialConta(),
				inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getPeriodoFinalConta(),
				inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getPeriodoVencimentoContaInicial(),
				inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getPeriodoVencimentoContaFinal(),
				inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getCobrancaAcao(),
				inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getCobrancaAtividade(),
				inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getCobrancaGrupo(),
				inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getGerenciaRegional(),
				inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getLocalidadeOrigemID(),
				inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getLocalidadeDestinoID(),
				inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getSetorComercialOrigemCD(),
				inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getSetorComercialDestinoCD(),
				inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getIdCliente(),
				inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getClienteRelacaoTipo(),
				inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getIndicador(),idRotaInicial,
				idRotaFinal,
				inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getSetorComercialOrigemID(),
				inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getSetorComercialDestinoID(),
				null,
				inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getUnidadeNegocio(),
				this.getUsuarioLogado(httpServletRequest),
				inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getTitulo(),
				inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getDescricaoSolicitacao(),
				inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getPrazoExecucao(),
				inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getQuantidadeMaximaDocumentos(),
				inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getValorLimiteObrigatoria(),
				inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getIndicadorImoveisDebito(),
				inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getIndicadorGerarBoletimCadastro(),
				inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getCodigoClienteSuperior(), codigoRotaInicial,
				codigoRotaFinal,
				inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getLogradouroId(),
				inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getConsumoMedioInicial(),
				inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getConsumoMedioFinal(),
				inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getTipoConsumo(),
				inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getPeriodoInicialFiscalizacao(),
				inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getPeriodoFinalFiscalizacao(),
				inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getSituacaoFiscalizacao(),
				inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getNumeroQuadraInicial(),
				inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getNumeroQuadraFinal());		
		
		//pesquisar cobranca acao
		//CobrancaAcao cobrancaAcao =  fachada.consultarCobrancaAcao(inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getCobrancaAcao());
		
		//pesquisar cobranca atividade
		CobrancaAtividade cobrancaAtividade = 
			this.getFachada().consultarCobrancaAtividade(inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getCobrancaAtividade());
		
		montarPaginaSucesso(httpServletRequest,
	           " "+colecaoCobrancaAcaoAtividadeComando.size()+" A��o(�es) de cobran�a para a atividade " 
	           + cobrancaAtividade.getDescricaoCobrancaAtividade() + " comandada(s) com sucesso.",
	           "Inserir outra Comando de A��o de Cobran�a",
	           "exibirInserirComandoAcaoCobrancaAction.do?limparForm=OK&menu=sim");

		return retorno;
	}

	
}