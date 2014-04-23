package gcom.gui.faturamento;

import gcom.fachada.Fachada;
import gcom.faturamento.FaturamentoAtividade;
import gcom.faturamento.FaturamentoGrupo;
import gcom.faturamento.FiltroFaturamentoAtividade;
import gcom.faturamento.FiltroFaturamentoGrupo;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExibirRotasNaoHabilitadasAction extends GcomAction {

    /**
     * 
     * @param actionMapping
     * @param actionForm
     * @param httpServletRequest
     * @param httpServletResponse
     * @return
     */
    public ActionForward execute(ActionMapping actionMapping,
            ActionForm actionForm, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {

        //Seta o mapeamento de retorno
        ActionForward retorno = actionMapping
                .findForward("exibirRotasNaoHabilitadas");

        //Carrega a instancia da fachada
        Fachada fachada = Fachada.getInstancia();

        //Carrega o objeto sessao
        HttpSession sessao = httpServletRequest.getSession(false);

        Collection colecaoFaturamentoGrupo;

        // Grupo de faturamento (Carregar cole��o)
        // [FS0001] - Verificar exist�ncia de dados
        if (sessao.getAttribute("colecaoGrupoFaturamento") == null) {

            FiltroFaturamentoGrupo filtroFaturamentoGrupo = new FiltroFaturamentoGrupo(
                    FiltroFaturamentoGrupo.DESCRICAO);

            colecaoFaturamentoGrupo = fachada.pesquisar(filtroFaturamentoGrupo,
                    FaturamentoGrupo.class.getName());

            if (colecaoFaturamentoGrupo == null
                    || colecaoFaturamentoGrupo.isEmpty()) {
                throw new ActionServletException(
                        "atencao.pesquisa.nenhum_registro_tabela", null,
                        "FATURAMENTO_GRUPO");
            } else {
                sessao.setAttribute("colecaoGrupoFaturamento",
                        colecaoFaturamentoGrupo);
            }
        } else {
            colecaoFaturamentoGrupo = (Collection) sessao
                    .getAttribute("colecaoGrupoFaturamento");
        }

        // Grupo selecionado
        String grupoFaturamentoJSP = httpServletRequest.getParameter("grupo");
        // Atividade selecionado
        String atividadeFaturamentoJSP = httpServletRequest
                .getParameter("atividade");

        FaturamentoGrupo faturamentoGrupo = obterFaturamentoGrupoSelecionado(
                grupoFaturamentoJSP, colecaoFaturamentoGrupo);

        // Lista as rotas "n�o habilitadas" do grupo

        // [FS0006] - Verificar exist�ncia de rotas para o grupo
        Collection colecaoRotasGrupo = fachada
                .verificarExistenciaRotaGrupo(faturamentoGrupo);

        //Obter objeto FaturamentoAtividade a partir do ID -------------------
        FiltroFaturamentoAtividade filtroFaturamentoAtividade = new FiltroFaturamentoAtividade();

        filtroFaturamentoAtividade.adicionarParametro(new ParametroSimples(
                FiltroFaturamentoAtividade.ID, atividadeFaturamentoJSP));

        filtroFaturamentoAtividade.adicionarParametro(new ParametroSimples(
                FiltroFaturamentoAtividade.INDICADOR_USO,
                ConstantesSistema.INDICADOR_USO_ATIVO));

        Collection colecaoFaturamentoAtividade = fachada.pesquisar(
                filtroFaturamentoAtividade, FaturamentoAtividade.class
                        .getName());

        FaturamentoAtividade faturamentoAtividade = (FaturamentoAtividade) Util
                .retonarObjetoDeColecao(colecaoFaturamentoAtividade);
        //--------------------------------------------------------------------

        //[SB0002] - Verificar Situa��o da Atividade para a Rota
        Collection colecaoRotasSituacao = fachada
                .verificarSituacaoAtividadeRota(colecaoRotasGrupo,
                        faturamentoAtividade, faturamentoGrupo, false);

        //[FS0007] - Verificar sele��o de pelo menos uma rota habilitada
        if (colecaoRotasSituacao == null || colecaoRotasSituacao.isEmpty()) {
            throw new ActionServletException(
                    "atencao.pesquisa.nenhuma.rota_nao_habilitada_grupo");
        }

        // Passa a colecao de rotas n�o habilitadas pela sess�o (Motivo:
        // pagina��o)
        httpServletRequest.setAttribute("colecaoRotasNaoHabilitadas", colecaoRotasSituacao);

        return retorno;
    }

    /**
     * Retorna o objeto FaturamentoGrupo selecionado
     * 
     * @param id
     * @param colecao
     * @return
     */
    private FaturamentoGrupo obterFaturamentoGrupoSelecionado(String id,
            Collection colecao) {
        FaturamentoGrupo retorno = null;
        Iterator colecaoIterator = colecao.iterator();

        while (colecaoIterator.hasNext()) {
            retorno = (FaturamentoGrupo) colecaoIterator.next();

            if (retorno.getId().equals(new Integer(id))) {
                break;
            }
        }

        return retorno;
    }

}
