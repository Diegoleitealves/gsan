package gcom.gui.operacional;

import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.Localidade;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.operacional.FiltroProducaoAgua;
import gcom.operacional.ProducaoAgua;
import gcom.util.ConstantesSistema;
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
 * Descri��o da classe
 * 
 * @author Vin�cius Medeiros
 * @date 09/06/2008
 */
public class InserirProducaoAguaAction extends GcomAction {

	/**
	 * Este caso de uso permite a inclus�o de uma Producao de Agua
	 * 
	 * [UC0812] Inserir Producao de Agua
	 * 
	 * 
	 * @author Vin�cius Medeiros
	 * @date 09/06/2008
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

		// Seta o caminho de retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		InserirProducaoAguaActionForm inserirProducaoAguaActionForm = (InserirProducaoAguaActionForm) actionForm;

		// Mudar isso quando for implementado o esquema de seguran�a	
		HttpSession sessao = httpServletRequest.getSession(false);

		// Obt�m a inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		String anoMesReferencia = inserirProducaoAguaActionForm
				.getAnoMesReferencia();
		String localidadeID = inserirProducaoAguaActionForm.getLocalidadeID();
		String volumeProduzido = inserirProducaoAguaActionForm
				.getVolumeProduzido();
		
		ProducaoAgua producaoAgua = new ProducaoAgua();
		Collection colecaoPesquisa = null;
		Collection colecaoPesquisa2 = null;

		String mes = anoMesReferencia.substring(0, 2);
		String ano = anoMesReferencia.substring(3, 7);

		anoMesReferencia = ano + "" + mes;
		producaoAgua.setAnoMesReferencia(new Integer(anoMesReferencia));

		// Localidade
		if (localidadeID != null && !localidadeID.equalsIgnoreCase("")) {

			FiltroLocalidade filtroLocalidade = new FiltroLocalidade();

			filtroLocalidade.adicionarParametro(new ParametroSimples(
					FiltroLocalidade.ID, localidadeID));

			filtroLocalidade.adicionarParametro(new ParametroSimples(
					FiltroLocalidade.INDICADORUSO,
					ConstantesSistema.INDICADOR_USO_ATIVO));

			// Retorna Localidade
			colecaoPesquisa = fachada.pesquisar(filtroLocalidade,
					Localidade.class.getName());

			if (colecaoPesquisa == null || colecaoPesquisa.isEmpty()) {
				
				throw new ActionServletException(
						"atencao.pesquisa.localidade_inexistente");
			
			} else {
			
				Localidade objetoLocalidade = (Localidade) Util
						.retonarObjetoDeColecao(colecaoPesquisa);
				producaoAgua.setLocalidade(objetoLocalidade);
			
			}
		}
		
		// Volume Produzido
		if (!"".equals(inserirProducaoAguaActionForm.getVolumeProduzido())) {
			
			producaoAgua.setVolumeProduzido(Util.formatarMoedaRealparaBigDecimal(volumeProduzido));
		
		} else {	
		
			throw new ActionServletException("atencao.required", null,
						"Volume Produzido");

		}
	
		producaoAgua.setUltimaAlteracao(new Date());

		FiltroProducaoAgua filtroProducaoAgua = new FiltroProducaoAgua();

		filtroProducaoAgua.adicionarParametro(
				new ParametroSimples(FiltroProducaoAgua.MES_ANO_REFERENCIA, 
						producaoAgua.getAnoMesReferencia()));
		
		filtroProducaoAgua.adicionarParametro(
				new ParametroSimples(FiltroProducaoAgua.LOCALIDADE, 
						producaoAgua.getLocalidade()));
		
		filtroProducaoAgua.adicionarParametro(
				new ParametroSimples(FiltroProducaoAgua.VOLUME_PRODUZIDO, 
						producaoAgua.getVolumeProduzido()));

		colecaoPesquisa2 = (Collection) fachada.pesquisar(
				filtroProducaoAgua,ProducaoAgua.class.getName());

		if (colecaoPesquisa2 != null && !colecaoPesquisa2.isEmpty()) {
			// Caso j� tenha sido cadastrada a Produ��o de �gua 
			throw new ActionServletException(
					"atencao.producao_agua_ja_cadastrada");
		
		} else {
		
			producaoAgua.setVolumeProduzido(Util.formatarMoedaRealparaBigDecimal(volumeProduzido));

			Integer idProducaoAgua = (Integer) fachada.inserir(producaoAgua);

			montarPaginaSucesso(httpServletRequest, "Produ��o de �gua " + idProducaoAgua
					+ " inserido com sucesso.",
					"Inserir outra Produ��o de �gua",
					"exibirInserirProducaoAguaAction.do?menu=sim",
					"exibirAtualizarProducaoAguaAction.do?idRegistroAtualizacao="
							+ idProducaoAgua,
					"Atualizar Produ��o de �gua Inserida");

			sessao.removeAttribute("InserirProducaoAguaActionForm");
		}

		return retorno;
	}
}
