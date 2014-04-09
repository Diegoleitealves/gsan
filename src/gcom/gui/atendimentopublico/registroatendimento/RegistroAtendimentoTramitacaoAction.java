package gcom.gui.atendimentopublico.registroatendimento;

import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.atendimentopublico.ordemservico.OrdemServicoPavimento;
import gcom.atendimentopublico.registroatendimento.Tramite;
import gcom.cadastro.unidade.UnidadeTipo;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;

import java.util.ArrayList;
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
 * [UC0503]Tramitar Conjunto de Registro de Atendimento
 * 
 * @author Ana Maria, Pedro Alexandre		
 * @date 16/01/2007, 08/01/2008
 */
public class RegistroAtendimentoTramitacaoAction extends GcomAction {
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
		
		// Seta a a��o de retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");
		
		// Obt�m a sess�o
		HttpSession sessao = httpServletRequest.getSession(false);
		
		sessao.setAttribute("tramitarConjunto","sim");
		
		Fachada fachada = Fachada.getInstancia();
		
		Collection<Tramite> tramites = (Collection)sessao.getAttribute("tramites");
		
		/* alterado por pedro alexandre */
		//Verifica se o usu�rio apertou o bot�o na p�gian de tramitar
		String primeiraVez = httpServletRequest.getParameter("primeiraVez");

		//Caso seja a primeira vez tramita o RA, caso contr�rio o RA n�o
		//vai ser mais tramitado
		if(primeiraVez != null && primeiraVez.equals("ok")){
			if(tramites == null || tramites.isEmpty()){
	            throw new ActionServletException(
	            "atencao.registros_tramitacao.nao_selecionados");
			}
		}	
				
		//Recupera a cole��o de ordem de servi�o que j� foram processadas		
		Collection colecaoOrdemServicoJaTratada = (Collection)sessao.getAttribute("colecaoOrdemServicoJaTratada");
		
		Tramite tramite = (Tramite) Util.retonarObjetoDeColecao(tramites);
		
		//Cria os indicadores de centralizadora e terceira		
		boolean flagCentralizadora = false;
		boolean flagTerceira = false;

		//Caso a unidade organizacional origem seja uma centralizadora
		if(tramite.getUnidadeOrganizacionalOrigem().getUnidadeTipo().getId().equals(UnidadeTipo.UNIDADE_TIPO_CENTRALIZADORA_ID)){
			flagCentralizadora = true;
		}

		//Caso a unidade organizacional destino seja uma terceira		
		if(tramite.getUnidadeOrganizacionalDestino().getUnidadeTipo().getId().equals(UnidadeTipo.UNIDADE_TIPO_TERCERIZADO_ID)){
			flagTerceira = true;
		}

