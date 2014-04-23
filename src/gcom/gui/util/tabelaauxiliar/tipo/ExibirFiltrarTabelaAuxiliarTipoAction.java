package gcom.gui.util.tabelaauxiliar.tipo;

import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
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
public class ExibirFiltrarTabelaAuxiliarTipoAction extends GcomAction {
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

        ActionForward retorno = actionMapping
                .findForward("filtrarTabelaAuxiliarTipo");

        //Obt�m a sessao
        HttpSession sessao = httpServletRequest.getSession(false);

        //Obt�m a fachada
        Fachada fachada = Fachada.getInstancia();

        //Declara��o de vari�veis e objetos
        Collection tipos = null;
        FiltroTabelaAuxiliarTipo filtroTabelaAuxiliarTipo = new FiltroTabelaAuxiliarTipo();
        String pacoteNomeObjeto = null;
        String tituloTipo = null;
        String titulo = null;
        int tamMaxCampoDescricao = 40;

        //Recebe valor do objeto envia pela sess�o
       // TabelaAuxiliarTipo tabelaAuxiliarTipo = (TabelaAuxiliarTipo) sessao
        //        .getAttribute("tabelaAuxiliarTipo");

        //********BLOCO DE C�DIGO PARA DEFINI��O DOS CADASTROS PERTENCENTES A
        // INSERIR TABELA AUXILIAR******//
        //        Para serem incluidos novos cadastros com c�digo e descri��o basta
        // apenas cria um novo
        //        if (condicional) semelhante ao exemplo abaixo, informando apenas os
        // dados relativos
        //        ao objeto desejado.

        //---BACIA
        //Identifica a string do objeto passado no get do request
        /*
         * if (tabelaAuxiliarTipo instanceof Bacia) { //Cria o objeto tipo
         * TipoPavimentoRua tipoPavimentoRua = new TipoPavimentoRua(); //Pega o
         * path do objeto mais o tipo pacoteNomeObjeto =
         * tipoPavimentoRua.getClass().getName(); //T�tulo da tela titulo =
         * "Bacia"; tituloTipo = "Tipo Pavimento Rua"; //Obt�m o tamanho da
         * propriedade da classe de acordo com length do mapeamento
         * tamMaxCampoDescricao =
         * HibernateUtil.getColumnSize(Bacia.class,"descricao"); }
         */
        //********FIM DO BLOCO DE C�DIGO*******//
        //Pesquisa o objeto de acordo com o filtro passado
        tipos = fachada.pesquisarTabelaAuxiliar(filtroTabelaAuxiliarTipo,
                pacoteNomeObjeto);
        //Adiciona o objeto no request
        httpServletRequest.setAttribute("tamMaxCampoDescricao", new Integer(
                tamMaxCampoDescricao));
        //Envia os objetos na sess�o
        sessao.setAttribute("titulo", titulo);
        sessao.setAttribute("tituloTipo", tituloTipo);
        sessao.setAttribute("tipos", tipos);

        return retorno;
    }

}
