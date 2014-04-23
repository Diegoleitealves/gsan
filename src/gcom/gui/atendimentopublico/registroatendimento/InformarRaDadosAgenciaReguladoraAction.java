package gcom.gui.atendimentopublico.registroatendimento;

import gcom.atendimentopublico.registroatendimento.RaDadosAgenciaReguladora;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC0459] Informar Dados da Agencia Reguladora
 *
 * @author K�ssia Albuquerque
 * @date 04/04/2007
 */

public class InformarRaDadosAgenciaReguladoraAction extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		
			ActionForward retorno = actionMapping.findForward("telaSucesso");
			
			InformarRaDadosAgenciaReguladoraActionForm form =(InformarRaDadosAgenciaReguladoraActionForm) actionForm;
			
			Fachada fachada = Fachada.getInstancia();
			
			HttpSession sessao = httpServletRequest.getSession(false);		
			
			Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");
			
			RaDadosAgenciaReguladora raDadosAgenciaReguladora = new RaDadosAgenciaReguladora();
			
			Collection collectionRaDadosAgenciaReguladoraFone = (Collection)sessao.getAttribute("collectionRaDadosAgenciaReguladoraFone");
			
			// Atualiza a entidade com os valores do formul�rio
			form.setDadosAgenciaReguladora(raDadosAgenciaReguladora);
			
			// Atualiza na base de dados da Agencia Reguladora
			fachada.informarDadosAgenciaReguladora(raDadosAgenciaReguladora,
										collectionRaDadosAgenciaReguladoraFone,usuarioLogado);
			
			//Monta a p�gina de sucesso
			montarPaginaSucesso(httpServletRequest, "Reclama��o do RA de c�digo "+ raDadosAgenciaReguladora.getRegistroAtendimento().getId() +" inserido com sucesso." 
					, "Voltar","exibirInformarRaDadosAgenciaReguladoraAction.do?menu=sim");
			
			
			return retorno;
			}
}
