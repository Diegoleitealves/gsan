package gcom.gui.faturamento.conta;

import gcom.cadastro.imovel.Imovel;
import gcom.fachada.Fachada;
import gcom.faturamento.VencimentoAlternativo;
import gcom.gui.GcomAction;
import gcom.interceptor.RegistradorOperacao;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioAcao;
import gcom.seguranca.acesso.usuario.UsuarioAcaoUsuarioHelper;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExcluirDiaVencimentoAlternativoAction extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		Fachada fachada = Fachada.getInstancia();

		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);
		
		Usuario usuarioLogado = (Usuario)sessao.getAttribute(Usuario.USUARIO_LOGADO);

		// Inst�ncia do formul�rio que est� sendo utilizado
		InformarVencimentoAlternativoActionForm informarVencimentoAlternativoActionForm = (InformarVencimentoAlternativoActionForm) actionForm;

		Imovel imovel = (Imovel) sessao.getAttribute("imovel");
		VencimentoAlternativo vencimentoAlternativoExcluir = (VencimentoAlternativo) sessao
				.getAttribute("vencimentoAlternativo");

        // Hugo Leonardo
    	// ------------ REGISTRAR TRANSA��O ----------------
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(
				Operacao.OPERACAO_EXCLUIR_VENCIMENTO_ALTERNATIVO, vencimentoAlternativoExcluir.getImovel().getId(),
				vencimentoAlternativoExcluir.getImovel().getId(), new UsuarioAcaoUsuarioHelper(usuarioLogado,
				UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));
		// ------------ REGISTRAR TRANSA��O ----------------

		if (imovel != null && !imovel.equals("")) {
			
			//------------ REGISTRAR TRANSA��O ----------------
	        registradorOperacao.registrarOperacao(imovel);
	        //------------ REGISTRAR TRANSA��O ----------------
	        
			imovel.setDiaVencimento(null);
			imovel.setIndicadorVencimentoMesSeguinte((short) 2);
			imovel.setUltimaAlteracao(new Date());

			fachada.atualizar(imovel);
		}
		if (vencimentoAlternativoExcluir != null && !vencimentoAlternativoExcluir.equals("")) {
			
			vencimentoAlternativoExcluir.setDateExclusao(new Date());
			vencimentoAlternativoExcluir.setUltimaAlteracao(new Date());
			
			// ------------ REGISTRAR TRANSA��O ----------------
			registradorOperacao.registrarOperacao(vencimentoAlternativoExcluir);
	        //------------ REGISTRAR TRANSA��O ----------------
			
			fachada.atualizar(vencimentoAlternativoExcluir);
		}

		sessao.removeAttribute("colecaoNovoDiaVencimento");
		sessao.removeAttribute("imovel");
		sessao.removeAttribute("vencimentoAlternativo");
		montarPaginaSucesso(httpServletRequest,
				"Vencimento Alternativo para o im�vel "
						+ informarVencimentoAlternativoActionForm.getIdImovel()
						+ " removido com sucesso.", "Informar outro Vencimento Alternativo",
				"exibirInformarVencimentoAlternativoAction.do?menu=sim");

		return retorno;

	}

}
