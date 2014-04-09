package gcom.gui.relatorio.arrecadacao;

import java.util.Collection;

import gcom.arrecadacao.banco.Agencia;
import gcom.arrecadacao.banco.Banco;
import gcom.arrecadacao.banco.FiltroAgencia;
import gcom.arrecadacao.banco.FiltroBanco;
import gcom.fachada.Fachada;
import gcom.gui.arrecadacao.banco.FiltrarAgenciaBancariaActionForm;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.arrecadacao.RelatorioManterAgenciaBancaria;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.SistemaException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

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
 * @author Fernando Fontelles
 * @version 1.0
 */

public class GerarRelatorioManterAgenciaBancariaAction extends
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

		Fachada fachada = Fachada.getInstancia();
		
		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		FiltrarAgenciaBancariaActionForm filtrarAgenciaBancariaActionForm = (FiltrarAgenciaBancariaActionForm) actionForm;

		FiltroAgencia filtroAgenciaBancaria = (FiltroAgencia) sessao
				.getAttribute("filtroAgencia");

		// Inicio da parte que vai mandar os parametros para o relat�rio

		Agencia agenciaBancariaParametros = new Agencia();		
		
		String id = null;		
		
		// seta os parametros que ser�o mostrados no relat�rio

		agenciaBancariaParametros.setId(id == null ? null : new Integer(
				id));
		
		//Banco
		if(filtrarAgenciaBancariaActionForm.getBancoID() != null && !
				filtrarAgenciaBancariaActionForm.getBancoID().equals("")){
			
			FiltroBanco filtroBanco = new FiltroBanco();
			filtroBanco.adicionarParametro( new ParametroSimples( FiltroBanco.ID,
					filtrarAgenciaBancariaActionForm.getBancoID() ) );
			
			Collection colecaoBanco = fachada.pesquisar(filtroBanco, Banco.class.getName());
			
			if( colecaoBanco != null && !colecaoBanco.equals("")){
				
				Banco banco =  (Banco) Util.retonarObjetoDeColecao(colecaoBanco);
				agenciaBancariaParametros.setBanco(banco);
				
			}
		
		}
		
		//Codigo Agencia
		if(filtrarAgenciaBancariaActionForm.getCodigo() != null 
				&& !filtrarAgenciaBancariaActionForm.getCodigo().equals("")){
			
			agenciaBancariaParametros.setCodigoAgencia(filtrarAgenciaBancariaActionForm.getCodigo());
			
		}
		
		//Nome Agencia
		if(filtrarAgenciaBancariaActionForm.getNome() != null
				&& !filtrarAgenciaBancariaActionForm.getNome().equals("")){
			
			agenciaBancariaParametros.setNomeAgencia(filtrarAgenciaBancariaActionForm.getNome());
			
		}
		
		// Fim da parte que vai mandar os parametros para o relat�rio

		// cria uma inst�ncia da classe do relat�rio
		RelatorioManterAgenciaBancaria relatorioManterAgenciaBancaria = new RelatorioManterAgenciaBancaria(
				(Usuario)(httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));
		relatorioManterAgenciaBancaria.addParametro("filtroAgenciaBancaria",
				filtroAgenciaBancaria	);
		relatorioManterAgenciaBancaria.addParametro("agenciaBancariaParametros",
				agenciaBancariaParametros);

		// chama o met�do de gerar relat�rio passando o c�digo da analise
		// como par�metro
		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");
		if (tipoRelatorio == null) {
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		relatorioManterAgenciaBancaria.addParametro("tipoFormatoRelatorio", Integer
				.parseInt(tipoRelatorio));
		try {
			retorno = processarExibicaoRelatorio(relatorioManterAgenciaBancaria,
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
