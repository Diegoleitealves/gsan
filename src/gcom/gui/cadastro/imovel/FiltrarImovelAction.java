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
package gcom.gui.cadastro.imovel;

import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.cliente.FiltroClienteImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.FiltroParametro;
import gcom.util.filtro.ParametroNulo;
import gcom.util.filtro.ParametroSimples;
import gcom.util.filtro.ParametroSimplesDiferenteDe;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * < <Descri��o da Classe>>
 * 
 * @author Administrador
 */
public class FiltrarImovelAction extends GcomAction {

	private FiltroClienteImovelParams filtroClienteImovelParams;
	private FiltroClienteImovel filtroClienteImovel;
	
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

        HttpSession sessao = httpServletRequest.getSession(false);
		
		FiltrarImovelActionForm filtrarImovelActionForm = filtrarImovelPorId(actionForm, httpServletRequest);

		verificaAtualizacaoDoFiltro(httpServletRequest, filtrarImovelActionForm);
        
        this.filtroClienteImovel = new FiltroClienteImovel();
        this.filtroClienteImovelParams = new FiltroClienteImovelParams(filtrarImovelActionForm, filtroClienteImovel);
        
        configurarCaminhosParaCarregamentoEntidade();        
        adicionaFiltroVinculoImovelRateioConsumo(sessao);
        adicionaFiltrosImovelExcluido();
		
        if (filtroClienteImovel.getParametros().size() != 0) {
        	httpServletRequest.setAttribute("filtroImovelPreenchido", filtroClienteImovel);
        	configuraSessao(sessao);
        	
    		setLinkSucesso(httpServletRequest, sessao);
    		
        } else {
        	throw new ActionServletException(
        			"atencao.filtro.nenhum_parametro_informado");		    
        }

        sessao.removeAttribute("primeiraVez");
        
