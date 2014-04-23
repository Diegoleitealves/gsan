package gcom.gui.micromedicao.hidrometro;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.gui.ManutencaoRegistroActionForm;

import java.util.Collection;

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
 */
public class MovimentarHidrometroAction extends GcomAction {
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

        //Define a��o de retorno
        ActionForward retorno = actionMapping
                .findForward("exibirConfirmarMovimentarHidrometro");

        ManutencaoRegistroActionForm manutencaoRegistroActionForm = (ManutencaoRegistroActionForm) actionForm;

        //Obt�m a facahda
        Fachada fachada = Fachada.getInstancia();

        //Obt�m a sess�o
        HttpSession sessao = httpServletRequest.getSession(false);

		// Obt�m os ids para movimenta��o
        String[] ids = manutencaoRegistroActionForm.getIdRegistrosRemocao();

        //Mensagem de erro quando o usu�rio tenta excluir sem ter selecionado nenhum registro
        if (ids == null || ids.length == 0) {
            throw new ActionServletException(
                    "atencao.registros.nao_selecionados");
        }

        Collection colecaoHidrometro = (Collection) sessao
                .getAttribute("colecaoHidrometro");

        Collection colecaoHidrometroSelecionado = fachada
                .obterColecaoObjetosSelecionados(colecaoHidrometro, ids);

        //Obt�m a quantidade de registros selecionados
        String numeroHidrometrosSelecionados = new Integer(ids.length).toString();

        //Verifca se existe uniformidade dos locais de armazenagem
        String verificacao = fachada
                .verificarLocalArmazenagemSituacao(colecaoHidrometroSelecionado);

        //Caso o local de armazenagem seja diferente entre os hidr�metros selecionados
        if (verificacao.equals("localArmazenagemDiferente")) {
            throw new ActionServletException(
                    "atencao.hidrometros.selecionados.nao.uniformidade.local.armazenagem");
        }

        //Caso os hidr�metros selecionados estejam com algum instalado
        if (verificacao.equals("hidrometroInstalado")) {
            throw new ActionServletException(
                    "atencao.nao.possivel.movimentar.hidrometros.instalados");
        }

        sessao.setAttribute("numeroHidrometrosSelecionados",
                numeroHidrometrosSelecionados);
        sessao.setAttribute("colecaoHidrometroSelecionado",
                colecaoHidrometroSelecionado);

        return retorno;
    }
}
