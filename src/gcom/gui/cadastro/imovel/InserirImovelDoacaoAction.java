package gcom.gui.cadastro.imovel;

import gcom.cadastro.imovel.EntidadeBeneficente;
import gcom.cadastro.imovel.FiltroEntidadeBeneficente;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.ImovelDoacao;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.conta.FiltroConta;
import gcom.faturamento.debito.DebitoTipo;
import gcom.faturamento.debito.FiltroDebitoTipo;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.interceptor.RegistradorOperacao;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.OperacaoEfetuada;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioAcao;
import gcom.seguranca.acesso.usuario.UsuarioAcaoUsuarioHelper;
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
 * [UC0389] - Inserir Imovel Doacao Action respons�vel pela inser��o de um
 * Im�vel Doa��o
 * 
 * @author C�sar Ara�jo
 * @created 30 de 08 de 2006
 */
public class InserirImovelDoacaoAction extends GcomAction {
	/**
	 * Description of the Method
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
		
		/*** Declara e inicializa vari�veis ***/
		Imovel imovel                                = null;
		Date dataAdesao                              = null;
		ActionForward retorno                        = null;
		HttpSession sessao                           = null;
		Usuario usuarioAdesao                        = null;
		BigDecimal valorDoacao                       = null;
		ImovelDoacao imovelDoacao                    = null;
		EntidadeBeneficente entidadeBeneficente      = null;
		
		/*** Procedimentos b�sicos para execu��o do m�todo ***/
		retorno = actionMapping.findForward("telaSucesso");
		
		ImovelDoacaoActionForm imovelDoacaoActionForm = (ImovelDoacaoActionForm) actionForm;
		sessao = this.getSessao(httpServletRequest);
		
		/*** Define os valores a serem armazenados na base de dados***/
		valorDoacao = 
			new BigDecimal(
				imovelDoacaoActionForm.getValorDoacao().replace(".", "").replace(",", "."));
		usuarioAdesao = new Usuario();	
		usuarioAdesao.setId(((Usuario)sessao.getAttribute("usuarioLogado")).getId());
		
		dataAdesao = new Date();
        
		entidadeBeneficente = new EntidadeBeneficente();
		entidadeBeneficente.setId(new Integer(imovelDoacaoActionForm.getIdEntidadeBeneficente()));
		
		imovel = new Imovel();
		imovel.setId(new Integer(imovelDoacaoActionForm.getIdImovel()));
		
        //------------ REGISTRAR TRANSA��O ----------------//
		RegistradorOperacao registradorOperacao = 
			new RegistradorOperacao(Operacao.OPERACAO_CATEGORIA_INSERIR,
				new UsuarioAcaoUsuarioHelper(usuarioAdesao,UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));
        
        Operacao operacao = new Operacao();
        operacao.setId(Operacao.OPERACAO_CATEGORIA_INSERIR);
        OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
        operacaoEfetuada.setOperacao(operacao);
        //------------ REGISTRAR TRANSA��O ----------------//
		
		/*** Verifica se a associa��o de imovel e entidade beneficente j� existe na base de dados ***/
		imovelDoacao = 
			this.getFachada().verificarExistenciaImovelDoacao(
				imovel.getId(), 
				entidadeBeneficente.getId());

		if (imovelDoacao != null && imovelDoacao.getId() != 0) {
			throw new ActionServletException("atencao.inserir_imovel_doacao_ja_existe", 
				null, imovelDoacao.getId().toString());		
		}
		
		/*** Preenche as informa��es de imovel doa��o ***/
		imovelDoacao = 
			new ImovelDoacao(imovel, 
                valorDoacao, 
                entidadeBeneficente, 
                dataAdesao, 
                usuarioAdesao, 
                new Date());
		
		//-------HUGO NASCIMENTO-------
		/*** Preenche as informa��es de imovel doa��o ***/
		Integer quantidadeParcela = null;
		if(imovelDoacaoActionForm.getQuantidadeParcela() != null && 
				!imovelDoacaoActionForm.getQuantidadeParcela().equals("")){
			quantidadeParcela = 
				new Integer(imovelDoacaoActionForm.getQuantidadeParcela());
		}		
		
		SistemaParametro sistemaParametro = this.getSistemaParametro();
		
		FiltroConta filtroConta = new FiltroConta();
		filtroConta.adicionarParametro(new ParametroSimples(FiltroConta.IMOVEL_ID,imovel.getId()));
		filtroConta.adicionarParametro(new ParametroSimples(FiltroConta.REFERENCIA,
			sistemaParametro.getAnoMesFaturamento()));

		
		Collection contaEncontrado = this.getFachada().pesquisar(filtroConta,Conta.class.getName());
		
