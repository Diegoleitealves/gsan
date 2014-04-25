package gcom.gui.util.tabelaauxiliar.tipo;

import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.ErroRepositorioException;
import gcom.util.tabelaauxiliar.tipo.TabelaAuxiliarTipo;

import java.rmi.RemoteException;
import java.util.Date;

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
public class InserirTabelaAuxiliarTipoAction extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping,
            ActionForm actionForm, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) throws RemoteException,
            ErroRepositorioException {

        // Prepara o retorno da A��o
        ActionForward retorno = actionMapping.findForward("telaSucesso");

        //Obt�m a sessao
        HttpSession sessao = httpServletRequest.getSession(false);

        //Obt�m a fachada
        Fachada fachada = Fachada.getInstancia();

        //Recebe valor do objeto envia pela sess�o
        TabelaAuxiliarTipo tabelaAuxiliarTipo = 
        	(TabelaAuxiliarTipo) sessao.getAttribute("tabelaAuxiliarTipo");

        //Seta data/hora
        tabelaAuxiliarTipo.setUltimaAlteracao(new Date());
        
        //Insere objeto
        fachada.inserirTabelaAuxiliar(tabelaAuxiliarTipo);

        //Monta a p�gina de sucesso
        if (retorno.getName().equalsIgnoreCase("telaSucesso")) {
        	montarPaginaSucesso(httpServletRequest,
            "Dados inclu�dos com sucesso",
            "Inserir outro(a) " + ((String) sessao.getAttribute("titulo")),
            ((String) sessao.getAttribute("funcionalidadeTabelaAuxiliarTipoInserir")));
        }

        //Remove os objetos da sess�o
        sessao.removeAttribute("titulo");
        sessao.removeAttribute("tituloTipo");
        sessao.removeAttribute("tabelaAuxiliarTipo");
        sessao.removeAttribute("funcionalidadeTabelaAuxiliarTipoInserir");

        return retorno;
    }
}
