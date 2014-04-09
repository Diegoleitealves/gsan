package gcom.gui.relatorio;

import gcom.arrecadacao.ArrecadacaoForma;
import gcom.arrecadacao.Arrecadador;
import gcom.arrecadacao.FiltroArrecadacaoForma;
import gcom.arrecadacao.FiltroArrecadador;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * action respons�vel pela exibi��o do relat�rio de bairro manter
 * 
 * @author S�vio Luiz
 * @created 11 de Julho de 2005
 */
public class GerarRelatorioAcompanhamentoMovimentoArrecadadoresAction extends ExibidorProcessamentoTarefaRelatorio {

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
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		AcompanharMovimentoArrecadadoresActionForm acompanharMovimentoArrecadadoresActionForm = (AcompanharMovimentoArrecadadoresActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		// Inicio da parte que vai mandar os parametros para o relat�rio
		String mesAnoReferencia = acompanharMovimentoArrecadadoresActionForm.getMesAnoReferencia();

		String idArrecadador = acompanharMovimentoArrecadadoresActionForm.getIdArrecadador();

		Arrecadador arrecadador = null;

		if (idArrecadador != null && !idArrecadador.equals("")) {
			FiltroArrecadador filtroArrecadador = new FiltroArrecadador();

			filtroArrecadador.adicionarParametro(new ParametroSimples(
					FiltroArrecadador.ID, idArrecadador));
			filtroArrecadador.adicionarCaminhoParaCarregamentoEntidade("cliente");

			Collection colecaoArrecadadores = fachada.pesquisar(filtroArrecadador,
					Arrecadador.class.getName());

			if (colecaoArrecadadores != null && !colecaoArrecadadores.isEmpty()) {
				// O arrecadador foi encontrado
				arrecadador = (Arrecadador) Util.retonarObjetoDeColecao(colecaoArrecadadores);
			} else {
				throw new ActionServletException(
						"atencao.pesquisa_inexistente", null, "Arrecadador");
			}

		} else {
			arrecadador = new Arrecadador();
		}

		String idArrecadacaoForma = acompanharMovimentoArrecadadoresActionForm.getIdFormaArrecadacao();
		
		ArrecadacaoForma arrecadacaoForma = null;

		if (idArrecadacaoForma != null
				&& !idArrecadacaoForma.equals("")
				&& !idArrecadacaoForma.equals(""
						+ ConstantesSistema.NUMERO_NAO_INFORMADO)) {
			FiltroArrecadacaoForma filtroArrecadacaoForma = new FiltroArrecadacaoForma();

			filtroArrecadacaoForma.adicionarParametro(new ParametroSimples(
					FiltroArrecadacaoForma.CODIGO, idArrecadacaoForma));

			Collection colecaoArrecadacaoForma = fachada.pesquisar(filtroArrecadacaoForma,
					ArrecadacaoForma.class.getName());

			if (colecaoArrecadacaoForma != null && !colecaoArrecadacaoForma.isEmpty()) {
				// O arrecadador foi encontrado
				arrecadacaoForma = (ArrecadacaoForma) Util.retonarObjetoDeColecao(colecaoArrecadacaoForma);
			} else {
				throw new ActionServletException(
						"atencao.pesquisa_inexistente", null, "Forma de Arrecada��o");
			}

		} else {
			arrecadacaoForma = new ArrecadacaoForma();
		}

		fachada.gerarResumoAcompanhamentoMovimentoArrecadadores((Usuario)(httpServletRequest.getSession(false)).getAttribute("usuarioLogado"), mesAnoReferencia, arrecadador, arrecadacaoForma);
		
		// Fim da parte que vai mandar os parametros para o relat�rio
//		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");
		
//		RelatorioAcompanhamentoMovimentoArrecadadores relatorioAcompanhamentoMovimentoArrecadadores = new RelatorioAcompanhamentoMovimentoArrecadadores((Usuario)(httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));
//		relatorioAcompanhamentoMovimentoArrecadadores.addParametro("mesAnoReferencia", mesAnoReferencia);
//		relatorioAcompanhamentoMovimentoArrecadadores.addParametro("arrecadador", arrecadador);
//		relatorioAcompanhamentoMovimentoArrecadadores.addParametro("arrecadacaoForma", arrecadacaoForma);
//		
//		if (tipoRelatorio  == null) {
//			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
//		}
//
//		relatorioAcompanhamentoMovimentoArrecadadores.addParametro("tipoFormatoRelatorio",Integer.parseInt(tipoRelatorio));
//		
//		retorno = processarExibicaoRelatorio(relatorioAcompanhamentoMovimentoArrecadadores, tipoRelatorio, httpServletRequest, httpServletResponse, actionMapping);
		
		// montando p�gina de sucesso
		montarPaginaSucesso(httpServletRequest,
				"Movimento Arrecadadores Enviado para Processamento", "Voltar",
				"/exibirAcompanharMovimentoArrecadadoresAction.do");

		// devolve o mapeamento contido na vari�vel retorno
		return retorno;
	}

}
