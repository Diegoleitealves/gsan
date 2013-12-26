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
package gcom.gui.cadastro.atualizacaocadastral;

import gcom.cadastro.empresa.Empresa;
import gcom.cadastro.empresa.FiltroEmpresa;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.FiltroSetorComercial;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.SetorComercial;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.gui.micromedicao.DadosLeiturista;
import gcom.micromedicao.FiltroLeiturista;
import gcom.micromedicao.Leiturista;
import gcom.seguranca.transacao.FiltroTabelaColuna;
import gcom.seguranca.transacao.TabelaColuna;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.DescriptorEntity;
import gcom.util.filtro.ParametroSimples;
import gcom.util.filtro.ParametroSimplesIn;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jboss.logging.Logger;

public class ExibirFiltrarAlteracaoAtualizacaoCadastralAction extends GcomAction {

	private static Logger logger = Logger.getLogger(ExibirFiltrarAlteracaoAtualizacaoCadastralAction.class);
	
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		FiltrarAlteracaoAtualizacaoCadastralActionForm form = (FiltrarAlteracaoAtualizacaoCadastralActionForm) actionForm;

		ActionForward retorno = actionMapping.findForward("exibirFiltrarAlteracaoAtualizacaoCadastral");

		Fachada fachada = Fachada.getInstancia();
		
		try {
			if (httpServletRequest.getParameterMap().containsKey("filtroLocalidade")){
				String field = httpServletRequest.getParameter("filtroLocalidade");
				FilterClassParameters filter = new FilterClassParameters(new FiltroLocalidade(), new Localidade(), "Localidade inexistente", field);
				preencherCampoDescricao(form, filter, httpServletRequest);
			}else if (httpServletRequest.getParameterMap().containsKey("filtroSetorComercial")){
				String field = httpServletRequest.getParameter("filtroSetorComercial");
				FilterClassParameters filter = new FilterClassParameters(new FiltroSetorComercial(), new SetorComercial(), "Setor comercial inexistente", field);
				preencherCampoDescricao(form, filter, httpServletRequest);
			}
		} catch (Exception e) {
			logger.error("Erro ao filtrar Cadastro", e);
		}
			

		// Obt�m a sess�o
		HttpSession sessao = httpServletRequest.getSession(false);


		Collection colecaoLeiturista = new ArrayList();

		if (sessao.getAttribute("colecaoEmpresa") == null) {
			FiltroEmpresa filtroEmpresa = new FiltroEmpresa();
			filtroEmpresa.adicionarParametro(new ParametroSimples(FiltroEmpresa.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

			filtroEmpresa.setCampoOrderBy(FiltroEmpresa.DESCRICAO);

			Collection<Empresa> colecaoEmpresa = fachada.pesquisar(filtroEmpresa, Empresa.class.getName());

			// [FS0001 - Verificar Existencia de dados]
			if ((colecaoEmpresa == null) || (colecaoEmpresa.size() == 0)) {
				throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, Empresa.class.getName());
			} else {
				sessao.setAttribute("colecaoEmpresa", colecaoEmpresa);
			}
		}

		// Leiturista da Empresa
		if (form.getIdEmpresa() != null && !form.getIdEmpresa().equals("-1") && !form.getIdEmpresa().equals("")) {

			FiltroLeiturista filtroLeiturista = new FiltroLeiturista(FiltroLeiturista.ID);
			filtroLeiturista.adicionarParametro(new ParametroSimples(FiltroLeiturista.EMPRESA_ID, form.getIdEmpresa()));
			filtroLeiturista.adicionarCaminhoParaCarregamentoEntidade(FiltroLeiturista.CLIENTE);
			filtroLeiturista.adicionarCaminhoParaCarregamentoEntidade(FiltroLeiturista.FUNCIONARIO);

			Collection colecao = fachada.pesquisar(filtroLeiturista, Leiturista.class.getName());

			if (colecao != null && !colecao.isEmpty()) {
				Iterator it = colecao.iterator();
				while (it.hasNext()) {
					Leiturista leitu = (Leiturista) it.next();
					DadosLeiturista dadosLeiu = null;
					if (leitu.getFuncionario() != null) {
						dadosLeiu = new DadosLeiturista(leitu.getId(), leitu.getFuncionario().getNome());
					} else {
						dadosLeiu = new DadosLeiturista(leitu.getId(), leitu.getCliente().getNome());
					}
					colecaoLeiturista.add(dadosLeiu);
				}
			}

		}

		sessao.setAttribute("colecaoLeiturista", colecaoLeiturista);

		Collection colecaoColunaImoveisSelecionados = null;

		if (form.getColunaImoveisSelecionados() != null) {

			String[] aux = form.getColunaImoveisSelecionados();

			List aux1 = Arrays.asList(aux);
			colecaoColunaImoveisSelecionados = aux1;

			FiltroTabelaColuna filtroTabelaColuna = new FiltroTabelaColuna();

			filtroTabelaColuna.adicionarParametro(new ParametroSimplesIn(FiltroTabelaColuna.ID, colecaoColunaImoveisSelecionados));

			filtroTabelaColuna.setCampoOrderBy(FiltroTabelaColuna.DESCRICAO_COLUNA);

			// Pesquisa de acordo com os par�metros informados no filtro
			colecaoColunaImoveisSelecionados = Fachada.getInstancia().pesquisar(filtroTabelaColuna, TabelaColuna.class.getName());

			// Verifica se a pesquisa retornou algum objeto para a cole��o
			if (colecaoColunaImoveisSelecionados != null && !colecaoColunaImoveisSelecionados.isEmpty()) {
				sessao.setAttribute("colecaoColunaImoveisSelecionados", colecaoColunaImoveisSelecionados);
				sessao.setAttribute("existeColecaoColunaImoveisSelecionados", colecaoColunaImoveisSelecionados);
			}
		}

		// Monta a colecao
		this.pesquisarColunaImoveis(httpServletRequest, colecaoColunaImoveisSelecionados);

		return retorno;
	}

