package gcom.gui.micromedicao;

import gcom.cadastro.empresa.Empresa;
import gcom.cadastro.empresa.FiltroEmpresa;
import gcom.cadastro.imovel.FiltroImovelPerfil;
import gcom.cadastro.imovel.ImovelPerfil;
import gcom.cadastro.localidade.FiltroGerenciaRegional;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.FiltroQuadra;
import gcom.cadastro.localidade.FiltroSetorComercial;
import gcom.cadastro.localidade.FiltroUnidadeNegocio;
import gcom.cadastro.localidade.GerenciaRegional;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cadastro.localidade.UnidadeNegocio;
import gcom.fachada.Fachada;
import gcom.faturamento.FaturamentoGrupo;
import gcom.faturamento.FiltroFaturamentoGrupo;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.FiltroLeiturista;
import gcom.micromedicao.Leiturista;
import gcom.micromedicao.leitura.FiltroLeituraAnormalidade;
import gcom.micromedicao.leitura.LeituraAnormalidade;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Pre- processamento para registrar leituras e anormalidades
 * 
 * @author S�vio Luiz
 */
public class ExibirRegistrarLeiturasAnormalidadesRelatorioAction extends GcomAction {
	private Collection colecaoPesquisa = null;

	private String localidadeID = null;

	private String setorComercialCD = null;
	
	//private String setorComercialID = null;

	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		// Seta o retorno
		ActionForward retorno = actionMapping
				.findForward("registrarLeiturasAnormalidadesRelatorio");

		// Obt�m a inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();
		
		//HttpSession sessao = httpServletRequest.getSession(false);

		// Cria��o das cole��es
		Collection faturamentosGrupos = null;

		FiltroFaturamentoGrupo filtroFaturamentoGrupo = new FiltroFaturamentoGrupo(FiltroFaturamentoGrupo.ID);

		faturamentosGrupos = fachada.pesquisar(filtroFaturamentoGrupo,
				FaturamentoGrupo.class.getName());

		if (faturamentosGrupos == null || faturamentosGrupos.isEmpty()) {
			// Nenhuma faturamento grupo cadastrada
			new ActionServletException("atencao.pesquisa.nenhumresultado",
					null, "faturamento grupo");
		}
		
		httpServletRequest.setAttribute("faturamentosGrupos",
				faturamentosGrupos);
		
