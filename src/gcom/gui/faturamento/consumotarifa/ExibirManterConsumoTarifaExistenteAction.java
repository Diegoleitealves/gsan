package gcom.gui.faturamento.consumotarifa;

import gcom.atendimentopublico.ligacaoagua.FiltroPerfilLigacao;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaPerfil;
import gcom.fachada.Fachada;
import gcom.faturamento.FaturamentoAtividade;
import gcom.faturamento.FaturamentoAtividadeCronograma;
import gcom.faturamento.FiltroFaturamentoAtividadeCronograma;
import gcom.faturamento.FiltroTarifaTipoCalculo;
import gcom.faturamento.TarifaTipoCalculo;
import gcom.faturamento.consumotarifa.ConsumoTarifaCategoria;
import gcom.faturamento.consumotarifa.ConsumoTarifaVigencia;
import gcom.faturamento.consumotarifa.FiltroConsumoTarifaCategoria;
import gcom.faturamento.consumotarifa.FiltroConsumoTarifaVigencia;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.gui.faturamento.consumotarifa.bean.CategoriaFaixaConsumoTarifaHelper;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.MaiorQue;
import gcom.util.filtro.ParametroNaoNulo;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * UC0169-Manter Tarifa de Consumo
 * 
 * @author Administrador,Rafael Santos
 * @since 18/07/2006
 */
