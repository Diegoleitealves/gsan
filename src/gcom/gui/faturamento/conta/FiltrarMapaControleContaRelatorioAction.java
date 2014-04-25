package gcom.gui.faturamento.conta;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class FiltrarMapaControleContaRelatorioAction extends GcomAction {

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
                .findForward("telaSucesso");

        FiltrarMapaControleContaRelatorioActionForm filtrarMapaControleContaRelatorioActionForm = (FiltrarMapaControleContaRelatorioActionForm) actionForm;
        //Mudar isso quando tiver esquema de seguran�a
        HttpSession sessao = httpServletRequest.getSession(false);
        Fachada fachada = Fachada.getInstancia();
        
        Usuario usuarioLogado = (Usuario)(httpServletRequest.getSession(false)).getAttribute("usuarioLogado");
        
       // Fachada fachada = Fachada.getInstancia();
        //Variaveis
        String idFaturamentoGrupo = (String) filtrarMapaControleContaRelatorioActionForm
                .getIdGrupoFaturamento();
        
        sessao.removeAttribute("indicadorAtualizar");
        
        String mesAno = (String)filtrarMapaControleContaRelatorioActionForm.getMesAno();
        
        boolean parametroInformado = false;
        String mesAnoSemBarra = "";
        
        if (idFaturamentoGrupo != null
                && !idFaturamentoGrupo.trim().equalsIgnoreCase("")
                && !idFaturamentoGrupo.trim().equalsIgnoreCase("-1")
                && mesAno != null && !mesAno.trim().equals("")) {
        	String mes = mesAno.substring(0, 2);
        	String ano = mesAno.substring(3, 7);
        	mesAnoSemBarra = mes+ano;
            parametroInformado = true;
        }
        
        String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");
		        
        if(parametroInformado){
        	//chamar metodo de filtragem
        	fachada.filtrarMapaControleContaRelatorio(new Integer(idFaturamentoGrupo), 
        			mesAnoSemBarra, usuarioLogado, tipoRelatorio, filtrarMapaControleContaRelatorioActionForm.getIndicadorFichaCompensacao());
        }else{
        	throw new ActionServletException(
				"atencao.filtro.nenhum_parametro_informado");
        }
        
//  	 montando p�gina de sucesso
		montarPaginaSucesso(httpServletRequest,
				"Gerar Relat�rio Mapa de Controle das Contas", "Voltar",
				"/exibirMapaControContalaRelatorioAction.do");
		
        return retorno;
    }

}
