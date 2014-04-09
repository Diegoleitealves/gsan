package gcom.gui.atendimentopublico.ligacaoagua;

import gcom.atendimentopublico.ligacaoagua.FiltroLigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
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
 * @date 15/05/2008
 */
public class InserirLigacaoAguaSituacaoAction extends GcomAction {

	/**
	 * Este caso de uso permite a inclus�o de uma Situacao de Ligacao de Agua
	 * 
	 * [UC0785] Inserir Situacao de Ligacao de Agua
	 * 
	 * 
	 * @author Vin�cius Medeiros
	 * @date 15/05/2008
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

		ActionForward retorno = actionMapping.findForward("telaSucesso");

		InserirLigacaoAguaSituacaoActionForm inserirLigacaoAguaSituacaoActionForm = (InserirLigacaoAguaSituacaoActionForm) actionForm;

		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();

		String descricao = inserirLigacaoAguaSituacaoActionForm.getDescricao();
		String descricaoAbreviado = inserirLigacaoAguaSituacaoActionForm.getDescricaoAbreviado();
		String consumoMinimoFaturamento = inserirLigacaoAguaSituacaoActionForm.getConsumoMinimoFaturamento();
		String indicadorExistenciaLigacao = inserirLigacaoAguaSituacaoActionForm.getIndicadorExistenciaLigacao();
		String indicadorExistenciaRede = inserirLigacaoAguaSituacaoActionForm.getIndicadorExistenciaRede();
		String indicadorFaturamentoSituacao = inserirLigacaoAguaSituacaoActionForm.getIndicadorFaturamentoSituacao();
		String indicadorAbastecimento = inserirLigacaoAguaSituacaoActionForm.getIndicadorAbastecimento();
		String indicadorAguaAtiva = inserirLigacaoAguaSituacaoActionForm.getIndicadorAguaAtiva();
		String indicadorAguaDesligada = inserirLigacaoAguaSituacaoActionForm.getIndicadorAguaDesligada();
		String indicadorAguaCadastrada = inserirLigacaoAguaSituacaoActionForm.getIndicadorAguaCadastrada();
		String indicadorAnalizeAgua = inserirLigacaoAguaSituacaoActionForm.getIndicadorAnalizeAgua();

		LigacaoAguaSituacao ligacaoAguaSituacao = new LigacaoAguaSituacao();
		Collection colecaoPesquisa = null;

		
		// Descri��o
		if (!"".equals(inserirLigacaoAguaSituacaoActionForm.getDescricao())) {
			ligacaoAguaSituacao.setDescricao(inserirLigacaoAguaSituacaoActionForm
					.getDescricao());
		} else {
			throw new ActionServletException("atencao.required", null,
					"Descri��o");
		}
		
		// Descri��o Abreviada
		if (!"".equals(inserirLigacaoAguaSituacaoActionForm.getDescricaoAbreviado())) {
			ligacaoAguaSituacao.setDescricaoAbreviado(inserirLigacaoAguaSituacaoActionForm
					.getDescricaoAbreviado());
		} else {
			throw new ActionServletException("atencao.required", null,
					"Descri��o Abreviada");
		}
		
		// Volume M�nimo da Situa��o de Liga��o
        if (consumoMinimoFaturamento == null 
        		|| consumoMinimoFaturamento.equals("")){
        	throw new ActionServletException("atencao.required",null,"Consumo M�nimo");
        }else{  
        	ligacaoAguaSituacao.setConsumoMinimoFaturamento(new Integer(consumoMinimoFaturamento));
        }
		
		
		// Indicador de Exist�ncia de Liga��o
        if (indicadorExistenciaLigacao == null 
        		|| indicadorExistenciaLigacao.equals("")){
        	throw new ActionServletException("atencao.required",null,"Indicador de Exist�ncia de Liga��o");
        }else{  
        	ligacaoAguaSituacao.setIndicadorExistenciaLigacao(new Short(indicadorExistenciaLigacao));
        }
        
		// Indicador de Exist�ncia de Rede
        if (indicadorExistenciaRede == null 
        		|| indicadorExistenciaRede.equals("")){
        	throw new ActionServletException("atencao.required",null,"Indicador de Exist�ncia de Rede");
        }else{  
        	ligacaoAguaSituacao.setIndicadorExistenciaRede(new Short(indicadorExistenciaRede));
        }
        
		// Indicador de Faturamento
        if (indicadorFaturamentoSituacao == null 
        		|| indicadorFaturamentoSituacao.equals("")){
        	throw new ActionServletException("atencao.required",null,"Indicador de Faturamento");
        }else{  
        	ligacaoAguaSituacao.setIndicadorFaturamentoSituacao(new Short(indicadorFaturamentoSituacao));
        }
        
        //Indicador de Abastecimento
        if (indicadorAbastecimento == null 
        		|| indicadorAbastecimento.equals("")){
        	throw new ActionServletException("atencao.required",null,"Indicador de Abastecimento");
        }else{  
        	ligacaoAguaSituacao.setIndicadorAbastecimento(new Short(indicadorAbastecimento));
        }
        
        //Indicador �gua Ativa
        if (indicadorAguaAtiva == null 
        		|| indicadorAguaAtiva.equals("")){
        	throw new ActionServletException("atencao.required",null,"Indicador �gua ativa");
        }else{  
        	ligacaoAguaSituacao.setIndicadorAguaAtiva(new Short(indicadorAguaAtiva));
        }
        
        //Indicador �gua Desligada
        if (indicadorAguaDesligada == null 
        		|| indicadorAguaDesligada.equals("")){
        	throw new ActionServletException("atencao.required",null,"Indicador �gua Desligada");
        }else{  
        	ligacaoAguaSituacao.setIndicadorAguaDesligada(new Short(indicadorAguaDesligada));
        }
        
        //Indicador �gua Cadastrada
        if (indicadorAguaCadastrada == null 
        		|| indicadorAguaCadastrada.equals("")){
        	throw new ActionServletException("atencao.required",null,"Indicador �gua Cadastrada");
        }else{  
        	ligacaoAguaSituacao.setIndicadorAguaCadastrada(new Short(indicadorAguaCadastrada));
        }
        
        //Indicado Analize de �gua
        if (indicadorAnalizeAgua == null 
        		|| indicadorAnalizeAgua.equals("")){
        	throw new ActionServletException("atencao.required",null,"Indicador de Analize de �gua");
        }else{  
        	ligacaoAguaSituacao.setIndicadorAnalizeAgua(new Short(indicadorAnalizeAgua));
        }
		
		
		// Ultima altera��o
        ligacaoAguaSituacao.setUltimaAlteracao(new Date());
		// Indicador de uso
		Short iu = 1;
		ligacaoAguaSituacao.setIndicadorUso(iu);

		FiltroLigacaoAguaSituacao filtroLigacaoAguaSituacao = new FiltroLigacaoAguaSituacao();

		filtroLigacaoAguaSituacao.adicionarParametro(new ParametroSimples(
				FiltroLigacaoAguaSituacao.DESCRICAO, ligacaoAguaSituacao.getDescricao()));

		colecaoPesquisa = (Collection) fachada.pesquisar(filtroLigacaoAguaSituacao,
				LigacaoAguaSituacao.class.getName());

		if (colecaoPesquisa != null && !colecaoPesquisa.isEmpty()) {
			// 
			throw new ActionServletException(
					"atencao.ligacao_situacao_agua_ja_cadastrada", null, ligacaoAguaSituacao
							.getDescricao());
		} else {
			ligacaoAguaSituacao.setDescricao(descricao);
			ligacaoAguaSituacao.setDescricaoAbreviado(descricaoAbreviado);
			ligacaoAguaSituacao.setConsumoMinimoFaturamento(new Integer (consumoMinimoFaturamento));
			ligacaoAguaSituacao.setIndicadorExistenciaLigacao(new Short (indicadorExistenciaLigacao));
			ligacaoAguaSituacao.setIndicadorExistenciaRede(new Short (indicadorExistenciaRede));
			ligacaoAguaSituacao.setIndicadorFaturamentoSituacao(new Short (indicadorFaturamentoSituacao));
			ligacaoAguaSituacao.setIndicadorAbastecimento(new Short (indicadorAbastecimento));
			ligacaoAguaSituacao.setIndicadorAguaAtiva(new Short(indicadorAguaAtiva));
			ligacaoAguaSituacao.setIndicadorAguaDesligada(new Short(indicadorAguaDesligada));
			ligacaoAguaSituacao.setIndicadorAguaCadastrada(new Short(indicadorAguaCadastrada));
			ligacaoAguaSituacao.setIndicadorAnalizeAgua(new Short(indicadorAnalizeAgua));
			
			Integer idSituacaoAguaLigacao = (Integer) fachada.inserir(ligacaoAguaSituacao);

			montarPaginaSucesso(httpServletRequest,
					"Situa��o de Liga��o de �gua " + descricao
							+ " inserida com sucesso.",
					"Inserir outra Situa��o de Liga��o de �gua",
					"exibirInserirLigacaoAguaSituacaoAction.do?menu=sim",
					"exibirAtualizarLigacaoAguaSituacaoAction.do?idRegistroAtualizacao="
							+ idSituacaoAguaLigacao,
					"Atualizar Situa��o de Liga��o de �gua Inserida");

			sessao.removeAttribute("InserirLigacaoAguaSituacaoActionForm");

			return retorno;
		}

	}
}
