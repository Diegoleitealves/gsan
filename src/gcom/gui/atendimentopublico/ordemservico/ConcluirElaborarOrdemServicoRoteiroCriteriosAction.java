package gcom.gui.atendimentopublico.ordemservico;

import gcom.atendimentopublico.ordemservico.OrdemServicoProgramacao;
import gcom.atendimentopublico.ordemservico.ProgramacaoRoteiro;
import gcom.atendimentopublico.ordemservico.bean.OSProgramacaoHelper;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.Util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ConcluirElaborarOrdemServicoRoteiroCriteriosAction extends GcomAction {
	
	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		// Seta o mapeamento de retorno
		ActionForward retorno = 
			actionMapping.findForward("telaSucesso");
		
		ElaborarOrdemServicoRoteiroCriteriosActionForm elaborarActionForm = 
			(ElaborarOrdemServicoRoteiroCriteriosActionForm) actionForm;

		String data = elaborarActionForm.getDataRoteiro();
		Date dataRoteiro = Util.converteStringParaDate(data);
		
		
		HttpSession sessao = httpServletRequest.getSession(false);
		
		HashMap mapOsProgramacaoHelper = (HashMap) sessao.getAttribute("mapOsProgramacaoHelper");
		
		Collection colecao = mapOsProgramacaoHelper.values();
		Collection colecaoNova = this.removeOrdemServicoProgramacaoJaExistente(colecao,dataRoteiro);
		
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		
		Fachada.getInstancia().elaborarRoteiro(colecaoNova, usuario);
		
		//Monta a p�gina de sucesso
		montarPaginaSucesso(httpServletRequest,
			"Elabora��o do Roteiro de Programa��o de Ordens de Servi�os efetuado com sucesso",
			"Efetuar outra Elabora��o do Roteiro",
			"exibirElaborarOrdemServicoRoteiroCriteriosAction.do?menu=sim&dataRoteiro="+elaborarActionForm.getDataRoteiro());
		
		return retorno;
	}
	
	/**
	 * Remove as ordem de servico programacao que j� existe no banco de dados
	 * so existe porque possui id
	 * 
	 * @author Rafael Pinto
	 * @date 17/08/2006
	 *
	 * @param colecao de OS ProgramacaoHelper
	 * @return Collection<OSProgramacaoHelper>
	 */
	
	private Collection<OSProgramacaoHelper> removeOrdemServicoProgramacaoJaExistente(Collection colecao,Date dataRoteiroElaboracao){
		Collection<OSProgramacaoHelper> retorno = new ArrayList();
		
		Iterator itera = colecao.iterator();
		
		while (itera.hasNext()) {
			
			Collection colecaoOSProgramacaoHelper = (Collection) itera.next();

			Iterator iteraOsProgramacao = colecaoOSProgramacaoHelper.iterator();
			
			short sequencial = 0;
			
			while (iteraOsProgramacao.hasNext()) {

				OSProgramacaoHelper osProgramacaoHelper = (OSProgramacaoHelper) iteraOsProgramacao.next();
				
				OrdemServicoProgramacao ordemServicoProgramacao = 
					osProgramacaoHelper.getOrdemServicoProgramacao();
				
				if(ordemServicoProgramacao.getId() == null || 
					ordemServicoProgramacao.getId().intValue() == ConstantesSistema.NUMERO_NAO_INFORMADO){
					
					Date dataRoteiro = ordemServicoProgramacao.getProgramacaoRoteiro().getDataRoteiro();
					
					//Caso tenha data diferentes � necessario incluir 2 programac�es: 
					if(Util.compararData(dataRoteiroElaboracao,dataRoteiro) != 0){
						
						short sequencialAnterior = ordemServicoProgramacao.getNnSequencialProgramacao();
						
						sequencial++;
						
						// 1.Ordem de servi�o programa��o com a data final da programa��o
						ordemServicoProgramacao.setNnSequencialProgramacao(sequencial);
						retorno.add(osProgramacaoHelper);
						
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(dataRoteiroElaboracao);

						//Vai adcionando programa��o at� a data Final de Programacao
						//Ex: dataRoteiroElaboracao = 10/01 e dataRoteiro(DataFinalProgramacao) = 12/01
						//Ex: 10/01 at� 12/01
						while(Util.compararData(calendar.getTime(),dataRoteiro) == -1){
							
							// 2.Ordem de servi�o programa��o com a data do roteiro da elabora��o
							ProgramacaoRoteiro programacaoRoteiro = 
								new ProgramacaoRoteiro(
									ordemServicoProgramacao.getProgramacaoRoteiro().getDataRoteiro(),
									ordemServicoProgramacao.getProgramacaoRoteiro().getUltimaAlteracao(),
									ordemServicoProgramacao.getProgramacaoRoteiro().getUnidadeOrganizacional() );
							
							programacaoRoteiro.setDataRoteiro(calendar.getTime());

							OSProgramacaoHelper helper = new OSProgramacaoHelper();

							OrdemServicoProgramacao ordemServicoProgramacaoNova = 
								new OrdemServicoProgramacao(
									ordemServicoProgramacao.getId(),
									sequencialAnterior,
									ordemServicoProgramacao.getIndicadorAtivo(),
									ordemServicoProgramacao.getIndicadorEquipePrincipal(),
									ordemServicoProgramacao.getUltimaAlteracao(),
									ordemServicoProgramacao.getEquipe(),
									ordemServicoProgramacao.getUsuarioProgramacao(),
									ordemServicoProgramacao.getUsuarioFechamento(),
									programacaoRoteiro,
									ordemServicoProgramacao.getOsProgramNaoEncerMotivo(),
									ordemServicoProgramacao.getOrdemServico() );

							helper.setOrdemServicoProgramacao(ordemServicoProgramacaoNova);
							retorno.add(helper);
							
							//Adciona um dia a mais na data
							calendar.add(Calendar.DAY_OF_MONTH,1);
						}

					}else{
						retorno.add(osProgramacaoHelper);
					}
					

				}
				
			}
			
		}
		return retorno;
	}
}
