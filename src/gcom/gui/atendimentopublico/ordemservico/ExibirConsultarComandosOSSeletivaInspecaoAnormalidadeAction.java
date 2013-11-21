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
package gcom.gui.atendimentopublico.ordemservico;

import gcom.atendimentopublico.ordemservico.ComandoOrdemSeletiva;
import gcom.atendimentopublico.ordemservico.ConsultarComandosOSSeletivaInspecaoAnormalidadeHelper;
import gcom.batch.Processo;
import gcom.cadastro.empresa.Empresa;
import gcom.cadastro.empresa.FiltroEmpresa;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC1193] Consultar Comandos de OS Seletiva de Inspe��o de Anormalidade
 * 
 * @author Vivianne Sousa
 * @since 11/07/2011
 */
public class ExibirConsultarComandosOSSeletivaInspecaoAnormalidadeAction extends GcomAction {

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
	
		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping
				.findForward("exibirConsultarComandosOSSeletivaInspecaoAnormalidade");
		
		String pagina = httpServletRequest.getParameter("page.offset");
	
		ConsultarComandosOSSeletivaInspecaoAnormalidadeActionForm form = (ConsultarComandosOSSeletivaInspecaoAnormalidadeActionForm) actionForm;
	
		Fachada fachada = Fachada.getInstancia();
	
		HttpSession sessao = httpServletRequest.getSession(false);
		
		this.pesquisarCampoEnter(httpServletRequest, form, fachada);
	
		if (httpServletRequest.getParameter("limpar") != null 
				&& httpServletRequest.getParameter("limpar").equalsIgnoreCase("sim")) {
	
			sessao.removeAttribute("colecaoConsultarComandosOSHelper");
			
		}
	
		if (httpServletRequest.getParameter("selecionarComandos") != null || pagina!=null) {
			if(pagina==null){
				pagina = "0";
			}
			
			retorno = this.pesquisarComandosOS(httpServletRequest, form, fachada,
					sessao,pagina,retorno);
		}
	
		if (httpServletRequest.getParameter("acao") != null 
				&& httpServletRequest.getParameter("acao").equalsIgnoreCase("gerarTxtComando")) {
			Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");
			
			ComandoOrdemSeletiva  comandoOrdemSeletiva = fachada.pesquisarComandoOSSeletiva(new Integer(form.getIdRegistro()));
			if (comandoOrdemSeletiva.getDataEncerramento() != null) {
				throw new ActionServletException("atencao.comando.ja_encerrado", 
						null, "Comandos de OS Seletiva de Inspe��o de Anormalidade");
			} 
			
			Integer qtdeOsNaoPendenteFazParteComando = fachada.pesquisaOrdemServicoNaoPendenteFazParteComando(new Integer(form.getIdRegistro()));
			if(qtdeOsNaoPendenteFazParteComando != null && qtdeOsNaoPendenteFazParteComando.intValue() > 0){
				throw new ActionServletException("atencao.nao_e_possivel_gerar_txt_comando");
			}
			
			Map parametros = new HashMap();
			parametros.put("idComandoOrdemSeletiva",new Integer(form.getIdRegistro()));
			parametros.put("qtdAnormalidadesConsecutivas",comandoOrdemSeletiva.getQuantidadeConsecutivaAnormalidade());
			Fachada.getInstancia().inserirProcessoIniciadoParametrosLivresAguardandoAutorizacao(
					parametros, 
	         		Processo.GERAR_TXT_OS_INSPECAO_ANORMALIDADE ,
	         		usuarioLogado);
				
			retorno = actionMapping.findForward("telaSucesso");
			//montando p�gina de sucesso
			montarPaginaSucesso(httpServletRequest,
				"Gera��o de Txt do Comando  de OS Seletiva de Inspe��o de Anormalidade Enviado para Processamento", 
				"Voltar",
				"exibirConsultarComandosOSSeletivaInspecaoAnormalidadeAction.do?menu=sim");
		}
		
