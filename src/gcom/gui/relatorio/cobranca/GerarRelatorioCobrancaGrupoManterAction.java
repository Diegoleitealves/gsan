package gcom.gui.relatorio.cobranca;

import gcom.cobranca.CobrancaGrupo;
import gcom.cobranca.FiltroCobrancaGrupo;
import gcom.gui.cobranca.FiltrarCobrancaGrupoActionForm;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.cobranca.RelatorioManterCobrancaGrupo;
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
 * Title: GCOM
 * Description: Sistema de Gest�o Comercial
 * Copyright: Copyright (c) 2004
 * @author Arthur Carvalho
 * @version 1.0
 */

public class GerarRelatorioCobrancaGrupoManterAction extends
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

		FiltrarCobrancaGrupoActionForm form = (FiltrarCobrancaGrupoActionForm) actionForm;

		FiltroCobrancaGrupo filtroCobrancaGrupo = (FiltroCobrancaGrupo) sessao
				.getAttribute("filtroCobrancaGrupo");

		// Inicio da parte que vai mandar os parametros para o relat�rio
		CobrancaGrupo cobrancaGrupoParametros = new CobrancaGrupo();

		//Codigo
		Integer id = null;
		if (form.getId() != null && !form.getId().equals("")) {
			id = new Integer( form.getId() ) ;
			cobrancaGrupoParametros.setId( id );
		}

		//Descri��o
		String descricao = "";
		if ( form.getDescricao() != null && !form.getDescricao().equals("")){
			descricao = form.getDescricao();
			cobrancaGrupoParametros.setDescricao(descricao);
		}
		
		//Descri��o Abreviada
		String descricaoAbreviada = "";
		if ( form.getDescricaoAbreviada() != null && !form.getDescricaoAbreviada().equals("")){
			descricaoAbreviada = form.getDescricaoAbreviada();
			cobrancaGrupoParametros.setDescricaoAbreviada(descricaoAbreviada);
		}
		
		//Ano Mes Referencia
		String anoMesReferencia = form.getAnoMesReferencia();
		if(anoMesReferencia != null && !anoMesReferencia.trim().equals("")){
        	String mes = anoMesReferencia.substring(0, 2);
        	String ano = anoMesReferencia.substring(3, 7);
        	String anoMes = ano+mes;
        	anoMesReferencia = anoMes;
        	cobrancaGrupoParametros.setAnoMesReferencia(new Integer(anoMesReferencia));
		}
		
		//Indicador de Uso
		Short indicadorUso = null;
		if(form.getIndicadorUso() != null && !form.getIndicadorUso().equals("")){
			indicadorUso = new Short("" + form.getIndicadorUso());
			cobrancaGrupoParametros.setIndicadorUso(indicadorUso);	
		}


		// cria uma inst�ncia da classe do relat�rio
		RelatorioManterCobrancaGrupo relatorioManterCobrancaGrupo = new RelatorioManterCobrancaGrupo(
				(Usuario)(httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));
		relatorioManterCobrancaGrupo.addParametro("filtroCobrancaGrupo",
				filtroCobrancaGrupo);
		relatorioManterCobrancaGrupo.addParametro("cobrancaGrupoParametros",
				cobrancaGrupoParametros);

		// chama o met�do de gerar relat�rio passando o c�digo da analise
		// como par�metro
		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");
		if (tipoRelatorio == null) {
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		relatorioManterCobrancaGrupo.addParametro("tipoFormatoRelatorio", Integer
				.parseInt(tipoRelatorio));
		try {
			retorno = processarExibicaoRelatorio(relatorioManterCobrancaGrupo,
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
