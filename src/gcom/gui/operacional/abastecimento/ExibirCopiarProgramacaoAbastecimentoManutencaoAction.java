package gcom.gui.operacional.abastecimento;

import gcom.cadastro.geografico.Bairro;
import gcom.cadastro.geografico.BairroArea;
import gcom.cadastro.geografico.FiltroBairro;
import gcom.cadastro.geografico.FiltroBairroArea;
import gcom.cadastro.geografico.FiltroMunicipio;
import gcom.cadastro.geografico.Municipio;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
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
 * [UC0414] - Informar Programa��o de Abastecimento e Manuten��o
 * 
 * [SB0008] - Copiar Programa��o de Abastecimento
 *
 * @author Rafael Pinto
 * 
 * @date 30/11/2006
 */

public class ExibirCopiarProgramacaoAbastecimentoManutencaoAction extends GcomAction {
	
	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		ActionForward retorno = actionMapping.findForward("copiarProgramacaoAbastecimentoManutencao");

		InformarProgramacaoAbastecimentoManutencaoActionForm form = 
			(InformarProgramacaoAbastecimentoManutencaoActionForm) actionForm;
		
		String tipoOperacao = httpServletRequest.getParameter("tipoOperacao");
		
		if(tipoOperacao != null && !tipoOperacao.equals("") && tipoOperacao.equals("C")){
			
			String mesAnoReferencia = form.getMesAnoReferencia();
			String idMunicipio = form.getMunicipioCopiar();
			String idBairro = form.getBairroCopiar();
			String areaBairro = form.getAreaBairroCopiar();
			
			Collection colecaoProgramacaoAbastecimento = 
				this.getFachada().consultarProgramacaoAbastecimento(idMunicipio, 
					idBairro,areaBairro, mesAnoReferencia);
			
			if(colecaoProgramacaoAbastecimento == null || colecaoProgramacaoAbastecimento.isEmpty()){
				
				this.pesquisarAreaBairro(new Integer(areaBairro),form);
				
				String[] msg = new String[2];
				
				msg[0] = form.getNomeAreaBairroCopiar();
				msg[1] = form.getMesAnoReferencia();
				
				throw new ActionServletException("atencao.ja_existe_programacao_abastecimento_na_area", 
					null,msg);				
			}
			
			form.setAbastecimentoProgramacao(colecaoProgramacaoAbastecimento);
			httpServletRequest.setAttribute("fechaPopup", "true");
			
		}else{
			
			// Flag indicando que o usu�rio fez uma consulta a partir da tecla Enter
			String objetoConsulta = httpServletRequest.getParameter("objetoConsulta");
			String tipoConsulta = httpServletRequest.getParameter("tipoConsulta");

			//[UC0075] - Pesquisar Municipio
			if (objetoConsulta != null && !objetoConsulta.trim().equals("") && 
				objetoConsulta.trim().equals("1")) {

				// Faz a consulta de Municipio
				this.pesquisarMunicipio(form);

			//[UC0141] - Pesquisar Bairro
			}else if (objetoConsulta != null && !objetoConsulta.trim().equals("") && 
					objetoConsulta.trim().equals("2")) {

				// Faz a consulta de Documento Cobran�a
				this.pesquisarBairro(form,httpServletRequest);
				
			}else if (tipoConsulta != null && !tipoConsulta.equals("")) {
					
				String idCampoEnviarDados = httpServletRequest.getParameter("idCampoEnviarDados");
				String descricaoCampoEnviarDados = httpServletRequest.getParameter("descricaoCampoEnviarDados");
				
				if (tipoConsulta.equals("municipio")) {
							
					form.setMunicipioCopiar(idCampoEnviarDados);
					form.setNomeMunicipioCopiar(descricaoCampoEnviarDados);

					form.setBairroCopiar(null);
					form.setNomeBairroCopiar(null);
					
					form.setAreaBairroCopiar(null);
					form.setNomeAreaBairroCopiar(null);
					

				}else if (tipoConsulta.equals("bairro")) {
							
					form.setBairroCopiar(idCampoEnviarDados);
					form.setNomeBairroCopiar(descricaoCampoEnviarDados);
					
					this.montarAreaBairroPorId(httpServletRequest,new Integer(idCampoEnviarDados));
				}		
			
			}else{
				this.resetPopup(form);
			}
			
			this.setaRequest(httpServletRequest,form);
		}
		
