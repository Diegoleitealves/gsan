package gcom.gui.relatorio.atendimentopublico.ordemservico;

import gcom.atendimentopublico.ordemservico.EquipamentosEspeciais;
import gcom.atendimentopublico.ordemservico.FiltroEquipe;
import gcom.atendimentopublico.ordemservico.FiltroServicoPerfilTipo;
import gcom.atendimentopublico.ordemservico.ServicoPerfilTipo;
import gcom.fachada.Fachada;
import gcom.gui.atendimentopublico.ordemservico.FiltrarTipoPerfilServicoActionForm;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.atendimentopublico.ordemservico.RelatorioManterTipoPerfilServico;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.SistemaException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <p>
 * Title: GCOM
 * </p>
 * <p>
 * Description: Sistema de Gest�o Comercial
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: COMPESA - Companhia Pernambucana de Saneamento
 * </p>
 * 
 * @author Arthur Carvalho
 * @version 1.0
 */

public class GerarRelatorioTipoPerfilServicoManterAction extends
		ExibidorProcessamentoTarefaRelatorio {

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

		// cria a vari�vel de retorno
		ActionForward retorno = null;

		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		FiltrarTipoPerfilServicoActionForm filtrarTipoPerfilServicoActionForm = (FiltrarTipoPerfilServicoActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		FiltroServicoPerfilTipo filtroServicoPerfilTipo = (FiltroServicoPerfilTipo) sessao
				.getAttribute("filtroServicoPerfilTipo");

		// Inicio da parte que vai mandar os parametros para o relat�rio

		ServicoPerfilTipo servicoPerfilTipoParametros = new ServicoPerfilTipo();

		String id = null;
		
		if (filtrarTipoPerfilServicoActionForm.getCodigoPerfilServico() != null
				&& !filtrarTipoPerfilServicoActionForm.getCodigoPerfilServico().equals("")) {

			id = filtrarTipoPerfilServicoActionForm.getCodigoPerfilServico();
		}

		Short indicadorVeiculoProprio = null;

		if (filtrarTipoPerfilServicoActionForm.getIndicadorVeiculoProprio() != null
				&& !filtrarTipoPerfilServicoActionForm.getIndicadorVeiculoProprio().equals("")) {

			indicadorVeiculoProprio= new Short(""
					+ filtrarTipoPerfilServicoActionForm.getIndicadorVeiculoProprio());
		}

		Short indicadordeUso = null;
		
		if(filtrarTipoPerfilServicoActionForm.getIndicadorUso()!= null && !filtrarTipoPerfilServicoActionForm.getIndicadorUso().equals("")){
			
			indicadordeUso = new Short ("" + filtrarTipoPerfilServicoActionForm.getIndicadorUso());
		}
		
		Short qtdComponentes = null;
		
		if(filtrarTipoPerfilServicoActionForm.getQtdComponentesEquipe()!= null && !filtrarTipoPerfilServicoActionForm.getQtdComponentesEquipe().equals("")){
			
			qtdComponentes  = new Short ("" + filtrarTipoPerfilServicoActionForm.getQtdComponentesEquipe());
		}
		
		if (filtrarTipoPerfilServicoActionForm.getIdEquipamentoEspecial() != null && !filtrarTipoPerfilServicoActionForm.getIdEquipamentoEspecial().equals("")) {
			
			FiltroEquipe filtroEquipe = new FiltroEquipe();
			filtroEquipe.adicionarParametro(new ParametroSimples(FiltroEquipe.ID, filtrarTipoPerfilServicoActionForm.getIdEquipamentoEspecial()));
			
			Collection colecaoEquipamentoEspecial = fachada.pesquisar(filtroEquipe, EquipamentosEspeciais.class.getName());
			
			if (colecaoEquipamentoEspecial != null && !colecaoEquipamentoEspecial.isEmpty()) {
				EquipamentosEspeciais equipamentosEspeciais = (EquipamentosEspeciais) Util.retonarObjetoDeColecao(colecaoEquipamentoEspecial);
				servicoPerfilTipoParametros.setEquipamentosEspeciais(equipamentosEspeciais);
			}
			
		}		
		
		
		// seta os parametros que ser�o mostrados no relat�rio

		servicoPerfilTipoParametros.setId(id == null ? null : new Integer(
				id));
		servicoPerfilTipoParametros.setDescricao(""
				+ filtrarTipoPerfilServicoActionForm.getDescricaoPerfilServico());
		servicoPerfilTipoParametros.setDescricaoAbreviada(""
				+ filtrarTipoPerfilServicoActionForm.getAbrevPerfilServico());
		servicoPerfilTipoParametros.setComponentesEquipe(qtdComponentes);
		servicoPerfilTipoParametros.setIndicadorVeiculoProprio(indicadorVeiculoProprio);
		servicoPerfilTipoParametros.setIndicadorUso(indicadordeUso);
		
		
		// Fim da parte que vai mandar os parametros para o relat�rio

		// cria uma inst�ncia da classe do relat�rio
		RelatorioManterTipoPerfilServico relatorioManterTipoPerfilServico = new RelatorioManterTipoPerfilServico(
				(Usuario)(httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));
		relatorioManterTipoPerfilServico.addParametro("filtroServicoPerfilTipo",
				filtroServicoPerfilTipo);
		relatorioManterTipoPerfilServico.addParametro("servicoPerfilTipoParametros",
				servicoPerfilTipoParametros);

		// chama o met�do de gerar relat�rio passando o c�digo da analise
		// como par�metro
		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");
		if (tipoRelatorio == null) {
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}
		
		relatorioManterTipoPerfilServico.addParametro("tipoFormatoRelatorio", Integer
				.parseInt(tipoRelatorio));
		try {
			retorno = processarExibicaoRelatorio(relatorioManterTipoPerfilServico,
					tipoRelatorio, httpServletRequest, httpServletResponse,
					actionMapping);

		} catch (SistemaException ex) {
			// manda o erro para a p�gina no request atual
			reportarErros(httpServletRequest, "erro.sistema");

			// seta o mapeamento de retorno para a tela de erro de popup
			retorno = actionMapping.findForward("telaErroPopup");

		} catch (RelatorioVazioException ex1) {
			// manda o erro para a p�gina no request atual
			reportarErros(httpServletRequest, "erro.relatorio.vazio");

			// seta o mapeamento de retorno para a tela de aten��o de popup
			retorno = actionMapping.findForward("telaAtencaoPopup");
		}

		// devolve o mapeamento contido na vari�vel retorno
		return retorno;
	}

}
