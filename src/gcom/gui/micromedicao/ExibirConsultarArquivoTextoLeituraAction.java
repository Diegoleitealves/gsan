package gcom.gui.micromedicao;

import gcom.cadastro.empresa.Empresa;
import gcom.cadastro.empresa.FiltroEmpresa;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.faturamento.FaturamentoGrupo;
import gcom.faturamento.FiltroFaturamentoGrupo;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.ArquivoTextoRoteiroEmpresa;
import gcom.micromedicao.FiltroLeiturista;
import gcom.micromedicao.FiltroServicoTipoCelular;
import gcom.micromedicao.FiltroSituacaoTransmissaoLeitura;
import gcom.micromedicao.Leiturista;
import gcom.micromedicao.ServicoTipoCelular;
import gcom.micromedicao.SituacaoTransmissaoLeitura;
import gcom.seguranca.acesso.PermissaoEspecial;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action que define o pr�-processamento da p�gina de pesquisa de cliente
 * 
 * @author Thiago Ten�rio
 * @created 25 de Abril de 2005
 */
public class ExibirConsultarArquivoTextoLeituraAction extends GcomAction {

	/**
	 * 
	 * 
	 * @param actionMapping
	 *            Description of the Parameter
	 * @param actionForm
	 *            Description of the Parameter
	 * @param httpServletRequest
	 *            Description of the Parameter
	 * @param httpServletResponse
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		// Obt�m a inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();
		
		SistemaParametro sistemaParametro = this.getSistemaParametro();

		HttpSession sessao = httpServletRequest.getSession(false);

		ConsultarArquivoTextoLeituraActionForm consultarArquivoTextoLeituraActionForm = (ConsultarArquivoTextoLeituraActionForm) actionForm;

		ArquivoTextoRoteiroEmpresa arquivoTextoRoteiroEmpresa = new ArquivoTextoRoteiroEmpresa();

		Collection colecaoLeiturista = new ArrayList();

		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

		if (httpServletRequest.getParameter("menu") != null) {

			consultarArquivoTextoLeituraActionForm.setEmpresaID(""
					+ usuarioLogado.getEmpresa().getId());
			consultarArquivoTextoLeituraActionForm.setGrupoFaturamentoID("");
			consultarArquivoTextoLeituraActionForm.setMesAno("");
			consultarArquivoTextoLeituraActionForm.setSituaTransmLeitura("");

		} else {

			if (httpServletRequest.getParameter("paginacao") == null
					&& sessao.getAttribute("filtrar") == null) {

				String atualizar = httpServletRequest.getParameter("atualizar");

				if (atualizar != null && !atualizar.equals("")) {
					sessao.setAttribute("atualizar", atualizar);
				} else {
					sessao.removeAttribute("atualizar");
				}

			}
		}

		sessao.removeAttribute("permissao");
		if (usuarioLogado.getEmpresa().getIndicadorEmpresaPrincipal().equals(
				new Short("1"))) {
			sessao.setAttribute("permissao", "1");
		} else {
			sessao.setAttribute("permissao", "2");
		}

		sessao.removeAttribute("equipeAtualizar");

		// Parte que passa as cole��es da Empresa necess�rias no jsp
		FiltroEmpresa filtroEmpresa = new FiltroEmpresa();
		
		filtroEmpresa.adicionarParametro(
				new ParametroSimples(FiltroEmpresa.INDICADOR_LEITURA,
						ConstantesSistema.SIM));
		
		filtroEmpresa.setCampoOrderBy(FiltroEmpresa.ID);
		Collection colecaoEmpresa = fachada.pesquisar(filtroEmpresa,
				Empresa.class.getName());

		if (colecaoEmpresa != null && !colecaoEmpresa.isEmpty()) {
			sessao.setAttribute("colecaoEmpresa", colecaoEmpresa);
		} else {
			throw new ActionServletException("atencao.naocadastrado", null,
					"Empresa");
		}

		// Parte que passa as cole��es da Grupo de Faturamento necess�rias no
		// jsp
		FiltroFaturamentoGrupo filtroFaturamentoGrupo = new FiltroFaturamentoGrupo();
		filtroFaturamentoGrupo.setCampoOrderBy(FiltroFaturamentoGrupo.ID);
		Collection colecaoFaturamentoGrupo = fachada.pesquisar(
				filtroFaturamentoGrupo, FaturamentoGrupo.class.getName());

		if (colecaoFaturamentoGrupo != null
				&& !colecaoFaturamentoGrupo.isEmpty()) {
			sessao.setAttribute("colecaoFaturamentoGrupo",
					colecaoFaturamentoGrupo);
		} else {
			throw new ActionServletException("atencao.naocadastrado", null,
					"Grupo de Faturamento");
		}
		
		//Parte que realiza a pesquisa pelos SERVICO_TIPO_CELULAR cujo indicadores de uso sejam 1 e colocar no JSP
		FiltroServicoTipoCelular filtroServicoTipoCelular = new FiltroServicoTipoCelular();
		filtroServicoTipoCelular.adicionarParametro(new ParametroSimples(FiltroServicoTipoCelular.INDICADOR_USO,
				                                                         ConstantesSistema.INDICADOR_USO_ATIVO));
		
	    Collection colecaoServicoTipoCelular = fachada.pesquisar(filtroServicoTipoCelular,ServicoTipoCelular.class.getName());
	    
	    if(colecaoServicoTipoCelular != null && !colecaoServicoTipoCelular.isEmpty()){
	    	sessao.setAttribute("colecaoServicoTipoCelular",colecaoServicoTipoCelular);
	    	
	    	if(consultarArquivoTextoLeituraActionForm.getServicoTipoCelular() == null ||
	    		consultarArquivoTextoLeituraActionForm.getServicoTipoCelular().equals("") ||
	    		consultarArquivoTextoLeituraActionForm.getServicoTipoCelular().equals("-1")){
	    		
	    		if(sistemaParametro.getNomeAbreviadoEmpresa().equalsIgnoreCase("COMPESA")){
	    			consultarArquivoTextoLeituraActionForm.setServicoTipoCelular("2");
	    		}else{
	    			consultarArquivoTextoLeituraActionForm.setServicoTipoCelular("1");
	    		}
	    		/*sessao.setAttribute("servicoTipoCelular","1");*/
	    	}
	    	
	    }
	    
	    if(sessao.getAttribute("colecaoSituacaoTransmissaoLeitur")==null){
	    	FiltroSituacaoTransmissaoLeitura filtroSituacaoTransmissaoLeitura = 
	    		new FiltroSituacaoTransmissaoLeitura();
	    	
	    	filtroSituacaoTransmissaoLeitura.setCampoOrderBy(FiltroSituacaoTransmissaoLeitura.ID);
	    	
	    	filtroSituacaoTransmissaoLeitura.adicionarParametro(
	    			new ParametroSimples(FiltroSituacaoTransmissaoLeitura.INDICADOR_USO,
	    					ConstantesSistema.INDICADOR_USO_ATIVO));
	    	
	    	Collection colecaoSituacaoTransmissaoLeitura = fachada.pesquisar(
	    			filtroSituacaoTransmissaoLeitura, SituacaoTransmissaoLeitura.class.getName());
	    	
	    	if(!Util.isVazioOrNulo(colecaoSituacaoTransmissaoLeitura)){
	    		sessao.setAttribute("colecaoSituacaoTransmissaoLeitura",
	    				colecaoSituacaoTransmissaoLeitura);
	    	}else{
	    		sessao.removeAttribute("colecaoSituacaoTransmissaoLeitura");
	    	}
	    }	

		// Leiturista da Empresa
		if (consultarArquivoTextoLeituraActionForm.getEmpresaID() != null
				&& !consultarArquivoTextoLeituraActionForm.getEmpresaID()
						.equals("-1")
				&& !consultarArquivoTextoLeituraActionForm.getEmpresaID()
						.equals("")) {

			FiltroLeiturista filtroLeiturista = new FiltroLeiturista(
					FiltroLeiturista.ID);
			filtroLeiturista.adicionarParametro(new ParametroSimples(
					FiltroLeiturista.EMPRESA_ID,
					consultarArquivoTextoLeituraActionForm.getEmpresaID()));
			filtroLeiturista.adicionarParametro(new ParametroSimples(
					FiltroLeiturista.INDICADOR_USO, ConstantesSistema.SIM));
			filtroLeiturista
					.adicionarCaminhoParaCarregamentoEntidade(FiltroLeiturista.CLIENTE);
			filtroLeiturista
					.adicionarCaminhoParaCarregamentoEntidade(FiltroLeiturista.FUNCIONARIO);
			
			filtroLeiturista.setCampoOrderBy(FiltroLeiturista.CLIENTE_NOME);

			Collection colecao = fachada.pesquisar(filtroLeiturista,
					Leiturista.class.getName());

			if (colecao != null && !colecao.isEmpty()) {
				Iterator it = colecao.iterator();
				while (it.hasNext()) {
					Leiturista leitu = (Leiturista) it.next();
					DadosLeiturista dadosLeiu = null;
					if (leitu.getFuncionario() != null) {
						dadosLeiu = new DadosLeiturista(leitu.getId(), leitu
								.getFuncionario().getNome());
					} else {
						dadosLeiu = new DadosLeiturista(leitu.getId(), leitu
								.getCliente().getNome());
						}
					
					colecaoLeiturista.add(dadosLeiu);
				}
			}

			Collections.sort((List) colecaoLeiturista, new Comparator() {
				public int compare(Object a, Object b) {
				String valor1 = ((DadosLeiturista)a).getDescricao();
				String valor2 = ((DadosLeiturista)b).getDescricao();

				return valor1.compareTo(valor2);

				}
				});

			
		}

		// Pega os codigos que o usuario digitou para a pesquisa direta de localidade.
		if (consultarArquivoTextoLeituraActionForm.getIdLocalidade() != null && !consultarArquivoTextoLeituraActionForm.getIdLocalidade().trim().equals("")) {
			
			pesquisarLocalidade( httpServletRequest, consultarArquivoTextoLeituraActionForm);
		}
		
		sessao.setAttribute("colecaoLeiturista", colecaoLeiturista);

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping
				.findForward("consultarArquivoTextoLeitura");

		sessao.setAttribute("arquivoTextoRoteiroEmpresaLiberar",
				arquivoTextoRoteiroEmpresa);
		sessao.setAttribute("achou","2");
		
		sessao.removeAttribute("idArquivoTextoRoteiroEmpresaDivisao");
		
		if ( fachada.verificarPermissaoEspecial( PermissaoEspecial.GERAR_ARQUIVO_TEXTO_IMOVEIS_NAO_ENVIADOS, usuarioLogado ) ){
			httpServletRequest.setAttribute( "permissaoRegerarArquivoImoveisNaoEnviados", "SIM" );
		} else {
			httpServletRequest.setAttribute( "permissaoRegerarArquivoImoveisNaoEnviados", "NAO" );
		}


		return retorno;

	}
	
	private void pesquisarLocalidade(HttpServletRequest httpServletRequest, 
			ConsultarArquivoTextoLeituraActionForm form) {

		Fachada fachada = Fachada.getInstancia();
		
		// Pesquisa a localidade na base
		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
		filtroLocalidade.adicionarParametro(new ParametroSimples(
			FiltroLocalidade.ID, form.getIdLocalidade()));
		
		Collection<Localidade> localidadePesquisada = fachada.pesquisar(
			filtroLocalidade, Localidade.class.getName());
		
		// Se nenhuma localidade for encontrada a mensagem � enviada para a
		// p�gina
		if (localidadePesquisada != null && !localidadePesquisada.isEmpty()) {
			Localidade localidade = (Localidade) Util.retonarObjetoDeColecao(localidadePesquisada);
			form.setIdLocalidade(""+localidade.getId());
			form.setNomeLocalidade(localidade.getDescricao());
			
		} else {
			form.setIdLocalidade("");
			form.setNomeLocalidade("LOCALIDADE INEXISTENTE");
			httpServletRequest.setAttribute("localidadeInexistente",true);
			httpServletRequest.setAttribute("nomeCampo", "idLocalidade");
		}
	}
}
