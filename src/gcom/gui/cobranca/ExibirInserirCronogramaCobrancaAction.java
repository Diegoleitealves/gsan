package gcom.gui.cobranca;

import gcom.cadastro.sistemaparametro.NacionalFeriado;
import gcom.cobranca.CobrancaAcao;
import gcom.cobranca.CobrancaAtividade;
import gcom.cobranca.CobrancaGrupo;
import gcom.cobranca.FiltroCobrancaAcao;
import gcom.cobranca.FiltroCobrancaAtividade;
import gcom.cobranca.FiltroCobrancaGrupo;
import gcom.cobranca.bean.AcaoEAtividadeCobrancaHelper;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroNulo;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * < <Descri��o da Classe>>
 * 
 * @author Fernanda Paiva
 */
public class ExibirInserirCronogramaCobrancaAction extends GcomAction {
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

		// Seta a a��o de retorno
		ActionForward retorno = actionMapping
				.findForward("inserirCronogramaCobranca");
		
        Fachada fachada = Fachada.getInstancia();

        //Mudar isso quando tiver esquema de seguran�a
        HttpSession sessao = httpServletRequest.getSession(false);
        
        CobrancaActionForm cobrancaActionForm = (CobrancaActionForm) actionForm;
        
        FiltroCobrancaGrupo filtroCobrancaGrupo = new FiltroCobrancaGrupo();
        filtroCobrancaGrupo.adicionarParametro(new ParametroSimples(FiltroCobrancaGrupo.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroCobrancaGrupo.setCampoOrderBy(FiltroCobrancaGrupo.DESCRICAO);
		
		Collection gruposCobranca = fachada.pesquisar(filtroCobrancaGrupo, CobrancaGrupo.class.getName());
		sessao.setAttribute("gruposCobranca", gruposCobranca);
		
		FiltroCobrancaAcao filtroCobrancaAcao = new FiltroCobrancaAcao();
		filtroCobrancaAcao.adicionarCaminhoParaCarregamentoEntidade("cobrancaAcaoPredecessora");
		filtroCobrancaAcao.adicionarParametro(new ParametroSimples(FiltroCobrancaAcao.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroCobrancaAcao.adicionarParametro(new ParametroSimples(FiltroCobrancaAcao.INDICADOR_CRONOGRAMA,CobrancaAcao.INDICADOR_CRONOGRAMA_ATIVO));
		filtroCobrancaAcao.setCampoOrderBy(FiltroCobrancaAcao.ORDEM_REALIZACAO);
		
		Collection acoesCobranca = fachada.pesquisar(filtroCobrancaAcao, CobrancaAcao.class.getName());
		sessao.setAttribute("acoesCobranca", acoesCobranca);
		
		FiltroCobrancaAtividade filtroCobrancaAtividade = new FiltroCobrancaAtividade();
//		Collection atividadesCobranca = fachada.pesquisar(filtroCobrancaAtividade, CobrancaAtividade.class.getName());

		
		//-------------------------------------------------------------------------
		
		//RM93 - adicionado por Vivianne Sousa - analista:Rosana Carvalho
		String limparForm = httpServletRequest.getParameter("limparForm");
		if(limparForm != null && limparForm.equals("1")){
			cobrancaActionForm.setIdGrupoCobranca(null);
			cobrancaActionForm.setDataInicio("");
			cobrancaActionForm.setMesAno("");
		}
		
		
		String exibirDataInicio = "2";
		if(cobrancaActionForm.getIdGrupoCobranca() != null &&
			!cobrancaActionForm.getIdGrupoCobranca().equals(ConstantesSistema.NUMERO_NAO_INFORMADO)
			&& verificarExecucaoAutomaticaGrupo(cobrancaActionForm.getIdGrupoCobranca())){
			
			exibirDataInicio = "1";
	
			String calcularDataPrevista = httpServletRequest.getParameter("calcularDataPrevista");
			
			if(calcularDataPrevista != null && calcularDataPrevista.equals("1")){
				if(cobrancaActionForm.getDataInicio() == null){
					throw new ActionServletException("atencao.campo.informada", null, "Data de In�cio");
				}
			
				Iterator iterAcoesCobranca = acoesCobranca.iterator();
				Collection colecaoAcaoEAtividadeCobranca = new ArrayList();
				boolean primeiraVez = true;
				Map dataPrevistaPredecessora = new HashMap();
				Date dataAcaoCobrancaAnterior = null;
				Date dataAtividadeCobrancaEncerrarAnterior = null;
				//FERIADO NACIONAL
				Collection<NacionalFeriado> colecaoFeriadosNacionais = fachada.pesquisarFeriadosNacionais();
				while (iterAcoesCobranca.hasNext()) {
					
					CobrancaAcao cobrancaAcao = (CobrancaAcao) iterAcoesCobranca.next();
					AcaoEAtividadeCobrancaHelper helper = new AcaoEAtividadeCobrancaHelper();
					helper.setAcaoCobranca(cobrancaAcao);
					
					Date dataTeste = Util.converteStringParaDate(cobrancaActionForm.getDataInicio());
					
					if(!primeiraVez){
						if(cobrancaAcao.getCobrancaAcaoPredecessora() != null){
							dataTeste = (Date)dataPrevistaPredecessora.get(cobrancaAcao.getCobrancaAcaoPredecessora().getId());
							
							if(cobrancaAcao.getNumeroDiasMinimoAcaoPrecedente() != null){
								dataTeste = Util.adicionarNumeroDiasUteisDeUmaData(dataTeste, cobrancaAcao.getNumeroDiasMinimoAcaoPrecedente(),colecaoFeriadosNacionais,null);
							}	
						}else if(dataAcaoCobrancaAnterior != null){
							dataTeste = dataAcaoCobrancaAnterior;
							if(cobrancaAcao.getNumeroDiasMinimoAcaoPrecedente() != null){
								dataTeste = Util.adicionarNumeroDiasUteisDeUmaData(dataTeste, cobrancaAcao.getNumeroDiasMinimoAcaoPrecedente(),colecaoFeriadosNacionais,null);
							}
						}
					}
					
					primeiraVez = true;
					
					filtroCobrancaAtividade = new FiltroCobrancaAtividade();
					filtroCobrancaAtividade.adicionarCaminhoParaCarregamentoEntidade("cobrancaAtividadePredecessora");
					filtroCobrancaAtividade.adicionarParametro(new ParametroSimples(FiltroCobrancaAtividade.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
					filtroCobrancaAtividade.adicionarParametro(new ParametroSimples(FiltroCobrancaAtividade.INDICADOR_CRONOGRAMA, CobrancaAtividade.ATIVO_CRONOGRAMA));
					filtroCobrancaAtividade.setCampoOrderBy(FiltroCobrancaAtividade.ORDEM_REALIZACAO);
					filtroCobrancaAtividade.adicionarParametro(new ParametroNulo(FiltroCobrancaAtividade.ID_COBRANCA_ACAO));
					
					Collection atividadesCobranca = fachada.pesquisar(filtroCobrancaAtividade, CobrancaAtividade.class.getName());
					
					//pesquisa cobran�a atividade especifica da cobran�a a��o 
					filtroCobrancaAtividade = new FiltroCobrancaAtividade();
					filtroCobrancaAtividade.adicionarCaminhoParaCarregamentoEntidade("cobrancaAtividadePredecessora");
					filtroCobrancaAtividade.adicionarParametro(new ParametroSimples(FiltroCobrancaAtividade.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
					filtroCobrancaAtividade.adicionarParametro(new ParametroSimples(FiltroCobrancaAtividade.INDICADOR_CRONOGRAMA, CobrancaAtividade.ATIVO_CRONOGRAMA));
					filtroCobrancaAtividade.setCampoOrderBy(FiltroCobrancaAtividade.ORDEM_REALIZACAO);
					filtroCobrancaAtividade.adicionarParametro(new ParametroSimples(
							FiltroCobrancaAtividade.ID_COBRANCA_ACAO, cobrancaAcao.getId()));
					 Collection atividadesCobrancaEspecifica = fachada.pesquisar(filtroCobrancaAtividade, CobrancaAtividade.class.getName());
	
					Iterator iterAtividadesCobranca = null;
					if(atividadesCobrancaEspecifica != null && !atividadesCobrancaEspecifica.isEmpty()){
		
						atividadesCobranca.addAll(atividadesCobrancaEspecifica);
					}
					 
					iterAtividadesCobranca = atividadesCobranca.iterator();
					
					while (iterAtividadesCobranca.hasNext()) {
						CobrancaAtividade cobrancaAtividade = (CobrancaAtividade) iterAtividadesCobranca.next();
						
						if(primeiraVez){
							//guarda a data da a��o de cobran�a
							primeiraVez = false;
							if(dataAtividadeCobrancaEncerrarAnterior != null){
								dataPrevistaPredecessora.put(cobrancaAcao.getId(),dataAtividadeCobrancaEncerrarAnterior);
								dataAcaoCobrancaAnterior = dataAtividadeCobrancaEncerrarAnterior;
								dataTeste = dataAcaoCobrancaAnterior;
							}else{
								dataPrevistaPredecessora.put(cobrancaAcao.getId(),dataTeste);
								dataAcaoCobrancaAnterior = dataTeste;
							}
						}else{
							//calcula a data da atividade de cobran�a
							Integer numeroDiasExecucao = 0; 
								//cobrancaAtividade.getNumeroDiasExecucao();
							
//							if(numeroDiasExecucao != null){
								
								if(cobrancaAtividade.getId().equals(CobrancaAtividade.ENCERRAR_OS)){
									
									if(cobrancaAcao.getNumerodiasEncerrarOSAtividade() != null){
										numeroDiasExecucao = cobrancaAcao.getNumerodiasEncerrarOSAtividade();
									}
									
								}else{
									if(cobrancaAcao.getNumerodiasEncerrarAtividade() != null){
										numeroDiasExecucao =  cobrancaAcao.getNumerodiasEncerrarAtividade();
									}
								}
								
								
								
								dataTeste = Util.adicionarNumeroDiasUteisDeUmaData(dataTeste,numeroDiasExecucao,colecaoFeriadosNacionais,null);
//							}
						}
						cobrancaAtividade.setDataPrevista(dataTeste);
						if(cobrancaAtividade.getId().equals(CobrancaAtividade.ENCERRAR)){
							dataAtividadeCobrancaEncerrarAnterior  = dataTeste;
						}
					}
			
					helper.setAtividadesCobranca(atividadesCobranca);
					colecaoAcaoEAtividadeCobranca.add(helper);
				}
				sessao.setAttribute("colecaoAcaoEAtividadeCobranca",colecaoAcaoEAtividadeCobranca);
			}
		}else{
			

			Iterator iterAcoesCobranca = acoesCobranca.iterator();
			Collection colecaoAcaoEAtividadeCobranca = new ArrayList();
			
			while (iterAcoesCobranca.hasNext()) {
				
				CobrancaAcao cobrancaAcao = (CobrancaAcao) iterAcoesCobranca.next();
				AcaoEAtividadeCobrancaHelper helper = new AcaoEAtividadeCobrancaHelper();
				helper.setAcaoCobranca(cobrancaAcao);
				
				filtroCobrancaAtividade = new FiltroCobrancaAtividade();
				filtroCobrancaAtividade.adicionarCaminhoParaCarregamentoEntidade("cobrancaAtividadePredecessora");
				filtroCobrancaAtividade.adicionarParametro(new ParametroSimples(FiltroCobrancaAtividade.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
				filtroCobrancaAtividade.adicionarParametro(new ParametroSimples(FiltroCobrancaAtividade.INDICADOR_CRONOGRAMA, CobrancaAtividade.ATIVO_CRONOGRAMA));
				filtroCobrancaAtividade.setCampoOrderBy(FiltroCobrancaAtividade.ORDEM_REALIZACAO);
				filtroCobrancaAtividade.adicionarParametro(new ParametroSimples(
						FiltroCobrancaAtividade.ID_COBRANCA_ACAO, cobrancaAcao.getId()));
				Collection atividadesCobranca  = fachada.pesquisar(filtroCobrancaAtividade, CobrancaAtividade.class.getName());

				if(atividadesCobranca == null || atividadesCobranca.isEmpty()){
					
					filtroCobrancaAtividade = new FiltroCobrancaAtividade();
					filtroCobrancaAtividade.adicionarCaminhoParaCarregamentoEntidade("cobrancaAtividadePredecessora");
					filtroCobrancaAtividade.adicionarParametro(new ParametroSimples(FiltroCobrancaAtividade.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
					filtroCobrancaAtividade.adicionarParametro(new ParametroSimples(FiltroCobrancaAtividade.INDICADOR_CRONOGRAMA, CobrancaAtividade.ATIVO_CRONOGRAMA));
					filtroCobrancaAtividade.setCampoOrderBy(FiltroCobrancaAtividade.ORDEM_REALIZACAO);
					filtroCobrancaAtividade.adicionarParametro(new ParametroNulo(FiltroCobrancaAtividade.ID_COBRANCA_ACAO));
					
					atividadesCobranca = fachada.pesquisar(filtroCobrancaAtividade, CobrancaAtividade.class.getName());
				}
					
				helper.setAtividadesCobranca(atividadesCobranca);
				colecaoAcaoEAtividadeCobranca.add(helper);

			}
			
			sessao.setAttribute("colecaoAcaoEAtividadeCobranca",colecaoAcaoEAtividadeCobranca);
		}
		
		httpServletRequest.setAttribute("exibirDataInicio",exibirDataInicio);
		//-------------------------------------------------------------------------
		
//		sessao.setAttribute("atividadesCobranca", atividadesCobranca);
		
		filtroCobrancaAtividade = new FiltroCobrancaAtividade();
		filtroCobrancaAtividade.adicionarCaminhoParaCarregamentoEntidade("cobrancaAtividadePredecessora");
		filtroCobrancaAtividade.adicionarParametro(new ParametroSimples(FiltroCobrancaAtividade.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroCobrancaAtividade.adicionarParametro(new ParametroSimples(FiltroCobrancaAtividade.INDICADOR_CRONOGRAMA, CobrancaAtividade.ATIVO_CRONOGRAMA));
		filtroCobrancaAtividade.adicionarParametro(new ParametroSimples(FiltroCobrancaAtividade.INDICADOR_OBRIGATORIEDADE, CobrancaAtividade.INDICADOR_OBRIGATORIEDADE_ATIVO));
		filtroCobrancaAtividade.setCampoOrderBy(FiltroCobrancaAtividade.ORDEM_REALIZACAO);
		
		Collection atividadesCobrancaObrigatoriedadeAtivo = fachada.pesquisar(filtroCobrancaAtividade, CobrancaAtividade.class.getName());
		sessao.setAttribute("atividadesCobrancaObrigatoriedadeAtivo", atividadesCobrancaObrigatoriedadeAtivo);
		
		return retorno;
	}
	
	 private boolean verificarExecucaoAutomaticaGrupo(String grupoCobranca){
	    	
    	boolean retorno = false;
    	
    	if(grupoCobranca != null && !grupoCobranca.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
    		
    		FiltroCobrancaGrupo filtroCobrancaGrupo = new FiltroCobrancaGrupo();
            filtroCobrancaGrupo.adicionarParametro(new ParametroSimples(FiltroCobrancaGrupo.ID, new Integer(grupoCobranca)));

     		Collection gruposCobranca = getFachada().pesquisar(filtroCobrancaGrupo, CobrancaGrupo.class.getName());
     		CobrancaGrupo cobrancaGrupo = (CobrancaGrupo)Util.retonarObjetoDeColecao(gruposCobranca);
     		
     		if(cobrancaGrupo.getIndicadorExecucaoAutomatica().equals(ConstantesSistema.SIM)){
     			retorno = true;
     		}
    	}
    	
    
		 
    	return retorno;
    }
	
	
}
