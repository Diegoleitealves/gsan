package gcom.gui.micromedicao.hidrometro;

import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.micromedicao.hidrometro.FiltroHidrometroLocalArmazenagem;
import gcom.micromedicao.hidrometro.FiltroHidrometroMotivoMovimentacao;
import gcom.micromedicao.hidrometro.Hidrometro;
import gcom.micromedicao.hidrometro.HidrometroLocalArmazenagem;
import gcom.micromedicao.hidrometro.HidrometroMotivoMovimentacao;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
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
 */
public class ExibirConfirmarMovimentarHidrometroAction extends GcomAction {
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

        //Obt�m o action form
        ConfirmarMovimentarHidrometroActionForm confirmarMovimentarHidrometroActionForm = (ConfirmarMovimentarHidrometroActionForm) actionForm;

        //Define a��o de retorno
        ActionForward retorno = actionMapping
                .findForward("confirmarMovimentarHidrometro");

        //Obt�m a sess�o
        HttpSession sessao = httpServletRequest.getSession(false);

        //Obt�m a facahda
        Fachada fachada = Fachada.getInstancia();

        Collection colecaoHidrometroSelecionado = (Collection) sessao
                .getAttribute("colecaoHidrometroSelecionado");
        
        httpServletRequest.setAttribute("qtdeHidrometrosMovimentados",colecaoHidrometroSelecionado.size());

        //Obt�m a descri��o do local de armazenagem
        Hidrometro hidrometro = (Hidrometro) Util
                .retonarObjetoDeColecao(colecaoHidrometroSelecionado);
        String codigoDescricaoLocalArmazenagemAtual = hidrometro
                .getHidrometroLocalArmazenagem().getId().toString();

        codigoDescricaoLocalArmazenagemAtual = codigoDescricaoLocalArmazenagemAtual
                + " - "
                + hidrometro.getHidrometroLocalArmazenagem().getDescricao();

        //Obt�m o objetoCosulta vindo na sess�o
        String objetoConsulta = (String) httpServletRequest
                .getParameter("objetoConsulta");
        
        httpServletRequest.setAttribute("nomeCampo", "idLocalArmazenagemDestino");
        
        //Verifica se o objeto � diferente de nulo
        if (objetoConsulta != null
                && !objetoConsulta.trim().equalsIgnoreCase("")
                && (Integer.parseInt(objetoConsulta)) == 1) {

            //Filtro para obter o local de armazenagem ativo de id informado
            FiltroHidrometroLocalArmazenagem filtroHidrometroLocalArmazenagem = new FiltroHidrometroLocalArmazenagem();

            filtroHidrometroLocalArmazenagem
                    .adicionarParametro(new ParametroSimples(
                            FiltroHidrometroLocalArmazenagem.ID, new Integer(
                                    confirmarMovimentarHidrometroActionForm
                                            .getIdLocalArmazenagemDestino()),
                            ParametroSimples.CONECTOR_AND));
            filtroHidrometroLocalArmazenagem
                    .adicionarParametro(new ParametroSimples(
                            FiltroHidrometroLocalArmazenagem.INDICADOR_USO,
                            ConstantesSistema.INDICADOR_USO_ATIVO));

            //Pesquisa de acordo com os par�metros informados no filtro
            Collection colecaoHidrometroLocalArmazenagem = fachada.pesquisar(
                    filtroHidrometroLocalArmazenagem,
                    HidrometroLocalArmazenagem.class.getName());

            //Verifica se a pesquisa retornou algum objeto para a cole��o
            if (colecaoHidrometroLocalArmazenagem != null
                    && !colecaoHidrometroLocalArmazenagem.isEmpty()) {

                //Obt�m o objeto da cole��o pesquisada
                HidrometroLocalArmazenagem hidrometroLocalArmazenagem = (HidrometroLocalArmazenagem) Util
                        .retonarObjetoDeColecao(colecaoHidrometroLocalArmazenagem);

                //Exibe o c�digo e a descri��o pesquisa na p�gina
                httpServletRequest.setAttribute("corLocalArmazenagem", "valor");
                confirmarMovimentarHidrometroActionForm
                        .setIdLocalArmazenagemDestino(hidrometroLocalArmazenagem
                                .getId().toString());
                confirmarMovimentarHidrometroActionForm
                        .setLocalArmazenagemDescricaoDestino(hidrometroLocalArmazenagem
                                .getDescricao());
                httpServletRequest.setAttribute("nomeCampo", "dataMovimentacao");

            } else {

                //Exibe mensagem de c�digo inexiste e limpa o campo de c�digo
                httpServletRequest.setAttribute("corLocalArmazenagem",
                        "exception");
                confirmarMovimentarHidrometroActionForm
                        .setIdLocalArmazenagemDestino("");
                confirmarMovimentarHidrometroActionForm
                        .setLocalArmazenagemDescricaoDestino("LOCAL DE ARMAZENAGEM INEXISTENTE");
            }

        }
        //Cria��o e defini��o do filto de hidr�metro motivo da movimenta��o
        FiltroHidrometroMotivoMovimentacao filtroHidrometroMotivoMovimentacao = new FiltroHidrometroMotivoMovimentacao();
        
        filtroHidrometroMotivoMovimentacao
                .adicionarParametro(new ParametroSimples(
                        FiltroHidrometroMotivoMovimentacao.INDICADOR_USO,
                        ConstantesSistema.INDICADOR_USO_ATIVO));
        filtroHidrometroMotivoMovimentacao
                .setCampoOrderBy(FiltroHidrometroMotivoMovimentacao.DESCRICAO);

        //Obt�m os motivos da movimenta��o
        Collection colecaoHidrometroMotivoMovimentacao = fachada.pesquisar(
                filtroHidrometroMotivoMovimentacao,
                HidrometroMotivoMovimentacao.class.getName());

        //Envia objeto no request
        httpServletRequest.setAttribute("colecaoHidrometroMotivoMovimentacao",
                colecaoHidrometroMotivoMovimentacao);
        //Envia objeto pela sess�o
        sessao.setAttribute("codigoDescricaoLocalArmazenagemAtual",
                codigoDescricaoLocalArmazenagemAtual);
        
        //Data Corrente
        SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
        Calendar dataCorrente = new GregorianCalendar();
        
        httpServletRequest.setAttribute("dataMovimentacao", formatoData
        		.format(dataCorrente.getTime()));
        httpServletRequest.setAttribute("dataAtual", formatoData
        		.format(dataCorrente.getTime()));

        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");
        Calendar horaCorrente = new GregorianCalendar();
        
        httpServletRequest.setAttribute("horaMovimentacao", formatoHora
        		.format(horaCorrente.getTime()));
        return retorno;
    }
}
