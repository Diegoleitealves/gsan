package gcom.gui.atendimentopublico.registroatendimento;

import gcom.atendimentopublico.registroatendimento.RegistroAtendimento;
import gcom.atendimentopublico.registroatendimento.RegistroAtendimentoSolicitante;
import gcom.atendimentopublico.registroatendimento.RegistroAtendimentoUnidade;
import gcom.atendimentopublico.registroatendimento.bean.FiltrarRegistroAtendimentoHelper;
import gcom.atendimentopublico.registroatendimento.bean.ObterDescricaoSituacaoRAHelper;
import gcom.atendimentopublico.registroatendimento.bean.RAFiltroHelper;
import gcom.cadastro.geografico.BairroArea;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.Util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class PesquisarRegistroAtendimentoAction extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirResultadoPesquisaRegistroAtendimento");
		// Instacia a fachada
		Fachada fachada = Fachada.getInstancia();
		// Sess�o
		HttpSession sessao = httpServletRequest.getSession(false);
		PesquisarRegistroAtendimentoActionForm pesquisarRegistroAtendimentoActionForm = (PesquisarRegistroAtendimentoActionForm) actionForm;
		boolean parametroInformado = false;
		
		RegistroAtendimento ra = new RegistroAtendimento();
		FiltrarRegistroAtendimentoHelper filtroRA = new FiltrarRegistroAtendimentoHelper();
		
		// Numero RA
		if (pesquisarRegistroAtendimentoActionForm.getNumeroRA() != null &&
				!pesquisarRegistroAtendimentoActionForm.getNumeroRA().equals("")) {
			ra.setId(new Integer(pesquisarRegistroAtendimentoActionForm.getNumeroRA()));
			parametroInformado = true;
		}
		
		//Numero Protocolo Atendimento
		RegistroAtendimentoSolicitante registroAtendimentoSolicitante = null;
		if (pesquisarRegistroAtendimentoActionForm.getNumeroProtocoloAtendimento() != null &&
			!pesquisarRegistroAtendimentoActionForm.getNumeroProtocoloAtendimento().equals("")) {
			
			registroAtendimentoSolicitante = new RegistroAtendimentoSolicitante();
			
			registroAtendimentoSolicitante.setNumeroProtocoloAtendimento(pesquisarRegistroAtendimentoActionForm
			.getNumeroProtocoloAtendimento());
			
			parametroInformado = true;
		}
		
		//N�mero Manual
		if (pesquisarRegistroAtendimentoActionForm.getNumeroRAManual() != null &&
				!pesquisarRegistroAtendimentoActionForm.getNumeroRAManual().equals("")) {
			
			//String[] arrayNumeroRAManual = pesquisarRegistroAtendimentoActionForm.getNumeroRAManual().split("-");
			String[] arrayNumeroRAManual = new String[2];
			arrayNumeroRAManual[0] = pesquisarRegistroAtendimentoActionForm.getNumeroRAManual().substring(0, pesquisarRegistroAtendimentoActionForm.getNumeroRAManual().length() - 1);
			arrayNumeroRAManual[1] = pesquisarRegistroAtendimentoActionForm.getNumeroRAManual().substring(pesquisarRegistroAtendimentoActionForm.getNumeroRAManual().length() - 1, pesquisarRegistroAtendimentoActionForm.getNumeroRAManual().length());
			
			Integer numeracao = new Integer(arrayNumeroRAManual[0]);
			Integer digitoModulo11 = new Integer(arrayNumeroRAManual[1]);
			
			//Caso o d�gito verificador do n�mero informado n�o bata com o d�gito calculado com o m�dulo 11
			if (!digitoModulo11.equals(Util.obterDigitoVerificadorModulo11(Long.parseLong(numeracao.toString())))){
				throw new ActionServletException("atencao.numeracao_ra_manual_digito_invalido");
			}
			
			//ra.setManual(Util.obterNumeracaoRAManual(pesquisarRegistroAtendimentoActionForm.getNumeroRAManual()));
			ra.setManual(new Integer(pesquisarRegistroAtendimentoActionForm.getNumeroRAManual()));
			parametroInformado = true;
		}
		
		// Quantidade RA Reiteradas
		Integer qtdeRAReiteradasInicial = null;
		Integer qtdeRAReiteradasFinal = null;
		
		if (pesquisarRegistroAtendimentoActionForm.getQuantidadeRAReiteradasInicial() != null &&
				!pesquisarRegistroAtendimentoActionForm.getQuantidadeRAReiteradasInicial().equals("")) {
			qtdeRAReiteradasInicial = new Integer(pesquisarRegistroAtendimentoActionForm.getQuantidadeRAReiteradasInicial());
			qtdeRAReiteradasFinal = new Integer(pesquisarRegistroAtendimentoActionForm.getQuantidadeRAReiteradasFinal());
			parametroInformado = true;
		}
		
		// Imovel
		if (pesquisarRegistroAtendimentoActionForm.getMatriculaImovel() != null &&
				!pesquisarRegistroAtendimentoActionForm.getMatriculaImovel().equals("")) {
			Imovel imovel = new Imovel();
			imovel.setId(new Integer(pesquisarRegistroAtendimentoActionForm.getMatriculaImovel()));
			ra.setImovel(imovel);
			parametroInformado = true;
		}
		// Situa��o
		if (pesquisarRegistroAtendimentoActionForm.getSituacao() != null &&
				!pesquisarRegistroAtendimentoActionForm.getSituacao().equals("")) {
			ra.setCodigoSituacao(new Short(pesquisarRegistroAtendimentoActionForm.getSituacao()));		
			parametroInformado = true;
		}
		// Tipo Especifica��o
		Collection<Integer> colecaoSolicitacaoTipoEspecificacao = new ArrayList();
		if (pesquisarRegistroAtendimentoActionForm.getEspecificacao() != null &&
				pesquisarRegistroAtendimentoActionForm.getEspecificacao().length > 0) {
			String[] tipoSolicitacaoEspecificacao = pesquisarRegistroAtendimentoActionForm.getEspecificacao();
			for (int i = 0; i < tipoSolicitacaoEspecificacao.length; i++) {
				if (new Integer(tipoSolicitacaoEspecificacao[i]).intValue() != ConstantesSistema.NUMERO_NAO_INFORMADO) {
					colecaoSolicitacaoTipoEspecificacao.add(new Integer(tipoSolicitacaoEspecificacao[i]));
					// passar a cole��o de especifica��o por par�metro			
					parametroInformado = true;
				}
			}
		}
        
        // Tipo Solicita��o
        Collection<Integer> colecaoSolicitacao = new ArrayList();
        if (pesquisarRegistroAtendimentoActionForm.getTipoSolicitacao() != null &&
                pesquisarRegistroAtendimentoActionForm.getTipoSolicitacao().length > 0) {
            String[] tipoSolicitacao = pesquisarRegistroAtendimentoActionForm.getTipoSolicitacao();
            for (int i = 0; i < tipoSolicitacao.length; i++) {
                if (new Integer(tipoSolicitacao[i]).intValue() != ConstantesSistema.NUMERO_NAO_INFORMADO) {
                    colecaoSolicitacao.add(new Integer(tipoSolicitacao[i]));
                    // passar a cole��o de especifica��o por par�metro          
                    parametroInformado = true;
                }
            }
        }        
        
		// Data de Atendimento
		Date dataAtendimentoInicial = null;
		Date dataAtendimentoFinal = null;
		if (pesquisarRegistroAtendimentoActionForm.getPeriodoAtendimentoInicial() != null &&
				!pesquisarRegistroAtendimentoActionForm.getPeriodoAtendimentoInicial().equals("")) {
			
			dataAtendimentoInicial = Util.converteStringParaDate(pesquisarRegistroAtendimentoActionForm.getPeriodoAtendimentoInicial());
			dataAtendimentoInicial = Util.formatarDataInicial(dataAtendimentoInicial);
			
			dataAtendimentoFinal = null;
			if (pesquisarRegistroAtendimentoActionForm.getPeriodoAtendimentoFinal() != null &&
					!pesquisarRegistroAtendimentoActionForm.getPeriodoAtendimentoFinal().equals("")) {
				dataAtendimentoFinal = Util.converteStringParaDate(pesquisarRegistroAtendimentoActionForm.getPeriodoAtendimentoFinal());
				dataAtendimentoFinal = Util.adaptarDataFinalComparacaoBetween(dataAtendimentoFinal);
				
			} else {
				dataAtendimentoFinal = new Date();
				dataAtendimentoFinal = Util.formatarDataFinal(dataAtendimentoFinal);
			}
			
			//[FS005] Verificar data final menor que data inicial
			int qtdeDias = Util.obterQuantidadeDiasEntreDuasDatas(dataAtendimentoInicial, dataAtendimentoFinal);
			if (qtdeDias < 0) {
				throw new ActionServletException("atencao.filtrar_data_final_maior_que_inicial");
			}
			// passar as datas de atendimento por par�metro
			parametroInformado = true;
			
		} else {

			if (pesquisarRegistroAtendimentoActionForm
					.getPeriodoAtendimentoFinal() != null
					&& !pesquisarRegistroAtendimentoActionForm
							.getPeriodoAtendimentoFinal().equals("")) {

				dataAtendimentoFinal = Util
						.converteStringParaDate(pesquisarRegistroAtendimentoActionForm
								.getPeriodoAtendimentoFinal());
				dataAtendimentoFinal = Util
						.formatarDataFinal(dataAtendimentoFinal);

				dataAtendimentoInicial = Util
						.converteStringParaDate("01/01/1900");
				dataAtendimentoInicial = Util
						.formatarDataInicial(dataAtendimentoInicial);

				// passar as datas de atendimento por par�metro
				parametroInformado = true;
			}

		}
		
		// Data de Encerramento
		Date dataEncerramentoInicial = null;
		Date dataEncerramentoFinal = null;
		if (pesquisarRegistroAtendimentoActionForm.getPeriodoEncerramentoInicial() != null &&
				!pesquisarRegistroAtendimentoActionForm.getPeriodoEncerramentoInicial().equals("")){
			
			dataEncerramentoInicial = Util.converteStringParaDate(pesquisarRegistroAtendimentoActionForm.getPeriodoEncerramentoInicial());
			dataEncerramentoInicial = Util.formatarDataInicial(dataEncerramentoInicial);
			
			dataEncerramentoFinal = null;
			if (pesquisarRegistroAtendimentoActionForm.getPeriodoEncerramentoFinal() != null &&
					!pesquisarRegistroAtendimentoActionForm.getPeriodoEncerramentoFinal().equals("") ) {
				dataEncerramentoFinal = Util.converteStringParaDate(pesquisarRegistroAtendimentoActionForm.getPeriodoEncerramentoFinal());
				dataEncerramentoFinal = Util.adaptarDataFinalComparacaoBetween(dataEncerramentoFinal);
			} else {
				dataEncerramentoFinal = new Date();
				dataEncerramentoFinal = Util.formatarDataInicial(dataEncerramentoFinal);
			}
			//[FS005] Verificar data final menor que data inicial
			int qtdeDias = Util.obterQuantidadeDiasEntreDuasDatas(dataEncerramentoInicial, dataEncerramentoFinal);
			if (qtdeDias < 0) {
				throw new ActionServletException("atencao.filtrar_data_final_maior_que_inicial");
			}
			// passar as datas de encerramento por par�metro
			parametroInformado = true;
		} else {

			if (pesquisarRegistroAtendimentoActionForm
					.getPeriodoEncerramentoFinal() != null
					&& !pesquisarRegistroAtendimentoActionForm
							.getPeriodoEncerramentoFinal().equals("")) {

				dataEncerramentoFinal = Util
						.converteStringParaDate(pesquisarRegistroAtendimentoActionForm
								.getPeriodoEncerramentoFinal());
				dataAtendimentoFinal = Util
						.formatarDataFinal(dataEncerramentoFinal);

				dataEncerramentoInicial = Util
						.converteStringParaDate("01/01/1900");
				dataEncerramentoInicial = Util
						.formatarDataInicial(dataAtendimentoInicial);

				// passar as datas de atendimento por par�metro
				parametroInformado = true;
			}

		}
		
		//Registro Atendimento Unidade
		RegistroAtendimentoUnidade registroAtendimentoUnidade = null;
		Usuario usuario = null;
		if (pesquisarRegistroAtendimentoActionForm.getLoginUsuario() != null &&
			!pesquisarRegistroAtendimentoActionForm.getLoginUsuario().equals("")) {
			
			usuario = new Usuario();
			usuario.setLogin(pesquisarRegistroAtendimentoActionForm.getLoginUsuario());
			
			registroAtendimentoUnidade = new RegistroAtendimentoUnidade();
			registroAtendimentoUnidade.setUsuario(usuario);
			
			// passar cole��o de registro atendimento unidades(usu�rio) por par�metro
			parametroInformado = true;
		}
		
		// Unidade de Atendimento
		UnidadeOrganizacional unidadeAtendimento = null;
		if (pesquisarRegistroAtendimentoActionForm.getUnidadeAtendimentoId() != null &&
				!pesquisarRegistroAtendimentoActionForm.getUnidadeAtendimentoId().equals("")) {
			unidadeAtendimento = new UnidadeOrganizacional();
			unidadeAtendimento.setId(new Integer(pesquisarRegistroAtendimentoActionForm.getUnidadeAtendimentoId()));
			// passar cole��o de unidades por par�metro
			parametroInformado = true;
		}
		// Unidade de Atual
		UnidadeOrganizacional unidadeAtual = null;
		if (pesquisarRegistroAtendimentoActionForm.getUnidadeAtualId() != null &&
				!pesquisarRegistroAtendimentoActionForm.getUnidadeAtualId().equals("")) {
			unidadeAtual = new UnidadeOrganizacional();
			unidadeAtual.setId(new Integer(pesquisarRegistroAtendimentoActionForm.getUnidadeAtualId()));
			// passar cole��o de unidades por par�metro			
			parametroInformado = true;
		}
		// Unidade de Atual
		UnidadeOrganizacional unidadeSuperior = null;
		if (pesquisarRegistroAtendimentoActionForm.getUnidadeSuperiorId() != null &&
				!pesquisarRegistroAtendimentoActionForm.getUnidadeSuperiorId().equals("")) {
			unidadeSuperior = new UnidadeOrganizacional();
			unidadeSuperior.setId(new Integer(pesquisarRegistroAtendimentoActionForm.getUnidadeSuperiorId()));
			// passar cole��o de unidades por par�metro			
			parametroInformado = true;
		}
		// Munic�pio
		String municipioId = null;
		if (pesquisarRegistroAtendimentoActionForm.getMunicipioId() != null &&
				!pesquisarRegistroAtendimentoActionForm.getMunicipioId().equals("")) {
			municipioId = pesquisarRegistroAtendimentoActionForm.getMunicipioId(); 
			parametroInformado = true;
		}
		// Bairro
		String bairroId = null;
		String bairroCodigo = null;
		if (pesquisarRegistroAtendimentoActionForm.getBairroCodigo() != null &&
				!pesquisarRegistroAtendimentoActionForm.getBairroCodigo().equals("")) {
			
			//[FS009] Verificar informa��o do munic�pio
			if (pesquisarRegistroAtendimentoActionForm.getMunicipioId() == null ||
					pesquisarRegistroAtendimentoActionForm.getMunicipioId().equals("")) {
				
				throw new ActionServletException("atencao.filtrar_informar_municipio");
			}
			
			bairroCodigo = pesquisarRegistroAtendimentoActionForm.getBairroCodigo();
			
			if (pesquisarRegistroAtendimentoActionForm.getBairroId() != null &&
				!pesquisarRegistroAtendimentoActionForm.getBairroId().equals("")){
				
				bairroId = pesquisarRegistroAtendimentoActionForm.getBairroId();
			}
			
			parametroInformado = true;
		}
		
		// Bairro �rea
		if (new Integer(pesquisarRegistroAtendimentoActionForm.getAreaBairroId()).intValue() != ConstantesSistema.NUMERO_NAO_INFORMADO) {
			BairroArea bairroArea = new BairroArea();
			bairroArea.setId(new Integer(pesquisarRegistroAtendimentoActionForm.getAreaBairroId()));
			ra.setBairroArea(bairroArea);
			parametroInformado = true;
		}
		// Logradouro
		String logradouroId = null;
		if (pesquisarRegistroAtendimentoActionForm.getLogradouroId() != null &&
				!pesquisarRegistroAtendimentoActionForm.getLogradouroId().equals("")) {
			logradouroId = pesquisarRegistroAtendimentoActionForm.getLogradouroId();
			parametroInformado = true;
		}
		
		// Filtra Registro Atendimento
		if (parametroInformado) {
			Collection<RegistroAtendimento> colecaoRegistroAtendimento = new ArrayList();
			
			filtroRA.setRegistroAtendimento(ra);
			filtroRA.setUnidadeAtendimento(unidadeAtendimento);
			filtroRA.setUnidadeAtual(unidadeAtual);
			filtroRA.setUnidadeSuperior(unidadeSuperior);
			filtroRA.setDataAtendimentoInicial(dataAtendimentoInicial);
			filtroRA.setDataAtendimentoFinal(dataAtendimentoFinal);
			filtroRA.setDataEncerramentoInicial(dataEncerramentoInicial);
			filtroRA.setDataEncerramentoFinal(dataEncerramentoFinal);
			filtroRA.setColecaoTipoSolicitacaoEspecificacao(colecaoSolicitacaoTipoEspecificacao);
            filtroRA.setColecaoTipoSolicitacao(colecaoSolicitacao);
			filtroRA.setMunicipioId(municipioId);
			filtroRA.setBairroId(bairroId);
			filtroRA.setBairroCodigo(bairroCodigo);
			filtroRA.setLogradouroId(logradouroId);
			filtroRA.setQuantidadeRAReiteradasIncial(qtdeRAReiteradasInicial);
			filtroRA.setQuantidadeRAReiteradasFinal(qtdeRAReiteradasFinal);
			
			filtroRA.setNumeroPagina(new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO));
			
			filtroRA.setRegistroAtendimentoSolicitante(registroAtendimentoSolicitante);
			
			Integer totalRegistros = fachada.filtrarRegistroAtendimento(filtroRA).size();
		
			retorno = this.controlarPaginacao(httpServletRequest, retorno, totalRegistros);
			
			filtroRA.setNumeroPagina(((Integer) httpServletRequest.getAttribute("numeroPaginasPesquisa")));
			colecaoRegistroAtendimento = fachada.filtrarRegistroAtendimento(filtroRA);

			if (colecaoRegistroAtendimento != null) {
				// Carrega Cole��o
				Collection colecaoRAHelper = loadColecaoRAHelper(colecaoRegistroAtendimento);
				sessao.setAttribute("colecaoRAHelper", colecaoRAHelper);
			} else {
				// Nenhum resultado
				throw new ActionServletException("atencao.pesquisa.nenhumresultado");
			}
			
		} else {
			throw new ActionServletException("atencao.filtrar_informar_um_filtro");
		}
		return retorno;
	}
	
	/**
	 * Carrega cole��o de registro atendimento, situa��o abreviada e unidade atual no 
	 * objeto facilitador 
	 *
	 * @author Leonardo Regis
	 * @date 10/08/2006
	 *
	 * @param colecaoRegistroAtendimento
	 * @return
	 */
	private Collection loadColecaoRAHelper(Collection<RegistroAtendimento> colecaoRegistroAtendimento) {
		Fachada fachada = Fachada.getInstancia();
		Collection colecaoRAHelper = new ArrayList();
		UnidadeOrganizacional unidadeAtual = null;
		ObterDescricaoSituacaoRAHelper situacao = null;
		RAFiltroHelper helper = null;
		for (Iterator iter = colecaoRegistroAtendimento.iterator(); iter.hasNext();) {
			RegistroAtendimento registroAtendimento = (RegistroAtendimento) iter.next();
			
			situacao = fachada.obterDescricaoSituacaoRA(registroAtendimento.getId());
			unidadeAtual = fachada.obterUnidadeAtualRA(registroAtendimento.getId());
			helper = new RAFiltroHelper();
			helper.setRegistroAtendimento(registroAtendimento);
			helper.setUnidadeAtual(unidadeAtual);
			helper.setSituacao(situacao.getDescricaoAbreviadaSituacao());
			colecaoRAHelper.add(helper);
		}
		return colecaoRAHelper;
	}	
}