		FiltroLeituraAnormalidade filtroLeituraAnormalidade = new FiltroLeituraAnormalidade(FiltroLeituraAnormalidade.DESCRICAO);
		filtroLeituraAnormalidade.adicionarParametro(new ParametroSimples(FiltroLeituraAnormalidade.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection colecaoAnormalidadesLeituras = this.getFachada().pesquisar(filtroLeituraAnormalidade, LeituraAnormalidade.class.getName());
		if(colecaoAnormalidadesLeituras == null || colecaoAnormalidadesLeituras.isEmpty()){
			new ActionServletException("atencao.pesquisa.nenhumresultado", null, "Anormalidade de Leitura");
		} else {
			httpServletRequest.setAttribute("colecaoAnormalidadesLeituras", colecaoAnormalidadesLeituras);
		}
		
		FiltroImovelPerfil filtroImovelPerfil = new FiltroImovelPerfil(FiltroImovelPerfil.DESCRICAO);
		filtroImovelPerfil.adicionarParametro(new ParametroSimples(FiltroImovelPerfil.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection colecaoPerfisImovel = this.getFachada().pesquisar(filtroImovelPerfil, ImovelPerfil.class.getName());
		if(colecaoPerfisImovel == null || colecaoPerfisImovel.isEmpty()){
			new ActionServletException("atencao.pesquisa.nenhumresultado", null, "Perfil do Im�vel");
		} else {
			httpServletRequest.setAttribute("colecaoPerfisImovel", colecaoPerfisImovel);
		}

		FiltroEmpresa filtroEmpresa = new FiltroEmpresa();
		filtroEmpresa.adicionarParametro( new ParametroSimples(
				FiltroEmpresa.INDICADOR_LEITURA,ConstantesSistema.SIM ) );	
    	filtroEmpresa.setCampoOrderBy(FiltroEmpresa.DESCRICAO);
    	Collection colecaoEmpresas = Fachada.getInstancia().pesquisar(filtroEmpresa, Empresa.class.getName());
    	
    	httpServletRequest.setAttribute("colecaoEmpresas", colecaoEmpresas);
    	
		String objetoConsulta = (String) httpServletRequest
		.getParameter("objetoConsulta");

		String inscricaoTipo = (String) httpServletRequest
				.getParameter("inscricaoTipo");
		
		RegistrarLeiturasAnormalidadesRelatorioActionForm form = (RegistrarLeiturasAnormalidadesRelatorioActionForm) actionForm;
		
		this.pesquisarGerenciaRegional(httpServletRequest);
		this.pesquisarUnidadeNegocio(httpServletRequest,form);
		
		if (objetoConsulta != null && !objetoConsulta.trim().equalsIgnoreCase("")
				|| ( inscricaoTipo != null && !inscricaoTipo.trim().equalsIgnoreCase("") ) ) {
		
			switch (Integer.parseInt(objetoConsulta)) {
			// Localidade
			case 1:
		
				pesquisarLocalidade(inscricaoTipo,
						form, fachada,
						httpServletRequest);
		
				break;
			// Setor Comercial
			case 2:
		
				pesquisarLocalidade(inscricaoTipo,
						form, fachada,
						httpServletRequest);
		
				pesquisarSetorComercial(inscricaoTipo,
						form, fachada,
						httpServletRequest);
		
				break;
				
			default:
				break;
			}
		}
		
		if ( form.getIdFirma() != null && !form.getIdFirma().equals( "" ) ){
			pesquisarLeituristasPorEmpresa( httpServletRequest, form );
		}

		return retorno;
	}
	
/*	private void pesquisarLeiturista(String leituristaId, 
			RegistrarLeiturasAnormalidadesRelatorioActionForm form, 
			Fachada fachada, HttpServletRequest httpServletRequest) {
		
		// Verifica se o c�digo do Leiturista foi digitado
        if (leituristaId != null
            && !leituristaId.trim().equals("")
            && Integer.parseInt(leituristaId) > 0) {
            
            //Recupera o leiturista informado pelo usu�rio
            FiltroLeiturista filtroLeiturista = new FiltroLeiturista();
            filtroLeiturista.adicionarCaminhoParaCarregamentoEntidade(FiltroLeiturista.FUNCIONARIO);
            filtroLeiturista.adicionarCaminhoParaCarregamentoEntidade(FiltroLeiturista.CLIENTE);
            filtroLeiturista.adicionarParametro(new ParametroSimples(
                    FiltroLeiturista.ID, leituristaId));
            filtroLeiturista.adicionarParametro(new ParametroSimples(
                    FiltroLeiturista.INDICADOR_USO,
                    ConstantesSistema.INDICADOR_USO_ATIVO));

            Collection leituristaEncontrado = this.getFachada().pesquisar(filtroLeiturista,
                    Leiturista.class.getName());
            
            //Caso o leiturista informado pelo usu�rio esteja cadastrado no sistema
            //Seta os dados do leiturista no form
            //Caso contr�rio seta as informa��es de leiturista para vazio 
            //e indica ao usu�rio que o leiturista n�o existe 
            
            if (leituristaEncontrado != null && leituristaEncontrado.size() > 0) {
                //leiturista foi encontrado
                Leiturista leiturista = (Leiturista) ((List) leituristaEncontrado).get(0); 
                form.setIdLeiturista("" + 
                    leiturista.getId());
                if (leiturista.getFuncionario() != null){
                    form.setNomeLeiturista(leiturista.getFuncionario().getNome());                 
                } else if (leiturista.getCliente() != null){
                    form.setNomeLeiturista(leiturista.getCliente().getNome());
                }
                httpServletRequest.setAttribute("idLeituristaEncontrado","true");
                httpServletRequest.setAttribute("nomeCampo","codigoSetorComercial");
                httpServletRequest.setAttribute( "idLeituristaEncontrado", "" );
            } else {
                //o leiturista n�o foi encontrado
                form.setIdLeiturista("");
                form.setNomeLeiturista("LEITURISTA INEXISTENTE");
                httpServletRequest.removeAttribute("idLeituristaEncontrado");
                httpServletRequest.setAttribute("nomeCampo","idLeiturista");
            }
        }
	}*/

	private void pesquisarLocalidade(String inscricaoTipo,
			RegistrarLeiturasAnormalidadesRelatorioActionForm form,
			Fachada fachada, HttpServletRequest httpServletRequest) {

		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();

		if (inscricaoTipo.equalsIgnoreCase("origem")) {
			form.setInscricaoTipo("origem");
			// Recebe o valor do campo localidadeOrigemID do formul�rio.
			localidadeID = (String) form
					.getLocalidadeOrigemID();

			filtroLocalidade.adicionarParametro(new ParametroSimples(
					FiltroLocalidade.ID, localidadeID));

			filtroLocalidade.adicionarParametro(new ParametroSimples(
					FiltroLocalidade.INDICADORUSO,
					ConstantesSistema.INDICADOR_USO_ATIVO));

			// Retorna localidade
			colecaoPesquisa = fachada.pesquisar(filtroLocalidade,
					Localidade.class.getName());

			if (colecaoPesquisa == null || colecaoPesquisa.isEmpty()) {
				// Localidade nao encontrada
				// Limpa os campos localidadeOrigemID e nomeLocalidadeOrigem do
				// formul�rio
				form.setLocalidadeOrigemID("");
				form
				.setNomeLocalidadeOrigem("Localidade inexistente.");
				form.setLocalidadeDestinoID("");
				form.setNomeLocalidadeDestino("");
				httpServletRequest.setAttribute("corLocalidadeOrigem",
						"exception");
				httpServletRequest.setAttribute("nomeCampo","localidadeOrigemID");
			} else {
				Localidade objetoLocalidade = (Localidade) Util
						.retonarObjetoDeColecao(colecaoPesquisa);
				form.setLocalidadeOrigemID(String
						.valueOf(objetoLocalidade.getId()));
				form
						.setNomeLocalidadeOrigem(objetoLocalidade
								.getDescricao());
				httpServletRequest.setAttribute("corLocalidadeOrigem", "valor");
				httpServletRequest.setAttribute("nomeCampo","setorComercialOrigemCD");
				//destino
				form.setLocalidadeDestinoID(String
						.valueOf(objetoLocalidade.getId()));
				form
						.setNomeLocalidadeDestino(objetoLocalidade
								.getDescricao());
				httpServletRequest.setAttribute("corLocalidadeDestino", "valor");
			}
		} else {
			// Recebe o valor do campo localidadeDestinoID do formul�rio.
			localidadeID = (String) form
					.getLocalidadeDestinoID();

			filtroLocalidade.adicionarParametro(new ParametroSimples(
					FiltroLocalidade.ID, localidadeID));

			filtroLocalidade.adicionarParametro(new ParametroSimples(
					FiltroLocalidade.INDICADORUSO,
					ConstantesSistema.INDICADOR_USO_ATIVO));

			// Retorna localidade
			colecaoPesquisa = fachada.pesquisar(filtroLocalidade,
					Localidade.class.getName());
			
			form.setInscricaoTipo("destino");
			
			if (colecaoPesquisa == null || colecaoPesquisa.isEmpty()) {
				// Localidade nao encontrada
				// Limpa os campos localidadeDestinoID e nomeLocalidadeDestino
				// do formul�rio
				form.setLocalidadeDestinoID("");
				form
						.setNomeLocalidadeDestino("Localidade inexistente.");
				httpServletRequest.setAttribute("corLocalidadeDestino",
						"exception");
				httpServletRequest.setAttribute("nomeCampo","localidadeDestinoID");
			} else {
				Localidade objetoLocalidade = (Localidade) Util
						.retonarObjetoDeColecao(colecaoPesquisa);
				form.setLocalidadeDestinoID(String
						.valueOf(objetoLocalidade.getId()));
				form
						.setNomeLocalidadeDestino(objetoLocalidade
								.getDescricao());
				httpServletRequest
						.setAttribute("corLocalidadeDestino", "valor");
				httpServletRequest.setAttribute("nomeCampo","setorComercialDestinoCD");
			}
		}

	}

	private void pesquisarSetorComercial(String inscricaoTipo,
			RegistrarLeiturasAnormalidadesRelatorioActionForm form,
			Fachada fachada, HttpServletRequest httpServletRequest) {

		FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();

		if (inscricaoTipo.equalsIgnoreCase("origem")) {
			form.setInscricaoTipo("origem");
			// Recebe o valor do campo localidadeOrigemID do formul�rio.
			localidadeID = (String) form
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
					httpServletRequest.setAttribute("corSetorComercialOrigem",
							"exception");
					httpServletRequest.setAttribute("nomeCampo","setorComercialOrigemCD");
					//destino
					form
							.setSetorComercialDestinoCD("");
					form
							.setSetorComercialDestinoID("");
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
					httpServletRequest.setAttribute("nomeCampo","quadraOrigemNM");
					//setorComercialOrigem
					
					//setorComercialDestino
					form
							.setSetorComercialDestinoCD(String
									.valueOf(objetoSetorComercial.getCodigo()));
					form
							.setSetorComercialDestinoID(String
									.valueOf(objetoSetorComercial.getId()));
					form
							.setNomeSetorComercialDestino(objetoSetorComercial
									.getDescricao());
					//setorComercialDestino					
					httpServletRequest.setAttribute("corSetorComercialDestino",
							"valor");
				}
			} else {
				// Limpa o campo setorComercialOrigemCD do formul�rio
				form.setSetorComercialOrigemCD("");
				form
						.setNomeSetorComercialOrigem("Informe a localidade da inscri��o de origem.");
				httpServletRequest.setAttribute("corSetorComercialOrigem",
						"exception");
			}
		} else {
			
			form.setInscricaoTipo("destino");
			
			// Recebe o valor do campo localidadeDestinoID do formul�rio.
			localidadeID = (String) form
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
					httpServletRequest.setAttribute("corSetorComercialDestino",
							"exception");
					httpServletRequest.setAttribute("nomeCampo","setorComercialDestinoCD");
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
					httpServletRequest.setAttribute("corSetorComercialDestino",
							"valor");
					httpServletRequest.setAttribute("nomeCampo","quadraDestinoNM");
				}
			} else {
				// Limpa o campo setorComercialDestinoCD do formul�rio
				form.setSetorComercialDestinoCD("");
				form
						.setNomeSetorComercialDestino("Informe a localidade da inscri��o de destino.");
				httpServletRequest.setAttribute("corSetorComercialDestino",
						"exception");
			}
		}

	}
	
	private void pesquisarGerenciaRegional(HttpServletRequest httpServletRequest){
		
		FiltroGerenciaRegional filtroGerenciaRegional = new FiltroGerenciaRegional();
		
		filtroGerenciaRegional.setConsultaSemLimites(true);
		filtroGerenciaRegional.setCampoOrderBy(FiltroGerenciaRegional.NOME);
		filtroGerenciaRegional.adicionarParametro(
				new ParametroSimples(FiltroQuadra.INDICADORUSO, 
				ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection colecaoGerenciaRegional = 
			this.getFachada().pesquisar(filtroGerenciaRegional,GerenciaRegional.class.getName());


		if (colecaoGerenciaRegional == null || colecaoGerenciaRegional.isEmpty()) {
			throw new ActionServletException("atencao.naocadastrado", null,"Ger�ncia Regional");
		} else {
			httpServletRequest.setAttribute("colecaoGerenciaRegional",colecaoGerenciaRegional);
		}
	}
	
	private void pesquisarUnidadeNegocio(HttpServletRequest httpServletRequest,
			RegistrarLeiturasAnormalidadesRelatorioActionForm form){
		
		FiltroUnidadeNegocio filtroUnidadeNegocio = new FiltroUnidadeNegocio();
		
		filtroUnidadeNegocio.setConsultaSemLimites(true);
		filtroUnidadeNegocio.setCampoOrderBy(FiltroUnidadeNegocio.NOME);
		
		if(form.getGerenciaRegional() != null && 
			!form.getGerenciaRegional().equals(""+ConstantesSistema.NUMERO_NAO_INFORMADO)){
			
			filtroUnidadeNegocio.adicionarParametro(
				new ParametroSimples(FiltroUnidadeNegocio.ID_GERENCIA, 
					form.getGerenciaRegional()));		
		}

		filtroUnidadeNegocio.adicionarParametro(
				new ParametroSimples(FiltroUnidadeNegocio.INDICADOR_USO, 
				ConstantesSistema.INDICADOR_USO_ATIVO));		

		Collection colecaoUnidadeNegocio = 
			this.getFachada().pesquisar(filtroUnidadeNegocio,UnidadeNegocio.class.getName());


		if (colecaoUnidadeNegocio == null || colecaoUnidadeNegocio.isEmpty()) {
			throw new ActionServletException("atencao.naocadastrado", null,"Unidade de Neg�cio");
		} else {
			httpServletRequest.setAttribute("colecaoUnidadeNegocio",colecaoUnidadeNegocio);
		}
	}
	
	private void pesquisarLeituristasPorEmpresa(HttpServletRequest httpServletRequest,
			RegistrarLeiturasAnormalidadesRelatorioActionForm form ){
		
		Collection colecaoLeiturista = new ArrayList();		

		FiltroLeiturista filtroLeiturista = 
			new FiltroLeiturista( FiltroLeiturista.ID );
		filtroLeiturista.adicionarParametro(
				new ParametroSimples( FiltroLeiturista.EMPRESA_ID, form.getIdFirma() ) );			
		filtroLeiturista.adicionarParametro(
				new ParametroSimples( FiltroLeiturista.INDICADOR_USO, ConstantesSistema.SIM ) );
		filtroLeiturista.adicionarCaminhoParaCarregamentoEntidade(
				FiltroLeiturista.CLIENTE );
		filtroLeiturista.adicionarCaminhoParaCarregamentoEntidade(
				FiltroLeiturista.FUNCIONARIO );
		
		filtroLeiturista.setCampoOrderBy( FiltroLeiturista.CLIENTE_NOME );

		Collection colecao = 
			Fachada.getInstancia().pesquisar(
					filtroLeiturista, Leiturista.class.getName() );

		if ( colecao != null && !colecao.isEmpty() ) {
			
			Iterator it = colecao.iterator();
			
			while ( it.hasNext() ) {
				
				Leiturista leitu = (Leiturista) it.next();
				
				DadosLeiturista dadosLeiu = null;
				
				if ( leitu.getFuncionario() != null ) {
					dadosLeiu = 
						new DadosLeiturista(
								leitu.getId(), leitu.getFuncionario().getNome() );
				} else {
					dadosLeiu = 
						new DadosLeiturista( leitu.getId(), leitu.getCliente().getNome() );
				}
				
				colecaoLeiturista.add(dadosLeiu);
			}
		}
		
		httpServletRequest.setAttribute("colecaoLeiturista", colecaoLeiturista);		
	}		
}
