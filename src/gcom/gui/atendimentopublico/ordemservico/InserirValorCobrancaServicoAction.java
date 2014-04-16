package gcom.gui.atendimentopublico.ordemservico;

import gcom.atendimentopublico.ordemservico.FiltroServicoTipo;
import gcom.atendimentopublico.ordemservico.ServicoCobrancaValor;
import gcom.atendimentopublico.ordemservico.ServicoTipo;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.ImovelPerfil;
import gcom.cadastro.imovel.Subcategoria;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.interceptor.RegistradorOperacao;
import gcom.micromedicao.hidrometro.HidrometroCapacidade;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioAcao;
import gcom.seguranca.acesso.usuario.UsuarioAcaoUsuarioHelper;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Inser��o de Valor de Cobran�a do Servi�o
 * 
 * @author Leonardo Regis
 * @date 29/09/2006
 */
public class InserirValorCobrancaServicoAction extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		
		// Forward
		ActionForward retorno = actionMapping.findForward("telaSucesso");
		
		Fachada fachada = Fachada.getInstancia();

		// Form
		InserirValorCobrancaServicoActionForm cobrancaServicoActionForm = (InserirValorCobrancaServicoActionForm) actionForm;
		
		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);
		
		Usuario usuarioLogado = (Usuario)sessao.getAttribute(Usuario.USUARIO_LOGADO);

		// Filtra Tipo de Servi�o
		FiltroServicoTipo filtroServicoTipo = new FiltroServicoTipo();
		filtroServicoTipo.adicionarParametro(new ParametroSimples(
				FiltroServicoTipo.ID, cobrancaServicoActionForm
						.getTipoServico()));
		filtroServicoTipo
				.adicionarCaminhoParaCarregamentoEntidade("debitoTipo");
		
		// Recupera Tipo de Servi�o
		Collection colecaoServicoTipo = fachada.pesquisar(
				filtroServicoTipo, ServicoTipo.class.getName());
		
		if (colecaoServicoTipo == null || colecaoServicoTipo.isEmpty()) {
			throw new ActionServletException("atencao.tipo_servico_inexistente");

		}

		ServicoTipo servicoTipo = new ServicoTipo();

		servicoTipo = (ServicoTipo) colecaoServicoTipo.iterator().next();

