package gcom.gui.util.tabelaauxiliar.tipo;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.tabelaauxiliar.TabelaAuxiliar;
import gcom.util.tabelaauxiliar.tipo.FiltroTabelaAuxiliarTipo;

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
public class ExibirManterTabelaAuxiliarTipoAction extends GcomAction {
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
                .findForward("manterTabelaAuxiliarTipo");

        //Obt�m a instancia da fachada
        Fachada fachada = Fachada.getInstancia();

        //Cria a cole��o de tabelas auxiliares
        Collection tabelasAuxiliaresTipos = null;

        //Obt�m a inst�ncia da sess�o
        HttpSession sessao = httpServletRequest.getSession(false);

        //Obt�m o nome da tela passado no get do request
        String tela = httpServletRequest.getParameter("tela");

        //Declara��o de objetos e tipos primitivos
        String titulo = null;
        String tituloTipo = null;
        TabelaAuxiliar tabelaAuxiliarTipo = null;
        String pacoteNomeObjeto = null;
        String funcionalidadeTabelaAuxiliarTipoManter = null;
        Object objetoTipo = null;
        //Inicializa a variavel que ser� usada em uma fun��o para pegar a
        // substring
        String pacoteNomeObjetoTipo = null;

        //Verifica se o exibir manter foi chamado da tela de filtro
        if (httpServletRequest.getAttribute("filtroTabelaAuxiliarTipo") != null) {
            tela = (String) sessao.getAttribute("tela");
        }

        //********BLOCO DE C�DIGO PARA DEFINI��O DOS CADASTROS PERTENCENTES A
        // INSERIR TABELA AUXILIAR TIPO******//
        //        Para serem incluidos novos cadastros com c�digo, descri��o e tipo
        // basta apenas cria um novo
        //        if (condicional) semelhante ao exemplo abaixo, informando apenas os
        // dados relativos
        //        ao objeto desejado.

        //---BACIA
        //Identifica a string do objeto passado no get do request
        /*
         * if (tela.equals("bacia")) { //T�tulo a ser exido nas p�ginas titulo =
         * "Bacia"; tituloTipo = "Tipo Pavimento Rua"; //Cria o objeto principal
         * Bacia bacia = new Bacia(); //Cria o objeto tipo TipoPavimentoRua
         * tipoPavimentoRua = new TipoPavimentoRua(); //Associa o objeto tabela
         * auxiliar ao tipo criado tabelaAuxiliarTipo = bacia; //Associa o
         * objeto tipo ao tipo criado objetoTipo = tipoPavimentoRua; //Pega o
         * path do pacote mais o nome da classe pacoteNomeObjeto =
         * bacia.getClass().getName(); //Define o link a ser exibido na p�gina
         * de sucesso do inserir funcionalidadeTabelaAuxiliarTipoManter =
         * Funcionalidade.TABELA_AUXILIAR_TIPO_MANTER +
         * Funcionalidade.TELA_BACIA; }
         */
        //********FIM DO BLOCO DE C�DIGO*******//
        //Parte da verifica��o do filtro
        FiltroTabelaAuxiliarTipo filtroTabelaAuxiliarTipo = null;
        //Inicializa a vari�vel tipo
        //Integer tipo = null;

        //Verifica se o filtro foi informado pela p�gina de filtragem da tabela
        // auxiliar abreviada
        if (httpServletRequest.getAttribute("filtroTabelaAuxiliarTipo") != null) {
            filtroTabelaAuxiliarTipo = (FiltroTabelaAuxiliarTipo) httpServletRequest
                    .getAttribute("filtroTabelaAuxiliarTipo");
            //Pega pelo request a variavel tipo para saber se veio null ou n�o
        } else {
            //Caso o exibirManterTabelaAuxiliar n�o tenha passado por algum
            // esquema de filtro,
            //a quantidade de registros � verificada para avaliar a necessidade
            // de filtragem
            filtroTabelaAuxiliarTipo = new FiltroTabelaAuxiliarTipo();

            if (fachada.registroMaximo(TabelaAuxiliar.class) > ConstantesSistema.NUMERO_MAXIMO_REGISTROS_MANUTENCAO) {
                //Se o limite de registros foi atingido, a p�gina de filtragem
                // � chamada
                retorno = actionMapping
                        .findForward("filtrarTabelaAuxiliarTipo");
                sessao.setAttribute("tela", tela);
                //A vari�vel recebe apenas o nome da classe do objeto
                pacoteNomeObjetoTipo = getNomeClasse(objetoTipo);
                //Envia o objeto na sess�o
                sessao.setAttribute("tipo", pacoteNomeObjetoTipo);
            }
        }

        //A pesquisa de tabelas auxiliares s� ser� feita se o forward estiver
        // direcionado
        //para a p�gina de manterTabelaAuxiliar
        if (retorno.getName().equalsIgnoreCase("manterTabelaAuxiliarTipo")) {
            //Seta a ordena��o desejada do filtro
            filtroTabelaAuxiliarTipo
                    .setCampoOrderBy(FiltroTabelaAuxiliarTipo.ID);

            //Pesquisa de tabelas auxiliares
            tabelasAuxiliaresTipos = fachada.pesquisarTabelaAuxiliar(
                    filtroTabelaAuxiliarTipo, pacoteNomeObjeto);

            if (tabelasAuxiliaresTipos == null
                    || tabelasAuxiliaresTipos.isEmpty()) {
                //Nenhum atividade cadastrado
                throw new ActionServletException("atencao.naocadastrado");
            }

            //Verifica o numero de objetos retornados
            if (tabelasAuxiliaresTipos.size() > ConstantesSistema.NUMERO_MAXIMO_REGISTROS_MANUTENCAO) {
                throw new ActionServletException("atencao.pesquisa.muitosregistros");
            }

            //A cole��o fica na sess�o devido ao esquema de pagina��o
            sessao.setAttribute("tabelasAuxiliaresTipos",
                    tabelasAuxiliaresTipos);
            //Envia o path do pacote na sess�o
            sessao.setAttribute("pacoteNomeObjeto", pacoteNomeObjeto);

        }

        //Envia os objetos na sess�o
        sessao.setAttribute("titulo", titulo);
        sessao.setAttribute("tipoTitulo", tituloTipo);
        sessao.setAttribute("funcionalidadeTabelaAuxiliarTipoManter",
                funcionalidadeTabelaAuxiliarTipoManter);
        sessao.setAttribute("tabelaAuxiliarTipo", tabelaAuxiliarTipo);

        //Devolve o mapeamento de retorno
        return retorno;
    }
}