		Collection colecaoOrdemServicoMovimento = new ArrayList();
		Collection colecaoOrdemServicoPavimento = new ArrayList();
		//Exportar as ordems de servi�o		
		if(flagCentralizadora && flagTerceira){
			Collection<OrdemServico> colecaoOrdemServicoPrestadora = new ArrayList();
			if(primeiraVez != null && primeiraVez.equals("ok")){
				//[UC0720 - Exportar Ordem Servi�o Prestadoras]
//				colecaoOrdemServicoPrestadora = fachada.exportarOrdemServicoPrestadoras(tramites);
//				sessao.setAttribute("colecaoOrdemServicoPrestadora",colecaoOrdemServicoPrestadora);
				
				Map dadosExportar =  fachada.exportarOrdemServicoPrestadoras(tramites);
				colecaoOrdemServicoPrestadora = (Collection)dadosExportar.get("colecaoOrdemServico");
				colecaoOrdemServicoMovimento = (Collection)dadosExportar.get("colecaoOrdemServicoMovimento");
				
				sessao.setAttribute("colecaoOrdemServicoMovimento", colecaoOrdemServicoMovimento);
				sessao.setAttribute("colecaoOrdemServicoPrestadora",colecaoOrdemServicoPrestadora);
				
			}else{
				colecaoOrdemServicoPrestadora = (Collection)sessao.getAttribute("colecaoOrdemServicoPrestadora");
				colecaoOrdemServicoMovimento = (Collection)sessao.getAttribute("colecaoOrdemServicoMovimento");
			}
			
			if(colecaoOrdemServicoJaTratada!= null && !colecaoOrdemServicoJaTratada.isEmpty()){
				colecaoOrdemServicoPrestadora.removeAll(colecaoOrdemServicoJaTratada);
			}

			//Caso ainda tenha ordem de servi�o de prestadora para ser processada.			
			if(colecaoOrdemServicoPrestadora!= null && !colecaoOrdemServicoPrestadora.isEmpty()){
				for(OrdemServico ordemServico : colecaoOrdemServicoPrestadora){

					//Caso a ordem de servi�o tenha im�vel, exibir o popup para 
					//inserir os dados do paviemnto 
					//Caso contr�rio inserir a ordem de servi�o pavimento 
					//com os dados de pavimento nulos.
					if(ordemServico.getImovel() != null){
						sessao.setAttribute("ordemServico",ordemServico);
						sessao.setAttribute("imovel",ordemServico.getImovel());
						httpServletRequest.setAttribute("indicadorPavimento","sim");
						retorno = actionMapping.findForward("tramitacaoRegistroAtendimento");
						return retorno;
					}else{
						//Inseri a OrdemServicoPavimento com os dados de pavimento 
						OrdemServicoPavimento ordemServicoPavimento = new OrdemServicoPavimento();
						ordemServicoPavimento.setOrdemServico(ordemServico);
						ordemServicoPavimento.setPavimentoRua(null);
						ordemServicoPavimento.setAreaPavimentoRua(null);
						ordemServicoPavimento.setPavimentoCalcada(null);
						ordemServicoPavimento.setAreaPavimentoCalcada(null);
						ordemServicoPavimento.setPavimentoRuaRetorno(null);
						ordemServicoPavimento.setAreaPavimentoRuaRetorno(null);
						ordemServicoPavimento.setPavimentoCalcadaRetorno(null);
						ordemServicoPavimento.setAreaPavimentoCalcadaRetorno(null);
						ordemServicoPavimento.setDataGeracao(new Date());
						
//						fachada.inserirOrdemServicoPavimento(ordemServicoPavimento);
						
						if(sessao.getAttribute("colecaoOrdemServicoPavimento") != null){
							colecaoOrdemServicoPavimento = (Collection)sessao.getAttribute("colecaoOrdemServicoPavimento");
						}	
						
						colecaoOrdemServicoPavimento.add(ordemServicoPavimento);
						
						sessao.setAttribute("colecaoOrdemServicoPavimento", colecaoOrdemServicoPavimento);
					}
				}
			}
		}
				
		//s� vai tramitar depois de exibir o popup de dados do pavimento
		//[SB0003]Incluir o Tramite
//		fachada.tramitarConjuntoRA(tramites);
		
		fachada.tramitarConjuntoRAExportandoOSPrestadoras(
				tramites, colecaoOrdemServicoPavimento, colecaoOrdemServicoMovimento);
		
		
		//[SB0001] � Verificar RA de urg�ncia
		if(tramites != null){
			//[SB0034] � Verificar RA de urg�ncia
			for(Tramite t : tramites){
				
				//Caso o STEP_ID da RA correspondente possua SOLICITACAO_TIPO_ESPECIFICACAO.ICURGENCIA=1
				if(this.getFachada().verificarRegistroAtendimentoUrgencia(t.getRegistroAtendimento().getId()) > 0){

					this.getFachada().atualizarUsuarioVisualizacaoRaUrgencia(t.getRegistroAtendimento().getId(), t.getUnidadeOrganizacionalOrigem().getId(), null, 1, null);			
			
					//caso j� exista relacionamento entre a RA e Unidade, atualizar usuarios da Unidade com tramitacao e visualizacao = 2.
					if(this.getFachada().verificarUsuariosRegistroAtendimentoUrgencia(t.getRegistroAtendimento().getId(), t.getUnidadeOrganizacionalDestino().getId()) > 0){
						
						this.getFachada().atualizarUsuarioVisualizacaoRaUrgencia(t.getRegistroAtendimento().getId(), t.getUnidadeOrganizacionalDestino().getId(), null, 2, 2);
					     
					//caso n�o exista relacionamento entre a RA e Unidade, insere nova RA.     
					}else{
						
						getFachada().inserirUsuarioVisualizacaoRaUrgencia(
								t.getRegistroAtendimento().getId(),	ConstantesSistema.NAO);
					}

				}

			}

		}


		sessao.removeAttribute("tramites");
		sessao.removeAttribute("colecaoRAHelper");
		
		if(retorno.getName().equalsIgnoreCase("telaSucesso")){
			//Monta a p�gina de sucesso
			montarPaginaSucesso(httpServletRequest, "Tramita��o do Conjunto de Registro de Atendimento efetuada com sucesso.", 
					"Tramitar outro Conjunto de Registro de Atendimento", 
					"exibirFiltrarRegistroAtendimentoTramitacaoAction.do?menu=sim");
		}
		return retorno;
	}
}
