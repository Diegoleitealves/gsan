package gcom.gui.util.tabelaauxiliar;

import gcom.cadastro.geografico.RegiaoDesenvolvimento;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;

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
public class ExibirInserirTabelaAuxiliarAction extends GcomAction {
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
                .findForward("inserirTabelaAuxiliar");


        //Declara��o de objetos e tipos primitivos
        String descricao = "Descri��o";
        int tamanhoMaximoCampo = 40;

        //Cria a sess�o
        HttpSession sessao = httpServletRequest.getSession(false);

        //Obt�m a inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();
        
        SistemaParametro sistemaParametro = (SistemaParametro) fachada
		.pesquisarParametrosDoSistema();

        sessao.setAttribute("nomeSistema", sistemaParametro.getNomeEmpresa());
        
        //tempo da sess�o
        sessao.setMaxInactiveInterval(1000);
        
        String tela = httpServletRequest.getParameter("tela");
        
		DadosTelaTabelaAuxiliar dados = DadosTelaTabelaAuxiliar
				.obterDadosTelaTabelaAuxiliar(tela);
	

		if (dados.getTabelaAuxiliar() instanceof RegiaoDesenvolvimento) {
			tamanhoMaximoCampo = 20;
			
		}
        
        //Adiciona os objetos na sess�o
        sessao.setAttribute("tabelaAuxiliar", dados.getTabelaAuxiliar());
        sessao.setAttribute("funcionalidadeTabelaAuxiliarInserir",dados.getFuncionalidadeTabelaAuxInserir());
        sessao.setAttribute("titulo", dados.getTitulo());

        //Adiciona o objeto no request
        httpServletRequest.setAttribute("descricao", descricao);
        httpServletRequest.setAttribute("tamanhoMaximoCampo", new Integer(tamanhoMaximoCampo));
        httpServletRequest.setAttribute("tela",tela);

        return retorno;
    }

}