		return retorno;
	}
	
	/**
	 * Reseta informa��es vindas do popup 
	 *
	 * @author Rafael Pinto
	 * @date 14/11/2006
	 *
	 * @param InformarProgramacaoAbastecimentoManutencaoActionForm
	 */
	private void resetPopup(InformarProgramacaoAbastecimentoManutencaoActionForm form) {

		form.setMunicipioCopiar(null);
		form.setNomeMunicipioCopiar(null);
		
		form.setBairroCopiar(null);
		form.setNomeBairroCopiar(null);
		
		form.setAreaBairroCopiar(null);
		form.setNomeAreaBairroCopiar(null);
	}	
	
	/**
	 * [UC0075] - Pesquisar Municipio
	 * 
	 * [FS0001] - Verificar exist�ncia do munic�pio
	 *
	 * @author Rafael Pinto
	 * @date 13/11/2006
	 */
	private void pesquisarMunicipio(InformarProgramacaoAbastecimentoManutencaoActionForm form) {
		
		FiltroMunicipio filtroMunicipio = new FiltroMunicipio();

		filtroMunicipio.adicionarParametro(
			new ParametroSimples(FiltroMunicipio.ID,new Integer(form.getMunicipioCopiar())));

		filtroMunicipio.adicionarParametro(
			new ParametroSimples(FiltroMunicipio.INDICADOR_USO,ConstantesSistema.INDICADOR_USO_ATIVO));

		// Pesquisa de acordo com os par�metros informados no filtro
		Collection colecaoMunicipio = 
			this.getFachada().pesquisar(filtroMunicipio,Municipio.class.getName());

		// Verifica se a pesquisa retornou algum objeto para a cole��o
		if (colecaoMunicipio != null && !colecaoMunicipio.isEmpty()) {

			// Obt�m o objeto da cole��o pesquisada
			Municipio municipio = 
				(Municipio) Util.retonarObjetoDeColecao(colecaoMunicipio);

			form.setMunicipioCopiar(municipio.getId().toString());
			form.setNomeMunicipioCopiar(municipio.getNome());
			

		} else {
			form.setMunicipioCopiar(null);
			form.setNomeMunicipioCopiar("Munic�pio inexistente");
		}
	}
	
	/**
	 * [UC0075] - Pesquisar Bairro
	 * 
	 * [FS0002] - Verificar informa��o do municipio
	 * [FS0003] - Verificar exist�ncia do bairro
	 *
	 * @author Rafael Pinto
	 * @date 13/11/2006
	 */
	private void pesquisarBairro(InformarProgramacaoAbastecimentoManutencaoActionForm form,
		HttpServletRequest httpServletRequest) {
		
		//[FS0002] - Verificar informa��o do municipio
		String codigoMunicipio = form.getMunicipioCopiar();
		if(codigoMunicipio == null || codigoMunicipio.equals("")){
			throw new ActionServletException("atencao.filtrar_informar_municipio");	
		}
		
		FiltroBairro filtroBairro = new FiltroBairro();

		filtroBairro.adicionarParametro(
			new ParametroSimples(FiltroBairro.CODIGO,new Integer(form.getBairroCopiar())));

		filtroBairro.adicionarParametro(
				new ParametroSimples(FiltroBairro.MUNICIPIO_ID,new Integer(codigoMunicipio)));

		filtroBairro.adicionarParametro(
			new ParametroSimples(FiltroBairro.INDICADOR_USO,ConstantesSistema.INDICADOR_USO_ATIVO));
		

		// Pesquisa de acordo com os par�metros informados no filtro
		Collection colecaoBairro = 
			this.getFachada().pesquisar(filtroBairro,Bairro.class.getName());

		// Verifica se a pesquisa retornou algum objeto para a cole��o
		if (colecaoBairro != null && !colecaoBairro.isEmpty()) {

			// Obt�m o objeto da cole��o pesquisada
			Bairro bairro = 
				(Bairro) Util.retonarObjetoDeColecao(colecaoBairro);

			this.montarAreaBairroPorId(httpServletRequest,new Integer(bairro.getId()));			
			
			form.setBairroCopiar(""+bairro.getCodigo());
			form.setNomeBairroCopiar(bairro.getNome());

		} else {
			form.setBairroCopiar(null);
			form.setNomeBairroCopiar("Bairro inexistente");
		}
	}	
	
	/**
	 * Pesquisa Area do Bairro pelo Id 
	 *
	 * @author Rafael Pinto
	 * @date 13/11/2006
	 */
	private void montarAreaBairroPorId(HttpServletRequest request,Integer id){
		
		// Parte que passa as cole��es necess�rias no jsp
		Collection colecaoAreaBairro = new ArrayList();
			
		FiltroBairroArea filtroBairroArea = new FiltroBairroArea();
		filtroBairroArea.adicionarParametro(new ParametroSimples(FiltroBairroArea.ID_BAIRRO,id));

		colecaoAreaBairro = 
			this.getFachada().pesquisar(filtroBairroArea, BairroArea.class.getName());

		if (colecaoAreaBairro != null && !colecaoAreaBairro.isEmpty()) {
			request.setAttribute("colecaoAreaBairroCopiar", colecaoAreaBairro);
		} else {
			throw new ActionServletException("atencao.naocadastrado", null,"�rea do Bairro");
		}
		
	}
	
	/**
	 * Seta os request com os id encontrados 
	 *
	 * @author Rafael Pinto
	 * @date 01/12/2006
	 */
	private void setaRequest(HttpServletRequest httpServletRequest,
			InformarProgramacaoAbastecimentoManutencaoActionForm form){
		
		//Municipio
		if(form.getMunicipioCopiar() != null && 
			!form.getMunicipioCopiar().equals("") && 
			form.getNomeMunicipioCopiar() != null && 
			!form.getNomeMunicipioCopiar().equals("")){
			
			httpServletRequest.setAttribute("municipioEncontrado","true");			
		}

		//Bairro
		if(form.getBairroCopiar() != null && !form.getBairroCopiar().equals("") && 
			form.getNomeBairroCopiar() != null && !form.getNomeBairroCopiar().equals("")){
					
			httpServletRequest.setAttribute("bairroEncontrado","true");
		}	
	}	
	
	
	/**
	 * Pesquisa Area do Bairro pelo Id 
	 *
	 * @author Rafael Pinto
	 * @date 13/11/2006
	 */
	private void pesquisarAreaBairro(Integer idArea,InformarProgramacaoAbastecimentoManutencaoActionForm form){
		
		// Parte que passa as cole��es necess�rias no jsp
		Collection colecaoAreaBairro = new ArrayList();
			
		FiltroBairroArea filtroBairroArea = new FiltroBairroArea();
		filtroBairroArea.adicionarParametro(new ParametroSimples(FiltroBairroArea.ID,idArea));

		colecaoAreaBairro = 
			this.getFachada().pesquisar(filtroBairroArea, BairroArea.class.getName());

		if (colecaoAreaBairro != null && !colecaoAreaBairro.isEmpty()) {
			
			BairroArea bairroArea =
				(BairroArea) Util.retonarObjetoDeColecao(colecaoAreaBairro);
			
			form.setNomeAreaBairroCopiar(bairroArea.getNome());
			
		} else {
			throw new ActionServletException("atencao.naocadastrado", null,"�rea do Bairro");
		}
		
	}
}
