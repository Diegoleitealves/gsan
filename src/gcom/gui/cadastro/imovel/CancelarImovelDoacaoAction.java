package gcom.gui.cadastro.imovel;

import gcom.cadastro.imovel.FiltroImovelDoacao;
import gcom.cadastro.imovel.ImovelDoacao;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.gui.ManutencaoRegistroActionForm;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.OperacaoEfetuada;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.filtro.ParametroSimples;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC0389] - Inserir Im�vel Doa��o Action respons�vel pela pre-exibi��o da
 * pagina de inserir ImovelDoacao
 * 
 * @author C�sar Ara�jo
 * @created 22 de agosto de 2006
 */
public class CancelarImovelDoacaoAction extends GcomAction {
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
		Fachada fachada                                           = null;
		HttpSession sessao                                        = null;
		ActionForward retorno                                     = null;
		String[] idsCancelamento                                  = null;
		ImovelDoacao imovelDoacao                                 = null;
		Usuario usuarioCancelamento                               = null;
		FiltroImovelDoacao filtroImovelDoacao                     = null;
		Integer contadorImovelDoacaoCancelados                    = null;          
		ManutencaoRegistroActionForm manutencaoRegistroActionForm = null;

		/*** Procedimentos b�sicos para execu��o do m�todo ***/
		retorno = actionMapping.findForward("telaSucesso");
		manutencaoRegistroActionForm = (ManutencaoRegistroActionForm) actionForm;
		fachada = Fachada.getInstancia();
		sessao = httpServletRequest.getSession(false);
		
		/*** Obt�m os ids para cancelamento ***/
		idsCancelamento = manutencaoRegistroActionForm.getIdRegistrosRemocao();
		
		/*** Avalia se existem ids de imovel doacao v�lidos ***/
		if (idsCancelamento.length == 0) {
			throw new ActionServletException("atencao.manter_imovel_doacao_nenhuma_entidade_beneficente_selecionada");		
		}
		
		/*** Cria filtro imovel doacao***/
		filtroImovelDoacao = new FiltroImovelDoacao();
		contadorImovelDoacaoCancelados = new Integer(0);

        /** alterado por pedro alexandre dia 17/11/2006 
         * Recupera o usu�rio logado para passar no met�do de cancelar 
         * para verificar se o usu�rio tem abrang�ncia para cancelar a doa��o
         */
        Usuario usuarioLogado = (Usuario)sessao.getAttribute(Usuario.USUARIO_LOGADO);
        
		/*** Manipula cada um dos ids de imovel doacao para cancelamento ***/
		for (String idCancelamento: idsCancelamento) {
			/*** Prepara o filtro para pesquisar o respectivo imovel doacao na base ***/
			filtroImovelDoacao.limparListaParametros();
    		filtroImovelDoacao.adicionarParametro(new ParametroSimples(FiltroImovelDoacao.ID, idCancelamento));
    		filtroImovelDoacao.adicionarCaminhoParaCarregamentoEntidade("imovel");
    		imovelDoacao = (ImovelDoacao)fachada.pesquisar(filtroImovelDoacao, ImovelDoacao.class.getName()).iterator().next();

    		/*** Cria e atribui o usu�rio de cancelamento ***/
    		usuarioCancelamento = new Usuario();
    		usuarioCancelamento.setId(((Usuario)sessao.getAttribute("usuarioLogado")).getId());
    		
    		/*** Atribui os dados que ser�o atualizados para o imovel doacao ***/
        	imovelDoacao.setDataCancelamento(new Date());
        	imovelDoacao.setUsuarioCancelamento(usuarioCancelamento);
        
        	fachada.atualizarImovelDoacao(imovelDoacao, usuarioLogado);
        	
        	contadorImovelDoacaoCancelados += 1;  
		}
		
        Operacao operacao = new Operacao();
        operacao.setId(Operacao.OPERACAO_CATEGORIA_INSERIR);
        OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
        operacaoEfetuada.setOperacao(operacao);
        //------------ REGISTRAR TRANSA��O ----------------//

        /*** Monta tela de sucesso ***/
		montarPaginaSucesso(httpServletRequest,
				            contadorImovelDoacaoCancelados+" Autoriza��o(�es) para Doa��o Mensal do Im�vel "+imovelDoacao.getImovel().getId().toString()+" cancelada(s) com sucesso.",
				            "Cancelar outra Autoriza��o para Doa��o Mensal", 
				            "exibirManterImovelDoacaoAction.do");

		return retorno;
	}
}
