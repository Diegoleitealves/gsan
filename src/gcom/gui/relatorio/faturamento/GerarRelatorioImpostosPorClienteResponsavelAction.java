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
package gcom.gui.relatorio.faturamento;

import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.faturamento.conta.ImpostoDeduzidoHelper;
import gcom.gui.ActionServletException;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.faturamento.RelatorioImpostosPorClienteResponsavelBean;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.Util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Permite atualizar um contrato de demanda [UC0513] Manter Contrato de Demanda
 * 
 * @author Rafael Corr�a
 * @since 31/03/2006
 */

public class GerarRelatorioImpostosPorClienteResponsavelAction extends ExibidorProcessamentoTarefaRelatorio {
	
	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		
		// Seta o mapeamento de retorno
		ActionForward retorno = null;
		httpServletRequest.setAttribute("telaSucessoRelatorio",true);

		
		RelatorioImpostosPorClienteResponsavelActionForm form = (RelatorioImpostosPorClienteResponsavelActionForm) actionForm;
		
		Fachada fachada = Fachada.getInstancia();
		
		String tipoImposto = form.getIndicadorTipoImposto();
		
		String anoMes = form.getAnoMesReferencia();
		
		Integer anoMesTemp = Util.formatarMesAnoComBarraParaAnoMes(anoMes);
		
		SistemaParametro sistemaParametro = this.getFachada().pesquisarParametrosDoSistema();
		
		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");
		
		String relatorioTipo = form.getRelatorioTipo();
		
		Integer clienteID = null;
		
		if ( form.getClienteID() != null && !form.getClienteID().equals("") ){
			
			clienteID = Util.converterStringParaInteger(form.getClienteID());
			
		}
		
		
		if (tipoRelatorio == null) {
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}
		
