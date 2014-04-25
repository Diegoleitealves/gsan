package gcom.gui.util.tabelaauxiliar.abreviadatipo;

import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.operacional.SistemaAbastecimento;
import gcom.util.ConstantesSistema;
import gcom.util.tabelaauxiliar.abreviadatipo.TabelaAuxiliarAbreviadaTipo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <Esse componente serve para SetorAbastecimento e ZonaAbastecimento, sendo o tipo SistemaAbastecimento>
 * 
 * @author Administrador
 */
public class AtualizarTabelaAuxiliarAbreviadaTipoAction extends GcomAction {
    /**
     * <Descri��o do M�todo>
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
        TabelaAuxiliarAbreviadaTipoActionForm tabelaAuxiliarAbreviadaTipoActionForm = (TabelaAuxiliarAbreviadaTipoActionForm) actionForm;

        //Seta o retorno
        ActionForward retorno = actionMapping.findForward("telaSucesso");

        //Obt�m a inst�ncia da fachada
        Fachada fachada = Fachada.getInstancia();

        //Obt�m a sess�o
        HttpSession sessao = httpServletRequest.getSession(false);

        //Recupera o ponto de coleta da sess�o
        TabelaAuxiliarAbreviadaTipo tabelaAuxiliarAbreviadaTipo = (TabelaAuxiliarAbreviadaTipo) sessao
                .getAttribute("tabelaAuxiliarAbreviadaTipo");

        //Atualiza a tabela auxiliar com os dados inseridos pelo usu�rio
        //A data de �ltima altera��o n�o � alterada, pois ser� usada na
        //verifica��o de atualiza��o
        tabelaAuxiliarAbreviadaTipo.setDescricao(tabelaAuxiliarAbreviadaTipoActionForm
                .getDescricao().toUpperCase());
        tabelaAuxiliarAbreviadaTipo.setDescricaoAbreviada(tabelaAuxiliarAbreviadaTipoActionForm
                .getDescricaoAbreviada().toUpperCase());
        
        
        if(tabelaAuxiliarAbreviadaTipoActionForm.getIndicadorUso()!=null){
        	tabelaAuxiliarAbreviadaTipo.setIndicadorUso(new Short(tabelaAuxiliarAbreviadaTipoActionForm.getIndicadorUso()));
        }
        if (tabelaAuxiliarAbreviadaTipoActionForm.getSistemaAbastecimento() != null
				&& !tabelaAuxiliarAbreviadaTipoActionForm
						.getSistemaAbastecimento().equals("")
				&& !tabelaAuxiliarAbreviadaTipoActionForm
						.getSistemaAbastecimento().equals(
								"" + ConstantesSistema.NUMERO_NAO_INFORMADO)) {
        	SistemaAbastecimento sistemaAbastecimento = new SistemaAbastecimento();	
        	sistemaAbastecimento.setId(new Integer(tabelaAuxiliarAbreviadaTipoActionForm.getSistemaAbastecimento()));
        	tabelaAuxiliarAbreviadaTipo.setSistemaAbastecimento(sistemaAbastecimento);
        } else {
        	tabelaAuxiliarAbreviadaTipo.setSistemaAbastecimento(null);
        }

        //Atualiza os dados
        fachada.atualizarTabelaAuxiliar(tabelaAuxiliarAbreviadaTipo);

        //Monta a p�gina de sucesso
        if (retorno.getName().equalsIgnoreCase("telaSucesso")) {

            montarPaginaSucesso(
                    httpServletRequest,
                    ((String) sessao.getAttribute("titulo")) + " " + tabelaAuxiliarAbreviadaTipo.getId().toString()
                            + " atualizado(a) com sucesso.",
                    "Realizar outra manuten��o de "
                            + ((String) sessao.getAttribute("titulo")),
                    ((String) sessao
                            .getAttribute("funcionalidadeTabelaAuxiliarAbreviadaTipoFiltrar"))+"&menu=sim");

        }

        //Remove os objetos da sess�o
        sessao.removeAttribute("funcionalidadeTabelaAuxiliarAbreviadaTipoManter");
        sessao.removeAttribute("titulo");
        sessao.removeAttribute("tabelaAuxiliarAbreviadaTipo");
        sessao.removeAttribute("tamMaxCampoDescricao");
        sessao.removeAttribute("tamMaxCampoDescricaoAbreviada");
        sessao.removeAttribute("descricao");
        sessao.removeAttribute("descricaoAbreviada");
        sessao.removeAttribute("tituloTipo");
        sessao.removeAttribute("tabelaAuxiliarTipo");
        sessao.removeAttribute("tabelasAuxiliaresTipos");
        sessao.removeAttribute("totalRegistros");
        sessao.removeAttribute("tipo");
        

        //devolve o mapeamento de retorno
        return retorno;
    }

}
