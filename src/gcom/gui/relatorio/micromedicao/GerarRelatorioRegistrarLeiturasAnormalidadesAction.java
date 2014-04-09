package gcom.gui.relatorio.micromedicao;

import gcom.cadastro.localidade.FiltroSetorComercial;
import gcom.cadastro.localidade.SetorComercial;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.micromedicao.RegistrarLeiturasAnormalidadesRelatorioActionForm;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.micromedicao.RelatorioComparativoLeiturasEAnormalidades;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
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
public class GerarRelatorioRegistrarLeiturasAnormalidadesAction extends
		ExibidorProcessamentoTarefaRelatorio {
	
	private Collection colecaoPesquisa = null;
	private String setorComercialCD = null;

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
		
		// Obt�m a inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		RegistrarLeiturasAnormalidadesRelatorioActionForm registrarLeiturasAnormalidadesRelatorioActionForm = (RegistrarLeiturasAnormalidadesRelatorioActionForm) actionForm;

		Integer idFaturamentoGrupo = null;
		if(registrarLeiturasAnormalidadesRelatorioActionForm.getIdFaturamentoGrupo() != null &&
				!registrarLeiturasAnormalidadesRelatorioActionForm.getIdFaturamentoGrupo().equals("-1")){
			idFaturamentoGrupo = new Integer(registrarLeiturasAnormalidadesRelatorioActionForm.getIdFaturamentoGrupo());
		}
		
		String anoMesReferenciaString = registrarLeiturasAnormalidadesRelatorioActionForm.getMesAno();
		
		Integer idEmpresa = null;
		if(registrarLeiturasAnormalidadesRelatorioActionForm.getIdFirma()!= null &&
				!registrarLeiturasAnormalidadesRelatorioActionForm.getIdFirma().equals("-1")){
			idEmpresa = new Integer(registrarLeiturasAnormalidadesRelatorioActionForm.getIdFirma());		
		}
		
		Integer idLocalidadeInicial = null;
		if(registrarLeiturasAnormalidadesRelatorioActionForm.getLocalidadeOrigemID() != null &&
				!registrarLeiturasAnormalidadesRelatorioActionForm.getLocalidadeOrigemID().equals("")){
			idLocalidadeInicial = new Integer(registrarLeiturasAnormalidadesRelatorioActionForm.getLocalidadeOrigemID());
		}
		Integer idLocalidadeFinal = null;
		if(registrarLeiturasAnormalidadesRelatorioActionForm.getLocalidadeDestinoID() != null &&
				!registrarLeiturasAnormalidadesRelatorioActionForm.getLocalidadeDestinoID().equals("")){
			idLocalidadeFinal = new Integer(registrarLeiturasAnormalidadesRelatorioActionForm.getLocalidadeDestinoID());
		}
		
		// Rota Inicial
		Integer idRotaInicial = null;
		if (registrarLeiturasAnormalidadesRelatorioActionForm.getRotaInicial() != null && 
			!registrarLeiturasAnormalidadesRelatorioActionForm.getRotaInicial().equals("") ) {
				
			idRotaInicial = new Integer( registrarLeiturasAnormalidadesRelatorioActionForm.getRotaInicial());
		}

		
		// Rota Final
		Integer idRotaFinal = null;
		if (registrarLeiturasAnormalidadesRelatorioActionForm.getRotaFinal() != null && 
			!registrarLeiturasAnormalidadesRelatorioActionForm.getRotaFinal().equals("") ) {
				
			idRotaFinal = new Integer (registrarLeiturasAnormalidadesRelatorioActionForm.getRotaFinal());
		}
		
		
		Integer idSetorInicial = null;
		if(registrarLeiturasAnormalidadesRelatorioActionForm.getSetorComercialOrigemCD() != null &&
			!registrarLeiturasAnormalidadesRelatorioActionForm.getSetorComercialOrigemCD().equals("")){
			if(registrarLeiturasAnormalidadesRelatorioActionForm.getSetorComercialOrigemID() != null &&
					!registrarLeiturasAnormalidadesRelatorioActionForm.getSetorComercialOrigemID().equals("")){
				idSetorInicial = new Integer(registrarLeiturasAnormalidadesRelatorioActionForm.getSetorComercialOrigemID());
			}else{
				pesquisarSetorComercial("origem", registrarLeiturasAnormalidadesRelatorioActionForm, fachada, httpServletRequest);
				idSetorInicial = new Integer(registrarLeiturasAnormalidadesRelatorioActionForm.getSetorComercialOrigemID());
			}
		}
		
		Integer idSetorFinal = null;
		if(registrarLeiturasAnormalidadesRelatorioActionForm.getSetorComercialDestinoCD() != null &&
				!registrarLeiturasAnormalidadesRelatorioActionForm.getSetorComercialDestinoCD().equals("")){
			if(registrarLeiturasAnormalidadesRelatorioActionForm.getSetorComercialDestinoID() != null &&
					!registrarLeiturasAnormalidadesRelatorioActionForm.getSetorComercialDestinoID().equals("")){
				idSetorFinal = new Integer(registrarLeiturasAnormalidadesRelatorioActionForm.getSetorComercialDestinoID());
			}else{
				pesquisarSetorComercial("destino", registrarLeiturasAnormalidadesRelatorioActionForm, fachada, httpServletRequest);
				idSetorFinal = new Integer(registrarLeiturasAnormalidadesRelatorioActionForm.getSetorComercialDestinoID());
			}
		}
		
		Integer idGerencia = null;
		if(registrarLeiturasAnormalidadesRelatorioActionForm.getGerenciaRegional() != null &&
				!registrarLeiturasAnormalidadesRelatorioActionForm.getGerenciaRegional().equals("-1")){
			idGerencia = new Integer(registrarLeiturasAnormalidadesRelatorioActionForm.getGerenciaRegional());
		}
		
		Integer idUnidadeNegocio = null;
		if(registrarLeiturasAnormalidadesRelatorioActionForm.getUnidadeNegocio() != null &&
				!registrarLeiturasAnormalidadesRelatorioActionForm.getUnidadeNegocio().equals("-1")){
			idUnidadeNegocio = new Integer(registrarLeiturasAnormalidadesRelatorioActionForm.getUnidadeNegocio());
		}
		
		Integer idLeiturista = null;
		if(registrarLeiturasAnormalidadesRelatorioActionForm.getIdLeiturista() != null && 
				!registrarLeiturasAnormalidadesRelatorioActionForm.getIdLeiturista().equals("")
				){
			
			idLeiturista = new Integer(registrarLeiturasAnormalidadesRelatorioActionForm.getIdLeiturista());
		}
		
		Integer anoMes = Util.formatarMesAnoComBarraParaAnoMes(anoMesReferenciaString);
		
		Integer idPerfilImovel = null;
		if(Util.verificarIdNaoVazio(registrarLeiturasAnormalidadesRelatorioActionForm.getPerfilImovel())){
			idPerfilImovel = Integer.parseInt(registrarLeiturasAnormalidadesRelatorioActionForm.getPerfilImovel());
		}
		
		Collection<String> colecaoAnormalidadesLeituras = new ArrayList<String>();
		if(registrarLeiturasAnormalidadesRelatorioActionForm.getAnormalidadesLeituras() != null && 
			registrarLeiturasAnormalidadesRelatorioActionForm.getAnormalidadesLeituras().length > 0){
			for (String anormalidade : registrarLeiturasAnormalidadesRelatorioActionForm.getAnormalidadesLeituras()) {
				
				if(anormalidade != null && !anormalidade.equals("-1")){
					colecaoAnormalidadesLeituras.add(anormalidade);	
				}
			}
		}
		
		Integer numOcorrenciasConsecutivas = null;
		if(Util.verificarNaoVazio(registrarLeiturasAnormalidadesRelatorioActionForm.getNumOcorrenciasConsecutivas())){
			numOcorrenciasConsecutivas = Integer.parseInt(registrarLeiturasAnormalidadesRelatorioActionForm.getNumOcorrenciasConsecutivas());
		}
		
		// Fim da parte que vai mandar os parametros para o relat�rio
		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");

		RelatorioComparativoLeiturasEAnormalidades relatorioComparativoLeiturasEAnormalidades = new RelatorioComparativoLeiturasEAnormalidades((Usuario)(httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));
		relatorioComparativoLeiturasEAnormalidades.addParametro(
				"idGrupoFaturamento", idFaturamentoGrupo);

		relatorioComparativoLeiturasEAnormalidades.addParametro(
				"anoMes", anoMes);
		
		relatorioComparativoLeiturasEAnormalidades.addParametro(
				"idEmpresa", idEmpresa);
		
		relatorioComparativoLeiturasEAnormalidades.addParametro(
				"idLocalidadeInicial", idLocalidadeInicial);
				
        relatorioComparativoLeiturasEAnormalidades.addParametro(
				"idLocalidadeFinal", idLocalidadeFinal);	
        
        relatorioComparativoLeiturasEAnormalidades.addParametro(
        		"idRotaInicial", idRotaInicial);
        
        relatorioComparativoLeiturasEAnormalidades.addParametro(
				"idRotaFinal", idRotaFinal);		
        
        relatorioComparativoLeiturasEAnormalidades.addParametro(
        		"idSetorInicial", idSetorInicial);
        
        relatorioComparativoLeiturasEAnormalidades.addParametro(
				"idSetorFinal", idSetorFinal);		        
		
        relatorioComparativoLeiturasEAnormalidades.addParametro(
				"idGerencia", idGerencia);	
        
        relatorioComparativoLeiturasEAnormalidades.addParametro(
				"idUnidadeNegocio", idUnidadeNegocio);	
        
    	relatorioComparativoLeiturasEAnormalidades.addParametro(
    			"idPerfilImovel", idPerfilImovel);
    	
    	relatorioComparativoLeiturasEAnormalidades.addParametro(
    			"numOcorrenciasConsecutivas", numOcorrenciasConsecutivas);
        
    	relatorioComparativoLeiturasEAnormalidades.addParametro(
    			"colecaoAnormalidadesLeituras", colecaoAnormalidadesLeituras);
        
        if(idLeiturista != null){
        	relatorioComparativoLeiturasEAnormalidades.addParametro("idLeiturista", idLeiturista);
        }
        
		// relatorioComparativoLeiturasEAnormalidades.addParametro(
		//				"dataUltimaAlteracao", new Date());
		
		if (tipoRelatorio == null) {
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		relatorioComparativoLeiturasEAnormalidades.addParametro("tipoFormatoRelatorio", Integer
				.parseInt(tipoRelatorio));
		retorno = processarExibicaoRelatorio(relatorioComparativoLeiturasEAnormalidades, tipoRelatorio,
				httpServletRequest, httpServletResponse, actionMapping);

		// devolve o mapeamento contido na vari�vel retorno
		return retorno;
	}
	
	private void pesquisarSetorComercial(String inscricaoTipo,
			RegistrarLeiturasAnormalidadesRelatorioActionForm form,
			Fachada fachada, HttpServletRequest httpServletRequest) {

		FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();

		if (inscricaoTipo.equalsIgnoreCase("origem")) {
			form.setInscricaoTipo("origem");
			// Recebe o valor do campo localidadeOrigemID do formul�rio.
			String localidadeID = (String) form
					.getLocalidadeOrigemID();

			// O campo localidadeOrigemID ser� obrigat�rio
			if (localidadeID != null
					&& !localidadeID.trim().equalsIgnoreCase("")) {

				setorComercialCD = (String) form
						.getSetorComercialOrigemCD();

				// Adiciona o id da localidade que est� no formul�rio para
				// compor a pesquisa.
				filtroSetorComercial.adicionarParametro(new ParametroSimples(
						FiltroSetorComercial.ID_LOCALIDADE, localidadeID));

				// Adiciona o c�digo do setor comercial que esta no formul�rio
				// para compor a pesquisa.
				filtroSetorComercial.adicionarParametro(new ParametroSimples(
						FiltroSetorComercial.CODIGO_SETOR_COMERCIAL,
						setorComercialCD));

				filtroSetorComercial.adicionarParametro(new ParametroSimples(
						FiltroSetorComercial.INDICADORUSO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

				// Retorna setorComercial
				colecaoPesquisa = fachada.pesquisar(filtroSetorComercial,
						SetorComercial.class.getName());

				if (colecaoPesquisa == null || colecaoPesquisa.isEmpty()) {
					// Setor Comercial nao encontrado
					// Limpa os campos setorComercialOrigemCD,
					// nomeSetorComercialOrigem e setorComercialOrigemID do
					// formul�rio
					form
							.setSetorComercialOrigemCD("");
					form
							.setSetorComercialOrigemID("");
					form
							.setNomeSetorComercialOrigem("Setor comercial inexistente.");
					
					throw new ActionServletException("atencao.setor_comercial.inexistente");
					
				} else {
					SetorComercial objetoSetorComercial = (SetorComercial) Util
							.retonarObjetoDeColecao(colecaoPesquisa);
					//setorComercialOrigem
					form
							.setSetorComercialOrigemCD(String
									.valueOf(objetoSetorComercial.getCodigo()));
					form
							.setSetorComercialOrigemID(String
									.valueOf(objetoSetorComercial.getId()));
					form
							.setNomeSetorComercialOrigem(objetoSetorComercial
									.getDescricao());
				}
			} else {
				// Limpa o campo setorComercialOrigemCD do formul�rio
				form.setSetorComercialOrigemCD("");
				form
						.setNomeSetorComercialOrigem("Informe a localidade da inscri��o de origem.");
			}
		} else {
			
			form.setInscricaoTipo("destino");
			
			// Recebe o valor do campo localidadeDestinoID do formul�rio.
			String localidadeID = (String) form
					.getLocalidadeDestinoID();

			// O campo localidadeOrigem ser� obrigat�rio
			if (localidadeID != null
					&& !localidadeID.trim().equalsIgnoreCase("")) {

				setorComercialCD = (String) form
						.getSetorComercialDestinoCD();

				// Adiciona o id da localidade que est� no formul�rio para
				// compor a pesquisa.
				filtroSetorComercial.adicionarParametro(new ParametroSimples(
						FiltroSetorComercial.ID_LOCALIDADE, localidadeID));

				// Adiciona o c�digo do setor comercial que esta no formul�rio
				// para compor a pesquisa.
				filtroSetorComercial.adicionarParametro(new ParametroSimples(
						FiltroSetorComercial.CODIGO_SETOR_COMERCIAL,
						setorComercialCD));

				filtroSetorComercial.adicionarParametro(new ParametroSimples(
						FiltroSetorComercial.INDICADORUSO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

				// Retorna setorComercial
				colecaoPesquisa = fachada.pesquisar(filtroSetorComercial,
						SetorComercial.class.getName());

				if (colecaoPesquisa == null || colecaoPesquisa.isEmpty()) {
					// Setor Comercial nao encontrado
					// Limpa os campos setorComercialDestinoCD,
					// nomeSetorComercialDestino e setorComercialDestinoID do
					// formul�rio
					form
							.setSetorComercialDestinoCD("");
					form
							.setSetorComercialDestinoID("");
					form
							.setNomeSetorComercialDestino("Setor comercial inexistente.");
				} else {
					SetorComercial objetoSetorComercial = (SetorComercial) Util
							.retonarObjetoDeColecao(colecaoPesquisa);
					form
							.setSetorComercialDestinoCD(String
									.valueOf(objetoSetorComercial.getCodigo()));
					form
							.setSetorComercialDestinoID(String
									.valueOf(objetoSetorComercial.getId()));
					form
							.setNomeSetorComercialDestino(objetoSetorComercial
									.getDescricao());
				}
			} else {
				// Limpa o campo setorComercialDestinoCD do formul�rio
				form.setSetorComercialDestinoCD("");
				form.setNomeSetorComercialDestino("Informe a localidade da inscri��o de destino.");
			}
		}

	}

}