        ActionForward retorno = getRetorno(actionMapping, sessao);
        return retorno;
    }

    private FiltrarImovelActionForm filtrarImovelPorId(ActionForm actionForm, HttpServletRequest httpServletRequest) {
		String idImovel = (String) httpServletRequest.getParameter("idImovel");
		
		FiltrarImovelActionForm filtrarImovelActionForm;
		if(idImovel != null && !idImovel.equals("")){
			
			filtrarImovelActionForm = new FiltrarImovelActionForm();
			filtrarImovelActionForm.setMatriculaFiltro(idImovel);
						
		}else{
			filtrarImovelActionForm = (FiltrarImovelActionForm) actionForm;
		}
		return filtrarImovelActionForm;
	}
    
    private void verificaAtualizacaoDoFiltro(HttpServletRequest httpServletRequest, FiltrarImovelActionForm filtrarImovelActionForm) {
    	String atualizar = (String)httpServletRequest.getParameter("atualizarFiltro");
    	
    	if (atualizar != null && atualizar.equals("1")) {
    		httpServletRequest.setAttribute("atualizar", atualizar);
    	}else{
    		filtrarImovelActionForm.setAtualizarFiltro("");
    	}
    }

    private void configurarCaminhosParaCarregamentoEntidade() {
    	filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel.logradouroCep.logradouro.logradouroTipo");
    	filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel.logradouroCep.logradouro.logradouroTitulo");
    	filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel.enderecoReferencia");
    	filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel.logradouroCep.cep");
    	filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel.perimetroInicial.logradouroTipo");
    	filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel.perimetroInicial.logradouroTitulo");
    	filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel.perimetroFinal.logradouroTipo");
    	filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel.perimetroFinal.logradouroTitulo");
    	filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel.logradouroBairro.bairro.municipio.unidadeFederacao");
    }

    private void adicionaFiltroVinculoImovelRateioConsumo(HttpSession sessao) {
    	String redirecionar = (String) sessao.getAttribute("redirecionar");
    	
    	if(redirecionar != null && !redirecionar.trim().equalsIgnoreCase("") && redirecionar.equals("ManterVinculoImoveisRateioConsumo")){
    		filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.CLIENTE_RELACAO_TIPO,ClienteRelacaoTipo.USUARIO));
    	}
    }

	private void adicionaFiltrosImovelExcluido() {
		filtroClienteImovel.adicionarParametro(new ParametroSimplesDiferenteDe(
				FiltroClienteImovel.INDICADOR_IMOVEL_EXCLUIDO,
					Imovel.IMOVEL_EXCLUIDO, FiltroParametro.CONECTOR_OR,2));

		filtroClienteImovel.adicionarParametro(new ParametroNulo(
				FiltroClienteImovel.INDICADOR_IMOVEL_EXCLUIDO));
        
		filtroClienteImovel.adicionarParametro(new ParametroSimples(
				FiltroClienteImovel.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
	}

	private void configuraSessao(HttpSession sessao) {
		sessao.setAttribute("filtroImovelPreenchido", filtroClienteImovel);
		sessao.setAttribute("idLocalidade", filtroClienteImovelParams.idLocalidade);
		sessao.setAttribute("idSetorComercial", filtroClienteImovelParams.idSetorComercial);
		sessao.setAttribute("idQuadra", filtroClienteImovelParams.idQuadra);
		sessao.setAttribute("lote", filtroClienteImovelParams.lote);
		sessao.setAttribute("subLote", filtroClienteImovelParams.subLote);
		sessao.setAttribute("codigoCliente", filtroClienteImovelParams.codigoCliente);
		sessao.setAttribute("idMunicipio", filtroClienteImovelParams.idMunicipio);
		sessao.setAttribute("cep", filtroClienteImovelParams.cep);
		sessao.setAttribute("idBairro", filtroClienteImovelParams.idBairro);
		sessao.setAttribute("idLogradouro", filtroClienteImovelParams.idLogradouro);
		sessao.setAttribute("idImovel", filtroClienteImovelParams.matricula);
		sessao.setAttribute("numeroImovelInicial", filtroClienteImovelParams.numeroImovelInicial);
		sessao.setAttribute("numeroImovelFinal", filtroClienteImovelParams.numeroImovelFinal);
	}

	private void setLinkSucesso(HttpServletRequest httpServletRequest, HttpSession sessao) {
		String linkSucesso = (String) httpServletRequest.getParameter("linkSucesso");
		
		if(linkSucesso != null && !linkSucesso.equals("")){			
		
			sessao.setAttribute("linkSucesso", linkSucesso);							
		}
	}

	private ActionForward getRetorno(ActionMapping actionMapping, HttpSession sessao) {
		ActionForward retorno;
		String redirecionar = (String) sessao.getAttribute("redirecionar");
		
		if (redirecionar != null && !redirecionar.trim().equalsIgnoreCase("") && "ManterVinculoImoveisRateioConsumo".equals(redirecionar)) {
	      	retorno = actionMapping.findForward("retornarVinculosImoveisRateioConsumo");
		} else if (redirecionar != null && !redirecionar.trim().equalsIgnoreCase("") && "ManterDadosTarifaSocial".equals(redirecionar)) {
			retorno = actionMapping.findForward("retornarManterDadosTarifaSocial");
		} else {
        	retorno = actionMapping.findForward("retornarFiltroImovel");
        }
		return retorno;
	}
	
	private class FiltroClienteImovelParams {
		public String idLocalidade;
		public String idSetorComercial;
		public String idQuadra;
		public String lote;
		public String subLote;
		public String codigoCliente;
		public String idMunicipio;
		public String cep;
		public String idBairro;
		public String idLogradouro;
		public String matricula;
		public String numeroImovelInicial;
		public String numeroImovelFinal;
		
		public FiltroClienteImovelParams(FiltrarImovelActionForm filtrarImovelActionForm, FiltroClienteImovel filtro){
			setIdLocalidade((String) filtrarImovelActionForm.getIdLocalidadeFiltro(), filtro);
			setIdSetorComercial((String) filtrarImovelActionForm.getIdSetorComercialFiltro(), filtro);
			setIdQuadra((String) filtrarImovelActionForm.getIdQuadraFiltro(), filtro);
			setLote((String) filtrarImovelActionForm.getLoteFiltro(), filtro);
			setSublote((String) filtrarImovelActionForm.getSubLoteFiltro(), filtro);
			setCodigoCliente((String) filtrarImovelActionForm.getIdClienteFiltro(), filtro);
			setIdMunicipio((String) filtrarImovelActionForm.getIdMunicipioFiltro(), filtro);
			setCep((String) filtrarImovelActionForm.getCepFiltro(), filtro);
			setIdBairro((String) filtrarImovelActionForm.getIdBairroFiltro(), filtro);
			setIdLogradouro((String) filtrarImovelActionForm.getIdLogradouroFiltro(), filtro);
			setMatricula((String) filtrarImovelActionForm.getMatriculaFiltro(), filtro);
			
	        this.numeroImovelInicial = (String) filtrarImovelActionForm.getNumeroImovelInicialFiltro();
	        this.numeroImovelFinal = (String) filtrarImovelActionForm.getNumeroImovelFinalFiltro();
		}
		
		private void setIdLocalidade(String valor, FiltroClienteImovel filtro){
			this.idLocalidade = valor;
			if(idLocalidade != null && !idLocalidade.equals("")){
				filtro.adicionarParametro(new ParametroSimples(FiltroClienteImovel.LOCALIDADE_ID, new Integer(idLocalidade)));
			}
		}
		
		private void setIdSetorComercial(String valor, FiltroClienteImovel filtro){
			this.idSetorComercial = valor;
			if(idSetorComercial != null && !idSetorComercial.equals("")){
				filtro.adicionarCaminhoParaCarregamentoEntidade("imovel.setorComercial");
				filtro.adicionarParametro(new ParametroSimples(FiltroClienteImovel.SETOR_COMERCIAL_CODIGO, new Integer(idSetorComercial)));
			}
		}
		
		private void setIdQuadra(String valor, FiltroClienteImovel filtro){
			this.idQuadra = valor;
			if(idQuadra != null && !idQuadra.equals("")){
				filtro.adicionarCaminhoParaCarregamentoEntidade("imovel.quadra.rota");
				filtro.adicionarParametro(new ParametroSimples(FiltroClienteImovel.QUADRA_NUMERO, new Integer(idQuadra)));
			}
		}
		
		private void setLote(String valor, FiltroClienteImovel filtro){
			this.lote = valor;
			if(lote != null && !lote.equals("")){
				filtro.adicionarParametro(new ParametroSimples(FiltroClienteImovel.LOTE, new Short(lote)));
			}
		}
		
		private void setSublote(String valor, FiltroClienteImovel filtro){
			this.subLote = valor;
			if(subLote != null && !subLote.equals("")){
				filtro.adicionarParametro(new ParametroSimples(FiltroClienteImovel.SUBLOTE, new Short(subLote)));
			}
		}
		
		private void setCodigoCliente(String valor, FiltroClienteImovel filtro){
			this.codigoCliente = valor;
			if(codigoCliente != null && !codigoCliente.equals("")){
				filtro.adicionarParametro(new ParametroSimples(FiltroClienteImovel.CLIENTE_ID, new Short(codigoCliente)));
			}
		}
		
		private void setIdMunicipio(String valor, FiltroClienteImovel filtro){
			this.idMunicipio = valor;
			if(idMunicipio != null && !idMunicipio.equals("")){
				filtro.adicionarCaminhoParaCarregamentoEntidade("imovel.setorComercial.municipio");
				filtro.adicionarParametro(new ParametroSimples(FiltroClienteImovel.MUNICIPIO_SETOR_COMERICAL_CODIGO, new Integer(idMunicipio)));
			}
		}
		
		private void setCep(String valor, FiltroClienteImovel filtro){
			this.cep = valor;
			if(cep != null && !cep.equals("")){
				filtro.adicionarParametro(new ParametroSimples(FiltroClienteImovel.CEP_CODIGO, new Integer(cep)));
			}
		}
		
		private void setIdBairro(String valor, FiltroClienteImovel filtro){
			this.idBairro = valor;
			if(idBairro != null && !idBairro.equals("")){
				filtro.adicionarParametro(new ParametroSimples(FiltroClienteImovel.BAIRRO_CODIGO, new Integer(idBairro)));
			}
		}
		
		private void setIdLogradouro(String valor, FiltroClienteImovel filtro){
			this.idLogradouro = valor;
			if(idLogradouro != null && !idLogradouro.equals("")){
				filtro.adicionarCaminhoParaCarregamentoEntidade("imovel.logradouroCep.logradouro");
				filtro.adicionarParametro(new ParametroSimples(FiltroClienteImovel.LOGRADOURO_ID, new Integer(idLogradouro)));
			}
		}
		
		private void setMatricula(String valor, FiltroClienteImovel filtro){
			this.matricula = valor;
			if(matricula != null && !matricula.equals("")){
				filtro.adicionarParametro(new ParametroSimples(FiltroClienteImovel.IMOVEL_ID, matricula));
			}
		}
	}
}