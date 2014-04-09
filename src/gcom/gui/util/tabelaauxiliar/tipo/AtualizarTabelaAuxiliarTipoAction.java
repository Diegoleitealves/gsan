package gcom.gui.util.tabelaauxiliar.tipo;

import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.tabelaauxiliar.tipo.TabelaAuxiliarTipo;

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
public class AtualizarTabelaAuxiliarTipoAction extends GcomAction {
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

        //Pega o action form
        TabelaAuxiliarTipoActionForm tabelaAuxiliarTipoActionForm = (TabelaAuxiliarTipoActionForm) actionForm;

        //Seta o retorno
        ActionForward retorno = actionMapping.findForward("telaSucesso");

        //Obt�m a inst�ncia da fachada
        Fachada fachada = Fachada.getInstancia();

        //Obt�m a sess�o
        HttpSession sessao = httpServletRequest.getSession(false);

        //inicializa o tipo do objeto para null
        //Object objetoTipo = null;

        //Recupera o ponto de coleta da sess�o
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
         * if (tabelaAuxiliarTipo instanceof Bacia) { //Cria o objeto principal
         * Bacia bacia = (Bacia) tabelaAuxiliarTipo; //Cria o objeto tipo
         * TipoPavimentoRua tipoPavimentoRua = new TipoPavimentoRua(); //Seta o
         * id do obejeto tipo tipoPavimentoRua.setId(new
         * Integer(tabelaAuxiliarTipoActionForm.getTipo())); //Seta o objeto
         * pricipal com o tipo bacia.setTipoPavimentoRua(tipoPavimentoRua);
         * //Faz uma referencia tabelaAuxiliarTipo = bacia; }
         */
        //********FIM DO BLOCO DE C�DIGO*******//
        //Atualiza a tabela auxiliar tipo com os dados inseridos pelo usu�rio
        //A data de �ltima altera��o n�o � alterada, pois ser� usada na
        //verifica��o de atualiza��o
        tabelaAuxiliarTipo.setDescricao(tabelaAuxiliarTipoActionForm
                .getDescricao());

        //Atualiza os dados
        fachada.atualizarTabelaAuxiliar(tabelaAuxiliarTipo);

        //Monta a p�gina de sucesso
        if (retorno.getName().equalsIgnoreCase("telaSucesso")) {

            montarPaginaSucesso(
                    httpServletRequest,
                    ((String) sessao.getAttribute("titulo"))
                            + " atualizada com sucesso",
                    "Realizar outra manuten��o de "
                            + ((String) sessao.getAttribute("titulo")),
                    ((String) sessao
                            .getAttribute("funcionalidadeTabelaAuxiliarTipoManter")));

        }

        //Remove os objetos da sess�o
        sessao.removeAttribute("funcionalidadeTabelaAuxiliarTipoManter");
        sessao.removeAttribute("titulo");
        sessao.removeAttribute("tabelaAuxiliarTipo");
        sessao.removeAttribute("tamMaxCampoDescricao");
        sessao.removeAttribute("tipoTitulo");
        sessao.removeAttribute("tabelaAuxiliarTipo");
        sessao.removeAttribute("tabelasAuxiliaresTipos");

        //devolve o mapeamento de retorno
        return retorno;
    }

}
