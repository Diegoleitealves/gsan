package gcom.gui.util.tabelaauxiliar.faixa;

import gcom.gui.GcomAction;
import gcom.util.tabelaauxiliar.faixa.TabelaAuxiliarFaixa;

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
public class ExibirInserirTabelaAuxiliarFaixaAction extends GcomAction {
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

        //Prepara o retorno da A��o
        ActionForward retorno = actionMapping
                .findForward("inserirTabelaAuxiliarFaixa");

        //Pega o parametro passado no request
        //String tela = (String) httpServletRequest.getParameter("tela");

        //Declara��o de objetos e tipos primitivos
        String titulo = null;
        int tamMaxCampoFaixaInicial = 3;
        int tamMaxCampoFaixaFinal = 3;
        TabelaAuxiliarFaixa tabelaAuxiliarFaixa = null;
        String funcionalidadeTabelaAuxiliarFaixaInserir = null;

        //********BLOCO DE C�DIGO PARA DEFINI��O DOS CADASTROS PERTENCENTES A
        // INSERIR TABELA AUXILIAR FAIXA******//
        //        Para serem incluidos novos cadastros com c�digo, faixa inicial e
        // faixa final basta apenas cria um novo
        //        if (condicional) semelhante ao exemplo abaixo, informando apenas os
        // dados relativos
        //        ao objeto desejado.

        //Identifica a string do objeto passado no get do request
        /*
         * if (tela.equals("areaConstruida")) { //T�tulo a ser exido nas p�ginas
         * titulo = "�rea Constru�da"; //Cria o objeto AreaConstruida
         * areaConstruida = new AreaConstruida(); //Associa o objeto tabela
         * auxiliar ao tipo criado tabelaAuxiliarFaixa = areaConstruida;
         * //Define o link a ser exibido na p�gina de sucesso do inserir
         * funcionalidadeTabelaAuxiliarFaixaInserir =
         * Funcionalidade.TABELA_AUXILIAR_FAIXA_INSERIR +
         * Funcionalidade.TELA_AREA_CONSTRUIDA; //Obt�m o tamanho da propriedade
         * da classe de acordo com length do mapeamento tamMaxCampoFaixaInicial =
         * HibernateUtil.getColumnSize(AreaConstruida.class,"faixaInicial");
         * tamMaxCampoFaixaFinal =
         * HibernateUtil.getColumnSize(AreaConstruida.class,"faixaFinal"); }
         */

        //********FIM DO BLOCO DE C�DIGO*******//
        //Cria a sess�o
        HttpSession sessao = httpServletRequest.getSession(false);

        //tempo da sess�o
        sessao.setMaxInactiveInterval(1000);
        //Adiciona os objetos na sess�o
        sessao.setAttribute("tabelaAuxiliarFaixa", tabelaAuxiliarFaixa);
        sessao.setAttribute("funcionalidadeTabelaAuxiliarFaixaInserir",
                funcionalidadeTabelaAuxiliarFaixaInserir);
        sessao.setAttribute("titulo", titulo);
        //Adiciona os objetos no request
        httpServletRequest.setAttribute("tamMaxCampoFaixaInicial", new Integer(
                tamMaxCampoFaixaInicial));
        httpServletRequest.setAttribute("tamMaxCampoFaixaFinal", new Integer(
                tamMaxCampoFaixaFinal));

        return retorno;
    }

}