		return retorno;
	}
	
	private void pesquisarCampoEnter(HttpServletRequest httpServletRequest,
			ConsultarComandosOSSeletivaInspecaoAnormalidadeActionForm form,
			Fachada fachada) {
	
		String idEmpresa = form.getIdEmpresa();
	
		// Pesquisa a empresa
		if (idEmpresa != null && !idEmpresa.trim().equals("")) {
	
			FiltroEmpresa filtroEmpresa = new FiltroEmpresa();
			filtroEmpresa.adicionarParametro(new ParametroSimples(
					FiltroEmpresa.ID, idEmpresa));
	
			Collection colecaoEmpresa = fachada.pesquisar(filtroEmpresa,
					Empresa.class.getName());
	
			if (colecaoEmpresa != null && !colecaoEmpresa.isEmpty()) {
				Empresa empresa = (Empresa) Util
						.retonarObjetoDeColecao(colecaoEmpresa);
				form.setIdEmpresa(empresa.getId().toString());
				form.setNomeEmpresa(empresa.getDescricao());
				httpServletRequest.setAttribute("nomeCampo", "idEmpresa");
			} else {
				form.setIdEmpresa("");
				form.setNomeEmpresa("EMPRESA INEXISTENTE");
	
				httpServletRequest.setAttribute("empresaInexistente", true);
				httpServletRequest.setAttribute("nomeCampo", "idEmpresa");
			}
	
		} else {
			form.setNomeEmpresa("");
		}
	
	}
	
	private ActionForward pesquisarComandosOS(HttpServletRequest httpServletRequest,
			ConsultarComandosOSSeletivaInspecaoAnormalidadeActionForm form,
			Fachada fachada, HttpSession sessao, String pagina, ActionForward retorno) {

		String idEmpresa = form.getIdEmpresa();
		
		ActionForward retorno2 = new ActionForward();

		String periodoExecucaoInicial = form.getPeriodoExecucaoInicial();

		String periodoExecucaoFinal = form.getPeriodoExecucaoFinal();

		Date execucaoInicial = null;

		Date execucaoFinal = null;

		// Pesquisa a empresa
		if (idEmpresa != null && !idEmpresa.trim().equals("")) {

			FiltroEmpresa filtroEmpresa = new FiltroEmpresa();
			filtroEmpresa.adicionarParametro(new ParametroSimples(FiltroEmpresa.ID, idEmpresa));

			Collection colecaoEmpresa = fachada.pesquisar(filtroEmpresa,Empresa.class.getName());

			if (Util.isVazioOrNulo(colecaoEmpresa)) {
				throw new ActionServletException("atencao.empresa.inexistente");
			}
		}else{
			throw new ActionServletException("atencao.campo_selecionado.obrigatorio", null, "Empresa");
		}

		if (periodoExecucaoFinal != null && !periodoExecucaoFinal.equals("")
			&& periodoExecucaoInicial != null && !periodoExecucaoInicial.equals("")) {

			execucaoInicial = Util.converteStringParaDate(periodoExecucaoInicial);
			execucaoFinal = Util.converteStringParaDate(periodoExecucaoFinal);

			if (execucaoInicial.compareTo(execucaoFinal) > 0) {
				throw new ActionServletException("atencao.data_inicial_periodo_execucao.posterior.data_final_periodo_execucao");
			}

		}
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		int quantidadeRegistros = 10;
		Integer totalRegistros = fachada.pesquisarDadosComandoOSSeletivaCount(
				new Integer(idEmpresa), execucaoInicial, execucaoFinal);

		retorno2 = this.controlarPaginacao(httpServletRequest, retorno,	totalRegistros);
		
		Collection<ConsultarComandosOSSeletivaInspecaoAnormalidadeHelper> colecaoConsultarComandosOSHelper = fachada
		.pesquisarDadosComandoOSSeletivaResumido(new Integer(idEmpresa),execucaoInicial, execucaoFinal,
				(Integer)httpServletRequest.getAttribute("numeroPaginasPesquisa"),quantidadeRegistros,
				sistemaParametro.getQtdeDiasValidadeOSAnormalidadeFiscalizacao());
		
		if(colecaoConsultarComandosOSHelper != null && !colecaoConsultarComandosOSHelper.isEmpty()){
			sessao.setAttribute("dataInicial",execucaoInicial);	
			sessao.setAttribute("dataFinal",execucaoFinal);	
			sessao.setAttribute("colecaoConsultarComandosOSHelper",	colecaoConsultarComandosOSHelper);
		}else{
			throw new ActionServletException("atencao.pesquisa.nenhumresultado");
		}

		return retorno2;
	}
}
