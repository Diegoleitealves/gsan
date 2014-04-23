package gcom.gui.atendimentopublico.ordemservico;

import gcom.atendimentopublico.ordemservico.FiltroOrdemServico;
import gcom.atendimentopublico.ordemservico.FiltroServicoTipo;
import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.atendimentopublico.ordemservico.ServicoTipo;
import gcom.atendimentopublico.registroatendimento.AtendimentoMotivoEncerramento;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC1176] Gerar Ordem de Fiscaliza��o para Ordem de Servi�o Encerrada 
 * @author Vivianne Sousa
 * @date 20/05/2011
 */
public class GerarOSSeletivaFiscalizacaoAction extends GcomAction {


	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		// cria a vari�vel de retorno
        ActionForward retorno = actionMapping.findForward("telaSucesso");
        
        Fachada fachada = Fachada.getInstancia();
		GerarOSSeletivaFiscalizacaoActionForm form = (GerarOSSeletivaFiscalizacaoActionForm) actionForm;
		HttpSession sessao = httpServletRequest.getSession(false);
		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		pesquisarordemServico(fachada, form, sistemaParametro);
		
		Integer qtdeDiasEncerramentoOS = new Integer(form.getQtdeDiasEncerramentoOS());
		if(qtdeDiasEncerramentoOS.equals(new Integer("0")) || 
		qtdeDiasEncerramentoOS.compareTo(sistemaParametro.getQtdeDiasEncerraOSFiscalizacao()) == 1){
			throw new ActionServletException("atencao.quantidade_dias_encerramento_os_maior_zero", 
					null, sistemaParametro.getQtdeDiasEncerraOSFiscalizacao().toString());
		}
		
		//bot�o pesquisar
		Integer idOrdemServico = null;
		Integer idGrupoCobranca = null;
		Integer idGerencia = null;
		Integer idUnidade = null; 
		Integer idLocalidadeInicial = null;
		Integer idLocalidadeFinal = null; 
		Integer idTipoServico = null; 
		BigDecimal percentualOSgeradas = null;
		Collection colecaoOSFiscalizacao = null;
		
		if(form.getIdOrdemServico() != null && !form.getIdOrdemServico().equals("")){
			//se usuario selecionou Ordem de Servi�o

			idOrdemServico = new Integer(form.getIdOrdemServico());
			
			Collection colecaoOs = new ArrayList();
			colecaoOs.add(idOrdemServico);
			
			ServicoTipo servicoTipo = null;
			FiltroServicoTipo filtroServicoTipo = new FiltroServicoTipo();
			filtroServicoTipo.adicionarParametro(new ParametroSimples(FiltroServicoTipo.ID, ServicoTipo.TIPO_FISCALIZACAO));
			Collection colecaoServicoTipo = fachada.pesquisar(filtroServicoTipo,
                    ServicoTipo.class.getName());
            
            if(colecaoServicoTipo != null && !colecaoServicoTipo.isEmpty()){
            	servicoTipo = (ServicoTipo) colecaoServicoTipo.iterator().next(); 
            }
            //[SB0004 � Gerar Ordem de Fiscaliza��o]
            colecaoOSFiscalizacao = fachada.gerarOrdemServicoFiscalizacao(colecaoOs,servicoTipo,usuarioLogado);
			
		}else{
			
			if(form.getGrupoCobranca() != null && !form.getGrupoCobranca().equals("")
					&& !form.getGrupoCobranca().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
				idGrupoCobranca = new Integer(form.getGrupoCobranca());
			}else{
				
				throw new ActionServletException("atencao.campo.informado", null, "Ordem de Servi�o ou Grupo de Cobran�a");
				
			}
			if(form.getGerenciaRegional() != null && !form.getGerenciaRegional().equals("")
					&& !form.getGerenciaRegional().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
				idGerencia = new Integer(form.getGerenciaRegional());
			}
			if(form.getUnidadeNegocio() != null && !form.getUnidadeNegocio().equals("")
				&& !form.getUnidadeNegocio().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
				idUnidade = new Integer(form.getUnidadeNegocio());
			}
			if(form.getIdLocalidadeInicial() != null && !form.getIdLocalidadeInicial().equals("")){
				idLocalidadeInicial = new Integer(form.getIdLocalidadeInicial());
				
				if(form.getIdLocalidadeFinal() != null && !form.getIdLocalidadeFinal().equals("")){
					idLocalidadeFinal = new Integer(form.getIdLocalidadeFinal());
				}else{
					idLocalidadeFinal = idLocalidadeInicial;
				}
			}
			if(form.getIdServicoTipo() != null && !form.getIdServicoTipo().equals("")){
				idTipoServico = new Integer(form.getIdServicoTipo());
			}
			if(form.getQtdeDiasEncerramentoOS() != null && !form.getQtdeDiasEncerramentoOS().equals("")){
				qtdeDiasEncerramentoOS = new Integer(form.getQtdeDiasEncerramentoOS());
			}
			
			if(form.getPercentualOSgeradas() != null && !form.getPercentualOSgeradas().equals("")){
				percentualOSgeradas = Util.formatarMoedaRealparaBigDecimal(form.getPercentualOSgeradas());
			}else{
				throw new ActionServletException("atencao.campo.informado", null, "Percentual de OS Geradas para Fiscaliza��o");
			}
			//[SB0003 � Gerar V�rias Ordens de Fiscaliza��o] 
			colecaoOSFiscalizacao = fachada.gerarVariasOsFiscalizacao(idGrupoCobranca,idGerencia, 
				idUnidade, idLocalidadeInicial,idLocalidadeFinal,idTipoServico, 
				qtdeDiasEncerramentoOS, percentualOSgeradas,usuarioLogado);
			
		}
        
