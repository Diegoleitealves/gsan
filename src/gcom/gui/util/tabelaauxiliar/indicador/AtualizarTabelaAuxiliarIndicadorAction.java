package gcom.gui.util.tabelaauxiliar.indicador;

import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.tabelaauxiliar.indicador.TabelaAuxiliarIndicador;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author R�mulo Aur�lio
 *
 */
public class AtualizarTabelaAuxiliarIndicadorAction extends GcomAction {
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
		TabelaAuxiliarIndicadorActionForm form = (TabelaAuxiliarIndicadorActionForm) actionForm;

		//Seta o retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		//Obt�m a inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		//Obt�m a sess�o
		HttpSession sessao = httpServletRequest.getSession(false);

		//Recupera o ponto de coleta da sess�o
		TabelaAuxiliarIndicador tabelaAuxiliarIndicador = (TabelaAuxiliarIndicador) sessao
				.getAttribute("tabelaAuxiliarIndicador");
		
		String tela = (String) sessao.getAttribute("tela");
		
		String indicadorNegocio = null ;
		
		if (tela.equalsIgnoreCase("quadraPerfil")) {
			
			indicadorNegocio = form.getIndicadorBaixaRenda();
			
		}
		
		

		//Atualiza a tabela auxiliar com os dados inseridos pelo usu�rio
		//A data de �ltima altera��o n�o � alterada, pois ser� usada na
		//verifica��o de atualiza��o

		tabelaAuxiliarIndicador.setDescricao(form.getDescricao());
		
		if(form.getIndicadorBaixaRenda()!=null)
		tabelaAuxiliarIndicador.setIndicadorBaixaRenda(new Short(indicadorNegocio));

		if (form.getIndicadorUso() != null) {
			tabelaAuxiliarIndicador.setIndicadorUso(new Short(form
					.getIndicadorUso()));
		}
		
		//Atualiza os dados
		fachada.atualizarTabelaAuxiliar(tabelaAuxiliarIndicador);

		//Monta a p�gina de sucesso
		if (retorno.getName().equalsIgnoreCase("telaSucesso")) {

			montarPaginaSucesso(
					httpServletRequest,
					((String) sessao.getAttribute("titulo")) + " "
							+ tabelaAuxiliarIndicador.getId().toString()
							+ " atualizado(a) com sucesso.",
					"Realizar outra manuten��o de "
							+ ((String) sessao.getAttribute("titulo")),
					((String) sessao
							.getAttribute("funcionalidadeTabelaAuxiliarIndicadorFiltrar")));

		}

		//Remove os objetos da sess�o
		sessao.removeAttribute("funcionalidadeTabelaAuxiliarIndicadorManter");
		sessao.removeAttribute("titulo");
		sessao.removeAttribute("tabelaAuxiliarIndicador");
		sessao.removeAttribute("tamMaxCampoDescricao");


		//devolve o mapeamento de retorno
		return retorno;
	}

}
