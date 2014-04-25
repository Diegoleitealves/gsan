package gcom.gui.util.tabelaauxiliar.tipo;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.gui.ManutencaoRegistroActionForm;
import gcom.util.filtro.ParametroSimples;
import gcom.util.tabelaauxiliar.tipo.FiltroTabelaAuxiliarTipo;
import gcom.util.tabelaauxiliar.tipo.TabelaAuxiliarTipo;

import java.util.Collection;
import java.util.Iterator;

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
public class ExibirAtualizarTabelaAuxiliarTipoAction extends GcomAction {
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
                .findForward("atualizarTabelaAuxiliarTipo");

        //Obt�m a inst�ncia da sess�o
        HttpSession sessao = httpServletRequest.getSession(false);

        //Obt�m a inst�ncia da fachada
        Fachada fachada = Fachada.getInstancia();

        //Pega o actionform associado
        ManutencaoRegistroActionForm manutencaoRegistroActionForm = (ManutencaoRegistroActionForm) actionForm;

        //C�digo da tabela auxiliar a ser atualizada
        String id = manutencaoRegistroActionForm.getIdRegistroAtualizacao();

        //Cria o filtro para atividade
        FiltroTabelaAuxiliarTipo filtroTabelaAuxiliarTipo = new FiltroTabelaAuxiliarTipo();

        //String com path do pacote mais o nome do objeto
        String pacoteNomeObjeto = (String) sessao
                .getAttribute("pacoteNomeObjeto");

        //Declara��o de vari�veis e objetos
        //String tituloTipo = null;
        //String titulo = null;
        //Object objetoTipo = null;
        String pacoteNomeObjetoTipo = null;
        //String funcionalidadeTabelaAuxiliarTipoManter = null;
        int tamMaxCampoDescricao = 40;

        //Recebe valor do objeto envia pela sess�o
        TabelaAuxiliarTipo tabelaAuxiliarTipo = (TabelaAuxiliarTipo) sessao
                .getAttribute("tabelaAuxiliarTipo");

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
         * if (tabelaAuxiliarTipo instanceof Bacia) { //Cria o objeto pricipal
         * Bacia bacia = new Bacia(); //Cria o objeto tipo TipoPavimentoRua
         * tipoPavimentoRua = new TipoPavimentoRua(); //Pega o path do pacote
         * mais o tipo do objeto pacoteNomeObjetoTipo =
         * tipoPavimentoRua.getClass().getName(); //T�tulo da p�gina titulo =
         * "Bacia"; tituloTipo = "Tipo Pavimento Rua"; //Associa o objeto tabela
         * auxiliar ao tipo criado tabelaAuxiliarTipo = bacia; //Associa o
         * objeto tipo ao tipo criado objetoTipo = tipoPavimentoRua; //Define o
         * link a ser exibido na p�gina de sucesso
         * funcionalidadeTabelaAuxiliarTipoManter =
         * Funcionalidade.TABELA_AUXILIAR_TIPO_MANTER +
         * Funcionalidade.TELA_BACIA; //Obt�m o tamanho da propriedade da classe
         * de acordo com length do mapeamento tamMaxCampoDescricao =
         * HibernateUtil.getColumnSize(Bacia.class,"descricao"); }
         */
        //********FIM DO BLOCO DE C�DIGO*******//
        //Pesquisa o objeto de acordo com o filtro passado
        Collection colecaoTipos = fachada.pesquisarTabelaAuxiliar(
                filtroTabelaAuxiliarTipo, pacoteNomeObjetoTipo);

        //Caso a cole��o esteja vazia, indica erro inesperado
        if (colecaoTipos == null || colecaoTipos.isEmpty()) {
            throw new ActionServletException("erro.sistema");
        }
        //Manda a cole��o de tipos da tabela auxiliar na sess�o
        httpServletRequest.setAttribute("colecaoTipos", colecaoTipos);

        //Adiciona o par�metro no filtro
        filtroTabelaAuxiliarTipo.adicionarParametro(new ParametroSimples(
                FiltroTabelaAuxiliarTipo.ID, id));

        //Pesquisa a tabela auxiliar no sistema
        Collection tabelasAuxiliaresTipos = fachada.pesquisarTabelaAuxiliar(
                filtroTabelaAuxiliarTipo, pacoteNomeObjeto);

        //Caso a cole��o esteja vazia, indica erro inesperado
        if (tabelasAuxiliaresTipos == null || tabelasAuxiliaresTipos.isEmpty()) {
            throw new ActionServletException("erro.sistema");
        }

        Iterator iteratorTabelaAuxiliarTipo = tabelasAuxiliaresTipos.iterator();

        //A tabela auxiliar tipo que ser� atualizada
        tabelaAuxiliarTipo = (TabelaAuxiliarTipo) iteratorTabelaAuxiliarTipo
                .next();

        //Manda os objetos na sess�o
        sessao.setAttribute("tabelaAuxiliarTipo", tabelaAuxiliarTipo);
        httpServletRequest.setAttribute("tipo", tabelaAuxiliarTipo.getIdTipo());
        httpServletRequest.setAttribute("tamMaxCampoDescricao", new Integer(
                tamMaxCampoDescricao));

        //Devolve o mapeamento de retorno
        return retorno;
    }

}
