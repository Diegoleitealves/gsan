package gcom.gui.micromedicao.hidrometro;

import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.FiltroQuadra;
import gcom.cadastro.localidade.FiltroSetorComercial;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.Quadra;
import gcom.cadastro.localidade.SetorComercial;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.FiltrarHidrometroHelper;
import gcom.micromedicao.hidrometro.FiltroHidrometro;
import gcom.micromedicao.hidrometro.FiltroHidrometroSituacao;
import gcom.micromedicao.hidrometro.HidrometroSituacao;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ComparacaoTexto;
import gcom.util.filtro.ParametroSimples;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * < <Descri��o da Classe>>
 * 
 * @author Administrador
 * @created 5 de Setembro de 2005
 */
public class FiltrarHidrometroAction extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		HidrometroActionForm hidrometroActionForm = (HidrometroActionForm) actionForm;
		ActionForward retorno = null;

		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);
		String tela = (String) sessao.getAttribute("tela");
		
		if (tela != null && !tela.equals("")) {
			if (tela.equals("movimentarHidrometro")) {
				retorno = actionMapping.findForward("movimentarHidrometro");
			}
		} else {
			retorno = actionMapping.findForward("retornarFiltroHidrometro");
		}

		// Recupera os par�metros do form
		String numeroHidrometro = hidrometroActionForm.getNumeroHidrometro();
		String dataAquisicao = hidrometroActionForm.getDataAquisicao();
		String anoFabricacao = hidrometroActionForm.getAnoFabricacao();
		String indicadorMacromedidor = hidrometroActionForm.getIndicadorMacromedidor();
		String idHidrometroClasseMetrologica = hidrometroActionForm.getIdHidrometroClasseMetrologica();
		String idHidrometroMarca = hidrometroActionForm.getIdHidrometroMarca();
		String idHidrometroDiametro = hidrometroActionForm.getIdHidrometroDiametro();
		String idHidrometroCapacidade = hidrometroActionForm.getIdHidrometroCapacidade();
		String idHidrometroTipo = hidrometroActionForm.getIdHidrometroTipo();
		
		String idHidrometroSituacao = hidrometroActionForm.getIdHidrometroSituacao();
		String idLocalArmazenagem = hidrometroActionForm.getIdLocalArmazenagem();
		String fixo = hidrometroActionForm.getFixo();
		String faixaInicial = hidrometroActionForm.getFaixaInicial();
		String faixaFinal = hidrometroActionForm.getFaixaFinal();
		
		String idHidrometroRelojoaria = hidrometroActionForm.getIdHidrometroRelojoaria();
		String idLocalidade = hidrometroActionForm.getIdLocalidade();
		
		String idSetorComercial = hidrometroActionForm.getIdSetorComercial();
		String codigoSetorComercial = hidrometroActionForm.getCodigoSetorComercial();
		
		String idQuadra = hidrometroActionForm.getIdQuadra();
		String numeroQuadra = hidrometroActionForm.getNumeroQuadra();
		
		String vazaoTransicao = hidrometroActionForm.getVazaoTransicao();
		String vazaoNominal = hidrometroActionForm.getVazaoNominal();
		String vazaoMinima = hidrometroActionForm.getVazaoMinima();
		String notaFiscal = hidrometroActionForm.getNotaFiscal();
		String tempoGarantiaAnos = hidrometroActionForm.getTempoGarantiaAnos();
		
		FiltroHidrometro filtroHidrometro = 
			new FiltroHidrometro(FiltroHidrometro.NUMERO_HIDROMETRO);
		
		boolean peloMenosUmParametroInformado = false;
		
		if (idHidrometroSituacao != null && !idHidrometroSituacao.equals("-1") ) { 
			FiltroHidrometroSituacao filtroHidrometroSituacao = new FiltroHidrometroSituacao();
			filtroHidrometroSituacao.adicionarParametro( new ParametroSimples( FiltroHidrometroSituacao.ID, 
					hidrometroActionForm.getIdHidrometroSituacao() ) );
			Collection colecaoHidrometroSituacao =  this.getFachada().pesquisar(filtroHidrometroSituacao, 
					HidrometroSituacao.class.getName() );
			Object hidrometroSituacao = Util.retonarObjetoDeColecao(colecaoHidrometroSituacao);
			
			HidrometroSituacao situacao = (HidrometroSituacao) hidrometroSituacao;
			if (situacao.getDescricao().equals("INSTALADO") && idLocalidade.equals("")) {
				throw new ActionServletException("atencao.required", null, "Localidade");
			}
		}

		// Caso o fixo, a faixa inicial e faixa final seja diferente de null
		// ent�o ignora os outros parametros e faz a pesquisa do filtro por
		// esses 3 par�metros
		if (fixo != null && !fixo.equalsIgnoreCase("")) {
			
			if (faixaInicial != null && !faixaInicial.equalsIgnoreCase("")) {
				sessao.setAttribute("faixaInicial", faixaInicial);
			}
			
			if (faixaFinal != null && !faixaFinal.equalsIgnoreCase("")) {
				sessao.setAttribute("faixaFinal", faixaFinal);
			}
			
			sessao.setAttribute("fixo", fixo);
			sessao.removeAttribute("instalado");
			
			peloMenosUmParametroInformado = true;
			
		} else if( hidrometroActionForm.getIdLocalidade() != null &&
				!hidrometroActionForm.getIdLocalidade().equals("") ){
			
			
			sessao.setAttribute("instalado", true);
			FiltrarHidrometroHelper helper = new FiltrarHidrometroHelper();
			
			// Insere os par�metros informados no filtro
			if (numeroHidrometro != null && 
				!numeroHidrometro.trim().equalsIgnoreCase("")) {
				
				peloMenosUmParametroInformado = true;
				helper.setNumeroHidrometro(numeroHidrometro);
				
			}
			
			Date dataAquisicaoDate = Util.converteStringParaDate(dataAquisicao);
			Calendar dataAtual = new GregorianCalendar();
			
			if (dataAquisicao != null && !dataAquisicao.trim().equalsIgnoreCase("")) {
				
				// caso a data de aquisi��o seja menor que a data atual
				if (dataAquisicaoDate.after(new Date())) {
					throw new ActionServletException("atencao.data.aquisicao.nao.superior.data.corrente");
				}
				
				peloMenosUmParametroInformado = true;
				helper.setDataAquisicao(dataAquisicaoDate);
			}

//			if (anoFabricacao != null && !anoFabricacao.trim().equalsIgnoreCase("")) {
			
			if (anoFabricacao != null && !anoFabricacao.equals("")) {
			
				peloMenosUmParametroInformado = true;
				
				filtroHidrometro.adicionarParametro(
					new ParametroSimples(
						FiltroHidrometro.ANO_FABRICACAO, anoFabricacao));

				helper.setAnoFabricacao(anoFabricacao);
				
				int anoAtual = dataAtual.get(Calendar.YEAR);
				Integer anoFabricacaoInteger = new Integer(anoFabricacao);
				
				// caso o ano de fabrica��o seja maior que o atual
				if (anoFabricacaoInteger > anoAtual) {
					throw new ActionServletException("atencao.ano.fabricacao.nao.superior.data.corrente");
				}
				if(dataAquisicaoDate != null){
					Integer anoDataAquisicao = Util.getAno(dataAquisicaoDate);
					// caso a data de aquisi��o seja menor que o ano fabrica��o
					if (anoDataAquisicao < anoFabricacaoInteger) {
						throw new ActionServletException("atencao.ano.fabricacao.menor.ano.aquisicao");
	
					}
				}
			}

			if (indicadorMacromedidor != null && 
				!indicadorMacromedidor.trim().equalsIgnoreCase("") && 
				!indicadorMacromedidor.trim().equalsIgnoreCase("-1")) {
				
				helper.setIndicadorMacromedidor(indicadorMacromedidor);
			}

			if (idHidrometroClasseMetrologica != null && 
				Integer.parseInt(idHidrometroClasseMetrologica) > ConstantesSistema.NUMERO_NAO_INFORMADO) {
				
				peloMenosUmParametroInformado = true;
				helper.setIdHidrometroClasseMetrologica(idHidrometroClasseMetrologica);
			}

			if (idHidrometroMarca != null && 
				Integer.parseInt(idHidrometroMarca) > ConstantesSistema.NUMERO_NAO_INFORMADO) {
				
				peloMenosUmParametroInformado = true;
				helper.setIdHidrometroMarca(idHidrometroMarca);
			}
			
			if (idHidrometroDiametro != null && 
				Integer.parseInt(idHidrometroDiametro) > ConstantesSistema.NUMERO_NAO_INFORMADO) {
				
				peloMenosUmParametroInformado = true;
				helper.setIdHidrometroDiametro(idHidrometroDiametro);
			}

			if (idHidrometroCapacidade != null && 
				Integer.parseInt(idHidrometroCapacidade) > ConstantesSistema.NUMERO_NAO_INFORMADO) {
				
				peloMenosUmParametroInformado = true;
				helper.setIdHidrometroCapacidade(idHidrometroCapacidade);
			}
			
			if (idHidrometroTipo != null && 
				Integer.parseInt(idHidrometroTipo) > ConstantesSistema.NUMERO_NAO_INFORMADO) {
							
				peloMenosUmParametroInformado = true;
				helper.setIdHidrometroTipo(idHidrometroTipo);
			}
			
			if (idHidrometroRelojoaria != null && 
				Integer.parseInt(idHidrometroRelojoaria) > ConstantesSistema.NUMERO_NAO_INFORMADO) {
				
				peloMenosUmParametroInformado = true;
				helper.setIdHidrometroRelojoaria(idHidrometroRelojoaria);
			}

			if (idHidrometroSituacao != null && 
				Integer.parseInt(idHidrometroSituacao) > ConstantesSistema.NUMERO_NAO_INFORMADO) {
				
				peloMenosUmParametroInformado = true;
				helper.setIdHidrometroSituacao(idHidrometroSituacao);
			}

			if (idLocalArmazenagem != null && !idLocalArmazenagem.equals("")) {
				
				peloMenosUmParametroInformado = true;
				helper.setIdLocalArmazenagem(idLocalArmazenagem);
			}
			
			if (idLocalidade != null && !idLocalidade.equals("")) {
				
				FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
				
				filtroLocalidade.adicionarParametro(
					new ParametroSimples(
						FiltroLocalidade.ID, 
						hidrometroActionForm.getIdLocalidade()));

				filtroLocalidade.adicionarParametro(
					new ParametroSimples(
						FiltroLocalidade.INDICADORUSO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

				// Retorna localidade
				Collection colecaoPesquisa = 
					this.getFachada().pesquisar(filtroLocalidade,Localidade.class.getName());
				
				if(colecaoPesquisa != null && !colecaoPesquisa.isEmpty()){
					
					Localidade localidade =(Localidade) Util.retonarObjetoDeColecao(colecaoPesquisa);
					
					idLocalidade = localidade.getId().toString();
					peloMenosUmParametroInformado = true;
					helper.setIdLocalidade(idLocalidade);
					helper.setNomeLocalidade(localidade.getDescricao());
					
				}else{
					throw new ActionServletException("atencao.localidade.inexistente");
				}
			}
			
			if (codigoSetorComercial != null && !codigoSetorComercial.equals("")) {
				
				FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
				
				// Adiciona o id da localidade que est� no formul�rio para
				// compor a pesquisa.
				filtroSetorComercial.adicionarParametro(
					new ParametroSimples(
						FiltroSetorComercial.ID_LOCALIDADE, 
						hidrometroActionForm.getIdLocalidade()));

				// Adiciona o c�digo do setor comercial que esta no formul�rio
				// para compor a pesquisa.
				filtroSetorComercial.adicionarParametro(
					new ParametroSimples(
						FiltroSetorComercial.CODIGO_SETOR_COMERCIAL,
						hidrometroActionForm.getCodigoSetorComercial()));

				filtroSetorComercial.adicionarParametro(
					new ParametroSimples(
						FiltroSetorComercial.INDICADORUSO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

				// Retorna setorComercial
				Collection colecaoPesquisa = 
					this.getFachada().pesquisar(filtroSetorComercial,
						SetorComercial.class.getName());
				
				
				if(colecaoPesquisa != null && !colecaoPesquisa.isEmpty()){
					
					SetorComercial setorComercial =(SetorComercial) Util.retonarObjetoDeColecao(colecaoPesquisa);
					idSetorComercial = setorComercial.getId().toString();
					
					peloMenosUmParametroInformado = true;
					helper.setIdSetorComercial(idSetorComercial);
					helper.setCodigoSetorComercial(""+setorComercial.getCodigo());
					
				}else{
					throw new ActionServletException("atencao.setor_comercial.inexistente");
				}
			}
			
			if ( vazaoTransicao != null && !vazaoTransicao.equals("") ) {
				helper.setVazaoTransicao( vazaoTransicao );
			}
			
			if ( vazaoNominal != null && !vazaoNominal.equals("") ) {
				helper.setVazaoNominal( vazaoNominal );
			}
			
			if ( vazaoMinima != null && !vazaoMinima.equals("") ) {
				helper.setVazaoMinima( vazaoMinima );
			}
			
			if ( notaFiscal != null && !notaFiscal.equals("") ) {
				helper.setNotaFiscal( notaFiscal );
			}

			if ( tempoGarantiaAnos != null && !tempoGarantiaAnos.equals("") ) {
				helper.setTempoGarantiaAnos( tempoGarantiaAnos );
			}
			
			if (numeroQuadra != null && !numeroQuadra.equals("")) {
				
				FiltroQuadra filtroQuadra = new FiltroQuadra();

				// Adiciona o id do setor comercial que est� no formul�rio para
				// compor a pesquisa.
				filtroQuadra.adicionarParametro(
					new ParametroSimples(
						FiltroQuadra.ID_SETORCOMERCIAL, 
						hidrometroActionForm.getIdSetorComercial()));

				// Adiciona o n�mero da quadra que esta no formul�rio para
				// compor a pesquisa.
				filtroQuadra.adicionarParametro(
					new ParametroSimples(
						FiltroQuadra.NUMERO_QUADRA, 
						hidrometroActionForm.getNumeroQuadra()));

				filtroQuadra.adicionarParametro(
					new ParametroSimples(
						FiltroQuadra.INDICADORUSO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

				// Retorna quadra
				Collection colecaoPesquisa = 
					this.getFachada().pesquisar(filtroQuadra,
						Quadra.class.getName());
					
				if(colecaoPesquisa !=null && !colecaoPesquisa.isEmpty()){
						
					Quadra quadra = (Quadra) Util.retonarObjetoDeColecao(colecaoPesquisa);
					idQuadra = quadra.getId().toString();
					
					peloMenosUmParametroInformado = true;
					helper.setIdQuadra(idQuadra);
					helper.setNumeroQuadra(""+quadra.getNumeroQuadra());
					
				}else{
					throw new ActionServletException("atencao.quadra.inexistente");
				}
			}

			// Erro caso o usu�rio mandou filtrar sem nenhum par�metro
			if (!peloMenosUmParametroInformado) {
				throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
			}

			sessao.setAttribute("helper",helper);
			sessao.setAttribute("voltarFiltrar","1");
			
			sessao.removeAttribute("fixo");
			sessao.removeAttribute("faixaFinal");
			sessao.removeAttribute("faixaInicial");
			sessao.removeAttribute("filtroHidrometro");
			
		}else{
			// Insere os par�metros informados no filtro
			if (numeroHidrometro != null && 
				!numeroHidrometro.trim().equalsIgnoreCase("")) {
				
				peloMenosUmParametroInformado = true;
				filtroHidrometro.adicionarParametro(
					new ComparacaoTexto(
						FiltroHidrometro.NUMERO_HIDROMETRO, 
						numeroHidrometro));
			}
			
			Date dataAquisicaoDate = Util.converteStringParaDate(dataAquisicao);
			Calendar dataAtual = new GregorianCalendar();
			
			if (dataAquisicao != null && 
				!dataAquisicao.trim().equalsIgnoreCase("")) {
				
				// caso a data de aquisi��o seja menor que a data atual
				if (dataAquisicaoDate.after(new Date())) {
					throw new ActionServletException("atencao.data.aquisicao.nao.superior.data.corrente");
				}
				
				
				peloMenosUmParametroInformado = true;
				filtroHidrometro.adicionarParametro(
					new ParametroSimples(
						FiltroHidrometro.DATA_AQUISICAO, 
						dataAquisicaoDate));
			}

			if (anoFabricacao != null && 
				!anoFabricacao.trim().equalsIgnoreCase("")) {
				
				peloMenosUmParametroInformado = true;
				filtroHidrometro.adicionarParametro(
					new ParametroSimples(
						FiltroHidrometro.ANO_FABRICACAO, 
						anoFabricacao));
				
				int anoAtual = dataAtual.get(Calendar.YEAR);
				Integer anoFabricacaoInteger = new Integer(anoFabricacao);
				
				// caso o ano de fabrica��o seja maior que o atual
				if (anoFabricacaoInteger > anoAtual) {
					throw new ActionServletException("atencao.ano.fabricacao.nao.superior.data.corrente");
				}
				if(dataAquisicaoDate != null){
					
					Integer anoDataAquisicao = Util.getAno(dataAquisicaoDate);
					// caso a data de aquisi��o seja menor que o ano fabrica��o
					if (anoDataAquisicao < anoFabricacaoInteger) {
						throw new ActionServletException("atencao.ano.fabricacao.menor.ano.aquisicao");
	
					}
				}
			}

			if (indicadorMacromedidor != null && 
				!indicadorMacromedidor.trim().equalsIgnoreCase("") && 
				!indicadorMacromedidor.trim().equalsIgnoreCase("-1")) {
				
				filtroHidrometro.adicionarParametro(
					new ParametroSimples(
						FiltroHidrometro.INDICADOR_MACROMEDIDOR,
						indicadorMacromedidor));
			}

			if (idHidrometroClasseMetrologica != null && 
				Integer.parseInt(idHidrometroClasseMetrologica) > ConstantesSistema.NUMERO_NAO_INFORMADO) {
				
				peloMenosUmParametroInformado = true;
				filtroHidrometro.adicionarParametro(
					new ParametroSimples(
						FiltroHidrometro.HIDROMETRO_CLASSE_METROLOGICA_ID,
						idHidrometroClasseMetrologica));
			}

			if (idHidrometroMarca != null && 
				Integer.parseInt(idHidrometroMarca) > ConstantesSistema.NUMERO_NAO_INFORMADO) {
				
				peloMenosUmParametroInformado = true;
				filtroHidrometro.adicionarParametro(
					new ParametroSimples(
						FiltroHidrometro.HIDROMETRO_MARCA_ID,
						idHidrometroMarca));
			}

			if (idHidrometroDiametro != null && 
				Integer.parseInt(idHidrometroDiametro) > ConstantesSistema.NUMERO_NAO_INFORMADO) {
				
				peloMenosUmParametroInformado = true;
				filtroHidrometro.adicionarParametro(
					new ParametroSimples(
						FiltroHidrometro.HIDROMETRO_DIAMETRO_ID,
						idHidrometroDiametro));
			}

			if (idHidrometroCapacidade != null && 
				Integer.parseInt(idHidrometroCapacidade) > ConstantesSistema.NUMERO_NAO_INFORMADO) {
				
				peloMenosUmParametroInformado = true;
				filtroHidrometro.adicionarParametro(
					new ParametroSimples(
						FiltroHidrometro.HIDROMETRO_CAPACIDADE_ID,
						idHidrometroCapacidade));
			}
			if (idHidrometroTipo != null && 
				Integer.parseInt(idHidrometroTipo) > ConstantesSistema.NUMERO_NAO_INFORMADO) {
				
				peloMenosUmParametroInformado = true;
				filtroHidrometro.adicionarParametro(
					new ParametroSimples(
						FiltroHidrometro.HIDROMETRO_TIPO_ID, 
						idHidrometroTipo));
			}
			
			if (idHidrometroRelojoaria != null && 
				Integer.parseInt(idHidrometroRelojoaria) > ConstantesSistema.NUMERO_NAO_INFORMADO) {
				
				peloMenosUmParametroInformado = true;
				filtroHidrometro.adicionarParametro(
					new ParametroSimples(
						FiltroHidrometro.HIDROMETRO_RELOJOARIA_ID, 
						idHidrometroRelojoaria));
			}


			if (idHidrometroSituacao != null && 
				Integer.parseInt(idHidrometroSituacao) > ConstantesSistema.NUMERO_NAO_INFORMADO) {
				
				peloMenosUmParametroInformado = true;
				filtroHidrometro.adicionarParametro(
					new ParametroSimples(
						FiltroHidrometro.HIDROMETRO_SITUACAO_ID,
						idHidrometroSituacao));
			}

			if (idLocalArmazenagem != null && !idLocalArmazenagem.equals("")) {
				
				peloMenosUmParametroInformado = true;
				filtroHidrometro.adicionarParametro(
					new ParametroSimples(
						FiltroHidrometro.HIDROMETRO_LOCAL_ARMAZENAGEM_ID,
						idLocalArmazenagem));
			}
			
			if ( vazaoTransicao != null && !vazaoTransicao.equals("") ) {
				peloMenosUmParametroInformado = true;
				filtroHidrometro.adicionarParametro( 
					new ParametroSimples(
						FiltroHidrometro.VAZAO_TRANSICAO, 
						vazaoTransicao.replace( "," , "." ) ) );
			}
			
			if ( vazaoNominal != null && !vazaoNominal.equals("") ) {
				peloMenosUmParametroInformado = true;
				filtroHidrometro.adicionarParametro( 
					new ParametroSimples(
						FiltroHidrometro.VAZAO_NOMINAL, 
						vazaoNominal.replace( "," , "." ) ) );
			}

			if ( vazaoMinima != null && !vazaoMinima.equals("") ) {
				peloMenosUmParametroInformado = true;
				filtroHidrometro.adicionarParametro( 
					new ParametroSimples(
						FiltroHidrometro.VAZAO_MINIMA, 
						vazaoMinima.replace( "," , "." ) ) ) ;
			}
			
			if ( notaFiscal != null && !notaFiscal.equals("") ) {
				peloMenosUmParametroInformado = true;
				filtroHidrometro.adicionarParametro( 
					new ParametroSimples(
						FiltroHidrometro.NOTA_FISCAL,  
						notaFiscal));
			}
			
			if ( tempoGarantiaAnos != null && !tempoGarantiaAnos.equals("") ) {
				peloMenosUmParametroInformado = true;
				filtroHidrometro.adicionarParametro( 
					new ParametroSimples(
						FiltroHidrometro.TEMPO_GARANTIA_ANOS, 
						tempoGarantiaAnos));
			}
			// Erro caso o usu�rio mandou filtrar sem nenhum par�metro
			if (!peloMenosUmParametroInformado) {
					throw new ActionServletException(
						"atencao.filtro.nenhum_parametro_informado");
			}

			if (retorno.getName().equalsIgnoreCase("movimentarHidrometro")) {
				filtroHidrometro.setConsultaSemLimites(true);
			}

			// Manda o filtro pela sess�o para o ExibirManterHidrometroAction
			sessao.setAttribute("filtroHidrometro",filtroHidrometro);
			sessao.setAttribute("voltarFiltrar","1");
			
			sessao.removeAttribute("fixo");
			sessao.removeAttribute("faixaFinal");
			sessao.removeAttribute("faixaInicial");
		}

		sessao.setAttribute("filtrar_manter", "filtrar_manter");
		
		return retorno;
	}
}