	private void pesquisarColunaImoveis(HttpServletRequest httpServletRequest, Collection colecaoColunaImoveisSelecionados) {
		HttpSession sessao = httpServletRequest.getSession(false);

		FiltroTabelaColuna filtroTabelaColuna = new FiltroTabelaColuna();

		filtroTabelaColuna.adicionarParametro(new ParametroSimples(FiltroTabelaColuna.INDICADOR_ATUALIZACAO_CADASRAL, ConstantesSistema.SIM));

		filtroTabelaColuna.setConsultaSemLimites(true);
		filtroTabelaColuna.setCampoOrderBy(FiltroTabelaColuna.DESCRICAO_COLUNA);

		Collection colecaoColunaImoveis = Fachada.getInstancia().pesquisar(filtroTabelaColuna, TabelaColuna.class.getName());

		if (colecaoColunaImoveisSelecionados == null) {
			sessao.setAttribute("colecaoColunaImoveis", colecaoColunaImoveis);
		} else {
			for (Iterator iteratorColunaImoveis = colecaoColunaImoveis.iterator(); iteratorColunaImoveis.hasNext();) {

				TabelaColuna colunaImoveis = (TabelaColuna) iteratorColunaImoveis.next();
				for (Iterator iteratorColunaImoveisSelecionados = colecaoColunaImoveisSelecionados.iterator(); iteratorColunaImoveisSelecionados.hasNext();) {

					TabelaColuna colunaImoveisSelecionado = (TabelaColuna) iteratorColunaImoveisSelecionados.next();

					if (colunaImoveis.getId().compareTo(colunaImoveisSelecionado.getId()) == 0) {
						iteratorColunaImoveis.remove();
					}
				}
			}

			sessao.setAttribute("colecaoColunaImoveis", colecaoColunaImoveis);
		}
	}

	private void preencherCampoDescricao(FiltrarAlteracaoAtualizacaoCadastralActionForm form, FilterClassParameters parameters, HttpServletRequest httpServletRequest)
			throws Exception {

		Method getId = FiltrarAlteracaoAtualizacaoCadastralActionForm.class.getMethod("getId" + parameters.getFieldName());

		Method setId = FiltrarAlteracaoAtualizacaoCadastralActionForm.class.getMethod("setId" + parameters.getFieldName(), String.class);

		Method setNome = FiltrarAlteracaoAtualizacaoCadastralActionForm.class.getMethod("setNome" + parameters.getFieldName(), String.class);

		String idEntidade = (String) getId.invoke(form);
		DescriptorEntity entidade = pesquisarEntidade(idEntidade, parameters);

		if (entidade == null) {
			setId.invoke(form, "");
			setNome.invoke(form, parameters.getInvalidMessage());
			httpServletRequest.setAttribute(parameters.getFieldName() + "Inexistente", "true");
		} else {
			setNome.invoke(form, entidade.getDescricao());
		}
	}

	private DescriptorEntity pesquisarEntidade(String idLocalidade, FilterClassParameters parameters) {
		parameters.getFilter().adicionarParametro(new ParametroSimples(parameters.getFilter().ID, idLocalidade));
		parameters.getFilter().adicionarParametro(new ParametroSimples(parameters.getFilter().INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection colecaoPesquisa = Fachada.getInstancia().pesquisar(parameters.getFilter(), parameters.getEntity().getClass().getName());

		if (colecaoPesquisa == null || colecaoPesquisa.isEmpty()) {
			return null;
		} else {
			return (DescriptorEntity) Util.retonarObjetoDeColecao(colecaoPesquisa);
		}
	}
}
