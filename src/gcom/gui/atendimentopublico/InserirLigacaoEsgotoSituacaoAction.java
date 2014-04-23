package gcom.gui.atendimentopublico;

import gcom.atendimentopublico.ligacaoesgoto.FiltroLigacaoEsgotoSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
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
 * @date 14/05/2008
 */
public class InserirLigacaoEsgotoSituacaoAction extends GcomAction {

	/**
	 * Este caso de uso permite a inclus�o de uma Situacao de Ligacao de Esgoto
	 * 
	 * [UC0788] Inserir Situacao de Ligacao de Esgoto
	 * 
	 * 
	 * @author Vin�cius Medeiros
	 * @date 14/05/2008
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

		InserirLigacaoEsgotoSituacaoActionForm inserirLigacaoEsgotoSituacaoActionForm = (InserirLigacaoEsgotoSituacaoActionForm) actionForm;

		// Mudar isso quando houver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		// Obt�m a inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		String descricao = inserirLigacaoEsgotoSituacaoActionForm.getDescricao();
		String descricaoAbreviado = inserirLigacaoEsgotoSituacaoActionForm.getDescricaoAbreviado();
		String volumeMinimoFaturamento = inserirLigacaoEsgotoSituacaoActionForm.getVolumeMinimoFaturamento();
		String indicadorExistenciaLigacao = inserirLigacaoEsgotoSituacaoActionForm.getIndicadorExistenciaLigacao();
		String indicadorExistenciaRede = inserirLigacaoEsgotoSituacaoActionForm.getIndicadorExistenciaRede();
		String indicadorFaturamentoSituacao = inserirLigacaoEsgotoSituacaoActionForm.getIndicadorFaturamentoSituacao();

		LigacaoEsgotoSituacao ligacaoEsgotoSituacao = new LigacaoEsgotoSituacao();
		Collection colecaoPesquisa = null;

		
		// Verifica se o campo Descri��o foi preenchido
		if (!"".equals(inserirLigacaoEsgotoSituacaoActionForm.getDescricao())) {
			
			ligacaoEsgotoSituacao.setDescricao(inserirLigacaoEsgotoSituacaoActionForm
					.getDescricao());
		
		} else {
		
			throw new ActionServletException("atencao.required", null,"Descri��o");
		}
		
		// Verifica se o campo Descri��o Abreviada foi preenchido
		if (!"".equals(inserirLigacaoEsgotoSituacaoActionForm.getDescricaoAbreviado())) {
			
			ligacaoEsgotoSituacao.setDescricaoAbreviado(inserirLigacaoEsgotoSituacaoActionForm
					.getDescricaoAbreviado());
			
		} else {
			
			throw new ActionServletException("atencao.required", null,
					"Descri��o Abreviada");
		}
		
		// Verifica se o campo Volume M�nimo da Situa��o de Liga��o foi preenchido
        if (volumeMinimoFaturamento == null || volumeMinimoFaturamento.equals("")){
        	
        	throw new ActionServletException("atencao.required",null,"Volume M�nimo da Situa��o de Liga��o");
        
        }else{  
        
        	ligacaoEsgotoSituacao.setVolumeMinimoFaturamento(new Integer(volumeMinimoFaturamento));
        
        }
		
		
		// Verifica se o campo Indicador de Exist�ncia de Liga��o foi preenchido
        if (indicadorExistenciaLigacao == null || indicadorExistenciaLigacao.equals("")){
        	
        	throw new ActionServletException("atencao.required",null,"Indicador de Exist�ncia de Liga��o");
        
        }else{  
        	
        	ligacaoEsgotoSituacao.setIndicadorExistenciaLigacao(new Short(indicadorExistenciaLigacao));
        
        }
        
		// Verifica se o campo Indicador de Exist�ncia de Rede foi preenchido
        
        if (indicadorExistenciaRede == null || indicadorExistenciaRede.equals("")){
        	
        	throw new ActionServletException("atencao.required",null,"Indicador de Exist�ncia de Rede");
        
        }else{  
        
        	ligacaoEsgotoSituacao.setIndicadorExistenciaRede(new Short(indicadorExistenciaRede));
        
        }
        
		// Verifica se o Indicador de Faturamento foi preenchido
        if (indicadorFaturamentoSituacao == null || indicadorFaturamentoSituacao.equals("")){
        	
        	throw new ActionServletException("atencao.required",null,"Indicador de Faturamento");
        
        }else{  
        
        	ligacaoEsgotoSituacao.setIndicadorFaturamentoSituacao(new Short(indicadorFaturamentoSituacao));
        
        }
		
		
		// Ultima altera��o
		ligacaoEsgotoSituacao.setUltimaAlteracao(new Date());
		
        // Indicador de uso
		Short iu = 1;
		ligacaoEsgotoSituacao.setIndicadorUso(iu);

		FiltroLigacaoEsgotoSituacao filtroLigacaoEsgotoSituacao = new FiltroLigacaoEsgotoSituacao();

		filtroLigacaoEsgotoSituacao.adicionarParametro(new ParametroSimples(
				FiltroLigacaoEsgotoSituacao.DESCRICAO, ligacaoEsgotoSituacao.getDescricao()));

		colecaoPesquisa = (Collection) fachada.pesquisar(filtroLigacaoEsgotoSituacao,
				LigacaoEsgotoSituacao.class.getName());

		if (colecaoPesquisa != null && !colecaoPesquisa.isEmpty()) {
			
			// Caso j� tenha uma Situa��o de Liga��o de Esgoto cadastrada
			throw new ActionServletException(
					"atencao.ligacao_situacao_esgoto_ja_cadastrada", null, ligacaoEsgotoSituacao
							.getDescricao());
		
		} else {
			
			ligacaoEsgotoSituacao.setDescricao(descricao);
			ligacaoEsgotoSituacao.setDescricaoAbreviado(descricaoAbreviado);
			ligacaoEsgotoSituacao.setVolumeMinimoFaturamento(new Integer (volumeMinimoFaturamento));
			ligacaoEsgotoSituacao.setIndicadorExistenciaLigacao(new Short (indicadorExistenciaLigacao));
			ligacaoEsgotoSituacao.setIndicadorExistenciaRede(new Short (indicadorExistenciaRede));
			ligacaoEsgotoSituacao.setIndicadorFaturamentoSituacao(new Short (indicadorFaturamentoSituacao));
			
			Integer idSituacaoEsgotoLigacao = (Integer) fachada.inserir(ligacaoEsgotoSituacao);

			montarPaginaSucesso(httpServletRequest,
					"Situa��o de Liga��o de Esgoto " + descricao
							+ " inserida com sucesso.",
					"Inserir outra Situa��o de Liga��o de Esgoto",
					"exibirInserirLigacaoEsgotoSituacaoAction.do?menu=sim",
					"exibirAtualizarLigacaoEsgotoSituacaoAction.do?idRegistroAtualizacao="
							+ idSituacaoEsgotoLigacao,
					"Atualizar Situa��o de Liga��o de Esgoto Inserida");

			sessao.removeAttribute("InserirLigacaoEsgotoSituacaoActionForm");

			return retorno;
		}

	}
}