//		if (servicoTipo.getDebitoTipo() == null) {
//			throw new ActionServletException(
//					"atencao.valor_cobranca_tipo_servico_sem_debito", null,
//					servicoTipo.getDescricao());
//		}

		// Seta Objeto Servico Cobran�a Valor
		ServicoCobrancaValor servicoCobrancaValor = setServicoCobrancaValor(cobrancaServicoActionForm);
		
		// FS0008 - Verificar pr�-exist�ncia de vig�ncia para o Servi�o tipo
		fachada.verificarExistenciaVigenciaDebito(cobrancaServicoActionForm.getDataVigenciaInicial(), 
				cobrancaServicoActionForm.getDataVigenciaFinal(), new Integer(cobrancaServicoActionForm.getTipoServico()),
				new Integer("1"));
		
		// FS0011 - Verificar pr�-exist�ncia de vig�ncia para o Servi�o tipo
		if(fachada.validarVigenciaValorCobrancaServico(servicoCobrancaValor)){
			throw new ActionServletException("atencao.existe_valor_para_vigencia");
		}
		
		// ------------ REGISTRAR TRANSA��O ----------------
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(
				Operacao.OPERACAO_VALOR_COBRANCA_SERVICO_INSERIR, servicoCobrancaValor.getServicoTipo().getId(),
				servicoCobrancaValor.getServicoTipo().getId(), new UsuarioAcaoUsuarioHelper(usuarioLogado,
				UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));
		// ------------ REGISTRAR TRANSA��O ----------------
		
		registradorOperacao.registrarOperacao(servicoCobrancaValor);

		// [FS0001] Verificar Servi�o Geral D�bito.
		fachada.inserirValorCobrancaServico(servicoCobrancaValor);

		// [FS008] Monta a p�gina de sucesso
		montarPaginaSucesso(httpServletRequest, "Valor da Cobran�a do Servi�o "
				+ servicoTipo.getDescricao() + " inserido com sucesso.",
				"Inserir outro Valor de Cobran�a do Servi�o",
				"exibirInserirValorCobrancaServicoAction.do?menu=sim");
		return retorno;
	}

	/**
	 * Preenche objeto com informa��es vindas da tela
	 * 
	 * @author Leonardo Regis
	 * @date 29/09/2006
	 * 
	 * @param form
	 * @return servicoCobrancaValor
	 */
	private ServicoCobrancaValor setServicoCobrancaValor(
			InserirValorCobrancaServicoActionForm form) {

		Fachada fachada = Fachada.getInstancia();
		
		ServicoCobrancaValor servicoCobrancaValor = new ServicoCobrancaValor();
		
		// Tipo do Servi�o
		FiltroServicoTipo filtroServicoTipo = new FiltroServicoTipo();
		filtroServicoTipo.adicionarParametro(new ParametroSimples(FiltroServicoTipo.ID, form.getTipoServico()));
		
        // Recupera Tipo de Servi�o
		Collection colecaoServicoTipo = fachada.pesquisar(filtroServicoTipo, ServicoTipo.class.getName());
		
		ServicoTipo tipoServico = (ServicoTipo) colecaoServicoTipo.iterator().next();

		servicoCobrancaValor.setServicoTipo(tipoServico);
		
		//Categoria
		Categoria categoria = null;
		if(form.getIdSubCategoria() != null 
				&& (!form.getIdCategoria().equals("") 
				&& !form.getIdCategoria().equals("-1"))){
			
			categoria = new Categoria();
			categoria.setId(new Integer(form.getIdCategoria()));
		}
		servicoCobrancaValor.setCategoria(categoria);
		
		// subCategoria
		Subcategoria subcategoria = null;
		if(form.getIdSubCategoria() != null 
				&& (!form.getIdSubCategoria().equals("") 
				&& !form.getIdSubCategoria().equals("-1"))){
			
			subcategoria = new Subcategoria();
			subcategoria.setId(new Integer(form.getIdSubCategoria()));
		}
		servicoCobrancaValor.setSubCategoria(subcategoria);
		
		// Perfil do Im�vel
		ImovelPerfil perfilImovel = null;
		
		if (form.getPerfilImovel() != null
				&& (!form.getPerfilImovel().equals("") && !form
						.getPerfilImovel().equals("-1"))) {
			perfilImovel = new ImovelPerfil();
			perfilImovel.setId(new Integer(form.getPerfilImovel()));
		}
		
		servicoCobrancaValor.setImovelPerfil(perfilImovel);
		
		// Indicador de Medido
		if (form.getIndicadorMedido().equals(ConstantesSistema.SIM.toString())) {
			
			servicoCobrancaValor.setIndicadorMedido(ConstantesSistema.SIM);
		} else {
			
			servicoCobrancaValor.setIndicadorMedido(ConstantesSistema.NAO);
		}
		
		// Indicativo Considera Economias
		if(form.getIndicativoTipoSevicoEconomias().equals(ConstantesSistema.SIM.toString())){
			
			servicoCobrancaValor.setIndicadorConsideraEconomias(ConstantesSistema.SIM);
		}else{
			servicoCobrancaValor.setIndicadorConsideraEconomias(ConstantesSistema.NAO);
		}
		
		// Hugo Leonardo - 19/04/2010
		// Economia Inicial e Economia Final
		// [FS0005] - validar quantidade de economia inicial
		if(form.getQuantidadeEconomiasInicial() != null 
				&& !form.getQuantidadeEconomiasInicial().equals("") 
				&& !form.getQuantidadeEconomiasInicial().equals("0")){
			
			// [FS0006] - validar quantidade de economia final
			if(form.getQuantidadeEconomiasFinal() != null 
					&& !form.getQuantidadeEconomiasFinal().equals("") 
					&& !form.getQuantidadeEconomiasFinal().equals("0")){
				
				Integer economiasInicial = new Integer(form.getQuantidadeEconomiasInicial());
				Integer economiasFinal = new Integer(form.getQuantidadeEconomiasFinal());
				
				if(economiasFinal <= economiasInicial){
					throw new ActionServletException("atencao.quatidade_economia_final_superior");
				}else{
				
					servicoCobrancaValor.setQuantidadeEconomiasInicial(new Integer(form.getQuantidadeEconomiasInicial()));
					servicoCobrancaValor.setQuantidadeEconomiasFinal(new Integer(form.getQuantidadeEconomiasFinal()));
				}
			}else if(form.getQuantidadeEconomiasFinal().equals("0")){
				throw new ActionServletException("atencao.quatidade_economia_final_invalida");
			}
		}else if(form.getQuantidadeEconomiasInicial() != null && form.getQuantidadeEconomiasInicial().equals("0")){
			throw new ActionServletException("atencao.quatidade_economia_inicial_invalida");
		}
		
		// Hugo Leonardo - 19/04/2010
		// Vig�ncia do Valor do Servi�o
		// [FS0007] 
		if (form.getDataVigenciaInicial() != null && !form.getDataVigenciaInicial().equals("")){
			if (!Util.validarDiaMesAno(form.getDataVigenciaInicial())) {
				servicoCobrancaValor.setDataVigenciaInicial(Util.converteStringParaDate(form.getDataVigenciaInicial()));
				//[FS0008] � Validar data da vig�ncia final
				if (!Util.validarDiaMesAno(form.getDataVigenciaFinal())) {
					servicoCobrancaValor.setDataVigenciaFinal(Util.converteStringParaDate(form.getDataVigenciaFinal()));
					if(Util.compararData(servicoCobrancaValor.getDataVigenciaInicial(),servicoCobrancaValor.getDataVigenciaFinal()) == 1){
						throw new ActionServletException("atencao.atencao.data_vigencia_final_menor");
					}
				}else{
					throw new ActionServletException("atencao.atencao.data_vigencia_final_invalida");
				}			
			}else{
				throw new ActionServletException("atencao.data_vigencia_inicial_invalida");
			}	
		}
		
		// Capacidade do Hidr�metro
		HidrometroCapacidade capacidadeHidrometro = null;
		
		if (form.getCapacidadeHidrometro() != null
				&& !form.getCapacidadeHidrometro().equals(ConstantesSistema.NUMERO_NAO_INFORMADO+"")) {
			capacidadeHidrometro = new HidrometroCapacidade();
			capacidadeHidrometro.setId(new Integer(form
					.getCapacidadeHidrometro()));
		}

		// Valor do Servi�o
		servicoCobrancaValor.setHidrometroCapacidade(capacidadeHidrometro);
		
		String valorSemPontos = form.getValorServico().replace(".", "");
		
		if(form.getIndicadorGeracaoDebito()!=null 
				&& form.getIndicadorGeracaoDebito().equals(ConstantesSistema.SIM.toString())){
			servicoCobrancaValor.setValor(new BigDecimal(valorSemPontos.replace(",", ".")));
			servicoCobrancaValor.setIndicadorGeracaoDebito(ConstantesSistema.SIM);
		}else{
			servicoCobrancaValor.setValor(BigDecimal.ZERO);
			servicoCobrancaValor.setIndicadorGeracaoDebito(ConstantesSistema.NAO);
		}
				
		servicoCobrancaValor.setUltimaAlteracao(new Date());
		
		return servicoCobrancaValor;

	}

}
