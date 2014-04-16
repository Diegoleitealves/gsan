package gcom.gui.relatorio.micromedicao;

import gcom.cadastro.localidade.Localidade;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.micromedicao.GerarDadosLeituraActionForm;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.micromedicao.GerarDadosLeituraHelper;
import gcom.relatorio.micromedicao.RelatorioGerarDadosLeitura;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;

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
public class GerarRelatorioDadosLeituraAction extends
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

		GerarDadosLeituraActionForm form = (GerarDadosLeituraActionForm) actionForm;
		
		Fachada fachada = Fachada.getInstancia();

		GerarDadosLeituraHelper gerarDadosLeituraHelper = new GerarDadosLeituraHelper();
		
		if (form.getIdGrupoFaturamento() != null && !form.getIdGrupoFaturamento().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)) {
			gerarDadosLeituraHelper.setIdGrupoFaturamento(new Integer(form.getIdGrupoFaturamento()));
		}
		
		if (form.getIdRota() != null && !form.getIdRota().equals("")) {
			gerarDadosLeituraHelper.setIdRota(new Integer(form.getIdRota()));
			gerarDadosLeituraHelper.setCodigoRota(new Integer(form.getCodigoRota()));
		}
		
		if (form.getIdLocalidadeInicial() != null && !form.getIdLocalidadeInicial().trim().equals("")) {
			
			Localidade localidade = fachada.pesquisarLocalidadeDigitada(new Integer(form.getIdLocalidadeInicial()));
		
			if (localidade != null) {
				gerarDadosLeituraHelper.setIdLocalidadeInicial(localidade.getId());
			} else {
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Localidade Inicial");
			}
			
		}
		
		if (form.getIdLocalidadeFinal() != null && !form.getIdLocalidadeFinal().trim().equals("")) {
			
			Localidade localidade = fachada.pesquisarLocalidadeDigitada(new Integer(form.getIdLocalidadeFinal()));
			
			if (localidade != null) {
				gerarDadosLeituraHelper.setIdLocalidadeFinal(localidade.getId());
			} else {
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Localidade Final");
			}
			
		}
		
		// Fim da parte que vai mandar os parametros para o relat�rio
		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");

		RelatorioGerarDadosLeitura relatorioGerarDadosLeitura = new RelatorioGerarDadosLeitura((Usuario)(httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));
		relatorioGerarDadosLeitura.addParametro("gerarDadosLeituraHelper",
				gerarDadosLeituraHelper);
		if (tipoRelatorio == null) {
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		relatorioGerarDadosLeitura.addParametro("tipoFormatoRelatorio", Integer
				.parseInt(tipoRelatorio));
		retorno = processarExibicaoRelatorio(relatorioGerarDadosLeitura, tipoRelatorio,
				httpServletRequest, httpServletResponse, actionMapping);

		// devolve o mapeamento contido na vari�vel retorno
		return retorno;
	}

}
