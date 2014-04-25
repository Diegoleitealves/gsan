package gcom.gui.relatorio.micromedicao;

import gcom.gui.micromedicao.FiltrarLocalArmazenagemHidrometroActionForm;
import gcom.micromedicao.hidrometro.FiltroHidrometroLocalArmazenagem;
import gcom.micromedicao.hidrometro.HidrometroLocalArmazenagem;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.micromedicao.RelatorioManterLocalArmazenagemHidrometro;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.SistemaException;
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

public class GerarRelatorioLocalArmazenagemHidrometroManterAction extends
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

		FiltrarLocalArmazenagemHidrometroActionForm filtrarLocalArmazenagemHidrometroActionForm = (FiltrarLocalArmazenagemHidrometroActionForm) actionForm;

		FiltroHidrometroLocalArmazenagem filtroHidrometroLocalArmazenagem= (FiltroHidrometroLocalArmazenagem) sessao
				.getAttribute("filtroHidrometroLocalArmazenagem");

		// Inicio da parte que vai mandar os parametros para o relat�rio

		HidrometroLocalArmazenagem hidrometroLocalArmazenagemParametros = new HidrometroLocalArmazenagem();

		String id = null;

		String idHidrometroLocalArmazenagemPesquisar = (String) filtrarLocalArmazenagemHidrometroActionForm.getId();

		if (idHidrometroLocalArmazenagemPesquisar != null && !idHidrometroLocalArmazenagemPesquisar.equals("")) {
		id = idHidrometroLocalArmazenagemPesquisar;
		}
		

		Short indicadorOficina= null;
		
		if(filtrarLocalArmazenagemHidrometroActionForm.getIndicadorOficina()!= null && !filtrarLocalArmazenagemHidrometroActionForm.getIndicadorOficina().equals("")){
			
			indicadorOficina = new Short ("" + filtrarLocalArmazenagemHidrometroActionForm.getIndicadorOficina());
		}
		
		Short indicadordeUso = null;
		
		if(filtrarLocalArmazenagemHidrometroActionForm.getIndicadorUso()!= null && !filtrarLocalArmazenagemHidrometroActionForm.getIndicadorUso().equals("")){
			
			indicadordeUso = new Short ("" + filtrarLocalArmazenagemHidrometroActionForm.getIndicadorUso());
		}
		
		
		// seta os parametros que ser�o mostrados no relat�rio

		hidrometroLocalArmazenagemParametros.setId(id == null ? null : new Integer(id));
		hidrometroLocalArmazenagemParametros.setDescricao(filtrarLocalArmazenagemHidrometroActionForm.getDescricao());
		hidrometroLocalArmazenagemParametros.setDescricaoAbreviada(filtrarLocalArmazenagemHidrometroActionForm.getDescricaoAbreviada());
		hidrometroLocalArmazenagemParametros.setIndicadorOficina(indicadorOficina);
		hidrometroLocalArmazenagemParametros.setIndicadorUso(indicadordeUso);
		
		// Fim da parte que vai mandar os parametros para o relat�rio

		// cria uma inst�ncia da classe do relat�rio
		RelatorioManterLocalArmazenagemHidrometro relatorioManterLocalArmazenagemHidrometro = new RelatorioManterLocalArmazenagemHidrometro(
				(Usuario)(httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));
		relatorioManterLocalArmazenagemHidrometro.addParametro("filtroHidrometroLocalArmazenagem",
				filtroHidrometroLocalArmazenagem);
		relatorioManterLocalArmazenagemHidrometro.addParametro("hidrometroLocalArmazenagemParametros",
				hidrometroLocalArmazenagemParametros);

		// chama o met�do de gerar relat�rio passando o c�digo da analise
		// como par�metro
		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");
		if (tipoRelatorio == null) {
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		relatorioManterLocalArmazenagemHidrometro.addParametro("tipoFormatoRelatorio", Integer
				.parseInt(tipoRelatorio));
		try {
			retorno = processarExibicaoRelatorio(relatorioManterLocalArmazenagemHidrometro,
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
