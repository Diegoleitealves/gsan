package gcom.gui.relatorio.financeiro;

import gcom.cadastro.geografico.FiltroMunicipio;
import gcom.cadastro.geografico.Municipio;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.financeiro.RelatorioSaldoContasAReceberContabil;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
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
 * Gera��o do relat�rio 
 * [UC0717] Gerar Relat�rio do Saldo do Contas a Receber Cont�bil
 * 
 */

public class GerarRelatorioSaldoContasAReceberContabilAction extends
		ExibidorProcessamentoTarefaRelatorio {

	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		ActionForward retorno = null;

        GerarRelatorioSaldoContasAReceberContabilActionForm form = (GerarRelatorioSaldoContasAReceberContabilActionForm) actionForm;

		String mesAno = form.getMesAno();
		Integer gerenciaRegional = null;
		Integer localidade = null;
		Integer unidadeNegocio = null;
		Integer municipio = null;
		String opcaoTotalizacao = form.getOpcaoTotalizacao();
		
		if(mesAno == null || mesAno.equals("")){
			throw new ActionServletException("atencao.required", null,	"M�s/Ano de Refer�ncia Cont�bil");
		}
		
		if (opcaoTotalizacao == null || opcaoTotalizacao.equalsIgnoreCase("")) {
			throw new ActionServletException("atencao.required", null, "Op��o de Totaliza��o ");
		}

		if (opcaoTotalizacao.trim().equals("gerenciaRegional")) {
			gerenciaRegional = Integer.parseInt(form.getGerenciaRegionalId());
			if (gerenciaRegional == null
					|| gerenciaRegional.equals(ConstantesSistema.NUMERO_NAO_INFORMADO)) {
				throw new ActionServletException("atencao.required", null, "Ger�ncia Regional");
			}
		} else if (opcaoTotalizacao.trim().equals("gerenciaRegionalUnidadeNegocio")) {
			gerenciaRegional = Integer.parseInt(form.getGerenciaRegionalporUnidadeNegocioId());
			if (gerenciaRegional == null
					|| gerenciaRegional.equals(ConstantesSistema.NUMERO_NAO_INFORMADO)) {
				throw new ActionServletException("atencao.required", null, "Ger�ncia Regional");
			}
		} else if (opcaoTotalizacao.trim().equals("gerenciaRegionalLocalidade")) {
            gerenciaRegional = Integer.parseInt(form.getGerenciaRegionalporLocalidadeId());
            if (gerenciaRegional == null
                    || gerenciaRegional.equals(ConstantesSistema.NUMERO_NAO_INFORMADO)) {
                throw new ActionServletException("atencao.required", null, "Ger�ncia Regional");
            }
        } else if (opcaoTotalizacao.trim().equals("localidade")) {
			String codigoLocalidade = form.getCodigoLocalidade();

			if (codigoLocalidade == null || codigoLocalidade.equalsIgnoreCase("")) {
				throw new ActionServletException("atencao.required", null, "Localidade ");

			} else {
				pesquisarLocalidade(codigoLocalidade, httpServletRequest);
			}

			localidade = Integer.parseInt(codigoLocalidade);
		} else if (opcaoTotalizacao.trim().equals("municipio")) {
			String codigoMunicipio = form.getCodigoMunicipio();
			if (codigoMunicipio == null || codigoMunicipio.equalsIgnoreCase("")) {
				throw new ActionServletException("atencao.required", null, "Munic�pio ");
			} else {
				pesquisarMunicipio(codigoMunicipio, httpServletRequest);
			}
			municipio = Integer.parseInt(codigoMunicipio);
		}
		
		
		if (opcaoTotalizacao.trim().equals("unidadeNegocio")) {
            unidadeNegocio = Integer.parseInt(form.getUnidadeNegocioId());

			if (unidadeNegocio == null
					|| unidadeNegocio.equals(ConstantesSistema.NUMERO_NAO_INFORMADO)) {
				throw new ActionServletException("atencao.required", null, "Unidade de Neg�cio ");

			}

		}else if (opcaoTotalizacao.trim().equals("unidadeNegocioLocalidade")) {
            unidadeNegocio = Integer.parseInt(form.getUnidadeNegocioLocalidadeId());
            
            if (unidadeNegocio == null
                    || unidadeNegocio.equals(ConstantesSistema.NUMERO_NAO_INFORMADO)) {
                throw new ActionServletException("atencao.required", null, "Unidade de Neg�cio ");

            }
        }
		
		int mesAnoInteger = Integer.parseInt(mesAno.substring(0, 2)	+ mesAno.substring(3, 7));

		// Verificar se o Ano/M�s est� valido [FS0002]
		if (!Util.validarMesAno(mesAno)) {
			// Se deu algum erro neste ponto, indica falha no sistema
			throw new ActionServletException("erro.sistema");
		}

		Fachada fachada = Fachada.getInstancia();
        SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
        
		// [FS0002] Verificar refer�ncia contabil
        verificarReferenciaContabil(mesAno, sistemaParametro);
        
		// Parte que vai mandar o relat�rio para a tela

		// cria uma inst�ncia da classe do relat�rio
        RelatorioSaldoContasAReceberContabil relatorioSaldoContasAReceberContabil = new RelatorioSaldoContasAReceberContabil(
				(Usuario) (httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));
        relatorioSaldoContasAReceberContabil.addParametro("opcaoTotalizacao", opcaoTotalizacao);
        relatorioSaldoContasAReceberContabil.addParametro("mesAnoInteger", mesAnoInteger);
        relatorioSaldoContasAReceberContabil.addParametro("localidade", localidade);
        relatorioSaldoContasAReceberContabil.addParametro("gerenciaRegional", gerenciaRegional);
        relatorioSaldoContasAReceberContabil.addParametro("unidadeNegocio",unidadeNegocio);
        relatorioSaldoContasAReceberContabil.addParametro("municipio", municipio);
        
		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");
		if (tipoRelatorio == null) {
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

        relatorioSaldoContasAReceberContabil.addParametro("tipoFormatoRelatorio", Integer.parseInt(tipoRelatorio));
		try {
			retorno = processarExibicaoRelatorio(relatorioSaldoContasAReceberContabil,
					tipoRelatorio, httpServletRequest, httpServletResponse,	actionMapping);

		} catch (RelatorioVazioException ex) {
			// manda o erro para a p�gina no request atual
			reportarErros(httpServletRequest, "atencao.relatorio.vazio");

			// seta o mapeamento de retorno para a tela de aten��o de popup
			retorno = actionMapping.findForward("telaAtencaoPopup");

		}

		return retorno;
	}
	
	private void pesquisarLocalidade(String idLocalidade, HttpServletRequest httpServletRequest) {

		Fachada fachada = Fachada.getInstancia();

		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
		filtroLocalidade.adicionarParametro(new ParametroSimples(
				FiltroLocalidade.ID, idLocalidade));

		Collection<Localidade> localidadePesquisada = fachada.pesquisar(
				filtroLocalidade, Localidade.class.getName());

		if (localidadePesquisada == null || localidadePesquisada.isEmpty()) {
			throw new ActionServletException("atencao.localidade.inexistente");
		}
	}
    
	private void pesquisarMunicipio(String idMunicipio, HttpServletRequest httpServletRequest) {

		Fachada fachada = Fachada.getInstancia();

		// Pesquisa a localidade na base
		FiltroMunicipio filtroMunicipio = new FiltroMunicipio();
		filtroMunicipio.adicionarParametro(new ParametroSimples(
				FiltroMunicipio.ID, idMunicipio));

		Collection<Municipio> municipioPesquisado = fachada.pesquisar(
				filtroMunicipio, Municipio.class.getName());

		if (municipioPesquisado == null || municipioPesquisado.isEmpty()) {
			throw new ActionServletException("atencao.localidade.inexistente");
		}
	}
	
    /**
     * Verifica se o M�s/Ano informado � inferior ao M�s/Ano do Sistema
     */
    public boolean verificarReferenciaContabil(
            String anoMesReferenciaContabil,SistemaParametro sistemaParametro)  {

        boolean verificacao = Util.compararAnoMesReferencia(anoMesReferenciaContabil
                .substring(3, 7)
                + anoMesReferenciaContabil.substring(0, 2), sistemaParametro
                .getAnoMesFaturamento().toString(), ">");

        if (verificacao) {
            String anoMesSistema = sistemaParametro.toString();

            throw new ActionServletException(
                    "atencao.referencia_contabil.ano_mes.inferior", null, anoMesSistema
                            .substring(0, 2)
                            + "/" + anoMesSistema.substring(3, 7));

        }

        return true;

    }

}
