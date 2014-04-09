package gcom.gui.relatorio.micromedicao;

import gcom.cadastro.empresa.Empresa;
import gcom.cadastro.empresa.FiltroEmpresa;
import gcom.gui.micromedicao.leitura.FiltrarLeituristaActionForm;
import gcom.micromedicao.FiltroLeiturista;
import gcom.micromedicao.Leiturista;

import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.micromedicao.RelatorioManterLeiturista;
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
 * Title: GCOM
 * Description: Sistema de Gest�o Comercial
 * Copyright: Copyright (c) 2004
 * Company: COMPESA - Companhia Pernambucana de Saneamento
 * 
 * @author Arthur Carvalho
 * @date 29/12/2009
 * @version 1.0
 */

public class GerarRelatorioLeituristaAction extends
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

		FiltrarLeituristaActionForm filtrarLeituristaActionForm = (FiltrarLeituristaActionForm) actionForm;

		FiltroLeiturista filtroLeiturista = (FiltroLeiturista) sessao
				.getAttribute("filtroLeiturista");

		// Inicio da parte que vai mandar os parametros para o relat�rio

		Leiturista leituristaParametros = new Leiturista();

		String idFuncionario = "";
		String descricaoFuncionario = "";
		// Id Funcionario
		if ( filtrarLeituristaActionForm.getIdFuncionario() != null &&
				!filtrarLeituristaActionForm.getIdFuncionario().equals("") ) {
			idFuncionario = filtrarLeituristaActionForm.getIdFuncionario();
			//Descricao do Funcionario
			if ( filtrarLeituristaActionForm.getDescricaoFuncionario() != null && 
					!filtrarLeituristaActionForm.getDescricaoFuncionario().equals("") ) {
				descricaoFuncionario = filtrarLeituristaActionForm.getDescricaoFuncionario();
			}
		}
		
		String descricaoEmpresa = "";
		//Descricao da Empresa
		if ( filtrarLeituristaActionForm.getEmpresaID() != null &&
				!filtrarLeituristaActionForm.getEmpresaID().equals("") ) {
			FiltroEmpresa filtroEmpresa = new FiltroEmpresa();
			filtroEmpresa.adicionarParametro( new ParametroSimples ( FiltroEmpresa.ID,
					filtrarLeituristaActionForm.getEmpresaID()));
			
			Collection colecaoEmpresa = getFachada().pesquisar(filtroEmpresa, Empresa.class.getName());
			Empresa empresa = (Empresa) Util.retonarObjetoDeColecao(colecaoEmpresa);
			descricaoEmpresa = empresa.getDescricao();
		}
		
		String idCliente = "";
		String descricaoCliente = "";
		// Id Cliente
		if ( filtrarLeituristaActionForm.getIdCliente() != null &&
				!filtrarLeituristaActionForm.getIdCliente().equals("") ) {
			idCliente= filtrarLeituristaActionForm.getIdCliente();
			//Descricao do Cliente
			if ( filtrarLeituristaActionForm.getDescricaoCliente() != null && 
					!filtrarLeituristaActionForm.getDescricaoCliente().equals("") ) {
				descricaoCliente = filtrarLeituristaActionForm.getDescricaoCliente();
			}
		}

		//codigo ddd do municipio
		String ddd = "";
		if ( filtrarLeituristaActionForm.getDdd() != null &&
				!filtrarLeituristaActionForm.getDdd().equals("") ) {
			ddd = filtrarLeituristaActionForm.getDdd();
		}
		
		//numero do telefone 
		String numeroTelefone = "";
		if ( filtrarLeituristaActionForm.getTelefone() != null &&
				!filtrarLeituristaActionForm.getTelefone().equals("") ) {
			numeroTelefone = filtrarLeituristaActionForm.getTelefone();
		}
		
		//numero do IMEI 
		String numeroIMEI = "";
		if ( filtrarLeituristaActionForm.getImei() != null &&
				!filtrarLeituristaActionForm.getImei().equals("") ) {
			numeroIMEI = filtrarLeituristaActionForm.getImei();
			
			leituristaParametros.setNumeroImei(new Long(numeroIMEI));
		}
		
		String indicadorUso = "";
		if ( filtrarLeituristaActionForm.getIndicadorUso() != null && 
				!filtrarLeituristaActionForm.getIndicadorUso().equals("") ) {
			indicadorUso = filtrarLeituristaActionForm.getIndicadorUso();
		
			leituristaParametros.setIndicadorUso(new Short(indicadorUso));
		}
		
		// seta os parametros que ser�o mostrados no relat�rio

		leituristaParametros.setCodigoDDD(ddd);
		leituristaParametros.setNumeroFone(numeroTelefone);
		
		// Fim da parte que vai mandar os parametros para o relat�rio

		// cria uma inst�ncia da classe do relat�rio
		RelatorioManterLeiturista relatorioManterLeiturista = new RelatorioManterLeiturista(
				(Usuario)(httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));
		
		relatorioManterLeiturista.addParametro("filtroLeiturista",
				filtroLeiturista);
		relatorioManterLeiturista.addParametro("leituristaParametros",
				leituristaParametros);
		relatorioManterLeiturista.addParametro("idFuncionario",
				idFuncionario);
		relatorioManterLeiturista.addParametro("descricaoFuncionario",
				descricaoFuncionario);
		relatorioManterLeiturista.addParametro("descricaoEmpresa",
				descricaoEmpresa);
		relatorioManterLeiturista.addParametro("idCliente",
				idCliente);
		relatorioManterLeiturista.addParametro("descricaoCliente",
				descricaoCliente);

		// chama o met�do de gerar relat�rio passando o c�digo da analise
		// como par�metro
		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");
		if (tipoRelatorio == null) {
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		relatorioManterLeiturista.addParametro("tipoFormatoRelatorio", Integer
				.parseInt(tipoRelatorio));
		try {
			retorno = processarExibicaoRelatorio(relatorioManterLeiturista,
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
