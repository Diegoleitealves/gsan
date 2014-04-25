package gcom.gui.financeiro;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Gerar integra��o para contabilidade.
 *
 * @author Pedro Alexandre
 * @date 28/05/2007
 */
public class GerarIntegracaoContabilidadeAction extends GcomAction {
	/**
	 * Description of the Method
	 * 
	 * @param actionMapping
	 *            Description of the Parameter
	 * @param actionForm
	 *            Description of the Parameter
	 * @param httpServletRequest
	 *            Description of the Parameter
	 * @param httpServletResponse
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, 
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		// Seta o mapeamento de retorno para a tela de sucesso.
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		GerarIntegracaoContabilidadeActionForm gerarIntegracaoContabilidadeActionForm = (GerarIntegracaoContabilidadeActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		//recupera os par�metros informados pelo usu�rio.
		String idLancamentoOrigem = gerarIntegracaoContabilidadeActionForm.getIdLacamentoOrigem();
		String dataLancamento = gerarIntegracaoContabilidadeActionForm.getDataLancamento();
		
		//verifica se a origem do lan�amento foi informada.
		if(idLancamentoOrigem == null || idLancamentoOrigem.trim().equals("")){
			throw new ActionServletException("atencao.naoinformado",null, "Lan�amento Origem");
		}
		
		/*
		 * Caso a data n�o tenha sido informada levanta a exce��o para o usu�rio.
		 */
		if(dataLancamento == null || dataLancamento.trim().equals("")){
			throw new ActionServletException("atencao.naoinformado",null, "Data de Lan�amento");
		}else{
			//[FS0002 - Validar data do lan�amento]
			//cria o formato da data
	        SimpleDateFormat dataFormato = new SimpleDateFormat("dd/MM/yyyy");

	        try {
	        	//tenta converter a data de lan�amento
	            dataFormato.parse(dataLancamento);

	            //se n�o conseguir converter
	        } catch (ParseException ex) {
	        	//levanta a exce��o para a pr�xima camada
	        	throw new ActionServletException("atencao.data_pagamento_invalida");
	        }
		}
		
		//recupera o m�s e o ano informados
        String mes = dataLancamento.substring(3, 5);
        String ano = dataLancamento.substring(6, 10);
        String anoMes = ano + mes;
        
        //chama o met�do para gerar o txt de integra��o para contabilidade 
        fachada.gerarIntegracaoContabilidade(idLancamentoOrigem, anoMes, dataLancamento);
		
	
		// montando p�gina de sucesso
		montarPaginaSucesso(httpServletRequest, "Gerando a Integra��o para a Contabilidade.",
				"Gerar Integra��o para a Contabilidade", "/exibirGerarIntegracaoContabilidadeAction.do");

		return retorno;
	}
}
