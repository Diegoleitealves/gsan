package gcom.gui.micromedicao.hidrometro;

import java.util.ArrayList;
import java.util.Collection;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.gui.ManutencaoRegistroActionForm;
import gcom.micromedicao.hidrometro.Hidrometro;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.OperacaoEfetuada;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioAcao;
import gcom.seguranca.acesso.usuario.UsuarioAcaoUsuarioHelper;

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
 * @created 15 de Setembro de 2005
 */
public class RemoverHidrometroAction extends GcomAction {
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
        ActionForward retorno = actionMapping.findForward("telaSucesso");

        ManutencaoRegistroActionForm manutencaoRegistroActionForm = (ManutencaoRegistroActionForm) actionForm;

        // Obt�m os ids de remo��o
        String[] ids = manutencaoRegistroActionForm.getIdRegistrosRemocao();

        //Obt�m a sess�o
        HttpSession sessao = httpServletRequest.getSession(false);
        
        Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
        
        // ------------ REGISTRAR TRANSA��O ----------------
        Operacao operacao = new Operacao();
        operacao.setId(Operacao.OPERACAO_HIDROMETRO_REMOVER);

        OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
        operacaoEfetuada.setOperacao(operacao);
        //------------ REGISTRAR TRANSA��O ----------------

        //Obt�m a inst�ncia da fachada
        Fachada fachada = Fachada.getInstancia();
        
       
        //mensagem de erro quando o usu�rio tenta excluir sem ter selecionado
        // nenhum
        //registro
        if (ids == null || ids.length == 0) {
            throw new ActionServletException(
                    "atencao.registros.nao_selecionados");
        }
//      ------------ REGISTRAR TRANSA��O ----------------
    	UsuarioAcaoUsuarioHelper usuarioAcaoUsuarioHelper = new UsuarioAcaoUsuarioHelper(usuario,UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
    	Collection<UsuarioAcaoUsuarioHelper> colecaoUsuarios = new ArrayList();
    	colecaoUsuarios.add(usuarioAcaoUsuarioHelper);
        //------------ REGISTRAR TRANSA��O ----------------

        fachada.remover(ids, Hidrometro.class.getName(),operacaoEfetuada, colecaoUsuarios);
        
        //Remove objetos da sess�o
        sessao.removeAttribute("HidrometroActionForm");
        sessao.removeAttribute("colecaoIntervalo");
        sessao.removeAttribute("colecaoHidrometroClasseMetrologica");
        sessao.removeAttribute("colecaoHidrometroMarca");
        sessao.removeAttribute("colecaoHidrometroDiametro");
        sessao.removeAttribute("colecaoHidrometroCapacidade");
        sessao.removeAttribute("colecaoHidrometroTipo");
        sessao.removeAttribute("fixo");
        sessao.removeAttribute("faixaInicial");
        sessao.removeAttribute("faixaFinal");
        sessao.removeAttribute("hidrometros");



        //Monta a p�gina de sucesso
        if (retorno.getName().equalsIgnoreCase("telaSucesso")) {
            montarPaginaSucesso(httpServletRequest,
            		ids.length + " Hidrometro(s) removido(s) com sucesso.",
                    "Realizar outra Manuten��o de Hidr�metro",
                    "exibirManterHidrometroAction.do?menu=sim");
        }

        return retorno;
    }
}
