package gcom.gui.atendimentopublico.ordemservico;

import gcom.atendimentopublico.ordemservico.FiltroServicoCobrancaValor;
import gcom.atendimentopublico.ordemservico.ServicoCobrancaValor;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.gui.ManutencaoRegistroActionForm;
import gcom.interceptor.RegistradorOperacao;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioAcao;
import gcom.seguranca.acesso.usuario.UsuarioAcaoUsuarioHelper;
import gcom.util.filtro.ParametroSimplesIn;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Descri��o da classe
 * 
 * @author R�mulo Aur�lio
 * @date 30/10/2006
 */
public class RemoverValorCobrancaServicoAction extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Obt�m a inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);
		
		Usuario usuarioLogado = (Usuario)sessao.getAttribute(Usuario.USUARIO_LOGADO);
		
		ManutencaoRegistroActionForm manutencaoRegistroActionForm = (ManutencaoRegistroActionForm) actionForm;

		// Obt�m os ids de remo��o
		String[] ids = manutencaoRegistroActionForm.getIdRegistrosRemocao();
		
		// mensagem de erro quando o usu�rio tenta excluir sem ter selecionado
		// nenhum
		// registro
		if (ids == null || ids.length == 0) {
			throw new ActionServletException(
					"atencao.registros.nao_selecionados");
		}
		
		FiltroServicoCobrancaValor filtroServicoCobrancaValor = new FiltroServicoCobrancaValor();
	
		Collection idsDocumentos = new ArrayList(ids.length);
	
		for (int i = 0; i < ids.length; i++) {
			
			idsDocumentos.add(new Integer(ids[i]));
		}
		
		filtroServicoCobrancaValor.adicionarParametro(new ParametroSimplesIn(FiltroServicoCobrancaValor.ID,idsDocumentos));

		Collection colecaoServicoCobrancaValor = fachada.pesquisar(filtroServicoCobrancaValor,
				ServicoCobrancaValor.class.getName());
		
		Iterator itera = colecaoServicoCobrancaValor.iterator();
		
		while(itera.hasNext()){
			
			ServicoCobrancaValor servicoCobrancaValor = (ServicoCobrancaValor) itera.next();
			
			// ------------ REGISTRAR TRANSA��O ----------------
			RegistradorOperacao registradorOperacao = new RegistradorOperacao(
					Operacao.OPERACAO_VALOR_COBRANCA_SERVICO_REMOVER, servicoCobrancaValor.getServicoTipo().getId(),
					servicoCobrancaValor.getServicoTipo().getId(), new UsuarioAcaoUsuarioHelper(usuarioLogado,
					UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));
			// ------------ REGISTRAR TRANSA��O ----------------
			registradorOperacao.registrarOperacao(servicoCobrancaValor);
			
			fachada.remover(servicoCobrancaValor);
		}
		
		Integer idQt = ids.length;

		montarPaginaSucesso(httpServletRequest, idQt.toString()
				+ " Valor(es) de Cobran�a removido(s) com sucesso.",
				"Manter outro Valor de Cobran�a do Servi�o",
				"exibirFiltrarValorCobrancaServicoAction.do?menu=sim");

		return retorno;
	}

}