		if(sistemaParametro.getAnoMesFaturamento().intValue() < anoMesTemp.intValue()){
			throw new ActionServletException("atencao.mes_ano.faturamento.inferior", 
					null,""+sistemaParametro.getAnoMesFaturamento());
		}
		
		
		if(relatorioTipo != null && tipoRelatorio != null){
			RelatorioImpostosPorClienteResponsavel relatorioImpCliResponsavel = 
				new RelatorioImpostosPorClienteResponsavel(
						(Usuario)(httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));
			
			relatorioImpCliResponsavel.addParametro("tipoFormatoRelatorio", Integer.parseInt(tipoRelatorio));
			relatorioImpCliResponsavel.addParametro("anoMes",anoMes);
			
			
			if(relatorioTipo.equals(ConstantesSistema.SINTETICO+"")){
				relatorioTipo = "SINTETICO";
				relatorioImpCliResponsavel.addParametro("tipoRelatorio", "SINTETICO");	
			}else if(relatorioTipo.equals(ConstantesSistema.ANALITICO+"")){
				relatorioTipo = "ANALITICO";
				relatorioImpCliResponsavel.addParametro("tipoRelatorio", "ANALITICO");
			}
			
			Collection<ImpostoDeduzidoHelper>helpersRelatorio = null;
			
			//Caso o relat�rio a ser gerado seja do tipo faturamento
			if(tipoImposto.equals("1")){
				//pega todas as faturas com seus respectivos impostos e clientes
				helpersRelatorio = fachada.pesquisarImpostosPorClienteResponsavelFederal( 
																	anoMesTemp, clienteID, relatorioTipo);
				relatorioImpCliResponsavel.addParametro("tipoImposto", "DO FATURAMENTO");
			//Caso o relat�rio a ser gerado seja do tipo arrecada��o	
			}else if(tipoImposto.equals("2")){
				helpersRelatorio = fachada.pesquisarImpostosArrecadacaoClienteResponsavelFederal(
																	anoMesTemp, clienteID, relatorioTipo);
				relatorioImpCliResponsavel.addParametro("tipoImposto", "DA ARRECADA��O");
			}
			//passa o tamanho da quantidade de registros
			relatorioImpCliResponsavel.addParametro("tamanhoColecao", helpersRelatorio.size());
			
			//Inicializa o bean do relatorio
			List<RelatorioImpostosPorClienteResponsavelBean> relatorioBeans = new ArrayList();
			
			if(relatorioTipo.equalsIgnoreCase("sintetico") ){
				
				if(helpersRelatorio != null && !helpersRelatorio.isEmpty()){
					
					for (Iterator iter = helpersRelatorio.iterator(); iter.hasNext();) {
						
						ImpostoDeduzidoHelper elementoHelper = (ImpostoDeduzidoHelper) iter.next();
										
						//cria o novo bean - cada bean representa um imposto por cliente e por fatura no relatorio
						RelatorioImpostosPorClienteResponsavelBean relatorioBean = 
							new RelatorioImpostosPorClienteResponsavelBean();      
						
						if(elementoHelper.getIdCliente() != null && elementoHelper.getIdCliente() != null){
							relatorioBean.setClienteIdNome(elementoHelper.getIdCliente() + " - "+ 
									elementoHelper.getNomeCliente());							   
						}							
						
						if(elementoHelper.getCnpjCliente() != null){
							relatorioBean.setCnpjCliente(elementoHelper.getCnpjCliente());
						}
						
						if(elementoHelper.getValorFatura() != null){
							relatorioBean.setValorFatura(Util.formataBigDecimal(elementoHelper.getValorFatura(),2,true));
						}
															
						if(elementoHelper.getIdImpostoTipo() != null){
							relatorioBean.setIdImpostoTipo(elementoHelper.getIdImpostoTipo());																		
						}
						
						if(elementoHelper.getDescricaoImposto() != null){
							relatorioBean.setDescricaoImposto(elementoHelper.getDescricaoImposto());
						}
						
						if(elementoHelper.getPercentualAliquota() != null){										
							relatorioBean.setPercentualAliquota(Util.formataBigDecimal(elementoHelper.getPercentualAliquota(),2,true));										
						}
						
						if(elementoHelper.getValor() != null){
							relatorioBean.setValorImposto(Util.formataBigDecimal(elementoHelper.getValor(),2,true));
						}								
						
						relatorioBeans.add(relatorioBean);	
					}				
				}
				
			}else if(relatorioTipo.equalsIgnoreCase("analitico")){
				
				if(helpersRelatorio != null && !helpersRelatorio.isEmpty()){
					
					for (Iterator iter = helpersRelatorio.iterator(); iter.hasNext();) {
						
						ImpostoDeduzidoHelper elementoHelper = (ImpostoDeduzidoHelper) iter.next();

						RelatorioImpostosPorClienteResponsavelBean relatorioBean = 
							new RelatorioImpostosPorClienteResponsavelBean();     
						
						if(elementoHelper.getIdCliente() != null && elementoHelper.getIdCliente() != null){
							relatorioBean.setClienteIdNome(elementoHelper.getIdCliente() + " - "+ 
									elementoHelper.getNomeCliente());							   
						}							
						
						if(elementoHelper.getCnpjCliente() != null){
							relatorioBean.setCnpjCliente(elementoHelper.getCnpjCliente());
						}
						
						if(elementoHelper.getValorFatura() != null){
							relatorioBean.setValorFatura(Util.formataBigDecimal(elementoHelper.getValorFatura(),2,true));
						}
						
						if(elementoHelper.getIdImpostoTipo() != null){
							relatorioBean.setIdImpostoTipo(elementoHelper.getIdImpostoTipo());																		
						}
						
						if(elementoHelper.getDescricaoImposto() != null){
							relatorioBean.setDescricaoImposto(elementoHelper.getDescricaoImposto());
						}
						
						if(elementoHelper.getPercentualAliquota() != null){										
							relatorioBean.setPercentualAliquota(Util.formataBigDecimal(elementoHelper.getPercentualAliquota(),2,true));										
						}
						
						if(elementoHelper.getValor() != null){
							relatorioBean.setValorImposto(Util.formataBigDecimal(elementoHelper.getValor(),2,true));
						}
						
						if ( elementoHelper.getIdImovel() != null ){
							
							relatorioBean.setImovelID( elementoHelper.getIdImovel().toString() );
							
						}
						relatorioBeans.add(relatorioBean);		
					}
				}
			}
				
			relatorioImpCliResponsavel.addParametro("beansRelatorio", relatorioBeans);
			
			retorno = processarExibicaoRelatorio(relatorioImpCliResponsavel,
					tipoRelatorio, httpServletRequest, httpServletResponse,
					actionMapping);
		}
		
		return retorno;
		
	}
	
}
