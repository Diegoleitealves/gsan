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

/* Este programa � software livre; voc� pode redistribu�-lo e/ou
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
package gcom.gui.cadastro.atualizacaocadastral;

import gcom.cadastro.atualizacaocadastral.bean.DadosTabelaAtualizacaoCadastralHelper;
import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.Quadra;
import gcom.cadastro.localidade.SetorComercial;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExibirAtualizarDadosImovelAtualizacaoCadastralPopupAction extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

		// Prepara o retorno da A��o
		ActionForward retorno = actionMapping.findForward("exibirAtualizarDadosImovelAtualizacaoCadastralPopup");
		
		// Cria a sess�o
		HttpSession sessao = httpServletRequest.getSession(false);
		
		// Obt�m o action form
		ExibirAtualizarDadosImovelAtualizacaoCadastralPopupActionForm form = (ExibirAtualizarDadosImovelAtualizacaoCadastralPopupActionForm) actionForm;

		// Obt�m a fachada
		Fachada fachada = Fachada.getInstancia();
		
		String idImovel = (String) httpServletRequest.getParameter("idImovel");
		
		// Realiza o Filtro para o Imovel
		if ( (idImovel != null && !idImovel.equals(""))) {
			FiltroImovel filtro = new FiltroImovel();
			
			filtro.adicionarParametro(new ParametroSimples(FiltroImovel.ID, idImovel));
			
			filtro.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LOCALIDADE);
			filtro.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.SETOR_COMERCIAL);
			filtro.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.QUADRA);
			
			Imovel imovel = (Imovel) Util.retonarObjetoDeColecao(fachada.pesquisar(filtro, Imovel.class.getName()));
			
			form.setIdImovel(idImovel);
			form.setDescricaoImovel("NOVO");
			
			if (imovel != null) {
				Localidade localidade = (Localidade) imovel.getLocalidade();
				SetorComercial setorComercial = (SetorComercial) imovel.getSetorComercial();
				Quadra quadra = (Quadra) imovel.getQuadra();
				
				// Imovel
				form.setDescricaoImovel(imovel.getId().toString());
				// Localidade
				form.setIdLocalidade(localidade.getId().toString());
				form.setDescricaoLocalidade(localidade.getDescricao());
				// Setor Comercial
				form.setIdSetorComercial(setorComercial.getId().toString());
				form.setCodigoSetorComercial("" + setorComercial.getCodigo());
				form.setDescricaoSetorComercial(setorComercial.getDescricao());
				// Quadra
				form.setIdQuadra(quadra.getId().toString());
				form.setNumeroQuadra("" + quadra.getNumeroQuadra());
			}
		}else {
			throw new ActionServletException(
					"atencao.pesquisa.nenhumresultado", null,
					"Dados do Imovel e Cliente");
		}
		
		Collection<DadosTabelaAtualizacaoCadastralHelper> colecaoDadosTabelaAtualizacaoCadastral = 
				fachada.consultarDadosTabelaColunaAtualizacaoCadastral(null, null, new Integer(idImovel), null, null);
		
		if (!colecaoDadosTabelaAtualizacaoCadastral.isEmpty()) {
			sessao.setAttribute("colecaoDadosTabelaAtualizacaoCadastral", colecaoDadosTabelaAtualizacaoCadastral);
		}else { 
			throw new ActionServletException(
					"atencao.pesquisa.nenhumresultado", null,
					"Dados Tabela Atualizacao Cadastral");
		}

		return retorno;
	}
}