		if (contaEncontrado != null && !contaEncontrado.isEmpty()) {
			imovelDoacao.setAnoMesReferenciaInicial(
				Util.somaMesAnoMesReferencia(sistemaParametro.getAnoMesFaturamento(), 1));
		}else{
			imovelDoacao.setAnoMesReferenciaInicial(sistemaParametro.getAnoMesFaturamento());
		}
		
		if(quantidadeParcela != null && quantidadeParcela.intValue() > 0){
			imovelDoacao.setAnoMesReferenciaFinal(
				Util.somaMesAnoMesReferencia(
					sistemaParametro.getAnoMesFaturamento(),quantidadeParcela ));
		}		


		
		Collection<EntidadeBeneficente> colecaoEntidadeBeneficente = null;
		FiltroEntidadeBeneficente filtroEntidadeBeneficente = new FiltroEntidadeBeneficente();
		filtroEntidadeBeneficente.adicionarParametro(new ParametroSimples(FiltroEntidadeBeneficente.ID,
				imovelDoacao.getEntidadeBeneficente().getId()));
		
		filtroEntidadeBeneficente.adicionarCaminhoParaCarregamentoEntidade("cliente");				
		
		colecaoEntidadeBeneficente =  this.getFachada().pesquisar(filtroEntidadeBeneficente, 
				EntidadeBeneficente.class.getName());
		
		if (colecaoEntidadeBeneficente != null && colecaoEntidadeBeneficente.size() > 0 ){
			
			EntidadeBeneficente entidadeBenediciente = 
				(EntidadeBeneficente)Util.retonarObjetoDeColecao(colecaoEntidadeBeneficente);
			
			Collection<DebitoTipo> colecaoDebitoTipo = null;
			FiltroDebitoTipo filtroDebitoTipo = new FiltroDebitoTipo();
			filtroDebitoTipo.adicionarParametro(new ParametroSimples(FiltroDebitoTipo.ID, 
					entidadeBenediciente.getDebitoTipo().getId()));
			colecaoDebitoTipo =  this.getFachada().pesquisar(filtroDebitoTipo, DebitoTipo.class.getName());
			
			if (colecaoDebitoTipo.size() > 0){
				DebitoTipo debitoTipo = (DebitoTipo)Util.retonarObjetoDeColecao(colecaoDebitoTipo);
				if(debitoTipo.getValorLimite() != null  && 
						(new Double(imovelDoacao.getValorDoacao().toEngineeringString()) > new Double(debitoTipo.getValorLimite().toEngineeringString())) ){
					throw new ActionServletException("atencao.doacao.debitotipo.valorlimite", null, debitoTipo.getValorLimite().toEngineeringString());
				}
				if(debitoTipo.getValorLimiteInferior() != null  && (new Double(imovelDoacao.getValorDoacao().toEngineeringString()) < new Double(debitoTipo.getValorLimiteInferior().toEngineeringString())) ){
					throw new ActionServletException("atencao.doacao.debitotipo.valorlimite.inferior", null, debitoTipo.getValorLimiteInferior().toEngineeringString());
				}
			}
		}
		
		//------------ REGISTRAR TRANSA��O ----------------//
        imovelDoacao.setOperacaoEfetuada(operacaoEfetuada);
        imovelDoacao.adicionarUsuario(usuarioAdesao, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
    	registradorOperacao.registrarOperacao(imovelDoacao);
        //------------ REGISTRAR TRANSA��O ----------------//

        /** alterado por pedro alexandre dia 16/11/2006 
         * Recupera o usu�rio logado para passar no met�do de inserir 
         * para verificar se o usu�rio tem abrang�ncia para inserir a doa��o
         */
        Usuario usuarioLogado = (Usuario)sessao.getAttribute(Usuario.USUARIO_LOGADO);
        
		/*** Insere o imovel doacao - As valida��es est�o no Controlador ***/
        this.getFachada().inserirImovelDoacaoRetorno(imovelDoacao, usuarioLogado);

		/*** Monta tela de sucesso ***/
		montarPaginaSucesso(httpServletRequest, 
				            "Autoriza��o para Doa��o Mensal do Im�vel "+ imovel.getId().toString() + " inserida com sucesso.",
				            "Inserir outra Autoriza��o para Doa��o Mensal", 
				            "exibirInserirImovelDoacaoAction.do",
				            "exibirManterImovelDoacaoAction.do?idRegistroAtualizacao=" + imovel.getId().toString(),
				            "Cancelar Autoriza��o para Doa��o Mensal do Im�vel "+imovel.getId().toString());
		
		return retorno;
	}
}
