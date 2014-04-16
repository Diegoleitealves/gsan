package gcom.gui.atendimentopublico.ordemservico;

import gcom.atendimentopublico.ordemservico.FiltroUnidadeRepavimentadoraCustoPavimentoCalcada;
import gcom.atendimentopublico.ordemservico.FiltroUnidadeRepavimentadoraCustoPavimentoRua;
import gcom.cadastro.unidade.UnidadeRepavimentadoraCustoPavimentoCalcada;
import gcom.cadastro.unidade.UnidadeRepavimentadoraCustoPavimentoRua;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.interceptor.RegistradorOperacao;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioAcao;
import gcom.seguranca.acesso.usuario.UsuarioAcaoUsuarioHelper;
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
 * @author Hugo Leonardo
 * @date 28/12/2010
 */
public class AtualizarCustoPavimentoPorRepavimentadoraAction extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Obt�m a inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();
		
		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);
		
		Usuario usuarioLogado = (Usuario)sessao.getAttribute(Usuario.USUARIO_LOGADO);
		
		AtualizarCustoPavimentoPorRepavimentadoraActionForm form = (AtualizarCustoPavimentoPorRepavimentadoraActionForm) actionForm;

		if (httpServletRequest.getParameter("confirmado") != null
				&& httpServletRequest.getParameter("confirmado").equalsIgnoreCase("ok")) {
			
			sessao.setAttribute("confirmou", "sim");
		}
		
		// Atualiza Custo Pavimento Rua
		if((httpServletRequest.getParameter("acao") != null && 
	        	httpServletRequest.getParameter("acao").equals("atualizarRua")) ||
	        	sessao.getAttribute("acao") != null && sessao.getAttribute("acao").equals("atualizarRua")){
			
			sessao.setAttribute("acao", "atualizarRua");
			
			Date timeStampDB = (Date) sessao.getAttribute("timeStamp");

			UnidadeRepavimentadoraCustoPavimentoRua unidadeRepavimentadoraCustoPavimentoRua = 
				(UnidadeRepavimentadoraCustoPavimentoRua) sessao.getAttribute("unidadeRepavimentadoraCustoPavimentoRua");

			String descricaoPavimento = unidadeRepavimentadoraCustoPavimentoRua.getPavimentoRua().getDescricao();
			
			String valorPavimentoRua = form.getValorPavimentoRua();
			
			unidadeRepavimentadoraCustoPavimentoRua.setValorPavimento(
					Util.formatarMoedaRealparaBigDecimal(valorPavimentoRua));

			// Verificar Atualiza��o realizada por outro usu�rio
			String idUnidadeRepavimentadoraCustoPavimentoRua = unidadeRepavimentadoraCustoPavimentoRua.getId().toString();
			
			FiltroUnidadeRepavimentadoraCustoPavimentoRua filtroUnidadeRepavimentadoraCustoPavimentoRua = 
				new FiltroUnidadeRepavimentadoraCustoPavimentoRua();
			
			filtroUnidadeRepavimentadoraCustoPavimentoRua.adicionarParametro(
					new ParametroSimples(FiltroUnidadeRepavimentadoraCustoPavimentoRua.ID, 
							idUnidadeRepavimentadoraCustoPavimentoRua));
			
			//  Recupera Debito Tipo Servi�o
			Collection colecaoUnidadeRepavimentadoraCustoPavimentoRua = 
				fachada.pesquisar(filtroUnidadeRepavimentadoraCustoPavimentoRua, 
						UnidadeRepavimentadoraCustoPavimentoRua.class.getName());
			
			UnidadeRepavimentadoraCustoPavimentoRua unidadeRepavimentadoraCustoPavimentoRuaBD = 
				(UnidadeRepavimentadoraCustoPavimentoRua) colecaoUnidadeRepavimentadoraCustoPavimentoRua.iterator().next();
			
			Date dtInicial = Util.converteStringParaDate(form.getDataVigenciaInicialPavimentoRua());
			
			Date dtFinal = Util.converteStringParaDate(form.getDataVigenciaFinalPavimentoRua());
			
			// [FS0006] Verificar exist�ncia de custo do pavimento de rua no per�odo informado
			fachada.verificaAtualizarCustoPavimentoPorRepavimentadora(unidadeRepavimentadoraCustoPavimentoRuaBD.getId(),
					unidadeRepavimentadoraCustoPavimentoRuaBD.getUnidadeRepavimentadora().getId(),
					unidadeRepavimentadoraCustoPavimentoRuaBD.getPavimentoRua().getId(), 
					dtInicial, dtFinal, new Integer("1"), new Integer("1"));
			
			fachada.verificaAtualizarCustoPavimentoPorRepavimentadora(unidadeRepavimentadoraCustoPavimentoRuaBD.getId(),
					unidadeRepavimentadoraCustoPavimentoRuaBD.getUnidadeRepavimentadora().getId(),
					unidadeRepavimentadoraCustoPavimentoRuaBD.getPavimentoRua().getId(), 
					dtInicial, dtFinal, new Integer("1"), new Integer("2"));
			
			Integer validar = fachada.verificarExistenciDiasSemValorCustoPavimentoPorRepavimentadora(
					unidadeRepavimentadoraCustoPavimentoRuaBD.getId(),
					unidadeRepavimentadoraCustoPavimentoRuaBD.getUnidadeRepavimentadora().getId(),
					unidadeRepavimentadoraCustoPavimentoRuaBD.getPavimentoRua().getId(), 
					dtInicial, dtFinal, new Integer("1"), new Integer("1"));
			
			Integer validar2 = fachada.verificarExistenciDiasSemValorCustoPavimentoPorRepavimentadora(
					unidadeRepavimentadoraCustoPavimentoRuaBD.getId(),
					unidadeRepavimentadoraCustoPavimentoRuaBD.getUnidadeRepavimentadora().getId(),
					unidadeRepavimentadoraCustoPavimentoRuaBD.getPavimentoRua().getId(), 
					dtInicial, dtFinal, new Integer("1"), new Integer("2"));
			
			if((validar != 0 || validar2 != 0) && sessao.getAttribute("confirmou") == null){
				
				httpServletRequest.setAttribute("caminhoActionConclusao", "atualizarCustoPavimentoPorRepavimentadoraAction.do");
				
				return montarPaginaConfirmacao("atencao.existe.periodo.sem_cadastro.custo_pavimento_por_repavimentadora",
						httpServletRequest, actionMapping, "acao=atualizarRua");
			}
			
			sessao.removeAttribute("acao");
			sessao.removeAttribute("confirmou");
				
			if (form.getDataVigenciaInicialPavimentoRua() != null && !form.getDataVigenciaInicialPavimentoRua().equals("")){
				
				if (!Util.validarDiaMesAno(form.getDataVigenciaInicialPavimentoRua())) {
					
					unidadeRepavimentadoraCustoPavimentoRua.setDataVigenciaInicial(
							Util.converteStringParaDate(form.getDataVigenciaInicialPavimentoRua()));
					
					if (form.getDataVigenciaFinalPavimentoRua() != null && !form.getDataVigenciaFinalPavimentoRua().equals("")){
						
						if (!Util.validarDiaMesAno(form.getDataVigenciaFinalPavimentoRua())) {
							
							unidadeRepavimentadoraCustoPavimentoRua.setDataVigenciaFinal(
									Util.converteStringParaDate(form.getDataVigenciaFinalPavimentoRua()));
							
							if(Util.compararData(unidadeRepavimentadoraCustoPavimentoRua.getDataVigenciaInicial(), 
									unidadeRepavimentadoraCustoPavimentoRua.getDataVigenciaFinal()) == 1){
								
								throw new ActionServletException("atencao.atencao.data_vigencia_final_menor");
							}
						}else{
							throw new ActionServletException("atencao.atencao.data_vigencia_final_invalida");
						}
					}else if(form.getDataVigenciaFinalPavimentoRua().equals("")){
						
						unidadeRepavimentadoraCustoPavimentoRua.setDataVigenciaFinal(null);
					}
				}else{
					throw new ActionServletException("atencao.data_vigencia_inicial_invalida");
				}
			}
			
			if(timeStampDB.compareTo(unidadeRepavimentadoraCustoPavimentoRua.getUltimaAlteracao()) != 0){
				throw new ActionServletException("atencao.atualizacao.timestamp");
			}
			
			unidadeRepavimentadoraCustoPavimentoRua.setUltimaAlteracao(new Date());
			
			// ------------ REGISTRAR TRANSA��O ----------------
			RegistradorOperacao registradorOperacao = new RegistradorOperacao(
					Operacao.OPERACAO_ATUALIZAR_CUSTO_PAVIMENTO, unidadeRepavimentadoraCustoPavimentoRua.getId(),
					unidadeRepavimentadoraCustoPavimentoRua.getId(), new UsuarioAcaoUsuarioHelper(usuarioLogado,
					UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));
			// ------------ REGISTRAR TRANSA��O ----------------
			registradorOperacao.registrarOperacao(unidadeRepavimentadoraCustoPavimentoRua);
			
			fachada.atualizar(unidadeRepavimentadoraCustoPavimentoRua);

			montarPaginaSucesso(httpServletRequest, "Custo do Pavimento de Rua "
					+ descricaoPavimento + " atualizado com sucesso.",
					"Realizar outra Manuten��o de Custo do Pavimento de Rua",
					"exibirFiltrarCustoPavimentoPorRepavimentadoraAction.do?menu=sim");
			
		}else{
			
			sessao.setAttribute("acao", "atualizarCalcada");
			
			Date timeStampDB = (Date) sessao.getAttribute("timeStamp");

			UnidadeRepavimentadoraCustoPavimentoCalcada unidadeRepavimentadoraCustoPavimentoCalcada = 
				(UnidadeRepavimentadoraCustoPavimentoCalcada) sessao.getAttribute("unidadeRepavimentadoraCustoPavimentoCalcada");

			String descricaoPavimento = unidadeRepavimentadoraCustoPavimentoCalcada.getPavimentoCalcada().getDescricao();
			
			String valorPavimentoCalcada = form.getValorPavimentoCalcada();
			
			unidadeRepavimentadoraCustoPavimentoCalcada.setValorPavimento(
					Util.formatarMoedaRealparaBigDecimal(valorPavimentoCalcada));

			// Verificar Atualiza��o realizada por outro usu�rio
			String idUnidadeRepavimentadoraCustoPavimentoCalcada = unidadeRepavimentadoraCustoPavimentoCalcada.getId().toString();
			
			FiltroUnidadeRepavimentadoraCustoPavimentoCalcada filtroUnidadeRepavimentadoraCustoPavimentoCalcada = 
				new FiltroUnidadeRepavimentadoraCustoPavimentoCalcada();
			
			filtroUnidadeRepavimentadoraCustoPavimentoCalcada.adicionarParametro(
					new ParametroSimples(FiltroUnidadeRepavimentadoraCustoPavimentoCalcada.ID, 
							idUnidadeRepavimentadoraCustoPavimentoCalcada));
			
			//  Recupera Debito Tipo Servi�o
			Collection colecaoUnidadeRepavimentadoraCustoPavimentoCalcada = 
				fachada.pesquisar(filtroUnidadeRepavimentadoraCustoPavimentoCalcada, 
						UnidadeRepavimentadoraCustoPavimentoCalcada.class.getName());
			
			UnidadeRepavimentadoraCustoPavimentoCalcada unidadeRepavimentadoraCustoPavimentoCalcadaBD = 
				(UnidadeRepavimentadoraCustoPavimentoCalcada) colecaoUnidadeRepavimentadoraCustoPavimentoCalcada.iterator().next();
			
			Date dtInicial = Util.converteStringParaDate(form.getDataVigenciaInicialPavimentoCalcada());
			
			Date dtFinal = Util.converteStringParaDate(form.getDataVigenciaFinalPavimentoCalcada());
			
			// [FS0007] Verificar exist�ncia de custo do pavimento de Cal�ada no per�odo informado
			fachada.verificaAtualizarCustoPavimentoPorRepavimentadora(
					unidadeRepavimentadoraCustoPavimentoCalcadaBD.getId(),
					unidadeRepavimentadoraCustoPavimentoCalcadaBD.getUnidadeRepavimentadora().getId(),
					unidadeRepavimentadoraCustoPavimentoCalcadaBD.getPavimentoCalcada().getId(), 
					dtInicial, dtFinal, new Integer("2"), new Integer("1"));
			
			fachada.verificaAtualizarCustoPavimentoPorRepavimentadora(
					unidadeRepavimentadoraCustoPavimentoCalcadaBD.getId(),
					unidadeRepavimentadoraCustoPavimentoCalcadaBD.getUnidadeRepavimentadora().getId(),
					unidadeRepavimentadoraCustoPavimentoCalcadaBD.getPavimentoCalcada().getId(), 
					dtInicial, dtFinal, new Integer("2"), new Integer("2"));
			
			Integer validar = fachada.verificarExistenciDiasSemValorCustoPavimentoPorRepavimentadora(
					unidadeRepavimentadoraCustoPavimentoCalcadaBD.getId(),
					unidadeRepavimentadoraCustoPavimentoCalcadaBD.getUnidadeRepavimentadora().getId(),
					unidadeRepavimentadoraCustoPavimentoCalcadaBD.getPavimentoCalcada().getId(), 
					dtInicial, dtFinal, new Integer("2"), new Integer("1"));
			
			Integer validar2 = fachada.verificarExistenciDiasSemValorCustoPavimentoPorRepavimentadora(
					unidadeRepavimentadoraCustoPavimentoCalcadaBD.getId(),
					unidadeRepavimentadoraCustoPavimentoCalcadaBD.getUnidadeRepavimentadora().getId(),
					unidadeRepavimentadoraCustoPavimentoCalcadaBD.getPavimentoCalcada().getId(), 
					dtInicial, dtFinal, new Integer("2"), new Integer("2"));
			
			if((validar != 0 || validar2 != 0) && sessao.getAttribute("confirmou") == null){
				
				httpServletRequest.setAttribute("caminhoActionConclusao", "atualizarCustoPavimentoPorRepavimentadoraAction.do");
				
				return montarPaginaConfirmacao("atencao.existe.periodo.sem_cadastro.custo_pavimento_por_repavimentadora",
						httpServletRequest, actionMapping, "acao=atualizarCalcada");
			}
			
			sessao.removeAttribute("acao");
			sessao.removeAttribute("confirmou");
				
			if (form.getDataVigenciaInicialPavimentoCalcada() != null 
					&& !form.getDataVigenciaInicialPavimentoCalcada().equals("")){
				
				if (!Util.validarDiaMesAno(form.getDataVigenciaInicialPavimentoCalcada())) {
					
					unidadeRepavimentadoraCustoPavimentoCalcada.setDataVigenciaInicial(
							Util.converteStringParaDate(form.getDataVigenciaInicialPavimentoCalcada()));
					
					if (form.getDataVigenciaFinalPavimentoCalcada() != null && 
							!form.getDataVigenciaFinalPavimentoCalcada().equals("")){
						
						if (!Util.validarDiaMesAno(form.getDataVigenciaFinalPavimentoCalcada())) {
							
							unidadeRepavimentadoraCustoPavimentoCalcada.setDataVigenciaFinal(
									Util.converteStringParaDate(form.getDataVigenciaFinalPavimentoCalcada()));
							
							if(Util.compararData(unidadeRepavimentadoraCustoPavimentoCalcada.getDataVigenciaInicial(), 
									unidadeRepavimentadoraCustoPavimentoCalcada.getDataVigenciaFinal()) == 1){
								
								throw new ActionServletException("atencao.atencao.data_vigencia_final_menor");
							}
						}else{
							throw new ActionServletException("atencao.atencao.data_vigencia_final_invalida");
						}
					}
				}else{
					throw new ActionServletException("atencao.data_vigencia_inicial_invalida");
				}
			}
			
			if(timeStampDB.compareTo(unidadeRepavimentadoraCustoPavimentoCalcada.getUltimaAlteracao()) != 0){
				throw new ActionServletException("atencao.atualizacao.timestamp");
			}
			
			unidadeRepavimentadoraCustoPavimentoCalcada.setUltimaAlteracao(new Date());
			
			fachada.atualizar(unidadeRepavimentadoraCustoPavimentoCalcada);

			montarPaginaSucesso(httpServletRequest, "Custo do Pavimento de Cal�ada "
					+ descricaoPavimento + " atualizado com sucesso.",
					"Realizar outra Manuten��o de Custo do Pavimento de Cal�ada",
					"exibirFiltrarCustoPavimentoPorRepavimentadoraAction.do?menu=sim");
			
		}

		return retorno;
	}
}
