package gcom.gui.micromedicao.hidrometro;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.hidrometro.FiltroHidrometro;
import gcom.micromedicao.hidrometro.Hidrometro;

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
 * @author rodrigo
 */
public class ExibirMovimentarHidrometroAction extends GcomAction {

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

        //Seta o retorno
        ActionForward retorno = actionMapping
                .findForward("movimentarHidrometro");

        //Obt�m a fachada
        Fachada fachada = Fachada.getInstancia();

        //Obt�m a sess�o
        HttpSession sessao = httpServletRequest.getSession(false);

        //remove objetos da sess�o vindos do filtro
        sessao.removeAttribute("colecaoHidrometroClasseMetrologica");
        sessao.removeAttribute("colecaoHidrometroMarca");
        sessao.removeAttribute("colecaoHidrometroDiametro");
        sessao.removeAttribute("colecaoHidrometroCapacidade");
        sessao.removeAttribute("colecaoHidrometroTipo");
        sessao.removeAttribute("colecaoHidrometroRelojoaria");
        

        sessao.removeAttribute("ManutencaoRegistroActionForm");
        sessao.removeAttribute("ConfirmarMovimentarHidrometroActionForm");

        //Obt�m objeto enviado na sess�o
        String fixo = (String) sessao.getAttribute("fixo");

        //Cria cole��o
        Collection colecaoHidrometro = null;
        //Verifica se o usu�rio preencheu o filtro pelo fixo e intervalo
        if (fixo != null && !fixo.equalsIgnoreCase("")) {

            String faixaInicial = (String) sessao.getAttribute("faixaInicial");
            String faixaFinal = (String) sessao.getAttribute("faixaFinal");

            colecaoHidrometro = fachada.pesquisarNumeroHidrometroFaixa(fixo,
                    faixaInicial, faixaFinal);

        } else {

        	FiltroHidrometro filtroHidrometro = (FiltroHidrometro) sessao
            	.getAttribute("filtroHidrometro");
    
        	/*FiltroHidrometro filtroHidrometro = (FiltroHidrometro) httpServletRequest
                    .getAttribute("filtroHidrometro");*/
            filtroHidrometro
            		.adicionarCaminhoParaCarregamentoEntidade("hidrometroMarca");
            filtroHidrometro
    				.adicionarCaminhoParaCarregamentoEntidade("hidrometroCapacidade");
            filtroHidrometro
    				.adicionarCaminhoParaCarregamentoEntidade("hidrometroSituacao");
            filtroHidrometro
    				.adicionarCaminhoParaCarregamentoEntidade("hidrometroLocalArmazenagem");

            int totalPesquisa = fachada.totalRegistrosPesquisa(filtroHidrometro,
            		Hidrometro.class.getName());
	        if ( totalPesquisa != 0 && totalPesquisa >= 1000 ) {
	         	throw new ActionServletException("atencao.pesquisa_maior_que_permitido", null, "" );
	        }
           
           colecaoHidrometro = fachada.pesquisar(
                    filtroHidrometro, Hidrometro.class.getName());
        }
        
       

        //Caso a cole��o seja null
        if (colecaoHidrometro == null || colecaoHidrometro.isEmpty()) {
            throw new ActionServletException("atencao.naocadastrado", null,
                    "Hidr�metro");
        }

        //Envia objeto na sess�o
        sessao.setAttribute("colecaoHidrometro", colecaoHidrometro);
        
        //devolve o mapeamento de retorno
        return retorno;
    }
}