public class ExibirManterConsumoTarifaExistenteAction extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping
				.findForward("manterConsumoTarifaExistenteAction");

		Fachada fachada = Fachada.getInstancia();

		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		String limparForm = (String) httpServletRequest
				.getParameter("limparForm");
		
		InserirConsumoTarifaActionForm inserirConsumoTarifaActionForm = (InserirConsumoTarifaActionForm) actionForm;

		String idVigencia = null;
		Collection colecaoVigencia = null;
		
       	if(httpServletRequest
        	.getParameter("idVigencia") != null){
       		idVigencia = (String) httpServletRequest
        	.getParameter("idVigencia");
        }else if(httpServletRequest
        	.getAttribute("idVigencia") != null){
        	idVigencia = (String) httpServletRequest
        	.getAttribute("idVigencia");
        }
		
       
       	
		if ((idVigencia != null)
				&& (!idVigencia.equals(""))) {
			
			if(httpServletRequest.getParameter("recarregar") == null){
				String [] ids = new String[1];
				ids[0] = idVigencia;
				
				//Colocado para ao tentar manter uma tarifa ja usada, so poder visualizar 
				// recebe os ids das vigencias a ser exclu�das do action e faz um
				// interator para fazer a verificacao de vigencia por vigencia
				for (int i = 0; i < ids.length; i++) {
					// monta o filtro pra recuperar a data da vigencia
					FiltroConsumoTarifaVigencia filtroConsumoTarifaVigencia = new FiltroConsumoTarifaVigencia();
					filtroConsumoTarifaVigencia
							.adicionarParametro(new ParametroSimples(
									FiltroConsumoTarifaVigencia.ID, ids[i]));
					Collection<ConsumoTarifaVigencia> colecaoConsumoTarifaVigencia = fachada
							.pesquisar(filtroConsumoTarifaVigencia,
									ConsumoTarifaVigencia.class.getName());
	
					// jogaa a data em nessa vari�vel
					Date dataVigencia = colecaoConsumoTarifaVigencia.iterator().next()
							.getDataVigencia();
	
					// monta o filtro para pegar todas os FaturamentoAtividadeCronograma
					// a data de realizacao tem que ser maior ou igual a data da
					// vigencia o id da atividade tem q ser igual a 2 (EFETUAR LEITURA)
	
					FiltroFaturamentoAtividadeCronograma filtroFaturamentoAtividadeCronograma = new FiltroFaturamentoAtividadeCronograma();
					filtroFaturamentoAtividadeCronograma
							.adicionarParametro(new MaiorQue(
									FiltroFaturamentoAtividadeCronograma.DATA_REALIZADA,
									dataVigencia));
					//valor correspondente a EFETUAR LEITURA
					filtroFaturamentoAtividadeCronograma
							.adicionarParametro(new ParametroSimples(
									FiltroFaturamentoAtividadeCronograma.FATURAMENTO_ATIVIDADE_ID,
									FaturamentoAtividade.EFETUAR_LEITURA.toString()));
					filtroFaturamentoAtividadeCronograma
							.adicionarCaminhoParaCarregamentoEntidade("faturamentoAtividade");
					filtroFaturamentoAtividadeCronograma
							.adicionarCaminhoParaCarregamentoEntidade("faturamentoGrupoCronogramaMensal");
					Collection<FaturamentoAtividadeCronograma> colecaoFaturamentoAtividadeCronograma = fachada
							.pesquisar(filtroFaturamentoAtividadeCronograma,
									FaturamentoAtividadeCronograma.class.getName());
	
					// verifica se h� dados na colecao e faz um iterator pra recuperar
					// todos que tem o id da atividade igual a 5 que a data de
					// realiza��o
					// seja diferente de null e que o id do cronograma grupo mensal seja
					// igual a da colecao de FatAtvCronograma vigente no iterator
					// anterior
					if (colecaoFaturamentoAtividadeCronograma != null
							&& !colecaoFaturamentoAtividadeCronograma.isEmpty()) {
						for (Iterator it = colecaoFaturamentoAtividadeCronograma
								.iterator(); it.hasNext();) {
							FaturamentoAtividadeCronograma faturamentoAtividadeCronograma = (FaturamentoAtividadeCronograma) it
									.next();
	
							FiltroFaturamentoAtividadeCronograma filtroFaturamentoAtividadeCronograma2 = new FiltroFaturamentoAtividadeCronograma();
							//valor correspondente a FATURAR GRUPO
							filtroFaturamentoAtividadeCronograma2
									.adicionarParametro(new ParametroSimples(
											FiltroFaturamentoAtividadeCronograma.FATURAMENTO_ATIVIDADE_ID,
											FaturamentoAtividade.FATURAR_GRUPO.toString()));
							filtroFaturamentoAtividadeCronograma2
									.adicionarParametro(new ParametroSimples(
											FiltroFaturamentoAtividadeCronograma.FATURAMENTO_GRUPO_CRONOGRAMA_MENSAL_ID,
											faturamentoAtividadeCronograma
													.getFaturamentoGrupoCronogramaMensal().getId()));
							filtroFaturamentoAtividadeCronograma2
									.adicionarParametro(new ParametroNaoNulo(
											FiltroFaturamentoAtividadeCronograma.DATA_REALIZADA));
							filtroFaturamentoAtividadeCronograma2
									.adicionarCaminhoParaCarregamentoEntidade("faturamentoGrupoCronogramaMensal");
	
							Collection<FaturamentoAtividadeCronograma> colecaoRetornoFaturamentoAtividadeCronograma = fachada
									.pesquisar(
											filtroFaturamentoAtividadeCronograma2,
											FaturamentoAtividadeCronograma.class
													.getName());
	
							// se a colecao retornar dados, jogasse a excess�o
							if (colecaoRetornoFaturamentoAtividadeCronograma != null
									&& !colecaoRetornoFaturamentoAtividadeCronograma
											.isEmpty()) {
								httpServletRequest.setAttribute(
										"caminhoActionConclusao",
										"/gsan/exibirManterConsumoTarifaExistenteAction.do?idVigencia="+ids[i]+"&recarregar=false");							
								
								return montarPaginaConfirmacao(
										"atencao.data_vigencia_usada.confirma",
										httpServletRequest, actionMapping, Util
										.formatarData(dataVigencia));
								
								
//								throw new ControladorException(
//										"atencao.data_vigencia_usada", null, Util
//												.formatarData(dataVigencia));
							}
						}
					}
	
				}
			}
			
			//
			FiltroConsumoTarifaVigencia filtroConsumoTarifaVigencia = new FiltroConsumoTarifaVigencia();

			filtroConsumoTarifaVigencia
					.adicionarParametro(new ParametroSimples(
							FiltroConsumoTarifaVigencia.ID, idVigencia));
			filtroConsumoTarifaVigencia
					.adicionarCaminhoParaCarregamentoEntidade("consumoTarifa");
			filtroConsumoTarifaVigencia
			.adicionarCaminhoParaCarregamentoEntidade("consumoTarifa.tarifaTipoCalculo");
			filtroConsumoTarifaVigencia.adicionarCaminhoParaCarregamentoEntidade(FiltroConsumoTarifaVigencia.CONSUMO_TARIFA_LIGACAO_AGUA_PERFIL);
			colecaoVigencia = fachada.pesquisar(
					filtroConsumoTarifaVigencia, ConsumoTarifaVigencia.class
							.getName());
			
			//Pesquisa os perfis de liga��o de �gua
			FiltroPerfilLigacao filtroPerfilLigacao = new FiltroPerfilLigacao();
			filtroPerfilLigacao.setCampoOrderBy(FiltroPerfilLigacao.DESCRICAO);
			filtroPerfilLigacao.adicionarParametro(new ParametroSimples(
					FiltroPerfilLigacao.INDICADOR_USO,
					ConstantesSistema.INDICADOR_USO_ATIVO));
			Collection colecaoLigacaoAguaPerfil = fachada.pesquisar(
					filtroPerfilLigacao, LigacaoAguaPerfil.class.getName());
			if (colecaoLigacaoAguaPerfil == null || colecaoLigacaoAguaPerfil.isEmpty()) {
				throw new ActionServletException("atencao.entidade_sem_dados_para_selecao",
						null, "Tabela Ligacao Agua Perfil");
			} else {
				sessao.setAttribute("colecaoLigacaoAguaPerfil", colecaoLigacaoAguaPerfil);
				
				//seta o perfil de ligacao de agua
				Iterator iter = colecaoVigencia.iterator();
				ConsumoTarifaVigencia consumoTarifaVigencia = (ConsumoTarifaVigencia) iter.next();
				Integer idLigacaoAguaPerfil=0;
				if (consumoTarifaVigencia.getConsumoTarifa()!=null){
					if (consumoTarifaVigencia.getConsumoTarifa().getLigacaoAguaPerfil()!=null){
						idLigacaoAguaPerfil = consumoTarifaVigencia.getConsumoTarifa().getLigacaoAguaPerfil().getId();
						sessao.setAttribute("selectLigacaoAguaPerfil", idLigacaoAguaPerfil);
					}else{
						sessao.setAttribute("selectLigacaoAguaPerfil", "");
					}
				}else{
					sessao.setAttribute("selectLigacaoAguaPerfil", "");
				}
			}
			
			// Pesquisa o tipo de calculo da tarifa
			FiltroTarifaTipoCalculo filtroTarifaTipoCalculo = new FiltroTarifaTipoCalculo();
			filtroTarifaTipoCalculo.setCampoOrderBy(FiltroTarifaTipoCalculo.DESCRICAO);
			filtroTarifaTipoCalculo.adicionarParametro(new ParametroSimples(
					FiltroPerfilLigacao.INDICADOR_USO,
					ConstantesSistema.INDICADOR_USO_ATIVO));
			Collection colecaoTarifaTipoCalculo = fachada.pesquisar(
					filtroTarifaTipoCalculo, TarifaTipoCalculo.class.getName());
			if (colecaoTarifaTipoCalculo == null || colecaoTarifaTipoCalculo.isEmpty()) {
				throw new ActionServletException("atencao.entidade_sem_dados_para_selecao",
						null, "Tabela Tarifa Tipo Calculo");
			} else {
				sessao.setAttribute("colecaoTarifaTipoCalculo", colecaoTarifaTipoCalculo);
				
				//seta o tipo de calculo da tarifa
				Iterator iter = colecaoVigencia.iterator();
				ConsumoTarifaVigencia consumoTarifaVigencia = (ConsumoTarifaVigencia) iter.next();
				Integer idTarifaTipoCalculo=0;
				if (consumoTarifaVigencia.getConsumoTarifa()!=null){
					if (consumoTarifaVigencia.getConsumoTarifa().getTarifaTipoCalculo()!=null){
						idTarifaTipoCalculo = consumoTarifaVigencia.getConsumoTarifa().getTarifaTipoCalculo().getId();
						sessao.setAttribute("selectTarifaTipoCalculo", idTarifaTipoCalculo);
					}else{
						sessao.setAttribute("selectTarifaTipoCalculo", "");
					}
				}else{
					sessao.setAttribute("selectTarifaTipoCalculo", "");
				}
			}

			FiltroConsumoTarifaCategoria filtroConsumoTarifaCategoria = new FiltroConsumoTarifaCategoria();

			filtroConsumoTarifaCategoria
					.adicionarParametro(new ParametroSimples(
							FiltroConsumoTarifaCategoria.CONSUMO_VIGENCIA_ID,
							idVigencia));
			
			filtroConsumoTarifaCategoria
				.adicionarParametro(new ParametroSimples(
					FiltroConsumoTarifaCategoria.SUBCATEGORIA_ID,
					"0"));

			filtroConsumoTarifaCategoria
					.adicionarCaminhoParaCarregamentoEntidade("consumoTarifaVigencia");
			filtroConsumoTarifaCategoria
				    .adicionarCaminhoParaCarregamentoEntidade("subCategoria");
			filtroConsumoTarifaCategoria
					.adicionarCaminhoParaCarregamentoEntidade("categoria");
			filtroConsumoTarifaCategoria
				.adicionarCaminhoParaCarregamentoEntidade("consumoTarifaFaixas");
			
			filtroConsumoTarifaCategoria.setCampoOrderBy(
					FiltroConsumoTarifaCategoria.CATEGORIA_ID);
	
			Collection colecaoCategoria = fachada.pesquisar(
					filtroConsumoTarifaCategoria, ConsumoTarifaCategoria.class
							.getName());
			Iterator iterator = colecaoCategoria.iterator();
			Collection colecaoHelpers = new ArrayList();
			while (iterator.hasNext()) {
				ConsumoTarifaCategoria consumoTarifaCategoria = (ConsumoTarifaCategoria) iterator
						.next();
				CategoriaFaixaConsumoTarifaHelper categoriaFaixaConsumoTarifaHelper = new CategoriaFaixaConsumoTarifaHelper(
						0, consumoTarifaCategoria);
				categoriaFaixaConsumoTarifaHelper
						.setColecaoFaixas(consumoTarifaCategoria
								.getConsumoTarifaFaixas());
				colecaoHelpers.add(categoriaFaixaConsumoTarifaHelper);
			}

			sessao.setAttribute("colecaoVigencia", colecaoVigencia);
			sessao.setAttribute("colecaoCategoria", colecaoHelpers);

		}
		
		// if ((sessao.getAttribute("Vigencia") != null)
		// && (!sessao.getAttribute("Vigencia").equals(""))) {
		// inserirConsumoTarifaActionForm.setDataVigencia((String) sessao
		// .getAttribute("Vigencia"));
		// }
		//
		// FiltroConsumoTarifa filtroConsumoTarifa = new FiltroConsumoTarifa();
		//
		// filtroConsumoTarifa.adicionarParametro(new ParametroSimples(
		// FiltroConsumoTarifa.INDICADOR_USO,
		// ConstantesSistema.INDICADOR_USO_ATIVO));
		//
		// Collection colecaoConsumoTarifa = fachada.pesquisar(
		// filtroConsumoTarifa, ConsumoTarifa.class.getName());
		// sessao.setAttribute("colecaoConsumoTarifa", colecaoConsumoTarifa);
		// /*
		// * if (colecaoConsumoTarifa == null ||
		// colecaoConsumoTarifa.isEmpty()){
		// * //... }
		// */
		// // sessao.setAttribute("colecaoConsumoTarifa", colecaoConsumoTarifa);
		// sessao.setAttribute("inserirConsumoTarifaActionForm",
		// inserirConsumoTarifaActionForm);

		if (limparForm != null && !limparForm.trim().equalsIgnoreCase("")) {
			inserirConsumoTarifaActionForm.reset(actionMapping,
					httpServletRequest);
		}

		return retorno;

	}
}