		sessao.setAttribute("colecaoOSFiscalizacao",colecaoOSFiscalizacao);
		
        montarPaginaSucesso(httpServletRequest, "Ordem de Servi�o Seletiva de Fiscaliza��o inserida com sucesso.", 
        		"Inserir outra Ordem de Servi�o Seletiva de Fiscaliza��o",
				"exibirGerarOSSeletivaFiscalizacaoAction.do?menu=sim",
				"gerarRelatorioOSFiscalizacaoAction.do?idGrupoCobranca=" + idGrupoCobranca,"Gerar formul�rio");
        
        return retorno;
	}
	
	private void pesquisarordemServico(Fachada fachada, GerarOSSeletivaFiscalizacaoActionForm form,SistemaParametro sistemaParametro) {
		
		if (form.getIdOrdemServico() != null && !form.getIdOrdemServico().equals("")) {

			FiltroOrdemServico filtroOrdemServico = new FiltroOrdemServico();
			filtroOrdemServico.adicionarParametro(new ParametroSimples(FiltroOrdemServico.ID,new Integer(form.getIdOrdemServico())));
//			filtroOrdemServico.adicionarCaminhoParaCarregamentoEntidade(FiltroOrdemServico.SOLICITACAO_TIPO_ESPECIFICACAO);
			
			Collection colecaoOrdemServico = fachada.pesquisar(filtroOrdemServico,OrdemServico.class.getName());
			if(colecaoOrdemServico != null && !colecaoOrdemServico.isEmpty()){
			
				OrdemServico ordemServico = (OrdemServico) colecaoOrdemServico.iterator().next();
//				form.setNomeOrdemServico(ordemServico.getServicoTipo().getDescricao());
				form.setNomeOrdemServico("");
				
				//[FS0001] � Validar Ordem de Servi�o.
				if (ordemServico.getAtendimentoMotivoEncerramento() != null &&
					ordemServico.getAtendimentoMotivoEncerramento().getId()
					.equals(new Integer(AtendimentoMotivoEncerramento.CANCELADO_POR_DERCURSO_DE_PRAZO))) {
					form.setIdOrdemServico("");
					throw new ActionServletException("atencao.os.cancelada.decurso.prazo");
				}
				
				if(ordemServico.getDataEncerramento() != null){
					Integer qtdeDias = 0;
					if(sistemaParametro.getQtdeDiasEncerraOSFiscalizacao() != null){
						qtdeDias = sistemaParametro.getQtdeDiasEncerraOSFiscalizacao();
					}
					Date dataEncerramentoMaisDiasLimite = Util.adicionarNumeroDiasDeUmaData(ordemServico.getDataEncerramento(),qtdeDias);
					if(dataEncerramentoMaisDiasLimite.compareTo(new Date()) == -1){
						form.setIdOrdemServico("");
						throw new ActionServletException("atencao.quantidade_dias_os_encerrada_maior");
					}
				}
				
				if (ordemServico.getServicoTipo() != null && ordemServico.getServicoTipo().getId() != null 
						&& ordemServico.getServicoTipo().getId().equals(ServicoTipo.TIPO_FISCALIZACAO)){
					form.setIdOrdemServico("");
					throw new ActionServletException("atencao.tipo_servico_associado_os_fiscalizacao");
				}
				
				Integer idOsPendente = fachada.pesquisarOSFiscalizacaoPendente(ordemServico.getId());
				if(idOsPendente != null){
					form.setIdOrdemServico("");
					throw new ActionServletException("atencao.existe_os_fiscalizacao_pendente");
				}
				
			} else {
				form.setIdOrdemServico("");
				form.setNomeOrdemServico("Ordem de Servi�o n�o cadastrada");
			}
		}
	}
}
