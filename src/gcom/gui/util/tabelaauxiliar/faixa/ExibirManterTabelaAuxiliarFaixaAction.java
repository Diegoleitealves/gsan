package gcom.gui.util.tabelaauxiliar.faixa;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.tabelaauxiliar.faixa.FiltroTabelaAuxiliarFaixa;
import gcom.util.tabelaauxiliar.faixa.TabelaAuxiliarFaixa;

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
public class ExibirManterTabelaAuxiliarFaixaAction extends GcomAction {
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

        //Seta o mapeamento de retorno
        ActionForward retorno = actionMapping
                .findForward("manterTabelaAuxiliarFaixa");

        //Obt�m a instancia da fachada
        Fachada fachada = Fachada.getInstancia();

        //Cria a cole��o de tabelas auxiliares
        Collection tabelasAuxiliaresFaixas = null;

        //Obt�m a inst�ncia da sess�o
        HttpSession sessao = httpServletRequest.getSession(false);

        //Obt�m o nome da tela passado no get do request
        String tela = httpServletRequest.getParameter("tela");

        //Declara��o de objetos e tipos primitivos
        String titulo = null;
//        TabelaAuxiliarFaixa tabelaAuxiliarFaixa = null;
        String pacoteNomeObjeto = null;
        String funcionalidadeTabelaAuxiliarFaixaManter = null;
        int tamMaxCampoFaixaInicial = 3;
        int tamMaxCampoFaixaFinal = 3;

        //Verifica se o exibir manter foi chamado da tela de filtro
        if (httpServletRequest.getAttribute("filtroTabelaAuxiliarFaixa") != null) {
            tela = (String) sessao.getAttribute("tela");
        }

        //********BLOCO DE C�DIGO PARA DEFINI��O DOS CADASTROS PERTENCENTES A
        // INSERIR TABELA FAIXA******//
        //        Para serem incluidos novos cadastros com c�digo, faixa inicial e
        // faixa final basta apenas cria um novo
        //        if (condicional) semelhante ao exemplo abaixo, informando apenas os
        // dados relativos
        //        ao objeto desejado.

        //---AREA CONSTRUIDA
        //Identifica a string do objeto passado no get do request
        /*
         * if (tela.equals("areaConstruida")) { //T�tulo a ser exido nas p�ginas
         * titulo = "�rea Constru�da"; //Cria o objeto AreaConstruida
         * areaConstruida = new AreaConstruida(); //Associa o objeto tabela
         * auxiliar ao tipo criado tabelaAuxiliarFaixa = areaConstruida; //Obt�m
         * o path do pacote mais o tipo do objeto pacoteNomeObjeto =
         * tabelaAuxiliarFaixa.getClass().getName(); //Define o link a ser
         * exibido na p�gina de sucesso funcionalidadeTabelaAuxiliarFaixaManter =
         * Funcionalidade.TABELA_AUXILIAR_FAIXA_MANTER +
         * Funcionalidade.TELA_AREA_CONSTRUIDA; //Obt�m o tamanho da propriedade
         * da classe de acordo com length do mapeamento tamMaxCampoFaixaInicial =
         * HibernateUtil.getColumnSize(AreaConstruida.class,"faixaInicial");
         * tamMaxCampoFaixaFinal =
         * HibernateUtil.getColumnSize(AreaConstruida.class,"faixaFinal"); }
         */
        //********FIM DO BLOCO DE C�DIGO*******//
        //Parte da verifica��o do filtro
        FiltroTabelaAuxiliarFaixa filtroTabelaAuxiliarFaixa = null;

        //Verifica se o filtro foi informado pela p�gina de filtragem da tabela
        // auxiliar faixa
        if (httpServletRequest.getAttribute("filtroTabelaAuxiliarFaixa") != null) {
            filtroTabelaAuxiliarFaixa = (FiltroTabelaAuxiliarFaixa) httpServletRequest
                    .getAttribute("filtroTabelaAuxiliarFaixa");
        } else {
            //Caso o exibirManterTabelaFaixa n�o tenha passado por algum
            // esquema de filtro,
            //a quantidade de registros � verificada para avaliar a necessidade
            // de filtragem
            filtroTabelaAuxiliarFaixa = new FiltroTabelaAuxiliarFaixa();

            if (fachada.registroMaximo(TabelaAuxiliarFaixa.class) > ConstantesSistema.NUMERO_MAXIMO_REGISTROS_MANUTENCAO) {
                //Se o limite de registros foi atingido, a p�gina de filtragem
                // � chamada
                retorno = actionMapping
                        .findForward("filtrarTabelaAuxiliarFaixa");
                sessao.setAttribute("tela", tela);
            }
        }

        //A pesquisa de tabelas auxiliares s� ser� feita se o forward estiver
        // direcionado
        //para a p�gina de manterTabelaAuxiliar
        if (retorno.getName().equalsIgnoreCase("manterTabelaAuxiliarFaixa")) {
            //Seta a ordena��o desejada do filtro
            filtroTabelaAuxiliarFaixa
                    .setCampoOrderBy(FiltroTabelaAuxiliarFaixa.ID);
            //Pesquisa de tabelas auxiliares
            tabelasAuxiliaresFaixas = fachada.pesquisarTabelaAuxiliar(
                    filtroTabelaAuxiliarFaixa, pacoteNomeObjeto);

            if (tabelasAuxiliaresFaixas == null
                    || tabelasAuxiliaresFaixas.isEmpty()) {
                //Nenhum atividade cadastrado
                throw new ActionServletException("atencao.naocadastrado", null,
                        titulo);
            }

            //Verifica o numero de objetos retornados
            if (tabelasAuxiliaresFaixas.size() > ConstantesSistema.NUMERO_MAXIMO_REGISTROS_MANUTENCAO) {
                throw new ActionServletException(
                        "atencao.pesquisa.muitosregistros");
            }

            //A cole��o fica na sess�o devido ao esquema de pagina��o
            sessao.setAttribute("tabelasAuxiliaresFaixas",
                    tabelasAuxiliaresFaixas);
            //Envia o path do pacote na sess�o
            sessao.setAttribute("pacoteNomeObjeto", pacoteNomeObjeto);
        }

        //Envia os objetos na sess�o
        sessao.setAttribute("titulo", titulo);
        sessao.setAttribute("funcionalidadeTabelaAuxiliarFaixaManter",
                funcionalidadeTabelaAuxiliarFaixaManter);
        sessao.setAttribute("tamMaxCampoFaixaInicial", new Integer(
                tamMaxCampoFaixaInicial));
        sessao.setAttribute("tamMaxCampoFaixaFinal", new Integer(
                tamMaxCampoFaixaFinal));

        //Devolve o mapeamento de retorno
        return retorno;
    }
}
