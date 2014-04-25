package gcom.gui.cobranca;

import gcom.cobranca.CobrancaSituacaoMotivo;
import gcom.cobranca.CobrancaSituacaoTipo;
import gcom.cobranca.FiltroCobrancaSituacaoMotivo;
import gcom.cobranca.FiltroCobrancaSituacaoTipo;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Esta classe tem por finalidade exibir para o usu�rio a tela que receber� os
 * par�metros para realiza��o da inser��o da situa��o especial da cobran�a
 * 
 * @author S�vio Luiz
 * @date 17/03/2006
 */

public class ExibirSituacaoEspecialCobrancaInserirAction extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		// Seta o retorno
		ActionForward retorno = actionMapping
				.findForward("exibirSituacaoEspecialCobrancaInserir");

		// Obt�m a inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		SituacaoEspecialCobrancaActionForm situacaoEspecialCobrancaActionForm = (SituacaoEspecialCobrancaActionForm) actionForm;

		// Pesquisa Cobranca Situacao Tipo
		FiltroCobrancaSituacaoTipo filtroCobrancaSituacaoTipo = new FiltroCobrancaSituacaoTipo();
		Collection collectionCobrancaSituacaoTipo = fachada.pesquisar(
				filtroCobrancaSituacaoTipo, CobrancaSituacaoTipo.class
						.getName());
		
		httpServletRequest.setAttribute("collectionCobrancaSituacaoTipo",
				collectionCobrancaSituacaoTipo);
		
		
		if(situacaoEspecialCobrancaActionForm.getIdCobrancaSituacaoTipo()!=null){ 
				
				Iterator it = collectionCobrancaSituacaoTipo.iterator();
				
				while(it.hasNext()){
					
					CobrancaSituacaoTipo cobrancaSituacaoTipo =(CobrancaSituacaoTipo) it.next();
					
					if(situacaoEspecialCobrancaActionForm.getIdCobrancaSituacaoTipo().toString()
							.equals(cobrancaSituacaoTipo.getId().toString())){
						if(cobrancaSituacaoTipo.getIndicadorInformarDataFinal().toString()
								.equals(ConstantesSistema.INDICADOR_USO_ATIVO.toString())){
							
							situacaoEspecialCobrancaActionForm.setIndicadorInformarDataFimSituacao(
									cobrancaSituacaoTipo.getIndicadorInformarDataFinal().toString());
							
							httpServletRequest.setAttribute("informarDataFimSituacao",true);
						}else{
							situacaoEspecialCobrancaActionForm.setDataFimSituacao("");
						}
					}
				}
		}
		
		
		FiltroCobrancaSituacaoMotivo filtroCobrancaSituacaoMotivo = new FiltroCobrancaSituacaoMotivo();
		Collection collectionCobrancaSituacaoMotivo = fachada.pesquisar(
				filtroCobrancaSituacaoMotivo,
				CobrancaSituacaoMotivo.class.getName());
		httpServletRequest.setAttribute("collectionCobrancaSituacaoMotivo",
				collectionCobrancaSituacaoMotivo);

		if(situacaoEspecialCobrancaActionForm
				.getQuantidadeImoveisCOMSituacaoEspecialCobranca() != null){
			
			String COM = situacaoEspecialCobrancaActionForm
					.getQuantidadeImoveisCOMSituacaoEspecialCobranca();
		
			String SEM = situacaoEspecialCobrancaActionForm
					.getQuantidadeImoveisSEMSituacaoEspecialCobranca();
			String quantidadeDeImoveisAtualizados = Integer.toString(Integer
					.parseInt(COM)
					+ Integer.parseInt(SEM));
		
		if (quantidadeDeImoveisAtualizados.equals("0"))
			throw new ActionServletException(
					"atencao.imovel.sem.situacao.especial.cobranca", null,
					"");
		
			situacaoEspecialCobrancaActionForm
					.setQuantidadeDeImoveis(quantidadeDeImoveisAtualizados);
		}
		return retorno;
	}

}
