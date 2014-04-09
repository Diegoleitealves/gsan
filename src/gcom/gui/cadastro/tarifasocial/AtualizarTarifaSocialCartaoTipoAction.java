package gcom.gui.cadastro.tarifasocial;

import gcom.cadastro.tarifasocial.TarifaSocialCartaoTipo;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
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
public class AtualizarTarifaSocialCartaoTipoAction extends GcomAction {
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

        //Obt�m o action form
        TarifaSocialCartaoTipoActionForm tarifaSocialCartaoTipoActionForm = (TarifaSocialCartaoTipoActionForm) actionForm;

        //Define a a��o de retorno
        ActionForward retorno = actionMapping.findForward("telaSucesso");

        //Mudar isso quando tiver esquema de seguran�a
        HttpSession sessao = httpServletRequest.getSession(false);

        //Obt�m a fachada
        Fachada fachada = Fachada.getInstancia();

        TarifaSocialCartaoTipo tarifaSocialCartaoTipo = (TarifaSocialCartaoTipo) sessao
                .getAttribute("tarifaSocialCartaoTipo");

        //Obt�m a descri��o
        String descricao = (String) tarifaSocialCartaoTipoActionForm
                .getDescricao();
        //Obt�m a descri��o abreviada
        String descricaoAbreviada = (String) tarifaSocialCartaoTipoActionForm
                .getDescricaoAbreviada();
        //Obt�m o indicador de exist�ncia de validade
        String validade = (String) tarifaSocialCartaoTipoActionForm
                .getValidade();

        //Obt�m e converte o indicador de uso
        Short indicadorDeUso = new Short(tarifaSocialCartaoTipoActionForm
                .getIndicadorUso());

        Short numeroMaximoMeses = null;

        if (!tarifaSocialCartaoTipoActionForm.getNumeroMaximoMeses().equals("")) {
            numeroMaximoMeses = new Short(tarifaSocialCartaoTipoActionForm
                    .getNumeroMaximoMeses());
            
            if (numeroMaximoMeses.intValue() == 0
					|| numeroMaximoMeses.intValue() > 99) {
				throw new ActionServletException(
						"atencao.numero.meses.fora.faixa.permitido");
			}
            
        }

        //Seta os campos para serem atualizados
        tarifaSocialCartaoTipo.setDescricao(descricao);
        tarifaSocialCartaoTipo.setDescricaoAbreviada(descricaoAbreviada);
        tarifaSocialCartaoTipo.setIndicadorValidade(new Short(validade));
        tarifaSocialCartaoTipo.setNumeroMesesAdesao(numeroMaximoMeses);
        tarifaSocialCartaoTipo.setIndicadorUso(indicadorDeUso);

        fachada.atualizarTarifaSocialCartaoTipo(tarifaSocialCartaoTipo);

        montarPaginaSucesso(httpServletRequest,
                "Tipo de Cart�o da Tarifa Social de c�digo  "
                        + tarifaSocialCartaoTipo.getId()
                        + " atualizado com sucesso.",
                "Realizar outra Manuten��o de Tipo de Cart�o da Tarifa Social",
                "filtrarTarifaSocialCartaoTipoAction.do?menu=sim");

        sessao.removeAttribute("TarifaSocialCartaoTipoActionForm");

        return retorno;
    }
}
