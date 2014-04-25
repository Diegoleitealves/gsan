package gcom.gui.cadastro.tarifasocial;

import gcom.cadastro.tarifasocial.TarifaSocialCartaoTipo;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * < <Descri��o da Classe>>
 * 
 * @author Administrador
 */
public class InserirTarifaSocialCartaoTipoAction extends GcomAction {
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

		// Obt�m o action form
		TarifaSocialCartaoTipoActionForm tarifaSocialCartaoTipoActionForm = (TarifaSocialCartaoTipoActionForm) actionForm;

		// Seta o retorno para a tela de sucesso
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Obt�m a inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		// Seta o indicador de uso como ativo
		Short indicadorDeUsoAtivo = ConstantesSistema.INDICADOR_USO_ATIVO;

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

		// Cria o objeto com os valores setados
		TarifaSocialCartaoTipo tarifaSocialCartaoTipo = new TarifaSocialCartaoTipo(
				tarifaSocialCartaoTipoActionForm.getDescricao(),
				tarifaSocialCartaoTipoActionForm.getDescricaoAbreviada(),
				new Short(tarifaSocialCartaoTipoActionForm.getValidade()),
				numeroMaximoMeses, indicadorDeUsoAtivo, new Date());

		// Inseri o objeto
		fachada.inserir(tarifaSocialCartaoTipo);

		// M�todo utilizado para montar a p�gina de sucesso
		montarPaginaSucesso(httpServletRequest,
				"Tipo de Cart�o da Tarifa Social de c�digo "
						+ tarifaSocialCartaoTipo.getId()
						+ " inserido com sucesso.",
				"Inserir outro Tipo de Cart�o da Tarifa Social",
				"exibirInserirTarifaSocialCartaoTipoAction.do",
				"exibirAtualizarTarifaSocialCartaoTipoAction.do?idRegistroAtualizacao="+tarifaSocialCartaoTipo.getId(),
				"Atualizar Tipo de Cart�o da Tarifa Social Inserido");

		return retorno;
	}
}
