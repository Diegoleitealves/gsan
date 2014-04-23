package gcom.gui.cadastro.sistemaparametro;

import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.gui.GcomAction;
import gcom.util.Util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Descri��o da classe
 * 
 * @author R�mulo Aur�lio
 * @date 08/01/2007
 */
public class ExibirInformarParametrosSistemaFaturamentoTarifaSocialAction extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		// localiza o action no objeto actionmapping
		ActionForward retorno = actionMapping.findForward("exibirInformarParametrosSistemaFaturamentoTarifaSocial");

		// obt�m a inst�ncia da sess�o
		HttpSession sessao = this.getSessao(httpServletRequest);

		InformarSistemaParametrosActionForm form = (InformarSistemaParametrosActionForm) actionForm;

		SistemaParametro sistemaParametro = 
			(SistemaParametro) sessao.getAttribute("sistemaParametro");

		Integer cont;
		
		// Verifica se ja entrou nesse action, caso nao coloca no form os dados
		// do objeto sistemaParametro
		if (sessao.getAttribute("FaturamentoTarifaSocial") == null) {
			
			cont = 1;
			sessao.setAttribute("FaturamentoTarifaSocial", cont);

			String anoMesFaturamento = 
				Util.formatarAnoMesParaMesAno(sistemaParametro.getAnoMesFaturamento().toString());
			
			form.setMesAnoReferencia(anoMesFaturamento);
			form.setMenorConsumo(sistemaParametro.getMenorConsumoGrandeUsuario().toString());

			if (sistemaParametro.getValorMinimoEmissaoConta() != null) {

				String valorAux = 
					Util.formatarMoedaReal(sistemaParametro.getValorMinimoEmissaoConta());

				form.setMenorValor(valorAux);
			}

			form.setQtdeEconomias(sistemaParametro.getMenorEconomiasGrandeUsuario().toString());
			
			
			form.setQtdeContasRetificadas(sistemaParametro.getQtdMaxContasRetificadas()+"");
			
			if(sistemaParametro.getMesesMediaConsumo() != null){
				form.setMesesCalculoMedio(sistemaParametro.getMesesMediaConsumo().toString());	
			}

			if(sistemaParametro.getNumeroMinimoDiasEmissaoVencimento() != null){
				form.setDiasMinimoVencimento(sistemaParametro.getNumeroMinimoDiasEmissaoVencimento().toString());
			}
			
			if(sistemaParametro.getNumeroDiasAdicionaisCorreios() != null){
				form.setDiasMinimoVencimentoCorreio(sistemaParametro.getNumeroDiasAdicionaisCorreios().toString());
			}
			
			if(sistemaParametro.getDiasVencimentoAlternativo() != null){
				form.setDiasVencimentoAlternativo(sistemaParametro.getDiasVencimentoAlternativo());
			}

			if(sistemaParametro.getNumeroMesesValidadeConta() != null){
				form.setNumeroMesesValidadeConta(sistemaParametro.getNumeroMesesValidadeConta().toString());
			}
			
			if(sistemaParametro.getNumeroMesesMinimoAlteracaoVencimento() != null){
				form.setNumeroMesesAlteracaoVencimento(sistemaParametro.getNumeroMesesMinimoAlteracaoVencimento().toString());
			}

			if(sistemaParametro.getNumeroMesesMaximoCalculoMedia() != null){
				form.setNumeroMesesMaximoCalculoMedia(sistemaParametro.getNumeroMesesMaximoCalculoMedia().toString());
			}

			if(sistemaParametro.getNumeroMesesCalculoCorrecao() != null){
				form.setNumeroMesesCalculoCorrecao(sistemaParametro.getNumeroMesesCalculoCorrecao().toString());
			}
			
			if(sistemaParametro.getNumeroVezesSuspendeLeitura() != null){
				form.setNumeroVezesSuspendeLeitura(sistemaParametro.getNumeroVezesSuspendeLeitura().toString());
			}
			
			if(sistemaParametro.getNumeroMesesLeituraSuspensa() != null){
				form.setNumeroMesesLeituraSuspensa(sistemaParametro.getNumeroMesesLeituraSuspensa().toString());
			}
			
			if(sistemaParametro.getNumeroMesesReinicioSitEspFaturamento() != null){
				form.setNumeroMesesReinicioSitEspFatu(sistemaParametro.getNumeroMesesReinicioSitEspFaturamento().toString());
			}
			
			if (sistemaParametro.getIndicadorRoteiroEmpresa() != null) {
				form.setIndicadorRoteiroEmpresa(sistemaParametro.getIndicadorRoteiroEmpresa().toString());
			}
			
			if (sistemaParametro.getIndicadorLimiteAlteracaoVencimento() != null) {
				form.setIndicadorLimiteAlteracaoVencimento(sistemaParametro.getIndicadorLimiteAlteracaoVencimento().toString());
			}

			if (sistemaParametro.getIndicadorCalculaVencimento() != null) {
				form.setIndicadorCalculaVencimento(sistemaParametro.getIndicadorCalculaVencimento().toString());
			}

			if (sistemaParametro.getIndicadorTarifaCategoria() != null) {
				form.setIndicadorTarifaCategoria(sistemaParametro.getIndicadorTarifaCategoria().toString());
			}

			form.setIndicadorAtualizacaoTarifaria(""+sistemaParametro.getIndicadorAtualizacaoTarifaria());
			
			if(sistemaParametro.getAnoMesAtualizacaoTarifaria() != null){

				String anoMes = 
					Util.formatarAnoMesParaMesAno(sistemaParametro.getAnoMesAtualizacaoTarifaria().toString());
				
				form.setMesAnoAtualizacaoTarifaria(anoMes);
			}

			if (sistemaParametro.getIndicadorFaturamentoAntecipado() != null) {
				form.setIndicadorFaturamentoAntecipado(sistemaParametro.getIndicadorFaturamentoAntecipado().toString());
			}
			
			form.setIndicadorRetificacaoValorMenor("" + sistemaParametro.getIndicadorRetificacaoValorMenor());
			
			form.setIndicadorTransferenciaComDebito("" + sistemaParametro.getIndicadorTransferenciaComDebito());
			
			form.setIndicadorNaoMedidoTarifa("" + sistemaParametro.getIndicadorNaoMedidoTarifa());
			
			form.setIndicadorQuadraFace("" + sistemaParametro.getIndicadorQuadraFace());
			
			if (sistemaParametro.getValorSalarioMinimo() != null) {

				String valorAux = 
					Util.formatarMoedaReal(sistemaParametro.getValorSalarioMinimo());

				form.setSalarioMinimo(valorAux);
			}
			
			if (sistemaParametro.getAreaMaximaTarifaSocial() != null) {
				form.setAreaMaxima(sistemaParametro.getAreaMaximaTarifaSocial().toString());
			}
			
			if (sistemaParametro.getConsumoEnergiaMaximoTarifaSocial() != null) {
				form.setConsumoMaximo(sistemaParametro.getConsumoEnergiaMaximoTarifaSocial().toString());
			}
			
			if (sistemaParametro.getIndicadorTarifaCategoria() != null) {
				form.setConsumoMaximo(sistemaParametro.getConsumoEnergiaMaximoTarifaSocial().toString());
			}
			
			if (sistemaParametro.getNumeroDiasVariacaoConsumo() != null) {
				form.setNumeroDiasVariacaoConsumo(sistemaParametro.getNumeroDiasVariacaoConsumo().toString());
			}
			
			if ( sistemaParametro.getNumeroDiasPrazoRecursoAutoInfracao() != null ) {
				form.setNnDiasPrazoRecursoAutoInfracao( sistemaParametro.getNumeroDiasPrazoRecursoAutoInfracao().toString() );
			}
			if ( sistemaParametro.getPercentualBonusSocial() != null ) {
				
				String valorAux = 
					Util.formatarMoedaReal(sistemaParametro.getPercentualBonusSocial());
				
				form.setPercentualBonusSocial( valorAux );
			}
			if(sistemaParametro.getIndicadorBloqueioContaMobile()!=null){
				form.setIndicadorBloqueioContaMobile(
						sistemaParametro.getIndicadorBloqueioContaMobile()
							.toString());
		

				if ( sistemaParametro.getValorContaFichaComp() != null ) {
					
					String valorAux = 
						Util.formatarMoedaReal(sistemaParametro.getValorContaFichaComp());
					
					form.setValorContaFichaComp( valorAux );
				}
	
				if (sistemaParametro.getNumeroMesesRetificarConta() != null) {
	
					String valorAux = sistemaParametro
							.getNumeroMesesRetificarConta().toString();
	
					form.setNumeroMesesRetificarConta(valorAux);
				}
				
				
				if (sistemaParametro.getMensagemContaBraile() != null) {
	
					String msgContaBraile = sistemaParametro
							.getMensagemContaBraile().toString();
	
					form.setMensagemContaBraile(msgContaBraile);
				}
				
				if(sistemaParametro.getCodigoTipoCalculoNaoMedido() != null){
					form.setCodigoTipoCalculoNaoMedido(sistemaParametro.getCodigoTipoCalculoNaoMedido().toString());
				}
				
				if (sistemaParametro.getIndicadorNormaRetificacao() != null) {
					form.setIndicadorNormaRetificacao(sistemaParametro.getIndicadorNormaRetificacao().toString());
				}
			}
		}
		return retorno;
	}
}


