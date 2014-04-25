package gcom.gui.cobranca;

import gcom.cobranca.DocumentosReceberFaixaDiasVencidos;
import gcom.cobranca.FiltroDocumentosReceberFaixaDiasVencidos;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import java.util.Collection;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Hugo Leonardo
 * 
 */
public class AtualizarFaixaDiasVencidosDocumentosReceberAction extends GcomAction {
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

		// Pega o action form
		AtualizarFaixaDiasVencidosDocumentosReceberActionForm form = (AtualizarFaixaDiasVencidosDocumentosReceberActionForm) actionForm;

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Obt�m a inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		// Obt�m a sess�o
		HttpSession sessao = httpServletRequest.getSession(false);

		// Recupera o ponto de coleta da sess�o
		DocumentosReceberFaixaDiasVencidos documentosReceberFaixaDiasVencidos = 
				(DocumentosReceberFaixaDiasVencidos) sessao.getAttribute("documentosReceberFaixaDiasVencidos");

		Date timeStamp = (Date)sessao.getAttribute("timeStamp");
		
		
		// Atualiza a tabela auxiliar com os dados inseridos pelo usu�rio
		// A data de �ltima altera��o n�o � alterada, pois ser� usada na
		// verifica��o de atualiza��o

		String descricaoFaixa = "";
		
		if (!form.getDescricaoFaixa().equals(documentosReceberFaixaDiasVencidos.getDescricaoFaixa())) {
			
			descricaoFaixa = form.getDescricaoFaixa();

			documentosReceberFaixaDiasVencidos.setDescricaoFaixa(descricaoFaixa);
		}
		
		Integer valorInicialFaixa = form.getValorInicialFaixa();
		
		if (!form.getValorInicialFaixa().equals(documentosReceberFaixaDiasVencidos.getValorInicialFaixa())) {
				
			documentosReceberFaixaDiasVencidos.setValorInicialFaixa(valorInicialFaixa);
		}
		
		Integer valorFinalFaixa = form.getValorFinalFaixa();
		if (!form.getValorFinalFaixa().equals(documentosReceberFaixaDiasVencidos.getValorFinalFaixa())) {
			
			documentosReceberFaixaDiasVencidos.setValorFinalFaixa(valorFinalFaixa);
			
			if (valorFinalFaixa != null					
					&& valorFinalFaixa != null) {
				if (valorInicialFaixa.compareTo(valorFinalFaixa) > 0) {
					throw new ActionServletException("atencao.valor_faixa_final_menor_valor_inicial_faixa");
				}
			}
		}
		
		if (form.getIndicadorUso() != null) {
			documentosReceberFaixaDiasVencidos.setIndicadorUso(new Short(form
					.getIndicadorUso()));
		}

		// Filtro da Descri��o
		FiltroDocumentosReceberFaixaDiasVencidos filtroDescricao = 
			new FiltroDocumentosReceberFaixaDiasVencidos();

		filtroDescricao
				.adicionarParametro(new ParametroSimples(
						FiltroDocumentosReceberFaixaDiasVencidos.DESCRICAO_FAIXA,
						documentosReceberFaixaDiasVencidos.getDescricaoFaixa()));

		// Collection por descri��o
		Collection colecaoDocumentosDescricao = fachada.pesquisar(
				filtroDescricao, DocumentosReceberFaixaDiasVencidos.class.getName());
		
		String auxDescricaoFaixa  = (String)sessao.getAttribute("descricaoFaixa");
		if(!auxDescricaoFaixa.equals(form.getDescricaoFaixa())){
			
			if (colecaoDocumentosDescricao != null && !colecaoDocumentosDescricao.isEmpty()) {
				DocumentosReceberFaixaDiasVencidos doc = (DocumentosReceberFaixaDiasVencidos) Util.retonarObjetoDeColecao(colecaoDocumentosDescricao);
				
				throw new ActionServletException(
						"atencao.verificar_descricao_faixa",doc.getDescricaoFaixa());
			}
		}
		
		Integer auxValorFaixaInicial  = (Integer)sessao.getAttribute("valorFaixaInicial");
		Integer auxValorFaixaFinal  = (Integer)sessao.getAttribute("valorFaixaFinal");
		
		// FS0003 - Verificar pr�-exist�ncia de faixa contendo o valor
		if(!auxValorFaixaInicial.equals(form.getValorInicialFaixa())){
			fachada.verificarExistenciaFaixaInicial(valorInicialFaixa);
		}
		if(!auxValorFaixaFinal.equals(form.getValorFinalFaixa())){
			fachada.verificarExistenciaFaixaFinal(valorFinalFaixa);
		}
		
		// FS0002 - Atualiza��o realizada por outro usu�rio.
		if(!timeStamp.equals(documentosReceberFaixaDiasVencidos.getUltimaAlteracao()) ){
			throw new ActionServletException("atencao.atualizacao_realizada_por_outro_usuario");
		}
		
		// Atualiza os dados
		fachada.atualizar(documentosReceberFaixaDiasVencidos);

		// Monta a p�gina de sucesso
		if (retorno.getName().equalsIgnoreCase("telaSucesso")) {

			montarPaginaSucesso(
					httpServletRequest,"Faixa atualizada com sucesso.",
					"Realizar outra Manuten��o de Faixa de Dias Vencidos para Documentos a Receber."
							+ ((String) sessao.getAttribute("titulo")),"exibirManterFaixaDiasVencidosDocumentosReceberAction.do?menu=sim");

		}

		// devolve o mapeamento de retorno
		return retorno;
	}

}
